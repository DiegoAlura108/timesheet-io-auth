package br.com.timesheetio.auth.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.timesheetio.auth.dto.PersonAuthDTO;

@Component
public class AuthQueueListener {

//	@RabbitListener(queues = {"${auth.properties.amqp.queue}"})
//	public void getMessageSended(@Payload PersonAuthDTO personAuthDTO) {
//		//TODO: implementar gravacao
//	}
}
