/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.taskcondition;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public abstract class TaskConditionAsset {
/* 12 */   public static final CodecMapCodec<TaskConditionAsset> CODEC = new CodecMapCodec("Type");
/*    */ 
/*    */   
/*    */   public abstract boolean isConditionFulfilled(ComponentAccessor<EntityStore> paramComponentAccessor, Ref<EntityStore> paramRef, Set<UUID> paramSet);
/*    */ 
/*    */   
/*    */   public abstract void consumeCondition(ComponentAccessor<EntityStore> paramComponentAccessor, Ref<EntityStore> paramRef, Set<UUID> paramSet);
/*    */ 
/*    */   
/*    */   public abstract boolean equals(Object paramObject);
/*    */ 
/*    */   
/*    */   public abstract int hashCode();
/*    */ 
/*    */   
/*    */   static {
/* 28 */     CODEC.register("SoloInventory", SoloInventoryCondition.class, (Codec)SoloInventoryCondition.CODEC);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\taskcondition\TaskConditionAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */