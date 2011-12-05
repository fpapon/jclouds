/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.tmrk.enterprisecloud.config;

import java.util.Map;

import org.jclouds.http.HttpErrorHandler;
import org.jclouds.http.HttpRetryHandler;
import org.jclouds.http.RequiresHttp;
import org.jclouds.http.annotation.ClientError;
import org.jclouds.http.annotation.Redirection;
import org.jclouds.http.annotation.ServerError;
import org.jclouds.http.handlers.BackoffLimitedRetryHandler;
import org.jclouds.rest.ConfiguresRestClient;
import org.jclouds.rest.config.RestClientModule;
import org.jclouds.tmrk.enterprisecloud.TerremarkEnterpriseCloudAsyncClient;
import org.jclouds.tmrk.enterprisecloud.TerremarkEnterpriseCloudClient;
import org.jclouds.tmrk.enterprisecloud.features.*;
import org.jclouds.tmrk.enterprisecloud.handlers.TerremarkEnterpriseCloudErrorHandler;

import com.google.common.collect.ImmutableMap;

/**
 * Configures the TerremarkEnterpriseCloud connection.
 * 
 * @author Adrian Cole
 */
@RequiresHttp
@ConfiguresRestClient
public class TerremarkEnterpriseCloudRestClientModule extends
      RestClientModule<TerremarkEnterpriseCloudClient, TerremarkEnterpriseCloudAsyncClient> {

   public static final Map<Class<?>, Class<?>> DELEGATE_MAP = ImmutableMap.<Class<?>, Class<?>> builder()
         .put(LocationClient.class, LocationAsyncClient.class)
         .put(TaskClient.class, TaskAsyncClient.class)
         .put(VirtualMachineClient.class, VirtualMachineAsyncClient.class)
         .put(TemplateClient.class, TemplateAsyncClient.class)
         .build();

   public TerremarkEnterpriseCloudRestClientModule() {
      super(TerremarkEnterpriseCloudClient.class, TerremarkEnterpriseCloudAsyncClient.class, DELEGATE_MAP);
   }

   @Override
   protected void configure() {
      super.configure();
   }

   @Override
   protected void bindErrorHandlers() {
      bind(HttpErrorHandler.class).annotatedWith(Redirection.class).to(TerremarkEnterpriseCloudErrorHandler.class);
      bind(HttpErrorHandler.class).annotatedWith(ClientError.class).to(TerremarkEnterpriseCloudErrorHandler.class);
      bind(HttpErrorHandler.class).annotatedWith(ServerError.class).to(TerremarkEnterpriseCloudErrorHandler.class);
   }

   @Override
   protected void bindRetryHandlers() {
      bind(HttpRetryHandler.class).annotatedWith(ClientError.class).to(BackoffLimitedRetryHandler.class);
   }

}
