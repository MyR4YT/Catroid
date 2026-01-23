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
package com.myradev.lunarcode.content.bricks;

import android.content.Context;
import android.view.View;

import com.myradev.lunarcode.R;
import com.myradev.lunarcode.common.Nameable;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.actions.ScriptSequenceAction;
import com.myradev.lunarcode.content.bricks.brickspinner.BrickSpinner;
import com.myradev.lunarcode.content.bricks.brickspinner.PickableDrum;
import com.myradev.lunarcode.formulaeditor.Formula;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class PlayDrumForBeatsBrick extends FormulaBrick implements BrickSpinner.OnItemSelectedListener<PickableDrum> {

	private PickableDrum drumSelection = PickableDrum.values()[0];

	@Override
	public int getViewResource() {
		return R.layout.brick_play_drum_for_beats;
	}

	protected int getSpinnerId() {
		return R.id.play_drum_for_beats_spinner;
	}

	public PlayDrumForBeatsBrick() {
		addAllowedBrickField(BrickField.PLAY_DRUM, R.id.brick_play_drum_for_beats_edit_text);
	}

	public PlayDrumForBeatsBrick(int value) {
		this(new Formula(value));
	}

	private PlayDrumForBeatsBrick(Formula formula) {
		this();
		setFormulaWithBrickField(BrickField.PLAY_DRUM, formula);
	}

	@Override
	public View getView(Context context) {
		super.getView(context);
		List<Nameable> items = new ArrayList<>();

		for (PickableDrum drum : PickableDrum.values()) {
			drum.setName(drum.getString(context));
			items.add(drum);
		}

		BrickSpinner<PickableDrum> spinner = new BrickSpinner<>(R.id.play_drum_for_beats_spinner, view, items);
		spinner.setSelection(PickableDrum.getIndexByValue(drumSelection.getValue()));
		spinner.setOnItemSelectedListener(this);

		return view;
	}

	@Override
	public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
		sequence.addAction(sprite.getActionFactory().createPlayDrumForBeatsAction(sprite, sequence,
				getFormulaWithBrickField(BrickField.PLAY_DRUM), drumSelection));
	}

	@Override
	public void onNewOptionSelected(Integer spinnerId) {
	}

	@Override
	public void onEditOptionSelected(Integer spinnerId) {
	}

	@Override
	public void onStringOptionSelected(Integer spinnerId, String string) {
	}

	@Override
	public void onItemSelected(Integer spinnerId, @Nullable PickableDrum item) {
		if (item != null) {
			drumSelection = item;
		}
	}
}
