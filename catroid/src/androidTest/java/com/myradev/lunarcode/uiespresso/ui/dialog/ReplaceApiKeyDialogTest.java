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

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.R;
import com.myradev.lunarcode.common.Constants;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Scene;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.bricks.BackgroundRequestBrick;
import com.myradev.lunarcode.ui.ProjectUploadTestActivity;
import com.myradev.lunarcode.uiespresso.util.rules.BaseActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import androidx.test.core.app.ApplicationProvider;

import static com.myradev.lunarcode.common.Constants.CODE_XML_FILE_NAME;
import static com.myradev.lunarcode.common.SharedPreferenceKeys.AGREED_TO_PRIVACY_POLICY_VERSION;
import static com.myradev.lunarcode.io.asynctask.ProjectSaverKt.saveProjectSerial;
import static com.myradev.lunarcode.ui.ProjectUploadActivityKt.PROJECT_DIR;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class ReplaceApiKeyDialogTest {

	private static final String TAG = ReplaceApiKeyDialogTest.class.getSimpleName();

	@Rule
	public BaseActivityTestRule<ProjectUploadTestActivity> activityTestRule =
			new BaseActivityTestRule<>(ProjectUploadTestActivity.class, false, false);

	private int bufferedPrivacyPolicyPreferenceSetting;

	Project dummyProject;
	String apikey = "AIzaas98d7f9a0sdf07ad0sf8a7sd09fASDf97asd9f";
	String linkapikey = "https://catrobat.at/joke?x=AIzaas98d7f9a0sdf07ad0sf8a7sd09fASDf97asd9f/";
	@Before
	public void before() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext());

		bufferedPrivacyPolicyPreferenceSetting = sharedPreferences
				.getInt(AGREED_TO_PRIVACY_POLICY_VERSION, 0);

		sharedPreferences
				.edit()
				.putInt(AGREED_TO_PRIVACY_POLICY_VERSION, Constants.CATROBAT_TERMS_OF_USE_ACCEPTED)
				.commit();
	}

	public void createProject(String secret) {
		dummyProject = new Project(ApplicationProvider.getApplicationContext(), "ApiProject");
		Scene dummyScene = new Scene("scene", dummyProject);
		ProjectManager.getInstance().setCurrentProject(dummyProject);
		Sprite sprite = new Sprite("sprite");
		Script firstScript = new StartScript();
		firstScript.addBrick(new BackgroundRequestBrick(secret));
		dummyScene.addSprite(sprite);
		sprite.addScript(firstScript);
		dummyProject.addScene(dummyScene);
		saveProjectSerial(dummyProject, ApplicationProvider.getApplicationContext());

		Intent intent = new Intent();
		intent.putExtra(PROJECT_DIR, dummyProject.getDirectory());

		activityTestRule.launchActivity(intent);
	}

	public void createProject(String secret1, String secret2) {
		dummyProject = new Project(ApplicationProvider.getApplicationContext(), "ApiProject");
		Scene dummyScene = new Scene("scene", dummyProject);
		ProjectManager.getInstance().setCurrentProject(dummyProject);
		Sprite sprite = new Sprite("sprite");
		Script firstScript = new StartScript();
		firstScript.addBrick(new BackgroundRequestBrick(secret1));
		firstScript.addBrick(new BackgroundRequestBrick(secret2));
		dummyScene.addSprite(sprite);
		sprite.addScript(firstScript);
		dummyProject.addScene(dummyScene);
		saveProjectSerial(dummyProject, ApplicationProvider.getApplicationContext());

		Intent intent = new Intent();
		intent.putExtra(PROJECT_DIR, dummyProject.getDirectory());

		activityTestRule.launchActivity(intent);
	}

	@After
	public void tearDown() throws Exception {
		PreferenceManager.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext())
				.edit()
				.putInt(AGREED_TO_PRIVACY_POLICY_VERSION,
						bufferedPrivacyPolicyPreferenceSetting)
				.commit();
	}

	@Test
	public void replaceApiKeyTestAPI() {
		createProject(apikey);
		File beforeReplace = new File(dummyProject.getDirectory(), CODE_XML_FILE_NAME);
		String beforeReplaceCode = "";
		try {
			beforeReplaceCode =
					Files.asCharSource(beforeReplace, Charsets.UTF_8).read();
		} catch (IOException exception) {
			Log.e(TAG, Log.getStackTraceString(exception));
		}
		assertTrue(beforeReplaceCode.contains(apikey));
		onView(withText(R.string.api_replacement_dialog_accept)).perform(click());
		File afterReplace = new File(dummyProject.getDirectory(), CODE_XML_FILE_NAME);
		String afterReplaceCode = "";
		try {
			afterReplaceCode = Files.asCharSource(afterReplace, Charsets.UTF_8).read();
		} catch (IOException exception) {
			Log.e(TAG, Log.getStackTraceString(exception));
		}
		assertFalse(afterReplaceCode.contains(apikey));
	}

	@Test
	public void replaceApiKeyTestLinkAPI() {
		createProject(linkapikey);
		File beforeReplace = new File(dummyProject.getDirectory(), CODE_XML_FILE_NAME);
		String beforeReplaceCode = "";
		try {
			beforeReplaceCode = Files.asCharSource(beforeReplace, Charsets.UTF_8).read();
		} catch (IOException exception) {
			Log.e(TAG, Log.getStackTraceString(exception));
		}
		assertTrue(beforeReplaceCode.contains(linkapikey));
		onView(withText(R.string.api_replacement_dialog_accept)).perform(click());
		File afterReplace = new File(dummyProject.getDirectory(), CODE_XML_FILE_NAME);
		String afterReplaceCode = "";
		try {
			afterReplaceCode = Files.asCharSource(afterReplace, Charsets.UTF_8).read();
		} catch (IOException exception) {
			Log.e(TAG, Log.getStackTraceString(exception));
		}
		assertFalse(afterReplaceCode.contains(linkapikey));
	}

	@Test
	public void replaceApiKeyTestLoadBackup() {
		createProject(linkapikey, apikey);

		File beforeReplace = new File(dummyProject.getDirectory(), CODE_XML_FILE_NAME);
		String beforeReplaceCode = "";
		try {
			beforeReplaceCode = Files.asCharSource(beforeReplace, Charsets.UTF_8).read();
		} catch (IOException exception) {
			Log.e(TAG, Log.getStackTraceString(exception));
		}
		assertTrue(beforeReplaceCode.contains(linkapikey));
		onView(withText(R.string.api_replacement_dialog_accept)).perform(click());
		File afterReplace = new File(dummyProject.getDirectory(), CODE_XML_FILE_NAME);
		String afterReplaceCode = "";
		try {
			afterReplaceCode =
					Files.asCharSource(afterReplace, Charsets.UTF_8).read();
		} catch (IOException exception) {
			Log.e(TAG, Log.getStackTraceString(exception));
		}
		assertFalse(afterReplaceCode.contains(linkapikey));
		onView(withText(R.string.cancel)).perform(click());

		File reloaded = new File(dummyProject.getDirectory(), CODE_XML_FILE_NAME);
		String reloadedCode = "";
		try {
			reloadedCode = Files.asCharSource(reloaded, Charsets.UTF_8).read();
		} catch (IOException exception) {
			Log.e(TAG, Log.getStackTraceString(exception));
		}
		assertTrue(reloadedCode.contains(linkapikey));
	}
}
