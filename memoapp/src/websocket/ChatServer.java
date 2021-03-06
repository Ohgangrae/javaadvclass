package websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


// @ServerEndpoint 애너테이션으로 웹소켓 서버를 지정한다.
// ws://호스트:포트번호/ChatingServer
@ServerEndpoint("/ChatingServer")
public class ChatServer {
	
	
	//새로 접속한 클라이언트의 세션을 지정하는 컬렉션을 생성한다.
	//Collections 클래스의 synchronizedSet() 매서드는 안전하게 Set컬랙션을 생성해준다.
	//여러 클라이언트가 동시에 접속해도 문제가 발생하지 않게 해준다.
   private static Set<Session> clients
      =Collections.synchronizedSet(new HashSet<Session>());
   
   
   
   //클라이언트가 접속했을때 실행할 매서드를 정의한다. 이 매서드에서는 단순하게 clients 컬렉션에
   //세션을 추가하게 된다.
   @OnOpen   //클라이언트가 접속시 실행
   public void onOpen(Session session) {
      clients.add(session);  //세션 추가
      System.out.println("웹 소켓 연결:" + session.getId());
   }
   
   
   
   
   //클라이언트로부터 메세지를 받았을때 실행되는 매서드를 정의한다. 클라이언트가 보낸 메세지와
   //클라이언트의 세션이 매개변수로 넘어온다.
   //모든 클라이언트들에게 전송한다. 단 메세지 작성자는 제외시킨다.
   @OnMessage //메시지를 받으면 실행
   public void onMessage(String message, Session session) 
         throws IOException {
      System.out.println("메시지 전송 : " + session.getId() + ":" + message);
      synchronized(clients) {
         for (Session client : clients) { //모든 클라이언트들에게 메세지 전달 반복
            if (!client.equals(session)) { //메세지 작성자는 제외
               client.getBasicRemote().sendText(message);
            }
         }
      }
   }
   
   
   
   //클라이언트가 접속을 종료했을대 실행되는 매서드를 정의한다. clients에서 해당 세션을 삭제한다.
   @OnClose //클라이언트와의 연결이 끊기면 실행 
   public void onClose(Session session) {
      clients.remove(session);
      System.out.println("웹소켓 종료 :" + session.getId());
   }
   
   
   
   
   //에러가 발생했을때 실행될 매서드를 정의한다.
   @OnError //에러가 발생할때 실행
   public void onError(Throwable e) {
      System.out.println("에러 발생");
      e.printStackTrace();
   }
   
   
   
}