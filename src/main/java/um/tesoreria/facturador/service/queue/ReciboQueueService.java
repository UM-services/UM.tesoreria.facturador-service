package um.tesoreria.facturador.service.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import um.tesoreria.facturador.configuration.RabbitMQConfig;
import um.tesoreria.facturador.kotlin.tesoreria.core.dto.FacturacionElectronicaDto;
import um.tesoreria.facturador.model.dto.ReciboMessageDto;

@Service
@Slf4j
public class ReciboQueueService {

    private final RabbitTemplate rabbitTemplate;

    public ReciboQueueService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public void sendReciboQueue(ReciboMessageDto reciboMessage) {
        log.debug("Processing ReciboQueueService.sendReciboQueue");
        log.debug("Encolando envío");
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_INVOICE, reciboMessage);
    }

}
