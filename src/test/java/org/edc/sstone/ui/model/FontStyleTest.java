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

import junit.framework.TestCase;

/**
 * @author Greg Orlowski
 */
public class FontStyleTest extends TestCase {

    public void testGetStyle() throws Exception {

        FontStyle style = new FontStyle();
        assertTrue(style.isNull());
        assertTrue(style.isEnableMagnification());

        byte styleBt = (byte) (Constants.FONT_STYLE_BOLD | Constants.FONT_STYLE_ITALIC | Constants.FONT_STYLE_STRIKETHROUGH);
        style = new FontStyle(Constants.FONT_FACE_MONOSPACE,
                styleBt, Constants.FONT_SIZE_SMALL, false);

        assertFalse(style.isEnableMagnification());
        assertEquals(Constants.FONT_SIZE_SMALL, style.getSize());
        assertEquals(Constants.FONT_STYLE_BOLD, style.getStyle() & Constants.FONT_STYLE_BOLD);
        assertEquals(Constants.FONT_STYLE_ITALIC, style.getStyle() & Constants.FONT_STYLE_ITALIC);
        assertEquals(0, style.getStyle() & Constants.FONT_STYLE_UNDERLINED);

        assertTrue(style.isStrikeThrough());
        assertTrue(Constants.FONT_STYLE_STRIKETHROUGH
                != (byte) (style.getStyle() & Constants.FONT_STYLE_STRIKETHROUGH));

        assertTrue(Constants.FONT_STYLE_STRIKETHROUGH
                == (byte) (style.getStyleForWrite() & Constants.FONT_STYLE_STRIKETHROUGH));

    }

    public void testWithDefaults() throws Exception {
        FontStyle style = null;
        FontStyle defaultStyle = new FontStyle(Constants.FONT_FACE_PROPORTIONAL,
                Constants.FONT_STYLE_PLAIN, Constants.FONT_SIZE_MEDIUM);

        style = new FontStyle(Constants.FONT_FACE_MONOSPACE,
                Constants.NUMBER_NOT_SET, Constants.FONT_SIZE_SMALL, false);
        
        style = style.withDefaults(defaultStyle);
        
        assertEquals(Constants.FONT_FACE_MONOSPACE, style.getFace());
        assertEquals(Constants.FONT_STYLE_PLAIN, style.getStyle());
        assertEquals(Constants.FONT_SIZE_SMALL, style.getSize());
        assertFalse(style.isEnableMagnification());
        assertFalse(style.isStrikeThrough());
    }

}
