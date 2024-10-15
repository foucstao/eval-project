<template>
    <div class="ve_home">
        <el-col :span="24" style="padding-top: 20px">
            <el-card>
                <el-button type="primary" @click="initLines('张三', '#67C23A', 0)"
                    @dblclick="clearLine('张三')">张三</el-button>
                <el-button type="primary" @click="initLines('李四', '#409EFF', 1)"
                    @dblclick="clearLine('李四')">李四</el-button>
                <el-button type="primary" @click="initLines('王五', '#E6A23C', 2)"
                    @dblclick="clearLine('王五')">王五</el-button>
                <el-button type="primary" @click="initLines('胡家浩', '#F56C6C', 3)"
                    @dblclick="clearLine('胡家浩')">胡家浩</el-button>
                <el-button type="primary" @click="initLines('clear', '#F56C6C', null)">全部清除</el-button>
                <el-button type="primary" @click="errorClick()" @dblclick="errorClick2">报错闪烁</el-button>
                <p slot="title" style="font-weight: bold">厂房区域人员行进图</p>
                <div ref="barechartRef" style="height: 600px;width: 600px;"></div>
            </el-card>
        </el-col>
    </div>

</template>

<script setup>
import * as echarts from 'echarts';
import axiosUtil from '@/utils/axiosUtil'
const { proxy } = getCurrentInstance();
const barechartRef = ref(null)
const barechart = ref('');

const coords = ref()

const seriesPer = ref([])
const regionsList = ref([])
const initCharts = () => {
    buildBarChart();
}

//初始化地图
const buildBarChart = () => {
    barechart.value = echarts.init(barechartRef.value);
    echarts.registerMap('MacOdrum-LV5-floorplan-web', {
        svg: '<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="600" height="600"><g name="0"><path d="M-2.3283064365386963e-10,64.23788322490873L599.9999999997672,64.23788322490873L599.9999999997672,535.7621167748002L0,535.7621167751495Z" stroke-width="0.5" stroke="#000000" fill="transparent"></path></g><g name="1"><path d="M43,113L99,113L99,206L43,206Z" stroke-width="0.5" stroke="#000000" fill="transparent"></path></g><g name="2"><path d="M45.104293315671384,289.7504605826107L107.97507498553023,289.7504605826107L107.97507498553023,403.1894444581121L45.104293315904215,403.18944445846137Z" stroke-width="0.5" stroke="#000000" fill="transparent"></path></g><g name="3"><path d="M329.38570273155347,120.27457002358278L329.38570273155347,198.1789380532573L168.11035905987956,198.1789380532573L168.11035905987956,120.27457002358278Z" stroke-width="0.5" stroke="#000000" fill="transparent"></path></g><g name="4"><path d="M161.27735224319622,314.35171493625967L336.22006584657356,314.35171493625967L336.22006584657356,390.8888555170852L161.27735224319622,390.8888555170852Z" stroke-width="0.5" stroke="#000000" fill="transparent"></path></g><g name="5"><path d="M422.3242670481559,124.37475483800517L548.06453170022,124.37475483800517L548.06453170022,191.34522902517347L422.3242670481559,191.34522902517347Z" stroke-width="0.5" stroke="#000000" fill="transparent"></path></g></svg>'
    });
    const option = {
        title: {
            text: '人员行进路线',
            left: 'center',
            bottom: 10
        },
        tooltip: {},
        //使用二维坐标系地图
        geo: {
            map: 'MacOdrum-LV5-floorplan-web',
            roam: true,
            emphasis: {
                itemStyle: {
                    color: undefined
                },
                label: {
                    show: false
                }
            },
            regions: regionsList.value,
        },
        //二维数组路径点（通过点 绘制线）
        series: seriesPer.value
    };
    barechart.value.setOption(option);
}

//
function errorClick() {
    regionsList.value = []
    regionsList.value.push({
        name: '1',
        silent: true,
        itemStyle: {
            color: '#bf0e08'
        },
    })
    buildBarChart()
}

function errorClick2() {
    regionsList.value = []
    regionsList.value.push({
        name: '1',
        silent: true,
        itemStyle: {
            color: '#FFFFFF'
        },
    })
    buildBarChart()
}


