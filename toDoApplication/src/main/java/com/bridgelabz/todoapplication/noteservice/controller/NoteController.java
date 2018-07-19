/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a Note Controller.  
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.noteservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.todoapplication.noteservice.model.Note;
import com.bridgelabz.todoapplication.noteservice.service.INoteService;
import com.bridgelabz.todoapplication.utilservice.ResponseDTO;
import com.bridgelabz.todoapplication.utilservice.ToDoException;

/**
 * @author Saurav
 *         <p>
 *         This class is a Note Controller having functionalities of creating a
 *         note, reading, updating as well as deleting a note
 *         </p>
 */
@RestController
public class NoteController {
	@Autowired
	INoteService noteService;
	public static final Logger logger = LoggerFactory.getLogger(NoteController.class);
	static String REQ_ID = "IN_Note";
	static String RESP_ID = "OUT_Note";

	/**
	 * @param note
	 * @param token
	 * @return ResponseEntity for showing the response on the swagger or Postman
	 *         <p>
	 *         This method is for creating a new Note. This method is interacting
	 *         with the service layer and calling createNote() of service layer
	 *         </p>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/createnote", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> createNote(@RequestBody Note note, @RequestParam("token") String token) {
		logger.info(REQ_ID + "Creating a new note");
		try {
			noteService.createNote(note, token);
		} catch (ToDoException e) {
			ResponseDTO response = new ResponseDTO();
			response.setMessage(e.getMessage());
			response.setStatus(-6);
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		logger.info(RESP_ID + " Note successfully created");
		return new ResponseEntity("Note successfully created", HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param noteId
	 * @return ResponseEntity ResponseEntity for showing the response on the swagger
	 *         or Postman
	 *         <p>
	 *         This method is for deleting a Note. This method is interacting with
	 *         the service layer and calling deleteNote() of service layer
	 *         </p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/deletenote", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> deleteNote(@RequestParam("token") String token,
			@RequestParam("noteId") String noteId) {
		logger.info(REQ_ID + " Deleting a Note");
		try {
			noteService.deleteNote(noteId, token);
		} catch (ToDoException e) {
			ResponseDTO response = new ResponseDTO();
			response.setMessage(e.getMessage());
			response.setStatus(-7);
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		logger.info(RESP_ID + " Note successfully Deleted");
		return new ResponseEntity("Note Successfully deleted", HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param title
	 * @param description
	 * @param token
	 * @return ResponseEntity ResponseEntity for showing the response on the swagger
	 *         or Postman
	 *         <p>
	 *         This method is for updating a Note. This method is interacting with
	 *         the service layer and calling updateNote() of service layer
	 *         </p>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updatenote", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> updateNote(@RequestParam("noteId") String noteId,
			@RequestParam("title") String title, @RequestParam("description") String description,
			@RequestParam("token") String token) {
		logger.info(REQ_ID + " Updating a note");
		try {
			noteService.updateNote(noteId, title, description, token);
		} catch (ToDoException e) {
			ResponseDTO response = new ResponseDTO();
			response.setMessage(e.getMessage());
			response.setStatus(-8);
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		logger.info(RESP_ID + " Note sucessfully updated");
		return new ResponseEntity("Note sucessfully updated", HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param token
	 * @return ResponseEntity ResponseEntity for showing the response on the swagger
	 *         or Postman
	 *         <p>
	 *         This method is for displaying the Notes. This method is interacting
	 *         with the service layer and calling displayAllNotes() of service layer
	 *         </p>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/displayAllNotes", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> displayAllNotes(@RequestParam("noteId") String noteId,
			@RequestParam("token") String token) {
		logger.info(REQ_ID + " Displaying all notes");
		List<Note> list;
		try {
			list = noteService.displayAllNotes(token);
		} catch (ToDoException e) {
			ResponseDTO response = new ResponseDTO();
			response.setMessage(e.getMessage());
			response.setStatus(-9);
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		logger.info(RESP_ID + " All notes are got to display by the controller");
		return new ResponseEntity("All notes are got to display by the controller " + list.toString(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/changecolour", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> changeColour(@RequestParam("Note Id") String noteId,
			@RequestParam("colour") String colour, @RequestParam("token") String token) {
		logger.info(REQ_ID + " Changing the colour of Note");
		try {
			noteService.changeColourOfNote(noteId, colour, token);
		} catch (ToDoException e) {
			ResponseDTO response = new ResponseDTO();
			response.setMessage(e.getMessage());
			response.setStatus(-10);
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		logger.info(RESP_ID + " Colour Successfully Changed ");
		return new ResponseEntity("Colour successfully changed", HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/deletepermanently", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseDTO> deleteFromTrash(@RequestParam("NoteId") String noteId,
			@RequestParam("token") String token) {
		logger.info(REQ_ID + " Deleting a Note permanently");
		try {
			noteService.deleteFromTrash(noteId, token);
		} catch (ToDoException e) {
			ResponseDTO response = new ResponseDTO();
			response.setMessage(e.getMessage());
			response.setStatus(-11);
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		logger.info(RESP_ID + " Note Deleted Permanently ");
		return new ResponseEntity("Colour successfully changed", HttpStatus.OK);
	}
}
