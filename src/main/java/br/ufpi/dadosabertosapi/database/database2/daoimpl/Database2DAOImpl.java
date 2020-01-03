package br.ufpi.dadosabertosapi.database.database2.daoimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.ufpi.dadosabertosapi.database.database2.dao.Database2DAO;

@Repository
public class Database2DAOImpl implements Database2DAO{

	@PersistenceContext(unitName="database2Context")
	private EntityManager entityManager;
	
	@Override
	public EntityManager getEntity() {
		// TODO Auto-generated method stub
		return entityManager;
	}
}