//获取随机点
function getMockNums() {
    const point = []
    for (let i = 0; i < 2; i++) {
        point.push(Math.floor(Math.random() * (600 - 100)) + 100)
    }
    return point;
}

//清除全部人员路线
function clearLines() {
    seriesPer.value = []
    barechart.value.setOption({
        title: {
            text: '人员行进路线',
            left: 'center',
            bottom: 10
        },
        tooltip: {},
        geo: {
            map: 'MacOdrum-LV5-floorplan-web',
            roam: true,
            emphasis: {
                itemStyle: {
                    color: undefined
                },
                label: {
                    show: false
                }
            },
            regions: [],
        },
        series: []
    }, true);
}

//清除指定人员路线
function clearLine(name) {
    console.log(seriesPer)
    seriesPer.value = seriesPer.value.filter(item => {
        return item.name !== name;
    })
    proxy.globalMessage(500, name + "的路线，清除绘制");
    barechart.value.setOption({}, true);
    buildBarChart()
    console.log(seriesPer.value)
}
const peoName = ref("");
//显示人员路线
function initLines(name, color, index) {
    peoName.value = name
    axiosUtil.axiosGet("api/v1/active/query/" + name).then(res => {
    })
}

//检查是否已经存在
function checkPush(name) {
    let result = false
    seriesPer.value.forEach(item => {
        if (item.name == name) {
            result = true
            return result;
        }
    })
    return result;
}
//绘制路线
function initPeoData(pname, color, coordsData) {
    const data = {
        name: pname,
        type: 'lines',
        coordinateSystem: 'geo',
        geoIndex: 0,
        emphasis: {
            label: {
                show: false
            }
        },
        polyline: true,
        lineStyle: {
            color: color,
            width: 5,
            opacity: 1,
            type: 'dotted'
        },
        effect: {
            show: true,
            period: 8,
            color: color,
            constantSpeed: 80,
            trailLength: 0,
            symbolSize: [40, 20],
            symbol:
                'path://M35.5 40.5c0-22.16 17.84-40 40-40s40 17.84 40 40c0 1.6939-.1042 3.3626-.3067 5H35.8067c-.2025-1.6374-.3067-3.3061-.3067-5zm90.9621-2.6663c-.62-1.4856-.9621-3.1182-.9621-4.8337 0-6.925 5.575-12.5 12.5-12.5s12.5 5.575 12.5 12.5a12.685 12.685 0 0 1-.1529 1.9691l.9537.5506-15.6454 27.0986-.1554-.0897V65.5h-28.7285c-7.318 9.1548-18.587 15-31.2715 15s-23.9535-5.8452-31.2715-15H15.5v-2.8059l-.0937.0437-8.8727-19.0274C2.912 41.5258.5 37.5549.5 33c0-6.925 5.575-12.5 12.5-12.5S25.5 26.075 25.5 33c0 .9035-.0949 1.784-.2753 2.6321L29.8262 45.5h92.2098z'
        },
        data: [
            {
                coords: coordsData
            }
        ]
    }

    return data;
}

//ws连接
const websocket = ref(null)
const wsUrl = ref('ws://localhost:8007/ws/notice/1');//链接地址
const initWebSocket = () => {//初始化
    websocket.value = new WebSocket(wsUrl.value);
    websocket.value.onopen = () => {
        console.log("连接成功");
    };
    websocket.value.onmessage = (e) => {
        const data = JSON.parse(e.data)
        const peo = data.data
        if (peoName.value == "clear") {
            clearLines()
        } else {
            proxy.globalMessage(200, peo.name + "的路线，开始绘制");
            seriesPer.value.push(initPeoData(peo.name, peo.color, eval('(' + peo.coords + ')')))
            buildBarChart()
        }
    }
    websocket.value.onerror = (e) => {
        console.log(e);
    };
};

//关闭链接（在页面销毁时可销毁链接）
const closeWebSocket = () => {
    websocket.value.close();
};
onBeforeUnmount(() => {
    closeWebSocket()
})
initWebSocket()
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
