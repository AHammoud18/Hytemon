/*     */ package com.hypixel.hytale.server.spawning.spawnmarkers;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.data.unknown.UnknownComponents;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.RootDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.modules.entity.AllLegacyEntityTypesQuery;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HiddenFromAdventurePlayers;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.WorldGenId;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
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
/*     */ @Deprecated(forRemoval = true)
/*     */ public class LegacyEntityMigration
/*     */   extends EntityModule.MigrationSystem
/*     */ {
/*  54 */   private final ComponentType<EntityStore, PersistentModel> persistentModelComponentType = PersistentModel.getComponentType();
/*  55 */   private final ComponentType<EntityStore, Nameplate> nameplateComponentType = Nameplate.getComponentType();
/*  56 */   private final ComponentType<EntityStore, UUIDComponent> uuidComponentType = UUIDComponent.getComponentType();
/*  57 */   private final ComponentType<EntityStore, UnknownComponents<EntityStore>> unknownComponentsComponentType = EntityStore.REGISTRY.getUnknownComponentType();
/*  58 */   private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)this.unknownComponentsComponentType, (Query)Query.not((Query)AllLegacyEntityTypesQuery.INSTANCE) });
/*     */ 
/*     */   
/*     */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*  62 */     Map<String, BsonDocument> unknownComponents = ((UnknownComponents)holder.getComponent(this.unknownComponentsComponentType)).getUnknownComponents();
/*     */     
/*  64 */     BsonDocument spawnMarker = unknownComponents.remove("SpawnMarker");
/*  65 */     if (spawnMarker == null)
/*     */       return; 
/*  67 */     if (!holder.getArchetype().contains(this.persistentModelComponentType)) {
/*  68 */       Model.ModelReference modelReference = Entity.MODEL.get(spawnMarker).get();
/*  69 */       holder.addComponent(this.persistentModelComponentType, (Component)new PersistentModel(modelReference));
/*     */     } 
/*     */     
/*  72 */     if (!holder.getArchetype().contains(this.nameplateComponentType)) {
/*  73 */       holder.addComponent(this.nameplateComponentType, (Component)new Nameplate(Entity.DISPLAY_NAME.get(spawnMarker).get()));
/*     */     }
/*     */     
/*  76 */     if (!holder.getArchetype().contains(this.uuidComponentType)) {
/*  77 */       holder.addComponent(this.uuidComponentType, (Component)new UUIDComponent(Entity.UUID.get(spawnMarker).get()));
/*     */     }
/*     */     
/*  80 */     holder.ensureComponent(HiddenFromAdventurePlayers.getComponentType());
/*     */     
/*  82 */     int worldgenId = ((Integer)Codec.INTEGER.decode(spawnMarker.get("WorldgenId"))).intValue();
/*  83 */     if (worldgenId != 0) holder.addComponent(WorldGenId.getComponentType(), (Component)new WorldGenId(worldgenId));
/*     */     
/*  85 */     SpawnMarkerEntity marker = (SpawnMarkerEntity)SpawnMarkerEntity.CODEC.decode((BsonValue)spawnMarker, new ExtraInfo(5));
/*  86 */     holder.addComponent(SpawnMarkerEntity.getComponentType(), marker);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  96 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 102 */     return RootDependency.firstSet();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\spawnmarkers\SpawnMarkerSystems$LegacyEntityMigration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */