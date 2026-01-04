package com.chacha.darajacmp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chacha.darajacmp.presentation.viewmodel.MpesaUiState
import com.chacha.darajacmp.presentation.viewmodel.MpesaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import io.ktor.util.*

@Composable
fun DynamicQRScreen(viewModel: MpesaViewModel, uiState: MpesaUiState) {
    var merchantName by remember { mutableStateOf("Test Merchant") }
    var refNo by remember { mutableStateOf("REF${kotlinx.datetime.Clock.System.now().epochSeconds}") }
    var amount by remember { mutableStateOf("100") }
    var trxCode by remember { mutableStateOf("PB") } // Pay Bill
    var cpi by remember { mutableStateOf("174379") } // Business Short Code
    var size by remember { mutableStateOf("300") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Dynamic QR Code",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Generate QR codes for M-Pesa payments that customers can scan",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedTextField(
            value = merchantName,
            onValueChange = { merchantName = it },
            label = { Text("Merchant Name") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Name of your business/merchant") }
        )
        
        OutlinedTextField(
            value = refNo,
            onValueChange = { refNo = it },
            label = { Text("Reference Number") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Unique reference for this transaction") }
        )
        
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount (KES)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        
        OutlinedTextField(
            value = trxCode,
            onValueChange = { trxCode = it },
            label = { Text("Transaction Code") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("PB (Pay Bill), BG (Buy Goods), WA (Withdraw at Agent)") }
        )
        
        OutlinedTextField(
            value = cpi,
            onValueChange = { cpi = it },
            label = { Text("CPI (Business Short Code)") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Your business short code (e.g., 174379)") }
        )
        
        OutlinedTextField(
            value = size,
            onValueChange = { size = it },
            label = { Text("QR Code Size") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = { Text("Size in pixels (e.g., 300)") }
        )
        
        Button(
            onClick = {
                if (
                    merchantName.isNotBlank() && refNo.isNotBlank() &&
                    amount.isNotBlank() && trxCode.isNotBlank() && 
                    cpi.isNotBlank() && size.isNotBlank()) {
                    viewModel.generateDynamicQR(
                        merchantName = merchantName,
                        refNo = refNo,
                        amount = amount.toIntOrNull() ?: 0,
                        trxCode = trxCode,
                        cpi = cpi,
                        size = size
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
            Text("Generate QR Code")
        }
        
        // Show Dynamic QR Response and QR Code
        uiState.dynamicQRResponse?.let { response ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Dynamic QR Response:",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Response Code: ${response.responseCode}")
                    Text("Response Description: ${response.responseDescription}")
                    
                    if (response.responseCode == "0" && response.qrCode.isNotBlank()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "QR Code:",
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Display QR Code
                        QRCodeDisplay(qrCodeData = response.qrCode)
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Scan this QR code with M-Pesa app to make payment",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
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
                    text = "ℹ️ Dynamic QR Information",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Transaction Codes: PB (Pay Bill), BG (Buy Goods), WA (Withdraw at Agent)",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• CPI is your business short code",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Reference number should be unique for each transaction",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• QR code size affects image quality and scanning speed",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Customers scan QR code with M-Pesa app to pay",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun QRCodeDisplay(qrCodeData: String) {
    var qrBitmap by remember { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(qrCodeData) {
        isLoading = true
        error = null
        
        try {
            // For now, we'll just show the base64 data as text  
            // In a real implementation, you'd decode the image bytes to a bitmap
            isLoading = false
        } catch (e: Exception) {
            error = "Failed to decode QR code: ${e.message}"
            isLoading = false
        }
    }
    
    Card(
        modifier = Modifier.size(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator()
                }
                error != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "❌",
                            fontSize = 24.sp
                        )
                        Text(
                            text = error ?: "Error",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                else -> {
                    // For now, show QR code data as text
                    // In a real implementation, you'd display the actual QR code image
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "📱",
                            fontSize = 48.sp
                        )
                        Text(
                            text = "QR Code Generated",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Data: ${qrCodeData.take(50)}...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
