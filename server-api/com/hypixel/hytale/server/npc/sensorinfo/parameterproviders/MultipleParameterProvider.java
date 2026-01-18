/*    */ package com.hypixel.hytale.server.npc.sensorinfo.parameterproviders;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MultipleParameterProvider
/*    */   implements ParameterProvider
/*    */ {
/* 11 */   private final Int2ObjectMap<ParameterProvider> providers = (Int2ObjectMap<ParameterProvider>)new Int2ObjectOpenHashMap();
/*    */ 
/*    */   
/*    */   public ParameterProvider getParameterProvider(int parameter) {
/* 15 */     return (ParameterProvider)this.providers.get(parameter);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() {
/* 21 */     this.providers.values().forEach(ParameterProvider::clear);
/*    */   }
/*    */   
/*    */   public void addParameterProvider(int parameter, ParameterProvider provider) {
/* 25 */     this.providers.put(parameter, provider);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\sensorinfo\parameterproviders\MultipleParameterProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */