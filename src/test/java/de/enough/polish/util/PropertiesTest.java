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
package de.enough.polish.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.edc.sstone.Constants;

import junit.framework.TestCase;

public class PropertiesTest extends TestCase {

    public void testLoad() throws IOException {
        Properties props = new Properties();
        props.load(getTestPropStream());

        assertEquals("v", props.getProperty("k"));
        assertEquals("d.e.f", props.getProperty("a.b.c"));
        assertEquals("j_value", props.getProperty("j"));
        assertEquals("good", props.getProperty("last.prop"));
    }

    public void testLoadFromFile() throws IOException {
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("/props/" + Constants.MODULE_PROPERTIES_FILENAME));
        //
        assertEquals("abcdefghijklmnopqrstuvwxyz", props.getProperty("alphabet.lowercase"));

    }

    public void testLoadFromUtf8() throws IOException {
        // french/utf8
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("/props/message_fr.properties"), "UTF-8");

        assertEquals("retard d'économiseur d'écran", props.getProperty("device.backlight.delay"));
        assertEquals("volume", props.getProperty("device.volume.level"));

        // props.load(getClass().getResourceAsStream("/props/message.properties"), "UTF-8");
    }

    public InputStream getTestPropStream() throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("### A comment line\n");
        sb.append("# another comment line\n");
        sb.append("k=v\n");
        sb.append("a.b.c=d.e.f\r\n");
        sb.append("j=j_value\n");
        sb.append("###blank lines");
        sb.append("\n\n\n");
        sb.append("###another prop\n");
        sb.append("last.prop=good");

        String str = sb.toString();

        return new ByteArrayInputStream(str.getBytes("UTF-8"));
    }
    
    public void testBlankLines() throws IOException {
        StringBuffer sb = new StringBuffer();
        //sb.append("###YO\n");
        sb.append("\n");
        sb.append("k=v\n");

        String str = sb.toString();
        InputStream in = new ByteArrayInputStream(str.getBytes("UTF-8"));
        Properties props = new Properties();
        props.load(in);
        
        assertEquals("v", props.getProperty("k"));
    }
}
