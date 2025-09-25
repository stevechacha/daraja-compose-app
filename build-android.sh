#!/bin/bash

# Daraja M-Pesa Android Build Script
# This script optimizes Gradle builds and prevents increasing build times

echo "🚀 Building Daraja M-Pesa Android App..."

# Set Android SDK path
export ANDROID_HOME=/Users/stephenchacha/Library/Android/sdk

# Clean previous builds
echo "🧹 Cleaning previous builds..."
./gradlew clean

# Build debug APK
echo "🔨 Building debug APK..."
./gradlew assembleDebug

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    
    # Install on connected device
    echo "📱 Installing on connected device..."
    ./gradlew installDebug
    
    if [ $? -eq 0 ]; then
        echo "✅ App installed successfully!"
        echo "📱 Check your Android device for the Daraja M-Pesa app"
    else
        echo "❌ Installation failed. Make sure you have a device connected."
        echo "💡 Connect your Android device via USB or start an emulator"
    fi
else
    echo "❌ Build failed. Check the error messages above."
fi

echo "🏁 Build process complete!"
