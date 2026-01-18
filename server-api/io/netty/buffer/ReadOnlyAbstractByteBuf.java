/*    */ package io.netty.buffer;
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
/*    */ final class ReadOnlyAbstractByteBuf
/*    */   extends ReadOnlyByteBuf
/*    */ {
/*    */   ReadOnlyAbstractByteBuf(AbstractByteBuf buffer) {
/* 25 */     super(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractByteBuf unwrap() {
/* 30 */     return (AbstractByteBuf)super.unwrap();
/*    */   }
/*    */ 
/*    */   
/*    */   protected byte _getByte(int index) {
/* 35 */     return unwrap()._getByte(index);
/*    */   }
/*    */ 
/*    */   
/*    */   protected short _getShort(int index) {
/* 40 */     return unwrap()._getShort(index);
/*    */   }
/*    */ 
/*    */   
/*    */   protected short _getShortLE(int index) {
/* 45 */     return unwrap()._getShortLE(index);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int _getUnsignedMedium(int index) {
/* 50 */     return unwrap()._getUnsignedMedium(index);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int _getUnsignedMediumLE(int index) {
/* 55 */     return unwrap()._getUnsignedMediumLE(index);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int _getInt(int index) {
/* 60 */     return unwrap()._getInt(index);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int _getIntLE(int index) {
/* 65 */     return unwrap()._getIntLE(index);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long _getLong(int index) {
/* 70 */     return unwrap()._getLong(index);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long _getLongLE(int index) {
/* 75 */     return unwrap()._getLongLE(index);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\ReadOnlyAbstractByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */