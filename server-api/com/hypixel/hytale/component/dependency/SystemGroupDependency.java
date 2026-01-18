/*    */ package com.hypixel.hytale.component.dependency;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.SystemGroup;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SystemGroupDependency<ECS_TYPE>
/*    */   extends Dependency<ECS_TYPE>
/*    */ {
/*    */   @Nonnull
/*    */   private final SystemGroup<ECS_TYPE> group;
/*    */   
/*    */   public SystemGroupDependency(@Nonnull Order order, @Nonnull SystemGroup<ECS_TYPE> group) {
/* 18 */     this(order, group, OrderPriority.NORMAL);
/*    */   }
/*    */   
/*    */   public SystemGroupDependency(@Nonnull Order order, @Nonnull SystemGroup<ECS_TYPE> group, int priority) {
/* 22 */     super(order, priority);
/* 23 */     this.group = group;
/*    */   }
/*    */   
/*    */   public SystemGroupDependency(@Nonnull Order order, @Nonnull SystemGroup<ECS_TYPE> group, @Nonnull OrderPriority priority) {
/* 27 */     super(order, priority);
/* 28 */     this.group = group;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public SystemGroup<ECS_TYPE> getGroup() {
/* 33 */     return this.group;
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(@Nonnull ComponentRegistry<ECS_TYPE> registry) {
/* 38 */     if (!registry.hasSystemGroup(this.group)) throw new IllegalArgumentException("System dependency isn't registered: " + String.valueOf(this.group));
/*    */   
/*    */   }
/*    */   
/*    */   public void resolveGraphEdge(@Nonnull ComponentRegistry<ECS_TYPE> registry, @Nonnull ISystem<ECS_TYPE> thisSystem, @Nonnull DependencyGraph<ECS_TYPE> graph) {
/* 43 */     switch (this.order) {
/*    */       
/*    */       case BEFORE:
/* 46 */         for (ISystem<ECS_TYPE> system : graph.getSystems()) {
/* 47 */           if (this.group.equals(system.getGroup())) {
/* 48 */             graph.addEdge(thisSystem, system, -this.priority);
/*    */           }
/*    */         } 
/*    */         break;
/*    */       
/*    */       case AFTER:
/* 54 */         for (ISystem<ECS_TYPE> system : graph.getSystems()) {
/* 55 */           if (this.group.equals(system.getGroup())) {
/* 56 */             graph.addEdge(system, thisSystem, this.priority);
/*    */           }
/*    */         } 
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 66 */     return "SystemGroupDependency{group=" + String.valueOf(this.group) + "} " + super
/*    */       
/* 68 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\dependency\SystemGroupDependency.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */