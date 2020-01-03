package br.ufpi.dadosabertosapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.ufpi.dadosabertosapi.database.local.dao.DataSetInfoRepository;
import br.ufpi.dadosabertosapi.database.local.model.DataSetInfo;
import br.ufpi.dadosabertosapi.exception.DataSetInfoSaveException;
import br.ufpi.dadosabertosapi.services.DataSetService;

@RestController
@RequestMapping("dataset")
public class DadosAbertosAdminResource {
	
	@Autowired
	private DataSetInfoRepository dataSetInfoRepository;
	
	@Autowired
	private DataSetService dataSetService;
	
	@RequestMapping(value="/list",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<DataSetInfo>> getCargosComissionados() throws Exception{

		List<DataSetInfo> listResult = dataSetInfoRepository.findAll();
		ResponseEntity<List<DataSetInfo>> response = new ResponseEntity<List<DataSetInfo>>(listResult, HttpStatus.OK);
		return response;
		
	}
	
	@RequestMapping(value="/salva",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataSetInfo> salvarDataSetInfo(@RequestBody DataSetInfo dataSetInfo, HttpServletResponse response){

		DataSetInfo listResult;
		try {
			listResult = dataSetService.salvar(dataSetInfo);
		} catch (DataSetInfoSaveException e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		ResponseEntity<DataSetInfo> dataSetResponse = new ResponseEntity<DataSetInfo>(listResult, HttpStatus.OK);
		return dataSetResponse;
		
	}

}
