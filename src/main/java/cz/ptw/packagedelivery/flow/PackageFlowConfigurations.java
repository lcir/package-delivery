package cz.ptw.packagedelivery.flow;

import cz.ptw.packagedelivery.functions.DataStorageAggregationTransformer;
import cz.ptw.packagedelivery.functions.DeserializerTransformer;
import cz.ptw.packagedelivery.record.Fee;
import cz.ptw.packagedelivery.record.SmartPackage;
import cz.ptw.packagedelivery.record.SmartPackageWithPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@EnableIntegration
@Configuration
@Slf4j
public class PackageFlowConfigurations {

    @Bean
    public IntegrationFlow errorHandlingFlow(MessageChannel errorChannel) {
        return IntegrationFlows.from(errorChannel)
                .handle(message -> log.error("""
                        User input must be defined as double number with 3 decimal places, " +
                        "1x white space, and post code with 5 fixed numbers.
                         Your input was:""" + ((MessagingException) message.getPayload()).getFailedMessage().getPayload()))
                .get();
    }

    @Bean
    public IntegrationFlow packageStoreProcessingFlow(MessageSource<String> inputMessageSource,
                                                      MessageHandler storeMessageHandler,
                                                      DeserializerTransformer deserializerTransformer,
                                                      ChannelInterceptor exitCodeChannelInterceptor) {
        return IntegrationFlows.from(inputMessageSource, configurer -> configurer.poller(Pollers.fixedDelay(500)))
                .filter(value -> !((String) value).isBlank())
                .intercept(exitCodeChannelInterceptor)
                .transform(message -> deserializerTransformer.deserialize((String) message))
                .handle(storeMessageHandler)
                .get();
    }

    @Bean
    public IntegrationFlow packageOutputProcessingFlow(List<SmartPackage> storage, List<Fee> feeStorage, MessageHandler outputMessageHandler, DataStorageAggregationTransformer transformer) {
        return IntegrationFlows.fromSupplier(() -> storage, configurer -> configurer.poller(Pollers.fixedDelay(10000)))
                .filter(source -> !((List) source).isEmpty())
                .transform(source -> transformer.transformToOutputMap((List<SmartPackage>) source, feeStorage))
                .transform(map -> transformer.transformToOutputStringList((HashMap<Integer, Optional<SmartPackageWithPrice>>) map))
                .split()
                .handle(outputMessageHandler)
                .get();
    }
}
