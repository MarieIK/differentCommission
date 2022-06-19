const val MASTERCARD = "Mastercard"
const val MAESTRO = "Maestro"
const val VISA = "Visa"
const val MIR = "Мир"
const val VK_PAY = "VK Pay"
const val MIN_COMMISSION_VISA_MIR = 3_500
const val COMMISSION_VISA_MIR = 0.0075
const val LIMIT_DAY_CARD = 15_000_000
const val LIMIT_MONTH_CARD = 60_000_000
const val LIMIT_DAY_VK_PAY = 1_500_000
const val LIMIT_MONTH_VK_PAY = 4_000_000
const val LIMIT_MASTERCARD_MAESTRO_MONTH = 7_500_000

fun main() {
    println(calculateTotalCommission(MASTERCARD, 50_000, 15_000_000))
    println(calculateTotalCommission(MAESTRO, 60_500_000, 5_000_000))
    println(calculateTotalCommission(VISA, 8_500_000, 100_000_000))
    println(calculateTotalCommission(MIR, 10_500_000, 50_000_000))
    println(calculateTotalCommission(VK_PAY, 15_000_000, 45_000))
    println(calculateTotalCommission(VK_PAY, 500_000, 10_000))
}

fun calculateTotalCommission(typeCard: String, amountTransfer: Int, transactionCurrentMonth: Int): String {
    val total = when (typeCard) {
        MASTERCARD -> calculateCommissionMastercardAndMaestro(amountTransfer, transactionCurrentMonth)
        MAESTRO -> calculateCommissionMastercardAndMaestro(amountTransfer, transactionCurrentMonth)
        VISA -> calculateCommissionVisaAndMir(amountTransfer, transactionCurrentMonth)
        MIR -> calculateCommissionVisaAndMir(amountTransfer, transactionCurrentMonth)
        VK_PAY -> calculateCommissionVkPay(amountTransfer, transactionCurrentMonth)
        else -> {"Неверные данные"}
    }
    return total
}

fun calculateCommissionMastercardAndMaestro(amountTransfer: Int, transactionCurrentMonth: Int): String {
    val commission = (amountTransfer * 0.006 + 2_000).toInt()
    val total = when {
        (transactionCurrentMonth >= LIMIT_MONTH_CARD) -> "Перевод невозможен, превышен месячный лимит: 600 000 руб."
        (amountTransfer >= LIMIT_DAY_CARD) -> "Перевод невозможен, превышен суточный лимит: 150 000 руб."
        (transactionCurrentMonth < LIMIT_MASTERCARD_MAESTRO_MONTH) -> "Комиссия составит 0 коп."
        else -> "Комиссия составит: $commission коп."
    }
    return total
}

fun calculateCommissionVisaAndMir(amountTransfer: Int, transactionCurrentMonth: Int): String {
    val commission = (amountTransfer * COMMISSION_VISA_MIR).toInt()
    val total = when {
        (transactionCurrentMonth >= LIMIT_MONTH_CARD) -> "Перевод невозможен, превышен месячный лимит: 600 000 руб."
        (amountTransfer >= LIMIT_DAY_CARD) -> "Перевод невозможен, превышен суточный лимит: 150 000 руб."
        (commission <= MIN_COMMISSION_VISA_MIR) -> "Комиссия составит $MIN_COMMISSION_VISA_MIR коп."
        else -> "Комиссия составит $commission коп."
    }
    return total
}

fun calculateCommissionVkPay(amountTransfer: Int, transactionCurrentMonth: Int): String {
    val total = when {
        (transactionCurrentMonth >= LIMIT_MONTH_VK_PAY) -> "Перевод невозможен, превышен месячный лимит: 40 000 руб."
        (amountTransfer >= LIMIT_DAY_VK_PAY) -> "Перевод невозможен, превышен суточный лимит: 15 000 руб."
        else -> "Комиссия составит 0 коп."
    }
    return total
}