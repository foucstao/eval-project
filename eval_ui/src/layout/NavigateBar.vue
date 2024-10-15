<template>
    <div class="ve_nav_bar">
        <hamburger></hamburger>
        <breadcrumb></breadcrumb>
        <personal></personal>
    </div>
</template>
<script setup>
import Hamburger from "../layout/components/Hamburger.vue";
import Breadcrumb from "../layout/components/Breadcrumb.vue";
import Personal from "../layout/components/Personal.vue";
</script>
<style lang="scss" scoped>
.ve_nav_bar {
    display: flex;
    align-items: center;
    height: 100%;
}
</style>
