/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.characterbuilder.persist.entity.service;

import javax.persistence.EntityManager;


/**
 *
 * @author jens
 */

public abstract class BaseService <T, Pk>{
	
	private final EntityManager entityManager;
	private final Class<T> clazz;

	public BaseService(Class<T> clazz, EntityManager entityManager) {
		this.entityManager = entityManager;
		this.clazz = clazz;
	}
	
	public T create(T newObject){
		entityManager.getTransaction().begin();
		entityManager.persist(newObject);
		entityManager.getTransaction().commit();
		return newObject;
	}
	
	public T read(Pk pk){
		return entityManager.find(clazz, pk);
	}
	
	public void delete(T obj){
		entityManager.getTransaction().begin();
		obj = entityManager.merge(obj);
		entityManager.remove(obj);
		entityManager.getTransaction().commit();
	}
	
	public T update(T obj){
		entityManager.getTransaction().begin();
		obj = entityManager.merge(obj);
		entityManager.getTransaction().commit();
		return obj;
	}
}
