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

package com.myradev.lunarcode.uiespresso.ui.activity;

import com.myradev.lunarcode.R;
import com.myradev.lunarcode.common.BrickValues;
import com.myradev.lunarcode.common.LookData;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.bricks.SetXBrick;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.testsuites.annotations.Cat;
import com.myradev.lunarcode.testsuites.annotations.Level;
import com.myradev.lunarcode.ui.ProjectActivity;
import com.myradev.lunarcode.uiespresso.util.UiTestUtils;
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.util.Locale;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.myradev.lunarcode.uiespresso.ui.fragment.rvutils.RecyclerViewInteractionWrapper.onRecyclerView;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ProjectActivityNumberOfBricksRegressionTest {

	@Rule
	public FragmentActivityTestRule<ProjectActivity> baseActivityTestRule = new
			FragmentActivityTestRule<>(ProjectActivity.class, ProjectActivity.EXTRA_FRAGMENT_POSITION, ProjectActivity.FRAGMENT_SPRITES);

	@Before
	public void setUp() {
		createProject();
		baseActivityTestRule.launchActivity();

		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText(R.string.show_details)).perform(click());
	}

	@After
	public void tearDown() {
		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText(R.string.hide_details)).perform(click());
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void numberOfBricksDetailsRegressionTest() throws Exception {
		onRecyclerView().atPosition(1).onChildView(R.id.details_view)
				.check(matches(withText(String.format(Locale.getDefault(),
						UiTestUtils.getResourcesString(R.string.sprite_details),
						7, 2, 0
				))));
	}

	private void createProject() {
		Project project = UiTestUtils.createDefaultTestProject("ProjectActivityNumberOfBricksRegressionTest");
		Sprite firstSprite = UiTestUtils.getDefaultTestSprite(project);

		Script firstScript = UiTestUtils.getDefaultTestScript(project);
		firstScript.addBrick(new SetXBrick(new Formula(BrickValues.X_POSITION)));
		firstScript.addBrick(new SetXBrick(new Formula(BrickValues.X_POSITION)));
		firstSprite.addScript(firstScript);

		Script secondScript = new StartScript();
		secondScript.addBrick(new SetXBrick(new Formula(BrickValues.X_POSITION)));
		secondScript.addBrick(new SetXBrick(new Formula(BrickValues.X_POSITION)));
		secondScript.addBrick(new SetXBrick(new Formula(BrickValues.X_POSITION)));
		firstSprite.addScript(secondScript);

		LookData lookData = new LookData();
		lookData.setName("red");
		firstSprite.getLookList().add(lookData);

		LookData anotherLookData = new LookData();
		anotherLookData.setName("blue");
		firstSprite.getLookList().add(anotherLookData);
	}
}
