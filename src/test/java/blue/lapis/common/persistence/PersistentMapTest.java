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
package blue.lapis.common.persistence;

import blue.lapis.common.persistence.jpa.MapFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Tests for the PersistentMaps
 */
public class PersistentMapTest {

    @Test
    public void testMap(){

        Map<String,String> testMap;
        try {
            testMap = MapFactory.newPersistentStringMap();
        }catch (Exception e){
            System.out.println("Cannot create DB backed map, exiting early.");
            return;
        }



        Map<String,String> referenceMap = new HashMap<String, String>();
        Random rand = new Random(0xBEEF);

        System.out.println(testMap.toString());
        for(int i=0;i<1000;i++) {
            String randKey = randomKey(rand,2000);
            String randVal = randomValue(rand);
            testMap.put(randKey, randVal);
            referenceMap.put(randKey, randVal);
        }

        // same size?
        Assert.assertEquals(referenceMap.size(), testMap.size());

        // all entries persisted?
        for (String key : referenceMap.keySet()) {
            Assert.assertEquals(referenceMap.get(key),testMap.get(key));

        }

        // does contains work?
        for(int i=0;i<1000;i++) {
            String randKey = randomKey(rand,2000);
            Assert.assertEquals(referenceMap.containsKey(randKey),testMap.containsKey(randKey));
        }

        // does remove work?
        for(int i=0;i<1000;i++) {
            String randKey = randomKey(rand,2000);
            Assert.assertEquals(referenceMap.remove(randKey),testMap.remove(randKey));
        }

        // still same size?
        Assert.assertEquals(referenceMap.size(), testMap.size());
        {
            Set<Map.Entry<String, String>> referenceSet = referenceMap.entrySet();
            Set<Map.Entry<String, String>> testSet = testMap.entrySet();
            Assert.assertEquals(referenceSet.size(), testSet.size());
            for (Map.Entry<String, String> entry : referenceSet) {
                Assert.assertTrue(testSet.contains(entry));
            }
        }
        {
            Set<String> referenceSet = referenceMap.keySet();
            Set<String> testSet = testMap.keySet();
            Assert.assertEquals(referenceSet.size(), testSet.size());
            for (String entry : referenceSet) {
                Assert.assertTrue(testSet.contains(entry));
            }
        }
        {
            Collection<String> referenceSet = referenceMap.values();
            Collection<String> testSet = testMap.values();
            Assert.assertEquals(referenceSet.size(), testSet.size());
            for (String entry : referenceSet) {
                Assert.assertTrue(testSet.contains(entry));
            }
        }
    }

    private static String randomKey(Random rand,int pool){
        return Long.toHexString(rand.nextInt(pool));
    }

    private static String randomValue(Random rand){
        return Long.toHexString(rand.nextLong());
    }
}
