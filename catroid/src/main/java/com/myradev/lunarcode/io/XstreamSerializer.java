/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2025  The Catrobat Team
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
package com.myradev.lunarcode.io;

import android.content.Context;
import android.util.Log;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;

import com.myradev.lunarcode.BuildConfig;
import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.common.LookData;
import com.myradev.lunarcode.common.NfcTagData;
import com.myradev.lunarcode.common.ProjectData;
import com.myradev.lunarcode.common.SoundInfo;
import com.myradev.lunarcode.content.BroadcastScript;
import com.myradev.lunarcode.content.EmptyScript;
import com.myradev.lunarcode.content.GroupItemSprite;
import com.myradev.lunarcode.content.GroupSprite;
import com.myradev.lunarcode.content.LegoEV3Setting;
import com.myradev.lunarcode.content.LegoNXTSetting;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.RaspiInterruptScript;
import com.myradev.lunarcode.content.Scene;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Setting;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.UserDefinedScript;
import com.myradev.lunarcode.content.WhenBackgroundChangesScript;
import com.myradev.lunarcode.content.WhenBounceOffScript;
import com.myradev.lunarcode.content.WhenClonedScript;
import com.myradev.lunarcode.content.WhenConditionScript;
import com.myradev.lunarcode.content.WhenGamepadButtonScript;
import com.myradev.lunarcode.content.WhenNfcScript;
import com.myradev.lunarcode.content.WhenScript;
import com.myradev.lunarcode.content.WhenTouchDownScript;
import com.myradev.lunarcode.content.XmlHeader;
import com.myradev.lunarcode.content.backwardcompatibility.LegacyDataContainer;
import com.myradev.lunarcode.content.backwardcompatibility.LegacyProjectWithoutScenes;
import com.myradev.lunarcode.content.backwardcompatibility.ProjectMetaDataParser;
import com.myradev.lunarcode.content.backwardcompatibility.ProjectUntilLanguageVersion0999;
import com.myradev.lunarcode.content.backwardcompatibility.SceneUntilLanguageVersion0999;
import com.myradev.lunarcode.content.bricks.AddItemToUserListBrick;
import com.myradev.lunarcode.content.bricks.ArduinoSendDigitalValueBrick;
import com.myradev.lunarcode.content.bricks.ArduinoSendPWMValueBrick;
import com.myradev.lunarcode.content.bricks.AskBrick;
import com.myradev.lunarcode.content.bricks.AskSpeechBrick;
import com.myradev.lunarcode.content.bricks.AssertEqualsBrick;
import com.myradev.lunarcode.content.bricks.AssertUserListsBrick;
import com.myradev.lunarcode.content.bricks.BackgroundRequestBrick;
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
import com.myradev.lunarcode.content.bricks.EmptyEventBrick;
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
import com.myradev.lunarcode.content.bricks.IfLogicElseBrick;
import com.myradev.lunarcode.content.bricks.IfLogicEndBrick;
import com.myradev.lunarcode.content.bricks.IfOnEdgeBounceBrick;
import com.myradev.lunarcode.content.bricks.IfThenLogicBeginBrick;
import com.myradev.lunarcode.content.bricks.IfThenLogicEndBrick;
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
import com.myradev.lunarcode.content.bricks.LookRequestBrick;
import com.myradev.lunarcode.content.bricks.LoopEndBrick;
import com.myradev.lunarcode.content.bricks.LoopEndlessBrick;
import com.myradev.lunarcode.content.bricks.MoveNStepsBrick;
import com.myradev.lunarcode.content.bricks.NextLookBrick;
import com.myradev.lunarcode.content.bricks.NoteBrick;
import com.myradev.lunarcode.content.bricks.OpenUrlBrick;
import com.myradev.lunarcode.content.bricks.PaintNewLookBrick;
import com.myradev.lunarcode.content.bricks.ParameterizedBrick;
import com.myradev.lunarcode.content.bricks.ParameterizedEndBrick;
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
import com.myradev.lunarcode.content.bricks.ReportBrick;
import com.myradev.lunarcode.content.bricks.ResetTimerBrick;
import com.myradev.lunarcode.content.bricks.RunningStitchBrick;
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
import com.myradev.lunarcode.content.bricks.SetNfcTagBrick;
import com.myradev.lunarcode.content.bricks.SetParticleColorBrick;
import com.myradev.lunarcode.content.bricks.SetPenColorBrick;
import com.myradev.lunarcode.content.bricks.SetPenSizeBrick;
import com.myradev.lunarcode.content.bricks.SetPhysicsObjectTypeBrick;
import com.myradev.lunarcode.content.bricks.SetRotationStyleBrick;
import com.myradev.lunarcode.content.bricks.SetSizeToBrick;
import com.myradev.lunarcode.content.bricks.SetTempoBrick;
import com.myradev.lunarcode.content.bricks.SetTextBrick;
import com.myradev.lunarcode.content.bricks.SetThreadColorBrick;
import com.myradev.lunarcode.content.bricks.SetTransparencyBrick;
import com.myradev.lunarcode.content.bricks.SetVariableBrick;
import com.myradev.lunarcode.content.bricks.SetVelocityBrick;
import com.myradev.lunarcode.content.bricks.SetVolumeToBrick;
import com.myradev.lunarcode.content.bricks.SetXBrick;
import com.myradev.lunarcode.content.bricks.SetYBrick;
import com.myradev.lunarcode.content.bricks.SewUpBrick;
import com.myradev.lunarcode.content.bricks.ShowBrick;
import com.myradev.lunarcode.content.bricks.ShowTextBrick;
import com.myradev.lunarcode.content.bricks.ShowTextColorSizeAlignmentBrick;
import com.myradev.lunarcode.content.bricks.SpeakAndWaitBrick;
import com.myradev.lunarcode.content.bricks.SpeakBrick;
import com.myradev.lunarcode.content.bricks.StampBrick;
import com.myradev.lunarcode.content.bricks.StartListeningBrick;
import com.myradev.lunarcode.content.bricks.StitchBrick;
import com.myradev.lunarcode.content.bricks.StopAllSoundsBrick;
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
import com.myradev.lunarcode.content.bricks.UserDefinedBrick;
import com.myradev.lunarcode.content.bricks.UserDefinedReceiverBrick;
import com.myradev.lunarcode.content.bricks.UserListBrick;
import com.myradev.lunarcode.content.bricks.UserVariableBrickWithFormula;
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
import com.myradev.lunarcode.content.bricks.WhenNfcBrick;
import com.myradev.lunarcode.content.bricks.WhenRaspiPinChangedBrick;
import com.myradev.lunarcode.content.bricks.WhenStartedBrick;
import com.myradev.lunarcode.content.bricks.WhenTouchDownBrick;
import com.myradev.lunarcode.content.bricks.WriteEmbroideryToFileBrick;
import com.myradev.lunarcode.content.bricks.WriteListOnDeviceBrick;
import com.myradev.lunarcode.content.bricks.WriteVariableOnDeviceBrick;
import com.myradev.lunarcode.content.bricks.WriteVariableToFileBrick;
import com.myradev.lunarcode.content.bricks.ZigZagStitchBrick;
import com.myradev.lunarcode.exceptions.LoadingProjectException;
import com.myradev.lunarcode.formulaeditor.UserList;
import com.myradev.lunarcode.formulaeditor.UserVariable;
import com.myradev.lunarcode.userbrick.UserDefinedBrickData;
import com.myradev.lunarcode.userbrick.UserDefinedBrickInput;
import com.myradev.lunarcode.userbrick.UserDefinedBrickLabel;
import com.myradev.lunarcode.utils.StringFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.VisibleForTesting;

import static com.myradev.lunarcode.common.Constants.CODE_XML_FILE_NAME;
import static com.myradev.lunarcode.common.Constants.IMAGE_DIRECTORY_NAME;
import static com.myradev.lunarcode.common.Constants.SOUND_DIRECTORY_NAME;
import static com.myradev.lunarcode.common.Constants.TMP_CODE_XML_FILE_NAME;
import static com.myradev.lunarcode.common.FlavoredConstants.DEFAULT_ROOT_DIRECTORY;

public final class XstreamSerializer {

	private static XstreamSerializer instance;
	private static final String TAG = XstreamSerializer.class.getSimpleName();
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n";
	private static final String PROGRAM_NAME_START_TAG = "<programName>";
	private static final String PROGRAM_NAME_END_TAG = "</programName>";

	private BackwardCompatibleCatrobatLanguageXStream xstream;
	private Lock loadSaveLock = new ReentrantLock();

	private XstreamSerializer() {
		prepareXstream(Project.class, Scene.class);
	}

	public static XstreamSerializer getInstance() {
		if (instance == null) {
			instance = new XstreamSerializer();
		}
		return instance;
	}

	private void prepareXstream(Class projectClass, Class sceneClass) {
		xstream = new BackwardCompatibleCatrobatLanguageXStream(
				new PureJavaReflectionProvider(new FieldDictionary(new CatroidFieldKeySorter())));

		xstream.allowTypesByWildcard(new String[] {"com.myradev.lunarcode.**"});

		xstream.processAnnotations(projectClass);
		xstream.processAnnotations(sceneClass);

		xstream.processAnnotations(Sprite.class);
		xstream.processAnnotations(XmlHeader.class);
		xstream.processAnnotations(Setting.class);
		xstream.processAnnotations(UserVariableBrickWithFormula.class);
		xstream.processAnnotations(UserListBrick.class);
		xstream.processAnnotations(UserDefinedBrickData.class);
		xstream.processAnnotations(UserDefinedBrickInput.class);
		xstream.processAnnotations(UserDefinedBrickLabel.class);

		xstream.registerConverter(new XStreamConcurrentFormulaHashMapConverter());
		xstream.registerConverter(new XStreamUserDataHashMapConverter());
		xstream.registerConverter(new XStreamUserVariableConverter(xstream.getMapper(), xstream.getReflectionProvider(),
				xstream.getClassLoaderReference()));
		xstream.registerConverter(new XStreamFormulaElementConverter(xstream.getMapper(), xstream.getReflectionProvider()));

		xstream.registerConverter(new XStreamBrickConverter(xstream.getMapper(), xstream.getReflectionProvider()));
		xstream.registerConverter(new XStreamScriptConverter(xstream.getMapper(), xstream.getReflectionProvider()));
		xstream.registerConverter(new XStreamSpriteConverter(xstream.getMapper(), xstream.getReflectionProvider()));
		xstream.registerConverter(new XStreamSettingConverter(xstream.getMapper(), xstream.getReflectionProvider()));

		xstream.omitField(sceneClass, "originalWidth");
		xstream.omitField(sceneClass, "originalHeight");

		xstream.omitField(Sprite.class, "userBricks");

		xstream.omitField(LegacyDataContainer.class, "userBrickVariableList");

		xstream.omitField(CameraBrick.class, "spinnerValues");
		xstream.omitField(ChooseCameraBrick.class, "spinnerValues");
		xstream.omitField(FlashBrick.class, "spinnerValues");

		xstream.omitField(SetNfcTagBrick.class, "nfcTagNdefDefaultType");

		xstream.omitField(SpeakAndWaitBrick.class, "speechFile");
		xstream.omitField(SpeakAndWaitBrick.class, "duration");

		xstream.omitField(StopScriptBrick.class, "spinnerValue");

		xstream.omitField(ShowTextBrick.class, "userVariableName");
		xstream.omitField(HideTextBrick.class, "userVariableName");
		xstream.omitField(HideTextBrick.class, "formulaList");
		xstream.omitField(HideTextBrick.class, "userDataList");

		xstream.omitField(SayBubbleBrick.class, "type");
		xstream.omitField(SayBubbleBrick.class, "type");

		xstream.omitField(ThinkBubbleBrick.class, "type");
		xstream.omitField(ThinkForBubbleBrick.class, "type");

		xstream.omitField(StartScript.class, "isUserScript");
		xstream.omitField(WhenScript.class, "action");

		xstream.omitField(RaspiInterruptScript.class, "receivedMessage");

		xstream.omitField(FadeParticleEffectBrick.class, "formulaList");
		xstream.omitField(ParticleEffectAdditivityBrick.class, "formulaList");
		xstream.omitField(SetParticleColorBrick.class, "formulaList");

		xstream.alias("look", LookData.class);
		xstream.alias("sound", SoundInfo.class);
		xstream.alias("nfcTag", NfcTagData.class);
		xstream.alias("userVariable", UserVariable.class);
		xstream.alias("userList", UserList.class);

		xstream.alias("script", Script.class);
		xstream.alias("object", Sprite.class);
		xstream.alias("object", GroupSprite.class);
		xstream.alias("object", GroupItemSprite.class);

		xstream.alias("script", StartScript.class);
		xstream.alias("script", WhenClonedScript.class);
		xstream.alias("script", WhenScript.class);
		xstream.alias("script", WhenConditionScript.class);
		xstream.alias("script", WhenNfcScript.class);
		xstream.alias("script", BroadcastScript.class);
		xstream.alias("script", RaspiInterruptScript.class);
		xstream.alias("script", WhenTouchDownScript.class);
		xstream.alias("script", WhenBackgroundChangesScript.class);
		xstream.alias("script", UserDefinedScript.class);
		xstream.alias("script", EmptyScript.class);

		xstream.alias("brick", AddItemToUserListBrick.class);
		xstream.alias("brick", AskBrick.class);
		xstream.alias("brick", AskSpeechBrick.class);
		xstream.alias("brick", BroadcastBrick.class);
		xstream.alias("brick", BroadcastReceiverBrick.class);
		xstream.alias("brick", BroadcastWaitBrick.class);
		xstream.alias("brick", ChangeBrightnessByNBrick.class);
		xstream.alias("brick", ChangeColorByNBrick.class);
		xstream.alias("brick", ChangeTransparencyByNBrick.class);
		xstream.alias("brick", ChangeSizeByNBrick.class);
		xstream.alias("brick", ChangeVariableBrick.class);
		xstream.alias("brick", ChangeTempoByNBrick.class);
		xstream.alias("brick", ChangeVolumeByNBrick.class);
		xstream.alias("brick", ChangeXByNBrick.class);
		xstream.alias("brick", ChangeYByNBrick.class);
		xstream.alias("brick", ClearBackgroundBrick.class);
		xstream.alias("brick", ClearGraphicEffectBrick.class);
		xstream.alias("brick", ClearUserListBrick.class);
		xstream.alias("brick", CloneBrick.class);
		xstream.alias("brick", ComeToFrontBrick.class);
		xstream.alias("brick", DeleteItemOfUserListBrick.class);
		xstream.alias("brick", DeleteThisCloneBrick.class);
		xstream.alias("brick", ForeverBrick.class);
		xstream.alias("brick", GlideToBrick.class);
		xstream.alias("brick", GoNStepsBackBrick.class);
		xstream.alias("brick", HideBrick.class);
		xstream.alias("brick", HideTextBrick.class);

		xstream.alias("brick", IfLogicBeginBrick.class);
		xstream.alias("brick", IfLogicElseBrick.class);
		xstream.alias("brick", IfLogicEndBrick.class);
		xstream.alias("brick", IfThenLogicBeginBrick.class);
		xstream.alias("brick", IfThenLogicEndBrick.class);

		xstream.alias("brick", UserDefinedBrick.class);
		xstream.alias("brick", UserDefinedReceiverBrick.class);
		xstream.alias("brick", ReportBrick.class);
		xstream.alias("brick", IfOnEdgeBounceBrick.class);
		xstream.alias("brick", InsertItemIntoUserListBrick.class);
		xstream.alias("brick", FlashBrick.class);
		xstream.alias("brick", ChooseCameraBrick.class);
		xstream.alias("brick", CameraBrick.class);
		xstream.alias("brick", LegoNxtMotorMoveBrick.class);
		xstream.alias("brick", LegoNxtMotorStopBrick.class);
		xstream.alias("brick", LegoNxtMotorTurnAngleBrick.class);
		xstream.alias("brick", LegoNxtPlayToneBrick.class);
		xstream.alias("brick", LoopEndBrick.class);
		xstream.alias("brick", LoopEndlessBrick.class);
		xstream.alias("brick", LookRequestBrick.class);
		xstream.alias("brick", PaintNewLookBrick.class);
		xstream.alias("brick", EditLookBrick.class);
		xstream.alias("brick", DeleteLookBrick.class);
		xstream.alias("brick", CopyLookBrick.class);
		xstream.alias("brick", BackgroundRequestBrick.class);
		xstream.alias("brick", MoveNStepsBrick.class);
		xstream.alias("brick", NextLookBrick.class);
		xstream.alias("brick", NoteBrick.class);
		xstream.alias("brick", PenDownBrick.class);
		xstream.alias("brick", PenUpBrick.class);
		xstream.alias("brick", PlaceAtBrick.class);
		xstream.alias("brick", GoToBrick.class);
		xstream.alias("brick", PlaySoundBrick.class);
		xstream.alias("brick", PlaySoundAndWaitBrick.class);
		xstream.alias("brick", PlaySoundAtBrick.class);
		xstream.alias("brick", PauseForBeatsBrick.class);
		xstream.alias("brick", PlayNoteForBeatsBrick.class);
		xstream.alias("brick", PointInDirectionBrick.class);
		xstream.alias("brick", PointToBrick.class);
		xstream.alias("brick", PreviousLookBrick.class);
		xstream.alias("brick", RepeatBrick.class);
		xstream.alias("brick", RepeatUntilBrick.class);
		xstream.alias("brick", ForVariableFromToBrick.class);
		xstream.alias("brick", ForItemInUserListBrick.class);
		xstream.alias("brick", ReplaceItemInUserListBrick.class);
		xstream.alias("brick", SceneTransitionBrick.class);
		xstream.alias("brick", SceneStartBrick.class);
		xstream.alias("brick", SetBrightnessBrick.class);
		xstream.alias("brick", SetCameraFocusPointBrick.class);
		xstream.alias("brick", SetColorBrick.class);
		xstream.alias("brick", SetTransparencyBrick.class);
		xstream.alias("brick", SetLookBrick.class);
		xstream.alias("brick", SetLookByIndexBrick.class);
		xstream.alias("brick", SetBackgroundBrick.class);
		xstream.alias("brick", SetBackgroundByIndexBrick.class);
		xstream.alias("brick", SetBackgroundAndWaitBrick.class);
		xstream.alias("brick", SetBackgroundByIndexAndWaitBrick.class);
		xstream.alias("brick", SetInstrumentBrick.class);
		xstream.alias("brick", SetTempoBrick.class);
		xstream.alias("brick", PlayDrumForBeatsBrick.class);
		xstream.alias("brick", SetPenColorBrick.class);
		xstream.alias("brick", SetPenSizeBrick.class);
		xstream.alias("brick", SetRotationStyleBrick.class);
		xstream.alias("brick", SetSizeToBrick.class);
		xstream.alias("brick", SetVariableBrick.class);
		xstream.alias("brick", SetVolumeToBrick.class);
		xstream.alias("brick", SetXBrick.class);
		xstream.alias("brick", SetYBrick.class);
		xstream.alias("brick", ShowBrick.class);
		xstream.alias("brick", ShowTextBrick.class);
		xstream.alias("brick", SpeakBrick.class);
		xstream.alias("brick", SpeakAndWaitBrick.class);
		xstream.alias("brick", StartListeningBrick.class);
		xstream.alias("brick", StampBrick.class);
		xstream.alias("brick", StopSoundBrick.class);
		xstream.alias("brick", StopAllSoundsBrick.class);
		xstream.alias("brick", SetListeningLanguageBrick.class);
		xstream.alias("brick", ThinkBubbleBrick.class);
		xstream.alias("brick", SayBubbleBrick.class);
		xstream.alias("brick", ThinkForBubbleBrick.class);
		xstream.alias("brick", SayForBubbleBrick.class);
		xstream.alias("brick", TurnLeftBrick.class);
		xstream.alias("brick", TurnRightBrick.class);
		xstream.alias("brick", VibrationBrick.class);
		xstream.alias("brick", WaitBrick.class);
		xstream.alias("brick", WaitUntilBrick.class);
		xstream.alias("brick", WhenBrick.class);
		xstream.alias("brick", WhenConditionBrick.class);
		xstream.alias("brick", WhenBackgroundChangesBrick.class);
		xstream.alias("brick", WhenStartedBrick.class);
		xstream.alias("brick", WhenClonedBrick.class);
		xstream.alias("brick", WriteVariableOnDeviceBrick.class);
		xstream.alias("brick", ReadVariableFromFileBrick.class);
		xstream.alias("brick", WriteListOnDeviceBrick.class);
		xstream.alias("brick", ReadVariableFromDeviceBrick.class);
		xstream.alias("brick", WriteVariableToFileBrick.class);
		xstream.alias("brick", ReadListFromDeviceBrick.class);
		xstream.alias("brick", StopScriptBrick.class);
		xstream.alias("brick", WebRequestBrick.class);
		xstream.alias("brick", StoreCSVIntoUserListBrick.class);
		xstream.alias("brick", ResetTimerBrick.class);
		xstream.alias("brick", EmptyEventBrick.class);

		xstream.alias("brick", WhenNfcBrick.class);
		xstream.alias("brick", SetNfcTagBrick.class);

		xstream.alias("brick", DronePlayLedAnimationBrick.class);
		xstream.alias("brick", DroneTakeOffLandBrick.class);
		xstream.alias("brick", DroneMoveForwardBrick.class);
		xstream.alias("brick", DroneMoveBackwardBrick.class);
		xstream.alias("brick", DroneMoveUpBrick.class);
		xstream.alias("brick", DroneMoveDownBrick.class);
		xstream.alias("brick", DroneMoveLeftBrick.class);
		xstream.alias("brick", DroneMoveRightBrick.class);
		xstream.alias("brick", DroneTurnLeftBrick.class);
		xstream.alias("brick", DroneTurnRightBrick.class);
		xstream.alias("brick", DroneSwitchCameraBrick.class);
		xstream.alias("brick", DroneEmergencyBrick.class);

		xstream.alias("brick", PhiroMotorMoveBackwardBrick.class);
		xstream.alias("brick", PhiroMotorMoveForwardBrick.class);
		xstream.alias("brick", PhiroMotorStopBrick.class);
		xstream.alias("brick", PhiroPlayToneBrick.class);
		xstream.alias("brick", PhiroRGBLightBrick.class);
		xstream.alias("brick", PhiroIfLogicBeginBrick.class);

		xstream.alias("brick", LegoEv3PlayToneBrick.class);
		xstream.alias("brick", LegoEv3MotorMoveBrick.class);
		xstream.alias("brick", LegoEv3MotorStopBrick.class);
		xstream.alias("brick", LegoEv3SetLedBrick.class);

		xstream.alias("brick", ArduinoSendPWMValueBrick.class);
		xstream.alias("brick", ArduinoSendDigitalValueBrick.class);

		xstream.alias("brick", RaspiSendDigitalValueBrick.class);
		xstream.alias("brick", RaspiIfLogicBeginBrick.class);
		xstream.alias("brick", RaspiPwmBrick.class);

		xstream.alias("script", WhenGamepadButtonScript.class);
		xstream.alias("brick", WhenGamepadButtonBrick.class);

		xstream.alias("brick", AssertEqualsBrick.class);
		xstream.alias("brick", FinishStageBrick.class);
		xstream.alias("brick", AssertUserListsBrick.class);
		xstream.alias("brick", ExitStageBrick.class);
		xstream.alias("brick", ParameterizedBrick.class);
		xstream.alias("brick", ParameterizedEndBrick.class);

		xstream.alias("brick", OpenUrlBrick.class);
		xstream.alias("brick", TapAtBrick.class);
		xstream.alias("brick", TapForBrick.class);
		xstream.alias("brick", TouchAndSlideBrick.class);
		xstream.alias("brick", DroneFlipBrick.class);
		xstream.alias("brick", JumpingSumoAnimationsBrick.class);
		xstream.alias("brick", JumpingSumoJumpHighBrick.class);
		xstream.alias("brick", JumpingSumoJumpLongBrick.class);
		xstream.alias("brick", JumpingSumoMoveBackwardBrick.class);
		xstream.alias("brick", JumpingSumoMoveForwardBrick.class);
		xstream.alias("brick", JumpingSumoNoSoundBrick.class);
		xstream.alias("brick", JumpingSumoRotateLeftBrick.class);
		xstream.alias("brick", JumpingSumoRotateRightBrick.class);
		xstream.alias("brick", JumpingSumoSoundBrick.class);
		xstream.alias("brick", JumpingSumoTakingPictureBrick.class);
		xstream.alias("brick", JumpingSumoTurnBrick.class);
		xstream.alias("brick", LegoEv3MotorTurnAngleBrick.class);
		xstream.alias("brick", SetTextBrick.class);
		xstream.alias("brick", ShowTextColorSizeAlignmentBrick.class);
		xstream.alias("brick", StitchBrick.class);
		xstream.alias("brick", RunningStitchBrick.class);
		xstream.alias("brick", StopRunningStitchBrick.class);
		xstream.alias("brick", ZigZagStitchBrick.class);
		xstream.alias("brick", TripleStitchBrick.class);
		xstream.alias("brick", SetThreadColorBrick.class);
		xstream.alias("brick", SewUpBrick.class);
		xstream.alias("brick", WriteEmbroideryToFileBrick.class);
		xstream.alias("brick", WaitTillIdleBrick.class);
		xstream.alias("brick", WhenRaspiPinChangedBrick.class);
		xstream.alias("brick", WhenTouchDownBrick.class);

		xstream.alias("script", WhenBounceOffScript.class);
		xstream.alias("brick", WhenBounceOffBrick.class);

		xstream.alias("brick", SetBounceBrick.class);
		xstream.alias("brick", SetFrictionBrick.class);
		xstream.alias("brick", SetGravityBrick.class);
		xstream.alias("brick", SetMassBrick.class);
		xstream.alias("brick", SetPhysicsObjectTypeBrick.class);
		xstream.alias("brick", SetVelocityBrick.class);
		xstream.alias("brick", TurnLeftSpeedBrick.class);
		xstream.alias("brick", TurnRightSpeedBrick.class);

		xstream.alias("setting", LegoNXTSetting.class);
		xstream.alias("nxtPort", LegoNXTSetting.NXTPort.class);

		xstream.alias("setting", LegoEV3Setting.class);
		xstream.alias("ev3Port", LegoEV3Setting.EV3Port.class);

		xstream.alias("brick", FadeParticleEffectBrick.class);
		xstream.alias("brick", ParticleEffectAdditivityBrick.class);
		xstream.alias("brick", SetParticleColorBrick.class);
	}

