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
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

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
import com.myradev.lunarcode.content.bricks.AssertEqualsBrick;
import com.myradev.lunarcode.content.bricks.ChangeBrightnessByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeColorByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeSizeByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeTransparencyByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeVariableBrick;
import com.myradev.lunarcode.content.bricks.ChangeVolumeByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeXByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeYByNBrick;
import com.myradev.lunarcode.content.bricks.DeleteItemOfUserListBrick;
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
import com.myradev.lunarcode.content.bricks.GoNStepsBackBrick;
import com.myradev.lunarcode.content.bricks.IfLogicBeginBrick;
import com.myradev.lunarcode.content.bricks.IfThenLogicBeginBrick;
import com.myradev.lunarcode.content.bricks.InsertItemIntoUserListBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoMoveBackwardBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoMoveForwardBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoRotateLeftBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoRotateRightBrick;
import com.myradev.lunarcode.content.bricks.JumpingSumoSoundBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3MotorMoveBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3MotorTurnAngleBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3PlayToneBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorMoveBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorTurnAngleBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtPlayToneBrick;
import com.myradev.lunarcode.content.bricks.MoveNStepsBrick;
import com.myradev.lunarcode.content.bricks.NoteBrick;
import com.myradev.lunarcode.content.bricks.PhiroMotorMoveBackwardBrick;
import com.myradev.lunarcode.content.bricks.PhiroMotorMoveForwardBrick;
import com.myradev.lunarcode.content.bricks.PhiroPlayToneBrick;
import com.myradev.lunarcode.content.bricks.PointInDirectionBrick;
import com.myradev.lunarcode.content.bricks.RaspiIfLogicBeginBrick;
import com.myradev.lunarcode.content.bricks.RaspiPwmBrick;
import com.myradev.lunarcode.content.bricks.RaspiSendDigitalValueBrick;
import com.myradev.lunarcode.content.bricks.ReplaceItemInUserListBrick;
import com.myradev.lunarcode.content.bricks.SayBubbleBrick;
import com.myradev.lunarcode.content.bricks.SayForBubbleBrick;
import com.myradev.lunarcode.content.bricks.SetBackgroundByIndexAndWaitBrick;
import com.myradev.lunarcode.content.bricks.SetBackgroundByIndexBrick;
import com.myradev.lunarcode.content.bricks.SetBounceBrick;
import com.myradev.lunarcode.content.bricks.SetBrightnessBrick;
import com.myradev.lunarcode.content.bricks.SetColorBrick;
import com.myradev.lunarcode.content.bricks.SetFrictionBrick;
import com.myradev.lunarcode.content.bricks.SetGravityBrick;
import com.myradev.lunarcode.content.bricks.SetLookByIndexBrick;
import com.myradev.lunarcode.content.bricks.SetMassBrick;
import com.myradev.lunarcode.content.bricks.SetNfcTagBrick;
import com.myradev.lunarcode.content.bricks.SetPenSizeBrick;
import com.myradev.lunarcode.content.bricks.SetSizeToBrick;
import com.myradev.lunarcode.content.bricks.SetTransparencyBrick;
import com.myradev.lunarcode.content.bricks.SetVariableBrick;
import com.myradev.lunarcode.content.bricks.SetVelocityBrick;
import com.myradev.lunarcode.content.bricks.SetVolumeToBrick;
import com.myradev.lunarcode.content.bricks.SetXBrick;
import com.myradev.lunarcode.content.bricks.SetYBrick;
import com.myradev.lunarcode.content.bricks.ShowTextColorSizeAlignmentBrick;
import com.myradev.lunarcode.content.bricks.SpeakAndWaitBrick;
import com.myradev.lunarcode.content.bricks.SpeakBrick;
import com.myradev.lunarcode.content.bricks.ThinkBubbleBrick;
import com.myradev.lunarcode.content.bricks.ThinkForBubbleBrick;
import com.myradev.lunarcode.content.bricks.TurnLeftBrick;
import com.myradev.lunarcode.content.bricks.TurnLeftSpeedBrick;
import com.myradev.lunarcode.content.bricks.TurnRightBrick;
import com.myradev.lunarcode.content.bricks.TurnRightSpeedBrick;
import com.myradev.lunarcode.content.bricks.VibrationBrick;
import com.myradev.lunarcode.content.bricks.WaitBrick;
import com.myradev.lunarcode.content.bricks.WaitUntilBrick;
import com.myradev.lunarcode.content.bricks.WhenConditionBrick;
import com.myradev.lunarcode.ui.SpriteActivity;
import com.myradev.lunarcode.ui.fragment.FormulaEditorFragment;
import com.myradev.lunarcode.ui.recyclerview.fragment.ScriptFragment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.ParameterizedRobolectricTestRunner;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.Collection;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(ParameterizedRobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.P})
public class BrickSingleFormulaFieldTest {

