package org.hepeng.hyperledgerfabric.app.javademo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * author he peng
 * date 2022/1/22 21:24
 */

@Configuration
@ConfigurationProperties(prefix = "fabric")
@Data
public class HyperLedgerFabricProperties {

    String networkConnectionConfigPath;

    String certificatePath;

    String privateKeyPath;
}
