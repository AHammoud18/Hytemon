/*    */ package io.netty.handler.codec.protobuf;
/*    */ 
/*    */ import com.google.protobuf.nano.MessageNano;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufUtil;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ 
/*    */ 
/*    */ @Sharable
/*    */ public class ProtobufDecoderNano
/*    */   extends MessageToMessageDecoder<ByteBuf>
/*    */ {
/*    */   private final Class<? extends MessageNano> clazz;
/*    */   
/*    */   public ProtobufDecoderNano(Class<? extends MessageNano> clazz) {
/* 69 */     super(ByteBuf.class);
/* 70 */     this.clazz = (Class<? extends MessageNano>)ObjectUtil.checkNotNull(clazz, "You must provide a Class");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
/*    */     byte[] array;
/* 78 */     int offset, length = msg.readableBytes();
/* 79 */     if (msg.hasArray()) {
/* 80 */       array = msg.array();
/* 81 */       offset = msg.arrayOffset() + msg.readerIndex();
/*    */     } else {
/* 83 */       array = ByteBufUtil.getBytes(msg, msg.readerIndex(), length, false);
/* 84 */       offset = 0;
/*    */     } 
/* 86 */     MessageNano prototype = this.clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
/* 87 */     out.add(MessageNano.mergeFrom(prototype, array, offset, length));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\protobuf\ProtobufDecoderNano.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */