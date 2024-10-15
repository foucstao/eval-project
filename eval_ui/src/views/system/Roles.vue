<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="queryParams">
            <el-form-item label="角色名称" prop="name">
                <el-input clearable v-model="queryParams.name" placeholder="请输入角色名称"></el-input>
            </el-form-item>
            <el-form-item label="权限字符" prop="roleKey">
                <el-input clearable v-model="queryParams.roleKey" placeholder="请输入权限字符"></el-input>
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
                    <el-col :span="1.5">
                        <el-button type="warning" plain @click="handleImport(true)">
                            <el-icon>
                                <Upload />
                            </el-icon>导入
                        </el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="warning" plain @click="handleExport">
                            <el-icon>
                                <Download />
                            </el-icon>导出
                        </el-button>
                    </el-col>
                </el-row>
            </template>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column prop="roleName" label="角色名称"></el-table-column>
            <el-table-column prop="roleKey" label="权限字符"></el-table-column>
            <el-table-column prop="roleSort" label="显示顺序">
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column prop="remark" label="备注"></el-table-column>
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

        <!-- 导入对话框 -->
        <el-dialog :title="upload.title" :model-value="upload.open" width="400px" append-to-body destroy-on-close
            @close="handleImport(false)">
            <el-upload ref="upefloadRef" class="upload-demo" accept=".xlsx, .xls" :headers="upload.headers"
                :action="upload.url" :disabled="upload.isUploading" :file-list="upload.fileList" :limit="1"
                :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag
                :on-exceed="handleExceed">
                <el-icon class="el-icon--upload">
                    <upload-filled />
                </el-icon>
                <div class="el-upload__text">
                    将文件拖到此处，或
                    <em>点击上传</em>
                </div>
                <template #tip>
                    <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
                </template>
                <div class="el-upload__tip" style="color:red" slot="tip">
                    提示：仅允许导入“xls”或“xlsx”格式文件！
                </div>
            </el-upload>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitFileForm">确 定</el-button>
                <el-button @click="handleImport(false)">取 消</el-button>
            </div>
        </el-dialog>

        <!-- 表单 -->
        <el-dialog :title="title" append-to-body destroy-on-close :model-value="open" @close="cancel()">
            <el-form ref="formRef" :model="form" :rules="rules" label-width="80px" :inline="false"
                :validate-on-rule-change="false">
                <el-form-item label="角色名称" prop="roleName">
                    <el-input v-model="form.roleName" placeholder="请输入角色名称" clearable></el-input>
                </el-form-item>
                <el-form-item label="权限字符" prop="roleKey">
                    <el-input v-model="form.roleKey" placeholder="请输入权限字符" clearable></el-input>
                </el-form-item>
                <el-form-item label="显示顺序">
                    <el-input-number v-model="form.roleSort" :min="0" step-strictly :step="1"></el-input-number>
                </el-form-item>
                <el-form-item class="ve_role_item" label="权限" prop="role">
                    <el-card shadow="never" :body-style="{ padding: 0, height: '200px', width: '500px' }">
                        <el-scrollbar>
                            <el-tree ref="tree" :data="menuList" node-key="id" default-expand-all
                                :props="{ label: 'name' }" empty-text="暂无数据" show-checkbox highlight-current>
                                <template v-slot="{ data }">
                                    <span :data-roleId="data.id">
                                        {{ data.name }}
                                    </span>
                                </template>
                            </el-tree>
                        </el-scrollbar>
                    </el-card>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input v-model="form.remark" show-password placeholder="" clearable></el-input>
                </el-form-item>
            </el-form>
            <template v-slot:footer>
                <span>
                    <el-button @click="cancel()">取消</el-button>
                    <el-button type="primary" @click="submitForm">确定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script>
export default {
    data: () => ({
        description: "角色信息查询与设置",
    }),
};
</script>

<script setup>
import axiosUtil from '@/utils/axiosUtil'
import base from '@/utils/base'
import { listMenu } from '@/api/system/menu'
import { genFileId } from 'element-plus'
import XE from "xe-utils";

//请求路径
const baseUrl = ref("api/v1/role")
//导入基础
const { proxy, ids, total, tableData, queryList, queryForm, multiple, open, tablePage, title, baseGetList, baseGet, baseUpdate, baseAdd, baseDelete } = base()

