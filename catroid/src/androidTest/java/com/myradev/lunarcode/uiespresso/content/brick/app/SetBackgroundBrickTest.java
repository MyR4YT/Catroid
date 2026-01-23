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
import com.myradev.lunarcode.content.bricks.SetBackgroundBrick;
import com.myradev.lunarcode.testsuites.annotations.Cat;
import com.myradev.lunarcode.testsuites.annotations.Level;
import com.myradev.lunarcode.ui.SpriteActivity;
import com.myradev.lunarcode.ui.WebViewActivity;
import com.myradev.lunarcode.uiespresso.util.UiTestUtils;
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.myradev.lunarcode.common.FlavoredConstants.CATROBAT_CONTENT_BACKGROUNDS_URL;
import static com.myradev.lunarcode.uiespresso.content.brick.utils.BrickDataInteractionWrapper.onBrickAtPosition;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.AllOf.allOf;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SetBackgroundBrickTest {
	private int brickPosition;

	@Rule
	public FragmentActivityTestRule<SpriteActivity> baseActivityTestRule = new
			FragmentActivityTestRule<>(SpriteActivity.class, SpriteActivity.EXTRA_FRAGMENT_POSITION, SpriteActivity.FRAGMENT_SCRIPTS);

	@Before
	public void setUp() throws Exception {
		brickPosition = 1;
		UiTestUtils.createProjectAndGetStartScript("SetBackgroundBrickTest")
			.addBrick(new SetBackgroundBrick());
		baseActivityTestRule.launchActivity();
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void testSetBackgroundBrickMediaLibraryUrl() {
		onBrickAtPosition(0).checkShowsText(R.string.brick_when_started);
		onBrickAtPosition(brickPosition).checkShowsText(R.string.brick_set_background);

		onBrickAtPosition(brickPosition).onSpinner(R.id.brick_set_background_spinner)
			.performSelectNameable(R.string.new_option);

		Intents.init();

		onView(withId(R.id.dialog_new_look_media_library))
			.perform(click());

		intended(allOf(hasComponent(WebViewActivity.class.getName()),
				hasExtras(allOf(hasEntry(equalTo(WebViewActivity.INTENT_PARAMETER_URL),
				equalTo(CATROBAT_CONTENT_BACKGROUNDS_URL))))));

		Intents.release();
	}
}
