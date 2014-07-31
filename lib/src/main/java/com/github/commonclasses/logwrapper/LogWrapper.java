/*
 * This source is part of the CommonClasses repository.
 *
 * Copyright 2014 Kevin Liu (airk908@gmail.com)
 *
 * CommonClasses is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CommonClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CommonClasses.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.commonclasses.logwrapper;

import android.util.Log;

/**
 * Custom Android LogWrapper
 */
public class LogWrapper {
    private static boolean LOGGING_ENABLED = true;
    private static final int STACK_TRACE_LEVELS_UP = 5;

    /**
     * Control the LogWrapper on or off
     * @param on true or false
     */
    public static void control(boolean on) {
        LOGGING_ENABLED = on;
    }

    public static void v(String msg) {
        if (!LOGGING_ENABLED) return;
        Log.v(getClassName(), getAll() + msg);
    }

    public static void v(String TAG, String msg) {
        if (!LOGGING_ENABLED) return;
        Log.v(TAG, getAll() + msg);
    }

    public static void d(String msg) {
        if (!LOGGING_ENABLED) return;
        Log.d(getClassName(), getAll() + msg);
    }

    public static void d(String TAG, String msg) {
        if (!LOGGING_ENABLED) return;
        Log.d(TAG, getAll() + msg);
    }

    public static void e(String msg) {
        if (!LOGGING_ENABLED) return;
        Log.e(getClassName(), getAll() + msg);
    }

    public static void e(String TAG, String msg) {
        if (!LOGGING_ENABLED) return;
        Log.e(TAG, getAll() + msg);
    }

    public static void i(String msg) {
        if (!LOGGING_ENABLED) return;
        Log.i(getClassName(), getAll() + msg);
    }

    public static void i(String TAG, String msg) {
        if (!LOGGING_ENABLED) return;
        Log.i(TAG, getAll() + msg);
    }

    private static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP].getLineNumber();
    }

    private static String getClassName() {
        String fileName = Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP].getFileName();

        return fileName.substring(0, fileName.length() - 5);
    }

    private static String getMethodName() {
        return Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP].getMethodName();
    }

    private static String getAll() {
        return "[" + getMethodName() + "()-" + getLineNumber() + "]: ";
    }
}