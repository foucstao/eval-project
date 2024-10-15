<template>
    <div class="ve_container" >
        <h1>模型评价输出界面</h1>
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="queryParams">
            <!-- 这里修改为下拉框 -->
            <el-form-item label="评估实例名称" prop="instance">
                <el-select style="width: 100%" v-model="queryParams.instance" placeholder="" clearable>
                    <el-option style="height: auto" v-for="item in InstanceList.value" :key="item.id" :label="item.evalInstance"
                                    :value="item.evalInstance">
                                    <p style="margin: 0">{{ item.evalInstance }}</p>
                                    <span class="ve_select_option_slot">
                                        评价对象：{{ item.evalObjCode }}
                                    </span>
                    </el-option>
                </el-select>         
            </el-form-item>
            <el-form-item>
               <el-button type="primary" @click="genIndexResultTable">
                    计算生成评估结果
                </el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="danger" @click="genTotalResult">
                    生成评估表单
                </el-button>
            </el-form-item>
        </el-form>

        <div class="table" v-if="tableOpen">
            <h3>指标层评估结果</h3>
            <ve-table :table="{
                data: indexResultData,
                onSelectionChange: (val) => handleSelectionChange(val)
            }"  >
                <el-table-column type="selection" width="55" align="center"/>
                        <el-table-column prop="id" label="id"  width="50"></el-table-column>
                        <el-table-column prop="indexCode" label="指标名称"  width="100"></el-table-column>
                        <el-table-column prop="nodeCode" label="所属节点" width="100"></el-table-column>
                        <el-table-column prop="inputValue" label="输入值"  width="100"></el-table-column>
                        <el-table-column prop="rating" label="等级"  width="100"></el-table-column>
                        <el-table-column prop="evalValue" label = "评估结果" width = "100"></el-table-column>
                        <el-table-column prop="evalInstance" label="实例名称"  width="100"></el-table-column>
                        <el-table-column prop="evalObjCode" label="评估对象"  width="100"></el-table-column>
                        <el-table-column prop="indexModel" label="实例名称"  width="100"></el-table-column>
                        <el-table-column prop="membership" label="函数模型"  width="100"></el-table-column>
            </ve-table>
            <h3>节点层评估结果</h3>
            <ve-table :table="{
                data: NodeResultData,nodeVec,nomalizeclass,navigationpregation,
                onSelectionChange: (val) => handleSelectionChange(val)
            }"  >
                <el-table-column type="selection" width="55" align="center"/>
                        <el-table-column prop="id" label="id"  width="50"></el-table-column>
                        <el-table-column prop="evalInstance" label="评估实例名称"  width="150"></el-table-column>
                        <el-table-column prop="evalObjCode" label="评估对象名称"  width="150"></el-table-column>
                        <el-table-column prop="nodeCode" label="节点名称"  width="150"></el-table-column>
                        <el-table-column prop="rating" label="等级"  width="150"></el-table-column>
                        <el-table-column prop="evalValue" label="评估值"  width="150"></el-table-column>
                        <el-table-column prop="description" label="相关描述"  width="150"></el-table-column>
            </ve-table>
        </div>
        <el-dialog :title="title" width="800px" append-to-body destroy-on-close :model-value="dialogOpen" @close="closeAlert">
            <span style="font-size: 20px; color:rgb(0, 10, 10)">
                态势评估系统：整体风险评估<br>
                从每个评估要素节点的风险值取最高，得到整个系统（产线）的风险值（原理是木桶理论，风险最高的节点决定了系统的风险）。<br><br>
                据评估结果可知：<br>
                <u1>
                    <li v-for="item in ObjResultData.value" :key="item.id">
                        {{ item.evalObjCode }} 的评价结果为：{{ item.rating }}
                    </li>
                </u1>
            </span>
            <br>
            <span style="font-size: 20px; color:red">
                {{queryParams.instance}}评估结果为： {{ risk }}<br>
            </span>
            <template v-slot:footer>
                <span>
                    <el-button @click="closeAlert">确认</el-button>
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
    import  {getObjNodeIndexList,axiosget,axiosput,axiosPost}  from '@/api/eval/nodeIndexEval'
    import { reactive } from 'vue';

    const IndexResultUrl = ref("api/eval/nodeIndex")
    const InstanceUrl = ref("api/eval/objIndexModel")
    const NodeResultUrl = ref("api/eval/nodeEval")
    const ResultUrl = ref("api/eval/fuzzy")
    const ObjResultUrl = ref("api/eval/objEval")

    //导入基础
    const tableOpen = ref(false)
    const dialogOpen = ref(false)
    const InstanceList = reactive([])
    const indexResultData = ref([])
    const NodeResultData = ref([])
    const ObjResultData = reactive([])
    const total = ref(0)
    const indexvec = reactive([])
    const nodeVec = reactive([])
    const objVec = reactive([])
    const showTeleport = ref(false)
    const ratingList = reactive([])
    const risk = ref("")

    //查询参数
    const queryParams = reactive({
        //创建时间
        instance:"",
        dateRange: []
    });
    function closeAlert(){
        dialogOpen.value = false;
    }
    //生成评估结果函数
    function genTotalResult(){
        dialogOpen.value = true;
    }
    
    const form = 
        {    /** */
            id: null,
            //版本号        
            institution: "",
            evalInstance: "",
            evalObjCode: "",
            importanceVersion: "",
            indexModel: "",
            rateVersion:""
        };
    
    //fuzzy算法启动函数
    
    function fuzzyProcess(){
        form.evalInstance = queryParams.instance;
        //console.log(form)
        //计算权重
        //获取当前选取的instance的indexmodle信息
        
        
        axiosget(InstanceUrl.value+'/instance/'+form.evalInstance).then(res=>{
            //console.log(res.data.InstanceJson)
            form.indexModel = res.data.InstanceJson.indexModel;
            form.importanceVersion = res.data.InstanceJson.importanceVersion;  
            axiosPost(ResultUrl.value +'/genWeight',form).then(res=>{
                console.log(res.data.count)
                axiosPost(ResultUrl.value+'/index',form).then(res=>{
                    indexvec.value = res.data.result
                    axiosPost(ResultUrl.value+'/node',form).then(res=>{
                        nodeVec.value = res.data.result
                        axiosPost(ResultUrl.value+'/obj',form).then(res=>{
                            risk.value = res.data.result;
                        })
                    })
                })
            })
        })
    }
    function genIndexResultTable(){
        const data = {
            instance:queryParams.instance
        }
        //fuzzyProcess()
        form.evalInstance = queryParams.instance;
        // 嵌套进行api调用
        axiosget(InstanceUrl.value+'/instance/'+form.evalInstance).then(res=>{
            //console.log(res.data.InstanceJson)
            form.indexModel = res.data.InstanceJson.indexModel;
            form.importanceVersion = res.data.InstanceJson.importanceVersion;  
            //生成权重
            axiosPost(ResultUrl.value +'/genWeight',form).then(res=>{
                console.log(res.data.count)
                //生成index评估结果
                axiosPost(ResultUrl.value+'/index',form).then(res=>{
                    indexvec.value = res.data.result
                    //生成node评估结果
                    axiosPost(ResultUrl.value+'/node',form).then(res=>{
                        nodeVec.value = res.data.result
                        //生成obj评估结果
                        axiosPost(ResultUrl.value+'/obj',form).then(res=>{
                            risk.value = res.data.result;
                            //获取index表
                            axiosget(IndexResultUrl.value+'/instancesimple/'+data.instance).then(res=>{
                                total.value = res.data.total
                                indexResultData.value = res.data.tableList;
                                //获取node表
                                axiosget(NodeResultUrl.value+'/instance/'+data.instance).then(res=>{
                                    NodeResultData.value = res.data.tableList;
                                    //获取result表
                                    axiosget(ObjResultUrl.value+"/instance/"+data.instance).then(res=>{
                                        ObjResultData.value = res.data.tableList;
                                        tableOpen.value = true;
                                    })
                                })
                            })
                        })
                    })
                })
            })
        })
        
        
        //const item = reactive()
        

    }

    //获取instance列表
    function getInstanceList(){
        axiosget(InstanceUrl.value+'/page').then(res=>{
            InstanceList.value = res.data.tableList;
        })
    }
    


    //测试 生成隶属度向量
    // 获取数据
    getInstanceList()
    //getResult()
    //getIndexVec()
    //getNodeFuzzy()

    //getObjFuzzy()



    //直方图显示

