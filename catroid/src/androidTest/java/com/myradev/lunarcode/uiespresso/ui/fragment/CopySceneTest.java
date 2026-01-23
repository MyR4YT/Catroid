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

package com.myradev.lunarcode.uiespresso.ui.fragment;

import android.content.Context;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.R;
import com.myradev.lunarcode.common.BrickValues;
import com.myradev.lunarcode.common.LookData;
import com.myradev.lunarcode.common.SoundInfo;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Scene;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.bricks.SetXBrick;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.io.ResourceImporter;
import com.myradev.lunarcode.io.XstreamSerializer;
import com.myradev.lunarcode.test.utils.TestUtils;
import com.myradev.lunarcode.testsuites.annotations.Cat;
import com.myradev.lunarcode.testsuites.annotations.Level;
import com.myradev.lunarcode.ui.ProjectActivity;
import com.myradev.lunarcode.uiespresso.util.rules.BaseActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.io.File;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static junit.framework.TestCase.assertEquals;

import static com.myradev.lunarcode.common.Constants.IMAGE_DIRECTORY_NAME;
import static com.myradev.lunarcode.common.Constants.SOUND_DIRECTORY_NAME;
import static com.myradev.lunarcode.uiespresso.ui.fragment.rvutils.RecyclerViewInteractionWrapper.onRecyclerView;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CopySceneTest {

	@Rule
	public BaseActivityTestRule<ProjectActivity> baseActivityTestRule = new BaseActivityTestRule<>(ProjectActivity.class, false, false);

	private String projectName = "CopySceneTest";
	private String toBeCopiedSceneName = "Scene";

	@Before
	public void setUp() throws Exception {
		createProject(ApplicationProvider.getApplicationContext(), projectName);
		baseActivityTestRule.launchActivity(null);
	}

	@After
	public void tearDown() throws Exception {
		TestUtils.deleteProjects(projectName);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void copySceneTest() {
		openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
		onView(withText(R.string.copy))
				.perform(click());

		onRecyclerView().atPosition(0)
				.performCheckItemClick();

		onView(withId(R.id.confirm))
				.perform(click());

		onView(withText(toBeCopiedSceneName + " (1)"))
				.check(matches(isDisplayed()));

		onView(withText(toBeCopiedSceneName + " (2)"))
				.check(matches(isDisplayed()));
		int copiedSceneSpritesCount =
				ProjectManager.getInstance().getCurrentProject().getSceneList().get(2).getSpriteList().size();
		int copiedSceneLooksCount =
				ProjectManager.getInstance().getCurrentProject().getSceneList().get(2).getSpriteList().get(1).getLookList().size();
		int copiedSceneSoundFilesCount =
				ProjectManager.getInstance().getCurrentProject().getSceneList().get(2).getSpriteList().get(1).getSoundList().size();

		assertEquals(2, copiedSceneSpritesCount);
		assertEquals(1, copiedSceneLooksCount);
		assertEquals(1, copiedSceneSoundFilesCount);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void selectFragmentToCopyTest() {
		openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
		onView(withText(R.string.copy)).perform(click());

		onRecyclerView().atPosition(0).perform(click());
		onRecyclerView().atPosition(0).performCheckItemCheck();
	}

	private void createProject(Context context, String projectName) throws Exception {

		Project project = new Project(context, projectName);
		Sprite sprite = new Sprite("firstSprite");
		project.getDefaultScene().addSprite(sprite);
		Scene scene2 = new Scene("Scene (1)", project);
		project.addScene(scene2);

		ProjectManager.getInstance().setCurrentProject(project);
		ProjectManager.getInstance().setCurrentSprite(sprite);
		XstreamSerializer.getInstance().saveProject(project);

		Script script = new StartScript();
		script.addBrick(new SetXBrick(new Formula(BrickValues.X_POSITION)));
		sprite.addScript(script);

		File imageFile = ResourceImporter.createImageFileFromResourcesInDirectory(
				InstrumentationRegistry.getInstrumentation().getContext().getResources(),
				com.myradev.lunarcode.test.R.drawable.catroid_banzai,
				new File(project.getDefaultScene().getDirectory(), IMAGE_DIRECTORY_NAME),
				"catroid_sunglasses.png",
				1);

		LookData lookData = new LookData("sunGlasses", imageFile);
		sprite.getLookList().add(lookData);

		File soundFile = ResourceImporter.createSoundFileFromResourcesInDirectory(
				InstrumentationRegistry.getInstrumentation().getContext().getResources(),
				com.myradev.lunarcode.test.R.raw.longsound,
				new File(project.getDefaultScene().getDirectory(), SOUND_DIRECTORY_NAME),
				"longsound.mp3");

		SoundInfo soundInfo = new SoundInfo("sound", soundFile);
		sprite.getSoundList().add(soundInfo);
	}
}
