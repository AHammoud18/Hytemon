/*    */ package com.hypixel.hytale.server.npc.sensorinfo.parameterproviders;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class SingleStringParameterProvider
/*    */   extends SingleParameterProvider
/*    */   implements StringParameterProvider
/*    */ {
/*    */   @Nullable
/*    */   private String value;
/*    */   
/*    */   public SingleStringParameterProvider(int parameter) {
/* 14 */     super(parameter);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getStringParameter() {
/* 20 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 25 */     this.value = null;
/*    */   }
/*    */   
/*    */   public void overrideString(String value) {
/* 29 */     this.value = value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\sensorinfo\parameterproviders\SingleStringParameterProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */