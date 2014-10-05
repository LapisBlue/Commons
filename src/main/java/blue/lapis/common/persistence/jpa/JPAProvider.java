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

import com.google.common.collect.Maps;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.internal.jpa.deployment.SEPersistenceUnitInfo;
import org.eclipse.persistence.jpa.PersistenceProvider;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitTransactionType;

/**
 * {@inheritDoc}
 */
public class JPAProvider implements JPAService {

    private static final String PUNIT_NAME = "sponge";

    private Map<String,String> datasourceProperties;
    private String jdbcUrlPrefix;

    public JPAProvider(String jdbcUrlPrefix) {
        this.jdbcUrlPrefix = jdbcUrlPrefix;

        // Load settings from sponge config, so we don't need a persistence.xml
        datasourceProperties = Maps.newHashMap();
        datasourceProperties.put(PersistenceUnitProperties.JDBC_DRIVER, "com.mysql.jdbc.Driver");
        datasourceProperties.put(PersistenceUnitProperties.JDBC_USER, "root");
        datasourceProperties.put(PersistenceUnitProperties.JDBC_PASSWORD, "1234");
        //datasourceProperties.put(DDL_GENERATION, "create-tables");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityManager getEntityManager(String databaseId, List<Class> entities) {
        Map<String,String> mOptions = Maps.newHashMap();
        mOptions.putAll(datasourceProperties);
        // try to create tables if not exsisting
        mOptions.put(PersistenceUnitProperties.DDL_GENERATION, "create-tables");
        return getEntityManagerFactory(databaseId, mOptions,entities).createEntityManager();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityManagerFactory getEntityManagerFactory(String databaseId, Map<String, String> options, List<Class> entities) {
        //---- assemble the persistence.xml / properties
        Map<String,String> mOptions = Maps.newHashMap();
        // any custom options?
        if(options!=null) {
            mOptions.putAll(options);
        }
        // overwrite with properties from sponge if necessary
        mOptions.putAll(datasourceProperties);
        // set the URL: PREFIX + databaseId
        mOptions.put(PersistenceUnitProperties.JDBC_URL,generateJDBCUrl(databaseId));

        // assemble a persistence.xml dynamically
        SEPersistenceUnitInfo punit = new SEPersistenceUnitInfo();
        punit.setPersistenceProviderClassName("org.eclipse.persistence.jpa.PersistenceProvider");
        punit.setTransactionType(PersistenceUnitTransactionType.RESOURCE_LOCAL);
        punit.setPersistenceUnitName("sponge-eclipselink");
        punit.setClassLoader(ClassLoader.getSystemClassLoader());

        Path currentRelativePath = Paths.get("");
        try {
            punit.setPersistenceUnitRootUrl(currentRelativePath.toUri().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // would be could to use java8 here -.-
        List<String> classNames = new ArrayList<String>();
        for (Class c : entities) {
            classNames.add(c.getCanonicalName());
        }

        punit.setManagedClassNames(classNames);

        Properties properties = new Properties();
        properties.putAll(mOptions);
        punit.setProperties(properties);
        PersistenceProvider provider = new PersistenceProvider();

        return provider.createContainerEntityManagerFactory(punit, mOptions);
    }

    private String generateJDBCUrl(String databaseId){
        return jdbcUrlPrefix.concat(databaseId);
    }
}
