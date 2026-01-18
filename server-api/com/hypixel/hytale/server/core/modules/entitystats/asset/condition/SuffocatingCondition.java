/*    */ package com.hypixel.hytale.server.core.modules.entitystats.asset.condition;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.protocol.BlockMaterial;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*    */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*    */ import com.hypixel.hytale.server.core.modules.collision.WorldUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*    */ import java.time.Instant;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SuffocatingCondition
/*    */   extends Condition
/*    */ {
/*    */   @Nonnull
/* 27 */   public static final BuilderCodec<SuffocatingCondition> CODEC = BuilderCodec.builder(SuffocatingCondition.class, SuffocatingCondition::new, Condition.BASE_CODEC)
/* 28 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected SuffocatingCondition() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SuffocatingCondition(boolean inverse) {
/* 44 */     super(inverse);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean eval0(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> ref, @Nonnull Instant currentTime) {
/*    */     LivingEntity livingEntity;
/* 52 */     Entity entity = EntityUtils.getEntity(ref, componentAccessor);
/* 53 */     if (entity instanceof LivingEntity) { livingEntity = (LivingEntity)entity; }
/* 54 */     else { return false; }
/*    */ 
/*    */     
/* 57 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*    */     
/* 59 */     Transform lookVec = TargetUtil.getLook(ref, componentAccessor);
/* 60 */     Vector3d position = lookVec.getPosition();
/*    */     
/* 62 */     ChunkStore chunkStore = world.getChunkStore();
/* 63 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(position.x, position.z);
/* 64 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/*    */ 
/*    */     
/* 67 */     if (chunkRef == null || !chunkRef.isValid()) {
/* 68 */       return false;
/*    */     }
/*    */     
/* 71 */     long packed = WorldUtil.getPackedMaterialAndFluidAtPosition(chunkRef, (ComponentAccessor)chunkStore.getStore(), position.x, position.y, position.z);
/* 72 */     BlockMaterial material = BlockMaterial.VALUES[MathUtil.unpackLeft(packed)];
/* 73 */     int fluidId = MathUtil.unpackRight(packed);
/*    */     
/* 75 */     return !livingEntity.canBreathe(ref, material, fluidId, componentAccessor);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 81 */     return "SuffocatingCondition{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\asset\condition\SuffocatingCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */