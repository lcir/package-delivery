package cz.ptw.packagedelivery.configuration;

import cz.ptw.packagedelivery.record.SmartPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.stream.CharacterStreamReadingMessageSource;
import org.springframework.integration.stream.CharacterStreamWritingMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MessageProcessingConfiguration {

    @Bean
    @Scope("singleton")
    public List<SmartPackage> getStorage() {
        return new ArrayList<>();
    }

    @Bean
    public MessageSource<String> inputMessageSource() {
        return CharacterStreamReadingMessageSource.stdin();
    }

    @Bean
    public MessageHandler storeMessageHandler(final List<SmartPackage> storage) {
        return message -> storage.add((SmartPackage) message.getPayload());
    }

    @Bean
    public MessageHandler outputMessageHandler() {
        final var stdout = CharacterStreamWritingMessageHandler.stdout();
        stdout.appendNewLine(true);
        return stdout;
    }

    @Bean
    public MessageChannel errorChannel() {
        return MessageChannels.direct("errorChannel")
                .get();
    }

    @Bean
    public ChannelInterceptor exitCodeChannelInterceptor() {
        return new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                if ("quit".equals(message.getPayload()))
                    System.exit(0);

                return ChannelInterceptor.super.preSend(message, channel);
            }
        };
    }
}
