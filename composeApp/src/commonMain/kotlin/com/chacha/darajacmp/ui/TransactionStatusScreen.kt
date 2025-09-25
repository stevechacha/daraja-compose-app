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
fun TransactionStatusScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var clientId by remember { mutableStateOf("xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ") }
    var clientSecret by remember { mutableStateOf("7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg") }
    var initiator by remember { mutableStateOf("testapi") }
    var securityCredential by remember { mutableStateOf("your-encrypted-security-credential") }
    var commandID by remember { mutableStateOf("TransactionStatusQuery") }
    var transactionID by remember { mutableStateOf("") }
    var partyA by remember { mutableStateOf("174379") }
    var identifierType by remember { mutableStateOf("4") } // 4 = Organization
    var resultURL by remember { mutableStateOf("https://your-callback-url.com/result") }
    var queueTimeOutURL by remember { mutableStateOf("https://your-callback-url.com/timeout") }
    var remarks by remember { mutableStateOf("Transaction status query") }
    var occasion by remember { mutableStateOf("Transaction status check") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Transaction Status Query",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Check the status of M-Pesa transactions using transaction ID",
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
            supportingText = { Text("TransactionStatusQuery") }
        )
        
        OutlinedTextField(
            value = transactionID,
            onValueChange = { transactionID = it },
            label = { Text("Transaction ID") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("The transaction ID to query") }
        )
        
        OutlinedTextField(
            value = partyA,
            onValueChange = { partyA = it },
            label = { Text("Party A") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Organization short code (e.g., 174379)") }
        )
        
        OutlinedTextField(
            value = identifierType,
            onValueChange = { identifierType = it },
            label = { Text("Identifier Type") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("1=MSISDN, 2=Till, 4=Organization") }
        )
        
        OutlinedTextField(
            value = resultURL,
            onValueChange = { resultURL = it },
            label = { Text("Result URL") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("URL to receive transaction status results") }
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
            supportingText = { Text("Additional information about the query") }
        )
        
        OutlinedTextField(
            value = occasion,
            onValueChange = { occasion = it },
            label = { Text("Occasion") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Reason for the transaction status query") }
        )
        
        Button(
            onClick = {
                if (clientId.isNotBlank() && clientSecret.isNotBlank() && 
                    initiator.isNotBlank() && securityCredential.isNotBlank() &&
                    commandID.isNotBlank() && transactionID.isNotBlank() &&
                    partyA.isNotBlank() && identifierType.isNotBlank() &&
                    resultURL.isNotBlank() && queueTimeOutURL.isNotBlank() &&
                    remarks.isNotBlank() && occasion.isNotBlank()) {
                    viewModel.queryTransactionStatus(
                        clientId = clientId,
                        clientSecret = clientSecret,
                        initiator = initiator,
                        securityCredential = securityCredential,
                        commandID = commandID,
                        transactionID = transactionID,
                        partyA = partyA,
                        identifierType = identifierType.toIntOrNull() ?: 4,
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
            Text("Query Transaction Status")
        }
        
        // Show Transaction Status Response
        uiState.transactionStatusResponse?.let { response ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Transaction Status Response:",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Conversation ID: ${response.conversationID}")
                    Text("Originator Conversation ID: ${response.originatorConversationID}")
                    Text("Response Code: ${response.responseCode}")
                    Text("Response Description: ${response.responseDescription}")
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Status interpretation
                    val statusColor = when (response.responseCode) {
                        "0" -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.error
                    }
                    
                    val statusText = when (response.responseCode) {
                        "0" -> "✅ Transaction Status Query Successful"
                        else -> "❌ Transaction Status Query Failed"
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
                    text = "ℹ️ Transaction Status Information",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Transaction ID: The unique identifier of the transaction to query",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Command ID: Must be 'TransactionStatusQuery'",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Identifier Type: 1=MSISDN, 2=Till, 4=Organization",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Party A: Organization short code for the transaction",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Security Credential: Must be encrypted using your certificate",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Result URL: Will receive the transaction status details",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        
        // Transaction ID Examples Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
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
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "B2C Transaction ID:",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "QFX_12345678901234567890",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Note: Transaction IDs are provided in the response of successful transactions",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    }
}
