import allContact.Contact
import allContact.ContactOrg
import allContact.ContactPerson

class PhoneBook {
    private var book = mutableListOf<Contact>()

    internal fun showMenu() {
        while (true) {
            println("[menu] Enter action (add, list, search, count, exit):")
            when(readln()) {
                "add" -> addContact()
                "list" -> showBook()
                "search" -> searchStart()
                "count" -> println("The Phone Book has ${book.size} records.")
                "exit" -> break
            }
            println("")
        }
    }

    private fun addContact() {
        println("Enter the type (person, organization):")
        val contact = if (readln() == "person") ContactPerson() else ContactOrg()
        contact.initContact()
        book.add(0, contact)
        println("The record added.")
    }

    private fun showChangeMenu(index: Int) {
        while (true) {
            println("\n[record] Enter action (edit, delete, menu):")
            when (readln()) {
                "edit" -> book[index].editContact()
                "delete" -> deleteContact(index)
                "menu" -> return
            }
        }
    }

    private fun searchStart() {
        while (true) {
            println("Enter search query:")
            val text = readln()
            val listFindContacts = searchInBook(Regex(text.lowercase()))
            println("Found ${listFindContacts.size} results:")
            for (i in listFindContacts.indices) {
                try {
                    text.toInt()
                    println("${i + 1}. ${listFindContacts[i].phoneNumber}")
                } catch (e: Exception) {
                    println("${i + 1}. ${listFindContacts[i]}")
                }
            }
            println("\n[search] Enter action ([number], back, again):")
            when(val input = readln()) {
                "back" -> break
                "again" -> continue
                else -> {
                    listFindContacts[input.toInt() - 1].printInfo()
                    showChangeMenu(input.toInt() - 1)
                    break
                }
            }
        }
    }

    private fun searchInBook(regex: Regex): List<Contact> {
        val list = mutableListOf<Contact>()
        for (i in book) if (i.checkAllFields(regex)) list.add(0, i)
        return list
    }

    private fun deleteContact(index: Int) {
        if (book.size == 0) {
            println("No records to remove!")
            return
        }
        book.removeAt(index)
        println("The record removed!")
    }

    private fun showBook() {
        for (i in book.indices) println("${i + 1}. ${book[i]}")
        println("\n[list] Enter action ([number], back):")
        val input = readln()
        if (input == "back") return else book[input.toInt() - 1].printInfo()
        showChangeMenu(input.toInt() - 1)
    }
}