/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a Repository that is extending Mongo DB repository so as to get the implementations of monghoDB.  
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.noteservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.todoapplication.noteservice.model.Note;

/**
 * @author Saurav
 *         <p>
 *         This is a Repository for Notes. Repository that is extending Mongo DB
 *         repository so as to get the implementations of monghoDB. created 2
 *         custome methods in the repository.
 *         </p>
 */
@Repository
public interface INoteRepository extends MongoRepository<Note, String> {
	/**
	 * @param noteId
	 * @return Optional<Note>
	 *         <p>
	 * 		This method is getting the note of corresponding noteId passed as the
	 *         parameter of the method
	 *         </p>
	 */
	public Optional<Note> getByNoteId(String noteId);

	/**
	 * @param string
	 * @return List of Notes
	 *         <p>
	 *         This method is for finding Notes by UserID passed in the parameter
	 *         and returning a list containing all the notes corresponding to that
	 *         userId
	 *         </p>
	 */
	public List<Note> findNotesByUserId(String userId);
}
