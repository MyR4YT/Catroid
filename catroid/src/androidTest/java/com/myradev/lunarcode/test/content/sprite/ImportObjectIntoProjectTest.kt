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

package com.myradev.lunarcode.test.content.sprite

import android.net.Uri
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.myradev.lunarcode.ProjectManager
import com.myradev.lunarcode.R
import com.myradev.lunarcode.common.Constants
import com.myradev.lunarcode.common.Constants.MEDIA_LIBRARY_CACHE_DIRECTORY
import com.myradev.lunarcode.common.DefaultProjectHandler
import com.myradev.lunarcode.content.Project
import com.myradev.lunarcode.content.Script
import com.myradev.lunarcode.content.Sprite
import com.myradev.lunarcode.content.StartScript
import com.myradev.lunarcode.content.bricks.PlaceAtBrick
import com.myradev.lunarcode.formulaeditor.UserList
import com.myradev.lunarcode.formulaeditor.UserVariable
import com.myradev.lunarcode.io.XstreamSerializer
import com.myradev.lunarcode.io.ZipArchiver
import com.myradev.lunarcode.test.merge.MergeTestUtils
import com.myradev.lunarcode.test.utils.TestUtils
import com.myradev.lunarcode.ui.ProjectActivity
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.java.KoinJavaComponent.inject
import java.io.File

class ImportObjectIntoProjectTest {
    private lateinit var project: Project
    private lateinit var importedProject: Project
    private var importName = "IMPORTED"
    private var defaultProjectName = "defaultProject"
    var uri: Uri? = null
    private var spriteToBeImported: Sprite? = null
    private lateinit var scriptForVisualPlacement: Script
    private val projectManager: ProjectManager = inject(ProjectManager::class.java).value
    val tag: String = ImportObjectIntoProjectTest::class.java.simpleName

    @get:Rule
    var baseActivityTestRule: FragmentActivityTestRule<ProjectActivity> = FragmentActivityTestRule(
        ProjectActivity::class.java,
        ProjectActivity.EXTRA_FRAGMENT_POSITION,
        ProjectActivity.FRAGMENT_SPRITES
    )

    @Throws(Exception::class)
    @Before
    fun setUp() {
        try {
            MEDIA_LIBRARY_CACHE_DIRECTORY.mkdirs()
        } catch (e: Exception) {
            Log.e(tag, Log.getStackTraceString(e))
        }

        TestUtils.deleteProjects(defaultProjectName, importName)

        project = DefaultProjectHandler
            .createAndSaveDefaultProject(
                defaultProjectName,
                ApplicationProvider.getApplicationContext(),
                false
            )

        importedProject = DefaultProjectHandler
            .createAndSaveDefaultProject(
                importName,
                ApplicationProvider.getApplicationContext(),
                false
            )

        initProjectVars()

        XstreamSerializer.getInstance().saveProject(importedProject)

        val projectZip = File(
            MEDIA_LIBRARY_CACHE_DIRECTORY,
            importedProject.name + Constants.CATROBAT_EXTENSION
        )

        ZipArchiver().zip(projectZip, importedProject.directory?.listFiles())
        uri = Uri.fromFile(projectZip)

        projectManager.currentProject = project
        projectManager.currentSprite = project.defaultScene.spriteList[1]
        Intents.init()
        baseActivityTestRule.launchActivity()
    }

    private fun initProjectVars() {
        spriteToBeImported = importedProject.defaultScene.spriteList[1]

        spriteToBeImported!!.userVariables.add(UserVariable("localVariable1"))
        spriteToBeImported!!.userVariables.add(UserVariable("localVariable2"))
        spriteToBeImported!!.userLists.add(UserList("localList1"))
        spriteToBeImported!!.userLists.add(UserList("localList2"))

        importedProject.addUserVariable(UserVariable("globalVariable1", 1))
        importedProject.addUserVariable(UserVariable("globalVariable2", 2))
        importedProject.addUserList(UserList("globalList1"))
        importedProject.addUserList(UserList("globalList2"))

        scriptForVisualPlacement = StartScript().apply {
            addBrick(PlaceAtBrick(100, 200))
        }
    }

    @After
    fun tearDown() {
        Intents.release()
        baseActivityTestRule.finishActivity()
        TestUtils.deleteProjects(defaultProjectName, importName)
    }

    @Test
    fun importProjectWithoutConflictsTest() {
        val anySpriteOfProject = project.defaultScene.spriteList[1]
        anySpriteOfProject.userVariables.add(UserVariable("localVariable3"))
        anySpriteOfProject.userVariables.add(UserVariable("localVariable4"))
        anySpriteOfProject.userLists.add(UserList("localList3"))
        anySpriteOfProject.userLists.add(UserList("localList4"))

        project.addUserVariable(UserVariable("globalVariable3", 1))
        project.addUserVariable(UserVariable("globalVariable4", 2))
        project.addUserList(UserList("globalList3"))
        project.addUserList(UserList("globalList4"))
        XstreamSerializer.getInstance().saveProject(project)

        baseActivityTestRule.activity.addObjectFromUri(uri)
        Espresso.onView(withText(R.string.ok)).perform(click())
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        Espresso.onView(withId(R.id.confirm)).perform(click())

        MergeTestUtils().assertSuccessfulSpriteImport(
            project, importedProject, importedProject
                .defaultScene.spriteList[1], project.defaultScene.spriteList.last(),
            true
        )
    }

