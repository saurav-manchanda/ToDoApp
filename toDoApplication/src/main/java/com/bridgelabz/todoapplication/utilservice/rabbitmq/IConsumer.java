package com.bridgelabz.todoapplication.utilservice.rabbitmq;

import javax.mail.MessagingException;

public interface IConsumer {

	void recievedMessage(Mail email) throws MessagingException;
}
