/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating an interface for Redis Repository. The redis Repository is being used for storing
 * tokens. We are getting the respective tokens and then comparing the respective email with 
 * the existing token email.If they both are not equal then we are throwing the error.
 * @author Saurav Manchanda
 * @version 1.0
 * @since 22/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.utilservice.RedisRepository;

/**
 * @author Saurav
 *         <p>
 *         an interface for Redis Repository. The redis Repository is being used
 *         for storing tokens. We are getting the respective tokens and then
 *         comparing the respective email with the existing token email.If they
 *         both are not equal then we are throwing the error.
 *         </p>
 */
public interface IRedisRepository {
	/**
	 * <p>
	 * The method is for setting the token in the redis repository. The token is
	 * being set with respect to a key being taken in terms of key value pairs.
	 * </p>
	 * 
	 * @param token
	 */
	void setToken(String token);

	/**
	 * <p>
	 * The method is for getting the token by passing the email from the respecting
	 * redis repository. The userID is acting as the respective key for getting the
	 * token.
	 * </p>
	 * 
	 * @param email
	 * @return Token
	 */
	String getToken(String userId);

	/**
	 * <p>
	 * The method is for deleting the token from the respective redis repository.The
	 * userId is acting as the respective key for getting the token
	 * </p>
	 * 
	 * @param email
	 */
	void deleteToken(String userId);

}
