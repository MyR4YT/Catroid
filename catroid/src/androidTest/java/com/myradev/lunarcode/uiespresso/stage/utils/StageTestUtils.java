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

package com.myradev.lunarcode.uiespresso.stage.utils;

import com.myradev.lunarcode.content.BroadcastScript;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.bricks.SetVariableBrick;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.formulaeditor.UserVariable;

public final class StageTestUtils {

	private StageTestUtils() {
		throw new AssertionError();
	}

	public static void addBroadcastScriptSettingUserVariableToSprite(Sprite sprite, String message,
			UserVariable userVariable, double value) {
		Script broadcastScript = new BroadcastScript(message);
		SetVariableBrick setVariableBrickAfterBroadcast = new SetVariableBrick(new Formula(value), userVariable);
		broadcastScript.addBrick(setVariableBrickAfterBroadcast);
		sprite.addScript(broadcastScript);
	}
}
