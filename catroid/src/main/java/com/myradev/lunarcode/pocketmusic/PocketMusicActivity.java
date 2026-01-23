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

package com.myradev.lunarcode.pocketmusic;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.myradev.lunarcode.ProjectManager;
import com.myradev.lunarcode.R;
import com.myradev.lunarcode.common.SoundInfo;
import com.myradev.lunarcode.pocketmusic.fastscroller.FastScroller;
import com.myradev.lunarcode.pocketmusic.mididriver.MidiNotePlayer;
import com.myradev.lunarcode.pocketmusic.note.MusicalBeat;
import com.myradev.lunarcode.pocketmusic.note.MusicalKey;
import com.myradev.lunarcode.pocketmusic.note.Project;
import com.myradev.lunarcode.pocketmusic.note.Track;
import com.myradev.lunarcode.pocketmusic.note.midi.MidiException;
import com.myradev.lunarcode.pocketmusic.note.midi.MidiToProjectConverter;
import com.myradev.lunarcode.pocketmusic.note.midi.ProjectToMidiConverter;
import com.myradev.lunarcode.pocketmusic.note.trackgrid.TrackGridToTrackConverter;
import com.myradev.lunarcode.pocketmusic.ui.TactScrollRecyclerView;
import com.myradev.lunarcode.ui.BaseActivity;
import com.myradev.lunarcode.ui.recyclerview.controller.SoundController;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import androidx.appcompat.widget.Toolbar;

import static com.myradev.lunarcode.common.Constants.SOUND_DIRECTORY_NAME;

public class PocketMusicActivity extends BaseActivity {

	private static final String TAG = PocketMusicActivity.class.getSimpleName();
	public static final String TITLE = "title";
	public static final String ABSOLUTE_FILE_PATH = "file";

	private Project project;
	private TactScrollRecyclerView tactScroller;

	private MidiNotePlayer midiDriver;

	private FastScroller fastScroller;

	private File midiFolder;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		midiFolder = new File(getApplicationContext().getFilesDir().getPath(), SOUND_DIRECTORY_NAME);

		midiDriver = new MidiNotePlayer();

		if (getIntent().getExtras() != null && getIntent().getStringExtra(ABSOLUTE_FILE_PATH) != null) {

			String title = getString(R.string.pocket_music_default_project_name);

			if (getIntent().getExtras() != null) {
				title = getIntent().getStringExtra(TITLE);
			}

			MidiToProjectConverter converter = new MidiToProjectConverter();
			File soundFile = new File(getIntent().getStringExtra(ABSOLUTE_FILE_PATH));

			try {
				project = converter.convertMidiFileToProject(soundFile);
				project.setName(title);
				project.setFile(soundFile);
			} catch (MidiException | IOException e) {
				try {
					project = createEmptyProject();
				} catch (IOException ioException) {
					Log.e(TAG, Log.getStackTraceString(ioException));
					finish();
				}
			}
		} else {
			try {
				project = createEmptyProject();
			} catch (IOException e) {
				Log.e(TAG, Log.getStackTraceString(e));
				finish();
			}
		}

		setContentView(R.layout.activity_pocketmusic);
		ViewGroup content = findViewById(android.R.id.content);

		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		getSupportActionBar().setLogo(R.drawable.ic_pocketmusic);
		getSupportActionBar().setTitle(project.getName());
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		tactScroller = findViewById(R.id.tact_scroller);
		tactScroller.setTrack(project.getTrack(getString(R.string.pocket_music_default_track_name)),
				project.getBeatsPerMinute());

		fastScroller = findViewById(R.id.fastscroll);
		fastScroller.setRecyclerView(tactScroller);

		new ScrollController(content, tactScroller, project.getBeatsPerMinute());
	}

	private Project createEmptyProject() throws IOException {
		if (!midiFolder.exists() && !midiFolder.mkdir()) {
			throw new IOException("Cannot create dir MIDI folder at: " + midiFolder.getAbsolutePath());
		}

		Project project = new Project(getString(R.string.pocket_music_default_project_name),
				MusicalBeat.BEAT_4_4, Project.DEFAULT_BEATS_PER_MINUTE);

		Track track = new Track(MusicalKey.VIOLIN, Project.DEFAULT_INSTRUMENT);

		project.putTrack(getString(R.string.pocket_music_default_track_name), track);
		project.setFile(new File(midiFolder, "MUS-" + Math.abs(new Random().nextInt()) + ".midi"));

		return project;
	}

	@Override
	public void finish() {
		if (project != null) {

			boolean receivedSoundInfoThroughIntent = project.getFile().exists();

			Track track = TrackGridToTrackConverter
					.convertTrackGridToTrack(tactScroller.getTrackGrid(), Project.DEFAULT_BEATS_PER_MINUTE);

			if (track.isEmpty() && receivedSoundInfoThroughIntent) {
				SoundInfo soundInfo = new SoundInfo(project.getName(), project.getFile(), true);
				ProjectManager.getInstance().getCurrentSprite().getSoundList().remove(soundInfo);

				try {
					new SoundController().delete(soundInfo);
				} catch (IOException e) {
					Log.e(TAG, Log.getStackTraceString(e));
				}
			} else if (!track.isEmpty()) {
				for (String trackName : project.getTrackNames()) {
					project.putTrack(trackName, track);
				}

				SoundInfo soundInfo = new SoundInfo(project.getName(), project.getFile(), true);
				ProjectToMidiConverter projectToMidiConverter = new ProjectToMidiConverter(midiFolder);

				try {
					projectToMidiConverter.writeProjectAsMidi(project, soundInfo.getFile());
				} catch (IOException | MidiException e) {
					Log.e(TAG, "Cannot save file:" + soundInfo.getFile().getAbsolutePath() + ".", e);
				}

				if (!receivedSoundInfoThroughIntent) {
					ProjectManager.getInstance().getCurrentSprite().getSoundList().add(soundInfo);
				}
			}
		}
		super.finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (midiDriver != null && !MidiNotePlayer.isInitialized()) {
			midiDriver.start();
		} else {
			midiDriver.setInstrument((byte) 0, Project.DEFAULT_INSTRUMENT);
		}
	}
}
