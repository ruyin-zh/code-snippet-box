package org.code.ruyin.util.snippet.key.config;

import org.code.ruyin.util.snippet.key.template.ClusterDistributeLockTemplate;
import org.code.ruyin.util.snippet.key.template.DistributeLockTemplate;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @author hjxz
 * @desc
 * @date 2021/4/9
 */
@Configuration
public class RedissonHandlerHolder {


    /**
     *
     * 配置参考:https://github.com/redisson/redisson/wiki/2.-Configuration
     *
     * */
    @Value("classpath:/redisson-conf.yml")
    private Resource configFile;


    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() throws IOException {
        Config config = Config.fromYAML(configFile.getInputStream());
        return Redisson.create(config);
    }


    @Bean
    public DistributeLockTemplate distributeLockTemplate(RedissonClient redissonClient){
        return new ClusterDistributeLockTemplate(redissonClient);
    }

}
