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

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.R;
import com.myradev.lunarcode.common.LookData;
import com.myradev.lunarcode.common.NfcTagData;
import com.myradev.lunarcode.common.SoundInfo;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.bricks.BroadcastBrick;
import com.myradev.lunarcode.content.bricks.PlaySoundBrick;
import com.myradev.lunarcode.content.bricks.SetLookBrick;
import com.myradev.lunarcode.content.bricks.SetVariableBrick;
import com.myradev.lunarcode.content.bricks.WhenNfcBrick;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.formulaeditor.UserVariable;
import com.myradev.lunarcode.nfc.NfcHandler;
import com.myradev.lunarcode.test.utils.TestUtils;
import com.myradev.lunarcode.ui.SpriteActivity;
import com.myradev.lunarcode.uiespresso.content.brick.utils.UiNFCTestUtils;
import com.myradev.lunarcode.uiespresso.util.UiTestUtils;
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.myradev.lunarcode.WaitForConditionAction.waitFor;
import static com.myradev.lunarcode.uiespresso.content.brick.utils.BrickDataInteractionWrapper.onBrickAtPosition;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(Parameterized.class)
public class ActionBarUndoSpinnerTest {

	private final long waitThreshold = 5000;

	@Rule
	public FragmentActivityTestRule<SpriteActivity> baseActivityTestRule = new
			FragmentActivityTestRule<>(SpriteActivity.class,
			SpriteActivity.EXTRA_FRAGMENT_POSITION, SpriteActivity.FRAGMENT_SCRIPTS);

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{PlaySoundBrick.class.getName(), 1, R.id.brick_play_sound_spinner},
				{SetLookBrick.class.getName(), 2, R.id.brick_set_look_spinner},
				{WhenNfcBrick.class.getName(), 3, R.id.brick_when_nfc_spinner},
				{SetVariableBrick.class.getName(), 4, R.id.set_variable_spinner},
				{BroadcastBrick.class.getName(), 5, R.id.brick_broadcast_spinner},
		});
	}

	@Parameterized.Parameter
	public String name;

	@Parameterized.Parameter(1)
	public int brickPosition;

	@Parameterized.Parameter(2)
	public int brickSpinnerViewId;

	private String firstItem = "abc";
	private String secondItem = "def";
	private String newItem = "new";

	@After
	public void tearDown() throws IOException {
		TestUtils.deleteProjects(ActionBarUndoSpinnerTest.class.getSimpleName());
	}

	@Before
	public void setUp() throws Exception {
		createProject();
		baseActivityTestRule.launchActivity();
	}

	@Test
	public void testUndoSpinnerActionVisible() {
		onView(withId(R.id.menu_undo)).check(doesNotExist());
		onBrickAtPosition(brickPosition)
				.onSpinner(brickSpinnerViewId)
				.performSelectNameable(secondItem);

		onView(withId(R.id.menu_undo))
				.perform(waitFor(isDisplayed(), waitThreshold));

		onBrickAtPosition(brickPosition)
				.onSpinner(brickSpinnerViewId).performSelectNameable(firstItem);

		onView(withId(R.id.menu_undo))
				.perform(waitFor(isDisplayed(), waitThreshold));
	}

	@Test
	public void testUndoSpinnerAction() {
		onBrickAtPosition(brickPosition)
				.onSpinner(brickSpinnerViewId)
				.performSelectNameable(secondItem);
		onView(withId(R.id.menu_undo))
				.perform(click());
		onView(withId(R.id.menu_undo)).check(doesNotExist());
	}

	@Test
	public void testUndoSpinnerNotVisibleAfterNewOptionSelected() {
		if (brickPosition >= 4) {
			onBrickAtPosition(brickPosition)
					.onVariableSpinner(brickSpinnerViewId)
					.performNewVariable(newItem);
			onView(withId(R.id.menu_undo)).check(doesNotExist());
		}
	}

	private void createProject() {
		Script script = UiTestUtils.createProjectAndGetStartScript(ActionBarUndoSpinnerTest.class.getSimpleName());
		Project currentProject = ProjectManager.getInstance().getCurrentProject();
		Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();

		currentProject.addUserVariable(new UserVariable(firstItem));
		currentProject.addUserVariable(new UserVariable(secondItem));

		File fileMock = Mockito.mock(File.class);
		List<SoundInfo> soundInfoList = currentSprite.getSoundList();
		soundInfoList.add(new SoundInfo(firstItem, fileMock));
		soundInfoList.add(new SoundInfo(secondItem, fileMock));

		List<LookData> lookDataList = currentSprite.getLookList();
		lookDataList.add(new LookData(firstItem, fileMock));
		lookDataList.add(new LookData(secondItem, fileMock));

		List<NfcTagData> nfcTagDataList = currentSprite.getNfcTagList();
		NfcTagData firstTagData = new NfcTagData();
		firstTagData.setName(firstItem);
		firstTagData.setNfcTagUid(NfcHandler.byteArrayToHex(UiNFCTestUtils.FIRST_TEST_TAG_ID.getBytes()));
		NfcTagData secondTagData = new NfcTagData();
		secondTagData.setName(secondItem);
		secondTagData.setNfcTagUid(NfcHandler.byteArrayToHex(UiNFCTestUtils.SECOND_TEST_TAG_ID.getBytes()));
		nfcTagDataList.add(firstTagData);
		nfcTagDataList.add(secondTagData);
		WhenNfcBrick whenNfcBrick = new WhenNfcBrick();
		whenNfcBrick.onItemSelected(R.id.brick_when_nfc_spinner, firstTagData);

		script.addBrick(new PlaySoundBrick());
		script.addBrick(new SetLookBrick());
		script.addBrick(whenNfcBrick);
		script.addBrick(new SetVariableBrick(new Formula(1), new UserVariable(firstItem)));
		script.addBrick(new BroadcastBrick(firstItem));
		script.addBrick(new BroadcastBrick(secondItem));
	}
}
