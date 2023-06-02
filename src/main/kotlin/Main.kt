import allContact.Contact
import allContact.ContactOrg
import allContact.ContactPerson
import com.squareup.moshi.*
import java.io.File


fun main(args: Array<String>) {
    val wayToFile = try {
        args.first()
    } catch (e: Exception) {
        "C:\\a\\phonebook.txt"
    }
    val file = File(wayToFile)
    println("open ${file.name}\n")
    val phoneBook = if (file.exists()) read(file) else PhoneBook(file)    //read() - delete
    phoneBook.showMenu()
}

internal fun save(file: File, book: MutableList<Contact>) {   //delete
    val bookString = bookToString(book)
    file.writeText(bookString)
}

internal fun read(file: File): PhoneBook {       //delete
    val bookString = file.readText()
    return toPhoneBook(bookString, file)
}

private fun bookToString(book: MutableList<Contact>): String {       //delete
    var bookString= "{\"book\": ["
    for (i in book.indices) {
        bookString += book[i].serializeString()
        if (i != book.lastIndex) bookString += ", "
    }
    return "$bookString]}"
}

private fun toPhoneBook(text: String, file: File): PhoneBook {     //delete
    val book = mutableListOf<Contact>()
    val listText = text.split(Regex(" \\["))
    if (listText[1].length < 3) return PhoneBook(file)
    val contacts = listText[1].substring(0, listText[1].lastIndex - 1).split("}, {").toMutableList()
    for (i in contacts) {
        val cont = i.replace(Regex("[{}]"), "").split(", ")
        val j = if (cont.size > 5) ContactPerson() else ContactOrg()
        j.deserialize(cont)
        book.add(j)
    }
    val phoneBook = PhoneBook(file)
    phoneBook.book = book
    return phoneBook
}