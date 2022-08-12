package com.spring.rest.repository.jpa;

import com.spring.rest.model.Pet;
import com.spring.rest.model.PetType;
import com.spring.rest.model.Visit;
import com.spring.rest.repository.PetTypeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.Collection;
import java.util.List;

@Repository
@Profile("jpa")
public class JpaPetTypeRepositoryImpl implements PetTypeRepository {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public PetType findById(int id) throws DataAccessException {
        return entityManager.find(PetType.class,id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<PetType> findAll() throws DataAccessException {
        return entityManager.createQuery("SELECT ptype FROM PetType ptype").getResultList();
    }

    @Override
    public void save(PetType petType) throws DataAccessException {
        if (petType.getId()==null){
            entityManager.persist(petType);
        }else{
            entityManager.merge(petType);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void delete(PetType petType) throws DataAccessException {
        entityManager.remove(entityManager.contains(petType) ? petType : entityManager.merge(petType));
        Integer petTypeId = petType.getId();

        List<Pet> pets = entityManager.createQuery("SELECT pet FROM Pet pet WHERE pet.type.id=" + petTypeId).getResultList();
        for (Pet pet : pets){
            List<Visit> visits = pet.getVisits();
            for(Visit visit : visits){
                entityManager.createQuery("DELETE FROM Visit visit WHERE visit.id=" + visit.getId()).executeUpdate();
            }
            entityManager.createQuery("DELETE FROM Pet pet WHERE pet.id=" + pet.getId()).executeUpdate();
        }
        entityManager.createQuery("DELETE FROM PetType ptype WHERE ptype.id" + petTypeId).executeUpdate();
    }
}
