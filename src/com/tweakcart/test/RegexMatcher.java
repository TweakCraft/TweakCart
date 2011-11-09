package com.tweakcart.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher {
    private static String teststring = "5:6;9|9|7";
    
    public static void main(String[] args){
        Pattern p = Pattern.compile(":");
        Matcher m = p.matcher(teststring);
        
       
       
    }
}
