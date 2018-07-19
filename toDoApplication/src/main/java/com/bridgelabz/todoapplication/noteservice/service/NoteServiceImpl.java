/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating the implementation class of InoteService interface.
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.noteservice.service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.todoapplication.noteservice.model.Note;
import com.bridgelabz.todoapplication.noteservice.repository.INoteRepository;
import com.bridgelabz.todoapplication.userservice.model.User;
import com.bridgelabz.todoapplication.userservice.repository.Repository;
import com.bridgelabz.todoapplication.utilservice.ToDoException;
import com.bridgelabz.todoapplication.utilservice.TokenGenerator;

/**
 * @author Saurav
 * <p>
 * This class is the Note Service Implementation class of the interface of INoteService
 * </p>
 */
@Service
public class NoteServiceImpl implements INoteService {
	@Autowired
	INoteRepository noteRepository;
	@Autowired
	Repository userRepository;
	TokenGenerator token = new TokenGenerator();
	@Override
	/**
	 * Method to create a note in the application
	 */
	public void createNote(Note note,String token1) throws ToDoException {
		if (note.getNoteId().equals("") || note.getDescription().equals("")||token1.equals("")){
			throw new ToDoException("Null values entered");
		}
		String email=token.parseJWT(token1);
		if(!userRepository.getByEmail(email).isPresent()) {
			throw new ToDoException("Invalid user");
		}
		Optional<User> user=userRepository.getByEmail(email);
		note.setUserId(user.get().getId());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String createdDate = simpleDateFormat.format(new Date());
		note.setCreatedDate(createdDate);
		note.setLastUpdatedDate(createdDate);
		noteRepository.save(note);
	}
	/**
	 * Method to delete an note in the application
	 */
	@Override
	public void deleteNote(String noteId,String token1) throws ToDoException {
		if(noteId.equals("")||token1.equals("")) {
			throw new ToDoException("Null values entered");
		}
		if(!noteRepository.existsById(noteId)) {
			throw new ToDoException("The entered NoteId is note present");
		}
		String email=token.parseJWT(token1);
		if(!userRepository.getByEmail(email).isPresent()) {
			throw new ToDoException("Invalid user");
		}
		//noteRepository.deleteById(noteId);
		Note note=noteRepository.getByNoteId(noteId).get();
		note.setTrashStatus(true);
		noteRepository.save(note);
	}
	/**
	 * Method to update a Note in the database inside the application
	 */
	@Override
	public void updateNote(String noteId,String title, String description,String token1) throws ToDoException {
		if(noteId.equals("")||description.equals("")||token1.equals("")) {
			throw new ToDoException("Description is null");
		}
		if(!noteRepository.existsById(noteId)) {
			throw new ToDoException("The entered NoteId is note present");
		}
		String email=token.parseJWT(token1);
		if(!userRepository.getByEmail(email).isPresent()) {
			throw new ToDoException("Invalid user");
		}
		Note note=noteRepository.getByNoteId(noteId).get();
		if(!note.isTrashStatus()) {
		note.setTitle(title);
		note.setDescription(description);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		note.setLastUpdatedDate(simpleDateFormat.format(new Date()));
		noteRepository.save(note);
		}
	}
	/**
	 * Method to display all the Notes availaible in the application
	 */
	@Override
	public List<Note> displayAllNotes(String token1) throws ToDoException {
		if(token1.equals("")) {
			throw new ToDoException("Description is null");
		}
		String email=token.parseJWT(token1);
		if(!userRepository.getByEmail(email).isPresent()) {
			throw new ToDoException("Invalid user");
		}
		Optional<User> user=userRepository.getByEmail(email);
		List<Note> list= noteRepository.findNotesByUserId(user.get().getId());
		return list;
	}
	@Override
	public void changeColourOfNote(String noteId,String colour, String token1) throws ToDoException {
		if(noteId.equals("")||colour.equals("")||token1.equals("")) {
			throw new ToDoException("Description is null");
		}
		if(!noteRepository.existsById(noteId)) {
			throw new ToDoException("The entered NoteId is note present");
		}
		String email=token.parseJWT(token1);
		if(!userRepository.getByEmail(email).isPresent()) {
			throw new ToDoException("Invalid user");
		}
		Note note=noteRepository.getByNoteId(noteId).get();
		if(!note.isTrashStatus()) {
		note.setColour(colour);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		note.setLastUpdatedDate(simpleDateFormat.format(new Date()));
		noteRepository.save(note);
		}
	}
	@Override
	public void deleteFromTrash(String noteId, String token1) throws ToDoException {
		if(noteId.equals("")||token1.equals("")) {
			throw new ToDoException("Null values entered");
		}
		if(!noteRepository.existsById(noteId)) {
			throw new ToDoException("The entered NoteId is note present");
		}
		String email=token.parseJWT(token1);
		if(!userRepository.getByEmail(email).isPresent()) {
			throw new ToDoException("Invalid user");
		}
		noteRepository.deleteById(noteId);
	}
}
