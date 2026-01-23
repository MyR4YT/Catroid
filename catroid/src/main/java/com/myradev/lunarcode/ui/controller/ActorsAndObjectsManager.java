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
package com.myradev.lunarcode.ui.controller;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.R;
import com.myradev.lunarcode.common.FlavoredConstants;
import com.myradev.lunarcode.common.LookData;
import com.myradev.lunarcode.content.Sprite;
import com.myradev.lunarcode.content.StartScript;
import com.myradev.lunarcode.content.bricks.MoveNStepsBrick;
import com.myradev.lunarcode.content.bricks.PenDownBrick;
import com.myradev.lunarcode.content.bricks.PlaceAtBrick;
import com.myradev.lunarcode.content.bricks.SetTransparencyBrick;
import com.myradev.lunarcode.content.bricks.TurnRightBrick;
import com.myradev.lunarcode.io.ResourceImporter;
import com.myradev.lunarcode.ui.settingsfragments.SettingsFragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.myradev.lunarcode.common.Constants.DEFAULT_IMAGE_EXTENSION;
import static com.myradev.lunarcode.common.Constants.IMAGE_DIRECTORY_NAME;

public final class ActorsAndObjectsManager {

	private static ActorsAndObjectsManager instance = null;

	public static ActorsAndObjectsManager getInstance() {
		if (instance == null) {
			instance = new ActorsAndObjectsManager();
		}
		return instance;
	}

	private ActorsAndObjectsManager() {
	}

	private Sprite generateSprite(String name, int imgId, double scale) throws IOException {
		var sprite = new Sprite(name);
		var folder = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, IMAGE_DIRECTORY_NAME);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		var path =
				ResourceImporter.createImageFileFromResourcesInDirectory(ProjectManager.getInstance().getApplicationContext().getResources(),
						imgId,
						folder,
						"img" + DEFAULT_IMAGE_EXTENSION,
						scale);
		var lookData = new LookData(name, path);
		sprite.getLookList().add(lookData);

		return sprite;
	}

	public List<Sprite> getSprites() {
		var sprites = new ArrayList<Sprite>();

		try {
			if (SettingsFragment.isEmroiderySharedPreferenceEnabled(ProjectManager.getInstance().getApplicationContext())) {
				var frame = generateSprite("10x10", R.drawable.frame, 1.0);
				var script = new StartScript();
				script.addBrick(new SetTransparencyBrick(100.0));
				script.addBrick(new PlaceAtBrick(-250, 250));
				script.addBrick(new PenDownBrick());
				for (int i = 0; i < 4; i++) {
					script.addBrick(new MoveNStepsBrick(500));
					script.addBrick(new TurnRightBrick(90));
				}
				frame.addScript(script);
				sprites.add(frame);

				sprites.add(generateSprite("Needle", R.drawable.plotter, 0.2));
			} else {
				sprites.add(generateSprite("Turtle", R.drawable.turtle, 0.2));
				sprites.add(generateSprite("Plotter", R.drawable.plotter, 0.2));
				sprites.add(generateSprite("PandaA", R.drawable.panda_a, 1.0));
				sprites.add(generateSprite("PandaB", R.drawable.panda_b, 1.0));
				sprites.add(generateSprite("Apple", R.drawable.apple, 1.0));
				sprites.add(generateSprite("LynxA", R.drawable.lynx_a, 1.0));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return sprites;
	}
}
