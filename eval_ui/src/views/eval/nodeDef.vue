<template>
  <div class="ve_container">
      <h1>评估节点管理界面</h1>
      <!-- 搜索 -->
      <el-form ref="queryForm" :inline="true" :model="queryParams">
          <el-form-item label="所属机构" prop="institution">
                <el-input clearable v-model="queryParams.institution" placeholder="请输入机构代号"></el-input>
          </el-form-item>
          <el-form-item>
              <el-button type="primary" @click="handleQuery(queryParams, getDataByInstitution)">
                  搜索
              </el-button>
              <el-button @click="resetQuery(queryParams, getDataByInstitution)">
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
                  <el-table-column prop="id" label="id" width="100"></el-table-column>
                  <el-table-column prop="code" label="节点代码"  width="150"></el-table-column>
                  <el-table-column prop="institution" label="所属机构"  width="150"></el-table-column>
                  <el-table-column prop="nodeType" label="节点类型"  width="150"></el-table-column>
                  <el-table-column prop="name" label="节点名称"  width="150"></el-table-column>
                  <el-table-column prop="isLeaf" label="是否为叶节点" width = "150"></el-table-column>
                  <el-table-column prop="pcode" label="父节点代码"  width="150"></el-table-column>
                  <el-table-column prop="pid" label="父节点id"  width="150"></el-table-column>                 
                  <el-table-column prop="relationType" label="节点属性"  width="150"></el-table-column>                 
                  <el-table-column prop="source" label="源节点"  width="150"></el-table-column>
                  <el-table-column prop="sink" label="目标节点"  width="150"></el-table-column>
                  <el-table-column prop="description" label="描述"  width="150"></el-table-column>
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
                        <el-form-item label="属性">
                            <el-radio-group v-model="rType" @change="changeCheckType">
                                <el-radio-button :label="0">实物节点</el-radio-button>
                                <el-radio-button :label="1">影响边节点</el-radio-button>
                            </el-radio-group>
                        </el-form-item>

                        <el-form-item label="节点类型" prop="pcode" v-show="rType !=1">
                            <el-select style="width: 100%" v-model="form.nodeType" placeholder="" clearable>
                                <el-option style="height: auto" v-for="item in thingTypeList.value" :key="item.id" :label="item.code"
                                    :value="item.code">
                                    <p style="margin: 0">{{ item.code }}</p>
                                    <span class="ve_select_option_slot">
                                        描述：{{ item.name }}
                                    </span>
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="节点类型" prop="pcode" v-show="rType !=0">
                            <el-select style="width: 100%" v-model="form.nodeType" placeholder="" clearable>
                                <el-option style="height: auto" v-for="item in relationTypeList.value" :key="item.id" :label="item.code"
                                    :value="item.code">
                                    <p style="margin: 0">{{ item.code }}</p>
                                    <span class="ve_select_option_slot">
                                        描述：{{ item.name }}
                                    </span>
                                </el-option>
                            </el-select>
                        </el-form-item>
                        
                        <el-form-item label="节点代码:" prop="code">
                            <el-input v-model="form.code" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="节点名称:" prop="name">  
                            <el-input v-model="form.name" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="父节点代码:" prop="pcode" >  
                            <el-input v-model="form.pcode" placeholder="" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="父节点id:" prop="pid" >  
                            <el-input v-model="form.pid" placeholder="" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="是否为叶节点:" prop="isLeaf" >  
                            <el-input v-model="form.isLeaf" placeholder="true or false" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="所属机构:" prop="institution" >  
                            <el-input v-model="form.institution" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="源节点:" prop="source" v-show="rType !=0">  
                            <el-input v-model="form.source" placeholder="请输入" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="汇节点:" prop="sink" v-show="rType !=0">  
                            <el-input v-model="form.sink" placeholder="请输入" clearable></el-input>
                        </el-form-item>

                        <el-form-item label="节点描述:" prop="description">
                            <el-input v-model="form.description" placeholder="" clearable></el-input>
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
          description: "评估节点管理",
      }),
  };
</script>

