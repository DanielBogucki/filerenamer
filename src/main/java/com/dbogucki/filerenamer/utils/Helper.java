package com.dbogucki.filerenamer.utils;

import java.io.File;

public class Helper {
    public static String combinePaths(String... paths) {
        if (paths.length == 0) {
            return "";
        }

        StringBuffer combined = new StringBuffer(paths[0]);

        int i = 1;
        while (i < paths.length) {
            combined.append(File.separator);
            combined.append(paths[i]);
            ++i;
        }

        return combined.toString();
    }

}