//查询参数
const queryParams = reactive({
    //创建时间
    dateRange: []
});
//菜单列表
const menuList = ref([]);
//引用表单参数
const formRef = ref(null);
//树组件引用
const tree = ref(null);
//上传组件
const upefloadRef = ref(null);
// 表单参数
const form = ref({
    id: null,
    roleName: "",
    roleKey: "",
    roleSort: 0,
    createTime: "",
    remark: "",
    menuIds: [],
    selectIds: []
});
// 导入参数
const upload = reactive({
    fileList: [],
    // 是否显示弹出层
    open: false,
    // 弹出层标题
    title: "导入列表",
    // 是否禁用上传
    isUploading: false,
    // 设置上传的请求头部
    headers: { Authorization: "Bearer " + sessionStorage.token },
    // 上传的地址
    url: "/space/api/v1/role/import"
})
//表单校验
const rules = {
    roleName: [
        {
            required: true,
            message: "请输入角色名称",
            trigger: "blur",
        },
    ],
    roleKey: [
        {
            required: true,
            message: "请输入权限字符",
            trigger: "blur",
        },
    ],
};
/** 导出按钮操作 */
const handleExport = () => {
    proxy.download('api/v1/role/export', {
        ...queryParams
    }, `权限管理-${+new Date()}.xlsx`)
}
/** 导入按钮 */
const handleImport = (open) => {
    upload.fileList = [];
    upload.open = open;
}
/** 文件选择改变事件 */
const handleExceed = (fileList) => {
    const file = fileList[0]
    console.log(file)
    file.uid = genFileId()
    upefloadRef.value.clearFiles();
    upefloadRef.value.handleStart(file)
}
// 文件上传中处理
const handleFileUploadProgress = (event, file, fileList) => {
    upload.isUploading = true;
}
/** 导入信息按钮 */
const submitFileForm = () => {
    if (upload.fileList.length > 0) {
        upefloadRef.value.submit();

    } else {
        proxy.globalMessage(500, "请先选择要上传的文件");

    }
}
/** 下载模板操作 */
const importTemplate = () => {
    proxy.download(
        "api/v1/role/importTemplate",
        {
            ...queryParams
        },
        `role_${new Date().getTime()}.xlsx`
    );
}
/** 上传文件成功操作 */
const handleFileSuccess = (response) => {
    upload.fileList = [];
    upefloadRef.value.clearFiles();
    upload.isUploading = false;
    upload.open = false;
    proxy.$alert(response.message, "导入结果", { dangerouslyUseHTMLString: true });
    getDataList()
}
/** 提交按钮 */
const submitForm = () => {
    let treeMenu = getSelectionTree();
    formRef.value.validate(valid => {
        if (valid) {
            form.value.menuIds = treeMenu.menuIds
            form.value.selectIds = treeMenu.selectIds;
            if (form.value.id !== null) {
                baseUpdate(baseUrl.value, form.value)
            } else {
                baseAdd(baseUrl.value, form.value)
            }
        }
    });
}
// 多选框选中数据
const handleSelectionChange = (selection) => {
    ids.value = selection.map(item => item.id);
    multiple.value = !selection.length;
}
//获取树形菜单id
const getSelectionTree = () => {
    let nodedata = tree.value.getCheckedNodes();
    const selectParent = [];
    const selectIds = [];
    if (nodedata.length !== 0) {
        nodedata.forEach(item => {
            selectIds.push(item.id);
            selectParent.push(item.parentId);
            getTree(selectParent, item.parentId)
        });
    }
    const menuIds = selectIds.concat(selectParent)
    const newMenuIds = [...new Set(menuIds)];
    const menuRole = {
        selectIds: selectIds,
        menuIds: newMenuIds
    }
    return menuRole;
}

const getTree = (selectIds, id) => {
    XE.toTreeArray(menuList.value).forEach(item => {
        if (item.id == id) {
            if (item.parentId != 0) {
                selectIds.push(item.id)
                getTree(selectIds, item.parentId)
            } else {
                selectIds.push(item.id)
            }
        }
    })
}

/** 新增按钮操作 */
const handleAdd = () => {
    open.value = true;
    reset();
    title.value = "添加角色";
}

// 表单重置
const reset = () => {
    form.value = {
        id: null,
        roleName: "",
        roleKey: "",
        roleSort: 0,
        createTime: "",
        remark: ""
    };
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
    const roleId = row.id || ids;
    if (row.id !== 1) {
        axiosUtil.axiosGet(baseUrl.value + "/" + roleId).then(res => {
            open.value = true;
            form.value = res.data.data
            title.value = '修改角色';
            axiosUtil.axiosGet(`api/v1/menurole/getallowmenu/${roleId}`).then(response => {
                nextTick(() => {
                    tree.value.setCheckedKeys(response.data.selectArray)
                });
            })
        })
    } else {
        proxy.globalMessage(500, "超级管理员角色不支持操作");
    }
};

/** 重置按钮操作 */
const resetQuery = () => {
    proxy.resetForm(queryForm.value);
    proxy.handleQuery(queryParams, getDataList);
}

/**删除行数据
 */
const handleDelete = (row) => {
    const roleIds = row.id || ids.value;
    if (row.id == 1 || ids.value.includes(1)) {
        proxy.globalMessage(500, "超级管理员角色不支持操作");
    } else {
        baseDelete(baseUrl.value, roleIds)
    }
};

//获取菜单列表
const getMenuList = () => {
    listMenu(sessionStorage.uid).then(res => {
        menuList.value = res.data.list;
    })
}
/**
 * 初始化查询参数
 */
const getDataList = () => {
    queryList.value = []
    proxy.setQueryParams("role_name", "like", queryParams.name, queryList.value)
    proxy.setQueryParams("role_key", "like", queryParams.roleKey, queryList.value)
    if (queryParams.dateRange.length > 0) {
        proxy.setQueryParams("create_time", "date(>=)", queryParams.dateRange[0], queryList.value)
        proxy.setQueryParams("create_time", "date(<=)", queryParams.dateRange[1], queryList.value)
    }
    baseGetList(baseUrl.value, tablePage)
}

getDataList()
getMenuList();
</script>

<style lang="scss" scoped>
</style>
