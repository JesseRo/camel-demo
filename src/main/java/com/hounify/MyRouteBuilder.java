/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hounify;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.Endpoint;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpEndpoint;
import org.apache.camel.component.jetty.JettyHttpEndpoint;
import org.apache.camel.component.jetty9.JettyHttpEndpoint9;
import org.apache.camel.spring.Main;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class MyRouteBuilder extends RouteBuilder {

    /**
     * Allow this route to be run as an application
     */
    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    public void configure() throws URISyntaxException {
//        from("jetty:http://0.0.0.0:9999/cxf/camel-spring?matchOnUriPrefix=true")
//                .routeId("camel-spring")
//                .removeHeaders("CamelHttp*")
//                .process(exchange -> {
//                    Map<String ,Object> m =  exchange.getProperties();
//                    m.clear();
//                })
//                .to("http://localhost:8080/detail").process(exchange -> {
//                    exchange.getIn();
//        });
        CamelContext camelContext = getContext();
        Endpoint endpoint = camelContext.getEndpoint("http://localhost:8080/detail");
        HttpEndpoint httpEndpoint = (HttpEndpoint)endpoint;
        httpEndpoint.setBridgeEndpoint(true);

        restConfiguration().component("jetty").port(9999);
        rest("/cxf/camel-spring?matchOnUriPrefix=true").get().id("23123123").route().to(endpoint);
        rest("/cxf/dasdasd").post().id("post").route().to(endpoint);
    }

//    public

    public static class SomeBean {

        public void someMethod(String body) {
            System.out.println("Received: " + body);
        }
    }

}
