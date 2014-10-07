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

package blue.lapis.common.command;

import blue.lapis.common.command.impl.Parsing;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;

/**
 *
 */
public class ParsingTest {

    @Test
    public void splitTest() {
        ImmutableList<String> parts = Parsing.split("qwertyuiopabczxcvbnmabcqetubc","abc");

        Assert.assertArrayEquals(parts.toArray(), new String[] { "qwertyuiop", "zxcvbnm", "qetubc" });
    }

    @Test
    public void joinTest() {
        List<String> test = new ImmutableList.Builder<String>()
                .add("Lorem", "ipsum", "dolor","sit","amet,","consectetur","adipiscing","elit")
                .build();
        Assert.assertEquals("Loremipsumdolorsitamet,consecteturadipiscingelit", Parsing.join(test));
        Assert.assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit", Parsing.join(test, " "));
    }

    @Test
    public void splitjoin() {
        String test = Parsing.join(
                Parsing.split("Lorem ipsum dolor sit amet, consectetur adipiscing elit", " ")
        );

        Assert.assertEquals("Loremipsumdolorsitamet,consecteturadipiscingelit",test);
    }

    //Uncomment to run benchmark
    //@Test
    public void benchmark() {
        String src = "qwertyuiopabczxcvbnmabcqetubc";
        String delimiter = "abc";
        String pattern = "\\Qabc\\E";
        Pattern p = Pattern.compile(pattern);
        int iterations = 9000000; //9 mil


        long start = System.currentTimeMillis();
        for(int i=0; i<iterations; i++) {
            Parsing.split(src,delimiter);
        }
        long parsingSplit = System.currentTimeMillis()-start;

        start = System.currentTimeMillis();
        for(int i=0; i<iterations; i++) {
            src.split(pattern);
        }
        long patternSplit = System.currentTimeMillis()-start;

        start = System.currentTimeMillis();
        for(int i=0; i<iterations; i++) {
            p.split(src);
            //src.split(pattern);
        }
        long patternCompiled = System.currentTimeMillis()-start;

        start = System.currentTimeMillis();
        for(int i=0; i<iterations; i++) {
            Splitter.on(delimiter).splitToList(src);
        }
        long guava = System.currentTimeMillis()-start;

        System.out.println("pattern: "+patternSplit+" parsing: "+parsingSplit+" compiledpattern: " +
                ""+patternCompiled + " guava: "+guava);
    }

    //@Test
    public void benchmarkJoin() {
        List<String> test = new ImmutableList.Builder<String>()
                .add("Lorem", "ipsum", "dolor","sit","amet,","consectetur","adipiscing","elit")
                .build();

        int iterations = 10000; //10k

        long start = System.currentTimeMillis();
        /*StringBuilder builder = new StringBuilder();
        for(int i=0; i<iterations; i++) {
            for(String s : test) builder.append(s);
            builder.toString();
        }
        long builderJoin = System.currentTimeMillis()-start;
        */
        start = System.currentTimeMillis();
        for(int i=0; i<iterations; i++) {
            Joiner.on("asdf").join(test);
        }
        long guavaJoin = System.currentTimeMillis()-start;

        start = System.currentTimeMillis();
        for(int i=0; i<iterations; i++) {
            Parsing.join(test,"asdf");
        }
        long parsingJoin = System.currentTimeMillis()-start;

        System.out.println("guava:"+guavaJoin+" parsing:"+parsingJoin);
    }
}
