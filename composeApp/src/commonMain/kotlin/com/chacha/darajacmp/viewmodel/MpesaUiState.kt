package com.chacha.darajacmp.viewmodel

import com.chacha.darajacmp.network.response.*

data class MpesaUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null,
    val stkPushResponse: STKPushResponse? = null,
    val stkQueryResponse: STKQueryResponse? = null,
    val c2bRegisterResponse: C2BRegisterResponse? = null,
    val c2bSimulateResponse: C2BSimulateResponse? = null,
    val b2cResponse: B2CResponse? = null,
    val dynamicQRResponse: DynamicQRResponse? = null,
    val accountBalanceResponse: AccountBalanceResponse? = null,
    val transactionStatusResponse: TransactionStatusResponse? = null,
    val reversalResponse: ReversalResponse? = null,
    val taxRemittanceResponse: TaxRemittanceResponse? = null,
    val businessPayBillResponse: BusinessPayBillResponse? = null,
    val businessBuyGoodsResponse: BusinessBuyGoodsResponse? = null,
    val billManagerResponse: BillManagerResponse? = null,
    val b2bExpressCheckOutResponse: B2BExpressCheckOutResponse? = null,
    val b2cAccountTopUpResponse: B2CAccountTopUpResponse? = null,
    val mpesaRatibaResponse: MpesaRatibaResponse? = null
)
