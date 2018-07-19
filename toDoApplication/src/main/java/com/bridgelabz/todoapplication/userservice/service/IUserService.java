/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a User Service Implementation  
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.userservice.service;

import javax.mail.MessagingException;

import com.bridgelabz.todoapplication.userservice.model.User;
import com.bridgelabz.todoapplication.utilservice.ToDoException;

/**
 * @author Saurav
 *<p>
 *IUserService 
 *</p>
 */
public interface IUserService {
	public void updateUser(User user) throws ToDoException, MessagingException;

	public boolean validateUser(User user)throws ToDoException;

	public boolean checkEmail(User user) throws ToDoException;

	public void sendActivationLink(String validToken, User user) throws ToDoException, MessagingException;

	public void sendMail(User user,String validToken) throws MessagingException;

	public boolean activate(String token2);

	public User getUserForLogin(String email,String password);
	
	public String tokengenerator(User user);
	
	public void resetPassword(String token,String password,String newPassword) throws ToDoException;

	public boolean isEmailPresent(User user) throws ToDoException;


}
