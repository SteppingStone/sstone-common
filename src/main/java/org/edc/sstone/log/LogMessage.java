/*
 * This code was originally part of the J4ME project:
 *  
 * https://code.google.com/p/j4me/
 *
 * Copyright 2007 J4ME
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License.  You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.  See the License for the specific language
 * governing permissions and limitations under the License.
 */

package org.edc.sstone.log;

/**
 * A message logged by the application.
 * 
 * @author J4ME
 */
public class LogMessage {
    /**
     * The level of this log message.
     * 
     * @see Level
     */
    public Level level;

    /**
     * The <code>System.cucurrentTimeMillis</code> at the time the message was logged.
     */
    public long time;

    /**
     * The message logged.
     */
    public String message;

    /**
     * Constructs a log message.
     * 
     * @param level
     *            is the severity of the log.
     * @param message
     *            is the text of the log message.
     */
    protected LogMessage(Level level, String message)
    {
        setLogMessage(level, message);
    }

    /**
     * Replaces the contents of a log message.
     * 
     * @param level
     *            is the severity of the log.
     * @param message
     *            is the text of the log message.
     */
    protected void setLogMessage(Level level, String message)
    {
        this.level = level;
        this.message = message;
        this.time = System.currentTimeMillis();
    }

    /**
     * The message as it will appear in the log.
     */
    public String toString()
    {
        return "[" + level + "] " + message;
    }
}
