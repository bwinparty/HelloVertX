/*
 * HelloVertX - Vert.X Template Project
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package net.zyclonite.vertx;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Future;
import org.vertx.java.platform.Verticle;

/**
 * Created by zyclonite on 28/05/14.
 */
public class ApplicationVerticle extends Verticle {

    @Override
    public void start(final Future<Void> startedResult) {
        container.deployVerticle("net.zyclonite.vertx.NetSocketVerticle", new AsyncResultHandler<String>() {
            public void handle(AsyncResult<String> deployResult) {
                if (deployResult.succeeded()) {
                    startedResult.setResult(null);
                } else {
                    startedResult.setFailure(deployResult.cause());
                }
            }
        });
        container.deployVerticle("net.zyclonite.vertx.HttpVerticle");
        container.deployVerticle("net.zyclonite.vertx.WebSocketVerticle");
        container.deployVerticle("JavaScriptVerticle.js");
        container.deployVerticle("ClosureVerticle.clj");
    }

    @Override
    public void stop() {
        super.stop();
    }
}
