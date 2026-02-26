const EventManager = (function() {
    
    const eventRegistry = new Map();

    function on(element, eventType, selector, handler, options = {}) {

        const [eventName, namespace = 'default'] = eventType.split('.');

        const eventKey = `${eventName}.${namespace}`;

        const eventHandler = function(e) {
            if (!selector) {
                return handler.call(element, e);
            }

            const target = e.target.closest(selector);
            if (target && element.contains(target)) {
                return handler.call(target, e);
            }
        };

        const handlerObj = {
            original: handler,
            delegated: eventHandler
        };

        if (!eventRegistry.has(element)) {
            eventRegistry.set(element, new Map());
        }
        const elementEvents = eventRegistry.get(element);
        if (!elementEvents.has(eventKey)) {
            elementEvents.set(eventKey, new Set());
        }
        elementEvents.get(eventKey).add(handlerObj);

        element.addEventListener(eventName, eventHandler, options);

        return () => off(element, eventType, handler);
    }

    function off(element, eventType, handler) {
        if (!eventRegistry.has(element)) return;

        const elementEvents = eventRegistry.get(element);

        if (!eventType) {
            elementEvents.forEach((handlers, key) => {
                const [eventName] = key.split('.');
                handlers.forEach(h => {
                    element.removeEventListener(eventName, h.delegated);
                });
            });
            eventRegistry.delete(element);
            return;
        }

        const [eventName, namespace] = eventType.split('.');
        const eventKey = namespace ? `${eventName}.${namespace}` : eventName;

        if (!elementEvents.has(eventKey)) return;

        const handlers = elementEvents.get(eventKey);

        if (!handler) {
            
            handlers.forEach(h => {
                element.removeEventListener(eventName, h.delegated);
            });
            elementEvents.delete(eventKey);
        } else {
            
            handlers.forEach(h => {
                if (h.original === handler) {
                    element.removeEventListener(eventName, h.delegated);
                    handlers.delete(h);
                }
            });
        }

        if (handlers.size === 0) {
            elementEvents.delete(eventKey);
        }
        if (elementEvents.size === 0) {
            eventRegistry.delete(element);
        }
    }

    return {
        on,
        off
    };
})();

/*

// Exemples:

// on

EventManager.on(document.querySelector('.btn'), 'click', () => console.log('Clicked!'));

EventManager.on(document.body, 'click.myApp', () => console.log('Body clicked!'));

EventManager.on(document.body, 'click', '.btn', (e) => console.log('Delegated click!'));

EventManager.on(window, 'scroll', () => console.log('Scrolling'), { passive: true });

// off

EventManager.off(document.body, 'click.myNamespace', handler);

EventManager.off(document.body, 'click.myNamespace');

EventManager.off(document.body);

*/