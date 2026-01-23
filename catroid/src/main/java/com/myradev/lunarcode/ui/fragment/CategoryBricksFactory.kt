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
package com.myradev.lunarcode.ui.fragment

import com.myradev.lunarcode.content.bricks.LuaBrick
import com.myradev.lunarcode.modloader.ModManager

import android.content.Context
import com.myradev.lunarcode.BuildConfig
import com.myradev.lunarcode.ProjectManager
import com.myradev.lunarcode.R
import com.myradev.lunarcode.common.BrickValues
import com.myradev.lunarcode.content.BroadcastScript
import com.myradev.lunarcode.content.RaspiInterruptScript
import com.myradev.lunarcode.content.WhenBounceOffScript
import com.myradev.lunarcode.content.WhenConditionScript
import com.myradev.lunarcode.content.WhenGamepadButtonScript
import com.myradev.lunarcode.content.bricks.AddItemToUserListBrick
import com.myradev.lunarcode.content.bricks.ArduinoSendDigitalValueBrick
import com.myradev.lunarcode.content.bricks.ArduinoSendPWMValueBrick
import com.myradev.lunarcode.content.bricks.AskBrick
import com.myradev.lunarcode.content.bricks.AskSpeechBrick
import com.myradev.lunarcode.content.bricks.AssertEqualsBrick
import com.myradev.lunarcode.content.bricks.AssertUserListsBrick
import com.myradev.lunarcode.content.bricks.BackgroundRequestBrick
import com.myradev.lunarcode.content.bricks.Brick
import com.myradev.lunarcode.content.bricks.BroadcastBrick
import com.myradev.lunarcode.content.bricks.BroadcastReceiverBrick
import com.myradev.lunarcode.content.bricks.BroadcastWaitBrick
import com.myradev.lunarcode.content.bricks.CameraBrick
import com.myradev.lunarcode.content.bricks.ChangeBrightnessByNBrick
import com.myradev.lunarcode.content.bricks.ChangeColorByNBrick
import com.myradev.lunarcode.content.bricks.ChangeSizeByNBrick
import com.myradev.lunarcode.content.bricks.ChangeTempoByNBrick
import com.myradev.lunarcode.content.bricks.ChangeTransparencyByNBrick
import com.myradev.lunarcode.content.bricks.ChangeVariableBrick
import com.myradev.lunarcode.content.bricks.ChangeVolumeByNBrick
import com.myradev.lunarcode.content.bricks.ChangeXByNBrick
import com.myradev.lunarcode.content.bricks.ChangeYByNBrick
import com.myradev.lunarcode.content.bricks.ChooseCameraBrick
import com.myradev.lunarcode.content.bricks.ClearBackgroundBrick
import com.myradev.lunarcode.content.bricks.ClearGraphicEffectBrick
import com.myradev.lunarcode.content.bricks.ClearUserListBrick
import com.myradev.lunarcode.content.bricks.CloneBrick
import com.myradev.lunarcode.content.bricks.ComeToFrontBrick
import com.myradev.lunarcode.content.bricks.CopyLookBrick
import com.myradev.lunarcode.content.bricks.DeleteItemOfUserListBrick
import com.myradev.lunarcode.content.bricks.DeleteLookBrick
import com.myradev.lunarcode.content.bricks.DeleteThisCloneBrick
import com.myradev.lunarcode.content.bricks.DroneEmergencyBrick
import com.myradev.lunarcode.content.bricks.DroneFlipBrick
import com.myradev.lunarcode.content.bricks.DroneMoveBackwardBrick
import com.myradev.lunarcode.content.bricks.DroneMoveDownBrick
import com.myradev.lunarcode.content.bricks.DroneMoveForwardBrick
import com.myradev.lunarcode.content.bricks.DroneMoveLeftBrick
import com.myradev.lunarcode.content.bricks.DroneMoveRightBrick
import com.myradev.lunarcode.content.bricks.DroneMoveUpBrick
import com.myradev.lunarcode.content.bricks.DronePlayLedAnimationBrick
import com.myradev.lunarcode.content.bricks.DroneSwitchCameraBrick
import com.myradev.lunarcode.content.bricks.DroneTakeOffLandBrick
import com.myradev.lunarcode.content.bricks.DroneTurnLeftBrick
import com.myradev.lunarcode.content.bricks.DroneTurnRightBrick
import com.myradev.lunarcode.content.bricks.EditLookBrick
import com.myradev.lunarcode.content.bricks.EmbroideryArcBrick
import com.myradev.lunarcode.content.bricks.EmbroideryThroughBrick
import com.myradev.lunarcode.content.bricks.ExitStageBrick
import com.myradev.lunarcode.content.bricks.FadeParticleEffectBrick
import com.myradev.lunarcode.content.bricks.FinishStageBrick
import com.myradev.lunarcode.content.bricks.FlashBrick
import com.myradev.lunarcode.content.bricks.ForItemInUserListBrick
import com.myradev.lunarcode.content.bricks.ForVariableFromToBrick
import com.myradev.lunarcode.content.bricks.ForeverBrick
import com.myradev.lunarcode.content.bricks.GlideToBrick
import com.myradev.lunarcode.content.bricks.GoNStepsBackBrick
import com.myradev.lunarcode.content.bricks.GoToBrick
import com.myradev.lunarcode.content.bricks.HideBrick
import com.myradev.lunarcode.content.bricks.HideTextBrick
import com.myradev.lunarcode.content.bricks.IfLogicBeginBrick
import com.myradev.lunarcode.content.bricks.IfOnEdgeBounceBrick
import com.myradev.lunarcode.content.bricks.IfThenLogicBeginBrick
import com.myradev.lunarcode.content.bricks.InsertItemIntoUserListBrick
import com.myradev.lunarcode.content.bricks.JumpingSumoAnimationsBrick
import com.myradev.lunarcode.content.bricks.JumpingSumoJumpHighBrick
import com.myradev.lunarcode.content.bricks.JumpingSumoJumpLongBrick
import com.myradev.lunarcode.content.bricks.JumpingSumoMoveBackwardBrick
import com.myradev.lunarcode.content.bricks.JumpingSumoMoveForwardBrick
import com.myradev.lunarcode.content.bricks.JumpingSumoNoSoundBrick
import com.myradev.lunarcode.content.bricks.JumpingSumoRotateLeftBrick
import com.myradev.lunarcode.content.bricks.JumpingSumoRotateRightBrick
import com.myradev.lunarcode.content.bricks.JumpingSumoSoundBrick
import com.myradev.lunarcode.content.bricks.JumpingSumoTakingPictureBrick
import com.myradev.lunarcode.content.bricks.JumpingSumoTurnBrick
import com.myradev.lunarcode.content.bricks.LaserArcBrick
import com.myradev.lunarcode.content.bricks.LaserThroughBrick
import com.myradev.lunarcode.content.bricks.LegoEv3MotorMoveBrick
import com.myradev.lunarcode.content.bricks.LegoEv3MotorStopBrick
import com.myradev.lunarcode.content.bricks.LegoEv3MotorTurnAngleBrick
import com.myradev.lunarcode.content.bricks.LegoEv3PlayToneBrick
import com.myradev.lunarcode.content.bricks.LegoEv3SetLedBrick
import com.myradev.lunarcode.content.bricks.LegoNxtMotorMoveBrick
import com.myradev.lunarcode.content.bricks.LegoNxtMotorStopBrick
import com.myradev.lunarcode.content.bricks.LegoNxtMotorTurnAngleBrick
import com.myradev.lunarcode.content.bricks.LegoNxtPlayToneBrick
import com.myradev.lunarcode.content.bricks.LookRequestBrick
import com.myradev.lunarcode.content.bricks.MoveNStepsBrick
import com.myradev.lunarcode.content.bricks.NextLookBrick
import com.myradev.lunarcode.content.bricks.NoteBrick
import com.myradev.lunarcode.content.bricks.OpenUrlBrick
import com.myradev.lunarcode.content.bricks.PaintNewLookBrick
import com.myradev.lunarcode.content.bricks.ParameterizedBrick
import com.myradev.lunarcode.content.bricks.ParameterizedEndBrick
import com.myradev.lunarcode.content.bricks.ParticleEffectAdditivityBrick
import com.myradev.lunarcode.content.bricks.PauseForBeatsBrick
import com.myradev.lunarcode.content.bricks.PenDownBrick
import com.myradev.lunarcode.content.bricks.PenUpBrick
import com.myradev.lunarcode.content.bricks.PhiroIfLogicBeginBrick
import com.myradev.lunarcode.content.bricks.PhiroMotorMoveBackwardBrick
import com.myradev.lunarcode.content.bricks.PhiroMotorMoveForwardBrick
import com.myradev.lunarcode.content.bricks.PhiroMotorStopBrick
import com.myradev.lunarcode.content.bricks.PhiroPlayToneBrick
import com.myradev.lunarcode.content.bricks.PhiroRGBLightBrick
import com.myradev.lunarcode.content.bricks.PlaceAtBrick
import com.myradev.lunarcode.content.bricks.PlayDrumForBeatsBrick
import com.myradev.lunarcode.content.bricks.PlayNoteForBeatsBrick
import com.myradev.lunarcode.content.bricks.PlaySoundAndWaitBrick
import com.myradev.lunarcode.content.bricks.PlaySoundAtBrick
import com.myradev.lunarcode.content.bricks.PlaySoundBrick
import com.myradev.lunarcode.content.bricks.PlotArcBrick
import com.myradev.lunarcode.content.bricks.PlotThroughBrick
import com.myradev.lunarcode.content.bricks.PointInDirectionBrick
import com.myradev.lunarcode.content.bricks.PointToBrick
import com.myradev.lunarcode.content.bricks.PreviousLookBrick
import com.myradev.lunarcode.content.bricks.RaspiIfLogicBeginBrick
import com.myradev.lunarcode.content.bricks.RaspiPwmBrick
import com.myradev.lunarcode.content.bricks.RaspiSendDigitalValueBrick
import com.myradev.lunarcode.content.bricks.ReadListFromDeviceBrick
import com.myradev.lunarcode.content.bricks.ReadVariableFromDeviceBrick
import com.myradev.lunarcode.content.bricks.ReadVariableFromFileBrick
import com.myradev.lunarcode.content.bricks.RepeatBrick
import com.myradev.lunarcode.content.bricks.RepeatUntilBrick
import com.myradev.lunarcode.content.bricks.ReplaceItemInUserListBrick
import com.myradev.lunarcode.content.bricks.ReportBrick
import com.myradev.lunarcode.content.bricks.ResetTimerBrick
import com.myradev.lunarcode.content.bricks.RunningStitchBrick
import com.myradev.lunarcode.content.bricks.SaveLaserBrick
import com.myradev.lunarcode.content.bricks.SavePlotBrick
import com.myradev.lunarcode.content.bricks.SharePlotBrick
import com.myradev.lunarcode.content.bricks.SayBubbleBrick
import com.myradev.lunarcode.content.bricks.SayForBubbleBrick
import com.myradev.lunarcode.content.bricks.SceneStartBrick
import com.myradev.lunarcode.content.bricks.SceneTransitionBrick
import com.myradev.lunarcode.content.bricks.SetBackgroundAndWaitBrick
import com.myradev.lunarcode.content.bricks.SetBackgroundBrick
import com.myradev.lunarcode.content.bricks.SetBackgroundByIndexAndWaitBrick
import com.myradev.lunarcode.content.bricks.SetBackgroundByIndexBrick
import com.myradev.lunarcode.content.bricks.SetBounceBrick
import com.myradev.lunarcode.content.bricks.SetBrightnessBrick
import com.myradev.lunarcode.content.bricks.SetCameraFocusPointBrick
import com.myradev.lunarcode.content.bricks.SetColorBrick
import com.myradev.lunarcode.content.bricks.SetFrictionBrick
import com.myradev.lunarcode.content.bricks.SetGravityBrick
import com.myradev.lunarcode.content.bricks.SetInstrumentBrick
import com.myradev.lunarcode.content.bricks.SetListeningLanguageBrick
import com.myradev.lunarcode.content.bricks.SetLookBrick
import com.myradev.lunarcode.content.bricks.SetLookByIndexBrick
import com.myradev.lunarcode.content.bricks.SetMassBrick
import com.myradev.lunarcode.content.bricks.SetNfcTagBrick
import com.myradev.lunarcode.content.bricks.SetParticleColorBrick
import com.myradev.lunarcode.content.bricks.SetPenColorBrick
import com.myradev.lunarcode.content.bricks.SetPenSizeBrick
import com.myradev.lunarcode.content.bricks.SetPhysicsObjectTypeBrick
import com.myradev.lunarcode.content.bricks.SetRotationStyleBrick
import com.myradev.lunarcode.content.bricks.SetSizeToBrick
import com.myradev.lunarcode.content.bricks.SetTempoBrick
import com.myradev.lunarcode.content.bricks.SetThreadColorBrick
import com.myradev.lunarcode.content.bricks.SetTransparencyBrick
import com.myradev.lunarcode.content.bricks.SetVariableBrick
import com.myradev.lunarcode.content.bricks.SetVelocityBrick
import com.myradev.lunarcode.content.bricks.SetVolumeToBrick
import com.myradev.lunarcode.content.bricks.SetXBrick
import com.myradev.lunarcode.content.bricks.SetYBrick
import com.myradev.lunarcode.content.bricks.SewUpBrick
import com.myradev.lunarcode.content.bricks.ShareLaserBrick
import com.myradev.lunarcode.content.bricks.ShowBrick
import com.myradev.lunarcode.content.bricks.ShowTextBrick
import com.myradev.lunarcode.content.bricks.ShowTextColorSizeAlignmentBrick
import com.myradev.lunarcode.content.bricks.SpeakAndWaitBrick
import com.myradev.lunarcode.content.bricks.SpeakBrick
import com.myradev.lunarcode.content.bricks.StampBrick
import com.myradev.lunarcode.content.bricks.StartCutBrick
import com.myradev.lunarcode.content.bricks.StartEngraveBrick
import com.myradev.lunarcode.content.bricks.StartListeningBrick
import com.myradev.lunarcode.content.bricks.StartPlotBrick
import com.myradev.lunarcode.content.bricks.StitchBrick
import com.myradev.lunarcode.content.bricks.StopAllSoundsBrick
import com.myradev.lunarcode.content.bricks.StopCutBrick
import com.myradev.lunarcode.content.bricks.StopEngraveBrick
import com.myradev.lunarcode.content.bricks.StopPlotBrick
import com.myradev.lunarcode.content.bricks.StopRunningStitchBrick
import com.myradev.lunarcode.content.bricks.StopScriptBrick
import com.myradev.lunarcode.content.bricks.StopSoundBrick
import com.myradev.lunarcode.content.bricks.StoreCSVIntoUserListBrick
import com.myradev.lunarcode.content.bricks.TapAtBrick
import com.myradev.lunarcode.content.bricks.TapForBrick
import com.myradev.lunarcode.content.bricks.ThinkBubbleBrick
import com.myradev.lunarcode.content.bricks.ThinkForBubbleBrick
import com.myradev.lunarcode.content.bricks.TouchAndSlideBrick
import com.myradev.lunarcode.content.bricks.TripleStitchBrick
import com.myradev.lunarcode.content.bricks.TurnLeftBrick
import com.myradev.lunarcode.content.bricks.TurnLeftSpeedBrick
import com.myradev.lunarcode.content.bricks.TurnRightBrick
import com.myradev.lunarcode.content.bricks.TurnRightSpeedBrick
import com.myradev.lunarcode.content.bricks.UserDefinedBrick
import com.myradev.lunarcode.content.bricks.UserDefinedReceiverBrick
import com.myradev.lunarcode.content.bricks.VibrationBrick
import com.myradev.lunarcode.content.bricks.WaitBrick
import com.myradev.lunarcode.content.bricks.WaitTillIdleBrick
import com.myradev.lunarcode.content.bricks.WaitUntilBrick
import com.myradev.lunarcode.content.bricks.WebRequestBrick
import com.myradev.lunarcode.content.bricks.WhenBackgroundChangesBrick
import com.myradev.lunarcode.content.bricks.WhenBounceOffBrick
import com.myradev.lunarcode.content.bricks.WhenBrick
import com.myradev.lunarcode.content.bricks.WhenClonedBrick
import com.myradev.lunarcode.content.bricks.WhenConditionBrick
import com.myradev.lunarcode.content.bricks.WhenGamepadButtonBrick
import com.myradev.lunarcode.content.bricks.WhenNfcBrick
import com.myradev.lunarcode.content.bricks.WhenRaspiPinChangedBrick
import com.myradev.lunarcode.content.bricks.WhenStartedBrick
import com.myradev.lunarcode.content.bricks.WhenTouchDownBrick
import com.myradev.lunarcode.content.bricks.WriteEmbroideryToFileBrick
import com.myradev.lunarcode.content.bricks.WriteListOnDeviceBrick
import com.myradev.lunarcode.content.bricks.WriteVariableOnDeviceBrick
import com.myradev.lunarcode.content.bricks.WriteVariableToFileBrick
import com.myradev.lunarcode.content.bricks.ZigZagStitchBrick
import com.myradev.lunarcode.formulaeditor.Formula
import com.myradev.lunarcode.formulaeditor.FormulaElement
import com.myradev.lunarcode.formulaeditor.Operators
import com.myradev.lunarcode.formulaeditor.Sensors
import com.myradev.lunarcode.ui.controller.RecentBrickListManager
import com.myradev.lunarcode.ui.settingsfragments.SettingsFragment
import java.util.ArrayList
import java.util.Locale

