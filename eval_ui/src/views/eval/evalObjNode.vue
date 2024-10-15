<template>
    <div class="ve_container">
        <h1>评估对象节点界面</h1>
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="queryParams">
            <el-form-item label="评估对象代码" prop="code">
                <el-input clearable v-model="queryParams.code" placeholder="请输入指标模板编号"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="handleQuery(queryParams, getObjNodeJoins)">
                    搜索
                </el-button>
                <el-button @click="resetQuery(queryParams, getObjNodeJoins)">
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
                    <el-table-column prop="id" label="id"  width="50"></el-table-column>
                    <el-table-column prop="evalObjCode" label="评估对象" width="150"></el-table-column>
                    <el-table-column prop="nodeCode" label="节点代码"  width="150"></el-table-column>
                    <el-table-column prop="institution" label="机构名称"  width="150"></el-table-column>
                    <el-table-column prop="nodeName" label="节点名称"  width="150"></el-table-column>
                    <el-table-column prop="nodeType" label="节点类型"  width="150"></el-table-column>
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

                        <el-form-item label="评估对象" prop="evalObjCode">
                            <el-select style="width: 100%" v-model="form.evalObjCode" placeholder="" clearable>
                                <el-option style="height: auto" v-for="item in evalObjList.value" :key="item.id" :label="item.evalCode"
                                    :value="item.evalCode">
                                    <p style="margin: 0">{{ item.evalCode }}</p>
                                    <span class="ve_select_option_slot">
                                        描述：{{ item.evalObj }}
                                    </span>
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="节点代码" prop="nodeCode">
                            <el-select style="width: 100%" v-model="form.nodeCode" placeholder="" clearable>
                                <el-option style="height: auto" v-for="item in evalNodeList.value" :key="item.id" :label="item.code"
                                    :value="item.code">
                                    <p style="margin: 0">{{ item.code }}</p>
                                    <span class="ve_select_option_slot">
                                        描述：{{ item.name }}
                                    </span>
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="结构名称:" prop="institution">
                            <el-input v-model="form.institution" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <!-- 这里需要根据节点的展示来实现就好了 -->
                        <el-form-item label="节点名称" prop="nodeName">
                            <el-input v-model="form.nodeName" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="节点类型" prop="nodeType">
                            <el-select style="width: 100%" v-model="form.nodeType" placeholder="" clearable>
                                <el-option style="height: auto" v-for="item in evalTypeList.value" :key="item.id" :label="item.code"
                                    :value="item.code">
                                    <p style="margin: 0">{{ item.code }}</p>
                                    <span class="ve_select_option_slot">
                                        描述：{{ item.name }}
                                    </span>
                                </el-option>
                            </el-select>
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
            description: "评估指标管理",
        }),
    };
</script>

<script setup>
    import axiosUtil from '@/utils/axiosUtil'
    import base from '@/utils/base'
    import  {getObjNodeIndexList,axiosget,axiosput,axiosPost,axiosDelete}  from '@/api/eval/nodeIndexEval'
import { reactive } from 'vue';
    //请求路径

    const baseUrl = ref("api/eval/evalObjNodeLink")
    const objUrl = ref("api/eval/evalobj")
    const nodeUrl = ref("api/eval/evalnode")
    const typeUrl = ref("api/eval/nodeType")
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
        code:"",
        //创建时间
        dateRange: []
    });
    //引用表单参数
    const formRef = ref(null);
    // 表单参数
    const form = reactive({
        /** */
            id: null,
        /** parent indicator id*/
            evalObjCode: "",
        /** parent indicator code*/
            nodeCode: "",
        /** */
            institution: "",
        /** */
            nodeName: "",
        /** index type: relation, entity*/
            nodeType: "",
    });
    const evalObjList = reactive([])
    const evalNodeList = reactive([])
    const evalTypeList = reactive([])
    //表单校验
    const rules = {
        id: {required: true, trigger: "blur", message: "id不能为空"},
        pid: {required: true, trigger: "blur", message: "parent indicator id不能为空"},
        pcode: {required: true, trigger: "blur", message: "parent indicator code不能为空"},
        code: {required: true, trigger: "blur", message: "不能为空"},
        name: {required: true, trigger: "blur", message: "不能为空"},
        relationType: {required: true, trigger: "blur", message: "index type: relation, entity不能为空"},
        isLeaf: {required: true, trigger: "blur", message: "1: leaf index, 0: not leaf index不能为空"},
        description: {required: true, trigger: "blur", message: "不能为空"},
    };

    /** 提交按钮 */
    function submitForm() {
        formRef.value.validate(valid => {
            if (valid) {
                if (form.id !== null) {
                    //baseUpdate(baseUrl.value, form.value)
                    axiosput(baseUrl.value +'/edit',form).then(res=>{
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
        open.value = true;
        reset();
        title.value = "添加";
    }

    // 表单重置
    function reset() {
        // form.value = {
        //     /** */
        //     id: null,
        // /** parent indicator id*/
        //     evalObjCode: "",
        // /** parent indicator code*/
        //     nodeCode: "",
        // /** */
        //     institution: "",
        // /** */
        //     nodeName: "",
        // /** index type: relation, entity*/
        //     nodeType: "",
        // };
        form.id = null;
        form.evalObjCode = "";
        form.nodeCode = "";
        form.institution = "";
        form.nodeName = "";
        form.nodeType = "";
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

    //查询所有参数
    function getDataList(){
        queryList.value = []
        proxy.setQueryParams("name", "like", queryParams.name, queryList.value)
        if (queryParams.dateRange.length > 0) {
            proxy.setQueryParams("create_time", "date(>=)", queryParams.dateRange[0], queryList.value)
            proxy.setQueryParams("create_time", "date(<=)", queryParams.dateRange[1], queryList.value)
        }
        baseGetList(baseUrl.value, tablePage)
    }
    function getObjNodeJoins() {
        const data = {
            code:queryParams.code
            // code:"workshop"
        }
        console.log(data)
        GetListByObj(baseUrl.value,data,tablePage)
    }
    function getObjNodeList(){
        axiosget(objUrl.value+'/page').then(res=>{
            evalObjList.value = res.data.tableList
            console.log("这是obj列表",evalObjList.value)
        })
        axiosget(nodeUrl.value+'/page').then(res=>{
            evalNodeList.value = res.data.tableList
            console.log("这是node列表",evalNodeList.value)
        })
        axiosget(typeUrl.value+'/page').then(res=>{
            evalTypeList.value = res.data.tableList
            console.log("这是obj列表",evalTypeList.value)
        })
    }
    getDataList()
    getObjNodeList()
    
</script>
<style lang="scss" scoped>
</style>
