# SR_dev
dev  for  ZheJiang SHUREN Univercity , Information   And   Sicence Technology  College  

## 代码编译和使用

- 在idea 的 Maven Projects 里面可以直接切换调试状态

- genrator 生成代码在test里面，直接运行CodeGenerator即可

- 在CodeGenerator内 genCode("表名1,表名2")

- Service 的操作文档在 core 里面有说明

```
/**
 * 等号的CRUD:
 * List<T> select(T record); 根据实体中的属性值进行查询，查询条件使用等号
 * T selectByPrimaryKey(Object key); 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
 * List<T> selectAll(); 查询全部结果，select(null)方法能达到同样的效果
 * T selectOne(T record); 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
 * int selectCount(T record); 根据实体中的属性查询总数，查询条件使用等号
 * int insert(T record); 保存一个实体，null的属性也会保存，不会使用数据库默认值
 * int insertSelective(T record); 保存一个实体，null的属性不会保存，会使用数据库默认值
 * int updateByPrimaryKey(T record); 根据主键更新实体全部字段，null值会被更新
 * int updateByPrimaryKeySelective(T record); 根据主键更新属性不为null的值
 * int delete(T record); 根据实体属性作为条件进行删除，查询条件使用等号
 * int deleteByPrimaryKey(Object key); 根据主键字段进行删除，方法参数必须包含完整的主键属性
 *
 * 条件的CRUD:
 * List<T> selectByCondition(Object condition); 根据Condition条件进行查询
 * int selectCountByCondition(Object condition); 根据Condition条件进行查询总数
 * int updateByCondition(@Param("record") T record, @Param("example") Object condition); 根据Condition条件更新实体record包含的全部属性，null值会被更新
 * int updateByConditionSelective(@Param("record") T record, @Param("example") Object condition); 根据Condition条件更新实体record包含的不是null的属性值
 * int deleteByCondition(Object condition); 根据Condition条件删除数据
 */
 ```

## 代码规范

[阿里Code文档](https://github.com/alibaba/p3c/blob/master/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4Java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C%EF%BC%88%E7%BA%AA%E5%BF%B5%E7%89%88%EF%BC%89.pdf)

如需下载IDEA 插件搜索 - Alibaba 即可查看到 code 插件。
