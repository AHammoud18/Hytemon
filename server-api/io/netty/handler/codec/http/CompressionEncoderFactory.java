package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToByteEncoder;

interface CompressionEncoderFactory {
  MessageToByteEncoder<ByteBuf> createEncoder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\CompressionEncoderFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */