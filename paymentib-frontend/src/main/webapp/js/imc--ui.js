// UI inicial

var imc_body;


// when/then

function when(...promises) {

    if (promises.length === 0) {
        return Promise.resolve();
    }

    if (promises.length === 1) {
        
        const promise = promises[0];

        if (promise && typeof promise.then === 'function') {
            return promise;
        }

        return Promise.resolve(promise);

    }

    return Promise.all(
        promises.map(
            promise => {

                if (promise && typeof promise.then === 'function') {
                    return promise;
                }

                return Promise.resolve(promise);

            }
        )
    );

}


// load -> JSON

function loadJSON(url, dades = {}, possibleError = false) {
    return new Promise(resolve =>
        {	
            
            const
                json_pag = url
                ,json_dades = dades
                ,json_opcions = {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify( json_dades )
                };

            fetch(json_pag, json_opcions)
                .then(response => response.json())
                .then(data => {

                    var json_ = data
                        ,json_estat = json_.estado
                        ,json_missatge = json_.mensaje
                        ,json_url = json_.url
                        ,json_error = json_.detalleError;

                    // revisem URL si tenim que redirigir

                    var func_ = function() {};

                    if (json_url && json_url !== "") {
                        func_ = function() { document.location = json_url; }
                    }

                    // missatge atenció

                    if (json_estat === "WARNING") {

                        appMissatge({ tipus: "informacio", estat: "atencio", titol: json_missatge.titulo, text: json_missatge.texto, alTancar: func_  });

                    }

                    // missatges error i fatal

                    if (json_estat === "ERROR" || json_estat === "FATAL") {

                        console
                            .error(`Error a la cridada JSON (${url}). Estat -> (${json_estat}): ${json_error}`);

                        if (json_estat === "FATAL") {

                            document
                                .location = URL_ERROR_SERVIDOR;

                        }

                        if (json_estat === "ERROR") {

                            appMissatge({ tipus: "informacio", estat: "error", titol: json_missatge.titulo, text: json_missatge.texto, alTancar: func_ });

                        }

                        return false;

                    }

                    resolve(data);

                })
                .catch(error => {

                    if (possibleError === "fatal") {

                        document
                            .location = URL_ERROR_SERVIDOR;
                        
                    }

                    console
                        .error(`Error al carregar les dades JSON: ${url}`);

                });

        });
}


// load -> HTML

var loadedHTML = new Array()
    ,loadedHTML_codi = new Array();

function loadHTML(url) {
    return new Promise(resolve =>
        {	

            var posicio = loadedHTML.indexOf(url);

            if (posicio !== -1) {
                console.log(`HTML carregat anteriorment: ${url}`);
                resolve(loadedHTML_codi[posicio]);
                return;
            }

            fetch(url)
                .then(response => response.text())
                .then(data => {

                    loadedHTML.push(url);
                    loadedHTML_codi.push(data);

                    resolve(data);

                })
                .catch(error => {

                    console
                        .error(`Error al carregar el HTML: ${url}`);

                });

        });
}


// load -> CSS

var loadedCSS = new Set();

function loadCSS(url, options = {}) {
    return new Promise((resolve, reject) => {
        
        if (loadedCSS.has(url)) {
            console.log(`CSS carregat anteriorment: ${url}`);
            resolve();
            return;
        }

        const link = document.createElement('link');
        link.rel = 'stylesheet';
        link.type = 'text/css';
        link.href = url;
        
        if (options.id) link.id = options.id;
        if (options.media) link.media = options.media;

        link
            .onload = () => {
                loadedCSS.add(url);
                resolve();
            };

        link
            .onerror = () => {
                console.error(`Error al carregar CSS: ${url}`);
                reject(new Error(`Error al carregar CSS: ${url}`));
            };

        const target = options.target || document.head;

        target
            .appendChild(link);

    });
}


// load -> JS

var loadedJS = new Set();

function loadJS(url) {
    return new Promise((resolve, reject) => {

        if (loadedJS.has(url)) {
            console.log(`JS carregat anteriorment: ${url}`);
            resolve();
            return;
        }

        const script = document.createElement('script');
        script.src = url;
        script.type = 'text/javascript';

        script
            .onload = () => {
                loadedJS.add(url);
                resolve();
            };

        script
            .onerror = () => {
                console.error(`Error al carrgar JS: ${url}`);
                reject(new Error(`Error al carrgar JS: ${url}`));
            };

        document
            .head
                .appendChild(script);

    });
}


// utilitats 

function trigger(el, eventType) {
	if (typeof eventType === 'string' && typeof el[eventType] === 'function') {
	  el[eventType]();
	} else {
	  const event =
		typeof eventType === 'string'
		  ? new Event(eventType, {bubbles: true})
		  : eventType;
	  el.dispatchEvent(event);
	}
}

function appUrlParametre(parametre, url = window.location.href) {
    try {
        const url_ = new URL(url);
        return url_.searchParams.get(parametre);
    } catch (error) {
        console.error('Error obtinguent URL paràmetre:', error);
        return null;
    }
}

function appCSS(el, styles) {
    for (var property in styles) {
        el.style[property] = styles[property];
    }
}


// Formateig fàcil de la mida d'un arxiu

function appMidaArxiuFormateja(bytes) {
    if (bytes === 0) return '0 Bytes';
    
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
}


// DOM inicia

function iniciaDOM() {

	imc_body = document.querySelector('body');

}

document
	.addEventListener("DOMContentLoaded", iniciaDOM, false);