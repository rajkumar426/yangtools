/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.model.repo.api;

import com.google.common.annotations.Beta;
import com.google.common.util.concurrent.CheckedFuture;
import java.util.Collection;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.model.api.SchemaContext;

/**
 * An asynchronous factory for building {@link SchemaContext} instances based on
 * a specification of what {@link SourceIdentifier}s are required and dynamic
 * recursive resolution.
 */
@Beta
public interface SchemaContextFactory {
    /**
     * Create a new schema context containing specified sources, pulling in any
     * dependencies they may have.
     *
     * @param requiredSources
     *            a collection of sources which are required to be present
     * @return A checked future, which will produce a schema context, or fail
     *         with an explanation why the creation of the schema context
     *         failed.
     */
    default CheckedFuture<SchemaContext, SchemaResolutionException> createSchemaContext(
            @Nonnull Collection<SourceIdentifier> requiredSources) {
        return createSchemaContext(requiredSources, StatementParserMode.DEFAULT_MODE, IfFeaturePredicates.ALL_FEATURES);
    }

    /**
     * Create a new schema context containing specified sources, pulling in any
     * dependencies they may have.
     *
     * @param requiredSources
     *            a collection of sources which are required to be present
     * @param statementParserMode
     *            mode of statement parser
     * @return A checked future, which will produce a schema context, or fail
     *         with an explanation why the creation of the schema context
     *         failed.
     */
    default CheckedFuture<SchemaContext, SchemaResolutionException> createSchemaContext(
            Collection<SourceIdentifier> requiredSources, StatementParserMode statementParserMode) {
        return createSchemaContext(requiredSources, statementParserMode, IfFeaturePredicates.ALL_FEATURES);
    }

    /**
     * Create a new schema context containing specified sources, pulling in any
     * dependencies they may have.
     *
     * @param requiredSources
     *            a collection of sources which are required to be present
     * @param isFeatureSupported
     *            a predicate based on which all if-feature statements in the
     *            parsed yang models are resolved
     * @return A checked future, which will produce a schema context, or fail
     *         with an explanation why the creation of the schema context
     *         failed.
     */
    default CheckedFuture<SchemaContext, SchemaResolutionException> createSchemaContext(
            @Nonnull Collection<SourceIdentifier> requiredSources, Predicate<QName> isFeatureSupported) {
        return createSchemaContext(requiredSources, StatementParserMode.DEFAULT_MODE, isFeatureSupported);
    }

    /**
     * Create a new schema context containing specified sources, pulling in any
     * dependencies they may have.
     *
     * @param requiredSources
     *            a collection of sources which are required to be present
     * @param statementParserMode
     *            mode of statement parser
     * @param isFeatureSupported
     *            a predicate based on which all if-feature statements in the
     *            parsed yang models are resolved
     * @return A checked future, which will produce a schema context, or fail
     *         with an explanation why the creation of the schema context
     *         failed.
     */
    CheckedFuture<SchemaContext, SchemaResolutionException> createSchemaContext(
            Collection<SourceIdentifier> requiredSources, StatementParserMode statementParserMode,
            Predicate<QName> isFeatureSupported);
}
