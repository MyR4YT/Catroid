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
package com.myradev.lunarcode.content;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.common.LookData;
import com.myradev.lunarcode.content.bricks.ScriptBrick;
import com.myradev.lunarcode.content.bricks.WhenBackgroundChangesBrick;
import com.myradev.lunarcode.content.eventids.EventId;
import com.myradev.lunarcode.content.eventids.SetLookEventId;

public class WhenBackgroundChangesScript extends Script {

	private static final long serialVersionUID = 1L;

	private LookData look;

	@Override
	public ScriptBrick getScriptBrick() {
		if (scriptBrick == null) {
			scriptBrick = new WhenBackgroundChangesBrick(this);
		}
		return scriptBrick;
	}

	public LookData getLook() {
		return look;
	}

	public void setLook(LookData look) {
		this.look = look;
	}

	@Override
	public EventId createEventId(Sprite sprite) {
		Sprite background = ProjectManager.getInstance().getCurrentlyPlayingScene().getBackgroundSprite();
		return new SetLookEventId(background, look);
	}
}
