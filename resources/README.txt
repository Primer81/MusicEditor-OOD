MUSIC EDITOR
CS3500 - FREIFELD
Ian Meyers, Grahm Larkham

CLASS OVERVIEW
----------------------------------------------------------------------

MODEL:
----------------------------------------------------------------------

Pitch:

- Enum
- Twelve values, representing the twelve standard pitches in Western Music
- Represented as a String
------------------------------------------------------------------------
Note:

- Four private fields: 
	- Duration (int), represents the length of the note
	- Octave (int), represents what octave the note is being played in
	- Start (int), represents what beat the note begins at
	- Pitch (Pitch), represents the pitch value of the note
- equals() and hashCode() have been overridden for Note comparisons
- No user functionality other than getters and instantiation.
- Duration representation will likely be changed, as some notes can have non-integer durations.

CHANGELOG:

- Instrument and volume fields added for MIDI playback
- setStart() method added for when two pieces are concatenated in the model
---------------------------------------------------------------------------------

IMusicEditorModel:

- Interface for music editor models
- Supports adding and removing notes to the music, as well as getting the state of the music
- Music is represented as a List<Note>

CHANGELOG:

- Additional methods added to allow merging of two pieces of music
- Can get the notes playing at a given beat, useful for MIDI and GUI views
----------------------------------------------------------------------------------------------

MusicEditorModel:

- Implementation of an IMusicEditorModel
- Music is represented as an ArrayList<Note>
- Adds notes to the music if the notes are valid (are neither null nor exist already)
- Removes notes from the music if the given note is valid (neither null, must exist already)
- State of the music is returned as a String

CHANGELOG:

- Necessary private methods added for new interface functionality

VIEW:
-----------------------------------------------------------------------------------------------------

IMusicEditorView:

- Interface for music editor views
- Allows the user to display the music using their chosen view type (text, GUI, MIDI)
- Has a factory class used for handling String input and returning the appropriate view type

ConcreteGuiViewPanel

- Draws everything in the top half of the GUI view (everything except the piano)
- Handles the key input so the user can move the red line in the GUI view

GuiViewFrame

- The window for the GUI view

PianoView

- Helper class that draws the piano in the bottom half of the GUI view
- Piano is represented as a Map<Note, Rectangle> so that the currently playing notes can be easily identified and turned orange

TextualView

- Represents the text/console view of the music
- Uses the methods from the previous assignment to draw the music as a String

MidiViewImpl

- Audible view of the music
- Uses the Synthesizer + Receiver method to handle MIDI input and output

MockMidiDevice:

- Mocks a MIDI Synthesizer
- Used for testing MIDI input and output

MockReceiverr

- Mocks a MIDI Receiver
- Used for testing MIDI input and output
-----------------------------------------------------------------------------------------------------

MusicEditor

- Handles input from the user in order to display the music
