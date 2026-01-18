/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import com.aayushatharva.brotli4j.encoder.Encoder;
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
/*    */ public enum BrotliMode
/*    */ {
/* 32 */   GENERIC,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   TEXT,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   FONT;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   Encoder.Mode adapt() {
/* 50 */     switch (this) {
/*    */       case GENERIC:
/* 52 */         return Encoder.Mode.GENERIC;
/*    */       case TEXT:
/* 54 */         return Encoder.Mode.TEXT;
/*    */       case FONT:
/* 56 */         return Encoder.Mode.FONT;
/*    */     } 
/* 58 */     throw new IllegalStateException("Unsupported enum value: " + this);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\BrotliMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */