/*    */ package it.unimi.dsi.fastutil.ints;
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
/*    */ public abstract class AbstractInt2FloatFunction
/*    */   implements Int2FloatFunction, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4940583368468432370L;
/*    */   protected float defRetValue;
/*    */   
/*    */   public void defaultReturnValue(float rv) {
/* 44 */     this.defRetValue = rv;
/*    */   }
/*    */ 
/*    */   
/*    */   public float defaultReturnValue() {
/* 49 */     return this.defRetValue;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2FloatFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */