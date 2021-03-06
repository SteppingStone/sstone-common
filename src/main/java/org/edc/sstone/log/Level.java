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
 * Defines the logging level constants. The logging levels are as follows listed in ascending order
 * of importance:
 * <ul>
 * <li><code>DEBUG</code> - Finely grained logging used to track down problems.
 * <li><code>INFO</code> - Documents important and expected program execution.
 * <li><code>WARN</code> - Used for unexpected problems that arise that do not disrupt the program.
 * <li><code>ERROR</code> - Records problems that cause the program to behave improperly.
 * </ul>
 * <p>
 * There is also an <code>OFF</code> level. It is used to disable logging.
 * 
 * @author J4ME
 */
public class Level {
    /**
     * The <code>DEBUG</code> level designates fine-grained informational events that are most
     * useful to debug an application.
     */
    public static final Level DEBUG = new Level("Debug", 1);

    /**
     * The <code>INFO</code> level designates informational messages that highlight the progress of
     * the application at coarse-grained level.
     */
    public static final Level INFO = new Level("Info", 2);

    /**
     * The <code>WARN</code> level designates potentially harmful situations.
     */
    public static final Level WARN = new Level("Warn", 3);

    /**
     * The <code>ERROR</code> level designates exceptional behavior that causes something in the
     * application to run improperly.
     */
    public static final Level ERROR = new Level("Error", 4);

    /**
     * The <code>OFF</code> level means no log statements will be stored.
     */
    public static final Level OFF = new Level("Off", 5);

    /**
     * String representation of the level.
     */
    private String levelString;

    /**
     * Integer representation of the level. The lower the number the lower the priority.
     */
    protected final int levelInt;

    /**
     * Constructs a <code>Level</code> object.
     * 
     * @param levelString
     *            is a human readable representation of the level.
     * @param levelInt
     *            is an integer representation of the level.
     */
    private Level(String levelString, int levelInt) {
        this.levelString = levelString;
        this.levelInt = levelInt;
    }

    public static Level forName(String nm) {
        Level[] levels = new Level[] { DEBUG, INFO, WARN, ERROR, OFF };
        for (int i = 0; i < levels.length; i++) {
            if (levels[i].levelString.toLowerCase().equalsIgnoreCase(nm))
                return levels[i];
        }
        return null;
    }

    /**
     * @return The string represention of this level.
     */
    public String toString() {
        return levelString;
    }

    /**
     * @return An integer representation of the log level. It can passed into the
     *         <code>setLogLevel</code> method.
     */
    public int toInt() {
        return levelInt;
    }
}
