/*    */ package com.hypixel.hytale.component.dependency;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.SystemType;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SystemTypeDependency<ECS_TYPE, T extends ISystem<ECS_TYPE>>
/*    */   extends Dependency<ECS_TYPE>
/*    */ {
/*    */   @Nonnull
/*    */   private final SystemType<ECS_TYPE, T> systemType;
/*    */   
/*    */   public SystemTypeDependency(@Nonnull Order order, @Nonnull SystemType<ECS_TYPE, T> systemType) {
/* 18 */     this(order, systemType, OrderPriority.NORMAL);
/*    */   }
/*    */   
/*    */   public SystemTypeDependency(@Nonnull Order order, @Nonnull SystemType<ECS_TYPE, T> systemType, int priority) {
/* 22 */     super(order, priority);
/* 23 */     this.systemType = systemType;
/*    */   }
/*    */   
/*    */   public SystemTypeDependency(@Nonnull Order order, @Nonnull SystemType<ECS_TYPE, T> systemType, @Nonnull OrderPriority priority) {
/* 27 */     super(order, priority);
/* 28 */     this.systemType = systemType;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public SystemType<ECS_TYPE, T> getSystemType() {
/* 33 */     return this.systemType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(@Nonnull ComponentRegistry<ECS_TYPE> registry) {
/* 38 */     if (!registry.hasSystemType(this.systemType)) throw new IllegalArgumentException("SystemType dependency isn't registered: " + String.valueOf(this.systemType));
/*    */   
/*    */   }
/*    */   
/*    */   public void resolveGraphEdge(@Nonnull ComponentRegistry<ECS_TYPE> registry, @Nonnull ISystem<ECS_TYPE> thisSystem, @Nonnull DependencyGraph<ECS_TYPE> graph) {
/* 43 */     switch (this.order) {
/*    */       
/*    */       case BEFORE:
/* 46 */         for (ISystem<ECS_TYPE> system : graph.getSystems()) {
/* 47 */           if (this.systemType.isType(system)) {
/* 48 */             graph.addEdge(thisSystem, system, -this.priority);
/*    */           }
/*    */         } 
/*    */         break;
/*    */       
/*    */       case AFTER:
/* 54 */         for (ISystem<ECS_TYPE> system : graph.getSystems()) {
/* 55 */           if (this.systemType.isType(system)) {
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
/* 66 */     return "SystemTypeDependency{systemType=" + String.valueOf(this.systemType) + "} " + super
/*    */       
/* 68 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\dependency\SystemTypeDependency.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */