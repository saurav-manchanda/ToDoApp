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
	 * @param userID
	 * @throws ToDoException
	 * 
	 */
	String createNote(NoteDTO noteDto, String userId) throws ToDoException;

	/**
	 * <p>
	 * This method is for creating a Note in the Application.
	 * </p>
	 * 
	 * @param noteId
	 * @param userId
	 * @throws ToDoException
	 *             <p>
	 *             This method is for deleting a Note in the application
	 *             </p>
	 */
	String deleteNote(String noteId, String userId) throws ToDoException;

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
	String updateNote(String noteId, String title, String description, String userId) throws ToDoException;

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
	List<Note> displayAllNotes(String userId) throws ToDoException;

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
	String changeColourOfNote(String noteId, String colour, String userId) throws ToDoException;

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
	String deleteFromTrash(String noteId, String userId) throws ToDoException;

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
	String restoreFromTrash(String noteId, String userId) throws ToDoException;

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
	String pinNote(String noteId, String userId) throws ToDoException;

	/**
	 * <p>
	 * This method is for creating the Label.
	 * </p>
	 * 
	 * @param label
	 * @param token
	 * @throws ToDoException
	 */
	String createLabel(Label label, String userId) throws ToDoException;

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
	String updateLabel(String labelId, String labelName, String userId) throws ToDoException;

	/**
	 * <p>
	 * This method is for deleting the Label
	 * </p>
	 * 
	 * @param labelId
	 * @param token
	 * @throws ToDoException
	 */
	String deleteLabel(String labelId, String userId) throws ToDoException;

	/**
	 * <p>
	 * This method is for displaying all the labels
	 * </p>
	 * 
	 * @param token
	 * @return
	 * @throws ToDoException
	 */
	List<Label> displayLabels(String userId) throws ToDoException;

	/**
	 * This method is for archiving the Note
	 * 
	 * @param noteId
	 * @param token
	 * @throws ToDoException
	 */
	String archieveNote(String noteId, String userId) throws ToDoException;

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
	String setReminder(String noteId, String remindTime, String userId) throws ToDoException, ParseException;

	/**
	 * This method is for searching the Notes by LabelName
	 * 
	 * @param labelName
	 * @param token
	 * @return
	 * @throws ToDoException
	 */
	List<Note> searchNotesByLabel(String labelName, String userId) throws ToDoException;

	/**
	 * This method is for deleting the Label from the Note but not from the label
	 * repository
	 * 
	 * @param noteId
	 * @param labelName
	 * @param token
	 * @throws ToDoException
	 */
	String deleteLabelFromNote(String noteId, String labelName, String userId) throws ToDoException;

	/**
	 * This method is for adding a label to a particular note. the label added
	 * should also be created in the label repository.
	 * 
	 * @param noteId
	 * @param label
	 * @param userId
	 * @return
	 * @throws ToDoException
	 */
	String addLabeltoNote(String noteId, Label label, String userId) throws ToDoException;

	List<Note> displayFromTrash(String userId) throws ToDoException;
}
