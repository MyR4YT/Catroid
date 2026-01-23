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
package com.myradev.lunarcode.test.formulaeditor

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.myradev.lunarcode.ProjectManager
import com.myradev.lunarcode.R
import com.myradev.lunarcode.content.Project
import com.myradev.lunarcode.content.Script
import com.myradev.lunarcode.content.Sprite
import com.myradev.lunarcode.content.StartScript
import com.myradev.lunarcode.content.bricks.Brick
import com.myradev.lunarcode.content.bricks.FormulaBrick
import com.myradev.lunarcode.content.bricks.NoteBrick
import com.myradev.lunarcode.formulaeditor.Formula
import com.myradev.lunarcode.formulaeditor.FormulaElement
import com.myradev.lunarcode.formulaeditor.FormulaElement.ElementType.FUNCTION
import com.myradev.lunarcode.formulaeditor.FormulaElement.ElementType.NUMBER
import com.myradev.lunarcode.formulaeditor.FormulaElement.ElementType.OPERATOR
import com.myradev.lunarcode.formulaeditor.FormulaElement.ElementType.USER_LIST
import com.myradev.lunarcode.formulaeditor.FormulaElement.ElementType.USER_VARIABLE
import com.myradev.lunarcode.formulaeditor.Functions.CONTAINS
import com.myradev.lunarcode.formulaeditor.Functions.FLATTEN
import com.myradev.lunarcode.formulaeditor.Functions.INDEX_OF_ITEM
import com.myradev.lunarcode.formulaeditor.Functions.LENGTH
import com.myradev.lunarcode.formulaeditor.Functions.LIST_ITEM
import com.myradev.lunarcode.formulaeditor.Functions.NUMBER_OF_ITEMS
import com.myradev.lunarcode.formulaeditor.Functions.SQRT
import com.myradev.lunarcode.formulaeditor.Operators.PLUS
import com.myradev.lunarcode.test.utils.TestUtils.deleteProjects
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.IOException

