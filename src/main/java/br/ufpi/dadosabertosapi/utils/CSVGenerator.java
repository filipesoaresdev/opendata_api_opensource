package br.ufpi.dadosabertosapi.utils;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import br.ufpi.dadosabertosapi.database.general.DAO;

public class CSVGenerator {
	
	public static byte[] csvToExcel(byte[] file) {

		CSVReader reader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(file)));
		ByteArrayOutputStream byteReturn = null;
		Workbook wb = null;
		SXSSFSheet sheet;
		String[] nextLine;
		
		try {
			byteReturn = new ByteArrayOutputStream();
			
			wb = new SXSSFWorkbook();
			sheet = (SXSSFSheet) wb.createSheet("Dados");
			int rowNum = 0;
			while((nextLine = reader.readNext()) !=null) {
				
				Row currentRow = sheet.createRow(rowNum++);
                for(int i=0; i < nextLine.length; i++) {
                    if(NumberUtils.isDigits(nextLine[i])) {
                        currentRow.createCell(i).setCellValue(Integer.parseInt(nextLine[i]));
                    } else if (NumberUtils.isCreatable(nextLine[i])) {
                        currentRow.createCell(i).setCellValue(Double.parseDouble(nextLine[i]));
                    } else {
                        currentRow.createCell(i).setCellValue(nextLine[i]);
                    }
                }
				
			}
		
			wb.write(byteReturn);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				wb.close();
				reader.close();
				byteReturn.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return byteReturn.toByteArray();
		
	}

	/**
	 * 
	 * @param dao Ele recebe como parametro o DAO referente ao banco onde será realizada a consulta.
	 * @param query Essa é a query nativa que será executada no dao
	 * @return Retorna o arquivo CSV em formato byte[]
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static byte[] gerarCSV(DAO dao, String query) throws IOException, IllegalArgumentException, IllegalAccessException{
		
		
		List<String[]> listResult = dao.listPureByQuery(query);
		if(listResult == null || listResult.size() == 0){
			return null;
		}

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BufferedWriter outputFile = new BufferedWriter( new OutputStreamWriter(output));

		CSVWriter writer = new CSVWriter(outputFile);

		for(String[] row : listResult){
			writer.writeNext(row);
		}

		writer.close();
		return output.toByteArray();
	}

	//	private <T> byte[] gerarCSV(List<T> listResult, Class classe) throws IOException, IllegalArgumentException, IllegalAccessException{
	//		
	//		
	//		List<String> listaHeader = new ArrayList<String>();
	//		Field[] atributos = classe.getDeclaredFields();
	//		int size = atributos.length;
	//		
	//		for(Field att : atributos){
	//			if(att.isAnnotationPresent(NameCKAN.class)){
	//				listaHeader.add(((NameCKAN)att.getAnnotation(NameCKAN.class)).name());
	//			}
	//		}
	//		
	//		ByteArrayOutputStream output = new ByteArrayOutputStream();
	//		BufferedWriter outputFile = new BufferedWriter( new OutputStreamWriter(output));
	//		
	//		CSVWriter writer = new CSVWriter(outputFile);
	//		
	//		writer.writeNext(listaHeader.toArray(new String[size]));
	//
	//		for(T item : listResult){
	//			List<String> listRow = new  ArrayList<>();
	//			for(Field att : atributos){
	//				if(att.isAnnotationPresent(NameCKAN.class)){
	//					listRow.add((String)att.get(item));
	//				}
	//			}
	//
	//			writer.writeNext(listRow.toArray(new String[size]));
	//		}
	//		
	//		
	//		writer.close();
	//		return output.toByteArray();
	//	}

}
