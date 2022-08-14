## 状态机管理和调度平台设计文档

### 背景

简单到一个实体的状态流转，复杂到一个业务的具体流程，随处可见状态机的身影，业务期望有个可视化管理和调度的状态机平台。本项目拟实现一个状态机管理和调度平台，使得业务接入便捷，配置简单，只需要完成对应的状态检查和执行状态机事件即可，可以大大的降低业务复杂度和维护难度。

### 术语&约定

- 状态

用于描述业务目前所处的一个阶段，以订单为例：等待商家接单、等待用户确认收货等，都可以看成是一个订单所处的状态。

- 事件

用于描述一个具体的业务流程的发生，以订单为例：商家在商家端选择接受用户的订单、用户在收到货后点击确认收货等，都可以看成是一个事件。

- 事件源

能产生事件的所有可能来源，本项目定义了三个事件源，分别为业务系统、定时任务和手动触发。

- 动作

用于描述一个状态出现时，会产生哪些操作，以订单系统为例：当到达商家已接单状态时，可以通知用户商家已接单，这个通知用户的行为便可以看成是一个动作。

- 状态机（有限状态机）

状态机任意时刻都只处于一个确定的状态，在该状态下，状态机只会对特定的事件进行响应并进行对应状态的流转，当状态机流转到一个新的状态时，会触发该状态预设的一些动作并执行。

