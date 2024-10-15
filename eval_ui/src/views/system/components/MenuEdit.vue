<template>
    <el-dialog :title="title" append-to-body destroy-on-close :model-value="showDialog" @close="closeDialog()">
        <!-- 表单内容 -->
        <el-form ref="formRef" :model="form" label-width="80px" :rules="rules" :validate-on-rule-change="false"
            :inline="false">
            <el-form-item label="类型">
                <el-radio-group v-model="type" @change="changeType">
                    <el-radio-button :label="0">
                        目录
                    </el-radio-button>
                    <el-radio-button :label="1">
                        菜单
                    </el-radio-button>
                    <el-radio-button :label="2">
                        按钮
                    </el-radio-button>
                </el-radio-group>
            </el-form-item>

            <el-form-item label="父级" prop="parentId">
                <el-tree-select v-model="parentId" :data="treeList" check-strictly />
            </el-form-item>
            
            <el-form-item label="排序" v-show="type != 2">
                <el-input-number v-model="sort" :min="0" step-strictly :step="1"></el-input-number>
            </el-form-item>
            <el-form-item label="名称" prop="name">
                <el-input v-model="name"></el-input>
            </el-form-item>
            <el-form-item label="权限" prop="perms" v-show="type != 0">
                <el-input v-model="perms"></el-input>
            </el-form-item>
            <el-form-item label="图标" prop="icon" v-show="type != 2">
                <el-select style="width: 100%" v-model="icon" placeholder="" clearable filterable
                    @visible-change="handelOptionsChange" popper-class="ve_option_box">
                    <template v-slot:prefix>
                        <el-icon :size="20" style="color: #000" v-if="type != 2">
                            <component :is="icon" />
                        </el-icon>
                    </template>
                    <el-option style="
                            display: inline-block;
                            height: auto;
                            padding: 10px 11px 0px;
                        " v-for="item in ve_icons" :key="item" :label="item" :value="item">
                        <el-icon :title="item" :size="30">
                            <component :is="item" />
                        </el-icon>
                    </el-option>
                </el-select>
            </el-form-item>


            <el-form-item label="路径" prop="path" v-show="type == 1">
                <el-select style="width: 100%" v-model="path" placeholder="" clearable>
                    <el-option style="height: auto" v-for="item in files" :key="item.path" :label="item.path"
                        :value="item.path">
                        <p style="margin: 0">{{ item.path }}</p>
                        <span class="ve_select_option_slot">
                            描述 :{{ item.description }}
                        </span>
                    </el-option>
                </el-select>
            </el-form-item>
        </el-form>
        
        <template v-slot:footer>
            <span>
                <el-button @click="closeDialog()">取消</el-button>
                <el-button type="primary" @click="onSubmit()">确定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { icons } from "@/utils";
import { addMenu, updateMenu } from '@/api/system/menu'
const { proxy } = getCurrentInstance();
/**
 * 获取文件路径

 */
const getfiles = () => {
    const files = import.meta.globEager('@/views/**/*.vue');
    console.log(files)
    let fileList = []
    for (const key in files) {
        const file = {
            path: key.replace(/^(\..\/\..\/)|(\.vue)/g, ""),
            description: files[key].default.data &&
                (files[key].default.data().description || "无"),
            menus:
                files[key].default.data &&
                (files[key].default.data().menus || []),
        }
        fileList.push(file)
    }
    return fileList.filter((key) => {
        return !key.path.includes("/components/");
    });
};


// import { useStore } from "vuex";
const props = defineProps({
    showDialog: {
        type: Boolean,
        default: true,
    },
    title: {
        type: String,
        default: "添加",
    },
    rowData: {
        type: Object,
        default: null,
    },
    menuList: {
        type: Array,
        default: null,
    },
});

const emit = defineEmits(["closeDialog"]);

const { title, rowData, menuList } = toRefs(props);
const closeDialog = () => {
    emit("closeDialog", false);
};
const ve_icons = ref([]);
const formRef = ref(null);
const files = getfiles();
const form = reactive({
    id: '',
    name: "",
    type: 0,
    parentId: '0',
    perms: "",
    path: "",
    icon: "Menu",
    iframe: 0,
    sort: 1,

});
const { id, name, type, parentId, perms, path, icon, iframe, sort } = toRefs(form);
/**
 *  字段重置
 */
const resetForm = () => {
    name.value = "";
    perms.value = "";
    path.value = "";
    icon.value = "Menu";
    iframe.value = 1;
    sort.value = 1;
};

/**表单验证规则
 */
const rules = computed(() => ({
    parentId: [
        {
            required: type.value == 2 ? true : false,
            message: "请选择父级菜单",
            trigger: "change",
        },
    ],
    name: [
        {
            required: true,
            message: "请输入菜单名称",
            trigger: "blur",
        },
    ],
    icon: [
        {
            required: type.value != 2 ? true : false,
            message: "请选择图标",
            trigger: "change",
        },
    ],
}));
//树形下拉框
const treeList = ref([{
    value: '0',
    label: '主类目',
    children: []
}])
/**
 *初始化赋值
 */ 
rowData.value &&
    (
        (id.value = rowData.value.id),
        (name.value = rowData.value.name),
        (type.value = rowData.value.type),
        (parentId.value = "" + rowData.value.parentId + ""),
        (path.value = rowData.value.path),
        (perms.value = rowData.value.perms),
        (icon.value = rowData.value.icon),
        (iframe.value = rowData.value.iframe),
        (sort.value = rowData.value.sort)
    );
/**
 * 类型切换事件
 */
const changeType = (val) => {
    nextTick(() => {
        formRef.value.resetFields();
    });
    val == 2 && (icon.value = "");
};
/**
 * 图标下拉框展开事件
 */
const handelOptionsChange = (flag) => {
    if (flag === true && ve_icons.value.length < 1) {
        ve_icons.value = icons();
    }
};

//树结构格式化
const treeDataFormat = (tree) => {
    let arr = [];
    if (!!tree && tree.length !== 0) {
        tree.forEach((item, index) => {
            let obj = {};
            obj.label = item.name;
            obj.value = "" + item.id + "";
            obj.disabled = false;
            if (item.children.length > 0) {
                obj.children = treeDataFormat(item.children); // 递归调用
            }
            arr.push(obj);
        });
    }

    return arr;
}

onMounted(() => {
    treeList.value[0].children = treeDataFormat(menuList.value)
});

/**
 * 提交表单
 */
const onSubmit = () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
            let res;
            if (title.value.includes("添加")) {
                addMenu(form).then((res) => {
                    proxy.$message({
                        type: "success",
                        message: res.message,
                    });

                })
            } else {
                updateMenu(form).then((res) => {
                    proxy.$message({
                        type: "success",
                        message: res.message,
                    });

                })
            }
            closeDialog();
        } else {
            return false;
        }
    });
};
</script>

<style lang="scss" scoped>
</style>
