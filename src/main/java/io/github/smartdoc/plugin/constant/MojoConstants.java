/*
 * smart-doc
 *
 * Copyright (C) 2018-2026 smart-doc
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
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
package io.github.smartdoc.plugin.constant;

import io.github.smartdoc.constants.DocGoal;

/**
 * Goal names declared by this plugin.
 * <p>
 * Every value comes from {@link DocGoal}, so the names declared here, the families
 * declared by {@code GoalFamily} and the names used in diagnostics cannot drift apart.
 *
 * @author yu 2021/10/30.
 */
public interface MojoConstants {
    /**
     * generate several formats from one parse
     */
    String GENERATE_MOJO = DocGoal.GENERATE;

    /**
     * api-adoc
     */
    String ADOC_MOJO = DocGoal.ADOC;

    /**
     * api-html
     */
    String HTML_MOJO = DocGoal.HTML;

    /**
     * api-markdown
     */
    String MARKDOWN_MOJO = DocGoal.MARKDOWN;

    /**
     * api-jmeter
     */
    String JMETER_MOJO = DocGoal.JMETER;

    /**
     * api-openapi
     */
    String OPENAPI_MOJO = DocGoal.OPENAPI;

    /**
     * api-swagger
     */
    String SWAGGER_MOJO = DocGoal.SWAGGER;

    /**
     * api-postman
     */
    String POSTMAN_MOJO = DocGoal.POSTMAN;

    /**
     * api-word
     */
    String WORD_MOJO = DocGoal.WORD;

    /**
     * javadoc-adoc
     */
    String JAVADOC_ADOC_MOJO = DocGoal.JAVADOC_ADOC;

    /**
     * javadoc-html
     */
    String JAVADOC_HTML_MOJO = DocGoal.JAVADOC_HTML;

    /**
     * javadoc-markdown
     */
    String JAVADOC_MARKDOWN_MOJO = DocGoal.JAVADOC_MARKDOWN;

    /**
     * rpc-adoc
     */
    String RPC_ADOC_MOJO = DocGoal.RPC_ADOC;

    /**
     * rpc-word
     */
    String RPC_WORD_MOJO = DocGoal.RPC_WORD;

    /**
     * rpc-html
     */
    String RPC_HTML_MOJO = DocGoal.RPC_HTML;

    /**
     * rpc-markdown
     */
    String RPC_MARKDOWN_MOJO = DocGoal.RPC_MARKDOWN;

    /**
     * torna-rest
     */
    String TORNA_REST_MOJO = DocGoal.TORNA_REST;
    /**
     * torna-rpc
     */
    String TORNA_RPC_MOJO = DocGoal.TORNA_RPC;


    /**
     * websocket markdown
     */
    String WEBSOCKET_MARKDOWN_MOJO = DocGoal.WEBSOCKET_MARKDOWN;

    /**
     * websocket html.
     */
    String WEBSOCKET_HTML_MOJO = DocGoal.WEBSOCKET_HTML;

    /**
     * websocket adoc.
     */
    String WEBSOCKET_ADOC_MOJO = DocGoal.WEBSOCKET_ADOC;

    /**
     * grpc markdown.
     */
    String GRPC_MARKDOWN_MOJO = DocGoal.GRPC_MARKDOWN;

    /**
     * grpc html.
     */
    String GRPC_HTML_MOJO = DocGoal.GRPC_HTML;

    /**
     * grpc adoc.
     */
    String GRPC_ADOC_MOJO = DocGoal.GRPC_ADOC;
}
