package com.fu.generator.server;

import com.fu.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerGenerator {
    static String toServicePath = "server\\src\\main\\java\\com\\fu\\server\\service\\";
    static String toControllerPath = "business\\src\\main\\java\\com\\fu\\business\\controller\\admin\\";


    public static void main(String[] args) throws IOException, TemplateException {

        String Domain = "Section";
        String domain = "section";
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);

        //生成Service
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "Service.java", map);
        toServicePath = "server\\src\\main\\java\\com\\fu\\server\\service\\impl\\";
        FreemarkerUtil.initConfig("serviceImpl.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "ServiceImpl.java", map);

        //生成Controller
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath + Domain + "Controller.java", map);
    }
}
