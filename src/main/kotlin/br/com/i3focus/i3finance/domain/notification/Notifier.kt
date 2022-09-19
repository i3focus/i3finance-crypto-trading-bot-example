package br.com.i3focus.i3finance.domain.notification

import br.com.i3focus.i3finance.domain.notification.message.Message

interface Notifier {
    fun notify(message: Message)
}
