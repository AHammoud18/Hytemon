/*    */ package com.hypixel.hytale.server.core.universe.world.worldlocationcondition;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WorldLocationCondition
/*    */ {
/* 14 */   public static final CodecMapCodec<WorldLocationCondition> CODEC = new CodecMapCodec("Type");
/* 15 */   public static final BuilderCodec<WorldLocationCondition> BASE_CODEC = BuilderCodec.abstractBuilder(WorldLocationCondition.class)
/* 16 */     .build();
/*    */ 
/*    */   
/*    */   public abstract boolean test(World paramWorld, int paramInt1, int paramInt2, int paramInt3);
/*    */ 
/*    */   
/*    */   public abstract boolean equals(Object paramObject);
/*    */ 
/*    */   
/*    */   public abstract int hashCode();
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 29 */     return "WorldLocationCondition{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldlocationcondition\WorldLocationCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */