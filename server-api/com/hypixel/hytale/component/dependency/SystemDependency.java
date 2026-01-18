/*    */ package com.hypixel.hytale.component.dependency;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SystemDependency<ECS_TYPE, T extends ISystem<ECS_TYPE>>
/*    */   extends Dependency<ECS_TYPE>
/*    */ {
/*    */   @Nonnull
/*    */   private final Class<T> systemClass;
/*    */   
/*    */   public SystemDependency(@Nonnull Order order, @Nonnull Class<T> systemClass) {
/* 17 */     this(order, systemClass, OrderPriority.NORMAL);
/*    */   }
/*    */   
/*    */   public SystemDependency(@Nonnull Order order, @Nonnull Class<T> systemClass, int priority) {
/* 21 */     super(order, priority);
/* 22 */     this.systemClass = systemClass;
/*    */   }
/*    */   
/*    */   public SystemDependency(@Nonnull Order order, @Nonnull Class<T> systemClass, @Nonnull OrderPriority priority) {
/* 26 */     super(order, priority);
/* 27 */     this.systemClass = systemClass;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Class<T> getSystemClass() {
/* 32 */     return this.systemClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(@Nonnull ComponentRegistry<ECS_TYPE> registry) {
/* 37 */     if (!registry.hasSystemClass(this.systemClass)) throw new IllegalArgumentException("SystemType dependency isn't registered: " + String.valueOf(this.systemClass));
/*    */   
/*    */   }
/*    */   
/*    */   public void resolveGraphEdge(@Nonnull ComponentRegistry<ECS_TYPE> registry, @Nonnull ISystem<ECS_TYPE> thisSystem, @Nonnull DependencyGraph<ECS_TYPE> graph) {
/* 42 */     switch (this.order) {
/*    */       
/*    */       case BEFORE:
/* 45 */         for (ISystem<ECS_TYPE> system : graph.getSystems()) {
/* 46 */           if (this.systemClass.equals(system.getClass())) {
/* 47 */             graph.addEdge(thisSystem, system, -this.priority);
/*    */           }
/*    */         } 
/*    */         break;
/*    */       
/*    */       case AFTER:
/* 53 */         for (ISystem<ECS_TYPE> system : graph.getSystems()) {
/* 54 */           if (this.systemClass.equals(system.getClass())) {
/* 55 */             graph.addEdge(system, thisSystem, this.priority);
/*    */           }
/*    */         } 
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 65 */     return "SystemDependency{systemClass=" + String.valueOf(this.systemClass) + "} " + super
/*    */       
/* 67 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\dependency\SystemDependency.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */