
一、安装 jdk 17
本项目源码编译要求是jdk17

二、安装 Redis
新版本 改成 安装redis 教程见
https://blog.csdn.net/weixin_44893902/article/details/123087435
快速下载 https://blog.csdn.net/lampo1024/article/details/121602028

三、安装Maven包管理软件
首先安装maven 3.9.3 安装教程见网络。

四、安装vscode 代码编辑器


五、创建数据库和表
数据库名称定义eval，注意src/main/resource/application.yml 配置的数据库名称与此一致
先要运行sql文件的数据库脚本，创建数据库的表结构和数据。

注意，安装成功之后，一定要确认在windows服务列表中已经启动了mysql服务。否则连接不上数据库的。

六、拉取pom依赖包

通过pom.xml拉取springboot项目依赖包
vscode 打开项目会自动拉取依赖（注意联网状态）

七、vscode 配置springboot插件
启动
开始步骤：
在 Visual Studio Code 中打开扩展视图(Ctrl+Shift+X)。
输入“java”搜索商店扩展插件。
找到并安装Java Extension Pack (Java 扩展包)，如果你已经安装了Language Support for Java(TM) by Red Hat，也可以单独找到并安装Java Debugger for Visual Studio Code扩展。
输入“Spring Boot Extension”搜索商店扩展插件。
找到并安装 “Spring Boot Extension Pack”。安装过程中可能会比较慢，耐心等待即可。
————————————————
版权声明：本文为CSDN博主「bloglin99999」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_33043025/article/details/124640057

配置Maven：
点左下角的设置图标->设置，打开设置内容筛选框，输入maven，然后点击右侧的打开json格式setting：
{

 "workbench.iconTheme": "vscode-icons",
 "workbench.startupEditor": "newUntitledFile",
 "java.errors.incompleteClasspath.severity": "ignore",
 "workbench.colorTheme": "Atom One Dark",
 "java.home":"c:\\shaoming\\jdk8\\jdk1.8.0_202",
 "java.configuration.maven.userSettings": "D:\\software\\apache-maven-3.3.3-bin\\apache-maven-3.3.3\\conf\\settings.xml",
 "maven.executable.path": "D:\\software\\apache-maven-3.3.3-bin\\apache-maven-3.3.3\\bin\\mvn.cmd",
 "maven.terminal.useJavaHome": true,
 "maven.terminal.customEnv": [
  {
   "environmentVariable": "JAVA_HOME",
   "value": "c:\\shaoming\\jdk8\\jdk1.8.0_202"
  }
 ],
}

八、配置启动入口程序

启动工程之前还需要配置下运行环境，如下图，点左边的小虫子图标，然后点上面的下拉箭头，选择添加配置，第一次设置时VS Code会提示选择需要运行的语言环境，选择对应环境后自动创建 launch.json 文件。
launch.json 调试配置文件如下，默认不修改配置也可使用：

选择对应的配置环境调式项目如下，默认端口为8080。

方式一使用 @SpringBootApplication 注解，右击 Run AS -> Java Application

方式二 打成 Jar 包的项目可以通过输入命令来启用该jar包 ：java -jar xxxxx.jar 。


九、查看和测试接口
通过swagger，点下面链接。先验证登录 admin 123
http://localhost:8007/swagger-ui.html#/
http://localhost:8007/swagger-ui/index.html#/
在swagger 搜索栏输入 /eval/api-docs
显示 eval 接口
目前有个小问题，无法成功认证测试，只能查看。