	public Project loadProject(File projectDir, Context context) throws IOException, LoadingProjectException {
		cleanUpTmpCodeFile(projectDir);

		File xmlFile = new File(projectDir, CODE_XML_FILE_NAME);
		if (!xmlFile.exists()) {
			throw new FileNotFoundException(xmlFile.getAbsolutePath() + " does not exist.");
		}
		xmlFile.setLastModified(System.currentTimeMillis());

		try {
			loadSaveLock.lock();

			Project project;
			ProjectData projectMetaData = new ProjectMetaDataParser(xmlFile).getProjectMetaData();

			if (!projectMetaData.hasScenes()) {
				new File(projectDir, IMAGE_DIRECTORY_NAME).mkdir();
				new File(projectDir, SOUND_DIRECTORY_NAME).mkdir();

				prepareXstream(LegacyProjectWithoutScenes.class, Scene.class);
				LegacyProjectWithoutScenes projectWithoutScenes =
						(LegacyProjectWithoutScenes) xstream.getProjectFromXML(xmlFile);
				prepareXstream(Project.class, Scene.class);

				project = projectWithoutScenes.toProject(context);
			} else if (projectMetaData.getLanguageVersion() < 0.9991) {
				prepareXstream(ProjectUntilLanguageVersion0999.class, SceneUntilLanguageVersion0999.class);
				ProjectUntilLanguageVersion0999 legacyProject =
						(ProjectUntilLanguageVersion0999) xstream.getProjectFromXML(xmlFile);
				prepareXstream(Project.class, Scene.class);

				project = legacyProject.toProject();
			} else {
				prepareXstream(Project.class, Scene.class);
				project = (Project) xstream.getProjectFromXML(xmlFile);

				for (Scene scene : project.getSceneList()) {
					scene.setProject(project);
				}
			}
			project.checkForInvisibleSprites();
			project.setDirectory(projectDir);
			setFileReferences(project);
			return project;
		} catch (Exception e) {
			throw new LoadingProjectException("Cannot load project from " + projectDir.getAbsolutePath()
					+ "\nException: " + e.getLocalizedMessage());
		} finally {
			loadSaveLock.unlock();
		}
	}

