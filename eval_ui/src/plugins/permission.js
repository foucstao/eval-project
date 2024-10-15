import { SET_MENU_LIST, SET_PERMISSION_LIST } from "@/store/modules/app/type";

import globalRoutes from "@/router/globalRoutes";
import mainRoutes from "@/router/mainRoutes";
import NProgress from "nprogress";
import request from '@/plugins/axios'

const modules = import.meta.glob('./../views/**/*.vue')
/**
 * 判断当前路由类型, global: 全局路由, main: 主入口路由
 * @param {*} route 当前路由
 */
function fnCurrentRouteType(route, globalRoutes = []) {
    let temp = [];
    for (let i = 0; i < globalRoutes.length; i++) {
        if (route.name === globalRoutes[i].name) {
            return "global";
        } else if (
            globalRoutes[i].children &&
            globalRoutes[i].children.length >= 1
        ) {
            temp = temp.concat(globalRoutes[i].children);
        }
    }
    return temp.length >= 1 ? fnCurrentRouteType(route, temp) : "main";
}

export default {
    install: (app, { router, store }) => {
        // let router = opt;
        router.beforeEach(async (to, from, next) => {
            const token = store.getters.token;
            if (
                router.options.isAddDynamicMenuRoutes ||
                fnCurrentRouteType(to, globalRoutes) === "global"
            ) {
                //* 1. 已经添加 or 全局路由, 直接访问
                if (to.meta.title) {
                    document.title = to.meta.title;
                }
                NProgress.start();
                next();
            } else {
                if (!token || !/\S/.test(token)) {
                    next({ name: "Login" });
                } else {
                    let data = await getRouters();
                    data = data.data
                    if (data) {
                        await fnAddDynamicMenuRoutes(data.list);
                        router.options.isAddDynamicMenuRoutes = true;
                        await store.dispatch(`app/${SET_MENU_LIST}`, data.list);
                        await store.dispatch(
                            `app/${SET_PERMISSION_LIST}`,
                            data.noTreelist
                        );
                        NProgress.start();
                        next({ ...to, replace: true });
                    } else {
                        next({ name: "Login" });
                    }
                }
            }
        });
        router.afterEach(() => {
            NProgress.done();
        });

        const getRouters = async () => {
            let menuList = [];
            await request
                .get(`api/v1/menu/getmenurole/${sessionStorage.uid}`)
                .then((response) => {
                    menuList = response
                })
                .catch((error) => {
                    console.log(error);
                });
            return menuList;
        }
        /**
         * 添加动态(菜单)路由
         * @param {*} menuList 菜单列表
         * @param {*} routes 递归创建的动态(菜单)路由
         */
        // eslint-disable-next-line no-unused-vars
        const fnAddDynamicMenuRoutes = async (menuList = [], routes = []) => {
            let temp = [];
            for (let i = 0; i < menuList.length; i++) {
                if (
                    menuList[i].type == 0 &&
                    menuList[i].children &&
                    menuList[i].children.length >= 1
                ) {
                    temp = temp.concat(menuList[i].children);
                } else if (menuList[i].type == 1) {
                    let route = {
                        path:
                            menuList[i].path.replace(/\//g, "-") +
                            `-${menuList[i].id}`,
                        component: null,
                        name:
                            menuList[i].path.replace(/\//g, "-"),
                        // meta: {
                        // }
                    };
                    const path = "views/" + menuList[i].path;
                    route["component"] = loadView(path);
                    routes.push(route);
                }
            }
            if (temp.length >= 1) {
                fnAddDynamicMenuRoutes(temp, routes);
            } else {
                mainRoutes.children = mainRoutes.children.concat(routes);
                // mainRoutes.children = routes;
                // console.log(
                //     "控制台打印--> ~ file: permission.js ~ line 127 ~ fnAddDynamicMenuRoutes ~ mainRoutes.children",
                //     mainRoutes.children
                // );
                await router.addRoute(mainRoutes);
                await router.addRoute({
                    path: "/:w+",
                    redirect: { name: "404" },
                });
            }
        };
        const loadView = (view) => {
            let res;
            for (const path in modules) {
                const dir = path.split('../')[1].split('.vue')[0];
                if (dir === view) {
                    res = () => modules[path]();
                }
            }
            return res;
        }
    },
};
