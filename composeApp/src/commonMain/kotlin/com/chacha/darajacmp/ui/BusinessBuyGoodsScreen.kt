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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.chacha.darajacmp.viewmodel.MpesaUiState
import com.chacha.darajacmp.viewmodel.MpesaViewModel

@Composable
fun BusinessBuyGoodsScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var initiator by remember { mutableStateOf("testapi") }
    var securityCredential by remember { mutableStateOf("safaricom123!@#") } // Replace with actual encrypted credential
    var commandID by remember { mutableStateOf("BusinessBuyGoods") }
    var amount by remember { mutableStateOf("239") }
    var partyA by remember { mutableStateOf("123456") }
    var partyB by remember { mutableStateOf("000000") }
    var senderIdentifierType by remember { mutableStateOf("4") }
    var receiverIdentifierType by remember { mutableStateOf("4") }
    var accountReference by remember { mutableStateOf("353353") }
    var requester by remember { mutableStateOf("254700000000") }
    var remarks by remember { mutableStateOf("OK") }
    var queueTimeOutURL by remember { mutableStateOf("https://mydomain.com/b2b/businessbuygoods/queue/") }
    var resultURL by remember { mutableStateOf("https://mydomain.com/b2b/businessbuygoods/result/") }
    var occasion by remember { mutableStateOf("Buy Goods Payment") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Business Buy Goods",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Initiate a business payment to a Buy Goods Till Number.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )


        OutlinedTextField(
            value = initiator,
            onValueChange = { initiator = it },
            label = { Text("Initiator") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("The username of the M-Pesa API account") }
        )

        OutlinedTextField(
            value = securityCredential,
            onValueChange = { securityCredential = it },
            label = { Text("Security Credential") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            supportingText = { Text("Encrypted password of the Initiator") }
        )

        OutlinedTextField(
            value = commandID,
            onValueChange = { commandID = it },
            label = { Text("Command ID") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("e.g., BusinessBuyGoods") }
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
            label = { Text("Party A (Organization Short Code)") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Your business short code") }
        )

        OutlinedTextField(
            value = partyB,
            onValueChange = { partyB = it },
            label = { Text("Party B (Buy Goods Till Number)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("The Buy Goods Till Number") }
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
            supportingText = { Text("Account reference for the buy goods transaction") }
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
            supportingText = { Text("Comments that are sent along with the transaction") }
        )

        OutlinedTextField(
            value = queueTimeOutURL,
            onValueChange = { queueTimeOutURL = it },
            label = { Text("Queue TimeOut URL") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("URL to receive timeout callbacks") }
        )

        OutlinedTextField(
            value = resultURL,
            onValueChange = { resultURL = it },
            label = { Text("Result URL") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("URL to receive transaction result callbacks") }
        )

        OutlinedTextField(
            value = occasion,
            onValueChange = { occasion = it },
            label = { Text("Occasion") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Optional: reason for the transaction") }
        )

        Button(
            onClick = {
                if (
                    initiator.isNotBlank() && securityCredential.isNotBlank() &&
                    commandID.isNotBlank() && amount.isNotBlank() &&
                    partyA.isNotBlank() && partyB.isNotBlank() &&
                    senderIdentifierType.isNotBlank() && receiverIdentifierType.isNotBlank() &&
                    accountReference.isNotBlank() && requester.isNotBlank() &&
                    remarks.isNotBlank() && queueTimeOutURL.isNotBlank() &&
                    resultURL.isNotBlank() && occasion.isNotBlank()) {
                    viewModel.processBusinessBuyGoods(
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
            Text("Process Business Buy Goods")
        }

        // Show Business Buy Goods Response
        uiState.businessBuyGoodsResponse?.let { response ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Business Buy Goods Response:",
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
                        response.errorCode != null -> "❌ Business Buy Goods Failed - Service Error"
                        response.responseCode == "0" -> "✅ Business Buy Goods Initiated Successfully"
                        else -> "⚠️ Business Buy Goods Status Unknown"
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
                    text = "ℹ️ Business Buy Goods Information",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Party A is your business short code.",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Party B is the Buy Goods Till Number (e.g., 174379).",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• CommandID for Business Buy Goods is typically 'BusinessBuyGoods'.",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Security Credential must be encrypted using your certificate.",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Results are sent via callback to the Result URL.",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Buy Goods Till Numbers are used for merchant payments.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
