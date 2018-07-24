/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a Note Controller.  
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.noteservice.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.bridgelabz.todoapplication.noteservice.model.Label;
import com.bridgelabz.todoapplication.noteservice.model.Note;
import com.bridgelabz.todoapplication.noteservice.model.NoteDTO;
import com.bridgelabz.todoapplication.noteservice.service.INoteService;
import com.bridgelabz.todoapplication.utilservice.ResponseDTO;
import com.bridgelabz.todoapplication.utilservice.ToDoException;
import com.bridgelabz.todoapplication.utilservice.ObjectMapper.ObjectMapping;
import com.google.common.base.Preconditions;

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
	@Autowired
	ObjectMapping objectMapping;
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
	 * @throws ToDoException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/createnote", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> createNote(@RequestBody NoteDTO noteDto, HttpServletRequest request) throws ToDoException  {
		logger.info(REQ_ID + "Creating a new note");
		String token=request.getHeader("Authorization");
		noteService.createNote(noteDto ,token);
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
	 * @throws ToDoException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/deletenote", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> deleteNote( HttpServletRequest request,
			@RequestBody String noteId) throws ToDoException {
		logger.info(REQ_ID + " Deleting a Note");
		String token=request.getHeader("Authorization");
		noteService.deleteNote(noteId, token);
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
	 * @throws ToDoException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updatenote", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> updateNote(@RequestBody NoteDTO noteDto,  HttpServletRequest request) throws ToDoException {
		Note note = objectMapping.map(noteDto, Note.class);
		logger.info(REQ_ID + " Updating a note");
		String token=request.getHeader("Authorization");
		noteService.updateNote(note.getNoteId(), note.getTitle(), note.getDescription(), token);
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
	 * @throws ToDoException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/displayAllNotes", method = RequestMethod.GET)
	public ResponseEntity displayAllNotes( HttpServletRequest request) throws ToDoException {
		logger.info(REQ_ID + " Displaying all notes");
		String token=request.getHeader("Authorization");
		List<Note> list = new ArrayList<>();
		list = noteService.displayAllNotes(token);
		logger.info(RESP_ID + " All notes are got to display by the controller");
		return new ResponseEntity(list, HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param colour
	 * @param token
	 * @return responseEntity ResponseEntity for showing the response on the swagger
	 *         or Postman
	 *         <p>
	 *         This method is for changing the colour of the Note.This method is
	 *         interacting with the service layer and calling changeColourOfNote()
	 *         of Note Service.
	 *         </p>
	 * @throws ToDoException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/changecolour", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> changeColour(@RequestParam("Note Id") String noteId,
			@RequestParam("colour") String colour,  HttpServletRequest request) throws ToDoException {
		logger.info(REQ_ID + " Changing the colour of Note");
		String token=request.getHeader("Authorization");
		noteService.changeColourOfNote(noteId, colour, token);
		logger.info(RESP_ID + " Colour Successfully Changed ");
		return new ResponseEntity("Colour successfully changed", HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param token
	 * @return responseEntity ResponseEntity for showing the response on the swagger
	 *         or Postman
	 *         <p>
	 *         This method is for deleting the Note permanently. It will not be
	 *         availavble in the trash as well.
	 *         </p>
	 * @throws ToDoException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/deletepermanently", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseDTO> deleteFromTrash(@RequestBody String noteId,
			 HttpServletRequest request) throws ToDoException {
		logger.info(REQ_ID + " Deleting a Note permanently");
		String token=request.getHeader("Authorization");
		noteService.deleteFromTrash(noteId, token);
		logger.info(RESP_ID + " Note Deleted Permanently ");
		return new ResponseEntity("Note deleted Permanently ", HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param token
	 * @return responseEntity ResponseEntity for showing the response on the swagger
	 *         or Postman
	 *         <p>
	 *         This method is for restoring a note from the trash. We are passing
	 *         the noteId and it will match the note Id and corresponding note will
	 *         be moved out of the trash. Therefore we r changing its status from
	 *         true to false.
	 *         </p>
	 * @throws ToDoException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/restorefromtrash", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> restoreFromTrash(@RequestBody String noteId,
			 HttpServletRequest request) throws ToDoException {
		logger.info(REQ_ID + " Restoring from trash");
		String token=request.getHeader("Authorization");
		noteService.restoreFromTrash(noteId, token);
		logger.info(RESP_ID + " Restored from Trashed");
		return new ResponseEntity("Restrored from trash", HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param token
	 * @return responseEntity ResponseEntity for showing the response on the swagger
	 *         or Postman
	 *         <p>
	 *         this method is for pinning the note. The notes pinned will move on
	 *         the top of the list.
	 *         </p>
	 * @throws ToDoException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/pinnote", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> pinNote(@RequestBody String noteId,
			 HttpServletRequest request) throws ToDoException {
		logger.info(REQ_ID + " Pinning a note");
		String token=request.getHeader("Authorization");
		noteService.pinNote(noteId, token);
		logger.info(RESP_ID + " Pinned the Note");
		return new ResponseEntity("Pinned the Note", HttpStatus.OK);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/archieve", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> archieveNote(@RequestBody String noteId,
			 HttpServletRequest request) throws ToDoException {
		logger.info(REQ_ID + " Archieving a note");
		String token=request.getHeader("Authorization");
		noteService.archieveNote(noteId, token);
		logger.info(RESP_ID + " Archived a note");
		return new ResponseEntity("Archieved the the Note", HttpStatus.OK);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/createlabel",method=RequestMethod.POST)
	public ResponseEntity<ResponseDTO> createLable(@RequestBody Label label,HttpServletRequest request) throws ToDoException{
		logger.info(REQ_ID + " Creating a Label");
		String token=request.getHeader("Authorization");
		noteService.createLabel(label,token);
		logger.info(RESP_ID + "Label Created successfully ");
		return new ResponseEntity(new ResponseDTO("Label Created successfully ",200),HttpStatus.OK);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/updatelabel",method=RequestMethod.POST )
	public ResponseEntity<ResponseDTO> updateLabel(@RequestParam String labelId,@RequestParam String labelName,HttpServletRequest request) throws ToDoException{
		logger.info(REQ_ID + " Updating a label");
		String token=request.getHeader("Authorization");
		noteService.updateLabel(labelId,labelName,token);
		logger.info(RESP_ID + "Label Updated successfully ");
		return new ResponseEntity(new ResponseDTO("Label Created successfully ",200),HttpStatus.OK);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/deletelabel",method=RequestMethod.DELETE)
	public ResponseEntity<ResponseDTO> deleteLabel(@RequestBody String labelId,HttpServletRequest request) throws ToDoException{
		logger.info(REQ_ID + " deleting a label");
		String token=request.getHeader("Authorization");
		noteService.deleteLabel(labelId,token);
		logger.info(RESP_ID + "Label Deleted successfully ");
		return new ResponseEntity(new ResponseDTO("Label deleted successfully ",200),HttpStatus.OK);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/readlabels",method=RequestMethod.GET)
	public ResponseEntity readLabels( HttpServletRequest request) throws ToDoException {
		logger.info(REQ_ID + " Displaying all labels");
		String token=request.getHeader("Authorization");
		List<Label> list = null;
		list = noteService.displayLabels(token);
		logger.info(RESP_ID + " All notes are got to display by the controller");
		return new ResponseEntity(list, HttpStatus.OK);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/setreminder",method=RequestMethod.POST)
	public ResponseEntity<ResponseDTO> setReminder(@RequestParam String noteId,@RequestParam String remindTime,HttpServletRequest request) throws ToDoException, ParseException{
		logger.info(REQ_ID + " Setting the Reminder");
		String token=request.getHeader("Authorization");
		noteService.setReminder(noteId,remindTime,token);
		logger.info(RESP_ID + "Label Deleted successfully ");
		return new ResponseEntity(new ResponseDTO("Reminder set successfully ",200),HttpStatus.OK);
	}
}
