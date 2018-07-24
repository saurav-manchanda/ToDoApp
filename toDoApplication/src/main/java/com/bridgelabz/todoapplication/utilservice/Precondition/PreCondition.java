package com.bridgelabz.todoapplication.utilservice.Precondition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import com.bridgelabz.todoapplication.noteservice.repository.INoteRepository;
import com.bridgelabz.todoapplication.userservice.repository.Repository;
import com.bridgelabz.todoapplication.utilservice.ToDoException;

public class PreCondition {
	@Autowired
	static INoteRepository noteRepository;
	@Autowired
	static Repository userRepository;
	/**
	 * @param reference
	 * @param errorMessage
	 *            <p>
	 *            Function is to check for null object and returns object if not
	 *            null
	 *            </p>
	 * @return reference
	 * @throws ToDoException
	 */
	public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) throws ToDoException {
		if (reference == null) {
			throw new ToDoException(String.valueOf(errorMessage));
		}
		return reference;
	}

	public static <T> T checkNotEmptyString(T reference, @Nullable Object errorMessage) throws ToDoException {
		if (reference.equals("")) {
			throw new ToDoException(String.valueOf(errorMessage));
		}
		return reference;
	}

	public static <T> void commonMethod( @Nullable Object errorMessage) throws ToDoException {
		throw new ToDoException(String.valueOf(errorMessage));	
	}
}
