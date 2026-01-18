/*    */ package com.hypixel.hytale.builtin.adventure.npcobjectives.task;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.assets.KillObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.resources.KillTrackerResource;
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.transaction.KillTaskTransaction;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.transaction.TransactionRecord;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class KillNPCObjectiveTask
/*    */   extends KillObjectiveTask
/*    */ {
/* 20 */   public static final BuilderCodec<KillNPCObjectiveTask> CODEC = BuilderCodec.builder(KillNPCObjectiveTask.class, KillNPCObjectiveTask::new, KillObjectiveTask.CODEC)
/* 21 */     .build();
/*    */   
/*    */   public KillNPCObjectiveTask(@Nonnull KillObjectiveTaskAsset asset, int taskSetIndex, int taskIndex) {
/* 24 */     super(asset, taskSetIndex, taskIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   protected KillNPCObjectiveTask() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected TransactionRecord[] setup0(@Nonnull Objective objective, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 33 */     KillTaskTransaction transaction = new KillTaskTransaction(this, objective, (ComponentAccessor)store);
/* 34 */     ((KillTrackerResource)store.getResource(KillTrackerResource.getResourceType())).watch(transaction);
/* 35 */     return new TransactionRecord[] { (TransactionRecord)transaction };
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 41 */     return "KillNPCObjectiveTask{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\task\KillNPCObjectiveTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */