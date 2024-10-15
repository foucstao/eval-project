export default {
    path: "/",
    name: "CommonLayout",
    component: () => import("@/layout/CommonLayout.vue"),
    redirect: { name: "Home" },
    children: [
        {
            path: "home",
            name: "Home",
            component: () => import("@/views/Home.vue"),
        },
        {
            path: "/dict/type/data/:dictId(\\d+)",
            component: () => import("@/views/system/DictData.vue"),
            name: "字典管理"
        }
    ],
};
