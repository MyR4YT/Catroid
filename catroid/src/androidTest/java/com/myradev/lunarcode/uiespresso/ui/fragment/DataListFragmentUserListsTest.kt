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
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.myradev.lunarcode.ProjectManager
import com.myradev.lunarcode.R
import com.myradev.lunarcode.content.Project
import com.myradev.lunarcode.content.bricks.ChangeSizeByNBrick
import com.myradev.lunarcode.formulaeditor.UserList
import com.myradev.lunarcode.test.utils.TestUtils
import com.myradev.lunarcode.ui.SpriteActivity
import com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorDataListWrapper.onDataList
import com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorWrapper.onFormulaEditor
import com.myradev.lunarcode.uiespresso.util.UiTestUtils
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DataListFragmentUserListsTest(
    private val name: String,
    private val values: List<Object>,
    private val expectedStrings: List<String>
) {
    lateinit var project: Project

    @Rule
    @JvmField
    var baseActivityTestRule = FragmentActivityTestRule(
        SpriteActivity::class.java, SpriteActivity.EXTRA_FRAGMENT_POSITION,
        SpriteActivity.FRAGMENT_SCRIPTS
    )

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val script = UiTestUtils.createProjectAndGetStartScript(projectName)
        script.addBrick(ChangeSizeByNBrick(0.0))
        project = ProjectManager.getInstance().currentProject
        baseActivityTestRule.launchActivity()
        project.addUserList(UserList(userListName, values))
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
    fun booleanUserListTest() {
        onDataList().onListAtPosition(0)
            .checkHasName(userListName)
        onDataList().onListAtPosition(0)
            .performClickDetails()
        expectedStrings.forEach { expectedString ->
            onView(withText(expectedString))
                .check(matches(isDisplayed()))
        }
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
        private const val userListName = "userList"

        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun parameters() = listOf(
            arrayOf("listOf(false)", listOf(false), listOf(falseString)),
            arrayOf("listOf(true)", listOf(true), listOf(trueString)),
            arrayOf("listOf(1)", listOf(1), listOf("1")),
            arrayOf("listOf(1k)", listOf(1_000), listOf("1k")),
            arrayOf("listOf(1M)", listOf(1_000_000), listOf("1M")),
            arrayOf("listOf(1.1)", listOf(1.1), listOf("1.1")),
            arrayOf("listOf(NaN)", listOf(Double.NaN), listOf("NaN")),
            arrayOf("listOf(hello)", listOf("hello"), listOf("hello")),
            arrayOf(
                "listOf(false, true, 1, 1k, 1M, 1.1, NaN, hello)",
                listOf(false, true, 1, 1_000, 1_000_000, 1.1, Double.NaN, "hello"),
                listOf(falseString, trueString, "1", "1k", "1M", "1.1", "NaN", "hello")
            )
        )
    }
}
