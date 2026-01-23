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

package com.myradev.lunarcode.test.content.bricks;

import com.myradev.lunarcode.CatroidApplication;
import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.Scene;
import com.myradev.lunarcode.content.Script;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.WhenScript;
import com.myradev.lunarcode.content.bricks.CompositeBrick;
import com.myradev.lunarcode.content.bricks.ConcurrentFormulaHashMap;
import com.myradev.lunarcode.content.bricks.ForeverBrick;
import com.myradev.lunarcode.content.bricks.FormulaBrick;
import com.myradev.lunarcode.content.bricks.IfThenLogicBeginBrick;
import com.myradev.lunarcode.content.bricks.RepeatBrick;
import com.myradev.lunarcode.content.bricks.RepeatUntilBrick;
import com.myradev.lunarcode.content.bricks.SetXBrick;
import com.myradev.lunarcode.formulaeditor.Formula;
import com.myradev.lunarcode.formulaeditor.FormulaElement;
import com.myradev.lunarcode.formulaeditor.UserList;
import com.myradev.lunarcode.formulaeditor.UserVariable;
import com.myradev.lunarcode.ui.recyclerview.fragment.DataListFragment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static com.myradev.lunarcode.test.StaticSingletonInitializer.initializeStaticSingletonMethods;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CompositeBrickVariableUpdateTest {

	private UserList userList;
	private UserVariable userVariable;
	private FormulaBrick formulaBrick;
	private static final String VARIABLE_NAME = "Test";
	private static final String VARIABLE_NAME_USERVARIABLE_FORMAT = "\"Test\" ";
	private static final String VARIABLE_NAME_USERLIST_FORMAT = "*Test* ";
	private static final String INVALID_NAME = "Abcd";
	private static final String NEW_VARIABLE_NAME = "NewName";
	private static final String NEW_VARIABLE_USERVARIABLE_FORMAT = "\"NewName\" ";
	private static final String NEW_VARIABLE_USERLIST_FORMAT = "*NewName* ";

	private static final String VARIABLE_VALUE = "Value";
	private static final String NEW_VARIABLE_VALUE = "NewValue";

	@Parameterized.Parameters(name = "{0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{IfThenLogicBeginBrick.class.getSimpleName(), IfThenLogicBeginBrick.class},
				{ForeverBrick.class.getSimpleName(), ForeverBrick.class},
				{RepeatBrick.class.getSimpleName(), RepeatBrick.class},
				{RepeatUntilBrick.class.getSimpleName(), RepeatUntilBrick.class},
		});
	}

	@Parameterized.Parameter
	public String name;

	@Parameterized.Parameter(1)
	public Class<CompositeBrick> compositeBrickClass;

	@Before
	public void setUp() throws IllegalAccessException, InstantiationException {
		initializeStaticSingletonMethods();
		Project project = new Project();
		userVariable = new UserVariable();
		userList = new UserList();
		Scene scene = new Scene();
		Sprite sprite = new Sprite();
		Script script = new WhenScript();
		CompositeBrick compositeBrick = compositeBrickClass.newInstance();
		formulaBrick = new SetXBrick();

		userVariable.setName(VARIABLE_NAME);
		userVariable.setValue(VARIABLE_VALUE);
		userList.setName(VARIABLE_NAME);

		project.addScene(scene);
		scene.addSprite(sprite);
		sprite.addScript(script);
		script.addBrick(compositeBrick);
		compositeBrick.getNestedBricks().add(formulaBrick);

		project.addUserVariable(userVariable);
		project.addUserList(userList);
		ProjectManager.getInstance().setCurrentProject(project);
	}

	@Test
	public void testEditVariable() {
		DataListFragment.updateUserVariableValue(NEW_VARIABLE_VALUE, userVariable);
		assertEquals(NEW_VARIABLE_VALUE, userVariable.getValue());
	}

	@Test
	public void testEditVariableSameValue() {
		DataListFragment.updateUserVariableValue(VARIABLE_VALUE, userVariable);
		assertEquals(VARIABLE_VALUE, userVariable.getValue());
	}

	@Test
	public void testRenamingVariable() {
		FormulaElement element = new FormulaElement(FormulaElement.ElementType.USER_VARIABLE,
				VARIABLE_NAME, null);
		Formula newFormula = new Formula(element);
		ConcurrentFormulaHashMap map = formulaBrick.getFormulaMap();
		map.forEach((k, v) -> {
			formulaBrick.setFormulaWithBrickField(k, newFormula);
		});

		DataListFragment.updateUserDataReferences(VARIABLE_NAME, NEW_VARIABLE_NAME, userVariable);

		map.forEach((k, v) -> {
			assertEquals(v.getTrimmedFormulaString(CatroidApplication.getAppContext()),
					NEW_VARIABLE_USERVARIABLE_FORMAT);
		});
	}

	@Test
	public void testRenamingVariableNoChanges() {
		FormulaElement element = new FormulaElement(FormulaElement.ElementType.USER_VARIABLE,
				VARIABLE_NAME, null);
		Formula newFormula = new Formula(element);
		ConcurrentFormulaHashMap map = formulaBrick.getFormulaMap();
		map.forEach((k, v) -> {
			formulaBrick.setFormulaWithBrickField(k, newFormula);
		});

		DataListFragment.updateUserDataReferences(INVALID_NAME, NEW_VARIABLE_NAME, userVariable);

		map.forEach((k, v) -> {
			assertEquals(v.getTrimmedFormulaString(CatroidApplication.getAppContext()),
					VARIABLE_NAME_USERVARIABLE_FORMAT);
		});
	}

	@Test
	public void testRenamingList() {
		FormulaElement element = new FormulaElement(FormulaElement.ElementType.USER_LIST,
				VARIABLE_NAME, null);
		Formula newFormula = new Formula(element);
		ConcurrentFormulaHashMap map = formulaBrick.getFormulaMap();
		map.forEach((k, v) -> {
			formulaBrick.setFormulaWithBrickField(k, newFormula);
		});

		DataListFragment.updateUserDataReferences(VARIABLE_NAME, NEW_VARIABLE_NAME, userList);

		map.forEach((k, v) -> {
			assertEquals(v.getTrimmedFormulaString(CatroidApplication.getAppContext()),
					NEW_VARIABLE_USERLIST_FORMAT);
		});
	}

	@Test
	public void testRenamingListNoChanges() {
		FormulaElement element = new FormulaElement(FormulaElement.ElementType.USER_LIST,
				VARIABLE_NAME, null);
		Formula newFormula = new Formula(element);
		ConcurrentFormulaHashMap map = formulaBrick.getFormulaMap();
		map.forEach((k, v) -> {
			formulaBrick.setFormulaWithBrickField(k, newFormula);
		});

		DataListFragment.updateUserDataReferences(INVALID_NAME, NEW_VARIABLE_NAME, userList);

		map.forEach((k, v) -> {
			assertEquals(v.getTrimmedFormulaString(CatroidApplication.getAppContext()),
					VARIABLE_NAME_USERLIST_FORMAT);
		});
	}
}
