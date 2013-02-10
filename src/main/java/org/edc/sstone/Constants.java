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

/**
 * @author Greg Orlowski
 */
public class Constants {

    public static final byte NUMBER_NOT_SET = -1;

    // FONT SIZES

    /**
     * Corresponds to javax.microedition.lcdui.Font SIZE_SMALL but uses byte not int
     */
    public static final byte FONT_SIZE_SMALL = 8;

    /**
     * Corresponds to javax.microedition.lcdui.Font SIZE_MEDIUM but uses byte not int
     */
    public static final byte FONT_SIZE_MEDIUM = 0;

    /**
     * Corresponds to javax.microedition.lcdui.Font SIZE_LARGE but uses byte not int
     */
    public static final byte FONT_SIZE_LARGE = 16;

    public static final byte FONT_FACE_MONOSPACE = 32;
    public static final byte FONT_FACE_PROPORTIONAL = 64;
    public static final byte FONT_FACE_SYSTEM = 0;

    public static final byte FONT_STYLE_BOLD = 1;
    public static final byte FONT_STYLE_ITALIC = 2;
    public static final byte FONT_STYLE_PLAIN = 0;
    public static final byte FONT_STYLE_UNDERLINED = 4;

    public static final byte FONT_STYLE_STRIKETHROUGH = 8;

    //
    public static final byte DIRECTION_FORWARD = 1;
    public static final byte DIRECTION_BACK = -1;

    /**
     * We use this to indicate that a byte array record is an integer
     */
    public static final byte TYPE_INT = 1;

    /**
     * We use this to indicate that a byte array record is a String
     */
    public static final byte TYPE_STRING = 2;

    //
    //
    //
    public static final byte NAVIGATION_DIRECTION_UP = 1;
    public static final byte NAVIGATION_DIRECTION_NEXT = 2;

    /*
     * We will never use descend without a qualified target because we always need to know a target
     * when we descend.
     */
    // public static final byte NAVIGATION_DIRECTION_DESCEND =3;

    public static final byte NAVIGATION_DIRECTION_PREVIOUS = 4;
    public static final byte NAVIGATION_DIRECTION_RELOAD_CURR = 5;
    public static final byte NAVIGATION_DIRECTION_EXIT = 6;

    /*
     * record ids for user preferences
     */
    public static final byte FONT_MAGNIFICATION_RECORD_ID = 1;
    public static final byte VOLUME_RECORD_ID = 2;
    public static final byte ANIMATION_SPEED_RECORD_ID = 3;
    public static final byte SCREENSAVER_DELAY_RECORD_ID = 4;
    public static final byte LANGUAGE_RECORD_ID = 5;

    /*
     * Actions
     */
    public static final byte ACTION_BLUR = 1;
    public static final byte ACTION_SELECT = 2;

    /*
     * Filenames
     */
    public static final String MODULE_INDEX_FILENAME = "index.mod";
    public static final String MODULE_PROPERTIES_FILENAME = "module.properties";

    /*
     * Project properties
     */
    public static final String IMAGE_FILETYPE = "image.filetype";
    public static final String AUDIO_FILETYPE = "audio.filetype";

    /**
     * When a project&#39;s metadata designates that the {@link #IMAGE_FILETYPE} or
     * {@link #AUDIO_FILETYPE} is of {@link #FILETYPE_ANY}, any file format/extension may be used in
     * the project.
     */
    public static final String FILETYPE_ANY = "any";

    public static final String MIME_TYPE_MPEG = "audio/mpeg";

    public static final String PROJECT_FILE_EXT = "ssp";
}
