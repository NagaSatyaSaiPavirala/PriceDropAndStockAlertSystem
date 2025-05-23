package com.pricedrop.codec;

import com.pricedrop.model.URL;
import io.lettuce.core.codec.RedisCodec;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayOutputStream;


public class URLSerializationCodec implements RedisCodec<String, URL> {
    private final Charset charset= StandardCharsets.UTF_8;
    @Override
    public String decodeKey(ByteBuffer byteBuffer) {
        return charset.decode(byteBuffer).toString();
    }

    @Override
    public URL decodeValue(ByteBuffer byteBuffer) {
        try {
            byte[] array = new byte[byteBuffer.remaining()];
            byteBuffer.get(array);
            ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(array));
            return (URL)is.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ByteBuffer encodeKey(String s) {
        return charset.encode(s);
    }

    @Override
    public ByteBuffer encodeValue(URL url) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bytes);
            os.writeObject(url);
            return ByteBuffer.wrap(bytes.toByteArray());
        } catch (IOException e) {
            return ByteBuffer.wrap(new byte[0]);
        }
    }
}
