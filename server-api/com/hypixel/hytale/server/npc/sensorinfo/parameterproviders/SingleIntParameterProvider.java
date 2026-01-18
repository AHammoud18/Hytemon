/*    */ package com.hypixel.hytale.server.npc.sensorinfo.parameterproviders;
/*    */ 
/*    */ 
/*    */ public class SingleIntParameterProvider
/*    */   extends SingleParameterProvider
/*    */   implements IntParameterProvider
/*    */ {
/*    */   private int value;
/*    */   
/*    */   public SingleIntParameterProvider(int parameter) {
/* 11 */     super(parameter);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIntParameter() {
/* 16 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 21 */     this.value = Integer.MIN_VALUE;
/*    */   }
/*    */   
/*    */   public void overrideInt(int value) {
/* 25 */     this.value = value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\sensorinfo\parameterproviders\SingleIntParameterProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */