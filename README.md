####创建用户和虚拟机
#####创建用户
使用<code>administrator</code>权限创建用户。在 Admin 标签下，在右侧选择 Users, 就可以输入用户和密码进行创建用户了, 创建用户的时候需要设置用户的 Tags.
 有四种Tags：management < policymaker < monitoring< administrator.
 
 management(普通管理者)  
 1. 列出自己可以通过AMQP登入的virtual hosts  
 2. 查看自己的virtual hosts中的queues, exchanges 和 bindings
 3. 查看和关闭自己的channels 和 connections
 4. 查看有关自己的virtual hosts的“全局”的统计信息，包含其他用户在这些virtual hosts中的活动。
 
 policymaker(策略制定者)   
  management可以做的任何事外加：
 1. 查看、创建和删除自己的virtual hosts所属的policies和parameters
 
 monitoring(监控者)   
 management可以做的任何事外加：
 1. 列出所有virtual hosts，包括他们不能登录的virtual hosts
 2. 查看其他用户的connections和channels
 3. 查看节点级别的数据如clustering和memory使用情况
 4. 查看真正的关于所有virtual hosts的全局的统计信息
 
 administrator(超级管理员)  
 policymaker和monitoring可以做的任何事外加:
 1. 创建和删除virtual hosts
 2. 查看、创建和删除users
 3. 查看创建和删除permissions
 4. 关闭其他用户的connections
  
 #####设置用户权限(配置、写、读）
>$ set_permissions [-p vhost] user conf write read

| Operation | other | Configure | Write | Read |  
|---------| --- |---------| ---------| -----|
|exchange.declare|(passive=false)|exchange
|exchange.declare|(passive=true)
|exchange.declare|(with  AE)|exchange|exchange (AE)|exchange|
|exchange.delete| |exchange
|queue.declare|(passive=false)|queue
|queue.declare|(passive=true)|
|queue.declare|	(with  DLX)|queue|exchange (DLX)|queue|
|queue.delete| |queue|
|exchange.bind| | |exchange (destination)|exchange (source)|
|exchange.unbind| | |exchange (destination)|exchange (source)|
|queue.bind| | | queue | exchange|
|queue.unbind| | | queue | exchange|
|basic.publish| | | exchange| |
|basic.get| | | |queue|
|basic.consume| | | |queue|
|queue.purge| | | |queue|

 
 
#####创建虚拟机
使用<code>administrator</code>权限创建。 在 Admin 标签下，在右侧选择 Virtual Hosts, 输入 虚拟机名称点击 Add virtual host 按钮就添加一个新的虚拟了。
#####用户和虚拟机关联
两种方式：可以通过用户关联虚拟机，也可以通过虚拟机关联用户，都可以的。  
1.通过用户关联虚拟机，在用户列表，通过点击用户名称，在下方有一个 Set permission, 在 Virtual Host: 选项选择需要挂靠的虚拟机，点击 Set Permission 按钮即可关联到对应的虚拟下。  
2.通过虚拟机关联用户，在虚拟机列表，通过点击虚拟机名称，在下方有一个 Set permission, 在 User 选项选择需要添加的用户，点击 Set permission 按钮即可添加用户到该虚拟机下。  

####创建交换器
在 Exchanges 标签下创建。  
交换器四种类型:  
路由模式(Direct)：  
路由全匹配才可以.  
通配符模式(Topic)：  
​类似路由模式，但是routing_key支持模糊匹配，按规则转发消息。符号“#”匹配一个(0个)或多个词，符号“*”匹配不多不少一个词。  
发布订阅模式(Fanout)：  
​转发消息到所有绑定队列，忽略routing_key。  
Headers:    
基本不使用

参数：alternate-exchange  
设置备用交换器，当消息没法路由到一个正确的队列的时候，会交给该交换器重新进行路由，如果还没法路由到一个正确的队列，则丢弃消息。  

####创建队列
在 Queues 标签下创建  
Name: 队列名称  
Durability: 是否持久化, 队列的声明默认是存放到内存中的，如果rabbitmq重启会丢失，如果想重启之后还存在就要使队列持久化，保存到Erlang自带的Mnesia数据库中，当rabbitmq重启之后会读取该数据库  
Auto delete：是否自动删除  
Node: 如果是集群的时候，会出现这个选项，选择一个节点  
Arguments: 
* Message TTL(x-message-ttl): 设置队列中的所有消息的生存周期(发送消息的时候可以单独指定本条消息的过期时间)
* Auto expire(x-expires): 当队列在指定的时间没有被访问(consume, basicGet, queueDeclare…)就会被删除
* Max length(x-max-length): 限定队列的消息的最大值长度(队列消息条数)
* Max length bytes(x-max-length-bytes): 限定队列最大占用的空间大小
* Overflow behaviour(x-overflow): 队列中的消息溢出时,如何处理这些消息.要么丢弃队列头部的消息,要么拒绝接收后面生产者发送过来的所有消息(drop-head:丢弃选进消息,reject-publish:拒绝接收消息)
* Dead letter exchange(x-dead-letter-exchange): 死信交换器(设置一个已定义好的交换器)
* Dead letter routing key(x-dead-letter-routing-key)： 死信路由
* Maximum priority(x-max-priority): 设置一个最大的优先级，发送消息的时候再指定一个优先级，越靠近最大优先级优先被消费
* Lazy mode(x-queue-mode=lazy): 消费模式，收到消息后先加载到硬盘中，消费的时候再从硬盘中读取
* Master locator(x-queue-master-locator): 集群有关设置

####交换器和队列的绑定
可以通过交换器绑定队列也可以通过队列绑定交换器,同时指定路由键。路由设置根据交换器类型作用不一。