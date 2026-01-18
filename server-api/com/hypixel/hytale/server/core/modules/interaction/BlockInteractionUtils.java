/*    */ package com.hypixel.hytale.server.core.modules.interaction;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class BlockInteractionUtils
/*    */ {
/*    */   public static boolean isNaturalAction(@Nullable Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 31 */     if (ref == null) return true;
/*    */ 
/*    */     
/* 34 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 35 */     if (playerComponent != null) {
/* 36 */       return (playerComponent.getGameMode() == GameMode.Adventure);
/*    */     }
/*    */ 
/*    */     
/* 40 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\BlockInteractionUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */