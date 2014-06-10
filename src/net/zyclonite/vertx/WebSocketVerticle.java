/*
 * HelloVertX - Vert.X Template Project
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package net.zyclonite.vertx;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.sockjs.SockJSServer;
import org.vertx.java.core.sockjs.SockJSSocket;
import org.vertx.java.platform.Verticle;

/**
 * Created by zyclonite on 28/05/14.
 */
public class WebSocketVerticle extends Verticle {
    @Override
    public void start() {
        final HttpServer httpServer = vertx.createHttpServer();
        final SockJSServer sockJSServer = vertx.createSockJSServer(httpServer);
        final JsonObject config = new JsonObject().putString("prefix", "/echo");
        sockJSServer.installApp(config, new Handler<SockJSSocket>() {
            public void handle(final SockJSSocket sock) {

            }
        });
        httpServer.listen(8080);
    }
}
