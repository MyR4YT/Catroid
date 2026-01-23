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

package com.myradev.lunarcode.test.io;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.R;
import com.myradev.lunarcode.common.defaultprojectcreators.ChromeCastProjectCreator;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Scene;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.exceptions.LoadingProjectException;
import com.myradev.lunarcode.io.XstreamSerializer;
import com.myradev.lunarcode.test.utils.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DefaultChromeCastProgramLoadingTest {

	private String projectName;
	private Project currentProjectBuffer;
	private Project project;

	@Before
	public void setUp() throws Exception {
		currentProjectBuffer = ProjectManager.getInstance().getCurrentProject();
		projectName = ApplicationProvider.getApplicationContext().getString(R.string.default_cast_project_name);
		project = new ChromeCastProjectCreator()
				.createDefaultProject(projectName, ApplicationProvider.getApplicationContext(), true);
	}

	@After
	public void tearDown() throws Exception {
		ProjectManager.getInstance().setCurrentProject(currentProjectBuffer);
		TestUtils.deleteProjects(projectName);
	}

	@Test
	public void testLoadingChromeCastProgram() throws IOException, LoadingProjectException {
		Project loadedProject = XstreamSerializer.getInstance()
				.loadProject(project.getDirectory(), ApplicationProvider.getApplicationContext());

		Scene preScene = project.getDefaultScene();
		Scene postScene = loadedProject.getDefaultScene();

		ArrayList<Sprite> preSpriteList = (ArrayList<Sprite>) project.getDefaultScene().getSpriteList();
		ArrayList<Sprite> postSpriteList = (ArrayList<Sprite>) loadedProject.getDefaultScene().getSpriteList();

		assertEquals(project.getName(), loadedProject.getName());
		assertEquals(preScene.getName(), postScene.getName());

		assertEquals(preSpriteList.get(0).getName(), postSpriteList.get(0).getName());
		assertEquals(preSpriteList.get(1).getName(), postSpriteList.get(1).getName());
		assertEquals(preSpriteList.get(2).getName(), postSpriteList.get(2).getName());
		assertEquals(preSpriteList.get(3).getName(), postSpriteList.get(3).getName());
	}
}