	private SpriteActivity activity;

	@ParameterizedRobolectricTestRunner.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				//Bricks with single formula fields
				{WhenConditionBrick.class.getSimpleName(), new WhenConditionBrick(), R.id.brick_when_condition_edit_text},
				{SetXBrick.class.getSimpleName(), new SetXBrick(), R.id.brick_set_x_edit_text},
				{SetYBrick.class.getSimpleName(), new SetYBrick(), R.id.brick_set_y_edit_text},
				{WaitBrick.class.getSimpleName(), new WaitBrick(), R.id.brick_wait_edit_text},
				{NoteBrick.class.getSimpleName(), new NoteBrick(), R.id.brick_note_edit_text},
				{WaitUntilBrick.class.getSimpleName(), new WaitUntilBrick(), R.id.brick_wait_until_edit_text},
				{ChangeXByNBrick.class.getSimpleName(), new ChangeXByNBrick(), R.id.brick_change_x_edit_text},
				{ChangeYByNBrick.class.getSimpleName(), new ChangeYByNBrick(), R.id.brick_change_y_edit_text},
				{MoveNStepsBrick.class.getSimpleName(), new MoveNStepsBrick(), R.id.brick_move_n_steps_edit_text},
				{TurnLeftBrick.class.getSimpleName(), new TurnLeftBrick(), R.id.brick_turn_left_edit_text},
				{TurnRightBrick.class.getSimpleName(), new TurnRightBrick(), R.id.brick_turn_right_edit_text},
				{PointInDirectionBrick.class.getSimpleName(), new PointInDirectionBrick(), R.id.brick_point_in_direction_edit_text},
				{GoNStepsBackBrick.class.getSimpleName(), new GoNStepsBackBrick(), R.id.brick_go_back_edit_text},
				{VibrationBrick.class.getSimpleName(), new VibrationBrick(), R.id.brick_vibration_edit_text},
				{TurnLeftSpeedBrick.class.getSimpleName(), new TurnLeftSpeedBrick(), R.id.brick_turn_left_speed_edit_text},
				{TurnRightSpeedBrick.class.getSimpleName(), new TurnRightSpeedBrick(), R.id.brick_turn_right_speed_edit_text},
				{SetMassBrick.class.getSimpleName(), new SetMassBrick(), R.id.brick_set_mass_edit_text},
				{SetBounceBrick.class.getSimpleName(), new SetBounceBrick(), R.id.brick_set_bounce_factor_edit_text},
				{SetFrictionBrick.class.getSimpleName(), new SetFrictionBrick(), R.id.brick_set_friction_edit_text},
				{SetVolumeToBrick.class.getSimpleName(), new SetVolumeToBrick(), R.id.brick_set_volume_to_edit_text},
				{ChangeVolumeByNBrick.class.getSimpleName(), new ChangeVolumeByNBrick(), R.id.brick_change_volume_by_edit_text},
				{SpeakBrick.class.getSimpleName(), new SpeakBrick(), R.id.brick_speak_edit_text},
				{SpeakAndWaitBrick.class.getSimpleName(), new SpeakAndWaitBrick(), R.id.brick_speak_and_wait_edit_text},
				{AskBrick.class.getSimpleName(), new AskBrick(), R.id.brick_ask_question_edit_text},
				{SetLookByIndexBrick.class.getSimpleName(), new SetLookByIndexBrick(), R.id.brick_set_look_by_index_edit_text},
				{SetSizeToBrick.class.getSimpleName(), new SetSizeToBrick(), R.id.brick_set_size_to_edit_text},
				{ChangeSizeByNBrick.class.getSimpleName(), new ChangeSizeByNBrick(), R.id.brick_change_size_by_edit_text},
				{AskSpeechBrick.class.getSimpleName(), new AskSpeechBrick(), R.id.brick_ask_speech_question_edit_text},
				{SayBubbleBrick.class.getSimpleName(), new SayBubbleBrick(), R.id.brick_bubble_edit_text},
				{ThinkBubbleBrick.class.getSimpleName(), new ThinkBubbleBrick(), R.id.brick_bubble_edit_text},
				{SetTransparencyBrick.class.getSimpleName(), new SetTransparencyBrick(), R.id.brick_set_transparency_to_edit_text},
				{ChangeTransparencyByNBrick.class.getSimpleName(), new ChangeTransparencyByNBrick(), R.id.brick_change_transparency_edit_text},
				{SetBrightnessBrick.class.getSimpleName(), new SetBrightnessBrick(), R.id.brick_set_brightness_edit_text},
				{ChangeBrightnessByNBrick.class.getSimpleName(), new ChangeBrightnessByNBrick(), R.id.brick_change_brightness_edit_text},
				{SetColorBrick.class.getSimpleName(), new SetColorBrick(), R.id.brick_set_color_edit_text},
				{ChangeColorByNBrick.class.getSimpleName(), new ChangeColorByNBrick(), R.id.brick_change_color_by_edit_text},
				{SetBackgroundByIndexBrick.class.getSimpleName(), new SetBackgroundByIndexBrick(), R.id.brick_set_background_by_index_edit_text},
				{SetBackgroundByIndexAndWaitBrick.class.getSimpleName(), new SetBackgroundByIndexAndWaitBrick(), R.id.brick_set_background_by_index_wait_edit_text},
				{SetPenSizeBrick.class.getSimpleName(), new SetPenSizeBrick(), R.id.brick_set_pen_size_edit_text},
				{SetVariableBrick.class.getSimpleName(), new SetVariableBrick(), R.id.brick_set_variable_edit_text},
				{ChangeVariableBrick.class.getSimpleName(), new ChangeVariableBrick(), R.id.brick_change_variable_edit_text},
				{AddItemToUserListBrick.class.getSimpleName(), new AddItemToUserListBrick(), R.id.brick_add_item_to_userlist_edit_text},
				{DeleteItemOfUserListBrick.class.getSimpleName(), new DeleteItemOfUserListBrick(), R.id.brick_delete_item_of_userlist_edit_text},
				{IfLogicBeginBrick.class.getSimpleName(), new IfLogicBeginBrick(), R.id.brick_if_begin_edit_text},
				{IfThenLogicBeginBrick.class.getSimpleName(), new IfThenLogicBeginBrick(), R.id.brick_if_begin_edit_text},

