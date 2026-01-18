package io.netty.handler.codec.http;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.DecoderResultProvider;

public interface HttpObject extends DecoderResultProvider {
  @Deprecated
  DecoderResult getDecoderResult();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */