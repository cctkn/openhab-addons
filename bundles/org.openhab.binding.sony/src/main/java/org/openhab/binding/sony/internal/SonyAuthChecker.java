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
package org.openhab.binding.sony.internal;

import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.sony.internal.net.NetUtil;
import org.openhab.binding.sony.internal.scalarweb.ScalarWebConstants;
import org.openhab.binding.sony.internal.transports.SonyTransport;
import org.openhab.binding.sony.internal.transports.TransportOptionHeader;

/**
 * This class contains the logic to determine if an authorization call is needed (via {@link SonyAuth})
 * 
 * @author Tim Roberts - Initial contribution
 */
@NonNullByDefault
public class SonyAuthChecker {
    /** The transport to use for check authorization */
    private final SonyTransport transport;

    /** The current access code */
    private final @Nullable String accessCode;

    /**
     * Constructs the checker from the transport and access code
     * 
     * @param transport a non-null transport
     * @param accessCode a possibly null, possibly empty access code
     */
    public SonyAuthChecker(final SonyTransport transport, final @Nullable String accessCode) {
        Objects.requireNonNull(transport, "transport cannot be null");

        this.transport = transport;
        this.accessCode = accessCode;
    }

    /**
     * Checks the result using the specified callback
     * 
     * @param callback a non-null callback
     * @return a non-null result
     */
    public CheckResult checkResult(final CheckResultCallback callback) {
        Objects.requireNonNull(callback, "callback cannot be null");

        // Check to see if auth
        final String localAccessCode = accessCode;
        if (localAccessCode != null
                && !StringUtils.equalsIgnoreCase(ScalarWebConstants.ACCESSCODE_RQST, localAccessCode)) {
            final TransportOptionHeader authHeader = new TransportOptionHeader(
                    NetUtil.createAccessCodeHeader(localAccessCode));
            try {
                transport.setOption(authHeader);
                if (AccessResult.OK.equals(callback.checkResult())) {
                    return CheckResult.OK_HEADER;
                }
            } finally {
                transport.removeOption(authHeader);
            }
        }

        final AccessResult res = callback.checkResult();
        if (res == null) {
            return new CheckResult(CheckResult.OTHER, "Check result returned null");
        }

        if (AccessResult.OK.equals(res)) {
            return CheckResult.OK_COOKIE;
        }
        return new CheckResult(res);
    }

    /**
     * Functional interface defining the check result callback
     */
    @NonNullByDefault
    public interface CheckResultCallback {
        /**
         * Called to check a result and return an {@link AccessResult}
         * 
         * @return a non-null access result
         */
        AccessResult checkResult();
    }
}
