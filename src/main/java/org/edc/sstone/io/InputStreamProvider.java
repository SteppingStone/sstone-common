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
package org.edc.sstone.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Greg Orlowski
 */
public interface InputStreamProvider {

    public InputStream getInputStream(String path) throws IOException;

    /**
     * Given a path that does not contain a URL, return a fully-qualified URL. For example, for a
     * file implementation, if path was &quot;/a/b/c&quot; then this would return file:///a/b/c.
     * 
     * @param path
     *            a file path
     * @return a URL that includes a protocol prefix or null if the implementation does not use a
     *         protocol that is recognized by the J2ME APIs.
     */
    public String getUrl(String path);

}
