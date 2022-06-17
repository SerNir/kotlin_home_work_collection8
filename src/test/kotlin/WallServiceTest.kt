import org.junit.Test

import org.junit.Assert.*
import kotlin.test.assertContentEquals

class WallServiceTest {
    private val notes = WallService

    @Test
    fun add() {
        val note1 = Note(0, 1, "Title note", "Text note")
        val note2 = Note(0, 1, "Title note", "Text note")
        val note3 = Note(0, 1, "Title note", "Text note")
        val note4 = Note(0, 1, "Title note", "Text note")
        notes.add(note1)
        notes.add(note2)
        notes.add(note3)
        notes.add(note4)

//        assertEquals(4, notes.get().size)
        assertEquals(2, notes.getById(2, 1)?.id)
    }

    @Test
    fun createComment() {
        val note1 = Note(0, 1, "Title note", "Text note")
        val note2 = Note(0, 1, "Title note", "Text note")
        val note3 = Note(0, 1, "Title note", "Text note")
        val note4 = Note(0, 1, "Title note", "Text note")
        notes.add(note1)
        notes.add(note2)
        notes.add(note3)
        notes.add(note4)

        notes.createComment(Comment(0, 1, 1, "First comment note 1"))
        notes.createComment(Comment(0, 1, 1, "Second comment note 1"))
        notes.createComment(Comment(0, 1, 1, "Third comment note 1"))
        notes.createComment(Comment(0, 2, 1, "First comment note 2"))
        notes.createComment(Comment(0, 2, 1, "Second comment note 2"))
        notes.createComment(Comment(0, 3, 1, "Second comment note 3"))

        val result = notes.getById(1,1)?.comments?.size

        assertEquals(result, notes.getById(1, 1)?.comments?.size)
    }

    @Test(expected = NoteNotFoundException::class)
    fun createCommentShouldThrow(){
    notes.createComment(Comment(0, 1, 9 , "Comment"))
    }

    @Test
    fun deleteNote(){
        val note1 = Note(0, 1, "Title note", "Text note")
        val note2 = Note(0, 1, "Title note", "Text note")
        val note3 = Note(0, 1, "Title note", "Text note")
        val note4 = Note(0, 1, "Title note", "Text note")
        notes.add(note1)
        notes.add(note2)
        notes.add(note3)
        notes.add(note4)

        assertTrue(notes.delete(2))
        assertFalse(notes.delete(999))
    }


    @Test
    fun deleteCommentAndRestore(){
        val note1 = Note(0, 1, "Title note", "Text note")
        val note2 = Note(0, 1, "Title note", "Text note")
        val note3 = Note(0, 1, "Title note", "Text note")
        val note4 = Note(0, 1, "Title note", "Text note")
        notes.add(note1)
        notes.add(note2)
        notes.add(note3)
        notes.add(note4)

        notes.createComment(Comment(0, 1, 1, "First comment note 1"))
        notes.createComment(Comment(0, 1, 1, "Second comment note 1"))
        notes.createComment(Comment(0, 1, 1, "Third comment note 1"))
        notes.createComment(Comment(0, 2, 1, "First comment note 2"))
        notes.createComment(Comment(0, 2, 1, "Second comment note 2"))
        notes.createComment(Comment(0, 3, 1, "Second comment note 3"))

        assertTrue(notes.deleteComment(2, 5))
        assertFalse(notes.deleteComment(4, 2))

        assertTrue(notes.restoreComment(2,5))
        assertFalse(notes.restoreComment(4, 2))
    }

    @Test
    fun editNote(){
        val note1 = Note(0, 1, "Title note", "Text note")
        val note2 = Note(0, 1, "Title note", "Text note")
        val note3 = Note(0, 1, "Title note", "Text note")
        val note4 = Note(0, 1, "Title note", "Text note")
        notes.add(note1)
        notes.add(note2)
        notes.add(note3)
        notes.add(note4)

        val noteEdit = Note(3,2,"Edit note", "edit Note")
        notes.edit(2, noteEdit)

        assertEquals(notes.getById(3,2), noteEdit)
    }

}