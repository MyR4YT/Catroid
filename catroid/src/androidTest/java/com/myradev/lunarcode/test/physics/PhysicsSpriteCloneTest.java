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
package com.myradev.lunarcode.test.physics;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.common.LookData;
import com.myradev.lunarcode.content.Look;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.bricks.Brick;
import com.myradev.lunarcode.content.bricks.SetPhysicsObjectTypeBrick;
import com.myradev.lunarcode.io.ResourceImporter;
import com.myradev.lunarcode.io.XstreamSerializer;
import com.myradev.lunarcode.physics.PhysicsObject;
import com.myradev.lunarcode.physics.PhysicsWorld;
import com.myradev.lunarcode.test.utils.TestUtils;
import com.myradev.lunarcode.ui.recyclerview.controller.SpriteController;
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
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

import static com.myradev.lunarcode.common.Constants.IMAGE_DIRECTORY_NAME;

@RunWith(AndroidJUnit4.class)
public class PhysicsSpriteCloneTest {

	private Sprite sprite;
	private Project project;
	private static final PhysicsObject.Type TYPE_TEST_VALUE = PhysicsObject.Type.DYNAMIC;

	@Before
	public void setUp() throws Exception {
		TestUtils.deleteProjects();

		project = new Project(ApplicationProvider.getApplicationContext(), TestUtils.DEFAULT_TEST_PROJECT_NAME);
		XstreamSerializer.getInstance().saveProject(project);
		ProjectManager.getInstance().setCurrentProject(project);

		sprite = new Sprite("TestSprite");
		project.getDefaultScene().addSprite(sprite);
	}

	@After
	public void tearDown() throws Exception {
		sprite = null;
		project = null;

		TestUtils.deleteProjects();
	}

	@Test
	public void testSpriteClonePhysicsLookAndPhysicsObject() throws IOException {
		StartScript startScript = new StartScript();
		Brick setPhysicsObjectTypeBrick = new SetPhysicsObjectTypeBrick(TYPE_TEST_VALUE);

		startScript.addBrick(setPhysicsObjectTypeBrick);
		sprite.addScript(startScript);

		PhysicsWorld physicsWorld = project.getDefaultScene().getPhysicsWorld();
		sprite.look = new Look(sprite);

		String rectangle125x125FileName = PhysicsTestUtils.getInternalImageFilenameFromFilename("rectangle_125x125.png");
		LookData lookdata;

		File rectangle125x125File = ResourceImporter.createImageFileFromResourcesInDirectory(
				InstrumentationRegistry.getInstrumentation().getContext().getResources(),
				com.myradev.lunarcode.test.R.raw.rectangle_125x125,
				new File(project.getDefaultScene().getDirectory(), IMAGE_DIRECTORY_NAME),
				rectangle125x125FileName,
				1);

		lookdata = PhysicsTestUtils.generateLookData(rectangle125x125File);
		sprite.look.setLookData(lookdata);

		assertNotNull(rectangle125x125File);
		assertNotNull(sprite.look.getLookData());

		PhysicsObject physicsObject = physicsWorld.getPhysicsObject(sprite);

		Sprite clonedSprite = new SpriteController().copy(sprite, project, project.getDefaultScene());

		assertNotNull(clonedSprite.look);

		PhysicsObject clonedPhysicsObject = physicsWorld.getPhysicsObject(clonedSprite);
		assertEquals(physicsObject.getType(), clonedPhysicsObject.getType());
		clonedPhysicsObject.setType(PhysicsObject.Type.FIXED);
		assertNotSame(physicsObject.getType(), clonedPhysicsObject.getType());
	}
}
