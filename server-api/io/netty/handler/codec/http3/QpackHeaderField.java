/*    */ package io.netty.handler.codec.http3;
/*    */ 
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
/*    */ class QpackHeaderField
/*    */ {
/*    */   static final int ENTRY_OVERHEAD = 32;
/*    */   final CharSequence name;
/*    */   final CharSequence value;
/*    */   
/*    */   static long sizeOf(CharSequence name, CharSequence value) {
/* 31 */     return (name.length() + value.length() + 32);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   QpackHeaderField(CharSequence name, CharSequence value) {
/* 39 */     this.name = (CharSequence)ObjectUtil.checkNotNull(name, "name");
/* 40 */     this.value = (CharSequence)ObjectUtil.checkNotNull(value, "value");
/*    */   }
/*    */   
/*    */   long size() {
/* 44 */     return sizeOf(this.name, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     return this.name + ": " + this.value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\QpackHeaderField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */