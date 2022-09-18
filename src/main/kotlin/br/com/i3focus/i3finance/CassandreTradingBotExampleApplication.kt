package br.com.i3focus.i3finance

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CassandreTradingBotExampleApplication

fun main(args: Array<String>) {
	runApplication<CassandreTradingBotExampleApplication>(*args)
}
