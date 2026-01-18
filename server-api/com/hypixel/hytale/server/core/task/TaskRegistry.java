/*    */ package com.hypixel.hytale.server.core.task;
/*    */ 
/*    */ import com.hypixel.hytale.function.consumer.BooleanConsumer;
/*    */ import com.hypixel.hytale.registry.Registry;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.ScheduledFuture;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaskRegistry
/*    */   extends Registry<TaskRegistration>
/*    */ {
/*    */   public TaskRegistry(@Nonnull List<BooleanConsumer> registrations, BooleanSupplier precondition, String preconditionMessage) {
/* 19 */     super(registrations, precondition, preconditionMessage, TaskRegistration::new);
/*    */   }
/*    */   
/*    */   public TaskRegistration registerTask(@Nonnull CompletableFuture<Void> task) {
/* 23 */     return (TaskRegistration)register(new TaskRegistration(task));
/*    */   }
/*    */   
/*    */   public TaskRegistration registerTask(@Nonnull ScheduledFuture<Void> task) {
/* 27 */     return (TaskRegistration)register(new TaskRegistration(task));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\task\TaskRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */