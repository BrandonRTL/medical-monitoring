package liga.medical.medicalmonitoring.core.api.service;

public interface RabbitRouterService {
    void routeMessageWithExchange(String message);
}
