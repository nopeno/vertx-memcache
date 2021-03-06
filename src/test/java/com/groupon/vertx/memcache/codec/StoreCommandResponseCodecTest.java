/**
 * Copyright 2014 Groupon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.groupon.vertx.memcache.codec;

import static org.junit.Assert.assertEquals;

import io.vertx.core.buffer.Buffer;
import org.junit.Before;
import org.junit.Test;

import com.groupon.vertx.memcache.client.JsendStatus;
import com.groupon.vertx.memcache.client.response.StoreCommandResponse;

/**
 * Tests for <code>StoreCommandResponseCodec</code>.
 *
 * @author Stuart Siegrist (fsiegrist at groupon dot com)
 * @since 3.1.0
 */
public class StoreCommandResponseCodecTest {
    private StoreCommandResponseCodec codec;
    private StoreCommandResponse command;

    @Before
    public void setUp() {
        codec = new StoreCommandResponseCodec();
        command = new StoreCommandResponse.Builder()
                .setData("data")
                .setStatus(JsendStatus.success)
                .setMessage("message")
                .build();
    }

    @Test
    public void testName() {
        assertEquals(codec.getClass().getSimpleName(), codec.name());
    }

    @Test
    public void testTransform() {
        assertEquals(command, codec.transform(command));
    }

    @Test
    public void testSystemCodecId() {
        assertEquals(-1, codec.systemCodecID());
    }

    @Test
    public void testEncodeToWireAndDecodeFromWire() {
        Buffer buffer = Buffer.buffer();
        codec.encodeToWire(buffer, command);

        StoreCommandResponse endCommand = codec.decodeFromWire(0, buffer);

        assertEquals(command.getStatus(), endCommand.getStatus());
        assertEquals(command.getData(), endCommand.getData());
        assertEquals(command.getMessage(), endCommand.getMessage());
    }
}
