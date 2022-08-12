package com.spring.rest.repository.jpa;

import com.spring.rest.model.Visit;
import com.spring.rest.repository.VisitRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@Repository
@Profile("jpa")
public class JpaVisitRepositoryImpl implements VisitRepository {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public void save(Visit visit) throws DataAccessException {
        if (visit.getId() == null){
            entityManager.persist(visit);
        }else{
            entityManager.merge(visit);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Visit> findByPetId(Integer petId) {
        Query query = entityManager.createQuery("SELECT visit FROM Visit visit WHERE visit.pet.id=:id");
        query.setParameter("id",petId);
        return query.getResultList();
    }

    @Override
    public Visit findById(int id) throws DataAccessException {
        return entityManager.find(Visit.class,id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Visit> findAll() throws DataAccessException {
        return entityManager.createQuery("SELECT visit FROM Visit visit").getResultList();
    }

    @Override
    public void delete(Visit visit) throws DataAccessException {
        entityManager.remove(entityManager.contains(visit) ? visit : entityManager.merge(visit));
    }
}
