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
- NEW: contains various setter methods so that the controller can update the view

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

CHANGELOG:

- All view implementations no longer depend on the model as a field. instead they each implement
  various setter method so that the controller can modify the view.

ConcreteGuiViewPanel

- Draws everything in the top half of the GUI view (everything except the piano)
- Handles the key input so the user can move the red line in the GUI view

GuiViewFrame

- The window for the GUI view

PianoView

- Helper class that draws the piano in the bottom half of the GUI view
- Piano is represented as a Map<Note, Rectangle> so that the currently playing notes can be
  easily identified and turned orange

TextualView

- Represents the text/console view of the music
- Uses the methods from the previous assignment to draw the music as a String

MidiViewImpl

- Audible view of the music
- Now uses a sequencer rather than a receiver and a synthesizer to play the music so that it
  can take advantage of its additional features such as pausing music

CHANGELOG:

- The MidViewImpl class now uses a sequencer to play the music rather than using the receiver
  directly so that it can take advantage of its additional functionality such as pausing
  music and responding to certain events via metaMessages.
- The sequencer has been embedded with meta messages so that a metaMessageListener
  can be set up by the controller so that it can react to these events

MockMidiDevice:

- Mocks a MIDI Synthesizer
- Used for testing MIDI input and output

MockReceiver

- Mocks a MIDI Receiver
- Used for testing MIDI input and output
- Out of date now that the MidiView uses a sequencer rather than a receiver

CONTROLLER:
-----------------------------------------------------------------------------------------------------

KeyboardListener

- The KeyListener used by the controller and view to respond to key commands
- Uses mappings that map either integers or characters to runnable objects in order to
  respond efficiently to commands.
- Maps are set by the controller.

MetaEventListener

- The MouseListener used by the controller and view to respond to mouse clicks
- Uses mappings that map either integers to runnable objects in order to
  respond efficiently to commands.
- Maps are set by the controller.

MetaEventsListener

- The MetaEventListener used by the controller and view to respond to metaEvents that are embedded
  in the sequencer of the midiViewImpl
- Uses mappings that map either strings to runnable objects in order to
  respond efficiently to commands.
- Maps are set by the controller.

MusicEditorController

- The controller to connect the model to the views.
- Controls the view with various mappings that contain runnable objects which are made in the
  controller so that it may have access to both the view and the model simultaneously.
- The controller now uses the meta messages in the sequencer to move the redline
    in the composition view each time it receives a metaMessage that says "beat" which
    are embedded in the sequencer following each beat in the music
- The controller now responds to metaMessages that say "end" which are embedded in
    the sequencer at the end of every sequence. This allows for the controller to pause the
    view when it reaches the end of a song

----------------------------------------------------------------------------------------------------
MusicEditor

- Handles input from the user in order to display the music