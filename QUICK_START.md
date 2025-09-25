# 🚀 Quick Start Guide - Daraja M-Pesa Integration

## ✅ Your Credentials Are Ready!

Your Daraja API credentials have been integrated into the app:

- **Client ID**: `xkS5JzqHgNItCXl29G9PWqdQqAH5Tb2cVxU1pi83GFHHtGSZ`
- **Client Secret**: `7Xo6rVHVdQxXfnU8sSR77Af0ibU2RaPJGXAhouaGHA3dnuq1e1seZKSt5b25bOpg`
- **Environment**: Sandbox (for testing)

## 🎯 How to Test

### 1. **Validate Your Credentials**
- Run the app
- Go to the "STK Push" tab
- Click "🔍 Validate Credentials" button
- This will check if your credentials are properly formatted

### 2. **Test STK Push (Recommended)**
- All form fields are pre-populated with your credentials
- Amount is set to 100 KES (test amount)
- Phone number is set to test number: `254708374149`
- Click "Initiate STK Push" to test

### 3. **What to Expect**
- ✅ **Success**: You'll see a success message with Merchant Request ID and Checkout Request ID
- ❌ **Error**: Check the error message for details

## 📱 Test Phone Numbers

Use these sandbox test numbers:
- `254708374149` (Default test number - pre-filled in app)
- `254711000000` - `254711000999` (Range of test numbers)

## 💰 Test Amounts

- **Minimum**: 1 KES
- **Maximum**: 70,000 KES
- **Recommended for testing**: 100 KES (pre-filled)

## 🔧 Troubleshooting

### Common Issues:

1. **Authentication Failed**
   - Check if your credentials are correct
   - Ensure you're using sandbox URLs (not production)

2. **STK Push Failed**
   - Verify the test phone number format: `254XXXXXXXXX`
   - Check if the amount is within limits (1-70,000 KES)
   - Ensure callback URL is accessible (use a test URL for now)

3. **Network Issues**
   - Check your internet connection
   - Verify you can access `https://sandbox.safaricom.co.ke`

## 🌐 Callback URLs

For testing, you can use these placeholder URLs:
- **STK Push Callback**: `https://your-callback-url.com`
- **C2B Confirmation**: `https://your-confirmation-url.com`
- **C2B Validation**: `https://your-validation-url.com`

**Note**: These are just placeholders. For production, you'll need real, publicly accessible URLs.

## 📊 Understanding Responses

### STK Push Success Response:
```json
{
  "MerchantRequestID": "29115-34620561-1",
  "CheckoutRequestID": "ws_CO_19122023171223456789",
  "ResponseCode": "0",
  "ResponseDescription": "Success. Request accepted for processing",
  "CustomerMessage": "Success. Request accepted for processing"
}
```

### Common Response Codes:
- `0`: Success
- `1`: Request failed
- `400`: Bad request
- `401`: Unauthorized

## 🎉 Next Steps

1. **Test the integration** using the pre-filled credentials
2. **Set up callback URLs** for production use
3. **Test with different amounts** and phone numbers
4. **Implement error handling** in your production app
5. **Get production approval** from Safaricom when ready

## 📞 Support

If you encounter issues:
1. Check the error messages in the app
2. Verify your credentials at [Safaricom Developer Portal](https://developer.safaricom.co.ke/)
3. Review the [Daraja API Documentation](https://developer.safaricom.co.ke/docs)

## 🔒 Security Note

- These are your actual credentials - keep them secure
- For production, use environment variables
- Never commit credentials to public repositories
- Rotate credentials regularly

---

**Ready to test? Run the app and try the STK Push functionality! 🚀**
