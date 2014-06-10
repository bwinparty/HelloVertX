/*
 * HelloVertX - Vert.X Template Project
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package net.zyclonite.vertx;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

/**
 * Created by zyclonite on 28/05/14.
 */
public class HttpVerticle extends Verticle {
    private int reqCounter = 0;

    @Override
    public void start() {
        final Logger logger = container.logger();
        final HttpServer server = vertx.createHttpServer();
        final RouteMatcher routeMatcher = new RouteMatcher();
        routeMatcher.get("/", new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest req) {
                req.response().putHeader("content-type", "text/plain");
                req.response().setStatusCode(200);
                req.response().end("your requested resource was index");
                logger.info("got web request on index");
                sendRequestEvent("resource index");
            }
        });
        routeMatcher.get("/:resource", new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest req) {
                final String resource = req.params().get("resource");
                req.response().putHeader("content-type", "text/plain");
                req.response().setStatusCode(200);
                req.response().end("your requested resource was " + resource);
                logger.info("got web request on "+resource);
                sendRequestEvent("resource "+resource);
            }
        });
        routeMatcher.noMatch(new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest req) {
                req.response().end("not found");
                logger.info("resource for web request not found");
            }
        });
        server.requestHandler(routeMatcher);
        server.listen(8080);
    }

    private void sendRequestEvent(final String message) {
        reqCounter++;
        final EventBus eb = vertx.eventBus();

        final JsonObject obj = new JsonObject().putString("message", message)
            .putNumber("id", reqCounter);

        eb.publish("requestevents", obj);
    }
}
