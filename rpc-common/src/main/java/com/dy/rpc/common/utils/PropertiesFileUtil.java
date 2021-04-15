package com.dy.rpc.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: chenyibai
 * @Date: 2021/2/20 15:51
 */
@Slf4j
public class PropertiesFileUtil {
    private static ConcurrentHashMap<String, Properties> map = new ConcurrentHashMap<>();

    private PropertiesFileUtil() {
    }

    public static Properties readPropertiesFile(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String rpcConfigPath = "";
        if (url != null) {
            rpcConfigPath = url.getPath() + fileName;
        }
        if (map.containsKey(rpcConfigPath)) {
            return map.get(rpcConfigPath);
        }

        Properties properties = null;
        try (InputStreamReader inputStreamReader = new InputStreamReader(
                new FileInputStream(rpcConfigPath), StandardCharsets.UTF_8)) {
            properties = new Properties();
            properties.load(inputStreamReader);
        } catch (IOException e) {
            log.error("occur exception when read properties file [{}]", fileName);
        }

        map.put(rpcConfigPath, properties);
        return properties;
    }

}
