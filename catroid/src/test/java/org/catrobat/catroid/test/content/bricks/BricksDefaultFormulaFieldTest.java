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

import com.myradev.lunarcode.content.bricks.ArduinoSendDigitalValueBrick;
import com.myradev.lunarcode.content.bricks.ArduinoSendPWMValueBrick;
import com.myradev.lunarcode.content.bricks.Brick;
import com.myradev.lunarcode.content.bricks.DroneMoveBackwardBrick;
import com.myradev.lunarcode.content.bricks.DroneMoveDownBrick;
import com.myradev.lunarcode.content.bricks.DroneMoveForwardBrick;
import com.myradev.lunarcode.content.bricks.DroneMoveLeftBrick;
import com.myradev.lunarcode.content.bricks.DroneMoveRightBrick;
import com.myradev.lunarcode.content.bricks.DroneMoveUpBrick;
import com.myradev.lunarcode.content.bricks.DroneTurnLeftBrick;
import com.myradev.lunarcode.content.bricks.DroneTurnRightBrick;
import com.myradev.lunarcode.content.bricks.FormulaBrick;
import com.myradev.lunarcode.content.bricks.GlideToBrick;
import com.myradev.lunarcode.content.bricks.InsertItemIntoUserListBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoMoveBackwardBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoMoveForwardBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3PlayToneBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtPlayToneBrick;
import com.myradev.lunarcode.content.bricks.PhiroRGBLightBrick;
import com.myradev.lunarcode.content.bricks.PlaceAtBrick;
import com.myradev.lunarcode.content.bricks.RaspiPwmBrick;
import com.myradev.lunarcode.content.bricks.RaspiSendDigitalValueBrick;
import com.myradev.lunarcode.content.bricks.ReplaceItemInUserListBrick;
import com.myradev.lunarcode.content.bricks.SayForBubbleBrick;
import com.myradev.lunarcode.content.bricks.SetGravityBrick;
import com.myradev.lunarcode.content.bricks.SetPenColorBrick;
import com.myradev.lunarcode.content.bricks.SetVelocityBrick;
import com.myradev.lunarcode.content.bricks.ShowTextBrick;
import com.myradev.lunarcode.content.bricks.ShowTextColorSizeAlignmentBrick;
import com.myradev.lunarcode.content.bricks.ThinkForBubbleBrick;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

import static com.myradev.lunarcode.content.bricks.Brick.BrickField.ARDUINO_ANALOG_PIN_NUMBER;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.ARDUINO_DIGITAL_PIN_NUMBER;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.DRONE_TIME_TO_FLY_IN_SECONDS;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.DURATION_IN_SECONDS;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.JUMPING_SUMO_TIME_TO_DRIVE_IN_SECONDS;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.LEGO_EV3_DURATION_IN_SECONDS;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.LEGO_NXT_DURATION_IN_SECONDS;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.PEN_COLOR_RED;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.PHIRO_LIGHT_RED;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.PHYSICS_GRAVITY_X;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.PHYSICS_VELOCITY_X;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.RASPI_DIGITAL_PIN_NUMBER;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.REPLACE_ITEM_IN_USERLIST_INDEX;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.STRING;
import static com.myradev.lunarcode.content.bricks.Brick.BrickField.X_POSITION;

@RunWith(Parameterized.class)
public class BricksDefaultFormulaFieldTest {

