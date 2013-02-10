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
package gnu.classpath.java.util.zip;

import java.io.InputStream;
import java.util.Arrays;

import org.edc.sstone.util.StdLib;

import junit.framework.TestCase;

public class ZipInputStreamTest extends TestCase {

    public void testGetNextEntry() throws Exception {
        ZipInputStream zin = getZipInputStream();

        String[] expectedEntries = new String[] {
                "z/",
                "z/goodbye.txt",
                "z/hello.txt"
        };

        try {
            ZipEntry ze = null;
            int entryCount = 0;
            while ((ze = zin.getNextEntry()) != null) {
                assertTrue(StdLib.arrayContains(expectedEntries, ze.getName()));
                entryCount++;
            }
            assertEquals(3, entryCount);
        } finally {
            zin.close();
        }
    }

    public void testRead() throws Exception {
        ZipInputStream zin = null;
        ZipEntry ze = null;
        byte[] buff = new byte[InflaterInputStream.DEFAULT_BUFF_SIZE];

        try {
            zin = getZipInputStream();
            while ((ze = zin.getNextEntry()) != null) {

                if (ze.isDirectory()) {
                    assertEquals(ze.getName(), "z/");
                    continue;
                }

                String s = null;
                Arrays.fill(buff, (byte) 0);

                int numRead = 0;

                if ("z/goodbye.txt".equals(ze.getName())) {
                    numRead = zin.read(buff, 0, buff.length);
                    s = new String(buff, 0, numRead);
                    assertEquals("goodbye world", s);
                    continue;
                }

                if ("z/hello.txt".equals(ze.getName())) {
                    numRead = zin.read(buff, 0, buff.length);
                    s = new String(buff, 0, numRead);
                    assertEquals("hello world", s);
                    Arrays.fill(buff, (byte) 0);
                    continue;
                }

                zin.reset();
            }
        } finally {
            zin.close();
        }

    }

    static ZipInputStream getZipInputStream() {
        return new ZipInputStream(getZipResource());
    }

    static InputStream getZipResource() {
        return ZipInputStreamTest.class.getResourceAsStream("/zip/z.zip");
    }

}
