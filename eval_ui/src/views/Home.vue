<template>
    <div class="ve_home">
        <el-row :gutter="20" style="padding-bottom: 10px">
            <el-col :span="12">
                <div class="ve-card ve_card1">
                    <el-icon>
                        <football />
                    </el-icon>
                    <div>
                        <p>菜单数量</p>
                        <span>6</span>
                    </div>
                </div>
            </el-col>
            <el-col :span="12">
                <div class="ve-card ve_card2">
                    <el-icon>
                        <user />
                    </el-icon>
                    <div>
                        <p>用户数量</p>
                        <span>6</span>
                    </div>
                </div>
            </el-col>
        </el-row>
        <el-row :gutter="20" style="padding-top: 10px">
            <el-col :span="12">
                <div class="ve-card ve_card3">
                    <el-icon>
                        <ice-cream />
                    </el-icon>
                    <div>
                        <p>角色数量</p>
                        <span>6</span>
                    </div>
                </div>
            </el-col>
            <el-col :span="12">
                <div class="ve-card ve_card4">
                    <el-icon>
                        <document />
                    </el-icon>
                    <div>
                        <p>资料数量</p>
                        <span>6</span>
                    </div>
                </div>
            </el-col>
            <el-col :span="10" style="padding-top: 20px">
                <el-card>
                    <p slot="title" style="font-weight: bold">今日识别概况</p>
                    <div ref="pieechartRef" style="height: 400px"></div>
                </el-card>
            </el-col>
            <el-col :span="14" style="padding-top: 20px">
                <el-card>
                    <p slot="title" style="font-weight: bold">近七日识别概况</p>
                    <div ref="barechartRef" style="height: 400px"></div>
                </el-card>
            </el-col>
        </el-row>
    </div>

</template>

<script setup>
import * as echarts from 'echarts';

const blackCountList = reactive([]);
const whiteCountList = reactive([]);
const unkonwCountList = reactive([]);

const barechartRef = ref(null)
const pieechartRef = ref(null)

const barechart = ref('');
const pieechart = ref('');

const initCharts = () => {
    unkonwCountList.value = getMockData(7)
    whiteCountList.value = getMockData(7)
    blackCountList.value = getMockData(7)
    const timeList = getMockTime(7)
    const legendData = ['黑名单记录', '白名单记录', '未知人员记录'];
    const bgColorList = ['#ed4014', '#19be6b', '#ff9900'];
    buildBarChart(legendData, timeList, bgColorList);
    buildPieChart(legendData, bgColorList)
}
const getMockData = (num) => {
    const data = []
    for (var i = 0; i < num; i++) {
        data.push(Math.round(Math.random() * 100))
    }
    return data;
}
const getMockTime = (num) => {
    const data = []
    for (var i = num - 1; i >= 0; i--) {
        data.push(getJumpDate(i))
    }
    return data;
}

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

const getJumpDate = (num, time) => {
    let n = num;
    let d = '';
    if (time) {
        d = new Date(time);
    } else {
        d = new Date();
    }
    let year = d.getFullYear();
    let mon = d.getMonth() + 1;
    let day = d.getDate();
    if (day <= n) {
        if (mon > 1) {
            mon = mon - 1;
        } else {
            year = year - 1;
            mon = 12;
        }
    }
    d.setDate(d.getDate() - n);
    year = d.getFullYear();
    mon = d.getMonth() + 1;
    day = d.getDate();
    let s = year + "-" + (mon < 10 ? ('0' + mon) : mon) + "-" + (day < 10 ? ('0' + day) : day);
    return s;
}

onMounted(() => {
    initCharts()
});
</script>

<style lang="scss" scoped>
.el-row {
    height: 50%;

    padding-right: 10px;
}

.ve-card {
    border-radius: 10px;
    height: 100%;
    display: flex;
    align-items: center;
    transition: all 500ms;
    color: #fff;

    &:hover {
        box-shadow: 3px 3px 6px 1px rgba(0, 0, 0, 0.2);
        background: #fff;
    }

    i {
        font-size: 100px;
        margin: 0 20px;
    }

    div {
        flex: 1;
        padding-right: 12px;

        p {
            margin: 0;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        span {
            font-size: 60px;
            font-weight: bold;
        }
    }
}

.ve_card1 {
    background: #409eff;

    &:hover {
        color: #409eff;
    }
}

.ve_card2 {
    background: #67c23a;

    &:hover {
        color: #67c23a;
    }
}

.ve_card3 {
    background: #e6a23c;

    &:hover {
        color: #e6a23c;
    }
}

.ve_card4 {
    background: #f56c6c;

    &:hover {
        color: #f56c6c;
    }
}
</style>
