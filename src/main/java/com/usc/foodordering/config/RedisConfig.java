package com.usc.foodordering.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import com.usc.foodordering.messagequeue.MessagePublisher;
import com.usc.foodordering.messagequeue.impl.MessagePublisherImpl;
import com.usc.foodordering.messagequeue.impl.MessageSubscriberImpl;

/**
 * @author venkateswarlusayana
 * GreatLeanring -FoodOrder app
 *
 */
@Configuration
@ComponentScan("com.usc.foodordering")
public class RedisConfig {

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Object.class));
		return redisTemplate;
	}

	@Bean
	ChannelTopic channelTopic() {
		return new ChannelTopic("pubsub:queue");
	}

	@Bean
	MessagePublisher redisPublisher() {
		return new MessagePublisherImpl(redisTemplate(), channelTopic());
	}

	@Bean
	MessageListenerAdapter messageListenerAdapter() {
		return new MessageListenerAdapter(new MessageSubscriberImpl());
	}

	@Bean
	RedisMessageListenerContainer redisMessageListenerContainer() {
		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
		redisMessageListenerContainer.setConnectionFactory(jedisConnectionFactory());
		redisMessageListenerContainer.addMessageListener(messageListenerAdapter(), channelTopic());
		return redisMessageListenerContainer;
	}
}
