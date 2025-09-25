# 🔧 STK Push Troubleshooting Guide

## Common STK Push Issues and Solutions

### 1. **Authentication Issues**

**Problem**: "Failed to get access token" or "Authentication failed"

**Solutions**:
- ✅ Verify your credentials are correct
- ✅ Check if you're using sandbox URLs (not production)
- ✅ Ensure your Daraja app is approved for sandbox testing
- ✅ Check your internet connection

**Debug Steps**:
1. Click "🔍 Validate Credentials" button in the app
2. Check the console output for detailed error messages
3. Verify credentials at [Safaricom Developer Portal](https://developer.safaricom.co.ke/)

### 2. **STK Push Request Issues**

**Problem**: "STK Push failed" or no response

**Common Causes**:
- ❌ Invalid phone number format
- ❌ Amount outside valid range (1-70,000 KES)
- ❌ Invalid business short code
- ❌ Incorrect password generation
- ❌ Network connectivity issues

**Solutions**:

#### Phone Number Format
```
✅ Correct: 254708374149
❌ Wrong: 0708374149, +254708374149, 254 708 374 149
```

#### Amount Validation
```
✅ Valid: 1 KES to 70,000 KES
❌ Invalid: 0 KES, 100,000 KES
```

#### Business Short Code
```
✅ Sandbox: 174379
✅ Production: Your actual business short code
```

### 3. **Network Issues**

**Problem**: Connection timeouts or network errors

**Solutions**:
- ✅ Check internet connection
- ✅ Verify you can access `https://sandbox.safaricom.co.ke`
- ✅ Check firewall settings
- ✅ Try again after a few minutes

### 4. **Response Issues**

**Problem**: Getting response but STK Push not working

**Check Response Codes**:
- `0`: Success - Check your phone for M-Pesa prompt
- `1`: Request failed - Check parameters
- `400`: Bad request - Check request format
- `401`: Unauthorized - Check credentials

### 5. **Phone Not Receiving M-Pesa Prompt**

**Possible Causes**:
- ❌ Using wrong phone number
- ❌ Phone not registered for M-Pesa
- ❌ Using production credentials with test phone
- ❌ Network issues on phone

**Solutions**:
- ✅ Use test phone numbers: 254708374149, 254711000000-254711000999
- ✅ Ensure phone has M-Pesa app installed
- ✅ Check phone network connection
- ✅ Wait a few minutes for prompt

## 🧪 Testing Steps

### Step 1: Validate Credentials
1. Open the app
2. Go to "STK Push" tab
3. Click "🔍 Validate Credentials"
4. Check if all credentials are properly formatted

### Step 2: Test Authentication
1. Click "Initiate STK Push" button
2. Watch console output for authentication messages
3. Look for "✅ Authentication successful!" message

### Step 3: Test STK Push
1. Use test phone number: 254708374149
2. Use test amount: 100 KES
3. Click "Initiate STK Push"
4. Check console for detailed logs
5. Check your phone for M-Pesa prompt

## 📱 Test Phone Numbers (Sandbox)

```
254708374149 - Default test number
254711000000 - 254711000999 - Range of test numbers
```

## 💰 Test Amounts

```
Minimum: 1 KES
Maximum: 70,000 KES
Recommended: 100 KES
```

## 🔍 Debug Information

The app now includes detailed logging. Check the console output for:

- 🔐 Authentication status
- ✅ Token information
- 📱 STK Push request details
- ❌ Error messages with stack traces

## 📞 Support

If issues persist:

1. **Check Console Logs**: Look for detailed error messages
2. **Verify Credentials**: Ensure they're correct and active
3. **Test Network**: Ensure you can access Daraja API
4. **Contact Support**: [Safaricom Developer Support](https://developer.safaricom.co.ke/support)

## 🚀 Quick Fixes

### Reset Everything
```bash
./gradlew clean
./gradlew run
```

### Check Device Connection
```bash
# For Android
adb devices

# For Desktop
# Just run the app normally
```

### Verify API Access
```bash
curl -X GET "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials" \
  -H "Authorization: Basic $(echo -n 'your_client_id:your_client_secret' | base64)"
```

---

**Remember**: This is a sandbox environment - no real money is involved in testing! 🎯