				//Bricks with multiple formula fields:
				{GlideToBrick.class.getSimpleName() + " duration", new GlideToBrick(), R.id.brick_glide_to_edit_text_duration},
				{SetVelocityBrick.class.getSimpleName() + " x", new SetVelocityBrick(), R.id.brick_set_velocity_edit_text_x},
				{SetVelocityBrick.class.getSimpleName() + " y", new SetVelocityBrick(), R.id.brick_set_velocity_edit_text_y},
				{SetGravityBrick.class.getSimpleName() + " x", new SetGravityBrick(), R.id.brick_set_gravity_edit_text_x},
				{SetGravityBrick.class.getSimpleName() + " y", new SetGravityBrick(), R.id.brick_set_gravity_edit_text_y},
				{SayForBubbleBrick.class.getSimpleName() + " text", new SayForBubbleBrick(), R.id.brick_for_bubble_edit_text_text},
				{SayForBubbleBrick.class.getSimpleName() + " duration", new SayForBubbleBrick(), R.id.brick_for_bubble_edit_text_duration},
				{ThinkForBubbleBrick.class.getSimpleName() + " text", new ThinkForBubbleBrick(), R.id.brick_for_bubble_edit_text_text},
				{ThinkForBubbleBrick.class.getSimpleName() + " duration", new ThinkForBubbleBrick(), R.id.brick_for_bubble_edit_text_duration},
				{ShowTextColorSizeAlignmentBrick.class.getSimpleName() + " color", new ShowTextColorSizeAlignmentBrick(), R.id.brick_show_variable_color_size_edit_color},
				{ShowTextColorSizeAlignmentBrick.class.getSimpleName() + " size", new ShowTextColorSizeAlignmentBrick(), R.id.brick_show_variable_color_size_edit_relative_size},
				{InsertItemIntoUserListBrick.class.getSimpleName() + " value", new InsertItemIntoUserListBrick(), R.id.brick_insert_item_into_userlist_value_edit_text},
				{InsertItemIntoUserListBrick.class.getSimpleName() + " index", new InsertItemIntoUserListBrick(), R.id.brick_insert_item_into_userlist_at_index_edit_text},
				{ReplaceItemInUserListBrick.class.getSimpleName() + " value", new ReplaceItemInUserListBrick(), R.id.brick_replace_item_in_userlist_value_edit_text},
				{ReplaceItemInUserListBrick.class.getSimpleName() + " index", new ReplaceItemInUserListBrick(), R.id.brick_replace_item_in_userlist_at_index_edit_text},
				{ReplaceItemInUserListBrick.class.getSimpleName() + " index", new ReplaceItemInUserListBrick(), R.id.brick_replace_item_in_userlist_at_index_edit_text},

				//NFC bricks:
				{SetNfcTagBrick.class.getSimpleName(), new SetNfcTagBrick(), R.id.brick_set_nfc_tag_edit_text},

				//Lego bricks:
				{LegoNxtMotorTurnAngleBrick.class.getSimpleName(), new LegoNxtMotorTurnAngleBrick(), R.id.motor_turn_angle_edit_text},
				{LegoNxtMotorMoveBrick.class.getSimpleName(), new LegoNxtMotorMoveBrick(), R.id.motor_action_speed_edit_text},
				{LegoNxtPlayToneBrick.class.getSimpleName() + " freq", new LegoNxtPlayToneBrick(), R.id.nxt_tone_freq_edit_text},
				{LegoNxtPlayToneBrick.class.getSimpleName() + " duration", new LegoNxtPlayToneBrick(), R.id.nxt_tone_duration_edit_text},
				{LegoEv3MotorTurnAngleBrick.class.getSimpleName(), new LegoEv3MotorTurnAngleBrick(), R.id.ev3_motor_turn_angle_edit_text},
				{LegoEv3MotorMoveBrick.class.getSimpleName(), new LegoEv3MotorMoveBrick(), R.id.ev3_motor_move_speed_edit_text},
				{LegoEv3PlayToneBrick.class.getSimpleName() + " freq", new LegoEv3PlayToneBrick(), R.id.brick_ev3_tone_freq_edit_text},
				{LegoEv3PlayToneBrick.class.getSimpleName() + " duration", new LegoEv3PlayToneBrick(), R.id.brick_ev3_tone_duration_edit_text},
				{LegoEv3PlayToneBrick.class.getSimpleName() + " volume", new LegoEv3PlayToneBrick(), R.id.brick_ev3_tone_volume_edit_text},

				//Drone Bricks
				{DroneMoveRightBrick.class.getSimpleName() + " duration", new DroneMoveRightBrick(), R.id.brick_drone_move_right_edit_text_second},
				{DroneMoveRightBrick.class.getSimpleName() + " power", new DroneMoveRightBrick(), R.id.brick_drone_move_right_edit_text_power},
				{DroneMoveLeftBrick.class.getSimpleName() + " duration", new DroneMoveLeftBrick(), R.id.brick_drone_move_left_edit_text_second},
				{DroneMoveLeftBrick.class.getSimpleName() + " power", new DroneMoveLeftBrick(), R.id.brick_drone_move_left_edit_text_power},
				{DroneMoveDownBrick.class.getSimpleName() + " duration", new DroneMoveDownBrick(), R.id.brick_drone_move_down_edit_text_second},
				{DroneMoveDownBrick.class.getSimpleName() + " power", new DroneMoveDownBrick(), R.id.brick_drone_move_down_edit_text_power},
				{DroneMoveUpBrick.class.getSimpleName() + " duration", new DroneMoveUpBrick(), R.id.brick_drone_move_up_edit_text_second},
				{DroneMoveUpBrick.class.getSimpleName() + " power", new DroneMoveUpBrick(), R.id.brick_drone_move_up_edit_text_power},
				{DroneMoveForwardBrick.class.getSimpleName() + " duration", new DroneMoveForwardBrick(), R.id.brick_drone_move_forward_edit_text_second},
				{DroneMoveForwardBrick.class.getSimpleName() + " power", new DroneMoveForwardBrick(), R.id.brick_drone_move_forward_edit_text_power},
				{DroneMoveBackwardBrick.class.getSimpleName() + " duration", new DroneMoveBackwardBrick(), R.id.brick_drone_move_backward_edit_text_second},
				{DroneMoveBackwardBrick.class.getSimpleName() + " power", new DroneMoveBackwardBrick(), R.id.brick_drone_move_backward_edit_text_power},
				{DroneTurnRightBrick.class.getSimpleName() + " duration", new DroneTurnRightBrick(), R.id.brick_drone_turn_right_edit_text_second},
				{DroneTurnRightBrick.class.getSimpleName() + " power", new DroneTurnRightBrick(), R.id.brick_drone_turn_right_edit_text_power},
				{DroneTurnLeftBrick.class.getSimpleName() + " duration", new DroneTurnLeftBrick(), R.id.brick_drone_turn_left_edit_text_second},
				{DroneTurnLeftBrick.class.getSimpleName() + " power", new DroneTurnLeftBrick(), R.id.brick_drone_turn_left_edit_text_power},

				{JumpingSumoMoveForwardBrick.class.getSimpleName() + " duration", new JumpingSumoMoveForwardBrick(), R.id.brick_jumping_sumo_move_forward_edit_text_second},
				{JumpingSumoMoveForwardBrick.class.getSimpleName() + " power", new JumpingSumoMoveForwardBrick(), R.id.brick_jumping_sumo_move_forward_edit_text_power},
				{JumpingSumoMoveBackwardBrick.class.getSimpleName() + " duration", new JumpingSumoMoveBackwardBrick(), R.id.brick_jumping_sumo_move_backward_edit_text_second},
				{JumpingSumoMoveBackwardBrick.class.getSimpleName() + " power", new JumpingSumoMoveBackwardBrick(), R.id.brick_jumping_sumo_move_backward_edit_text_power},
				{JumpingSumoSoundBrick.class.getSimpleName(), new JumpingSumoSoundBrick(), R.id.brick_jumping_sumo_sound_edit_text},
				{JumpingSumoRotateLeftBrick.class.getSimpleName(), new JumpingSumoRotateLeftBrick(), R.id.brick_jumping_sumo_change_left_variable_edit_text},
				{JumpingSumoRotateRightBrick.class.getSimpleName(), new JumpingSumoRotateRightBrick(), R.id.brick_jumping_sumo_change_right_variable_edit_text},

				//Phiro bricks
				{PhiroMotorMoveBackwardBrick.class.getSimpleName(), new PhiroMotorMoveBackwardBrick(), R.id.brick_phiro_motor_backward_action_speed_edit_text},
				{PhiroMotorMoveForwardBrick.class.getSimpleName(), new PhiroMotorMoveForwardBrick(), R.id.brick_phiro_motor_forward_action_speed_edit_text},
				{PhiroPlayToneBrick.class.getSimpleName(), new PhiroPlayToneBrick(), R.id.brick_phiro_play_tone_duration_edit_text},

				//Arduino bricks
				{ArduinoSendDigitalValueBrick.class.getSimpleName() + " pin", new ArduinoSendDigitalValueBrick(), R.id.brick_arduino_set_digital_pin_edit_text},
				{ArduinoSendDigitalValueBrick.class.getSimpleName() + " value", new ArduinoSendDigitalValueBrick(), R.id.brick_arduino_set_digital_value_edit_text},
				{ArduinoSendPWMValueBrick.class.getSimpleName() + " pin", new ArduinoSendPWMValueBrick(), R.id.brick_arduino_set_analog_pin_edit_text},
				{ArduinoSendPWMValueBrick.class.getSimpleName() + " value", new ArduinoSendPWMValueBrick(), R.id.brick_arduino_set_analog_value_edit_text},

				//Raspberry bricks
				{RaspiSendDigitalValueBrick.class.getSimpleName() + " pin", new RaspiSendDigitalValueBrick(), R.id.brick_raspi_set_digital_pin_edit_text},
				{RaspiSendDigitalValueBrick.class.getSimpleName() + " value", new RaspiSendDigitalValueBrick(), R.id.brick_raspi_set_digital_value_edit_text},
				{RaspiPwmBrick.class.getSimpleName() + " pin", new RaspiPwmBrick(), R.id.brick_raspi_pwm_pin_edit_text},
				{RaspiPwmBrick.class.getSimpleName() + " frequency", new RaspiPwmBrick(), R.id.brick_raspi_pwm_frequency_edit_text},
				{RaspiPwmBrick.class.getSimpleName() + " percentage", new RaspiPwmBrick(), R.id.brick_raspi_pwm_percentage_edit_text},
				{RaspiIfLogicBeginBrick.class.getSimpleName(), new RaspiIfLogicBeginBrick(), R.id.brick_if_begin_edit_text},

				//Testing bricks
				{AssertEqualsBrick.class.getSimpleName() + " actual", new AssertEqualsBrick(), R.id.brick_assert_actual},
				{AssertEqualsBrick.class.getSimpleName() + " expected", new AssertEqualsBrick(), R.id.brick_assert_expected},
		});
	}

	@SuppressWarnings("PMD.UnusedPrivateField")
	private String name;

	private FormulaBrick brick;

	private @IdRes int formulaTextFieldId;

	public BrickSingleFormulaFieldTest(String name, FormulaBrick brick, @IdRes int formulaTextFieldId) {
		this.name = name;
		this.brick = brick;
		this.formulaTextFieldId = formulaTextFieldId;
	}

	@Before
	public void setUp() throws Exception {
		ActivityController<SpriteActivity> activityController = Robolectric.buildActivity(SpriteActivity.class);
		activity = activityController.get();
		createProject(activity);
		activityController.create().resume();

		assertCurrentFragmentEquals(ScriptFragment.class);
	}

	@After
	public void tearDown() {
		ProjectManager.getInstance().resetProjectManager();
	}

	@Test
	public void openFormulaEditorTest() {
		clickOnBricksFormulaTextView();

		assertCurrentFragmentEquals(FormulaEditorFragment.class);

		enterSomeValueAndSave();

		assertCurrentFragmentEquals(ScriptFragment.class);

		assertBricksFormulaTextView();
	}

	private void assertBricksFormulaTextView() {
		View brickView = brick.getView(activity);
		assertNotNull(brickView);

		TextView brickFormulaTextView = brickView.findViewById(formulaTextFieldId);
		assertNotNull(brickFormulaTextView);
		assertEquals("5 ", brickFormulaTextView.getText().toString());
	}

	private void clickOnBricksFormulaTextView() {
		View brickView = brick.getView(activity);
		assertNotNull(brickView);

		TextView brickFormulaTextView = brickView.findViewById(formulaTextFieldId);
		assertNotNull(brickFormulaTextView);

		brick.onClick(brickFormulaTextView);

		shadowOf(Looper.getMainLooper()).idle();
	}

	private void enterSomeValueAndSave() {
		Fragment formulaEditorFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);

		((FormulaEditorFragment) formulaEditorFragment)
				.getFormulaEditorEditText().handleKeyEvent(R.id.formula_editor_keyboard_5, "");

		((FormulaEditorFragment) formulaEditorFragment)
				.endFormulaEditor();

		shadowOf(Looper.getMainLooper()).idle();
	}

	private void assertCurrentFragmentEquals(Class fragmentClazz) {
		Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
		assertNotNull(fragment);
		assertThat(fragment, is(instanceOf(fragmentClazz)));
	}

	private void createProject(Activity activity) {
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
