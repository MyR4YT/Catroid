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
package com.myradev.lunarcode.uiespresso.ui.dialog

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.myradev.lunarcode.ProjectManager
import com.myradev.lunarcode.R
import com.myradev.lunarcode.content.Project
import com.myradev.lunarcode.content.Scene
import com.myradev.lunarcode.content.Script
import com.myradev.lunarcode.content.Sprite
import com.myradev.lunarcode.content.StartScript
import com.myradev.lunarcode.formulaeditor.UserVariable
import com.myradev.lunarcode.io.XstreamSerializer
import com.myradev.lunarcode.io.asynctask.saveProjectSerial
import com.myradev.lunarcode.ui.PROJECT_DIR
import com.myradev.lunarcode.ui.ProjectUploadTestActivity
import com.myradev.lunarcode.uiespresso.util.rules.BaseActivityTestRule
import org.junit.After
import org.junit.Rule
import org.junit.Test

class ReuploadProjectDialogTest {
    @get:Rule
    var activityTestRule = BaseActivityTestRule(
        ProjectUploadTestActivity::class.java, false, false
    )

    var dummyProject: Project? = null
    var projectName = "reUploadedProject"

    fun createDownloadedProject(name: String?) {
        dummyProject = Project(
            ApplicationProvider.getApplicationContext(),
            name
        )
        val dummyScene = dummyProject?.let { Scene("scene", it) }
        ProjectManager.getInstance().currentProject = dummyProject
        val sprite = Sprite("sprite")
        val firstScript: Script = StartScript()
        dummyScene?.addSprite(sprite)
        sprite.addScript(firstScript)
        dummyProject!!.addScene(dummyScene)
        saveProjectSerial(dummyProject, ApplicationProvider.getApplicationContext())
        val intent = Intent()
        intent.putExtra(PROJECT_DIR, dummyProject?.directory)
        activityTestRule.launchActivity(intent)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        ProjectManager.getInstance().deleteDownloadedProjectInformation(projectName)
    }

    @Test
    fun showUploadWarningForUnchangedProjectTest() {
        ProjectManager.getInstance().deleteDownloadedProjectInformation(projectName)
        ProjectManager.getInstance().addNewDownloadedProject(projectName)
        createDownloadedProject(projectName)
        Espresso.onView(ViewMatchers.withText(R.string.warning))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.ok)).perform(ViewActions.click())
    }

    @Test
    fun notShowUploadWarningForChangedProjectTest() {
        ProjectManager.getInstance().loadDownloadedProjects()
        ProjectManager.getInstance().deleteDownloadedProjectInformation(projectName)
        ProjectManager.getInstance().addNewDownloadedProject(projectName)
        createDownloadedProject(projectName)
        Espresso.onView(ViewMatchers.withText(R.string.warning))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.ok)).perform(ViewActions.click())
        val currentProject = ProjectManager.getInstance().currentProject
        val newScene = Scene("scene", currentProject)
        currentProject.addScene(newScene)
        XstreamSerializer.getInstance().saveProject(currentProject)
        saveProjectSerial(currentProject, ApplicationProvider.getApplicationContext())
        val intent = Intent()
        intent.putExtra(PROJECT_DIR, currentProject.directory)
        activityTestRule.launchActivity(intent)
        Espresso.onView(ViewMatchers.withText(R.string.main_menu_upload))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun notShowUploadWarningForAddedVariableProjectTest() {
        ProjectManager.getInstance().loadDownloadedProjects()
        ProjectManager.getInstance().deleteDownloadedProjectInformation(projectName)
        ProjectManager.getInstance().addNewDownloadedProject(projectName)
        createDownloadedProject(projectName)
        Espresso.onView(ViewMatchers.withText(R.string.warning))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.ok)).perform(ViewActions.click())
        val currentProject = ProjectManager.getInstance().currentProject
        val userVariable = UserVariable("uservariable")
        currentProject.addUserVariable(userVariable)
        XstreamSerializer.getInstance().saveProject(currentProject)
        saveProjectSerial(currentProject, ApplicationProvider.getApplicationContext())
        val intent = Intent()
        intent.putExtra(PROJECT_DIR, currentProject.directory)
        activityTestRule.launchActivity(intent)
        Espresso.onView(ViewMatchers.withText(R.string.main_menu_upload))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
