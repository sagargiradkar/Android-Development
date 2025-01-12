package com.devdroid.screenshotapp;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CloudinaryUploader {

    private final Cloudinary cloudinary;

    // Constructor to initialize Cloudinary with configuration
    public CloudinaryUploader(String cloudName, String apiKey, String apiSecret) {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

    // Method to upload a file to Cloudinary
    public Map uploadFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist at path: " + filePath);
        }

        return cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
    }

    // Method to get the URL of the uploaded file
    public String getUrl(Map uploadResult) {
        return (String) uploadResult.get("url");
    }
}
