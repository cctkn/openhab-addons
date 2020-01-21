/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.sony.internal.transports;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * A transport option to specify whether to automatically authenticate the communication
 * 
 * @author Tim Roberts - Initial contribution
 */
@NonNullByDefault
public class TransportOptionAutoAuth implements TransportOption {
    /** Do automatically authenticate */
    public static final TransportOptionAutoAuth TRUE = new TransportOptionAutoAuth(true);

    /** Do NOT automatically authenticate */
    public static final TransportOptionAutoAuth FALSE = new TransportOptionAutoAuth(false);

    /** Whether to automatically authenticate */
    private final boolean autoAuth;

    /**
     * Constructs the option from the parameter
     * 
     * @param autoAuth whether to automatically authenticate (true) or not (false)
     */
    private TransportOptionAutoAuth(final boolean autoAuth) {
        this.autoAuth = autoAuth;
    }

    /**
     * Whether to automatically authenticate
     * 
     * @return true if enabled, false if not
     */
    public boolean isAutoAuth() {
        return autoAuth;
    }
}
