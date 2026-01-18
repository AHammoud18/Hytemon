/*    */ package io.netty.handler.codec.protobuf;
/*    */ 
/*    */ import com.google.protobuf.MessageLite;
/*    */ import com.google.protobuf.MessageLiteOrBuilder;
/*    */ import io.netty.buffer.Unpooled;
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
/*    */ 
/*    */ 
/*    */ @Sharable
/*    */ public class ProtobufEncoder
/*    */   extends MessageToMessageEncoder<MessageLiteOrBuilder>
/*    */ {
/*    */   public ProtobufEncoder() {
/* 64 */     super(MessageLiteOrBuilder.class);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) throws Exception {
/* 70 */     if (msg instanceof MessageLite) {
/* 71 */       out.add(Unpooled.wrappedBuffer(((MessageLite)msg).toByteArray()));
/*    */       return;
/*    */     } 
/* 74 */     if (msg instanceof MessageLite.Builder)
/* 75 */       out.add(Unpooled.wrappedBuffer(((MessageLite.Builder)msg).build().toByteArray())); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\protobuf\ProtobufEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */