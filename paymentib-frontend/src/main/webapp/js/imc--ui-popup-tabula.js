// Document JavaScript

// missatge

function appPopupTabula(opcions) {

	var dades_basiques = {
            element: false
            ,accio: false
            ,enfocaEn: false
		};

	var dades = Object.assign(dades_basiques, opcions);

	var element = dades.element
        ,accio = dades.accio
		,enfocaEn = dades.enfocaEn
		,elems_tab = []
		,elems_tab_size = 0;

	var esEnfocable = function(enfocable_bt_) {

            var enfocable_bt_estil = window.getComputedStyle(enfocable_bt_);

            return (!enfocable_bt_.disabled && enfocable_bt_estil.display !== 'none' && enfocable_bt_estil.visibility !== 'hidden' && enfocable_bt_.offsetWidth > 0 && enfocable_bt_.offsetHeight > 0);

        },
        trobaEnfocable = function(bt_, llista_bt_, posicio) {

            var bt_index = llista_bt_.indexOf(bt_)
                ,llista_bt_size = llista_bt_.length;
            
            if (posicio === "seguent") {

                for (let i = bt_index + 1; i < llista_bt_size; i++) {
                    if ( esEnfocable(llista_bt_[i]) ) {
                        return llista_bt_[i];
                    }
                }

                for (let i = 0; i < bt_index; i++) {
                    if ( esEnfocable(llista_bt_[i]) ) {
                        return llista_bt_[i];
                    }
                }

            } else {

                for (let i = bt_index - 1; i >= 0; i--) {
                    if ( esEnfocable(llista_bt_[i]) ) {
                        return llista_bt_[i];
                    }
                }

                for (let i = llista_bt_size - 1; i > bt_index; i--) {
                    if ( esEnfocable(llista_bt_[i]) ) {
                        return llista_bt_[i];
                    }
                }

            }

            return null;

        }
        ,tabula = function(event) {

            var bt_ = event.target
                ,tecla = event.keyCode
                ,esShift = !!event.shiftKey
                ,aEnfocar = false;

            if ( esShift && tecla === 9 ) {

                event.preventDefault();

                aEnfocar = trobaEnfocable(bt_, elems_tab, "anterior");

            } else if ( !esShift && tecla === 9 ){
            
                event.preventDefault();

                aEnfocar = trobaEnfocable(bt_, elems_tab, "seguent");

            }

            if (aEnfocar) {

                aEnfocar
                    .focus();

            }

        }
        ,activa = function() {

            elems_tab = Array.from( element.querySelectorAll('[data-tabula="si"]') );

            elems_tab
                .splice(0, 0, element);

			elems_tab_size = elems_tab.length;

        }
        ,deseventua = function() {

            EventManager
                .off(element, 'keydown.appPopupTabulaKD');

        }
        ,eventua = function() {

            // deseventua

            deseventua();


            // eventua

            EventManager
                .on(element, 'keydown.appPopupTabulaKD', false, (event) => { tabula(event); });

        }
        ,enfoca = function() {

            // enfoquem en algun element?

            if (enfocaEn) {

                enfocaEn
                    .focus();

            } else {

                element
                    .focus();

            }

        }
        ,observa = function() {

            const obs_el_ = element
                ,obs_config = { childList: true };

            const obs_resposta = (mutationList, observador) => {
                for (const mutation of mutationList) {
                    if (mutation.type === "childList") {

                        observador
                            .disconnect();

                        prepara();

                        return;
                    }
                }
            };

            const observador = new MutationObserver(obs_resposta);

            observador
                .observe(obs_el_, obs_config);

        }
        ,prepara = function() {

            element
                .querySelectorAll("button, a, input:not([type=checkbox]):not([type=radio]), textarea, tr[data-id], select, label.imc--bt-radio")
                    .forEach(el => { el.setAttribute("data-tabula", "si"); });

            element
                .querySelectorAll("tr[data-id]")
                    .forEach(el => { el.setAttribute("tabindex", "0"); });

            // activa

            activa();
            eventua();
            enfoca();

            // observa

            observa();

        }
        ,inicia = function(bt) {

            if (accio === "finalitza") {

                deseventua();

                return;

            }

            elems_tab = [];
            elems_tab_size = 0;

            setTimeout(
                function() {

                    prepara();

                }
                ,100
            );

        };

    // inicia

	inicia();

}
