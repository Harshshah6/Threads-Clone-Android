package com.harsh.shah.threads.clone.database;

import android.util.Log;

import java.io.File;

import io.github.techgnious.IVCompressor;
import io.github.techgnious.dto.ImageFormats;
import io.github.techgnious.dto.ResizeResolution;
import io.github.techgnious.exception.ImageException;

public class StorageHelper {

    private static final String TAG = "StorageHelper";

    private static StorageHelper instance;
    public static StorageHelper getInstance() {
        if (instance == null) {
            instance = new StorageHelper();
        }
        return instance;
    }

    private final IVCompressor compressor;

    private StorageHelper() {
        compressor = new IVCompressor();
    }

    public void resizeImage(File file){
        try {
            String path = compressor.resizeAndSaveImageToAPath(file, ImageFormats.JPG, file.getParent()+"/resized_"+file.getName(), ResizeResolution.IMAGE_DEFAULT);
            Log.i(TAG, "resizeImage: " + path);
        } catch (ImageException e) {
            Log.e(TAG, "resizeImage: ", e);
        }
    }

}
