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

package com.myradev.lunarcode.test.robolectric.bricks;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.Spinner;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.R;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.bricks.Brick;
import com.myradev.lunarcode.content.bricks.CameraBrick;
import com.myradev.lunarcode.content.bricks.ChooseCameraBrick;
import com.myradev.lunarcode.content.bricks.DronePlayLedAnimationBrick;
import com.myradev.lunarcode.content.bricks.FlashBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoAnimationsBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoSoundBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3MotorMoveBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3MotorStopBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3MotorTurnAngleBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3SetLedBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorMoveBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorStopBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorTurnAngleBrick;
import com.myradev.lunarcode.content.bricks.PhiroIfLogicBeginBrick;
import com.myradev.lunarcode.content.bricks.PhiroMotorMoveBackwardBrick;
import com.myradev.lunarcode.content.bricks.PhiroMotorMoveForwardBrick;
import com.myradev.lunarcode.content.bricks.PhiroMotorStopBrick;
import com.myradev.lunarcode.content.bricks.PhiroPlayToneBrick;
import com.myradev.lunarcode.content.bricks.PhiroRGBLightBrick;
import com.myradev.lunarcode.content.bricks.SetPhysicsObjectTypeBrick;
import com.myradev.lunarcode.content.bricks.StopScriptBrick;
import com.myradev.lunarcode.ui.SpriteActivity;
import com.myradev.lunarcode.ui.recyclerview.fragment.ScriptFragment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.ParameterizedRobolectricTestRunner;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(ParameterizedRobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.P})
public class BrickStringSpinnerTest {

	private SpriteActivity activity;

	Spinner brickSpinner;

	@ParameterizedRobolectricTestRunner.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{StopScriptBrick.class.getSimpleName(), new StopScriptBrick(), R.id.brick_stop_script_spinner, "this script", Arrays.asList("this script", "all scripts", "other scripts of this actor or object")},
				{SetPhysicsObjectTypeBrick.class.getSimpleName(), new SetPhysicsObjectTypeBrick(), R.id.brick_set_physics_object_type_spinner, "not moving or bouncing under gravity (default)", Arrays.asList("moving and bouncing under gravity", "not moving under gravity, but others bounce off you under gravity", "not moving or bouncing under gravity (default)")},
				{CameraBrick.class.getSimpleName(), new CameraBrick(), R.id.brick_video_spinner, "on", Arrays.asList("off", "on")},
				{ChooseCameraBrick.class.getSimpleName(), new ChooseCameraBrick(), R.id.brick_choose_camera_spinner, "front", Arrays.asList("rear", "front")},
				{FlashBrick.class.getSimpleName(), new FlashBrick(), R.id.brick_flash_spinner, "on", Arrays.asList("off", "on")},

				//Lego bricks
				{LegoNxtMotorTurnAngleBrick.class.getSimpleName(), new LegoNxtMotorTurnAngleBrick(), R.id.lego_motor_turn_angle_spinner, "A", Arrays.asList("A", "B", "C", "B+C")},
				{LegoNxtMotorStopBrick.class.getSimpleName(), new LegoNxtMotorStopBrick(), R.id.stop_motor_spinner, "A", Arrays.asList("A", "B", "C", "B+C", "All")},
				{LegoNxtMotorMoveBrick.class.getSimpleName(), new LegoNxtMotorMoveBrick(), R.id.lego_motor_action_spinner, "A", Arrays.asList("A", "B", "C", "B+C")},
				{LegoEv3MotorTurnAngleBrick.class.getSimpleName(), new LegoEv3MotorTurnAngleBrick(), R.id.lego_ev3_motor_turn_angle_spinner, "A", Arrays.asList("A", "B", "C", "D", "B+C")},
				{LegoEv3MotorMoveBrick.class.getSimpleName(), new LegoEv3MotorMoveBrick(), R.id.brick_ev3_motor_move_spinner, "A", Arrays.asList("A", "B", "C", "D", "B+C")},
				{LegoEv3MotorStopBrick.class.getSimpleName(), new LegoEv3MotorStopBrick(), R.id.ev3_stop_motor_spinner, "A", Arrays.asList("A", "B", "C", "D", "B+C", "All")},
				{LegoEv3SetLedBrick.class.getSimpleName(), new LegoEv3SetLedBrick(), R.id.brick_ev3_set_led_spinner, "Green", Arrays.asList("Off", "Green", "Red", "Orange", "Green flashing", "Red flashing", "Orange flashing", "Green pulse", "Red pulse", "Orange pulse")},

