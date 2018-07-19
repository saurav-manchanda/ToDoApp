/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a POJO class of Note having fields related to a Note and corresponding setters and getters  
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.noteservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Saurav
 *         <p>
 *         This is a POJO class of Note having fields related to a Note and
 *         corresponding setters and getters
 *         </p>
 */
@Document
public class Note {
	@Id
	private String noteId;
	private String title;
	private String description;
	@ApiModelProperty(hidden = true)
	private String createdDate;
	@ApiModelProperty(hidden = true)
	private String lastUpdatedDate;
	@ApiModelProperty(hidden = true)
	private String userId;
	@ApiModelProperty(hidden = true)
	private String colour="white";
	@ApiModelProperty(hidden = true)
	private boolean trashStatus=false;
	public boolean isTrashStatus() {
		return trashStatus;
	}

	public void setTrashStatus(boolean trashStatus) {
		this.trashStatus = trashStatus;
	}

	public String getColour() {
		return colour;
	}

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

}
