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
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Scene;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.actions.ScriptSequenceAction;
import com.myradev.lunarcode.content.bricks.brickspinner.BrickSpinner;
import com.myradev.lunarcode.content.bricks.brickspinner.NewOption;
import com.myradev.lunarcode.ui.UiUtils;
import com.myradev.lunarcode.ui.recyclerview.controller.SceneController;
import com.myradev.lunarcode.ui.recyclerview.dialog.TextInputDialog;
import com.myradev.lunarcode.ui.recyclerview.dialog.textwatcher.DuplicateInputTextWatcher;
import com.myradev.lunarcode.ui.recyclerview.util.UniqueNameProvider;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SceneTransitionBrick extends BrickBaseType implements BrickSpinner.OnItemSelectedListener<Scene> {

	private static final long serialVersionUID = 1L;

	private String sceneForTransition;

	private transient BrickSpinner<Scene> spinner;

	public SceneTransitionBrick() {
		sceneForTransition = "";
	}

	public SceneTransitionBrick(String scene) {
		this.sceneForTransition = scene;
	}

	public String getSceneForTransition() {
		return sceneForTransition;
	}

	public void setSceneForTransition(String sceneForTransition) {
		this.sceneForTransition = sceneForTransition;
	}

	@Override
	public Brick clone() throws CloneNotSupportedException {
		SceneTransitionBrick clone = (SceneTransitionBrick) super.clone();
		clone.spinner = null;
		return clone;
	}

	@Override
	public int getViewResource() {
		return R.layout.brick_scene_transition;
	}

	@Override
	public View getView(final Context context) {
		super.getView(context);

		List<Nameable> items = new ArrayList<>();
		items.add(new NewOption(context.getString(R.string.new_option)));
		items.addAll(ProjectManager.getInstance().getCurrentProject().getSceneList());
		items.remove(ProjectManager.getInstance().getCurrentlyEditedScene());
		spinner = new BrickSpinner<>(R.id.brick_scene_transition_spinner, view, items);
		spinner.setOnItemSelectedListener(this);
		spinner.setSelection(sceneForTransition);

		return view;
	}

	@Override
	public void onNewOptionSelected(Integer spinnerId) {
		AppCompatActivity activity = UiUtils.getActivityFromView(view);
		if (activity == null) {
			return;
		}

		Project currentProject = ProjectManager.getInstance().getCurrentProject();
		List<Scene> currentSceneList = currentProject.getSceneList();

		String defaultSceneName =
				new UniqueNameProvider().getUniqueNameInNameables(activity.getResources().getString(R.string.default_scene_name), currentSceneList);

		TextInputDialog.Builder builder = new TextInputDialog.Builder(activity);

		builder.setHint(activity.getString(R.string.scene_name_label))
				.setText(defaultSceneName)
				.setTextWatcher(new DuplicateInputTextWatcher<>(currentSceneList))
				.setPositiveButton(activity.getString(R.string.ok), (TextInputDialog.OnClickListener) (dialog, textInput) -> {
					Scene scene = SceneController.newSceneWithBackgroundSprite(
							textInput, activity.getString(R.string.background), currentProject);
					currentProject.addScene(scene);
					spinner.add(scene);
					spinner.setSelection(scene);
					notifyDataSetChanged(activity);
				});

		builder.setTitle(R.string.new_scene_dialog)
				.setNegativeButton(R.string.cancel, (dialog, which) -> spinner.setSelection(sceneForTransition))
				.setOnCancelListener(dialog -> spinner.setSelection(sceneForTransition))
				.show();
	}

	@Override
	public void onEditOptionSelected(Integer spinnerId) {
	}

	@Override
	public void onStringOptionSelected(Integer spinnerId, String string) {
	}

	@Override
	public void onItemSelected(Integer spinnerId, @Nullable Scene item) {
		sceneForTransition = item != null ? item.getName() : null;
	}

	@Override
	public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
		sequence.addAction(sprite.getActionFactory().createSceneTransitionAction(sceneForTransition, sprite));
	}
}
