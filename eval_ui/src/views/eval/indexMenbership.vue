<template>
    <div class="ve_container">
        <h1>指标隶属度参数设计</h1>
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
                    <el-table-column prop="indexCode" label="指标代码" width="100"></el-table-column>
                    <el-table-column prop="ratingCode" label="等级代码" width="100"></el-table-column>
                    <el-table-column prop="membershipFunction" label="隶属度函数" width="100"></el-table-column>
                    
                    <el-table-column prop="param1" label="参数1" width="100"></el-table-column>
                    <el-table-column prop="param2" label="参数2" width="100"></el-table-column>
                    <el-table-column prop="param3" label="参数3" width="100"></el-table-column>
                    <el-table-column prop="version" label="版本" width="100"></el-table-column>
                    <el-table-column prop="param4" label="参数4" width="100"></el-table-column>
                    <el-table-column prop="param5" label="参数5" width="100"></el-table-column>

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
                        <el-form-item label="指标代码:" prop="indexCode">
                            <el-input v-model="form.indexCode" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="等级代码:" prop="ratingCode">
                            <el-input v-model="form.ratingCode" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="隶属度函数:" prop="membershipFunction">
                            <el-input v-model="form.membershipFunction" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="版本:" prop="version">
                            <el-input v-model="form.version" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="参数1:" prop="param1">
                            <el-input v-model="form.param1" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="参数2:" prop="param2">
                            <el-input v-model="form.param2" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="参数3:" prop="param3">
                            <el-input v-model="form.param3" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="参数4:" prop="param4">
                            <el-input v-model="form.param4" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="参数5:" prop="param5">
                            <el-input v-model="form.param5" placeholder="请输入" clearable></el-input>
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
            description: "指标隶属度函数参数设置",
        }),
    };
</script>

<script setup>
    import axiosUtil from '@/utils/axiosUtil'
    import  {getObjNodeIndexList,axiosget,axiosput,axiosPost}  from '@/api/eval/nodeIndexEval'
    import base from '@/utils/base'
    import { reactive } from 'vue';
    //请求路径
    const baseUrl = ref("api/eval/membership")
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
            indexCode:"",
            ratingCode:"",
            membershipFunction:"",
            version:"",
            param1:"",
            param2:"",
            param3:"",
            param4:"",
            param5:"",
    });
    //表单校验
    const rules = {
        indexCode:[
            {
                required:true,
                message:"请填入指标代码",
            }
        ],
        ratingCode:[
            {
                required:true,
                message:"请填入等级代码",
            }
        ],
        membershipFunction:[
            {
                required:true,
                message:"请填入隶属函数类型",
            }
        ],
        param1:[
            {
                required:true,
            }
        ],
        param2:[
            {
                required:true,
            }
        ],
    };

    /** 提交按钮 */
    function submitForm() {
        formRef.value.validate(valid => {
            // 提交按钮分开分析，如果id已经存在，则可以视为更新
            if(valid){
                if(form.id != null){

                    axiosput(baseUrl.value +"/edit",form).then(res=>{
                        open.value = false;
                        getDataList()
                    })
                    //baseUpdate(baseUrl.value+'/edit',form)
                }
                else{
                    baseAdd(baseUrl.value,form)
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
        // /** */
        //     indexCode:"",
        //     ratingCode:"",
        //     membershipFunction:"",
        //     version:"",
        //     param1:"",
        //     param2:"",
        //     param3:"",
        //     param4:"",
        //     param5:"",
        // };
        form.id = null;
        form.indexCode = "";
        form.ratingCode = "";
        form.membershipFunction = "";
        form.version = "";
        form.param1 = "";
        form.param2 = "";
        form.param3 = "";
        form.param4 = "";
        form.param5 = "";
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
        open.value = true
        Object.keys(row).forEach(key=>{
        if (key != "_X_ROW_KEY"){
            form[key]=row[key];
             }
        });
      //  baseGet(baseUrl.value, form, id, '修改')
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
