# seata-example
springboot+springcloud+mybatis整合阿里分布式事务框架seata

/order为正常下单，扣减库存以及账户资金

/order/fail为异常回滚

BUG：多个分支事务似乎不能放一起，因为做压测的时候，库存没有回滚，可以按照官方例子，写全局事务管理多个分支事务！
