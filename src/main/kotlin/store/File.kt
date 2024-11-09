package store

import java.io.File

class File {
    fun read(path: String): List<String> {
        return File(path).readLines().drop(1)
    }
}