	public static boolean renameProject(File xmlFile, String destinationName) throws IOException {
		if (!xmlFile.exists()) {
			throw new FileNotFoundException(xmlFile + " does not exist.");
		}

		String currentXml = Files.asCharSource(xmlFile, Charsets.UTF_8).read();
		StringFinder stringFinder = new StringFinder();

		String sourceName = stringFinder.findBetween(currentXml, PROGRAM_NAME_START_TAG,
				PROGRAM_NAME_END_TAG);

		if (sourceName == null) {
			return false;
		}

		destinationName = getXMLEncodedString(destinationName);

		if (sourceName.equals(destinationName)) {
			return true;
		}

		String sourceProjectNameTag = PROGRAM_NAME_START_TAG + sourceName + PROGRAM_NAME_END_TAG;
		String destinationProjectNameTag = PROGRAM_NAME_START_TAG + destinationName + PROGRAM_NAME_END_TAG;
		String newXml = currentXml.replace(sourceProjectNameTag, destinationProjectNameTag);

		if (currentXml.equals(newXml)) {
			Log.e(TAG, "Cannot find projectNameTag in code.xml");
			return false;
		}

		StorageOperations.writeToFile(xmlFile, newXml);
		return true;
	}

	private static String getXMLEncodedString(String sourceName) {
		sourceName = new XStream().toXML(sourceName);
		sourceName = sourceName.replace("<string>", "");
		sourceName = sourceName.replace("</string>", "");
		return sourceName;
	}

	private static void setFileReferences(Project project) {
		for (Scene scene : project.getSceneList()) {
			File imageDir = new File(scene.getDirectory(), IMAGE_DIRECTORY_NAME);
			File soundDir = new File(scene.getDirectory(), SOUND_DIRECTORY_NAME);

			for (Sprite sprite : scene.getSpriteList()) {
				for (Iterator<LookData> iterator = sprite.getLookList().iterator(); iterator.hasNext(); ) {
					LookData lookData = iterator.next();
					File lookFile = new File(imageDir, lookData.getXstreamFileName());

					if (lookFile.exists()) {
						lookData.setFile(lookFile);
					} else {
						iterator.remove();
					}
				}

				for (Iterator<SoundInfo> iterator = sprite.getSoundList().iterator(); iterator.hasNext(); ) {
					SoundInfo soundInfo = iterator.next();
					File soundFile = new File(soundDir, soundInfo.getXstreamFileName());

					if (soundFile.exists()) {
						soundInfo.setFile(soundFile);
					} else {
						iterator.remove();
					}
				}
			}
		}
	}
	private boolean unnecessaryChanges(String currentXml, String previousXml) {
		String formulaYRegex = "<formula category=\".*Y.*\">";
		String formulaXRegex = "<formula category=\".*X.*\">";
		Pattern formulaYPattern = Pattern.compile(formulaYRegex, Pattern.CASE_INSENSITIVE);
		Pattern formulaXPattern = Pattern.compile(formulaXRegex, Pattern.CASE_INSENSITIVE);
		Matcher currentFormulaYMatcher = formulaYPattern.matcher(currentXml);
		Matcher previousFormulaXMatcher = formulaXPattern.matcher(previousXml);
		currentFormulaYMatcher.find();
		previousFormulaXMatcher.find();
		if (previousFormulaXMatcher.matches() && currentFormulaYMatcher.matches() && (currentXml.indexOf(previousFormulaXMatcher.group(0)) == previousXml.indexOf(currentFormulaYMatcher.group(0)))) {
			return true;
		}
		return false;
	}

	public boolean saveProject(Project project) {
		if (project == null) {
			return false;
		}

		try {
			cleanUpTmpCodeFile(project.getDirectory());
		} catch (LoadingProjectException e) {
			return false;
		}

		loadSaveLock.lock();
		if (BuildConfig.FLAVOR.equals("pocketCodeBeta")) {
			project.getXmlHeader().setApplicationBuildType("debug");
		} else {
			project.getXmlHeader().setApplicationBuildType(BuildConfig.BUILD_TYPE);
		}

		try {
			String currentXml = XML_HEADER.concat(xstream.toXML(project));
			File tmpCodeFile = new File(project.getDirectory(), TMP_CODE_XML_FILE_NAME);
			File currentCodeFile = new File(project.getDirectory(), CODE_XML_FILE_NAME);

			if (currentCodeFile.exists()) {
				try {
					String previousXml = Files.asCharSource(currentCodeFile, Charsets.UTF_8).read();

					if (previousXml.equals(currentXml)) {
						Log.d(TAG, "Project version is the same. Do not update " + currentCodeFile.getName());
						return false;
					} else {
						String languageRegex = "<catrobatLanguageVersion>.*</catrobatLanguageVersion>";
						Pattern languagePattern = Pattern.compile(languageRegex, Pattern.CASE_INSENSITIVE);
						Matcher currentLanguageMatcher = languagePattern.matcher(currentXml);
						Matcher previousLanguageMatcher = languagePattern.matcher(previousXml);
						currentLanguageMatcher.find();
						previousLanguageMatcher.find();
						if (Objects.equals(currentLanguageMatcher.group(0),
								previousLanguageMatcher.group(0)) && (!unnecessaryChanges(currentXml, previousXml))) {
							ProjectManager.getInstance().changedProject(project.getName());
						}
					}
				} catch (Exception e) {
					Log.e(TAG, "Opening project at " + currentCodeFile.getAbsolutePath() + " failed.", e);
					return false;
				}
			}

			StorageOperations.createDir(DEFAULT_ROOT_DIRECTORY);
			StorageOperations.createDir(project.getDirectory());

			for (Scene scene : project.getSceneList()) {
				StorageOperations.createSceneDirectory(scene.getDirectory());
			}
			StorageOperations.writeToFile(tmpCodeFile, currentXml);

			if (currentCodeFile.exists() && !currentCodeFile.delete()) {
				Log.e(TAG, "Cannot delete " + currentCodeFile.getName());
			}

			if (!tmpCodeFile.renameTo(currentCodeFile)) {
				Log.e(TAG, "Cannot rename code.xml for " + project.getName());
			}

			return true;
		} catch (Exception exception) {
			Log.e(TAG, "Saving project " + project.getName() + " failed.", exception);
			return false;
		} finally {
			loadSaveLock.unlock();
		}
	}

