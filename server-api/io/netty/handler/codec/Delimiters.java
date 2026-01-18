/*    */ package io.netty.handler.codec;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
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
/*    */ public final class Delimiters
/*    */ {
/*    */   public static ByteBuf[] nulDelimiter() {
/* 31 */     return new ByteBuf[] {
/* 32 */         Unpooled.wrappedBuffer(new byte[] { 0 })
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ByteBuf[] lineDelimiter() {
/* 40 */     return new ByteBuf[] {
/* 41 */         Unpooled.wrappedBuffer(new byte[] { 13, 10
/* 42 */           }), Unpooled.wrappedBuffer(new byte[] { 10 })
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\Delimiters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */