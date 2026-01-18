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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class AllLegacyLivingEntityTypesQuery
/*    */   implements Query<EntityStore>
/*    */ {
/*    */   @Nonnull
/* 25 */   public static final AllLegacyLivingEntityTypesQuery INSTANCE = new AllLegacyLivingEntityTypesQuery();
/*    */ 
/*    */   
/*    */   public boolean test(@Nonnull Archetype<EntityStore> archetype) {
/* 29 */     return EntityUtils.hasLivingEntity(archetype);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean requiresComponentType(ComponentType<EntityStore, ?> componentType) {
/* 34 */     return false;
/*    */   }
/*    */   
/*    */   public void validateRegistry(ComponentRegistry<EntityStore> registry) {}
/*    */   
/*    */   public void validate() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\AllLegacyLivingEntityTypesQuery.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */