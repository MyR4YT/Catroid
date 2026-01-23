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

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Scene;
import com.myradev.lunarcode.content.Scope;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.bricks.ForeverBrick;
import com.myradev.lunarcode.content.bricks.SceneStartBrick;
import com.myradev.lunarcode.content.bricks.SetVariableBrick;
import com.myradev.lunarcode.content.bricks.WaitUntilBrick;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.formulaeditor.FormulaElement;
import com.myradev.lunarcode.formulaeditor.InternFormulaParser;
import com.myradev.lunarcode.formulaeditor.InternToken;
import com.myradev.lunarcode.formulaeditor.InternTokenType;
import com.myradev.lunarcode.formulaeditor.Sensors;
import com.myradev.lunarcode.formulaeditor.UserVariable;
import com.myradev.lunarcode.stage.StageActivity;
import com.myradev.lunarcode.testsuites.annotations.Cat;
import com.myradev.lunarcode.testsuites.annotations.Level;
import com.myradev.lunarcode.uiespresso.stage.utils.StageTestTouchUtils;
import com.myradev.lunarcode.uiespresso.util.UiTestUtils;
import com.myradev.lunarcode.uiespresso.util.actions.CustomActions;
import com.myradev.lunarcode.uiespresso.util.rules.BaseActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.LinkedList;
import java.util.List;

import static com.myradev.lunarcode.uiespresso.util.UserVariableAssertions.assertUserVariableEqualsWithTimeout;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isFocusable;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

@Category({Cat.AppUi.class, Level.Smoke.class})
public class StartStageTouchTest {

	private UserVariable screenIsTouchedUserVariable = null;

	@Rule
	public BaseActivityTestRule<StageActivity> baseActivityTestRule = new
			BaseActivityTestRule<>(StageActivity.class, true, false);

	@Before
	public void setUp() throws Exception {
		createProject();
		baseActivityTestRule.launchActivity(null);
	}

	@Test
	public void switchStageTouchTest() {
		onView(isRoot()).perform(CustomActions.wait(500));
		onView(isFocusable()).perform(StageTestTouchUtils.touchDown(50, 50));
		assertUserVariableEqualsWithTimeout(screenIsTouchedUserVariable, "true", 500);
		onView(isFocusable()).perform(StageTestTouchUtils.touchUp(50, 50));
		assertUserVariableEqualsWithTimeout(screenIsTouchedUserVariable, "false", 500);
	}

	private void createProject() {
		Project project = UiTestUtils.createDefaultTestProject(getClass().getSimpleName());
		Sprite sprite = UiTestUtils.getDefaultTestSprite(project);
		Script background1StartScript = UiTestUtils.getDefaultTestScript(project);

		String scene2Name = "Scene2";
		Scene scene2 = new Scene(scene2Name, project);
		scene2.addSprite(sprite);

		screenIsTouchedUserVariable = new UserVariable("ScreenTouched");
		project.addUserVariable(screenIsTouchedUserVariable);

		Scope scope = new Scope(project, sprite, new SequenceAction());

		background1StartScript.addBrick(new WaitUntilBrick(createFormulaWithSensor(Sensors.FINGER_TOUCHED, scope)));
		background1StartScript.addBrick(new SceneStartBrick("Scene2"));

		Script background2StartScript = new StartScript();
		ForeverBrick foreverBrick = new ForeverBrick();
		foreverBrick.addBrick(
				new SetVariableBrick(createFormulaWithSensor(Sensors.FINGER_TOUCHED, scope),
						screenIsTouchedUserVariable));
		background2StartScript.addBrick(foreverBrick);

		scene2.getBackgroundSprite().addScript(background2StartScript);
	}

	private Formula createFormulaWithSensor(Sensors sensor, Scope scope) {
		List<InternToken> internTokenList = new LinkedList<InternToken>();
		internTokenList.add(new InternToken(InternTokenType.SENSOR, sensor.name()));
		InternFormulaParser internFormulaParser = new InternFormulaParser(internTokenList);
		FormulaElement root = internFormulaParser.parseFormula(scope);
		return new Formula(root);
	}
}
