/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import com.aayushatharva.brotli4j.encoder.Encoder;
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ public final class BrotliOptions
/*    */   implements CompressionOptions
/*    */ {
/*    */   private final Encoder.Parameters parameters;
/* 32 */   static final BrotliOptions DEFAULT = new BrotliOptions((new Encoder.Parameters())
/* 33 */       .setQuality(4).setMode(Encoder.Mode.TEXT));
/*    */ 
/*    */   
/*    */   BrotliOptions(Encoder.Parameters parameters) {
/* 37 */     if (!Brotli.isAvailable()) {
/* 38 */       throw new IllegalStateException("Brotli is not available", Brotli.cause());
/*    */     }
/*    */     
/* 41 */     this.parameters = (Encoder.Parameters)ObjectUtil.checkNotNull(parameters, "Parameters");
/*    */   }
/*    */   
/*    */   public Encoder.Parameters parameters() {
/* 45 */     return this.parameters;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\BrotliOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */