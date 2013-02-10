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
package org.edc.sstone.util;

/**
 * @author Greg Orlowski
 */
public class StringTokenizer {

    private final char[] notDroppableDelims; // = { '-' };
    private char[] droppableDelims = { ' ', '\t', '\n', '\r' };
    private char[] preserveAsTokens = {};

    private int pos = 0;
    private String str;

    public StringTokenizer(String str, String droppableDelims, String preserveAsTokens, String notDroppableDelims) {
        this.str = str;
        if (droppableDelims != null) {
            this.droppableDelims = droppableDelims.toCharArray();
        }
        if (preserveAsTokens != null) {
            this.preserveAsTokens = preserveAsTokens.toCharArray();
        }
        this.notDroppableDelims = notDroppableDelims != null
                ? notDroppableDelims.toCharArray() :
                new char[] { '-' };
    }

    public StringTokenizer(String str, String droppableDelims, String preserveAsTokens) {
        this(str, droppableDelims, preserveAsTokens, null);
    }

    public StringTokenizer(String str) {
        this(str, null, null, null);
    }

    public StringTokenizer(String str, String droppableDelims) {
        this(str, droppableDelims, null, null);
    }

    public String nextToken() {
        while (pos < str.length() && isDelim(str.charAt(pos))) {
            pos++;
        }
        if (isPreserveAsTokenChar(str.charAt(pos))) {
            String ret = str.substring(pos, pos + 1);
            pos++;
            return ret;
        }
        if (pos < str.length()) {
            StringBuffer sb = new StringBuffer();
            for (; pos < str.length()
                    && !isDelim(str.charAt(pos))
                    && !isPreserveAsTokenChar(str.charAt(pos)); pos++) {
                sb.append(str.charAt(pos));
            }
            if (pos < str.length() && isNonDroppableDelim(str.charAt(pos))) {
                sb.append(str.charAt(pos++));
            }
            return sb.toString();
        }
        return null;
    }

    public boolean hasMoreTokens() {
        if (pos < str.length()) {
            for (int i = pos; i < str.length(); i++) {
                if (!isDelim(str.charAt(i)))
                    return true;
            }
        }
        return false;
    }

    private boolean isDelim(char c) {
        return isDroppableDelim(c) || isNonDroppableDelim(c);
    }

    private boolean isDroppableDelim(char c) {
        return contains(droppableDelims, c);
    }

    private boolean isNonDroppableDelim(char c) {
        return contains(notDroppableDelims, c);
    }

    private boolean isPreserveAsTokenChar(char c) {
        return contains(preserveAsTokens, c);
    }

    private static boolean contains(char[] chars, char c) {
        for (int i = 0; i < chars.length; i++) {
            if (c == chars[i])
                return true;
        }
        return false;
    }
}