open class CategoryBricksFactory {

    fun getBricks(category: String, isBackgroundSprite: Boolean, context: Context): List<Brick> {
        when (category) {
            context.getString(R.string.category_recently_used) -> return setupRecentBricksCategoryList(
                isBackgroundSprite
            )

            context.getString(R.string.category_event) -> return setupEventCategoryList(
                context,
                isBackgroundSprite
            )

            context.getString(R.string.category_control) -> return setupControlCategoryList(context)
            context.getString(R.string.category_motion) -> return setupMotionCategoryList(
                context,
                isBackgroundSprite
            )

            context.getString(R.string.category_sound) -> return setupSoundCategoryList(context)
            context.getString(R.string.category_looks) -> return setupLooksCategoryList(
                context,
                isBackgroundSprite
            )

            context.getString(R.string.category_pen) -> return setupPenCategoryList(
                isBackgroundSprite
            )

            context.getString(R.string.category_user_bricks) -> return setupUserBricksCategoryList()
            context.getString(R.string.category_data) -> return setupDataCategoryList(
                context,
                isBackgroundSprite
            )

            context.getString(R.string.category_device) -> return setupDeviceCategoryList(
                context,
                isBackgroundSprite
            )

            context.getString(R.string.category_lego_nxt) -> return setupLegoNxtCategoryList()
            context.getString(R.string.category_lego_ev3) -> return setupLegoEv3CategoryList()
            context.getString(R.string.category_arduino) -> return setupArduinoCategoryList()
            context.getString(R.string.category_drone) -> return setupDroneCategoryList()
            context.getString(R.string.category_jumping_sumo) -> return setupJumpingSumoCategoryList()
            context.getString(R.string.category_phiro) -> return setupPhiroProCategoryList()
            context.getString(R.string.category_cast) -> return setupChromecastCategoryList(context)
            context.getString(R.string.category_raspi) -> return setupRaspiCategoryList()
            context.getString(R.string.category_embroidery) -> return setupEmbroideryCategoryList(
                context
            )

            context.getString(R.string.category_plot) -> return setupPlotCategoryList(context)
            context.getString(R.string.category_laser) -> return setupLaserCategoryList(context)
            context.getString(R.string.category_assertions) -> return setupAssertionsCategoryList(
                context
            )

            else -> {
                val customBricksFromLua = ModManager.getCustomBricks().filter { it.category == category }.map { LuaBrick(it.name, it.category) }
                if (customBricksFromLua.isNotEmpty()) return customBricksFromLua
                return emptyList()
            }
        }
    }

    private fun setupRecentBricksCategoryList(isBackgroundSprite: Boolean): List<Brick> =
        RecentBrickListManager.getInstance().getRecentBricks(isBackgroundSprite)

    protected open fun setupEventCategoryList(
        context: Context,
        isBackgroundSprite: Boolean
    ): List<Brick> {
        val defaultIf = FormulaElement(
            FormulaElement.ElementType.OPERATOR,
            Operators.SMALLER_THAN.toString(),
            null
        )
        defaultIf.setLeftChild(FormulaElement(FormulaElement.ElementType.NUMBER, "1", null))
        defaultIf.setRightChild(FormulaElement(FormulaElement.ElementType.NUMBER, "2", null))
        val eventBrickList: MutableList<Brick> = ArrayList()
        eventBrickList.add(WhenStartedBrick())
        eventBrickList.add(WhenBrick())
        eventBrickList.add(WhenTouchDownBrick())
        val broadcastMessages =
            ProjectManager.getInstance().currentProject?.broadcastMessageContainer?.broadcastMessages
        var broadcastMessage: String? = context.getString(R.string.brick_broadcast_default_value)
        if (broadcastMessages != null && broadcastMessages.size > 0) {
            broadcastMessage = broadcastMessages[0]
        }
        eventBrickList.add(BroadcastReceiverBrick(BroadcastScript(broadcastMessage)))
        eventBrickList.add(BroadcastBrick(broadcastMessage))
        eventBrickList.add(BroadcastWaitBrick(broadcastMessage))
        eventBrickList.add(WhenConditionBrick(WhenConditionScript(Formula(defaultIf))))
        if (!isBackgroundSprite) {
            eventBrickList.add(WhenBounceOffBrick(WhenBounceOffScript(null)))
        }
        eventBrickList.add(WhenBackgroundChangesBrick())
        eventBrickList.add(WhenClonedBrick())
        eventBrickList.add(CloneBrick())
        eventBrickList.add(DeleteThisCloneBrick())
        if (SettingsFragment.isNfcSharedPreferenceEnabled(context)) {
            eventBrickList.add(WhenNfcBrick())
        }
        return eventBrickList
    }

