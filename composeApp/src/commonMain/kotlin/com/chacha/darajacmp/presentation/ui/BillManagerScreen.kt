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
fun BillManagerScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var clientId by remember { mutableStateOf("xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ") }
    var clientSecret by remember { mutableStateOf("7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg") }
    var shortcode by remember { mutableStateOf("718003") }
    var email by remember { mutableStateOf("youremail@gmail.com") }
    var officialContact by remember { mutableStateOf("0710XXXXXX") }
    var sendReminders by remember { mutableStateOf("1") }
    var logo by remember { mutableStateOf("image") }
    var callbackurl by remember { mutableStateOf("http://my.server.com/bar/callback") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Bill Manager",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Initiate a bill payment through the Bill Manager system.",
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
            value = shortcode,
            onValueChange = { shortcode = it },
            label = { Text("Short Code") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Your business short code (e.g., 718003)") }
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            supportingText = { Text("Contact email address for billing") }
        )

        OutlinedTextField(
            value = officialContact,
            onValueChange = { officialContact = it },
            label = { Text("Official Contact") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            supportingText = { Text("Official contact phone number (e.g., 0710XXXXXX)") }
        )

        OutlinedTextField(
            value = sendReminders,
            onValueChange = { sendReminders = it },
            label = { Text("Send Reminders") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("'1' for yes, '0' for no to send reminders") }
        )

        OutlinedTextField(
            value = logo,
            onValueChange = { logo = it },
            label = { Text("Logo") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Base64 encoded logo image data") }
        )

        OutlinedTextField(
            value = callbackurl,
            onValueChange = { callbackurl = it },
            label = { Text("Callback URL") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("URL to receive callbacks from bill manager service") }
        )

        Button(
            onClick = {
                if (clientId.isNotBlank() && clientSecret.isNotBlank() &&
                    shortcode.isNotBlank() && email.isNotBlank() &&
                    officialContact.isNotBlank() && sendReminders.isNotBlank() &&
                    logo.isNotBlank() && callbackurl.isNotBlank()) {
                    viewModel.processBillManager(
                        clientId = clientId,
                        clientSecret = clientSecret,
                        shortcode = shortcode,
                        email = email,
                        officialContact = officialContact,
                        sendReminders = sendReminders,
                        logo = logo,
                        callbackurl = callbackurl
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
            Text("Process Bill Manager")
        }

        // Show Bill Manager Response
        uiState.billManagerResponse?.let { response ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Bill Manager Response:",
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
                        response.errorCode != null -> "❌ Bill Manager Failed - Service Error"
                        response.responseCode == "0" -> "✅ Bill Manager Processed Successfully"
                        else -> "⚠️ Bill Manager Status Unknown"
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
                    text = "ℹ️ Bill Manager Information",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Short Code: Your business short code for bill manager opt-in",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Email: Contact email address for billing notifications",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Official Contact: Phone number for bill manager service",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Send Reminders: '1' for yes, '0' for no email/text reminders",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Logo: Base64 encoded image for brand display",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Callback URL: Where payment confirmations are received",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Bill Manager opt-in process for invoice management",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

