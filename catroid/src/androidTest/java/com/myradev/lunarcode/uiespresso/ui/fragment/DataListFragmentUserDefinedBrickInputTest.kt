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

package com.myradev.lunarcode.uiespresso.ui.fragment

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.myradev.lunarcode.ProjectManager
import com.myradev.lunarcode.R
import com.myradev.lunarcode.content.Project
import com.myradev.lunarcode.content.UserDefinedScript
import com.myradev.lunarcode.content.bricks.ChangeSizeByNBrick
import com.myradev.lunarcode.content.bricks.UserDefinedBrick
import com.myradev.lunarcode.content.bricks.UserDefinedReceiverBrick
import com.myradev.lunarcode.formulaeditor.Formula
import com.myradev.lunarcode.formulaeditor.FormulaElement
import com.myradev.lunarcode.formulaeditor.Functions
import com.myradev.lunarcode.test.utils.TestUtils
import com.myradev.lunarcode.ui.SpriteActivity
import com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorDataListWrapper.onDataList
import com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorWrapper.onFormulaEditor
import com.myradev.lunarcode.uiespresso.util.UiTestUtils
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule
import com.myradev.lunarcode.userbrick.UserDefinedBrickData
import com.myradev.lunarcode.userbrick.UserDefinedBrickInput
import com.myradev.lunarcode.userbrick.UserDefinedBrickLabel
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DataListFragmentUserDefinedBrickInputTest(
    private val name: String,
    private val formula: Formula,
    private val expectedString: String
) {
    private lateinit var userDefinedBrick: UserDefinedBrick
    private val label = UserDefinedBrickLabel("Label")
    private var input = UserDefinedBrickInput("Input")

    @Rule
    @JvmField
    var baseActivityTestRule = FragmentActivityTestRule(
        SpriteActivity::class.java, SpriteActivity.EXTRA_FRAGMENT_POSITION,
        SpriteActivity.FRAGMENT_SCRIPTS
    )

    @Before
    @kotlin.jvm.Throws(Exception::class)
    fun setUp() {
        UiTestUtils.createProjectAndGetStartScript(projectName)

        input.value = formula
        val userDefinedScript = UserDefinedScript()
        userDefinedBrick = UserDefinedBrick(mutableListOf<UserDefinedBrickData>(label, input))
        userDefinedBrick.setCallingBrick(true)
        userDefinedBrick.formulaMap.putIfAbsent(
            input.inputFormulaField,
            formula
        )
        userDefinedScript.scriptBrick = UserDefinedReceiverBrick(userDefinedBrick)
        userDefinedScript.addBrick(ChangeSizeByNBrick(0.0))
        ProjectManager.getInstance().currentSprite.addScript(userDefinedScript)

        project = ProjectManager.getInstance().currentProject
        baseActivityTestRule.launchActivity()
        openDataFragment()
        onView(withId(R.id.empty_view))
            .check(matches(not(isDisplayed())))
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        TestUtils.deleteProjects(projectName)
    }

    @Test
    fun userDefinedBrickInputTest() {
        onDataList().onVariableAtPosition(0)
            .checkHasName(input.name)
        onDataList().onVariableAtPosition(0)
            .checkHasValue(expectedString)
    }

    private fun openDataFragment() {
        onView(withId(R.id.brick_change_size_by_edit_text))
            .perform(click())
        onFormulaEditor()
            .performOpenDataFragment()
    }

    companion object {
        private val applicationContext: Context =
            ApplicationProvider.getApplicationContext<Context>()

        private val trueString = applicationContext.getString(R.string.formula_editor_true)
        private val falseString = applicationContext.getString(R.string.formula_editor_false)

        private lateinit var project: Project
        private const val projectName = "DataListFragmentBooleanUserVariablesTest"

        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun parameters() = listOf(
            arrayOf(
                "Boolean false",
                Formula(
                    FormulaElement(
                        FormulaElement.ElementType.FUNCTION,
                        Functions.FALSE.toString(),
                        null
                    )
                ),
                falseString
            ),
            arrayOf(
                "Boolean true",
                Formula(
                    FormulaElement(
                        FormulaElement.ElementType.FUNCTION,
                        Functions.TRUE.toString(),
                        null
                    )
                ),
                trueString
            ),
            arrayOf("Int 1", Formula(1), "1"),
            arrayOf("Int 1000", Formula(1_000), "1k"),
            arrayOf("Int 1000000", Formula(1_000_000), "1M"),
            arrayOf("Double 1.1", Formula(1.1), "1.1"),
            arrayOf("String hello", Formula("hello"), "hello")
        )
    }
}
