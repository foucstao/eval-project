let modules = {};
const files = import.meta.globEager('./modules/app/index.js');
for (const key in files) {
    const fileName = key.split("/")[2];
    modules[fileName] = files[key].default;
}
import { createStore } from "vuex";
import getters from "./getters";
export default createStore({
    getters,
    modules,
});
