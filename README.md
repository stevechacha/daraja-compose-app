# Daraja Compose Multiplatform App

A Kotlin Multiplatform Mobile (KMP) application built with Jetpack Compose Multiplatform that provides a comprehensive interface for interacting with Safaricom's Daraja M-Pesa API. The application targets Android, iOS, and Desktop (JVM) platforms and follows Clean Architecture principles.

## 📱 Overview

This application provides a complete solution for integrating with Safaricom's Daraja M-Pesa API, offering 16 different M-Pesa operations through a modern, cross-platform UI. The app is built using Kotlin Multiplatform, allowing code sharing across Android, iOS, and Desktop platforms while maintaining platform-specific optimizations.

## ✨ Features

The application supports the following M-Pesa API operations:

1. **STK Push** - Customer Pay Bill via STK
2. **STK Query** - Query STK Push Status
3. **C2B Simulate** - Simulate Customer to Business payment
4. **C2B Register** - Register C2B URLs
5. **B2C Transfer** - Business to Customer transfer
6. **Dynamic QR** - Generate Dynamic QR Code
7. **Transaction Status** - Query transaction status
8. **Account Balance** - Check account balance
9. **Reversals** - Reverse a transaction
10. **Tax Remittance** - Remit tax payment
11. **Business Pay Bill** - Business pay bill operation
12. **Business Buy Goods** - Business buy goods operation
13. **Bill Manager** - Manage bills
14. **B2B Express Checkout** - B2B express checkout
15. **B2C Account Top Up** - B2C account top up
16. **M-Pesa Ratiba** - Standing order operations
17. **Other Operations** - Additional operations

### UI Features

- **LazyVerticalGrid Navigation Menu** - Visual grid-based menu for easy navigation
- **Modern Material Design 3 UI** - Clean and intuitive user interface
- **Cross-platform Compatibility** - Runs on Android, iOS, and Desktop
- **Real-time Status Updates** - Live updates for transaction status

## 🏗️ Architecture

This project follows **Clean Architecture** principles with clear separation of concerns:

### Layer Structure

```
com.chacha.darajacmp/
├── domain/              # Domain Layer (Business Logic)
│   ├── model/          # Domain entities (pure business models)
│   ├── repository/     # Repository interfaces
│   ├── service/        # Business services (utilities)
│   └── usecase/        # Use cases (business operations)
│
├── data/               # Data Layer (Infrastructure)
│   ├── dto/           # Data Transfer Objects
│   │   ├── request/   # API request DTOs
│   │   └── response/  # API response DTOs
│   ├── network/       # Network infrastructure
│   ├── remote/        # Remote data sources
│   └── repository/    # Repository implementations
│
├── presentation/       # Presentation Layer (UI)
│   ├── ui/            # Compose UI screens
│   └── viewmodel/     # ViewModels (MVVM pattern)
│
└── di/                # Dependency Injection (Koin modules)
```

### Clean Architecture Principles

1. **Domain Layer** (Innermost)
   - Pure Kotlin, no dependencies on other layers
   - Contains business entities, use cases, and repository interfaces
   - Independent of frameworks and external libraries

2. **Data Layer**
   - Implements domain repository interfaces
   - Handles data sources (remote API, local storage)
   - Maps DTOs to domain entities
   - Network infrastructure (Ktor client, API services)

3. **Presentation Layer**
   - UI components (Compose Multiplatform)
   - ViewModels that depend only on Use Cases (not repositories)
   - Observes domain state and handles user interactions

### Key Architectural Decisions

- **ViewModels depend on UseCases only** - Ensures clean separation and testability
- **DTOs separate from Domain Models** - Clear distinction between API contracts and business logic
- **Use Cases encapsulate business logic** - Single responsibility per use case
- **Repository pattern** - Abstracts data sources from business logic
- **Dependency Injection with Koin** - Modular and testable dependency management

## 📦 Package Structure

