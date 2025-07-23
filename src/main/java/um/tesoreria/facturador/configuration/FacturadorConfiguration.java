package um.tesoreria.facturador.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableFeignClients(basePackages = "um.tesoreria.facturador")
public class FacturadorConfiguration {

}
