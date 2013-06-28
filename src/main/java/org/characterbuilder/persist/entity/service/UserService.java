/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.characterbuilder.persist.entity.service;

import javax.persistence.EntityManager;
import org.characterbuilder.persist.entity.RollspelUser;

/**
 *
 * @author jens
 */
public class UserService extends BaseService<RollspelUser, Integer>{

	public UserService(EntityManager entityManager) {
		super(RollspelUser.class, entityManager);
	}
}