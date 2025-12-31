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

    // 2. Flag to track if we've already logged the warning (Stateful singleton)
    private boolean hasLoggedMissingUrl = false;

    // FIXED: Removed RestClient.Builder from arguments to avoid "No qualifying bean" error
    public KeepAliveService(@Value("${app.public.url:}") String appUrl) {
        this.appUrl = appUrl;
        // Manually build the client. Simple and robust.
        this.restClient = RestClient.builder().build();
    }

    // Runs every 45 seconds
    @Scheduled(fixedRate = 45000)
    public void pingSelf() {
        // Assign field to a local variable for thread-safe null analysis
        final String targetUrl = this.appUrl;
        // 3. Logic check: Is URL valid?
        if (targetUrl == null || targetUrl.isEmpty() || targetUrl.contains("localhost")) {
            // Only print warning once
            if (!hasLoggedMissingUrl) {
                logger.warn("⚠️ KeepAlive: Skipping ping (URL is localhost or empty). Set 'app.public.url' in your environment.");
                hasLoggedMissingUrl = true; // Mark as logged so it doesn't repeat
            }
            return;
        }

        try {
            // 4. RestClient implementation (Synchronous)
            restClient.get()
                    .uri(targetUrl)
                    .retrieve()
                    .toBodilessEntity(); // We don't care about the body, just the connection
            
            // Optional: logger.info("Ping successful"); 
        } catch (Exception e) {
            logger.error("❌ KeepAlive Ping Failed: {}", e.getMessage());
        }
    }
}