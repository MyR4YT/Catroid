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

package com.myradev.lunarcode.uiespresso.content.brick.stage

import android.content.Intent
import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import com.myradev.lunarcode.ProjectManager
import com.myradev.lunarcode.R
import com.myradev.lunarcode.common.FlavoredConstants
import com.myradev.lunarcode.content.Script
import com.myradev.lunarcode.content.Sprite
import com.myradev.lunarcode.content.bricks.CloneBrick
import com.myradev.lunarcode.content.bricks.ParticleEffectAdditivityBrick
import com.myradev.lunarcode.content.bricks.ParticleEffectAdditivityBrick.Companion.OFF
import com.myradev.lunarcode.content.bricks.ParticleEffectAdditivityBrick.Companion.ON
import com.myradev.lunarcode.content.bricks.WaitBrick
import com.myradev.lunarcode.io.StorageOperations
import com.myradev.lunarcode.stage.StageActivity
import com.myradev.lunarcode.ui.SpriteActivity
import com.myradev.lunarcode.uiespresso.util.UiTestUtils
import com.myradev.lunarcode.uiespresso.util.actions.CustomActions.wait
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.java.KoinJavaComponent.inject
import java.io.File
import java.io.IOException

class ParticleAdditivityTest {

    val projectManager: ProjectManager by inject(ProjectManager::class.java)

    companion object {
        const val PROJECT_NAME = "particleTestProject"
    }

    lateinit var script: Script
    lateinit var sprite: Sprite

    @get:Rule
    val baseActivityTestRule = FragmentActivityTestRule(
        SpriteActivity::class.java,
        SpriteActivity.EXTRA_FRAGMENT_POSITION,
        SpriteActivity.FRAGMENT_SCRIPTS
    )

    @Before
    fun setUp() {
        val project = UiTestUtils.createDefaultTestProject(PROJECT_NAME)
        sprite = UiTestUtils.getDefaultTestSprite(project)
        script = UiTestUtils.getDefaultTestScript(project)
        baseActivityTestRule.launchActivity(Intent())
    }

    @Test
    fun enableParticleEffectAdditivityTest() {
        script.addBrick(ParticleEffectAdditivityBrick(ON))
        onView(ViewMatchers.withId(R.id.button_play)).perform(click())
        assertTrue(projectManager.currentSprite.look.isAdditive)
    }

    @Test
    fun disableParticleEffectAdditivityTest() {
        script.addBrick(ParticleEffectAdditivityBrick(OFF))
        onView(ViewMatchers.withId(R.id.button_play)).perform(click())
        assertFalse(projectManager.currentSprite.look.isAdditive)
    }

    fun checkAllCloneHaveAdditivitySetAs(value: Boolean) {
        val sprites = StageActivity.stageListener.spritesFromStage
        var cloneFound = false
        for (sprite in sprites) {
            if (sprite.isClone) {
                cloneFound = true
                assertEquals(sprite.look.isAdditive, value)
            }
        }
        if (!cloneFound) {
            Assert.fail("No Clone Found")
        }
    }

    @Test
    fun particleEffectAdditivityAfterWaitTest() {
        script.addBrick(ParticleEffectAdditivityBrick(OFF))
        script.addBrick(WaitBrick(1000))
        script.addBrick(ParticleEffectAdditivityBrick(ON))

        onView(ViewMatchers.withId(R.id.button_play)).perform(click())

        val projectManager = projectManager
        val look = projectManager.currentSprite.look

        assertFalse(look.isAdditive)
        onView(isRoot()).perform(wait(2000))
        assertTrue(look.isAdditive) // particle effect initialised after 2 seconds
    }

    @Test
    fun particleAdditiveEffectEnabledOnCloneTest() {
        script.addBrick(ParticleEffectAdditivityBrick(ON))
        script.addBrick(CloneBrick())
        onView(ViewMatchers.withId(R.id.button_play)).perform(click())
        checkAllCloneHaveAdditivitySetAs(true)
    }

    @Test
    fun particleAdditiveEffectDisabledOnCloneTest() {
        script.addBrick(ParticleEffectAdditivityBrick(OFF))
        script.addBrick(CloneBrick())
        onView(ViewMatchers.withId(R.id.button_play)).perform(click())
        checkAllCloneHaveAdditivitySetAs(false)
    }

    @After
    fun tearDown() {
        try {
            StorageOperations.deleteDir(
                File(
                    FlavoredConstants.DEFAULT_ROOT_DIRECTORY,
                    PROJECT_NAME
                )
            )
        } catch (e: IOException) {
            Log.d(javaClass.simpleName, Log.getStackTraceString(e))
        }
    }
}
