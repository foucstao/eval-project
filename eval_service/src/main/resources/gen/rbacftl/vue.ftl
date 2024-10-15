<template>
    <div class="ve_container">
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
            <#list fieldList as field>
                <#if field.name!=pkCloumn.name>
                    <el-table-column prop="${field.nameHump}" label="${field.comment}"  width="200"></el-table-column>
                </#if>
            </#list>
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
        <el-dialog :title="title" width="580px" append-to-body destroy-on-close :model-value="open" @close="cancel()">
            <el-form :model="form" ref="formRef" :rules="rules" label-width="80px" :inline="false">
                <#list fieldList as field>
                    <#if field.name!=pkCloumn.name>
                        <el-form-item label="${field.comment}:" prop="${field.nameHump}">
                            <el-input v-model="form.${field.nameHump}" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                    </#if>
                </#list>
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
            description: "",
        }),
    };
</script>

<script setup>
    import axiosUtil from '@/utils/axiosUtil'
    import base from '@/utils/base'
    //请求路径
    const baseUrl = ref("api/v1/${businessName}")
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
        <#list fieldList as field >
        /** ${field.comment}*/
            ${ field.nameHump }: "",
        </#list >
    });
    //表单校验
    const rules = {
        <#list fieldList as field>
        <#if field.name!= pkCloumn.name >
        ${ field.nameHump }: {required: true, trigger: "blur", message: "${field.comment}不能为空"},
        </#if >
        </#list >
    };

    /** 提交按钮 */
    function submitForm() {
        formRef.value.validate(valid => {
            if (valid) {
                if (form.value.${pkCloumn.nameHump} !== null) {
                    baseUpdate(baseUrl.value, form.value)
                } else {
                    baseAdd(baseUrl.value, form.value)
                }
            }
        });
    }

    // 多选框选中数据
    function handleSelectionChange(selection) {
        ids.value = selection.map(item => item.${pkCloumn.nameHump});
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
            <#list fieldList as field>
            <#if field??>
            <#if field.name==pkCloumn.nameHump>
            ${pkCloumn.nameHump}: null,
            </#if>
            <#if field.name!=pkCloumn.nameHump>
            ${field.nameHump}: "",
            </#if>
            </#if>
            </#list>
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
        const id = row.${pkCloumn.nameHump} || ids;
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
        const array = row.${pkCloumn.nameHump} || ids.value;
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
