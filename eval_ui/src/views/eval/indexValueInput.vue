<template>
    <div class="ve_container">
        <h1>指标值输入界面</h1>
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
                    <el-table-column prop="id" label="指标id"  width="150"></el-table-column>
                    <el-table-column prop="evalInstance" label="评估实例" width="150"></el-table-column>
                    <el-table-column prop="evalObjCode" label="评估对象" width="150"></el-table-column>
                    <el-table-column prop="nodeCode" label="节点代码"  width="150"></el-table-column>
                    <el-table-column prop="indexCode" label="指标代码"  width="150"></el-table-column>
                    <el-table-column prop="inputValue" label="指标值"  width="150"></el-table-column>
                    <el-table-column prop="pcode" label="父级代码"  width="150"></el-table-column>
                    <el-table-column prop="nodeType" label="节点类型"  width="150"></el-table-column>
                    <el-table-column prop="relationType" label="属性"  width="150"></el-table-column>
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
                        <el-form-item label="指标id" prop="id">
                            <el-input v-model="form.id" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="指标代码:" prop="code">
                            <el-input v-model="form.code" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="父节点id" prop="pid">
                            <el-input v-model="form.pid" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="父节点代码" prop="pcode">
                            <el-input v-model="form.pcode" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="指标名称:" prop="name">
                            <el-input v-model="form.name" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="指标类型:" prop="relationType">
                            <el-input v-model="form.relationType" placeholder="两种类型：关系边、节点实体" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="是否为叶节点:" prop="isLeaf">
                            <el-input v-model="form.isLeaf" placeholder="1:是；0:否。" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="指标描述:" prop="description">
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
            description: "评估指标管理",
        }),
    };
</script>

<script setup>
    import axiosUtil from '@/utils/axiosUtil'
    import base from '@/utils/base'
    //请求路径
    const baseUrl = ref("api/eval/objindexlink")
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
    const form = ref({
        /** */
            id: "",
        /** parent indicator id*/
            pid: "",
        /** parent indicator code*/
            pcode: "",
        /** */
            code: "",
        /** */
            name: "",
        /** index type: relation, entity*/
            relationType: "",
        /** 1: leaf index, 0: not leaf index*/
            isLeaf: "",
        /** */
            description: "",
    });
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
                if (form.value.id !== null) {
                    baseUpdate(baseUrl.value, form.value)
                } else {
                    baseAdd(baseUrl.value, form.value)
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
        const id = row.id || ids;
        baseGet(baseUrl.value, form, id, '修改')
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
