package io.vertx.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.JsonObject;
import io.vertx.example.dto.AccountPolicy;
import io.vertx.example.dto.DevicePolicy;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
public class HttpRequestHandler {

    public void getDevicePolicies(final RoutingContext context) {
        try {
            context.response().end(new JsonObject(new ObjectMapper().writeValueAsString(
                    new DevicePolicy().setDeviceTpe("apple").setEasDeviceId("Raghu"))).encodePrettily());
        } catch (JsonProcessingException e) {
            context.response().setStatusCode(500).end();
        }
    }

    public void getAccountPolicies(final RoutingContext context) {
        try {
            context.response().end(new JsonObject(new ObjectMapper().writeValueAsString(
                    new AccountPolicy().setUserName("Doug").setAllowSync(true))).encodePrettily());
        } catch (JsonProcessingException e) {
            context.response().setStatusCode(500).end();
        }
    }

    public void postActiveSyncPayload(final RoutingContext context) {
        context.response().end("Request processed successfully! Posted request body ::: \n\n\n " + context.getBodyAsString());
    }
}