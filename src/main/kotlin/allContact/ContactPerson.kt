package allContact

class ContactPerson : Contact() {
    override val contactType = "person"
    private var surname: String = ""

    private var birthDate = "[no data]"
        set(value) {
            field = checkBirthday(value)
        }

    private var gender = "[no data]"
        set(value) {
            field = checkGender(value)
        }

    override fun initContact() {
        println("Enter the name:")
        name = readln()
        println("Enter the surname:")
        surname = readln()
        println("Enter the birth date:")
        birthDate = readln()
        println("Enter the gender (M, F):")
        gender = readln()
        println("Enter the number:")
        phoneNumber = readln()
    }

    override fun printInfo() {
        println("Name: $name\nSurname: $surname\nBirth date: $birthDate\nGender: $gender")
        println("Number: $phoneNumber\nTime created: $timeCreated\nTime last edit: $timeEdit")
    }

    override fun changeField(field: String, value: String) {
        super.changeField(field, value)
        when (field) {
            "surname" -> surname = value
            "birth" -> birthDate = value
            "gender" -> gender = value
        }
    }

    override fun showFieldsToChange() = "name, surname, birth, gender, number"

    override fun checkAllFields(regex: Regex)= when {
        name.lowercase().contains(regex) -> true
        surname.lowercase().contains(regex) -> true
        phoneNumber.lowercase().contains(regex) -> true
        else -> false
    }

    private fun checkBirthday(stringDate: String): String {
        if (stringDate == "[no data]") return stringDate
        return try {
            kotlinx.datetime.LocalDate.parse(stringDate)
            stringDate
        } catch (e: Exception){
            println("Bad birth date!")
            "[no data]"
        }
    }

    private fun checkGender(gender: String): String {
        if (gender == "[no data]") return gender
        return if (gender != "M" && gender != "F") {
            println("Bad gender!")
            "[no data]"
        } else gender
    }

    override fun toString() = "$name $surname"
}