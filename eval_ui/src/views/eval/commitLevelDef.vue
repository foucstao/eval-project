<template>
    <div class="ve_container">
        <h1>评估等级管理界面</h1>
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="queryParams">
            <el-form-item label="名称" prop="name">
                <el-input clearable v-model="queryParams.name" placeholder="请输入名称"></el-input>
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
                                <Plus/>
                            </el-icon>
                            新增
                        </el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="danger" plain :disabled="multiple" @click="handleDelete">
                            <el-icon>
                                <Delete/>
                            </el-icon>
                            删除
                        </el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="warning" plain>
                            <el-icon>
                                <Download/>
                            </el-icon>
                            导出
                        </el-button>
                    </el-col>
                </el-row>
            </template>
            <el-table-column type="selection" width="55" align="center"/>
                    <el-table-column prop="id" label="id" width="100"></el-table-column>
                    <el-table-column prop="code" label="等级代码"  width="150"></el-table-column>
                    <el-table-column prop="name" label="等级名称"  width="150"></el-table-column>
                    
                    <el-table-column prop="value" label="等效数值"  width="150"></el-table-column>
                    <el-table-column prop="version" label="版本号"  width="150"></el-table-column>
                    <el-table-column prop="description" label="等级描述"  width="150"></el-table-column>
            <el-table-column fixed="right" label="操作"  width="200">
                <template v-slot:default="{ row }" >
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
                        
                        <el-form-item label="等级代码:" prop="code">
                            <el-input v-model="form.code" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="等级名称:" prop="name">
                            <el-input v-model="form.name" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        
                        <el-form-item label="等效数值:" prop="value">
                            <el-input v-model="form.value" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="版本号:" prop="version">
                            <el-input v-model="form.version" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="相关描述:" prop="description">
                            <el-input v-model="form.description" placeholder="请输入" clearable></el-input>
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
            description: "评价等级设置",
        }),
    };
</script>

<script setup>
    import axiosUtil from '@/utils/axiosUtil'
    import base from '@/utils/base'
import { reactive } from 'vue';
import  {getObjNodeIndexList,axiosget,axiosput,axiosPost,axiosDelete}  from '@/api/eval/nodeIndexEval'
  
    //请求路径
    const baseUrl = ref("api/eval/rating")
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
    baseGet,
    baseUpdate,
    baseAdd,
    baseDelete
    } = base()
    //查询参数
    const queryParams = reactive({
        //创建时间
        dateRange: []
    });
    //引用表单参数
    const formRef = ref(null);
    // 表单参数
    const form = reactive({
        /** */
            id: null,
        /** */
            code: "",
        /** */
            name: "",
        /** */
            description: "",
        /** */
            value: null,
        /** */
            version: "",
    });
    //表单校验
    const rules = {
        //id: {required: true, trigger: "blur", message: "不能为空"},
        code: {required: true, trigger: "blur", message: "不能为空"},
        name: {required: true, trigger: "blur", message: "不能为空"},
        // 描述可以为空
        // description: {required: true, trigger: "blur", message: "不能为空"},
        value: {required: true, trigger: "blur", message: "不能为空"},
        version: {required: true, trigger: "blur", message: "不能为空"},
    };

    /** 提交按钮 */
    function submitForm() {
        formRef.value.validate(valid => {
            if (valid) {
                if (form.id !== null) {
                    //baseUpdate(baseUrl.value, form.value)
                    axiosput(baseUrl.value +'/edit',form).then(res=>{
                        getDataList();
                        open.value = false;
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
        // form.value = {
        //     id: null,
        //     code: "",
        //     name: "",
        //     description: "",
        //     value: "",
        //     version: "",
        // };
        form.id = null;
        form.name = "";
        form.code = "";
        form.description = "";
        form.value = null;
        form.version = "";
        nextTick(() => {
            formRef.value.resetFields();
        });
    }
    
    // 取消按钮
    function cancel() {
        open.value = false;
        reset();
    }

    // 修改按钮
    function handleEdit(row) {
        const id = row.id || ids;
        //baseGet(baseUrl.value, form, id, '修改')
        open.value = true;
        Object.keys(row).forEach(key=>{
        if(key != '_X_ROW_KEY'){
            form[key] = row[key];
        }
        // if(form.relationType != 'thing'){
        //     rType.value = 1;
        // }else{
        //     rType.value = 0;
        // }
      })
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

    getDataList()
</script>
<style lang="scss" scoped>
</style>