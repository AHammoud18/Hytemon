/*    */ package it.unimi.dsi.fastutil.shorts;
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
/*    */ public abstract class AbstractShort2IntFunction
/*    */   implements Short2IntFunction, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4940583368468432370L;
/*    */   protected int defRetValue;
/*    */   
/*    */   public void defaultReturnValue(int rv) {
/* 44 */     this.defRetValue = rv;
/*    */   }
/*    */ 
/*    */   
/*    */   public int defaultReturnValue() {
/* 49 */     return this.defRetValue;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2IntFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */