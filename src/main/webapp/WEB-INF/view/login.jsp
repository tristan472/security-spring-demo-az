<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="resources/css/style.css" />
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>Ma page de login custom</h3>
    <form:form action="${pageContext.request.contextPath}/authenticateLogin" method="POST">
    
    <c:if test="${param.error != null}">
    	<i class="failed">Nom d'utilisiateur / mot de passe invalide.</i>
    </c:if>
    
    <!-- checker le logout -->
	<c:if test="${param.logout != null}">
	    <i class="logout">Vous avez été déconnecté.</i>
	</c:if>
    
        <p>
            Nom utilisateur : <input type="text" name="username" />
        </p>
        <p>
            Mot de passe : <input type="text" name="password" />
        </p>
        <input type="submit" value="Login" />
    </form:form>
</body>
</html>