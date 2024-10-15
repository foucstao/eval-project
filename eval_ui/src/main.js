
import { createApp } from "vue";
import App from "@/App.vue";
import router from "@/router";
import store from "@/store";
import installElementPlus from "@/plugins/element";
import elementIcon from "@/plugins/svgicon";
import permission from "@/plugins/permission";
import directives from "@/directives";
import baseComponents from "@/components/baseComponents";
import "normalize.css/normalize.css";
import "nprogress/nprogress.css";
import "@/assets/styles/common.scss";
import {
    handleQuery, resetForm,
    handleSizeChange, handleCurrentChange,
    setQueryParams, globalMessage, formatDate,
    download,
    selectDictLabel
} from "@/utils/common";
import { getDicts } from "@/api/system/dict/data";
import vue3videoPlay from "vue3-video-play"; // 引入组件
import "vue3-video-play/dist/style.css"; // 引入css
const app = createApp(App);

app.config.globalProperties.handleQuery = handleQuery
app.config.globalProperties.resetForm = resetForm
app.config.globalProperties.handleSizeChange = handleSizeChange
app.config.globalProperties.handleCurrentChange = handleCurrentChange
app.config.globalProperties.setQueryParams = setQueryParams
app.config.globalProperties.globalMessage = globalMessage
app.config.globalProperties.formatDate = formatDate
app.config.globalProperties.getDicts = getDicts
app.config.globalProperties.selectDictLabel = selectDictLabel
app.config.globalProperties.download = download
app
    .use(elementIcon)
    .use(baseComponents)
    .use(store)
    .use(router)
    .use(installElementPlus)
    .use(permission, { router, store })
    .use(vue3videoPlay)
    .use(directives, { router, store })
    .mount("#app");


