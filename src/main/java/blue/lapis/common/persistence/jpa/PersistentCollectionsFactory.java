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
package blue.lapis.common.persistence.jpa;

import java.util.Collections;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Factory class that creates database-backed collections for convenience
 */
public class PersistentCollectionsFactory {

    // FIXME this should be fetched from the Sponge API ServiceManager as soon as sponge provides JPA
    private static JPAService jpaService = new JPAProvider("jdbc:mysql://localhost:3306/jpatest_");

    public static PersistentMap<String,String> newPersistentStringMap(){
        // fetch our entity manager
        EntityManager em = jpaService.getEntityManager("lapis", Collections.<Class>singletonList(PersistentStringMap.class));

        EntityTransaction t = em.getTransaction();
        t.begin();

        PersistentStringMap map = new PersistentStringMap();
        em.persist(map);

        t.commit();

        return new PersistentStringMapWrapper(em,map.getId());
    }

    public static Map<String,String> findPersistentStringMap(Long mapId){
        // fetch our entity manager
        EntityManager em = jpaService.getEntityManager("lapis", Collections.<Class>singletonList(PersistentStringMap.class));

        EntityTransaction t = em.getTransaction();
        t.begin();
        PersistentStringMap map = em.find(PersistentStringMap.class,mapId);
        t.commit();

        return new PersistentStringMapWrapper(em,map.getId());
    }

}
