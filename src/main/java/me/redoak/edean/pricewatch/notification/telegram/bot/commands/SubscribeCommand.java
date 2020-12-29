package me.redoak.edean.pricewatch.notification.telegram.bot.commands;

import me.redoak.edean.pricewatch.products.ProductRequest;
import me.redoak.edean.pricewatch.products.TrackedProductService;
import me.redoak.edean.pricewatch.subscribers.Subscriber;
import me.redoak.edean.pricewatch.subscribers.SubscriberRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
public class SubscribeCommand extends AuthenticatedCommand {

    private final TrackedProductService trackedProductService;

    public SubscribeCommand(SubscriberRepository subscriberRepository, TrackedProductService trackedProductService) {
        super(subscriberRepository);
        this.trackedProductService = trackedProductService;
    }

    @Override
    public boolean appliesTo(Message message) {
        return message.getText().split(" ")[0].toLowerCase().startsWith("/subscribe");
    }

    @Override
    protected String executeInternal(Message message, Subscriber subscriber) {
        var s = message.getText().split(" ");
        if (s.length != 2)
            return "Falsche Menge an Argumenten! `/subscribe »url«`";
        ProductRequest productRequest = new ProductRequest();
        productRequest.setUrl(s[1]);
        trackedProductService.subscribe(productRequest, subscriber);

        return "Erledigt!";
    }
}
