/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2025  The Catrobat Team
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

package com.myradev.lunarcode.test.ui

import junit.framework.TestCase.assertEquals
import com.myradev.lunarcode.ProjectManager
import com.myradev.lunarcode.content.Scene
import com.myradev.lunarcode.content.Script
import com.myradev.lunarcode.content.Sprite
import com.myradev.lunarcode.content.bricks.AddItemToUserListBrick
import com.myradev.lunarcode.content.bricks.Brick
import com.myradev.lunarcode.content.bricks.SetVariableBrick
import com.myradev.lunarcode.formulaeditor.Formula
import com.myradev.lunarcode.formulaeditor.UserList
import com.myradev.lunarcode.formulaeditor.UserVariable
import com.myradev.lunarcode.test.utils.TestUtils
import com.myradev.lunarcode.ui.controller.BackpackListManager
import com.myradev.lunarcode.ui.recyclerview.controller.ScriptController
import com.myradev.lunarcode.ui.recyclerview.controller.SpriteController
import com.myradev.lunarcode.uiespresso.util.UiTestUtils
import org.junit.After
import org.junit.Test
import org.koin.java.KoinJavaComponent.inject

class BackpackTest {

    private val backpackManager by inject(BackpackListManager::class.java)
    private val projectManager by inject(ProjectManager::class.java)

    @After
    fun tearDown() {
        TestUtils.deleteProjects(projectName + "_1", projectName + "_2")
    }

    @Test
    fun shouldPackAndUnpackScriptWithVariableAndList() {
        // given
        val script = UiTestUtils.createProjectAndGetStartScript(projectName)
        val scriptController = ScriptController()
        val scriptGroupName = "scriptGroup"
        addVarAndSetVarBrick("varName", script)
        addListAndAddItemToListBrick("listName", script)
        packScript(scriptController, scriptGroupName, script)

        // when
        scriptController.unpack(
            scriptGroupName, backpackManager
                .backpackedScripts[scriptGroupName]!![0], projectManager.currentSprite
        )

        // then
        assertEquals(1, projectManager.currentSprite.userVariables.size)
        assertEquals(1, projectManager.currentSprite.userLists.size)
        assertEquals(2, projectManager.currentSprite.scriptList.size)
        backpackManager.removeItemFromScriptBackPack(scriptGroupName)
    }

    @Test
    fun shouldPackAndUnpackScriptWithVariableAndListInOtherSprite() {
        // given
        val script = UiTestUtils.createProjectAndGetStartScript(projectName)
        val scriptController = ScriptController()
        val scriptGroupName = "scriptGroup"
        val varName = "varName"
        val listName = "listName"
        addVarAndSetVarBrick(varName, script)
        addListAndAddItemToListBrick(listName, script)
        packScript(scriptController, scriptGroupName, script)
        val scene = Scene("newScene", projectManager.currentProject)
        val sprite = Sprite("newSprite")
        scene.addSprite(sprite)
        projectManager.currentProject.addScene(scene)
        projectManager.setCurrentSceneAndSprite("newScene", "newSprite")

        // when
        scriptController.unpack(
            scriptGroupName, backpackManager
                .backpackedScripts[scriptGroupName]!![0], projectManager.currentSprite
        )

        // then
        val unpackedSetVarBrick = projectManager.currentSprite.scriptList[0].brickList[0] as
            SetVariableBrick
        val unpackedAddItemToListBrick = projectManager.currentSprite.scriptList[0].brickList[1]
            as AddItemToUserListBrick
        assertEquals(1, projectManager.currentSprite.userVariables.size)
        assertEquals(varName, projectManager.currentSprite.userVariables[0].name)
        assertEquals(
            projectManager.currentSprite.userVariables[0],
            unpackedSetVarBrick.userVariable
        )
        assertEquals(1, projectManager.currentSprite.userLists.size)
        assertEquals(listName, projectManager.currentSprite.userLists[0].name)
        assertEquals(projectManager.currentSprite.userLists[0], unpackedAddItemToListBrick.userList)
        assertEquals(1, projectManager.currentSprite.scriptList.size)
        backpackManager.removeItemFromScriptBackPack(scriptGroupName)
    }

    @Test
    fun shouldPackAndUnpackScriptWithVariableAndListInOtherProject() {
        // given
        val script = UiTestUtils.createProjectAndGetStartScript(projectName + "_1")
        val scriptController = ScriptController()
        val scriptGroupName = "scriptGroup"
        val varName = "varName"
        val listName = "listName"
        addVarAndSetVarBrick(varName, script)
        addListAndAddItemToListBrick(listName, script)
        packScript(scriptController, scriptGroupName, script)
        UiTestUtils.createDefaultTestProject(projectName + "_2")

        // when
        scriptController.unpack(
            scriptGroupName, backpackManager
                .backpackedScripts[scriptGroupName]!![0], projectManager.currentSprite
        )

        // then
        val unpackedSetVarBrick = projectManager.currentSprite.scriptList[1].brickList[0] as
            SetVariableBrick
        val unpackedAddItemToListBrick = projectManager.currentSprite.scriptList[1].brickList[1]
            as AddItemToUserListBrick
        assertEquals(1, projectManager.currentSprite.userVariables.size)
        assertEquals(varName, projectManager.currentSprite.userVariables[0].name)
        assertEquals(
            projectManager.currentSprite.userVariables[0],
            unpackedSetVarBrick.userVariable
        )
        assertEquals(1, projectManager.currentSprite.userLists.size)
        assertEquals(listName, projectManager.currentSprite.userLists[0].name)
        assertEquals(projectManager.currentSprite.userLists[0], unpackedAddItemToListBrick.userList)
        assertEquals(2, projectManager.currentSprite.scriptList.size)
        backpackManager.removeItemFromScriptBackPack(scriptGroupName)
    }

    @Test
    fun shouldPackAndUnpackScriptWithLocalVariableAndListIntoProjectWithConflictingPublics() {
        // given
        val script = UiTestUtils.createProjectAndGetStartScript(projectName + "_1")
        val scriptController = ScriptController()
        val scriptGroupName = "scriptGroup"
        val varName = "varName"
        val listName = "listName"
        addVarAndSetVarBrick(varName, script)
        addListAndAddItemToListBrick(listName, script)
        packScript(scriptController, scriptGroupName, script)
        val scriptInOtherProject = UiTestUtils.createProjectAndGetStartScript(projectName + "_2")
        addVarAndSetVarBrick(varName, scriptInOtherProject, true)
        addListAndAddItemToListBrick(listName, scriptInOtherProject, true)

        // when
        scriptController.unpack(
            scriptGroupName, backpackManager
                .backpackedScripts[scriptGroupName]!![0], projectManager.currentSprite
        )

        // then
        val unpackedSetVarBrick = projectManager.currentSprite.scriptList[1].brickList[0] as
            SetVariableBrick
        val unpackedAddItemToListBrick = projectManager.currentSprite.scriptList[1].brickList[1]
            as AddItemToUserListBrick
        assertEquals(1, projectManager.currentSprite.userVariables.size)
        assertEquals("$varName (1)", projectManager.currentSprite.userVariables[0].name)
        assertEquals(1, projectManager.currentProject.userVariables.size)
        assertEquals(
            projectManager.currentSprite.userVariables[0],
            unpackedSetVarBrick.userVariable
        )
        assertEquals(1, projectManager.currentSprite.userLists.size)
        assertEquals("$listName (1)", projectManager.currentSprite.userLists[0].name)
        assertEquals(1, projectManager.currentProject.userLists.size)
        assertEquals(projectManager.currentSprite.userLists[0], unpackedAddItemToListBrick.userList)
        assertEquals(2, projectManager.currentSprite.scriptList.size)
        backpackManager.removeItemFromScriptBackPack(scriptGroupName)
    }

