package cs3500.music.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Testing class for MusicEditorModel.
 */
public class MusicEditorModelTest {

  Note a3 = new Note(3, 3, 0, Pitch.A, 1, 1);
  Note b4 = new Note(2, 4, 3, Pitch.B, 1, 1);
  Note c3 = new Note(3, 3, 5, Pitch.C, 1, 1);
  IMusicEditorModel model;

  /**
   * Initializes test data.
   */
  @Before
  public void init() {
    this.model = new MusicEditorModel();
  }

  // Sets Rule to expect no exception unless stated otherwise.
  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteNullException() {
    model.addNote(null);
  }

  @Test
  public void testValidAddNote() {
    model.addNote(a3);
    model.addNote(b4);
    model.addNote(c3);
    assertEquals(model.getMusic().size(), 3);
    assertEquals(model.getMusic().get(0), a3);
    assertEquals(model.getMusic().get(1), b4);
    assertEquals(model.getMusic().get(2), c3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteExistsAlreadyException() {
    model.addNote(a3);
    model.addNote(a3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNoteNullException() {
    model.removeNote(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNoteDoesNotExistException() {
    model.addNote(a3);
    model.addNote(b4);
    model.addNote(c3);
    model.removeNote(new Note(0, 0, 0, Pitch.D,1 , 1));
  }

  @Test
  public void testValidRemoveNote() {
    model.addNote(a3);
    model.addNote(b4);
    model.addNote(c3);
    model.removeNote(a3);
    assertEquals(model.getMusic().size(), 2);
    assertEquals(model.getMusic().contains(a3), false);
  }

  // Tests that the add note method throws an exception if the note about to be added
  // would overlap with another note.
  @Test
  public void testAddNote01() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: This note overlaps with an existing note.");

    this.model.addNote(new Note(1, 1, 0, Pitch.A, 1, 1));
    this.model.addNote(new Note(1, 1, 0, Pitch.A, 1, 1));
  }

  // Tests that the add note method throws an exception if the note about to be added
  // would overlap with another note.
  @Test
  public void testAddNote02() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: This note overlaps with an existing note.");

    this.model.addNote(new Note(2, 1, 0, Pitch.A, 1, 1));
    this.model.addNote(new Note(1, 1, 1, Pitch.A, 1, 1));
  }

  // Tests that the add note method throws an exception if the note about to be added
  // would overlap with another note.
  @Test
  public void testAddNote03() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: This note overlaps with an existing note.");

    this.model.addNote(new Note(1, 1, 1, Pitch.A, 1, 1));
    this.model.addNote(new Note(2, 1, 0, Pitch.A, 1, 1));
  }

  // Tests that the add note method can add notes.
  @Test
  public void testAddNote04() {
    this.model.addNote(new Note(1, 1, 1, Pitch.A, 1, 1));
    assertEquals("" +
            "   A1 \n" +
            "0     \n" +
            "1  X  ",
        this.model.getState());
  }

  // Tests that the add not method can add notes of same octave and pitch within close proximity.
  @Test
  public void testAddNote05() {
    this.model.addNote(new Note(1, 1, 1, Pitch.A, 1, 1));
    this.model.addNote(new Note(1, 1, 2, Pitch.A, 1, 1));
    assertEquals("" +
            "   A1 \n" +
            "0     \n" +
            "1  X  \n" +
            "2  X  ",
        this.model.getState());
  }

  // Tests adding a bunch of different notes with the add note method.
  @Test
  public void testAddNote06() {
    this.model.addNote(new Note(1, 5, 1, Pitch.A, 1, 1));
    this.model.addNote(new Note(10, 5, 2, Pitch.B, 1, 1));
    this.model.addNote(new Note(9, 5, 3, Pitch.C, 1, 1));
    this.model.addNote(new Note(8, 6, 5, Pitch.C_Sharp, 1, 1));
    this.model.addNote(new Note(5, 6, 8, Pitch.D, 1, 1));
    assertEquals("" +
            "    C5  C#5   D5  D#5   E5   F5  F#5   G5  G#5   A5  A#5   B5   C6  C#6   D6 \n" +
            " 0                                                                           \n" +
            " 1                                               X                           \n" +
            " 2                                                         X                 \n" +
            " 3  X                                                      |                 \n" +
            " 4  |                                                      |                 \n" +
            " 5  |                                                      |         X       \n" +
            " 6  |                                                      |         |       \n" +
            " 7  |                                                      |         |       \n" +
            " 8  |                                                      |         |    X  \n" +
            " 9  |                                                      |         |    |  \n" +
            "10  |                                                      |         |    |  \n" +
            "11  |                                                      |         |    |  \n" +
            "12                                                                   |    |  ",
        this.model.getState());
  }

  // Tests that the remove note method throws an exception if it cannot find a note.
  @Test
  public void testRemoveNote01_0() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: Note does not exist.");

    this.model.removeNote(new Note(1, 1,0, Pitch.A, 1, 1));
  }

  // Tests that the remove note method throws an exception if it cannot find a note.
  @Test
  public void testRemoveNote01_1() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: Note does not exist.");

    this.model.addNote(new Note(1, 1,0, Pitch.B, 1, 1));
    this.model.removeNote(new Note(1, 1,0, Pitch.A, 1, 1));
  }

  // Tests that the remove note method throws an exception if it cannot find a note.
  @Test
  public void testRemoveNote01_2() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: Note does not exist.");

    this.model.addNote(new Note(1, 1,1, Pitch.A, 1, 1));
    this.model.removeNote(new Note(1, 1,0, Pitch.A, 1, 1));
  }

