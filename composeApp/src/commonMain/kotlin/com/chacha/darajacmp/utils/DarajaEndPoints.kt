package com.chacha.darajacmp.utils

object DarajaEndPoints {
    // Sandbox URLs (for testing)
    const val SANDBOX_BASE_URL = "https://sandbox.safaricom.co.ke"
    const val SANDBOX_AUTH_URL = "$SANDBOX_BASE_URL/oauth/v1/generate?grant_type=client_credentials"
    const val SANDBOX_STK_PUSH_URL = "$SANDBOX_BASE_URL/mpesa/stkpush/v1/processrequest"
    const val SANDBOX_STK_QUERY_URL = "$SANDBOX_BASE_URL/mpesa/stkpushquery/v1/query"
    const val SANDBOX_C2B_REGISTER_URL = "$SANDBOX_BASE_URL/mpesa/c2b/v1/registerurl"
    const val SANDBOX_C2B_SIMULATE_URL = "$SANDBOX_BASE_URL/mpesa/c2b/v1/simulate"
    const val SANDBOX_B2C = "https://sandbox.safaricom.co.ke/mpesa/b2c/v3/paymentrequest"
    const val SANDBOX_DYNAMIC_QR_CODE = "https://sandbox.safaricom.co.ke/mpesa/qrcode/v1/generate"
    const val SANDBOX_ACCOUNT_BALANCE_URL = "$SANDBOX_BASE_URL/mpesa/accountbalance/v1/query"
    const val SANDBOX_TRANSACTION_STATUS_URL = "$SANDBOX_BASE_URL/mpesa/transactionstatus/v1/query"
    const val SANDBOX_REVERSAL_URL = "$SANDBOX_BASE_URL/mpesa/reversal/v1/request"
    const val SANDBOX_TAX_REMITTANCE = "$SANDBOX_BASE_URL/mpesa/b2b/v1/remittax"
    const val SANDBOX_BUSINESS_PAY_BILL = "https://sandbox.safaricom.co.ke/mpesa/b2b/v1/paymentrequest"
    const val SANDBOX_BUSINESS_BUY_GOODS = "https://sandbox.safaricom.co.ke/mpesa/b2b/v1/paymentrequest"
    const val SANDBOX_BILL_MANAGER = "https://api.safaricom.co.ke/v1/billmanager-invoice/optin"
    const val SANDBOX_B2C_ACCOUNT_TOP_UP = "https://sandbox.safaricom.co.ke/mpesa/b2b/v1/paymentrequest"
    const val SANDBOX_M_RATIBA = "https://sandbox.safaricom.co.ke/standingorder/v1/createStandingOrderExternal"
    const val SANDBOX_B2B_EXPRESS_CHECKOUT = "https://sandbox.safaricom.co.ke/v1/ussdpush/get-msisdn"


    // Production URLs (for live environment)
    const val PRODUCTION_BASE_URL = "https://api.safaricom.co.ke"
    const val PRODUCTION_AUTH_URL = "$PRODUCTION_BASE_URL/oauth/v1/generate?grant_type=client_credentials"
    const val PRODUCTION_STK_PUSH_URL = "$PRODUCTION_BASE_URL/mpesa/stkpush/v1/processrequest"
    const val PRODUCTION_STK_QUERY_URL = "$PRODUCTION_BASE_URL/mpesa/stkpushquery/v1/query"
    const val PRODUCTION_C2B_REGISTER_URL = "$PRODUCTION_BASE_URL/mpesa/c2b/v1/registerurl"
    const val PRODUCTION_C2B_SIMULATE_URL = "$PRODUCTION_BASE_URL/mpesa/c2b/v1/simulate"
    const val PRODUCTION_B2C_URL = "$PRODUCTION_BASE_URL/mpesa/b2c/v1/paymentrequest"
    const val PRODUCTION_ACCOUNT_BALANCE_URL = "$PRODUCTION_BASE_URL/mpesa/accountbalance/v1/query"
    const val PRODUCTION_TRANSACTION_STATUS_URL = "$PRODUCTION_BASE_URL/mpesa/transactionstatus/v1/query"
    const val PRODUCTION_REVERSAL_URL = "$PRODUCTION_BASE_URL/mpesa/reversal/v1/request"

