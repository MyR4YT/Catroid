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

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.R;
import com.myradev.lunarcode.common.Nameable;
import com.myradev.lunarcode.common.SoundInfo;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.actions.ScriptSequenceAction;
import com.myradev.lunarcode.content.bricks.brickspinner.BrickSpinner;
import com.myradev.lunarcode.content.bricks.brickspinner.NewOption;
import com.myradev.lunarcode.ui.SpriteActivity;
import com.myradev.lunarcode.ui.UiUtils;
import com.myradev.lunarcode.ui.recyclerview.dialog.dialoginterface.NewItemInterface;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PlaySoundBrick extends BrickBaseType implements BrickSpinner.OnItemSelectedListener<SoundInfo>,
		NewItemInterface<SoundInfo> {

	private static final long serialVersionUID = 1L;

	protected SoundInfo sound;

	private transient BrickSpinner<SoundInfo> spinner;

	public PlaySoundBrick() {
	}

	public SoundInfo getSound() {
		return sound;
	}

	public void setSound(SoundInfo sound) {
		this.sound = sound;
	}

	@Override
	public Brick clone() throws CloneNotSupportedException {
		PlaySoundBrick clone = (PlaySoundBrick) super.clone();
		clone.spinner = null;
		return clone;
	}

	@Override
	public int getViewResource() {
		return R.layout.brick_play_sound;
	}

	@Override
	public View getView(Context context) {
		super.getView(context);
		onViewCreated(view);

		List<Nameable> items = new ArrayList<>();
		items.add(new NewOption(context.getString(R.string.new_option)));
		items.addAll(ProjectManager.getInstance().getCurrentSprite().getSoundList());
		spinner = new BrickSpinner<>(R.id.brick_play_sound_spinner, view, items);
		spinner.setOnItemSelectedListener(this);
		spinner.setSelection(sound);

		return view;
	}

	protected void onViewCreated(View view) {
	}

	@Override
	public void onNewOptionSelected(Integer spinnerId) {
		AppCompatActivity activity = UiUtils.getActivityFromView(view);
		if (!(activity instanceof SpriteActivity)) {
			return;
		}
		((SpriteActivity) activity).registerOnNewSoundListener(this);
		((SpriteActivity) activity).handleAddSoundButton();
	}

	@Override
	public void addItem(SoundInfo item) {
		spinner.add(item);
		spinner.setSelection(item);
	}

	@Override
	public void onEditOptionSelected(Integer spinnerId) {
	}

	@Override
	public void onStringOptionSelected(Integer spinnerId, String string) {
	}

	@Override
	public void onItemSelected(Integer spinnerId, @Nullable SoundInfo item) {
		sound = item;
	}

	@Override
	public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
		sequence.addAction(sprite.getActionFactory().createPlaySoundAction(sprite, sound));
	}
}
