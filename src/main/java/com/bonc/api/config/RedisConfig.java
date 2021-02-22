package com.bonc.api.config;

import com.huawei.jredis.client.GlobalConfig;
import com.huawei.jredis.client.auth.AuthConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sunshuo
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxTotal;

    @Value("${spring.redis.block-when-exhausted}")
    private boolean blockWhenExhausted;

    @Value("${spring.redis.cluster.nodes}")
    private String hostsAndPorts;
    @Value("${redis.cluster.princal}")
    private String princal;

    @Bean
    public JedisCluster redisClusterFactory() throws Exception {
        JedisPoolConfig jedisPoolConfig = getJedisPoolConfig();
        String[] split = hostsAndPorts.split(",");
        Set<HostAndPort> sets = new HashSet<>();
        for (String hostAndPort : split) {
            String[] split1 = hostAndPort.split(":");
            if (split1.length == 2) {
                sets.add(new HostAndPort(split1[0].trim(), Integer.parseInt(split1[1].trim())));
            }
        }
        String root = System.getProperty("user.dir") + File.separatorChar + "conf" + File.separatorChar;
        System.setProperty("java.security.krb5.conf", root + "krb5.conf");
        AuthConfiguration authConfiguration = new AuthConfiguration(root + "user.keytab", princal);
        GlobalConfig.setAuthConfiguration(authConfiguration);
        return new JedisCluster(sets, jedisPoolConfig);
    }

    private JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        jedisPoolConfig.setMaxTotal(maxTotal);
        return jedisPoolConfig;
    }
}
