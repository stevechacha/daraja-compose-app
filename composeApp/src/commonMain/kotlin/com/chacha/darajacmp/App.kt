package com.chacha.darajacmp

// import androidx.compose.material.icons.Icons
// import androidx.compose.material.icons.filled.*

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chacha.darajacmp.di.appModule
import com.chacha.darajacmp.di.darajaModule
import com.chacha.darajacmp.ui.AccountBalanceScreen
import com.chacha.darajacmp.ui.B2BExpressCheckOutScreen
import com.chacha.darajacmp.ui.B2CAccountTopUpScreen
import com.chacha.darajacmp.ui.B2CScreen
import com.chacha.darajacmp.ui.BillManagerScreen
import com.chacha.darajacmp.ui.BusinessPayBillScreen
import com.chacha.darajacmp.ui.BusinessBuyGoodsScreen
import com.chacha.darajacmp.ui.C2BScreen
import com.chacha.darajacmp.ui.C2BRegisterScreen
import com.chacha.darajacmp.ui.MpesaRatibaScreen
import com.chacha.darajacmp.ui.ReversalsScreen
import com.chacha.darajacmp.ui.TaxRemittanceScreen
import com.chacha.darajacmp.ui.TransactionStatusScreen
import com.chacha.darajacmp.viewmodel.MpesaViewModel
import com.chacha.darajacmp.ui.OtherOperationsScreen
import com.chacha.darajacmp.ui.STKPushScreen
import com.chacha.darajacmp.ui.STKQueryScreen
import com.chacha.darajacmp.ui.DynamicQRScreen
import com.chacha.darajacmp.viewmodel.NewMpesaViewModel
import daraja_compose_app.composeapp.generated.resources.Res
import daraja_compose_app.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(listOf(appModule,darajaModule))
    }) {

        MaterialTheme {
            MpesaApp()
        }
    }
}

@Composable
fun MpesaApp() {
    val viewModel: MpesaViewModel = viewModel()
    val newMpesaViewModel: NewMpesaViewModel = viewModel()
    val uiStates by newMpesaViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }
    
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
        
        // Tab Row
        TabRow(selectedTabIndex = selectedTab) {
            listOf("STK Push", "STK Query", "C2B", "C2B Register", "B2C", "Dynamic QR", "Transaction Status", "Account Balance", "Reversals", "Tax Remittance", "Business Pay Bill", "Business Buy Goods", "Bill Manager", "B2B Express CheckOut", "B2C Account Top Up", "M-Pesa Ratiba", "Other").forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
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