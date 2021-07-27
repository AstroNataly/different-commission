import kotlin.math.round

fun calculationAlgorithm (
    currentTransferAmount: Int,
    accountCardType: String = "Vk Pay",
    previousAmountOfTransfersThisMonth: Int = 0
): Int {

    val startCommissionAmount = round(((currentTransferAmount * cardCommissionSize(accountCardType,
        currentTransferAmount))/100)).toInt()

    val commissionAmount = when (accountCardType) {
        "Mastercard", "Maestro" -> if (currentTransferAmount < 7_500_000) startCommissionAmount
            else startCommissionAmount + 2_000
        "Visa", "Мир" -> if (currentTransferAmount >= (100 * 3_500 / 0.75)) startCommissionAmount
            else 3_500
        else -> {
            startCommissionAmount
        }
    }

    return when {
        isNoLimitsCards(currentTransferAmount, previousAmountOfTransfersThisMonth) -> commissionAmount
        isNoLimitsVkPay(currentTransferAmount, previousAmountOfTransfersThisMonth) -> commissionAmount
         else -> {
             0
         }
    }
 }

fun cardCommissionSize(accountCardType: String, currentTransferAmount: Int): Double {
    return when (accountCardType) {
        "Mastercard", "Maestro" -> if (currentTransferAmount < 75_000_00) 0.00 else 0.6
        "Visa", "Мир" -> 0.75
        else -> {
            0.0
        }
    }
}

fun isNoLimitsVkPay(currentTransferAmount: Int, previousAmountOfTransfersThisMonth: Int): Boolean {
    return when {
            (currentTransferAmount < 15_000_00 || previousAmountOfTransfersThisMonth < 40_000_000) -> true
        else -> false
    }
}

fun isNoLimitsCards(currentTransferAmount: Int, previousAmountOfTransfersThisMonth: Int): Boolean {
    return when {
        (currentTransferAmount > 150_000_00 || previousAmountOfTransfersThisMonth > 600_000_00) -> true
        else -> false
    }
}

fun main() {
    val currentTransferAmount = 15_000_00

    println("Коммиссия равна ${calculationAlgorithm(currentTransferAmount) / 100} руб." +
                "${calculationAlgorithm(currentTransferAmount) % 100} коп.")
}

