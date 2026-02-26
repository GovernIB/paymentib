// Document JavaScript

// MISSATGE

//let imc_missatge;


// missatge

function appMissatge(opcions) {

	var dades_basiques = {
			tipus: ""
			,estat: ""
			,titol: ""
			,text: ""
			,accio: false
			,siNoEs: false
			,enfocaEn: false
			,botonera: false
			,alMostrar: function() {}
			,alAmagar: function() {}
			,alAcceptar: function() {}
			,alCancelar: function() {}
			,alTancar: function() {}
		};

	var dades = Object.assign(dades_basiques, opcions);

	var tipus = dades.tipus
		,estat = dades.estat
		,titol_txt = dades.titol
		,text_txt = dades.text
		,accio = dades.accio
		,siNoEs = dades.siNoEs
		,enfocaEn = dades.enfocaEn
		,botonera = dades.botonera
		,alMostrar = dades.alMostrar
		,alAmagar = dades.alAmagar
		,alAcceptar = dades.alAcceptar
		,alCancelar = dades.alCancelar
		,alTancar = dades.alTancar;

	var amaga = function(bt) {

			alAmagar();

			imc_missatge
				.setAttribute("aria-hidden", "true");

			appPopupTabula({ element: imc_missatge, accio: "finalitza" });

			setTimeout(
				function() {

					if (enfocaEn) {

						enfocaEn
							.focus();

					}

				}
				,100
			);

		}
		,mostra = function() {

			alMostrar();

			imc_missatge
				.setAttribute("aria-hidden", "false");

			appPopupTabula({ element: imc_missatge });

		}
		,accepta = function(e) {

			alAcceptar();

			if (tipus === "informa" || tipus === "alerta") {
				
				amaga();

			}

		}
		,cancela = function() {

			alCancelar();

			amaga();

		}
		,tanca = function() {

			alTancar();

			amaga();

		}
		,botoneraPersonalitzada = function() {

			const botonera_html = `<div class="imc--botonera-personalitzada"></div>`;

			imc_missatge
				.setAttribute("data-botonera-personalitzada", "");

			var contingut_ = imc_missatge.querySelector(".imc-mi--con");

			contingut_
				.insertAdjacentHTML('beforeend', botonera_html);

			var bt_personalitzada_ = imc_missatge.querySelector(".imc--botonera-personalitzada");
			
			botonera
                .forEach(
                    (bt) => {

						var bt_text = bt.text
							,bt_accio = bt.accio
							,bt_class = bt.class
							,bt_funcio = bt.funcio;

						const bt_html = `<button type="button" class="${bt_class}" data-accio="${bt_accio}" data-tabula="si"><span>${bt_text}</span></button>`;

						bt_personalitzada_
							.insertAdjacentHTML('beforeend', bt_html);

                    }
                );

		}
		,inicia = function() {

			if (siNoEs && siNoEs !== "") {

				var siNoEs_ops = siNoEs.split(",").map(item => item.trim())
					,esEstat = false;

				if (siNoEs_ops.length) {

					siNoEs_ops
						.forEach(
							(op) => {
								if (imc_missatge.getAttribute("data-estat") === op) {
									esEstat = true;
								}
							}
						);

				}

				if (esEstat) {
					return;
				}

			}

			if (accio === "amaga") {

				amaga();
				return;

			}

			// botonera

			var botonera_clonada_ = imc_missatge.querySelector(".imc--botonera").cloneNode(true);

			imc_missatge
				.querySelector(".imc--botonera")
					.replaceWith( botonera_clonada_ );

			// títol

			imc_missatge
				.querySelector("h2 span")
					.textContent = titol_txt;

			// text HTML

			imc_missatge
				.querySelector(".imc--info")
					.innerHTML = "";

			if (text_txt) {

				text_txt = text_txt.replace("script", "");

				imc_missatge
					.querySelector(".imc--info")
						.innerHTML = text_txt;

			}

			// botonera personalitzada?

			var bt_pers_ = imc_missatge.querySelectorAll(".imc--botonera-personalitzada");

			if (bt_pers_.length) {
				
				imc_missatge
					.querySelector(".imc--botonera-personalitzada")
						.remove();

			}

			imc_missatge
				.removeAttribute("data-botonera-personalitzada");

			if (botonera) {
				botoneraPersonalitzada();
			}

			// dades del missatge

			imc_missatge
				.setAttribute("data-tipus", tipus);

			imc_missatge
				.setAttribute("data-estat", estat);

			// events

			EventManager
				.off(imc_missatge);

			EventManager
        		.on(imc_missatge, 'click.appMissatge', 'button[data-accio=dacord]', tanca);

			EventManager
        		.on(imc_missatge, 'click.appMissatge', 'button[data-accio=accepta]', accepta);

			EventManager
        		.on(imc_missatge, 'click.appMissatge', 'button[data-accio=cancela]', cancela);

			// mostra

			mostra();

		};

	// inicia

	inicia();

}
