<template>
	<div>
		<el-row :gutter="24">
			<el-col :span="20">
				<el-card shadow="hover">
					<template #header>
						<div class="clearfix">
							<span>评估实例参数文件上传</span>
						</div>
					</template>
					<el-form label-width="240px" ref="formRef" :model="form" :rules="rules" :inline="false"
						:validate-on-rule-change="false">
						<el-form-item label="用户名："> {{ name }} </el-form-item>
						<el-form-item label="文件路径：" prop="old">
							<el-input type="password" v-model="form.old"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" @click="onSubmit">确认上传    </el-button>
						</el-form-item>
					</el-form>
				</el-card>
				<el-card shadow="hover">
					<template #header>
						<div class="clearfix">
							<span>节点信息文件上传</span>
						</div>
					</template>
					<el-form label-width="240px" ref="formRef" :model="form" :rules="rules" :inline="false"
						:validate-on-rule-change="false">
						<el-form-item label="用户名："> {{ name }} </el-form-item>
						<el-form-item label="文件路径：" prop="old">
							<el-input type="password" v-model="form.old"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" @click="onSubmit">确认上传    </el-button>
						</el-form-item>
					</el-form>
				</el-card>
				<el-card shadow="hover">
					<template #header>
						<div class="clearfix">
							<span>节点指标值输入文件上传</span>
						</div>
					</template>
					<el-form label-width="240px" ref="formRef" :model="form" :rules="rules" :inline="false"
						:validate-on-rule-change="false">
						<el-form-item label="用户名："> {{ name }} </el-form-item>
						<el-form-item label="文件路径：" prop="old">
							<el-input type="password" v-model="form.old"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" @click="onSubmit">确认上传    </el-button>
						</el-form-item>
					</el-form>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script>
export default {
	data: () => ({
		description: "配置文件输出与输入",
	}),
}
</script>
<script setup>

import request from '@/plugins/axios'
import { useRouter } from 'vue-router';
let router = useRouter();
const formRef = ref(null);
const name = sessionStorage.uname
const { proxy } = getCurrentInstance();
const form = reactive({
	old: "",
	new: "",
});
//表单校验
const rules = {
	// old: [
	// 	{
	// 		required: true,
	// 		message: "请输入原密码",
	// 		trigger: "blur",
	// 	},
	// ],
	// new: [
	// 	{
	// 		required: true,
	// 		message: "请输入新密码",
	// 		trigger: "blur",
	// 	},
	// ],
};
const onSubmit = () => {

	formRef.value.validate(valid => {
		if (valid) {
			const user = {
				'name': name,
				'password': form.old
			};
			request.post(`api/v1/user/checkPwd`, user).then(response => {
				let sysUser = response.data.sysUser;
				if (response.status !== 200)
					proxy.globalMessage(response.status, "原密码错误");
				else {
					sysUser.password = form.new;
					request.put(`api/v1/user`, sysUser).then(response => {
						proxy.globalMessage(200, "修改成功，请使用新密码登录")
						router.push({ name: 'Login' });
					})
				}
			})
		} else {
			this.btnLoading = false;
			this.$Message.error("表单验证失败");
		}
	});


};
</script>

<style scoped>
.info {
	text-align: center;
	padding: 35px 0;
}

.info-image {
	position: relative;
	margin: auto;
	width: 100px;
	height: 100px;
	background: #f8f8f8;
	border: 1px solid #eee;
	border-radius: 50px;
	overflow: hidden;
}

.info-image img {
	width: 100%;
	height: 100%;
}

.info-edit {
	display: flex;
	justify-content: center;
	align-items: center;
	position: absolute;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.5);
	opacity: 0;
	transition: opacity 0.3s ease;
}

.info-edit i {
	color: #eee;
	font-size: 25px;
}

.info-image:hover .info-edit {
	opacity: 1;
}

.info-name {
	margin: 15px 0 10px;
	font-size: 24px;
	font-weight: 500;
	color: #262626;
}

.crop-demo-btn {
	position: relative;
}

.crop-input {
	position: absolute;
	width: 100px;
	height: 40px;
	left: 0;
	top: 0;
	opacity: 0;
	cursor: pointer;
}
</style>