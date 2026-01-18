/*    */ package io.netty.handler.codec.protobuf;
/*    */ 
/*    */ import com.google.protobuf.nano.CodedOutputByteBufferNano;
/*    */ import com.google.protobuf.nano.MessageNano;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageEncoder;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Sharable
/*    */ public class ProtobufEncoderNano
/*    */   extends MessageToMessageEncoder<MessageNano>
/*    */ {
/*    */   public ProtobufEncoderNano() {
/* 62 */     super(MessageNano.class);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, MessageNano msg, List<Object> out) throws Exception {
/* 68 */     int size = msg.getSerializedSize();
/* 69 */     ByteBuf buffer = ctx.alloc().heapBuffer(size, size);
/* 70 */     byte[] array = buffer.array();
/* 71 */     CodedOutputByteBufferNano cobbn = CodedOutputByteBufferNano.newInstance(array, buffer
/* 72 */         .arrayOffset(), buffer.capacity());
/* 73 */     msg.writeTo(cobbn);
/* 74 */     buffer.writerIndex(size);
/* 75 */     out.add(buffer);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\protobuf\ProtobufEncoderNano.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */