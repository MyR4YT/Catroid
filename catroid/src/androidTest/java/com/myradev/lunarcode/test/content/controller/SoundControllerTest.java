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
import com.myradev.lunarcode.common.SoundInfo;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Scene;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.io.ResourceImporter;
import com.myradev.lunarcode.io.StorageOperations;
import com.myradev.lunarcode.io.XstreamSerializer;
import com.myradev.lunarcode.ui.controller.BackpackListManager;
import com.myradev.lunarcode.ui.recyclerview.controller.SoundController;
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

import static com.myradev.lunarcode.common.Constants.SOUND_DIRECTORY_NAME;
import static com.myradev.lunarcode.test.utils.TestUtils.clearBackPack;
import static com.myradev.lunarcode.uiespresso.util.FileTestUtils.assertFileDoesNotExist;
import static com.myradev.lunarcode.uiespresso.util.FileTestUtils.assertFileDoesNotExistInDirectory;
import static com.myradev.lunarcode.uiespresso.util.FileTestUtils.assertFileExists;
import static com.myradev.lunarcode.uiespresso.util.FileTestUtils.assertFileExistsInDirectory;

@RunWith(AndroidJUnit4.class)
public class SoundControllerTest {

	private Project project;
	private Scene scene;
	private Sprite sprite;
	private SoundInfo soundInfo;
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
	public void testCopySound() throws IOException {
		SoundController controller = new SoundController();
		SoundInfo copy = controller.copy(soundInfo, scene, sprite);

		assertEquals(1, sprite.getSoundList().size());
		assertFileExists(copy.getFile());
	}

	@Test
	public void testDeleteSound() throws IOException {
		SoundController controller = new SoundController();
		File deletedSoundFile = soundInfo.getFile();
		controller.delete(soundInfo);

		assertEquals(1, sprite.getSoundList().size());
		assertFileDoesNotExist(deletedSoundFile);
	}

	@Test
	public void testPackSound() throws IOException {
		SoundController controller = new SoundController();
		SoundInfo packedSound = controller.pack(soundInfo);

		assertEquals(0, backpackListManager.getBackpackedSounds().size());

		assertFileExists(packedSound.getFile());
		assertFileExistsInDirectory(packedSound.getFile(),
				backpackListManager.backpackSoundDirectory);
	}

	@Test
	public void testDeleteSoundFromBackPack() throws IOException {
		SoundController controller = new SoundController();
		SoundInfo packedSound = controller.pack(soundInfo);
		controller.delete(packedSound);

		assertEquals(0, BackpackListManager.getInstance().getBackpackedSounds().size());

		assertFileDoesNotExist(packedSound.getFile());
		assertFileDoesNotExistInDirectory(packedSound.getFile(), backpackListManager.backpackSoundDirectory);

		assertEquals(1, sprite.getSoundList().size());
		assertFileExists(soundInfo.getFile());
	}

	@Test
	public void testUnpackSound() throws IOException {
		SoundController controller = new SoundController();
		SoundInfo packedSound = controller.pack(soundInfo);
		SoundInfo unpackedSound = controller.unpack(packedSound, scene, sprite);

		assertEquals(0, BackpackListManager.getInstance().getBackpackedSounds().size());

		assertFileExists(packedSound.getFile());
		assertFileExistsInDirectory(packedSound.getFile(), backpackListManager.backpackSoundDirectory);

		assertEquals(1, sprite.getSoundList().size());
		assertFileExists(unpackedSound.getFile());
	}

	@Test
	public void testDeepCopySound() throws IOException {
		SoundController controller = new SoundController();
		SoundInfo copy = controller.copy(soundInfo, scene, sprite);

		assertFileExists(copy.getFile());

		controller.delete(copy);

		assertFileDoesNotExist(copy.getFile());
		assertFileExists(soundInfo.getFile());
	}

	private void createProject() throws IOException {
		project = new Project(ApplicationProvider.getApplicationContext(), "SoundControllerTest");
		scene = project.getDefaultScene();
		ProjectManager.getInstance().setCurrentProject(project);

		sprite = new Sprite("testSprite");
		scene.addSprite(sprite);

		XstreamSerializer.getInstance().saveProject(project);

		File soundFile = ResourceImporter.createSoundFileFromResourcesInDirectory(
				InstrumentationRegistry.getInstrumentation().getContext().getResources(),
				com.myradev.lunarcode.test.R.raw.longsound,
				new File(project.getDefaultScene().getDirectory(), SOUND_DIRECTORY_NAME),
				"longsound.mp3");

		soundInfo = new SoundInfo("testSound", soundFile);
		sprite.getSoundList().add(soundInfo);

		XstreamSerializer.getInstance().saveProject(project);
	}

	private void deleteProject() throws IOException {
		if (project.getDirectory().exists()) {
			StorageOperations.deleteDir(project.getDirectory());
		}
	}
}
