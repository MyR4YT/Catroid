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

package com.myradev.lunarcode.test.formulaeditor.parser

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.myradev.lunarcode.ProjectManager
import com.myradev.lunarcode.content.Project
import com.myradev.lunarcode.content.Scope
import com.myradev.lunarcode.content.Sprite
import com.myradev.lunarcode.formulaeditor.Functions
import com.myradev.lunarcode.formulaeditor.Functions.ABS
import com.myradev.lunarcode.formulaeditor.Functions.ARCCOS
import com.myradev.lunarcode.formulaeditor.Functions.ARCSIN
import com.myradev.lunarcode.formulaeditor.Functions.ARCTAN
import com.myradev.lunarcode.formulaeditor.Functions.CEIL
import com.myradev.lunarcode.formulaeditor.Functions.COS
import com.myradev.lunarcode.formulaeditor.Functions.EXP
import com.myradev.lunarcode.formulaeditor.Functions.FLOOR
import com.myradev.lunarcode.formulaeditor.Functions.LN
import com.myradev.lunarcode.formulaeditor.Functions.LOG
import com.myradev.lunarcode.formulaeditor.Functions.ROUND
import com.myradev.lunarcode.formulaeditor.Functions.SIN
import com.myradev.lunarcode.formulaeditor.Functions.SQRT
import com.myradev.lunarcode.formulaeditor.Functions.TAN
import com.myradev.lunarcode.formulaeditor.InternToken
import com.myradev.lunarcode.formulaeditor.InternTokenType.NUMBER
import com.myradev.lunarcode.formulaeditor.InternTokenType.STRING
import com.myradev.lunarcode.formulaeditor.Operators.PLUS
import com.myradev.lunarcode.test.MockUtil
import com.myradev.lunarcode.test.formulaeditor.FormulaEditorTestUtil.buildBinaryOperator
import com.myradev.lunarcode.test.formulaeditor.FormulaEditorTestUtil.testSingleParameterFunction
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.lang.Math.abs
import java.lang.Math.acos
import java.lang.Math.asin
import java.lang.Math.atan
import java.lang.Math.ceil
import java.lang.Math.cos
import java.lang.Math.exp
import java.lang.Math.floor
import java.lang.Math.log
import java.lang.Math.log10
import java.lang.Math.round
import java.lang.Math.sin
import java.lang.Math.sqrt
import java.lang.Math.tan
import java.lang.Math.toDegrees
import java.lang.Math.toRadians

@RunWith(Parameterized::class)
class SingleParameterFunctionParserTest(
    val name: String,
    private val function: Functions,
    private val associatedFunction: AssociatedFunction,
    private val parameterValue: Double
) {

    companion object {
        private const val NOT_NUMERICAL_STRING = "NOT_NUMERICAL_STRING"

        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data(): Iterable<Array<Any>> {
            return listOf(
                arrayOf("SIN", SIN, AssociatedFunction { par -> sin(toRadians(par)) }, 90.0),
                arrayOf("COS", COS, AssociatedFunction { par -> cos(toRadians(par)) }, 180.0),
                arrayOf("TAN", TAN, AssociatedFunction { par -> tan(toRadians(par)) }, 30.0),
                arrayOf("LN", LN, AssociatedFunction { par -> log(par) }, 30.0),
                arrayOf("LOG", LOG, AssociatedFunction { par -> log10(par) }, 30.0),
                arrayOf("SQRT", SQRT, AssociatedFunction { par -> sqrt(par) }, 900.0),
                arrayOf("ROUND", ROUND, AssociatedFunction { par -> round(par).toDouble() }, 3.5),
                arrayOf("FLOOR", FLOOR, AssociatedFunction { par -> floor(par) }, 3.5),
                arrayOf("CEIL", CEIL, AssociatedFunction { par -> ceil(par) }, 3.5),
                arrayOf("ABS", ABS, AssociatedFunction { par -> abs(par) }, -4.0),
                arrayOf("ARCSIN", ARCSIN, AssociatedFunction { par -> toDegrees(asin(par)) }, 0.66),
                arrayOf("ARCCOS", ARCCOS, AssociatedFunction { par -> toDegrees(acos(par)) }, 0.66),
                arrayOf("ARCTAN", ARCTAN, AssociatedFunction { par -> toDegrees(atan(par)) }, 45.66),
                arrayOf("EXP", EXP, AssociatedFunction { par -> exp(par) }, 45.66)
                )
        }
    }

    private var sprite: Sprite? = null
    private var scope: Scope? = null

    @Before
    fun setUp() {
        val project = Project(
            MockUtil.mockContextForProject(),
            "Project"
        )
        sprite = Sprite("testSprite")
        scope = Scope(project, sprite!!, SequenceAction())
        project.defaultScene.addSprite(sprite)
        ProjectManager.getInstance().currentProject = project
    }

    @Test
    fun testNumberParameter() {
        val internToken = InternToken(NUMBER, parameterValue.toString())
        val expectedValue = associatedFunction.function(parameterValue)
        testSingleParameterFunction(function, listOf(internToken), expectedValue, null)
    }

    @Test
    fun testStringParameter() {
        val internToken = InternToken(STRING, parameterValue.toString())
        val expectedValue = associatedFunction.function(parameterValue)
        testSingleParameterFunction(function, listOf(internToken), expectedValue, null)
    }

    @Test
    fun testEmptyParameter() {
        val internToken = InternToken(STRING, "")
        val expectedValue = 0.0
        testSingleParameterFunction(function, listOf(internToken), expectedValue, null)
    }

    @Test
    fun testNotNumericalStringParameter() {
        val internToken = InternToken(STRING, NOT_NUMERICAL_STRING)
        val expectedValue = 0.0
        testSingleParameterFunction(function, listOf(internToken), expectedValue, null)
    }

    @Test
    fun testInvalidFormulaParameter() {
        val parameter = buildBinaryOperator(NUMBER, "15.0", PLUS, STRING, NOT_NUMERICAL_STRING)
        val expectedValue = associatedFunction.function(Double.NaN)
        testSingleParameterFunction(function, parameter, expectedValue, scope)
    }

    data class AssociatedFunction(val function: (Double) -> Double)
}
