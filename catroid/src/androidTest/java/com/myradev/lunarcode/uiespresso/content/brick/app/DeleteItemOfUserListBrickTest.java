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

package com.myradev.lunarcode.uiespresso.content.brick.app;

import com.myradev.lunarcode.R;
import com.myradev.lunarcode.content.bricks.DeleteItemOfUserListBrick;
import com.myradev.lunarcode.formulaeditor.UserList;
import com.myradev.lunarcode.rules.FlakyTestRule;
import com.myradev.lunarcode.runner.Flaky;
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

import static junit.framework.Assert.assertEquals;

import static com.myradev.lunarcode.uiespresso.content.brick.utils.BrickDataInteractionWrapper.onBrickAtPosition;
import static com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorDataListWrapper.onDataList;
import static com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorWrapper.onFormulaEditor;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertNotNull;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DeleteItemOfUserListBrickTest {
	private static int brickPosition;
	private DeleteItemOfUserListBrick deleteItemOfUserListBrick;

	@Rule
	public FragmentActivityTestRule<SpriteActivity> baseActivityTestRule = new
			FragmentActivityTestRule<>(SpriteActivity.class, SpriteActivity.EXTRA_FRAGMENT_POSITION, SpriteActivity.FRAGMENT_SCRIPTS);

	@Rule
	public FlakyTestRule flakyTestRule = new FlakyTestRule();

	@Before
	public void setUp() throws Exception {
		brickPosition = 1;
		deleteItemOfUserListBrick = new DeleteItemOfUserListBrick();
		UiTestUtils.createProjectAndGetStartScript("deleteItemOfUserListBrickTest1")
				.addBrick(deleteItemOfUserListBrick);
		baseActivityTestRule.launchActivity();
	}

	@Category({Cat.AppUi.class, Level.Detailed.class})
	@Test
	@Flaky
	public void testDeleteItemOfUserListBrickMultipleLists() {
		String firstUserListName = "Test1";
		String secondUserListName = "Test2";

		onBrickAtPosition(brickPosition).onVariableSpinner(R.id.delete_item_of_userlist_spinner)
				.performNewVariable(firstUserListName);

		UserList userList = deleteItemOfUserListBrick.getUserList();
		assertNotNull(userList);
		assertEquals(firstUserListName, userList.getName());

		onBrickAtPosition(brickPosition).onVariableSpinner(R.id.delete_item_of_userlist_spinner)
				.performNewVariable(secondUserListName)
				.checkShowsText(secondUserListName);

		userList = deleteItemOfUserListBrick.getUserList();
		assertNotNull(userList);
		assertEquals(secondUserListName, userList.getName());

		onBrickAtPosition(brickPosition).onChildView(withId(R.id.brick_delete_item_of_userlist_edit_text))
				.perform(click());

		onFormulaEditor()
				.performOpenDataFragment();
		onDataList().onListAtPosition(1)
				.checkHasName(secondUserListName)
				.performDelete();
		onDataList()
				.performClose();
		pressBack();

		onView(allOf(withText(secondUserListName), isDisplayed()))
				.check(doesNotExist());
		userList = deleteItemOfUserListBrick.getUserList();
		assertNotNull(userList);
		assertEquals(firstUserListName, userList.getName());
	}

	@Category({Cat.AppUi.class, Level.Detailed.class})
	@Test
	public void testCreateUserListInFormulaEditor() {
		String userListName = "test1";
		onBrickAtPosition(brickPosition).onChildView(withId(R.id.brick_delete_item_of_userlist_edit_text))
				.perform(click());
		onFormulaEditor()
				.performOpenDataFragment();
		onDataList()
				.performAdd(userListName, FormulaEditorDataListWrapper.ItemType.LIST)
				.performClose();
		pressBack();
	}
}
