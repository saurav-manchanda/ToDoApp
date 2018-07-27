/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating the implementation class of InoteService interface.
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.noteservice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.todoapplication.noteservice.model.Label;
import com.bridgelabz.todoapplication.noteservice.model.Note;
import com.bridgelabz.todoapplication.noteservice.model.NoteDTO;
import com.bridgelabz.todoapplication.noteservice.repository.ILabelRepository;
import com.bridgelabz.todoapplication.noteservice.repository.INoteRepository;
import com.bridgelabz.todoapplication.userservice.model.User;
import com.bridgelabz.todoapplication.userservice.repository.Repository;
import com.bridgelabz.todoapplication.utilservice.ToDoException;
import com.bridgelabz.todoapplication.utilservice.TokenGenerator;
import com.bridgelabz.todoapplication.utilservice.ObjectMapper.ObjectMapping;
import com.bridgelabz.todoapplication.utilservice.Precondition.PreCondition;
import com.bridgelabz.todoapplication.utilservice.RedisRepository.IRedisRepository;
import com.bridgelabz.todoapplication.utilservice.rabbitmq.IProducer;

/**
 * @author Saurav
 *         <p>
 *         This class is the Note Service Implementation class of the interface
 *         of INoteService
 *         </p>
 */
@Service
public class NoteServiceImpl implements INoteService {
	@Autowired
	INoteRepository noteRepository;
	@Autowired
	Repository userRepository;
	@Autowired
	ILabelRepository labelRepository;
	@Autowired
	TokenGenerator token;
	@Autowired
	ObjectMapping objectMapping;
	@Autowired
	IProducer producer;
	@Autowired
	IRedisRepository redisRepository;

	@Override
	/**
	 * Method to create a note in the application
	 */
	public void createNote(NoteDTO noteDto, String token1) throws ToDoException {
		PreCondition.checkNotNull(noteDto.getDescription(), "Descricption cannot be null");
		PreCondition.checkNotNull(noteDto.getTitle(), "Title cannot be Null");
		Note note = objectMapping.map(noteDto, Note.class);
		String email = token.parseJWT(token1);
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		if(!email.equals(redisRepository.getToken(email))) {
			PreCondition.commonMethod("Email Match not Found");
		}
		Optional<User> user = userRepository.getByEmail(email);
		note.setUserId(user.get().getId());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String createdDate = simpleDateFormat.format(new Date());
		note.setCreatedDate(createdDate);
		note.setLastUpdatedDate(createdDate);
		noteRepository.save(note);
		for (int i = 0; i < note.getLabels().size(); i++) {
			if (!note.getLabels().get(i).getLabelName().equals("")) {
				Label label = new Label();
				label.setLabelName(note.getLabels().get(i).getLabelName());
				label.setNoteId(note.getNoteId());
				label.setUserId(user.get().getId());
				labelRepository.save(label);
			}
		}
	}

	/**
	 * Method to delete an note in the application
	 */
	@Override
	public void deleteNote(String noteId, String token1) throws ToDoException {
		PreCondition.checkNotNull(noteId, "Note id cant be null");
		PreCondition.checkNotNull(token1, "Token cants be null");
		PreCondition.checkNotEmptyString(noteId, "NoteId cannot be an empty string");
		PreCondition.checkNotEmptyString(token1, "token cannot be an empty string");
		if (!noteRepository.existsById(noteId)) {
			PreCondition.commonMethod("Note not present with the corresponding Id");
		}
		String email = token.parseJWT(token1);
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		Note note = noteRepository.getByNoteId(noteId).get();
		note.setTrashStatus(true);
		noteRepository.save(note);
	}

