<template>
    <div class="ve_container">
        <h1>指标权重设置界面</h1>
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
                    <el-table-column prop="id" label="id" width="200"></el-table-column>
                    <el-table-column prop="indexFirst" label="比较节点" width="200"></el-table-column>
                    <el-table-column prop="indexSecond" label="被比较节点" width="200"></el-table-column>
                    <el-table-column prop="importance" label="重要性" width="200"></el-table-column>
                    <el-table-column prop="version" label="版本" width="200"></el-table-column>
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
                        
                        <!-- <el-form-item label="等级代码:" prop="code">
                            <el-input v-model="form.code" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="等级名称:" prop="name">
                            <el-input v-model="form.name" placeholder="请输入" clearable></el-input>
                        </el-form-item> -->
                        <!-- <el-form-item label="等级描述:" prop="description">
                            <el-input v-model="form.description" placeholder="请输入" clearable></el-input>
                        </el-form-item> -->
                        <el-form-item label="比较节点" prop="indexFirst">
                            <el-select style="width: 100%" v-model="form.indexFirst" placeholder="" clearable>
                                <el-option style="height: auto" v-for="item in indexList.value" :key="item.id" :label="item.code"
                                    :value="item.code">
                                    <p style="margin: 0">{{ item.code }}</p>
                                    <span class="ve_select_option_slot">
                                        描述：{{ item.name }}
                                    </span>
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="被比较节点" prop="indexFirst">
                            <el-select style="width: 100%" v-model="form.indexSecond" placeholder="" clearable>
                                <el-option style="height: auto" v-for="item in indexList.value" :key="item.id" :label="item.code"
                                    :value="item.code">
                                    <p style="margin: 0">{{ item.code }}</p>
                                    <span class="ve_select_option_slot">
                                        描述：{{ item.name }}
                                    </span>
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="重要性度量:" prop="importance">
                            <el-input v-model="form.importance" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="版本号:" prop="version">
                            <el-input v-model="form.version" placeholder="形式例如 V1、V2" clearable></el-input>
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
            description: "指标重要性关系",
        }),
    };
</script>

<script setup>
    import axiosUtil from '@/utils/axiosUtil'
    import base from '@/utils/base'
    import { reactive } from 'vue';
    import  {getObjNodeIndexList,axiosget,axiosput,axiosPost,axiosDelete}  from '@/api/eval/nodeIndexEval'
  
    //请求路径
    const baseUrl = ref("api/eval/indexImportance")
    const indexUrl = ref('api/eval/index')
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
            indexFirst:"",
            indexSecond:"",
            importance:null,
            version: "",
    });
    //表单校验
    const rules = {
        //id: {required: true, trigger: "blur", message: "不能为空"},
        indexFirst: {required: true, trigger: "blur", message: "不能为空"},
        indexSecond: {required: true, trigger: "blur", message: "不能为空"},
        // 描述可以为空
        // description: {required: true, trigger: "blur", message: "不能为空"},
        importance: {required: true, trigger: "blur", message: "不能为空"},
        version: {required: true, trigger: "blur", message: "不能为空"},
    };
    const indexList = reactive([])
    /** 提交按钮 */
    function submitForm() {
        formRef.value.validate(valid => {
            if (valid) {
                console.log('这是提交的form参数',form)
                if (form.id !== null) {
                    //baseUpdate(baseUrl.value, form.value)
                    axiosput(baseUrl.value +"/edit",form).then(res=>{
                        open.value = false;
                        getDataList();
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
        reset();
        open.value = true;
        title.value = "添加";
    }

    // 表单重置
    function reset() {
        // form.value = {
        //     id: null,
        // /** */
        //     indexFirst:"",
        //     indexSecond:"",
        //     importance:"",
        //     version: "",
        // };
        //form.indexFirst = ""
        form.value = null;
        form.indexFirst = "";
        form.indexSecond = "";
        form.importance = null;
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

    //修改按钮
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
    function getIndexList(){
        axiosget(indexUrl.value+'/page').then(res=>{
            indexList.value = res.data.tableList;
        })
    }

    getDataList()
    getIndexList()
</script>
<style lang="scss" scoped>
</style>
