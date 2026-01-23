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

package com.myradev.lunarcode.uiespresso.stage;

import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.WhenClonedScript;
import com.myradev.lunarcode.content.bricks.Brick;
import com.myradev.lunarcode.content.bricks.CloneBrick;
import com.myradev.lunarcode.content.bricks.SetVariableBrick;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.formulaeditor.UserVariable;
import com.myradev.lunarcode.stage.StageActivity;
import com.myradev.lunarcode.testsuites.annotations.Cat;
import com.myradev.lunarcode.testsuites.annotations.Level;
import com.myradev.lunarcode.uiespresso.stage.utils.ScriptEvaluationGateBrick;
import com.myradev.lunarcode.uiespresso.util.UiTestUtils;
import com.myradev.lunarcode.uiespresso.util.rules.BaseActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.myradev.lunarcode.uiespresso.util.UserVariableAssertions.assertUserVariableEqualsWithTimeout;

@RunWith(AndroidJUnit4.class)
public class DisabledBrickInClonesRegressionTest {

	private static final String VARIABLE_NAME = "var1";

	private UserVariable userVariable;
	private ScriptEvaluationGateBrick lastBrickInCloneScript;

	@Rule
	public BaseActivityTestRule<StageActivity> baseActivityTestRule = new
			BaseActivityTestRule<>(StageActivity.class, false, false);

	@Before
	public void setUp() throws Exception {
		createProject();
	}

	private void createProject() {
		Project project = UiTestUtils.createDefaultTestProject("DisabledBrickInClonesRegressionTest");
		Sprite sprite = UiTestUtils.getDefaultTestSprite(project);

		userVariable = new UserVariable(VARIABLE_NAME);
		project.addUserVariable(userVariable);

		Script startScript = UiTestUtils.getDefaultTestScript(project);
		Brick setVariableInitial = new SetVariableBrick(new Formula(1D), userVariable);
		startScript.addBrick(setVariableInitial);
		startScript.addBrick(new CloneBrick());

		Script whenClonedScript = new WhenClonedScript();
		sprite.addScript(whenClonedScript);
		Brick shouldntBeExecuted = new SetVariableBrick(new Formula(9000.1D), userVariable);
		shouldntBeExecuted.setCommentedOut(true);
		whenClonedScript.addBrick(shouldntBeExecuted);

		lastBrickInCloneScript = ScriptEvaluationGateBrick.appendToScript(whenClonedScript);
	}

	@Category({Level.Functional.class, Cat.CatrobatLanguage.class})
	@Test
	public void setVariableInCloneShouldNotBeExecutedTest() {
		baseActivityTestRule.launchActivity(null);
		lastBrickInCloneScript.waitUntilEvaluated(3000);
		assertUserVariableEqualsWithTimeout(userVariable, 1D, 3000);
	}
}
