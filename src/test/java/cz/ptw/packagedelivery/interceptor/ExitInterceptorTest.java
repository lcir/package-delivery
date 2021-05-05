package cz.ptw.packagedelivery.interceptor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.MessageHandler;

import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Exit Interceptor Test")
@ComponentScan("cz.ptw.packagedelivery.interceptor.configuration")
class ExitInterceptorTest {

    @Autowired
    private MessageHandler mockHandler;

    @Test
    @DisplayName("Should Pass Message Through Interceptor When Quit Is Not Typed")
    void shouldPassMessageThroughInterceptorWhenQuitIsNotTyped() throws InterruptedException {
        verify(mockHandler, atLeastOnce()).handleMessage(any());
        TimeUnit.SECONDS.sleep(1);
    }
}
