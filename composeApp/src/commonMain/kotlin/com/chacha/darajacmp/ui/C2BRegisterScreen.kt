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
fun C2BRegisterScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var clientId by remember { mutableStateOf("xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ") }
    var clientSecret by remember { mutableStateOf("7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg") }
    var shortCode by remember { mutableStateOf("174379") }
    var responseType by remember { mutableStateOf("Completed") }
    var confirmationURL by remember { mutableStateOf("https://your-callback-url.com/confirmation") }
    var validationURL by remember { mutableStateOf("https://your-callback-url.com/validation") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "C2B Register URL",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Register callback URLs for Customer to Business transactions",
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
            value = shortCode,
            onValueChange = { shortCode = it },
            label = { Text("Short Code") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Your business short code (e.g., 174379)") }
        )
        
        OutlinedTextField(
            value = responseType,
            onValueChange = { responseType = it },
            label = { Text("Response Type") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Options: Completed, Cancelled") }
        )
        
        OutlinedTextField(
            value = confirmationURL,
            onValueChange = { confirmationURL = it },
            label = { Text("Confirmation URL") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("URL to receive confirmation callbacks") }
        )
        
        OutlinedTextField(
            value = validationURL,
            onValueChange = { validationURL = it },
            label = { Text("Validation URL") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("URL to receive validation callbacks") }
        )
        
        Button(
            onClick = {
                if (clientId.isNotBlank() && clientSecret.isNotBlank() && 
                    shortCode.isNotBlank() && responseType.isNotBlank() &&
                    confirmationURL.isNotBlank() && validationURL.isNotBlank()) {
                    viewModel.registerC2BURL(
                        clientId = clientId,
                        clientSecret = clientSecret,
                        shortCode = shortCode,
                        responseType = responseType,
                        confirmationURL = confirmationURL,
                        validationURL = validationURL
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
            Text("Register C2B URLs")
        }
        
        // Show C2B Register Response
        uiState.c2bRegisterResponse?.let { response ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "C2B Register Response:",
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
                        response.errorCode != null -> "❌ Registration Failed - Service Error"
                        response.responseCode == "0" -> "✅ URLs Registered Successfully"
                        else -> "⚠️ Registration Status Unknown"
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
                    text = "ℹ️ C2B Register Information",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Response Type: Completed (for successful transactions), Cancelled (for cancelled transactions)",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Confirmation URL: Receives callbacks when transactions are completed",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Validation URL: Receives callbacks to validate transactions",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• URLs must be publicly accessible and return appropriate responses",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Registration is required before C2B transactions can be processed",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Note: C2B Register service may be temporarily unavailable in sandbox",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        
        // Callback URL Examples Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "📝 Callback URL Examples",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Confirmation URL Example:",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "https://your-domain.com/mpesa/confirmation",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Validation URL Example:",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "https://your-domain.com/mpesa/validation",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Note: These URLs must be publicly accessible and return HTTP 200 status",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    }
}
