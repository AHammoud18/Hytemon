/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.worldlocationproviders;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public abstract class WorldLocationProvider {
/* 12 */   public static final CodecMapCodec<WorldLocationProvider> CODEC = new CodecMapCodec("Type");
/* 13 */   public static final BuilderCodec<WorldLocationProvider> BASE_CODEC = BuilderCodec.abstractBuilder(WorldLocationProvider.class)
/* 14 */     .build();
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public abstract Vector3i runCondition(World paramWorld, Vector3i paramVector3i);
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract boolean equals(Object paramObject);
/*    */ 
/*    */   
/*    */   public abstract int hashCode();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 31 */     return "WorldLocationProvider{}";
/*    */   }
/*    */   
/*    */   static {
/* 35 */     CODEC.register("LookBlocksBelow", LookBlocksBelowProvider.class, (Codec)LookBlocksBelowProvider.CODEC);
/* 36 */     CODEC.register("LocationRadius", LocationRadiusProvider.class, (Codec)LocationRadiusProvider.CODEC);
/* 37 */     CODEC.register("TagBlockHeight", CheckTagWorldHeightRadiusProvider.class, (Codec)CheckTagWorldHeightRadiusProvider.CODEC);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\worldlocationproviders\WorldLocationProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */