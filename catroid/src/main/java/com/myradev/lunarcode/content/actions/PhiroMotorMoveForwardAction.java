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
import com.myradev.lunarcode.content.bricks.PhiroMotorMoveForwardBrick.Motor;
import com.myradev.lunarcode.devices.arduino.phiro.Phiro;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.formulaeditor.InterpretationException;

public class PhiroMotorMoveForwardAction extends TemporalAction {
	private static final int MIN_SPEED = 0;
	private static final int MAX_SPEED = 100;

	private Motor motorEnum;
	private Formula speed;
	private Scope scope;

	private BluetoothDeviceService btService = ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);

	@Override
	protected void update(float percent) {
		int speedValue;
		try {
			speedValue = speed.interpretInteger(scope);
		} catch (InterpretationException interpretationException) {
			speedValue = 0;
			Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
		}

		if (speedValue < MIN_SPEED) {
			speedValue = MIN_SPEED;
		} else if (speedValue > MAX_SPEED) {
			speedValue = MAX_SPEED;
		}

		Phiro phiro = btService.getDevice(BluetoothDevice.PHIRO);
		if (phiro == null) {
			return;
		}

		switch (motorEnum) {
			case MOTOR_LEFT:
				phiro.moveLeftMotorForward(speedValue);
				break;
			case MOTOR_RIGHT:
				phiro.moveRightMotorForward(speedValue);
				break;
			case MOTOR_BOTH:
				phiro.moveRightMotorForward(speedValue);
				phiro.moveLeftMotorForward(speedValue);
				break;
		}
	}

	public void setMotorEnum(Motor motorEnum) {
		this.motorEnum = motorEnum;
	}

	public void setSpeed(Formula speed) {
		this.speed = speed;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}
}
