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
fun B2BExpressCheckOutScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var clientId by remember { mutableStateOf("xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ") }
    var clientSecret by remember { mutableStateOf("7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg") }
    var initiator by remember { mutableStateOf("testapi") }
    var securityCredential by remember { mutableStateOf("safaricom123!@#") } // Replace with actual encrypted credential
    var commandID by remember { mutableStateOf("B2BExpressCheckOut") }
    var amount by remember { mutableStateOf("5") }
    var partyA by remember { mutableStateOf("174379") } // Your business short code
    var partyB by remember { mutableStateOf("174379") } // Receiving business short code
    var remarks by remember { mutableStateOf("B2B Express CheckOut Test") }
    var queueTimeOutURL by remember { mutableStateOf("https://mydomain.com/b2c/queue") }
    var resultURL by remember { mutableStateOf("https://mydomain.com/b2c/result") }
    var occasion by remember { mutableStateOf("B2B Payment") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "B2B Express CheckOut",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Initiate a business-to-business payment through Express CheckOut.",
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
            visualTransformation = PasswordVisualTransformation()
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
            supportingText = { Text("e.g., B2BExpressCheckOut") }
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
            label = { Text("Party A (Sending Business Short Code)") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Your business short code") }
        )

        OutlinedTextField(
            value = partyB,
            onValueChange = { partyB = it },
            label = { Text("Party B (Receiving Business Short Code)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("The receiving business short code") }
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
                if (clientId.isNotBlank() && clientSecret.isNotBlank() &&
                    initiator.isNotBlank() && securityCredential.isNotBlank() &&
                    commandID.isNotBlank() && amount.isNotBlank() &&
                    partyA.isNotBlank() && partyB.isNotBlank() &&
                    remarks.isNotBlank() && queueTimeOutURL.isNotBlank() &&
                    resultURL.isNotBlank() && occasion.isNotBlank()) {
                    viewModel.processB2BExpressCheckOut(
                        clientId = clientId,
                        clientSecret = clientSecret,
                        initiator = initiator,
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
            Text("Process B2B Express CheckOut")
        }

        // Show B2B Express CheckOut Response
        uiState.b2bExpressCheckOutResponse?.let { response ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "B2B Express CheckOut Response:",
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
                        response.errorCode != null -> "❌ B2B Express CheckOut Failed - Service Error"
                        response.responseCode == "0" -> "✅ B2B Express CheckOut Processed Successfully"
                        else -> "⚠️ B2B Express CheckOut Status Unknown"
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
                    text = "ℹ️ B2B Express CheckOut Information",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Party A is your business short code (sending).",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Party B is the receiving business short code.",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• CommandID for B2B Express CheckOut is typically 'B2BExpressCheckOut'.",
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
                    text = "• B2B Express CheckOut enables fast business-to-business payments.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
