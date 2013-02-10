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
package org.edc.sstone.util;

import java.util.Vector;

/**
 * This is like {@link java.util.Timer}, except it will only start a successive {@link TimerTask}
 * once the previous one has completed (cancel() called).
 * 
 * All tasks run in a single thread
 * 
 * @author Greg Orlowski
 */
public class TimerQueue {

    Vector queue = new Vector();
    private boolean allowNewTasks = true;

    public TimerQueue() {
        Worker workerThread = new Worker();
        workerThread.start();
    }

    public void schedule(TimerTask task, long delay, long period) {
        synchronized (queue) {
            if (allowNewTasks) {
                queue.addElement(new QueueEntry(false, delay, period, task));
                queue.notify();
            }
        }
    }

    public void scheduleOnce(TimerTask task, long delay) {
        synchronized (queue) {
            if (allowNewTasks) {
                queue.addElement(new QueueEntry(true, delay, 0l, task));
                queue.notify();
            }
        }
    }

    /**
     * Because J2ME does not support finalizers, you should call manually this to clean up after
     * you're done using this {@link TimerQueue}.
     */
    public void cleanup() {
        allowNewTasks = false;

        // I think we need to ensure that we notify the queue so we know there
        // are no more threads waiting for it.
        synchronized (queue) {
            queue.notify();
            queue.removeAllElements();
        }
    }

    static class QueueEntry {
        private final boolean runOnce;
        private final long delay;
        private final long period;
        private TimerTask task;

        private QueueEntry(boolean runOnce, long delay, long period, TimerTask task) {
            super();
            this.runOnce = runOnce;
            this.delay = delay;
            this.period = period;
            this.task = task;
        }
    }

    private class Worker extends Thread {

        private void mainLoop() {
            QueueEntry queueEntry;

            WHILE_ALLOW_NEW_TASKS: while (allowNewTasks) {
                synchronized (queue) {
                    while (allowNewTasks && queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }
                    if (queue.isEmpty()) {
                        continue WHILE_ALLOW_NEW_TASKS;
                    }
                    queueEntry = (QueueEntry) queue.firstElement();
                    queue.removeElementAt(0);
                }

                try {
                    if (queueEntry.delay > 0l)
                        Thread.sleep(queueEntry.delay);

                    if (queueEntry.runOnce) {
                        queueEntry.task.run();
                    } else {
                        while (!queueEntry.task.isCancelled()) {
                            queueEntry.task.run();
                            if (queueEntry.period > 0l)
                                Thread.sleep(queueEntry.period);
                        }
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }

        public void run() {
            try {
                mainLoop();
            } finally {
                synchronized (queue) {
                    allowNewTasks = false; // just in case this was killed not cleaned up?
                    queue.removeAllElements();
                }
            }
        }
    }
}
