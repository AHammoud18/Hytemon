/*    */ package com.hypixel.hytale.builtin.adventure.objectives.task;
/*    */ 
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectiveTaskRef<T extends ObjectiveTask>
/*    */ {
/*    */   private final UUID objectiveUUID;
/*    */   private final T objectiveTask;
/*    */   
/*    */   public ObjectiveTaskRef(UUID objectiveUUID, T objectiveTask) {
/* 14 */     this.objectiveUUID = objectiveUUID;
/* 15 */     this.objectiveTask = objectiveTask;
/*    */   }
/*    */   
/*    */   public UUID getObjectiveUUID() {
/* 19 */     return this.objectiveUUID;
/*    */   }
/*    */   
/*    */   public T getObjectiveTask() {
/* 23 */     return this.objectiveTask;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\task\ObjectiveTaskRef.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */