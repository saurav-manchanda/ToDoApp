/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a Class Link having appropriate fields.  
 * @author Saurav Manchanda
 * @version 1.0
 * @since 6/08/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.noteservice.model;

/**
 * @author Saurav
 *         <p>
 *         This class is for obtaining the properties from link
 *         </p>
 */
public class Link {

	private String linkTitle;
	private String linkDomainName;
	private String linkImage;

	public Link() {
	}

	public String getLinkTitle() {
		return linkTitle;
	}

	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	public String getLinkDomainName() {
		return linkDomainName;
	}

	public void setLinkDomainName(String linkDomainName) {
		this.linkDomainName = linkDomainName;
	}

	public String getLinkImage() {
		return linkImage;
	}

	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
	}

	@Override
	public String toString() {
		return "Link [linkTitle=" + linkTitle + ", linkDomainName=" + linkDomainName + ", linkImage=" + linkImage + "]";
	}
}