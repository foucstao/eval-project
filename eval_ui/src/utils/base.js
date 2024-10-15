import axiosUtil from '@/utils/axiosUtil'
import qs from 'qs'     


export default function base() {
	//全局代理(等于vue2中this)
	const { proxy } = getCurrentInstance();
	// 选中数组
	const ids = ref([]);
	//总条目
	const total = ref(0);
	
	
	//列表数据
	const tableData = ref([]);
	//查询条件集合
	const queryList = ref([]);
	//查询表单参数
	const queryForm = ref(null);	
	// 非多个禁用
	const multiple = ref(true);
	// 是否显示弹出层
	const open = ref(false);
	//分页参数
	const tablePage = reactive({
		pageNum: 1,
		pageSize: 200
	})
	//弹出层标题
	const title = ref("");

	//基础获取列表
	const baseGetList = (url, tablePage) => {
		const data = {
			queryList: queryList.value
		}
		axiosUtil.axiosPost(url + `/page?pageNum=` + 
		tablePage.pageNum + "&pageSize=" + tablePage.pageSize, data).then(res => {
			total.value = res.data.total
			tableData.value = res.data.tableList;
		})
	}

	// 通过type来获得后端数据
	const getListByType = (url, type, tablePage) => {
		const data = {
			queryList: queryList.value
		}
		axiosUtil.axiosPost(url + `/typeAll/`+ type + `?pageNum=` + 
		tablePage.pageNum + "&pageSize=" + tablePage.pageSize, data).then(res => {
			total.value = res.data.total
			tableData.value = res.data.tableList;
		})
	}

	//基础获取列表
	const GetListByObj = (url, obj) => {
		url = url+"/obj"; //api接口
		axiosUtil.axiosGet2(url,obj).then(res=>{
			total.value = res.data.total
			tableData.value = res.data.tableList;
		})
	}

	const GetListByInstance=(url,instance)=>{
		url = url+"/instance"; //api接口
		axiosUtil.axiosGet2(url,instance).then(res=>{
			total.value = res.data.total
			tableData.value = res.data.tableList;
		})
	}
	const GetListByInstitution = (url,data,tablePage)=>{
		axiosUtil.axiosPost(url + `/institution?pageNum=` + 
		tablePage.pageNum + "&pageSize=" + tablePage.pageSize, 
		data).then(res => {
			total.value = res.data.total
			tableData.value = res.data.tableList;
		})
	}

	const GetListByQuery = (url, data, tablePage) => {
		axiosUtil.axiosPost(url + `/query?pageNum=` + 
		tablePage.pageNum + "&pageSize=" + tablePage.pageSize, 
		data).then(res => {
			total.value = res.data.total
			tableData.value = res.data.tableList;
		})
	}

	//基础getInfo
	function baseGet(url, form, id, data) {
		axiosUtil.axiosGet(url + "/" + id, null).then(res => {
			form.value = res.data.data
			open.value = true;
			title.value = data;
		})
	}

	//基础putInfo
	const baseUpdate = (url, data) => {
		axiosUtil.axiosPut(url, data).then(res => {
			proxy.globalMessage(res.status, res.message);
			open.value = false;
			baseGetList(url, tablePage)
		})
	}

	//基础addInfo
	const baseAdd = (url, data) => {		
		axiosUtil.axiosPost(url+"/add", data).then(res => {
			proxy.globalMessage(res.status, res.message);
			open.value = false;
			baseGetList(url, tablePage)
		})
	}

	//基础deleteInfo
	const baseDelete = (url, ids) => {
		proxy
			.$confirm('是否确认删除编号为"' + ids + '"的数据项?', "警告", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			})
			.then(() => {
				return axiosUtil.axiosDelete(url + "/remove/" + ids)
			}).then((response) => {
				proxy.globalMessage(response.status, response.message);
				baseGetList(url, tablePage);
			});
	}

	//定义的新的delet方法，用来防止出现没有basegetlist的问题
	const baseDelete_2 = (url, ids) => {
		proxy
			.$confirm('是否确认删除编号为"' + ids + '"的数据项?', "警告", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			})
			.then(() => {
				return axiosUtil.axiosDelete(url + "/remove/" + ids)
			}).then((response) => {
				proxy.globalMessage(response.status, response.message);
			});
	}
	return {
		ids,
		total,
		tableData,
		queryList,
		queryForm,
		multiple,
		open,
		tablePage,
		title,
		proxy,
		baseGetList,
		GetListByObj,
		GetListByInstance,
		GetListByQuery,
		getListByType,
		baseGet,
		baseUpdate,
		baseAdd,
		baseDelete,
		baseDelete_2,
		GetListByInstitution
	}

}