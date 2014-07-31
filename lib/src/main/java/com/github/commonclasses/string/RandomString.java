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

package com.github.commonclasses.string;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Random String Generator
 */
public class RandomString {
    private static int DEFAULT_STRING_LONG = 6;
    private static char[] CHARSET_AZ_09 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    /**
     * Get a random string with default length
     *
     * @return the random string
     */
    public static String getRandomString() {
        return randomString(CHARSET_AZ_09, DEFAULT_STRING_LONG);
    }

    /**
     * Change the default length with new value
     *
     * @param newLength the new length you wanna change to
     * @return the default length
     */
    public static int changeDefaultLength(int newLength) {
        if (newLength > 0) {
            DEFAULT_STRING_LONG = newLength;
        }
        return DEFAULT_STRING_LONG;
    }

    private static String randomString(char[] characterSet, int length) {
        Random random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            // picks a random index out of character set > random character
            int randomCharIndex = random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result);
    }
}
