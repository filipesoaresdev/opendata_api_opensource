package br.ufpi.dadosabertosapi.database.database3.daoimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.ufpi.dadosabertosapi.database.database3.dao.Database3DAO;

@Repository
public class Database3DAOImpl implements Database3DAO{

	@PersistenceContext(unitName="database3Context")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntity() {
		// TODO Auto-generated method stub
		return entityManager;
	}
	
}
