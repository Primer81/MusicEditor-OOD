MUSIC EDITOR
CS3500 - FREIFELD
Ian Meyers

CLASS OVERVIEW
----------------------------------------------------------------------

Pitch:

- Enum
- Twelve values, representing the twelve standard pitches in Western Music
- Represented as a String

Note:

- Four private fields: 
	- Duration (int), represents the length of the note
	- Octave (int), represents what octave the note is being played in
	- Start (int), represents what beat the note begins at
	- Pitch (Pitch), represents the pitch value of the note
- equals() and hashCode() have been overridden for Note comparisons
- No user functionality other than getters and instantiation.
- Duration representation will likely be changed, as some notes can have non-integer durations.

IMusicEditorModel:

- Interface for music editor models
- Supports adding and removing notes to the music, as well as getting the state of the music
- Music is represented as a List<Note>

MusicEditorModel:

- Implementation of an IMusicEditorModel
- Music is represented as an ArrayList<Note>
- Adds notes to the music if the notes are valid (are neither null nor exist already)
- Removes notes from the music if the given note is valid (neither null, must exist already)
- State of the music is returned as a String (NOTE: this part is unfinished)


