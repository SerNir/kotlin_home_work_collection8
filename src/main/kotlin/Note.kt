data class Note(
    var id: Int,
    val ownerId: Int,
    val title: String,
    val text: String,
    val comments: MutableList<Comment> = mutableListOf(),
    var isDeleted: Boolean = false
) {
    override fun toString(): String {
        return "Заметка №$id, пользователя: $ownerId, заголовок заметки: $title, текст заметки: $text, комментарии [ ${comments.size} ]"
    }
}

data class Comment(
    var id: Int,
    val noteId: Int,
    val ownerId: Int,
    var message: String,
    var isDeleted: Boolean = false
) {
    override fun toString(): String {
        return "Комментарий $id от пользователя $ownerId текст: $message"
    }
}

object WallService {
    private var countIdNote = 0
    private var countIdComment = 0
    private val notes = mutableListOf<Note>()

    fun add(note: Note) {//Создает новую заметку у текущего пользователя.
        countIdNote++
        note.id = countIdNote
        notes.add(note)
    }

    fun createComment(comment: Comment): Comment {//Добавляет новый комментарий к заметке.
        for (note in notes) {
            if (note.id == comment.noteId) {
                countIdComment++
                comment.id = countIdComment
                note.comments.add(comment)
                return comment
            }
        }
        throw NoteNotFoundException("Заметка не найдена!")
    }

    fun delete(noteId: Int): Boolean {
        for (note in notes) {
            if (note.id == noteId) {
                note.isDeleted = true
                return true
            }
        }
        return false
    }

    fun deleteComment(noteId: Int, commentId: Int):Boolean {//Удаляет комментарий к заметке.
        for (note in notes) {
            if (note.id == noteId) {
                for (comment in note.comments) {
                    if (comment.id == commentId || !comment.isDeleted) {
                        comment.isDeleted = true
                        return true
                    }
                }
            }
        }
        return false
    }

    fun edit(id: Int, note: Note) {//Редактирует заметку текущего пользователя.
        try {
            notes[id] = note
        } catch (e: java.lang.IndexOutOfBoundsException) {
            println("Заметки с id = $id не существует!")
        }
    }

    fun editComment(
        noteId: Int,
        commentId: Int,
        ownerId: Int,
        text: String
    ) {//Редактирует указанный комментарий у заметки.
        for (note in notes) {
            if (note.id == noteId) {
                for (comment in note.comments) {
                    if (comment.id == commentId && comment.ownerId == ownerId) {
                        note.comments[commentId - 1].message = text
                        return
                    }
                }
            }
        }
    }

    //Возвращает список заметок, созданных пользователем.
    fun get(): MutableList<Note> {
        return notes
    }

    fun printAllNote() {
        for (note in notes) {
            if (!note.isDeleted) {
                println(note)
            }
        }
    }

    fun getById(noteId: Int, ownerId: Int): Note? {//Возвращает заметку по её id.
        for (note in notes) {
            if (note.id == noteId && note.ownerId == ownerId) {
                return note
            }
        }
        return null
    }

    fun getComments(noteId: Int) {//Возвращает список комментариев к заметке.
        for (note in notes) {
            if (note.id == noteId) {
                for (comment in note.comments) {
                    if (!comment.isDeleted) {
                        println(comment)
                    }
                }
            }
        }

    }

    fun restoreComment(noteId: Int, commentId: Int): Boolean {//Восстанавливает удалённый комментарий.
        for (note in notes) {
            if (note.id == noteId) {
                val comments = note.comments
                for (comment in comments) {
                    if (comment.id == commentId || comment.isDeleted) {
                        comment.isDeleted = false
                        return true
                    }
                }
            }
        }
        return false
    }
}


class NoteNotFoundException(message: String) : RuntimeException(message)