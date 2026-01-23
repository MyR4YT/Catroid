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
import com.myradev.lunarcode.common.LookData;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.ui.controller.BackpackListManager;
import com.myradev.lunarcode.ui.recyclerview.adapter.LookAdapter;
import com.myradev.lunarcode.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import com.myradev.lunarcode.ui.recyclerview.controller.LookController;
import com.myradev.lunarcode.utils.ToastUtil;

import java.io.IOException;
import java.util.List;

import androidx.annotation.PluralsRes;

import static com.myradev.lunarcode.common.SharedPreferenceKeys.SHOW_DETAILS_LOOKS_PREFERENCE_KEY;
import static org.koin.java.KoinJavaComponent.inject;

public class BackpackLookFragment extends BackpackRecyclerViewFragment<LookData> {

	public static final String TAG = BackpackLookFragment.class.getSimpleName();
	public final ProjectManager projectManager = inject(ProjectManager.class).getValue();

	private LookController lookController = new LookController();

	@Override
	protected void initializeAdapter() {
		sharedPreferenceDetailsKey = SHOW_DETAILS_LOOKS_PREFERENCE_KEY;
		hasDetails = true;
		List<LookData> items = BackpackListManager.getInstance().getBackpackedLooks();
		adapter = new LookAdapter(items);
		onAdapterReady();
	}

	@Override
	protected void unpackItems(List<LookData> selectedItems) {
		setShowProgressBar(true);
		Sprite destinationSprite = projectManager.getCurrentSprite();
		int unpackedItemCnt = 0;

		for (LookData item : selectedItems) {
			try {
				destinationSprite.getLookList().add(lookController.unpack(item,
						ProjectManager.getInstance().getCurrentlyEditedScene(),
						destinationSprite));
				unpackedItemCnt++;
			} catch (IOException e) {
				Log.e(TAG, Log.getStackTraceString(e));
			}
		}

		if (unpackedItemCnt > 0) {
			ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.unpacked_looks,
					unpackedItemCnt,
					unpackedItemCnt));
			getActivity().finish();
		}

		finishActionMode();
	}

	@Override
	@PluralsRes
	protected int getDeleteAlertTitleId() {
		return R.plurals.delete_looks;
	}

	@Override
	protected void deleteItems(List<LookData> selectedItems) {
		setShowProgressBar(true);
		for (LookData item : selectedItems) {
			try {
				lookController.delete(item);
			} catch (IOException e) {
				Log.e(TAG, Log.getStackTraceString(e));
			}
			adapter.remove(item);
		}
		ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_looks,
				selectedItems.size(),
				selectedItems.size()));

		BackpackListManager.getInstance().saveBackpack();
		finishActionMode();
		if (adapter.getItems().isEmpty()) {
			getActivity().finish();
		}
	}

	@Override
	public void onItemClick(final LookData item, MultiSelectionManager selectionManager) {
		super.onItemClick(item, selectionManager);
	}

	@Override
	protected String getItemName(LookData item) {
		return item.getName();
	}
}
