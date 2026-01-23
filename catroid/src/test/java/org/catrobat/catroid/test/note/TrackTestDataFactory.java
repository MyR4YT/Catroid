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
package com.myradev.lunarcode.test.note;

import com.myradev.lunarcode.pocketmusic.note.MusicalInstrument;
import com.myradev.lunarcode.pocketmusic.note.MusicalKey;
import com.myradev.lunarcode.pocketmusic.note.NoteEvent;
import com.myradev.lunarcode.pocketmusic.note.NoteLength;
import com.myradev.lunarcode.pocketmusic.note.NoteName;
import com.myradev.lunarcode.pocketmusic.note.Project;
import com.myradev.lunarcode.pocketmusic.note.Track;

public final class TrackTestDataFactory {

	private TrackTestDataFactory() {
	}

	public static Track createSimpleTrackWithoutOffEvents() {
		Track track = new Track(MusicalKey.VIOLIN, MusicalInstrument.ACOUSTIC_GRAND_PIANO);

		long tick = 0;

		track.addNoteEvent(tick, new NoteEvent(NoteName.C4, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.E4, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.F4, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.C4, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.C5, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.C5, false));

		return track;
	}

	public static Track createSimpleTrack() {
		Track track = new Track(MusicalKey.VIOLIN, MusicalInstrument.ACOUSTIC_GRAND_PIANO);

		long tick = 0;

		track.addNoteEvent(tick, new NoteEvent(NoteName.C4, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.C4, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.E4, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.E4, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.F4, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.F4, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C4, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.C4, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C5, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.C5, false));

		return track;
	}

	public static Track createTrackWithSeveralBreaks() {
		Track track = new Track(MusicalKey.VIOLIN, MusicalInstrument.ACOUSTIC_GRAND_PIANO);
		long tick = 0;

		tick += NoteLength.HALF.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);
		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.C4, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.C4, false));

		return track;
	}

	public static Track createSemiComplexTrack(MusicalInstrument instrument) {
		Track track = new Track(MusicalKey.VIOLIN, instrument);

		long tick = 0;

		track.addNoteEvent(tick, new NoteEvent(NoteName.C5, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.C5, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C4, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D4, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.C4, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D4, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C4, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D4, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.C4, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D4, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.E4, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.F4, true));

		tick += NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

		track.addNoteEvent(tick, new NoteEvent(NoteName.E4, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.F4, false));

		return track;
	}
}
