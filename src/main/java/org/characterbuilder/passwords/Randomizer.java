/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.characterbuilder.passwords;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author jens
 */
public final class Randomizer {

	private final SecureRandom random = new SecureRandom();

	public String nextSessionId() {
		return new BigInteger(130, random).toString(32);
	}
}
