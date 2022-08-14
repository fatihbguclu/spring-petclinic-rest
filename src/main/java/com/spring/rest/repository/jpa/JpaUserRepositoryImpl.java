package com.spring.rest.repository.jpa;

import com.spring.rest.model.User;
import com.spring.rest.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Repository
@Profile("jpa")
public class JpaUserRepositoryImpl implements UserRepository {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public void save(User user) throws DataAccessException {
        if (entityManager.find(User.class,user.getUsername()) == null){
            entityManager.persist(user);
        }else{
            entityManager.merge(user);
        }
    }
}
