/*    */ package com.hypixel.hytale.builtin.adventure.farming.config.stages.spread;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldlocationcondition.WorldLocationCondition;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SpreadGrowthBehaviour
/*    */ {
/* 18 */   public static final CodecMapCodec<SpreadGrowthBehaviour> CODEC = new CodecMapCodec("Type");
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<SpreadGrowthBehaviour> BASE_CODEC;
/*    */   
/*    */   protected WorldLocationCondition[] worldLocationConditions;
/*    */ 
/*    */   
/*    */   static {
/* 27 */     BASE_CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(SpreadGrowthBehaviour.class).append(new KeyedCodec("LocationConditions", (Codec)new ArrayCodec((Codec)WorldLocationCondition.CODEC, x$0 -> new WorldLocationCondition[x$0])), (spreadGrowthBehaviour, worldLocationConditions) -> spreadGrowthBehaviour.worldLocationConditions = worldLocationConditions, spreadGrowthBehaviour -> spreadGrowthBehaviour.worldLocationConditions).documentation("Defines the possible location conditions a position has to fulfill to be considered as valid.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean validatePosition(World world, int worldX, int worldY, int worldZ) {
/* 34 */     if (this.worldLocationConditions == null) return true;
/*    */     
/* 36 */     for (int i = 0; i < this.worldLocationConditions.length; i++) {
/* 37 */       if (!this.worldLocationConditions[i].test(world, worldX, worldY, worldZ)) return false;
/*    */     
/*    */     } 
/* 40 */     return true;
/*    */   }
/*    */   
/*    */   public abstract void execute(ComponentAccessor<ChunkStore> paramComponentAccessor, Ref<ChunkStore> paramRef1, Ref<ChunkStore> paramRef2, int paramInt1, int paramInt2, int paramInt3, float paramFloat);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\config\stages\spread\SpreadGrowthBehaviour.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */