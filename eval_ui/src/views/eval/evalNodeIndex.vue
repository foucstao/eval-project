<template>
    <div class="ve_container">
        <h1>评估节点指标界面</h1>
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="queryParams">
            <!-- 这里修改为下拉框 -->
            <el-form-item label="评估实例名称" prop="instamce">
                <el-select style="width: 100%" v-model="queryParams.instance" placeholder="" clearable>
                    <el-option style="height: auto" v-for="item in InstanceList.value" :key="item.id" :label="item.evalInstance"
                                    :value="item.evalInstance">
                                    <p style="margin: 0">{{ item.evalInstance }}</p>
                                    <span class="ve_select_option_slot">
                                        评价对象：{{ item.evalObjCode }}
                                    </span>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="handleQuery(queryParams, getDikarNodeIndex)">
                    生成实例评估状态树
                </el-button>
                <el-button type="success" @click="generateTheTable">
                    查询实例状态表
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
        }" v-if="tableOpen">
            <el-table-column type="selection" width="55" align="center"/>
                    <el-table-column prop="id" label="id"  width="50"></el-table-column>
                    <el-table-column prop="evalInstance" label="评估实例名称"  width="150"></el-table-column>
                    <el-table-column prop="evalObjCode" label="评估对象" width="150"></el-table-column>
                    <el-table-column prop="nodeCode" label="节点名称"  width="150"></el-table-column>
                    <el-table-column prop="nodeType" label="节点类型"  width="150"></el-table-column>
                    <el-table-column prop="indexCode" label="指标代码"  width="150"></el-table-column>
                    <el-table-column prop="inputValue" label="指标值"  width="150"></el-table-column>
                    <el-table-column prop="pcode" label="父级代码"  width="150"></el-table-column>
                    <el-table-column prop="relationType" label="节点属性"  width="150"></el-table-column>
            <el-table-column fixed="right" label="操作"  width="200">
                <template v-slot:default="{ row }" >
                    <el-button @click.prevent="handleEdit(row)" type="danger" size="small">
                        输入指标值
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>

        <!-- 表单 -->
        <el-dialog :title="title" width="700px" append-to-body destroy-on-close :model-value="open" @close="cancel()">
            <el-form :model="form" ref="formRef" :rules="rules" label-width="150px" :inline="false">
                        <el-form-item label="指标id" prop="id">
                            <el-input v-model="form.id" placeholder="请输入" clearable :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="评估实例名称：" prop="evalInstance">
                            <el-input v-model="form.evalInstance" placeholder="请输入" clearable :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="评估对象：" prop="evalObjCode">
                            <el-input v-model="form.evalObjCode" placeholder="请输入" clearable :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="节点名称：" prop="nodeCode">
                            <el-input v-model="form.nodeCode" placeholder="请输入" clearable :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="节点类型：" prop="nodeType">
                            <el-input v-model="form.nodeType" placeholder="请输入" clearable :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="指标代码：" prop="indexCode">
                            <el-input v-model="form.indexCode" placeholder="" clearable :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="指标值：" prop="inputValue">
                            <el-input v-model="form.inputValue" placeholder="" clearable ></el-input>
                        </el-form-item>
                        <el-form-item label="父级代码：" prop="pcode">
                            <el-input v-model="form.pcode" placeholder="请输入" clearable :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="节点属性：" prop="relationType">
                            <el-input v-model="form.relationType" placeholder="请输入" clearable :disabled="true"></el-input>
                        </el-form-item>
            </el-form>
            <template v-slot:footer>
                <span>
                    <el-button @click="cancel()">取消</el-button>
                    <el-button type="primary" @click="submitForm()">确定</el-button>
                </span>
            </template>
        </el-dialog>
        
        <el-dialog :title="title" width="600px" append-to-body destroy-on-close :model-value="showTeleport" @close="closeAlert">
            <span style="font-size: 20px; color:rgb(0, 10, 10)">
                {{dialogText1}} <br><br><br> {{ dialogText2 }}
            </span>
            <template v-slot:footer>
                <span>
                    <el-button @click="closeAlert">确认</el-button>
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
    import  {getObjNodeIndexList,axiosget,axiosput}  from '@/api/eval/nodeIndexEval'
    import { reactive } from 'vue';

    const baseUrl = ref("api/eval/objindexlink")
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
        GetListByInstance,
        GetListByObj,
        baseGet,
        baseUpdate,
        baseAdd,
        baseDelete
    } = base()
    const DikarNodeIndexList = reactive([])
    const InstanceList = reactive([])
    const tableOpen = ref(false)
    const showTeleport = ref(false)
    const dialogText1 = ref("")
    const dialogText2 = ref("")
    const sqlData = ref([])
    
    //查询参数
    const queryParams = reactive({
        //创建时间
        instance:"",
        dateRange: []
    });
    //引用表单参数
    const formRef = ref(null);
    // 表单参数
    const form = ref({
        /** */
            id: "",

            evalInstance: "",

            evalObjCode: "",

            nodeCode: "",

            indexCode: "",

            inputValue: "",

            pcode: "",

            nodeType: "",

            relationType: ""
    });
    //表单校验
    const rules = {
        inputValue:{required: true}
    };

    /** 提交按钮 */
    function submitForm() {
        console.log(form.value)
        axiosput(baseUrl.value+"/edit",form.value)
            .then(res=>{
                console.log(res.data);
                GetListByInstance(baseUrl.value,data.instance)
            })
        open.value = false;

        //刷新表
        const data = {
            instance:queryParams.instance
        }
        console.log(data.instance)
        
    }
    function closeAlert(){
        dialogText1.value = "";
        dialogText2.value = "";
      showTeleport.value = false;
    };
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
            pid: "",
            pcode: "",
            code: "",
            name: "",
            relationType: "",
            isLeaf: "",
            description: "",
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
        console.log(row)
        const id = row.id || ids;
    
        form.value.id = row.id;
        form.value.evalInstance = row.evalInstance;
        form.value.evalObjCode = row.evalObjCode;
        form.value.nodeCode = row.nodeCode;
        form.value.nodeType = row.nodeType;
        form.value.indexCode = row.indexCode;
        form.value.inputValue = row.inputValue;
        form.value.pcode = row.pcode;
        form.value.relationType = row.relationType;

        open.value = true;
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
    function getDikarNodeIndex() {
        const data = {
            instance:queryParams.instance
        }
        if(data.instance==""){
            dialogText1.value = "请选取评估实例名称！！！"
            showTeleport.value = true;
            return;
        }
        axiosget(baseUrl.value+"/instance/"+data.instance).then(res=>{
            tableOpen.value = false;
            total.value = res.data.total;
            // console.log('~~~~~~~~~',res.data.total==0)
            // console.log('~~~~~~~~~',res.data.tableList)
            //如果查询不到 数据
            if(total.value == 0){
                console.log('正在联表查询生成状态表')
                dialogText1.value="正在联表查询生成实例状态表"
                axiosget(baseUrl.value+"/DikarNodeIndex/"+data.instance).then(res=>{
                        DikarNodeIndexList.value = res.data.tableList;
                    }
                )
                showTeleport.value = true;
            }
            else {
                console.log('该状态表已生成，请点击查询按钮')
                dialogText1.value=data.instance+"实例状态表已生成，请点击查询按钮！";
                showTeleport.value = true;
            }
        })       
    }

    //生成指标输入树
    function generateTheTable(){
        const data = {
            instance:queryParams.instance
        }
        if(data.instance==""){
            dialogText1.value = "请选取评估实例名称！！！";
            showTeleport.value = true;
            return;
        }
        axiosget(baseUrl.value+"/instance/"+data.instance).then(res=>{
            total.value = res.data.total;
            sqlData.value = res.data.tableList;
            tableData.value = sqlData.value;    
            tableOpen.value = true;
        })
        dialogText1.value=data.instance+"实例状态表已生成!";
        dialogText2.value = "请输入相应的指标值"
        showTeleport.value = true;
    }

    function getDataList() {
        queryList.value = []
        proxy.setQueryParams("name", "like", queryParams.name, queryList.value)
        if (queryParams.dateRange.length > 0) {
            proxy.setQueryParams("create_time", "date(>=)", queryParams.dateRange[0], queryList.value)
            proxy.setQueryParams("create_time", "date(<=)", queryParams.dateRange[1], queryList.value)
        }
        baseGetList(baseUrl.value, tablePage)
        
    }
    //axios是异步操作，在执行get或post请求时，会直接返回执行下面的语句，所以后端的数据还未传递过来
    //正确的操作是将对数据的操作写在get请求内部
    function getInstanceList(){
        axiosget(InstanceUrl.value+'/page').then(res=>{
            InstanceList.value = res.data.tableList;
        })
    }
    getInstanceList()
    // getDataList()

</script>
<style lang="scss" scoped>
</style>