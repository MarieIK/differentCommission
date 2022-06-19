import org.junit.Test

import org.junit.Assert.*

class MainKtTest {

    @Test
    fun commissionMasterCard() {
        val typeCard = "Mastercard"
        val amountTransfer = 50_000
        val transactionCurrentMonth = 15_000_000

        val result = calculateTotalCommission(typeCard, amountTransfer, transactionCurrentMonth)

        assertEquals("Комиссия составит: 2300 коп.", result)
    }

    @Test
    fun commissionMaestro() {
        val typeCard = "Maestro"
        val amountTransfer = 11_500_000
        val transactionCurrentMonth = 5_000_000

        val result = calculateTotalCommission(typeCard, amountTransfer, transactionCurrentMonth)

        assertEquals("Комиссия составит 0 коп.", result)
    }

    @Test
    fun commissionCardIfExceedLimitDay() {
        val maestro = "Maestro"
        val visa = "Visa"
        val amountTransfer = 60_500_000
        val transactionCurrentMonth = 5_000_000

        val resultMaestro = calculateTotalCommission(maestro, amountTransfer, transactionCurrentMonth)
        val resultVisa = calculateTotalCommission(visa, amountTransfer, transactionCurrentMonth)

        assertEquals("Перевод невозможен, превышен суточный лимит: 150 000 руб.", resultMaestro)
        assertEquals("Перевод невозможен, превышен суточный лимит: 150 000 руб.", resultVisa)
    }


    @Test
    fun commissionCardIfExceedLimitMonth() {
        val visa = "Visa"
        val mastercard = "Mastercard"
        val amountTransfer = 8_500_000
        val transactionCurrentMonth = 100_000_000

        val resultVisa = calculateTotalCommission(visa, amountTransfer, transactionCurrentMonth)
        val resultMastercard = calculateTotalCommission(mastercard, amountTransfer, transactionCurrentMonth)

        assertEquals("Перевод невозможен, превышен месячный лимит: 600 000 руб.", resultVisa)
        assertEquals("Перевод невозможен, превышен месячный лимит: 600 000 руб.", resultMastercard)
    }

    @Test
    fun commissionMir() {
        val typeCard = "Мир"
        val amountTransfer = 10_500_000
        val transactionCurrentMonth = 50_000_000

        val result = calculateTotalCommission(typeCard, amountTransfer, transactionCurrentMonth)

        assertEquals("Комиссия составит 78750 коп.", result)
    }

    @Test
    fun minCommissionVisaAndMir() {
        val typeCard = "Мир"
        val amountTransfer = 3_500
        val transactionCurrentMonth = 50_000_000

        val result = calculateTotalCommission(typeCard, amountTransfer, transactionCurrentMonth)

        assertEquals("Комиссия составит 3500 коп.", result)
    }

    @Test
    fun commissionVkPay() {
        val typeCard = "VK Pay"
        val amountTransfer = 1_500_000
        val transactionCurrentMonth = 4_500_000

        val result = calculateTotalCommission(typeCard, amountTransfer, transactionCurrentMonth)

        assertEquals("Перевод невозможен, превышен месячный лимит: 40 000 руб.", result)
    }

    @Test
    fun commissionVkPayEqualsNull() {
        val typeCard = "VK Pay"
        val amountTransfer = 500_000
        val transactionCurrentMonth = 10_000

        val result = calculateTotalCommission(typeCard, amountTransfer, transactionCurrentMonth)

        assertEquals("Комиссия составит 0 коп.", result)
    }

    @Test
    fun invalidInput() {
        val typeCard = " "
        val amountTransfer = 15_000_000
        val transactionCurrentMonth = 45_000

        val result = calculateTotalCommission(typeCard, amountTransfer, transactionCurrentMonth)

        assertEquals("Неверные данн", result)
    }
}