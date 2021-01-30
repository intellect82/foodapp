package com.usc.foodordering.messagequeue.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.usc.foodordering.messagequeue.MessagePublisher;

@Service
public class MessagePublisherImpl implements MessagePublisher{

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private ChannelTopic channelTopic;
	
	public MessagePublisherImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public MessagePublisherImpl(RedisTemplate<String, Object> redisTemplate, ChannelTopic channelTopic){
		this.redisTemplate = redisTemplate;
		this.channelTopic = channelTopic;
	}
	
	@Override
	public void publish(String message) {
		// TODO Auto-generated method stub
		redisTemplate.convertAndSend(channelTopic.getTopic(), message);
	}

}
