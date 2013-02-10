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
package org.edc.sstone.ui.model;

import org.edc.sstone.Constants;

/**
 * NOTE: This class can only be mutated by classes with access to its package-private scope
 * 
 * @author Greg Orlowski
 */
public class FontStyle {

    /**
     * When the DISABLE_FONT_MAGNIFICATION bit is set on font size, do not support font resizing.
     * TODO: consider calling this DISABLE_FONT_RESIZING and also renaming the methods
     */
    private static final byte DISABLE_FONT_MAGNIFICATION = -128;

    private static final byte[] MAGNIFICATION_SIZE_LOOKUP = new byte[] {
            Constants.FONT_SIZE_SMALL,
            Constants.FONT_SIZE_MEDIUM,
            Constants.FONT_SIZE_LARGE };

    private static final byte STYLE_FILTER = (Constants.FONT_STYLE_BOLD | Constants.FONT_STYLE_ITALIC | Constants.FONT_STYLE_UNDERLINED);

    boolean enableMagnification = true;

    byte face;
    byte style;
    byte size;

    public FontStyle() {
        this(Constants.NUMBER_NOT_SET, Constants.NUMBER_NOT_SET, Constants.NUMBER_NOT_SET);
    }

    public FontStyle(byte face, byte style, byte size) {
        this(face, style, size, true);
    }

    public FontStyle(byte face, byte style, byte size, final boolean supportMagnification) {
        this.face = face;
        this.style = style;
        this.enableMagnification = supportMagnification;
        if (size != Constants.NUMBER_NOT_SET && (size & DISABLE_FONT_MAGNIFICATION) != 0) {
            this.enableMagnification = false;
            size ^= DISABLE_FONT_MAGNIFICATION;
        }
        this.size = size;
    }

    /**
     * NOTE: this never mutates the current FontStyle object. It either returns the styleDefaults
     * parameter if the current object has all its values unset or it returns a copy with merged
     * values.
     * 
     * @param styleDefaults
     *            an object to use with default style values
     * @return a new {@link FontStyle} object that merges any unset values with values from
     *         styleDefaults.
     */
    public FontStyle withDefaults(FontStyle styleDefaults) {
        if (isNull())
            return styleDefaults;

        FontStyle mergedStyle = this;
        if (styleDefaults != null && !styleDefaults.isNull()) {
            mergedStyle = new FontStyle(styleDefaults.face,
                    styleDefaults.style, styleDefaults.size);
            mergedStyle.enableMagnification = this.enableMagnification;

            if (this.face != Constants.NUMBER_NOT_SET) {
                mergedStyle.face = this.face;
            }
            if (this.style != Constants.NUMBER_NOT_SET) {
                mergedStyle.style = this.style;
            }
            if (this.size != Constants.NUMBER_NOT_SET) {
                mergedStyle.size = this.size;
            }
        }
        return mergedStyle;
    }

    public byte getFace() {
        // return (face == Constants.NUMBER_NOT_SET)
        // ? Constants.FONT_FACE_PROPORTIONAL
        // : face;
        return face;
    }

    public byte getStyle() {
        return (style == Constants.NUMBER_NOT_SET)
                ? style
                : (byte) (style & STYLE_FILTER);
    }

    public byte getStyleForWrite() {
        return style;
    }

    public byte getSize() {
        return size;
        // return (size == Constants.NUMBER_NOT_SET)
        // ? Constants.FONT_SIZE_MEDIUM
        // : size;
    }

    public byte getSizeForWrite() {
        if (size == Constants.NUMBER_NOT_SET)
            return size;
        int ret = this.size;
        if (!enableMagnification) {
            ret |= DISABLE_FONT_MAGNIFICATION;
        }
        return (byte) ret;
    }

    public boolean isStrikeThrough() {
        return (style != Constants.NUMBER_NOT_SET)
                && (style & Constants.FONT_STYLE_STRIKETHROUGH) == Constants.FONT_STYLE_STRIKETHROUGH;
    }

    public FontStyle magnified(byte magnification) {
        if (magnification == Constants.FONT_SIZE_MEDIUM || !enableMagnification)
            return this;

        int sizeIdx = 1;
        switch (size) {
            case Constants.FONT_SIZE_SMALL:
                sizeIdx = 0;
                break;
            case Constants.FONT_SIZE_LARGE:
                sizeIdx = 2;
                break;
        }

        switch (magnification) {
            case Constants.FONT_SIZE_SMALL:
                sizeIdx = Math.max(0, sizeIdx - 1);
                break;
            case Constants.FONT_SIZE_LARGE:
                sizeIdx = Math.min(MAGNIFICATION_SIZE_LOOKUP.length - 1, sizeIdx + 1);
                break;
        }

        return new FontStyle(face, style, MAGNIFICATION_SIZE_LOOKUP[sizeIdx]);
    }

    public boolean isEnableMagnification() {
        return enableMagnification;
    }

    public boolean isNull() {
        return face == Constants.NUMBER_NOT_SET
                && style == Constants.NUMBER_NOT_SET
                && size == Constants.NUMBER_NOT_SET;
    }
}
