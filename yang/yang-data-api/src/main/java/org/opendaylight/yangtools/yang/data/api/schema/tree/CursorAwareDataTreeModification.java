/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.data.api.schema.tree;

import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier;

/**
 * A {@link DataTreeModification} which allows creation of a {@link DataTreeModificationCursor}.
 */
@Beta
public interface CursorAwareDataTreeModification extends DataTreeModification, CursorAwareDataTreeSnapshot {
    /**
     * Create a new {@link DataTreeModificationCursor} at specified path. May fail
     * if specified path does not exist. It is a programming error to use normal
     *
     *
     * @param path Path at which the cursor is to be anchored
     * @return A new cursor, or null if the path does not exist.
     * @throws IllegalStateException if there is another cursor currently open,
     *                               or the modification is already {@link #ready()}.
     */
    @Override
    @Nullable DataTreeModificationCursor createCursor(@Nonnull YangInstanceIdentifier path);
}
