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
	 * <p>
	 * This method is for creating a Note in the Application.
	 * </p>
	 * 
	 * @param note
	 * @param token
	 * @throws ToDoException
	 * 
	 */
	void createNote(NoteDTO noteDto, String token) throws ToDoException;

	/**
	 * <p>
	 * This method is for creating a Note in the Application.
	 * </p>
	 * 
	 * @param noteId
	 * @param token
	 * @throws ToDoException
	 *             <p>
	 *             This method is for deleting a Note in the application
	 *             </p>
	 */
	void deleteNote(String noteId, String token) throws ToDoException;

	/**
	 * <p>
	 * This method is for updating a Note or editing a Note in the Application
	 * </p>
	 * 
	 * @param noteId
	 * @param title
	 * @param description
	 * @param token
	 * @throws ToDoException
	 */
	void updateNote(String noteId, String title, String description, String token) throws ToDoException;

	/**
	 * <p>
	 * This method is displaying All the Notes available in the database
	 * </p>
	 * 
	 * @param token
	 * @return
	 * @throws ToDoException
	 * 
	 */
	List<Note> displayAllNotes(String token) throws ToDoException;

	/**
	 * <p>
	 * This method is for changing the color of the Note in the database
	 * </p>
	 * 
	 * @param noteId
	 * @param colour
	 * @param token
	 * @throws ToDoException
	 * 
	 */
	void changeColourOfNote(String noteId, String colour, String token) throws ToDoException;

	/**
	 * <p>
	 * This method is for deleting the note from the trash
	 * </p>
	 * 
	 * @param noteId
	 * @param token
	 * @throws ToDoException
	 * 
	 */
	void deleteFromTrash(String noteId, String token) throws ToDoException;

	/**
	 * <p>
	 * This method is for restoring the note from the trash
	 * </p>
	 * 
	 * @param noteId
	 * @param token
	 * @throws ToDoException
	 * 
	 */
	void restoreFromTrash(String noteId, String token) throws ToDoException;

	/**
	 * <p>
	 * This is for pinning the Note
	 * </p>
	 * 
	 * @param noteId
	 * @param token
	 * @throws ToDoException
	 * 
	 */
	void pinNote(String noteId, String token) throws ToDoException;

	/**
	 * <p>
	 * This method is for creating the Label.
	 * </p>
	 * 
	 * @param label
	 * @param token
	 * @throws ToDoException
	 */
	void createLabel(Label label, String token) throws ToDoException;

	/**
	 * <p>
	 * This method is for creating the label.
	 * </p>
	 * 
	 * @param labelId
	 * @param labelName
	 * @param token
	 * @throws ToDoException
	 */
	void updateLabel(String labelId, String labelName, String token) throws ToDoException;

	/**
	 * <p>
	 * This method is for deleting the Label
	 * </p>
	 * 
	 * @param labelId
	 * @param token
	 * @throws ToDoException
	 */
	void deleteLabel(String labelId, String token) throws ToDoException;

	/**
	 * <p>
	 * This method is for displaying all the labels
	 * </p>
	 * 
	 * @param token
	 * @return
	 * @throws ToDoException
	 */
	List<Label> displayLabels(String token) throws ToDoException;

	/**
	 * This method is for archiving the Note
	 * 
	 * @param noteId
	 * @param token
	 * @throws ToDoException
	 */
	void archieveNote(String noteId, String token) throws ToDoException;

	/**
	 * This method is for setting the remainder to remind the user about the note he
	 * or she created
	 * 
	 * @param noteId
	 * @param remindTime
	 * @param token
	 * @throws ToDoException
	 * @throws ParseException
	 */
	void setReminder(String noteId, String remindTime, String token) throws ToDoException, ParseException;

	/**
	 * This method is for searching the Notes by LabelName
	 * 
	 * @param labelName
	 * @param token
	 * @return
	 * @throws ToDoException
	 */
	List<Note> searchNotesByLabel(String labelName, String token) throws ToDoException;

	/**
	 * This method is for deleting the Label from the Note but not from the label
	 * repository
	 * 
	 * @param noteId
	 * @param labelName
	 * @param token
	 * @throws ToDoException
	 */
	void deleteLabelFromNote(String noteId, String labelName, String token) throws ToDoException;

}
