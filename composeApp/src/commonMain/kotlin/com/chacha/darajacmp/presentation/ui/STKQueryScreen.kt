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
import com.chacha.darajacmp.presentation.viewmodel.MpesaUiState
import com.chacha.darajacmp.presentation.viewmodel.MpesaViewModel

@Composable
fun STKQueryScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var businessShortCode by remember { mutableStateOf("174379") }
    var passKey by remember { mutableStateOf("bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919") }
    var checkoutRequestID by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "STK Query",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Check the status of STK Push payments",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedTextField(
            value = businessShortCode,
            onValueChange = { businessShortCode = it },
            label = { Text("Business Short Code") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Your business short code (e.g., 174379)") }
        )
        
        OutlinedTextField(
            value = passKey,
            onValueChange = { passKey = it },
            label = { Text("Pass Key") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
            supportingText = { Text("Your Daraja API pass key") }
        )
        
        OutlinedTextField(
            value = checkoutRequestID,
            onValueChange = { checkoutRequestID = it },
            label = { Text("Checkout Request ID") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Get this from STK Push response (e.g., ws_CO_25092025060021586746656813)") }
        )
        
        Button(
            onClick = {
                if (
                    businessShortCode.isNotBlank() && passKey.isNotBlank() &&
                    checkoutRequestID.isNotBlank()) {
                    viewModel.querySTKStatus(
                        businessShortCode = businessShortCode,
                        passKey = passKey,
                        checkoutRequestID = checkoutRequestID
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
            Text("Query STK Status")
        }
        
        // Show STK Query Response
        uiState.stkQueryResponse?.let { response ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "STK Query Response:",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Response Code: ${response.responseCode ?: "N/A"}")
                    Text("Response Description: ${response.responseDescription ?: "N/A"}")
                    Text("Merchant Request ID: ${response.merchantRequestID ?: "N/A"}")
                    Text("Checkout Request ID: ${response.checkoutRequestID ?: "N/A"}")
                    Text("Result Code: ${response.resultCode ?: "N/A"}")
                    Text("Result Description: ${response.resultDesc ?: "N/A"}")
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Status interpretation
                    val statusColor = when (response.resultCode) {
                        0 -> MaterialTheme.colorScheme.primary
                        1 -> MaterialTheme.colorScheme.error
                        null -> MaterialTheme.colorScheme.onSurfaceVariant
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                    
                    val statusText = when (response.resultCode) {
                        0 -> "✅ Payment Successful"
                        1 -> "❌ Payment Failed"
                        null -> "⏳ Payment Pending or No Result"
                        else -> "⏳ Payment Pending"
                    }
                    
                    Text(
                        text = statusText,
                        color = statusColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        
        // Information Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "ℹ️ STK Query Information",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Use this to check the status of STK Push payments",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Checkout Request ID comes from STK Push response",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Result Code 0 = Success, 1 = Failed, Others = Pending",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Query can be done multiple times for the same payment",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Use same credentials as the original STK Push",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        
        // Recent STK Push Results (if available)
        uiState.stkPushResponse?.let { stkResponse ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📱 Recent STK Push Result:",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Merchant Request ID: ${stkResponse.merchantRequestID}")
                    Text("Checkout Request ID: ${stkResponse.checkoutRequestID}")
                    Text("Response Code: ${stkResponse.responseCode}")
                    Text("Response Description: ${stkResponse.responseDescription}")
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Button(
                        onClick = {
                            checkoutRequestID = stkResponse.checkoutRequestID
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Use This Checkout Request ID")
                    }
                }
            }
        }
    }
}
