package cz.ptw.packagedelivery.configuration;

import cz.ptw.packagedelivery.record.Fee;
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

/**
 * Configuration and bean initialization for main process
 */
@Configuration
public class MessageProcessingConfiguration {

    /**
     * Main Data Storage
     *
     * @return List of Smart Packages
     */
    @Bean("storage")
    @Scope("singleton")
    public List<SmartPackage> getStorage() {
        return new ArrayList<>();
    }

    /**
     * Fee Storage
     *
     * @return List of Fees
     */
    @Bean("feeStorage")
    @Scope("singleton")
    public List<Fee> getFeeStorage() {
        return new ArrayList<>();
    }

    /**
     * Shell Input Message Source
     *
     * @return Stdin Message source
     */
    @Bean
    public MessageSource<String> inputMessageSource() {
        return CharacterStreamReadingMessageSource.stdin();
    }

    /**
     * Handler for filling data into storage
     *
     * @param storage Injected main data storage
     * @return Bean of Message handler
     */
    @Bean
    public MessageHandler storeMessageHandler(final List<SmartPackage> storage) {
        return message -> storage.add((SmartPackage) message.getPayload());
    }

    /**
     * Output message handler
     *
     * @return StdOut message handler
     */
    @Bean
    public MessageHandler outputMessageHandler() {
        final var stdout = CharacterStreamWritingMessageHandler.stdout();
        stdout.appendNewLine(true);
        return stdout;
    }

    /**
     * Channel for exceptions
     *
     * @return Direct channel for exceptions.
     */
    @Bean
    public MessageChannel errorChannel() {
        return MessageChannels.direct("errorChannel")
                .get();
    }

    /**
     * Interceptor for listening of quit fraze from stdin.
     *
     * @return Channel Interceptor
     */
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
