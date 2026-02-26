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
				<h1><span><spring:message code="pagoExterno.titulo"/></span></h1>
				<p><spring:message code="pagoExterno.texto1"/></p>
				<p><spring:message code="pagoExterno.texto2"/></p>
			</header>


			<div class="imc--atib">

				<div class="imc--dades">

					<h2><span><spring:message code="pagoExterno.pagoRequerido"/></span></h2>

					<div class="imc--els">

						<div class="imc--el">
							<div class="imc--etiqueta">
								<spring:message code="pagoExterno.nifSujetoPasivo"/>
							</div>
							<div class="imc--control">
								<p>${datosPago.sujetoPasivoNif}</p>
							</div>
						</div>

						<div class="imc--el">
							<div class="imc--etiqueta">
								<spring:message code="pagoExterno.nombreSujetoPasivo"/>
							</div>
							<div class="imc--control">
								<p>${datosPago.sujetoPasivoNombre}</p>
							</div>
						</div>

						<!-- Solo tenemos id tasa, asi que la ponemos junto con el concepto
						<div class="imc--el imc--el-taxa">
							<div class="imc--etiqueta">
								<spring:message code="pagoExterno.tasa"/>
							</div>
							<div class="imc--control">
								<p>${datosPago.tasaId}</p>
							</div>
						</div>
						-->

						<div class="imc--el imc--el-concepte">
							<div class="imc--etiqueta">
								<spring:message code="pagoExterno.concepto"/>
							</div>
							<div class="imc--control">
								<p>${datosPago.tasaId} - ${datosPago.concepto}</p>
							</div>
						</div>

						<div class="imc--el imc--el-import">
							<div class="imc--etiqueta">
								<spring:message code="pagoExterno.importe"/>
							</div>
							<div class="imc--control">
								<p><strong><fmt:formatNumber value="${datosPago.importe / 100.0}" type="number" minFractionDigits="2" maxFractionDigits="2"/> €</strong></p>
							</div>
						</div>

					</div>

				</div>

				<div class="imc--form">

					<h2><span><spring:message code="pagoExterno.datosVerificacionPago"/></span></h2>

					<div class="imc--els">

						<div class="imc--el">
							<div class="imc--etiqueta">
								<label for="imc--f-atib-localitzador"><span><spring:message code="pagoExterno.localizadorPago"/></span></label>
							</div>
							<div class="imc--control">
								<input type="text" id="imc--f-atib-localitzador" name="imc--f-atib-localitzador" maxlength="50">
							</div>
						</div>

						<div class="imc--el">
							<div class="imc--etiqueta">
								<label for="imc--f-atib-data"><span><spring:message code="pagoExterno.fechaPago"/></span></label>
							</div>
							<div class="imc--control">
								<input type="date" id="imc--f-atib-data" name="imc--f-atib-data">
							</div>
						</div>

					</div>

					<div class="imc--botonera">
						<button type="button" data-accio="atib-valida" data-tabula="si"><span><spring:message code="pagoExterno.botonValidarPago"/></span></button>
					</div>

				</div>


			</div>


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

</body>