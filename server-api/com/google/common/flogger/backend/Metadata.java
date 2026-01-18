/*    */ package com.google.common.flogger.backend;
/*    */ 
/*    */ import com.google.common.flogger.MetadataKey;
/*    */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
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
/*    */ public abstract class Metadata
/*    */ {
/*    */   public abstract int size();
/*    */   
/*    */   public abstract MetadataKey<?> getKey(int paramInt);
/*    */   
/*    */   public abstract Object getValue(int paramInt);
/*    */   
/*    */   public static Metadata empty() {
/* 43 */     return Empty.INSTANCE;
/*    */   }
/*    */   
/*    */   @NullableDecl
/*    */   public abstract <T> T findValue(MetadataKey<T> paramMetadataKey);
/*    */   
/*    */   private static final class Empty
/*    */     extends Metadata {
/* 51 */     static final Empty INSTANCE = new Empty();
/*    */ 
/*    */     
/*    */     public int size() {
/* 55 */       return 0;
/*    */     }
/*    */ 
/*    */     
/*    */     public MetadataKey<?> getKey(int n) {
/* 60 */       throw new IndexOutOfBoundsException("cannot read from empty metadata");
/*    */     }
/*    */ 
/*    */     
/*    */     public Object getValue(int n) {
/* 65 */       throw new IndexOutOfBoundsException("cannot read from empty metadata");
/*    */     }
/*    */ 
/*    */     
/*    */     @NullableDecl
/*    */     public <T> T findValue(MetadataKey<T> key) {
/* 71 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\Metadata.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */