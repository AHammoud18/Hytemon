/*    */ package com.hypixel.hytale.server.core.task;
/*    */ 
/*    */ import com.hypixel.hytale.registry.Registration;
/*    */ import java.util.concurrent.Future;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaskRegistration
/*    */   extends Registration
/*    */ {
/*    */   private final Future<?> task;
/*    */   
/*    */   public TaskRegistration(@Nonnull Future<?> task) {
/* 18 */     super(() -> true, () -> task.cancel(false));
/* 19 */     this.task = task;
/*    */   }
/*    */   
/*    */   public TaskRegistration(@Nonnull TaskRegistration registration, BooleanSupplier isEnabled, Runnable unregister) {
/* 23 */     super(isEnabled, unregister);
/* 24 */     this.task = registration.task;
/*    */   }
/*    */   
/*    */   public Future<?> getTask() {
/* 28 */     return this.task;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 34 */     return "TaskRegistration{task=" + String.valueOf(this.task) + ", " + super
/*    */       
/* 36 */       .toString() + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\task\TaskRegistration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */