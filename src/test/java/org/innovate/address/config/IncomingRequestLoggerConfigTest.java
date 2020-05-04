package org.innovate.address.config;

import org.innovate.address.interceptor.LoggingInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@ExtendWith({MockitoExtension.class})
public class IncomingRequestLoggerConfigTest {
    private IncomingRequestLoggerConfig incomingRequestLoggerConfig;

    @BeforeEach
    public void setup() {
        incomingRequestLoggerConfig = new IncomingRequestLoggerConfig();
    }

    @Test
    public void testAddInterceptors() {
        IncomingRequestLoggerConfig incomingRequestLoggerConfig = new IncomingRequestLoggerConfig();
        InterceptorRegistry interceptorRegistry = Mockito.mock(InterceptorRegistry.class);
        incomingRequestLoggerConfig.addInterceptors(interceptorRegistry);
        Mockito.verify(interceptorRegistry, Mockito.times(1))
                .addInterceptor(Mockito.any(LoggingInterceptor.class));
    }
}
