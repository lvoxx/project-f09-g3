package com.cp2396f09_sem4_grp3.onmart_service.helper.utils;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;

import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

public class ResourceReader {

    private ResourceReader(){}

    public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), Charset.forName("UTF-8"))) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}