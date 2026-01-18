/*     */ package org.bson.codecs;
/*     */ 
/*     */ import org.bson.BsonWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EncoderContext
/*     */ {
/*  29 */   private static final EncoderContext DEFAULT_CONTEXT = builder().build();
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean encodingCollectibleDocument;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder builder() {
/*  39 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private boolean encodingCollectibleDocument;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder isEncodingCollectibleDocument(boolean encodingCollectibleDocument) {
/*  58 */       this.encodingCollectibleDocument = encodingCollectibleDocument;
/*  59 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EncoderContext build() {
/*  67 */       return new EncoderContext(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEncodingCollectibleDocument() {
/*  79 */     return this.encodingCollectibleDocument;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void encodeWithChildContext(Encoder<T> encoder, BsonWriter writer, T value) {
/*  91 */     encoder.encode(writer, value, DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncoderContext getChildContext() {
/* 100 */     return DEFAULT_CONTEXT;
/*     */   }
/*     */   
/*     */   private EncoderContext(Builder builder) {
/* 104 */     this.encodingCollectibleDocument = builder.encodingCollectibleDocument;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\EncoderContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */