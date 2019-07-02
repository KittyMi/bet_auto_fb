package com.auto.ball;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;

/**
 * 代码生成器
 * @author Andy_lai
 */
public class MybatisPlusGenerator {

    private static String packageName = "C:\\code\\auto-ball";    //文件路径
    private static String authorName = "Andy_Lai";     //作者
    private static String table = "ls_zero_mul_factors_new_bet_only";                  //table名字
    private static String prefix = "ls_zero";                     //table前缀
    private static File file = new File(packageName);
    private static String path = file.getAbsolutePath();

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                // 全局配置
                new GlobalConfig()
                        .setOutputDir(path + "/src/main/java")//输出目录
                        .setFileOverride(true)// 是否覆盖文件
                        .setActiveRecord(true)// 开启 activeRecord 模式
                        .setEnableCache(false)// XML 二级缓存
                        .setBaseResultMap(true)// XML ResultMap
                        .setBaseColumnList(true)// XML columList
                        .setOpen(false)//生成后打开文件夹
                        .setAuthor(authorName)
                        // 自定义文件命名，注意 %s 会自动填充表实体属性！
                        .setMapperName("%sMapper")
                        .setXmlName("%sMapper")
                        .setServiceName("%sService")
                        .setServiceImplName("%sServiceImpl")
                        .setControllerName("%sController")
        ).setDataSource(
                // 数据源配置
                new DataSourceConfig()
                        .setDbType(DbType.MYSQL)// 数据库类型
                        .setTypeConvert(new MySqlTypeConvert() {

                        })
                        .setDriverName("com.mysql.jdbc.Driver")
                        .setUsername("root")
                        .setPassword("root!@#")
                        .setUrl("jdbc:mysql://113.108.239.124:3306/football?characterEncoding=utf8")
                       // .setUrl("jdbc:mysql://113.108.239.124:3306/hr_sys_data?characterEncoding=utf8")
        ).setStrategy(
                // 策略配置
                new StrategyConfig()
                        .setTablePrefix(new String[]{prefix})// 此处可以修改为您的表前缀
                        .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                        .setInclude(new String[]{table}) // 需要生成的表
                        .setRestControllerStyle(true)
                        // 自定义实体父类
                        .setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model")
                        // 自定义 mapper 父类
                        .setSuperMapperClass("SuperMapper")
                        // 自定义 controller 父类
                        .setSuperControllerClass("com.baomidou.mybatisplus.extension.api.ApiController")
                        // 【实体】是否生成字段常量（默认 false）
                        .setEntityColumnConstant(false)
                        // 【实体】是否为构建者模型（默认 false）
                        // public User setName(String name) {this.name = name; return this;}
                        .setEntityBuilderModel(true)
                        // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                        .setEntityLombokModel(true)
                        // Boolean类型字段是否移除is前缀处理
                        .setEntityBooleanColumnRemoveIsPrefix(true)
                        .setRestControllerStyle(true)
                        .setControllerMappingHyphenStyle(true)
        ).setPackageInfo(
                // 包配置
                new PackageConfig()
                        //.setModuleName("User")
                        .setParent("com.auto.ball")// 自定义包路径
                        .setController("controller")// 这里是控制器包名，默认 web
                        .setEntity("entity")
                        //.setMapper("mapper.sys")
                        .setMapper("mapper")
                        //.setService("service.sys")
                        .setService("service")
                        .setServiceImpl("service.impl")
                .setXml("mapper.xml")
        );

        // 执行生成
        mpg.execute();

        // 打印注入设置，这里演示模板里面怎么获取注入内容【可无】

    }
}
