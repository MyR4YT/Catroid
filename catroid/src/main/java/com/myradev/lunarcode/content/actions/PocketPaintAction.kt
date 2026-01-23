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

package com.myradev.lunarcode.content.actions

import com.badlogic.gdx.scenes.scene2d.Action
import com.myradev.lunarcode.content.Scope
import com.myradev.lunarcode.formulaeditor.Formula
import com.myradev.lunarcode.io.XstreamSerializer
import com.myradev.lunarcode.stage.StageActivity

abstract class PocketPaintAction : Action(), StageActivity.IntentListener {
    var formula: Formula? = null
    var scope: Scope? = null
    protected var responseReceived = false
    protected var questionAsked = false
    protected var nextLookAction: SetNextLookAction? = null
    protected var xstreamSerializer: XstreamSerializer = XstreamSerializer.getInstance()

    override fun restart() {
        questionAsked = false
        responseReceived = false
        super.restart()
    }

    fun nextLookAction(nextLookAction: SetNextLookAction) {
        this.nextLookAction = nextLookAction
    }
}