```
commonMain/kotlin/com/chacha/darajacmp/
├── domain/
│   ├── model/
│   │   ├── Result.kt                    # Result sealed class (Success/Error)
│   │   ├── STKPush.kt
│   │   ├── STKQuery.kt
│   │   ├── C2BRegister.kt
│   │   ├── C2BSimulate.kt
│   │   ├── B2C.kt
│   │   ├── DynamicQR.kt
│   │   ├── AccountBalance.kt
│   │   ├── TransactionStatus.kt
│   │   ├── Reversal.kt
│   │   ├── TaxRemittance.kt
│   │   ├── BusinessPayBill.kt
│   │   ├── BusinessBuyGoods.kt
│   │   ├── BillManager.kt
│   │   ├── B2BExpressCheckOut.kt
│   │   ├── B2CAccountTopUp.kt
│   │   └── MpesaRatiba.kt
│   ├── repository/
│   │   └── DarajaRepository.kt          # Repository interface
│   ├── service/
│   │   └── DarajaService.kt             # Utility functions
│   └── usecase/
│       ├── InitiateSTKPushUseCase.kt
│       ├── QuerySTKStatusUseCase.kt
│       ├── RegisterC2BURLUseCase.kt
│       ├── SimulateC2BUseCase.kt
│       ├── InitiateB2CUseCase.kt
│       ├── GenerateDynamicQRUseCase.kt
│       ├── CheckAccountBalanceUseCase.kt
│       ├── QueryTransactionStatusUseCase.kt
│       ├── ReverseTransactionUseCase.kt
│       ├── RemitTaxUseCase.kt
│       ├── ProcessBusinessPayBillUseCase.kt
│       ├── ProcessBusinessBuyGoodsUseCase.kt
│       ├── ProcessBillManagerUseCase.kt
│       ├── ProcessB2BExpressCheckOutUseCase.kt
│       ├── ProcessB2CAccountTopUpUseCase.kt
│       └── ProcessMpesaRatibaUseCase.kt
│
├── data/
│   ├── dto/
│   │   ├── DarajaModels.kt              # Common DTOs
│   │   ├── request/                     # Request DTOs
│   │   └── response/                    # Response DTOs
│   ├── network/
│   │   ├── AuthService.kt               # Authentication service
│   │   ├── DarajaApiCallService.kt      # API call service
│   │   ├── DarajaApiService.kt          # API endpoints
│   │   └── dataResultSafeApiCall.kt     # Safe API call wrapper
│   ├── remote/
│   │   ├── DarajaRemoteDataSource.kt    # Remote data source interface
│   │   └── DarajaRemoteDataSourceImpl.kt # Remote data source implementation
│   └── repository/
│       └── DarajaRepositoryImpl.kt      # Repository implementation
│
├── presentation/
│   ├── ui/                              # Compose UI screens
│   │   ├── STKPushScreen.kt
│   │   ├── STKQueryScreen.kt
│   │   ├── C2BScreen.kt
│   │   ├── C2BRegisterScreen.kt
│   │   ├── B2CScreen.kt
│   │   ├── DynamicQRScreen.kt
│   │   ├── TransactionStatusScreen.kt
│   │   ├── AccountBalanceScreen.kt
│   │   ├── ReversalsScreen.kt
│   │   ├── TaxRemittanceScreen.kt
│   │   ├── BusinessPayBillScreen.kt
│   │   ├── BusinessBuyGoodsScreen.kt
│   │   ├── BillManagerScreen.kt
│   │   ├── B2BExpressCheckOutScreen.kt
│   │   ├── B2CAccountTopUpScreen.kt
│   │   ├── MpesaRatibaScreen.kt
│   │   └── OtherOperationsScreen.kt
│   └── viewmodel/
│       └── MpesaViewModel.kt            # Main ViewModel
│
└── di/                                  # Dependency Injection
    ├── AppModule.kt
    ├── DataModule.kt
    ├── DomainModule.kt
    ├── PresentationModule.kt
    ├── NetworkModule.kt
    ├── DarajaModule.kt
    └── DarajaApiCallModule.kt
```

## 🚀 Getting Started

### Prerequisites

- **JDK 11 or higher**
- **Android Studio Hedgehog (2023.1.1) or newer** (for Android/iOS development)
- **Xcode 14+** (for iOS development, macOS only)
- **Daraja API Credentials** from Safaricom Developer Portal

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/daraja-compose-app.git
   cd daraja-compose-app
   ```

2. **Configure API Credentials**

   The app uses **BuildKonfig** to securely manage API credentials. Credentials are loaded from `local.properties` and are never committed to Git.

   Create a `local.properties` file in the root directory:
   ```bash
   cp local.properties.example local.properties
   ```

   Edit `local.properties` and add your Daraja API credentials:
   ```properties
   daraja.client.id=your_client_id_here
   daraja.client.secret=your_client_secret_here
   ```

   **Important**: Never commit `local.properties` to Git. It's already added to `.gitignore`.

3. **Generate BuildKonfig**

   BuildKonfig will automatically generate the `BuildKonfig` object during build. If you need to manually trigger it:
   ```bash
   ./gradlew :composeApp:generateBuildKonfig
   ```

4. **Sync Project**

   Open the project in Android Studio and sync the Gradle files.

### Build and Run

#### Android

```bash
# Build debug APK
./gradlew :composeApp:assembleDebug

# Run on connected device/emulator
./gradlew :composeApp:installDebug
```

Or use Android Studio's run configuration.

#### Desktop (JVM)

```bash
# Run desktop application
./gradlew :composeApp:run
```

Or use Android Studio's run configuration for Desktop.

#### iOS

1. Open the project in Android Studio
2. Select the iOS target
3. Build and run from Android Studio (which will sync with Xcode)
   
Or manually:
```bash
# Open in Xcode
open iosApp/iosApp.xcodeproj

