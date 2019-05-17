/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kisline.dbcp;

import org.apache.derby.drda.NetworkServerControl;
import org.apache.derby.jdbc.ClientDataSource;
import org.apache.nifi.reporting.InitializationException;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;

public class TestStandardHikariCPService {

  private static NetworkServerControl derby;

  @Before
  public void init() throws Exception {
    derby = new NetworkServerControl();
    derby.start(new PrintWriter(System.out));
  }

  @After
  public void down() throws Exception {
    derby.shutdown();
  }

  @Test
  public void testService() throws InitializationException {
    final TestRunner runner = TestRunners.newTestRunner(TestProcessor.class);
    final StandardHikariCPService service = new StandardHikariCPService();
    runner.addControllerService("dbcp", service);

    runner.setProperty(
        service, DBCPConfigUtil.DATASOURCE_CLASSNAME, ClientDataSource.class.getName());
    runner.setProperty(service, DBCPConfigUtil.USERNAME, "test");
    runner.setProperty(service, DBCPConfigUtil.PASSWORD, "test");
    runner.setProperty(service, DBCPConfigUtil.AUTO_COMMIT, "true");
    runner.setProperty(service, DBCPConfigUtil.METRICS, "true");

    runner.setProperty(service, "databaseName", "test");
    runner.setProperty(service, "createDatabase", "create");
    runner.setProperty(service, "serverName", "localhost");
    runner.setProperty(service, "portNumber", "1527");

    runner.enableControllerService(service);
    runner.setProperty(TestProcessor.DS_DROP, "dbcp");
    runner.run();

    runner.assertValid(service);
    runner.disableControllerService(service);
    runner.shutdown();
  }
}
