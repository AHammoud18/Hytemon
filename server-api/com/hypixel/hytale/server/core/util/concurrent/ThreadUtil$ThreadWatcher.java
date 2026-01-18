/*     */ package com.hypixel.hytale.server.core.util.concurrent;
/*     */ 
/*     */ import java.security.Permission;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
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
/*     */ class ThreadWatcher
/*     */   extends SecurityManager
/*     */ {
/*     */   private final Predicate<Thread> predicate;
/*     */   private final Consumer<Thread> action;
/*     */   
/*     */   public ThreadWatcher(Predicate<Thread> predicate, Consumer<Thread> action) {
/*  89 */     this.predicate = predicate;
/*  90 */     this.action = action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkPermission(Permission perm) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkPermission(Permission perm, Object context) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkAccess(ThreadGroup g) {
/* 105 */     Thread creatingThread = Thread.currentThread();
/* 106 */     if (this.predicate.test(creatingThread))
/* 107 */       this.action.accept(creatingThread); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\concurrent\ThreadUtil$ThreadWatcher.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */