package dev.jlkeesh.library.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract class AbstractRepository {
    protected final EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql-persistence-unit");
    protected final EntityManager em = emf.createEntityManager();

    protected void commitTransaction() {
        em.getTransaction().commit();
    }

    protected void beginTransaction() {
        em.getTransaction().begin();
    }
}
