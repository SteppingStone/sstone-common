/*
 * Copyright (c) 2012 EDC
 * 
 * This file is part of Stepping Stone.
 * 
 * Stepping Stone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Stepping Stone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Stepping Stone.  If not, see <http://www.gnu.org/licenses/gpl.txt>.
 */
package org.edc.sstone.text;

import org.apache.commons.lang.IntHashMap;

/**
 * @author Greg Orlowski
 */
public class Alphabet {

    final IntHashMap letterMap = new IntHashMap();

    public Alphabet(String lowercaseLetters, String uppercaseLetters) {
        // We do not have to map lowercase to themselves since we map
        // any character to itself if it is not a known uppercase char

        // We DO need lowercaseLetters in the map so we know
        // what is and what is not a letter
        mapLetters(lowercaseLetters, lowercaseLetters);
        mapLetters(uppercaseLetters, lowercaseLetters);
    }

    public String stripNonLetters(String str) {
        StringBuffer sb = new StringBuffer(str.length());
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (letterMap.containsKey((int) c))
                sb.append(c);
        }
        return sb.toString();
    }

    private void mapLetters(String keys, String values) {
        char k, v;
        for (int i = 0; i < keys.length(); i++) {
            k = keys.charAt(i);
            v = values.charAt(i);
            letterMap.put((int) k, new Character(v));
        }
    }

    public String toLowerCase(String str) {
        int len = str.length();
        char[] ret = new char[len];
        for (int i = 0; i < len; i++)
            ret[i] = getChar(str.charAt(i));
        return new String(ret);
    }

    /**
     * NOTE: if a char is not mapped, we just return the char itself. We need this to handle
     * non-letter characters (numbers, punctuation, etc)
     * 
     * @param key
     *            the char that we want to lookup in the map to find its lowercase value.
     * @return the lowercase value of the character if it is in the map or the character itself if
     *         it is not mapped.
     */
    private char getChar(char key) {
        Object val = letterMap.get((int) key);
        return val == null ? key : ((Character) val).charValue();
    }

}