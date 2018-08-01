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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.todoapplication.noteservice.model.Label;
import com.bridgelabz.todoapplication.noteservice.model.Note;
import com.bridgelabz.todoapplication.noteservice.model.NoteDTO;
import com.bridgelabz.todoapplication.noteservice.repository.ILabelElasticRepository;
import com.bridgelabz.todoapplication.noteservice.repository.ILabelRepository;
import com.bridgelabz.todoapplication.noteservice.repository.INoteElasticRepository;
import com.bridgelabz.todoapplication.noteservice.repository.INoteRepository;
import com.bridgelabz.todoapplication.userservice.model.User;
import com.bridgelabz.todoapplication.userservice.repository.Repository;
import com.bridgelabz.todoapplication.utilservice.ToDoException;
import com.bridgelabz.todoapplication.utilservice.TokenGenerator;
import com.bridgelabz.todoapplication.utilservice.ObjectMapper.ObjectMapping;
import com.bridgelabz.todoapplication.utilservice.Precondition.PreCondition;
import com.bridgelabz.todoapplication.utilservice.RedisRepository.IRedisRepository;
import com.bridgelabz.todoapplication.utilservice.messageaccessor.Messages;
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
	@Autowired
	Messages messages;
	@Autowired
	INoteElasticRepository noteElasticRepository;
	@Autowired
	ILabelElasticRepository labelElasticRepository;
	public static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
	static String REQ_ID = "IN_Note_SERVICE";
	static String RESP_ID = "OUT_Note_SERVICE";

	@Override
	/**
	 * Method to create a note in the application
	 */
	public String createNote(NoteDTO noteDto, String userId) throws ToDoException {
		logger.info(REQ_ID + " Creating Note in Service");
		PreCondition.checkNotNull(noteDto.getDescription(), messages.get("101"));
		PreCondition.checkNotNull(noteDto.getTitle(), messages.get("102"));
		Note note = objectMapping.map(noteDto, Note.class);
		Optional<User> user = userRepository.findById(userId);
		note.setUserId(user.get().getId());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String createdDate = simpleDateFormat.format(new Date());
		note.setCreatedDate(createdDate);
		note.setLastUpdatedDate(createdDate);
		noteRepository.save(note);
		noteElasticRepository.save(note);
		logger.info(RESP_ID + " Note created in Service");
		for (int i = 0; i < note.getLabels().size(); i++) {
			if (!note.getLabels().get(i).getLabelName().equals("")) {
				logger.info("inside the loop");
				Label label = new Label();
				label.setLabelName(note.getLabels().get(i).getLabelName());
				label.setNoteId(note.getNoteId());
				label.setUserId(user.get().getId());
				labelRepository.save(label);
				labelElasticRepository.save(label);
			}
		}
		return note.getNoteId();
	}

	/**
	 * Method to delete an note in the application
	 */
	@Override
	public String deleteNote(String noteId, String userId) throws ToDoException {
		logger.info(REQ_ID + " Deleting Note in Service");
		PreCondition.checkNotNull(noteId, messages.get("103"));
		PreCondition.checkNotNull(userId, messages.get("104"));
		PreCondition.checkNotEmptyString(noteId, messages.get("105"));
		PreCondition.checkNotEmptyString(userId, messages.get("106"));
		if (!userRepository.findById(userId).isPresent()) {
			logger.error("Invalid user");
			PreCondition.commonMethod(messages.get("107"));
		}
		if (!noteElasticRepository.existsById(noteId)) {
			logger.error("Note not present with the corresponding Id");
			PreCondition.commonMethod(messages.get("108"));
		}
		Note note = noteRepository.getByNoteId(noteId).get();
		note.setTrashStatus(true);
		noteRepository.save(note);
		noteElasticRepository.save(note);
		logger.info(RESP_ID + " Note created in Service");
		return note.getNoteId();
	}

	/**
	 * Method to update a Note in the database inside the application
	 */
	@Override
	public String updateNote(String noteId, String title, String description, String userId) throws ToDoException {
		logger.info(REQ_ID + " Updating Note in Service");
		PreCondition.checkNotNull(noteId, messages.get("103"));
		PreCondition.checkNotNull(title, messages.get("102"));
		PreCondition.checkNotNull(description, messages.get("101"));
		PreCondition.checkNotEmptyString(noteId, messages.get("105"));
		PreCondition.checkNotEmptyString(userId, messages.get("106"));
		if (!noteElasticRepository.existsById(noteId)) {
			logger.error("Note not present with the corresponding Id");
			PreCondition.commonMethod(messages.get("108"));
		}
		if (!userRepository.findById(userId).isPresent()) {
			logger.error("Invalid user");
			PreCondition.commonMethod(messages.get("107"));
		}
		Note note = noteElasticRepository.getByNoteId(noteId).get();
		if (!note.isTrashStatus()) {
			note.setTitle(title);
			note.setDescription(note.getDescription());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			note.setLastUpdatedDate(simpleDateFormat.format(new Date()));
			noteRepository.save(note);
			noteElasticRepository.save(note);
			logger.info(RESP_ID + " Note Updated in service");
		}
		return noteId;
	}

	/**
	 * Method to display all the Notes available in the application
	 */
	@Override
	public List<Note> displayAllNotes(String userId) throws ToDoException {
		logger.info(REQ_ID + " Displaying Notes in Service");
		List<Note> list = new ArrayList<>();
		PreCondition.checkNotEmptyString(userId, messages.get("106"));
		list = noteElasticRepository.findNotesByUserId(userId);
		if (!userRepository.findById(userId).isPresent()) {
			logger.error("Invalid user");
			PreCondition.commonMethod(messages.get("107"));
		}
		return list;
	}

	/**
	 * Method for changing the color of the Note
	 */
	@Override
	public String changeColourOfNote(String noteId, String colour, String userId) throws ToDoException {
		logger.info(REQ_ID + " Changing the colour of Notes");
		PreCondition.checkNotNull(noteId, messages.get("103"));
		PreCondition.checkNotNull(userId, messages.get("104"));
		PreCondition.checkNotNull(colour, messages.get("109"));
		PreCondition.checkNotEmptyString(noteId, messages.get("105"));
		PreCondition.checkNotEmptyString(userId, messages.get("106"));
		PreCondition.checkNotEmptyString(colour, messages.get("110"));
		if (!userRepository.findById(userId).isPresent()) {
			logger.error("Invalid user");
			PreCondition.commonMethod(messages.get("107"));
		}
		if (!noteElasticRepository.existsById(noteId)) {
			logger.error("Note not present with corresponding id");
			PreCondition.commonMethod(messages.get("108"));
		}
		Note note = noteElasticRepository.getByNoteId(noteId).get();
		if (!note.isTrashStatus()) {
			note.setColour(colour);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			note.setLastUpdatedDate(simpleDateFormat.format(new Date()));
			noteRepository.save(note);
			noteElasticRepository.save(note);
			logger.info(RESP_ID + "Colour changed");
			return note.getNoteId();
		}
		return noteId;
	}

	/**
	 * Method for deleting the Note from the trash
	 */
	@Override
	public String deleteFromTrash(String noteId, String userId) throws ToDoException {
		logger.info(REQ_ID + " Deleting from the trash");
		PreCondition.checkNotNull(noteId, messages.get("103"));
		PreCondition.checkNotNull(userId, messages.get("104"));
		PreCondition.checkNotEmptyString(noteId, messages.get("105"));
		PreCondition.checkNotEmptyString(userId, messages.get("106"));
		if (!noteElasticRepository.existsById(noteId)) {
			logger.error("Note not present with the corresponding Id");
			PreCondition.commonMethod(messages.get("108"));
		}
		if (!userRepository.findById(userId).isPresent()) {
			logger.error("Invalid user");
			PreCondition.commonMethod(messages.get("107"));
		}
		noteElasticRepository.deleteById(noteId);
		logger.info(RESP_ID + " Deleted from the trash");
		return noteId;
	}

	/**
	 * Method for restoring the Note from the Trash
	 */
	@Override
	public String restoreFromTrash(String noteId, String userId) throws ToDoException {
		logger.info(REQ_ID + " Restoring from trash in Service");
		PreCondition.checkNotNull(noteId, messages.get("103"));
		PreCondition.checkNotNull(userId, messages.get("104"));
		PreCondition.checkNotEmptyString(noteId, messages.get("105"));
		PreCondition.checkNotEmptyString(userId, messages.get("106"));
		if (!noteElasticRepository.existsById(noteId)) {
			logger.error("Note not present with the corresponding Id");
			PreCondition.commonMethod(messages.get("108"));
		}
		if (!userRepository.findById(userId).isPresent()) {
			logger.error("Invalid user");
			PreCondition.commonMethod(messages.get("107"));
		}
		Note note = noteElasticRepository.getByNoteId(noteId).get();
		if (note.isTrashStatus()) {
			note.setTrashStatus(false);
			noteRepository.save(note);
			noteElasticRepository.save(note);
			logger.info(RESP_ID + " Restored from trash in Service");
		}
		return noteId;
	}

	/**
	 * Method for Pinning the Note
	 */
	@Override
	public String pinNote(String noteId, String userId) throws ToDoException {
		logger.info(REQ_ID + " Pinning the Note in Service");
		PreCondition.checkNotNull(noteId, messages.get("103"));
		PreCondition.checkNotNull(userId, messages.get("104"));
		PreCondition.checkNotEmptyString(noteId, messages.get("105"));
		PreCondition.checkNotEmptyString(userId, messages.get("106"));
		if (!noteElasticRepository.existsById(noteId)) {
			logger.error("Note not present with the corresponding Id");
			PreCondition.commonMethod(messages.get("108"));
		}
		if (!userRepository.findById(userId).isPresent()) {
			logger.error("Invalid user");
			PreCondition.commonMethod(messages.get("107"));
		}
		Note note = noteElasticRepository.getByNoteId(noteId).get();
		if (!note.isTrashStatus() && !note.isArchieveStatus()) {
			note.setPinnedStatus(true);
			noteRepository.save(note);
			noteElasticRepository.save(note);
			logger.info(RESP_ID + " the Note is pinned in Service");
		}
		return noteId;
	}

	/**
	 * Method for archiving the note. The archived note will come at the end of the
	 * list of notes
	 */
	@Override
	public String archieveNote(String noteId, String userId) throws ToDoException {
		logger.info(REQ_ID + " Archieving the note in Service");
		PreCondition.checkNotNull(noteId, messages.get("103"));
		PreCondition.checkNotNull(userId, messages.get("104"));
		PreCondition.checkNotEmptyString(noteId, messages.get("105"));
		PreCondition.checkNotEmptyString(userId, messages.get("106"));
		if (!noteElasticRepository.existsById(noteId)) {
			logger.error("Note not present with the corresponding Id");
			PreCondition.commonMethod(messages.get("108"));
		}
		if (!userRepository.findById(userId).isPresent()) {
			logger.error("Invalid user");
			PreCondition.commonMethod(messages.get("107"));
		}
		Note note = noteElasticRepository.getByNoteId(noteId).get();
		if (!note.isTrashStatus() && !note.isPinnedStatus()) {
			note.setArchieveStatus(true);
			noteRepository.save(note);
			noteElasticRepository.save(note);
			logger.info(RESP_ID + " The Note is Archieved");
		}
		return noteId;
	}

	/**
	 * This method is for creating the Label
	 */
	@Override
	public String createLabel(Label label, String userId) throws ToDoException {
		logger.info(REQ_ID + " creating the label in Service");
		PreCondition.checkNotNull(label.getLabelName(), messages.get("111"));
		PreCondition.checkNotNull(userId, messages.get("104"));
		PreCondition.checkNotEmptyString(label.getLabelName(), messages.get("112"));
		PreCondition.checkNotEmptyString(userId, messages.get("106"));
		if (!userRepository.findById(userId).isPresent()) {
			logger.error("Invalid user");
			PreCondition.commonMethod(messages.get("107"));
		}
		List<Label> list = labelElasticRepository.findLabelsByUserId(userId);
		for (Label l : list) {
			if (l.getLabelName().equals(label.getLabelName())) {
				PreCondition.commonMethod(messages.get("113"));
			}
		}
		label.setUserId(userId);
		labelRepository.save(label);
		labelElasticRepository.save(label);
		logger.info(RESP_ID + " the Label is created in Service");
		return label.getLableId();
	}

	/**
	 * The method is for updating the label
	 */
	@Override
	public String updateLabel(String labelId, String labelName, String userId) throws ToDoException {
		logger.info(REQ_ID + " The label is updating in Service");
		PreCondition.checkNotNull(labelId, messages.get("114"));
		PreCondition.checkNotEmptyString(labelId, messages.get("115"));
		PreCondition.checkNotNull(labelName, messages.get("111"));
		PreCondition.checkNotEmptyString(labelName, messages.get("112"));
		PreCondition.checkNotNull(userId, messages.get("104"));
		PreCondition.checkNotEmptyString(userId, messages.get("106"));
		if (!userRepository.findById(userId).isPresent()) {
			logger.error("Invalid user");
			PreCondition.commonMethod(messages.get("107"));
		}
		Label label = labelElasticRepository.findById(labelId).get();
		label.setLabelName(labelName);
		labelRepository.save(label);
		labelElasticRepository.save(label);
		logger.info(RESP_ID + "The label is updated in Service");
		return labelId;
	}

	/**
	 * The method is for deleting the Label
	 */
	@Override
	public String deleteLabel(String labelName, String userId) throws ToDoException {
		logger.info(REQ_ID + " Deleting the Label in Service");
		PreCondition.checkNotNull(labelName, messages.get("114"));
		PreCondition.checkNotEmptyString(labelName, messages.get("115"));
		PreCondition.checkNotNull(userId, messages.get("104"));
		PreCondition.checkNotEmptyString(userId, messages.get("106"));
		if (!userRepository.findById(userId).isPresent()) {
			logger.error("Invalid user");
			PreCondition.commonMethod(messages.get("107"));
		}
		for (int i = 0; i < labelRepository.findByLabelName(labelName).size(); i++) {
			labelElasticRepository.deleteById(labelRepository.findByLabelName(labelName).get(i).getLableId());
			logger.info(RESP_ID + "Deletd the Label in Service");
		}
		List<Note> notes = noteRepository.findAll();
		for (int i = 0; i < notes.size(); i++) {
			List<Label> labels = notes.get(i).getLabels();
			for (Label label : labels) {
				if (label.getLabelName().equals(labelName)) {
					labels.remove(label);
					notes.get(i).setLabels(labels);
					noteRepository.save(notes.get(i));
					noteElasticRepository.save(notes.get(i));
					logger.info(RESP_ID + "Deleted from the notes also");
				}
			}
		}
		return labelName;
	}

	/**
	 * The method is for displaying the labels
	 */
	@Override
	public List<Label> displayLabels(String userId) throws ToDoException {
		logger.info(REQ_ID + " Displaying the Labels in Service");
		List<Label> list = new ArrayList<>();
		PreCondition.checkNotNull(userId, messages.get("104"));
		PreCondition.checkNotEmptyString(userId, messages.get("106"));
		if (!userRepository.findById(userId).isPresent()) {
			logger.error("Invalid user");
			PreCondition.commonMethod(messages.get("107"));
		}
		list = labelElasticRepository.findLabelsByUserId(userId);
		logger.info(RESP_ID + " The labels are Displayed in Service");
		return list;
	}

	/**
	 * The method is for setting the reminders
	 */
	@Override
	public String setReminder(String noteId, String remindTime, String userId) throws ToDoException, ParseException {
		logger.info(REQ_ID + "Setting the Reminder in Service");
		PreCondition.checkNotNull(noteId, messages.get("103"));
		PreCondition.checkNotEmptyString(noteId, messages.get("105"));
		PreCondition.checkNotNull(remindTime, messages.get("116"));
		PreCondition.checkNotEmptyString(remindTime, messages.get("117"));
		if (!noteElasticRepository.existsById(noteId)) {
			logger.error("Note not present with the corresponding Id");
			PreCondition.commonMethod(messages.get("108"));
		}
		if (!userRepository.findById(userId).isPresent()) {
			logger.error("Invalid user");
			PreCondition.commonMethod(messages.get("107"));
		}
		Optional<User> user = userRepository.findById(userId);
		Note note = noteElasticRepository.getByNoteId(noteId).get();
		note.setReminder(remindTime);
		Date reminder = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(remindTime);
		long timeDifference = reminder.getTime() - new Date().getTime();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				String to = user.get().getEmail();
				String subject = "Reminder about your Note";
				String body = "Note Title:" + note.getTitle() + "\nNote Discription:" + note.getDescription();
				producer.produceMsg(to, subject, body);
			}
		}, timeDifference);
		noteRepository.save(note);
		noteElasticRepository.save(note);
		logger.error(RESP_ID + " The reminder is set in the Service");
		return note.getNoteId();
	}

	/**
	 * The method is for search notes by Label
	 */
	@Override
	public List<Note> searchNotesByLabel(String labelName, String userId) throws ToDoException {
		logger.info(REQ_ID + " The Notes are searching by the Label Name entered");
		PreCondition.checkNotNull(labelName, messages.get("111"));
		PreCondition.checkNotEmptyString(labelName, messages.get("112"));
		PreCondition.checkNotNull(userId, messages.get("104"));
		PreCondition.checkNotEmptyString(userId, messages.get("106"));
		List<Note> listOfNotes = new ArrayList<>();
		List<Label> labelList = labelElasticRepository.findByLabelName(labelName);
		if (labelList.size() == 0) {
			logger.error("No such label present");
			PreCondition.commonMethod(messages.get("118"));
		}
		for (Label label : labelList) {
			listOfNotes.add(noteElasticRepository.findById(label.getNoteId()).get());
		}
		logger.info(RESP_ID + " The Notes are searched by the Label");
		return listOfNotes;
	}

	/**
	 * The method is for deleting the Label from The note
	 */
	@Override
	public String deleteLabelFromNote(String noteId, String labelName, String userId) throws ToDoException {
		logger.info(REQ_ID + " Deleting Label From the Note");
		PreCondition.checkNotNull(labelName, messages.get(messages.get("111")));
		PreCondition.checkNotEmptyString(labelName, messages.get("112"));
		PreCondition.checkNotNull(userId, messages.get("104"));
		PreCondition.checkNotEmptyString(userId, messages.get("106"));
		if (!userRepository.findById(userId).isPresent()) {
			logger.error("Invalid user");
			PreCondition.commonMethod(messages.get("107"));
		}
		Note note = noteElasticRepository.getByNoteId(noteId).get();
		List<Label> labels = note.getLabels();
		for (Label label : labels) {
			if (label.getLabelName().equals(labelName)) {
				labels.remove(label);
				note.setLabels(labels);
				noteRepository.save(note);
				noteElasticRepository.save(note);
				logger.info(RESP_ID + " The Label is delted from the note");
			}
		}
		return noteId;
	}

	/**
	 * This method is for adding a label to a particular note. the label added
	 * should also be created in the label repository.
	 */
	@Override
	public String addLabeltoNote(String noteId, Label label, String userId) throws ToDoException {
		PreCondition.checkNotNull(noteId, messages.get("103"));
		PreCondition.checkNotEmptyString(noteId, messages.get("105"));
		if (!noteElasticRepository.existsById(noteId)) {
			logger.error("Note not present with the corresponding Id");
			PreCondition.commonMethod(messages.get("108"));
		}
		Note note = noteElasticRepository.getByNoteId(noteId).get();
		List<Label> listOfLabels = note.getLabels();
		listOfLabels.add(label);
		note.setLabels(listOfLabels);
		noteRepository.save(note);
		noteElasticRepository.save(note);
		label.setNoteId(noteId);
		label.setUserId(userId);
		labelRepository.save(label);
		labelElasticRepository.save(label);
		return noteId;
	}
}
