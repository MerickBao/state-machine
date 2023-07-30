package com.example.demo.service;

import com.example.demo.domain.ActionEntity;
import com.example.demo.domain.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: merickbao
 * @Created_Time: 2023-07-30 17:30
 * @Description:
 */

@Component
@ServerEndpoint("/websocket")
public class WebsocketServer {

    private static CopyOnWriteArrayList<Session> sessions = new CopyOnWriteArrayList<>();

    @OnOpen
    public void opOpen(Session session) {
        // 当有新的连接打开时，将新的 Session 添加到 sessions 列表中
        sessions.add(session);
        System.out.println("New session opened: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 收到客户端发送的消息时，可以在这里进行处理
        System.out.println("Received message from " + session.getId() + ": " + message);
    }

    @OnClose
    public void onClose(Session session) {
        // 当连接关闭时，从 sessions 列表中移除对应的 Session
        sessions.remove(session);
        System.out.println("Session closed: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // 处理发生的错误
        System.err.println("Error occurred in session " + session.getId() + ": " + throwable.getMessage());
    }

    public static void sendMessageToAll(String message) {
        // 向所有连接的客户端发送消息
        for (Session session : sessions) {
            try {
                ActionEntity actionEntity = new ActionEntity();
                actionEntity.setId(1);
                actionEntity.setUrl("222");
                actionEntity.setNodeId(3);
                session.getBasicRemote().sendText(JsonResponse.success(actionEntity.toString()).toString());
            } catch (IOException e) {
                System.err.println("Error while sending message to session " + session.getId() + ": " + e.getMessage());
            }
        }
    }
}
