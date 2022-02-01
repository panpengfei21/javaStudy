package com.ppf.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ZkClient {

    private final String connectString = "10.211.55.19:2181,10.211.55.20:2181,10.211.55.21:2181";
    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper(connectString, 2000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    /**
     * 创建节点
     */
    @Test
    public void createNode() throws KeeperException, InterruptedException {
        String s = zkClient.create("/test/api",
                "new".getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println(s);
    }

    @Test
    public void getChildren() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/test", false);
        System.out.println(children);
    }

    @Test
    public void deleteNode() throws KeeperException, InterruptedException {
        zkClient.delete("/test/nNodeS",1);
    }

    @Test
    public void exists() throws KeeperException, InterruptedException {
        Stat stat = zkClient.exists("/test/nNodeS0000000005", false);
        System.out.println(stat);
    }
}
