package com.chacha.darajacmp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chacha.darajacmp.utils.DarajaConfig
import com.chacha.darajacmp.presentation.viewmodel.MpesaUiState
import com.chacha.darajacmp.presentation.viewmodel.MpesaViewModel


@Composable
fun C2BScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var clientId by remember { mutableStateOf(com.chacha.darajacmp.BuildKonfig.CLIENT_ID) }
    var clientSecret by remember { mutableStateOf(com.chacha.darajacmp.BuildKonfig.CLIENT_SECRET) }
    var shortCode by remember { mutableStateOf(DarajaConfig.DarajaCredentials.BUSINESS_SHORT_CODE) }
    var confirmationURL by remember { mutableStateOf("https://your-confirmation-url.com") }
    var validationURL by remember { mutableStateOf("https://your-validation-url.com") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "C2B (Customer to Business)",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
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
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = confirmationURL,
            onValueChange = { confirmationURL = it },
            label = { Text("Confirmation URL") },
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = validationURL,
            onValueChange = { validationURL = it },
            label = { Text("Validation URL") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Button(
            onClick = {
                if (clientId.isNotBlank() && clientSecret.isNotBlank() && 
                    shortCode.isNotBlank() && confirmationURL.isNotBlank() && 
                    validationURL.isNotBlank()) {
                    viewModel.registerC2BURL(
                        clientId = clientId,
                        clientSecret = clientSecret,
                        shortCode = shortCode,
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
            Text("Register C2B URL")
        }
    }
}