    @Test
    fun importProjectWithoutConflictsTestEqualLocalVariable() {
        val anySpriteOfProject = project.defaultScene.spriteList[1]
        anySpriteOfProject.userVariables.add(UserVariable("localVariable1"))
        anySpriteOfProject.userVariables.add(UserVariable("localVariable2"))
        anySpriteOfProject.userLists.add(UserList("localList1"))
        anySpriteOfProject.userLists.add(UserList("localList2"))

        project.addUserVariable(UserVariable("globalVariable3", 1))
        project.addUserVariable(UserVariable("globalVariable4", 2))
        project.addUserList(UserList("globalList3"))
        project.addUserList(UserList("globalList4"))
        XstreamSerializer.getInstance().saveProject(project)

        baseActivityTestRule.activity.addObjectFromUri(uri)
        Espresso.onView(withText(R.string.ok)).perform(click())
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        Espresso.onView(withId(R.id.confirm)).perform(click())

        MergeTestUtils().assertSuccessfulSpriteImport(
            project, importedProject, importedProject
                .defaultScene.spriteList[1], project.defaultScene.spriteList.last(), true
        )
    }

    @Test
    fun importProjectWithoutConflictsTestEqualGlobalVariable() {
        val anySpriteOfProject = project.defaultScene.spriteList[1]
        anySpriteOfProject.userVariables.add(UserVariable("localVariable3"))
        anySpriteOfProject.userVariables.add(UserVariable("localVariable4"))
        anySpriteOfProject.userLists.add(UserList("localList3"))
        anySpriteOfProject.userLists.add(UserList("localList4"))

        project.addUserVariable(UserVariable("globalVariable1", 1))
        project.addUserVariable(UserVariable("globalVariable2", 2))
        project.addUserList(UserList("globalList1"))
        project.addUserList(UserList("globalList2"))
        XstreamSerializer.getInstance().saveProject(project)

        baseActivityTestRule.activity.addObjectFromUri(uri)
        Espresso.onView(withText(R.string.ok)).perform(click())
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        Espresso.onView(withId(R.id.confirm)).perform(click())

        MergeTestUtils().assertSuccessfulSpriteImport(
            project, importedProject, importedProject
                .defaultScene.spriteList[1], project.defaultScene.spriteList.last(), true
        )
    }

    @Test
    fun abortImportWithConflictsTestLocalVar() {
        val anySpriteOfProject = project.defaultScene.spriteList[1]
        anySpriteOfProject.userVariables.add(UserVariable("globalVariable1")) // Conflicting var
        anySpriteOfProject.userVariables.add(UserVariable("localVariable4"))
        anySpriteOfProject.userLists.add(UserList("localList3"))
        anySpriteOfProject.userLists.add(UserList("globalList1")) // Conflicting var

        project.addUserVariable(UserVariable("globalVariable1", 1))
        project.addUserVariable(UserVariable("globalVariable2", 2))
        project.addUserList(UserList("globalList1"))
        project.addUserList(UserList("globalList2"))
        XstreamSerializer.getInstance().saveProject(project)

        val original = MergeTestUtils().getOriginalProjectData(project)

        baseActivityTestRule.activity.addObjectFromUri(uri)
        Espresso.onView(withText(R.string.ok)).perform(click())

        MergeTestUtils().assertRejectedImport(project, original)
    }

    @Test
    fun abortImportWithConflictsTestGlobalVar() {
        val anySpriteOfProject = project.defaultScene.spriteList[1]
        anySpriteOfProject.userVariables.add(UserVariable("localVariable3"))
        anySpriteOfProject.userVariables.add(UserVariable("localVariable4"))
        anySpriteOfProject.userLists.add(UserList("localList3"))
        anySpriteOfProject.userLists.add(UserList("localList2"))

        project.addUserVariable(UserVariable("globalVariable1", 1))
        project.addUserVariable(UserVariable("localVariable1", 2)) // Conflicting var
        project.addUserList(UserList("localList1")) // Conflicting var
        project.addUserList(UserList("globalList2"))
        XstreamSerializer.getInstance().saveProject(project)

        val original = MergeTestUtils().getOriginalProjectData(project)

        baseActivityTestRule.activity.addObjectFromUri(uri)
        Espresso.onView(withText(R.string.ok)).perform(click())

        MergeTestUtils().assertRejectedImport(project, original)
    }

    @Test
    fun rejectProjectImportDialogTest() {
        val anySpriteOfProject = project.defaultScene.spriteList[1]
        anySpriteOfProject.userVariables.add(UserVariable("localVariable3"))
        anySpriteOfProject.userVariables.add(UserVariable("localVariable4"))
        anySpriteOfProject.userLists.add(UserList("localList3"))
        anySpriteOfProject.userLists.add(UserList("localList2"))

        project.addUserVariable(UserVariable("globalVariable1", 1))
        project.addUserVariable(UserVariable("localVariable1", 2)) // Conflicting var
        project.addUserList(UserList("globalList2"))
        XstreamSerializer.getInstance().saveProject(project)

        baseActivityTestRule.activity.addObjectFromUri(uri)
        Espresso.onView(withId(R.id.import_conflicting_variables)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.import_conflicting_variables_try_again))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.conflicting_variables)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.import_conflicting_variables_reason))
            .check(matches(isDisplayed()))
        Espresso.onView(withText(R.string.ok)).inRoot(isDialog()).check(matches(isDisplayed()))
            .perform(click())
    }
}
