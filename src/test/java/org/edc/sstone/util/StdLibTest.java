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

import junit.framework.TestCase;

/**
 * @author Greg Orlowski
 */
public class StdLibTest extends TestCase {

    public void testRemoveChar() throws Exception {
        assertEquals("abc", StdLib.removeChar("a-b-c", '-'));
        assertEquals("abc", StdLib.removeChar("a-b-c-", '-'));
        assertEquals("abc", StdLib.removeChar("a-b-c--", '-'));
        assertEquals("abc", StdLib.removeChar("-a-b-c--", '-'));
        assertEquals("", StdLib.removeChar("---", '-'));
        assertEquals("abc", StdLib.removeChar("abc", '-'));
        assertEquals("a", StdLib.removeChar("-a", '-'));
        assertEquals("a", StdLib.removeChar("a-", '-'));
    }

    public void testMergeArray() {
        Integer[] a = new Integer[] { Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3) };
        Integer[] b = new Integer[] { Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7) };

        Object[] combined = StdLib.mergeArray(a, b);

        assertEquals(1, ((Integer) combined[0]).intValue());
        assertEquals(6, combined.length);
        assertEquals(7, ((Integer) combined[combined.length - 1]).intValue());
    }

    public void testBasename() {
        assertEquals("test.zip", StdLib.basename("/test.zip"));
        assertEquals("test.zip", StdLib.basename("/a/b/c/test.zip"));
        assertEquals("test.zip", StdLib.basename("file:///a/b/c/test.zip"));
        assertEquals("test.zip", StdLib.basename("file:///test.zip"));
        assertEquals("/", StdLib.basename("/"));
    }

    public void testDirname() {
        assertEquals("/a/b", StdLib.dirname("/a/b/c"));
        assertEquals("/a/b", StdLib.dirname("/a/b/c/"));
        assertEquals("file:///a/b", StdLib.dirname("file:///a/b/c/"));
        assertEquals("file:///a/b", StdLib.dirname("file:///a/b/c"));
        assertEquals("file:///a/b", StdLib.dirname("file:///a/b/c.zip"));
        assertEquals("/", StdLib.dirname("/"));
        assertEquals("/", StdLib.dirname("/dev"));
        assertEquals("file:///", StdLib.dirname("file:///a"));
    }

    public void testParentDirs() {
        String[] parentDirs = StdLib.parentDirs("/home/greg/notes");
        assertEquals("/", parentDirs[0]);
        assertEquals("/home", parentDirs[1]);
        assertEquals("/home/greg", parentDirs[2]);

        parentDirs = StdLib.parentDirs("file:///home/greg/notes");
        assertEquals("file:///", parentDirs[0]);
        assertEquals("file:///home", parentDirs[1]);
        assertEquals("file:///home/greg", parentDirs[2]);

        parentDirs = StdLib.parentDirs("file:///home/greg/");
        assertEquals("file:///", parentDirs[0]);
        assertEquals("file:///home", parentDirs[1]);

        // 2-arg function
        parentDirs = StdLib.parentDirs("file:///home/greg/notes", "/home");
        assertEquals(parentDirs.length, 1);
        assertEquals(parentDirs[0], "file:///home/greg");
    }

    public void testAbsPath() {
        assertEquals("file:///e/a.txt", StdLib.absPath("e/", "a.txt", false));
        assertEquals("file:///e/a.txt", StdLib.absPath("file:///e/", "a.txt", false));
        assertEquals("file:///e/a/b/c.txt", StdLib.absPath("file:///e/a", "b/c.txt", false));
        assertEquals("file:///e/a/b/c.txt", StdLib.absPath("file:///e/a/", "b/c.txt", false));

        assertEquals("file:///e/a/b/", StdLib.absPath("file:///e/a/b/", "", true));

        assertEquals("file:///e/a/b/", StdLib.absPath("file:///e/a/b/", true));
        assertEquals("file:///e/a/b/", StdLib.absPath("e/a/b", true));

    }

    public void testSplit() {
        String[] expected = { "a", "b", "c" };
        String[] testStrings = { "-a-b-c", "a--b-c", "--a-b-c", "a-b-c--", "-a-b-c-", "---a--b--c---" };

        for (int i = 0; i < testStrings.length; i++) {
            String[] split = StdLib.split(testStrings[i], '-');
            assertEquals(3, split.length);
            for (int j = 0; j < 3; j++) {
                assertEquals(split[j], expected[j]);
            }

            split = StdLib.split("a b c", ' ');
            assertEquals(3, split.length);
            assertEquals("c", split[2]);
        }

        String langMappingVal = "fr Français en English bm Bambara es Español";
        final String[] langMappingElements = StdLib.split(langMappingVal, ' ');

        assertEquals("Français", langMappingElements[1]);
        
        String testStr = "a";
        String[] split = StdLib.split(testStr, ' ');
        assertEquals(1, split.length);
        assertEquals("a", split[0]);
        
    }

    public void testJoin() throws Exception {
        Object[] values = new Object[] { "a", "b", "c" };
        assertEquals("a b c", StdLib.join(values, " "));
    }

}
