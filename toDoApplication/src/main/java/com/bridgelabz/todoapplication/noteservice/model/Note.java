/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a POJO class of Note having fields related to a Note and corresponding setters and getters  
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.noteservice.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author Saurav
 *         <p>
 *         This is a POJO class of Note having fields related to a Note and
 *         corresponding setters and getters
 *         </p>
 */
@Document(indexName="test",type="note")
public class Note {
	@Id
	private String noteId;
	private String title;
	private String description;
	private String createdDate;
	private String lastUpdatedDate;  
	private String userId;
	private String colour = "white";
	private String reminder;
	private boolean trashStatus = false;
	private boolean pinnedStatus = false;
	private boolean archieveStatus = false;
	private List<Label> labels;

	/**
	 * Method to find the archieve Status
	 * 
	 * @return the archieveStatus
	 */
	public boolean isArchieveStatus() {
		return archieveStatus;
	}

	/**
	 * Method to get the List of Labels
	 * 
	 * @return listofLabels
	 */
	public List<Label> getLabels() {
		return labels;
	}

	/**
	 * Method to set the listOfLabels
	 * 
	 * @param listOfLabels
	 */
	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	/**
	 * Method to set the archieve status
	 * 
	 * @param archieveStatus
	 */
	public void setArchieveStatus(boolean archieveStatus) {
		this.archieveStatus = archieveStatus;
	}

	/**
	 * Method is to pin the status
	 * 
	 * @return
	 */
	public boolean isPinnedStatus() {
		return pinnedStatus;
	}

	/**
	 * Method is to set Pinned Status
	 * 
	 * @param pinnedStatus
	 */
	public void setPinnedStatus(boolean pinnedStatus) {
		this.pinnedStatus = pinnedStatus;
	}

	/**
	 * Method to get the Trash Status
	 * 
	 * @return
	 */
	public boolean isTrashStatus() {
		return trashStatus;
	}

	/**
	 * Method to set the Trash Status
	 * 
	 * @param trashStatus
	 */
	public void setTrashStatus(boolean trashStatus) {
		this.trashStatus = trashStatus;
	}

	/**
	 * Method to get the color
	 * 
	 * @return
	 */
	public String getColour() {
		return colour;
	}

	/**
	 * Method to set the color
	 * 
	 * @param colour
	 */
	public void setColour(String colour) {
		this.colour = colour;
	}

	/**
	 * Method to get the LastUpdatedDate in the Note
	 * 
	 * @return
	 */
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	/**
	 * Method to set the LastUpdatedDate of the Note
	 * 
	 * @param lastUpdatedDate
	 */
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	/**
	 * Method to get the Title of the Note
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Method to get the UserId of the Note
	 * 
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Method to get the userId of the note
	 * 
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Method to Set the title of Note
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Method to get the description of Note
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Method to set the description of the Note
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Method to get the createdDate of the Note
	 * 
	 * @return
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * Method to set the createdDate of the Note
	 * 
	 * @param createdDate
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Method to get the NoteId of the Note
	 * 
	 * @return
	 */
	public String getNoteId() {
		return noteId;
	}

	/**
	 * Method to set the NoteId of the note
	 * 
	 * @param noteId
	 */
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
/**
 * Method to get the reminder
 * @return
 */
	public String getReminder() {
		return reminder;
	}
/**
 * Method to set the reminder
 * @param reminder
 */
	public void setReminder(String reminder) {
		this.reminder = reminder;
	}
}
