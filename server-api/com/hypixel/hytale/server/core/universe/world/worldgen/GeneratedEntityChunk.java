/*     */ package com.hypixel.hytale.server.core.universe.world.worldgen;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.FromWorldGen;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.EntityChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GeneratedEntityChunk
/*     */ {
/*     */   private final List<EntityWrapperEntry> entities;
/*     */   
/*     */   public GeneratedEntityChunk() {
/*  27 */     this((List<EntityWrapperEntry>)new ObjectArrayList());
/*     */   }
/*     */   
/*     */   protected GeneratedEntityChunk(List<EntityWrapperEntry> entities) {
/*  31 */     this.entities = entities;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<EntityWrapperEntry> getEntities() {
/*  38 */     return this.entities;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEachEntity(Consumer<EntityWrapperEntry> consumer) {
/*  47 */     this.entities.forEach(consumer);
/*     */   }
/*     */   
/*     */   public void addEntities(Vector3i offset, PrefabRotation rotation, @Nullable Holder<EntityStore>[] entityHolders, int objectId) {
/*  51 */     if (entityHolders != null && entityHolders.length > 0) {
/*  52 */       this.entities.add(new EntityWrapperEntry(offset, rotation, entityHolders, objectId));
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public EntityChunk toEntityChunk() {
/*  58 */     EntityChunk entityChunk = new EntityChunk();
/*     */     
/*  60 */     for (EntityWrapperEntry entry : this.entities) {
/*  61 */       FromWorldGen fromWorldGen = new FromWorldGen(entry.worldgenId());
/*     */ 
/*     */       
/*  64 */       for (Holder<EntityStore> entityHolder : entry.entityHolders()) {
/*  65 */         TransformComponent transformComponent = (TransformComponent)entityHolder.getComponent(TransformComponent.getComponentType());
/*  66 */         assert transformComponent != null;
/*     */ 
/*     */         
/*  69 */         entry.rotation().rotate(transformComponent.getPosition().subtract(0.5D, 0.0D, 0.5D));
/*  70 */         transformComponent.getPosition().add(0.5D, 0.0D, 0.5D);
/*     */         
/*  72 */         HeadRotation headRotationComponent = (HeadRotation)entityHolder.getComponent(HeadRotation.getComponentType());
/*  73 */         if (headRotationComponent != null) {
/*  74 */           headRotationComponent.getRotation().addYaw(-entry.rotation().getYaw());
/*     */         }
/*  76 */         transformComponent.getRotation().addYaw(-entry.rotation().getYaw());
/*     */ 
/*     */         
/*  79 */         transformComponent.getPosition().add(entry.offset());
/*     */         
/*  81 */         entityHolder.putComponent(FromWorldGen.getComponentType(), (Component)fromWorldGen);
/*     */         
/*  83 */         entityChunk.storeEntityHolder(entityHolder);
/*     */       } 
/*     */     } 
/*  86 */     return entityChunk;
/*     */   }
/*     */   public static final class EntityWrapperEntry extends Record { private final Vector3i offset; private final PrefabRotation rotation;
/*     */     private final Holder<EntityStore>[] entityHolders;
/*     */     private final int worldgenId;
/*     */     
/*  92 */     public EntityWrapperEntry(Vector3i offset, PrefabRotation rotation, Holder<EntityStore>[] entityHolders, int worldgenId) { this.offset = offset; this.rotation = rotation; this.entityHolders = entityHolders; this.worldgenId = worldgenId; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/universe/world/worldgen/GeneratedEntityChunk$EntityWrapperEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #92	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  92 */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/universe/world/worldgen/GeneratedEntityChunk$EntityWrapperEntry; } public Vector3i offset() { return this.offset; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/universe/world/worldgen/GeneratedEntityChunk$EntityWrapperEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #92	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/universe/world/worldgen/GeneratedEntityChunk$EntityWrapperEntry;
/*  92 */       //   0	8	1	o	Ljava/lang/Object; } public PrefabRotation rotation() { return this.rotation; } public Holder<EntityStore>[] entityHolders() { return this.entityHolders; } public int worldgenId() { return this.worldgenId; }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/*  97 */       return "EntityWrapperEntry{offset=" + String.valueOf(this.offset) + ", rotation=" + String.valueOf(this.rotation) + ", entityHolders=" + 
/*     */ 
/*     */         
/* 100 */         Arrays.toString((Object[])this.entityHolders) + "}";
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\GeneratedEntityChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */