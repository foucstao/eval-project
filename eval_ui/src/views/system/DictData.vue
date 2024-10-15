<template>
    <div class="ve_container">
        <el-page-header @back="goBack" />
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="queryParams">
            <el-form-item label="字典标签" prop="dictLabel">
                <el-input clearable v-model="queryParams.dictLabel" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="handleQuery(queryParams, getDataList)">
                    搜索
                </el-button>
                <el-button @click="resetQuery(queryParams, getDataList)">
                    重置
                </el-button>
            </el-form-item>
        </el-form>

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
}">
            <template #tool_bar>
                <el-row :gutter="10" class="mb8">
                    <el-col :span="1.5">
                        <el-button type="primary" plain @click="handleAdd">
                            <el-icon>
                                <Plus />
                            </el-icon>新增
                        </el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="danger" plain :disabled="multiple" @click="handleDelete">
                            <el-icon>
                                <Delete />
                            </el-icon>删除
                        </el-button>
                    </el-col>
                </el-row>
            </template>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column prop="id" label="字典编号"></el-table-column>
            <el-table-column prop="dictLabel" label="字典标签"></el-table-column>
            <el-table-column prop="dictValue" label="字典键值"></el-table-column>
            <el-table-column prop="dictSort" label="显示顺序"></el-table-column>
            <el-table-column prop="remark" label="备注"></el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column fixed="right" label="操作">
                <template v-slot:default="{ row }">
                    <el-button @click.prevent="handleEdit(row)" type="primary" size="small">
                        修改
                    </el-button>
                    <el-button @click.prevent="handleDelete(row)" type="danger" size="small">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>

        <!-- 表单 -->
        <el-dialog :title="title" width="580px" append-to-body destroy-on-close :model-value="open" @close="cancel()">
            <el-form :model="form" ref="formRef" :rules="rules" label-width="80px" :inline="false">
                <el-form-item label="字典标签" prop="dictLabel">
                    <el-input v-model="form.dictLabel" placeholder="" clearable></el-input>
                </el-form-item>
                <el-form-item label="字典键值" prop="dictValue">
                    <el-input v-model="form.dictValue" placeholder="" clearable></el-input>
                </el-form-item>
                <el-form-item label="显示顺序">
                    <el-input-number v-model="form.dictSort" :min="0" step-strictly :step="1"></el-input-number>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input v-model="form.remark" placeholder="" clearable></el-input>
                </el-form-item>
            </el-form>
            <template v-slot:footer>
                <span>
                    <el-button @click="cancel()">取消</el-button>
                    <el-button type="primary" @click="submitForm()">确定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script>
export default {
    data: () => ({
        description: "字典数据信息查询与设置",
    }),
};
</script>

<script setup>
import { useRoute, useRouter } from 'vue-router';
import { listDictData, getDictData, addDictData, updateDictData, delDictData } from '@/api/system/dict/data'
import { getDictType } from '@/api/system/dict/type'
const { proxy } = getCurrentInstance();
//路由
let route = useRoute();
let router = useRouter();
// 选中数组
const ids = ref([]);
// 非多个禁用
const multiple = ref(true);
//弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);
//查询条件集合
const queryList = ref([]);
//列表数据
const tableData = ref([]);
//查询参数
const queryParams = reactive({
    //创建时间
    dateRange: []
});
//分页参数
const tablePage = reactive({
    pageNum: 1,
    pageSize: 10
})
//总条目
const total = ref(0);
//角色列表
const roleList = ref([]);
//引用表单参数
const formRef = ref(null);
//查询表单参数
const queryForm = ref(null);
//字典类型id
const dictTypeId = ref(0)
//字典类型
const dictType = ref("")

// 表单参数
const form = reactive({
    id: null,
    dictSort: "",
    dictLabel: "",
    dictValue: "",
    dictType: '',
    createTime: "",
    remark: ""
});
//表单校验
const rules = {
    dictLabel: [
        {
            required: true,
            message: "字典标签不能为空",
            trigger: "blur",
        },
    ],
    dictValue: [
        {
            required: true,
            message: "字典键值不能为空",
            trigger: "blur",
        },
    ],
};
/** 提交按钮 */
const submitForm = () => {
    formRef.value.validate(valid => {
        if (valid) {
            if (form.id !== null) {
                updateDictData(form).then(response => {
                    proxy.globalMessage(response.status, response.message);
                    open.value = false;
                    getDataList();
                });
            } else {
                addDictData(form).then(response => {
                    proxy.globalMessage(response.status, response.message);
                    open.value = false;
                    getDataList();
                });
            }
        }
    });
}
// 多选框选中数据
const handleSelectionChange = (selection) => {
    ids.value = selection.map(item => item.id);
    multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
    open.value = true;
    reset();
    title.value = "添加字典数据";
}

// 表单重置
const reset = () => {
    form.id = null,
        form.dictSort = 0,
        form.dictType = dictType.value,
        form.dictValue = "",
        form.dictLabel = "",
        form.remark = "",
        form.createTime = "",
        nextTick(() => {
            formRef.value.resetFields();
        });
}
// 取消按钮
const cancel = () => {
    open.value = false;
    reset();
}
//修改按钮
const handleEdit = (row) => {
    const userId = row.id || ids;
    getDictData(userId).then(res => {
        open.value = true;
        const data = res.data.data
        form.id = data.id;
        form.dictSort = data.dictSort;
        form.dictType = data.dictType;
        form.dictValue = data.dictValue;
        form.dictLabel = data.dictLabel;
        form.createTime = data.createTime;
        form.remark = data.remark
        title.value = '修改字典数据';
    })
};

/** 重置按钮操作 */
const resetQuery = () => {
    proxy.resetForm(queryForm.value);
    proxy.handleQuery(queryParams, getDataList);
}

/**删除行数据
 */
const handleDelete = (row) => {
    const userIds = row.id || ids.value;
    proxy
        .$confirm('是否确认删除字典数据编号为"' + userIds + '"的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
        })
        .then(() => {
            return delDictData(userIds)
        }).then((response) => {
            proxy.globalMessage(response.status, response.message);
            getDataList();
        });
};
/**
 * 初始化查询参数
 */
const initData = () => {
    queryList.value = []
    proxy.setQueryParams("dict_type", "=", dictType.value, queryList.value)
    proxy.setQueryParams("dict_label", "like", queryParams.dictLabel, queryList.value)
    if (queryParams.dateRange.length > 0) {
        proxy.setQueryParams("create_time", "date(>=)", queryParams.dateRange[0], queryList.value)
        proxy.setQueryParams("create_time", "date(<=)", queryParams.dateRange[1], queryList.value)
    }
}
/**
 * 获取列表数据
 */
const getDataList = async () => {
    initData();
    const data = {
        queryList: queryList.value
    }
    listDictData(tablePage, data).then(res => {
        total.value = res.data.total
        tableData.value = res.data.tableList;
    })
};

const getType = async () => {
    getDictType(dictTypeId.value).then(response => {
        dictType.value = response.data.data.dictType
        getDataList();
    })
}

const goBack = () => {
    router.back()
}
onMounted(async () => {
    dictTypeId.value = route.params.dictId
    await getType();
});
</script>

<style lang="scss" scoped>
</style>
