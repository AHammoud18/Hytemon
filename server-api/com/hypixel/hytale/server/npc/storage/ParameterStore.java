/*    */ package com.hypixel.hytale.server.npc.storage;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ParameterStore<Type extends PersistentParameter<?>>
/*    */ {
/* 14 */   protected Map<String, Type> parameters = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Type get(@Nonnull Entity owner, String name) {
/* 20 */     PersistentParameter persistentParameter = (PersistentParameter)this.parameters.get(name);
/* 21 */     if (persistentParameter == null) {
/* 22 */       persistentParameter = (PersistentParameter)createParameter();
/* 23 */       this.parameters.put(name, (Type)persistentParameter);
/* 24 */       owner.markNeedsSave();
/*    */     } 
/* 26 */     return (Type)persistentParameter;
/*    */   }
/*    */   
/*    */   protected abstract Type createParameter();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\storage\ParameterStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */