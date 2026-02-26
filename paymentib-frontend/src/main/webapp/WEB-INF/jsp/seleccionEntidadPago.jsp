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
	<link rel="stylesheet" media="screen" href="estils/imc--ui-missatge.css" />

	<!-- js -->
	<script src="literales.html"></script>

	<script src="js/imc-pib--config.js"></script>

	<script src="js/imc--ui-events.js"></script>
	<script src="js/imc--ui-missatge.js"></script>
	<script src="js/imc--ui-popup-tabula.js"></script>

	<script src="js/imc-pib--app.js"></script>

</head>

<body>

<!-- contenidor -->

<div class="imc--contenidor">

	<div class="imc--contingut">
		<div class="imc--c">

			<header>
				<h1><span><spring:message code="seleccionEntidadPago.titulo" /></span></h1>
			</header>


			<!-- botons -->

			<ul>
				<c:forEach items="${datos.entidadesPago}" var="e">
				<li>
					<button type="button" data-href="redirigirPagoPasarela.html?entidadPagoId=${e.codigo}" style="background-image: url('${e.logo}');" data-accio="entitat-inicia">
						<strong>${e.titulo}</strong>
						<p>${e.descripcion}</p>
					</button>
				</li>
				</c:forEach>
			</ul>

		</div>
	</div>

</div>


<!-- missatge -->

<div id="imc--missatge" class="imc--missatge" data-estat="" data-tipus="" role="alertdialog" aria-modal="true" aria-labelledby="imc--mi-titol" aria-describedby="imc--mi-info" aria-hidden="true" tabindex="0">
	<div class="imc--contingut imc-mi--con">

		<header>
			<h2 id="imc--mi-titol"><span></span></h2>
		</header>

		<div id="imc--mi-info" class="imc--info"></div>

		<div class="imc--botonera">
			<button type="button" class="imc--bt-terciari" data-accio="cancela" data-tabula="si"><span><spring:message code="boton.cancela"/></span></button>
			<button type="button" data-accio="dacord" data-tabula="si"><span><spring:message code="boton.ok"/></span></button>
			<button type="button" data-accio="accepta" data-tabula="si"><span><spring:message code="boton.acepta"/></span></button>
		</div>

	</div>
</div>

<div style="display:none">
	<p>E:${datos.entorno}</p>
	<p>V:${datos.version}</p>
	<p>C:${datos.commit}</p>
</div>

</body>