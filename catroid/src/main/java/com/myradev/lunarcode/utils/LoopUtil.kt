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

package com.myradev.lunarcode.utils

import com.myradev.lunarcode.ProjectManager
import com.myradev.lunarcode.content.Script
import com.myradev.lunarcode.content.UserDefinedScript
import com.myradev.lunarcode.content.bricks.Brick
import com.myradev.lunarcode.content.bricks.BrickBaseType
import com.myradev.lunarcode.content.bricks.ChangeBrightnessByNBrick
import com.myradev.lunarcode.content.bricks.ChangeColorByNBrick
import com.myradev.lunarcode.content.bricks.ChangeSizeByNBrick
import com.myradev.lunarcode.content.bricks.ChangeTransparencyByNBrick
import com.myradev.lunarcode.content.bricks.ChangeVolumeByNBrick
import com.myradev.lunarcode.content.bricks.ChangeXByNBrick
import com.myradev.lunarcode.content.bricks.ChangeYByNBrick
import com.myradev.lunarcode.content.bricks.CompositeBrick
import com.myradev.lunarcode.content.bricks.GoToBrick
import com.myradev.lunarcode.content.bricks.IfOnEdgeBounceBrick
import com.myradev.lunarcode.content.bricks.MoveNStepsBrick
import com.myradev.lunarcode.content.bricks.NextLookBrick
import com.myradev.lunarcode.content.bricks.PlaceAtBrick
import com.myradev.lunarcode.content.bricks.PointInDirectionBrick
import com.myradev.lunarcode.content.bricks.PointToBrick
import com.myradev.lunarcode.content.bricks.PreviousLookBrick
import com.myradev.lunarcode.content.bricks.SetBackgroundBrick
import com.myradev.lunarcode.content.bricks.SetBackgroundByIndexBrick
import com.myradev.lunarcode.content.bricks.SetBrightnessBrick
import com.myradev.lunarcode.content.bricks.SetColorBrick
import com.myradev.lunarcode.content.bricks.SetLookBrick
import com.myradev.lunarcode.content.bricks.SetLookByIndexBrick
import com.myradev.lunarcode.content.bricks.SetSizeToBrick
import com.myradev.lunarcode.content.bricks.SetTempoBrick
import com.myradev.lunarcode.content.bricks.SetTransparencyBrick
import com.myradev.lunarcode.content.bricks.SetVolumeToBrick
import com.myradev.lunarcode.content.bricks.SetXBrick
import com.myradev.lunarcode.content.bricks.SetYBrick
import com.myradev.lunarcode.content.bricks.TurnLeftBrick
import com.myradev.lunarcode.content.bricks.TurnRightBrick
import java.util.ArrayList
import kotlin.reflect.KClass

object LoopUtil {
    private val loopDelayBricks: List<KClass<out BrickBaseType>> = listOf(
        PlaceAtBrick::class, SetXBrick::class, SetYBrick::class, ChangeXByNBrick::class,
        ChangeYByNBrick::class, GoToBrick::class, IfOnEdgeBounceBrick::class,
        MoveNStepsBrick::class, TurnLeftBrick::class, TurnRightBrick::class,
        PointInDirectionBrick::class, PointToBrick::class, SetLookBrick::class,
        SetLookByIndexBrick::class, NextLookBrick::class, PreviousLookBrick::class,
        SetSizeToBrick::class, ChangeSizeByNBrick::class, SetTransparencyBrick::class,
        ChangeTransparencyByNBrick::class, SetBrightnessBrick::class,
        ChangeBrightnessByNBrick::class, SetColorBrick::class, ChangeColorByNBrick::class,
        SetBackgroundBrick::class, SetBackgroundByIndexBrick::class,
        SetVolumeToBrick::class, ChangeVolumeByNBrick::class, SetTempoBrick::class)

    @JvmStatic
    fun checkLoopBrickForLoopDelay(loopBrick: CompositeBrick, script: Script): Boolean {
        val allNestedBricks: List<Brick> = ArrayList()
        loopBrick.addToFlatList(allNestedBricks)

        if (script is UserDefinedScript && !script.screenRefresh) {
            return false
        }
        for (brick in allNestedBricks.filter { b -> !b.isCommentedOut }) {
            if (loopDelayBricks.contains(brick::class)) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun isAnyStitchRunning(): Boolean {
        ProjectManager.getInstance() ?: return false
        ProjectManager.getInstance().currentProject ?: return false
        ProjectManager.getInstance().currentProject.spriteListWithClones?.forEach {
            if (it.runningStitch.isRunning) {
                return true
            }
        }
        return false
    }
}