    protected open fun setupControlCategoryList(context: Context): List<Brick> {
        val ifConditionFormulaElement = FormulaElement(
            FormulaElement.ElementType.OPERATOR,
            Operators.SMALLER_THAN.toString(),
            null
        )
        ifConditionFormulaElement.setLeftChild(
            FormulaElement(
                FormulaElement.ElementType.NUMBER,
                "1",
                null
            )
        )
        ifConditionFormulaElement.setRightChild(
            FormulaElement(
                FormulaElement.ElementType.NUMBER,
                "2",
                null
            )
        )
        val ifConditionFormula = Formula(ifConditionFormulaElement)
        val controlBrickList: MutableList<Brick> = ArrayList()
        controlBrickList.add(WaitBrick(BrickValues.WAIT))
        controlBrickList.add(NoteBrick(context.getString(R.string.brick_note_default_value)))
        controlBrickList.add(ForeverBrick())
        controlBrickList.add(IfLogicBeginBrick(ifConditionFormula))
        controlBrickList.add(IfThenLogicBeginBrick(ifConditionFormula))
        controlBrickList.add(WaitUntilBrick(ifConditionFormula))
        controlBrickList.add(RepeatBrick(Formula(BrickValues.REPEAT)))
        controlBrickList.add(RepeatUntilBrick(ifConditionFormula))
        controlBrickList.add(
            ForVariableFromToBrick(
                Formula(BrickValues.FOR_LOOP_FROM),
                Formula(BrickValues.FOR_LOOP_TO)
            )
        )
        controlBrickList.add(ForItemInUserListBrick())
        controlBrickList.add(SceneTransitionBrick(null))
        controlBrickList.add(SceneStartBrick(null))
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
            controlBrickList.add(PhiroIfLogicBeginBrick())
        }
        controlBrickList.add(ExitStageBrick())
        controlBrickList.add(StopScriptBrick(BrickValues.STOP_THIS_SCRIPT))
        controlBrickList.add(WaitTillIdleBrick())
        controlBrickList.add(WhenClonedBrick())
        controlBrickList.add(CloneBrick())
        controlBrickList.add(DeleteThisCloneBrick())
        if (SettingsFragment.isNfcSharedPreferenceEnabled(context)) {
            controlBrickList.add(SetNfcTagBrick(context.getString(R.string.brick_set_nfc_tag_default_value)))
        }
        val broadcastMessages =
            ProjectManager.getInstance().currentProject?.broadcastMessageContainer?.broadcastMessages
        var broadcastMessage: String? = context.getString(R.string.brick_broadcast_default_value)
        if (broadcastMessages != null && broadcastMessages.size > 0) {
            broadcastMessage = broadcastMessages[0]
        }
        controlBrickList.add(BroadcastReceiverBrick(BroadcastScript(broadcastMessage)))
        controlBrickList.add(BroadcastBrick(broadcastMessage))
        controlBrickList.add(BroadcastWaitBrick(broadcastMessage))
        controlBrickList.add(TapAtBrick(BrickValues.TOUCH_X_START, BrickValues.TOUCH_Y_START))
        controlBrickList.add(
            TapForBrick(
                BrickValues.TOUCH_X_START,
                BrickValues.TOUCH_Y_START,
                BrickValues.TOUCH_DURATION
            )
        )
        controlBrickList.add(
            TouchAndSlideBrick(
                BrickValues.TOUCH_X_START,
                BrickValues.TOUCH_Y_START,
                BrickValues.TOUCH_X_GOAL,
                BrickValues.TOUCH_Y_GOAL,
                BrickValues.TOUCH_DURATION
            )
        )
        controlBrickList.add(OpenUrlBrick(BrickValues.OPEN_IN_BROWSER))
        return controlBrickList
    }

    private fun setupUserBricksCategoryList(): List<Brick> {
        val currentSprite = ProjectManager.getInstance().currentSprite
        var userDefinedBricks: MutableList<Brick> = ArrayList()
        if (currentSprite != null) userDefinedBricks = currentSprite.userDefinedBrickList
        userDefinedBricks = ArrayList(userDefinedBricks)
        if (BuildConfig.FEATURE_USER_REPORTERS_ENABLED) userDefinedBricks.add(ReportBrick())
        return userDefinedBricks
    }

    private fun setupChromecastCategoryList(context: Context): List<Brick> {
        val chromecastBrickList: MutableList<Brick> = ArrayList()
        chromecastBrickList.add(WhenGamepadButtonBrick(WhenGamepadButtonScript(context.getString(R.string.cast_gamepad_A))))
        return chromecastBrickList
    }

    protected open fun setupMotionCategoryList(
        context: Context?,
        isBackgroundSprite: Boolean
    ): List<Brick> {
        val motionBrickList: MutableList<Brick> = ArrayList()
        motionBrickList.add(PlaceAtBrick(BrickValues.X_POSITION, BrickValues.Y_POSITION))
        motionBrickList.add(SetXBrick(Formula(BrickValues.X_POSITION)))
        motionBrickList.add(SetYBrick(BrickValues.Y_POSITION))
        motionBrickList.add(ChangeXByNBrick(BrickValues.CHANGE_X_BY))
        motionBrickList.add(ChangeYByNBrick(BrickValues.CHANGE_Y_BY))
        motionBrickList.add(GoToBrick(null))
        if (!isBackgroundSprite) motionBrickList.add(IfOnEdgeBounceBrick())
        motionBrickList.add(MoveNStepsBrick(BrickValues.MOVE_STEPS))
        motionBrickList.add(TurnLeftBrick(BrickValues.TURN_DEGREES))
        motionBrickList.add(TurnRightBrick(BrickValues.TURN_DEGREES))
        motionBrickList.add(PointInDirectionBrick(BrickValues.POINT_IN_DIRECTION))
        motionBrickList.add(PointToBrick(null))
        motionBrickList.add(SetRotationStyleBrick())
        motionBrickList.add(
            GlideToBrick(
                BrickValues.X_POSITION,
                BrickValues.Y_POSITION,
                BrickValues.GLIDE_SECONDS
            )
        )
        if (!isBackgroundSprite) {
            motionBrickList.add(GoNStepsBackBrick(BrickValues.GO_BACK))
            motionBrickList.add(ComeToFrontBrick())
        }
        motionBrickList.add(SetCameraFocusPointBrick())
        motionBrickList.add(VibrationBrick(BrickValues.VIBRATE_SECONDS))
        motionBrickList.add(SetPhysicsObjectTypeBrick(BrickValues.PHYSIC_TYPE))
        if (!isBackgroundSprite) motionBrickList.add(WhenBounceOffBrick(WhenBounceOffScript(null)))
        motionBrickList.add(SetVelocityBrick(BrickValues.PHYSIC_VELOCITY))
        motionBrickList.add(TurnLeftSpeedBrick(BrickValues.PHYSIC_TURN_DEGREES))
        motionBrickList.add(TurnRightSpeedBrick(BrickValues.PHYSIC_TURN_DEGREES))
        motionBrickList.add(SetGravityBrick(BrickValues.PHYSIC_GRAVITY))
        motionBrickList.add(SetMassBrick(BrickValues.PHYSIC_MASS))
        motionBrickList.add(SetBounceBrick(BrickValues.PHYSIC_BOUNCE_FACTOR * BrickValues.PHYSIC_MULTIPLIER))
        motionBrickList.add(SetFrictionBrick(BrickValues.PHYSIC_FRICTION * BrickValues.PHYSIC_MULTIPLIER))
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
            motionBrickList.add(
                PhiroMotorMoveForwardBrick(
                    PhiroMotorMoveForwardBrick.Motor.MOTOR_LEFT,
                    BrickValues.PHIRO_SPEED
                )
            )
            motionBrickList.add(
                PhiroMotorMoveBackwardBrick(
                    PhiroMotorMoveBackwardBrick.Motor.MOTOR_LEFT,
                    BrickValues.PHIRO_SPEED
                )
            )
            motionBrickList.add(PhiroMotorStopBrick(PhiroMotorStopBrick.Motor.MOTOR_BOTH))
        }
        motionBrickList.add(FadeParticleEffectBrick())
        return motionBrickList
    }

    protected open fun setupSoundCategoryList(context: Context): List<Brick> {
        val soundBrickList: MutableList<Brick> = ArrayList()
        soundBrickList.add(PlaySoundBrick())
        soundBrickList.add(PlaySoundAndWaitBrick())
        soundBrickList.add(PlaySoundAtBrick(BrickValues.PLAY_AT_DEFAULT_OFFSET))
        soundBrickList.add(StopSoundBrick())
        soundBrickList.add(StopAllSoundsBrick())
        soundBrickList.add(SetVolumeToBrick(BrickValues.SET_VOLUME_TO))
        soundBrickList.add(ChangeVolumeByNBrick(Formula(BrickValues.CHANGE_VOLUME_BY)))
        if (SettingsFragment.isAISpeechSynthetizationSharedPreferenceEnabled(context)) {
            soundBrickList.add(SpeakBrick(context.getString(R.string.brick_speak_default_value)))
            soundBrickList.add(SpeakAndWaitBrick(context.getString(R.string.brick_speak_default_value)))
        }
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
            soundBrickList.add(
                PhiroPlayToneBrick(
                    PhiroPlayToneBrick.Tone.DO,
                    BrickValues.PHIRO_DURATION
                )
            )
        }
        if (SettingsFragment.isAISpeechRecognitionSharedPreferenceEnabled(context)) {
            soundBrickList.add(AskSpeechBrick(context.getString(R.string.brick_ask_speech_default_question)))
            soundBrickList.add(StartListeningBrick())
            soundBrickList.add(SetListeningLanguageBrick())
        }
        soundBrickList.add(SetInstrumentBrick())
        soundBrickList.add(
            PlayNoteForBeatsBrick(
                BrickValues.DEFAULT_NOTE,
                BrickValues.PAUSED_BEATS_INT
            )
        )
        soundBrickList.add(PlayDrumForBeatsBrick(BrickValues.PAUSED_BEATS_INT))
        soundBrickList.add(SetTempoBrick(BrickValues.DEFAULT_TEMPO))
        soundBrickList.add(ChangeTempoByNBrick(BrickValues.CHANGE_TEMPO))
        soundBrickList.add(PauseForBeatsBrick(BrickValues.PAUSED_BEATS_FLOAT))
        return soundBrickList
    }

    protected open fun setupLooksCategoryList(
        context: Context,
        isBackgroundSprite: Boolean
    ): List<Brick> {
        val looksBrickList = mutableListOf<Brick>()
        if (!isBackgroundSprite) {
            looksBrickList.add(SetLookBrick())
            looksBrickList.add(SetLookByIndexBrick(BrickValues.SET_LOOK_BY_INDEX))
        }
        looksBrickList.add(NextLookBrick())
        looksBrickList.add(PreviousLookBrick())
        looksBrickList.add(SetSizeToBrick(BrickValues.SET_SIZE_TO))
        looksBrickList.add(ChangeSizeByNBrick(BrickValues.CHANGE_SIZE_BY))
        looksBrickList.add(HideBrick())
        looksBrickList.add(ShowBrick())
        looksBrickList.add(AskBrick(context.getString(R.string.brick_ask_default_question)))
        if (!isBackgroundSprite) {
            looksBrickList.add(SayBubbleBrick(context.getString(R.string.brick_say_bubble_default_value)))
            looksBrickList.add(
                SayForBubbleBrick(
                    context.getString(R.string.brick_say_bubble_default_value),
                    1.0f
                )
            )
            looksBrickList.add(ThinkBubbleBrick(context.getString(R.string.brick_think_bubble_default_value)))
            looksBrickList.add(
                ThinkForBubbleBrick(
                    context.getString(R.string.brick_think_bubble_default_value),
                    1.0f
                )
            )
        }
        looksBrickList.add(ShowTextBrick(BrickValues.X_POSITION, BrickValues.Y_POSITION))
        looksBrickList.add(
            ShowTextColorSizeAlignmentBrick(
                BrickValues.X_POSITION,
                BrickValues.Y_POSITION,
                BrickValues.RELATIVE_SIZE_IN_PERCENT,
                BrickValues.SHOW_VARIABLE_COLOR
            )
        )
        looksBrickList.add(SetTransparencyBrick(BrickValues.SET_TRANSPARENCY))
        looksBrickList.add(ChangeTransparencyByNBrick(BrickValues.CHANGE_TRANSPARENCY_EFFECT))
        looksBrickList.add(SetBrightnessBrick(BrickValues.SET_BRIGHTNESS_TO))
        looksBrickList.add(ChangeBrightnessByNBrick(BrickValues.CHANGE_BRIGHTNESS_BY))
        looksBrickList.add(SetColorBrick(BrickValues.SET_COLOR_TO))
        looksBrickList.add(ChangeColorByNBrick(BrickValues.CHANGE_COLOR_BY))
        looksBrickList.add(FadeParticleEffectBrick())
        looksBrickList.add(ParticleEffectAdditivityBrick())
        looksBrickList.add(SetParticleColorBrick(BrickValues.PARTICLE_COLOR))
        looksBrickList.add(ClearGraphicEffectBrick())
        looksBrickList.add(SetCameraFocusPointBrick())
        looksBrickList.add(WhenBackgroundChangesBrick())
        looksBrickList.add(SetBackgroundBrick())
        looksBrickList.add(SetBackgroundByIndexBrick(BrickValues.SET_LOOK_BY_INDEX))
        looksBrickList.add(SetBackgroundAndWaitBrick())
        looksBrickList.add(SetBackgroundByIndexAndWaitBrick(BrickValues.SET_LOOK_BY_INDEX))
        if (!ProjectManager.getInstance().currentProject.isCastProject) {
            looksBrickList.add(CameraBrick())
            looksBrickList.add(ChooseCameraBrick())
            looksBrickList.add(FlashBrick())
        }
        when {
            !isBackgroundSprite -> looksBrickList.add(LookRequestBrick(BrickValues.LOOK_REQUEST))
            ProjectManager.getInstance().currentProject.xmlHeader.islandscapeMode() -> looksBrickList.add(
                BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST_LANDSCAPE)
            )

            else -> looksBrickList.add(BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST))
        }
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) looksBrickList.add(
            PhiroRGBLightBrick(
                PhiroRGBLightBrick.Eye.BOTH,
                BrickValues.PHIRO_VALUE_RED,
                BrickValues.PHIRO_VALUE_GREEN,
                BrickValues.PHIRO_VALUE_BLUE
            )
        )
        looksBrickList.add(PaintNewLookBrick(context.getString(R.string.brick_paint_new_look_name)))
        looksBrickList.add(EditLookBrick())
        looksBrickList.add(CopyLookBrick(context.getString(R.string.brick_copy_look_name)))
        looksBrickList.add(DeleteLookBrick())
        looksBrickList.add(OpenUrlBrick(BrickValues.OPEN_IN_BROWSER))
        return looksBrickList
    }

    private fun setupPenCategoryList(isBackgroundSprite: Boolean): List<Brick> {
        val penBrickList: MutableList<Brick> = ArrayList()
        if (!isBackgroundSprite) {
            penBrickList.add(PenDownBrick())
            penBrickList.add(PenUpBrick())
            penBrickList.add(SetPenSizeBrick(BrickValues.PEN_SIZE))
            penBrickList.add(
                SetPenColorBrick(
                    BrickValues.PEN_COLOR_R,
                    BrickValues.PEN_COLOR_G,
                    BrickValues.PEN_COLOR_B
                )
            )
            penBrickList.add(StampBrick())
        }
        penBrickList.add(ClearBackgroundBrick())
        return penBrickList
    }

    protected open fun setupDataCategoryList(
        context: Context,
        isBackgroundSprite: Boolean
    ): List<Brick> {
        val dataBrickList: MutableList<Brick> = ArrayList()
        dataBrickList.add(SetVariableBrick(BrickValues.SET_VARIABLE))
        dataBrickList.add(ChangeVariableBrick(BrickValues.CHANGE_VARIABLE))
        dataBrickList.add(ShowTextBrick(BrickValues.X_POSITION, BrickValues.Y_POSITION))
        dataBrickList.add(
            ShowTextColorSizeAlignmentBrick(
                BrickValues.X_POSITION,
                BrickValues.Y_POSITION,
                BrickValues.RELATIVE_SIZE_IN_PERCENT,
                BrickValues.SHOW_VARIABLE_COLOR
            )
        )
        dataBrickList.add(HideTextBrick())
        dataBrickList.add(WriteVariableOnDeviceBrick())
        dataBrickList.add(ReadVariableFromDeviceBrick())
        dataBrickList.add(WriteVariableToFileBrick(context.getString(R.string.brick_write_variable_to_file_default_value)))
        dataBrickList.add(ReadVariableFromFileBrick(context.getString(R.string.brick_write_variable_to_file_default_value)))
        dataBrickList.add(AddItemToUserListBrick(BrickValues.ADD_ITEM_TO_USERLIST))
        dataBrickList.add(DeleteItemOfUserListBrick(BrickValues.DELETE_ITEM_OF_USERLIST))
        dataBrickList.add(ClearUserListBrick())
        dataBrickList.add(
            InsertItemIntoUserListBrick(
                BrickValues.INSERT_ITEM_INTO_USERLIST_VALUE,
                BrickValues.INSERT_ITEM_INTO_USERLIST_INDEX
            )
        )
        dataBrickList.add(
            ReplaceItemInUserListBrick(
                BrickValues.REPLACE_ITEM_IN_USERLIST_VALUE,
                BrickValues.REPLACE_ITEM_IN_USERLIST_INDEX
            )
        )
        dataBrickList.add(WriteListOnDeviceBrick())
        dataBrickList.add(ReadListFromDeviceBrick())
        dataBrickList.add(
            StoreCSVIntoUserListBrick(
                BrickValues.STORE_CSV_INTO_USERLIST_COLUMN,
                context.getString(R.string.brick_store_csv_into_userlist_data)
            )
        )
        dataBrickList.add(WebRequestBrick(context.getString(R.string.brick_web_request_default_value)))
        when {
            !isBackgroundSprite -> dataBrickList.add(LookRequestBrick(BrickValues.LOOK_REQUEST))
            ProjectManager.getInstance().currentProject.xmlHeader.islandscapeMode() -> dataBrickList.add(
                BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST_LANDSCAPE)
            )

            else -> dataBrickList.add(BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST))
        }
        dataBrickList.add(AskBrick(context.getString(R.string.brick_ask_default_question)))
        if (SettingsFragment.isAISpeechRecognitionSharedPreferenceEnabled(context)) {
            dataBrickList.add(AskSpeechBrick(context.getString(R.string.brick_ask_speech_default_question)))
        }
        if (SettingsFragment.isEmroiderySharedPreferenceEnabled(context)) {
            dataBrickList.add(WriteEmbroideryToFileBrick(context.getString(R.string.brick_default_embroidery_file)))
        }
        if (SettingsFragment.isAISpeechRecognitionSharedPreferenceEnabled(context)) {
            dataBrickList.add(StartListeningBrick())
        }
        if (SettingsFragment.isNfcSharedPreferenceEnabled(context)) {
            dataBrickList.add(SetNfcTagBrick(context.getString(R.string.brick_set_nfc_tag_default_value)))
        }
        return dataBrickList
    }

    @SuppressWarnings("ComplexMethod")
    protected fun setupDeviceCategoryList(
        context: Context,
        isBackgroundSprite: Boolean
    ): List<Brick> {
        val deviceBrickList: MutableList<Brick> = ArrayList()
        deviceBrickList.add(ResetTimerBrick())
        deviceBrickList.add(WhenBrick())
        deviceBrickList.add(WhenTouchDownBrick())
        if (SettingsFragment.isNfcSharedPreferenceEnabled(context)) {
            deviceBrickList.add(WhenNfcBrick())
            deviceBrickList.add(SetNfcTagBrick(context.getString(R.string.brick_set_nfc_tag_default_value)))
        }
        deviceBrickList.add(WebRequestBrick(context.getString(R.string.brick_web_request_default_value)))
        when {
            !isBackgroundSprite -> deviceBrickList.add(LookRequestBrick(BrickValues.LOOK_REQUEST))
            ProjectManager.getInstance().currentProject.xmlHeader.islandscapeMode() -> deviceBrickList.add(
                BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST_LANDSCAPE)
            )

            else -> deviceBrickList.add(BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST))
        }
        deviceBrickList.add(OpenUrlBrick(BrickValues.OPEN_IN_BROWSER))
        deviceBrickList.add(VibrationBrick(BrickValues.VIBRATE_SECONDS))

        if (SettingsFragment.isAISpeechSynthetizationSharedPreferenceEnabled(context)) {
            deviceBrickList.add(SpeakBrick(context.getString(R.string.brick_speak_default_value)))
            deviceBrickList.add(SpeakAndWaitBrick(context.getString(R.string.brick_speak_default_value)))
        }

        if (SettingsFragment.isAISpeechRecognitionSharedPreferenceEnabled(context)) {
            deviceBrickList.add(AskSpeechBrick(context.getString(R.string.brick_ask_speech_default_question)))
            deviceBrickList.add(StartListeningBrick())
        }
        if (ProjectManager.getInstance().currentProject != null && !ProjectManager.getInstance().currentProject.isCastProject) {
            deviceBrickList.add(CameraBrick())
            deviceBrickList.add(ChooseCameraBrick())
            deviceBrickList.add(FlashBrick())
        }
        deviceBrickList.add(WriteVariableOnDeviceBrick())
        deviceBrickList.add(ReadVariableFromDeviceBrick())
        deviceBrickList.add(WriteVariableToFileBrick(context.getString(R.string.brick_write_variable_to_file_default_value)))
        deviceBrickList.add(ReadVariableFromFileBrick(context.getString(R.string.brick_write_variable_to_file_default_value)))
        deviceBrickList.add(WriteListOnDeviceBrick())
        deviceBrickList.add(ReadListFromDeviceBrick())
        deviceBrickList.add(TapAtBrick(BrickValues.TOUCH_X_START, BrickValues.TOUCH_Y_START))
        deviceBrickList.add(
            TapForBrick(
                BrickValues.TOUCH_X_START,
                BrickValues.TOUCH_Y_START,
                BrickValues.TOUCH_DURATION
            )
        )
        deviceBrickList.add(
            TouchAndSlideBrick(
                BrickValues.TOUCH_X_START,
                BrickValues.TOUCH_Y_START,
                BrickValues.TOUCH_X_GOAL,
                BrickValues.TOUCH_Y_GOAL,
                BrickValues.TOUCH_DURATION
            )
        )
        if (SettingsFragment.isCastSharedPreferenceEnabled(context)) deviceBrickList.addAll(
            setupChromecastCategoryList(context)
        )
        if (SettingsFragment.isMindstormsNXTSharedPreferenceEnabled(context)) deviceBrickList.addAll(
            setupLegoNxtCategoryList()
        )
        if (SettingsFragment.isMindstormsEV3SharedPreferenceEnabled(context)) deviceBrickList.addAll(
            setupLegoEv3CategoryList()
        )
        if (SettingsFragment.isDroneSharedPreferenceEnabled(context)) deviceBrickList.addAll(
            setupDroneCategoryList()
        )
        if (SettingsFragment.isJSSharedPreferenceEnabled(context)) deviceBrickList.addAll(
            setupJumpingSumoCategoryList()
        )
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) deviceBrickList.addAll(
            setupPhiroProCategoryList()
        )
        if (SettingsFragment.isArduinoSharedPreferenceEnabled(context)) deviceBrickList.addAll(
            setupArduinoCategoryList()
        )
        if (SettingsFragment.isRaspiSharedPreferenceEnabled(context)) deviceBrickList.addAll(
            setupRaspiCategoryList()
        )
        if (SettingsFragment.isEmroiderySharedPreferenceEnabled(context)) deviceBrickList.addAll(
            setupEmbroideryCategoryList(context)
        )
        return deviceBrickList
    }

    private fun setupLegoNxtCategoryList(): List<Brick> {
        val legoNXTBrickList: MutableList<Brick> = ArrayList()
        legoNXTBrickList.add(
            LegoNxtMotorTurnAngleBrick(
                LegoNxtMotorTurnAngleBrick.Motor.MOTOR_A,
                BrickValues.LEGO_ANGLE
            )
        )
        legoNXTBrickList.add(LegoNxtMotorStopBrick(LegoNxtMotorStopBrick.Motor.MOTOR_A))
        legoNXTBrickList.add(
            LegoNxtMotorMoveBrick(
                LegoNxtMotorMoveBrick.Motor.MOTOR_A,
                BrickValues.LEGO_SPEED
            )
        )
        legoNXTBrickList.add(
            LegoNxtPlayToneBrick(
                BrickValues.LEGO_FREQUENCY,
                BrickValues.LEGO_DURATION
            )
        )
        return legoNXTBrickList
    }

    private fun setupLegoEv3CategoryList(): List<Brick> {
        val legoEV3BrickList: MutableList<Brick> = ArrayList()
        legoEV3BrickList.add(
            LegoEv3MotorTurnAngleBrick(
                LegoEv3MotorTurnAngleBrick.Motor.MOTOR_A,
                BrickValues.LEGO_ANGLE
            )
        )
        legoEV3BrickList.add(
            LegoEv3MotorMoveBrick(
                LegoEv3MotorMoveBrick.Motor.MOTOR_A,
                BrickValues.LEGO_SPEED
            )
        )
        legoEV3BrickList.add(LegoEv3MotorStopBrick(LegoEv3MotorStopBrick.Motor.MOTOR_A))
        legoEV3BrickList.add(
            LegoEv3PlayToneBrick(
                BrickValues.LEGO_FREQUENCY,
                BrickValues.LEGO_DURATION,
                BrickValues.LEGO_VOLUME
            )
        )
        legoEV3BrickList.add(LegoEv3SetLedBrick(LegoEv3SetLedBrick.LedStatus.LED_GREEN))
        return legoEV3BrickList
    }

    private fun setupDroneCategoryList(): List<Brick> {
        val droneBrickList: MutableList<Brick> = ArrayList()
        droneBrickList.add(DroneTakeOffLandBrick())
        droneBrickList.add(DroneEmergencyBrick())
        droneBrickList.add(
            DroneMoveUpBrick(
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_TIME_MILLISECONDS,
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_POWER_PERCENT
            )
        )
        droneBrickList.add(
            DroneMoveDownBrick(
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_TIME_MILLISECONDS,
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_POWER_PERCENT
            )
        )
        droneBrickList.add(
            DroneMoveLeftBrick(
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_TIME_MILLISECONDS,
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_POWER_PERCENT
            )
        )
        droneBrickList.add(
            DroneMoveRightBrick(
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_TIME_MILLISECONDS,
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_POWER_PERCENT
            )
        )
        droneBrickList.add(
            DroneMoveForwardBrick(
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_TIME_MILLISECONDS,
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_POWER_PERCENT
            )
        )
        droneBrickList.add(
            DroneMoveBackwardBrick(
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_TIME_MILLISECONDS,
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_POWER_PERCENT
            )
        )
        droneBrickList.add(
            DroneTurnLeftBrick(
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_TIME_MILLISECONDS,
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_POWER_PERCENT
            )
        )
        droneBrickList.add(
            DroneTurnRightBrick(
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_TIME_MILLISECONDS,
                BrickValues.DRONE_MOVE_BRICK_DEFAULT_POWER_PERCENT
            )
        )
        droneBrickList.add(DroneFlipBrick())
        droneBrickList.add(DronePlayLedAnimationBrick())
        droneBrickList.add(DroneSwitchCameraBrick())
        return droneBrickList
    }

    private fun setupJumpingSumoCategoryList(): List<Brick> {
        val jumpingSumoBrickList: MutableList<Brick> = ArrayList()
        jumpingSumoBrickList.add(
            JumpingSumoMoveForwardBrick(
                BrickValues.JUMPING_SUMO_MOVE_BRICK_DEFAULT_TIME_MILLISECONDS,
                BrickValues.JUMPING_SUMO_MOVE_BRICK_DEFAULT_MOVE_POWER_PERCENT
            )
        )
        jumpingSumoBrickList.add(
            JumpingSumoMoveBackwardBrick(
                BrickValues.JUMPING_SUMO_MOVE_BRICK_DEFAULT_TIME_MILLISECONDS,
                BrickValues.JUMPING_SUMO_MOVE_BRICK_DEFAULT_MOVE_POWER_PERCENT
            )
        )
        jumpingSumoBrickList.add(JumpingSumoAnimationsBrick(JumpingSumoAnimationsBrick.Animation.SPIN))
        jumpingSumoBrickList.add(
            JumpingSumoSoundBrick(
                JumpingSumoSoundBrick.Sounds.DEFAULT,
                BrickValues.JUMPING_SUMO_SOUND_BRICK_DEFAULT_VOLUME_PERCENT
            )
        )
        jumpingSumoBrickList.add(JumpingSumoNoSoundBrick())
        jumpingSumoBrickList.add(JumpingSumoJumpLongBrick())
        jumpingSumoBrickList.add(JumpingSumoJumpHighBrick())
        jumpingSumoBrickList.add(JumpingSumoRotateLeftBrick(BrickValues.JUMPING_SUMO_ROTATE_DEFAULT_DEGREE))
        jumpingSumoBrickList.add(JumpingSumoRotateRightBrick(BrickValues.JUMPING_SUMO_ROTATE_DEFAULT_DEGREE))
        jumpingSumoBrickList.add(JumpingSumoTurnBrick())
        jumpingSumoBrickList.add(JumpingSumoTakingPictureBrick())
        return jumpingSumoBrickList
    }

    private fun setupPhiroProCategoryList(): List<Brick> {
        val phiroProBrickList: MutableList<Brick> = ArrayList()
        phiroProBrickList.add(
            PhiroMotorMoveForwardBrick(
                PhiroMotorMoveForwardBrick.Motor.MOTOR_LEFT,
                BrickValues.PHIRO_SPEED
            )
        )
        phiroProBrickList.add(
            PhiroMotorMoveBackwardBrick(
                PhiroMotorMoveBackwardBrick.Motor.MOTOR_LEFT,
                BrickValues.PHIRO_SPEED
            )
        )
        phiroProBrickList.add(PhiroMotorStopBrick(PhiroMotorStopBrick.Motor.MOTOR_BOTH))
        phiroProBrickList.add(
            PhiroPlayToneBrick(
                PhiroPlayToneBrick.Tone.DO,
                BrickValues.PHIRO_DURATION
            )
        )
        phiroProBrickList.add(
            PhiroRGBLightBrick(
                PhiroRGBLightBrick.Eye.BOTH,
                BrickValues.PHIRO_VALUE_RED,
                BrickValues.PHIRO_VALUE_GREEN,
                BrickValues.PHIRO_VALUE_BLUE
            )
        )
        phiroProBrickList.add(PhiroIfLogicBeginBrick())
        phiroProBrickList.add(SetVariableBrick(Sensors.PHIRO_FRONT_LEFT))
        phiroProBrickList.add(SetVariableBrick(Sensors.PHIRO_FRONT_RIGHT))
        phiroProBrickList.add(SetVariableBrick(Sensors.PHIRO_SIDE_LEFT))
        phiroProBrickList.add(SetVariableBrick(Sensors.PHIRO_SIDE_RIGHT))
        phiroProBrickList.add(SetVariableBrick(Sensors.PHIRO_BOTTOM_LEFT))
        phiroProBrickList.add(SetVariableBrick(Sensors.PHIRO_BOTTOM_RIGHT))
        return phiroProBrickList
    }

    private fun setupArduinoCategoryList(): List<Brick> {
        val arduinoBrickList: MutableList<Brick> = ArrayList()
        arduinoBrickList.add(
            ArduinoSendDigitalValueBrick(
                BrickValues.ARDUINO_DIGITAL_INITIAL_PIN_NUMBER,
                BrickValues.ARDUINO_DIGITAL_INITIAL_PIN_VALUE
            )
        )
        arduinoBrickList.add(
            ArduinoSendPWMValueBrick(
                BrickValues.ARDUINO_PWM_INITIAL_PIN_NUMBER,
                BrickValues.ARDUINO_PWM_INITIAL_PIN_VALUE
            )
        )
        return arduinoBrickList
    }

    private fun setupRaspiCategoryList(): List<Brick> {
        val defaultScript = RaspiInterruptScript("3", "pressed")
        val raspiBrickList: MutableList<Brick> = ArrayList()
        raspiBrickList.add(WhenRaspiPinChangedBrick(defaultScript))
        raspiBrickList.add(RaspiIfLogicBeginBrick(Formula(BrickValues.RASPI_DIGITAL_INITIAL_PIN_NUMBER)))
        raspiBrickList.add(
            RaspiSendDigitalValueBrick(
                BrickValues.RASPI_DIGITAL_INITIAL_PIN_NUMBER,
                BrickValues.RASPI_DIGITAL_INITIAL_PIN_VALUE
            )
        )
        raspiBrickList.add(
            RaspiPwmBrick(
                BrickValues.RASPI_DIGITAL_INITIAL_PIN_NUMBER,
                BrickValues.RASPI_PWM_INITIAL_FREQUENCY,
                BrickValues.RASPI_PWM_INITIAL_PERCENTAGE
            )
        )
        return raspiBrickList
    }

    private fun setupEmbroideryCategoryList(context: Context): List<Brick> {
        val embroideryBrickList: MutableList<Brick> = ArrayList()
        embroideryBrickList.add(StitchBrick())
        embroideryBrickList.add(SetThreadColorBrick(Formula(BrickValues.THREAD_COLOR)))
        embroideryBrickList.add(RunningStitchBrick(Formula(BrickValues.STITCH_LENGTH)))
        embroideryBrickList.add(
            ZigZagStitchBrick(
                Formula(BrickValues.ZIGZAG_STITCH_LENGTH),
                Formula(BrickValues.ZIGZAG_STITCH_WIDTH)
            )
        )
        embroideryBrickList.add(TripleStitchBrick(Formula(BrickValues.STITCH_LENGTH)))
        embroideryBrickList.add(SewUpBrick())
        embroideryBrickList.add(StopRunningStitchBrick())
        embroideryBrickList.add(WriteEmbroideryToFileBrick(context.getString(R.string.brick_default_embroidery_file)))
        embroideryBrickList.add(EmbroideryArcBrick(PlotArcBrick.Directions.LEFT, 10.0f, 360.0f))
        embroideryBrickList.add(EmbroideryThroughBrick(0, 0, 0, 0))
        return embroideryBrickList
    }

    private fun setupPlotCategoryList(context: Context): List<Brick> {
        val plotBrickList: MutableList<Brick> = ArrayList()
        plotBrickList.add(StartPlotBrick())
        plotBrickList.add(StopPlotBrick())
        plotBrickList.add(SavePlotBrick(context.getString(R.string.brick_default_plot_file)))
        plotBrickList.add(SharePlotBrick(context.getString(R.string.brick_default_plot_file)))
        plotBrickList.add(PlotArcBrick(PlotArcBrick.Directions.LEFT, 10.0f, 360.0f))
        plotBrickList.add(PlotThroughBrick(0, 0, 0, 0))
        return plotBrickList
    }

    private fun setupLaserCategoryList(context: Context): List<Brick> {
        val laserBrickList: MutableList<Brick> = ArrayList()
        laserBrickList.add(StartEngraveBrick())
        laserBrickList.add(StopEngraveBrick())
        laserBrickList.add(StartCutBrick())
        laserBrickList.add(StopCutBrick())
        laserBrickList.add(SaveLaserBrick(context.getString(R.string.brick_default_laser_file)))
        laserBrickList.add(ShareLaserBrick(context.getString(R.string.brick_default_laser_file)))
        laserBrickList.add(LaserArcBrick(PlotArcBrick.Directions.LEFT, 10.0f, 360.0f))
        laserBrickList.add(LaserThroughBrick(0, 0, 0, 0))
        return laserBrickList
    }

    private fun setupAssertionsCategoryList(context: Context): List<Brick> {
        val assertionsBrickList: MutableList<Brick> = ArrayList()
        assertionsBrickList.add(AssertEqualsBrick())
        assertionsBrickList.add(AssertUserListsBrick())
        assertionsBrickList.add(ParameterizedBrick())
        assertionsBrickList.add(WaitTillIdleBrick())
        assertionsBrickList.add(TapAtBrick(BrickValues.TOUCH_X_START, BrickValues.TOUCH_Y_START))
        assertionsBrickList.add(
            TapForBrick(
                BrickValues.TOUCH_X_START,
                BrickValues.TOUCH_Y_START,
                BrickValues.TOUCH_DURATION
            )
        )
        assertionsBrickList.add(
            TouchAndSlideBrick(
                BrickValues.TOUCH_X_START,
                BrickValues.TOUCH_Y_START,
                BrickValues.TOUCH_X_GOAL,
                BrickValues.TOUCH_Y_GOAL,
                BrickValues.TOUCH_DURATION
            )
        )
        assertionsBrickList.add(FinishStageBrick())
        assertionsBrickList.add(
            StoreCSVIntoUserListBrick(
                BrickValues.STORE_CSV_INTO_USERLIST_COLUMN,
                context.getString(R.string.brick_store_csv_into_userlist_data)
            )
        )
        assertionsBrickList.add(WebRequestBrick(context.getString(R.string.brick_web_request_default_value)))
        return assertionsBrickList
    }

    private fun searchList(searchBrick: Brick, list: List<Brick>): Boolean =
        list.any { it == searchBrick.javaClass }

    fun getBrickCategory(brick: Brick, isBackgroundSprite: Boolean, context: Context): String {
        val res = context.resources
        val config = res.configuration
        val savedLocale = config.locale
        config.locale = Locale.ENGLISH
        res.updateConfiguration(config, null)
        var category: String
        category = when {
            searchList(
                brick,
                setupControlCategoryList(context)
            ) -> res.getString(R.string.category_control)

            searchList(brick, setupEventCategoryList(context, isBackgroundSprite)) -> res.getString(
                R.string.category_event
            )

            searchList(
                brick,
                setupMotionCategoryList(context, isBackgroundSprite)
            ) -> res.getString(R.string.category_motion)

            searchList(
                brick,
                setupSoundCategoryList(context)
            ) -> res.getString(R.string.category_sound)

            searchList(brick, setupLooksCategoryList(context, isBackgroundSprite)) -> res.getString(
                R.string.category_looks
            )

            searchList(
                brick,
                setupPenCategoryList(isBackgroundSprite)
            ) -> res.getString(R.string.category_pen)

            searchList(
                brick,
                setupDataCategoryList(context, isBackgroundSprite)
            ) -> res.getString(R.string.category_data)

            searchList(
                brick,
                setupLegoNxtCategoryList()
            ) -> res.getString(R.string.category_lego_nxt)

            searchList(
                brick,
                setupLegoEv3CategoryList()
            ) -> res.getString(R.string.category_lego_ev3)

            searchList(
                brick,
                setupArduinoCategoryList()
            ) -> res.getString(R.string.category_arduino)

            searchList(brick, setupDroneCategoryList()) -> res.getString(R.string.category_drone)
            searchList(
                brick,
                setupJumpingSumoCategoryList()
            ) -> res.getString(R.string.category_jumping_sumo)

            searchList(brick, setupPhiroProCategoryList()) -> res.getString(R.string.category_phiro)
            searchList(brick, setupRaspiCategoryList()) -> res.getString(R.string.category_raspi)
            searchList(
                brick,
                setupChromecastCategoryList(context)
            ) -> res.getString(R.string.category_cast)

            searchList(
                brick,
                setupEmbroideryCategoryList(context)
            ) -> res.getString(R.string.category_embroidery)

            searchList(
                brick,
                setupAssertionsCategoryList(context)
            ) -> res.getString(R.string.category_assertions)

            else -> "No Match"
        }

        when (brick) {
            is AskBrick -> category = res.getString(R.string.category_looks)
            is AskSpeechBrick -> category = res.getString(R.string.category_sound)
            is LookRequestBrick -> category = res.getString(R.string.category_looks)
            is BackgroundRequestBrick -> category = res.getString(R.string.category_looks)
            is WhenClonedBrick -> category = res.getString(R.string.category_control)
            is WhenBackgroundChangesBrick -> category = res.getString(R.string.category_event)
            is SetVariableBrick -> category = res.getString(R.string.category_data)
            is WebRequestBrick -> category = res.getString(R.string.category_data)
            is StoreCSVIntoUserListBrick -> category = res.getString(R.string.category_data)
            is UserDefinedBrick -> category = res.getString(R.string.category_user_bricks)
            is UserDefinedReceiverBrick -> category = res.getString(R.string.category_user_bricks)
            is ParameterizedEndBrick -> category = res.getString(R.string.category_assertions)
            is WriteEmbroideryToFileBrick -> category = res.getString(R.string.category_embroidery)
        }

        config.locale = savedLocale
        res.updateConfiguration(config, null)
        return category
    }
}
