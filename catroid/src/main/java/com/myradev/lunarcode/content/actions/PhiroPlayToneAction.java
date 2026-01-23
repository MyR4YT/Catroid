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
package com.myradev.lunarcode.content.actions;

import android.util.Log;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

import com.myradev.lunarcode.bluetooth.base.BluetoothDevice;
import com.myradev.lunarcode.bluetooth.base.BluetoothDeviceService;
import com.myradev.lunarcode.common.CatroidService;
import com.myradev.lunarcode.common.ServiceProvider;
import com.myradev.lunarcode.content.Scope;
import com.myradev.lunarcode.content.bricks.PhiroPlayToneBrick.Tone;
import com.myradev.lunarcode.devices.arduino.phiro.Phiro;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.formulaeditor.InterpretationException;

public class PhiroPlayToneAction extends TemporalAction {

	private Tone toneEnum;
	private Formula durationInSeconds;
	private Scope scope;

	private BluetoothDeviceService btService = ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);

	@Override
	protected void update(float percent) {
		int durationInterpretation;

		try {
			durationInterpretation = durationInSeconds.interpretInteger(scope);
		} catch (InterpretationException interpretationException) {
			durationInterpretation = 0;
			Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
		}

		Phiro phiro = btService.getDevice(BluetoothDevice.PHIRO);
		if (phiro == null) {
			return;
		}

		switch (toneEnum) {
			case DO:
				phiro.playTone(262, durationInterpretation);
				break;
			case RE:
				phiro.playTone(294, durationInterpretation);
				break;
			case MI:
				phiro.playTone(330, durationInterpretation);
				break;
			case FA:
				phiro.playTone(349, durationInterpretation);
				break;
			case SO:
				phiro.playTone(392, durationInterpretation);
				break;
			case LA:
				phiro.playTone(440, durationInterpretation);
				break;
			case TI:
				phiro.playTone(494, durationInterpretation);
				break;
		}
	}

	public void setSelectedTone(Tone toneEnum) {
		this.toneEnum = toneEnum;
	}

	public void setDurationInSeconds(Formula durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}
}
