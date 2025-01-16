package um.tesoreria.facturador.service.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import um.tesoreria.facturador.configuration.RabbitMQConfig;

@Service
@Slf4j
public class QueueService {

    private final RabbitTemplate rabbitTemplate;

    public QueueService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void test(String message) {
        log.debug("Enviando mensaje a consumer: {}", message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_TESTER, message);
    }

}
