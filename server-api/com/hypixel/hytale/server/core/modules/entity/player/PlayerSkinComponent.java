/*    */ package com.hypixel.hytale.server.core.modules.entity.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.protocol.PlayerSkin;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerSkinComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   private final PlayerSkin playerSkin;
/*    */   
/*    */   @Nonnull
/*    */   public static ComponentType<EntityStore, PlayerSkinComponent> getComponentType() {
/* 21 */     return EntityModule.get().getPlayerSkinComponentType();
/*    */   }
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
/*    */   private boolean isNetworkOutdated = true;
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
/*    */   public PlayerSkinComponent(@Nonnull PlayerSkin playerSkin) {
/* 46 */     this.playerSkin = playerSkin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean consumeNetworkOutdated() {
/* 55 */     boolean temp = this.isNetworkOutdated;
/* 56 */     this.isNetworkOutdated = false;
/* 57 */     return temp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PlayerSkin getPlayerSkin() {
/* 65 */     return this.playerSkin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setNetworkOutdated() {
/* 72 */     this.isNetworkOutdated = true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 78 */     return new PlayerSkinComponent(this.playerSkin);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerSkinComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */