/*    */ package io.netty.handler.codec.protobuf;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
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
/*    */ public class ProtobufVarint32LengthFieldPrepender
/*    */   extends MessageToByteEncoder<ByteBuf>
/*    */ {
/*    */   public ProtobufVarint32LengthFieldPrepender() {
/* 44 */     super(ByteBuf.class);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
/* 50 */     int bodyLen = msg.readableBytes();
/* 51 */     int headerLen = computeRawVarint32Size(bodyLen);
/* 52 */     out.ensureWritable(headerLen + bodyLen);
/* 53 */     writeRawVarint32(out, bodyLen);
/* 54 */     out.writeBytes(msg, msg.readerIndex(), bodyLen);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static void writeRawVarint32(ByteBuf out, int value) {
/*    */     while (true) {
/* 64 */       if ((value & 0xFFFFFF80) == 0) {
/* 65 */         out.writeByte(value);
/*    */         return;
/*    */       } 
/* 68 */       out.writeByte(value & 0x7F | 0x80);
/* 69 */       value >>>= 7;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static int computeRawVarint32Size(int value) {
/* 80 */     if ((value & 0xFFFFFF80) == 0) {
/* 81 */       return 1;
/*    */     }
/* 83 */     if ((value & 0xFFFFC000) == 0) {
/* 84 */       return 2;
/*    */     }
/* 86 */     if ((value & 0xFFE00000) == 0) {
/* 87 */       return 3;
/*    */     }
/* 89 */     if ((value & 0xF0000000) == 0) {
/* 90 */       return 4;
/*    */     }
/* 92 */     return 5;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\protobuf\ProtobufVarint32LengthFieldPrepender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */