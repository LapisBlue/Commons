/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered.org <http://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package blue.lapis.common.persistence.jpa;


import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *  Returns a JPA {@link EntityManager} that can be used as a persistence provider.
 *
 * @author n1b
 */
public interface JPAService {

    /**
     * Returns an EntityManager,
     * @param databaseId id that identifies the backing datasource
     * @param entities All {@link Entity} annotated classed that should be managed
     * @return an {@link EntityManager} for the datasource identified with databaseId
     */
    EntityManager getEntityManager(String databaseId, List<Class> entities);

    /**
     * Returns an EntityManagerFactory for
     * @param databaseId id that identifies the backing datasource
     * @return an {@link EntityManager} for the datasource identified with databaseId
     */
    EntityManagerFactory getEntityManagerFactory(String databaseId, @Nullable Map<String, String> options, List<Class> entities);
}
