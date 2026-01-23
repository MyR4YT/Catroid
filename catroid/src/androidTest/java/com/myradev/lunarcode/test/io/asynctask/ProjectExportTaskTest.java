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

package com.myradev.lunarcode.test.io.asynctask;

import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.common.Constants;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.io.StorageOperations;
import com.myradev.lunarcode.io.asynctask.ProjectExportTask;
import com.myradev.lunarcode.utils.notifications.NotificationData;
import com.myradev.lunarcode.utils.notifications.StatusBarNotificationManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.myradev.lunarcode.common.Constants.CATROBAT_EXTENSION;
import static com.myradev.lunarcode.common.Constants.CODE_XML_FILE_NAME;
import static com.myradev.lunarcode.common.Constants.DOWNLOAD_DIRECTORY;
import static com.myradev.lunarcode.common.Constants.UNDO_CODE_XML_FILE_NAME;
import static com.myradev.lunarcode.io.asynctask.ProjectSaverKt.saveProjectSerial;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(AndroidJUnit4.class)
public class ProjectExportTaskTest {

	public static final String TAG = ProjectExportTaskTest.class.getSimpleName();

	private Project project;
	private Context contextMock;
	private File projectZip;

	@Before
	public void setUp() {
		project = new Project(ApplicationProvider.getApplicationContext(),
				ProjectExportTaskTest.class.getSimpleName());

		ProjectManager.getInstance().setCurrentProject(project);
		saveProjectSerial(project, ApplicationProvider.getApplicationContext());

		NotificationManager notificationManagerMock = Mockito.mock(NotificationManager.class);
		contextMock = Mockito.mock(Context.class);
		Mockito.when(contextMock.getResources()).thenReturn(ApplicationProvider.getApplicationContext().getResources());
		Mockito.when(contextMock.getSystemService(anyString())).thenReturn(notificationManagerMock);
	}

	@Test
	public void exportProjectTest() {
		createUndoCodeXmlFile();

		StatusBarNotificationManager notificationManager = new StatusBarNotificationManager(contextMock);

		String fileName = project.getDirectory().getName() + "_destination" + CATROBAT_EXTENSION;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			projectZip = new File(Constants.CACHE_DIRECTORY, fileName);
		} else {
			projectZip = new File(DOWNLOAD_DIRECTORY, fileName);
		}
		Uri projectUri = Uri.fromFile(projectZip);
		NotificationData notificationData = notificationManager
				.createSaveProjectToExternalMemoryNotification(ApplicationProvider.getApplicationContext(),
						projectUri, project.getName());

		ProjectExportTask task =
				new ProjectExportTask(project.getDirectory(), projectUri, notificationData,
						ApplicationProvider.getApplicationContext());

		task.registerCallback(() -> {
			assertTrue(projectZip.exists());

			checkUndoCodeXmlFileIsDeleted(projectZip);
		});
		task.exportProjectToExternalStorage();
	}

	private void createUndoCodeXmlFile() {
		File currentCodeFile = new File(project.getDirectory(), CODE_XML_FILE_NAME);
		File undoCodeFile = new File(project.getDirectory(), UNDO_CODE_XML_FILE_NAME);

		try {
			StorageOperations.transferData(currentCodeFile, undoCodeFile);
		} catch (IOException exception) {
			Log.e(TAG, "Copying project " + project.getName() + " failed.", exception);
		}
	}

	private void checkUndoCodeXmlFileIsDeleted(File externalProjectZip) {
		String zipFileName = externalProjectZip.getAbsolutePath().replace(CATROBAT_EXTENSION, ".zip");
		externalProjectZip.renameTo(new File(zipFileName));

		try {
			ZipFile zipFile = new ZipFile(zipFileName);
			Enumeration zipEntries = zipFile.entries();
			String fileName;

			while (zipEntries.hasMoreElements()) {
				fileName = ((ZipEntry) zipEntries.nextElement()).getName();
				assertNotEquals(UNDO_CODE_XML_FILE_NAME, fileName);
			}
		} catch (IOException exception) {
			Log.e(TAG, "Creating zip folder failed.", exception);
		}
	}

	@After
	public void tearDown() throws Exception {
		if (project.getDirectory().isDirectory()) {
			StorageOperations.deleteDir(project.getDirectory());
		}
		projectZip.delete();
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
				&& DOWNLOAD_DIRECTORY.exists()) {
			StorageOperations.deleteDir(DOWNLOAD_DIRECTORY);
		}
	}
}
