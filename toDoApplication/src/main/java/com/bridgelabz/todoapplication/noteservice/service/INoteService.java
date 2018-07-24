/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating an Interface for Note Service.
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.noteservice.service;

import java.text.ParseException;
import java.util.List;

import com.bridgelabz.todoapplication.noteservice.model.Label;
import com.bridgelabz.todoapplication.noteservice.model.Note;
import com.bridgelabz.todoapplication.noteservice.model.NoteDTO;
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
	 *             <p>
	 *             This method is for creating a Note in the Application.
	 *             </p>
	 */
	void createNote(NoteDTO noteDto, String token) throws ToDoException;

	/**
	 * @param noteId
	 * @param token
	 * @throws ToDoException
	 *             <p>
	 *             This method is for deleting a Note in the application
	 *             </p>
	 */
	void deleteNote(String noteId, String token) throws ToDoException;

	/**
	 * @param noteId
	 * @param title
	 * @param description
	 * @param token
	 * @throws ToDoException
	 *             <p>
	 *             This method is for updating a Note or editing a Note in the
	 *             Application
	 *             </p>
	 */
	void updateNote(String noteId,String title,String description, String token) throws ToDoException;

	/**
	 * @param token
	 * @return
	 * @throws ToDoException
	 *             <p>
	 *             This method is displaying All the Notes available in the database
	 *             </p>
	 */
	List<Note> displayAllNotes(String token) throws ToDoException;

	/**
	 * @param noteId
	 * @param colour
	 * @param token
	 * @throws ToDoException
	 *             <p>
	 *             This method is for changing the color of the Note in the database
	 *             </p>
	 */
	void changeColourOfNote(String noteId, String colour, String token) throws ToDoException;

	/**
	 * @param noteId
	 * @param token
	 * @throws ToDoException
	 *             <p>
	 *             This method is for deleting the note from the trash
	 *             </p>
	 */
	void deleteFromTrash(String noteId, String token) throws ToDoException;

	/**
	 * @param noteId
	 * @param token
	 * @throws ToDoException
	 *             <p>
	 *             This method is for restoring the note from the trash
	 *             </p>
	 */
	void restoreFromTrash(String noteId, String token) throws ToDoException;

	/**
	 * @param noteId
	 * @param token
	 * @throws ToDoException
	 *             <p>
	 *             This is for pinning the Note
	 *             </p>
	 */
	void pinNote(String noteId, String token) throws ToDoException;

	void createLabel(Label label, String token) throws ToDoException;

	void updateLabel(String labelId, String labelName, String token) throws ToDoException;

	void deleteLabel(String labelId, String token) throws ToDoException;

	List<Label> displayLabels(String token) throws ToDoException;

	void archieveNote(String noteId, String token) throws ToDoException;

	void setReminder(String noteId, String remindTime,String token) throws ToDoException, ParseException;


}
