package allContact

import java.time.LocalDateTime

class ContactOrg : Contact() {
    private var address: String = ""

    override fun initContact() {
        println("Enter the organization name:")
        name = readln()
        println("Enter the address:")
        address = readln()
        println("Enter the number:")
        phoneNumber = readln()
    }

    override fun printInfo() {
        println("Organization name: $name\nAddress: $address")
        println("Number: $phoneNumber\nTime created: $timeCreated\nTime last edit: $timeEdit")
    }

    override fun changeField(field: String, value: String) {
        super.changeField(field, value)
        if (field == "address") address = value
    }

    override fun showFieldsToChange() = "name, address, number"

    override fun checkAllFields(regex: Regex) = when {
        name.lowercase().contains(regex) -> true
        address.lowercase().contains(regex) -> true
        phoneNumber.lowercase().contains(regex) -> true
        else -> false
    }

    override fun serializeString(): String {       //delete
        val res = super.serializeString()
        return "$res\"address\": \"$address\"}"
    }

    override fun toString() = name

}