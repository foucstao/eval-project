<template>
  <div>
    <h1>评估节点管理界面</h1>
    <div class="tableList" >
      <el-card >
        <el-table :data="nodeTypeList.value" style="width: 100%;">
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
          <!-- <el-table-column type="selection" width="55" align="center"/>
                    <el-table-column prop="id" label="id" width="100"></el-table-column>
                    <el-table-column prop="code" label="等级代码"  width="150"></el-table-column>
                    <el-table-column prop="name" label="等级名称"  width="150"></el-table-column>
                    <el-table-column prop="description" label="等级描述"  width="150"></el-table-column>
                    <el-table-column prop="value" label="等效数值"  width="150"></el-table-column>
                    <el-table-column prop="version" label="版本号"  width="150"></el-table-column>
            <el-table-column fixed="right" label="操作"  width="200">
                <template v-slot:default="{ row }" >
                    <el-button @click.prevent="handleEdit(row)" type="primary" size="small">
                        修改
                    </el-button>
                    <el-button @click.prevent="handleDelete(row)" type="danger" size="small">
                        删除
                    </el-button>
                </template>
            </el-table-column> -->
        </el-table>
      </el-card>
    </div>
    
  </div>

</template>


<script>
export default {
  data: () => ({
      description: "测试",
  }),
};
</script>

<script setup>
import {getTree,getType } from '@/api/eval/evalNodeDef'
import {reactive } from 'vue';
// import TreeEdit from './components/TreeEdit.vue';
//请求路径
const nodeUrl = "api/eval/evalnode"
const typeUrl = "api/eval/nodeType"

//树状参数
const nodeTreeData  = reactive([])
const edgeTreeData = reactive([])
const nodeTypeList = reactive([])
const edgeTypeList = reactive([])

const nodeTableData = reactive([]) 
const edgeTableData = reactive([])

const visibleN = ref(false)
const visibleE = ref(false)


const formN = {
id:'',
pid:'',
label:'',
type:''
}
const formE={
id:'',
pid:'',
label:'',
type:'',
from:'',
to:''
}

function appendN(data){
  visibleN.value.dialogVisble=true
}
function appendE(){
  visibleE.value.dialogVisble = true
}
// const handelDialog = async (e) => {
//     visibleN.value = e;
// };


//tree参数
const defaultProps={
  children:'children',
  label:'name',
  id:'id'
}


//获取node tree参数
function getNodeTree(){
getTree(nodeUrl+"/typeAll/thing").then(res =>{
  nodeTreeData.value = res.data.tableList;

})
}
function getEdgeTree(){
getTree(nodeUrl+"/typeAll/relation").then(res =>{
  edgeTreeData.value = res.data.tableList;

})
}

function getNodeType(){
getType(typeUrl+"/typeAll/thing").then(res=>{
  nodeTypeList.value = res.data.tableList;
  const total = res.data.total;
  //操作一下添加树
  nodeTypeList.value[0].children=[];
  const newChild = {
    id:-1,
    name:'new child', 
    children:[]
  }
})
}

function getEdgeType(){
getType(typeUrl+"/typeAll/relation").then(res=>{
  edgeTypeList.value = res.data.tableList;

})
}
//进页面执行函数

getNodeTree()
getEdgeTree()
getNodeType()
getEdgeType()



</script>


<style lang="scss" scoped>
</style>


