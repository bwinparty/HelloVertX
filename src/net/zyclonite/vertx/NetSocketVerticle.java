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
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.core.net.NetServer;
import org.vertx.java.core.net.NetSocket;
import org.vertx.java.platform.Verticle;

/**
 * Created by zyclonite on 27/05/14.
 */
public class NetSocketVerticle extends Verticle {

    @Override
    public void start() {
        final Logger logger = container.logger();
        final NetServer server = vertx.createNetServer();
        final EventBus eb = vertx.eventBus();
        server.connectHandler(new Handler<NetSocket>() {
            public void handle(final NetSocket socket) {
                socket.closeHandler(new Handler<Void>() {
                    @Override
                    public void handle(Void aVoid) {
                        logger.info("tcp client disconnected");
                    }
                });
                //could use the autoregistered handler too (socket.writeHandlerID())
                eb.registerHandler("requestevents", new Handler<Message<JsonObject>>() {
                    public void handle(final Message<JsonObject> message) {
                        socket.write("got request: "+message.body().getString("message")+" ("+message.body().getInteger("id")+")\n");
                    }
                });
                eb.registerHandler("timedevents", new Handler<Message<JsonObject>>() {
                    public void handle(final Message<JsonObject> message) {
                        socket.write("got timer event: "+message.body().getString("message")+" ("+message.body().getInteger("id")+")\n");
                    }
                });
                logger.info("tcp client connected");
            }
        });
        server.listen(1234);
    }
}
