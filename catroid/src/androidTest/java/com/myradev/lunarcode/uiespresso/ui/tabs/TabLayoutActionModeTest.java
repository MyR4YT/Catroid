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

package com.myradev.lunarcode.uiespresso.ui.tabs;

import com.google.android.material.tabs.TabLayout;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.R;
import com.myradev.lunarcode.common.LookData;
import com.myradev.lunarcode.common.SoundInfo;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.io.ResourceImporter;
import com.myradev.lunarcode.io.XstreamSerializer;
import com.myradev.lunarcode.ui.SpriteActivity;
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import static com.myradev.lunarcode.R.id.tab_layout;
import static com.myradev.lunarcode.common.Constants.SOUND_DIRECTORY_NAME;
import static com.myradev.lunarcode.ui.SpriteActivity.FRAGMENT_LOOKS;
import static com.myradev.lunarcode.ui.SpriteActivity.FRAGMENT_SCRIPTS;
import static com.myradev.lunarcode.ui.SpriteActivity.FRAGMENT_SOUNDS;
import static com.myradev.lunarcode.uiespresso.util.actions.TabActionsKt.selectTabAtPosition;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;
import static org.koin.java.KoinJavaComponent.inject;

import static androidx.test.espresso.Espresso.onIdle;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(Parameterized.class)
public class TabLayoutActionModeTest {
	@Rule
	public FragmentActivityTestRule<SpriteActivity> baseActivityTestRule = new
			FragmentActivityTestRule<>(SpriteActivity.class, SpriteActivity.EXTRA_FRAGMENT_POSITION, FRAGMENT_SCRIPTS);

	@Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{FRAGMENT_SCRIPTS},
				{FRAGMENT_LOOKS},
				{FRAGMENT_SOUNDS}
		});
	}

	@Parameter
	public Integer fragment;

	private Project project;
	final ProjectManager projectManager = inject(ProjectManager.class).getValue();

	@Before
	public void setUp() throws IOException {
		createProject();
		baseActivityTestRule.launchActivity();
	}

	@Test
	public void testFragmentAbortDelete() {
		onView(withId(tab_layout)).perform(selectTabAtPosition(fragment));
		assertTabLayoutIsShown(fragment);
		openActionBarOverflowOrOptionsMenu(baseActivityTestRule.getActivity());
		onView(withText(R.string.delete)).perform(click());
		assertTabLayoutIsNotShown();
		pressBack();
		assertTabLayoutIsShown(fragment);
	}

	private void assertTabLayoutIsShown(int tabSelected) {
		onIdle();
		TabLayout tabLayout = baseActivityTestRule.getActivity().findViewById(tab_layout);
		assertNotNull(tabLayout);
		assertEquals(tabSelected, tabLayout.getSelectedTabPosition());
	}

	private void assertTabLayoutIsNotShown() {
		onIdle();
		assertNull(baseActivityTestRule.getActivity().findViewById(tab_layout));
	}

	private void createProject() throws IOException {
		project = new Project(ApplicationProvider.getApplicationContext(), "TabLayoutActionModeTest");
		Sprite sprite = new Sprite("testSprite");
		project.getDefaultScene().addSprite(sprite);
		projectManager.setCurrentProject(project);
		projectManager.setCurrentSprite(sprite);
		projectManager.setCurrentlyEditedScene(project.getDefaultScene());
		XstreamSerializer.getInstance().saveProject(project);

		Script script = new StartScript();
		sprite.addScript(script);
		sprite.addScript(new StartScript());
		sprite.getLookList().add(createLookData());
		sprite.getLookList().add(createLookData());
		sprite.getSoundList().add(createSoundInfo());
		sprite.getSoundList().add(createSoundInfo());
	}

	private LookData createLookData() {
		LookData lookData = new LookData();
		lookData.setFile(Mockito.mock(File.class));
		lookData.setName("look");
		return lookData;
	}

	private SoundInfo createSoundInfo() throws IOException {
		File soundFile = ResourceImporter.createSoundFileFromResourcesInDirectory(
				InstrumentationRegistry.getInstrumentation().getContext().getResources(),
				com.myradev.lunarcode.test.R.raw.longsound,
				new File(project.getDefaultScene().getDirectory(), SOUND_DIRECTORY_NAME),
				"longsound.mp3");

		SoundInfo soundInfo = new SoundInfo();
		soundInfo.setFile(soundFile);
		soundInfo.setName("sound");
		return soundInfo;
	}
}
