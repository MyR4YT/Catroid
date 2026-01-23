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
import com.myradev.lunarcode.content.bricks.SetVariableBrick;
import com.myradev.lunarcode.ui.SpriteActivity;
import com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorWrapper;
import com.myradev.lunarcode.uiespresso.util.UiTestUtils;
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.myradev.lunarcode.uiespresso.content.brick.utils.BrickDataInteractionWrapper.onBrickAtPosition;
import static com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorWrapper.onFormulaEditor;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class FormulaEditorRegexAssistantTest {

	@Rule
	public FragmentActivityTestRule<SpriteActivity> baseActivityTestRule = new
			FragmentActivityTestRule<>(SpriteActivity.class, SpriteActivity.EXTRA_FRAGMENT_POSITION, SpriteActivity.FRAGMENT_SCRIPTS);

	@Before
	public void setUp() {
		Script script = UiTestUtils.createProjectAndGetStartScript(
				"FormulaEditorRegExDetectionTest");
		script.addBrick(new SetVariableBrick(0)); //standard value of editor field is 123
		baseActivityTestRule.launchActivity();
	}

	@Test
	public void testAssistantReplacesTextInFormulaEditor() {
		onBrickAtPosition(1).onChildView(withId(R.id.brick_set_variable_edit_text)).perform(click());
		clickOnAssistantInFunctionList();

		onView(withText(R.string.cancel)).perform(click());

		String selectedFunctionString =
				getSelectedFunctionString(
						UiTestUtils.getResourcesString(R.string.formula_editor_function_regex)
								+ UiTestUtils.getResourcesString(R.string.formula_editor_function_regex_parameter));

		onFormulaEditor().checkShows(selectedFunctionString);
	}

	private void clickOnAssistantInFunctionList() {
		String regularExpressionAssistant =
				"\t\t\t\t\t" + UiTestUtils.getResourcesString(R.string.formula_editor_function_regex_assistant);
		onFormulaEditor().performOpenCategory(FormulaEditorWrapper.Category.FUNCTIONS).performSelect(regularExpressionAssistant);
	}

	private String getSelectedFunctionString(String functionString) {
		return functionString
				.replaceAll("^(.+?)\\(", "$1( ")
				.replace(",", " , ")
				.replace("-", "- ")
				.replaceAll("\\)$", " )")
				.concat(" ");
	}
}
