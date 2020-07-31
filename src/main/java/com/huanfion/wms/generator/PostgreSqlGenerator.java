package com.huanfion.wms.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

/**
 * @author huanfion
 * @version 1.0
 * @date 2019/7/29 12:13
 */
@Component
public class PostgreSqlGenerator {
    private static String driverName;

    @Value("${spring.datasource.driver-class-name}")
    public void setDriverName(String driverName) {
       PostgreSqlGenerator.driverName = driverName;
    }

    @Value("${spring.datasource.username}")
    public void setUserName(String userName) {
        PostgreSqlGenerator.userName = userName;
    }

    @Value("${spring.datasource.password}")
    public void setPassWord(String passWord) {
        PostgreSqlGenerator.passWord = passWord;
    }

    @Value("${spring.datasource.url}")
    public void setUrl(String url) {
        PostgreSqlGenerator.url = url;
    }

    private static String userName;
    private static String passWord;
    private static String url;

    /**
     *
     * @param tableName 表名，多个表名使用,分割
     * @param prefix 表名前缀
     * @param Package 包名
     * @param override 是否覆盖
     * @param author 作者
     */
    public void generator(String tableName, String prefix, String Package, boolean override, String author) {
        //代码生成器
        AutoGenerator mpg = new AutoGenerator();
        //全局配置
        mpg.setGlobalConfig(getGlobalConfig(override, author))
                //配置数据源
                .setDataSource(getDataSourceConfig())
                //包配置
                .setPackageInfo(getPackageConfig(Package))
                //策略配置
                .setStrategy(getStrategyConfig(tableName, prefix))
                //配置模板
                .setTemplate(getTemplateConfig())
                //注入自定义配置 可以在 VM 中使用 cfg.abc 设置的值
                .setCfg(getInjectionConfig(Package.replaceAll("\\.", "/")))
                //.setTemplateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 获取InjectionConfig
     *
     * @param Package
     * @return
     */
    protected InjectionConfig getInjectionConfig(String Package) {
        FileOutConfig mapperConfig = new FileOutConfig(
                "/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return getResourcePath() + "/mapper/" + Package + "/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        };
        FileOutConfig DTOconfig = new FileOutConfig(
                "/templates/DTO.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return getJavaPath() + "/cn/com/enersun/" + Package + "/model/dto/"
                        + tableInfo.getEntityName() + "DTO.java";
            }
        };
        ArrayList<FileOutConfig> fileOutConfigs = new ArrayList<>();
        fileOutConfigs.add(mapperConfig);
        //fileOutConfigs.add(DTOconfig);
        return new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                this.setMap(map);
            }
        }.setFileOutConfigList(fileOutConfigs);
    }

    /**
     * 获取StrategyConfig
     *
     * @param tableName
     * @param prefix
     * @return
     */
    protected StrategyConfig getStrategyConfig(String tableName, String prefix) {
        StrategyConfig strategy = new StrategyConfig();
        List<TableFill> tableFillList = getTableFills();
        strategy.setCapitalMode(false)//全局大写命名
                .setTablePrefix(prefix)// 表前缀
                .setNaming(NamingStrategy.underline_to_camel)//下划线转驼峰命名
                .setColumnNaming(NamingStrategy.underline_to_camel)//列名生成策略(下划线转驼峰命名)
                .setInclude(tableName.split(","))//多个表名用，分割
                .setEntityLombokModel(true)//实体是否为lombok模型
                //.setSuperEntityClass("com.zegobird.servicehi.entity.BaseEntity")//自定义实体父类
                .setSuperEntityColumns("Id")//写于父类中的公共字段
                //.setTableFillList(tableFillList)
                .setRestControllerStyle(true);//生成 @RestController 控制器


        return strategy;
    }

    /**
     * 获取PackageConfig
     *
     * @param aPackage
     * @return
     */
    protected PackageConfig getPackageConfig(String aPackage) {
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(aPackage)
                .setParent("com.huanfion.wms")
                .setEntity("entity")
                .setController("controller")
                .setService("service")
                .setMapper("mapper")
                .setServiceImpl("service.impl");
        return pc;
    }

    /**
     * 获取DataSourceConfig
     *
     * @return
     */
    protected DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.POSTGRE_SQL)//数据库类型
                .setSchemaName("public")//数据库schema
                .setDriverName(driverName)//驱动名称
                .setUrl(url)//驱动链接的url
                .setUsername(userName)//数据库连接用户名
                .setPassword(passWord);//数据库连接密码
        return dsc;
    }

    /**
     * 获取GlobalConfig
     *
     * @param override
     * @param author
     * @return
     */
    private GlobalConfig getGlobalConfig(boolean override, String author) {
        //全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor(author)//设置作者
                .setOutputDir(getJavaPath())//设置生成文件输出目录 默认D盘根目录
                .setOpen(false)//是否打开输出目录 默认true
                .setActiveRecord(true)//是否开启AR模式
                //自定义文件命名，注意 %s 会自动填充表实体属性！
                .setEntityName("%s")
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("I%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController")
                .setBaseResultMap(true)//生成基本的resultMap
                .setBaseColumnList(true);//生成基本的SQL片段
        return gc;
    }

    /**
     * 获取项目根目录
     *
     * @return
     */
    protected String getRootPath() {
        String file = Objects.requireNonNull(PostgreSqlGenerator.class.getClassLoader().getResource("")).getFile();
        String parent = new File(file).getParentFile().getParent();
        System.out.println("根目录:" + parent);
        return parent;
    }

    /**
     * 获取JAVA目录
     *
     * @return
     */
    protected String getJavaPath() {
        return getRootPath() + "/src/main/java";
    }

    /**
     * 获取Resource目录
     *
     * @return
     */
    protected String getResourcePath() {
        return getRootPath() + "/src/main/resources";
    }

    /**
     * 获取TableFill策略
     *
     * @return
     */
    protected List<TableFill> getTableFills() {
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("createTime", FieldFill.INSERT));
        tableFillList.add(new TableFill("updateTime", FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("createUid", FieldFill.INSERT));
        tableFillList.add(new TableFill("updateUid", FieldFill.INSERT_UPDATE));
        return tableFillList;
    }

    /**
     * 获取TemplateConfig
     *
     * @return
     */
    protected TemplateConfig getTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();
        templateConfig.setXml(null);
        return templateConfig;
    }
}
