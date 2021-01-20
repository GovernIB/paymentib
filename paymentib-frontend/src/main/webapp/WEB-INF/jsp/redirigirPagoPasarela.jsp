<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<c:out value="${sesionHttp.idioma}"/>" lang="<c:out value="${sesionHttp.idioma}"/>">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<script type="text/javascript">
	<!--
	function redirigirPasarela() {
		document.getElementById('redirigirPasarela').submit();
	}
	-->
	</script>

</head>

<body onload="redirigirPasarela();">
	<form id="redirigirPasarela" action="<c:out value="${urlPasarela.url}"/>" method="post">
		<c:forEach items="${urlPasarela.parametersPost}" var="parameter">
	        <input type="hidden" id="<c:out value="${parameter.key}"/>" name="<c:out value="${parameter.key}"/>" value="<c:out value="${parameter.value}"/>"/>
	    </c:forEach>
	</form>
</body>
</html>