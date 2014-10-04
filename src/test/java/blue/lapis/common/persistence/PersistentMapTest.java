package blue.lapis.common.persistence;

import blue.lapis.common.persistence.jpa.PersistentMapFactory;
import org.junit.Test;

import java.util.Map;

/**
 * Created on 05.10.2014.
 * @author Thomas
 */
public class PersistentMapTest {
    @Test
    public void testMap(){
        Map<String,String> testMap = PersistentMapFactory.newStringMap();

        System.out.println(testMap.toString());

        testMap.put("Hello","World");
    }
}
