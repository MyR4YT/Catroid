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
import com.myradev.lunarcode.common.SoundInfo;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.ui.controller.BackpackListManager;
import com.myradev.lunarcode.ui.recyclerview.adapter.SoundAdapter;
import com.myradev.lunarcode.ui.recyclerview.controller.SoundController;
import com.myradev.lunarcode.utils.ToastUtil;

import java.io.IOException;
import java.util.List;

import androidx.annotation.PluralsRes;

import static com.myradev.lunarcode.common.SharedPreferenceKeys.SHOW_DETAILS_SOUNDS_PREFERENCE_KEY;

public class BackpackSoundFragment extends BackpackRecyclerViewFragment<SoundInfo> {

	public static final String TAG = BackpackSoundFragment.class.getSimpleName();

	private SoundController soundController = new SoundController();

	@Override
	protected void initializeAdapter() {
		sharedPreferenceDetailsKey = SHOW_DETAILS_SOUNDS_PREFERENCE_KEY;
		hasDetails = true;
		List<SoundInfo> items = BackpackListManager.getInstance().getBackpackedSounds();
		adapter = new SoundAdapter(items);
		onAdapterReady();
	}

	@Override
	protected void unpackItems(List<SoundInfo> selectedItems) {
		setShowProgressBar(true);
		Sprite destinationSprite = ProjectManager.getInstance().getCurrentSprite();
		int unpackedItemCnt = 0;

		for (SoundInfo item : selectedItems) {
			try {
				destinationSprite.getSoundList().add(soundController.unpack(item,
						ProjectManager.getInstance().getCurrentlyEditedScene(),
						destinationSprite));
				unpackedItemCnt++;
			} catch (IOException e) {
				Log.e(TAG, Log.getStackTraceString(e));
			}
		}

		if (unpackedItemCnt > 0) {
			ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.unpacked_sounds,
					unpackedItemCnt,
					unpackedItemCnt));
			getActivity().finish();
		}

		finishActionMode();
	}

	@Override
	@PluralsRes
	protected int getDeleteAlertTitleId() {
		return R.plurals.delete_sounds;
	}

	@Override
	protected void deleteItems(List<SoundInfo> selectedItems) {
		setShowProgressBar(true);
		for (SoundInfo item : selectedItems) {
			try {
				soundController.delete(item);
			} catch (IOException e) {
				Log.e(TAG, Log.getStackTraceString(e));
			}
			adapter.remove(item);
		}
		ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_sounds,
				selectedItems.size(),
				selectedItems.size()));

		BackpackListManager.getInstance().saveBackpack();
		finishActionMode();
		if (adapter.getItems().isEmpty()) {
			getActivity().finish();
		}
	}

	@Override
	public String getItemName(SoundInfo item) {
		return item.getName();
	}
}
