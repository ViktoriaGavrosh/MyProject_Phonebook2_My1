package allContact

import java.time.LocalDateTime

abstract class Contact {
    internal var name: String = ""
    internal var phoneNumber: String = ""        //"[no number]"
        set(value) {
            field = checkNumber(value)
        }

    internal var timeCreated = LocalDateTime.now().toString().substring(0, 16)

    internal var timeEdit = LocalDateTime.now().toString().substring(0, 16)
        set(value) {
            field = if (value.length > 15) value.substring(0, 16) else value
        }

    internal var isDeserialize = false       //delete

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
        timeEdit = LocalDateTime.now().toString()       //delete
        printInfo()
    }

    internal open fun serializeString(): String {             //delete
        val res = "{\"name\": \"$name\", \"phoneNumber\": \"$phoneNumber\", "
        return "$res\"timeCreated\": \"$timeCreated\", \"timeEdit\": \"$timeEdit\", "
    }

    internal fun deserialize(listFields: List<String>) {        //delete
        isDeserialize = true
        for (i in listFields) {
            val field = i.substring(1,i.lastIndex).split("\": \"")
            changeField(field[0], field[1])
        }
        isDeserialize = false
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
        val listNum = number.split(Regex("[ \\-]"))
        val regex1 = Regex("\\+?\\(.+\\)")     //it works, but +(23) - true!
        var countBrackets = 0
        try {
            //if (!Regex("\\d+").containsMatchIn(number)) throw Exception()   //поменять!!!
            for (i in listNum.indices) {
                val regex2 = if (i == 0) Regex("\\+?\\(\\w+\\)|\\+?\\w+") else Regex("\\(\\w{2,}\\)|\\w{2,}")
                if (regex1.matches(listNum[i])) {
                    countBrackets++
                    if (i > 1 || countBrackets > 1) throw Exception()
                }
                if (!regex2.matches(listNum[i])) throw Exception()
            }
        } catch (e: Exception) {
            if (!isDeserialize) println("Wrong number format!")
            return phoneNumber
        }
        return number
    }
}