<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!doctype html>
<html lang="<c:out value="${sesionHttp.idioma}"/>">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>GOIB</title>

	<!--[if IE]><link rel="shortcut icon" href="imgs/favicon/favicon.ico" type="image/x-icon" /><![endif]-->
	<link rel="apple-touch-icon-precomposed" href="imgs/favicon/favicon-apple.png" />
	<link rel="icon" href="imgs/favicon/favicon.png" />

	<!-- css -->

	<link rel="stylesheet" media="screen" href="estils/imc-pib--app.css" />

</head>

<body>

<!-- contenidor -->

<div class="imc--contenidor">

	<div class="imc--contingut imc--error">
		<div class="imc--c">

			<header>
				<h1><span><fmt:message key="atencion"/></span></h1>
				<p><fmt:message key="${mensaje}"/></p>
			</header>

		</div>
	</div>

</div>


</body>
</html>
