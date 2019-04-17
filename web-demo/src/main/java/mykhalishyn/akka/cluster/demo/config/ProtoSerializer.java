package mykhalishyn.akka.cluster.demo.config;

import akka.serialization.SerializerWithStringManifest;
import com.google.protobuf.InvalidProtocolBufferException;
import mykhalishyn.akka.cluster.demo.actor.MessageProto.Task;

/**
 * Proto Serializer
 * @author dmihalishin@gmail.com
 */
public class ProtoSerializer extends SerializerWithStringManifest {

    private static final String TASK_MANIFEST = "task";

    // 0 - 40 is reserved by Akka itself
    @Override
    public int identifier() {
        return 1234567;
    }

    @Override
    public String manifest(Object obj) {
        if (obj instanceof Task)
            return TASK_MANIFEST;
        else
            throw new IllegalArgumentException("Unknown type: " + obj);
    }

    // "toBinary" serializes the given object to an Array of Bytes
    @Override
    public byte[] toBinary(Object obj) {
        // Put the real code that serializes the object here
        if (obj instanceof Task)
            return ((Task) obj).toByteArray();
        else
            throw new IllegalArgumentException("Unknown type: " + obj);
    }

    // "fromBinary" deserializes the given array,
    // using the type hint
    @Override
    public Object fromBinary(byte[] bytes, String manifest) {
        // Put the real code that deserializes here
        try {
            switch (manifest) {
                case TASK_MANIFEST:
                    return Task.parseFrom(bytes);
                default:
                    throw new IllegalArgumentException("Unknown manifest: " + manifest);
            }
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
