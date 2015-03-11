/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.sakserv.minicluster;

import com.github.sakserv.minicluster.config.ConfigVars;
import com.github.sakserv.minicluster.config.PropertyParser;
import com.github.sakserv.minicluster.impl.YarnLocalCluster;
import org.apache.hadoop.conf.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YarnLocalClusterTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(YarnLocalClusterTest.class);

    // Setup the property parser
    private static PropertyParser propertyParser;
    static {
        try {
            propertyParser = new PropertyParser(ConfigVars.DEFAULT_PROPS_FILE);
        } catch(IOException e) {
            LOG.error("Unable to load property file: " + propertyParser.getProperty(ConfigVars.DEFAULT_PROPS_FILE));
        }
    }

    private static YarnLocalCluster yarnLocalCluster;

    @BeforeClass
    public static void setUp() throws IOException {
        yarnLocalCluster = new YarnLocalCluster.Builder()
                .setNumNodeManagers(Integer.parseInt(propertyParser.getProperty(ConfigVars.YARN_NUM_NODE_MANAGERS_KEY)))
                .setNumLocalDirs(Integer.parseInt(propertyParser.getProperty(ConfigVars.YARN_NUM_LOCAL_DIRS_KEY)))
                .setNumLogDirs(Integer.parseInt(propertyParser.getProperty(ConfigVars.YARN_NUM_LOG_DIRS_KEY)))
                .setResourceManagerAddress(propertyParser.getProperty(ConfigVars.YARN_RESOURCE_MANAGER_ADDRESS_KEY))
                .setResourceManagerHostname(propertyParser.getProperty(ConfigVars.YARN_RESOURCE_MANAGER_HOSTNAME_KEY))
                .setResourceManagerSchedulerAddress(propertyParser.getProperty(
                        ConfigVars.YARN_RESOURCE_MANAGER_SCHEDULER_ADDRESS_KEY))
                .setResourceManagerResourceTrackerAddress(propertyParser.getProperty(
                        ConfigVars.YARN_RESOURCE_MANAGER_RESOURCE_TRACKER_ADDRESS_KEY))
                .setConfig(new Configuration())
                .build();
    }

    @Test
    public void testNumNodeManagers() {
        assertEquals(Integer.parseInt(propertyParser.getProperty(ConfigVars.YARN_NUM_NODE_MANAGERS_KEY)),
                (int) yarnLocalCluster.getNumNodeManagers());
    }

    @Test
    public void testNumLocalDirs() {
        assertEquals(Integer.parseInt(propertyParser.getProperty(ConfigVars.YARN_NUM_LOCAL_DIRS_KEY)),
                (int) yarnLocalCluster.getNumLocalDirs());
    }

    @Test
    public void testNumLogDirs() {
        assertEquals(Integer.parseInt(propertyParser.getProperty(ConfigVars.YARN_NUM_LOG_DIRS_KEY)),
                (int) yarnLocalCluster.getNumLogDirs());
    }
    
    @Test
    public void testResourceManagerAddress() {
        assertEquals(propertyParser.getProperty(ConfigVars.YARN_RESOURCE_MANAGER_ADDRESS_KEY), 
                yarnLocalCluster.getResourceManagerAddress());
    }

    @Test
    public void testResourceManagerHostname() {
        assertEquals(propertyParser.getProperty(ConfigVars.YARN_RESOURCE_MANAGER_HOSTNAME_KEY),
                yarnLocalCluster.getResourceManagerHostname());
    }

    @Test
    public void testResourceManagerSchedulerAddress() {
        assertEquals(propertyParser.getProperty(ConfigVars.YARN_RESOURCE_MANAGER_SCHEDULER_ADDRESS_KEY),
                yarnLocalCluster.getResourceManagerSchedulerAddress());
    }

    @Test
    public void testResourceManagerResourceTrackerAddress() {
        assertEquals(propertyParser.getProperty(ConfigVars.YARN_RESOURCE_MANAGER_RESOURCE_TRACKER_ADDRESS_KEY),
                yarnLocalCluster.getResourceManagerResourceTrackerAddress());
    }

    @Test
    public void testConf() {
        assertTrue(yarnLocalCluster.getConfig() instanceof org.apache.hadoop.conf.Configuration);

    }
}