	private void cleanUpTmpCodeFile(File projectDir) throws LoadingProjectException {
		loadSaveLock.lock();

		File tmpXmlFile = new File(projectDir, TMP_CODE_XML_FILE_NAME);
		File actualXmlFile = new File(projectDir, CODE_XML_FILE_NAME);

		try {
			if (tmpXmlFile.exists()) {
				if (actualXmlFile.exists()) {
					tmpXmlFile.delete();
				} else {
					if (!tmpXmlFile.renameTo(actualXmlFile)) {
						throw new LoadingProjectException(CODE_XML_FILE_NAME + " did not exist. But wait, renaming "
								+ tmpXmlFile.getAbsolutePath() + " failed too.");
					}
				}
			}
		} finally {
			loadSaveLock.unlock();
		}
	}

	public String getXmlAsStringFromProject(Project project) {
		loadSaveLock.lock();
		String xmlString;
		try {
			prepareXstream(project.getClass(), project.getSceneList().get(0).getClass());
			xmlString = xstream.toXML(project);
			prepareXstream(Project.class, Scene.class);
		} finally {
			loadSaveLock.unlock();
		}
		return xmlString;
	}

	public static String extractDefaultSceneNameFromXml(File projectDir) {
		File xmlFile = new File(projectDir, CODE_XML_FILE_NAME);

		StringFinder stringFinder = new StringFinder();

		try {
			String xml = Files.asCharSource(xmlFile, Charsets.UTF_8).read();
			return stringFinder.findBetween(xml, "<scenes>\\s*<scene>\\s*<name>", "</name>");
		} catch (IOException e) {
			Log.e(TAG, Log.getStackTraceString(e));
		}
		return null;
	}

	@VisibleForTesting
	public BackwardCompatibleCatrobatLanguageXStream getXstream() {
		return xstream;
	}
}
