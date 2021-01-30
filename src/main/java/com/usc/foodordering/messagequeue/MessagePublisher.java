package com.usc.foodordering.messagequeue;

public interface MessagePublisher {
	void publish(String message);
}
