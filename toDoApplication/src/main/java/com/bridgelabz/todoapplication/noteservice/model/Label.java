package com.bridgelabz.todoapplication.noteservice.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

@Document
public class Label {
	@Id
	@ApiModelProperty(hidden = true)
	private String lableId;
	private String labelName;
	@ApiModelProperty(hidden=true)
	private String userId;
	@ApiModelProperty(hidden=true)
	private String noteId;
	public String getLableId() {
		return lableId;
	}
	public void setLableId(String lableId) {
		this.lableId = lableId;
	}
	public String getLabelName() {
		return labelName;
	}
	public String getNoteId() {
		return noteId;
	}
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}
