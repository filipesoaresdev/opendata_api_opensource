package br.ufpi.dadosabertosapi.services;

import java.util.List;

public interface OpenDataService {

	List<String[]> list(String path) throws Exception;

	void uploadAllFiles();

}