<script setup>
  import axiosUtil from '@/utils/axiosUtil'
  import { getType,getTree,addNode} from '@/api/eval/evalNodeDef.js'
  import base from '@/utils/base'
  import { computed, reactive } from 'vue';
  import  {getObjNodeIndexList,axiosget,axiosput,axiosPost,axiosDelete}  from '@/api/eval/nodeIndexEval'
  
  //请求路径
  const baseUrl = ref("api/eval/evalnode")
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
        baseDelete,
        GetListByInstitution
    } = base()
  //查询参数
  const queryParams = reactive({
      //创建时间
      dateRange: []
  });
  //引用表单参数
  const thingTypeList = reactive([]);
  const relationTypeList = reactive([]);
  const formRef = ref(null);
  const rType = ref(0);
  // 表单参数
  const form = reactive({
      /** */
          id: null,
      /** */
          code: "",
          pcode:"",
          pid:"",
      /** */
          institution:"",
          name: "",
      /** */
          description: "",
          nodeType:"",
      /** */
          source:"",
          sink:"",
          isLeaf: "",
      /** */
          relationType: "thing",  //默认为thing，实物节点
  });
  //表单校验
  const rules = computed(()=>({
    nodeType:[
        {
            required:true,
            message:"请选择节点类型",
            trigger:['blue',"change"],
        }
    ],
    code:[
        {
            required:true,
            message:"请输入节点代码",
            trigger:"blue",
        }
    ],
    name:[
        {
            required:true,
            message:"请输入节点名称",
            trigger:"blue",
        }
    ],
    institution:[
        {
            required:true,
            message:"请输入所属机构名称",
            trigger:"blue",
        }
    ],
    source:[
        {
            required:true,
            message:"请输入源节点",
            trigger:"blue",
        }
    ],
    sink:[
        {
            required:true,
            message:"请输入汇节点",
            trigger:"blue", 
        }
    ],

  }));



  //el-radio-group切换函数
  const changeCheckType = (val) => {
    nextTick(() => {
        formRef.value.resetFields();
    });
    val == 2 && (icon.value = "");
    console.log("增加框切换type")

    };


  /** 提交按钮 */
  function submitForm() {
      formRef.value.validate(valid => {
            if(rType.value !=0){
                form.relationType = 'relation';
            }
            console.log('这是nodeDef中的form参数',form);
            if(form.id != null){
                axiosput(baseUrl.value+'/edit',form).then(res=>{
                    open.value = false
                    getDataList()
                })
            }else{
                baseAdd(baseUrl.value, form)
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
    //   form.value= {
    //       id: null,
    //       code: "",
    //       pcode:"",
    //       name: "",
    //       institution:"",
    //       description: "",
    //       nodeType:"",
    //       source:"",
    //       sink:"",
    //       value: "",
    //       relationType: "thing",  //默认为thing，实物节点
    //   };
      form.id = null;
      form.code = "";
      form.pcode = "";
      form.name = "";
      form.institution = "";
      form.description = "";
      form.nodeType = "";
      form.isLeaf = "";
      form.source = "";
      form.sink = "";
      form.value = "";
      form.relationType = "thing";
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
      //const id = row.id || ids;
      //open.value = true
      //表单操作
    //   if(row.relationType == 'thing')
    //     rType.value = 0;
    //   else
    //     rType.value = 1;
    //   form.relationType = row.relationType;
    //   form.nodeType = row.nodeType;
    //   form.code = row.code;
    //   form.name = row.name;
    //   form.pcode = row.pcode;
    //   form.institution = row.institution;
    //   form.source = row.source;
    //   form.sink = row.sink;
    //   form.description = row.description;  
      // baseGet(baseUrl.value, form, id, '修改')

      //新的方法
      const id = row.id || ids;
      open.value = true
      Object.keys(row).forEach(key=>{
        if(key != '_X_ROW_KEY'){
            form[key] = row[key];
        }
        if(form.relationType != 'thing'){
            rType.value = 1;
        }else{
            rType.value = 0;
        }
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
      console.log("这是所删除信息的内容")
      console.log(array)
      baseDelete(baseUrl.value, array)
  };

  /**
   * 初始化查询参数
   */
  function getDataList() {
      queryList.value = []
      proxy.setQueryParams("name", "like", queryParams.institution, queryList.value)
      if (queryParams.dateRange.length > 0) {
          proxy.setQueryParams("create_time", "date(>=)", queryParams.dateRange[0], queryList.value)
          proxy.setQueryParams("create_time", "date(<=)", queryParams.dateRange[1], queryList.value)
      }
        // const data = { 
        //     institution: queryParams.institution
        //     // nodeType: form.nodeType
        // }
        // console.log(data)
        // GetListByInstitution(baseUrl.value, data,tablePage)
      baseGetList(baseUrl.value, tablePage)
    
  }
  //获取type表单
  function getTypeList(){
    getType(typeUrl.value+"/typeAll/thing").then(res=>{
        thingTypeList.value = res.data.tableList;
    })
    getType(typeUrl.value+"/typeAll/relation").then(res=>{
        relationTypeList.value = res.data.tableList;
    })
  }
  function getDataByInstitution(){
    const data = { 
            institution: queryParams.institution
            // nodeType: form.nodeType
        }
    console.log(data)
    axiosget(baseUrl.value+'/institution/'+data.institution).then(res=>{
        total.value = res.data.total
        tableData.value = res.data.tableList;
    })
  }
  
  getDataList();
  getTypeList();


</script>
<style lang="scss" scoped>
</style>