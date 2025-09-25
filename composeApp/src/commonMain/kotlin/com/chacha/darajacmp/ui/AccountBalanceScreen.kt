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
fun AccountBalanceScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var clientId by remember { mutableStateOf("xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ") }
    var clientSecret by remember { mutableStateOf("7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg") }
    var initiator by remember { mutableStateOf("testapi") }
    var securityCredential by remember { mutableStateOf("your-encrypted-security-credential") }
    var commandID by remember { mutableStateOf("AccountBalance") }
    var partyA by remember { mutableStateOf("174379") }
    var identifierType by remember { mutableStateOf("4") } // 4 = Organization
    var remarks by remember { mutableStateOf("Account balance query") }
    var queueTimeOutURL by remember { mutableStateOf("https://your-callback-url.com/timeout") }
    var resultURL by remember { mutableStateOf("https://your-callback-url.com/result") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Account Balance Query",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Check the account balance for M-Pesa transactions",
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
            supportingText = { Text("AccountBalance") }
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
            value = remarks,
            onValueChange = { remarks = it },
            label = { Text("Remarks") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Additional information about the query") }
        )
        
        OutlinedTextField(
            value = queueTimeOutURL,
            onValueChange = { queueTimeOutURL = it },
            label = { Text("Queue Timeout URL") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("URL to receive timeout notifications") }
        )
        
        OutlinedTextField(
            value = resultURL,
            onValueChange = { resultURL = it },
            label = { Text("Result URL") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("URL to receive account balance results") }
        )
        
        Button(
            onClick = {
                if (clientId.isNotBlank() && clientSecret.isNotBlank() && 
                    initiator.isNotBlank() && securityCredential.isNotBlank() &&
                    commandID.isNotBlank() && partyA.isNotBlank() && 
                    identifierType.isNotBlank() && remarks.isNotBlank() &&
                    queueTimeOutURL.isNotBlank() && resultURL.isNotBlank()) {
                    viewModel.checkAccountBalance(
                        clientId = clientId,
                        clientSecret = clientSecret,
                        initiator = initiator,
                        securityCredential = securityCredential,
                        commandID = commandID,
                        partyA = partyA,
                        identifierType = identifierType.toIntOrNull() ?: 4,
                        remarks = remarks,
                        queueTimeOutURL = queueTimeOutURL,
                        resultURL = resultURL
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
            Text("Check Account Balance")
        }
        
        // Show Account Balance Response
        uiState.accountBalanceResponse?.let { response ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Account Balance Response:",
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
                        response.errorCode != null -> "❌ Account Balance Query Failed - Service Error"
                        response.responseCode == "0" -> "✅ Account Balance Query Successful"
                        else -> "⚠️ Account Balance Query Status Unknown"
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
                    text = "ℹ️ Account Balance Information",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Command ID: Must be 'AccountBalance'",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Identifier Type: 1=MSISDN, 2=Till, 4=Organization",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Party A: Organization short code for the account",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Security Credential: Must be encrypted using your certificate",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Result URL: Will receive the account balance details",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Balance information is sent via callback to Result URL",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        
        // Account Balance Details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "💰 Account Balance Details",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "The actual balance information will be sent to your Result URL callback endpoint.",
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Callback will include:",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "• Available Balance",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = "• Account Status",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = "• Transaction Details",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Note: This query initiates the balance check. The actual balance data comes via callback.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    }
}
