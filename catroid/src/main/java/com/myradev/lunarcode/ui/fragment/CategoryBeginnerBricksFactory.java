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
package com.myradev.lunarcode.ui.fragment;

import android.content.Context;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.R;
import com.myradev.lunarcode.common.BrickValues;
import com.myradev.lunarcode.content.BroadcastScript;
import com.myradev.lunarcode.content.Project;
import com.myradev.lunarcode.content.bricks.AskBrick;
import com.myradev.lunarcode.content.bricks.Brick;
import com.myradev.lunarcode.content.bricks.BroadcastBrick;
import com.myradev.lunarcode.content.bricks.BroadcastReceiverBrick;
import com.myradev.lunarcode.content.bricks.ChangeColorByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeSizeByNBrick;
import com.myradev.lunarcode.content.bricks.ChangeVariableBrick;
import com.myradev.lunarcode.content.bricks.CloneBrick;
import com.myradev.lunarcode.content.bricks.DeleteThisCloneBrick;
import com.myradev.lunarcode.content.bricks.ForeverBrick;
import com.myradev.lunarcode.content.bricks.GlideToBrick;
import com.myradev.lunarcode.content.bricks.GoToBrick;
import com.myradev.lunarcode.content.bricks.HideBrick;
import com.myradev.lunarcode.content.bricks.HideTextBrick;
import com.myradev.lunarcode.content.bricks.IfOnEdgeBounceBrick;
import com.myradev.lunarcode.content.bricks.MoveNStepsBrick;
import com.myradev.lunarcode.content.bricks.NextLookBrick;
import com.myradev.lunarcode.content.bricks.PlaceAtBrick;
import com.myradev.lunarcode.content.bricks.PlaySoundBrick;
import com.myradev.lunarcode.content.bricks.PreviousLookBrick;
import com.myradev.lunarcode.content.bricks.SayBubbleBrick;
import com.myradev.lunarcode.content.bricks.SayForBubbleBrick;
import com.myradev.lunarcode.content.bricks.SetBackgroundBrick;
import com.myradev.lunarcode.content.bricks.SetColorBrick;
import com.myradev.lunarcode.content.bricks.SetLookBrick;
import com.myradev.lunarcode.content.bricks.SetSizeToBrick;
import com.myradev.lunarcode.content.bricks.SetVariableBrick;
import com.myradev.lunarcode.content.bricks.ShowBrick;
import com.myradev.lunarcode.content.bricks.ShowTextBrick;
import com.myradev.lunarcode.content.bricks.SpeakBrick;
import com.myradev.lunarcode.content.bricks.StopAllSoundsBrick;
import com.myradev.lunarcode.content.bricks.ThinkBubbleBrick;
import com.myradev.lunarcode.content.bricks.ThinkForBubbleBrick;
import com.myradev.lunarcode.content.bricks.TurnLeftBrick;
import com.myradev.lunarcode.content.bricks.TurnRightBrick;
import com.myradev.lunarcode.content.bricks.WaitBrick;
import com.myradev.lunarcode.content.bricks.WhenClonedBrick;
import com.myradev.lunarcode.content.bricks.WhenStartedBrick;
import com.myradev.lunarcode.content.bricks.WhenTouchDownBrick;

import java.util.ArrayList;
import java.util.List;

public class CategoryBeginnerBricksFactory extends CategoryBricksFactory {

	@Override
	protected List<Brick> setupEventCategoryList(Context context, boolean isBackgroundSprite) {
		List<Brick> eventBrickList = new ArrayList<>();
		eventBrickList.add(new WhenStartedBrick());
		eventBrickList.add(new WhenTouchDownBrick());
		Project currentProject = ProjectManager.getInstance().getCurrentProject();
		List<String> broadcastMessages = currentProject.getBroadcastMessageContainer().getBroadcastMessages();
		String broadcastMessage = context.getString(R.string.brick_broadcast_default_value);
		if (broadcastMessages.size() > 0) {
			broadcastMessage = broadcastMessages.get(0);
		}
		eventBrickList.add(new BroadcastReceiverBrick(new BroadcastScript(broadcastMessage)));
		eventBrickList.add(new BroadcastBrick(broadcastMessage));
		return eventBrickList;
	}

