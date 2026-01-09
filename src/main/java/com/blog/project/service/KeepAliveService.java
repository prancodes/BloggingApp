package com.blog.project.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Profile("prod") // 1. Only loads in Production (requires spring.profiles.active=prod)
public class KeepAliveService {

    private static final Logger logger = LoggerFactory.getLogger(KeepAliveService.class);
    private final RestClient restClient;
    private final String appUrl;

    // Flag to ensure we log the missing URL warning only once
    private boolean hasLoggedMissingUrl = false;

    public KeepAliveService(@Value("${app.public.url:}") String appUrl) {
        this.appUrl = appUrl;
        // Build the RestClient manually to avoid dependency injection issues
        this.restClient = RestClient.builder().build();
    }

    // Runs every 45 seconds to keep the Render instance awake
    @Scheduled(fixedRate = 45000)
    public void pingSelf() {
        // Assign field to a local variable for thread-safe null analysis
        final String targetUrl = this.appUrl;

        // Validation: Check if the URL is valid
        if (targetUrl == null || targetUrl.isEmpty() || targetUrl.contains("localhost")) {
            if (!hasLoggedMissingUrl) {
                logger.warn("⚠️ KeepAlive: Skipping ping (URL is localhost or empty). Set 'app.public.url' in your environment.");
                hasLoggedMissingUrl = true; // Mark as logged so it doesn't repeat
            }
            return;
        }

        try {
            // UPDATED: Point to the Spring Boot Actuator endpoint
            // This endpoint is lightweight and configured to NOT touch the DB
            String healthUrl = targetUrl.endsWith("/") ? targetUrl + "actuator/health" : targetUrl + "/actuator/health";

            restClient.get()
                    .uri(healthUrl)
                    .retrieve()
                    .toBodilessEntity(); // Discard body, we just need the 200 OK status
            
            // logger.info("✅ Ping successful to {}", healthUrl); 
        } catch (Exception e) {
            logger.error("❌ KeepAlive Ping Failed: {}", e.getMessage());
        }
    }
}