package com.spring.rest.repository;

import com.spring.rest.model.User;
import org.springframework.dao.DataAccessException;

public interface UserRepository {

    void save(User user) throws DataAccessException;

}
