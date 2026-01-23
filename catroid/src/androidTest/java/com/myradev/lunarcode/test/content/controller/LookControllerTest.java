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

package com.myradev.lunarcode.test.content.controller;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.common.LookData;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Scene;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.io.ResourceImporter;
import com.myradev.lunarcode.io.StorageOperations;
import com.myradev.lunarcode.io.XstreamSerializer;
import com.myradev.lunarcode.ui.controller.BackpackListManager;
import com.myradev.lunarcode.ui.recyclerview.controller.LookController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static junit.framework.Assert.assertEquals;

import static com.myradev.lunarcode.common.Constants.IMAGE_DIRECTORY_NAME;
import static com.myradev.lunarcode.test.utils.TestUtils.clearBackPack;
import static com.myradev.lunarcode.uiespresso.util.FileTestUtils.assertFileDoesNotExist;
import static com.myradev.lunarcode.uiespresso.util.FileTestUtils.assertFileDoesNotExistInDirectory;
import static com.myradev.lunarcode.uiespresso.util.FileTestUtils.assertFileExists;
import static com.myradev.lunarcode.uiespresso.util.FileTestUtils.assertFileExistsInDirectory;

@RunWith(AndroidJUnit4.class)
public class LookControllerTest {

	private Project project;
	private Scene scene;
	private Sprite sprite;
	private LookData lookData;
	private BackpackListManager backpackListManager;

	@Before
	public void setUp() throws IOException {
		backpackListManager = BackpackListManager.getInstance();
		clearBackPack(backpackListManager);
		createProject();
	}

	@After
	public void tearDown() throws IOException {
		deleteProject();
		clearBackPack(backpackListManager);
	}

	@Test
	public void testCopyLook() throws IOException {
		LookController controller = new LookController();
		LookData copy = controller.copy(lookData, scene, sprite);

		assertEquals(1, sprite.getLookList().size());
		assertFileExists(copy.getFile());
	}

	@Test
	public void testDeleteLook() throws IOException {
		LookController controller = new LookController();
		File deletedLookFile = lookData.getFile();
		controller.delete(lookData);

		assertEquals(1, sprite.getLookList().size());
		assertFileDoesNotExist(deletedLookFile);
	}

	@Test
	public void testPackLook() throws IOException {
		LookController controller = new LookController();
		LookData packedLook = controller.pack(lookData);

		assertEquals(0, backpackListManager.getBackpackedLooks().size());
		assertFileExistsInDirectory(packedLook.getFile(), backpackListManager.backpackImageDirectory);
	}

	@Test
	public void testDeleteLookFromBackPack() throws IOException {
		LookController controller = new LookController();
		LookData packedLook = controller.pack(lookData);
		controller.delete(packedLook);

		assertEquals(0, BackpackListManager.getInstance().getBackpackedLooks().size());
		assertFileDoesNotExistInDirectory(packedLook.getFile(), backpackListManager.backpackImageDirectory);

		assertEquals(1, sprite.getLookList().size());
		assertFileExists(lookData.getFile());
	}

	@Test
	public void testUnpackLook() throws IOException {
		LookController controller = new LookController();
		LookData packedLook = controller.pack(lookData);
		LookData unpackedLook = controller.unpack(packedLook, scene, sprite);

		assertEquals(0, BackpackListManager.getInstance().getBackpackedLooks().size());
		assertFileExistsInDirectory(packedLook.getFile(), backpackListManager.backpackImageDirectory);

		assertEquals(1, sprite.getLookList().size());
		assertFileExists(unpackedLook.getFile());
	}

	@Test
	public void testDeepCopyLook() throws IOException {
		LookController controller = new LookController();
		LookData copy = controller.copy(lookData, scene, sprite);

		assertFileExists(copy.getFile());

		controller.delete(copy);

		assertFileDoesNotExist(copy.getFile());
		assertFileExists(lookData.getFile());
	}

	private void createProject() throws IOException {
		project = new Project(ApplicationProvider.getApplicationContext(), "LookControllerTest");
		scene = project.getDefaultScene();
		ProjectManager.getInstance().setCurrentProject(project);

		sprite = new Sprite("testSprite");
		project.getDefaultScene().addSprite(sprite);
		XstreamSerializer.getInstance().saveProject(project);

		File imageFile = ResourceImporter.createImageFileFromResourcesInDirectory(
				InstrumentationRegistry.getInstrumentation().getContext().getResources(),
				com.myradev.lunarcode.test.R.raw.red_image,
				new File(project.getDefaultScene().getDirectory(), IMAGE_DIRECTORY_NAME),
				"red_image.bmp",
				1);

		lookData = new LookData("testLook", imageFile);
		sprite.getLookList().add(lookData);

		XstreamSerializer.getInstance().saveProject(project);
	}

	private void deleteProject() throws IOException {
		if (project.getDirectory().exists()) {
			StorageOperations.deleteDir(project.getDirectory());
		}
	}
}
