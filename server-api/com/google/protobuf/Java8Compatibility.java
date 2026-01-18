/*    */ package com.google.protobuf;
/*    */ 
/*    */ import java.nio.Buffer;
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
/*    */ final class Java8Compatibility
/*    */ {
/*    */   static void clear(Buffer b) {
/* 20 */     b.clear();
/*    */   }
/*    */   
/*    */   static void flip(Buffer b) {
/* 24 */     b.flip();
/*    */   }
/*    */   
/*    */   static void limit(Buffer b, int limit) {
/* 28 */     b.limit(limit);
/*    */   }
/*    */   
/*    */   static void mark(Buffer b) {
/* 32 */     b.mark();
/*    */   }
/*    */   
/*    */   static void position(Buffer b, int position) {
/* 36 */     b.position(position);
/*    */   }
/*    */   
/*    */   static void reset(Buffer b) {
/* 40 */     b.reset();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Java8Compatibility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */