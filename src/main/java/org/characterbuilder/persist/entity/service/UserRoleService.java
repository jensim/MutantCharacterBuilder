/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.characterbuilder.persist.entity.service;

import javax.persistence.EntityManager;
import org.characterbuilder.persist.entity.RollspelUserRole;

/**
 *
 * @author jens
 */
public class UserRoleService extends BaseService<RollspelUserRole, Integer>{

	public UserRoleService(EntityManager entityManager) {
		super(RollspelUserRole.class, entityManager);
	}
}
