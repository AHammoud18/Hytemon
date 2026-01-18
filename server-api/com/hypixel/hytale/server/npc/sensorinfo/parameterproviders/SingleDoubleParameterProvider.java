/*    */ package com.hypixel.hytale.server.npc.sensorinfo.parameterproviders;
/*    */ 
/*    */ 
/*    */ public class SingleDoubleParameterProvider
/*    */   extends SingleParameterProvider
/*    */   implements DoubleParameterProvider
/*    */ {
/*    */   private double value;
/*    */   
/*    */   public SingleDoubleParameterProvider(int parameter) {
/* 11 */     super(parameter);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDoubleParameter() {
/* 16 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 21 */     this.value = -1.7976931348623157E308D;
/*    */   }
/*    */   
/*    */   public void overrideDouble(double value) {
/* 25 */     this.value = value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\sensorinfo\parameterproviders\SingleDoubleParameterProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */