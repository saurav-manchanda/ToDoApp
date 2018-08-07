package com.bridgelabz.todoapplication.noteservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bridgelabz.todoapplication.noteservice.model.Note;

/**
 * @author Saurav
 *         <p>
 *         Elastic repository for Notes
 *         </p>
 *
 */
public interface INoteElasticRepository extends ElasticsearchRepository<Note, String> {
	/**
	 * @param noteId
	 * @return Optional<Note>
	 *         <p>
	 *         This method is getting the note of corresponding noteId passed as the
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
