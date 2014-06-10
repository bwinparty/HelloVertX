/*
 * HelloVertX - Vert.X Template Project
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
var vertx = require('vertx');
var console = require('vertx/console');

var counter = 0;
var eb = vertx.eventBus;

vertx.createNetServer().connectHandler(function(sock) {
    new vertx.Pump(sock, sock).start();
}).listen(1235);

var timerID = vertx.setPeriodic(5000, function(timerID) {
    var myObj = {
        message: 'js verticle timed event',
        id: counter++
    }
    eb.publish('timedevents', myObj);
    console.log("javascript verticle timer fired");
});

function vertxStop() {
   console.log('Verticle has been undeployed');
}