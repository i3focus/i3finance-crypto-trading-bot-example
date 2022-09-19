package br.com.i3focus.i3finance

import br.com.i3focus.i3finance.domain.I3financeSimpleStrategy
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import tech.cassandre.trading.bot.dto.position.PositionStatusDTO
import tech.cassandre.trading.bot.dto.util.CurrencyDTO
import tech.cassandre.trading.bot.dto.util.CurrencyDTO.USDT
import tech.cassandre.trading.bot.dto.util.GainDTO
import tech.cassandre.trading.bot.test.mock.TickerFluxMock

@SpringBootTest
@Import(TickerFluxMock::class)
@ActiveProfiles("test")
@DisplayName("I3finance - Cassandre Trading Bot Example Test")
class CassandreTradingBotExampleApplicationTests(
    @Autowired private val strategy: I3financeSimpleStrategy,
    @Autowired private val tickerFluxMock: TickerFluxMock
) {
    private val logger: Logger = LoggerFactory.getLogger(CassandreTradingBotExampleApplicationTests::class.java)

    @Test
    @DisplayName("GivenOnTickerUpdateEvent_whenTickersArrives_thenCheckGains")
    fun `givenOnTickerUpdateEvent whenTickersArrives thenCheckGains`() {
        await().forever().until { tickerFluxMock.isFluxDone }
        val gains: MutableMap<CurrencyDTO, GainDTO> = strategy.gains

        logger.info("Cumulated gains: ")
        gains.forEach { (currency, gain) -> logger.info(currency.toString() + " : " + gain.amount) }

        logger.info("Position still opened: ")
        strategy.positions.values
            .filter { position -> position.status == PositionStatusDTO.OPENED }
            .forEach { position -> logger.info(" - {} " + position.description) }

        assertTrue(gains[USDT]!!.percentage > 0)
    }
}