  // Tests that the remove note method throws an exception if it cannot find a note.
  @Test
  public void testRemoveNote01_3() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: Note does not exist.");

    this.model.addNote(new Note(1, 2,0, Pitch.A, 1, 1));
    this.model.removeNote(new Note(1, 1,0, Pitch.A, 1, 1));
  }

  // Tests that the remove note method throws an exception if it cannot find a note.
  @Test
  public void testRemoveNote01_4() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: Note does not exist.");

    this.model.addNote(new Note(2, 1,0, Pitch.A, 1, 1));
    this.model.removeNote(new Note(1, 1,0, Pitch.A, 1, 1));
  }

  // Tests that the remove note method throws an exception if it cannot find a note.
  @Test
  public void testRemoveNote01_5() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: Note does not exist.");

    this.model.addNote(new Note(1, 1,0, Pitch.A, 2, 1));
    this.model.removeNote(new Note(1, 1,0, Pitch.A, 1, 1));
  }

  // Tests that the remove note method throws an exception if it cannot find a note.
  @Test
  public void testRemoveNote01_6() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: Note does not exist.");

    this.model.addNote(new Note(1, 1,0, Pitch.A, 1, 2));
    this.model.removeNote(new Note(1, 1,0, Pitch.A, 1, 1));
  }

  // Tests that the remove note method removes the note.
  @Test
  public void testRemoveNote02() {
    this.model.addNote(new Note(1, 1,0, Pitch.A, 1, 1));
    assertEquals("" +
            "   A1 \n" +
            "0  X  ",
        this.model.getState());
    this.model.removeNote(new Note(1, 1,0, Pitch.A, 1, 1));
    assertEquals("",
        this.model.getState());
  }

  // Tests that the merge method throws an exception if given an invalid sheet.
  @Test
  public void testMerge01() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: Invalid sheet given to merge method.");

    this.model.merge(new ArrayList<>(Arrays.asList(
        new Note(1, 1,0, Pitch.A, 1, 1),
        new Note(1, 1,0, Pitch.A, 1, 1)

    )));
  }

  // Tests that the merge method throws an exception if given an invalid sheet.
  @Test
  public void testMerge02() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: Invalid sheet given to merge method.");

    this.model.merge(new ArrayList<>(Arrays.asList(
        new Note(2, 1,0, Pitch.A, 1, 1),
        new Note(1, 1,1, Pitch.A, 1, 1)
    )));
  }

  // Tests that the merge method throws an exception if given an invalid sheet.
  @Test
  public void testMerge03() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: Invalid sheet given to merge method.");

    this.model.merge(new ArrayList<>(Arrays.asList(
        new Note(1, 1,1, Pitch.A, 1, 1),
        new Note(2, 1,0, Pitch.A, 1, 1)
    )));
  }

  // Tests that the merge method throws an exception if the merge would result in
  // an overlap.
  @Test
  public void testMerge04() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: This note overlaps with an existing note.");

    this.model.addNote(new Note(1, 1,0, Pitch.A, 1, 1));
    this.model.merge(new ArrayList<>(Arrays.asList(
        new Note(1, 1,0, Pitch.A, 1, 1)
    )));
  }

  // Tests that the merge method throws an exception if the merge would result in
  // an overlap.
  @Test
  public void testMerge05() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: This note overlaps with an existing note.");

    this.model.addNote(new Note(2, 1,0, Pitch.A, 1, 1));
    this.model.merge(new ArrayList<>(Arrays.asList(
        new Note(1, 1,1, Pitch.A, 1, 1)
    )));
  }

  // Tests that the merge method throws an exception if the merge would result in
  // an overlap.
  @Test
  public void testMerge06() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: This note overlaps with an existing note.");

    this.model.addNote(new Note(1, 1,1, Pitch.A, 1, 1));
    this.model.merge(new ArrayList<>(Arrays.asList(
        new Note(2, 1,0, Pitch.A, 1, 1)
    )));
  }

  // Tests that the merge method can merge two pieces of music.
  @Test
  public void testMerge07() {
    ArrayList<Note> newNotes = new ArrayList<>(Arrays.asList(
        new Note(1, 1,2, Pitch.A, 1, 1),
        new Note(2, 1,5, Pitch.B, 1, 1),
        new Note(2, 2,2, Pitch.C, 1, 1)
    ));
    MusicEditorModel dummy = new MusicEditorModel(newNotes, 1);
    this.model.addNote(new Note(1, 2,2, Pitch.D, 1, 1));
    this.model.addNote(new Note(4, 2,3, Pitch.E, 1, 1));
    this.model.addNote(new Note(2, 2,4, Pitch.F, 1, 1));

    assertEquals("" +
        "   D2  D#2   E2   F2 \n" +
        "0                    \n" +
        "1                    \n" +
        "2  X                 \n" +
        "3            X       \n" +
        "4            |    X  \n" +
        "5            |    |  \n" +
        "6            |       ", this.model.getState());
    assertEquals("" +
        "   A1  A#1   B1   C2 \n" +
        "0                    \n" +
        "1                    \n" +
        "2  X              X  \n" +
        "3                 |  \n" +
        "4                    \n" +
        "5            X       \n" +
        "6            |       ", dummy.getState());

    this.model.merge(newNotes);

    assertEquals("" +
        "   A1  A#1   B1   C2  C#2   D2  D#2   E2   F2 \n" +
        "0                                             \n" +
        "1                                             \n" +
        "2  X              X         X                 \n" +
        "3                 |                   X       \n" +
        "4                                     |    X  \n" +
        "5            X                        |    |  \n" +
        "6            |                        |       ", this.model.getState());
  }

  // Tests that the configurable merge throws an exception if a note would overlap
  // another note as a result of its second parameter.
  @Test
  public void testMerge08() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: This note overlaps with an existing note.");

    this.model.addNote(new Note(1, 1, 4, Pitch.A, 1, 1));
    this.model.merge(new ArrayList<>(Arrays.asList(
        new Note(1, 1, 0, Pitch.A, 1, 1))), 4);
  }

  // Tests that the configurable merge throws an exception if a note would overlap
  // another note as a result of its second parameter.
  @Test
  public void testMerge09() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: This note overlaps with an existing note.");

    this.model.addNote(new Note(1, 1, 0, Pitch.A, 1, 1));
    this.model.merge(new ArrayList<>(Arrays.asList(
        new Note(1, 1, 4, Pitch.A, 1, 1))), -4);
  }

  // Tests that the configurable merge can merge at whatever time specified.
  @Test
  public void testMerge10() {
    ArrayList<Note> newNotes = new ArrayList<>(Arrays.asList(
        new Note(1, 1,2, Pitch.A, 1, 1),
        new Note(2, 1,5, Pitch.B, 1, 1),
        new Note(2, 2,2, Pitch.C, 1, 1)
    ));
    MusicEditorModel dummy = new MusicEditorModel(newNotes, 1);
    this.model.addNote(new Note(1, 2,2, Pitch.D, 1, 1));
    this.model.addNote(new Note(4, 2,3, Pitch.E, 1, 1));
    this.model.addNote(new Note(2, 2,4, Pitch.F, 1, 1));

    assertEquals("" +
        "   D2  D#2   E2   F2 \n" +
        "0                    \n" +
        "1                    \n" +
        "2  X                 \n" +
        "3            X       \n" +
        "4            |    X  \n" +
        "5            |    |  \n" +
        "6            |       ", this.model.getState());
    assertEquals("" +
        "   A1  A#1   B1   C2 \n" +
        "0                    \n" +
        "1                    \n" +
        "2  X              X  \n" +
        "3                 |  \n" +
        "4                    \n" +
        "5            X       \n" +
        "6            |       ", dummy.getState());

    this.model.merge(newNotes, 4);

    assertEquals("" +
        "    A1  A#1   B1   C2  C#2   D2  D#2   E2   F2 \n" +
        " 0                                             \n" +
        " 1                                             \n" +
        " 2                           X                 \n" +
        " 3                                     X       \n" +
        " 4                                     |    X  \n" +
        " 5                                     |    |  \n" +
        " 6  X              X                   |       \n" +
        " 7                 |                           \n" +
        " 8                                             \n" +
        " 9            X                                \n" +
        "10            |                                ", this.model.getState());
  }

  // Tests that the configurable merge can merge at whatever time specified.
  @Test
  public void testMerge11() {
    ArrayList<Note> newNotes = new ArrayList<>(Arrays.asList(
        new Note(1, 1,2, Pitch.A, 1, 1),
        new Note(2, 1,5, Pitch.B, 1, 1),
        new Note(2, 2,2, Pitch.C, 1, 1)
    ));
    MusicEditorModel dummy = new MusicEditorModel(newNotes, 1);
    this.model.addNote(new Note(1, 2,2, Pitch.D, 1, 1));
    this.model.addNote(new Note(4, 2,3, Pitch.E, 1, 1));
    this.model.addNote(new Note(2, 2,4, Pitch.F, 1, 1));

    assertEquals("" +
        "   D2  D#2   E2   F2 \n" +
        "0                    \n" +
        "1                    \n" +
        "2  X                 \n" +
        "3            X       \n" +
        "4            |    X  \n" +
        "5            |    |  \n" +
        "6            |       ", this.model.getState());
    assertEquals("" +
        "   A1  A#1   B1   C2 \n" +
        "0                    \n" +
        "1                    \n" +
        "2  X              X  \n" +
        "3                 |  \n" +
        "4                    \n" +
        "5            X       \n" +
        "6            |       ", dummy.getState());

    this.model.merge(newNotes, -4);

    assertEquals("" +
        "    A1  A#1   B1   C2  C#2   D2  D#2   E2   F2 \n" +
        " 0                                             \n" +
        " 1                                             \n" +
        " 2  X              X                           \n" +
        " 3                 |                           \n" +
        " 4                                             \n" +
        " 5            X                                \n" +
        " 6            |              X                 \n" +
        " 7                                     X       \n" +
        " 8                                     |    X  \n" +
        " 9                                     |    |  \n" +
        "10                                     |       ", this.model.getState());
  }

  // Tests that the append method throws an exception if given an invalid list of notes.
  @Test
  public void testAppend01() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Invalid sheet given to append method.");

    this.model.append(new ArrayList<>(Arrays.asList(
        new Note(1, 1, 0, Pitch.A, 1, 1),
        new Note(1, 1, 0, Pitch.A, 1, 1)
    )));
  }

  // Tests that the append method throws an exception if given an invalid list of notes.
  @Test
  public void testAppend02() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Invalid sheet given to append method.");

    this.model.append(new ArrayList<>(Arrays.asList(
        new Note(1, 1, 1, Pitch.A, 1, 1),
        new Note(2, 1, 0, Pitch.A, 1, 1)
    )));
  }

  // Tests that the append method throws an exception if given an invalid list of notes.
  @Test
  public void testAppend03() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Invalid sheet given to append method.");

    this.model.append(new ArrayList<>(Arrays.asList(
        new Note(2, 1, 0, Pitch.A, 1, 1),
        new Note(1, 1, 1, Pitch.A, 1, 1)
    )));
  }

  // Tests that the append method throws an exception if given an invalid list of notes.
  @Test
  public void testAppend04() {
    ArrayList<Note> newNotes = new ArrayList<>(Arrays.asList(
        new Note(1, 1,2, Pitch.A, 1, 1),
        new Note(2, 1,5, Pitch.B, 1, 1),
        new Note(2, 2,2, Pitch.C, 1, 1)
    ));
    MusicEditorModel dummy = new MusicEditorModel(newNotes, 1);
    assertEquals("" +
        "   A1  A#1   B1   C2 \n" +
        "0                    \n" +
        "1                    \n" +
        "2  X              X  \n" +
        "3                 |  \n" +
        "4                    \n" +
        "5            X       \n" +
        "6            |       ", dummy.getState());
    assertEquals("", this.model.getState());

    this.model.append(newNotes);

    assertEquals("" +
        "   A1  A#1   B1   C2 \n" +
        "0                    \n" +
        "1                    \n" +
        "2  X              X  \n" +
        "3                 |  \n" +
        "4                    \n" +
        "5            X       \n" +
        "6            |       ", this.model.getState());
  }

  // Tests that the append can append a list of notes.
  @Test
  public void testAppend05() {
    ArrayList<Note> newNotes = new ArrayList<>(Arrays.asList(
        new Note(1, 1,2, Pitch.A, 1, 1),
        new Note(2, 1,5, Pitch.B, 1, 1),
        new Note(2, 2,2, Pitch.C, 1, 1)
    ));
    MusicEditorModel dummy = new MusicEditorModel(newNotes, 1);
    this.model.addNote(new Note(1, 2,2, Pitch.D, 1, 1));
    this.model.addNote(new Note(4, 2,3, Pitch.E, 1, 1));
    this.model.addNote(new Note(2, 2,4, Pitch.F, 1, 1));

    assertEquals("" +
        "   D2  D#2   E2   F2 \n" +
        "0                    \n" +
        "1                    \n" +
        "2  X                 \n" +
        "3            X       \n" +
        "4            |    X  \n" +
        "5            |    |  \n" +
        "6            |       ", this.model.getState());
    assertEquals("" +
        "   A1  A#1   B1   C2 \n" +
        "0                    \n" +
        "1                    \n" +
        "2  X              X  \n" +
        "3                 |  \n" +
        "4                    \n" +
        "5            X       \n" +
        "6            |       ", dummy.getState());

    this.model.append(newNotes);

    assertEquals("" +
        "    A1  A#1   B1   C2  C#2   D2  D#2   E2   F2 \n" +
        " 0                                             \n" +
        " 1                                             \n" +
        " 2                           X                 \n" +
        " 3                                     X       \n" +
        " 4                                     |    X  \n" +
        " 5                                     |    |  \n" +
        " 6                                     |       \n" +
        " 7                                             \n" +
        " 8                                             \n" +
        " 9  X              X                           \n" +
        "10                 |                           \n" +
        "11                                             \n" +
        "12            X                                \n" +
        "13            |                                ", this.model.getState());
  }


  // Tests that the getState method returns the expected string.
  @Test
  public void testgetState01() {
    assertEquals("", this.model.getState());
    this.model.addNote(new Note(2, 1, 0, Pitch.A, 1, 1));
    assertEquals("" +
        "   A1 \n" +
        "0  X  \n" +
        "1  |  ", this.model.getState());
    this.model.addNote(new Note(3, 2, 0, Pitch.A, 1, 1));
    assertEquals("" +
            "   A1  A#1   B1   C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2 \n" +
            "0  X                                                           X  \n" +
            "1  |                                                           |  \n" +
            "2                                                              |  ",
        this.model.getState());
    this.model.addNote(new Note(1, 2, 2, Pitch.D, 1, 1));
    assertEquals("" +
            "   A1  A#1   B1   C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2 \n" +
            "0  X                                                           X  \n" +
            "1  |                                                           |  \n" +
            "2                           X                                  |  ",
        this.model.getState());
    this.model.addNote(new Note(10, 2, 2, Pitch.C_Sharp, 1, 1));
    assertEquals("" +
            "    A1  A#1   B1   C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2 \n" +
            " 0  X                                                           X  \n" +
            " 1  |                                                           |  \n" +
            " 2                      X    X                                  |  \n" +
            " 3                      |                                          \n" +
            " 4                      |                                          \n" +
            " 5                      |                                          \n" +
            " 6                      |                                          \n" +
            " 7                      |                                          \n" +
            " 8                      |                                          \n" +
            " 9                      |                                          \n" +
            "10                      |                                          \n" +
            "11                      |                                          ",
        this.model.getState());
  }

  // Tests that the hasNext method can determine if there is a next beat.
  @Test
  public void testHasNext() {
    assertEquals(false, this.model.hasNext());
    this.model.addNote(new Note(1, 1, 0, Pitch.A, 1, 1));
    assertEquals(false, this.model.hasNext());
    this.model.addNote(new Note(2, 1, 1, Pitch.A, 1, 1));
    assertEquals(true, this.model.hasNext());
    this.model.removeNote(new Note(2, 1, 1, Pitch.A, 1, 1));
    this.model.addNote(new Note(1, 1, 2, Pitch.A, 1, 1));
    assertEquals(true, this.model.hasNext());

  }

  // Tests that the hasPrev method can determine if tere is a previous beat.
  @Test
  public void testHasPrev() {
    assertEquals(false, this.model.hasPrev());
    this.model.addNote(new Note(1, 1, 0, Pitch.A, 1, 1));
    assertEquals(false, this.model.hasPrev());
    this.model.addNote(new Note(2, 1, 1, Pitch.A, 1, 1));
    this.model.nextBeat();
    assertEquals(true, this.model.hasPrev());
    this.model.removeNote(new Note(2, 1, 1, Pitch.A, 1, 1));
    this.model.addNote(new Note(1, 1, 2, Pitch.A, 1, 1));
    this.model.setBeat(1);
    assertEquals(true, this.model.hasPrev());
  }

  // Tests the isEmpty method
  @Test
  public void testIsEmpty01() {
    assertEquals(true, this.model.isEmpty());
    this.model.addNote(new Note(1, 1, 0, Pitch.A, 1, 1));
    assertEquals(false, this.model.isEmpty());
  }

  // Tests that the nextBeat method throws an exception if there is no next beat.
  @Test
  public void testNextBeat01() {
    expectedEx.expect(IllegalStateException.class);
    expectedEx.expectMessage("No next beat.");

    this.model.nextBeat();
  }

  // Tests that the nextBeat method throws an exception if there is no next beat.
  @Test
  public void testNextBeat02() {
    expectedEx.expect(IllegalStateException.class);
    expectedEx.expectMessage("No next beat.");

    this.model.addNote(new Note(1, 1, 0, Pitch.A, 1, 1));
    this.model.nextBeat();
  }

  // Tests that the nextBeat method returns an empty list if the next beat is a rest.
  @Test
  public void testNextBeat03() {
    this.model.addNote(new Note(1, 1, 2, Pitch.A, 1, 1));
    this.model.nextBeat();
    assertEquals(new ArrayList<Note>(), this.model.playBeat());
  }

  // Tests that the nextBeat method returns the expected list of notes.
  @Test
  public void testNextBeat04() {
    this.model.addNote(new Note(1, 1, 0, Pitch.A, 1, 1));
    this.model.addNote(new Note(1, 1, 1, Pitch.A, 1, 1));
    this.model.nextBeat();
    assertEquals(new ArrayList<Note>(
            Arrays.asList(
                new Note(1, 1, 1, Pitch.A, 1, 1))),
        this.model.playBeat());
    this.model.addNote(new Note(1, 1, 2, Pitch.A, 1, 1));
    this.model.addNote(new Note(1, 2, 2, Pitch.A, 1, 1));
    this.model.nextBeat();
    assertEquals(new ArrayList<Note>(
            Arrays.asList(
                new Note(1, 1, 2, Pitch.A, 1, 1),
                new Note(1, 2, 2, Pitch.A, 1, 1))),
        this.model.playBeat());
    this.model.addNote(new Note(1, 1, 3, Pitch.A, 1, 1));
    this.model.addNote(new Note(1, 1, 3, Pitch.B, 1, 1));
    this.model.addNote(new Note(1, 1, 3, Pitch.C, 1, 1));
    this.model.nextBeat();
    assertEquals(new ArrayList<Note>(
            Arrays.asList(
                new Note(1, 1, 3, Pitch.A, 1, 1),
                new Note(1, 1, 3, Pitch.B, 1, 1),
                new Note(1, 1, 3, Pitch.C, 1, 1))),
        this.model.playBeat());
  }

  // Tests that the nextBeat method throws an exception if there is no next beat.
  @Test
  public void testPrevBeat01() {
    expectedEx.expect(IllegalStateException.class);
    expectedEx.expectMessage("No previous beat.");

    this.model.prevBeat();
  }

  // Tests that the nextBeat method throws an exception if there is no next beat.
  @Test
  public void testPrevBeat02() {
    expectedEx.expect(IllegalStateException.class);
    expectedEx.expectMessage("No previous beat.");

    this.model.addNote(new Note(1, 1, 0, Pitch.A, 1, 1));
    this.model.addNote(new Note(1, 1, 1, Pitch.A, 1, 1));
    this.model.nextBeat();
    assertEquals(new ArrayList<Note>(
            Arrays.asList(
                new Note(1, 1, 1, Pitch.A, 1, 1))),
        this.model.playBeat());
    this.model.prevBeat();
    assertEquals(new ArrayList<Note>(
            Arrays.asList(
                new Note(1, 1, 0, Pitch.A, 1, 1))),
        this.model.playBeat());
    this.model.prevBeat(); // no more beats left
  }

  // Tests that the prevBeat method returns an empty list if the previous beat is a rest.
  @Test
  public void testPrevBeat03() {
    this.model.addNote(new Note(1, 1, 2, Pitch.A, 1, 1));
    this.model.nextBeat();
    assertEquals(new ArrayList<Note>(), this.model.playBeat());
    this.model.prevBeat();
    assertEquals(new ArrayList<Note>(), this.model.playBeat());
  }

  // Tests that the prevBeat method returns the expected list of notes.
  @Test
  public void testPrevBeat04() {
    this.model.addNote(new Note(1, 1, 0, Pitch.A, 1, 1));
    this.model.addNote(new Note(1, 1, 1, Pitch.A, 1, 1));
    // Advance with nextBeat
    this.model.nextBeat();
    assertEquals(new ArrayList<Note>(
            Arrays.asList(
                new Note(1, 1, 1, Pitch.A, 1, 1))),
        this.model.playBeat());
    // Go back with prevBeat
    this.model.prevBeat();
    assertEquals(new ArrayList<Note>(
            Arrays.asList(
                new Note(1, 1, 0, Pitch.A, 1, 1))),
        this.model.playBeat());
    this.model.addNote(new Note(1, 2, 0, Pitch.A, 1, 1));
    // Advance with nextBeat
    this.model.nextBeat();
    assertEquals(new ArrayList<Note>(
            Arrays.asList(
                new Note(1, 1, 1, Pitch.A, 1, 1))),
        this.model.playBeat());
    // Go back with prevBeat
    this.model.prevBeat();
    assertEquals(new ArrayList<Note>(
            Arrays.asList(
                new Note(1, 1, 0, Pitch.A, 1, 1),
                new Note(1, 2, 0, Pitch.A, 1, 1))),
        this.model.playBeat());
    this.model.addNote(new Note(1, 1, 0, Pitch.B, 1, 1));
    this.model.addNote(new Note(1, 1, 0, Pitch.C, 1, 1));
    // Advance with nextBeat
    this.model.nextBeat();
    assertEquals(new ArrayList<Note>(
            Arrays.asList(
                new Note(1, 1, 1, Pitch.A, 1, 1))),
        this.model.playBeat());
    // Go back with prevBeat
    this.model.prevBeat();
    assertEquals(new ArrayList<Note>(
            Arrays.asList(
                new Note(1, 1, 0, Pitch.A, 1, 1),
                new Note(1, 2, 0, Pitch.A, 1, 1),
                new Note(1, 1, 0, Pitch.B, 1, 1),
                new Note(1, 1, 0, Pitch.C, 1, 1))),
        this.model.playBeat());
  }

  // Tests that the playBeat method throws an exception if there are no beats in the current
  // music sheet.
  @Test
  public void testPlayBeat01() {
    expectedEx.expect(IllegalStateException.class);
    expectedEx.expectMessage("No notes have been added to the editor.");

    this.model.playBeat();
  }

  // Tests that the playBeat method returns an empty list if the current beat is a rest.
  @Test
  public void testPlayBeat02() {
    this.model.addNote(new Note(1, 1, 1, Pitch.A, 1, 1));
    assertEquals(new ArrayList<Note>(), this.model.playBeat());
  }

  // Tests that the playBeat method returns the expected list of notes.
  @Test
  public void testPlayBeat03() {
    this.model.addNote(new Note(1, 1, 0, Pitch.A, 1, 1));
    assertEquals(new ArrayList<Note>(Arrays.asList(
        new Note(1, 1, 0, Pitch.A, 1, 1))),
        this.model.playBeat());
    this.model.addNote(new Note(1, 1, 1, Pitch.A, 1, 1));
    assertEquals(new ArrayList<Note>(Arrays.asList(
        new Note(1, 1, 0, Pitch.A, 1, 1))),
        this.model.playBeat());
    this.model.addNote(new Note(1, 1, 0, Pitch.B, 1, 1));
    assertEquals(new ArrayList<Note>(Arrays.asList(
        new Note(1, 1, 0, Pitch.A, 1, 1),
        new Note(1, 1, 0, Pitch.B, 1, 1))),
        this.model.playBeat());
    this.model.addNote(new Note(1, 1, 1, Pitch.B, 1, 1));
    assertEquals(new ArrayList<Note>(Arrays.asList(
        new Note(1, 1, 0, Pitch.A, 1, 1),
        new Note(1, 1, 0, Pitch.B, 1, 1))),
        this.model.playBeat());
    this.model.addNote(new Note(1, 1, 0, Pitch.C, 1, 1));
    assertEquals(new ArrayList<Note>(Arrays.asList(
        new Note(1, 1, 0, Pitch.A, 1, 1),
        new Note(1, 1, 0, Pitch.B, 1, 1),
        new Note(1, 1, 0, Pitch.C, 1, 1))),
        this.model.playBeat());
  }

  // Tests that the setBeat method throws an exception if the given value is negative.
  @Test
  public void testSetBeat01() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Cannot set the current beat to the given value.");

    this.model.addNote(new Note(1, 1, 0, Pitch.A, 1, 1));
    this.model.setBeat(-1);
  }

  // Tests that the setBeat method throws an exception if the given value is greater than the
  // total number of beats.
  @Test
  public void testSetBeat02() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Cannot set the current beat to the given value.");

    this.model.addNote(new Note(1, 1, 0, Pitch.A, 1, 1));
    this.model.setBeat(1);
  }

  // Tests that the setBeat method throws an IllegalStateException if trying to set the beat
  // when there are no beats to be set to.
  @Test
  public void testSetBeat03() {
    expectedEx.expect(IllegalStateException.class);
    expectedEx.expectMessage("No beats exist.");

    this.model.setBeat(0);
  }

  // Tests that the getBeat method throws an IllegalStateException if trying to get the beat when
  // no beats exist.
  @Test
  public void testGetBeat01() {
    expectedEx.expect(IllegalStateException.class);
    expectedEx.expectMessage("No beats exist.");

    this.model.getBeat();
  }

  // Tests that the getBeat method can get the current beat.
  @Test
  public void testGetBeat02() {
    this.model.addNote(new Note(1, 1, 0, Pitch.A, 1, 1));
    assertEquals(0, this.model.getBeat());
    this.model.addNote(new Note(1, 1, 1, Pitch.A, 1, 1));
    this.model.nextBeat();
    assertEquals(1, this.model.getBeat());
  }
}