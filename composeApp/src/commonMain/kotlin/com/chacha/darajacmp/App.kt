package com.chacha.darajacmp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chacha.darajacmp.di.appModule
import com.chacha.darajacmp.di.dataModule
import com.chacha.darajacmp.di.darajaModule
import com.chacha.darajacmp.di.domainModule
import com.chacha.darajacmp.di.presentationModule
import com.chacha.darajacmp.presentation.ui.AccountBalanceScreen
import com.chacha.darajacmp.presentation.ui.B2BExpressCheckOutScreen
import com.chacha.darajacmp.presentation.ui.B2CAccountTopUpScreen
import com.chacha.darajacmp.presentation.ui.B2CScreen
import com.chacha.darajacmp.presentation.ui.BillManagerScreen
import com.chacha.darajacmp.presentation.ui.BusinessPayBillScreen
import com.chacha.darajacmp.presentation.ui.BusinessBuyGoodsScreen
import com.chacha.darajacmp.presentation.ui.C2BScreen
import com.chacha.darajacmp.presentation.ui.C2BRegisterScreen
import com.chacha.darajacmp.presentation.ui.MpesaRatibaScreen
import com.chacha.darajacmp.presentation.ui.ReversalsScreen
import com.chacha.darajacmp.presentation.ui.TaxRemittanceScreen
import com.chacha.darajacmp.presentation.ui.TransactionStatusScreen
import com.chacha.darajacmp.presentation.viewmodel.MpesaViewModel
import com.chacha.darajacmp.presentation.ui.OtherOperationsScreen
import com.chacha.darajacmp.presentation.ui.STKPushScreen
import com.chacha.darajacmp.presentation.ui.STKQueryScreen
import com.chacha.darajacmp.presentation.ui.DynamicQRScreen
import daraja_compose_app.composeapp.generated.resources.Res
import daraja_compose_app.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

/**
 * Data class representing a navigation item for M-Pesa operations
 */
data class MpesaOperation(
    val id: Int,
    val title: String,
    val description: String,
    val icon: String = "📱"
)

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(listOf(appModule, darajaModule, dataModule, domainModule, presentationModule))
    }) {

        MaterialTheme {
            MpesaApp()
        }
    }
}

@Composable
fun MpesaApp() {
    val viewModel: MpesaViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    var selectedTab by remember { mutableStateOf<Int?>(null) }
    
    // Define all M-Pesa operations
    val operations = remember {
        listOf(
            MpesaOperation(0, "STK Push", "Customer Pay Bill via STK", "💳"),
            MpesaOperation(1, "STK Query", "Query STK Push Status", "🔍"),
            MpesaOperation(2, "C2B Simulate", "Simulate Customer to Business", "📥"),
            MpesaOperation(3, "C2B Register", "Register C2B URLs", "📝"),
            MpesaOperation(4, "B2C Transfer", "Business to Customer Transfer", "📤"),
            MpesaOperation(5, "Dynamic QR", "Generate Dynamic QR Code", "📲"),
            MpesaOperation(6, "Transaction Status", "Query Transaction Status", "📊"),
            MpesaOperation(7, "Account Balance", "Check Account Balance", "💰"),
            MpesaOperation(8, "Reversals", "Reverse Transaction", "↩️"),
            MpesaOperation(9, "Tax Remittance", "Remit Tax Payment", "🏛️"),
            MpesaOperation(10, "Business Pay Bill", "Business Pay Bill", "💼"),
            MpesaOperation(11, "Business Buy Goods", "Business Buy Goods", "🛒"),
            MpesaOperation(12, "Bill Manager", "Manage Bills", "📋"),
            MpesaOperation(13, "B2B Express", "B2B Express Checkout", "⚡"),
            MpesaOperation(14, "B2C Top Up", "B2C Account Top Up", "📱"),
            MpesaOperation(15, "M-Pesa Ratiba", "Standing Order", "📅"),
            MpesaOperation(16, "Other", "Other Operations", "🔧")
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Daraja M-Pesa API",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Compose Multiplatform",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        
        // Navigation Grid or Content Screen
        if (selectedTab == null) {
            // Show grid navigation menu
            Text(
                text = "Select an Operation",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(operations) { operation ->
                    OperationCard(
                        operation = operation,
                        onClick = { selectedTab = operation.id }
                    )
                }
            }
        } else {
            // Show selected screen with back button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { selectedTab = null }
                ) {
                    Text("← Back to Operations")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = operations.find { it.id == selectedTab }?.title ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Content
            when (selectedTab) {
                0 -> STKPushScreen(viewModel = viewModel, uiState = uiState)
                1 -> STKQueryScreen(viewModel = viewModel, uiState = uiState)
                2 -> C2BScreen(viewModel = viewModel, uiState = uiState)
                3 -> C2BRegisterScreen(viewModel = viewModel, uiState = uiState)
                4 -> B2CScreen(viewModel = viewModel, uiState = uiState)
                5 -> DynamicQRScreen(viewModel = viewModel, uiState = uiState)
                6 -> TransactionStatusScreen(viewModel = viewModel, uiState = uiState)
                7 -> AccountBalanceScreen(viewModel = viewModel, uiState = uiState)
                8 -> ReversalsScreen(viewModel = viewModel, uiState = uiState)
                9 -> TaxRemittanceScreen(viewModel = viewModel, uiState = uiState)
                10 -> BusinessPayBillScreen(viewModel = viewModel, uiState = uiState)
                11 -> BusinessBuyGoodsScreen(viewModel = viewModel, uiState = uiState)
                12 -> BillManagerScreen(viewModel = viewModel, uiState = uiState)
                13 -> B2BExpressCheckOutScreen(viewModel = viewModel, uiState = uiState)
                14 -> B2CAccountTopUpScreen(viewModel = viewModel, uiState = uiState)
                15 -> MpesaRatibaScreen(viewModel = viewModel, uiState = uiState)
                16 -> OtherOperationsScreen(viewModel = viewModel, uiState = uiState)
                else -> {}
            }
        }
        
        // Status Messages
        uiState.error?.let { error ->
            LaunchedEffect(error) {
                // Auto-clear error after 5 seconds
                kotlinx.coroutines.delay(5000)
                viewModel.clearMessages()
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Icon(Icons.Default.Error, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
        
        uiState.success?.let { success ->
            LaunchedEffect(success) {
                // Auto-clear success after 5 seconds
                kotlinx.coroutines.delay(5000)
                viewModel.clearMessages()
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = success,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

/**
 * Composable for displaying an operation card in the grid
 */
@Composable
fun OperationCard(
    operation: MpesaOperation,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = operation.icon,
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = operation.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = operation.description,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}