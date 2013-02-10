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
package org.edc.sstone.il8n;

import junit.framework.TestCase;

/**
 * @author Greg Orlowski
 */
public class PropertyResourceMessageSourceTest extends TestCase {

    private static final String rootPath = "/test";

    public void testLoadProps() throws Exception {

        // Since we have no file for "ru", the strings should match the default message.properties
        MessageSource ms = new PropertyResourceMessageSource("ru", rootPath);
        assertEquals("Hello World", ms.getString("hello.message"));
        assertEquals("cat", ms.getString("cat.message"));

        ms = new PropertyResourceMessageSource("pl", rootPath);
        assertEquals("Witaj Świecie", ms.getString("hello.message"));

        // Since we do not specify a value in the message_pl.properties,
        // it should default to the value in message.properties
        assertEquals("cat", ms.getString("cat.message"));
    }

    public void testGetString() throws Exception {
        MessageSource ms = new PropertyResourceMessageSource("en", rootPath);
        assertEquals("I have a Dell laptop", ms.getString("computer.brand.message", new Object[] { "Dell" }));
    }

}
