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

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Scope;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.bricks.GoNStepsBackBrick;
import com.myradev.lunarcode.content.bricks.PointInDirectionBrick;
import com.myradev.lunarcode.content.bricks.SetBrightnessBrick;
import com.myradev.lunarcode.content.bricks.SetSizeToBrick;
import com.myradev.lunarcode.content.bricks.SetTransparencyBrick;
import com.myradev.lunarcode.content.bricks.SetXBrick;
import com.myradev.lunarcode.content.bricks.SetYBrick;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.formulaeditor.FormulaElement;
import com.myradev.lunarcode.formulaeditor.InternFormulaParser;
import com.myradev.lunarcode.formulaeditor.InternToken;
import com.myradev.lunarcode.formulaeditor.InternTokenType;
import com.myradev.lunarcode.formulaeditor.InterpretationException;
import com.myradev.lunarcode.formulaeditor.Sensors;
import com.myradev.lunarcode.stage.StageActivity;
import com.myradev.lunarcode.testsuites.annotations.Cat;
import com.myradev.lunarcode.testsuites.annotations.Level;
import com.myradev.lunarcode.uiespresso.stage.utils.ScriptEvaluationGateBrick;
import com.myradev.lunarcode.uiespresso.util.rules.BaseActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ObjectVariableTest {
	private Scope scope;
	private ScriptEvaluationGateBrick lastBrickInScript;

	private static final double DELTA = 0.01d;

	private static final double SPRITE_X_POSITION = 30.0d;
	private static final double SPRITE_Y_POSITION = 50.0d;
	private static final double SPRITE_TRANSPARENCY = 0.8d;
	private static final double SPRITE_BRIGHTNESS = 0.7d;
	private static final double SPRITE_SIZE = 90.0d;
	private static final double SPRITE_DIRECTION = 42.0d;
	private static final int SPRITE_LAYER_CHANGE = 2;
	private static final int NUMBER_OF_SPRITES = 5;

	@Rule
	public BaseActivityTestRule<StageActivity> baseActivityTestRule = new
			BaseActivityTestRule<>(StageActivity.class, true, false);

	@Before
	public void setUp() throws Exception {
		createProject("LookSensorValuesTest");
	}

	@Category({Level.Functional.class, Cat.CatrobatLanguage.class})
	@Test
	public void testLookSensorValueInStage() throws InterpretationException {
		baseActivityTestRule.launchActivity(null);
		lastBrickInScript.waitUntilEvaluated(5000);

		assertEquals(SPRITE_X_POSITION, getSensorValue(Sensors.OBJECT_X), DELTA);
		assertEquals(SPRITE_Y_POSITION, getSensorValue(Sensors.OBJECT_Y), DELTA);
		assertEquals(SPRITE_TRANSPARENCY, getSensorValue(Sensors.OBJECT_TRANSPARENCY), DELTA);
		assertEquals(SPRITE_BRIGHTNESS, getSensorValue(Sensors.OBJECT_BRIGHTNESS), DELTA);
		assertEquals(SPRITE_SIZE, getSensorValue(Sensors.OBJECT_SIZE), DELTA);
		assertEquals(SPRITE_DIRECTION, getSensorValue(Sensors.MOTION_DIRECTION), DELTA);
		assertEquals(NUMBER_OF_SPRITES - SPRITE_LAYER_CHANGE, getSensorValue(Sensors.OBJECT_LAYER), DELTA);
	}

	public Double getSensorValue(Sensors sensor) throws InterpretationException {
		List<InternToken> internTokenList = new LinkedList<InternToken>();
		internTokenList.add(new InternToken(InternTokenType.SENSOR, sensor.name()));
		InternFormulaParser internParser = new InternFormulaParser(internTokenList);
		FormulaElement parseTree = internParser.parseFormula(scope);
		Formula sensorFormula = new Formula(parseTree);
		return sensorFormula.interpretDouble(scope);
	}

	private void createProject(String projectName) {
		Project project = new Project(ApplicationProvider.getApplicationContext(), projectName);
		ProjectManager.getInstance().setCurrentProject(project);
		ProjectManager.getInstance().setCurrentlyEditedScene(project.getDefaultScene());
		ProjectManager.getInstance().getCurrentlyEditedScene().addSprite(new Sprite("sprite1"));
		ProjectManager.getInstance().getCurrentlyEditedScene().addSprite(new Sprite("sprite2"));
		ProjectManager.getInstance().getCurrentlyEditedScene().addSprite(new Sprite("sprite3"));
		ProjectManager.getInstance().getCurrentlyEditedScene().addSprite(new Sprite("sprite4"));

		Sprite sprite = new Sprite("sprite5");
		StartScript startScript = new StartScript();
		scope = new Scope(project, sprite, new SequenceAction());

		SetXBrick setXBrick = new SetXBrick((int) SPRITE_X_POSITION);
		startScript.addBrick(setXBrick);

		SetYBrick setYBrick = new SetYBrick((int) SPRITE_Y_POSITION);
		startScript.addBrick(setYBrick);

		SetTransparencyBrick setTransparencyBrick = new SetTransparencyBrick(SPRITE_TRANSPARENCY);
		startScript.addBrick(setTransparencyBrick);

		SetBrightnessBrick setBrightnessBrick = new SetBrightnessBrick(SPRITE_BRIGHTNESS);
		startScript.addBrick(setBrightnessBrick);

		SetSizeToBrick setSizeToBrick = new SetSizeToBrick(SPRITE_SIZE);
		startScript.addBrick(setSizeToBrick);

		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(SPRITE_DIRECTION);
		startScript.addBrick(pointInDirectionBrick);

		GoNStepsBackBrick goNStepsBackBrick = new GoNStepsBackBrick(SPRITE_LAYER_CHANGE);
		startScript.addBrick(goNStepsBackBrick);

		sprite.addScript(startScript);

		ProjectManager.getInstance().getCurrentlyEditedScene().addSprite(sprite);
		ProjectManager.getInstance().setCurrentSprite(sprite);

		lastBrickInScript = ScriptEvaluationGateBrick.appendToScript(startScript);
	}
}
