/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a User Service Implementation  
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.userservice.service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.todoapplication.userservice.model.User;
import com.bridgelabz.todoapplication.utilservice.ToDoException;

/**
 * @author Saurav
 *         <p>
 *         IUserService
 *         </p>
 */
public interface IUserService {
	/**
	 * @param user
	 * @throws ToDoException
	 * @throws MessagingException
	 *             <p>
	 *             Method to update the User
	 *             </p>
	 */
	public void updateUser(User user,String validToken) throws ToDoException, MessagingException;

	/**
	 * @param user
	 * @return
	 * @throws ToDoException
	 *             <p>
	 *             This is to validate the User
	 *             </p>
	 */
	public boolean validateUser(User user,HttpServletResponse resp,String validToken) throws ToDoException;

	/**
	 * @param user
	 * @return
	 * @throws ToDoException
	 *             <p>
	 *             This method is to check the Email if its present or not
	 *             </p>
	 */
	public boolean checkEmail(User user) throws ToDoException;

	/**
	 * @param validToken
	 * @param user
	 * @throws ToDoException
	 * @throws MessagingException
	 *             <p>
	 *             this is to send the activation link via Email
	 *             </p>
	 */
	public void sendActivationLink(String validToken, User user) throws ToDoException, MessagingException;

	/**
	 * @param user
	 * @param validToken
	 * @throws MessagingException
	 *             <p>
	 *             This Method is to Send the Mail with the token
	 *             </p>
	 */
	public void sendMail(User user, String validToken) throws MessagingException;

	/**
	 * @param token2
	 * @return This Method is to activate the User
	 */
	public boolean activate(String token2);

	/**
	 * @param user
	 * @return
	 *         <p>
	 *         This Method is to generate the token
	 *         </p>
	 */
	public String tokengenerator(User user);

	/**
	 * @param token
	 * @param password
	 * @param newPassword
	 * @throws ToDoException
	 *             <p>
	 *             This method to reset the Password of the Note
	 *             </p>
	 */
	public void resetPassword(String token, String password, String newPassword) throws ToDoException;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws ToDoException
	 *             <p>
	 *             This method is to check if the Email is present or not
	 *             </p>
	 */
	public boolean isEmailPresent(User user) throws ToDoException;

}
