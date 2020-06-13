package com.restartindia.naukri.util;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class Utils {
    public static Uri compressImage(Context context, Uri imageUri) {
        File file = new File(imageUri.getPath());
        try {
            File postImageFile = new Compressor(context)
                    .setMaxHeight(500)
                    .setMaxWidth(500)
                    .setQuality(50)
                    .compressToFile(file);
            return Uri.fromFile(postImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
