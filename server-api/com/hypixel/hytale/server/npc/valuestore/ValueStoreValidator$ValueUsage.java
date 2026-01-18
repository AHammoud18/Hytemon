/*    */ package com.hypixel.hytale.server.npc.valuestore;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderContext;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValueUsage
/*    */ {
/*    */   protected final String name;
/*    */   protected final ValueStore.Type valueType;
/*    */   protected final ValueStoreValidator.UseType useType;
/*    */   protected final BuilderContext context;
/*    */   
/*    */   public ValueUsage(String name, ValueStore.Type valueType, ValueStoreValidator.UseType useType, BuilderContext context) {
/* 85 */     this.name = name;
/* 86 */     this.valueType = valueType;
/* 87 */     this.useType = useType;
/* 88 */     this.context = context;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\valuestore\ValueStoreValidator$ValueUsage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */