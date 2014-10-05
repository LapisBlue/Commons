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

import blue.lapis.common.persistence.jpa.sponge.JPAProvider;
import blue.lapis.common.persistence.jpa.sponge.JPAService;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Tests for the JPA Provider
 */
public class JPAProviderTest {

    private static final String DB = "test2";

    @Test
    public void connectTest(){
        try {
            // create DB, fails if it doesn't exsist (we should check first)
            createDB();
        } catch (SQLException e) {
            System.out.println("SQL Exception, DB missing");
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver missing, exiting test.");
            return;
        }

        // create a JPAService
        JPAService service = new JPAProvider("jdbc:mysql://localhost:3306/jpatest_");

        // fetch an EntityManager
        EntityManager em = service.getEntityManager(DB, Collections.<Class>singletonList(AnEntity.class));

        // we need a transaction, otherwise nothing will happen (in JEE the container does it for you)
        EntityTransaction t = em.getTransaction();
        t.begin();

        // create a dummy
        AnEntity entity = new AnEntity();
        entity.setText("Hello World!");
        em.persist(entity);

        t.commit();
    }

    private void createDB() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/","root","1234");
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            int ret = stmt.executeUpdate("CREATE DATABASE jpatest_" + DB);
        }finally {
            if(stmt!=null) {
                stmt.close();
            }
        }
        conn.close();
    }
}
