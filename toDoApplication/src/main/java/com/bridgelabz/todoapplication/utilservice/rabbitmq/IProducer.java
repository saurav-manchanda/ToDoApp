package com.bridgelabz.todoapplication.utilservice.rabbitmq;

public interface IProducer {
	public void produceMsg(String to,String subject,String body);
}
