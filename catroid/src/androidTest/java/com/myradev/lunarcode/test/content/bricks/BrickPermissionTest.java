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
import com.myradev.lunarcode.content.Scene;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.WhenGamepadButtonScript;
import com.myradev.lunarcode.content.bricks.AskSpeechBrick;
import com.myradev.lunarcode.content.bricks.Brick;
import com.myradev.lunarcode.content.bricks.CameraBrick;
import com.myradev.lunarcode.content.bricks.ForeverBrick;
import com.myradev.lunarcode.content.bricks.IfLogicBeginBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorMoveBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorTurnAngleBrick;
import com.myradev.lunarcode.content.bricks.RepeatBrick;
import com.myradev.lunarcode.content.bricks.SetVariableBrick;
import com.myradev.lunarcode.content.bricks.WhenGamepadButtonBrick;
import com.myradev.lunarcode.content.bricks.WhenNfcBrick;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.formulaeditor.FormulaElement;
import com.myradev.lunarcode.formulaeditor.UserVariable;
import com.myradev.lunarcode.stage.StageResourceHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_ADMIN;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.CHANGE_WIFI_MULTICAST_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.NFC;
import static android.Manifest.permission.RECORD_AUDIO;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class BrickPermissionTest {

	private static Brick brickWithGPS =
			new SetVariableBrick(new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, "LONGITUDE", null)), new UserVariable("x"));

	@Parameterized.Parameters(name = "{0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{"CameraBrick", new Brick[]{new CameraBrick()}, new String[]{CAMERA}},
				{"LegoNxtMotorMoveBrick", new Brick[]{new LegoNxtMotorMoveBrick()}, new String[]{BLUETOOTH_ADMIN, BLUETOOTH}},
				{"CameraBrick + LegoNxtMotorTurnAngleBrick", new Brick[]{new CameraBrick(), new LegoNxtMotorTurnAngleBrick()}, new String[]{CAMERA, BLUETOOTH_ADMIN, BLUETOOTH}},
				{"AskSpeechBrick", new Brick[]{new AskSpeechBrick()}, new String[]{RECORD_AUDIO}},
				{"WhenGamepadButtonBrick", new Brick[]{new WhenGamepadButtonBrick(new WhenGamepadButtonScript())}, new String[]{CHANGE_WIFI_MULTICAST_STATE, CHANGE_WIFI_STATE, ACCESS_WIFI_STATE}},
				{"WhenNfcBrick", new Brick[]{new WhenNfcBrick()}, new String[]{NFC}},
				{"WhenNfcBrick + GPS", new Brick[]{new WhenNfcBrick(), brickWithGPS}, new String[]{NFC, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}},
				{"Brick With GPS Formula", new Brick[]{brickWithGPS}, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}}
		});
	}

	@Parameterized.Parameter
	public String name;

	@Parameterized.Parameter(1)
	public Brick[] bricks;

	@Parameterized.Parameter(2)
	public String[] expectedPermission;

	Script script;
	@Before
	public void setUp() {
		Project project = new Project();
		Scene scene = new Scene();
		project.addScene(scene);
		Sprite sprite = new Sprite();
		scene.addSprite(sprite);
		script = new StartScript();
		sprite.addScript(script);
		ProjectManager.getInstance().setCurrentProject(project);
	}

	@Test
	public void testGetProjectRuntimePermissionList() {
		script.getBrickList().addAll(Arrays.asList(bricks));
		checkProjectRuntimePermissions();
	}

	@Test
	public void testRuntimePermissionInsideIf() {
		IfLogicBeginBrick ifBrick = new IfLogicBeginBrick();
		for (Brick brick : bricks) {
			ifBrick.addBrickToIfBranch(brick);
		}
		script.addBrick(ifBrick);
		checkProjectRuntimePermissions();
	}

	@Test
	public void testRuntimePermissionInsideElse() {
		IfLogicBeginBrick ifBrick = new IfLogicBeginBrick();
		for (Brick brick : bricks) {
			ifBrick.addBrickToElseBranch(brick);
		}
		script.addBrick(ifBrick);
		checkProjectRuntimePermissions();
	}

	@Test
	public void testRuntimePermissionInsideForever() {
		ForeverBrick foreverBrick = new ForeverBrick();
		for (Brick brick : bricks) {
			foreverBrick.addBrick(brick);
		}
		script.addBrick(foreverBrick);
		checkProjectRuntimePermissions();
	}

	@Test
	public void testRuntimePermissionInsideRepeat() {
		RepeatBrick repeatBrick = new RepeatBrick();
		for (Brick brick : bricks) {
			repeatBrick.addBrick(brick);
		}
		script.addBrick(repeatBrick);
		checkProjectRuntimePermissions();
	}

	@Test
	public void testDoubleNestedPermission() {
		RepeatBrick repeatBrick0 = new RepeatBrick();
		RepeatBrick repeatBrick1 = new RepeatBrick();
		for (Brick brick : bricks) {
			repeatBrick1.addBrick(brick);
		}
		repeatBrick0.addBrick(repeatBrick1);

		script.addBrick(repeatBrick0);
		checkProjectRuntimePermissions();
	}

	private void checkProjectRuntimePermissions() {
		List<String> requestedString = StageResourceHolder.getProjectsRuntimePermissionList();
		assertTrue(requestedString.containsAll(Arrays.asList(expectedPermission)));
		assertTrue(Arrays.asList(expectedPermission).containsAll(requestedString));
	}
}
