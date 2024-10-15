<template>
    
    <div class="ve_container">
        <h1>节点类型管理界面</h1>
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="queryParams">
            <el-form-item label="名称" prop="name">
                <el-input clearable v-model="queryParams.name" placeholder="请输入名称"></el-input>
            </el-form-item>
            <el-form-item label="创建时间" prop="dateRange">
                <el-date-picker v-model="queryParams.dateRange" style="width: 240px" value-format="YYYYMMDD"
                                type="daterange" range-separator="-" start-placeholder="开始时间" end-placeholder="结束时间">
                </el-date-picker>
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
                    <el-table-column prop="code" label="类型代码"  width="200"></el-table-column>
                    <el-table-column prop="name" label="类型名称"  width="200"></el-table-column>
                    <el-table-column prop="relationType" label="类型归属"  width="200"></el-table-column>
                    <el-table-column prop="description" label="类型描述"  width="200"></el-table-column>
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
            <el-form :model="form" ref="formRef" :rules="rules" label-width="120px" :inline="false">
                        <el-form-item label="属性">
                            <el-radio-group v-model="rType" @change="changeCheckType">
                                <el-radio-button :label="0">Thing</el-radio-button>
                                <el-radio-button :label="1">Relation</el-radio-button>
                            </el-radio-group>
                        </el-form-item>
                        <!-- <el-form-item label="父级id:" prop="pid">
                            <el-input v-model="form.pid" placeholder="默认为0" clearable></el-input>
                        </el-form-item> -->
                        <el-form-item label="类型代码:" prop="code">
                            <el-input v-model="form.code" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="类型名称:" prop="name">
                            <el-input v-model="form.name" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="类型描述:" prop="description">
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
            description: "节点类型设计",
        }),
    };
</script>

<script setup>
    import axiosUtil from '@/utils/axiosUtil'
    import base from '@/utils/base'
import { reactive } from 'vue';
import  {getObjNodeIndexList,axiosget,axiosput,axiosPost,axiosDelete}  from '@/api/eval/nodeIndexEval'
    //请求路径
    //ref可以接受原始类型，即bool int double等
    //reactive只能接受对象类型数据
    //ref必须.value来访问（html中可以不.value），reactive不需要（可以这样，也可以不这样）
    const baseUrl = ref("api/eval/nodeType")
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
    const rType = ref(0);
    // 表单参数
    const form = reactive({
        /** */
            id:null,
            
        /** */
            code: "",
        /** */
            name: "",
        /** two types:  relation, thing*/
            relationType: "thing", //默认
        /** */
            description: "",
    });
    //表单校验
    const rules = {
        //id: {required: true, trigger: "blur", message: "id不能为空"},
        code: {required: true, trigger: "blur", message: "  不能为空"},
        name: {required: true, trigger: "blur", message: "不能为空"},
        
    };

    /** 提交按钮 */
    function submitForm() {
        formRef.value.validate(valid => {
            if (valid) {
                if(rType.value != 0){
                    form.relationType = 'relation'
                }
                //console.log('这是选择的relationType',form.relationType)
                console.log(form)
                if (form.id !== null) {
                    //  baseUpdate不能实现表更新
                    //baseUpdate(baseUrl.value+'/edit', form)
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
        // form.value = {
        //     id: null,
        //     code: "",
        //     name: "",
        //     relationType: "",
        //     description: "",
        // };
        form.id = null;
        form.code = "";
        form.name = "";
        form.relationType = "thing";
        form.description= "";
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
        open.value = true;
        Object.keys(row).forEach(key=>{
        if (key != "_X_ROW_KEY"){
            form[key]=row[key];
        }
        if(form.relationType != 'thing')
            rType.value = 1
        else
            rType.value = 0
    });
        //baseGet(baseUrl.value, form, id, '修改')
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
