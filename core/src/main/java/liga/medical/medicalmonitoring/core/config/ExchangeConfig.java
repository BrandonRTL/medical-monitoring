package liga.medical.medicalmonitoring.core.config;

import liga.medical.dto.MessageType;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfig {

    public static final String DIRECT_EXCHANGE_NAME = "exchange";

    private final Queue dailyQueue;

    private final Queue alertQueue;

    private final Queue errorQueue;

    public ExchangeConfig(@Qualifier("dailyQueue") Queue dailyQueue,
                          @Qualifier("alertQueue") Queue alertQueue,
                          @Qualifier("errorQueue") Queue errorQueue) {
        this.errorQueue = errorQueue;
        this.dailyQueue = dailyQueue;
        this.alertQueue = alertQueue;
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingDailyMessage() {
        return BindingBuilder.bind(dailyQueue).to(directExchange()).with(MessageType.DAILY.toString());
    }

    @Bean
    public Binding bindingAlertMessage() {
        return BindingBuilder.bind(alertQueue).to(directExchange()).with(MessageType.ALERT.toString());
    }

    @Bean
    public Binding bindingErrorMessage() {
        return BindingBuilder.bind(errorQueue).to(directExchange()).with(MessageType.ERROR.toString());
    }
}
