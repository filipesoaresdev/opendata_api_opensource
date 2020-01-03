package br.ufpi.dadosabertosapi.database.database1.daoimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.ufpi.dadosabertosapi.database.database1.dao.Database1DAO;

@Repository
public class Database1DAOImpl implements Database1DAO {

	@PersistenceContext(unitName="database1Context")
	private EntityManager entityManager;
	
	@Override
	public EntityManager getEntity() {
		// TODO Auto-generated method stub
		return entityManager;
	}
}
