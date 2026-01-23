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

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.content.Scope;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.bricks.Brick;
import com.myradev.lunarcode.content.bricks.ChangeBrightnessByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeColorByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeSizeByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeTransparencyByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeVariableBrick;
import com.myradev.lunarcode.content.bricks.ChangeVolumeByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeXByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeYByNBrick;
import com.myradev.lunarcode.content.bricks.FormulaBrick;
import com.myradev.lunarcode.content.bricks.GlideToBrick;
import com.myradev.lunarcode.content.bricks.GoNStepsBackBrick;
import com.myradev.lunarcode.content.bricks.IfLogicBeginBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorMoveBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorTurnAngleBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtPlayToneBrick;
import com.myradev.lunarcode.content.bricks.MoveNStepsBrick;
import com.myradev.lunarcode.content.bricks.NoteBrick;
import com.myradev.lunarcode.content.bricks.PlaceAtBrick;
import com.myradev.lunarcode.content.bricks.RepeatBrick;
import com.myradev.lunarcode.content.bricks.SetBounceBrick;
import com.myradev.lunarcode.content.bricks.SetBrightnessBrick;
import com.myradev.lunarcode.content.bricks.SetColorBrick;
import com.myradev.lunarcode.content.bricks.SetFrictionBrick;
import com.myradev.lunarcode.content.bricks.SetGravityBrick;
import com.myradev.lunarcode.content.bricks.SetMassBrick;
import com.myradev.lunarcode.content.bricks.SetSizeToBrick;
import com.myradev.lunarcode.content.bricks.SetTransparencyBrick;
import com.myradev.lunarcode.content.bricks.SetVariableBrick;
import com.myradev.lunarcode.content.bricks.SetVelocityBrick;
import com.myradev.lunarcode.content.bricks.SetVolumeToBrick;
import com.myradev.lunarcode.content.bricks.SetXBrick;
import com.myradev.lunarcode.content.bricks.SetYBrick;
import com.myradev.lunarcode.content.bricks.SpeakBrick;
import com.myradev.lunarcode.content.bricks.TurnLeftBrick;
import com.myradev.lunarcode.content.bricks.TurnLeftSpeedBrick;
import com.myradev.lunarcode.content.bricks.TurnRightBrick;
import com.myradev.lunarcode.content.bricks.TurnRightSpeedBrick;
import com.myradev.lunarcode.content.bricks.VibrationBrick;
import com.myradev.lunarcode.content.bricks.WaitBrick;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.formulaeditor.FormulaElement;
import com.myradev.lunarcode.formulaeditor.InterpretationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static com.myradev.lunarcode.test.StaticSingletonInitializer.initializeStaticSingletonMethods;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class CloneBrickWithFormulaTest {

	private static final Integer BRICK_FORMULA_VALUE = 0;
	private static final String BRICK_INVALID_FORMULA_VALUE = "1";
	private static final String CLONE_BRICK_FORMULA_VALUE = "2";

	@Parameterized.Parameters(name = "{0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{"SetBounceBrick", new SetBounceBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.PHYSICS_BOUNCE_FACTOR},
				{"SetFrictionBrick", new SetFrictionBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.PHYSICS_FRICTION},
				{"SetGravityBrickX", new SetGravityBrick(new Formula(BRICK_FORMULA_VALUE), new Formula(BRICK_INVALID_FORMULA_VALUE)), Brick.BrickField.PHYSICS_GRAVITY_X},
				{"SetGravityBrickY", new SetGravityBrick(new Formula(BRICK_INVALID_FORMULA_VALUE), new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.PHYSICS_GRAVITY_Y},
				{"SetMassBrick", new SetMassBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.PHYSICS_MASS},
				{"SetVelocityBrickX", new SetVelocityBrick(new Formula(BRICK_FORMULA_VALUE), new Formula(BRICK_INVALID_FORMULA_VALUE)), Brick.BrickField.PHYSICS_VELOCITY_X},
				{"SetVelocityBrickY", new SetVelocityBrick(new Formula(BRICK_INVALID_FORMULA_VALUE), new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.PHYSICS_VELOCITY_Y},
				{"TurnRightSpeedBrick", new TurnRightSpeedBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.PHYSICS_TURN_RIGHT_SPEED},
				{"TurnLeftSpeedBrick", new TurnLeftSpeedBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.PHYSICS_TURN_LEFT_SPEED},
				{"ChangeBrightnessByNBrick", new ChangeBrightnessByNBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.BRIGHTNESS_CHANGE},
				{"ChangeTransparencyByNBrick", new ChangeTransparencyByNBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.TRANSPARENCY_CHANGE},
				{"ChangeSizeByNBrick", new ChangeSizeByNBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.SIZE_CHANGE},
				{"ChangeVariableBrick", new ChangeVariableBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.VARIABLE_CHANGE},
				{"ChangeVolumeByNBrick", new ChangeVolumeByNBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.VOLUME_CHANGE},
				{"ChangeXByNBrick", new ChangeXByNBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.X_POSITION_CHANGE},
				{"ChangeYByNBrick", new ChangeYByNBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.Y_POSITION_CHANGE},
				{"GoNStepsBackBrick", new GoNStepsBackBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.STEPS},
				{"IfLogicBeginBrick", new IfLogicBeginBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.IF_CONDITION},
				{"LegoNxtMotorMoveBrick", new LegoNxtMotorMoveBrick(LegoNxtMotorMoveBrick.Motor.MOTOR_A, new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.LEGO_NXT_SPEED},
				{"LegoNxtMotorTurnAngleBrick", new LegoNxtMotorTurnAngleBrick(LegoNxtMotorTurnAngleBrick.Motor.MOTOR_A, new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.LEGO_NXT_DEGREES},
				{"LegoNxtPlayToneBrick Frequency", new LegoNxtPlayToneBrick(BRICK_FORMULA_VALUE, BRICK_FORMULA_VALUE), Brick.BrickField.LEGO_NXT_FREQUENCY},
				{"LegoNxtPlayToneBrick Duration", new LegoNxtPlayToneBrick(BRICK_FORMULA_VALUE, BRICK_FORMULA_VALUE), Brick.BrickField.LEGO_NXT_DURATION_IN_SECONDS},
				{"MoveNStepsBrick", new MoveNStepsBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.STEPS},
				{"RepeatBrick", new RepeatBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.TIMES_TO_REPEAT},
				{"SetBrightnessBrick", new SetBrightnessBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.BRIGHTNESS},
				{"SetTransparencyBrick", new SetTransparencyBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.TRANSPARENCY},
				{"SetColorBrick", new SetColorBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.COLOR},
				{"ChangeColorByNBrick", new ChangeColorByNBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.COLOR_CHANGE},
				{"SetSizeToBrick", new SetSizeToBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.SIZE},
				{"SetVariableBrick", new SetVariableBrick(BRICK_FORMULA_VALUE), Brick.BrickField.VARIABLE},
				{"SetVolumeToBrick", new SetVolumeToBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.VOLUME},
				{"SetXBrick", new SetXBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.X_POSITION},
				{"SetYBrick", new SetYBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.Y_POSITION},
				{"TurnLeftBrick", new TurnLeftBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.TURN_LEFT_DEGREES},
				{"TurnRightBrick", new TurnRightBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.TURN_RIGHT_DEGREES},
				{"VibrationBrick", new VibrationBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.VIBRATE_DURATION_IN_SECONDS},
				{"WaitBrick", new WaitBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.TIME_TO_WAIT_IN_SECONDS},
				{"PlaceAtBrick X", new PlaceAtBrick(BRICK_FORMULA_VALUE, BRICK_FORMULA_VALUE), Brick.BrickField.X_POSITION},
				{"PlaceAtBrick Y", new PlaceAtBrick(BRICK_FORMULA_VALUE, BRICK_FORMULA_VALUE), Brick.BrickField.Y_POSITION},
				{"GlideToBrick X", new GlideToBrick(BRICK_FORMULA_VALUE, BRICK_FORMULA_VALUE, BRICK_FORMULA_VALUE), Brick.BrickField.X_DESTINATION},
				{"GlideToBrick Y", new GlideToBrick(BRICK_FORMULA_VALUE, BRICK_FORMULA_VALUE, BRICK_FORMULA_VALUE), Brick.BrickField.Y_DESTINATION},
				{"GlideToBrick Duration", new GlideToBrick(BRICK_FORMULA_VALUE, BRICK_FORMULA_VALUE, BRICK_FORMULA_VALUE), Brick.BrickField.DURATION_IN_SECONDS},
				{"NoteBrick", new NoteBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.NOTE},
				{"SpeakBrick", new SpeakBrick(new Formula(BRICK_FORMULA_VALUE)), Brick.BrickField.SPEAK},
		});
	}

	@Parameterized.Parameter
	public String name;

	@Parameterized.Parameter(1)
	public FormulaBrick brick;

	@Parameterized.Parameter(2)
	public Brick.BrickField brickField;

	private Sprite sprite = new Sprite("testSprite");
	private Formula brickFormula;
	private Formula cloneBrickFormula;
	private Scope scope;

	@Before
	public void setUp() throws CloneNotSupportedException {
		initializeStaticSingletonMethods();
		FormulaBrick cloneBrick = (FormulaBrick) brick.clone();
		brickFormula = brick.getFormulaWithBrickField(brickField);
		cloneBrickFormula = cloneBrick.getFormulaWithBrickField(brickField);
		scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, new SequenceAction());
	}

	@Test
	public void testChangeBrickField() throws InterpretationException {
		cloneBrickFormula.setRoot(new FormulaElement(FormulaElement.ElementType.NUMBER, CLONE_BRICK_FORMULA_VALUE, null));
		assertNotEquals(brickFormula.interpretInteger(scope),
				cloneBrickFormula.interpretInteger(scope));
	}

	@Test
	public void testBrickFieldValidValue() throws InterpretationException {
		assertEquals(BRICK_FORMULA_VALUE, brickFormula.interpretInteger(scope));
	}

	@Test
	public void testBrickFieldEquals() throws InterpretationException {
		assertEquals(brickFormula.interpretInteger(scope), cloneBrickFormula.interpretInteger(scope));
	}
}
