<template>
    <div class="ve_container">
        <h1>指标模型设计</h1>
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="queryParams">
            <el-form-item label="指标模板编号" prop="indexModel">
                <el-input clearable v-model="queryParams.indexModel" placeholder="请输入指标模板编号"></el-input>
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
            <el-table-column prop="id" label="id" width="200"></el-table-column>
            <el-table-column prop="indexModel" label="指标模型" width="200"></el-table-column>
            <el-table-column prop="nodeType" label="所属类型" width="200"></el-table-column>
            <el-table-column prop="indexCode" label="指标代码" width="200"></el-table-column>
            <!-- <el-table-column prop="weight" label="指标权重" width="200"></el-table-column> -->
            <el-table-column prop="membership" label="隶属度函数" width="200"></el-table-column>
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

                <el-form-item label="指标模型:" prop="indexModel">
                    <el-input v-model="form.indexModel" placeholder="请输入" clearable></el-input>
                </el-form-item>
                <!-- <el-form-item label="节点类型:" prop="nodeType">
                    <el-input v-model="form.nodeType" placeholder="请输入" clearable></el-input>
                </el-form-item> -->
                <el-form-item label="节点类型" prop="pcode" v-show="rType !=1">
                            <el-select style="width: 100%" v-model="form.nodeType" placeholder="" clearable>
                                <el-option style="height: auto" v-for="item in typeList.value" :key="item.id" :label="item.code"
                                    :value="item.code">
                                    <p style="margin: 0">{{ item.code }}</p>
                                    <span class="ve_select_option_slot">
                                        描述：{{ item.name }}
                                    </span>
                                </el-option>
                            </el-select>
                        </el-form-item>
                <!-- <el-form-item label="指标代码:" prop="indexCode">
                    <el-input v-model="form.indexCode" placeholder="请输入" clearable></el-input>
                </el-form-item> -->
                <el-form-item label="指标代码" prop="indexCode">
                            <el-select style="width: 100%" v-model="form.indexCode" placeholder="" clearable>
                                <el-option style="height: auto" v-for="item in indexList.value" :key="item.id" :label="item.code"
                                    :value="item.code">
                                    <p style="margin: 0">{{ item.code }}</p>
                                    <span class="ve_select_option_slot">
                                        描述：{{ item.name }}
                                    </span>
                                </el-option>
                            </el-select>
                        </el-form-item>
                <el-form-item label="隶属度函数类型">
                            <el-radio-group v-model="funcType" @change="changeCheckType">
                                <el-radio-button :label="0">高斯函数</el-radio-button>
                                <el-radio-button :label="1">三角形函数</el-radio-button>
                            </el-radio-group>
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
import { reactive } from 'vue';
import  {getObjNodeIndexList,axiosget,axiosput,axiosPost,axiosDelete}  from '@/api/eval/nodeIndexEval'
import { getType,getTree,addNode} from '@/api/eval/evalNodeDef.js'
//请求路径
const baseUrl = ref("api/eval/indexModel")
const typeUrl = ref("api/eval/nodeType")
const indexUrl = ref("api/eval/index")
const funcType = ref(0)
const typeList  = reactive([])
const indexList = reactive([])
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
    baseDelete,
    baseDelete_2,
} = base()
//查询参数
const queryParams = reactive({
    indexModel: "",
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
    indexModel: "",
    nodeType: "",
    indexCode: "",
    weight: null,
    membership: "guass"
});
//表单校验
const rules = {
};

/** 提交按钮 */
function submitForm() {
    formRef.value.validate(valid => {
        if (valid) {
            //判断menbership
            if(funcType.value == 0){
                form.membership = "gauss";
            }else{
                form.membership = "triangle";
            }
            if (form.id !== null) {
                // baseUpdate(baseUrl.value+'/edit', form)
                // 需要用自己的方式来实现
                axiosput(baseUrl.value+'/edit',form).then(res=>{
                    getDataList()
                    open.value = false
                })  
            } else {
                //后端未实现全局查询的内容，这里改用自己写的查询方法
                //baseAdd(baseUrl.value, form)
                axiosPost(baseUrl.value+'/add',form).then(res=>{
                    getDataList()
                    open.value = false
                })
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
    form.id = null;
    form.indexModel = "";
    form.nodeType = "";
    form.indexCode = "";
    form.weight = null;
    form.membership = "guass";
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
    //baseGet(baseUrl.value, form, id, '修改')
    const id = row.id || ids;
        open.value = true
        Object.keys(row).forEach(key=>{
        if (key != "_X_ROW_KEY"){
            form[key]=row[key];
            }
        console.log('这是本行的membership',form.membership)

        if(form.membership != "triangle"){
            funcType.value = 0;
        }else{
            funcType.value = 1;
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
    // baseDelete_2(baseUrl.value, array)
    axiosDelete(baseUrl.value+'/remove',array)
    // .$confirm('是否确认删除编号为"' + ids + '"的数据项?', "警告", {
	// 			confirmButtonText: "确定",
	// 			cancelButtonText: "取消",
	// 			type: "warning",
	// 		})
    .then(res=>{
        getDataList()
    })
};

/**
 * 初始化查询参数
 */

function getDataList() {
    const data = { 
        indexModel: queryParams.indexModel
        // nodeType: form.nodeType
    }
    console.log(data)
    GetListByQuery(baseUrl.value, data, tablePage)
}
function getTypeList(){
    getType(typeUrl.value+"/page").then(res=>{
        typeList.value = res.data.tableList;
    })
}
function getIndexList(){
        axiosget(indexUrl.value+'/page').then(res=>{
            indexList.value = res.data.tableList;
        })
    }
getIndexList()
getTypeList()
getDataList()
</script>
<style lang="scss" scoped></style>
