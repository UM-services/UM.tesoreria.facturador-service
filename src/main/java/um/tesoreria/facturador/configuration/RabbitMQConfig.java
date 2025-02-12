package um.tesoreria.facturador.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_INVOICE = "recibo_queue";
    public static final String QUEUE_TESTER = "tester_queue";

    @Bean
    public Queue reciboQueue() {
        return new Queue(QUEUE_INVOICE, true); // Cola persistente
    }

    @Bean
    public Queue testerQueue() {
        return new Queue(QUEUE_TESTER, true); // Cola persistente
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        
        // Configurar política de reintentos
        RetryTemplate retryTemplate = new RetryTemplate();
        
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);    // 500ms inicial
        backOffPolicy.setMultiplier(2.0);         // duplicar tiempo entre reintentos
        backOffPolicy.setMaxInterval(10000);      // máximo 10 segundos
        
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);            // máximo 3 intentos
        
        retryTemplate.setBackOffPolicy(backOffPolicy);
        retryTemplate.setRetryPolicy(retryPolicy);
        
        template.setRetryTemplate(retryTemplate);
        return template;
    }

    @Bean
    public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        // Limitar el número de canales por conexión
        factory.setChannelCacheSize(5);           // Suficiente para un servicio de facturación
        // Limitar el número de conexiones en caché
        factory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        // Establecer límite de tiempo para conexiones inactivas
        factory.setChannelCheckoutTimeout(1000);  // 1 segundo es adecuado para el caso de uso
        return factory;
    }

}
