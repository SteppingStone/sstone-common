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
package org.apache.commons.lang;

import java.util.Enumeration;

import junit.framework.TestCase;

public class IntHashMapTest extends TestCase {

    public void testKeys() throws Exception {
        IntHashMap m = new IntHashMap();
        assertFalse(m.keys().hasMoreElements());

        int count = 3;
        for (int i = 0; i < count; i++) {
            int n = (i * 2) + 1;
            m.put(n, forInt(n));
        }
        assertEquals(count, m.size());

        Enumeration e = m.keys();
        assertEquals(true, e.hasMoreElements());

        for (; e.hasMoreElements(); e.nextElement()) {
            count--;
        }
        assertEquals(0, count);

        for (e = m.keys(); e.hasMoreElements();) {
            Object k = e.nextElement();
            assertTrue(k instanceof Integer);
            int i = ((Integer) k).intValue();
            Object v = m.remove(i);

            // System.out.println("i: " + i + "; v: " + v);
            assertTrue(v instanceof String);
            assertEquals(forInt(i), v.toString());
        }
    }

    static String forInt(int i) {
        switch (i) {
            case 1:
                return "one";
            case 3:
                return "three";
            case 5:
                return "five";
        }
        return null;
    }

}
