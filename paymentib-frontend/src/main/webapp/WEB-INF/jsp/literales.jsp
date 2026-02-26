<%@ page isELIgnored="false" %>
<%@page contentType="application/x-javascript" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="entry" items="${literales}">
    var <c:out value="${entry.key}"/> = "<c:out value="${entry.value}" escapeXml='false' />";
</c:forEach>