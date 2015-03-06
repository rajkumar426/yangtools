/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.model.api.stmt;

import javax.annotation.Nonnull;
import org.opendaylight.yangtools.yang.model.api.RevisionAwareXPath;
import org.opendaylight.yangtools.yang.model.api.meta.DeclaredStatement;

public interface PathStatement extends DeclaredStatement<RevisionAwareXPath> {

    // FIXME: Introduce proper type representing parsed leafref
    @Nonnull String getValue();

}
