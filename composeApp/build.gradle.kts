import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm()

    sourceSets {
        commonMain {
            kotlin.srcDir("build/generated/kotlin")
            kotlin.srcDir("build/buildkonfig/commonMain")
        }
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core.ktx)
            implementation(libs.ktor.client.okhttp)
            implementation("io.insert-koin:koin-android:4.1.0")

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.ktor.client.core)
            implementation(libs.bundles.ktor.client)
            implementation(libs.kotlinx.datetime)
            implementation(libs.bundles.ktor.serialization)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation("io.insert-koin:koin-compose")
            implementation("io.insert-koin:koin-compose-viewmodel")
            implementation("io.insert-koin:koin-compose-viewmodel-navigation")
            
            implementation(libs.touchlab.kermit)


        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.ktor.client.okhttp)
        }
    }
}

android {
    namespace = "com.chacha.darajacmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.chacha.darajacmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

// Task to generate DarajaConfig from gradle.properties
tasks.register("generateDarajaConfig") {
    group = "build"
    description = "Generate DarajaConfig.kt from gradle.properties"
    
    // Disable configuration cache for this task
    notCompatibleWithConfigurationCache("Uses file operations that are not compatible with configuration cache")
    
    val outputDir = layout.buildDirectory.dir("generated/kotlin/com/chacha/darajacmp/utils")
    
    outputs.dir(outputDir)
    
    doLast {
        val configDir = outputDir.get().asFile
        configDir.mkdirs()
        
        val configFile = configDir.resolve("DarajaConfig.kt")
        
        val environment = project.findProperty("daraja.environment") as String? ?: "sandbox"
        val clientId = project.findProperty("daraja.$environment.client.id") as String? ?: "xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ"
        val clientSecret = project.findProperty("daraja.$environment.client.secret") as String? ?: "7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg"
        val businessShortCode = project.findProperty("daraja.$environment.business.shortcode") as String? ?: "174379"
        val passKey = project.findProperty("daraja.$environment.passkey") as String? ?: "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"
        val testPhone = project.findProperty("daraja.sandbox.test.phone") as String? ?: "254708374149"
        
        // Compute URLs directly to avoid string interpolation issues
        val sandboxBaseUrl = "https://sandbox.safaricom.co.ke"
        val productionBaseUrl = "https://api.safaricom.co.ke"
        
        configFile.writeText("""
package com.chacha.darajacmp.utils

/**
 * Daraja API Configuration
 * 
 * This file is auto-generated from gradle.properties
 * DO NOT EDIT MANUALLY - Update gradle.properties instead
 * 
 * Environment: $environment
 */

object DarajaConfig {
    // Sandbox URLs (for testing)
    const val SANDBOX_BASE_URL = "$sandboxBaseUrl"
    const val SANDBOX_AUTH_URL = "$sandboxBaseUrl/oauth/v1/generate?grant_type=client_credentials"
    const val SANDBOX_STK_PUSH_URL = "$sandboxBaseUrl/mpesa/stkpush/v1/processrequest"
    const val SANDBOX_STK_QUERY_URL = "$sandboxBaseUrl/mpesa/stkpushquery/v1/query"
    const val SANDBOX_C2B_REGISTER_URL = "$sandboxBaseUrl/mpesa/c2b/v1/registerurl"
    const val SANDBOX_C2B_SIMULATE_URL = "$sandboxBaseUrl/mpesa/c2b/v1/simulate"
    const val SANDBOX_B2C = "$sandboxBaseUrl/mpesa/b2c/v3/paymentrequest"
    const val SANDBOX_DYNAMIC_QR_CODE = "$sandboxBaseUrl/mpesa/qrcode/v1/generate"
    const val SANDBOX_ACCOUNT_BALANCE_URL = "$sandboxBaseUrl/mpesa/accountbalance/v1/query"
    const val SANDBOX_TRANSACTION_STATUS_URL = "$sandboxBaseUrl/mpesa/transactionstatus/v1/query"
    const val SANDBOX_REVERSAL_URL = "$sandboxBaseUrl/mpesa/reversal/v1/request"
    const val SANDBOX_TAX_REMITTANCE = "$sandboxBaseUrl/mpesa/b2b/v1/remittax"
    const val SANDBOX_BUSINESS_PAY_BILL = "$sandboxBaseUrl/mpesa/b2b/v1/paymentrequest"
    const val SANDBOX_BUSINESS_BUY_GOODS = "$sandboxBaseUrl/mpesa/b2b/v1/paymentrequest"
    const val SANDBOX_BILL_MANAGER = "$productionBaseUrl/v1/billmanager-invoice/optin"
    const val SANDBOX_B2C_ACCOUNT_TOP_UP = "$sandboxBaseUrl/mpesa/b2b/v1/paymentrequest"
    const val SANDBOX_M_RATIBA = "$sandboxBaseUrl/standingorder/v1/createStandingOrderExternal"
    const val SANDBOX_B2B_EXPRESS_CHECKOUT = "$sandboxBaseUrl/v1/ussdpush/get-msisdn"

    // Production URLs (for live environment)
    const val PRODUCTION_BASE_URL = "$productionBaseUrl"
    const val PRODUCTION_AUTH_URL = "$productionBaseUrl/oauth/v1/generate?grant_type=client_credentials"
    const val PRODUCTION_STK_PUSH_URL = "$productionBaseUrl/mpesa/stkpush/v1/processrequest"
    const val PRODUCTION_STK_QUERY_URL = "$productionBaseUrl/mpesa/stkpushquery/v1/query"
    const val PRODUCTION_C2B_REGISTER_URL = "$productionBaseUrl/mpesa/c2b/v1/registerurl"
    const val PRODUCTION_C2B_SIMULATE_URL = "$productionBaseUrl/mpesa/c2b/v1/simulate"
    const val PRODUCTION_B2C_URL = "$productionBaseUrl/mpesa/b2c/v1/paymentrequest"
    const val PRODUCTION_ACCOUNT_BALANCE_URL = "$productionBaseUrl/mpesa/accountbalance/v1/query"
    const val PRODUCTION_TRANSACTION_STATUS_URL = "$productionBaseUrl/mpesa/transactionstatus/v1/query"
    const val PRODUCTION_REVERSAL_URL = "$productionBaseUrl/mpesa/reversal/v1/request"

    // Common M-Pesa Command IDs
    object CommandIDs {
        const val CUSTOMER_PAY_BILL_ONLINE = "CustomerPayBillOnline"
        const val CUSTOMER_BUY_GOODS_ONLINE = "CustomerBuyGoodsOnline"
        const val SALARY_PAYMENT = "SalaryPayment"
        const val BUSINESS_PAYMENT = "BusinessPayment"
        const val PROMOTION_PAYMENT = "PromotionPayment"
        const val ACCOUNT_BALANCE = "AccountBalance"
        const val TRANSACTION_STATUS_QUERY = "TransactionStatusQuery"
        const val REVERSAL = "TransactionReversal"
        const val C2B_PAY_BILL = "CustomerPayBillOnline"
    }

    // Identifier Types
    object IdentifierTypes {
        const val MSISDN = 1
        const val TILL_NUMBER = 2
        const val SHORT_CODE = 4
    }

    // Response Types for C2B
    object ResponseTypes {
        const val COMPLETED = "Completed"
        const val CANCELLED = "Cancelled"
    }

    // Transaction Types
    object TransactionTypes {
        const val PAY_BILL = "CustomerPayBillOnline"
        const val BUY_GOODS = "CustomerBuyGoodsOnline"
    }

    // Default callback URLs (replace with your actual URLs)
    object DefaultCallbacks {
        const val STK_PUSH_CALLBACK = "https://your-domain.com/mpesa/stkpush/callback"
        const val C2B_CONFIRMATION = "https://your-domain.com/mpesa/c2b/confirmation"
        const val C2B_VALIDATION = "https://your-domain.com/mpesa/c2b/validation"
        const val B2C_RESULT = "https://your-domain.com/mpesa/b2c/result"
        const val B2C_TIMEOUT = "https://your-domain.com/mpesa/b2c/timeout"
        const val ACCOUNT_BALANCE_RESULT = "https://your-domain.com/mpesa/accountbalance/result"
        const val ACCOUNT_BALANCE_TIMEOUT = "https://your-domain.com/mpesa/accountbalance/timeout"
        const val TRANSACTION_STATUS_RESULT = "https://your-domain.com/mpesa/transactionstatus/result"
        const val TRANSACTION_STATUS_TIMEOUT = "https://your-domain.com/mpesa/transactionstatus/timeout"
        const val REVERSAL_RESULT = "https://your-domain.com/mpesa/reversal/result"
        const val REVERSAL_TIMEOUT = "https://your-domain.com/mpesa/reversal/timeout"
    }

    // Your actual Daraja API credentials (loaded from gradle.properties)
    object DarajaCredentials {
        const val CLIENT_ID = "$clientId"
        const val CLIENT_SECRET = "$clientSecret"
        const val BUSINESS_SHORT_CODE = "$businessShortCode"
        const val PASS_KEY = "$passKey"
        const val TEST_PHONE_NUMBER = "$testPhone"
        const val ENVIRONMENT = "$environment"
    }

    // Phone number formats
    object PhoneFormats {
        const val KENYA_PREFIX = "254"
        const val KENYA_FORMAT = "254XXXXXXXXX"

        fun formatKenyaPhone(phone: String): String {
            val cleaned = phone.replace(Regex("[^0-9]"), "")
            return when {
                cleaned.startsWith("254") -> cleaned
                cleaned.startsWith("0") -> "254\${'$'}{cleaned.substring(1)}"
                cleaned.startsWith("7") -> "254\${'$'}cleaned"
                else -> "254\${'$'}cleaned"
            }
        }

        fun isValidKenyaPhone(phone: String): Boolean {
            val formatted = formatKenyaPhone(phone)
            return formatted.matches(Regex("^254[0-9]{9}\${'$'}"))
        }
    }

    // Amount validation
    object AmountLimits {
        const val MIN_AMOUNT = 1
        const val MAX_AMOUNT = 70000
        const val MAX_AMOUNT_B2C = 150000

        fun isValidAmount(amount: Int): Boolean {
            return amount in MIN_AMOUNT..MAX_AMOUNT
        }

        fun isValidB2CAmount(amount: Int): Boolean {
            return amount in MIN_AMOUNT..MAX_AMOUNT_B2C
        }
    }
}
""".trimIndent())
        
        println("✅ Generated DarajaConfig.kt with environment: $environment")
    }
}

// Make sure config is generated before compilation
tasks.named("compileKotlinMetadata") {
    dependsOn("generateDarajaConfig")
}

// Run for Android compilation tasks
tasks.matching { it.name.startsWith("compile") && it.name.contains("Kotlin") }.configureEach {
    dependsOn("generateDarajaConfig")
}

// Helper function to read properties from local.properties or gradle.properties
fun readProperty(propertyName: String, defaultValue: String = ""): String {
    val localProperties = rootProject.file("local.properties")
    val properties = Properties()
    
    if (localProperties.exists()) {
        try {
            localProperties.inputStream().use { properties.load(it) }
            val value = properties.getProperty(propertyName)
            if (value != null && value.isNotBlank()) {
                println("✅ Found $propertyName in local.properties (length: ${value.length})")
                return value
            } else {
                println("⚠️ $propertyName in local.properties is empty, using default")
            }
        } catch (e: Exception) {
            println("⚠️ Error reading local.properties: ${e.message}")
        }
    } else {
        println("⚠️ local.properties not found, using default for $propertyName")
    }
    
    // Try gradle.properties
    val gradleValue = project.findProperty(propertyName) as String?
    if (gradleValue != null && gradleValue.isNotBlank()) {
        println("✅ Found $propertyName in gradle.properties")
        return gradleValue
    }
    
    // Use default
    if (defaultValue.isNotBlank()) {
        println("ℹ️ Using default value for $propertyName")
    }
    return defaultValue
}

// BuildKonfig configuration for secure credentials
buildkonfig {
    packageName = "com.chacha.darajacmp"
    
    // Expose fields as public (not internal) so they can be accessed from common code
    exposeObjectWithName = "BuildKonfig"
    
    // Default configs with fallback values (for desktop builds and other variants)
    defaultConfigs {
        val clientId = readProperty("daraja.client.id", "xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ")
        val clientSecret = readProperty("daraja.client.secret", "7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg")
        buildConfigField(
            type = com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            name = "CLIENT_ID",
            value = "\"$clientId\""
        )
        buildConfigField(
            type = com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            name = "CLIENT_SECRET",
            value = "\"$clientSecret\""
        )
    }
    
    defaultConfigs("debug") {
        val clientId = readProperty("daraja.client.id", "xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ")
        val clientSecret = readProperty("daraja.client.secret", "7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg")
        buildConfigField(
            type = com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            name = "CLIENT_ID",
            value = "\"$clientId\""
        )
        buildConfigField(
            type = com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            name = "CLIENT_SECRET",
            value = "\"$clientSecret\""
        )
    }
    
    defaultConfigs("release") {
        val clientId = readProperty("daraja.client.id", "")
        val clientSecret = readProperty("daraja.client.secret", "")
        buildConfigField(
            type = com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            name = "CLIENT_ID",
            value = "\"$clientId\""
        )
        buildConfigField(
            type = com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            name = "CLIENT_SECRET",
            value = "\"$clientSecret\""
        )
    }
}

compose.desktop {
    application {
        mainClass = "com.chacha.darajacmp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.chacha.darajacmp"
            packageVersion = "1.0.0"
        }
    }
}
