<%request.setCharacterEncoding("UTF-8");%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <jsp:useBean id="user" class = "jdbc.UserDTO" scope="page" />
 <jsp:setProperty property = "*" name = "user"/>
    
    
 <%-- <%
 /* request.setCharacterEncoding("UTF-8");
 
 String uid = request.getParameter("uid");
 String upw = request.getParameter("upw");
 String uname = request.getParameter("uname");
 String ugender = request.getParameter("ugender");
 String ubirth = request.getParameter("ubirth");
 String uemail = request.getParameter("uemail");
 String uphone = request.getParameter("uphone");
 String uaddr= request.getParameter("uaddr"); */
 
 
 UserDAO dao = new UserDAO();
 int result = dao.insert(uid, upw, uname, ugender, ubirth, uemail, uphone, uaddr);
 
 if(result == 1) {
	 response.sendRedirect("/user/login.jsp");
 }else{
	 response.sendRedirect("/user/useradd.jsp");
 }
 %> --%>
 
 아이디 : <jsp:getProperty property="uid" name="user" /> 
 암   호 : <jsp:getProperty property="upw" name="user" />
 이   름 : <jsp:getProperty property="uname" name="user" />