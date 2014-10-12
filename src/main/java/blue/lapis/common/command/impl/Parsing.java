/*
 * LapisCommons
 * Copyright (c) 2014, Lapis <https://github.com/LapisBlue>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package blue.lapis.common.command.impl;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Locale;

/**
 * Basic parsing tasks
 */
public class Parsing {

    public static String toLowerCase(String s) {
        return s.toLowerCase(Locale.ENGLISH);
    }

    public static boolean startsWithIgnoreCase(String s, String start) {
        return s.regionMatches(true, 0, start, 0, start.length());
    }

    public static ImmutableList<String> split(String s, String delimiter) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        int tokenEnd = s.indexOf(delimiter);
        int loc = 0;
        while (tokenEnd >= 0) {
            if (tokenEnd > loc) {
                builder.add(s.substring(loc, tokenEnd));
            }
            loc = tokenEnd + delimiter.length();
            tokenEnd = s.indexOf(delimiter, loc);
        }
        if (loc < s.length()) builder.add(s.substring(loc, s.length()));

        return builder.build();
    }

    public static String join(List<String> strings) {
        int length = 0;
        for (String s : strings) {
            length += s.length();
        }

        char[] value = new char[length];
        int count = 0;
        for (String s : strings) {
            int len = s.length();
            if (len == 0) continue;
            int newCount = count + len;
            s.getChars(0, len, value, count);
            count = newCount;
        }
        return new String(value);
    }

    public static String join(List<String> strings, String delimiter) {
        int length = 0;
        for (String s : strings) {
            length += s.length();
        }
        length += delimiter.length() * (strings.size() - 1);

        char[] value = new char[length];
        int count = 0;
        int numStrings = strings.size();
        for (String s : strings) {
            int len = s.length();
            if (len == 0) continue;
            int newCount = count + len;
            s.getChars(0, len, value, count);
            count = newCount;

            numStrings--;
            if (numStrings > 0) {
                len = delimiter.length();
                if (len == 0) continue;
                newCount = count + len;
                delimiter.getChars(0, len, value, count);
                count = newCount;
            }
        }
        return new String(value);
    }

}
