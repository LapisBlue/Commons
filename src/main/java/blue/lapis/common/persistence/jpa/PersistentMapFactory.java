package blue.lapis.common.persistence.jpa;

import java.util.Collections;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created on 05.10.2014.
 * @author Thomas
 */
public class PersistentMapFactory {

    private static JPAService jpaService = new JPAProvider("jdbc:mysql://localhost:3306/jpatest_");

    public static Map<String,String> newStringMap(){
        EntityManager em = jpaService.getEntityManager("lapis", Collections.<Class>singletonList(PersistentStringMap.class));
        EntityTransaction t = em.getTransaction();
        t.begin();
        PersistentStringMap map = new PersistentStringMap();
        em.persist(map);
        t.commit();
        return new PersistentStringMapWrapper(em,map.getId());
    }

}
