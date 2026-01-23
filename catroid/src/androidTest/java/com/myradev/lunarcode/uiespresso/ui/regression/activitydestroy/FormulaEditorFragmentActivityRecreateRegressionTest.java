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

package com.myradev.lunarcode.uiespresso.ui.regression.activitydestroy;

import android.content.Intent;

import com.myradev.lunarcode.R;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.bricks.ChangeSizeByNBrick;
import com.myradev.lunarcode.testsuites.annotations.Cat;
import com.myradev.lunarcode.testsuites.annotations.Level;
import com.myradev.lunarcode.ui.SpriteActivity;
import com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorWrapper;
import com.myradev.lunarcode.uiespresso.util.UiTestUtils;
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static com.myradev.lunarcode.uiespresso.content.brick.utils.BrickDataInteractionWrapper.onBrickAtPosition;
import static com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorWrapper.FORMULA_EDITOR_KEYBOARD_MATCHER;
import static com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorWrapper.FORMULA_EDITOR_TEXT_FIELD_MATCHER;
import static com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorWrapper.onFormulaEditor;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class FormulaEditorFragmentActivityRecreateRegressionTest {

	@Rule
	public FragmentActivityTestRule<SpriteActivity> baseActivityTestRule = new
			FragmentActivityTestRule<>(SpriteActivity.class, SpriteActivity.EXTRA_FRAGMENT_POSITION,
			SpriteActivity.FRAGMENT_SCRIPTS);

	@Before
	public void setUp() throws Exception {
		Script script = UiTestUtils.createProjectAndGetStartScript("FormulaEditorEditTextTest");
		script.addBrick(new ChangeSizeByNBrick());
		baseActivityTestRule.launchActivity();
		onBrickAtPosition(1)
				.onFormulaTextField(R.id.brick_change_size_by_edit_text)
				.perform(click());
		InstrumentationRegistry.getInstrumentation().waitForIdleSync();
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.Quarantine.class})
	@Test
	public void testActivityRecreateFormulaEditorFragment() {
		recreateActivity();
		checkInitialListeners();
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.Quarantine.class})
	@Test
	public void testActivityRecreateDataFragment() {
		onFormulaEditor().performOpenDataFragment();
		InstrumentationRegistry.getInstrumentation().waitForIdleSync();
		recreateActivity();
		checkInitialListeners();
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.Quarantine.class})
	@Test
	public void testActivityRecreateCategoryFragment() {
		onFormulaEditor().performOpenFunctions();
		InstrumentationRegistry.getInstrumentation().waitForIdleSync();
		recreateActivity();
		checkInitialListeners();
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.Quarantine.class})
	@Test
	public void testActivityRecreateStringDialogFragment() {
		onFormulaEditor().performClickOn(FormulaEditorWrapper.Control.TEXT);
		InstrumentationRegistry.getInstrumentation().waitForIdleSync();
		onView(withId(R.id.input_edit_text))
				.check(matches(isDisplayed()));
		onView(withText(R.string.formula_editor_new_string_name)).inRoot(isDialog())
				.check(matches(isDisplayed()));
		recreateActivity();
		checkInitialListeners();
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.Quarantine.class})
	@Test
	public void testActivityRecreateComputeDialogFragment() {
		onFormulaEditor().performCompute();
		InstrumentationRegistry.getInstrumentation().waitForIdleSync();
		onView(withId(R.id.formula_editor_compute_dialog_textview)).inRoot(isDialog())
				.check(matches(isDisplayed()));
		recreateActivity();
		checkInitialListeners();
	}

	private void recreateActivity() {
		Intent intent = baseActivityTestRule.getActivity().getIntent();
		InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> baseActivityTestRule.getActivity().finish());
		InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> baseActivityTestRule.getActivity().startActivity(intent));
		InstrumentationRegistry.getInstrumentation().waitForIdleSync();
	}

	private void checkInitialListeners() {
		onBrickAtPosition(0)
				.checkShowsText(R.string.brick_when_started);
		onBrickAtPosition(1)
				.checkShowsText(R.string.brick_change_size_by);
		onView(FORMULA_EDITOR_KEYBOARD_MATCHER)
				.check(doesNotExist());
		onView(FORMULA_EDITOR_TEXT_FIELD_MATCHER)
				.check(doesNotExist());
		onBrickAtPosition(1).perform(click());
		onView(withText(R.string.brick_context_dialog_formula_edit_brick)).perform(click());
		InstrumentationRegistry.getInstrumentation().waitForIdleSync();
		onView(FORMULA_EDITOR_KEYBOARD_MATCHER)
				.check(matches(isDisplayed()));
		onView(FORMULA_EDITOR_TEXT_FIELD_MATCHER)
				.check(matches(isDisplayed()));
		InstrumentationRegistry.getInstrumentation().waitForIdleSync();
	}
}
