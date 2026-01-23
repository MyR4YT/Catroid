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

package com.myradev.lunarcode.uiespresso.content.brick.stage;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Scene;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.WhenTouchDownScript;
import com.myradev.lunarcode.content.bricks.ChangeVariableBrick;
import com.myradev.lunarcode.content.bricks.SceneTransitionBrick;
import com.myradev.lunarcode.content.bricks.WaitBrick;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.formulaeditor.UserVariable;
import com.myradev.lunarcode.stage.StageActivity;
import com.myradev.lunarcode.uiespresso.stage.utils.ScriptEvaluationGateBrick;
import com.myradev.lunarcode.uiespresso.util.UserVariableAssertions;
import com.myradev.lunarcode.uiespresso.util.rules.BaseActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.myradev.lunarcode.io.asynctask.ProjectSaverKt.saveProjectSerial;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isFocusable;

@RunWith(AndroidJUnit4.class)
public class SceneTransitionTouchEventRegressionTest {

	private UserVariable testResultVariable = new UserVariable("firstVar");

	private ScriptEvaluationGateBrick firstBrickInScript;

	@Rule
	public BaseActivityTestRule<StageActivity> baseActivityTestRule = new
			BaseActivityTestRule<>(StageActivity.class, true, false);

	@Before
	public void setUp() throws Exception {
		createProject();
		baseActivityTestRule.launchActivity(null);
	}

	@Test
	public void testContinueScene() {
		firstBrickInScript.waitUntilEvaluated(3000);
		onView(isFocusable())
				.perform(click())
				.perform(click())
				.perform(click());
		UserVariableAssertions.assertUserVariableEqualsWithTimeout(testResultVariable, 3, 3000);
	}

	private void createProject() {
		Project project = new Project(ApplicationProvider.getApplicationContext(), getClass().getSimpleName());
		ProjectManager.getInstance().setCurrentProject(project);

		Scene firstScene = project.getDefaultScene();
		Scene secondScene = new Scene("Scene 2", project);
		project.addUserVariable(testResultVariable);

		Sprite firstBackground = firstScene.getBackgroundSprite();
		Script firstStartScript = new StartScript();
		firstStartScript.addBrick(new SceneTransitionBrick(secondScene.getName()));
		firstStartScript.addBrick(new WaitBrick(500));
		firstBrickInScript = ScriptEvaluationGateBrick.appendToScript(firstStartScript);

		WhenTouchDownScript whenTouchDownScript = new WhenTouchDownScript();
		whenTouchDownScript.addBrick(new ChangeVariableBrick(new Formula(1), testResultVariable));
		firstBackground.addScript(firstStartScript);
		firstBackground.addScript(whenTouchDownScript);

		Sprite secondBackground = new Sprite("Background");
		Script secondStartScript = new StartScript();
		secondStartScript.addBrick(new SceneTransitionBrick(firstScene.getName()));
		secondBackground.addScript(secondStartScript);
		secondScene.addSprite(secondBackground);

		project.addScene(secondScene);
		saveProjectSerial(project, ApplicationProvider.getApplicationContext());
	}
}
