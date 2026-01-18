/*    */ package com.hypixel.hytale.builtin.adventure.memories.temple;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*    */ 
/*    */ public class TempleRespawnPlayersSystem extends DelayedEntitySystem<EntityStore> {
/* 18 */   public static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] { (Query)PlayerRef.getComponentType(), (Query)TransformComponent.getComponentType() });
/*    */   
/*    */   public TempleRespawnPlayersSystem() {
/* 21 */     super(1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
/* 26 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 27 */     GameplayConfig gameplayConfig = world.getGameplayConfig();
/* 28 */     ForgottenTempleConfig config = (ForgottenTempleConfig)gameplayConfig.getPluginConfig().get(ForgottenTempleConfig.class);
/* 29 */     if (config == null)
/*    */       return; 
/* 31 */     Vector3d position = ((TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType())).getPosition();
/* 32 */     if (position.getY() > config.getMinYRespawn())
/*    */       return; 
/* 34 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 35 */     ISpawnProvider spawnProvider = world.getWorldConfig().getSpawnProvider();
/* 36 */     Transform spawnPoint = spawnProvider.getSpawnPoint(ref, (ComponentAccessor)commandBuffer);
/* 37 */     commandBuffer.addComponent(ref, Teleport.getComponentType(), (Component)new Teleport(null, spawnPoint));
/*    */     
/* 39 */     PlayerRef playerRef = (PlayerRef)archetypeChunk.getComponent(index, PlayerRef.getComponentType());
/* 40 */     SoundUtil.playSoundEvent2dToPlayer(playerRef, config.getRespawnSoundIndex(), SoundCategory.SFX);
/*    */   }
/*    */ 
/*    */   
/*    */   @NullableDecl
/*    */   public Query<EntityStore> getQuery() {
/* 46 */     return QUERY;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\temple\TempleRespawnPlayersSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */