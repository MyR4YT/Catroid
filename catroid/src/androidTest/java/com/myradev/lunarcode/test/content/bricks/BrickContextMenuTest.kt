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

package com.myradev.lunarcode.test.content.bricks

import com.myradev.lunarcode.R
import com.myradev.lunarcode.content.bricks.AssertEqualsBrick
import com.myradev.lunarcode.content.bricks.AssertUserListsBrick
import com.myradev.lunarcode.content.bricks.Brick
import com.myradev.lunarcode.content.bricks.EmptyEventBrick
import com.myradev.lunarcode.content.bricks.FadeParticleEffectBrick
import com.myradev.lunarcode.content.bricks.FlashBrick
import com.myradev.lunarcode.content.bricks.ForItemInUserListBrick
import com.myradev.lunarcode.content.bricks.ParticleEffectAdditivityBrick
import com.myradev.lunarcode.content.bricks.PenDownBrick
import com.myradev.lunarcode.content.bricks.ReadVariableFromDeviceBrick
import com.myradev.lunarcode.content.bricks.RepeatUntilBrick
import com.myradev.lunarcode.content.bricks.ScriptBrick
import com.myradev.lunarcode.content.bricks.SetInstrumentBrick
import com.myradev.lunarcode.content.bricks.SetVariableBrick
import com.myradev.lunarcode.content.bricks.SetVolumeToBrick
import com.myradev.lunarcode.content.bricks.SetXBrick
import com.myradev.lunarcode.content.bricks.UserDefinedBrick
import com.myradev.lunarcode.content.bricks.UserDefinedReceiverBrick
import com.myradev.lunarcode.content.bricks.WhenBounceOffBrick
import com.myradev.lunarcode.content.bricks.WhenBrick
import com.myradev.lunarcode.content.bricks.WhenClonedBrick
import com.myradev.lunarcode.content.bricks.WhenGamepadButtonBrick
import com.myradev.lunarcode.content.bricks.WhenNfcBrick
import com.myradev.lunarcode.content.bricks.WhenRaspiPinChangedBrick
import com.myradev.lunarcode.content.bricks.WhenStartedBrick
import com.myradev.lunarcode.content.bricks.WhenTouchDownBrick
import com.myradev.lunarcode.ui.recyclerview.fragment.ScriptFragment.getContextMenuItems
import com.myradev.lunarcode.userbrick.UserDefinedBrickInput
import com.myradev.lunarcode.userbrick.UserDefinedBrickLabel
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BrickContextMenuTest(
    private val brick: Brick,
    private val expectedEditFormula: Boolean,
    private val expectedHighlight: Boolean
) {

    private val contextMenuItems: MutableList<Int> = ArrayList()

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun parameters() = listOf(
            arrayOf(AssertUserListsBrick(), false, false),
            arrayOf(AssertEqualsBrick(), true, false),
            arrayOf(UserDefinedReceiverBrick(), false, false),
            arrayOf(UserDefinedBrick(), false, false),
            arrayOf(UserDefinedBrick(listOf(UserDefinedBrickInput(""))), true, false),
            arrayOf(UserDefinedBrick(listOf(UserDefinedBrickLabel(""))), false, false),
            arrayOf(ForItemInUserListBrick(), false, true),
            arrayOf(RepeatUntilBrick(), true, true),
            arrayOf(WhenStartedBrick(), false, false),
            arrayOf(SetXBrick(), true, false),
            arrayOf(SetVariableBrick(), true, false),
            arrayOf(PenDownBrick(), false, false),
            arrayOf(SetVolumeToBrick(), true, false),
            arrayOf(SetInstrumentBrick(), false, false),
            arrayOf(FadeParticleEffectBrick(), false, false),
            arrayOf(ParticleEffectAdditivityBrick(), false, false),
            arrayOf(FlashBrick(), false, false),
            arrayOf(ReadVariableFromDeviceBrick(), false, false),
            arrayOf(EmptyEventBrick(), false, false),
            arrayOf(WhenBounceOffBrick(), false, false),
            arrayOf(WhenBrick(), false, false),
            arrayOf(WhenClonedBrick(), false, false),
            arrayOf(WhenGamepadButtonBrick(), false, false),
            arrayOf(WhenNfcBrick(), false, false),
            arrayOf(WhenRaspiPinChangedBrick(), false, false),
            arrayOf(WhenTouchDownBrick(), false, false)
            )
    }

    @Before
    fun setUp() {
        contextMenuItems.addAll(getContextMenuItems(brick))
    }

    @After
    fun tearDown() {
        contextMenuItems.clear()
    }

    @Test
    fun testEditFormula() {
        assertEquals(contextMenuItems.contains(R.string.brick_context_dialog_formula_edit_brick), expectedEditFormula)
    }

    @Test
    fun testHighlightBrickParts() {
        assertEquals(contextMenuItems.contains(R.string.brick_context_dialog_highlight_brick_parts), expectedHighlight)
    }

    @Test
    fun testExpectedDelete() {
        val showsDelete: Boolean = when (brick) {
            is UserDefinedReceiverBrick -> contextMenuItems.contains(R.string.brick_context_dialog_delete_definition)
            is ScriptBrick -> contextMenuItems.contains(R.string.brick_context_dialog_delete_script)
            else -> contextMenuItems.contains(R.string.brick_context_dialog_delete_brick)
        }
        assertTrue(showsDelete)
    }

    @Test
    fun testExpectedCommentOut() {
        val showsCommentOut: Boolean = when (brick) {
            is UserDefinedReceiverBrick, is EmptyEventBrick -> !contextMenuItems.contains(R.string.brick_context_dialog_comment_out_script)
            is ScriptBrick -> contextMenuItems.contains(R.string.brick_context_dialog_comment_out_script)
            else -> contextMenuItems.contains(R.string.brick_context_dialog_comment_out)
        }
        assertTrue(showsCommentOut)
    }

    @Test
    fun testExpectedCommentIn() {
        brick.isCommentedOut = true
        contextMenuItems.addAll(getContextMenuItems(brick))
        val showsCommentIn: Boolean = when (brick) {
            is UserDefinedReceiverBrick, is EmptyEventBrick -> !contextMenuItems.contains(R.string.brick_context_dialog_comment_in)
            is ScriptBrick -> contextMenuItems.contains(R.string.brick_context_dialog_comment_in_script)
            else -> contextMenuItems.contains(R.string.brick_context_dialog_comment_in)
        }
        assertTrue(showsCommentIn)
    }

    @Test
    fun testExpectedCopy() {
        val showsCopy: Boolean = when (brick) {
            is UserDefinedReceiverBrick -> !contextMenuItems.contains(R.string.brick_context_dialog_copy_script)
            is ScriptBrick -> contextMenuItems.contains(R.string.brick_context_dialog_copy_script)
            else -> contextMenuItems.contains(R.string.brick_context_dialog_copy_brick)
        }
        assertTrue(showsCopy)
    }

    @Test
    fun testExpectedHelp() {
        assertTrue(contextMenuItems.contains(R.string.brick_context_dialog_help))
    }

    @Test
    fun testExpectedAddToBackpack() {
        val showsAddToBackpack: Boolean = when (brick) {
            is UserDefinedReceiverBrick, is ScriptBrick -> contextMenuItems.contains(R.string.backpack_add)
            else -> !contextMenuItems.contains(R.string.backpack_add)
        }
        assertTrue(showsAddToBackpack)
    }
}
