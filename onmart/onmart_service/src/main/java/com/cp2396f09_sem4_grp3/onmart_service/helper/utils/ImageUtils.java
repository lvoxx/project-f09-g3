package com.cp2396f09_sem4_grp3.onmart_service.helper.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import net.coobird.thumbnailator.Thumbnails;

public class ImageUtils {

    private ImageUtils() {
    }

    public static byte[] compressImage(byte[] imageData) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(imageData);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageData.length);
        byte[] tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] imageData) {
        Inflater inflater = new Inflater();
        inflater.setInput(imageData);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageData.length);
        byte[] tmp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public static byte[] resizeImage(byte[] originalImageData, int width, int height) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(new ByteArrayInputStream(originalImageData))
                .size(width, height)
                .keepAspectRatio(true)
                .toOutputStream(outputStream);

        return outputStream.toByteArray();
    }

    public static HttpHeaders imagHeaders(String type) {
        HttpHeaders headers = new HttpHeaders();
        if (type.equals("image/jpeg") || type.equals("image/jpg"))
            headers.setContentType(MediaType.IMAGE_JPEG);
        else
            headers.setContentType(MediaType.IMAGE_PNG);
        return headers;
    }
}
