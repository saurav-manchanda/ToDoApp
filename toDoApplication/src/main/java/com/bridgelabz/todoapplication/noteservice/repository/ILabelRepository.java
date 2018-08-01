/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a Label Repository that is extending Mongo DB repository so as to get the implementations of monghoDB.  
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.noteservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.todoapplication.noteservice.model.Label;

/**
 * @author Saurav
 *         <p>
 *         This is a Repository for Labels. Repository that is extending Mongo
 *         DB repository so as to get the implementations of monghoDB. created a
 *         custom method in the Repository
 *         </p>
 */
@Repository
public interface ILabelRepository extends MongoRepository<Label, String> {
	/**
	 * @param userId
	 * @return List of labels
	 *         <p>
	 *         This method is for finding the labels by user id
	 *         </p>
	 */
	public List<Label> findLabelsByUserId(String userId);
	/**
	 * <p>
	 * This method is for finding the Labels by their respective LabelName
	 * </p>
	 * @param labelName
	 * @return
	 */
	public List<Label> findByLabelName(String labelName);
}
