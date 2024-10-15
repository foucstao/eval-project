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
                                <Plus />
                            </el-icon>新增
                        </el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="danger" plain :disabled="multiple" @click="handleDelete">
                            <el-icon>
                                <Delete />
                            </el-icon>删除
                        </el-button>
                    </el-col>
                </el-row>
            </template>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column prop="name" label="用户名"></el-table-column>
            <el-table-column prop="role" label="角色">
                <template v-slot:default="{ row }">
                    {{ formatRole(row) }}
                </template>
            </el-table-column>
            <el-table-column prop="remark" label="备注"></el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column fixed="right" label="操作">
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
        <el-dialog :title="title" width="580px" append-to-body destroy-on-close :model-value="open" @close="cancel()">
            <el-form :model="form" ref="formRef" :rules="rules" label-width="80px" :inline="false">
                <el-form-item label="账号" prop="name">
                    <el-input v-model="form.name" placeholder="" clearable></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password" v-if="!title.includes('修改')">
                    <el-input v-model="form.password" show-password placeholder="" clearable></el-input>
                </el-form-item>
                <el-form-item label="角色" prop="role">
                    <el-select style="width: 100%" v-model="form.role" placeholder="" clearable multiple>
                        <el-option v-for="item in roleList" :key="item.id" :label="item.roleName" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input v-model="form.remark" show-password placeholder="" clearable></el-input>
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
        description: "用户信息查询与设置",
    }),
};
</script>

<script setup>
import { listUser, getUser, addUser, updateUser, delUser } from '@/api/system/user'
import { listRoleNoPage } from '@/api/system/role'

const { proxy } = getCurrentInstance();

// 选中数组
const ids = ref([]);
// 非多个禁用
const multiple = ref(true);
//弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);
//查询条件集合
const queryList = ref([]);
//列表数据
const tableData = ref([]);
//查询参数
const queryParams = reactive({
    //创建时间
    dateRange: []
});
//分页参数
const tablePage = reactive({
    pageNum: 1,
    pageSize: 10
})
//总条目
const total = ref(0);
//角色列表
const roleList = ref([]);
//引用表单参数
const formRef = ref(null);
//查询表单参数
const queryForm = ref(null);
// 表单参数
const form = reactive({
    id: null,
    name: "",
    role: [],
    password: "",
    createTime: "",
    remark: ""
});
//表单校验
const rules = {
    name: [
        {
            required: true,
            message: "请输入用户名",
            trigger: "blur",
        },
    ],
    password: [
        {
            required: true,
            message: "密码不能为空",
            trigger: "blur",
        },
    ],
    role: [
        {
            required: true,
            trigger: "change",
            type: 'array',
            message: "至少指定一个角色"
        },
    ],
};

//格式化角色
const formatRole = (row) => {
    const role = row.role.split(",")
    let rolename = []
    role.forEach(item => {
        rolename.push(roleList.value.filter(r => { return r.id == item })[0].roleName)
    })
    return rolename.join(",")
}
/** 提交按钮 */
const submitForm = () => {
    formRef.value.validate(valid => {
        if (valid) {
            open.value = false;
            form.role = form.role.join(",")
            if (form.id !== null) {
                updateUser(form).then(response => {
                    proxy.globalMessage(response.status, response.message);
                    getDataList();
                });
            } else {
                addUser(form).then(response => {
                    proxy.globalMessage(response.status, response.message);
                    getDataList();
                });
            }
        }
    });
}
// 多选框选中数据
const handleSelectionChange = (selection) => {
    ids.value = selection.map(item => item.id);
    multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
    open.value = true;
    reset();
    title.value = "添加用户";
}

// 表单重置
const reset = () => {
    form.id = null;
    form.name = "";
    form.role = [];
    form.password = "";
    form.createTime = "";
    form.remark = ""
    nextTick(() => {
        formRef.value.resetFields();
    });
}
// 取消按钮
const cancel = () => {
    open.value = false;
    reset();
}
//修改按钮
const handleEdit = (row) => {
    const userId = row.id || ids;
    getUser(userId).then(res => {
        open.value = true;
        const data = res.data.data
        form.id = data.id,
            form.name = data.name,
            form.role = data.role.split(",").map(Number),
            form.password = data.password,
            form.createTime = data.createTime,
            form.remark = data.remark
        title.value = '修改用户';
    })
};

/** 重置按钮操作 */
const resetQuery = () => {
    proxy.resetForm(queryForm.value);
    proxy.handleQuery(queryParams, getDataList);
}

/**删除行数据
 */
const handleDelete = (row) => {
    const userIds = row.id || ids.value;
    proxy
        .$confirm('是否确认删除用户编号为"' + userIds + '"的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
        })
        .then(() => {
            return delUser(userIds)
        }).then((response) => {
            proxy.globalMessage(response.status, response.message);
            getDataList();
        });
};
/**
 * 初始化查询参数
 */
const initData = () => {
    proxy.setQueryParams("name", "like", queryParams.name, queryList.value)
    if (queryParams.dateRange.length > 0) {
        proxy.setQueryParams("create_time", "date(>=)", queryParams.dateRange[0], queryList.value)
        proxy.setQueryParams("create_time", "date(<=)", queryParams.dateRange[1], queryList.value)
    }
}
/**
 获取角色列表
 */
const getRoleList = async () => {
    listRoleNoPage().then(response => {
        roleList.value = response.data.tableList;
    })
};
/**
 * 获取列表数据
 */
const getDataList = async () => {
    queryList.value = []
    initData();
    const data = {
        queryList: queryList.value
    }
    listUser(tablePage, data).then(res => {
        total.value = res.data.total
        tableData.value = res.data.tableList;
    })
};
onMounted(async () => {

    await getRoleList();
    await getDataList();
});
</script>

<style lang="scss" scoped>
</style>
