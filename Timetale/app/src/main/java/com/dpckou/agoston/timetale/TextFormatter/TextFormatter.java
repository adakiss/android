package com.dpckou.agoston.timetale.TextFormatter;

import android.arch.persistence.room.util.StringUtil;

/**
 * Created by agoston on 2017.11.17..
 */

public class TextFormatter {

    public static final int CAPITALIZE_FIRST = 1;
    public static final int DIVIDE_BY_CAPS = 2;
    public static final int DIVIDE_BY_UNDERSCORE = 3;
    public static final int SEPARATE_NUMBERS = 4;

    private static boolean isNumber(char c){
        try{
            int i = Integer.parseInt(Character.toString(c));
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private static boolean isCapitalLetter(char c){
        return (c >= 'A' && c <= 'Z');
    }

    public static String capitalizeFirst(String s){
        if(s == null || s == "")return null;
        String first = Character.toString(s.charAt(0)).toUpperCase();
        return first + s.substring(1,s.length());
    }

    public static String divideByCaps(String s) {
        if(s == null || s == "")return null;
        String r = "";
        char[] _s = s.toCharArray();
        r+=_s[0];
        for(int i = 1; i < _s.length; i++){
            char c = _s[i];
            if(isCapitalLetter(c)){
                r += ' ';
            }
            r += c;
        }
        return r;
    }

    public static String divideByUnderscore(String s){
        if(s == null || s == "")return null;
        String r = "";
        char[] _s = s.toCharArray();
        for(int i = 0; i < _s.length; i++){
            char c = _s[i];
            if(c == '_'){
                c = ' ';
            }
            r += c;
        }
        return r;
    }
    //fucking miss .net predicates here :(
    public static String separateNumbers(String s){
        if(s == null || s == "")return null;
        String r = "";
        char[] _s = s.toCharArray();
        r += _s[0];
        for(int i = 1; i < _s.length; i++){
            if((!isNumber(_s[i-1]) && isNumber(_s[i]))
                    ||
                    (isNumber(_s[i-1]) && !isNumber(_s[i]))){
                r += ' ';
            }
            r += _s[i];
        }
        return r;
    }
    //TODO in c# loop through enums is possible and map the action call by a dictionary. is it here? to eliminate switch.
    public static String multiFormat(String s, int[] formatRules){
        for(int rule : formatRules){
            switch(rule){
                case CAPITALIZE_FIRST:
                    s = capitalizeFirst(s);
                    break;
                case DIVIDE_BY_CAPS:
                    s = divideByCaps(s);
                    break;
                case DIVIDE_BY_UNDERSCORE:
                    s = divideByUnderscore(s);
                    break;
                case SEPARATE_NUMBERS:
                    s = separateNumbers(s);
                    break;
            }
        }
        return s;
    }
}
