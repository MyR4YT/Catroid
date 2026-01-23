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

package com.myradev.lunarcode.uiespresso.content.brick.rtl;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.R;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.bricks.AddItemToUserListBrick;
import com.myradev.lunarcode.content.bricks.ArduinoSendDigitalValueBrick;
import com.myradev.lunarcode.content.bricks.ArduinoSendPWMValueBrick;
import com.myradev.lunarcode.content.bricks.AskBrick;
import com.myradev.lunarcode.content.bricks.AskSpeechBrick;
import com.myradev.lunarcode.content.bricks.BroadcastBrick;
import com.myradev.lunarcode.content.bricks.BroadcastReceiverBrick;
import com.myradev.lunarcode.content.bricks.BroadcastWaitBrick;
import com.myradev.lunarcode.content.bricks.CameraBrick;
import com.myradev.lunarcode.content.bricks.ChangeBrightnessByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeColorByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeSizeByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeTempoByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeTransparencyByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeVariableBrick;
import com.myradev.lunarcode.content.bricks.ChangeVolumeByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeXByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeYByNBrick;
import com.myradev.lunarcode.content.bricks.ChooseCameraBrick;
import com.myradev.lunarcode.content.bricks.ClearBackgroundBrick;
import com.myradev.lunarcode.content.bricks.ClearGraphicEffectBrick;
import com.myradev.lunarcode.content.bricks.CloneBrick;
import com.myradev.lunarcode.content.bricks.CopyLookBrick;
import com.myradev.lunarcode.content.bricks.DeleteItemOfUserListBrick;
import com.myradev.lunarcode.content.bricks.DeleteLookBrick;
import com.myradev.lunarcode.content.bricks.DeleteThisCloneBrick;
import com.myradev.lunarcode.content.bricks.DroneEmergencyBrick;
import com.myradev.lunarcode.content.bricks.DroneFlipBrick;
import com.myradev.lunarcode.content.bricks.DroneMoveBackwardBrick;
import com.myradev.lunarcode.content.bricks.DroneMoveDownBrick;
import com.myradev.lunarcode.content.bricks.DroneMoveForwardBrick;
import com.myradev.lunarcode.content.bricks.DroneMoveLeftBrick;
import com.myradev.lunarcode.content.bricks.DroneMoveRightBrick;
import com.myradev.lunarcode.content.bricks.DroneMoveUpBrick;
import com.myradev.lunarcode.content.bricks.DronePlayLedAnimationBrick;
import com.myradev.lunarcode.content.bricks.DroneSwitchCameraBrick;
import com.myradev.lunarcode.content.bricks.DroneTakeOffLandBrick;
import com.myradev.lunarcode.content.bricks.DroneTurnLeftBrick;
import com.myradev.lunarcode.content.bricks.DroneTurnRightBrick;
import com.myradev.lunarcode.content.bricks.EditLookBrick;
import com.myradev.lunarcode.content.bricks.FadeParticleEffectBrick;
import com.myradev.lunarcode.content.bricks.FlashBrick;
import com.myradev.lunarcode.content.bricks.ForeverBrick;
import com.myradev.lunarcode.content.bricks.GlideToBrick;
import com.myradev.lunarcode.content.bricks.GoToBrick;
import com.myradev.lunarcode.content.bricks.HideBrick;
import com.myradev.lunarcode.content.bricks.HideTextBrick;
import com.myradev.lunarcode.content.bricks.IfLogicBeginBrick;
import com.myradev.lunarcode.content.bricks.IfThenLogicBeginBrick;
import com.myradev.lunarcode.content.bricks.InsertItemIntoUserListBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoAnimationsBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoJumpHighBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoJumpLongBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoMoveBackwardBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoMoveForwardBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoNoSoundBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoRotateLeftBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoRotateRightBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoSoundBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoTakingPictureBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoTurnBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3MotorMoveBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3MotorStopBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3MotorTurnAngleBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3PlayToneBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3SetLedBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorMoveBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorStopBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorTurnAngleBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtPlayToneBrick;
import com.myradev.lunarcode.content.bricks.MoveNStepsBrick;
import com.myradev.lunarcode.content.bricks.NextLookBrick;
import com.myradev.lunarcode.content.bricks.NoteBrick;
import com.myradev.lunarcode.content.bricks.OpenUrlBrick;
import com.myradev.lunarcode.content.bricks.PaintNewLookBrick;
import com.myradev.lunarcode.content.bricks.ParticleEffectAdditivityBrick;
import com.myradev.lunarcode.content.bricks.PauseForBeatsBrick;
import com.myradev.lunarcode.content.bricks.PenDownBrick;
import com.myradev.lunarcode.content.bricks.PenUpBrick;
import com.myradev.lunarcode.content.bricks.PhiroIfLogicBeginBrick;
import com.myradev.lunarcode.content.bricks.PhiroMotorMoveBackwardBrick;
import com.myradev.lunarcode.content.bricks.PhiroMotorMoveForwardBrick;
import com.myradev.lunarcode.content.bricks.PhiroMotorStopBrick;
import com.myradev.lunarcode.content.bricks.PhiroPlayToneBrick;
import com.myradev.lunarcode.content.bricks.PhiroRGBLightBrick;
import com.myradev.lunarcode.content.bricks.PlaceAtBrick;
import com.myradev.lunarcode.content.bricks.PlayDrumForBeatsBrick;
import com.myradev.lunarcode.content.bricks.PlayNoteForBeatsBrick;
import com.myradev.lunarcode.content.bricks.PlaySoundAndWaitBrick;
import com.myradev.lunarcode.content.bricks.PlaySoundBrick;
import com.myradev.lunarcode.content.bricks.PointInDirectionBrick;
import com.myradev.lunarcode.content.bricks.PointToBrick;
import com.myradev.lunarcode.content.bricks.PreviousLookBrick;
import com.myradev.lunarcode.content.bricks.RaspiIfLogicBeginBrick;
import com.myradev.lunarcode.content.bricks.RaspiPwmBrick;
import com.myradev.lunarcode.content.bricks.RaspiSendDigitalValueBrick;
import com.myradev.lunarcode.content.bricks.RepeatBrick;
import com.myradev.lunarcode.content.bricks.RepeatUntilBrick;
import com.myradev.lunarcode.content.bricks.ReplaceItemInUserListBrick;
import com.myradev.lunarcode.content.bricks.ReportBrick;
import com.myradev.lunarcode.content.bricks.SceneStartBrick;
import com.myradev.lunarcode.content.bricks.SceneTransitionBrick;
import com.myradev.lunarcode.content.bricks.SetBackgroundAndWaitBrick;
import com.myradev.lunarcode.content.bricks.SetBackgroundBrick;
import com.myradev.lunarcode.content.bricks.SetBackgroundByIndexAndWaitBrick;
import com.myradev.lunarcode.content.bricks.SetBackgroundByIndexBrick;
import com.myradev.lunarcode.content.bricks.SetBounceBrick;
import com.myradev.lunarcode.content.bricks.SetBrightnessBrick;
import com.myradev.lunarcode.content.bricks.SetCameraFocusPointBrick;
import com.myradev.lunarcode.content.bricks.SetColorBrick;
import com.myradev.lunarcode.content.bricks.SetFrictionBrick;
import com.myradev.lunarcode.content.bricks.SetGravityBrick;
import com.myradev.lunarcode.content.bricks.SetListeningLanguageBrick;
import com.myradev.lunarcode.content.bricks.SetMassBrick;
import com.myradev.lunarcode.content.bricks.SetNfcTagBrick;
import com.myradev.lunarcode.content.bricks.SetParticleColorBrick;
import com.myradev.lunarcode.content.bricks.SetPenColorBrick;
import com.myradev.lunarcode.content.bricks.SetPenSizeBrick;
import com.myradev.lunarcode.content.bricks.SetPhysicsObjectTypeBrick;
import com.myradev.lunarcode.content.bricks.SetRotationStyleBrick;
import com.myradev.lunarcode.content.bricks.SetSizeToBrick;
import com.myradev.lunarcode.content.bricks.SetTempoBrick;
import com.myradev.lunarcode.content.bricks.SetTransparencyBrick;
import com.myradev.lunarcode.content.bricks.SetVariableBrick;
import com.myradev.lunarcode.content.bricks.SetVelocityBrick;
import com.myradev.lunarcode.content.bricks.SetVolumeToBrick;
import com.myradev.lunarcode.content.bricks.SetXBrick;
import com.myradev.lunarcode.content.bricks.SetYBrick;
import com.myradev.lunarcode.content.bricks.ShowBrick;
import com.myradev.lunarcode.content.bricks.ShowTextBrick;
import com.myradev.lunarcode.content.bricks.SpeakAndWaitBrick;
import com.myradev.lunarcode.content.bricks.SpeakBrick;
import com.myradev.lunarcode.content.bricks.StampBrick;
import com.myradev.lunarcode.content.bricks.StartListeningBrick;
import com.myradev.lunarcode.content.bricks.StopAllSoundsBrick;
import com.myradev.lunarcode.content.bricks.StopScriptBrick;
import com.myradev.lunarcode.content.bricks.StopSoundBrick;
import com.myradev.lunarcode.content.bricks.TurnLeftBrick;
import com.myradev.lunarcode.content.bricks.TurnLeftSpeedBrick;
import com.myradev.lunarcode.content.bricks.TurnRightBrick;
import com.myradev.lunarcode.content.bricks.TurnRightSpeedBrick;
import com.myradev.lunarcode.content.bricks.VibrationBrick;
import com.myradev.lunarcode.content.bricks.WaitBrick;
import com.myradev.lunarcode.content.bricks.WaitUntilBrick;
import com.myradev.lunarcode.content.bricks.WhenBackgroundChangesBrick;
import com.myradev.lunarcode.content.bricks.WhenBounceOffBrick;
import com.myradev.lunarcode.content.bricks.WhenBrick;
import com.myradev.lunarcode.content.bricks.WhenClonedBrick;
import com.myradev.lunarcode.content.bricks.WhenConditionBrick;
import com.myradev.lunarcode.content.bricks.WhenNfcBrick;
import com.myradev.lunarcode.content.bricks.WhenRaspiPinChangedBrick;
import com.myradev.lunarcode.content.bricks.WhenStartedBrick;
import com.myradev.lunarcode.content.bricks.WhenTouchDownBrick;
import com.myradev.lunarcode.testsuites.annotations.Cat;
import com.myradev.lunarcode.testsuites.annotations.Level;
import com.myradev.lunarcode.ui.SpriteActivity;
import com.myradev.lunarcode.ui.settingsfragments.SettingsFragment;
import com.myradev.lunarcode.uiespresso.ui.activity.rtl.RtlUiTestUtils;
import com.myradev.lunarcode.uiespresso.util.UiTestUtils;
import com.myradev.lunarcode.uiespresso.util.matchers.BrickCategoryListMatchers;
import com.myradev.lunarcode.uiespresso.util.matchers.BrickPrototypeListMatchers;
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule;
import com.myradev.lunarcode.utils.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_MINDSTORMS_EV3_BRICKS_CHECKBOX_PREFERENCE;
import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_MINDSTORMS_NXT_BRICKS_CHECKBOX_PREFERENCE;
import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_SHOW_AI_FACE_DETECTION_SENSORS;
import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_SHOW_AI_POSE_DETECTION_SENSORS;
import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_SHOW_AI_SPEECH_RECOGNITION_SENSORS;
import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_SHOW_AI_SPEECH_SYNTHETIZATION_SENSORS;
import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_SHOW_AI_TEXT_RECOGNITION_SENSORS;
import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_SHOW_ARDUINO_BRICKS;
import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_SHOW_JUMPING_SUMO_BRICKS;
import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_SHOW_NFC_BRICKS;
import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_SHOW_PARROT_AR_DRONE_BRICKS;
import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_SHOW_PHIRO_BRICKS_CHECKBOX_PREFERENCE;
import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_SHOW_RASPI_BRICKS;
import static com.myradev.lunarcode.uiespresso.util.matchers.rtl.RtlViewDirection.isViewRtl;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RtlBrickTest {

	@Rule
	public FragmentActivityTestRule<SpriteActivity> baseActivityTestRule = new
			FragmentActivityTestRule<>(SpriteActivity.class, SpriteActivity.EXTRA_FRAGMENT_POSITION,
			SpriteActivity.FRAGMENT_SCRIPTS);

	private Locale arLocale = new Locale("ar");
	private List<String> allPeripheralCategories = new ArrayList<>(Arrays.asList(SETTINGS_MINDSTORMS_NXT_BRICKS_CHECKBOX_PREFERENCE,
			SETTINGS_MINDSTORMS_EV3_BRICKS_CHECKBOX_PREFERENCE, SETTINGS_SHOW_PARROT_AR_DRONE_BRICKS, SETTINGS_SHOW_PHIRO_BRICKS_CHECKBOX_PREFERENCE,
			SETTINGS_SHOW_ARDUINO_BRICKS, SETTINGS_SHOW_RASPI_BRICKS, SETTINGS_SHOW_NFC_BRICKS,
			SETTINGS_SHOW_JUMPING_SUMO_BRICKS, SETTINGS_SHOW_AI_SPEECH_RECOGNITION_SENSORS,
			SETTINGS_SHOW_AI_SPEECH_SYNTHETIZATION_SENSORS,
			SETTINGS_SHOW_AI_FACE_DETECTION_SENSORS, SETTINGS_SHOW_AI_POSE_DETECTION_SENSORS,
			SETTINGS_SHOW_AI_TEXT_RECOGNITION_SENSORS));
	private List<String> enabledByThisTestPeripheralCategories = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		SettingsFragment.setLanguageSharedPreference(getTargetContext(), "ar");
		createProject("RtlBricksTest");
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext());

		for (String category : allPeripheralCategories) {
			boolean categoryEnabled = sharedPreferences.getBoolean(category, false);
			if (!categoryEnabled) {
				sharedPreferences.edit().putBoolean(category, true).commit();
				enabledByThisTestPeripheralCategories.add(category);
			}
		}
		Utils.fetchSpeechRecognitionSupportedLanguages(ApplicationProvider.getApplicationContext());
		baseActivityTestRule.launchActivity();
	}

	@After
	public void tearDown() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext());
		for (String category : enabledByThisTestPeripheralCategories) {
			sharedPreferences.edit().putBoolean(category, false).commit();
		}
		enabledByThisTestPeripheralCategories.clear();
		SettingsFragment.removeLanguageSharedPreference(getTargetContext());
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void eventBricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_event);

		checkIfBrickISRtl(WhenStartedBrick.class, R.id.brick_when_started_layout);

		checkIfBrickISRtl(WhenBrick.class, R.id.brick_when_layout);

		checkIfBrickISRtl(WhenTouchDownBrick.class, R.id.brick_when_screen_touched_layout);

		checkIfBrickISRtl(BroadcastReceiverBrick.class, R.id.brick_broadcast_receive_layout);

		checkIfBrickAtPositionIsRtl(BroadcastBrick.class, 0, R.id.brick_broadcast_layout);

		checkIfBrickISRtl(BroadcastWaitBrick.class, R.id.brick_broadcast_wait_layout);

		checkIfBrickISRtl(WhenConditionBrick.class, R.id.brick_when_condition_layout);

		checkIfBrickISRtl(WhenBounceOffBrick.class, R.id.brick_when_bounce_off_layout);

		checkIfBrickISRtl(WhenBackgroundChangesBrick.class, R.id.brick_when_background_layout);

		checkIfBrickISRtl(WhenClonedBrick.class, R.id.brick_when_cloned_layout);

		checkIfBrickISRtl(WhenNfcBrick.class, R.id.brick_when_nfc_layout);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void controlBricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_control);

		checkIfBrickISRtl(WaitBrick.class, R.id.brick_wait_layout);

		checkIfBrickISRtl(NoteBrick.class, R.id.brick_note_layout);

		checkIfBrickISRtl(ForeverBrick.class, R.id.brick_forever_layout);

		checkIfBrickAtPositionIsRtl(IfLogicBeginBrick.class, 0, R.id.brick_if_begin_layout);

		checkIfBrickISRtl(IfThenLogicBeginBrick.class, R.id.brick_if_begin_layout);

		checkIfBrickISRtl(WaitUntilBrick.class, R.id.brick_wait_until_layout);

		checkIfBrickISRtl(RepeatBrick.class, R.id.brick_repeat_layout);

		checkIfBrickISRtl(RepeatUntilBrick.class, R.id.brick_repeat_until_layout);

		checkIfBrickISRtl(SceneTransitionBrick.class, R.id.brick_scene_transition_layout);

		checkIfBrickISRtl(SceneStartBrick.class, R.id.brick_scene_start_layout);

		checkIfBrickISRtl(StopScriptBrick.class, R.id.brick_stop_script_layout);

		checkIfBrickISRtl(CloneBrick.class, R.id.brick_clone_layout);

		checkIfBrickISRtl(DeleteThisCloneBrick.class, R.id.brick_delete_clone_layout);

		checkIfBrickISRtl(WhenClonedBrick.class, R.id.brick_when_cloned_layout);

		checkIfBrickISRtl(SetNfcTagBrick.class, R.id.brick_set_nfc_tag_layout);

		checkIfBrickISRtl(OpenUrlBrick.class, R.id.brick_open_url_layout);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void motionBricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_motion);

		checkIfBrickISRtl(PlaceAtBrick.class, R.id.brick_place_at_layout);

		checkIfBrickISRtl(SetXBrick.class, R.id.brick_set_x_layout);

		checkIfBrickISRtl(SetYBrick.class, R.id.brick_set_y_layout);

		checkIfBrickISRtl(ChangeXByNBrick.class, R.id.brick_change_x_layout);

		checkIfBrickISRtl(ChangeYByNBrick.class, R.id.brick_change_y_layout);

		checkIfBrickISRtl(GoToBrick.class, R.id.brick_go_to_layout);

		checkIfBrickISRtl(MoveNStepsBrick.class, R.id.brick_move_n_steps_layout);

		checkIfBrickISRtl(TurnLeftBrick.class, R.id.brick_turn_left_layout);

		checkIfBrickISRtl(TurnRightBrick.class, R.id.brick_turn_right_layout);

		checkIfBrickISRtl(PointInDirectionBrick.class, R.id.brick_point_in_direction_layout);

		checkIfBrickISRtl(PointToBrick.class, R.id.brick_point_to_layout);

		checkIfBrickISRtl(SetRotationStyleBrick.class, R.id.brick_set_rotation_style_normal_layout);

		checkIfBrickISRtl(GlideToBrick.class, R.id.brick_glide_to_layout);

		checkIfBrickISRtl(VibrationBrick.class, R.id.brick_vibration_layout);

		checkIfBrickISRtl(SetPhysicsObjectTypeBrick.class, R.id.brick_set_physics_object_layout);

		checkIfBrickISRtl(SetVelocityBrick.class, R.id.brick_set_velocity_layout);

		checkIfBrickISRtl(TurnLeftSpeedBrick.class, R.id.brick_turn_left_speed_layout);

		checkIfBrickISRtl(TurnRightSpeedBrick.class, R.id.brick_turn_right_speed_layout);

		checkIfBrickISRtl(SetGravityBrick.class, R.id.brick_set_gravity_layout);

		checkIfBrickISRtl(SetMassBrick.class, R.id.brick_set_mass_layout);

		checkIfBrickISRtl(SetBounceBrick.class, R.id.brick_set_bounce_factor_layout);

		checkIfBrickISRtl(SetFrictionBrick.class, R.id.brick_set_friction_layout);

		checkIfBrickISRtl(FadeParticleEffectBrick.class, R.id.brick_fade_particle_effect_layout);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void soundBricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_sound);

		checkIfBrickAtPositionIsRtl(PlaySoundBrick.class, 0, R.id.brick_play_sound_layout);

		checkIfBrickISRtl(PlaySoundAndWaitBrick.class, R.id.brick_play_sound_layout);

		checkIfBrickISRtl(StopSoundBrick.class, R.id.brick_stop_sound_layout);

		checkIfBrickISRtl(SetTempoBrick.class, R.id.brick_set_tempo_layout);

		checkIfBrickISRtl(ChangeTempoByNBrick.class, R.id.brick_change_tempo_by_layout);

		checkIfBrickISRtl(StopAllSoundsBrick.class, R.id.brick_stop_all_sounds_layout);

		checkIfBrickISRtl(SetVolumeToBrick.class, R.id.brick_set_volume_to_layout);

		checkIfBrickISRtl(ChangeVolumeByNBrick.class, R.id.brick_change_volume_by_layout);

		checkIfBrickISRtl(SpeakBrick.class, R.id.brick_speak_layout);

		checkIfBrickISRtl(SpeakAndWaitBrick.class, R.id.brick_speak_and_wait_layout);

		checkIfBrickISRtl(PhiroPlayToneBrick.class, R.id.brick_phiro_play_tone_layout);

		checkIfBrickISRtl(AskSpeechBrick.class, R.id.brick_set_variable_layout);

		checkIfBrickISRtl(StartListeningBrick.class, R.id.brick_start_listening_layout);

		checkIfBrickISRtl(SetListeningLanguageBrick.class, R.id.brick_set_listening_language_layout);

		checkIfBrickISRtl(PauseForBeatsBrick.class, R.id.brick_pause_for_beats_layout);

		checkIfBrickISRtl(PlayNoteForBeatsBrick.class, R.id.brick_play_note_for_beats_layout);

		checkIfBrickISRtl(PlayDrumForBeatsBrick.class, R.id.brick_play_drum_for_beats_layout);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void looksBricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_looks);

		checkIfBrickISRtl(NextLookBrick.class, R.id.brick_next_look_layout);

		checkIfBrickISRtl(PreviousLookBrick.class, R.id.brick_previous_look_layout);

		checkIfBrickISRtl(SetSizeToBrick.class, R.id.brick_set_size_to_layout);

		checkIfBrickISRtl(ChangeSizeByNBrick.class, R.id.brick_change_size_by_layout);

		checkIfBrickISRtl(HideBrick.class, R.id.brick_hide_layout);

		checkIfBrickISRtl(ShowBrick.class, R.id.brick_show_layout);

		checkIfBrickISRtl(AskBrick.class, R.id.brick_set_variable_layout);

		checkIfBrickISRtl(SetTransparencyBrick.class, R.id.brick_set_transparency_layout);

		checkIfBrickISRtl(ChangeTransparencyByNBrick.class, R.id.brick_change_transparency_layout);

		checkIfBrickISRtl(SetBrightnessBrick.class, R.id.brick_set_brightness_layout);

		checkIfBrickISRtl(ChangeBrightnessByNBrick.class, R.id.brick_change_brightness_layout);

		checkIfBrickISRtl(SetColorBrick.class, R.id.brick_set_color_layout);

		checkIfBrickISRtl(ChangeColorByNBrick.class, R.id.brick_change_color_by_layout);

		checkIfBrickISRtl(ClearGraphicEffectBrick.class, R.id.brick_clear_graphic_effect_layout);

		checkIfBrickISRtl(SetCameraFocusPointBrick.class, R.id.brick_set_camera_focus_layout);

		checkIfBrickISRtl(WhenBackgroundChangesBrick.class, R.id.brick_when_background_layout);

		checkIfBrickAtPositionIsRtl(SetBackgroundBrick.class, 0, R.id.brick_set_background_layout);

		checkIfBrickAtPositionIsRtl(SetBackgroundAndWaitBrick.class, 0, R.id.brick_set_background_layout);

		checkIfBrickAtPositionIsRtl(SetBackgroundByIndexBrick.class, 0, R.id.brick_set_background_by_index_layout);

		checkIfBrickISRtl(SetBackgroundByIndexAndWaitBrick.class, R.id.brick_set_background_by_index_layout);

		checkIfBrickISRtl(CameraBrick.class, R.id.brick_video_layout);

		checkIfBrickISRtl(ChooseCameraBrick.class, R.id.brick_choose_camera_layout);

		checkIfBrickISRtl(FlashBrick.class, R.id.brick_flash_layout);

		checkIfBrickISRtl(PhiroRGBLightBrick.class, R.id.brick_phiro_rgb_led_layout);

		checkIfBrickISRtl(PaintNewLookBrick.class, R.id.brick_paint_new_look);

		checkIfBrickISRtl(DeleteLookBrick.class, R.id.brick_delete_look_layout);

		checkIfBrickISRtl(CopyLookBrick.class, R.id.brick_copy_look);

		checkIfBrickISRtl(OpenUrlBrick.class, R.id.brick_open_url_layout);

		checkIfBrickISRtl(EditLookBrick.class, R.id.brick_edit_look_layout);

		checkIfBrickISRtl(FadeParticleEffectBrick.class, R.id.brick_fade_particle_effect_layout);

		checkIfBrickISRtl(ParticleEffectAdditivityBrick.class, R.id.brick_particle_effect_additivity_layout);

		checkIfBrickISRtl(SetParticleColorBrick.class, R.id.brick_set_particle_color_layout);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void penBricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_pen);

		checkIfBrickISRtl(PenDownBrick.class, R.id.brick_pen_down_layout);

		checkIfBrickISRtl(PenUpBrick.class, R.id.brick_pen_up_layout);

		checkIfBrickISRtl(SetPenSizeBrick.class, R.id.brick_set_pen_size_layout);

		checkIfBrickISRtl(SetPenColorBrick.class, R.id.brick_set_pen_color_layout);

		checkIfBrickISRtl(StampBrick.class, R.id.brick_stamp_layout);

		checkIfBrickISRtl(ClearBackgroundBrick.class, R.id.brick_clear_background_layout);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void dataBricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_data);

		checkIfBrickISRtl(SetVariableBrick.class, R.id.brick_set_variable_layout);

		checkIfBrickISRtl(ChangeVariableBrick.class, R.id.brick_change_variable_layout);

		checkIfBrickISRtl(ShowTextBrick.class, R.id.brick_show_variable_layout);

		checkIfBrickISRtl(HideTextBrick.class, R.id.brick_hide_variable_layout);

		checkIfBrickISRtl(AddItemToUserListBrick.class, R.id.brick_add_item_to_userlist_layout);

		checkIfBrickISRtl(DeleteItemOfUserListBrick.class, R.id.brick_delete_item_of_userlist_layout);

		checkIfBrickISRtl(InsertItemIntoUserListBrick.class, R.id.brick_insert_item_into_userlist_layout);

		checkIfBrickISRtl(ReplaceItemInUserListBrick.class, R.id.brick_replace_item_in_userlist_layout);

		checkIfBrickISRtl(AskBrick.class, R.id.brick_set_variable_layout);

		checkIfBrickISRtl(AskSpeechBrick.class, R.id.brick_set_variable_layout);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void legoNxtBricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_lego_nxt);

		checkIfBrickISRtl(LegoNxtMotorTurnAngleBrick.class, R.id.brick_nxt_motor_turn_layout);

		checkIfBrickISRtl(LegoNxtMotorStopBrick.class, R.id.brick_nxt_motor_stop_layout);

		checkIfBrickISRtl(LegoNxtMotorMoveBrick.class, R.id.brick_nxt_motor_action_layout);

		checkIfBrickISRtl(LegoNxtPlayToneBrick.class, R.id.brick_nxt_play_tone_layout);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void legoEv3Bricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_lego_ev3);

		checkIfBrickISRtl(LegoEv3MotorTurnAngleBrick.class, R.id.brick_ev3_motor_turn_layout);

		checkIfBrickISRtl(LegoEv3MotorMoveBrick.class, R.id.brick_ev3_motor_move_layout);

		checkIfBrickISRtl(LegoEv3MotorStopBrick.class, R.id.brick_ev3_motor_stop_layout);

		checkIfBrickISRtl(LegoEv3PlayToneBrick.class, R.id.brick_ev3_play_tone_layout);

		checkIfBrickISRtl(LegoEv3SetLedBrick.class, R.id.brick_ev3_set_led_layout);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void arDroneBricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_drone);

		checkIfBrickISRtl(DroneTakeOffLandBrick.class, R.id.brick_drone_takeoff_land_layout);

		checkIfBrickISRtl(DroneEmergencyBrick.class, R.id.brick_drone_emergency_layout);

		checkIfBrickISRtl(DroneMoveUpBrick.class, R.id.brick_drone_move_up_layout);

		checkIfBrickISRtl(DroneMoveDownBrick.class, R.id.brick_drone_move_down_layout);

		checkIfBrickISRtl(DroneMoveLeftBrick.class, R.id.brick_drone_move_left_layout);

		checkIfBrickISRtl(DroneMoveRightBrick.class, R.id.brick_drone_move_right_layout);

		checkIfBrickISRtl(DroneMoveForwardBrick.class, R.id.brick_drone_move_forward_layout);

		checkIfBrickISRtl(DroneMoveBackwardBrick.class, R.id.brick_drone_move_backward_layout);

		checkIfBrickISRtl(DroneTurnLeftBrick.class, R.id.brick_drone_turn_left_layout);

		checkIfBrickISRtl(DroneTurnRightBrick.class, R.id.brick_drone_turn_right_layout);

		checkIfBrickISRtl(DroneFlipBrick.class, R.id.brick_drone_flip_layout);

		checkIfBrickISRtl(DronePlayLedAnimationBrick.class, R.id.brick_drone_play_led_animation_layout);

		checkIfBrickISRtl(DroneSwitchCameraBrick.class, R.id.brick_drone_switch_camera_layout);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void jumpingSumoBricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_jumping_sumo);

		checkIfBrickISRtl(JumpingSumoMoveForwardBrick.class, R.id.brick_jumping_sumo_move_forward_layout);

		checkIfBrickISRtl(JumpingSumoMoveBackwardBrick.class, R.id.brick_jumping_sumo_move_backward_layout);

		checkIfBrickISRtl(JumpingSumoAnimationsBrick.class, R.id.brick_jumping_sumo_animation_layout);

		checkIfBrickISRtl(JumpingSumoSoundBrick.class, R.id.brick_jumping_sumo_sound_layout);

		checkIfBrickISRtl(JumpingSumoNoSoundBrick.class, R.id.brick_jumping_sumo_nosound_layout);

		checkIfBrickISRtl(JumpingSumoJumpLongBrick.class, R.id.brick_jumping_sumo_jump_long_layout);

		checkIfBrickISRtl(JumpingSumoJumpHighBrick.class, R.id.brick_jumping_sumo_jump_high_layout);

		checkIfBrickISRtl(JumpingSumoRotateLeftBrick.class, R.id.brick_jumping_sumo_rotate_left_layout);

		checkIfBrickISRtl(JumpingSumoRotateRightBrick.class, R.id.brick_jumping_sumo_rotate_right_layout);

		checkIfBrickISRtl(JumpingSumoTurnBrick.class, R.id.brick_jumping_sumo_turn_layout);

		checkIfBrickISRtl(JumpingSumoTakingPictureBrick.class, R.id.brick_jumping_sumo_taking_picture_layout);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void phiroBricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_phiro);

		checkIfBrickISRtl(PhiroMotorMoveForwardBrick.class, R.id.brick_phiro_motor_forward_action_layout);

		checkIfBrickISRtl(PhiroMotorMoveBackwardBrick.class, R.id.brick_phiro_motor_backward_action_layout);

		checkIfBrickISRtl(PhiroMotorStopBrick.class, R.id.brick_phiro_motor_stop_layout);

		checkIfBrickISRtl(PhiroPlayToneBrick.class, R.id.brick_phiro_play_tone_layout);

		checkIfBrickISRtl(PhiroRGBLightBrick.class, R.id.brick_phiro_rgb_led_layout);

		checkIfBrickISRtl(PhiroIfLogicBeginBrick.class, R.id.brick_phiro_sensor_layout);

		checkIfBrickAtPositionIsRtl(SetVariableBrick.class, 0, R.id.brick_set_variable_layout);
		checkIfBrickAtPositionIsRtl(SetVariableBrick.class, 1, R.id.brick_set_variable_layout);
		checkIfBrickAtPositionIsRtl(SetVariableBrick.class, 2, R.id.brick_set_variable_layout);
		checkIfBrickAtPositionIsRtl(SetVariableBrick.class, 3, R.id.brick_set_variable_layout);
		checkIfBrickAtPositionIsRtl(SetVariableBrick.class, 4, R.id.brick_set_variable_layout);
		checkIfBrickAtPositionIsRtl(SetVariableBrick.class, 5, R.id.brick_set_variable_layout);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void arduinoBricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_arduino);

		checkIfBrickISRtl(ArduinoSendDigitalValueBrick.class, R.id.brick_arduino_send_digital_layout);

		checkIfBrickISRtl(ArduinoSendPWMValueBrick.class, R.id.brick_arduino_send_analog_layout);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void userBricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_user_bricks);

		checkIfBrickISRtl(ReportBrick.class, R.id.brick_report_layout);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void raspPiBricks() {
		assertEquals(arLocale.getDisplayLanguage(), Locale.getDefault().getDisplayLanguage());
		assertTrue(RtlUiTestUtils.checkTextDirectionIsRtl(Locale.getDefault().getDisplayName()));
		openCategory(R.string.category_raspi);

		checkIfBrickISRtl(WhenRaspiPinChangedBrick.class, R.id.brick_raspi_when_layout);

		checkIfBrickISRtl(RaspiIfLogicBeginBrick.class, R.id.brick_raspi_if_begin_layout);

		checkIfBrickISRtl(RaspiSendDigitalValueBrick.class, R.id.brick_raspi_send_digital_layout);

		checkIfBrickISRtl(RaspiPwmBrick.class, R.id.brick_raspi_pwm_layout);
	}

	private void checkIfBrickISRtl(Class brickClass, int bricksId) {
		onData(instanceOf(brickClass)).inAdapterView(BrickPrototypeListMatchers.isBrickPrototypeView())
				.onChildView(withId(bricksId))
				.check(matches(isViewRtl()));
	}

	private void checkIfBrickAtPositionIsRtl(Class brickClass, int position, int brickId) {
		onData(instanceOf(brickClass)).inAdapterView(BrickPrototypeListMatchers.isBrickPrototypeView())
				.atPosition(position)
				.onChildView(withId(brickId))
				.check(matches(isViewRtl()));
	}

	private void createProject(String projectName) {
		String nameSpriteTwo = "testSpriteTwo";
		Project project = UiTestUtils.createDefaultTestProject(projectName);

		Sprite spriteTwo = new Sprite(nameSpriteTwo);
		Script script = new StartScript();
		spriteTwo.addScript(script);

		project.getDefaultScene().addSprite(spriteTwo);
		ProjectManager.getInstance().setCurrentSprite(spriteTwo);
	}

	private void openCategory(int categoryNameStringResourceId) {
		onView(withId(R.id.button_add))
				.perform(click());

		onData(allOf(is(instanceOf(String.class)), is(UiTestUtils.getResourcesString(categoryNameStringResourceId))))
				.inAdapterView(BrickCategoryListMatchers.isBrickCategoryView())
				.perform(click());
	}
}
