package com.tweakcraft.parsers;

public class BasicParser {
    public static String removeBrackets(String line) {
        if (line.length() > 2 && line.charAt(0) == '[' && line.charAt(line.length() - 1) == ']') {
            return line.substring(1, line.length() - 1);
        } else {
            return line;
        }
    }
}
