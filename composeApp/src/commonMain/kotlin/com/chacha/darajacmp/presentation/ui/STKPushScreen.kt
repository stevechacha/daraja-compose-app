package com.chacha.darajacmp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.chacha.darajacmp.utils.DarajaConfig
import com.chacha.darajacmp.utils.TestUtils
import com.chacha.darajacmp.presentation.viewmodel.MpesaUiState
import com.chacha.darajacmp.presentation.viewmodel.MpesaViewModel


@Composable
fun STKPushScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var clientId by remember { mutableStateOf(DarajaConfig.DarajaCredentials.CLIENT_ID) }
    var clientSecret by remember { mutableStateOf(DarajaConfig.DarajaCredentials.CLIENT_SECRET) }
    var businessShortCode by remember { mutableStateOf(DarajaConfig.DarajaCredentials.BUSINESS_SHORT_CODE) }
    var passKey by remember { mutableStateOf(DarajaConfig.DarajaCredentials.PASS_KEY) }
    var amount by remember { mutableStateOf("100") }
    var phoneNumber by remember { mutableStateOf(DarajaConfig.DarajaCredentials.TEST_PHONE_NUMBER) }
    var callBackURL by remember { mutableStateOf("https://your-callback-url.com") }
    var accountReference by remember { mutableStateOf("Test Payment") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "STK Push (Customer Pay Bill)",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        // Test Credentials Button
        var testResult by remember { mutableStateOf<String?>(null) }
        
        Button(
            onClick = {
                testResult = TestUtils.validateCredentials()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("🔍 Validate Credentials")
        }
        
        testResult?.let { result ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (result.startsWith("✅")) 
                        MaterialTheme.colorScheme.primaryContainer 
                    else 
                        MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = result,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        
//        OutlinedTextField(
//            value = clientId,
//            onValueChange = { clientId = it },
//            label = { Text("Client ID") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = clientSecret,
//            onValueChange = { clientSecret = it },
//            label = { Text("Client Secret") },
//            modifier = Modifier.fillMaxWidth(),
//            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
//        )
//
//        OutlinedTextField(
//            value = businessShortCode,
//            onValueChange = { businessShortCode = it },
//            label = { Text("Business Short Code") },
//            modifier = Modifier.fillMaxWidth()
//        )
        
        OutlinedTextField(
            value = passKey,
            onValueChange = { passKey = it },
            label = { Text("Pass Key") },
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount (KES)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number (254XXXXXXXXX)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        
        OutlinedTextField(
            value = callBackURL,
            onValueChange = { callBackURL = it },
            label = { Text("Callback URL") },
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = accountReference,
            onValueChange = { accountReference = it },
            label = { Text("Account Reference") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Button(
            onClick = {
                if (clientId.isNotBlank() && clientSecret.isNotBlank() &&
                    businessShortCode.isNotBlank() && passKey.isNotBlank() &&
                    amount.isNotBlank() && phoneNumber.isNotBlank() &&
                    accountReference.isNotBlank()) {
                    viewModel.initiateSTKPush(
                        clientId = clientId,
                        clientSecret = clientSecret,
                        businessShortCode = businessShortCode,
                        passKey = passKey,
                        amount = amount.toIntOrNull() ?: 0,
                        phoneNumber = phoneNumber,
                        callBackURL = callBackURL,
                        accountReference = accountReference
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text("Initiate STK Push")
        }
        
        // Show STK Push Response
        uiState.stkPushResponse?.let { response ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "STK Push Response:",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Merchant Request ID: ${response.merchantRequestID}")
                    Text("Checkout Request ID: ${response.checkoutRequestID}")
                    Text("Response Code: ${response.responseCode}")
                    Text("Response Description: ${response.responseDescription}")
                    Text("Customer Message: ${response.customerMessage}")
                }
            }
        }
    }
}