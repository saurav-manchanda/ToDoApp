/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a Class Label having appropriate fields.  
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
 *         This is a POJO class of Label having fields like
 *         LableId,LableName,userId and noteId and corresponding setters and
 *         getters.
 *         </p>
 */
@Document
public class Label {
	@Id
	@ApiModelProperty(hidden = true)
	private String lableId;
	private String labelName;
	@ApiModelProperty(hidden = true)
	private String userId;
	@ApiModelProperty(hidden = true)
	private String noteId;

	/**
	 * Method to get the Lable Id
	 * 
	 * @return
	 */
	public String getLableId() {
		return lableId;
	}

	/**
	 * Method to set the labelId
	 * 
	 * @param lableId
	 */
	public void setLableId(String lableId) {
		this.lableId = lableId;
	}

	/**
	 * Method to get the LabelName
	 * 
	 * @return
	 */
	public String getLabelName() {
		return labelName;
	}

	/**
	 * Method to get the NoteId
	 * 
	 * @return
	 */
	public String getNoteId() {
		return noteId;
	}

	/**
	 * Method to set the NoteID
	 * 
	 * @param noteId
	 */
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	/**
	 * Method to get the UserId
	 * 
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Method to set the USerId
	 * 
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Method to set the Label Name
	 * 
	 * @param labelName
	 */
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}
