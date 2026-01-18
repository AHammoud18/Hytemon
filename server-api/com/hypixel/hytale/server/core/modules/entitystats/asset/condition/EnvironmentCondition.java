/*     */ package com.hypixel.hytale.server.core.modules.entitystats.asset.condition;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.time.Instant;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnvironmentCondition
/*     */   extends Condition
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<EnvironmentCondition> CODEC;
/*     */   protected String[] unknownEnvironments;
/*     */   @Nullable
/*     */   protected int[] environments;
/*     */   
/*     */   static {
/*  38 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(EnvironmentCondition.class, EnvironmentCondition::new, Condition.BASE_CODEC).append(new KeyedCodec("Environments", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (condition, value) -> condition.unknownEnvironments = value, condition -> condition.unknownEnvironments).documentation("The environments to evaluate the condition against.").add()).afterDecode(condition -> condition.environments = null)).build();
/*     */   }
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
/*     */   public int[] getEnvironments() {
/*  64 */     if (this.environments == null && this.unknownEnvironments != null) {
/*  65 */       this
/*     */ 
/*     */         
/*  68 */         .environments = Arrays.<String>stream(this.unknownEnvironments).mapToInt(environment -> Environment.getAssetMap().getIndex(environment)).sorted().toArray();
/*     */     }
/*     */     
/*  71 */     return this.environments;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean eval0(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> ref, @Nonnull Instant currentTime) {
/*  76 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  77 */     assert transformComponent != null;
/*     */     
/*  79 */     Vector3d position = transformComponent.getPosition();
/*     */     
/*  81 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*  82 */     ChunkStore chunkStore = world.getChunkStore();
/*     */     
/*  84 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(position.getX(), position.getZ());
/*  85 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/*  86 */     if (chunkRef == null || !chunkRef.isValid()) {
/*  87 */       return false;
/*     */     }
/*     */     
/*  90 */     Store<ChunkStore> chunkComponentStore = chunkStore.getStore();
/*  91 */     BlockChunk blockChunkComponent = (BlockChunk)chunkComponentStore.getComponent(chunkRef, BlockChunk.getComponentType());
/*  92 */     assert blockChunkComponent != null;
/*     */     
/*  94 */     int environmentId = blockChunkComponent.getEnvironment(position);
/*  95 */     return (Arrays.binarySearch(getEnvironments(), environmentId) >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 102 */     return "EnvironmentCondition{unknownEnvironments=" + Arrays.toString((Object[])this.unknownEnvironments) + ", environments=" + 
/* 103 */       Arrays.toString(this.environments) + "} " + super
/* 104 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\asset\condition\EnvironmentCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */