package cz.ptw.packagedelivery.interceptor.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.ChannelInterceptor;

import static org.mockito.Mockito.mock;

@Configuration
public class InterceptorTestConfigurations {

   @Bean
   public MessageHandler mockHandler(){
       return mock(MessageHandler.class);
   }

    @Bean
    public IntegrationFlow interceptIntegrationFlow(ChannelInterceptor exitCodeChannelInterceptor, MessageHandler mockHandler) {
        return IntegrationFlows.fromSupplier(() -> "Some stupid text", configurer -> configurer.poller(Pollers.fixedRate(100)))
                .intercept(exitCodeChannelInterceptor)
                .handle(mockHandler).get();
    }
}
