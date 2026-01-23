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
package com.myradev.lunarcode.test;

import android.util.Log;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.bricks.Brick;
import com.myradev.lunarcode.ui.fragment.CategoryBricksFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.test.platform.app.InstrumentationRegistry;
import dalvik.system.DexFile;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BricksHelpUrlTest {
	public static final String TAG = BricksHelpUrlTest.class.getSimpleName();
	public static Map<String, String> brickToHelpUrlMapping;

	static {
		brickToHelpUrlMapping = new HashMap<>();
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.JumpingSumoMoveBackwardBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/JumpingSumoMoveBackwardBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ChangeSizeByNBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ChangeSizeByNBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.JumpingSumoTakingPictureBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/JumpingSumoTakingPictureBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.NoteBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/NoteBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.InsertItemIntoUserListBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/InsertItemIntoUserListBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DroneFlipBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DroneFlipBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PhiroMotorMoveBackwardBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PhiroMotorMoveBackwardBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SayForBubbleBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SayForBubbleBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DroneEmergencyBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DroneEmergencyBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DroneMoveRightBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DroneMoveRightBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.IfLogicBeginBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/IfLogicBeginBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.NextLookBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/NextLookBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetBackgroundByIndexAndWaitBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetBackgroundByIndexAndWaitBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ShowBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ShowBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SpeakAndWaitBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SpeakAndWaitBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.HideTextBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/HideTextBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DroneSwitchCameraBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DroneSwitchCameraBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetFrictionBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetFrictionBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DroneTurnLeftBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DroneTurnLeftBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PlaySoundAndWaitBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PlaySoundAndWaitBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PlaySoundAtBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PlaySoundAtBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetBackgroundBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetBackgroundBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PhiroPlayToneBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PhiroPlayToneBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetPhysicsObjectTypeBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetPhysicsObjectTypeBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.JumpingSumoTurnBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/JumpingSumoTurnBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.RepeatBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/RepeatBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ForVariableFromToBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ForVariableFromToBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ForItemInUserListBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ForItemInUserListBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SayBubbleBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SayBubbleBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetBrightnessBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetBrightnessBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WebRequestBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WebRequestBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.LookRequestBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/LookRequestBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.BackgroundRequestBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/BackgroundRequestBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.LegoEv3MotorMoveBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/LegoEv3MotorMoveBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.HideBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/HideBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ChangeYByNBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ChangeYByNBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.IfOnEdgeBounceBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/IfOnEdgeBounceBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WhenNfcBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WhenNfcBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.BroadcastWaitBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/BroadcastWaitBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PenUpBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PenUpBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetLookBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetLookBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.CameraBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/CameraBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.IfThenLogicBeginBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/IfThenLogicBeginBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WhenBounceOffBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WhenBounceOffBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SceneStartBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SceneStartBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.LegoEv3MotorStopBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/LegoEv3MotorStopBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.LegoNxtMotorMoveBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/LegoNxtMotorMoveBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.RepeatUntilBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/RepeatUntilBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PhiroMotorMoveForwardBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PhiroMotorMoveForwardBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.IfLogicElseBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/IfLogicElseBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.JumpingSumoNoSoundBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/JumpingSumoNoSoundBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.RaspiIfLogicBeginBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/RaspiIfLogicBeginBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ChangeXByNBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ChangeXByNBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ChangeColorByNBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ChangeColorByNBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetThreadColorBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetThreadColorBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.TurnLeftSpeedBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/TurnLeftSpeedBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.LegoEv3PlayToneBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/LegoEv3PlayToneBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.TurnRightBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/TurnRightBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.CloneBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/CloneBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.TurnLeftBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/TurnLeftBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ClearGraphicEffectBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ClearGraphicEffectBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.BroadcastBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/BroadcastBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.FlashBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/FlashBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.StopSoundBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/StopSoundBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.StopAllSoundsBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/StopAllSoundsBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WriteListOnDeviceBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WriteListOnDeviceBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.JumpingSumoSoundBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/JumpingSumoSoundBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PreviousLookBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PreviousLookBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ComeToFrontBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ComeToFrontBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ReadListFromDeviceBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ReadListFromDeviceBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PenDownBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PenDownBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DroneMoveLeftBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DroneMoveLeftBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetSizeToBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetSizeToBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WaitUntilBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WaitUntilBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ArduinoSendDigitalValueBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ArduinoSendDigitalValueBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ChangeVolumeByNBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ChangeVolumeByNBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.JumpingSumoJumpLongBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/JumpingSumoJumpLongBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DroneMoveBackwardBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DroneMoveBackwardBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.StampBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/StampBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PhiroIfLogicBeginBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PhiroIfLogicBeginBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.RaspiPwmBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/RaspiPwmBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PlaceAtBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PlaceAtBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.StopScriptBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/StopScriptBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ArduinoSendPWMValueBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ArduinoSendPWMValueBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DronePlayLedAnimationBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DronePlayLedAnimationBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PointToBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PointToBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetXBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetXBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WhenConditionBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WhenConditionBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.RaspiSendDigitalValueBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/RaspiSendDigitalValueBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.BroadcastReceiverBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/BroadcastReceiverBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SpeakBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SpeakBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.IfLogicEndBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/IfLogicEndBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.JumpingSumoMoveForwardBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/JumpingSumoMoveForwardBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WhenClonedBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WhenClonedBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DroneMoveDownBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DroneMoveDownBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ClearBackgroundBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ClearBackgroundBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.AssertEqualsBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/AssertEqualsBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PhiroMotorStopBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PhiroMotorStopBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.TapAtBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/TapAtBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.JumpingSumoJumpHighBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/JumpingSumoJumpHighBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.FinishStageBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/FinishStageBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.JumpingSumoRotateRightBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/JumpingSumoRotateRightBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.StitchBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/StitchBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetPenSizeBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetPenSizeBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ChangeBrightnessByNBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ChangeBrightnessByNBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.LegoEv3SetLedBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/LegoEv3SetLedBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetVolumeToBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetVolumeToBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PointInDirectionBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PointInDirectionBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SceneTransitionBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SceneTransitionBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.JumpingSumoRotateLeftBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/JumpingSumoRotateLeftBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetVariableBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetVariableBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.GoNStepsBackBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/GoNStepsBackBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ThinkForBubbleBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ThinkForBubbleBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetLookByIndexBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetLookByIndexBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.IfThenLogicEndBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/IfThenLogicEndBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetTextBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetTextBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetTransparencyBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetTransparencyBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ForeverBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ForeverBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ChangeTransparencyByNBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ChangeTransparencyByNBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetColorBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetColorBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetRotationStyleBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetRotationStyleBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetBackgroundAndWaitBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetBackgroundAndWaitBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WhenBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WhenBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WhenStartedBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WhenStartedBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DeleteItemOfUserListBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DeleteItemOfUserListBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ClearUserListBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ClearUserListBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.GoToBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/GoToBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.LegoNxtMotorStopBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/LegoNxtMotorStopBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WhenTouchDownBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WhenTouchDownBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WaitBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WaitBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetGravityBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetGravityBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetMassBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetMassBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ReadVariableFromDeviceBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ReadVariableFromDeviceBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.GlideToBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/GlideToBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.LegoEv3MotorTurnAngleBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/LegoEv3MotorTurnAngleBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WhenBackgroundChangesBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WhenBackgroundChangesBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.LegoNxtMotorTurnAngleBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/LegoNxtMotorTurnAngleBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WhenRaspiPinChangedBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WhenRaspiPinChangedBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetBackgroundByIndexBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetBackgroundByIndexBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DroneTakeOffLandBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DroneTakeOffLandBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.LegoNxtPlayToneBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/LegoNxtPlayToneBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WaitTillIdleBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WaitTillIdleBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DeleteThisCloneBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DeleteThisCloneBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetNfcTagBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetNfcTagBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PhiroRGBLightBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PhiroRGBLightBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.AddItemToUserListBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/AddItemToUserListBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DroneTurnRightBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DroneTurnRightBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WhenGamepadButtonBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WhenGamepadButtonBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.AskSpeechBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/AskSpeechBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ReplaceItemInUserListBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ReplaceItemInUserListBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ChooseCameraBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ChooseCameraBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetVelocityBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetVelocityBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DroneMoveForwardBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DroneMoveForwardBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DroneMoveUpBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DroneMoveUpBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.TurnRightSpeedBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/TurnRightSpeedBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetBounceBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetBounceBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ShowTextBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ShowTextBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.VibrationBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/VibrationBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WriteVariableOnDeviceBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WriteVariableOnDeviceBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WriteVariableToFileBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WriteVariableToFileBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ReadVariableFromFileBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ReadVariableFromFileBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.JumpingSumoAnimationsBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/JumpingSumoAnimationsBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetPenColorBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetPenColorBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.LoopEndlessBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/LoopEndlessBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PlaySoundBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PlaySoundBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetInstrumentBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetInstrumentBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetTempoBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetTempoBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ChangeTempoByNBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ChangeTempoByNBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PlayDrumForBeatsBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PlayDrumForBeatsBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ThinkBubbleBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ThinkBubbleBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.AskBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/AskBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.LoopEndBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/LoopEndBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetYBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetYBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ChangeVariableBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ChangeVariableBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.MoveNStepsBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/MoveNStepsBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ShowTextColorSizeAlignmentBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ShowTextColorSizeAlignmentBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.RunningStitchBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/RunningStitchBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.StopRunningStitchBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/StopRunningStitchBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ZigZagStitchBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ZigZagStitchBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.TripleStitchBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/TripleStitchBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SewUpBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SewUpBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.WriteEmbroideryToFileBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/WriteEmbroideryToFileBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.UserDefinedBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/UserDefinedBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.UserDefinedReceiverBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/UserDefinedReceiverBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ReportBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ReportBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.StoreCSVIntoUserListBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/StoreCSVIntoUserListBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.AssertUserListsBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/AssertUserListsBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ExitStageBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ExitStageBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ParameterizedBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ParameterizedBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ParameterizedEndBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ParameterizedEndBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.TapForBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/TapForBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.TouchAndSlideBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/TouchAndSlideBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.StartListeningBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/StartListeningBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetListeningLanguageBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetListeningLanguageBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PauseForBeatsBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PauseForBeatsBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.DeleteLookBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/DeleteLookBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ResetTimerBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ResetTimerBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PaintNewLookBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PaintNewLookBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PlayNoteForBeatsBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PlayNoteForBeatsBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.OpenUrlBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/OpenUrlBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.CopyLookBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/CopyLookBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.EditLookBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/EditLookBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.EmptyEventBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/EmptyEventBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.FadeParticleEffectBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/FadeParticleEffectBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ParticleEffectAdditivityBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ParticleEffectAdditivityBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetCameraFocusPointBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetCameraFocusPointBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SetParticleColorBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SetParticleColorBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.StartCutBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/StartCutBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.StopCutBrick", "https://wiki"
				+ ".catrobat.org/bin/view/Documentation/BrickDocumentation/StopCutBrick");
		brickToHelpUrlMapping.put(".catrobat.org/bin/view/Documentation/BrickDocumentation"
				+ "/StartCutBrick", "http://wiki.catrobat"
				+ ".org/bin/view/Documentation/BrickDocumentation/StartCutBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SaveLaserBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SaveLaserBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.StopEngraveBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/StopEngraveBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.StartEngraveBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation"
						+ "/StartEngraveBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SavePlotBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SavePlotBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.StartPlotBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/StartPlotBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.StopPlotBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/StopPlotBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.LaserArcBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/LaserArcBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.LaserThroughBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/LaserThroughBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.ShareLaserBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/ShareLaserBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.EmbroideryArcBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/EmbroideryArcBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PlotArcBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PlotArcBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.SharePlotBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/SharePlotBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.PlotThroughBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/PlotThroughBrick");
		brickToHelpUrlMapping.put("com.myradev.lunarcode.content.bricks.EmbroideryThroughBrick",
				"https://wiki.catrobat.org/bin/view/Documentation/BrickDocumentation/EmbroideryThroughBrick");

	}
	@Parameterized.Parameters(name = "{0}")
	public static Iterable<Object[]> data() {
		List<Object[]> parameters = new ArrayList<>();
		Set<Class> brickClasses = getAllBrickClasses();

		brickClasses = removeAbstractClasses(brickClasses);
		brickClasses = removeInnerClasses(brickClasses);
		brickClasses = removeEndBrick(brickClasses);
		for (Class<?> brickClazz : brickClasses) {
			parameters.add(new Object[] {brickClazz.getName(), brickClazz});
		}

		return parameters;
	}

	@Parameterized.Parameter
	public String simpleName;

	@Parameterized.Parameter(1)
	public Class brickClass;

	private static Set<Class> getAllBrickClasses() {
		ArrayList<Class> classes = new ArrayList<>();
		try {
			String packageCodePath =
					InstrumentationRegistry.getInstrumentation().getTargetContext().getPackageCodePath();
			DexFile dexFile = new DexFile(packageCodePath);
			for (Enumeration<String> iter = dexFile.entries(); iter.hasMoreElements(); ) {
				String className = iter.nextElement();
				if (className.contains("com.myradev.lunarcode.content.bricks") && className.endsWith(
						"Brick")) {
					classes.add(Class.forName(className));
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}

		return new HashSet<>(classes);
	}

	@Before
	public void setUp() {
		ProjectManager.getInstance().setCurrentProject(
				new Project(InstrumentationRegistry.getInstrumentation().getTargetContext(), "empty"));
	}

	@Test
	public void testBrickHelpUrl() throws IllegalAccessException,
			InstantiationException {
		Brick brick = (Brick) brickClass.newInstance();
		String category = new CategoryBricksFactory().getBrickCategory(brick, false,
				InstrumentationRegistry.getInstrumentation().getTargetContext());
		String brickHelpUrl = brick.getHelpUrl(category);
		assertEquals(brickToHelpUrlMapping.get(simpleName), brickHelpUrl);
	}

	private static Set<Class> removeAbstractClasses(Set<Class> classes) {
		Set<Class> filtered = new HashSet<>();

		for (Class clazz : classes) {
			boolean isAbstract = Modifier.isAbstract(clazz.getModifiers());
			if (!isAbstract) {
				filtered.add(clazz);
			}
		}
		return filtered;
	}

	private static Set<Class> removeInnerClasses(Set<Class> classes) {
		Set<Class> filtered = new HashSet<>();

		for (Class clazz : classes) {
			boolean isInnerClass = clazz.getEnclosingClass() != null;
			if (!isInnerClass) {
				filtered.add(clazz);
			}
		}
		return filtered;
	}

	private static Set<Class> removeEndBrick(Set<Class> classes) {
		Set<Class> filtered = new HashSet<>();

		for (Class clazz : classes) {
			if (!clazz.getName().contains("EndBrick")) {
				filtered.add(clazz);
			}
		}
		return filtered;
	}
}
