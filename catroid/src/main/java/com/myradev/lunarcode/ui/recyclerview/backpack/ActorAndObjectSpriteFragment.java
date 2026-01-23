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

package com.myradev.lunarcode.ui.recyclerview.backpack;

import android.util.Log;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.R;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Scene;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.ui.controller.ActorsAndObjectsManager;
import com.myradev.lunarcode.ui.controller.BackpackListManager;
import com.myradev.lunarcode.ui.recyclerview.adapter.SpriteAdapter;
import com.myradev.lunarcode.ui.recyclerview.controller.SpriteController;
import com.myradev.lunarcode.utils.ToastUtil;

import java.io.IOException;
import java.util.List;

import androidx.annotation.PluralsRes;

import static com.myradev.lunarcode.common.SharedPreferenceKeys.SHOW_DETAILS_SPRITES_PREFERENCE_KEY;

public class ActorAndObjectSpriteFragment extends ActorAndObjectRecyclerViewFragment<Sprite> {

	public static final String TAG = ActorAndObjectSpriteFragment.class.getSimpleName();

	private SpriteController spriteController = new SpriteController();

	@Override
	protected void initializeAdapter() {
		sharedPreferenceDetailsKey = SHOW_DETAILS_SPRITES_PREFERENCE_KEY;
		hasDetails = true;
		List<Sprite> items = ActorsAndObjectsManager.getInstance().getSprites();
		adapter = new SpriteAdapter(items);
		onAdapterReady();
	}

	@Override
	protected void unpackItems(List<Sprite> selectedItems) {
		setShowProgressBar(true);
		Project dstProject = ProjectManager.getInstance().getCurrentProject();
		Scene dstScene = ProjectManager.getInstance().getCurrentlyEditedScene();
		int unpackedItemCnt = 0;

		for (Sprite item : selectedItems) {
			try {
				dstScene.getSpriteList().add(spriteController.unpack(item, dstProject, dstScene));
				unpackedItemCnt++;
			} catch (IOException e) {
				Log.e(TAG, Log.getStackTraceString(e));
			}
		}

		if (unpackedItemCnt > 0) {
			ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.unpacked_sprites,
					unpackedItemCnt,
					unpackedItemCnt));
			getActivity().finish();
		}

		finishActionMode();
	}

	@Override
	@PluralsRes
	protected int getDeleteAlertTitleId() {
		return R.plurals.delete_sprites;
	}

	@Override
	protected void deleteItems(List<Sprite> selectedItems) {
		setShowProgressBar(true);
		for (Sprite item : selectedItems) {
			spriteController.delete(item);
			adapter.remove(item);
		}
		ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_sprites,
				selectedItems.size(),
				selectedItems.size()));

		BackpackListManager.getInstance().saveBackpack();
		finishActionMode();
		if (adapter.getItems().isEmpty()) {
			getActivity().finish();
		}
	}
}
