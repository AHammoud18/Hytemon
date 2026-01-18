package com.nimbusds.jose;

public interface PayloadTransformer<T> {
  T transform(Payload paramPayload);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\PayloadTransformer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */