/*     */ package com.hypixel.hytale.server.core.universe.world.worldgen;
/*     */ 
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Arrays;
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
/*     */ public final class EntityWrapperEntry
/*     */   extends Record
/*     */ {
/*     */   private final Vector3i offset;
/*     */   private final PrefabRotation rotation;
/*     */   private final Holder<EntityStore>[] entityHolders;
/*     */   private final int worldgenId;
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/universe/world/worldgen/GeneratedEntityChunk$EntityWrapperEntry;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #92	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/universe/world/worldgen/GeneratedEntityChunk$EntityWrapperEntry;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/universe/world/worldgen/GeneratedEntityChunk$EntityWrapperEntry;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #92	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/universe/world/worldgen/GeneratedEntityChunk$EntityWrapperEntry;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public EntityWrapperEntry(Vector3i offset, PrefabRotation rotation, Holder<EntityStore>[] entityHolders, int worldgenId) {
/*  92 */     this.offset = offset; this.rotation = rotation; this.entityHolders = entityHolders; this.worldgenId = worldgenId; } public Vector3i offset() { return this.offset; } public PrefabRotation rotation() { return this.rotation; } public Holder<EntityStore>[] entityHolders() { return this.entityHolders; } public int worldgenId() { return this.worldgenId; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  97 */     return "EntityWrapperEntry{offset=" + String.valueOf(this.offset) + ", rotation=" + String.valueOf(this.rotation) + ", entityHolders=" + 
/*     */ 
/*     */       
/* 100 */       Arrays.toString((Object[])this.entityHolders) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\GeneratedEntityChunk$EntityWrapperEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */