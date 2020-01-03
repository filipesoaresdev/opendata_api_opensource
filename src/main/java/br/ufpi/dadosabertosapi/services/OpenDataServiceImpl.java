package br.ufpi.dadosabertosapi.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpi.dadosabertosapi.database.database1.dao.Database1DAO;
import br.ufpi.dadosabertosapi.database.database2.dao.Database2DAO;
import br.ufpi.dadosabertosapi.database.database3.dao.Database3DAO;
import br.ufpi.dadosabertosapi.database.database4.dao.Database4DAO;
import br.ufpi.dadosabertosapi.database.general.DAO;
import br.ufpi.dadosabertosapi.database.local.dao.DataSetInfoRepository;
import br.ufpi.dadosabertosapi.database.local.model.DataSetInfo;
import br.ufpi.dadosabertosapi.exception.DataBaseNotFoundException;
import br.ufpi.dadosabertosapi.uploadfile.UploadFileAPICKAN;
import br.ufpi.dadosabertosapi.utils.CSVGenerator;
import br.ufpi.dadosabertosapi.utils.ConstantesCKAN;

/**
 * 
 * 
 * @author FilipeSoares-STI
 */
@Service
public class OpenDataServiceImpl implements OpenDataService {
	
	@Autowired
	private Database3DAO database3dao;
	@Autowired
	private Database4DAO database4dao;
	@Autowired
	private Database1DAO database1dao;
	@Autowired
	private Database2DAO database2dao;
	@Autowired
	private DataSetInfoRepository dataSetInfoRepository;
	
	@Override
	public List<String[]> list(String nome_package) throws Exception{
		List<DataSetInfo> dataSetList = dataSetInfoRepository.findByNomePackage(nome_package);
		if(dataSetList == null || dataSetList.isEmpty()) {
			throw new Exception("O Package - "+ nome_package + " não possui cadastro no banco.");
		}
		return database4dao.listPureByQuery(dataSetList.get(0).getQuery());
	}
	

	@Override
	public void uploadAllFiles() {
		
		
		List<DataSetInfo> list = dataSetInfoRepository.findAll();
		
		list.forEach(data -> {
			try {
				//Verifica se já é tempo para realizar um novo upload. 
				if(data.isAvailableToUpload())
					uploadFile(data);
			} catch (DataBaseNotFoundException | IllegalArgumentException | IllegalAccessException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}
	
	protected void uploadFile( DataSetInfo dataSetInfo ) throws  ClientProtocolException, IllegalArgumentException, IllegalAccessException, IOException, DataBaseNotFoundException {
		DAO dao = null;
		
		//Verifica o banco de dados que é feita a consulta. Esse banco é identicado no registo do Conjunto de Dados (DataSet) guardado no banco local.
		switch (dataSetInfo.getTipoDataBase()) {
			case "DATABASE3":
				dao = database3dao;
				break;
			case "DATABASE1":
				dao = database1dao;
				break;
			case "DATABASE4":
				dao = database4dao;
				break;
			case "DATABASE2":
				dao = database2dao;
				break;
			default:
				throw new DataBaseNotFoundException("Banco de consulta inválido");
		}
		
		byte[] file = CSVGenerator.gerarCSV(dao, dataSetInfo.getQuery() );
		
		//Cria o recurso no CKAN, enviando arquivo CSV
		UploadFileAPICKAN.criarRecursoComArquivo(file,dataSetInfo,ConstantesCKAN.FORMAT_CSV);
		
		//O mesmo que acima, mas arquivo xls
		UploadFileAPICKAN.criarRecursoComArquivo(CSVGenerator.csvToExcel(file),dataSetInfo,ConstantesCKAN.FORMAT_XLS);
		
		dataSetInfo.setLastUpdate(LocalDate.now());
		
		dataSetInfoRepository.save(dataSetInfo);	
	}
	

}