	/**
	 * Method to update a Note in the database inside the application
	 */
	@Override
	public void updateNote(String noteId, String title, String description, String token1) throws ToDoException {
		PreCondition.checkNotNull(noteId, "NoteId cannot be null");
		PreCondition.checkNotNull(title, "Title cannot be null");
		PreCondition.checkNotNull(description, "Description cannot be null");
		PreCondition.checkNotEmptyString(noteId, "Noteid cannot be an empty String");
		PreCondition.checkNotEmptyString(token1, "Token cannot be empty");
		if (!noteRepository.existsById(noteId)) {
			PreCondition.commonMethod("Note not present with the corresponding Id");

		}
		String email = token.parseJWT(token1);
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		Note note = noteRepository.getByNoteId(noteId).get();
		if (!note.isTrashStatus()) {
			note.setTitle(title);
			note.setDescription(note.getDescription());
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
		List<Note> list = new ArrayList<>();
		List<Note> modifiedList = new ArrayList<>();
		PreCondition.checkNotEmptyString(token1, "Token cannot be empty");
		String email = token.parseJWT(token1);
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		Optional<User> user = userRepository.getByEmail(email);
		list = noteRepository.findNotesByUserId(user.get().getId());
		for (Note n : list) {
			if (n.isPinnedStatus() && !n.isTrashStatus() && !n.isArchieveStatus()) {
				modifiedList.add(n);
			}
		}
		for (Note n : list) {
			if (!n.isArchieveStatus() && !n.isTrashStatus() && !n.isPinnedStatus()) {
				modifiedList.add(n);
			}
		}
		for (Note n : list) {
			if (n.isArchieveStatus() && !n.isTrashStatus()) {
				modifiedList.add(n);
			}
		}
		return modifiedList;
	}

	/**
	 * Method for changing the color of the Note
	 */
	@Override
	public void changeColourOfNote(String noteId, String colour, String token1) throws ToDoException {
		PreCondition.checkNotNull(noteId, "Note id cant be null");
		PreCondition.checkNotNull(token1, "Token cants be null");
		PreCondition.checkNotNull(colour, "Color cant be null");
		PreCondition.checkNotEmptyString(noteId, "NoteId cannot be an empty string");
		PreCondition.checkNotEmptyString(token1, "token cannot be an empty string");
		PreCondition.checkNotEmptyString(colour, "Colour cannot be empty string");
		if (!noteRepository.existsById(noteId)) {
			PreCondition.commonMethod("Note not present with the corresponding Id");
		}
		String email = token.parseJWT(token1);
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		Note note = noteRepository.getByNoteId(noteId).get();
		if (!note.isTrashStatus()) {
			note.setColour(colour);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			note.setLastUpdatedDate(simpleDateFormat.format(new Date()));
			noteRepository.save(note);
		}
	}

	/**
	 * Method for deleting the Note from the trash
	 */
	@Override
	public void deleteFromTrash(String noteId, String token1) throws ToDoException {
		PreCondition.checkNotNull(noteId, "Note id cant be null");
		PreCondition.checkNotNull(token1, "Token cants be null");
		PreCondition.checkNotEmptyString(noteId, "NoteId cannot be an empty string");
		PreCondition.checkNotEmptyString(token1, "token cannot be an empty string");
		if (!noteRepository.existsById(noteId)) {
			PreCondition.commonMethod("Note not present with the corresponding Id");
		}
		String email = token.parseJWT(token1);
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		noteRepository.deleteById(noteId);
	}

	/**
	 * Method for restoring the Note from the Trash
	 */
	@Override
	public void restoreFromTrash(String noteId, String token1) throws ToDoException {
		PreCondition.checkNotNull(noteId, "Note id cant be null");
		PreCondition.checkNotNull(token1, "Token cants be null");
		PreCondition.checkNotEmptyString(noteId, "NoteId cannot be an empty string");
		PreCondition.checkNotEmptyString(token1, "token cannot be an empty string");
		if (!noteRepository.existsById(noteId)) {
			PreCondition.commonMethod("Note not present with the corresponding Id");
		}
		String email = token.parseJWT(token1);
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		Note note = noteRepository.getByNoteId(noteId).get();
		if (note.isTrashStatus()) {
			note.setTrashStatus(false);
			noteRepository.save(note);
		}

	}

	/**
	 * Method for Pinning the Note
	 */
	@Override
	public void pinNote(String noteId, String token1) throws ToDoException {
		PreCondition.checkNotNull(noteId, "Note id cant be null");
		PreCondition.checkNotNull(token1, "Token cants be null");
		PreCondition.checkNotEmptyString(noteId, "NoteId cannot be an empty string");
		PreCondition.checkNotEmptyString(token1, "token cannot be an empty string");
		if (!noteRepository.existsById(noteId)) {
			PreCondition.commonMethod("Note not present with the corresponding Id");
		}
		String email = token.parseJWT(token1);
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		Note note = noteRepository.getByNoteId(noteId).get();
		if (!note.isTrashStatus() && !note.isArchieveStatus()) {
			note.setPinnedStatus(true);
			noteRepository.save(note);
		}
	}
/**
 * Method for archiving the note. The archived note will come at the end of the list of notes 
 */
	@Override
	public void archieveNote(String noteId, String token1) throws ToDoException {
		PreCondition.checkNotNull(noteId, "Note id cant be null");
		PreCondition.checkNotNull(token1, "Token cants be null");
		PreCondition.checkNotEmptyString(noteId, "NoteId cannot be an empty string");
		PreCondition.checkNotEmptyString(token1, "token cannot be an empty string");
		if (!noteRepository.existsById(noteId)) {
			PreCondition.commonMethod("Note not present with the corresponding Id");
		}
		String email = token.parseJWT(token1);
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		Note note = noteRepository.getByNoteId(noteId).get();
		if (!note.isTrashStatus() && !note.isPinnedStatus()) {
			note.setArchieveStatus(true);
			noteRepository.save(note);
		}
	}
/**
 * This method is for creating the Label
 */
	@Override
	public void createLabel(Label label, String token1) throws ToDoException {
		PreCondition.checkNotNull(label.getLabelName(), "Label name cannot be null");
		PreCondition.checkNotNull(token1, "Token cannot be Null");
		PreCondition.checkNotEmptyString(label.getLabelName(), "Label name cannot be an empty string");
		PreCondition.checkNotNull(token1, "Label Name cannot be empty");

		String email = token.parseJWT(token1);
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		Optional<User> user = userRepository.getByEmail(email);
		List<Label> list = labelRepository.findLabelsByUserId(user.get().getId());
		for (Label l : list) {
			if (l.getLabelName().equals(label.getLabelName())) {
				PreCondition.commonMethod("Label already present");
			}
		}
		label.setUserId(user.get().getId());
		labelRepository.save(label);
	}
/**
 * The method is for updating the label
 */
	@Override
	public void updateLabel(String labelId, String labelName, String token1) throws ToDoException {
		PreCondition.checkNotNull(labelId, "Label id cannot be null");
		PreCondition.checkNotEmptyString(labelId, "Label id cannot be an empty string");
		PreCondition.checkNotNull(labelName, "Label Name cannot be Null");
		PreCondition.checkNotEmptyString(labelName, "Label Name cannot be an empty string");
		PreCondition.checkNotNull(token1, "Token cannot be null");
		PreCondition.checkNotEmptyString(token1, "Token cannot be an empty string");
		String email = token.parseJWT(token1);
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		Label label = labelRepository.findById(labelId).get();
		label.setLabelName(labelName);
		labelRepository.save(label);
	}
/**
 * The method is for deleting the Label 
 */
	@Override
	public void deleteLabel(String labelName, String token1) throws ToDoException {
		PreCondition.checkNotNull(labelName, "Label Id cannot be Null");
		PreCondition.checkNotEmptyString(labelName, "Label Id cannot be an empty string");
		PreCondition.checkNotNull(token1, "Token cannot be Null");
		PreCondition.checkNotEmptyString(token1, "token cannot be an Empty String");
		String email = token.parseJWT(token1);
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		for(int i=0;i<labelRepository.findByLabelName(labelName).size();i++) {
		labelRepository.deleteById(labelRepository.findByLabelName(labelName).get(i).getLableId());
		}
		List<Note> notes = noteRepository.findAll();
		for (int i = 0; i < notes.size(); i++) {
			List<Label> labels = notes.get(i).getLabels();
			for (Label label : labels) {
				if (label.getLabelName().equals(labelName)) {
					labels.remove(label);
					notes.get(i).setLabels(labels);
					noteRepository.save(notes.get(i));
				}
			}
		}
	}
/**
 * The method is for displaying the labels
 */
	@Override
	public List<Label> displayLabels(String token1) throws ToDoException {
		List<Label> list = new ArrayList<>();
		PreCondition.checkNotNull(token1, "Token cannot be null");
		PreCondition.checkNotEmptyString(token1, "Token cannot be empty string");
		String email = token.parseJWT(token1);
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		Optional<User> user = userRepository.getByEmail(email);
		list = labelRepository.findLabelsByUserId(user.get().getId());
		return list;
	}
/**
 * The method is for setting the reminders
 */
	@Override
	public void setReminder(String noteId, String remindTime, String token1) throws ToDoException, ParseException {
		PreCondition.checkNotNull(noteId, "Note id cant be null");
		PreCondition.checkNotEmptyString(noteId, "NoteId cannot be an empty string");
		PreCondition.checkNotNull(remindTime, "Remindtime cannot be Null");
		PreCondition.checkNotEmptyString(remindTime, "RemindTime cannot be an empty String");
		String email = token.parseJWT(token1);
		if (!noteRepository.existsById(noteId)) {
			PreCondition.commonMethod("Note not present with the corresponding Id");
		}
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		Note note = noteRepository.getByNoteId(noteId).get();
		note.setReminder(remindTime);
		Date reminder = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(remindTime);
		long timeDifference = reminder.getTime() - new Date().getTime();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				String to = email;
				String subject = "Reminder about your Note";
				String body = "Note Title:" + note.getTitle() + "\nNote Discription:" + note.getDescription();
				producer.produceMsg(to, subject, body);
			}
		}, timeDifference);
		noteRepository.save(note);
	}
