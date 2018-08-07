/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a Class Description having appropriate fields.  
 * @author Saurav Manchanda
 * @version 1.0
 * @since 6/08/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.noteservice.model;

import java.util.List;

/**
 * @author Saurav
 *         <p>
 *         This class is for storing the description of the note.All the simple
 *         strings in description will go to the list of simple words and the
 *         link part will go to other list.
 *         </p>
 *
 */
public class Description {
	private List<String> simpleDescription;
	private List<Link> linkDescription;
/**
 * Constructor
 */
	public Description() {
	}
/**
 * Method to get the simple description
 * @return
 */
	public List<String> getSimpleDescription() {
		return simpleDescription;
	}
/**
 * Method to set the simple Description
 * @param simpleDescription
 */
	public void setSimpleDescription(List<String> simpleDescription) {
		this.simpleDescription = simpleDescription;
	}
/**
 * Method to get the Link Description
 * @return
 */
	public List<Link> getLinkDescription() {
		return linkDescription;
	}
/**
 * Method to set the Link Description
 * @param linkDescription
 */
	public void setLinkDescription(List<Link> linkDescription) {
		this.linkDescription = linkDescription;
	}
/**
 * Overridden tostring method
 */
	@Override
	public String toString() {
		return "Description [description=" + simpleDescription + ", linkDescription=" + linkDescription + "]";
	}

}