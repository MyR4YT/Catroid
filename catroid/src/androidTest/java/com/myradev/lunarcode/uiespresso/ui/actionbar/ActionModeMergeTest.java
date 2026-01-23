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

package com.myradev.lunarcode.uiespresso.ui.actionbar;

import com.myradev.lunarcode.R;
import com.myradev.lunarcode.common.BrickValues;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.bricks.SetXBrick;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.io.XstreamSerializer;
import com.myradev.lunarcode.testsuites.annotations.Cat;
import com.myradev.lunarcode.testsuites.annotations.Level;
import com.myradev.lunarcode.ui.ProjectListActivity;
import com.myradev.lunarcode.uiespresso.util.UiTestUtils;
import com.myradev.lunarcode.uiespresso.util.rules.BaseActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.myradev.lunarcode.uiespresso.ui.actionbar.utils.ActionModeWrapper.onActionMode;
import static com.myradev.lunarcode.uiespresso.ui.fragment.rvutils.RecyclerViewInteractionWrapper.onRecyclerView;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ActionModeMergeTest {

	@Rule
	public BaseActivityTestRule<ProjectListActivity> baseActivityTestRule = new
			BaseActivityTestRule<>(ProjectListActivity.class, true, false);

	private String firstProjectName = "firstProjectName";
	private String secondProjectName = "secondProjectName";
	private String thirdProjectName = "thirdProjectName";

	@Before
	public void setUp() throws Exception {
		createProject(firstProjectName);
		createProject(secondProjectName);
		createProject(thirdProjectName);

		baseActivityTestRule.launchActivity(null);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void onlyTwoProjectsCheckedTest() {
		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText(R.string.merge)).perform(click());

		onRecyclerView().atPosition(0)
				.performCheckItemClick();

		onRecyclerView().atPosition(1)
				.performCheckItemClick();

		onRecyclerView().atPosition(2)
				.performCheckItemClick();

		onRecyclerView().atPosition(0).onChildView(R.id.checkbox).check(matches(isChecked()));
		onRecyclerView().atPosition(1).onChildView(R.id.checkbox).check(matches(isChecked()));
		onRecyclerView().atPosition(2).onChildView(R.id.checkbox).check(matches(isNotChecked()));
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void checkMenuButtonTest() {
		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText(R.string.merge)).check(matches(isDisplayed()));
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void actionModeMergeTitleTest() {
		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

		onView(withText(R.string.merge))
				.perform(click());

		onRecyclerView().atPosition(0)
				.performCheckItemClick();
		onActionMode().checkTitleMatches(UiTestUtils.getResourcesString(R.string.merge) + " 1");

		onRecyclerView().atPosition(1)
				.performCheckItemClick();
		onActionMode().checkTitleMatches(UiTestUtils.getResourcesString(R.string.merge) + " 2");

		onRecyclerView().atPosition(0)
				.performCheckItemClick();
		onActionMode().checkTitleMatches(UiTestUtils.getResourcesString(R.string.merge) + " 1");

		onRecyclerView().atPosition(1)
				.performCheckItemClick();

		onActionMode().checkTitleMatches(UiTestUtils.getResourcesString(R.string.merge) + " 0");
	}

	private void createProject(String projectName) {
		Project project = UiTestUtils.createDefaultTestProject(projectName);
		Script script = UiTestUtils.getDefaultTestScript(project);
		script.addBrick(new SetXBrick(new Formula(BrickValues.X_POSITION)));
		script.addBrick(new SetXBrick(new Formula(BrickValues.X_POSITION)));
		XstreamSerializer.getInstance().saveProject(project);
	}
}
