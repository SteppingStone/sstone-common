/*
 * Created on Jul 9, 2007 at 10:23:37 PM.
 * 
 * Copyright (c) 2009 Andre Schmidt / Enough Software
 *
 * This file is part of J2ME Polish.
 *
 * J2ME Polish is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * J2ME Polish is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with J2ME Polish; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 * Commercial licenses are also available, please
 * refer to the accompanying LICENSE.txt or visit
 * http://www.j2mepolish.org for details.
 */
package de.enough.polish.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Reads a property list (key and element pairs) from the input stream and stores it in the internal
 * <code>Hashtable</code>. The stream is assumed to be using the ISO 8859-1 character encoding; that
 * is each byte is one Latin1 character.
 * 
 * <p>
 * Copyright (c) 2009-2008 Enough Software
 * </p>
 * 
 * <pre>
 * history
 *        09-Jul-2007 - asc creation
 *        2012-03-16 - Greg Orlowski:   - extend Hashtable not HashMap
 *                                      - use Vector not ArrayList
 *                                      - remove some methods
 *                                      - fix handling of leading blank lines
 * </pre>
 * 
 * @author Andre Schmidt
 * @author Robert Virkus (optimizations)
 * @author Greg Orlowski (refactoring, rem
 */
public class Properties extends Hashtable {

    private static final long serialVersionUID = 1L;
    private char separator = '=';
    private char comment = '#';

    // GKO: changed to vector to remove arraylist + reduce code size
    Vector orderedKeys = new Vector();
    Vector orderedValues = new Vector();

    /**
     * Creates a new empty Properties set.
     */
    public Properties() {
        super();
    }

    /**
     * Returns the property value corresponding to the given key
     * 
     * @param key
     *            the key
     * @return the String value corresponding to the key
     */
    public String getProperty(String key) {
        return (String) this.get(key);
    }

    /**
     * Returns the property value corresponding to the given key, or if no value exists the default
     * value.
     * 
     * @param key
     *            the key
     * @param defaultValue
     *            the default value
     * @return the String value corresponding to the key, or the default value
     */
    public String getProperty(String key, String defaultValue) {
        String value = (String) this.get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Inserts a property with the given key and the corresponding value
     * 
     * @param key
     *            the key to be placed into this property list.
     * @param value
     *            the value corresponding to key
     */
    public void setProperty(String key, String value) {
        this.put(key, value);
    }

    /**
     * Reads a property list (key and element pairs) from the input stream. The stream is assumed to
     * be using the ISO 8859-1 character encoding; that is each byte is one Latin1 character.
     * 
     * @param in
     *            the input stream.
     * @throws IOException
     *             if an error occurred when reading from the input stream.
     */
    public void load(InputStream in) throws IOException {
        load(in, null);
    }

    /**
     * Reads a property list (key and element pairs) from the input stream.
     * 
     * @param in
     *            the input stream.
     * @param encoding
     *            the expected encoding, null for the default encoding of the system
     * @param generateIntegerValues
     *            true when Integer values should be generated. Integers must be retrieved using
     *            get(key) instead of getProperty(key) later onwards.
     * @throws IOException
     *             if an error occurred when reading from the input stream.
     */
    public void load(InputStream in, String encoding) throws IOException {
        this.orderedKeys.removeAllElements();
        this.orderedValues.removeAllElements();
        int bufferLength = 2 * 1024;
        byte[] buffer = new byte[bufferLength];
        int read;
        int start = 0;
        int end = 0;
        boolean newLineFound;
        while ((read = in.read(buffer, start, bufferLength - start)) != -1) {
            // add \r to last line if its missing
            if (buffer.length != start + read && buffer[read - 1] != '\n' && buffer[read - 1] != '\r') {
                buffer[read] = '\n';
                read++;
            }

            // search for next \r or \n
            String line;

            // fix for leading whitespace lines
            int strStartPos = 0;
            while (buffer[strStartPos] == '\n' || buffer[strStartPos] == '\r') {
                strStartPos++;
            }

            if (encoding != null) {
                line = new String(buffer, strStartPos, read + start, encoding);
            } else {
                line = new String(buffer, strStartPos, read + start);
            }

            start = 0;
            newLineFound = true;
            while (newLineFound) {
                newLineFound = false;
                char c = '\n';
                for (int i = start; i < line.length(); i++) {
                    c = line.charAt(i);
                    if (c == '\r' || c == '\n') {
                        end = i;
                        newLineFound = true;
                        break;
                    }
                }
                if (newLineFound) {
                    // this is not a comment or blank line
                    if (line.indexOf(this.comment, start) != start) {
                        int splitPos = line.indexOf(this.separator, start);
                        if (splitPos != -1) {
                            String key = line.substring(start, splitPos);
                            String value = line.substring(splitPos + 1, end);
                            this.orderedKeys.addElement(key);
                            this.orderedValues.addElement(value);
                            put(key, value);
                        }
                    }

                    while (end < line.length() && (line.charAt(end) == '\r' || line.charAt(end) == '\n')) {
                        end++;
                    }
                    start = end;
                }
            }
            // now all key-value pairs have been read, now move any remaining data to the beginning
            // of the buffer:
            if (start < read) {
                System.arraycopy(buffer, start, buffer, 0, read - start);
                start = read - start;
            } else {
                start = 0;
            }
        }

    }

}
