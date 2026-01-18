/*    */ package com.google.protobuf;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
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
/*    */ @CheckReturnValue
/*    */ final class ListFieldSchemaFull
/*    */   implements ListFieldSchema
/*    */ {
/* 22 */   private static final Class<?> UNMODIFIABLE_LIST_CLASS = Collections.unmodifiableList(Collections.emptyList()).getClass();
/*    */ 
/*    */   
/*    */   public <L> List<L> mutableListAt(Object message, long offset) {
/* 26 */     return mutableListAt(message, offset, 10);
/*    */   }
/*    */ 
/*    */   
/*    */   private static <L> List<L> mutableListAt(Object message, long offset, int additionalCapacity) {
/* 31 */     List<L> list = getList(message, offset);
/* 32 */     if (list.isEmpty()) {
/* 33 */       if (list instanceof LazyStringList) {
/* 34 */         list = new LazyStringArrayList(additionalCapacity);
/* 35 */       } else if (list instanceof PrimitiveNonBoxingCollection && list instanceof Internal.ProtobufList) {
/* 36 */         list = ((Internal.ProtobufList<L>)list).mutableCopyWithCapacity(additionalCapacity);
/*    */       } else {
/* 38 */         list = new ArrayList<>(additionalCapacity);
/*    */       } 
/* 40 */       UnsafeUtil.putObject(message, offset, list);
/* 41 */     } else if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass())) {
/* 42 */       ArrayList<L> newList = new ArrayList<>(list.size() + additionalCapacity);
/* 43 */       newList.addAll(list);
/* 44 */       list = newList;
/* 45 */       UnsafeUtil.putObject(message, offset, list);
/* 46 */     } else if (list instanceof UnmodifiableLazyStringList) {
/* 47 */       LazyStringArrayList newList = new LazyStringArrayList(list.size() + additionalCapacity);
/* 48 */       newList.addAll((UnmodifiableLazyStringList)list);
/* 49 */       list = newList;
/* 50 */       UnsafeUtil.putObject(message, offset, list);
/* 51 */     } else if (list instanceof PrimitiveNonBoxingCollection && list instanceof Internal.ProtobufList && 
/*    */       
/* 53 */       !((Internal.ProtobufList)list).isModifiable()) {
/* 54 */       list = ((Internal.ProtobufList<L>)list).mutableCopyWithCapacity(list.size() + additionalCapacity);
/* 55 */       UnsafeUtil.putObject(message, offset, list);
/*    */     } 
/* 57 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public void makeImmutableListAt(Object message, long offset) {
/* 62 */     List<?> list = (List)UnsafeUtil.getObject(message, offset);
/* 63 */     Object<?> immutable = null;
/* 64 */     if (list instanceof LazyStringList)
/* 65 */     { immutable = (Object<?>)((LazyStringList)list).getUnmodifiableView(); }
/* 66 */     else { if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass())) {
/*    */         return;
/*    */       }
/* 69 */       if (list instanceof PrimitiveNonBoxingCollection && list instanceof Internal.ProtobufList) {
/* 70 */         if (((Internal.ProtobufList)list).isModifiable()) {
/* 71 */           ((Internal.ProtobufList)list).makeImmutable();
/*    */         }
/*    */         return;
/*    */       } 
/* 75 */       immutable = Collections.unmodifiableList(list); }
/*    */     
/* 77 */     UnsafeUtil.putObject(message, offset, immutable);
/*    */   }
/*    */ 
/*    */   
/*    */   public <E> void mergeListsAt(Object msg, Object otherMsg, long offset) {
/* 82 */     List<E> other = getList(otherMsg, offset);
/* 83 */     List<E> mine = mutableListAt(msg, offset, other.size());
/*    */     
/* 85 */     int size = mine.size();
/* 86 */     int otherSize = other.size();
/* 87 */     if (size > 0 && otherSize > 0) {
/* 88 */       mine.addAll(other);
/*    */     }
/*    */     
/* 91 */     List<E> merged = (size > 0) ? mine : other;
/* 92 */     UnsafeUtil.putObject(msg, offset, merged);
/*    */   }
/*    */ 
/*    */   
/*    */   static <E> List<E> getList(Object message, long offset) {
/* 97 */     return (List<E>)UnsafeUtil.getObject(message, offset);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ListFieldSchemaFull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */