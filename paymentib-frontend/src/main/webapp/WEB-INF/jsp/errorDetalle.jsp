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
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>GOIB</title>

	<!--[if IE]><link rel="shortcut icon" href="imgs/favicon/favicon.ico" type="image/x-icon" /><![endif]-->
	<link rel="apple-touch-icon-precomposed" href="imgs/favicon/favicon-apple.png" />
	<link rel="icon" href="imgs/favicon/favicon.png" />

	<link href="estilos/imc-loginib.css" rel="stylesheet" type="text/css"/>

</head>
<body>

	<!-- contenidor -->
	<div id="imc-contenidor" class="imc-contenidor">

		<div class="imc-contingut" id="imc-contingut">
			<div class="imc--c">

				<h1><span><fmt:message key="atencion"/></span></h1>
				<span>${mensaje}</span>

			</div>
		</div>

	</div>
	<!-- /contenidor -->
</body>
</html>