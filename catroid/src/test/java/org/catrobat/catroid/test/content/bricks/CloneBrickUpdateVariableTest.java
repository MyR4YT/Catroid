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
package com.myradev.lunarcode.test.content.bricks;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.bricks.AskBrick;
import com.myradev.lunarcode.content.bricks.AskSpeechBrick;
import com.myradev.lunarcode.content.bricks.ChangeVariableBrick;
import com.myradev.lunarcode.content.bricks.HideTextBrick;
import com.myradev.lunarcode.content.bricks.ReadVariableFromDeviceBrick;
import com.myradev.lunarcode.content.bricks.SetVariableBrick;
import com.myradev.lunarcode.content.bricks.ShowTextBrick;
import com.myradev.lunarcode.content.bricks.ShowTextColorSizeAlignmentBrick;
import com.myradev.lunarcode.content.bricks.UserVariableBrickInterface;
import com.myradev.lunarcode.content.bricks.WebRequestBrick;
import com.myradev.lunarcode.content.bricks.WriteVariableOnDeviceBrick;
import com.myradev.lunarcode.formulaeditor.UserVariable;
import com.myradev.lunarcode.test.MockUtil;
import com.myradev.lunarcode.ui.recyclerview.controller.SpriteController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

import static org.junit.Assert.assertSame;

@RunWith(Parameterized.class)
public class CloneBrickUpdateVariableTest {
	private static final String VARIABLE_NAME = "test_variable";
	private static final UserVariable USER_VARIABLE = new UserVariable(VARIABLE_NAME);

	@Parameterized.Parameters(name = "{0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{"SetVariableBrick", new SetVariableBrick()},
				{"ChangeVariableBrick", new ChangeVariableBrick()},
				{"AskBrick", new AskBrick()},
				{"AskSpeechBrick", new AskSpeechBrick()},
				{"HideTextBrick", new HideTextBrick()},
				{"ShowTextBrick", new ShowTextBrick()},
				{"ShowTextColorSizeAlignmentBrick", new ShowTextColorSizeAlignmentBrick()},
				{"WebRequestBrick", new WebRequestBrick()},
				{"ReadVariableFromDeviceBrick", new ReadVariableFromDeviceBrick()},
				{"WriteVariableOnDeviceBrick", new WriteVariableOnDeviceBrick()},
		});
	}

	@Parameterized.Parameter
	public String name;

	@Parameterized.Parameter(1)
	public UserVariableBrickInterface brick;

	private Sprite sprite;
	private Sprite clonedSprite;

	@Before
	public void setUp() throws Exception {
		Project project = new Project(MockUtil.mockContextForProject(), "testProject");
		ProjectManager.getInstance().setCurrentProject(project);
		sprite = new Sprite("testSprite");
		project.getDefaultScene().addSprite(sprite);

		StartScript script = new StartScript();
		sprite.addScript(script);
		sprite.addUserVariable(new UserVariable(VARIABLE_NAME));
		script.addBrick(brick);
		brick.setUserVariable(USER_VARIABLE);
		clonedSprite = new SpriteController().copy(sprite, project, project.getDefaultScene());
	}

	@Test
	public void testClonedSpriteAndBrickVariableSame() {
		UserVariableBrickInterface clonedBrick = (UserVariableBrickInterface) clonedSprite.getScript(0).getBrick(0);

		UserVariable clonedVariable = clonedSprite.getUserVariable(VARIABLE_NAME);
		UserVariable clonedVariableFromBrick = clonedBrick.getUserVariable();

		assertNotNull(clonedVariable);
		assertSame(clonedVariable, clonedVariableFromBrick);
	}

	@Test
	public void testOriginalAndClonedVariableEquals() {
		UserVariable spriteVariable = sprite.getUserVariable(VARIABLE_NAME);

		UserVariableBrickInterface clonedBrick = (UserVariableBrickInterface) clonedSprite.getScript(0).getBrick(0);
		UserVariable clonedVariableFromBrick = clonedBrick.getUserVariable();

		assertEquals(spriteVariable, clonedVariableFromBrick);
	}

	@Test
	public void testOriginalAndClonedVariableNotSame() {
		UserVariable spriteVariable = sprite.getUserVariable(VARIABLE_NAME);

		UserVariable clonedVariable = clonedSprite.getUserVariable(VARIABLE_NAME);

		UserVariableBrickInterface clonedBrick = (UserVariableBrickInterface) clonedSprite.getScript(0).getBrick(0);
		UserVariable clonedVariableFromBrick = clonedBrick.getUserVariable();

		assertNotSame(spriteVariable, clonedVariable);
		assertNotSame(spriteVariable, clonedVariableFromBrick);
	}
}

