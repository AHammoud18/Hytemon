/*    */ package com.hypixel.hytale.server.core.modules.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class AllLegacyEntityTypesQuery
/*    */   implements Query<EntityStore>
/*    */ {
/* 18 */   public static final AllLegacyEntityTypesQuery INSTANCE = new AllLegacyEntityTypesQuery();
/*    */ 
/*    */   
/*    */   public boolean test(@Nonnull Archetype<EntityStore> archetype) {
/* 22 */     return EntityUtils.hasEntity(archetype);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean requiresComponentType(ComponentType<EntityStore, ?> componentType) {
/* 27 */     return false;
/*    */   }
/*    */   
/*    */   public void validateRegistry(ComponentRegistry<EntityStore> registry) {}
/*    */   
/*    */   public void validate() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\AllLegacyEntityTypesQuery.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */