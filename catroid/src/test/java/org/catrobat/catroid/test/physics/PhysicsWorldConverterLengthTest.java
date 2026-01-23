/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2025 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.myradev.lunarcode.test.physics;

import junit.framework.Assert;

import com.myradev.lunarcode.physics.PhysicsWorld;
import com.myradev.lunarcode.physics.PhysicsWorldConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class PhysicsWorldConverterLengthTest {

	@Parameterized.Parameters(name = "{0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{0.0f},
				{123.456f},
				{-654.321f}
		});
	}

	@Parameterized.Parameter
	public float length;

	@Test
	public void testLengthBox2dToNormalConversion() {
		float expectedLength = length * PhysicsWorld.RATIO;
		Assert.assertEquals(expectedLength, PhysicsWorldConverter.convertBox2dToNormalCoordinate(length));
	}

	@Test
	public void testLengthNormalToBoxConversion() {
		float expectedLength = length / PhysicsWorld.RATIO;
		Assert.assertEquals(expectedLength, PhysicsWorldConverter.convertNormalToBox2dCoordinate(length));
	}
}
