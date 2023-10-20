package com.example.whapp.data

data class UserData(
    val userId: String? = "",
    val name: String? = "",
    val number: String? = "",
    val imageUrl: String? = ""
) {
    fun toMap() = mapOf(
        "userId" to userId,
        "name" to name,
        "number" to number,
        "imageUrl" to imageUrl,
    )
}


data class ChatDate(
    val chatId: String? = "",
    val sender: ChatUser = ChatUser(),
    val receiver: ChatUser = ChatUser(),
)

data class ChatUser(
    val userId: String? = "",
    val name: String? = "",
    val imageUrl: String? = "",
    val number: String? = "",
)

data class Message(
    val sentBy: String? = "",
    val message: String? = "",
    val timestamp: String? = ""
)