<template>
    <div class="ve_container">
        <!-- 列表 -->
        <ve-table :table="{
            data: tableData,
            rowKey: 'id'
        }" :pagination="{
            hideOnSinglePage: true,
            total: total,
        }">
            <template #tool_bar>
                <el-button type="primary" @click="handleEdit('添加')">
                    新增
                </el-button>
            </template>
            <el-table-column prop="name" label="名称"></el-table-column>
            <el-table-column prop="icon" label="图标">
                <template v-slot="{ row }">
                    <template v-if="row.type !== 2">
                        <el-icon style="margin-right: 6px; vertical-align: middle">
                            <component :is="row.icon" />
                        </el-icon>
                        <span style="vertical-align: middle">
                            {{ row.icon }}
                        </span>
                    </template>
                    <span v-else>/</span>
                </template>
            </el-table-column>
            <el-table-column prop="type" label="类型">
                <template v-slot="{ row }">
                    <el-tag :type="
                        row.type == 0
                            ? ''
                            : row.type == 1
                                ? 'success'
                                : 'warning'
                    ">
                        {{
                        row.type == 0
                        ? "目录"
                        : row.type == 1
                        ? "菜单"
                        : "按钮"
                        }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="sort" label="排序">
                <template v-slot="{ row }">
                    <span v-if="row.type !== 2" style="font-weight: bold">
                        {{ row.sort }}
                    </span>
                    <span v-else>/</span>
                </template>
            </el-table-column>
            <el-table-column prop="perms" label="权限"></el-table-column>
            <el-table-column prop="path" label="组件路径" show-overflow-tooltip>
                <template v-slot="{ row }">
                    <template v-if="row.type == 1">
                        <span>{{ row.path }}</span>
                    </template>
                    <span v-else>/</span>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="240">
                <template v-slot:default="{ row }">
                    <el-button @click.prevent="handleEdit('编辑', row)" type="primary" size="small">
                        编辑
                    </el-button>
                    <el-button @click.prevent="handleDel(row.id)" type="danger" size="small">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>

        <!-- 编辑组件 -->
        <menu-edit v-if="showDialog" :menuList="tableData" :rowData="rowData" :title="dialogTitle"
            :showDialog="showDialog" @closeDialog="handelDialog($event)" />
    </div> 
</template>
<script>
export default {
    data: () => ({
        description: "菜单查询与设置",
    }),
};
</script>

<script setup>
import MenuEdit from "./components/MenuEdit.vue";
import { listMenu, delMenu } from '@/api/system/menu'
const { proxy } = getCurrentInstance();

const queryForm = ref(null);
const dialogTitle = ref("");
const showDialog = ref(false);
const rowData = ref(null);
const tableData = ref([]);

// 选中数组
const ids = ref([]);
//总条目
const total = ref(0);

/**
 * 添加or编辑事件
 */
const handleEdit = (title, row = null) => {
    showDialog.value = true;
    dialogTitle.value = title;
    rowData.value = row;
};
/**
 * @description: dialog事件
 * @param {*}
 * @return {*}
 */
const handelDialog = async (e) => {
    showDialog.value = e;
    await getDataList()
};
/**删除行数据
 */
const handleDel = (id) => {
    ids.value.push(id);
    proxy
        .$confirm('是否确认删除菜单编号为"' + ids.value + '"的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
        })
        .then(() => {
            return delMenu(ids.value)
        })
        .then((response) => {
            proxy.globalMessage(response.status, response.message);
            getDataList();
        });
};
/**
 * @description: 获取列表数据
 * @param {*}
 * @return {*}
 */
const getDataList = async () => {
    listMenu(sessionStorage.uid).then(res => {
        tableData.value = res.data.list;
    })
};

onMounted(async () => {
    await getDataList();
});
</script>

<style lang="scss" scoped>

</style>
