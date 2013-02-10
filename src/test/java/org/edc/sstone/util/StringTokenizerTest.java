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
public class StringTokenizerTest extends TestCase {

    public void testPreserveNewlines() {
        StringTokenizer st = new StringTokenizer("Code.\nCaution.", " ", "\n");
        assertEquals("Code.", st.nextToken());
        assertEquals("\n", st.nextToken());
        assertEquals("Caution.", st.nextToken());
    }

    public void testNextToken() {
        StringTokenizer st = new StringTokenizer("A fox in the woods.");
        assertEquals("A", st.nextToken());
        assertEquals("fox", st.nextToken());
        assertEquals("in", st.nextToken());
        assertEquals("the", st.nextToken());
        assertEquals("woods.", st.nextToken());

        st = new StringTokenizer("To fine-tune, be careful.", " ");
        assertEquals("To", st.nextToken());
        assertEquals("fine-", st.nextToken());
        assertEquals("tune,", st.nextToken());
        assertEquals("be", st.nextToken());
        assertEquals("careful.", st.nextToken());

        st = new StringTokenizer("This is a test of our j2me TextArea widget, which should wrap.", " ");
        assertEquals("This", st.nextToken());
        assertEquals("is", st.nextToken());
        assertEquals("a", st.nextToken());
        assertEquals("test", st.nextToken());
        assertEquals("of", st.nextToken());
        assertEquals("our", st.nextToken());
        assertEquals("j2me", st.nextToken());
        assertEquals("TextArea", st.nextToken());
        assertEquals("widget,", st.nextToken());
        assertEquals("which", st.nextToken());
        assertEquals("should", st.nextToken());
        assertEquals("wrap.", st.nextToken());
    }

    

}
