<template>
    <div class="ve_container">
        <h1>评估实例管理界面</h1>
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="queryParams">
            <el-form-item label="评估对象名称" prop="obj">
                <el-input clearable v-model="queryParams.obj" placeholder="请输入指标模板编号"></el-input>
            </el-form-item>
            
            <el-form-item>
                <el-button type="primary" @click="handleQuery(queryParams, getDataByObj)">
                    搜索
                </el-button>
                <el-button @click="resetQuery(queryParams, getDataByObj)">
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
                            </el-icon>
                            新增
                        </el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="danger" plain :disabled="multiple" @click="handleDelete">
                            <el-icon>
                                <Delete />
                            </el-icon>
                            删除
                        </el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="warning" plain>
                            <el-icon>
                                <Download />
                            </el-icon>
                            导出
                        </el-button>
                    </el-col>
                </el-row>
            </template>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column prop="id" label="id" width="100"></el-table-column>
            <el-table-column prop="evalInstance" label="评价实例" width="200"></el-table-column>
            <el-table-column prop="evalObjCode" label="评价对象" width="200"></el-table-column>
            <el-table-column prop="indexModel" label="指标模型" width="200"></el-table-column>
            <el-table-column prop="institution" label="机构名称" width="200"></el-table-column>
            <el-table-column prop="importanceVersion" label="权重版本" width="200"></el-table-column>
            <el-table-column prop="clusterFunction" label="模糊算子" width="200"></el-table-column>
            <el-table-column prop="rateVersion" label="等级版本" width="200"></el-table-column>
            <el-table-column fixed="right" label="操作" width="200">
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
        <el-dialog :title="title" width="700px" append-to-body destroy-on-close :model-value="open" @close="cancel()">
            <el-form :model="form" ref="formRef" :rules="rules" label-width="150px" :inline="false">
                <el-form-item label="机构名称:" prop="institution">
                    <el-input v-model="form.institution" placeholder="请输入" clearable></el-input>
                </el-form-item>
                <el-form-item label="实例名称:" prop="evalInstance">
                    <el-input v-model="form.evalInstance" placeholder="请输入" clearable></el-input>
                </el-form-item>
                <el-form-item label="评估对象:" prop="evalObjCode">
                    <el-input v-model="form.evalObjCode" placeholder="请输入" clearable></el-input>
                </el-form-item>
                <el-form-item label="权重矩阵版号:" prop="importanceVersion">
                    <el-input v-model="form.importanceVersion" placeholder="请输入" clearable></el-input>
                </el-form-item>
                <el-form-item label="评估模板版号:" prop="indexModel">
                    <el-input v-model="form.indexModel" placeholder="请输入" clearable></el-input>
                </el-form-item>
                <el-form-item label="评估等级版号:" prop="rateVersion">
                    <el-input v-model="form.rateVersion" placeholder="请输入" clearable></el-input>
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
        description: "指标模型设计",
    }),
};
</script>

<script setup>
import axiosUtil from '@/utils/axiosUtil'
import base from '@/utils/base'
import  {getObjNodeIndexList,axiosget,axiosput,axiosPost,axiosDelete}  from '@/api/eval/nodeIndexEval'
//请求路径
const baseUrl = ref("api/eval/objIndexModel")
const InstanceUrl = ref("api/eval/objIndexModel")
//导入基础
const { 
    proxy,
    ids,
    total,
    tableData,
    queryList,
    queryForm,
    multiple,
    open,
    tablePage,
    title,
    baseGetList,
    GetListByObj,
    GetListByQuery,
    baseGet,
    baseUpdate,
    baseAdd,
    baseDelete
} = base()
//查询参数
const queryParams = reactive({
    obj: "",
    //创建时间
    dateRange: []
});
//引用表单参数
const formRef = ref(null);
// 表单参数
const form = reactive({
    /** */
    id: null,
    //版本号        
    institution: "",
    evalInstance: "",
    evalObjCode: "",
    importanceVersion: "",
    clusterFunction:"",
    indexModel: "",       
    rateVersion:""
});
//表单校验
const rules = {
    id: { required: true, trigger: "blur", message: "不能为空" },
};

/** 提交按钮 */
function submitForm() {
    formRef.value.validate(valid => {
        if (valid) {
            if (form.id !== null) {
                // console.log('正在进行update修改')
                // baseUpdate(baseUrl.value + "/edit", form)
                axiosput(baseUrl.value+'/edit',form).then(res=>{
                    open.value = false
                    getDataList()
                })
            } else {
                baseAdd(baseUrl.value, form)
            }
        }
    });
}

// 多选框选中数据
function handleSelectionChange(selection) {
    ids.value = selection.map(item => item.id);
    multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
    open.value = true;
    reset();
    title.value = "添加";
}

// 表单重置
function reset() {
    form.value = {
        id: null,
        code: "",
        name: "",
        description: "",
        value: "",
        version: "",
    };
    nextTick(() => {
        formRef.value.resetFields();
    });
}

// 取消按钮
function cancel() {
    open.value = false;
    reset();
}

//修改按钮
function handleEdit(row) {
    const id = row.id || ids;
    // baseGet(baseUrl.value, form, id, '修改')
    open.value = true
    Object.keys(row).forEach(key=>{
        if (key != "_X_ROW_KEY"){
            form[key]=row[key];
        }
    });
};

/** 重置按钮操作 */
function resetQuery() {
    proxy.resetForm(queryForm.value);
    proxy.handleQuery(queryParams, getDataList);
}

/**删除行数据
 */
function handleDelete(row) {
    const array = row.id || ids.value;
    baseDelete(baseUrl.value, array)
};

/**
 * 初始化查询参数
 */
 function getDataList() {
        queryList.value = []
        proxy.setQueryParams("name", "like", queryParams.name, queryList.value)
        if (queryParams.dateRange.length > 0) {
            proxy.setQueryParams("create_time", "date(>=)", queryParams.dateRange[0], queryList.value)
            proxy.setQueryParams("create_time", "date(<=)", queryParams.dateRange[1], queryList.value)
        }
        baseGetList(baseUrl.value, tablePage)
    }

function getDataByObj() {
    const data = { 
        obj: queryParams.obj
    }
    GetListByObj(baseUrl.value, data.obj)
}


getDataList()
</script>
<style lang="scss" scoped></style>
