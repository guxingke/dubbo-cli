# Dubbo-cli
简单 dubbo telnet 包装. 
方便临时调用和调试.

# 特性
## 支持多 dubbo provider
应用场景多个 dubbo 应用
## 支持多环境
快速切换至 local, test, beta 等环境
## 查看接口信息
基于 dubbo telnet ls 命令
## 普通调用
基于 dubbo telnet invoke 命令
## 别名调用
基于普通调用, 支持配置模板, 可调用时动态传参

# 快速开始
## 安装
```bash
brew tap guxingke/repo && brew install dubbo
```

## 示例

### 示例提供者代码[provider](https://github.com/guxingke/demo/blob/master/dubbo-demo/hello-demo/src/main/java/com/gxk/demo/boot/ServerApp.java)

### 示例操作
```bash
# in
dubbo help
# out
Example usage:
  dubbo init // init base cfg
  dubbo deinit // reverse init , for re init or uninstall
  dubbo ls [Service] // show dubbo service or service detail
  dubbo status(st) // show active context, env.
  dubbo context(ctx) <subcmd> [args] // context operation
  dubbo env <subcmd> [args] // context operation
  dubbo invoke // call rpc or call alias rpc

Troubleshooting:
  dubbo config // show cfg

Further help:
  dubbo cmd help
  dubbo version

# in
dubbo init
# out
~

# in
dubbo st
# out
Active Context: default
Active Env: local
Env Detail: Env(host=127.0.0.1, port=20880, link=telnet, charset=GBK)

# in
dubbo ls
# out
PROVIDER:
com.gxk.demo.service.HelloService ->  published: N

# in
dubbo ls com.gxk.demo.service.HelloService
# out
com.gxk.demo.service.HelloService (as provider):
	com.gxk.demo.service.TestResp hello1(com.gxk.demo.service.TestReq)
	java.lang.String hello(java.lang.String)

# in
dubbo invoke test 'com.gxk.demo.service.HelloService.hello("xx")'
# out
"Hello xx, response from provider: null"

# in
dubbo invoke test 'com.gxk.demo.service.HelloService.hello1({"id": "1","msg": "xx"})'
# out
{"id":"1","msg":"xx"}

# in
dubbo invoke set f1 'com.gxk.demo.service.HelloService.hello1({"id": "{}","msg": "xx"})' 10
# out
~

# in
dubbo invoke f1
# out
{"id":"10","msg":"xx"}

# in
dubbo invoke f1 20
# out
{"id":"20","msg":"xx"}

# 命令用法参考 dubbo <cmd> help, 
# e.g
# in
dubbo invoke help
# out
dubbo telnet invoke wrapper

USAGE:
- test call
test <service>.<method>(<args>)

- alias call
ls                                 # list all alias
<alias> <arg1> <arg2>              # e.g f1 "test" true
set <alias> <tpl> <default args>   # e.g set f1 test.HelloService.hello("{}", {}) test,true
rm <alias>                         # e.g rm f1

```

# 支持版本
- dubbox 2.8.4
- dubbo 2.7.1

# 限制
## 某些 dubbo 版本不支持, 已知 2.7.0 不支持
## 仅支持 Unix*

# 概念
## context
上下文, 对应多个 dubbo provider

## env
环境, 同一个 context 下多个环境, 可对应不同的应用实例, e.g local, test, beta...

## invoke alias
别名, 用来快速调用, 支持默认值, 和传参替换, 模板预发使用 slf4j 日志格式, e.g "xxx: {}"

# 开发
## 环境
- maven 3.3+
- jdk 1.8+
- graalvm 1.0+

## 构建
```bash
mvn clean package
./build.sh # build binary executable file named dubbo.
```

# 安装
```bash
mv target/dubbo ~/.local/bin
```
或者
```bash
ln -snf `pwd`/target/dubbo ~/.local/dubbo
```

# 变更记录
- 基本可用
