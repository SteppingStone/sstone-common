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
package org.edc.sstone;

import org.apache.commons.lang.IntHashMap;

/**
 * Because we want to minimize the number of classes, I am including this generic checked exception
 * class that can handle reason codes. We can catch other checked exceptions, translate to this,
 * rethrow, and then consumers can read the reason code and do something sensible.
 * 
 * @author Greg Orlowski
 */
public class CheckedException extends Exception {

    private static final long serialVersionUID = 1L;

    public static final byte REASON_UNKNOWN = 1;
    public static final byte MODULE_READ_ERROR = 2;

    public static final byte MODULE_PROPERTIES_READ_ERROR = 3;

    public static final byte PHONE_PROFILE_SILENT_MODE_ERROR = 4;

    public static final byte PREF_DB_READ_ERROR = 5;
    public static final byte PREF_DB_WRITE_ERROR = 6;

    // public static final byte VALUE__ERROR = 5;

    public String[] messageArgs;

    public final byte code;
    final Throwable reason;

    /*
     * TODO: we could consider implementing some generic indicator to let the consumer know whether
     * or not this exception is recoverable. If it is then the consumer can handle recovery instead
     * of displaying an error and then forcing an exit.
     */
    // public boolean recoverable = false;

    public CheckedException(byte code) {
        this(null, null, code);
    }

    public CheckedException(Throwable e, byte code) {
        this(null, e, code);
    }

    public CheckedException(String message, Throwable e, byte code) {
        super(message);
        this.code = code;
        this.reason = e;
    }

    public CheckedException(String message, byte code) {
        this(message, null, code);
    }

    public String getErrorMessageKey() {
        Object ret = null;
        IntHashMap m = new IntHashMap();
        m.put(PHONE_PROFILE_SILENT_MODE_ERROR, "audio.silent.error");
        m.put(MODULE_READ_ERROR, "module.read.error");
        m.put(MODULE_PROPERTIES_READ_ERROR, "module.properties.read.error");
        ret = m.get(code);
        return (ret != null && ret instanceof String) ? (String) ret : "application.error";
    }
}
