package br.com.i3focus.i3finance.resource

import br.com.i3focus.i3finance.domain.notification.Notifier
import br.com.i3focus.i3finance.domain.notification.message.Message
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class TelegramNotification : Notifier {
    private val logger: Logger = LoggerFactory.getLogger(TelegramNotification::class.java)

    @Async
    override fun notify(message: Message) {
        logger.info("Message: {} - {}", message.getTitle(), message.getMessage())
    }
}
