import kotlin.math.round

fun calculationAlgorithm (
    accountCardType: String = "VK Pay",
    previousAmountOfTransfersThisMonth: Int = 0,
    currentTransferAmount: Int
): Int {

    val startCommissionAmount = round(((currentTransferAmount * cardCommissionSize(accountCardType,
        currentTransferAmount))/100)).toInt()
    return when (accountCardType) {
        "Mastercard", "Maestro" -> if (currentTransferAmount < 7_500_000) startCommissionAmount
            else startCommissionAmount + 2_000
        "Visa", "Мир" -> if (currentTransferAmount >= (100 * 3_500 / 0.75)) startCommissionAmount
            else 3_500
        else -> {
            startCommissionAmount
        }
    }

    //return currentTransferAmount * cardCommissionSize(accountCardType, currentTransferAmount).toInt()


}


fun isNoLimitsVkPay(currentTransferAmount: Int, previousAmountOfTransfersThisMonth: Int): Boolean {
    return when {
            (currentTransferAmount < 1_500_000 && previousAmountOfTransfersThisMonth < 4_000_000) -> true
        else -> false

    }

}

fun isNoLimitsCards(currentTransferAmount: Int, previousAmountOfTransfersThisMonth: Int): Boolean {
    return when {
        (currentTransferAmount > 15_000_000 || previousAmountOfTransfersThisMonth > 60_000_000) -> true
        else -> false
    }

}

fun cardCommissionSize(accountCardType: String, currentTransferAmount: Int): Double {
    return when (accountCardType) {
        "Mastercard", "Maestro" -> if (currentTransferAmount < 7_500_000) 0.00 else 0.6
        "Visa", "Мир" -> 0.75
        else -> {
            0.0
        }
    }
}


fun convertToRub(currentTransferAmount: Int): Int {
    return round(currentTransferAmount / 100.00).toInt()

}

fun main() {
    val accountCardType = "Visa"
    val previousAmountOfTransfersThisMonth = 0
    val currentTransferAmount = 467000
    val d = println("Коммиссия за перевод ${convertToRub(currentTransferAmount)} руб. ${currentTransferAmount % 100} коп. " +
            "равна ${calculationAlgorithm(accountCardType, previousAmountOfTransfersThisMonth, currentTransferAmount)
                    / 100} руб. ${calculationAlgorithm(accountCardType, previousAmountOfTransfersThisMonth,
                currentTransferAmount) % 100} коп.")


    when  {
        isNoLimitsCards(currentTransferAmount, previousAmountOfTransfersThisMonth) -> d
        isNoLimitsVkPay(currentTransferAmount, previousAmountOfTransfersThisMonth) -> d


    }

}

