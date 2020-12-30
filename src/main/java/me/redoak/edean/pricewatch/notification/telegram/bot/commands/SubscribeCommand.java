package me.redoak.edean.pricewatch.notification.telegram.bot.commands;

import me.redoak.edean.pricewatch.products.ProductRequest;
import me.redoak.edean.pricewatch.products.TrackedProductService;
import me.redoak.edean.pricewatch.subscribers.Subscriber;
import me.redoak.edean.pricewatch.subscribers.SubscriberRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

@Component
public class SubscribeCommand extends AuthenticatedCommand {

    private static final int URL_INDEX = 1;

    private final TrackedProductService trackedProductService;

    public SubscribeCommand(SubscriberRepository subscriberRepository, TrackedProductService trackedProductService) {
        super(subscriberRepository);
        this.trackedProductService = trackedProductService;
    }

    @Override
    protected String execute(Message message, Subscriber subscriber, List<Argument> argumentList) {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setUrl(argumentList.get(URL_INDEX).getValue());

        trackedProductService.subscribe(productRequest, subscriber);

        return "Erledigt!";
    }

    @Override
    protected void initializeArguments(List<Argument> argumentList) {
        argumentList.add(Argument.builder()
                .name("Abonnieren")
                .description("Abonniere einen Artikel anhand seiner URL, um Benachrichtigungen zu erhalten, " +
                        "wenn sich der Preis ändert.")
                .value("/subscribe")
                .build());
        argumentList.add(Argument.builder()
                .name("URL")
                .description("Die URL des Produkts, für das Du keine Benachrichtigungen mehr erhalten möchtest.")
                .build());
    }
}
