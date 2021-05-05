package cz.ptw.packagedelivery;

import cz.ptw.packagedelivery.service.FileLoadingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

import java.util.Arrays;

@Slf4j
@IntegrationComponentScan
@EnableIntegration
@SpringBootApplication
@RequiredArgsConstructor
public class PackageDeliveryApplication implements CommandLineRunner {

    private final FileLoadingService initialDataFileLoadingService;
    private final FileLoadingService initialFeeFileLoadingService;

    public static void main(String[] args) {
        SpringApplication.run(PackageDeliveryApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Application started with command-line arguments: {} . \n To kill this application, type \"quit\".", Arrays.toString(args));

        if (args.length > 0) {
            initialDataFileLoadingService.fileLoading(args[0]);
            if (args.length > 1) {
                initialFeeFileLoadingService.fileLoading(args[1]);
            }
        }
    }
}
