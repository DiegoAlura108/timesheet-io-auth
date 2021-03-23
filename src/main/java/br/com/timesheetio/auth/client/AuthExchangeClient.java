package br.com.timesheetio.auth.client;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.timesheetio.auth.dto.PersonAuthToPersonMessageDTO;

@Component
public class AuthExchangeClient {

//	@Value("${auth.properties.amqp.exchange}")
//	private String exchange;
//	
//	@Value("${auth.properties.amqp.routing-key}")
//	private String routingKey;
//	
//	@Autowired
//	private RabbitTemplate rabbitTemplate;
//	
//	public void sendMessage(PersonAuthToPersonMessageDTO personAuthToPersonMessageDTO) {
//		rabbitTemplate.convertAndSend(exchange, routingKey, personAuthToPersonMessageDTO);
//	}
}