    // Common M-Pesa Command IDs
    object CommandIDs {
        const val CUSTOMER_PAY_BILL_ONLINE = "CustomerPayBillOnline"
        const val CUSTOMER_BUY_GOODS_ONLINE = "CustomerBuyGoodsOnline"
        const val SALARY_PAYMENT = "SalaryPayment"
        const val BUSINESS_PAYMENT = "BusinessPayment"
        const val PROMOTION_PAYMENT = "PromotionPayment"
        const val ACCOUNT_BALANCE = "AccountBalance"
        const val TRANSACTION_STATUS_QUERY = "TransactionStatusQuery"
        const val REVERSAL = "TransactionReversal"
        const val C2B_PAY_BILL = "CustomerPayBillOnline"
    }

    // Identifier Types
    object IdentifierTypes {
        const val MSISDN = 1
        const val TILL_NUMBER = 2
        const val SHORT_CODE = 4
    }

    // Response Types for C2B
    object ResponseTypes {
        const val COMPLETED = "Completed"
        const val CANCELLED = "Cancelled"
    }

    // Transaction Types
    object TransactionTypes {
        const val PAY_BILL = "CustomerPayBillOnline"
        const val BUY_GOODS = "CustomerBuyGoodsOnline"
    }

    // Default callback URLs (replace with your actual URLs)
    object DefaultCallbacks {
        const val STK_PUSH_CALLBACK = "https://your-domain.com/mpesa/stkpush/callback"
        const val C2B_CONFIRMATION = "https://your-domain.com/mpesa/c2b/confirmation"
        const val C2B_VALIDATION = "https://your-domain.com/mpesa/c2b/validation"
        const val B2C_RESULT = "https://your-domain.com/mpesa/b2c/result"
        const val B2C_TIMEOUT = "https://your-domain.com/mpesa/b2c/timeout"
        const val ACCOUNT_BALANCE_RESULT = "https://your-domain.com/mpesa/accountbalance/result"
        const val ACCOUNT_BALANCE_TIMEOUT = "https://your-domain.com/mpesa/accountbalance/timeout"
        const val TRANSACTION_STATUS_RESULT = "https://your-domain.com/mpesa/transactionstatus/result"
        const val TRANSACTION_STATUS_TIMEOUT = "https://your-domain.com/mpesa/transactionstatus/timeout"
        const val REVERSAL_RESULT = "https://your-domain.com/mpesa/reversal/result"
        const val REVERSAL_TIMEOUT = "https://your-domain.com/mpesa/reversal/timeout"
    }

    // Your actual Daraja API credentials
    object DarajaCredentials {
        const val CLIENT_ID = "xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ"
        const val CLIENT_SECRET = "7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg"
        const val BUSINESS_SHORT_CODE = "174379" // Test short code for sandbox
        const val PASS_KEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919" // Test pass key for sandbox
        const val TEST_PHONE_NUMBER = "254708374149" // Test phone number
    }

    // Phone number formats
    object PhoneFormats {
        const val KENYA_PREFIX = "254"
        const val KENYA_FORMAT = "254XXXXXXXXX"

        fun formatKenyaPhone(phone: String): String {
            val cleaned = phone.replace(Regex("[^0-9]"), "")
            return when {
                cleaned.startsWith("254") -> cleaned
                cleaned.startsWith("0") -> "254${cleaned.substring(1)}"
                cleaned.startsWith("7") -> "254$cleaned"
                else -> "254$cleaned"
            }
        }

        fun isValidKenyaPhone(phone: String): Boolean {
            val formatted = formatKenyaPhone(phone)
            return formatted.matches(Regex("^254[0-9]{9}$"))
        }
    }

    // Amount validation
    object AmountLimits {
        const val MIN_AMOUNT = 1
        const val MAX_AMOUNT = 70000
        const val MAX_AMOUNT_B2C = 150000

        fun isValidAmount(amount: Int): Boolean {
            return amount in MIN_AMOUNT..MAX_AMOUNT
        }

        fun isValidB2CAmount(amount: Int): Boolean {
            return amount in MIN_AMOUNT..MAX_AMOUNT_B2C
        }
    }
}