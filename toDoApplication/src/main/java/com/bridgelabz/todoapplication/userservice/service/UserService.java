/********************************************************************************* *
 * Purpose: To do Login Registration with the help of MONGODB repository. 
 * Creating UserService class which is a service class.
 * 
 * @author Saurav Manchanda
 * @version 1.0
 * @since 11/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.userservice.service;

import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.todoapplication.userservice.model.User;
import com.bridgelabz.todoapplication.userservice.repository.Repository;
import com.bridgelabz.todoapplication.utilservice.MailService;
import com.bridgelabz.todoapplication.utilservice.ToDoException;
import com.bridgelabz.todoapplication.utilservice.TokenGenerator;
import com.bridgelabz.todoapplication.utilservice.Precondition.PreCondition;
import com.bridgelabz.todoapplication.utilservice.rabbitmq.IProducer;

/**
 * @author Saurav
 *         <p>
 *         Class UserService that implements the methods of IUserService
 *         interface
 *         </p>
 */
@Service
public class UserService implements IUserService {
	@Autowired
	Repository repository;
	TokenGenerator token = new TokenGenerator();
	@Autowired
	MailService mailService;
	@Autowired
	IProducer producer;

	@Autowired
	private PasswordEncoder passwordEncoder;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	/**
	 * @param user
	 * @return
	 * @throws ToDoException
	 *             <p>
	 *             This Method to validate the user.
	 *             </p>
	 */
	@Override
	public boolean validateUser(User user, HttpServletResponse resp) throws ToDoException {
		String email = user.getEmail();
		String password = user.getPassword();
		PreCondition.checkNotNull(email, "Email cannot be Null");
		PreCondition.checkNotNull(password, "Password Cannot be null");
		PreCondition.checkNotEmptyString(email, "Email cannot be an empty string");
		PreCondition.checkNotEmptyString(password, "Password cannot be an empty string");
		if (repository.getByEmail(email).isPresent()) {
			Optional<User> user1 = repository.getByEmail(email);
			if (passwordEncoder.matches(password, user1.get().getPassword())
					&& user1.get().getStatus().equals("true")) {
				String validToken = tokengenerator(user);
				resp.addHeader("Authorization", validToken);
				return true;
			}
		}
		return false;
	}

	/**
	 * @param user
	 * @return
	 * @throws ToDoException
	 *             <p>
	 *             This Method to check the email if present or not in the database
	 *             </p>
	 */
	@Override
	public boolean checkEmail(User user) throws ToDoException {
		String email = user.getEmail();
		PreCondition.checkNotEmptyString(email, "Email cannot be empty");
		PreCondition.checkNotNull(email, "Email cannot be Null");
		if (repository.getByEmail(email).isPresent()) {
			PreCondition.commonMethod("User not present with the corresponding email");
		}
		return false;
	}

	/**
	 * @param user
	 * @throws ToDoException
	 * @throws MessagingException
	 *             <p>
	 *             This Method to update the user in the database
	 *             </p>
	 */
	@Override
	public void updateUser(User user) throws ToDoException, MessagingException {
		PreCondition.checkNotNull(user.getEmail(), "Email cannot be null");
		PreCondition.checkNotNull(user.getPassword(), "Password cannot be Null");
		PreCondition.checkNotNull(user.getUserName(), "Username cannot be Null");
		String validToken = tokengenerator(user);
		sendActivationLink(validToken, user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repository.save(user);
	}

	/**
	 * @param validToken
	 * @param user
	 * @throws ToDoException
	 * @throws MessagingException
	 *             <p>
	 *             This Method to send the validation link
	 *             </p>
	 */
	@Override
	public void sendActivationLink(String validToken, User user) throws ToDoException, MessagingException {
		String email = user.getEmail();
		PreCondition.checkNotNull(email, "Email cannot be Null");
		PreCondition.checkNotEmptyString(email, "Email cannot be empty String");
		String to = email;
		String subject = "Activation Link";
		String body = "Click on the link below to activate your acount\n" + "http://localhost:8080/activate/?"
				+ validToken;
		producer.produceMsg(to, subject, body);

		logger.info("mail sent successfully to activate the account");

	}

	/**
	 * @param user
	 * @throws MessagingException
	 *             <p>
	 *             This Method to send the mail to the user with there password
	 *             </p>
	 */
	@Override
	public void sendMail(User user, String validToken) throws MessagingException {
		String email = user.getEmail();
		if (repository.getByEmail(email).isPresent()) {
			String to = email;
			String subject = "To change your password";
			String body = "Click on the link below to change your password\n" + "http://localhost:8080/newpassword/?"
					+ validToken;
			producer.produceMsg(to, subject, body);
			logger.info("Mail sent successfully to change the password");
		}
	}

	/**
	 * @param token2
	 *            <p>
	 *            This method is to activate the user by setting its status as true
	 *            </p>
	 */
	@Override
	public boolean activate(String token2) {
		Optional<User> user = repository.getByEmail(token.parseJWT(token2));
		user.get().setStatus("true");
		repository.save(user.get());
		return true;
	}

	/**
	 * @param user
	 *            <p>
	 *            This method is generating the token.
	 *            </p>
	 */
	@Override
	public String tokengenerator(User user) {
		String validToken = token.generator(user);
		token.parseJWT(validToken);
		logger.info("The token generated is:" + validToken);
		return validToken;
	}

	/**
	 * @param token,password
	 *            <p>
	 *            This method is resetting the password of the corresponding user
	 *            email id entered
	 *            </p>
	 * @throws ToDoException
	 */
	@Override
	public void resetPassword(String token1, String password, String newPassword) throws ToDoException {
		if (password.equals(newPassword)) {
			String email = token.parseJWT(token1);
			Optional<User> user1 = repository.getByEmail(email);
			User user = new User();
			user.setEmail(user1.get().getEmail());
			user.setId(user1.get().getId());
			user.setPassword(passwordEncoder.encode(password));
			user.setStatus(user1.get().getStatus());
			user.setUserName(user1.get().getUserName());
			repository.save(user);
		} else {
			PreCondition.commonMethod("password and comfirm password donot match");
		}
	}

	@Override
	public boolean isEmailPresent(User user) throws ToDoException {
		String email = user.getEmail();
		PreCondition.checkNotEmptyString(email, "Email cannot be an empty String");
		PreCondition.checkNotNull(email, "Email cannot be Null");
		if (repository.getByEmail(email).isPresent()) {
			return true;
		}
		return false;
	}
}