# Build and run from Xcode
```

## 🔐 Credential Management

This project uses **BuildKonfig** to securely manage API credentials:

- **BuildKonfig Plugin**: `com.codingfeline.buildkonfig` version `0.17.1`
- **Configuration**: Credentials are read from `local.properties` file
- **Access**: Use `BuildKonfig.CLIENT_ID` and `BuildKonfig.CLIENT_SECRET` in code
- **Security**: `local.properties` is excluded from Git via `.gitignore`

### Usage in Code

```kotlin
import com.chacha.darajacmp.BuildKonfig

// Access credentials
val clientId = BuildKonfig.CLIENT_ID
val clientSecret = BuildKonfig.CLIENT_SECRET
```

All UI screens and services use `BuildKonfig` to access credentials dynamically, ensuring they are never hardcoded.

## 🛠️ Technologies Used

### Core Technologies

- **Kotlin Multiplatform** - Code sharing across platforms
- **Jetpack Compose Multiplatform** - Modern declarative UI
- **Material Design 3** - Modern UI components
- **Ktor Client** - HTTP client for API calls
- **Kotlinx Serialization** - JSON serialization/deserialization
- **Koin** - Dependency injection framework
- **Coroutines** - Asynchronous programming
- **BuildKonfig** - Secure configuration management

### Libraries

- **Ktor Client** (`3.3.0`)
  - `ktor-client-core`
  - `ktor-client-content-negotiation`
  - `ktor-client-logging`
  - `ktor-client-okhttp` (Android/Desktop)
  - `ktor-client-darwin` (iOS)

- **Koin** (`4.1.1`)
  - `koin-core`
  - `koin-compose`
  - `koin-android` (Android)

- **Compose Multiplatform** (`1.9.0`)
  - UI components
  - Material 3
  - Resources

- **Kotlinx**
  - `kotlinx-serialization-json`
  - `kotlinx-datetime`
  - `kotlinx-coroutines`

## 📋 API Coverage

The application provides full coverage for the Daraja M-Pesa API:

### STK Push Operations
- Initiate STK Push
- Query STK Push Status

### Customer to Business (C2B)
- Register C2B URLs
- Simulate C2B transactions

### Business to Customer (B2C)
- Initiate B2C transfers
- B2C Account Top Up

### Business to Business (B2B)
- Business Pay Bill
- Business Buy Goods
- Tax Remittance
- B2B Express Checkout

### Transaction Management
- Query Transaction Status
- Reverse Transaction
- Account Balance Query

### Other Operations
- Dynamic QR Code Generation
- Bill Manager
- M-Pesa Ratiba (Standing Orders)

## 🏛️ Dependency Injection

The project uses **Koin** for dependency injection with the following modules:

- **AppModule** - Application-level dependencies
- **NetworkModule** - Network configuration (Ktor client)
- **DataModule** - Data layer dependencies (repositories, data sources)
- **DomainModule** - Domain layer dependencies (use cases, services)
- **PresentationModule** - Presentation layer dependencies (ViewModels)
- **DarajaModule** - Daraja API specific configuration
- **DarajaApiCallModule** - API call service configuration

## 🔄 Data Flow

1. **User Interaction** → UI Screen
2. **UI Screen** → ViewModel
3. **ViewModel** → Use Case
4. **Use Case** → Repository Interface
5. **Repository Implementation** → Remote Data Source
6. **Remote Data Source** → API Service (Ktor Client)
7. **API Response** → DTO → Domain Entity → Use Case → ViewModel → UI

This unidirectional data flow ensures:
- Testability at each layer
- Clear separation of concerns
- Easy to maintain and extend

## 📝 Notes

### Environment Configuration

The app is configured to use the **Sandbox** environment by default. To switch to production:

1. Update `daraja.environment` in `gradle.properties` or `local.properties`
2. Ensure production credentials are set in `local.properties`
3. Rebuild the project

### Callback URLs

Update the callback URLs in `DarajaConfig` or configure them per operation according to your backend infrastructure.

### Phone Number Format

The app handles Kenya phone numbers in multiple formats:
- `254XXXXXXXXX` (international format)
- `07XXXXXXXX` (local format with leading 0)
- `7XXXXXXXX` (local format without leading 0)

All formats are automatically normalized to the international format (`254XXXXXXXXX`).

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🔗 Resources

- [Safaricom Daraja API Documentation](https://developer.safaricom.co.ke/)
- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Jetpack Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Ktor Documentation](https://ktor.io/)
- [Koin Documentation](https://insert-koin.io/)

## 📞 Support

For issues and questions:
- Open an issue on GitHub
- Check the Safaricom Developer Portal for API-related issues

---

**Note**: This is a demonstration application. For production use, ensure proper security measures, error handling, and compliance with Safaricom's terms of service.