const buildBarChart = (legendData, axisLabel, bgColorList) => {
    barechart.value = echarts.init(barechartRef.value);
    const labelOption = {
        show: true,
        position: 'top',
    }
    const labelWidth = 15;
    const option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'//阴影，若需要为直线，则值为'line'
            }
        },
        toolbox: {
            show: true,
            feature: {
                saveAsImage: { show: true }
            }
        },
        legend: { data: legendData, y: 'bottom' },
        grid: { left: '0%', right: '5%', bottom: '10%', top: '10%', containLabel: true, width: '90%' },
        xAxis: [{ min: 0, type: 'category', data: axisLabel }],
        yAxis: [{ min: 0, type: 'value', splitArea: { show: false } }],
        series: [
            {
                barWidth: labelWidth,//柱状条宽度
                name: legendData[0],
                type: 'bar',
                itemStyle: { show: true, color: bgColorList[0] },
                label: labelOption,
                data: blackCountList.value
            },
            {
                barWidth: labelWidth,
                name: legendData[1],
                type: 'bar',
                itemStyle: { show: true, color: bgColorList[1] },
                label: labelOption,
                data: whiteCountList.value
            },
            {
                barWidth: labelWidth,
                name: legendData[2],
                type: 'bar',
                itemStyle: { show: true, color: bgColorList[2] },
                label: labelOption,
                data: unkonwCountList.value
            }
        ]
    };
    barechart.value.setOption(option);
}
const buildPieChart = (legendData, bgColorList) => {
    pieechart.value = echarts.init(pieechartRef.value);
    const option = {
        legend: { data: legendData, y: 'bottom' },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        toolbox: {
            show: true,
            feature: {
                saveAsImage: { show: true }
            }
        },
        series: [
            {
                name: '今日识别概况',
                type: 'pie',
                radius: '65%',
                data: [
                    { value: blackCountList.value[6], name: legendData[0], color: bgColorList[0] },
                    { value: whiteCountList.value[6], name: legendData[1], color: bgColorList[1] },
                    { value: unkonwCountList.value[6], name: legendData[2], color: bgColorList[2] },

                ],
                roseType: 'angle',
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    },
                    show: true,
                    color: function (params) {
                        return bgColorList[params.dataIndex]
                    }
                }
            }
        ]
    };
    pieechart.value.setOption(option)
}

</script>
<style lang="scss" scoped>
</style>