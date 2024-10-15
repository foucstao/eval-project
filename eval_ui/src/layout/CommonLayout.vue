<template>
    <el-container>
        <el-aside width="auto">
            <side-bar></side-bar>
        </el-aside>
        <el-container>
            <el-header :height="nav_height">
                <navigate-bar></navigate-bar>
            </el-header>
            <el-main>
                <el-scrollbar style="
                        padding: 20px;
                        box-sizing: border-box;
                        background: #fff;
                    ">
                    <router-view v-slot="{ Component }">
                        <transition name="el-zoom-in-top" mode="out-in">
                            <component :key="routerAlive" :is="Component" />
                        </transition>
                    </router-view>
                </el-scrollbar>
            </el-main>
        </el-container>
    </el-container>
</template>
<script setup>
import NavigateBar from "./NavigateBar.vue";
import SideBar from "./SideBar.vue";
import { useRoute } from "vue-router";
import { nav_height } from "@/assets/styles/variables.js";
const route = useRoute();
const routerAlive = ref(null);
watchEffect(() => {
    routerAlive.value = route.name;
});
provide("reload", () => {
    routerAlive.value = Math.random() + "_" + Math.random();
});
</script>
<style lang="scss" scoped>
@import '@/assets/styles/variables.scss';

.el-main {
    height: calc(100vh - v-bind(nav_height));
    background: $main-bg-color;
}
</style>
