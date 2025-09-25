# Daraja M-Pesa API Setup Guide

This guide will help you set up the Daraja M-Pesa API integration in your Compose Multiplatform app.

## Prerequisites

1. **Daraja API Account**: Register at [https://developer.safaricom.co.ke/](https://developer.safaricom.co.ke/)
2. **Kotlin Multiplatform Project**: This app is built with Compose Multiplatform
3. **M-Pesa Developer Account**: You need to have an M-Pesa business account

## Getting Started

### 1. Register for Daraja API

1. Go to [Safaricom Developer Portal](https://developer.safaricom.co.ke/)
2. Create an account and log in
3. Navigate to "My Apps" and create a new app
4. Note down your:
   - Consumer Key (Client ID)
   - Consumer Secret (Client Secret)
   - Business Short Code
   - Pass Key

### 2. Configure Your App

Update the configuration in `DarajaConfig.kt` with your actual credentials:

```kotlin
object SampleCredentials {
    const val SAMPLE_CLIENT_ID = "your_actual_client_id"
    const val SAMPLE_CLIENT_SECRET = "your_actual_client_secret"
    const val SAMPLE_BUSINESS_SHORT_CODE = "your_business_short_code"
    const val SAMPLE_PASS_KEY = "your_actual_pass_key"
}
```

### 3. Set Up Callback URLs

You need to set up callback URLs that will receive payment notifications. These URLs should be publicly accessible and return appropriate responses.

Example callback URLs:
- STK Push: `https://your-domain.com/mpesa/stkpush/callback`
- C2B Confirmation: `https://your-domain.com/mpesa/c2b/confirmation`
- C2B Validation: `https://your-domain.com/mpesa/c2b/validation`

### 4. Test Phone Numbers

For sandbox testing, use these test phone numbers:
- `254708374149` (Default test number)
- `254711000000` - `254711000999` (Range of test numbers)

## API Features

### STK Push (Customer Pay Bill Online)
- Initiate payment requests directly from customer's phone
- Customer receives M-Pesa prompt on their phone
- Real-time payment processing

### C2B (Customer to Business)
- Register validation and confirmation URLs
- Simulate customer payments for testing
- Handle customer-initiated payments

### B2C (Business to Customer)
- Send money from business to customers
- Salary payments, refunds, etc.
- Bulk payment capabilities

### Other Operations
- Account Balance queries
- Transaction Status queries
- Payment Reversals

## Security Best Practices

1. **Never commit credentials to source control**
2. **Use environment variables for production**
3. **Implement proper error handling**
4. **Validate all inputs**
5. **Use HTTPS for all callbacks**
6. **Implement proper logging (without sensitive data)**

## Testing

### Sandbox Environment
- Use sandbox URLs for development
- Test with provided test phone numbers
- All amounts are virtual in sandbox

### Production Environment
- Switch to production URLs
- Use real phone numbers
- Real money transactions

## Common Issues

### Authentication Errors
- Verify your Client ID and Client Secret
- Check if your app is approved for production
- Ensure you're using the correct environment URLs

### STK Push Issues
- Verify Business Short Code and Pass Key
- Check callback URL accessibility
- Ensure phone number is in correct format (254XXXXXXXXX)

### C2B Issues
- Verify validation and confirmation URLs are accessible
- Check if URLs return proper HTTP status codes
- Ensure short code is registered for C2B

## Phone Number Format

Kenya phone numbers should be in the format: `254XXXXXXXXX`
- Remove any spaces, dashes, or parentheses
- Always start with country code 254
- Total length should be 12 digits

## Amount Limits

- Minimum amount: 1 KES
- Maximum STK Push: 70,000 KES
- Maximum B2C: 150,000 KES

## Support

- [Safaricom Developer Documentation](https://developer.safaricom.co.ke/docs)
- [Daraja API Reference](https://developer.safaricom.co.ke/docs)
- [M-Pesa Developer Portal](https://developer.safaricom.co.ke/)

## Sample Usage

```kotlin
// Initialize the ViewModel
val viewModel: MpesaViewModel = viewModel()

// Initiate STK Push
viewModel.initiateSTKPush(
    clientId = "your_client_id",
    clientSecret = "your_client_secret",
    businessShortCode = "your_short_code",
    passKey = "your_pass_key",
    amount = 100,
    phoneNumber = "254708374149",
    callBackURL = "https://your-callback-url.com",
    accountReference = "Test Payment"
)
```

## Next Steps

1. Test all API endpoints in sandbox
2. Set up your callback URLs
3. Implement proper error handling
4. Add logging and monitoring
5. Prepare for production deployment
6. Get your app approved by Safaricom
7. Switch to production environment

Remember to always test thoroughly in the sandbox environment before moving to production!
