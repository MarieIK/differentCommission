const val MASTERCARD = "Mastercard"
const val MAESTRO = "Maestro"
const val VISA = "Visa"
const val MIR = "Мир"
const val VK_PAY = "VK Pay"
const val MIN_COMMISSION_VISA_MIR = 3_500.0
const val COMMISSION_VISA_MIR = 0.0075
const val LIMIT_DAY_CARD = 15_000_000.0
const val LIMIT_MONTH_CARD = 60_000_000.0
const val LIMIT_DAY_VK_PAY = 1_500_000.0
const val LIMIT_MONTH_VK_PAY = 4_000_000.0
const val LIMIT_MASTERCARD_MAESTRO_MONTH = 7_500_000.0

fun main() {
    calculateTotalCommission(MIR, 10_500_000.0, 7_000_000.0)
    calculateTotalCommission(MASTERCARD, 50_000.0, 15_000_000.0)
    calculateTotalCommission(VK_PAY, 15_000_000.0, 45_000.0)
}

fun calculateTotalCommission(typeCard: String = "VK Pay", amountTransfer: Double, transactionCurrentMonth: Double = 0.0) {
    when (typeCard) {
        MASTERCARD -> calculateCommissionMastercardAndMaestro(amountTransfer, transactionCurrentMonth)
        MAESTRO -> calculateCommissionMastercardAndMaestro(amountTransfer, transactionCurrentMonth)
        VISA -> calculateCommissionVisaAndMir(amountTransfer, transactionCurrentMonth)
        MIR -> calculateCommissionVisaAndMir(amountTransfer, transactionCurrentMonth)
        VK_PAY -> calculateCommissionVkPay(amountTransfer, transactionCurrentMonth)
    }
}

fun calculateCommissionMastercardAndMaestro(amountTransfer: Double, transactionCurrentMonth: Double) {
    val commission = amountTransfer * 0.006 + 2_000
    when {
        (transactionCurrentMonth >= LIMIT_MONTH_CARD) -> println("Перевод невозможен, превышен месячный лимит: 600 000 руб.")
        (amountTransfer >= LIMIT_DAY_CARD) -> println("Перевод невозможен, превышен суточный лимит: 150 000 руб.")
        (transactionCurrentMonth < LIMIT_MASTERCARD_MAESTRO_MONTH) -> println("Комиссия составит 0 коп.")
        else -> println("Комиссия составит: $commission коп.")
    }
}

fun calculateCommissionVisaAndMir(amountTransfer: Double, transactionCurrentMonth: Double) {
    val commission = amountTransfer * COMMISSION_VISA_MIR
    when {
        (transactionCurrentMonth >= LIMIT_MONTH_CARD) -> println("Перевод невозможен, превышен месячный лимит: 600 000 руб.")
        (amountTransfer >= LIMIT_DAY_CARD) -> println("Перевод невозможен, превышен суточный лимит: 150 000 руб.")
        (commission <= MIN_COMMISSION_VISA_MIR) -> println("Комиссия составит $MIN_COMMISSION_VISA_MIR коп.")
        else -> println("Комиссия составит $commission коп.")
    }
}

fun calculateCommissionVkPay(amountTransfer: Double, transactionCurrentMonth: Double) {
    when {
        (transactionCurrentMonth >= LIMIT_MONTH_VK_PAY) -> println("Перевод невозможен, превышен месячный лимит: 40 000 руб.")
        (amountTransfer >= LIMIT_DAY_VK_PAY) -> println("Перевод невозможен, превышен суточный лимит: 15 000 руб.")
        else -> println("Комиссия составит 0 коп.")
    }
}