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

package com.myradev.lunarcode.test.devices.phiro;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.common.FlavoredConstants;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.bricks.SetSizeToBrick;
import com.myradev.lunarcode.exceptions.ProjectException;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.formulaeditor.FormulaElement;
import com.myradev.lunarcode.formulaeditor.Sensors;
import com.myradev.lunarcode.io.StorageOperations;
import com.myradev.lunarcode.ui.settingsfragments.SettingsFragment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import static com.myradev.lunarcode.io.asynctask.ProjectSaverKt.saveProjectSerial;

@RunWith(AndroidJUnit4.class)
public class PhiroSettingsTest {

	private boolean sharedPreferenceBuffer;
	private String projectName = "testProject";
	private Project project;

	@Before
	public void setUp() throws Exception {
		sharedPreferenceBuffer =
				SettingsFragment.isPhiroSharedPreferenceEnabled(ApplicationProvider.getApplicationContext());
		SettingsFragment.setPhiroSharedPreferenceEnabled(ApplicationProvider.getApplicationContext(), false);
		createProject();
	}

	@After
	public void tearDown() throws Exception {
		SettingsFragment
				.setPhiroSharedPreferenceEnabled(ApplicationProvider.getApplicationContext(), sharedPreferenceBuffer);
	}

	@Test
	public void testIfPhiroBricksAreEnabledIfItItUsedInAProgram() throws IOException, ProjectException {
		assertFalse(SettingsFragment.isPhiroSharedPreferenceEnabled(ApplicationProvider.getApplicationContext()));

		ProjectManager.getInstance()
				.loadProject(project.getDirectory(), ApplicationProvider.getApplicationContext());

		assertTrue(SettingsFragment.isPhiroSharedPreferenceEnabled(ApplicationProvider.getApplicationContext()));

		StorageOperations.deleteDir(new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, projectName));
	}

	private void createProject() {
		project = new Project(ApplicationProvider.getApplicationContext(), projectName);
		Sprite sprite = new Sprite("Phiro");
		StartScript startScript = new StartScript();
		SetSizeToBrick setSizeToBrick = new SetSizeToBrick(
				new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR,
						Sensors.PHIRO_BOTTOM_LEFT.name(), null)));

		startScript.addBrick(setSizeToBrick);
		sprite.addScript(startScript);
		project.getDefaultScene().addSprite(sprite);

		ProjectManager.getInstance().setCurrentProject(project);
		saveProjectSerial(project, ApplicationProvider.getApplicationContext());
	}
}
