package br.com.i3focus.i3finance

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class CassandreTradingBotExampleApplication

fun main(args: Array<String>) {
	runApplication<CassandreTradingBotExampleApplication>(*args)
}
