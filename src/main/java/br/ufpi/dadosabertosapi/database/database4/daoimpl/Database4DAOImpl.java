package br.ufpi.dadosabertosapi.database.database4.daoimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.ufpi.dadosabertosapi.database.database4.dao.Database4DAO;

@Repository
public class Database4DAOImpl implements Database4DAO {

	@PersistenceContext(unitName="database4Context")
	private EntityManager entityManager;
	
	
	@Override
	public EntityManager getEntity() {
		// TODO Auto-generated method stub
		return entityManager;
	}
	
}
