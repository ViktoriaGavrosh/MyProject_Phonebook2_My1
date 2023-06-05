import allContact.Contact
import allContact.ContactOrg
import allContact.ContactPerson
import com.squareup.moshi.*
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File


fun main(args: Array<String>) {
    val wayToFile = try {
        args.first()
    } catch (e: Exception) {
        "C:\\a\\phonebook.txt"
    }
    val file = File(wayToFile)
    println("open ${file.name}\n")
    val phoneBookAdapter = createAdapter()
    val phoneBook = if (file.exists()) phoneBookAdapter.fromJson(file.readText()) else PhoneBook()
    phoneBook?.showMenu()
    file.writeText(phoneBookAdapter.toJson(phoneBook))
}

private fun createAdapter(): JsonAdapter<PhoneBook>{
    val adapterFactory = PolymorphicJsonAdapterFactory.of(Contact::class.java,"contactType")
        .withSubtype(ContactPerson::class.java,"person")
        .withSubtype(ContactOrg::class.java,"organization")
    val moshi = Moshi.Builder().add(adapterFactory).addLast(KotlinJsonAdapterFactory()).build()
    return moshi.adapter(PhoneBook::class.java)
}