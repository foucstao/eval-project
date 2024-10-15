export default {
    install: (app) => {
        const files = import.meta.globEager('@/components/baseComponents/*.vue');
        for (const key in files) {
            const componentConfig = files[key]
            // 全局注册组件
            app.component(
                componentConfig.default.name,
                componentConfig.default
            );
        }
    },
};
