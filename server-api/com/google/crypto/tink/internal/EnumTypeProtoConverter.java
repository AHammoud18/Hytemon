/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ @Immutable
/*    */ public final class EnumTypeProtoConverter<E extends Enum<E>, O>
/*    */ {
/*    */   private final Map<E, O> fromProtoEnumMap;
/*    */   private final Map<O, E> toProtoEnumMap;
/*    */   
/*    */   private EnumTypeProtoConverter(Map<E, O> fromProtoEnumMap, Map<O, E> toProtoEnumMap) {
/* 39 */     this.fromProtoEnumMap = fromProtoEnumMap;
/* 40 */     this.toProtoEnumMap = toProtoEnumMap;
/*    */   }
/*    */   
/*    */   public static final class Builder<E extends Enum<E>, O>
/*    */   {
/* 45 */     Map<E, O> fromProtoEnumMap = new HashMap<>();
/* 46 */     Map<O, E> toProtoEnumMap = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     @CanIgnoreReturnValue
/*    */     public Builder<E, O> add(E protoEnum, O objectEnum) {
/* 53 */       this.fromProtoEnumMap.put(protoEnum, objectEnum);
/* 54 */       this.toProtoEnumMap.put(objectEnum, protoEnum);
/* 55 */       return this;
/*    */     }
/*    */     private Builder() {}
/*    */     public EnumTypeProtoConverter<E, O> build() {
/* 59 */       return new EnumTypeProtoConverter<>(
/* 60 */           Collections.unmodifiableMap(this.fromProtoEnumMap), 
/* 61 */           Collections.unmodifiableMap(this.toProtoEnumMap));
/*    */     }
/*    */   }
/*    */   
/*    */   public static <E extends Enum<E>, O> Builder<E, O> builder() {
/* 66 */     return new Builder<>();
/*    */   }
/*    */ 
/*    */   
/*    */   public E toProtoEnum(O objectEnum) throws GeneralSecurityException {
/* 71 */     Enum enum_ = (Enum)this.toProtoEnumMap.get(objectEnum);
/* 72 */     if (enum_ == null) {
/* 73 */       throw new GeneralSecurityException("Unable to convert object enum: " + objectEnum);
/*    */     }
/* 75 */     return (E)enum_;
/*    */   }
/*    */ 
/*    */   
/*    */   public O fromProtoEnum(E protoEnum) throws GeneralSecurityException {
/* 80 */     O objectEnum = this.fromProtoEnumMap.get(protoEnum);
/* 81 */     if (objectEnum == null) {
/* 82 */       throw new GeneralSecurityException("Unable to convert proto enum: " + protoEnum);
/*    */     }
/* 84 */     return objectEnum;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\EnumTypeProtoConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */