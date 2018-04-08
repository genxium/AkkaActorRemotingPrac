package com.sample.remoting.akka;

import akka.serialization.JSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MyAppSerializer extends JSerializer {
    private static final Logger logger = LoggerFactory.getLogger(MyAppSerializer.class);

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object fromBinaryJava(byte[] bytes, Class<?> manifest) {
        try {
            return mapper.readValue(bytes, manifest);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public int identifier() {
        // This is just a magic number.
        return 1011;
    }

    @Override
    public byte[] toBinary(Object o) {
        try {
            return mapper.writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            return new byte[0];
        }
    }

    @Override
    public boolean includeManifest() {
        return true;
    }
}
