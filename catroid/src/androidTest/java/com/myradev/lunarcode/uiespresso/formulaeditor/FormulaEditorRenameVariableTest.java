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

package com.myradev.lunarcode.uiespresso.formulaeditor;

import com.myradev.lunarcode.R;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.bricks.ChangeSizeByNBrick;
import com.myradev.lunarcode.testsuites.annotations.Cat;
import com.myradev.lunarcode.testsuites.annotations.Level;
import com.myradev.lunarcode.ui.SpriteActivity;
import com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorDataListWrapper;
import com.myradev.lunarcode.uiespresso.util.UiTestUtils;
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.myradev.lunarcode.uiespresso.content.brick.utils.BrickDataInteractionWrapper.onBrickAtPosition;
import static com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorDataListWrapper.onDataList;
import static com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorWrapper.onFormulaEditor;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class FormulaEditorRenameVariableTest {
	@Rule
	public FragmentActivityTestRule<SpriteActivity> baseActivityTestRule = new
			FragmentActivityTestRule<>(SpriteActivity.class, SpriteActivity.EXTRA_FRAGMENT_POSITION, SpriteActivity.FRAGMENT_SCRIPTS);

	@Before
	public void setUp() throws Exception {
		Script script = UiTestUtils.createProjectAndGetStartScript("FormulaEditorRenameVariableTest");
		script.addBrick(new ChangeSizeByNBrick(0));
		baseActivityTestRule.launchActivity();
	}

	private static String variableNameOld = "variableOld";
	private static String variableNameNew = "variableNew";
	private static Integer whenBrickPosition = 0;
	private static Integer changeSizeBrickPosition = 1;

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void renameVariable() {
		onBrickAtPosition(whenBrickPosition).checkShowsText(R.string.brick_when_started);
		onBrickAtPosition(changeSizeBrickPosition).checkShowsText(R.string.brick_change_size_by);

		onBrickAtPosition(changeSizeBrickPosition).onChildView(withId(R.id.brick_change_size_by_edit_text))
				.perform(click());

		onFormulaEditor()
				.performOpenDataFragment();

		onDataList()
				.performAdd(variableNameOld);

		onDataList().onVariableAtPosition(0)
				.performSelect();

		onFormulaEditor()
				.checkShows(getUserVariableEditText(variableNameOld));

		onFormulaEditor()
				.performOpenDataFragment();

		onDataList().onVariableAtPosition(0)
				.performRename(variableNameNew);

		onDataList()
				.performClose();

		onFormulaEditor()
				.checkShows(getUserVariableEditText(variableNameNew));
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void renameList() {
		onBrickAtPosition(whenBrickPosition).checkShowsText(R.string.brick_when_started);
		onBrickAtPosition(changeSizeBrickPosition).checkShowsText(R.string.brick_change_size_by);

		onBrickAtPosition(changeSizeBrickPosition).onChildView(withId(R.id.brick_change_size_by_edit_text))
				.perform(click());

		onFormulaEditor()
				.performOpenDataFragment();

		onDataList()
				.performAdd(variableNameOld, FormulaEditorDataListWrapper.ItemType.LIST);

		onDataList()
				.onListAtPosition(0)
				.performSelect();

		onFormulaEditor()
				.checkShows(getUserListEditText(variableNameOld));

		onFormulaEditor()
				.performOpenDataFragment();

		onDataList().onListAtPosition(0)
				.performRename(variableNameNew);

		onDataList()
				.performClose();

		onFormulaEditor()
				.checkShows(getUserListEditText(variableNameNew));
	}

	private String getUserVariableEditText(String variableName) {
		return "\"" + variableName + "\" ";
	}

	private String getUserListEditText(String variableName) {
		return "*" + variableName + "* ";
	}
}
