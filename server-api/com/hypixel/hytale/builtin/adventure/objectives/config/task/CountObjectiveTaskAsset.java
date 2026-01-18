/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.task;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.taskcondition.TaskConditionAsset;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class CountObjectiveTaskAsset
/*    */   extends ObjectiveTaskAsset
/*    */ {
/*    */   public static final BuilderCodec<CountObjectiveTaskAsset> CODEC;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(CountObjectiveTaskAsset.class, BASE_CODEC).append(new KeyedCodec("Count", (Codec)Codec.INTEGER), (taskAsset, count) -> taskAsset.count = count.intValue(), taskAsset -> Integer.valueOf(taskAsset.count)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).build();
/*    */   }
/* 21 */   protected int count = 1;
/*    */   
/*    */   public CountObjectiveTaskAsset(String descriptionId, TaskConditionAsset[] taskConditions, Vector3i[] mapMarkers, int count) {
/* 24 */     super(descriptionId, taskConditions, mapMarkers);
/* 25 */     this.count = count;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCount() {
/* 32 */     return this.count;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean matchesAsset0(ObjectiveTaskAsset task) {
/* 37 */     if (!(task instanceof CountObjectiveTaskAsset)) return false;
/*    */     
/* 39 */     return (((CountObjectiveTaskAsset)task).count == this.count);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 45 */     return "CountObjectiveTaskAsset{count=" + this.count + "} " + super
/*    */       
/* 47 */       .toString();
/*    */   }
/*    */   
/*    */   protected CountObjectiveTaskAsset() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\task\CountObjectiveTaskAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */