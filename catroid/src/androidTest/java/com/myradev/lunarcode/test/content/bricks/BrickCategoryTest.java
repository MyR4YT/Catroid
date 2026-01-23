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

import android.content.Context;
import android.preference.PreferenceManager;

import com.myradev.lunarcode.ProjectManager;
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
import com.myradev.lunarcode.content.bricks.AssertUserListsBrick;
import com.myradev.lunarcode.content.bricks.Brick;
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
import com.myradev.lunarcode.content.bricks.ClearUserListBrick;
import com.myradev.lunarcode.content.bricks.CloneBrick;
import com.myradev.lunarcode.content.bricks.ComeToFrontBrick;
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
import com.myradev.lunarcode.content.bricks.EmbroideryArcBrick;
import com.myradev.lunarcode.content.bricks.EmbroideryThroughBrick;
import com.myradev.lunarcode.content.bricks.ExitStageBrick;
import com.myradev.lunarcode.content.bricks.FadeParticleEffectBrick;
import com.myradev.lunarcode.content.bricks.FinishStageBrick;
import com.myradev.lunarcode.content.bricks.FlashBrick;
import com.myradev.lunarcode.content.bricks.ForItemInUserListBrick;
import com.myradev.lunarcode.content.bricks.ForVariableFromToBrick;
import com.myradev.lunarcode.content.bricks.ForeverBrick;
import com.myradev.lunarcode.content.bricks.GlideToBrick;
import com.myradev.lunarcode.content.bricks.GoNStepsBackBrick;
import com.myradev.lunarcode.content.bricks.GoToBrick;
import com.myradev.lunarcode.content.bricks.HideBrick;
import com.myradev.lunarcode.content.bricks.HideTextBrick;
import com.myradev.lunarcode.content.bricks.IfLogicBeginBrick;
import com.myradev.lunarcode.content.bricks.IfOnEdgeBounceBrick;
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
import com.myradev.lunarcode.content.bricks.LaserArcBrick;
import com.myradev.lunarcode.content.bricks.LaserThroughBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3MotorMoveBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3MotorStopBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3MotorTurnAngleBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3PlayToneBrick;
import com.myradev.lunarcode.content.bricks.LegoEv3SetLedBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorMoveBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorStopBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtMotorTurnAngleBrick;
import com.myradev.lunarcode.content.bricks.LegoNxtPlayToneBrick;
import com.myradev.lunarcode.content.bricks.LookRequestBrick;
import com.myradev.lunarcode.content.bricks.MoveNStepsBrick;
import com.myradev.lunarcode.content.bricks.NextLookBrick;
import com.myradev.lunarcode.content.bricks.NoteBrick;
import com.myradev.lunarcode.content.bricks.OpenUrlBrick;
import com.myradev.lunarcode.content.bricks.PaintNewLookBrick;
import com.myradev.lunarcode.content.bricks.ParameterizedBrick;
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
import com.myradev.lunarcode.content.bricks.PlaySoundAtBrick;
import com.myradev.lunarcode.content.bricks.PlaySoundBrick;
import com.myradev.lunarcode.content.bricks.PlotArcBrick;
import com.myradev.lunarcode.content.bricks.PlotThroughBrick;
import com.myradev.lunarcode.content.bricks.PointInDirectionBrick;
import com.myradev.lunarcode.content.bricks.PointToBrick;
import com.myradev.lunarcode.content.bricks.PreviousLookBrick;
import com.myradev.lunarcode.content.bricks.RaspiIfLogicBeginBrick;
import com.myradev.lunarcode.content.bricks.RaspiPwmBrick;
import com.myradev.lunarcode.content.bricks.RaspiSendDigitalValueBrick;
import com.myradev.lunarcode.content.bricks.ReadListFromDeviceBrick;
import com.myradev.lunarcode.content.bricks.ReadVariableFromDeviceBrick;
import com.myradev.lunarcode.content.bricks.ReadVariableFromFileBrick;
import com.myradev.lunarcode.content.bricks.RepeatBrick;
import com.myradev.lunarcode.content.bricks.RepeatUntilBrick;
import com.myradev.lunarcode.content.bricks.ReplaceItemInUserListBrick;
import com.myradev.lunarcode.content.bricks.ResetTimerBrick;
import com.myradev.lunarcode.content.bricks.RunningStitchBrick;
import com.myradev.lunarcode.content.bricks.SaveLaserBrick;
import com.myradev.lunarcode.content.bricks.SavePlotBrick;
import com.myradev.lunarcode.content.bricks.SayBubbleBrick;
import com.myradev.lunarcode.content.bricks.SayForBubbleBrick;
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
import com.myradev.lunarcode.content.bricks.SetInstrumentBrick;
import com.myradev.lunarcode.content.bricks.SetListeningLanguageBrick;
import com.myradev.lunarcode.content.bricks.SetLookBrick;
import com.myradev.lunarcode.content.bricks.SetLookByIndexBrick;
import com.myradev.lunarcode.content.bricks.SetMassBrick;
import com.myradev.lunarcode.content.bricks.SetParticleColorBrick;
import com.myradev.lunarcode.content.bricks.SetPenColorBrick;
import com.myradev.lunarcode.content.bricks.SetPenSizeBrick;
import com.myradev.lunarcode.content.bricks.SetPhysicsObjectTypeBrick;
import com.myradev.lunarcode.content.bricks.SetRotationStyleBrick;
import com.myradev.lunarcode.content.bricks.SetSizeToBrick;
import com.myradev.lunarcode.content.bricks.SetTempoBrick;
import com.myradev.lunarcode.content.bricks.SetThreadColorBrick;
import com.myradev.lunarcode.content.bricks.SetTransparencyBrick;
import com.myradev.lunarcode.content.bricks.SetVariableBrick;
import com.myradev.lunarcode.content.bricks.SetVelocityBrick;
import com.myradev.lunarcode.content.bricks.SetVolumeToBrick;
import com.myradev.lunarcode.content.bricks.SetXBrick;
import com.myradev.lunarcode.content.bricks.SetYBrick;
import com.myradev.lunarcode.content.bricks.SewUpBrick;
import com.myradev.lunarcode.content.bricks.ShareLaserBrick;
import com.myradev.lunarcode.content.bricks.SharePlotBrick;
import com.myradev.lunarcode.content.bricks.ShowBrick;
import com.myradev.lunarcode.content.bricks.ShowTextBrick;
import com.myradev.lunarcode.content.bricks.ShowTextColorSizeAlignmentBrick;
import com.myradev.lunarcode.content.bricks.SpeakAndWaitBrick;
import com.myradev.lunarcode.content.bricks.SpeakBrick;
import com.myradev.lunarcode.content.bricks.StampBrick;
import com.myradev.lunarcode.content.bricks.StartCutBrick;
import com.myradev.lunarcode.content.bricks.StartEngraveBrick;
import com.myradev.lunarcode.content.bricks.StartListeningBrick;
import com.myradev.lunarcode.content.bricks.StartPlotBrick;
import com.myradev.lunarcode.content.bricks.StitchBrick;
import com.myradev.lunarcode.content.bricks.StopAllSoundsBrick;
import com.myradev.lunarcode.content.bricks.StopCutBrick;
import com.myradev.lunarcode.content.bricks.StopEngraveBrick;
import com.myradev.lunarcode.content.bricks.StopPlotBrick;
import com.myradev.lunarcode.content.bricks.StopRunningStitchBrick;
import com.myradev.lunarcode.content.bricks.StopScriptBrick;
import com.myradev.lunarcode.content.bricks.StopSoundBrick;
import com.myradev.lunarcode.content.bricks.StoreCSVIntoUserListBrick;
import com.myradev.lunarcode.content.bricks.TapAtBrick;
import com.myradev.lunarcode.content.bricks.TapForBrick;
import com.myradev.lunarcode.content.bricks.ThinkBubbleBrick;
import com.myradev.lunarcode.content.bricks.ThinkForBubbleBrick;
import com.myradev.lunarcode.content.bricks.TouchAndSlideBrick;
import com.myradev.lunarcode.content.bricks.TripleStitchBrick;
import com.myradev.lunarcode.content.bricks.TurnLeftBrick;
import com.myradev.lunarcode.content.bricks.TurnLeftSpeedBrick;
import com.myradev.lunarcode.content.bricks.TurnRightBrick;
import com.myradev.lunarcode.content.bricks.TurnRightSpeedBrick;
import com.myradev.lunarcode.content.bricks.VibrationBrick;
import com.myradev.lunarcode.content.bricks.WaitBrick;
import com.myradev.lunarcode.content.bricks.WaitTillIdleBrick;
import com.myradev.lunarcode.content.bricks.WaitUntilBrick;
import com.myradev.lunarcode.content.bricks.WebRequestBrick;
import com.myradev.lunarcode.content.bricks.WhenBackgroundChangesBrick;
import com.myradev.lunarcode.content.bricks.WhenBounceOffBrick;
import com.myradev.lunarcode.content.bricks.WhenBrick;
import com.myradev.lunarcode.content.bricks.WhenClonedBrick;
import com.myradev.lunarcode.content.bricks.WhenConditionBrick;
import com.myradev.lunarcode.content.bricks.WhenGamepadButtonBrick;
import com.myradev.lunarcode.content.bricks.WhenRaspiPinChangedBrick;
import com.myradev.lunarcode.content.bricks.WhenStartedBrick;
import com.myradev.lunarcode.content.bricks.WhenTouchDownBrick;
import com.myradev.lunarcode.content.bricks.WriteEmbroideryToFileBrick;
import com.myradev.lunarcode.content.bricks.WriteListOnDeviceBrick;
import com.myradev.lunarcode.content.bricks.WriteVariableOnDeviceBrick;
import com.myradev.lunarcode.content.bricks.WriteVariableToFileBrick;
import com.myradev.lunarcode.content.bricks.ZigZagStitchBrick;
import com.myradev.lunarcode.ui.fragment.CategoryBricksFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import androidx.test.core.app.ApplicationProvider;

import static android.content.SharedPreferences.Editor;

import static junit.framework.Assert.assertEquals;

import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_SHOW_AI_SPEECH_RECOGNITION_SENSORS;
import static com.myradev.lunarcode.ui.settingsfragments.SettingsFragment.SETTINGS_SHOW_AI_SPEECH_SYNTHETIZATION_SENSORS;

@RunWith(Parameterized.class)
public class BrickCategoryTest {

	private final List<String> speechAISettings = new ArrayList<>(Arrays.asList(
			SETTINGS_SHOW_AI_SPEECH_RECOGNITION_SENSORS,
			SETTINGS_SHOW_AI_SPEECH_SYNTHETIZATION_SENSORS));

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{"Motion", Arrays.asList(PlaceAtBrick.class,
						SetXBrick.class,
						SetYBrick.class,
						ChangeXByNBrick.class,
						ChangeYByNBrick.class,
						GoToBrick.class,
						IfOnEdgeBounceBrick.class,
						MoveNStepsBrick.class,
						TurnLeftBrick.class,
						TurnRightBrick.class,
						PointInDirectionBrick.class,
						PointToBrick.class,
						SetRotationStyleBrick.class,
						GlideToBrick.class,
						GoNStepsBackBrick.class,
						ComeToFrontBrick.class,
						SetCameraFocusPointBrick.class,
						VibrationBrick.class,
						SetPhysicsObjectTypeBrick.class,
						WhenBounceOffBrick.class,
						SetVelocityBrick.class,
						TurnLeftSpeedBrick.class,
						TurnRightSpeedBrick.class,
						SetGravityBrick.class,
						SetMassBrick.class,
						SetBounceBrick.class,
						SetFrictionBrick.class,
						FadeParticleEffectBrick.class)},
				{"Embroidery", Arrays.asList(StitchBrick.class,
						SetThreadColorBrick.class,
						RunningStitchBrick.class,
						ZigZagStitchBrick.class,
						TripleStitchBrick.class,
						SewUpBrick.class,
						StopRunningStitchBrick.class,
						WriteEmbroideryToFileBrick.class,
						EmbroideryArcBrick.class,
						EmbroideryThroughBrick.class)},
				{"Laser Cutter", Arrays.asList(StartEngraveBrick.class,
						StopEngraveBrick.class,
						StartCutBrick.class,
						StopCutBrick.class,
						SaveLaserBrick.class,
						ShareLaserBrick.class,
						LaserArcBrick.class,
						LaserThroughBrick.class)},
				{"Plot", Arrays.asList(StartPlotBrick.class,
						StopPlotBrick.class,
						SavePlotBrick.class,
						SharePlotBrick.class,
						PlotArcBrick.class,
						PlotThroughBrick.class)},
				{"Event", Arrays.asList(WhenStartedBrick.class,
						WhenBrick.class,
						WhenTouchDownBrick.class,
						BroadcastReceiverBrick.class,
						BroadcastBrick.class,
						BroadcastWaitBrick.class,
						WhenConditionBrick.class,
						WhenBounceOffBrick.class,
						WhenBackgroundChangesBrick.class,
						WhenClonedBrick.class,
						CloneBrick.class,
						DeleteThisCloneBrick.class)},
				{"Looks", Arrays.asList(SetLookBrick.class,
						SetLookByIndexBrick.class,
						NextLookBrick.class,
						PreviousLookBrick.class,
						SetSizeToBrick.class,
						ChangeSizeByNBrick.class,
						HideBrick.class,
						ShowBrick.class,
						AskBrick.class,
						SayBubbleBrick.class,
						SayForBubbleBrick.class,
						ThinkBubbleBrick.class,
						ThinkForBubbleBrick.class,
						ShowTextBrick.class,
						ShowTextColorSizeAlignmentBrick.class,
						SetTransparencyBrick.class,
						ChangeTransparencyByNBrick.class,
						SetBrightnessBrick.class,
						ChangeBrightnessByNBrick.class,
						SetColorBrick.class,
						ChangeColorByNBrick.class,
						FadeParticleEffectBrick.class,
						ParticleEffectAdditivityBrick.class,
						SetParticleColorBrick.class,
						ClearGraphicEffectBrick.class,
						SetCameraFocusPointBrick.class,
						WhenBackgroundChangesBrick.class,
						SetBackgroundBrick.class,
						SetBackgroundByIndexBrick.class,
						SetBackgroundAndWaitBrick.class,
						SetBackgroundByIndexAndWaitBrick.class,
						CameraBrick.class,
						ChooseCameraBrick.class,
						FlashBrick.class,
						LookRequestBrick.class,
						PaintNewLookBrick.class,
						EditLookBrick.class,
						CopyLookBrick.class,
						DeleteLookBrick.class,
						OpenUrlBrick.class)},
				{"Pen", Arrays.asList(PenDownBrick.class,
						PenUpBrick.class,
						SetPenSizeBrick.class,
						SetPenColorBrick.class,
						StampBrick.class,
						ClearBackgroundBrick.class)},
				{"Sound", Arrays.asList(PlaySoundBrick.class,
						PlaySoundAndWaitBrick.class,
						PlaySoundAtBrick.class,
						StopSoundBrick.class,
						StopAllSoundsBrick.class,
						SetVolumeToBrick.class,
						ChangeVolumeByNBrick.class,
						SpeakBrick.class,
						SpeakAndWaitBrick.class,
						AskSpeechBrick.class,
						StartListeningBrick.class,
						SetListeningLanguageBrick.class,
						SetInstrumentBrick.class,
						PlayNoteForBeatsBrick.class,
						PlayDrumForBeatsBrick.class,
						SetTempoBrick.class,
						ChangeTempoByNBrick.class,
						PauseForBeatsBrick.class)},
				{"Control", Arrays.asList(WaitBrick.class,
						NoteBrick.class,
						ForeverBrick.class,
						IfLogicBeginBrick.class,
						IfThenLogicBeginBrick.class,
						WaitUntilBrick.class,
						RepeatBrick.class,
						RepeatUntilBrick.class,
						ForVariableFromToBrick.class,
						ForItemInUserListBrick.class,
						SceneTransitionBrick.class,
						SceneStartBrick.class,
						ExitStageBrick.class,
						StopScriptBrick.class,
						WaitTillIdleBrick.class,
						WhenClonedBrick.class,
						CloneBrick.class,
						DeleteThisCloneBrick.class,
						BroadcastReceiverBrick.class,
						BroadcastBrick.class,
						BroadcastWaitBrick.class,
						TapAtBrick.class,
						TapForBrick.class,
						TouchAndSlideBrick.class,
						OpenUrlBrick.class)},
				{"Data", Arrays.asList(SetVariableBrick.class,
						ChangeVariableBrick.class,
						ShowTextBrick.class,
						ShowTextColorSizeAlignmentBrick.class,
						HideTextBrick.class,
						WriteVariableOnDeviceBrick.class,
						ReadVariableFromDeviceBrick.class,
						WriteVariableToFileBrick.class,
						ReadVariableFromFileBrick.class,
						AddItemToUserListBrick.class,
						DeleteItemOfUserListBrick.class,
						ClearUserListBrick.class,
						InsertItemIntoUserListBrick.class,
						ReplaceItemInUserListBrick.class,
						WriteListOnDeviceBrick.class,
						ReadListFromDeviceBrick.class,
						StoreCSVIntoUserListBrick.class,
						WebRequestBrick.class,
						LookRequestBrick.class,
						AskBrick.class,
						AskSpeechBrick.class,
						StartListeningBrick.class)},
				{"Device", Arrays.asList(ResetTimerBrick.class,
						WhenBrick.class,
						WhenTouchDownBrick.class,
						WebRequestBrick.class,
						LookRequestBrick.class,
						OpenUrlBrick.class,
						VibrationBrick.class,
						SpeakBrick.class,
						SpeakAndWaitBrick.class,
						AskSpeechBrick.class,
						StartListeningBrick.class,
						CameraBrick.class,
						ChooseCameraBrick.class,
						FlashBrick.class,
						WriteVariableOnDeviceBrick.class,
						ReadVariableFromDeviceBrick.class,
						WriteVariableToFileBrick.class,
						ReadVariableFromFileBrick.class,
						WriteListOnDeviceBrick.class,
						ReadListFromDeviceBrick.class,
						TapAtBrick.class,
						TapForBrick.class,
						TouchAndSlideBrick.class)
				},
				{"Lego NXT", Arrays.asList(LegoNxtMotorTurnAngleBrick.class,
						LegoNxtMotorStopBrick.class,
						LegoNxtMotorMoveBrick.class,
						LegoNxtPlayToneBrick.class)},
				{"Lego EV3", Arrays.asList(LegoEv3MotorTurnAngleBrick.class,
						LegoEv3MotorMoveBrick.class,
						LegoEv3MotorStopBrick.class,
						LegoEv3PlayToneBrick.class,
						LegoEv3SetLedBrick.class)},
				{"AR.Drone 2.0", Arrays.asList(DroneTakeOffLandBrick.class,
						DroneEmergencyBrick.class,
						DroneMoveUpBrick.class,
						DroneMoveDownBrick.class,
						DroneMoveLeftBrick.class,
						DroneMoveRightBrick.class,
						DroneMoveForwardBrick.class,
						DroneMoveBackwardBrick.class,
						DroneTurnLeftBrick.class,
						DroneTurnRightBrick.class,
						DroneFlipBrick.class,
						DronePlayLedAnimationBrick.class,
						DroneSwitchCameraBrick.class)},
				{"Jumping Sumo", Arrays.asList(JumpingSumoMoveForwardBrick.class,
						JumpingSumoMoveBackwardBrick.class,
						JumpingSumoAnimationsBrick.class,
						JumpingSumoSoundBrick.class,
						JumpingSumoNoSoundBrick.class,
						JumpingSumoJumpLongBrick.class,
						JumpingSumoJumpHighBrick.class,
						JumpingSumoRotateLeftBrick.class,
						JumpingSumoRotateRightBrick.class,
						JumpingSumoTurnBrick.class,
						JumpingSumoTakingPictureBrick.class)},
				{"Phiro", Arrays.asList(PhiroMotorMoveForwardBrick.class,
						PhiroMotorMoveBackwardBrick.class,
						PhiroMotorStopBrick.class,
						PhiroPlayToneBrick.class,
						PhiroRGBLightBrick.class,
						PhiroIfLogicBeginBrick.class,
						SetVariableBrick.class,
						SetVariableBrick.class,
						SetVariableBrick.class,
						SetVariableBrick.class,
						SetVariableBrick.class,
						SetVariableBrick.class)},
				{"Arduino", Arrays.asList(ArduinoSendDigitalValueBrick.class,
						ArduinoSendPWMValueBrick.class)},
				{"Chromecast", Arrays.asList(WhenGamepadButtonBrick.class)},
				{"Raspberry Pi", Arrays.asList(WhenRaspiPinChangedBrick.class,
						RaspiIfLogicBeginBrick.class,
						RaspiSendDigitalValueBrick.class,
						RaspiPwmBrick.class)},
				{"Testing", Arrays.asList(AssertEqualsBrick.class,
						AssertUserListsBrick.class,
						ParameterizedBrick.class,
						WaitTillIdleBrick.class,
						TapAtBrick.class,
						TapForBrick.class,
						TouchAndSlideBrick.class,
						FinishStageBrick.class,
						StoreCSVIntoUserListBrick.class,
						WebRequestBrick.class)},
		});
	}

	@Parameterized.Parameter
	public String category;

	@Parameterized.Parameter(1)
	public List<Class> expectedClasses;

	private CategoryBricksFactory categoryBricksFactory;

	@Before
	public void setUp() throws Exception {
		Editor sharedPreferencesEditor = PreferenceManager
				.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext()).edit();

		sharedPreferencesEditor.clear();
		// The speech AI settings have to be activated here, because these bricks have no own
		// brick category.
		for (String setting : speechAISettings) {
			sharedPreferencesEditor.putBoolean(setting, true);
		}
		sharedPreferencesEditor.commit();

		createProject(ApplicationProvider.getApplicationContext());
		categoryBricksFactory = new CategoryBricksFactory();
	}

	@After
	public void tearDown() {
		Editor sharedPreferencesEditor = PreferenceManager
				.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext()).edit();
		sharedPreferencesEditor.clear().commit();
	}

	public void createProject(Context context) {
		Project project = new Project(context, getClass().getSimpleName());
		Sprite sprite = new Sprite("testSprite");
		Script script = new StartScript();
		script.addBrick(new SetXBrick());
		sprite.addScript(script);
		project.getDefaultScene().addSprite(sprite);
		ProjectManager.getInstance().setCurrentProject(project);
		ProjectManager.getInstance().setCurrentSprite(sprite);
		ProjectManager.getInstance().setCurrentlyEditedScene(project.getDefaultScene());
	}

	@Test
	public void testBrickCategory() {
		List<Brick> categoryBricks = categoryBricksFactory.getBricks(category, false,
				ApplicationProvider.getApplicationContext());

		List<Class> brickClasses = new ArrayList<>();
		for (Brick brick : categoryBricks) {
			brickClasses.add(brick.getClass());
		}

		assertEquals(expectedClasses, brickClasses);
	}
}
