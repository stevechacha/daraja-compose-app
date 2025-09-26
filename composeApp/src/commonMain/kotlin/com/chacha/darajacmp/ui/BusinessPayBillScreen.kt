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
fun BusinessPayBillScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var clientId by remember { mutableStateOf("xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ") }
    var clientSecret by remember { mutableStateOf("7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg") }
    var initiator by remember { mutableStateOf("testapi") }
    var securityCredential by remember { mutableStateOf("your-encrypted-security-credential") }
    var commandID by remember { mutableStateOf("BusinessPayBill") }
    var amount by remember { mutableStateOf("") }
    var partyA by remember { mutableStateOf("123456") }
    var partyB by remember { mutableStateOf("000000") }
    var senderIdentifierType by remember { mutableStateOf("4") }
    var receiverIdentifierType by remember { mutableStateOf("4") }
    var accountReference by remember { mutableStateOf("353353") }
    var requester by remember { mutableStateOf("254700000000") }
    var remarks by remember { mutableStateOf("OK") }
    var queueTimeOutURL by remember { mutableStateOf("https://your-callback-url.com/timeout") }
    var resultURL by remember { mutableStateOf("https://your-callback-url.com/result") }
    var occasion by remember { mutableStateOf("Business payment") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Business Pay Bill",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Process business payments through Pay Bill numbers",
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
            supportingText = { Text("BusinessPayBill") }
        )
        
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount (KES)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Payment amount") }
        )
        
        OutlinedTextField(
            value = partyA,
            onValueChange = { partyA = it },
            label = { Text("Party A (Business Short Code)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Your business short code") }
        )
        
        OutlinedTextField(
            value = partyB,
            onValueChange = { partyB = it },
            label = { Text("Party B (Pay Bill Number)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Pay bill number") }
        )
        
        OutlinedTextField(
            value = senderIdentifierType,
            onValueChange = { senderIdentifierType = it },
            label = { Text("Sender Identifier Type") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Sender identifier type (usually '4' for short code)") }
        )
        
        OutlinedTextField(
            value = receiverIdentifierType,
            onValueChange = { receiverIdentifierType = it },
            label = { Text("Receiver Identifier Type") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Receiver identifier type (usually '4' for short code)") }
        )
        
        OutlinedTextField(
            value = accountReference,
            onValueChange = { accountReference = it },
            label = { Text("Account Reference") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Account reference number") }
        )
        
        OutlinedTextField(
            value = requester,
            onValueChange = { requester = it },
            label = { Text("Requester Phone") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Phone number of the requester (254XXXXXXXXX)") }
        )
        
        OutlinedTextField(
            value = remarks,
            onValueChange = { remarks = it },
            label = { Text("Remarks") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Additional information about the payment") }
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
            supportingText = { Text("URL to receive payment results") }
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
                if (clientId.isNotBlank() && clientSecret.isNotBlank() && 
                    initiator.isNotBlank() && securityCredential.isNotBlank() &&
                    commandID.isNotBlank() && amount.isNotBlank() &&
                    partyA.isNotBlank() && partyB.isNotBlank() &&
                    senderIdentifierType.isNotBlank() && receiverIdentifierType.isNotBlank() &&
                    accountReference.isNotBlank() && requester.isNotBlank() &&
                    remarks.isNotBlank() && queueTimeOutURL.isNotBlank() &&
                    resultURL.isNotBlank() && occasion.isNotBlank()) {
                    viewModel.processBusinessPayBill(
                        clientId = clientId,
                        clientSecret = clientSecret,
                        initiator = initiator,
                        securityCredential = securityCredential,
                        commandID = commandID,
                        senderIdentifierType = senderIdentifierType,
                        receiverIdentifierType = receiverIdentifierType,
                        amount = amount,
                        partyA = partyA,
                        partyB = partyB,
                        accountReference = accountReference,
                        requester = requester,
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
            Text("Process Pay Bill")
        }
        
        // Show Business Pay Bill Response
        uiState.businessPayBillResponse?.let { response ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Business Pay Bill Response:",
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
                        response.errorCode != null -> "❌ Business Pay Bill Failed - Service Error"
                        response.responseCode == "0" -> "✅ Business Pay Bill Processed Successfully"
                        else -> "⚠️ Business Pay Bill Status Unknown"
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
                    text = "ℹ️ Business Pay Bill Information",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Command ID: Must be 'BusinessPayBill'",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Party A: Your business short code",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Party B: Pay Bill account number",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Sender/Receiver Identifier Type: Use '4' for short codes",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Account Reference: Account reference number",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Requester: Phone number of the customer",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Amount: Payment amount to be processed",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Security Credential: Must be encrypted using your certificate",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Results are sent via callback to Result URL",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        
        // Business Pay Bill Details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "💼 Business Pay Bill Process",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "The business pay bill process:",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "1. Initiate payment from business to customer",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = "2. M-Pesa processes the business payment",
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
                    text = "Note: This is typically used for business-to-customer payments or refunds.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
        
        // Pay Bill Examples Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "📱 Pay Bill Examples",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Common Pay Bill Use Cases:",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "• Utility Bill Payments",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "• School Fee Payments",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "• Insurance Premiums",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "• Loan Repayments",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "• Government Services",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ensure you have the correct Pay Bill number and account number.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

