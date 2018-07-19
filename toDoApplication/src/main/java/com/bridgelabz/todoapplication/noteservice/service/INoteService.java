/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating an Interface for Note Service.
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.noteservice.service;

import java.util.List;

import com.bridgelabz.todoapplication.noteservice.model.Note;
import com.bridgelabz.todoapplication.utilservice.ToDoException;

/**
 * @author Saurav
 *         <p>
 *         This is an interface having methods for CRUD operations as well as
 *         functionalities related to the ToDoApp application.
 *         </p>
 */
public interface INoteService {
/**
 * @param note
 * @param token
 * @throws ToDoException
 * <p>
 * This method is for creating a Note in the Application.
 * </p>
 */
	void createNote(Note note, String token) throws ToDoException;
/**
 * @param noteId
 * @param token
 * @throws ToDoException
 * <p>
 * This method is for deleting a Note in the application
 * </p>
 */
	void deleteNote(String noteId, String token) throws ToDoException;
/**
 * @param noteId
 * @param title
 * @param description
 * @param token
 * @throws ToDoException
 * <p>
 * This method is for updating a Note or editing a Note in the Application
 * </p>
 */
	void updateNote(String noteId, String title, String description, String token) throws ToDoException;
/**
 * @param token
 * @return
 * @throws ToDoException
 * <p>
 * This method is displaying All the Notes available in the database
 * </p>
 */
	List<Note> displayAllNotes(String token) throws ToDoException;
void changeColourOfNote(String noteId,String colour, String token) throws ToDoException;
void deleteFromTrash(String noteId, String token) throws ToDoException;

}
