/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2026 The Catrobat Team
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
/*
package com.myradev.lunarcode.test.content.bricks

import com.badlogic.gdx.utils.GdxNativesLoader
import com.myradev.lunarcode.common.BrickValues
import com.myradev.lunarcode.content.Script
import com.myradev.lunarcode.content.Sprite
import com.myradev.lunarcode.content.StartScript
import com.myradev.lunarcode.content.UserDefinedScript
import com.myradev.lunarcode.content.bricks.Brick
import com.myradev.lunarcode.content.bricks.ChangeBrightnessByNBrick
import com.myradev.lunarcode.content.bricks.ChangeColorByNBrick
import com.myradev.lunarcode.content.bricks.ChangeSizeByNBrick
import com.myradev.lunarcode.content.bricks.ChangeTransparencyByNBrick
import com.myradev.lunarcode.content.bricks.ChangeVolumeByNBrick
import com.myradev.lunarcode.content.bricks.ChangeXByNBrick
import com.myradev.lunarcode.content.bricks.ChangeYByNBrick
import com.myradev.lunarcode.content.bricks.GoToBrick
import com.myradev.lunarcode.content.bricks.IfOnEdgeBounceBrick
import com.myradev.lunarcode.content.bricks.IfThenLogicBeginBrick
import com.myradev.lunarcode.content.bricks.MoveNStepsBrick
import com.myradev.lunarcode.content.bricks.NextLookBrick
import com.myradev.lunarcode.content.bricks.ParameterizedBrick
import com.myradev.lunarcode.content.bricks.PlaceAtBrick
import com.myradev.lunarcode.content.bricks.PointInDirectionBrick
import com.myradev.lunarcode.content.bricks.PointToBrick
import com.myradev.lunarcode.content.bricks.PreviousLookBrick
import com.myradev.lunarcode.content.bricks.RepeatBrick
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
import com.myradev.lunarcode.formulaeditor.Formula
import com.myradev.lunarcode.utils.LoopUtil
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate

@RunWith(PowerMockRunner::class)
@PrepareForTest(GdxNativesLoader::class, ParameterizedBrick::class)
@PowerMockRunnerDelegate(Parameterized::class)
internal class LoopDelayBricksTest(private val brick: Brick?) {

    private val REPEAT_TIMES = 3
    private lateinit var script: Script
    private lateinit var repeatBrickInner: RepeatBrick
    private lateinit var repeatBrickOuter: RepeatBrick
    private lateinit var conditionBrick: IfThenLogicBeginBrick

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data(): List<Array<out Any?>> {
            return listOf(
                arrayOf(PlaceAtBrick(0, 0)),
                arrayOf(SetXBrick(0)),
                arrayOf(SetYBrick(0)),
                arrayOf(ChangeXByNBrick(0)),
                arrayOf(ChangeYByNBrick(0)),
                arrayOf(GoToBrick()),
                arrayOf(IfOnEdgeBounceBrick()),
                arrayOf(MoveNStepsBrick(0.0)),
                arrayOf(TurnLeftBrick(0.0)),
                arrayOf(TurnRightBrick(0.0)),
                arrayOf(PointInDirectionBrick(0.0)),
                arrayOf(PointToBrick(Sprite())),
                arrayOf(SetLookBrick()),
                arrayOf(SetLookByIndexBrick(0)),
                arrayOf(NextLookBrick()),
                arrayOf(PreviousLookBrick()),
                arrayOf(SetSizeToBrick(0.0)),
                arrayOf(ChangeSizeByNBrick(0.0)),
                arrayOf(SetTransparencyBrick(0.0)),
                arrayOf(ChangeTransparencyByNBrick(0.0)),
                arrayOf(SetBrightnessBrick(0.0)),
                arrayOf(ChangeBrightnessByNBrick(0.0)),
                arrayOf(SetColorBrick(0.0)),
                arrayOf(ChangeColorByNBrick(0.0)),
                arrayOf(SetBackgroundBrick()),
                arrayOf(SetBackgroundByIndexBrick(0)),
                arrayOf(SetVolumeToBrick(0.0)),
                arrayOf(ChangeVolumeByNBrick(0.0)),
                arrayOf(SetTempoBrick(0))
            )
        }
    }

    @Before
    fun setUp() {
        repeatBrickInner = RepeatBrick(Formula(REPEAT_TIMES))
        repeatBrickOuter = RepeatBrick(Formula(REPEAT_TIMES))
        conditionBrick = IfThenLogicBeginBrick()
        if (brick is GoToBrick) {
            brick.onItemSelected(BrickValues.GO_TO_RANDOM_POSITION, null)
        }
    }

    @Test
    fun testLoopDelayOnly() {
        script = StartScript()
        repeatBrickInner.addBrick(brick)
        assert(LoopUtil.checkLoopBrickForLoopDelay(repeatBrickInner, script))
    }

    @Test
    fun testLoopDelayWithInnerLoop() {
        script = StartScript()
        repeatBrickOuter.addBrick(repeatBrickInner)
        repeatBrickInner.addBrick(brick)
        assert(LoopUtil.checkLoopBrickForLoopDelay(repeatBrickOuter, script))
    }

    @Test
    fun testLoopDelayWithInnerCondition() {
        script = StartScript()
        repeatBrickOuter.addBrick(conditionBrick)
        conditionBrick.addBrick(brick)
        assert(LoopUtil.checkLoopBrickForLoopDelay(repeatBrickOuter, script))
    }

    @Test
    fun testLoopDelayInUserDefinedBrickWithoutScreenRefreshingOnly() {
        script = UserDefinedScript()
        (script as UserDefinedScript).screenRefresh = false
        repeatBrickInner.addBrick(brick)
        assert(!LoopUtil.checkLoopBrickForLoopDelay(repeatBrickInner, script))
    }

    @Test
    fun testLoopDelayInUserDefinedBrickWithScreenRefreshingOnly() {
        script = UserDefinedScript()
        (script as UserDefinedScript).screenRefresh = true
        repeatBrickInner.addBrick(brick)
        assert(LoopUtil.checkLoopBrickForLoopDelay(repeatBrickInner, script))
    }
}
*/