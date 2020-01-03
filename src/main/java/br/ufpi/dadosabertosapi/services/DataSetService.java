package br.ufpi.dadosabertosapi.services;

import br.ufpi.dadosabertosapi.database.local.model.DataSetInfo;
import br.ufpi.dadosabertosapi.exception.DataSetInfoSaveException;

public interface DataSetService {

	DataSetInfo salvar(DataSetInfo dataSetInfo) throws DataSetInfoSaveException;

}
