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
 * @author Saurav
 * <p>
 * Interface that extends MongoRepository and we can use all the methods of Mongo repository plus can write extra custom methods
 * </p>
 */
@org.springframework.stereotype.Repository
public interface Repository extends MongoRepository<User, String> {
	/**
	 * @param email
	 * @return Optional
	 * 
	 *         <p>
	 *         This method return User object corresponding to Email. If User object
	 *         is present in Data Base it will return the Same Object Otherwise
	 *         Optional Class Reference for null information.
	 *         </p>
	 */
	public Optional<User> getByEmail(String email);
}