	@Override
	protected List<Brick> setupControlCategoryList(Context context) {
		List<Brick> controlBrickList = new ArrayList<>();
		controlBrickList.add(new WaitBrick(BrickValues.WAIT));
		controlBrickList.add(new ForeverBrick());
		controlBrickList.add(new CloneBrick());
		controlBrickList.add(new DeleteThisCloneBrick());
		controlBrickList.add(new WhenClonedBrick());
		return controlBrickList;
	}

	@Override
	protected List<Brick> setupMotionCategoryList(Context context, boolean isBackgroundSprite) {
		List<Brick> motionBrickList = new ArrayList<>();
		motionBrickList.add(new PlaceAtBrick(BrickValues.X_POSITION, BrickValues.Y_POSITION));
		motionBrickList.add(new GoToBrick(null));
		if (!isBackgroundSprite) {
			motionBrickList.add(new IfOnEdgeBounceBrick());
		}
		motionBrickList.add(new MoveNStepsBrick(BrickValues.MOVE_STEPS));
		motionBrickList.add(new TurnLeftBrick(BrickValues.TURN_DEGREES));
		motionBrickList.add(new TurnRightBrick(BrickValues.TURN_DEGREES));
		motionBrickList.add(new GlideToBrick(BrickValues.X_POSITION, BrickValues.Y_POSITION,
				BrickValues.GLIDE_SECONDS));
		return motionBrickList;
	}

	@Override
	protected List<Brick> setupSoundCategoryList(Context context) {
		List<Brick> soundBrickList = new ArrayList<>();
		soundBrickList.add(new PlaySoundBrick());
		soundBrickList.add(new StopAllSoundsBrick());
		soundBrickList.add(new SpeakBrick(context.getString(R.string.brick_speak_default_value)));
		return soundBrickList;
	}

	@Override
	protected List<Brick> setupLooksCategoryList(Context context, boolean isBackgroundSprite) {
		List<Brick> looksBrickList = new ArrayList<>();

		if (!isBackgroundSprite) {
			looksBrickList.add(new SetLookBrick());
		}
		looksBrickList.add(new NextLookBrick());
		looksBrickList.add(new PreviousLookBrick());
		looksBrickList.add(new SetSizeToBrick(BrickValues.SET_SIZE_TO));
		looksBrickList.add(new ChangeSizeByNBrick(BrickValues.CHANGE_SIZE_BY));
		looksBrickList.add(new HideBrick());
		looksBrickList.add(new ShowBrick());
		looksBrickList.add(new AskBrick(context.getString(R.string.brick_ask_default_question)));
		if (!isBackgroundSprite) {
			looksBrickList.add(new SayBubbleBrick(context.getString(R.string.brick_say_bubble_default_value)));
			looksBrickList.add(new SayForBubbleBrick(context.getString(R.string.brick_say_bubble_default_value), 1.0f));
			looksBrickList.add(new ThinkBubbleBrick(context.getString(R.string.brick_think_bubble_default_value)));
			looksBrickList.add(new ThinkForBubbleBrick(context.getString(R.string.brick_think_bubble_default_value), 1.0f));
		}
		looksBrickList.add(new SetColorBrick(BrickValues.SET_COLOR_TO));
		looksBrickList.add(new ChangeColorByNBrick(BrickValues.CHANGE_COLOR_BY));
		looksBrickList.add(new SetBackgroundBrick());
		return looksBrickList;
	}

	@Override
	protected List<Brick> setupDataCategoryList(Context context, boolean isBackgroundSprite) {
		List<Brick> dataBrickList = new ArrayList<>();
		dataBrickList.add(new SetVariableBrick(BrickValues.SET_VARIABLE));
		dataBrickList.add(new ChangeVariableBrick(BrickValues.CHANGE_VARIABLE));
		dataBrickList.add(new ShowTextBrick(BrickValues.X_POSITION, BrickValues.Y_POSITION));
		dataBrickList.add(new HideTextBrick());
		return dataBrickList;
	}
}
