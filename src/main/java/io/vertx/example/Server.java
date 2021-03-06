package io.vertx.example;

import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.PfxOptions;
import io.vertx.ext.web.RoutingContext;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertxbeans.rxjava.ContextRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.MINUTES;

@Component
public class Server {

    public static final int PORT = 8090;
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    @Resource
    private Vertx vertx;
    @Resource
    private ContextRunner contextRunner;
    @Autowired
    private HttpRequestHandler httpRequestHandler;
    @Autowired
    private VertxOptions vertxOptions;

    @PostConstruct
    public void init() throws InterruptedException, ExecutionException, TimeoutException {
        LOGGER.info("Creating {} instances of HttpServer", vertxOptions.getEventLoopPoolSize());
        contextRunner.executeBlocking(vertxOptions.getEventLoopPoolSize(),
                () -> createHttpServer().buffer(2), 1, MINUTES);
    }

    private Observable<HttpServer> createHttpServer() {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create()).failureHandler(context -> context.response().end());
        router.get("/policies/device").handler(httpRequestHandler::getDevicePolicies);
        router.get("/policies/account").handler(httpRequestHandler::getAccountPolicies);

        router.route().failureHandler(context -> {
            LOGGER.error("Error handling request {}", context.request().uri(), ((RoutingContext) context.getDelegate()).failure());
        });
        HttpServerOptions options = createHttpServerOptions();
        return vertx.createHttpServer(options).requestHandler(router::accept).listenObservable()
                .doOnCompleted(() -> LOGGER.info("Listening on port {}", PORT));
    }

    private HttpServerOptions createHttpServerOptions() {
        return new HttpServerOptions().setSsl(true).setPfxKeyCertOptions(
                new PfxOptions().setPath("ssl-certs/ssl-keystore.pfx").setPassword("111111"))
                .setPort(PORT).setCompressionSupported(true);
    }
}
