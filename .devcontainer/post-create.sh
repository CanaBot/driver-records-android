bash#!/bin/bash

# Set up Android development environment
echo "Setting up Android development environment..."

# Install Android SDK components
sdkmanager --update
sdkmanager "platform-tools" "platforms;android-33" "build-tools;33.0.1"
sdkmanager "system-images;android-33;google_apis;x86_64"

# Create Android Virtual Device
echo "no" | avdmanager create avd -n "Pixel_4_API_33" -k "system-images;android-33;google_apis;x86_64" -d "pixel_4"

# Set environment variables
echo 'export ANDROID_HOME=/usr/local/android-sdk' >> ~/.bashrc
echo 'export PATH=$PATH:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools' >> ~/.bashrc

echo "Android development environment setup complete!"
