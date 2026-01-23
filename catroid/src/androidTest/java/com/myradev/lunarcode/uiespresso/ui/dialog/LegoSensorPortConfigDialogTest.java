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

package com.myradev.lunarcode.uiespresso.ui.dialog;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.myradev.lunarcode.R;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.bricks.ChangeSizeByNBrick;
import com.myradev.lunarcode.devices.mindstorms.nxt.sensors.NXTSensor;
import com.myradev.lunarcode.test.utils.TestUtils;
import com.myradev.lunarcode.testsuites.annotations.Cat;
import com.myradev.lunarcode.testsuites.annotations.Level;
import com.myradev.lunarcode.ui.SpriteActivity;
import com.myradev.lunarcode.ui.settingsfragments.SettingsFragment;
import com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorWrapper;
import com.myradev.lunarcode.uiespresso.util.UiTestUtils;
import com.myradev.lunarcode.uiespresso.util.rules.FragmentActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

import androidx.test.core.app.ApplicationProvider;

import static com.myradev.lunarcode.uiespresso.content.brick.utils.BrickDataInteractionWrapper.onBrickAtPosition;
import static com.myradev.lunarcode.uiespresso.formulaeditor.utils.FormulaEditorWrapper.onFormulaEditor;
import static com.myradev.lunarcode.uiespresso.ui.dialog.utils.LegoSensorPortConfigDialogWrapper.NXT_NO_SENSOR;
import static com.myradev.lunarcode.uiespresso.ui.dialog.utils.LegoSensorPortConfigDialogWrapper.NXT_SENSOR_TOUCH;
import static com.myradev.lunarcode.uiespresso.ui.dialog.utils.LegoSensorPortConfigDialogWrapper.PORT_1;
import static com.myradev.lunarcode.uiespresso.ui.dialog.utils.LegoSensorPortConfigDialogWrapper.onLegoSensorPortConfigDialog;
import static org.hamcrest.core.IsNot.not;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@Category({Cat.AppUi.class, Level.Smoke.class})
public class LegoSensorPortConfigDialogTest {

	@Rule
	public FragmentActivityTestRule<SpriteActivity> baseActivityTestRule = new
			FragmentActivityTestRule<>(SpriteActivity.class, SpriteActivity.EXTRA_FRAGMENT_POSITION, SpriteActivity.FRAGMENT_SCRIPTS);

	private NXTSensor.Sensor[] sensorMappingBuffer;
	private boolean nxtSettingBuffer;

	@Before
	public void setUp() throws Exception {
		Script script = UiTestUtils.createProjectAndGetStartScript(getClass().getSimpleName());
		script.addBrick(new ChangeSizeByNBrick(0));

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext());

		nxtSettingBuffer = sharedPreferences
				.getBoolean(SettingsFragment.SETTINGS_MINDSTORMS_NXT_BRICKS_CHECKBOX_PREFERENCE, false);

		sharedPreferences.edit()
				.putBoolean(SettingsFragment.SETTINGS_MINDSTORMS_NXT_BRICKS_CHECKBOX_PREFERENCE, true)
				.commit();

		sensorMappingBuffer = SettingsFragment.getLegoNXTSensorMapping(ApplicationProvider.getApplicationContext());

		SettingsFragment.setLegoMindstormsNXTSensorMapping(ApplicationProvider.getApplicationContext(),
				new NXTSensor.Sensor[] {
						NXTSensor.Sensor.NO_SENSOR,
						NXTSensor.Sensor.NO_SENSOR,
						NXTSensor.Sensor.NO_SENSOR,
						NXTSensor.Sensor.NO_SENSOR
				});

		baseActivityTestRule.launchActivity();
	}

	@Test
	public void pressOKTest() {
		onBrickAtPosition(1).onChildView(withId(R.id.brick_change_size_by_edit_text)).perform(click());
		openLegoSensorPortConfigDialog();
		onLegoSensorPortConfigDialog(NXT_SENSOR_TOUCH)
				.checkDialogVisible();
		onView(withText(R.string.ok)).check(matches(not(isEnabled())));
	}

	@Test
	public void checkDialogTest() {
		onBrickAtPosition(1).onChildView(withId(R.id.brick_change_size_by_edit_text)).perform(click());
		openLegoSensorPortConfigDialog();
		onLegoSensorPortConfigDialog(NXT_SENSOR_TOUCH)
				.performClickOnPort(PORT_1, NXT_NO_SENSOR)
				.performClickOnOK();

		String result = UiTestUtils.getResourcesString(R.string.formula_editor_sensor_lego_nxt_1);
		onFormulaEditor().checkValue(result);

		openLegoSensorPortConfigDialog();

		onLegoSensorPortConfigDialog(NXT_SENSOR_TOUCH).checkPortDisplayed(PORT_1, NXT_SENSOR_TOUCH);
	}

	@After
	public void tearDown() throws IOException {
		PreferenceManager.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext()).edit()
				.putBoolean(SettingsFragment.SETTINGS_MINDSTORMS_NXT_BRICKS_CHECKBOX_PREFERENCE, nxtSettingBuffer)
				.commit();

		SettingsFragment
				.setLegoMindstormsNXTSensorMapping(ApplicationProvider.getApplicationContext(), sensorMappingBuffer);

		TestUtils.deleteProjects(getClass().getSimpleName());
	}

	private void openLegoSensorPortConfigDialog() {
		onFormulaEditor()
				.performOpenCategory(FormulaEditorWrapper.Category.DEVICE)
				.performSelect(R.string.formula_editor_sensor_lego_nxt_touch);
	}
}
