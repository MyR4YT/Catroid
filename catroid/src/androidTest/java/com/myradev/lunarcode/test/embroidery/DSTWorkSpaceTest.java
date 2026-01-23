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

package com.myradev.lunarcode.test.embroidery;

import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.embroidery.DSTWorkSpace;
import com.myradev.lunarcode.embroidery.EmbroideryWorkSpace;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DSTWorkSpaceTest {

	@Test
	public void simpleWorkSpaceTest() {
		final Sprite sprite = Mockito.mock(Sprite.class);
		final float x = 1.5f;
		final float y = 1.5f;

		EmbroideryWorkSpace workSpace = new DSTWorkSpace();
		workSpace.set(x, y, sprite);

		assertEquals(x, workSpace.getCurrentX(), Float.MIN_VALUE);
		assertEquals(y, workSpace.getCurrentY(), Float.MIN_VALUE);
		assertEquals(sprite, workSpace.getLastSprite());
	}
}
