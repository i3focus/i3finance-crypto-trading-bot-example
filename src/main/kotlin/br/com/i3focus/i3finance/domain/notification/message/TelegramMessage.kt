package br.com.i3focus.i3finance.domain.notification.message

data class TelegramMessage(val body: String) : Message {
    override fun getTitle(): String = "I3Finance Trading Bot"

    override fun getMessage(): String = body
}
