package com.chacha.darajacmp.ui

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
import com.chacha.darajacmp.viewmodel.MpesaUiState
import com.chacha.darajacmp.viewmodel.MpesaViewModel

@Composable
fun ReversalsScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var clientId by remember { mutableStateOf("xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ") }
    var clientSecret by remember { mutableStateOf("7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg") }
    var initiator by remember { mutableStateOf("testapi") }
    var securityCredential by remember { mutableStateOf("your-encrypted-security-credential") }
    var commandID by remember { mutableStateOf("TransactionReversal") }
    var transactionID by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var receiverParty by remember { mutableStateOf("254712345678") }
    var recieverIdentifierType by remember { mutableStateOf("1") } // 1 = MSISDN
    var resultURL by remember { mutableStateOf("https://your-callback-url.com/result") }
    var queueTimeOutURL by remember { mutableStateOf("https://your-callback-url.com/timeout") }
    var remarks by remember { mutableStateOf("Transaction reversal") }
    var occasion by remember { mutableStateOf("Reversal request") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Transaction Reversal",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Reverse M-Pesa transactions that have been completed",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        OutlinedTextField(
            value = clientId,
            onValueChange = { clientId = it },
            label = { Text("Client ID") },
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = clientSecret,
            onValueChange = { clientSecret = it },
            label = { Text("Client Secret") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
        )
        
        OutlinedTextField(
            value = initiator,
            onValueChange = { initiator = it },
            label = { Text("Initiator") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Username for the initiator") }
        )
        
        OutlinedTextField(
            value = securityCredential,
            onValueChange = { securityCredential = it },
            label = { Text("Security Credential") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
            supportingText = { Text("Encrypted credential for the initiator") }
        )
        
        OutlinedTextField(
            value = commandID,
            onValueChange = { commandID = it },
            label = { Text("Command ID") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("TransactionReversal") }
        )
        
        OutlinedTextField(
            value = transactionID,
            onValueChange = { transactionID = it },
            label = { Text("Transaction ID") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("The transaction ID to reverse") }
        )
        
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount (KES)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Amount to reverse (must match original transaction)") }
        )
        
        OutlinedTextField(
            value = receiverParty,
            onValueChange = { receiverParty = it },
            label = { Text("Receiver Party") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Phone number or short code to receive the reversal") }
        )
        
        OutlinedTextField(
            value = recieverIdentifierType,
            onValueChange = { recieverIdentifierType = it },
            label = { Text("Receiver Identifier Type") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("1=MSISDN, 2=Till, 4=Organization") }
        )
        
        OutlinedTextField(
            value = resultURL,
            onValueChange = { resultURL = it },
            label = { Text("Result URL") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("URL to receive reversal results") }
        )
        
        OutlinedTextField(
            value = queueTimeOutURL,
            onValueChange = { queueTimeOutURL = it },
            label = { Text("Queue Timeout URL") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("URL to receive timeout notifications") }
        )
        
        OutlinedTextField(
            value = remarks,
            onValueChange = { remarks = it },
            label = { Text("Remarks") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Additional information about the reversal") }
        )
        
        OutlinedTextField(
            value = occasion,
            onValueChange = { occasion = it },
            label = { Text("Occasion") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Reason for the transaction reversal") }
        )
        
        Button(
            onClick = {
                if (clientId.isNotBlank() && clientSecret.isNotBlank() && 
                    initiator.isNotBlank() && securityCredential.isNotBlank() &&
                    commandID.isNotBlank() && transactionID.isNotBlank() &&
                    amount.isNotBlank() && receiverParty.isNotBlank() &&
                    recieverIdentifierType.isNotBlank() && resultURL.isNotBlank() &&
                    queueTimeOutURL.isNotBlank() && remarks.isNotBlank() && occasion.isNotBlank()) {
                    viewModel.reverseTransaction(
                        clientId = clientId,
                        clientSecret = clientSecret,
                        initiator = initiator,
                        securityCredential = securityCredential,
                        commandID = commandID,
                        transactionID = transactionID,
                        amount = amount.toIntOrNull() ?: 0,
                        receiverParty = receiverParty,
                        recieverIdentifierType = recieverIdentifierType.toIntOrNull() ?: 1,
                        resultURL = resultURL,
                        queueTimeOutURL = queueTimeOutURL,
                        remarks = remarks,
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
            Text("Reverse Transaction")
        }
        
        // Show Reversal Response
        uiState.reversalResponse?.let { response ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Reversal Response:",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Conversation ID: ${response.conversationID ?: "N/A"}")
                    Text("Originator Conversation ID: ${response.originatorConversationID ?: "N/A"}")
                    Text("Response Code: ${response.responseCode ?: "N/A"}")
                    Text("Response Description: ${response.responseDescription ?: "N/A"}")
                    
                    // Show error information if present
                    response.errorCode?.let { errorCode ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Error Code: $errorCode", color = MaterialTheme.colorScheme.error)
                    }
                    response.errorMessage?.let { errorMessage ->
                        Text("Error Message: $errorMessage", color = MaterialTheme.colorScheme.error)
                    }
                    response.requestId?.let { requestId ->
                        Text("Request ID: $requestId")
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Status interpretation
                    val statusColor = when {
                        response.errorCode != null -> MaterialTheme.colorScheme.error
                        response.responseCode == "0" -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                    
                    val statusText = when {
                        response.errorCode != null -> "❌ Reversal Failed - Service Error"
                        response.responseCode == "0" -> "✅ Reversal Initiated Successfully"
                        else -> "⚠️ Reversal Status Unknown"
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
                    text = "ℹ️ Transaction Reversal Information",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Command ID: Must be 'TransactionReversal'",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Transaction ID: The original transaction ID to reverse",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Amount: Must match the original transaction amount",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Receiver Party: Phone number or short code to receive the reversal",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Identifier Type: 1=MSISDN, 2=Till, 4=Organization",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Security Credential: Must be encrypted using your certificate",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Reversal results are sent via callback to Result URL",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        
        // Reversal Details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "🔄 Reversal Process Details",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "The reversal process:",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "1. Initiate reversal with transaction details",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = "2. M-Pesa processes the reversal request",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = "3. Results are sent to your Result URL callback",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Important: Reversals can only be done on completed transactions and within 24 hours.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        
        // Transaction ID Examples Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "📝 Transaction ID Examples",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "STK Push Transaction ID:",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "ws_CO_25092025110237338712701823",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "B2C Transaction ID:",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "QFX_12345678901234567890",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Note: Use the transaction ID from the original successful transaction",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}
