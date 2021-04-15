# wsd-rpc-java
基于Netty实现的RPC框架,可以让客户端直接调用服务端方法就像调用本地方法一样简单。

# RPC框架设计思路
![image](https://github.com/oahunc/wsd-rpc-java/blob/master/images/rpc%E6%9E%B6%E6%9E%84.jpg)

服务提供端Server向注册中心注册服务，服务消费者Client通过注册中心拿到服务相关信息，然后再通过网络请求服务提供端 Server。

# 项目基本情况
[V1.0版本](https://github.com/oahunc/wsd-rpc-java/tree/v1.0)  
- [x] 基于BIO实现网络传输
- [x] 实现Kyro、Json和Hessian三种序列化机制
- [x] 客户端与服务端通信协议重新设计
- [x] 基于jdk动态代理实现客户端代理 

[V2.0版本](https://github.com/oahunc/wsd-rpc-java/tree/v2.0)  
- [x] 使用Netty(基于NIO)实现网络传输
- [x] 使用注解自动注册服务

[V3.0版本](https://github.com/oahunc/wsd-rpc-java/tree/v3.0)  
- [x] 使用Zookeeper和Nacos作为管理相关服务地址信息的注册中心(zk支持单机模式、集群模式，nacos支持单机模式)
- [x] Netty重用Channel避免重复连接服务端
- [x] 增加Netty心跳机制
- [x] 客户端调用远程服务的时候进行负载均衡(目前已实现随机、轮询和一致性哈希三种算法)
- [x] 支持同一个接口多个实现类
- [x] 使用SPI机制实现松耦合
- [x] 实现gzip压缩机制
- [x] 客户端重用相关服务地址信息列表
- [x] 支持Protobuf序列化机制 

[V4.0版本](https://github.com/oahunc/wsd-rpc-java)
- [x] 集成Spring，通过注解注册服务、消费服务
- [x] 支持配置文件的方式进行配置
- [ ] 容错机制
- [ ] 兼容Http2.0协议
- [ ] 服务性能监控和报警中心  

