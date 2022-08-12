package com.spring.rest.repository.jpa;

import com.spring.rest.model.Vet;
import com.spring.rest.repository.VetRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.Collection;

@Repository
@Profile("jpa")
public class JpaVetRepositoryImpl implements VetRepository {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Vet> findAll() throws DataAccessException {
        return this.entityManager.createQuery("SELECT vet FROM Vet vet").getResultList();
    }

    @Override
    public Vet findById(int id) throws DataAccessException {
        return this.entityManager.find(Vet.class, id);
    }

    @Override
    public void save(Vet vet) throws DataAccessException {
        if (vet.getId() == null) {
            this.entityManager.persist(vet);
        } else {
            this.entityManager.merge(vet);
        }
    }

    @Override
    public void delete(Vet vet) throws DataAccessException {
        this.entityManager.remove(entityManager.contains(vet) ? vet : entityManager.merge(vet));
    }
}