@RunWith(Parameterized::class)
class FlattenListTest(
    private val name: String,
    private val parentFormulaElement: FormulaElement,
    private val leftChildrenFormulaElementList: List<FormulaElement>?,
    private val rightChildrenFormulaElementList: List<FormulaElement>?,
    private val expectedValue: String?
) {
    private var projectManager: ProjectManager? = null
    private lateinit var project: Project
    private lateinit var brick: Brick
    private lateinit var context: Context

    @Before
    @Throws(Exception::class)
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        projectManager = ProjectManager.getInstance()
        createProject()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        projectManager?.currentProject = null
        deleteProjects(PROJECT_NAME)
    }

    @Test
    @Throws(IOException::class)
    fun testFlattenAllLists() {
        ProjectManager.flattenAllLists(project)
        val newFormula = (brick as FormulaBrick).formulas[0].getTrimmedFormulaString(context)
        assertEquals(expectedValue, newFormula)
    }

    fun createProject() {
        val sprite = Sprite(SPRITE_NAME)
        val script: Script = StartScript()
        brick = NoteBrick(buildFormula())
        script.addBrick(brick)
        sprite.addScript(script)
        project = Project(context, PROJECT_NAME)
        project.defaultScene.addSprite(sprite)
        ProjectManager.getInstance().currentProject = project
        ProjectManager.getInstance().currentSprite = sprite
    }

    private fun buildFormula(): Formula {
        linkFormulaElements(leftChildrenFormulaElementList)
        linkFormulaElements(rightChildrenFormulaElementList)
        if (leftChildrenFormulaElementList != null && leftChildrenFormulaElementList.isNotEmpty()) {
            parentFormulaElement.setLeftChild(leftChildrenFormulaElementList[0])
        }
        if (rightChildrenFormulaElementList != null && rightChildrenFormulaElementList.isNotEmpty()) {
            parentFormulaElement.setRightChild(rightChildrenFormulaElementList[0])
        }
        return Formula(parentFormulaElement)
    }

    private fun linkFormulaElements(
        formulaElementList: List<FormulaElement>?
    ) {
        var previousElement: FormulaElement? = null
        if (formulaElementList != null) {
            for (formulaElement in formulaElementList) {
                previousElement?.setLeftChild(formulaElement)
                previousElement = formulaElement
            }
        }
    }

    companion object {
        private const val PROJECT_NAME = "project"
        private const val SPRITE_NAME = "sprite"
        private const val USER_VARIABLE_NAME = "variable"
        private const val USER_LIST_NAME = "list"
        private const val SECOND_USER_LIST_NAME = "secondList"
        private val context = ApplicationProvider.getApplicationContext<Context>()
        private val functionFlatten = context.getString(R.string.formula_editor_function_flatten)
        private val functionNumberOfItems =
            context.getString(R.string.formula_editor_function_number_of_items)
        private val functionListItem = context.getString(R.string.formula_editor_function_list_item)
        private val functionContains = context.getString(R.string.formula_editor_function_contains)
        private val functionIndexOfItem =
            context.getString(R.string.formula_editor_function_index_of_item)
        private val functionLength = context.getString(R.string.formula_editor_function_length)
        private val functionSqrt = context.getString(R.string.formula_editor_function_sqrt)
        private val operatorPlus = context.getString(R.string.formula_editor_operator_plus)

        private val numberTwo = FormulaElement(NUMBER, "2", null)
        private val userVariable = FormulaElement(USER_VARIABLE, USER_VARIABLE_NAME, null)
        private val userList = FormulaElement(USER_LIST, USER_LIST_NAME, null)
        private val secondUserList = FormulaElement(USER_LIST, SECOND_USER_LIST_NAME, null)
        private val numberOfItems = FormulaElement(FUNCTION, NUMBER_OF_ITEMS.name, null)
        private val listItem = FormulaElement(FUNCTION, LIST_ITEM.name, null)
        private val containsItem = FormulaElement(FUNCTION, CONTAINS.name, null)
        private val indexOfItem = FormulaElement(FUNCTION, INDEX_OF_ITEM.name, null)
        private val flattenList = FormulaElement(FUNCTION, FLATTEN.name, null)
        private val length = FormulaElement(FUNCTION, LENGTH.name, null)
        private val squareRoot = FormulaElement(FUNCTION, SQRT.name, null)
        private val operatorAdd = FormulaElement(OPERATOR, PLUS.name, null)

        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun parameters() = listOf(
            arrayOf(
                "no flatten in number of items function", numberOfItems, listOf(userList), null,
                "$functionNumberOfItems( *$USER_LIST_NAME* ) "
            ),
            arrayOf(
                "no flatten in item function", listItem, listOf(numberTwo), listOf(userList),
                "$functionListItem( 2 , *$USER_LIST_NAME* ) "
            ),
            arrayOf(
                "no flatten in contains function", containsItem, listOf(userList),
                listOf(numberTwo), "$functionContains( *$USER_LIST_NAME* , 2 ) "
            ),
            arrayOf(
                "no flatten in item's index function", indexOfItem, listOf(numberTwo),
                listOf(userList), "$functionIndexOfItem( 2 , *$USER_LIST_NAME* ) "
            ),
            arrayOf(
                "no flatten in flatten function", flattenList, listOf(userList), null,
                "$functionFlatten( *$USER_LIST_NAME* ) "
            ),
            arrayOf(
                "user variable", userVariable, null, null, "\"$USER_VARIABLE_NAME\" "
            ),
            arrayOf(
                "user variable nested once", squareRoot, listOf(userVariable), null,
                "$functionSqrt( \"$USER_VARIABLE_NAME\" ) "
            ),
            arrayOf(
                "user list simple", userList, null, null, "$functionFlatten( *$USER_LIST_NAME* ) "
            ),
            arrayOf(
                "user list nested once", length, listOf(userList), null,
                "$functionLength( $functionFlatten( *$USER_LIST_NAME* ) ) "
            ),
            arrayOf(
                "user list nested twice", squareRoot, listOf(length, userList), null,
                "$functionSqrt( $functionLength( $functionFlatten( *$USER_LIST_NAME* ) ) ) "
            ),
            arrayOf(
                "multiple user lists", operatorAdd, listOf(userList), listOf(secondUserList),
                "$functionFlatten( *$USER_LIST_NAME* ) $operatorPlus " +
                    "$functionFlatten( *$SECOND_USER_LIST_NAME* ) "
            ),
            arrayOf(
                "multiple nested user lists", operatorAdd, listOf(length, userList),
                listOf(squareRoot, secondUserList),
                "$functionLength( $functionFlatten( *$USER_LIST_NAME* ) ) $operatorPlus " +
                    "$functionSqrt( $functionFlatten( *$SECOND_USER_LIST_NAME* ) ) "
            )
        )
    }
}
