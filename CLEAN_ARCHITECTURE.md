# Clean Architecture Structure

This project now follows Clean Architecture principles with proper separation of concerns across layers.

## Project Structure

```
composeApp/src/commonMain/kotlin/com/chacha/darajacmp/
├── domain/                          # Domain Layer (Business Logic)
│   ├── model/                       # Domain Entities (Pure business models)
│   │   ├── Result.kt               # Result sealed class
│   │   ├── STKPush.kt
│   │   ├── STKQuery.kt
│   │   ├── C2BRegister.kt
│   │   ├── B2C.kt
│   │   └── ... (other domain entities)
│   ├── repository/                  # Repository Interfaces
│   │   └── DarajaRepository.kt     # Repository contract
│   └── usecase/                     # Use Cases (Business logic)
│       └── InitiateSTKPushUseCase.kt
│
├── data/                            # Data Layer
│   ├── dto/                         # Data Transfer Objects
│   │   ├── request/                 # Request DTOs
│   │   │   ├── STKPushRequest.kt
│   │   │   ├── C2BRegisterRequest.kt
│   │   │   └── ... (other request DTOs)
│   │   ├── response/                # Response DTOs
│   │   │   ├── STKPushResponse.kt
│   │   │   ├── B2CResponse.kt
│   │   │   └── ... (other response DTOs)
│   │   └── DarajaModels.kt          # Auth and common DTOs
│   ├── network/                     # Network Services (API layer)
│   │   ├── AuthService.kt           # Authentication service
│   │   ├── DarajaApiService.kt      # Main API service
│   │   ├── DarajaApiCallService.kt  # API call service
│   │   └── dataResultSafeApiCall.kt # Safe API call utilities
│   ├── remote/                      # Remote Data Sources
│   │   ├── DarajaRemoteDataSource.kt        # Data source interface
│   │   └── DarajaRemoteDataSourceImpl.kt    # Implementation
│   └── repository/                  # Repository Implementations
│       └── DarajaRepositoryImpl.kt  # Repository implementation
│
├── presentation/                    # Presentation Layer
│   ├── viewmodel/
│   │   └── MpesaViewModel.kt       # ViewModel with UI state
│   └── ui/                          # (UI screens remain in ui/ for now)
│
│
├── di/                              # Dependency Injection
│   ├── DataModule.kt               # Data layer dependencies
│   ├── DomainModule.kt             # Domain layer dependencies
│   ├── PresentationModule.kt       # Presentation layer dependencies
│   ├── AppModule.kt                # App-level dependencies
│   └── DarajaModule.kt             # Daraja-specific config
│
└── ui/                              # UI Screens (Compose)
    ├── STKPushScreen.kt
    ├── STKQueryScreen.kt
    └── ... (other screens)
```

## Architecture Layers

### 1. Domain Layer
**Purpose**: Contains business logic and entities

- **Entities**: Pure Kotlin data classes representing business concepts
  - No framework dependencies
  - No serialization annotations
  - Business-focused naming

- **Repository Interfaces**: Contracts for data operations
  - Define what operations are needed
  - No implementation details

- **Use Cases**: Single-purpose business operations
  - Encapsulate business logic
  - Orchestrate repository calls
  - Example: `InitiateSTKPushUseCase`

### 2. Data Layer
**Purpose**: Implements data sources and repositories

- **Remote Data Source**: 
  - Interface: `DarajaRemoteDataSource`
  - Implementation: `DarajaRemoteDataSourceImpl`
  - Handles API calls and converts DTOs to domain entities

- **Repository Implementation**: 
  - `DarajaRepositoryImpl` implements `DarajaRepository`
  - Delegates to remote data sources
  - Can add caching, local storage, etc.

- **Network Services**: API services in `data/network/`
  - `AuthService`: Handles authentication
  - `DarajaApiService`: Main API service with all endpoints
  - `DarajaApiCallService`: API call service wrapper
  - Low-level HTTP communication

- **DTOs**: Data Transfer Objects (in `data/dto/`)
  - **Request DTOs**: `data/dto/request/` - Request models for API calls
  - **Response DTOs**: `data/dto/response/` - Response models from API
  - Used for API communication (serialization/deserialization)
  - Have serialization annotations
  - Converted to domain entities in data layer

### 3. Presentation Layer
**Purpose**: UI and state management

- **ViewModels**: 
  - Manage UI state
  - Use use cases or repositories directly
  - Handle user interactions

- **UI State**: 
  - Defined in ViewModels
  - Uses domain entities (not DTOs)

## Dependency Flow

```
UI → ViewModel → UseCase → Repository Interface
                              ↑
                              |
                         Repository Implementation
                              ↑
                              |
                         Remote Data Source
                              ↑
                              |
                         API Service (Network Layer)
```

## Key Principles

1. **Dependency Rule**: Inner layers don't know about outer layers
   - Domain layer has no dependencies on data or presentation
   - Data layer depends on domain, not presentation
   - Presentation depends on domain and uses data through domain interfaces

2. **Separation of Concerns**: Each layer has a single responsibility
   - Domain: Business logic
   - Data: Data management
   - Presentation: UI and user interaction

3. **Testability**: Each layer can be tested independently
   - Domain: Pure business logic tests
   - Data: Repository and data source tests
   - Presentation: ViewModel tests with mocked repositories

## Usage Example

```kotlin
// ViewModel uses UseCase or Repository
class MpesaViewModel(
    private val initiateSTKPushUseCase: InitiateSTKPushUseCase,
    private val repository: DarajaRepository
) {
    fun initiateSTKPush(...) {
        viewModelScope.launch {
            when (val result = initiateSTKPushUseCase(...)) {
                is Result.Success -> // Update UI
                is Result.Error -> // Show error
            }
        }
    }
}
```

## Migration Notes

- **ViewModels**: 
  - Old `viewmodel/MpesaViewModel.kt` → New `presentation/viewmodel/MpesaViewModel.kt`
  - Old `viewmodel/MpesaUiState.kt` → Now in `presentation/viewmodel/MpesaViewModel.kt`

- **DTOs Reorganization**:
  - Old `models/` → New `data/dto/request/` (Request DTOs)
  - Old `network/response/` → New `data/dto/response/` (Response DTOs)
  - All DTOs moved to data layer with proper package structure

- **Domain Entities**: 
  - Pure business models in `domain/model/`
  - No serialization, no framework dependencies
  - Used throughout the application (presentation layer, use cases, etc.)

## Next Steps

1. Move UI screens to `presentation/ui/` (optional)
2. Create more use cases for other operations
3. Add local data sources for caching (optional)
4. Add error handling and mapping layers
5. Add unit tests for each layer