				//Drone bricks
				{DronePlayLedAnimationBrick.class.getSimpleName(), new DronePlayLedAnimationBrick(), R.id.brick_drone_play_led_animation_spinner, "Blink green", Arrays.asList("Blink green red", "Blink green", "Blink red", "Blink orange", "Snake green red", "Fire", "Standard", "Red", "Green", "Red snake", "Blank", "Right missile", "Left missile", "Double missile", "Front left green others red", "Front right green others red", "Rear right green others red", "Rear left green others red", "Left green right red", "Left red right green", "Blink standard")},
				{JumpingSumoAnimationsBrick.class.getSimpleName(), new JumpingSumoAnimationsBrick(), R.id.brick_jumping_sumo_animation_spinner, "Spin", Arrays.asList("Spin", "Tab", "Slowshake", "Metronome", "Ondulation", "Spinjump", "Spiral", "Slalom")},
				{JumpingSumoSoundBrick.class.getSimpleName(), new JumpingSumoSoundBrick(), R.id.brick_jumping_sumo_sound_spinner, "Normal", Arrays.asList("Normal", "Robot", "Insect", "Monster")},

				//Phiro bricks
				{PhiroMotorMoveForwardBrick.class.getSimpleName(), new PhiroMotorMoveForwardBrick(), R.id.brick_phiro_motor_forward_action_spinner, "Left", Arrays.asList("Left", "Right", "Both")},
				{PhiroMotorMoveBackwardBrick.class.getSimpleName(), new PhiroMotorMoveBackwardBrick(), R.id.brick_phiro_motor_backward_action_spinner, "Left", Arrays.asList("Left", "Right", "Both")},
				{PhiroMotorStopBrick.class.getSimpleName(), new PhiroMotorStopBrick(), R.id.brick_phiro_stop_motor_spinner, "Both", Arrays.asList("Left", "Right", "Both")},
				{PhiroPlayToneBrick.class.getSimpleName(), new PhiroPlayToneBrick(), R.id.brick_phiro_select_tone_spinner, "Do", Arrays.asList("Do", "Re", "Mi", "Fa", "So", "La", "Ti")},
				{PhiroRGBLightBrick.class.getSimpleName(), new PhiroRGBLightBrick(), R.id.brick_phiro_rgb_light_spinner, "Both", Arrays.asList("Left", "Right", "Both")},
				{PhiroIfLogicBeginBrick.class.getSimpleName(), new PhiroIfLogicBeginBrick(), R.id.brick_phiro_sensor_action_spinner, "Front Left Sensor", Arrays.asList("Front Left Sensor", "Front Right Sensor", "Side Left Sensor", "Side Right Sensor", "Bottom Left Sensor", "Bottom Right Sensor")}
		});
	}

	@SuppressWarnings("PMD.UnusedPrivateField")
	private String name;

	private Brick brick;

	private @IdRes int spinnerId;

	private String expectedSelection;

	private List<String> expectedContent;

	public BrickStringSpinnerTest(String name, Brick brick, @IdRes int spinnerId, String expectedSelection, List<String> expectedContent) {
		this.name = name;
		this.brick = brick;
		this.spinnerId = spinnerId;
		this.expectedSelection = expectedSelection;
		this.expectedContent = expectedContent;
	}

	@Before
	public void setUp() throws Exception {
		ActivityController<SpriteActivity> activityController = Robolectric.buildActivity(SpriteActivity.class);
		activity = activityController.get();
		createProject(activity);
		activityController.create().resume();

		Fragment scriptFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
		assertNotNull(scriptFragment);
		assertThat(scriptFragment, is(instanceOf(ScriptFragment.class)));

		View brickView = brick.getView(activity);
		assertNotNull(brickView);

		brickSpinner = (Spinner) brickView.findViewById(spinnerId);
		assertNotNull(brickSpinner);
	}

	@After
	public void tearDown() {
		ProjectManager.getInstance().resetProjectManager();
	}

	@Test
	public void spinnerDefaultSelectionTest() {
		assertEquals(expectedSelection, (String) brickSpinner.getSelectedItem());
	}

	@Test
	public void spinnerContentTest() {
		List<String> spinnerContent = new ArrayList<>();
		for (int index = 0; index < brickSpinner.getAdapter().getCount(); index++) {
			spinnerContent.add((String) brickSpinner.getAdapter().getItem(index));
		}
		assertEquals(expectedContent, spinnerContent);
	}

	public void createProject(Activity activity) {
		Project project = new Project(activity, getClass().getSimpleName());
		Sprite sprite = new Sprite("testSprite");
		Script script = new StartScript();
		script.addBrick(brick);
		sprite.addScript(script);
		project.getDefaultScene().addSprite(sprite);
		ProjectManager.getInstance().setCurrentProject(project);
		ProjectManager.getInstance().setCurrentSprite(sprite);
		ProjectManager.getInstance().setCurrentlyEditedScene(project.getDefaultScene());
	}
}
