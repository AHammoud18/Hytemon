/*    */ package com.google.protobuf;
/*    */ 
/*    */ import java.util.List;
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
/*    */ final class ListFieldSchemaLite
/*    */   implements ListFieldSchema
/*    */ {
/*    */   public <L> List<L> mutableListAt(Object message, long offset) {
/* 20 */     Internal.ProtobufList<L> list = getProtobufList(message, offset);
/* 21 */     if (!list.isModifiable()) {
/* 22 */       int size = list.size();
/*    */       
/* 24 */       list = list.mutableCopyWithCapacity(
/* 25 */           (size == 0) ? 10 : (size * 2));
/* 26 */       UnsafeUtil.putObject(message, offset, list);
/*    */     } 
/* 28 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public void makeImmutableListAt(Object message, long offset) {
/* 33 */     Internal.ProtobufList<?> list = getProtobufList(message, offset);
/* 34 */     list.makeImmutable();
/*    */   }
/*    */ 
/*    */   
/*    */   public <E> void mergeListsAt(Object msg, Object otherMsg, long offset) {
/* 39 */     Internal.ProtobufList<E> mine = getProtobufList(msg, offset);
/* 40 */     Internal.ProtobufList<E> other = getProtobufList(otherMsg, offset);
/*    */     
/* 42 */     int size = mine.size();
/* 43 */     int otherSize = other.size();
/* 44 */     if (size > 0 && otherSize > 0) {
/* 45 */       if (!mine.isModifiable()) {
/* 46 */         mine = mine.mutableCopyWithCapacity(size + otherSize);
/*    */       }
/* 48 */       mine.addAll(other);
/*    */     } 
/*    */     
/* 51 */     Internal.ProtobufList<E> merged = (size > 0) ? mine : other;
/* 52 */     UnsafeUtil.putObject(msg, offset, merged);
/*    */   }
/*    */ 
/*    */   
/*    */   static <E> Internal.ProtobufList<E> getProtobufList(Object message, long offset) {
/* 57 */     return (Internal.ProtobufList<E>)UnsafeUtil.getObject(message, offset);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ListFieldSchemaLite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */