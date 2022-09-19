package br.com.i3focus.i3finance.domain.notification.message

interface Message {
    fun getTitle(): String
    fun getMessage(): String
}
