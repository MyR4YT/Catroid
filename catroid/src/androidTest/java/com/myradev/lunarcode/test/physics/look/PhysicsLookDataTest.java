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
package com.myradev.lunarcode.test.physics.look;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.common.FlavoredConstants;
import com.myradev.lunarcode.common.LookData;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.io.ResourceImporter;
import com.myradev.lunarcode.io.StorageOperations;
import com.myradev.lunarcode.io.XstreamSerializer;
import com.myradev.lunarcode.physics.PhysicsLook;
import com.myradev.lunarcode.physics.PhysicsObject;
import com.myradev.lunarcode.physics.PhysicsWorld;
import com.myradev.lunarcode.physics.shapebuilder.PhysicsShapeBuilder;
import com.myradev.lunarcode.test.R;
import com.myradev.lunarcode.test.physics.PhysicsTestUtils;
import com.myradev.lunarcode.test.utils.Reflection;
import com.myradev.lunarcode.test.utils.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import static com.myradev.lunarcode.common.Constants.IMAGE_DIRECTORY_NAME;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class PhysicsLookDataTest {

	private PhysicsWorld physicsWorld;
	private final String projectName = "testProject";
	private Sprite sprite;
	private LookData lookData;

	@Before
	public void setUp() throws Exception {
		physicsWorld = new PhysicsWorld(1920, 1600);
		File projectDir = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, projectName);
		if (projectDir.exists()) {
			StorageOperations.deleteDir(projectDir);
		}
		String testImageFilename = PhysicsTestUtils.getInternalImageFilenameFromFilename("testImage.png");
		Project project = new Project(ApplicationProvider.getApplicationContext(), projectName);
		XstreamSerializer.getInstance().saveProject(project);
		ProjectManager.getInstance().setCurrentProject(project);

		File testImage = ResourceImporter.createImageFileFromResourcesInDirectory(
				InstrumentationRegistry.getInstrumentation().getContext().getResources(),
				R.raw.multible_mixed_polygons,
				new File(project.getDefaultScene().getDirectory(), IMAGE_DIRECTORY_NAME),
				testImageFilename,
				1);

		sprite = new Sprite("TestSprite");
		lookData = PhysicsTestUtils.generateLookData(testImage);
		sprite.getLookList().add(lookData);
		Pixmap pixmap = PhysicsTestUtils.getPixmapFromFile(testImage);
		lookData.setPixmap(pixmap);
	}

	@After
	public void tearDown() throws Exception {
		TestUtils.deleteProjects(projectName);
	}

	@Test
	public void testShapeComputationOfLook() {
		PhysicsShapeBuilder physicsShapeBuilder = PhysicsShapeBuilder.getInstance();

		Shape[] shapes = physicsShapeBuilder.getScaledShapes(lookData, sprite.look.getSizeInUserInterfaceDimensionUnit() / 100f);

		assertThat(shapes.length, is(greaterThan(0)));
		physicsShapeBuilder.reset();
	}

	@Test
	public void testSetScale() throws Exception {
		PhysicsObject physicsObject = physicsWorld.getPhysicsObject(sprite);
		PhysicsLook physicsLook = new PhysicsLook(sprite, physicsWorld);
		physicsLook.setLookData(lookData);

		float testScaleFactor = 1.1f;

		Vector2[] expectedVertices = new Vector2[] {
				new Vector2(10.84f, -7.31f),
				new Vector2(10.84f, -0.6f),
				new Vector2(9.63f, 10.62f),
				new Vector2(-10.84f, 10.62f),
				new Vector2(-10.84f, 6.44f),
				new Vector2(-3.35f, -7.31f),
				new Vector2(-0.06f, -10.61f),
				new Vector2(7.54f, -10.61f),
		};

		physicsLook.setScale(testScaleFactor, testScaleFactor);
		Shape[] scaledShapes = (Shape[]) Reflection.getPrivateField(physicsObject, "shapes");
		assertNotNull(scaledShapes);
		assertEquals(1, scaledShapes.length);

		Shape scaledShape = scaledShapes[0];
		assertEquals(Shape.Type.Polygon, scaledShape.getType());

		int scaledVertexCount = ((PolygonShape) scaledShape).getVertexCount();
		assertEquals(8, scaledVertexCount);
		Vector2[] scaledVertices = new Vector2[8];
		for (int idx = 0; idx < scaledVertexCount; idx++) {
			scaledVertices[idx] = new Vector2();
			((PolygonShape) scaledShape).getVertex(idx, scaledVertices[idx]);
		}
		assertArrayEquals(expectedVertices, scaledVertices);
	}
}
