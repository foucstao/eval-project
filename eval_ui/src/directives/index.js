
export default {
    install: (app, { router, store }) => {
        const files = import.meta.globEager('./modules/*.js');
        for (const key in files) {
            let name = key.replace("./modules/", "").replace(".js", "");
            let method = files[key].default;
            app.directive(name, (el, binding) =>
                method(el, binding, app, router, store)
            );
        }
    },
};
