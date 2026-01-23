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

package com.myradev.lunarcode.test.content.script;

import com.myradev.lunarcode.content.BroadcastScript;
import com.myradev.lunarcode.content.RaspiInterruptScript;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.UserDefinedScript;
import com.myradev.lunarcode.content.WhenBackgroundChangesScript;
import com.myradev.lunarcode.content.WhenBounceOffScript;
import com.myradev.lunarcode.content.WhenClonedScript;
import com.myradev.lunarcode.content.WhenConditionScript;
import com.myradev.lunarcode.content.WhenGamepadButtonScript;
import com.myradev.lunarcode.content.WhenNfcScript;
import com.myradev.lunarcode.content.WhenScript;
import com.myradev.lunarcode.content.WhenTouchDownScript;
import com.myradev.lunarcode.content.bricks.Brick;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertSame;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class CloneScriptTest {

	@Parameterized.Parameters(name = "{0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{BroadcastScript.class.getSimpleName(), BroadcastScript.class},
				{WhenBounceOffScript.class.getSimpleName(), WhenBounceOffScript.class},
				{RaspiInterruptScript.class.getSimpleName(), RaspiInterruptScript.class},
				{StartScript.class.getSimpleName(), StartScript.class},
				{WhenBackgroundChangesScript.class.getSimpleName(), WhenBackgroundChangesScript.class},
				{WhenClonedScript.class.getSimpleName(), WhenClonedScript.class},
				{WhenConditionScript.class.getSimpleName(), WhenConditionScript.class},
				{WhenGamepadButtonScript.class.getSimpleName(), WhenGamepadButtonScript.class},
				{WhenNfcScript.class.getSimpleName(), WhenNfcScript.class},
				{WhenScript.class.getSimpleName(), WhenScript.class},
				{WhenTouchDownScript.class.getSimpleName(), WhenTouchDownScript.class},
				{UserDefinedScript.class.getSimpleName(), UserDefinedScript.class},
		});
	}

	@Parameterized.Parameter
	public String name;

	@Parameterized.Parameter(1)
	public Class<Script> scriptClass;

	private Script script;

	@Before
	public void setUp() throws IllegalAccessException, InstantiationException {
		script = scriptClass.newInstance();
	}

	@Test
	public void testCloneEmptyScript() throws CloneNotSupportedException {
		Script clone = script.clone();

		assertNotNull(clone);

		assertNotSame(clone, script);
		assertNotSame(clone.getScriptBrick(), script.getScriptBrick());

		assertNotNull(clone.getScriptBrick());
		assertNotNull(clone.getScriptBrick().getScript());

		assertSame(clone.getScriptBrick().getScript(), clone);
		assertSame(script.getScriptBrick().getScript(), script);

		assertNotNull(clone.getBrickList());
		assertNotSame(clone.getBrickList(), script.getBrickList());
	}

	@Test
	public void testCloneWithBrick() throws CloneNotSupportedException {
		Brick brick = mock(Brick.class);
		Brick clonedBrick = mock(Brick.class);

		when(brick.clone()).thenReturn(clonedBrick);

		script.addBrick(brick);

		Script clone = script.clone();

		verify(brick).clone();

		assertNotSame(clone, script);
		assertNotSame(clone.getScriptBrick(), script.getScriptBrick());

		assertNotNull(clone.getScriptBrick());
		assertNotNull(clone.getScriptBrick().getScript());

		assertSame(clone.getScriptBrick().getScript(), clone);
		assertSame(script.getScriptBrick().getScript(), script);

		assertNotNull(clone.getBrickList());
		assertNotSame(clone.getBrickList(), script.getBrickList());

		List<Brick> cloneBrickList = clone.getBrickList();
		List<Brick> scriptBrickList = script.getBrickList();

		assertEquals(cloneBrickList.size(), scriptBrickList.size());

		Brick clonedBrickInScript = cloneBrickList.get(0);

		assertSame(clonedBrick, clonedBrickInScript);

		Brick originalBrickInScript = scriptBrickList.get(0);

		assertNotSame(clonedBrickInScript, originalBrickInScript);
	}
}
