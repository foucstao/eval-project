# 态势评估系统WEB版



页面路由组织结构

![image-20231108172520490](assets/image-20231108172520490.png)

基于vue3+springboot开发

## 一、文件架构

* eval_ui  -- 前端交互组件
* eval_service --  后端逻辑服务组件
* eval_service/sql --  数据库建表文件

## 二、修改记录
9.16 采用了郑大发的新版本（基于JDK17和redis做消息缓存），替换了原版本（基于JDK8和ActiveMQ）。
旧版本重命名为eval_service_old（保留，但不再更新）
相应地，eval_ui 是郑大发的新版本，可以编译启动，建议基于此版本开发。
旧版本重命名为 eval_ui_old（保留，但不再更新）。

