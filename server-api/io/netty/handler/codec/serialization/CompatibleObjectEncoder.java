/*    */ package io.netty.handler.codec.serialization;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufOutputStream;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.Serializable;
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
/*    */ @Deprecated
/*    */ public class CompatibleObjectEncoder
/*    */   extends MessageToByteEncoder<Serializable>
/*    */ {
/*    */   private final int resetInterval;
/*    */   private int writtenObjects;
/*    */   
/*    */   public CompatibleObjectEncoder() {
/* 56 */     this(16);
/*    */   }
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
/*    */   public CompatibleObjectEncoder(int resetInterval) {
/* 69 */     super(Serializable.class);
/* 70 */     this.resetInterval = ObjectUtil.checkPositiveOrZero(resetInterval, "resetInterval");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ObjectOutputStream newObjectOutputStream(OutputStream out) throws Exception {
/* 79 */     return new ObjectOutputStream(out);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {
/* 85 */     ObjectOutputStream oos = newObjectOutputStream((OutputStream)new ByteBufOutputStream(out));
/*    */     
/*    */     try {
/* 88 */       if (this.resetInterval != 0) {
/*    */         
/* 90 */         this.writtenObjects++;
/* 91 */         if (this.writtenObjects % this.resetInterval == 0) {
/* 92 */           oos.reset();
/*    */         }
/*    */       } 
/*    */       
/* 96 */       oos.writeObject(msg);
/* 97 */       oos.flush();
/*    */     } finally {
/* 99 */       oos.close();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\serialization\CompatibleObjectEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */