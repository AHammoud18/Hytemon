/*    */ package com.hypixel.hytale.component.dependency;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Dependency<ECS_TYPE>
/*    */ {
/*    */   @Nonnull
/*    */   protected final Order order;
/*    */   protected final int priority;
/*    */   
/*    */   public Dependency(@Nonnull Order order, int priority) {
/* 18 */     this.order = order;
/* 19 */     this.priority = priority;
/*    */   }
/*    */   
/*    */   public Dependency(@Nonnull Order order, @Nonnull OrderPriority priority) {
/* 23 */     this.order = order;
/* 24 */     this.priority = priority.getValue();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Order getOrder() {
/* 29 */     return this.order;
/*    */   }
/*    */   
/*    */   public int getPriority() {
/* 33 */     return this.priority;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void validate(@Nonnull ComponentRegistry<ECS_TYPE> paramComponentRegistry);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void resolveGraphEdge(@Nonnull ComponentRegistry<ECS_TYPE> paramComponentRegistry, @Nonnull ISystem<ECS_TYPE> paramISystem, @Nonnull DependencyGraph<ECS_TYPE> paramDependencyGraph);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 51 */     return "Dependency{order=" + String.valueOf(this.order) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\dependency\Dependency.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */