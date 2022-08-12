package com.spring.rest.repository.jpa;

import com.spring.rest.model.Pet;
import com.spring.rest.model.PetType;
import com.spring.rest.repository.PetRepository;
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
public class JpaPetRepositoryImpl implements PetRepository {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Pet> findAll() throws DataAccessException {
        return entityManager.createQuery("SELECT pet FROM Pet pet").getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PetType> findPetTypes() throws DataAccessException {
        return entityManager.createQuery("SELECT ptype FROM PetType ptype ORDER BY ptype.name").getResultList();
    }

    @Override
    public Pet findById(int id) throws DataAccessException {
        return entityManager.find(Pet.class,id);
    }

    @Override
    public void save(Pet pet) throws DataAccessException {
        if (pet.getId() == null){
            entityManager.persist(pet);
        }else{
            entityManager.merge(pet);
        }
    }

    @Override
    public void delete(Pet pet) throws DataAccessException {
        String petId = pet.getId().toString();
        entityManager.createQuery("DELETE FROM Visit visit WHERE visit.pet.id=" + petId).executeUpdate();
        entityManager.createQuery("DELETE FROM Pet pet WHERE pet.id=" + petId).executeUpdate();
        if (entityManager.contains(pet)){
            entityManager.remove(pet);
        }
    }
}
