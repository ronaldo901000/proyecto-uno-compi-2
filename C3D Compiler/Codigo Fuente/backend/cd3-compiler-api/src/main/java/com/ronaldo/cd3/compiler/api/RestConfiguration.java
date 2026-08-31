package com.ronaldo.cd3.compiler.api;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */
@ApplicationPath("/api/v1")
public class RestConfiguration extends ResourceConfig {
    
}
