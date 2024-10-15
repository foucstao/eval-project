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
        }" v-loading="loading" element-loading-background="rgba(122, 122, 122, 0.8)">
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
                        <el-button type="warning" plain>
                            <el-icon>
                                <Download />
                            </el-icon>导出
                        </el-button>
                    </el-col>
                </el-row>
            </template>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column prop="name" label="摄像头名称"></el-table-column>
            <el-table-column prop="url" label="播放地址"></el-table-column>
            <el-table-column prop="address" label="位置"></el-table-column>
            <el-table-column prop="remark" label="备注"></el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column fixed="right" label="操作">
                <template v-slot:default="{ row }">
                    <el-button @click.prevent="startVideo(row)" type="primary" size="small">
                        播放
                    </el-button>
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
        <el-dialog :title="title" width="680px" append-to-body destroy-on-close :model-value="open" @close="cancel()">
            <el-form :model="form" ref="formRef" :rules="rules" label-width="100px" :inline="false">
                <el-form-item label="名称:" prop="name">
                    <el-input v-model="form.name" placeholder="请输入" clearable></el-input>
                </el-form-item>
                <el-form-item label="播放地址:" prop="url">
                    <el-input v-model="form.url" placeholder="请输入" clearable></el-input>
                </el-form-item>
                <el-form-item label="位置:" prop="address">
                    <el-input v-model="form.address" placeholder="请输入" clearable></el-input>
                </el-form-item>
                <el-form-item label="备注:" prop="remark">
                    <el-input v-model="form.remark" placeholder="请输入" clearable></el-input>
                </el-form-item>
            </el-form>
            <template v-slot:footer>
                <span>
                    <el-button @click="cancel()">取消</el-button>
                    <el-button type="primary" @click="submitForm()">确定</el-button>
                </span>
            </template>
        </el-dialog>

        <!-- 视频播放 -->
        <el-dialog :title="videoOptions.title" width="580px" append-to-body destroy-on-close
            :model-value="videoOptions.open" @close="videoOptions.open=false">
            <div class="player-container">
                <vue3VideoPlay v-bind="videoOptions" :type="videoOptions.type" />
            </div>
            <template v-slot:footer>
                <span>
                    <el-button @click="videoOptions.open=false">取消</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script>
export default {
    data: () => ({
        description: "视频信息查询与设置",
    }),
};
</script>
<script setup>
import axiosUtil from '@/utils/axiosUtil'
import base from '@/utils/base'
//请求路径
const baseUrl = ref("api/v1/video")
//导入基础
const { proxy, ids, total, tableData, queryList, queryForm, multiple, open, tablePage, title, baseGetList, baseGet, baseUpdate, baseAdd, baseDelete } = base()
//查询参数
const queryParams = reactive({
    //创建时间
    dateRange: []
});
//加载
const loading = ref(false)

//视频组件参数
const videoOptions = reactive({
    //弹出层
    open: false,
    width: "100%", //播放器高度
    height: "450px", //播放器高度
    color: "#409eff", //主题色
    title: "", //视频名称
    src: "https://cdn.jsdelivr.net/gh/xdlumia/files/video-play/IronMan.mp4", //视频源
    speedRate: ["0.75", "1.0", "1.25", "1.5", "2.0"], //播放倍速
    autoPlay: true, //自动播放
    volume: 0.3, //默认音量大小
    control: true, //是否显示控制
    type: "video/mp4",//视频类型
    controlBtns: [
        "audioTrack",
        "quality",
        "speedRate",
        "volume",
        "setting",
        "pip",
        "pageFullScreen",
        "fullScreen",
    ], //显示所有按钮,
});

//引用表单参数
const formRef = ref(null);
// 表单参数
const form = ref({
    /** 主键*/
    id: "",
    /** 摄像头名称*/
    name: "",
    /** 播放地址*/
    url: "",
    /** 位置*/
    address: "",
    /** 备注*/
    remark: "",
    /** 创建时间*/
    createTime: "",
});
//表单校验
const rules = {
    name: { required: true, trigger: "blur", message: "摄像头名称不能为空" },
    url: { required: true, trigger: "blur", message: "播放地址不能为空" },
    address: { required: true, trigger: "blur", message: "位置不能为空" },
};

//播放视频
function startVideo(row) {
    videoOptions.title = row.name
    videoOptions.src = row.url
    videoOptions.open = true
}

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
        name: "",
        url: "",
        address: "",
        remark: "",
        createTime: "",
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
