package liga.medical.medicalmonitoring.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.medical.medicalmonitoring.core.api.service.RabbitRouterService;
import liga.medical.medicalmonitoring.core.config.ExchangeConfig;
import liga.medical.dto.MessageType;
import liga.medical.dto.RabbitMessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitRouterServiceImpl implements RabbitRouterService {
    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    public RabbitRouterServiceImpl(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void routeMessageWithExchange(String message) {
        rabbitTemplate.setExchange(ExchangeConfig.DIRECT_EXCHANGE_NAME);

        try {
            RabbitMessageDto rabbitMessageDto = objectMapper.readValue(message, RabbitMessageDto.class);
            rabbitTemplate.convertAndSend(rabbitMessageDto.getMessageType().toString(), message);
            System.out.println("Роутер перенаправит сообщение");
        } catch (JsonProcessingException e) {
            rabbitTemplate.convertAndSend(MessageType.ERROR.toString(), e.getMessage());
            System.out.println("При перенаправлении произошла ошибка");
        }
    }
}