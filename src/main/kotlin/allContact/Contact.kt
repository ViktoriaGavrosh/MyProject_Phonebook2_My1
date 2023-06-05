package allContact

import kotlinx.datetime.*

abstract class Contact {
    abstract val contactType: String
    internal var name: String = ""
    internal var phoneNumber: String = ""
        set(value) {
            field = checkNumber(value)
        }

    internal var timeCreated = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+3")).toString().substring(0, 16)

    internal var timeEdit = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+3")).toString().substring(0, 16)
        set(value) {
            field = if (value.length > 15) value.substring(0, 16) else value
        }

    abstract fun printInfo()

    abstract fun initContact()

    abstract fun showFieldsToChange(): String

    abstract fun checkAllFields(regex: Regex): Boolean

    internal fun editContact() {
        println("Select a field (${showFieldsToChange()}):")
        val field = readln()
        println("Enter $field:")
        changeField(field, readln())
        println("Saved")
        timeEdit = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+3")).toString()
        printInfo()
    }

    internal open fun changeField(field: String, value: String) {
        when (field) {
            "name" -> name = value
            "number", "phoneNumber" -> phoneNumber = value
            "timeCreated" -> timeCreated = value
            "timeEdit" -> timeEdit = value
        }
    }

    private fun checkNumber(number: String): String {
        if (number == "[no data]") return number
        val listNum = number.split(Regex("[ \\-]"))
        val regex1 = Regex("\\+?\\(.+\\)")     //it works, but +(23) - true!
        var countBrackets = 0
        try {
            for (i in listNum.indices) {
                val regex2 = if (i == 0) Regex("\\+?\\(\\w+\\)|\\+?\\w+") else Regex("\\(\\w{2,}\\)|\\w{2,}")
                if (regex1.matches(listNum[i])) {
                    countBrackets++
                    if (i > 1 || countBrackets > 1) throw Exception()
                }
                if (!regex2.matches(listNum[i])) throw Exception()
            }
        } catch (e: Exception) {
            println("Wrong number format!")
            return phoneNumber
        }
        return number
    }
}