    @Test
    fun shouldPackAndUnpackScriptWithPublicVariableAndListIntoProjectWithConflictingLocals() {
        // given
        val script = UiTestUtils.createProjectAndGetStartScript(projectName + "_1")
        val scriptController = ScriptController()
        val scriptGroupName = "scriptGroup"
        val varName = "varName"
        val listName = "listName"
        addVarAndSetVarBrick(varName, script, true)
        addListAndAddItemToListBrick(listName, script, true)
        packScript(scriptController, scriptGroupName, script)
        val scriptInOtherProject = UiTestUtils.createProjectAndGetStartScript(projectName + "_2")
        addVarAndSetVarBrick(varName, scriptInOtherProject)
        addListAndAddItemToListBrick(listName, scriptInOtherProject)

        // when
        scriptController.unpack(
            scriptGroupName, backpackManager
                .backpackedScripts[scriptGroupName]!![0], projectManager.currentSprite
        )

        // then
        val unpackedSetVarBrick = projectManager.currentSprite.scriptList[1].brickList[0] as
            SetVariableBrick
        val unpackedAddItemToListBrick = projectManager.currentSprite.scriptList[1].brickList[1]
            as AddItemToUserListBrick
        assertEquals(1, projectManager.currentSprite.userVariables.size)
        assertEquals("$varName (1)", projectManager.currentProject.userVariables[0].name)
        assertEquals(1, projectManager.currentProject.userVariables.size)
        assertEquals(
            projectManager.currentProject.userVariables[0],
            unpackedSetVarBrick.userVariable
        )
        assertEquals(1, projectManager.currentSprite.userLists.size)
        assertEquals("$listName (1)", projectManager.currentProject.userLists[0].name)
        assertEquals(1, projectManager.currentProject.userLists.size)
        assertEquals(
            projectManager.currentProject.userLists[0],
            unpackedAddItemToListBrick.userList
        )
        assertEquals(2, projectManager.currentSprite.scriptList.size)
        backpackManager.removeItemFromScriptBackPack(scriptGroupName)
    }

    @Test
    fun shouldPackAndUnpackSprite() {
        // given
        backpackManager.sprites.clear()
        val script = UiTestUtils.createProjectAndGetStartScript(projectName + "_1")
        val spriteController = SpriteController()
        val varName = "varName"
        val listName = "listName"
        addVarAndSetVarBrick(varName, script, true)
        addListAndAddItemToListBrick(listName, script, true)
        val packedSprite = spriteController.pack(projectManager.currentSprite)
        val scriptInOtherProject = UiTestUtils.createProjectAndGetStartScript(projectName + "_2")
        addVarAndSetVarBrick(varName, scriptInOtherProject)
        addListAndAddItemToListBrick(listName, scriptInOtherProject)

        // when
        spriteController.unpack(
            packedSprite,
            projectManager.currentProject,
            projectManager.currentlyEditedScene
        )

        // then
        val newSprite = projectManager.currentlyEditedScene.spriteList[1]
        projectManager.currentSprite = newSprite
        val unpackedSetVarBrick =
            projectManager.currentSprite.scriptList[0].brickList[0] as SetVariableBrick
        val unpackedAddItemToListBrick =
            projectManager.currentSprite.scriptList[0].brickList[1] as AddItemToUserListBrick
        assertEquals(1, projectManager.currentSprite.userVariables.size)
        assertEquals(varName, projectManager.currentSprite.userVariables[0].name)
        assertEquals(
            projectManager.currentSprite.userVariables[0],
            unpackedSetVarBrick.userVariable
        )
        assertEquals(1, projectManager.currentSprite.userLists.size)
        assertEquals(listName, projectManager.currentSprite.userLists[0].name)
        assertEquals(projectManager.currentSprite.userLists[0], unpackedAddItemToListBrick.userList)
        assertEquals(2, projectManager.currentlyEditedScene.spriteList.size)
    }

    private fun addVarAndSetVarBrick(name: String, script: Script, isPublic: Boolean = false) {
        val variable = UserVariable(name)
        if (isPublic) {
            projectManager.currentProject.addUserVariable(variable)
        } else {
            projectManager.currentSprite.addUserVariable(variable)
        }
        val formula = Formula("0")
        val setVariableBrick = SetVariableBrick(formula, variable)
        script.addBrick(setVariableBrick)
    }

    private fun addListAndAddItemToListBrick(
        name: String,
        script: Script,
        isPublic: Boolean = false
    ) {
        val userList = UserList(name)
        if (isPublic) {
            projectManager.currentProject.addUserList(userList)
        } else {
            projectManager.currentSprite.addUserList(userList)
        }
        val addItemToUserListBrick = AddItemToUserListBrick(3.3)
        addItemToUserListBrick.userList = userList
        script.addBrick(addItemToUserListBrick)
    }

    private fun packScript(
        scriptController: ScriptController,
        scriptGroupName: String,
        script: Script
    ) {
        val selectedBricks = ArrayList<Brick>()
        selectedBricks.add(script.scriptBrick)
        selectedBricks.addAll(script.brickList)
        if (backpackManager.backpackedScripts.containsKey(scriptGroupName)) {
            backpackManager.removeItemFromScriptBackPack(scriptGroupName)
        }
        scriptController.pack(scriptGroupName, selectedBricks)
    }

    private companion object {
        val projectName = BackpackTest::class.simpleName
    }
}
