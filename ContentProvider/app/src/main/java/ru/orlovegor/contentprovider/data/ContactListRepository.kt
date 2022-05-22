package ru.orlovegor.contentprovider.data

import android.content.ContentProviderOperation
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import ru.orlovegor.contentprovider.utils.WrongEmailExceptions

class ContactListRepository(
    private val context: Context
) {

     fun saveContact(name: String, lastName: String, phone: String, email: String?)  {
        if (email == null)
            throw WrongEmailExceptions()
        val contactId = saveRawContact()
        saveContactName(contactId, name,lastName)
        saveContactPhone(contactId, phone)
        saveContactEmail(contactId,email)

    }

    private fun saveRawContact(): Long {
        val uri = context.contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, ContentValues())
        return uri?.lastPathSegment?.toLongOrNull() ?: error("cannot save raw contact")
    }

    private fun saveContactName(contactId: Long, name: String, lastName: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
            put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name)
            put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, lastName)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    private fun saveContactPhone(contactId: Long, phone: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
            put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    private fun saveContactEmail(contactId: Long, email: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
            put(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    fun getAllContacts(): List<Contact> =
        // получаем список контактов
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.use {
            getContactsFromCursor(it)
        }.orEmpty()


    fun deleteContact(rawId: Long) {
        val ops: ArrayList<ContentProviderOperation> = ArrayList()
        val uri = ContactsContract.RawContacts.CONTENT_URI
            .buildUpon()
            .appendQueryParameter(
                ContactsContract.CALLER_IS_SYNCADAPTER,
                "true"
            )
            .build()
        ops.add(
            ContentProviderOperation
                .newDelete(uri)
                .withSelection(
                    ContactsContract.RawContacts._ID + " = ?",
                    arrayOf(rawId.toString())
                )
                .build()
        )
        context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
    }

    private fun getContactsFromCursor(cursor: Cursor): List<Contact> { // забираем список контактов у курсора
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<Contact>()
        do { // из каждой строки получаеминфо по контакту
            val nameIndex =
                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY) //получаем индексы
            val name = cursor.getString(nameIndex).orEmpty() //получаем имя
            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID) //айди
            val id = cursor.getLong(idIndex) // айди контакта
            list.add(
                Contact(
                    id = id,
                    name = name,
                    phones = getPhonesForContact(id),
                    email = getMailAddressForContact(id)
                )
            )
        } while (cursor.moveToNext())
        return list
    }

    private fun getPhonesForContact(contactId: Long): List<String> {
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null
        )?.use {
            getPhonesFromCursor(it)
        }.orEmpty()
    }

    private fun getPhonesFromCursor(cursor: Cursor): List<String> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<String>()
        do {
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val number = cursor.getString(numberIndex)
            list.add(number)
        } while (cursor.moveToNext())
        return list
    }

    private fun getMailAddressForContact(contactId: Long): List<String> {
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null
        )?.use {
            getMailAddressFromCursor(it)
        }.orEmpty()
    }

    private fun getMailAddressFromCursor(cursor: Cursor): List<String> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<String>()
        do {
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
            val number = cursor.getString(numberIndex)
            list.add(number)
        } while (cursor.moveToNext())
        return list
    }
}