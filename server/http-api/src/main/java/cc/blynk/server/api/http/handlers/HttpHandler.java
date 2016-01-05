package cc.blynk.server.api.http.handlers;

import cc.blynk.server.handlers.http.BaseHttpAPIHandler;
import cc.blynk.server.handlers.http.logic.FileLogic;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * The Blynk Project.
 * Created by Dmitriy Dumanskiy.
 * Created on 01.01.16.
 */
public class HttpHandler extends BaseHttpAPIHandler {

    private final FileLogic fileLogic = new FileLogic();

    @Override
    public void process(ChannelHandlerContext ctx, HttpRequest request) {
        //a bit ugly code but it is ok for now. 2 branches. 1 fro static files, second for normal http api
        if (request.getUri().equals("/favicon.ico")) {
            request.setUri("/admin/static/favicon.ico");
            try {
                fileLogic.channelRead(ctx, request);
            } catch (Exception e) {
                log.error("Error handling static file.", e);
            }
        } else {
            super.process(ctx, request);
        }
    }
}