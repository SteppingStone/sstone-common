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

/**
 * Fixed-pixel spacing
 * 
 * @author Greg Orlowski
 */
public class FixedSpacing implements Spacing {

    protected short top;
    protected short right;
    protected short bottom;
    protected short left;

    public FixedSpacing(short spacing) {
        top = right = bottom = left = spacing;
    }

    public FixedSpacing(short vertical, short horizontal) {
        top = bottom = vertical;
        left = right = horizontal;
    }

    public FixedSpacing(short top, short right, short bottom, short left) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

}
