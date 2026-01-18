package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import org.jboss.marshalling.Unmarshaller;

public interface UnmarshallerProvider {
  Unmarshaller getUnmarshaller(ChannelHandlerContext paramChannelHandlerContext) throws Exception;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\marshalling\UnmarshallerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */