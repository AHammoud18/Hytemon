/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.objectivesetup;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ObjectiveTypeSetup
/*    */ {
/*    */   @Nonnull
/* 23 */   public static final CodecMapCodec<ObjectiveTypeSetup> CODEC = new CodecMapCodec("Type");
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public abstract String getObjectiveIdToStart();
/*    */   
/*    */   @Nullable
/*    */   public abstract Objective setup(@Nonnull Set<UUID> paramSet, @Nonnull UUID paramUUID1, @Nullable UUID paramUUID2, @Nonnull Store<EntityStore> paramStore);
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 34 */     return "ObjectiveTypeSetup{}";
/*    */   }
/*    */   
/*    */   static {
/* 38 */     CODEC.register("Objective", SetupObjective.class, (Codec)SetupObjective.CODEC);
/* 39 */     CODEC.register("ObjectiveLine", SetupObjectiveLine.class, (Codec)SetupObjectiveLine.CODEC);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\objectivesetup\ObjectiveTypeSetup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */