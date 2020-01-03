package br.ufpi.dadosabertosapi.resource;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.ufpi.dadosabertosapi.services.OpenDataService;

@RestController
public class DadosAbertosResource {
	
	@Autowired
	private OpenDataService dadosAbertosService;
	
	@RequestMapping(value="/{package}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<String[]>> getCargosComissionados(@PathParam(value="package") String nome_package) throws Exception{

		List<String[]> listResult = dadosAbertosService.list(nome_package);
		ResponseEntity<List<String[]>> response = new ResponseEntity<List<String[]>>(listResult, HttpStatus.OK);
		return response;
		
	}


}
