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
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.io.ResourceImporter;
import com.myradev.lunarcode.io.XstreamSerializer;
import com.myradev.lunarcode.physics.PhysicsLook;
import com.myradev.lunarcode.physics.PhysicsWorld;
import com.myradev.lunarcode.physics.content.ActionPhysicsFactory;
import com.myradev.lunarcode.test.R;
import com.myradev.lunarcode.test.utils.TestUtils;
import org.junit.rules.ExternalResource;

import java.io.File;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import static junit.framework.Assert.assertNotNull;

import static com.myradev.lunarcode.common.Constants.IMAGE_DIRECTORY_NAME;

public class PhysicsTestRule extends ExternalResource {

	public Sprite sprite;
	public PhysicsWorld physicsWorld;

	public Project project;
	public String rectangle125x125FileName;
	public File rectangle125x125File;

	@Override
	protected void before() throws Throwable {
		TestUtils.deleteProjects();
		rectangle125x125FileName = PhysicsTestUtils.getInternalImageFilenameFromFilename("rectangle_125x125.png");

		project = new Project(ApplicationProvider.getApplicationContext(), TestUtils.DEFAULT_TEST_PROJECT_NAME);

		physicsWorld = project.getDefaultScene().getPhysicsWorld();
		sprite = new Sprite("TestSprite");
		sprite.look = new PhysicsLook(sprite, physicsWorld);

		project.getDefaultScene().addSprite(sprite);

		XstreamSerializer.getInstance().saveProject(project);
		ProjectManager.getInstance().setCurrentProject(project);

		rectangle125x125File = ResourceImporter.createImageFileFromResourcesInDirectory(
				InstrumentationRegistry.getInstrumentation().getContext().getResources(),
				R.raw.rectangle_125x125,
				new File(project.getDefaultScene().getDirectory(), IMAGE_DIRECTORY_NAME),
				rectangle125x125FileName,
				1);

		LookData lookdata = PhysicsTestUtils.generateLookData(rectangle125x125File);
		sprite.look.setLookData(lookdata);
		sprite.setActionFactory(new ActionPhysicsFactory());

		assertNotNull(sprite.look.getLookData());

		stabilizePhysicsWorld(physicsWorld);
	}

	@Override
	protected void after() {
		sprite = null;
		physicsWorld = null;

		project = null;
		rectangle125x125FileName = null;
		rectangle125x125File = null;
	}

	public static void stabilizePhysicsWorld(PhysicsWorld physicsWorld) {
		for (int index = 0; index < PhysicsWorld.STABILIZING_STEPS; index++) {
			physicsWorld.step(0.0f);
		}
	}
}
