<template>
    <div class="ve_container">
        <!-- 列表 -->
        <ve-table :table="{
            data: tableData,
            onSelectionChange: (val) => handleSelectionChange(val)
        }" :pagination="{
    onSizeChange: (val) =>
        handleSizeChange(val, tablePage, getDataList),
    onCurrentChange: (val) =>
        handleCurrentChange(val, tablePage, getDataList),
    page: tablePage.pageNum,
    limit: tablePage.pageSize,
    total: total,
}" ref="tableRef">

            <template #tool_bar>
                <el-row :gutter="10" class="mb8">
                    <el-col :span="1.5">
                        <el-button type="success" plain @click="handleAdd">
                            <el-icon>
                                <Briefcase />
                            </el-icon>默认配置
                        </el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="primary" plain :disabled="multiple" @click="handleBatchDown">
                            <el-icon>
                                <Download />
                            </el-icon>批量下载
                        </el-button>
                    </el-col>
                </el-row>
            </template>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column prop="tableName" label="表名"></el-table-column>
            <el-table-column prop="tableComment" label="表描述"></el-table-column>
            <el-table-column fixed="right" label="操作">
                <template v-slot:default="{ row }">
                    <el-button @click.prevent="handleDown(row)" type="primary" size="small">
                        <el-icon>
                            <Download />
                        </el-icon>下载代码
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>

        <!-- 表单 -->
        <el-dialog :title="title" width="580px" append-to-body destroy-on-close :model-value="open" @close="cancel()">
            <el-form :model="form" ref="formRef" :rules="rules" label-width="120px" :inline="false">
                <el-form-item label="访问路径:" prop="businessName">
                    <el-input v-model="form.businessName" placeholder="即controller的访问路径 如：user" clearable></el-input>
                </el-form-item>
                <el-form-item label="权限字符:" prop="moduleName">
                    <el-input v-model="form.moduleName" placeholder="即controller所在的包 如:system" clearable>
                    </el-input>
                </el-form-item>
                <el-form-item label="包名:" prop="packageName">
                    <el-input v-model="form.packageName" placeholder="如：com.system" clearable>
                    </el-input>
                </el-form-item>
            </el-form>
            <template v-slot:footer>
                <span>
                    <el-button @click="cancel()">取消</el-button>
                    <el-button type="primary" @click="submitForm()">保存</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script>
export default {
    data: () => ({
        description: "代码生成",
    }),
};
</script>

<script setup>
import { listCode, downCode, batchDownCode } from '@/api/system/genCode'

const { proxy } = getCurrentInstance();

// 非多个禁用
const multiple = ref(true);
//弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);
//列表数据
const tableData = ref([]);
//分页参数
const tablePage = reactive({
    pageNum: 1,
    pageSize: 10
})
const formRef = ref(null)
//总条目
const total = ref(0);
// 表单参数
const form = reactive({
    tableName: "",
    packageName: "com.rbac",
    businessName: "test",
    moduleName: "system"
});

const tableRef = ref(null)

// 选中的数据
const forms = ref([]);

//表单校验
const rules = {
    packageName: [
        {
            required: true,
            message: "包名不能为空",
            trigger: "blur",
        },
    ],
    businessName: [
        {
            required: true,
            message: "访问路径不能为空",
            trigger: "blur",
        },
    ],
    moduleName: [
        {
            required: true,
            message: "权限字符不能为空",
            trigger: "blur",
        },
    ],
};

// 多选框选中数据
const handleSelectionChange = (selection) => {
    forms.value = []
    selection.forEach(item => {
        const form = {
            tableName: item.tableName,
            packageName: sessionStorage.packageName,
            businessName: sessionStorage.businessName,
            moduleName: sessionStorage.moduleName
        }
        forms.value.push(form)
    })
    multiple.value = !selection.length;
}

const handleBatchDown = () => {
    batchDownCode(forms.value).then((res) => {
        tableRef.value.elTable.clearSelection()
        const blob = new Blob([res]);//处理文档流
        const down = document.createElement('a');
        down.download = 'tableList.zip';
        down.style.display = 'none';//隐藏,没必要展示出来
        down.href = URL.createObjectURL(blob);
        document.body.appendChild(down);
        down.click();
        URL.revokeObjectURL(down.href); // 释放URL 对象
        document.body.removeChild(down);//下载完成移除
    }).catch((r) => {
        window.console.error(r)
    })
}
// 表单重置
const reset = () => {
    form.packageName = sessionStorage.packageName
    form.businessName = sessionStorage.businessName
    form.moduleName = sessionStorage.moduleName
}

/** 新增按钮操作 */
const handleAdd = () => {
    open.value = true;
    reset();
    title.value = "默认配置";
}

// 取消按钮
const cancel = () => {
    open.value = false;
    reset();
}

//下载代码
const handleDown = (row) => {
    form.tableName = row.tableName
    downCode(form).then((res) => {
        const blob = new Blob([res]);//处理文档流
        const down = document.createElement('a');
        down.download = row.tableName + '.zip';
        down.style.display = 'none';//隐藏,没必要展示出来
        down.href = URL.createObjectURL(blob);
        document.body.appendChild(down);
        down.click();
        URL.revokeObjectURL(down.href); // 释放URL 对象
        document.body.removeChild(down);//下载完成移除
    }).catch((r) => {
        window.console.error(r)
    })
}

/**
 * 初始化参数
 */
const initData = () => {
    sessionStorage.packageName = form.packageName
    sessionStorage.businessName = form.businessName
    sessionStorage.moduleName = form.moduleName
}

/** 提交按钮 */
const submitForm = () => {
    formRef.value.validate(valid => {
        if (valid) {
            sessionStorage.packageName = form.packageName
            sessionStorage.businessName = form.businessName
            sessionStorage.moduleName = form.moduleName
            open.value = false
            proxy.globalMessage(200, "设置成功");
        }
    });
}

/**
 * 获取列表数据
 */
const getDataList = async () => {
    initData()
    listCode(tablePage).then(res => {
        total.value = res.data.data.total
        tableData.value = res.data.data.list;
    })
};
onMounted(async () => {
    await getDataList();
});
</script>

<style lang="scss" scoped></style>
