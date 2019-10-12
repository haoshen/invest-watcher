# invest-watcher
## 简介
>&nbsp;&nbsp;&nbsp;&nbsp;invest-watcher提供对贵金属、原油、天然气等品种的实时行情、点位报警、持仓管理、历史数据存储分析等功能  
## 运行测试
>&nbsp;&nbsp;&nbsp;&nbsp;执行数据库建库脚本sql/invest.sql，将项目导入到IDE中执行（或直接执行打包后的jar文件）。
## 运行截图
### 首页
![image](https://github.com/haoshen/invest-watcher/blob/master/about.png)
### 行情监控
![image](https://github.com/haoshen/invest-watcher/blob/master/price_watch.png)
## 特色功能
### 价格监控
>&nbsp;&nbsp;&nbsp;&nbsp;invest-watcher会从网络上抓取实时的行情数据，结合用户当前的持仓情况，一览式地展示各投资品种的实时价格及用户投资利润。 
### 点位报警
>&nbsp;&nbsp;&nbsp;&nbsp;invest-watcher可以设置某个品种的报警点位，当该品种价格达到该点位时，会使用相应方式发送报警（需用户实现报警方法）。
### 持仓管理
>&nbsp;&nbsp;&nbsp;&nbsp;invest-watcher根据用户开/平仓、做多/空操作实时更新持仓记录。用户每次投资活动的基本信息都会被记录，包括：操作数量、操作价格、操作利润、操作后的仓位及均价等。用户还可以对某次持仓操作进行总结记录。
### 数据分析   
>&nbsp;&nbsp;&nbsp;&nbsp;在监控实时行情数据的同时，invest-watcher会周期性（目前为分钟级别）的将投资品种行情数据落库保存。后续可以根据这些历史行情数据，使用数据分析、机器学习、量化投资等技术进行投资分析。
### 支持品种
>+ 工商银行
>   + 黄金
>   + 白银
>   + 原油
>   + 天然气   
>
> &nbsp;&nbsp;&nbsp;&nbsp;更多投资品种持续开发中
## More  
>&nbsp;&nbsp;&nbsp;&nbsp;如果您对本项目感兴趣，或者想新增网站账户、新增投资品种等，    
>&nbsp;&nbsp;&nbsp;&nbsp;欢迎联系：717632581@qq.com（Email）
