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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.chacha.darajacmp.presentation.viewmodel.MpesaUiState
import com.chacha.darajacmp.presentation.viewmodel.MpesaViewModel

@Composable
fun B2BExpressCheckOutScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var clientId by remember { mutableStateOf("xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ") }
    var clientSecret by remember { mutableStateOf("7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg") }
    var primaryShortCode by remember { mutableStateOf("000001") }
    var receiverShortCode by remember { mutableStateOf("000002") }
    var amount by remember { mutableStateOf("100") }
    var paymentRef by remember { mutableStateOf("paymentRef") }
    var callbackUrl by remember { mutableStateOf("http://..../result") }
    var partnerName by remember { mutableStateOf("Vendor") }
    var requestRefID by remember { mutableStateOf("100001") }

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
            value = primaryShortCode,
            onValueChange = { primaryShortCode = it },
            label = { Text("Primary Short Code") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Your business short code (e.g., 000001)") }
        )

        OutlinedTextField(
            value = receiverShortCode,
            onValueChange = { receiverShortCode = it },
            label = { Text("Receiver Short Code") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Receiving business short code (e.g., 000002)") }
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
            value = paymentRef,
            onValueChange = { paymentRef = it },
            label = { Text("Payment Reference") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Payment reference for tracking") }
        )

        OutlinedTextField(
            value = callbackUrl,
            onValueChange = { callbackUrl = it },
            label = { Text("Callback URL") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("URL to receive payment result callbacks") }
        )

        OutlinedTextField(
            value = partnerName,
            onValueChange = { partnerName = it },
            label = { Text("Partner Name") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Name of the partner/vendor") }
        )

        OutlinedTextField(
            value = requestRefID,
            onValueChange = { requestRefID = it },
            label = { Text("Request Reference ID") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Unique identifier for each request") }
        )

        Button(
            onClick = {
                if (primaryShortCode.isNotBlank() && receiverShortCode.isNotBlank() &&
                    amount.isNotBlank() && paymentRef.isNotBlank() &&
                    callbackUrl.isNotBlank() && partnerName.isNotBlank() &&
                    requestRefID.isNotBlank()) {
                    viewModel.processB2BExpressCheckOut(
                        primaryShortCode = primaryShortCode,
                        receiverShortCode = receiverShortCode,
                        amount = amount,
                        paymentRef = paymentRef,
                        callbackUrl = callbackUrl,
                        partnerName = partnerName,
                        requestRefID = requestRefID
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
                    text = "• Primary Short Code: Your business short code for initiating payments",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Receiver Short Code: The business receiving the payment",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Payment Reference: Unique identifier for tracking this payment",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Callback URL: Where payment confirmations are sent",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Partner Name: Name of the vendor/partner receiving payment",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Request Reference ID: Unique identifier for each request",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• B2B Express CheckOut provides instant business-to-business payments",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}