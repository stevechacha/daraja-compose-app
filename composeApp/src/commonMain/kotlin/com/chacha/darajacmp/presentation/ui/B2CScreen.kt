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
fun B2CScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var initiatorName by remember { mutableStateOf("testapi") }
    var securityCredential by remember { mutableStateOf("Safaricom999!*!") }
    var commandID by remember { mutableStateOf("BusinessPayment") }
    var amount by remember { mutableStateOf("100") }
    var partyA by remember { mutableStateOf("174379") } // Business short code
    var partyB by remember { mutableStateOf("254708374149") } // Customer phone number
    var remarks by remember { mutableStateOf("B2C Payment") }
    var queueTimeOutURL by remember { mutableStateOf("https://your-callback-url.com/timeout") }
    var resultURL by remember { mutableStateOf("https://your-callback-url.com/result") }
    var occasion by remember { mutableStateOf("Payment") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "B2C (Business to Customer)",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Send money from business account to customer M-Pesa account",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        

        
        OutlinedTextField(
            value = initiatorName,
            onValueChange = { initiatorName = it },
            label = { Text("Initiator Name") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Usually 'testapi' for sandbox") }
        )
        
        OutlinedTextField(
            value = securityCredential,
            onValueChange = { securityCredential = it },
            label = { Text("Security Credential") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
            supportingText = { Text("Usually 'Safaricom999!*!' for sandbox") }
        )
        
        OutlinedTextField(
            value = commandID,
            onValueChange = { commandID = it },
            label = { Text("Command ID") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Options: BusinessPayment, SalaryPayment, PromotionPayment") }
        )
        
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount (KES)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        
        OutlinedTextField(
            value = partyA,
            onValueChange = { partyA = it },
            label = { Text("Party A (Business Short Code)") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Your business short code (e.g., 174379)") }
        )
        
        OutlinedTextField(
            value = partyB,
            onValueChange = { partyB = it },
            label = { Text("Party B (Customer Phone)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            supportingText = { Text("Customer phone number (e.g., 254708374149)") }
        )
        
        OutlinedTextField(
            value = remarks,
            onValueChange = { remarks = it },
            label = { Text("Remarks") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Description of the payment") }
        )
        
        OutlinedTextField(
            value = queueTimeOutURL,
            onValueChange = { queueTimeOutURL = it },
            label = { Text("Queue Timeout URL") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Callback URL for timeout events") }
        )
        
        OutlinedTextField(
            value = resultURL,
            onValueChange = { resultURL = it },
            label = { Text("Result URL") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Callback URL for result events") }
        )
        
        OutlinedTextField(
            value = occasion,
            onValueChange = { occasion = it },
            label = { Text("Occasion") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Reason for the payment") }
        )
        
        Button(
            onClick = {
                if (
                    initiatorName.isNotBlank() && securityCredential.isNotBlank() &&
                    commandID.isNotBlank() && amount.isNotBlank() && 
                    partyA.isNotBlank() && partyB.isNotBlank() && 
                    remarks.isNotBlank() && queueTimeOutURL.isNotBlank() && 
                    resultURL.isNotBlank() && occasion.isNotBlank()) {
                    viewModel.initiateB2C(
                        initiatorName = initiatorName,
                        securityCredential = securityCredential,
                        commandID = commandID,
                        amount = amount.toIntOrNull() ?: 0,
                        partyA = partyA,
                        partyB = partyB,
                        remarks = remarks,
                        queueTimeOutURL = queueTimeOutURL,
                        resultURL = resultURL,
                        occasion = occasion
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
            Text("Initiate B2C Transfer")
        }
        
        // Show B2C Response
        uiState.b2cResponse?.let { response ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "B2C Transfer Response:",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Conversation ID: ${response.conversationID}")
                    Text("Originator Conversation ID: ${response.originatorConversationID}")
                    Text("Response Code: ${response.responseCode}")
                    Text("Response Description: ${response.responseDescription}")
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
                    text = "ℹ️ B2C Information",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Command ID options: BusinessPayment, SalaryPayment, PromotionPayment",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Party A is your business short code",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Party B is the customer's phone number",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Amount should be in KES",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Callback URLs will receive transaction results",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}