/*    */ package com.hypixel.hytale.component.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public interface QuerySystem<ECS_TYPE>
/*    */   extends ISystem<ECS_TYPE> {
/*    */   default boolean test(ComponentRegistry<ECS_TYPE> componentRegistry, Archetype<ECS_TYPE> archetype) {
/* 11 */     return getQuery().test(archetype);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   Query<ECS_TYPE> getQuery();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\QuerySystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */