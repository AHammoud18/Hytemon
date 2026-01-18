/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ShutdownHooks
/*     */ {
/*  52 */   private static final List<Task> tasks = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Thread hook;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized <T extends Task> T add(T task) {
/*  77 */     Objects.requireNonNull(task);
/*     */ 
/*     */     
/*  80 */     if (hook == null) {
/*  81 */       hook = addHook(new Thread("JLine Shutdown Hook")
/*     */           {
/*     */             public void run() {
/*  84 */               ShutdownHooks.runTasks();
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*  90 */     Log.debug(new Object[] { "Adding shutdown-hook task: ", task });
/*  91 */     tasks.add((Task)task);
/*     */     
/*  93 */     return task;
/*     */   }
/*     */   
/*     */   private static synchronized void runTasks() {
/*  97 */     Log.debug(new Object[] { "Running all shutdown-hook tasks" });
/*     */ 
/*     */     
/* 100 */     for (Task task : (Task[])tasks.<Task>toArray(new Task[tasks.size()])) {
/* 101 */       Log.debug(new Object[] { "Running task: ", task });
/*     */       try {
/* 103 */         task.run();
/* 104 */       } catch (Throwable e) {
/* 105 */         Log.warn(new Object[] { "Task failed", e });
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     tasks.clear();
/*     */   }
/*     */   
/*     */   private static Thread addHook(Thread thread) {
/* 113 */     Log.debug(new Object[] { "Registering shutdown-hook: ", thread });
/* 114 */     Runtime.getRuntime().addShutdownHook(thread);
/* 115 */     return thread;
/*     */   }
/*     */   
/*     */   public static synchronized void remove(Task task) {
/* 119 */     Objects.requireNonNull(task);
/*     */ 
/*     */     
/* 122 */     if (hook == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 127 */     tasks.remove(task);
/*     */ 
/*     */     
/* 130 */     if (tasks.isEmpty()) {
/* 131 */       removeHook(hook);
/* 132 */       hook = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void removeHook(Thread thread) {
/* 137 */     Log.debug(new Object[] { "Removing shutdown-hook: ", thread });
/*     */     
/*     */     try {
/* 140 */       Runtime.getRuntime().removeShutdownHook(thread);
/* 141 */     } catch (IllegalStateException illegalStateException) {}
/*     */   }
/*     */   
/*     */   public static interface Task {
/*     */     void run() throws Exception;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\ShutdownHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */