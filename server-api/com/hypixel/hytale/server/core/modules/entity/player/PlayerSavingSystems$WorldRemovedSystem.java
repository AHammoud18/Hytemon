/*    */ package com.hypixel.hytale.server.core.modules.entity.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.StoreSystem;
/*    */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.logging.Level;
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
/*    */ public class WorldRemovedSystem
/*    */   extends StoreSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   private final ComponentType<EntityStore, Player> playerComponentType;
/*    */   @Nonnull
/*    */   private final ComponentType<EntityStore, PlayerRef> refComponentType;
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */   
/*    */   public WorldRemovedSystem(@Nonnull ComponentType<EntityStore, Player> playerComponentType) {
/* 64 */     this.playerComponentType = playerComponentType;
/* 65 */     this.refComponentType = PlayerRef.getComponentType();
/* 66 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)playerComponentType, (Query)this.refComponentType });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onSystemAddedToStore(@Nonnull Store<EntityStore> store) {}
/*    */ 
/*    */   
/*    */   public void onSystemRemovedFromStore(@Nonnull Store<EntityStore> store) {
/* 75 */     if (((EntityStore)store.getExternalData()).getWorld().getWorldConfig().isSavingPlayers()) {
/* 76 */       PlayerSavingSystems.LOGGER.at(Level.INFO).log("Saving Players...");
/*    */     } else {
/* 78 */       PlayerSavingSystems.LOGGER.at(Level.INFO).log("Disconnecting Players...");
/*    */     } 
/*    */     
/* 81 */     store.forEachEntityParallel(this.query, (index, archetypeChunk, commandBuffer) -> {
/*    */           Player playerComponent = (Player)archetypeChunk.getComponent(index, this.playerComponentType);
/*    */           assert playerComponent != null;
/*    */           PlayerRef playerRefComponent = (PlayerRef)archetypeChunk.getComponent(index, this.refComponentType);
/*    */           assert playerRefComponent != null;
/*    */           World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*    */           if (world.getWorldConfig().isSavingPlayers())
/*    */             playerComponent.saveConfig(world, EntityUtils.toHolder(index, archetypeChunk)); 
/*    */           playerRefComponent.getPacketHandler().disconnect("Stopping world!");
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerSavingSystems$WorldRemovedSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */