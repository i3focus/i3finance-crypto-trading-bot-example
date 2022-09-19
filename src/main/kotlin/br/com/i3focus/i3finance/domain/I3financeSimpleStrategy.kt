package br.com.i3focus.i3finance.domain

import br.com.i3focus.i3finance.domain.notification.Notifier
import br.com.i3focus.i3finance.domain.notification.message.TelegramMessage
import tech.cassandre.trading.bot.dto.market.TickerDTO
import tech.cassandre.trading.bot.dto.position.PositionDTO
import tech.cassandre.trading.bot.dto.position.PositionRulesDTO
import tech.cassandre.trading.bot.dto.position.PositionStatusDTO.CLOSED
import tech.cassandre.trading.bot.dto.position.PositionStatusDTO.OPENED
import tech.cassandre.trading.bot.dto.user.AccountDTO
import tech.cassandre.trading.bot.dto.util.CurrencyDTO.BTC
import tech.cassandre.trading.bot.dto.util.CurrencyDTO.USDT
import tech.cassandre.trading.bot.dto.util.CurrencyPairDTO
import tech.cassandre.trading.bot.strategy.BasicCassandreStrategy
import tech.cassandre.trading.bot.strategy.CassandreStrategy
import java.math.BigDecimal
import java.util.*

private const val PRICE_GOOD_TO_BUY = "56000"
private const val AMOUNT_TO_BUY = "0.01"
private const val STOP_GAIN_PERCENTAGE = 4f
private const val STOP_LOSS_PERCENTAGE = 25f

@CassandreStrategy
class I3financeSimpleStrategy(
    private val notifier: Notifier
) : BasicCassandreStrategy() {
    override fun getRequestedCurrencyPairs(): Set<CurrencyPairDTO> =
        setOf(CurrencyPairDTO(BTC, USDT))

    override fun getTradeAccount(accounts: Set<AccountDTO>): Optional<AccountDTO> =
        accounts
            .first { account -> "trade" == account.name }
            .let { Optional.of(it) }

    override fun onAccountsUpdates(accounts: MutableMap<String, AccountDTO>) =
        accounts.values.forEach {
            logger.info("Received information about an account: {}", it)
            notifier.notify(TelegramMessage("Received information about an account: %s".format(it)))
        }

    override fun onTickersUpdates(tickers: MutableMap<CurrencyPairDTO, TickerDTO>) =
        tickers.values.forEach { ticker ->
            logger.info("Received a new ticker : {}", ticker)

            val priceGoodToBuy = BigDecimal(PRICE_GOOD_TO_BUY)
            val amountToBuy = BigDecimal(AMOUNT_TO_BUY)

            if (priceGoodToBuy == ticker.last &&
                canBuy(CurrencyPairDTO(BTC, USDT), amountToBuy)
            ) {

                PositionRulesDTO.builder()
                    .stopGainPercentage(STOP_GAIN_PERCENTAGE)
                    .stopLossPercentage(STOP_LOSS_PERCENTAGE)
                    .build().let { rules ->
                        createLongPosition(
                            CurrencyPairDTO(BTC, USDT),
                            BigDecimal(AMOUNT_TO_BUY),
                            rules
                        )

                        notifier.notify(TelegramMessage("Creating new position: %s".format(rules)))
                    }
            }
        }

    override fun onPositionsUpdates(positions: Map<Long, PositionDTO>) =
        positions.values.forEach {
            logger.info("Received information about a position: {}", it)
            notifier.notify(TelegramMessage("Received information about a position: %s".format(it)))
        }

    override fun onPositionsStatusUpdates(positions: Map<Long, PositionDTO>) =
        positions.values.forEach {
            if (it.status == OPENED) {
                logger.info(">>> New position opened: {}", it)
                notifier.notify(TelegramMessage("New position opened: %s".format(it)))
            }

            if (it.status == CLOSED) {
                logger.info(">>> Position closed: {}", it)
                notifier.notify(TelegramMessage("Position closed: %s".format(it)))
            }
        }
}
