package com.dpckou.agoston.timetale.TextFormatter;

public class TextFormatter {

    private static final int CAPITALIZE_FIRST = 1;
    private static final int DIVIDE_BY_CAPS = 2;
    private static final int DIVIDE_BY_UNDERSCORE = 3;
    private static final int SEPARATE_NUMBERS = 4;

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
        if(s == null || s.equals(""))return null;
        String first = Character.toString(s.charAt(0)).toUpperCase();
        return first + s.substring(1,s.length());
    }

    public static String divideByCaps(String s) {
        if(s == null || s.equals(""))return null;
        StringBuilder r = new StringBuilder();
        char[] _s = s.toCharArray();
        r.append(_s[0]);
        for(int i = 1; i < _s.length; i++){
            char c = _s[i];
            if(isCapitalLetter(c)){
                r.append(' ');
            }
            r.append(c);
        }
        return r.toString();
    }

    public static String divideByUnderscore(String s){
        if(s == null || s.equals(""))return null;
        StringBuilder r = new StringBuilder();
        char[] _s = s.toCharArray();
        for(int i = 0; i < _s.length; i++){
            char c = _s[i];
            if(c == '_'){
                c = ' ';
            }
            r.append(c);
        }
        return r.toString();
    }
    //fucking miss .net predicates here :(
    public static String separateNumbers(String s){
        if(s == null || s.equals(""))return null;
        StringBuilder r = new StringBuilder();
        char[] _s = s.toCharArray();
        r.append(_s[0]);
        for(int i = 1; i < _s.length; i++){
            if((!isNumber(_s[i-1]) && isNumber(_s[i]))
                    ||
                    (isNumber(_s[i-1]) && !isNumber(_s[i]))){
                r.append(' ');
            }
            r.append(_s[i]);
        }
        return r.toString();
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
