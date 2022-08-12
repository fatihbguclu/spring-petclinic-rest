package com.spring.rest.repository.jpa;

import com.spring.rest.model.Owner;
import com.spring.rest.repository.OwnerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.Collection;

@Repository
@Profile("jpa")
public class JpaOwnerRepositoryImpl implements OwnerRepository {

    /*
    * The persistence context is the first-level cache
    * Where all the entities are fetched from the database or saved to the database.
    * It sits between our application and persistent storage.
    */
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public Collection<Owner> findByLastName(String lastName) throws DataAccessException {
        // using 'join fetch' because a single query should load both owners and pets
        // using 'left join fetch' because it might happen that an owner does not have pets yet

        Query query = entityManager.createQuery(
                 "SELECT DISTINCT owner FROM Owner owner " +
                    "left join fetch owner.pets " +
                    "WHERE owner.lastName LIKE :lastname");

        query.setParameter("lastname", lastName);

        return query.getResultList();
    }

    @Override
    public Collection<Owner> findAll() throws DataAccessException {

        Query query = entityManager.createQuery("SELECT owner FROM Owner owner");
        return query.getResultList();
    }

    @Override
    public Owner findById(int id) throws DataAccessException {

        Query query = entityManager.createQuery(
                 "SELECT Owner FROM Owner owner " +
                    "LEFT JOIN FETCH owner.pets " +
                    "WHERE owner.id =:id"
        );
        query.setParameter("id",id);
        return (Owner) query.getSingleResult();
    }

    @Override
    public void save(Owner owner) throws DataAccessException {
        if (owner.getId() == null){
            this.entityManager.persist(owner);
        }else {
            this.entityManager.merge(owner);
        }
    }

    @Override
    public void delete(Owner owner) throws DataAccessException {
        this.entityManager.remove(this.entityManager.contains(owner) ? owner : this.entityManager.merge(owner));
    }
}
