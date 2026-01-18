/*    */ package it.unimi.dsi.fastutil.bytes;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractByte2ByteFunction
/*    */   implements Byte2ByteFunction, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4940583368468432370L;
/*    */   protected byte defRetValue;
/*    */   
/*    */   public void defaultReturnValue(byte rv) {
/* 44 */     this.defRetValue = rv;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte defaultReturnValue() {
/* 49 */     return this.defRetValue;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2ByteFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */