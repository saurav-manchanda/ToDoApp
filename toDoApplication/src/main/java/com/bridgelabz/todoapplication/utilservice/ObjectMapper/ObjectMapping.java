/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication).
 * Creating a ObjectMapping class
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.utilservice.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.todoapplication.utilservice.ToDoException;
import com.bridgelabz.todoapplication.utilservice.Precondition.PreCondition;

/**
 * @author Saurav
 * <p>
 * This class is for object Mapping using the ModelMapper Class.
 * </p>
 */
public class ObjectMapping {
	@Autowired
	ModelMapper modelmapper;

	public  <T> T map(Object source,Class<T>destinationType) throws ToDoException
	{
		PreCondition.checkNotNull(source,"NullPointerException: source is null");
		PreCondition.checkNotNull(destinationType,"NullPointerException : DestinationType is null");
		return modelmapper.map(source, destinationType);
	}
}
