package com.dy.rpc.core.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chengyibai
 * @Date: 2021-04-07 20:34
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RpcServiceProperties {

    private String version;
    private String group;
    private String serviceName;

    @Override
    public String toString() {
        return serviceName + "_" + group + "_" + version;
    }
}
