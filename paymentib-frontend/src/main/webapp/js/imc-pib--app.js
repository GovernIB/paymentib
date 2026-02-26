// Document JavaScript


// elements globals

let imc_body
    ,imc_missatge;


// payment IB

function appPaymentIB() {

	var valida = function() {

            var bt_ = this
                ,form_ = bt_.closest(".imc--form")
                ,localitzador_ = form_.querySelector("#imc--f-atib-localitzador")
                ,data_ = form_.querySelector("#imc--f-atib-data");
            
            // revisem

            if ( localitzador_.value.trim() === "" || data_.value.trim() === "" ) {

                appMissatge({ tipus: "informacio", estat: "error", titol: txt_verificaErrorTitol, text: txt_verificaErrorText, alTancar: function() { form_.querySelector("#imc--f-atib-localitzador").focus(); } });
                return;

            }

            // enviem

            appMissatge({ tipus: "execucio", estat: "executant", titol: txt_verificaEnviantTitol, alMostrar: function() { validant({ localitzador: localitzador_.value.trim(), data: data_.value.trim() }); } });
        
        }
        ,validant = function(pagament_dades) {

            const
                json_pag = APP_PIB_VALIDA
                ,json_dades = {
                    localizador: pagament_dades.localitzador
                    ,fecha: pagament_dades.data
                }
                ,json_opcions = {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify( json_dades )
                };

            fetch(json_pag, json_opcions)
                .then(response => response.json())
                .then(data => {

                    var pagament_resposta = data;

                    validat( pagament_resposta );

                })
                .catch(error => {

                    console
                        .error('Error en la comunicació amb el servei de validació: ', error);

                    appMissatge({ tipus: "informacio", estat: "error", titol: txt_enviamentErrorTitol, text: txt_enviamentErrorText });

                });

        }
        ,validat = function(pagament_resposta) {

            var estat_ = (pagament_resposta.estado === "OK") ? "correcte" : "error"
                ,url_fn = function() {};

            if (pagament_resposta.url && pagament_resposta.url.trim() !== "" && pagament_resposta.url !== null) {

                url_fn = function() {
                            
                            document
                                .querySelector('.imc--contenidor')
                                    .setAttribute('data-url', 'true');

                            document
                                .location = pagament_resposta.url;
                            
                        };

            }

            appMissatge({ tipus: "informacio", estat: estat_, titol: pagament_resposta.mensaje.titulo, text: pagament_resposta.mensaje.texto, alTancar: url_fn });
        
        }
		,entitat = function() {

            var bt_ = this
                ,bt_href = bt_.getAttribute("data-href");

            // enviem

            appMissatge({ tipus: "execucio", estat: "executant", titol: txt_carregaEntitatTitol, alMostrar: function() { document.location = bt_href; } });

		}
		,inicia = function() {

            // elements

            imc_body = document.querySelector('body');
            imc_missatge = document.querySelector('.imc--missatge');

            // events

            EventManager
                .off(imc_body, 'click.appPaymentIB');

            EventManager
                .on(imc_body, 'click.appPaymentIB', 'button[data-accio=atib-valida]', valida);

            EventManager
                .on(imc_body, 'click.appPaymentIB', 'button[data-accio=entitat-inicia]', entitat);

		};

	// inicia

	inicia();

}

// executem funció

document
	.addEventListener("DOMContentLoaded", appPaymentIB, false);
