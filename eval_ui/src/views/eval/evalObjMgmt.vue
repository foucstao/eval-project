<template>
  <div class="ve_container">
    <h1>评估对象管理界面</h1>
      <!-- 搜索 -->
      <p>评估对象列表</p>
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
                  <el-table-column prop="evalObj" label="对象名称"  width="200"></el-table-column>
                  <el-table-column prop="evalCode" label="对象类型"  width="200"></el-table-column>
                  <el-table-column prop="evalMethod" label="评价方法"  width="200"></el-table-column>
                  <el-table-column prop="institution" label="机构" width = "200"></el-table-column>
                  <el-table-column prop="description" label="对象描述"  width="200"></el-table-column>
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
      <el-dialog :title="title" width="720px" append-to-body destroy-on-close :model-value="open" @close="cancel()">
          <el-form :model="form" ref="formRef" :rules="rules" label-width="120px" :inline="false">
                      <el-form-item label="对象名称:" prop="evalObj">
                          <el-input v-model="form.evalObj" placeholder="请输入" clearable></el-input>
                      </el-form-item>
                      <el-form-item label="对象类型:" prop="evalCode">
                          <el-input v-model="form.evalCode" placeholder="请输入" clearable></el-input>
                      </el-form-item>
                      <el-form-item label="评价方法:" prop="evalMethod">
                          <el-input v-model="form.evalMethod" placeholder="默认：模糊评判" clearable></el-input>
                      </el-form-item>
                      <el-form-item label="结构名称:" prop="institution">
                          <el-input v-model="form.institution" placeholder="请输入" clearable></el-input>
                      </el-form-item>
                      <el-form-item label="对象描述:" prop="description">
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
          description: "评估对象管理",
      }),
  };
</script>

<script setup>
  import axiosUtil from '@/utils/axiosUtil'
  import base from '@/utils/base'
import { reactive } from 'vue';
import  {getObjNodeIndexList,axiosget,axiosput,axiosPost}  from '@/api/eval/nodeIndexEval'
  //请求路径
  const baseUrl = ref("api/eval/evalobj")
  const instanceUrl = ref("api/eval/objIndexModel")
  
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

    //表2所需的参数
    const tableData2 = ref([]);
    const tablePage2 = reactive({
		pageNum: 1,
		pageSize: 10
	})
    const total2 =ref(0)
    // const {
    //     proxy2,
    //     ids2,
    //     total2,
    //     tableData2,
    //     queryList2,
    //     queryForm2,
    //     multiple2,
    //     open2,
    //     tablePage2,
    //     title2,
    //     baseGetList2,
    //     GetListByObj2,
    //     baseGet2,
    //     baseUpdate2,
    //     baseAdd2,
    //     baseDelete2
    // } = base()
  //查询参数
  const queryParams = reactive({
      //创建时间
      dateRange: []
  });
  const queryParams2 = reactive({
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
          evalObj: "",
      /** */
          evalCode: "",
      /** */
          evalMethod: "",
      /** */
          institution:"",
          description: "",
  });
  //表单校验
  const rules = {
      evalObj: {required: true, trigger: "blur", message: "不能为空"},
      evalCode: {required: true, trigger: "blur", message: "不能为空"},
      evalMethod: {required: true, trigger: "blur", message: "不能为空"},
      institution: {required: true, trigger: "blur", message: "不能为空"},
  };

  /** 提交按钮 */
  function submitForm() {
      formRef.value.validate(valid => {
          if (valid) {
              if (form.id !== null) {
                  //baseUpdate(baseUrl.value, form.value)
                  axiosput(baseUrl.value+'/edit',form).then(res=>{
                    open.value = false;
                    getDataList()
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
    //   form.value = {
    //       id: null,
    //       evalObj: "",
    //       evalCode: "",
    //       evalMethod: "",
    //       description: "",
    //   };
    form.id = null;
    form.evalObj = "";
    form.evalCode = "";
    form.evalMethod = "";
    form.institution = "";
    form.description = "";
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
        if (key != "_X_ROW_KEY"){
            form[key]=row[key];
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
  
//   function getDataList2(){
//     const data = {
//         queryList: queryList.value
//     }
//     axiosUtil.axiosPost(instanceUrl.value,data).then(res =>{
//         total2.value = res.data.total;
//         tableData2.value = res.data.tableList;
//     })

//     }


  getDataList()
//   getDataList2()

</script>
<style lang="scss" scoped>
</style>