/**
 * The method is for search notes by Label
 */
	@Override
	public List<Note> searchNotesByLabel(String labelName, String token1) throws ToDoException {
		PreCondition.checkNotNull(labelName, "Label Name cannot be Null");
		PreCondition.checkNotEmptyString(labelName, "Label Name cannot be an empty string");
		PreCondition.checkNotNull(token1, "Token cannot be Null");
		PreCondition.checkNotEmptyString(token1, "token cannot be an Empty String");
		List<Note> listOfNotes = new ArrayList<>();
		List<Label> labelList = labelRepository.findByLabelName(labelName);
		for (Label label : labelList) {
			listOfNotes.add(noteRepository.findById(label.getNoteId()).get());
		}
		return listOfNotes;
	}
/**
 * The method is for deleting the Label from The note
 */
	@Override
	public void deleteLabelFromNote(String noteId,String labelName, String token1) throws ToDoException {
		PreCondition.checkNotNull(labelName, "Label Id cannot be Null");
		PreCondition.checkNotEmptyString(labelName, "Label Id cannot be an empty string");
		PreCondition.checkNotNull(token1, "Token cannot be Null");
		PreCondition.checkNotEmptyString(token1, "token cannot be an Empty String");
		String email = token.parseJWT(token1);
		if (!userRepository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("Invalid User");
		}
		Note note = noteRepository.getByNoteId(noteId).get();
		List<Label> labels = note.getLabels();
		for (Label label : labels) {
			if (label.getLabelName().equals(labelName)) {
				labels.remove(label);
				note.setLabels(labels);
				noteRepository.save(note);
			}
		}	
	}
}
