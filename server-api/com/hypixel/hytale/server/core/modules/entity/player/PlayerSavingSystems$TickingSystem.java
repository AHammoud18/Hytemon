/*     */ package com.hypixel.hytale.server.core.modules.entity.player;
/*     */ 
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.ArchetypeTickingSystem;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.component.system.tick.RunWhenPausedSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerConfigData;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TickingSystem
/*     */   extends EntityTickingSystem<EntityStore>
/*     */   implements RunWhenPausedSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/* 106 */   private final ResourceType<EntityStore, PlayerSavingSystems.SaveDataResource> dataResourceType = registerResource(PlayerSavingSystems.SaveDataResource.class, SaveDataResource::new);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Player> playerComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, HeadRotation> headRotationComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TickingSystem(@Nonnull ComponentType<EntityStore, Player> playerComponentType) {
/* 138 */     this.playerComponentType = playerComponentType;
/* 139 */     this.transformComponentType = TransformComponent.getComponentType();
/* 140 */     this.headRotationComponentType = HeadRotation.getComponentType();
/* 141 */     this.query = (Query<EntityStore>)Archetype.of(new ComponentType[] { playerComponentType, this.transformComponentType, this.headRotationComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 147 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 152 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 157 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 158 */     if (!world.getWorldConfig().isSavingPlayers())
/*     */       return; 
/* 160 */     PlayerSavingSystems.SaveDataResource data = (PlayerSavingSystems.SaveDataResource)store.getResource(this.dataResourceType);
/*     */     
/* 162 */     data.delay -= dt;
/* 163 */     if (data.delay <= 0.0F) {
/* 164 */       data.delay = 10.0F;
/*     */ 
/*     */       
/* 167 */       store.tick((ArchetypeTickingSystem)this, dt, systemIndex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 173 */     Player playerComponent = (Player)archetypeChunk.getComponent(index, this.playerComponentType);
/* 174 */     assert playerComponent != null;
/*     */     
/* 176 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/* 177 */     assert transformComponent != null;
/*     */     
/* 179 */     HeadRotation headRotationComponent = (HeadRotation)archetypeChunk.getComponent(index, this.headRotationComponentType);
/* 180 */     assert headRotationComponent != null;
/*     */     
/* 182 */     PlayerConfigData data = playerComponent.getPlayerConfigData();
/* 183 */     Vector3d position = transformComponent.getPosition();
/* 184 */     Vector3f rotation = headRotationComponent.getRotation();
/* 185 */     Vector3d lastSavedPosition = data.lastSavedPosition;
/* 186 */     Vector3f lastSavedRotation = data.lastSavedRotation;
/*     */     
/* 188 */     if (!lastSavedPosition.equals(position) || !lastSavedRotation.equals(rotation) || data.consumeHasChanged() || playerComponent.getInventory().consumeNeedsSaving()) {
/* 189 */       lastSavedPosition.assign(position);
/* 190 */       lastSavedRotation.assign(rotation);
/*     */       
/* 192 */       playerComponent.saveConfig(((EntityStore)store.getExternalData()).getWorld(), EntityUtils.toHolder(index, archetypeChunk));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerSavingSystems$TickingSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */