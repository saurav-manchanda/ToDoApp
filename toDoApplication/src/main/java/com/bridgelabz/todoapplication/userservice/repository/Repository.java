/********************************************************************************* *
 * Purpose: To do Login Registration with the help of MONGODB repository
 * 
 * @author Saurav Manchanda
 * @version 1.0
 * @since 11/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.userservice.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.todoapplication.userservice.model.User;

/**
 * @author Saurav:
 * Interface that extends MongoRepository and we can use all the methods of Mongo repository plus can write extra custom methods
 */
@org.springframework.stereotype.Repository
public interface Repository extends MongoRepository<User, String> {
	public Optional<User> getByEmail(String email);
	
}
