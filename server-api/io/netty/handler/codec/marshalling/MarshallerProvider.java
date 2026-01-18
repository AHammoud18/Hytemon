package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import org.jboss.marshalling.Marshaller;

public interface MarshallerProvider {
  Marshaller getMarshaller(ChannelHandlerContext paramChannelHandlerContext) throws Exception;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\marshalling\MarshallerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */