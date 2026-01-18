/*    */ package org.bson.internal;
/*    */ 
/*    */ import org.bson.UuidRepresentation;
/*    */ import org.bson.assertions.Assertions;
/*    */ import org.bson.codecs.Codec;
/*    */ import org.bson.codecs.OverridableUuidRepresentationCodec;
/*    */ import org.bson.codecs.configuration.CodecProvider;
/*    */ import org.bson.codecs.configuration.CodecRegistry;
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
/*    */ public class OverridableUuidRepresentationCodecRegistry
/*    */   implements CycleDetectingCodecRegistry
/*    */ {
/*    */   private final CodecProvider wrapped;
/* 30 */   private final CodecCache codecCache = new CodecCache();
/*    */   private final UuidRepresentation uuidRepresentation;
/*    */   
/*    */   OverridableUuidRepresentationCodecRegistry(CodecProvider wrapped, UuidRepresentation uuidRepresentation) {
/* 34 */     this.uuidRepresentation = (UuidRepresentation)Assertions.notNull("uuidRepresentation", uuidRepresentation);
/* 35 */     this.wrapped = (CodecProvider)Assertions.notNull("wrapped", wrapped);
/*    */   }
/*    */   
/*    */   public UuidRepresentation getUuidRepresentation() {
/* 39 */     return this.uuidRepresentation;
/*    */   }
/*    */   
/*    */   public CodecProvider getWrapped() {
/* 43 */     return this.wrapped;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> Codec<T> get(Class<T> clazz) {
/* 48 */     return get(new ChildCodecRegistry<>(this, clazz));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
/* 55 */     Codec<T> codec = this.wrapped.get(clazz, registry);
/* 56 */     if (codec instanceof OverridableUuidRepresentationCodec) {
/* 57 */       return ((OverridableUuidRepresentationCodec)codec).withUuidRepresentation(this.uuidRepresentation);
/*    */     }
/* 59 */     return codec;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> Codec<T> get(ChildCodecRegistry<T> context) {
/* 65 */     if (!this.codecCache.containsKey(context.getCodecClass())) {
/* 66 */       Codec<T> codec = this.wrapped.get(context.getCodecClass(), context);
/* 67 */       if (codec instanceof OverridableUuidRepresentationCodec) {
/* 68 */         codec = ((OverridableUuidRepresentationCodec)codec).withUuidRepresentation(this.uuidRepresentation);
/*    */       }
/* 70 */       this.codecCache.put(context.getCodecClass(), codec);
/*    */     } 
/* 72 */     return this.codecCache.getOrThrow(context.getCodecClass());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 77 */     if (this == o) {
/* 78 */       return true;
/*    */     }
/* 80 */     if (o == null || getClass() != o.getClass()) {
/* 81 */       return false;
/*    */     }
/*    */     
/* 84 */     OverridableUuidRepresentationCodecRegistry that = (OverridableUuidRepresentationCodecRegistry)o;
/*    */     
/* 86 */     if (!this.wrapped.equals(that.wrapped)) {
/* 87 */       return false;
/*    */     }
/* 89 */     return (this.uuidRepresentation == that.uuidRepresentation);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 94 */     int result = this.wrapped.hashCode();
/* 95 */     result = 31 * result + this.uuidRepresentation.hashCode();
/* 96 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\internal\OverridableUuidRepresentationCodecRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */