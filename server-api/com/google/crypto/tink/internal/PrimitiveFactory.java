/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import com.google.protobuf.MessageLite;
/*    */ import java.security.GeneralSecurityException;
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
/*    */ public abstract class PrimitiveFactory<PrimitiveT, KeyProtoT extends MessageLite>
/*    */ {
/*    */   private final Class<PrimitiveT> clazz;
/*    */   
/*    */   public PrimitiveFactory(Class<PrimitiveT> clazz) {
/* 27 */     this.clazz = clazz;
/*    */   }
/*    */ 
/*    */   
/*    */   final Class<PrimitiveT> getPrimitiveClass() {
/* 32 */     return this.clazz;
/*    */   }
/*    */   
/*    */   public abstract PrimitiveT getPrimitive(KeyProtoT paramKeyProtoT) throws GeneralSecurityException;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\PrimitiveFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */