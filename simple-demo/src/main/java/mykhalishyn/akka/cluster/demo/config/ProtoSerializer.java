package mykhalishyn.akka.cluster.demo.config;

import akka.serialization.SerializerWithStringManifest;
import com.google.protobuf.InvalidProtocolBufferException;
import mykhalishyn.akka.cluster.demo.actor.SimpleDemoProtos.PingRequest;
import mykhalishyn.akka.cluster.demo.actor.SimpleDemoProtos.PongResponse;

/**
 * Proto Serializer
 *
 * @author dmihalishin@gmail.com
 */
public class ProtoSerializer extends SerializerWithStringManifest {

    private static final String PING_MANIFEST = "ping";
    private static final String PONG_MANIFEST = "pong";

    // 0 - 40 is reserved by Akka itself
    @Override
    public int identifier() {
        return 1234567;
    }

    @Override
    public String manifest(Object obj) {
        if (obj instanceof PingRequest)
            return PING_MANIFEST;
        else if (obj instanceof PongResponse)
            return PONG_MANIFEST;
        else
            throw new IllegalArgumentException("Unknown type: " + obj);
    }

    // "toBinary" serializes the given object to an Array of Bytes
    @Override
    public byte[] toBinary(Object obj) {
        // Put the real code that serializes the object here
        if (obj instanceof PingRequest)
            return ((PingRequest) obj).toByteArray();
        else if (obj instanceof PongResponse)
            return ((PongResponse) obj).toByteArray();
        else
            throw new IllegalArgumentException("Unknown type: " + obj);
    }

    // "fromBinary" deserializes the given array,
    @Override
    public Object fromBinary(byte[] bytes, String manifest) {
        // Put the real code that deserializes here
        try {
            switch (manifest) {
                case PING_MANIFEST:
                    return PingRequest.parseFrom(bytes);
                case PONG_MANIFEST:
                    return PongResponse.parseFrom(bytes);
                default:
                    throw new IllegalArgumentException("Unknown manifest: " + manifest);
            }
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
