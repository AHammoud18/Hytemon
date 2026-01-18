/*     */ package org.bson.codecs;
/*     */ 
/*     */ import org.bson.BsonReader;
/*     */ import org.bson.assertions.Assertions;
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
/*     */ public final class DecoderContext
/*     */ {
/*  30 */   private static final DecoderContext DEFAULT_CONTEXT = builder().build();
/*     */ 
/*     */   
/*     */   private final boolean checkedDiscriminator;
/*     */ 
/*     */   
/*     */   public boolean hasCheckedDiscriminator() {
/*  37 */     return this.checkedDiscriminator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder builder() {
/*  46 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private boolean checkedDiscriminator;
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasCheckedDiscriminator() {
/*  62 */       return this.checkedDiscriminator;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder checkedDiscriminator(boolean checkedDiscriminator) {
/*  72 */       this.checkedDiscriminator = checkedDiscriminator;
/*  73 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DecoderContext build() {
/*  81 */       return new DecoderContext(this);
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
/*     */ 
/*     */   
/*     */   public <T> T decodeWithChildContext(Decoder<T> decoder, BsonReader reader) {
/*  95 */     Assertions.notNull("decoder", decoder);
/*  96 */     return decoder.decode(reader, DEFAULT_CONTEXT);
/*     */   }
/*     */   
/*     */   private DecoderContext(Builder builder) {
/* 100 */     this.checkedDiscriminator = builder.hasCheckedDiscriminator();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\DecoderContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */