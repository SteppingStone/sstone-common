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

import java.util.Stack;
import java.util.Vector;

import org.edc.sstone.Constants;

/**
 * A collection of static functions. We want to minimize class count to reduce our jar size on j2me.
 * Therefore, rather than providing ByteUtil, ArrayUtil, StringUtil, etc etc classes, all that stuff
 * will get thrown into this class.
 * 
 * @author Greg Orlowski
 */
public class StdLib {

    /*
     * ByteUtil methods
     */
    public static void copyIntInto(int i, byte[] dest, int destPos) {
        System.arraycopy(StdLib.intToBytes(i), 0, dest, destPos, 4);
    }

    public static final byte[] intToBytes(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (i >>> 24);
        bytes[1] = (byte) (i >>> 16);
        bytes[2] = (byte) (i >>> 8);
        bytes[3] = (byte) i;
        return bytes;
    }

    public static final int bytesToInt(byte[] bytes, int offset) {
        return ((int) bytes[offset] & 0xFF) << 24
                | ((int) bytes[offset + 1] & 0xFF) << 16
                | ((int) bytes[offset + 2] & 0xFF) << 8
                | (int) (bytes[offset + 3] & 0xFF);
    }

    public static final int bytesToInt(byte[] bytes) {
        return bytesToInt(bytes, 0);
    }

    /*
     * ArrayUtil methods
     */

    /**
     * @param haystack
     *            the array to search
     * @param needle
     *            the needle to search for within the array
     * @return true if haystack contains a reference to needle (tests using the == operator) else
     *         false
     */
    public static boolean arrayContainsReference(Object[] haystack, Object needle) {
        for (int i = 0; i < haystack.length; i++) {
            if (haystack[i] == needle)
                return true;
        }
        return false;
    }

    /**
     * @param haystack
     *            the array to search
     * @param needle
     *            the non-null needle to search for within the array.
     * @return true if haystack contains an object equivalent to needle (tests using the .equals()
     *         method) else false
     */
    public static boolean arrayContains(Object[] haystack, Object needle) {
        for (int i = 0; i < haystack.length; i++) {
            if (needle.equals(haystack[i]))
                return true;
        }
        return false;
    }

    public static Object[] mergeArray(Object[] a, Object[] b) {
        Object[] ret = new Object[a.length + b.length];
        System.arraycopy(a, 0, ret, 0, a.length);
        System.arraycopy(b, 0, ret, a.length, b.length);
        return ret;
    }

    public static String join(Object[] values, String joinString) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < values.length - 1; i++) {
            sb.append(values[i].toString()).append(joinString);
        }
        if (values.length > 0)
            sb.append(values[values.length - 1]);
        return sb.toString();
    }

    public static String arrayToString(Object[] values) {
        if (values == null)
            return "null";

        switch (values.length) {
            case 0:
                return "[]";
            case 1:
                return "[" + values[0] + "]";
        }

        StringBuffer sb = new StringBuffer("[");
        int i = 0;
        for (i = 0; i < values.length - 1; i++) {
            sb.append(values[i].toString());
            sb.append(", ");
        }
        sb.append(values[i].toString()).append(']');
        return sb.toString();
    }

    /**
     * If str does not contain char ch, return str. Else return a new String with all occurrences of
     * ch removed from str.
     * 
     */
    public static String removeChar(String str, char ch) {
        int nextIdx = str.indexOf(ch, 0);
        if (nextIdx == -1)
            return str;

        int startIdx = 0;
        StringBuffer sb = new StringBuffer(str.length());
        while (true) {
            sb.append(str.substring(startIdx, nextIdx));
            startIdx = nextIdx + 1;
            nextIdx = str.indexOf(ch, startIdx);
            if (nextIdx == -1) {
                sb.append(str.substring(startIdx));
                break;
            }
        }

        return sb.toString();
    }

    public static String[] split(String str, char ch) {
        Vector list = new Vector();
        int foundIdx = 0;
        int startIdx = 0;
        while (startIdx < str.length() && str.charAt(startIdx) == ch)
            foundIdx = ++startIdx;

        for (; (foundIdx = str.indexOf(ch, startIdx)) != -1; startIdx = foundIdx + 1) {
            if (foundIdx > startIdx)
                list.addElement(str.substring(startIdx, foundIdx));
        }
        if (startIdx < str.length() && str.charAt(startIdx) != ch)
            list.addElement(str.substring(startIdx));

        String[] ret = new String[list.size()];
        for (int i = 0; i < ret.length; i++)
            ret[i] = (String) list.elementAt(i);
        return ret;
    }

    /*
     * ModelUtil
     */
    public static boolean isSet(int v) {
        return v != Constants.NUMBER_NOT_SET;
    }

    /*
     * FileUtil
     */

    /**
     * Our display logic on the log screen will word-wrap only words that do not exceed the length
     * of the screen. Because file paths can be long, insert spaces in the middle when logging so
     * the end does not get truncated in the display.
     * 
     * @param path
     *            a file path
     * @return the original path, replacing slashes with slash+space
     */
    public static String addSpacesToPath(String path) {
        String[] parts = StdLib.split(path, '/');
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < parts.length; i++) {
            sb.append(parts[i]);
            sb.append(i < parts.length - 1 ? "/ " : "");
        }
        return sb.toString();
    }

    public static String basename(String filename) {
        filename = StdLib.stripTrailingSlashes(filename);
        if ("/".equals(filename)) {
            return "/";
        }
        int idx = filename.lastIndexOf('/');
        return filename.substring(idx + 1);
    }

    public static String dirname(String filename) {
        filename = StdLib.stripTrailingSlashes(filename);
        int idx = filename.lastIndexOf('/');
        if (filename.startsWith("file://") && idx == "file://".length())
            return "file:///";

        return idx == 0 ? "/" : filename.substring(0, idx);
    }

    static String stripTrailingSlashes(String filename) {
        while (filename.endsWith("/") && filename.length() > 1) {
            filename = filename.substring(0, filename.length() - 1);
        }
        return filename;
    }

    public static String[] parentDirs(String path) {
        Stack stack = new Stack();
        while (!"/".equals(path) && !"file:///".equals(path) && path.trim().length() > 0) {
            path = dirname(path);
            stack.push(path);
        }
        String[] ret = new String[stack.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (String) stack.pop();
        }
        return ret;
    }

    public static String[] parentDirs(String path, String aboveRoot) {
        String[] parents = parentDirs(path);
        aboveRoot = StdLib.absPath(aboveRoot, true);

        int i = parents.length - 1;
        for (; i >= 0; i--) {
            if (StdLib.absPath(parents[i], true).equals(aboveRoot)) {
                i++;
                break;
            }
        }

        String[] ret = new String[parents.length - i];
        System.arraycopy(parents, i, ret, 0, ret.length);
        return ret;
    }

    public static String absPath(String file, boolean isDir) {
        return StdLib.absPath(file, "", isDir);
    }

    public static String absPath(String parentDir, String file, boolean isDir) {
        StringBuffer sb = new StringBuffer();
        if (!parentDir.startsWith("file://")) {
            sb.append(parentDir.startsWith("/")
                    ? "file://"
                    : "file:///");
        }
        sb.append(parentDir);
        if (!parentDir.endsWith("/"))
            sb.append("/");
        sb.append(file);

        if (isDir
                && !file.endsWith("/")
                && (sb.charAt(sb.length() - 1) != '/'))
            sb.append("/");

        return sb.toString();
    }

}
