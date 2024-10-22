import {
    TOGGLE_SLIDER,
    SET_TOKEN,
    SET_UNAME,
    SET_MENU_LIST,
    SET_PERMISSION_LIST,
} from "./type.js";
export default {
    namespaced: true,
    state: {
        slider: {
            opened: JSON.parse(sessionStorage.getItem("opened")),
        },
        token: sessionStorage.getItem("token") || "",
        menuList: null,
        permissionList: [],
        uname: sessionStorage.getItem("uname") || "",
        uid: sessionStorage.getItem("uid") || 0
    },
    mutations: {
        [TOGGLE_SLIDER](state) {
            state.slider.opened = !state.slider.opened;
            sessionStorage.setItem(
                "opened",
                JSON.stringify(state.slider.opened)
            );
        },
        [SET_TOKEN](state, token) {
            state.token = token;
            sessionStorage.setItem("token", state.token);
        },
        [SET_UNAME](state, user) {
            state.uname = user.name;
            state.uid = user.id;
            sessionStorage.setItem("uname", state.uname);
            sessionStorage.setItem("uid", state.uid);
        },
        [SET_MENU_LIST](state, menuList) {
            state.menuList = menuList;
        },
        [SET_PERMISSION_LIST](state, permissionList) {
            state.permissionList = permissionList;
        },
    },
    actions: {
        [TOGGLE_SLIDER]({ commit }) {
            commit(TOGGLE_SLIDER);
        },
        [SET_TOKEN]({ commit }, token) {
            commit(SET_TOKEN, token);
        },
        [SET_UNAME]({ commit }, uname) {
            commit(SET_UNAME, uname);
        },
        [SET_MENU_LIST]({ commit }, menuList) {
            commit(SET_MENU_LIST, menuList);
        },
        [SET_PERMISSION_LIST]({ commit }, menuList) {
            let allMenus = menuList.filter((item) => item.type == 1);
            let permissionList = [];
            allMenus.forEach((item) => {
                if (item.perms !== null && item.perms !== '') permissionList.push(item.perms);
                if (item.children && item.children.length > 0) {
                    item.children.forEach((menu) => {
                        if (menu.perms !== null && menu.perms !== '') permissionList.push(menu.perms);
                    });
                }
            });
            commit(SET_PERMISSION_LIST, permissionList);
        },
    },
};
