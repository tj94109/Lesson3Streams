package com.terrance.Lesson3Streams;

import java.util.regex.Pattern;


public class RegexTester {

    public static void main(String args[]){

        String regex = ".*0{8}[0-9A-fa-f]{8}.*";
        CharSequence input = "11>000000005c020000<11";
        if (Pattern.matches(regex, input)) {
            System.out.println("YES");
        } else {
            System.out.println("no");
        }
    }
}
