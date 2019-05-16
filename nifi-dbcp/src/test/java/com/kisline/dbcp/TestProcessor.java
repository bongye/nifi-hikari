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

import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.exception.ProcessException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestProcessor extends AbstractProcessor {

  public static final PropertyDescriptor DS_DROP =
      new PropertyDescriptor.Builder()
          .name("dbcp")
          .description("HikariCPService test processor")
          .identifiesControllerService(HikariCPService.class)
          .required(true)
          .build();

  @Override
  public void onTrigger(ProcessContext context, ProcessSession session) throws ProcessException {}

  @Override
  protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
    List<PropertyDescriptor> propDescs = new ArrayList<>();
    propDescs.add(DS_DROP);
    return propDescs;
  }

  @OnScheduled
  public void onScheduled(final ProcessContext context) {
    HikariCPService dbcpService =
        (HikariCPService) context.getProperty(DS_DROP).asControllerService();
    try (Connection conn = dbcpService.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("values 1")) {
      while (rs.next()) {
        getLogger().info("Result: " + rs.getObject(1));
      }
    } catch (Exception e) {
      getLogger().error("Problem testing connection, e");
    }
  }
}
