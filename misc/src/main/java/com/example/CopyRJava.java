package com.example;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyRJava {
    public static void main(String[] args) {
        // the path of the R.java file we want to copy
        Path src = Paths.get("app/build/generated/source/r/debug/io/github/simcards/simcards/R.java");
        // the path of the new R.java file
        Path dst = Paths.get("desktop/src/main/java/io/github/simcards/desktop/R.java");
        // the names of the class constants we want to copy
        String[] classNames = new String[]{"raw", "drawable"};


        Charset charset = StandardCharsets.UTF_8;
        String content;
        try {
            content = new String(Files.readAllBytes(src), charset);
        } catch (IOException e) {
            System.out.println("CopyRJava failed...");
            e.printStackTrace();
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("package io.github.simcards.desktop;\n\n");
        builder.append("public final class R {\n");
        builder = getClasses(content, builder, classNames);
        builder.append("}\n");
        System.out.println(builder.toString());

        try {
            Files.write(dst, builder.toString().getBytes(charset));
        } catch (IOException e) {
            System.out.println("CopyRJava failed...");
            e.printStackTrace();
            return;
        }

        System.out.println("CopyRJava succeeded");
    }

    public static StringBuilder getClasses(String content, StringBuilder builder, String[] classes) {
        for (int i = 0; i < classes.length; i++) {
            int begin_ind = content.indexOf("public static final class " + classes[i]);
            int end_ind = content.indexOf('}', begin_ind);
            String classStr = content.substring(begin_ind, end_ind + 1);
            builder.append("\t");
            builder.append(classStr);
            builder.append("\n");
        }
        return builder;
    }
}
