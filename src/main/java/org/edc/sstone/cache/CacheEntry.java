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
package org.edc.sstone.cache;

/**
 * An entry in a cache. This can be used as a building block for either LRU or LFU caches.
 * 
 * @author Greg Orlowski
 */
public class CacheEntry {

    public final Object object;
    public final int cacheId;
    private int usageCount = 0;
    private long timestamp;

    public CacheEntry(Object obj, int cacheId) {
        this.object = obj;
        this.cacheId = cacheId;
        markUsage();
    }

    public void markUsage() {
        this.usageCount++;
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getUsageCount() {
        return usageCount;
    }
    
    public void setUsageCount(int count) {
        usageCount = count;
    }
}
