package com.spring.rest.repository.jpa;

import com.spring.rest.model.Specialty;
import com.spring.rest.repository.SpecialtyRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.Collection;

@Repository
@Profile("jpa")
public class JpaSpecialtyRepositoryImpl implements SpecialtyRepository {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public Specialty findById(int id) throws DataAccessException {
        return entityManager.find(Specialty.class,id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Specialty> findAll() throws DataAccessException {
        return entityManager.createQuery("SELECT s FROM Specialty s").getResultList();
    }

    @Override
    public void save(Specialty specialty) throws DataAccessException {
        if (specialty.getId() == null) {
            entityManager.persist(specialty);
        }else{
            entityManager.merge(specialty);
        }
    }

    @Override
    public void delete(Specialty specialty) throws DataAccessException {
        entityManager.remove(entityManager.contains(specialty) ? specialty : entityManager.merge(specialty));
        Integer specId = specialty.getId();
        entityManager.createNativeQuery("DELETE FROM vet_specialties  WHERE speciality_id=" + specId).executeUpdate();
        entityManager.createQuery("DELETE FROM Specialty sp WHERE sp.id=" + specId).executeUpdate();
    }
}
