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
package com.myradev.lunarcode.uiespresso.ui.dialog;

import android.view.View;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.R;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.bricks.NoteBrick;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.testsuites.annotations.Cat;
import com.myradev.lunarcode.testsuites.annotations.Level;
import com.myradev.lunarcode.ui.SpriteActivity;
import com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorWrapper;
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorWrapper.onFormulaEditor;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class FormulaEditorComputeDialogTest {

	@Rule
	public FragmentActivityTestRule<SpriteActivity> baseActivityTestRule = new
			FragmentActivityTestRule<>(SpriteActivity.class, SpriteActivity.EXTRA_FRAGMENT_POSITION,
			SpriteActivity.FRAGMENT_SCRIPTS);
	private Integer someIntegerVaule = 911;

	@Before
	public void setUp() throws Exception {
		createProject("formulaEditorComputeDialogTest");

		baseActivityTestRule.launchActivity();
	}

	private void openComputeDialog() {
		onView(withId(R.id.brick_note_edit_text))
				.perform(click());
		onFormulaEditor()
				.performClickOn(FormulaEditorWrapper.Control.COMPUTE);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void dialogCreationTest() {
		openComputeDialog();

		String computedValueText = Integer.toString(someIntegerVaule);

		onView(withId(R.id.formula_editor_compute_dialog_textview))
				.check(matches(withText(computedValueText)));
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void cancelOnTouchInsideOfDialogTest() {
		openComputeDialog();

		onView(withId(R.id.formula_editor_compute_dialog_textview))
				.perform(click());

		onView(withId(R.id.formula_editor_compute_dialog_textview))
				.check(doesNotExist());
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void cancelOnBackDialogTest() {
		openComputeDialog();

		pressBack();

		onView(withId(R.id.formula_editor_compute_dialog_textview))
				.check(doesNotExist());
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void cancelOnTouchOutsideOfDialogTest() {
		openComputeDialog();

		onView(withId(R.id.formula_editor_compute_dialog_textview))
				.perform(clickWithOffset(-50, -50));

		onView(withId(R.id.formula_editor_compute_dialog_textview))
				.check(doesNotExist());
	}

	public Project createProject(String projectName) {
		Project project = new Project(ApplicationProvider.getApplicationContext(), projectName);
		Sprite sprite = new Sprite("testSprite");
		Script script = new StartScript();

		Formula noteFormula = new Formula(someIntegerVaule);
		script.addBrick(new NoteBrick(noteFormula));

		sprite.addScript(script);
		project.getDefaultScene().addSprite(sprite);
		ProjectManager.getInstance().setCurrentProject(project);
		ProjectManager.getInstance().setCurrentSprite(sprite);

		return project;
	}

	public static ViewAction clickWithOffset(final int xOffset, final int yOffset) {
		return new GeneralClickAction(
			Tap.SINGLE,
			new CoordinatesProvider() {
				@Override
				public float[] calculateCoordinates(View view) {
					final int[] viewsCoordinates = new int[2];
					view.getLocationOnScreen(viewsCoordinates);

					float[] clickCoordinates = {viewsCoordinates[0] + xOffset, viewsCoordinates[1] + yOffset};
					return clickCoordinates;
				}
			},
			Press.FINGER);
	}
}
