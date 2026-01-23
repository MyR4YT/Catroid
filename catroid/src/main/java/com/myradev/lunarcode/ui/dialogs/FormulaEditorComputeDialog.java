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
package com.myradev.lunarcode.ui.dialogs;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.R;
import com.myradev.lunarcode.bluetooth.base.BluetoothDevice;
import com.myradev.lunarcode.bluetooth.base.BluetoothDeviceService;
import com.myradev.lunarcode.common.CatroidService;
import com.myradev.lunarcode.common.ServiceProvider;
import com.myradev.lunarcode.content.Scope;
import com.myradev.lunarcode.content.bricks.Brick;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.formulaeditor.FormulaElement.ElementType;
import com.myradev.lunarcode.formulaeditor.SensorHandler;
import com.myradev.lunarcode.formulaeditor.SensorLoudness;
import com.myradev.lunarcode.utils.ShowTextUtils.AndroidStringProvider;

import androidx.appcompat.app.AlertDialog;

import static com.myradev.lunarcode.utils.NumberFormats.trimTrailingCharacters;

public class FormulaEditorComputeDialog extends AlertDialog implements SensorEventListener {

	private Formula formulaToCompute = null;
	private Context context;
	private TextView computeTextView;
	private Scope scope;

	public FormulaEditorComputeDialog(Context context, Scope scope) {
		super(context);
		this.context = context;
		this.scope = scope;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ProjectManager projectManager = ProjectManager.getInstance();
		if (projectManager.isCurrentProjectLandscapeMode()) {
			setContentView(R.layout.dialog_formulaeditor_compute_landscape);
			computeTextView = findViewById(R.id.formula_editor_compute_dialog_textview_landscape_mode);
		} else {
			setContentView(R.layout.dialog_formulaeditor_compute);
			computeTextView = findViewById(R.id.formula_editor_compute_dialog_textview);
		}
		AndroidStringProvider stringProvider = new AndroidStringProvider(context);
		showFormulaResult(scope, stringProvider);
	}

	public void setFormula(Formula formula) {
		formulaToCompute = formula;

		Brick.ResourcesSet resourcesSet = new Brick.ResourcesSet();
		formula.addRequiredResources(resourcesSet);

		if (resourcesSet.contains(Brick.MICROPHONE)) {
			SensorHandler.getInstance(getContext()).setSensorLoudness(new SensorLoudness());
		}

		if (resourcesSet.contains(Brick.BLUETOOTH_LEGO_NXT)) {
			BluetoothDeviceService btService = ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
			btService.connectDevice(BluetoothDevice.LEGO_NXT, this.getContext());
		}

		if (resourcesSet.contains(Brick.BLUETOOTH_LEGO_EV3)) {
			BluetoothDeviceService btService = ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
			btService.connectDevice(BluetoothDevice.LEGO_EV3, this.getContext());
		}

		if (resourcesSet.contains(Brick.BLUETOOTH_SENSORS_ARDUINO)) {
			BluetoothDeviceService btService = ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
			btService.connectDevice(BluetoothDevice.ARDUINO, this.getContext());
		}

		if (resourcesSet.contains(Brick.BLUETOOTH_PHIRO)) {
			BluetoothDeviceService btService = ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
			btService.connectDevice(BluetoothDevice.PHIRO, this.getContext());
		}

		if (formula.containsElement(ElementType.SENSOR)) {
			SensorHandler.startSensorListener(context);
			SensorHandler.registerListener(this);
		}
	}

	@Override
	protected void onStop() {
		SensorHandler.unregisterListener(this);
		ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE).pause();
		super.onStop();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		dismiss();
		return true;
	}

	private void showFormulaResult(Scope scope, Formula.StringProvider stringProvider) {
		if (computeTextView == null) {
			return;
		}

		String result = formulaToCompute.getUserFriendlyString(stringProvider, scope);
		setDialogTextView(trimTrailingCharacters(result));
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		AndroidStringProvider stringProvider = new AndroidStringProvider(context);
		showFormulaResult(scope, stringProvider);
	}

	private void setDialogTextView(final String newString) {
		computeTextView.post(() -> {
			computeTextView.setText(newString);

			ViewGroup.LayoutParams params = computeTextView.getLayoutParams();
			int height = computeTextView.getLineCount() * computeTextView.getLineHeight();
			int heightMargin = (int) (height * 0.5);
			params.width = ViewGroup.LayoutParams.MATCH_PARENT;
			params.height = height + heightMargin;
			computeTextView.setLayoutParams(params);
		});
	}
}
