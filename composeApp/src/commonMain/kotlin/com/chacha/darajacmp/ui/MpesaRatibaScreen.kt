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
fun MpesaRatibaScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var standingOrderName by remember { mutableStateOf("Test Standing Order") }
    var startDate by remember { mutableStateOf("20240905") }
    var endDate by remember { mutableStateOf("20230905") }
    var businessShortCode by remember { mutableStateOf("174379") }
    var transactionType by remember { mutableStateOf("Standing Order Customer Pay Bill") }
    var receiverPartyIdentifierType by remember { mutableStateOf("4") }
    var amount by remember { mutableStateOf("4500") }
    var partyA by remember { mutableStateOf("254708374149") }
    var callBackURL by remember { mutableStateOf("https://mydomain.com/pat") }
    var accountReference by remember { mutableStateOf("Test") }
    var transactionDesc by remember { mutableStateOf("Test") }
    var frequency by remember { mutableStateOf("2") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "M-Pesa Ratiba",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Schedule recurring payments through the M-Pesa system.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )


        OutlinedTextField(
            value = standingOrderName,
            onValueChange = { standingOrderName = it },
            label = { Text("Standing Order Name") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Name for the standing order (e.g., Test Standing Order)") }
        )

        OutlinedTextField(
            value = startDate,
            onValueChange = { startDate = it },
            label = { Text("Start Date") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Start date in YYYYMMDD format (e.g., 20240905)") }
        )

        OutlinedTextField(
            value = endDate,
            onValueChange = { endDate = it },
            label = { Text("End Date") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("End date in YYYYMMDD format (e.g., 20230905)") }
        )

        OutlinedTextField(
            value = businessShortCode,
            onValueChange = { businessShortCode = it },
            label = { Text("Business Short Code") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Your business short code (e.g., 174379)") }
        )

        OutlinedTextField(
            value = transactionType,
            onValueChange = { transactionType = it },
            label = { Text("Transaction Type") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Type of standing order transaction") }
        )

        OutlinedTextField(
            value = receiverPartyIdentifierType,
            onValueChange = { receiverPartyIdentifierType = it },
            label = { Text("Receiver Party Identifier Type") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Identifier type (e.g., '4' for short code)") }
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount (KES)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Payment amount per occurrence") }
        )

        OutlinedTextField(
            value = partyA,
            onValueChange = { partyA = it },
            label = { Text("Party A (Customer Phone)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            supportingText = { Text("Customer's M-Pesa phone number (e.g., 254708374149)") }
        )

        OutlinedTextField(
            value = callBackURL,
            onValueChange = { callBackURL = it },
            label = { Text("Callback URL") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("URL to receive standing order callbacks") }
        )

        OutlinedTextField(
            value = accountReference,
            onValueChange = { accountReference = it },
            label = { Text("Account Reference") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Reference for tracking the standing order") }
        )

        OutlinedTextField(
            value = transactionDesc,
            onValueChange = { transactionDesc = it },
            label = { Text("Transaction Description") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Description of the standing order") }
        )

        OutlinedTextField(
            value = frequency,
            onValueChange = { frequency = it },
            label = { Text("Frequency") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Execution frequency (e.g., '2' for biweekly)") }
        )

        Button(
            onClick = {
                if (
                    standingOrderName.isNotBlank() && startDate.isNotBlank() &&
                    endDate.isNotBlank() && businessShortCode.isNotBlank() &&
                    transactionType.isNotBlank() && receiverPartyIdentifierType.isNotBlank() &&
                    amount.isNotBlank() && partyA.isNotBlank() &&
                    callBackURL.isNotBlank() && accountReference.isNotBlank() &&
                    transactionDesc.isNotBlank() && frequency.isNotBlank()) {
                    viewModel.processMpesaRatiba(
                        standingOrderName = standingOrderName,
                        startDate = startDate,
                        endDate = endDate,
                        businessShortCode = businessShortCode,
                        transactionType = transactionType,
                        receiverPartyIdentifierType = receiverPartyIdentifierType,
                        amount = amount,
                        partyA = partyA,
                        callBackURL = callBackURL,
                        accountReference = accountReference,
                        transactionDesc = transactionDesc,
                        frequency = frequency
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
            Text("Process M-Pesa Ratiba")
        }

        // Show M-Pesa Ratiba Response
        uiState.mpesaRatibaResponse?.let { response ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "M-Pesa Ratiba Response:",
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
                        response.errorCode != null -> "❌ M-Pesa Ratiba Failed - Service Error"
                        response.responseCode == "0" -> "✅ M-Pesa Ratiba Processed Successfully"
                        else -> "⚠️ M-Pesa Ratiba Status Unknown"
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
                    text = "ℹ️ M-Pesa Ratiba Information",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Standing Order Name: Unique name for the recurring payment order",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Start/End Date: Date range in YYYYMMDD format for order validity",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Business Short Code: Your M-Pesa business short code",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Party A: Customer's M-Pesa phone number receiving the payment",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Frequency: How often the payment executes (e.g., '2' for biweekly)",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• M-Pesa Ratiba enables automated recurring standing orders",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

