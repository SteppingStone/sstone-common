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

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import de.enough.polish.util.Properties;

/**
 * @author Greg Orlowski
 */
public class PropertyResourceMessageSource implements MessageSource {

    Properties props = new Properties();

    PropertyResourceMessageSource(String isoLanguageCode, String rootPath) {
        props = loadProps(rootPath + "/message.properties");
        if (isoLanguageCode != null && isoLanguageCode.length() > 0) {
            Properties localized = loadProps(rootPath + "/message_" + isoLanguageCode + ".properties");
            for (Enumeration e = localized.keys(); e.hasMoreElements();) {
                Object k = (String) e.nextElement();
                props.put(k, localized.get(k));
            }
        }
    }

    /**
     * Initialize the global/static ResourceManager for the desired locale
     * 
     * @param isoLanguageCode
     *            A 2 or 3 letter (ISO 639-1, ISO 639-2 or ISO 639-3) locale string (e.g., en, fr,
     *            taq (Tamasheq), son (Songhai), bm (Bambara), etc.)
     */
    public PropertyResourceMessageSource(String isoLanguageCode) {
        this(isoLanguageCode, "");
    }

    protected Properties loadProps(String filename) {
        Properties ret = new Properties();
        InputStream is = null;
        try {
            is = PropertyResourceMessageSource.class.getResourceAsStream(filename);
            if (is != null) {
                ret.load(is, "UTF-8");
            }
        } catch (IOException e) {
            // TODO: fix logging
            // Log.warn("Could not load properties from: " + filename);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignoreCloseFailure) {
                }
            }
        }
        return ret;
    }

    public String getString(String key) {
        String ret = props.getProperty(key);
        return ret == null ? key : ret;
    }

    public String getString(String key, Object[] args) {
        String msg = getString(key);
        if (args == null) {
            return msg;
        }
        StringBuffer sb = new StringBuffer(msg.length() * 2);
        int startIdx = 0;
        for (int i = 0; i < args.length; i++) {
            String placeholder = "%" + (i + 1);
            int placeHolderIdx = msg.indexOf(placeholder);
            if (placeHolderIdx >= 0) {
                sb.append(msg.substring(startIdx, placeHolderIdx));
                sb.append(args[i]);
                startIdx = placeHolderIdx + placeholder.length();
            } else {
                break;
            }
        }
        return sb.append(msg.substring(startIdx)).toString();
    }
}
