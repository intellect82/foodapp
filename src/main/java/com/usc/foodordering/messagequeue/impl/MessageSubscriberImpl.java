package com.usc.foodordering.messagequeue.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class MessageSubscriberImpl implements MessageListener{

	public static List<String> messageList = new ArrayList<>();
	
	@Override
	public void onMessage(Message arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		messageList.add(arg0.toString());
	}

}
