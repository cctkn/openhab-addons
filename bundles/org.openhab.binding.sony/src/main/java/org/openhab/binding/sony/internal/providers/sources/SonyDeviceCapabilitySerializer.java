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
package org.openhab.binding.sony.internal.providers.sources;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.sony.internal.providers.models.SonyDeviceCapability;

/**
 * This class represents the serializer for a SonyDeviceCapability that will remove the baseURL (private information)
 * from the serialized bit.
 *
 * @author Tim Roberts - Initial contribution
 */
@NonNullByDefault
public class SonyDeviceCapabilitySerializer implements JsonSerializer<SonyDeviceCapability> {
    @Override
    public JsonElement serialize(SonyDeviceCapability src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonElement je = context.serialize(src, typeOfSrc);
        if (je != null && je instanceof JsonObject) {
            final JsonObject jo = je.getAsJsonObject();
            jo.remove("baseURL");
            return jo;
        }
        return je;
    }
}
