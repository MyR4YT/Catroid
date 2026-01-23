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

import com.myradev.lunarcode.common.LookData;
import com.myradev.lunarcode.common.ScreenValues;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.bricks.PlaceAtBrick;
import com.myradev.lunarcode.content.bricks.SetSizeToBrick;
import com.myradev.lunarcode.io.ResourceImporter;
import com.myradev.lunarcode.io.XstreamSerializer;
import com.myradev.lunarcode.stage.StageActivity;
import com.myradev.lunarcode.testsuites.annotations.Cat;
import com.myradev.lunarcode.uiespresso.util.UiTestUtils;
import com.myradev.lunarcode.uiespresso.util.matchers.StageMatchers;
import com.myradev.lunarcode.uiespresso.util.rules.BaseActivityTestRule;
import com.myradev.lunarcode.utils.Resolution;
import com.myradev.lunarcode.utils.ScreenValueHandler;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static com.myradev.lunarcode.common.Constants.IMAGE_DIRECTORY_NAME;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isFocusable;

@RunWith(AndroidJUnit4.class)
public class StageSimpleTest {

	private static final int PROJECT_WIDTH = 480;
	private static final int PROJECT_HEIGHT = 800;

	@Rule
	public BaseActivityTestRule<StageActivity> baseActivityTestRule = new
			BaseActivityTestRule<>(StageActivity.class, true, false);

	@Before
	public void setUp() throws Exception {
		createProjectWithBlueSprite("blueProject");
	}

	@Category({Cat.Educational.class})
	@Test
	public void checkForBlueSpriteColor() {
		baseActivityTestRule.launchActivity(null);

		byte[] blue = {0, (byte) 162, (byte) 232, (byte) 255};

		//color matcher only accepts a GL20View, this can be aquired by getting the only focusable element in the stage
		onView(isFocusable())
				.check(matches(StageMatchers.isColorAtPx(blue, 1, 1)));
	}

	public Project createProjectWithBlueSprite(String projectName) throws IOException {
		ScreenValues.currentScreenResolution = new Resolution(PROJECT_WIDTH, PROJECT_HEIGHT);

		Project project = UiTestUtils.createDefaultTestProject(projectName);

		Sprite blueSprite = UiTestUtils.getDefaultTestSprite(project);
		Script blueStartScript = UiTestUtils.getDefaultTestScript(project);
		LookData blueLookData = new LookData();
		String blueImageName = "blue_image.bmp";

		blueLookData.setName(blueImageName);

		blueSprite.getLookList().add(blueLookData);

		blueStartScript.addBrick(new PlaceAtBrick(0, 0));
		blueStartScript.addBrick(new SetSizeToBrick(5000));

		XstreamSerializer.getInstance().saveProject(project);

		File blueImageFile = ResourceImporter.createImageFileFromResourcesInDirectory(
				InstrumentationRegistry.getInstrumentation().getContext().getResources(),
				com.myradev.lunarcode.test.R.raw.blue_image,
				new File(project.getDefaultScene().getDirectory(), IMAGE_DIRECTORY_NAME),
				blueImageName,
				1);

		blueLookData.setFile(blueImageFile);

		XstreamSerializer.getInstance().saveProject(project);
		ScreenValueHandler.updateScreenWidthAndHeight(InstrumentationRegistry.getInstrumentation().getContext());

		return project;
	}
}
