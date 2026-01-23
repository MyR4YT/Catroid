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

package com.myradev.lunarcode.uiespresso.stage;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;

import com.myradev.lunarcode.R;
import com.myradev.lunarcode.common.Constants;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.io.ResourceImporter;
import com.myradev.lunarcode.io.XstreamSerializer;
import com.myradev.lunarcode.test.utils.TestUtils;
import com.myradev.lunarcode.testsuites.annotations.Cat;
import com.myradev.lunarcode.testsuites.annotations.Level;
import com.myradev.lunarcode.ui.SpriteActivity;
import com.myradev.lunarcode.uiespresso.util.UiTestUtils;
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import androidx.test.espresso.intent.Intents;
import androidx.test.platform.app.InstrumentationRegistry;

import static com.myradev.lunarcode.uiespresso.ui.fragment.rvutils.RecyclerViewInteractionWrapper.onRecyclerView;
import static com.myradev.lunarcode.uiespresso.util.matchers.BundleMatchers.bundleHasExtraIntent;
import static com.myradev.lunarcode.uiespresso.util.matchers.BundleMatchers.bundleHasMatchingString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.AllOf.allOf;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasCategories;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class LookUndoTest {

	private Matcher<Intent> expectedChooserIntent;
	private Matcher<Intent> expectedPaintNewLookIntent;
	private final String lookFileName = "catroid_sunglasses.png";
	private final String projectName = getClass().getSimpleName();
	private final String spriteName = "testSprite";
	private File imageFile;

	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();

	@Rule
	public FragmentActivityTestRule<SpriteActivity> baseActivityTestRule = new
			FragmentActivityTestRule<>(SpriteActivity.class, SpriteActivity.EXTRA_FRAGMENT_POSITION, SpriteActivity.FRAGMENT_LOOKS);

	@Before
	public void setUp() throws Exception {
		Project project = UiTestUtils.createDefaultTestProject(projectName);
		XstreamSerializer.getInstance().saveProject(project);

		baseActivityTestRule.launchActivity();
		Intents.init();

		Matcher<Intent> expectedGetContentIntent = allOf(
				hasAction("android.intent.action.GET_CONTENT"),
				hasType("image/*"));

		String chooserTitle = UiTestUtils.getResourcesString(R.string.select_look_from_gallery);
		expectedChooserIntent = allOf(
				hasAction("android.intent.action.CHOOSER"),
				hasExtras(bundleHasMatchingString("android.intent.extra.TITLE", chooserTitle)),
				hasExtras(bundleHasExtraIntent(expectedGetContentIntent)));

		imageFile = ResourceImporter.createImageFileFromResourcesInDirectory(
				InstrumentationRegistry.getInstrumentation().getContext().getResources(),
				com.myradev.lunarcode.test.R.drawable.catroid_banzai,
				tmpFolder.getRoot(),
				lookFileName,
				1);

		Intent resultData = new Intent();
		resultData.setData(Uri.fromFile(imageFile));

		Instrumentation.ActivityResult result =
				new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

		intending(expectedChooserIntent).respondWith(result);

		expectedPaintNewLookIntent = allOf(
				hasComponent(Constants.POCKET_PAINT_INTENT_ACTIVITY_NAME),
				hasAction("android.intent.action.MAIN"),
				hasCategories(hasItem(equalTo("android.intent.category.LAUNCHER"))));

		Instrumentation.ActivityResult resultPaintroid = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);

		intending(expectedPaintNewLookIntent).respondWith(resultPaintroid);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void testAddNewLook() {
		onView(withId(R.id.menu_undo)).check(doesNotExist());  // undo shouldn't be visible
		// adding image

		onView(withId(R.id.button_add))
				.perform(click());

		onView(withId(R.id.dialog_new_look_paintroid))
				.perform(click());

		intended(expectedPaintNewLookIntent);

		onRecyclerView().atPosition(0).onChildView(R.id.title_view)
				.check(matches(withText(spriteName)));

		onView(withId(R.id.menu_undo)).perform(click()); // press undo

		onRecyclerView().checkHasNumberOfItems(0);

		onView(withId(R.id.menu_undo)).check(doesNotExist());  // undo shouldn't be visible
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void testUndoLookFromGalleryIntentTest() {
		onView(withId(R.id.menu_undo)).check(doesNotExist()); // undo shouldn't be visible

		onView(withId(R.id.button_add))
				.perform(click());

		onView(withId(R.id.dialog_new_look_gallery))
				.perform(click());

		intended(expectedChooserIntent);

		onRecyclerView().atPosition(0).onChildView(R.id.title_view)
				.check(matches(withText(lookFileName.replace(".png", ""))));

		onView(withId(R.id.menu_undo)).perform(click()); // press undo

		onRecyclerView().checkHasNumberOfItems(0);

		onView(withId(R.id.menu_undo)).check(doesNotExist()); // undo shouldn't be visible
	}

	@After
	public void tearDown() throws IOException {
		Intents.release();
		baseActivityTestRule.finishActivity();
		TestUtils.deleteProjects(projectName);
	}
}