	@Parameterized.Parameters(name = "{0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ArduinoSendDigitalValueBrick.class.getSimpleName(), new ArduinoSendDigitalValueBrick(), ARDUINO_DIGITAL_PIN_NUMBER},
				{ArduinoSendPWMValueBrick.class.getSimpleName(), new ArduinoSendPWMValueBrick(), ARDUINO_ANALOG_PIN_NUMBER},
				{RaspiPwmBrick.class.getSimpleName(), new RaspiPwmBrick(), RASPI_DIGITAL_PIN_NUMBER},
				{RaspiSendDigitalValueBrick.class.getSimpleName(), new RaspiSendDigitalValueBrick(), RASPI_DIGITAL_PIN_NUMBER},
				{LegoEv3PlayToneBrick.class.getSimpleName(), new LegoEv3PlayToneBrick(), LEGO_EV3_DURATION_IN_SECONDS},
				{LegoNxtPlayToneBrick.class.getSimpleName(), new LegoNxtPlayToneBrick(), LEGO_NXT_DURATION_IN_SECONDS},
				{DroneMoveUpBrick.class.getSimpleName(), new DroneMoveUpBrick(), DRONE_TIME_TO_FLY_IN_SECONDS},
				{DroneMoveDownBrick.class.getSimpleName(), new DroneMoveDownBrick(), DRONE_TIME_TO_FLY_IN_SECONDS},
				{DroneMoveLeftBrick.class.getSimpleName(), new DroneMoveLeftBrick(), DRONE_TIME_TO_FLY_IN_SECONDS},
				{DroneMoveRightBrick.class.getSimpleName(), new DroneMoveRightBrick(), DRONE_TIME_TO_FLY_IN_SECONDS},
				{DroneMoveForwardBrick.class.getSimpleName(), new DroneMoveForwardBrick(), DRONE_TIME_TO_FLY_IN_SECONDS},
				{DroneMoveBackwardBrick.class.getSimpleName(), new DroneMoveBackwardBrick(), DRONE_TIME_TO_FLY_IN_SECONDS},
				{DroneTurnLeftBrick.class.getSimpleName(), new DroneTurnLeftBrick(), DRONE_TIME_TO_FLY_IN_SECONDS},
				{DroneTurnRightBrick.class.getSimpleName(), new DroneTurnRightBrick(), DRONE_TIME_TO_FLY_IN_SECONDS},
				{JumpingSumoMoveBackwardBrick.class.getSimpleName(), new JumpingSumoMoveBackwardBrick(), JUMPING_SUMO_TIME_TO_DRIVE_IN_SECONDS},
				{JumpingSumoMoveForwardBrick.class.getSimpleName(), new JumpingSumoMoveForwardBrick(), JUMPING_SUMO_TIME_TO_DRIVE_IN_SECONDS},
				{ShowTextBrick.class.getSimpleName(), new ShowTextBrick(), X_POSITION},
				{ShowTextColorSizeAlignmentBrick.class.getSimpleName(), new ShowTextColorSizeAlignmentBrick(), X_POSITION},
				{InsertItemIntoUserListBrick.class.getSimpleName(), new InsertItemIntoUserListBrick(), INSERT_ITEM_INTO_USERLIST_VALUE},
				{ReplaceItemInUserListBrick.class.getSimpleName(), new ReplaceItemInUserListBrick(), REPLACE_ITEM_IN_USERLIST_INDEX},
				{SayForBubbleBrick.class.getSimpleName(), new SayForBubbleBrick(), STRING},
				{ThinkForBubbleBrick.class.getSimpleName(), new ThinkForBubbleBrick(), STRING},
				{PhiroRGBLightBrick.class.getSimpleName(), new PhiroRGBLightBrick(), PHIRO_LIGHT_RED},
				{SetPenColorBrick.class.getSimpleName(), new SetPenColorBrick(), PEN_COLOR_RED},
				{PlaceAtBrick.class.getSimpleName(), new PlaceAtBrick(), X_POSITION},
				{GlideToBrick.class.getSimpleName(), new GlideToBrick(), DURATION_IN_SECONDS},
				{SetVelocityBrick.class.getSimpleName(), new SetVelocityBrick(), PHYSICS_VELOCITY_X},
				{SetGravityBrick.class.getSimpleName(), new SetGravityBrick(), PHYSICS_GRAVITY_X},
		});
	}

	@Parameterized.Parameter
	public String name;

	@Parameterized.Parameter(1)
	public FormulaBrick brick;

	@Parameterized.Parameter(2)
	public Brick.BrickField expectedDefaultBrickField;

	@Test
	public void testEditFormula() {
		assertEquals(expectedDefaultBrickField, brick.getDefaultBrickField());
	}
}