![C2C交易状态机](https://cdn.jsdelivr.net/gh/MerickBao/picEmbedding/img/20220814172939.png)

### 概要设计

#### 系统交互上下游

该系统的定位为一个底层的状态机管理和调度系统（下面简称状态机系统），其上游系统主要为业务系统，不存在下游系统。业务系统可以有多种，如订单系统、合同签署系统等。

![系统上下游](https://cdn.jsdelivr.net/gh/MerickBao/picEmbedding/img/20220814170751.png)


#### 系统间的交互

系统间的交互基于调用接口实现。上游业务系统首先需要进行状态机结构的配置，通过前端页面或直接发送json格式的状态机结构至状态机系统，状态机系统进行状态机的新建。当业务系统有新业务（如客户下单）时，发送状态机结构编号和业务描述至状态机系统。当客户有新动作（如取消订单）时，发送对应的code（预先由业务平台定义）至状态机系统。状态机系统收到code后，可能会进行状态流转。若流转成功，则根据业务方的定义回调业务系统的接口。这样两个系统就形成了一个闭环。

#### 用例图

![状态机系统用例图](https://cdn.jsdelivr.net/gh/MerickBao/picEmbedding/img/20220814170845.png)

#### 模块划分

##### 功能

1. 状态机创建

1. 状态机实例创建

1. 根据转移码code进行状态流转

1. 动作回调

1. 查询状态机信息

1. 查询状态机实例信息

1. 查询结点信息

1. 查询流转信息

1. 查询转移链

1. 导出状态机配置文件

1. 实例重置

##### 模块

后端共划分成api、service、dao、database四层。其中，api层内主要定义REST风格的HTTP接口，service层负责实现具体业务逻辑，dao层负责与数据库进行交互。

api层内包含3个模块，其中

- StateMachineInstanceApi 处理与状态机实例相关的请求

- StateMachineApi 处理与状态机相关的请求

- StateNodeApi 处理与状态机结点相关的请求

service层内包含7个模块。其中

- StateMachineInstanceService 实现状态机实例创建、流转、更新、回调的具体逻辑。

- StateMachineService 状态机结构（Schema）维护，实现配置文件导入、导出的具体逻辑。

- TransitionService 负责状态机转移结构维护

- StateNodeService 负责状态机结点维护

- TransLogService 负责转移日志维护

- EventService 负责事件转移码维护

- ActionService 动作回调服务

模块间交互图：

![系统各模块交互图](https://cdn.jsdelivr.net/gh/MerickBao/picEmbedding/img/20220814171025.jpeg)

- ##### 组件设计

  - 状态机生命周期流程图

  ![状态机实例生命周期](https://cdn.jsdelivr.net/gh/MerickBao/picEmbedding/img/20220814172750.png)

  - 状态流转实现逻辑

![状态机流转逻辑](https://cdn.jsdelivr.net/gh/MerickBao/picEmbedding/img/20220814171321.jpeg)

![状态机流转时序图](https://cdn.jsdelivr.net/gh/MerickBao/picEmbedding/img/20220814171502.jpeg)


- 状态机（Schema）创建实现逻辑

 ![状态机schema创建时序图](https://cdn.jsdelivr.net/gh/MerickBao/picEmbedding/img/20220814171607.jpeg)

- 以配置文件形式创建状态机

![img](https://bytedancecampus1.feishu.cn/space/api/box/stream/download/asynccode/?code=MzYyMWUwMTEwZTQzMmJhNDQ2MmNiNzhiOTNhZDBiZWFfbDdySExtczdTVmRXTFNhWmVpd1I0RmFwaHhWWUloTWVfVG9rZW46Ym94Y25pallVZFBweERRRkNMTUFYUDlCVU5jXzE2NjA0Njc4NTY6MTY2MDQ3MTQ1Nl9WNA)

上述状态机的配置文件：

```JSON
{
    "description": "机器人销售合同",
    "defaultState": "等待签署",
    "stateNodes": [
        {
            "description": "等待公司签署",
            "identification": "等待签署",
            "actions": [
                {
                    "url": "通知公司有新的合同需要签署"
                }
            ]
        },
        {
            "description": "合同生效",
            "identification": "合同生效",
            "actions": [
                {
                    "url": "通知客户合同已生效"
                }
            ]
        },
        {
            "description": "合同失效",
            "identification": "合同失效",
            "actions": [
                {
                    "url": "通知客户合同已失效"
                }
            ]
        }
    ],
    "transitions": [
        {
            "prevNode": "等待签署",
            "nextNode": "合同生效",
            "event": {
                "description": "签署成功",
                "code": 501
            }
        },
        {
            "prevNode": "等待签署",
            "nextNode": "合同失效",
            "event": {
                "description": "签署失败",
                "code": 503
            }
        }
    ]
}
```

- ##### 非功能性设计

- - 稳定性：

- 数据一致性：

- 扩展性：新的的业务系统可以以配置文件或者在系统界面以交互的形式，定义并生成专用于自身业务的状态机，后续便可以使用该状态机创建对应业务的状态机实例，实现业务功能和维护。

### 详细设计

#### 数据库表结构

https://dbdiagram.io/d/62f0fcb7c2d9cf52fa671249

![img](https://bytedancecampus1.feishu.cn/space/api/box/stream/download/asynccode/?code=ZDI5OTYwNmJmZmY5MWFmYWZlZjdlMTg0ZGEzODRjZWVfUWR5d0RiM2dYS0NRaWM2NkdwVUc0ZnQ5SWZncUZqZThfVG9rZW46Ym94Y255ZDUxcnU4STZXV2MzcUxSbGVNQnBmXzE2NjA0Njc4NTY6MTY2MDQ3MTQ1Nl9WNA)

#### 接口文档（待补充）

https://documenter.getpostman.com/view/21471749/VUjSENfJ

| 接口名称                | 方法说明                           | 请求方法 | 参数             | 参数说明                     | 返回类型                       |
| ----------------------- | ---------------------------------- | -------- | ---------------- | ---------------------------- | ------------------------------ |
| StateMachineApi         |                                    |          |                  |                              |                                |
| state-machine           | 获取指定的状态机信息               | GET      | machineId        | 状态机id                     | 状态机实体信息                 |
| state-machine           | 通过配置文件生成一个状态机         | POST     | StateMachineJSON | 用于生成状态机的JSON配置文件 | JSON                           |
| state-machine-struct    | 获取状态机的配置文件               | GET      | machineId        | 状态机id                     | 状态机对应的配置信息           |
| state-machines          | 查询系统中所有种类的状态机         | GET      |                  | 无参数                       | 系统中所有类型状态机的实体信息 |
| transitions             | 查询状态机所拥有的所有事件转移条件 | GET      | machineId        | 状态机id                     | 状态机所拥有的所有事件转移条件 |
| StateMachineInstanceApi |                                    |          |                  |                              |                                |
| instance                |                                    | POST     | InstanceEntity   | 状态机实时、体分装           |                                |
|                         |                                    |          |                  |                              |                                |
| StateNodeApi            |                                    |          |                  |                              |                                |
|                         |                                    |          |                  |                              |                                |

### 总结

通过为期一周学习与实践，从刚开始对状态机系统概念的不清晰，到逐渐设计并构建出状态机系统的每一个细节，比较完整的体验了一遍一个系统从无到有的设计开发全过程。系统目前只实现了状态机系统的核心功能，还有很多需要改进和完善的地方，比如：前端界面、动作和事件处理机制的完善（超时等各种错误的处理方案）、数据一致性等。