package com.example;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyRJava {
    public static void main(String[] args) {
        String src = "app/build/generated/source/r/debug/io/github/simcards/simcards/R.java";
        String dst = "desktop/src/main/java/io/github/simcards/desktop/R.java";
        try {
            Charset charset = StandardCharsets.UTF_8;
            String content = new String(Files.readAllBytes(Paths.get(src)), charset);
            content = content.replaceAll("package io.github.simcards.simcards", "package io.github.simcards.desktop");
            Files.write(Paths.get(dst), content.getBytes(charset));
        } catch (IOException e) {
            System.out.println("CopyRJava failed...");
            e.printStackTrace();
            return;
        }
        System.out.println("CopyRJava succeeded");
    }
}
