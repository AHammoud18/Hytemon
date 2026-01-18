/*    */ package com.hypixel.hytale.component.dependency;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RootDependency<ECS_TYPE>
/*    */   extends Dependency<ECS_TYPE>
/*    */ {
/* 16 */   private static final RootDependency<?> FIRST = new RootDependency(OrderPriority.CLOSEST);
/* 17 */   private static final RootDependency<?> LAST = new RootDependency(OrderPriority.FURTHEST);
/* 18 */   private static final Set<Dependency<?>> FIRST_SET = Set.of(FIRST);
/* 19 */   private static final Set<Dependency<?>> LAST_SET = Set.of(LAST);
/*    */ 
/*    */   
/*    */   public static <ECS_TYPE> RootDependency<ECS_TYPE> first() {
/* 23 */     return (RootDependency)FIRST;
/*    */   }
/*    */ 
/*    */   
/*    */   public static <ECS_TYPE> RootDependency<ECS_TYPE> last() {
/* 28 */     return (RootDependency)LAST;
/*    */   }
/*    */ 
/*    */   
/*    */   public static <ECS_TYPE> Set<Dependency<ECS_TYPE>> firstSet() {
/* 33 */     return (Set)FIRST_SET;
/*    */   }
/*    */ 
/*    */   
/*    */   public static <ECS_TYPE> Set<Dependency<ECS_TYPE>> lastSet() {
/* 38 */     return (Set)LAST_SET;
/*    */   }
/*    */   
/*    */   public RootDependency(int priority) {
/* 42 */     super(Order.AFTER, priority);
/*    */   }
/*    */   
/*    */   public RootDependency(@Nonnull OrderPriority priority) {
/* 46 */     super(Order.AFTER, priority);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void validate(@Nonnull ComponentRegistry<ECS_TYPE> registry) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void resolveGraphEdge(@Nonnull ComponentRegistry<ECS_TYPE> registry, @Nonnull ISystem<ECS_TYPE> thisSystem, @Nonnull DependencyGraph<ECS_TYPE> graph) {
/* 56 */     if (this.order == Order.BEFORE) throw new UnsupportedOperationException("RootDependency can't have Order.BEFORE!"); 
/* 57 */     graph.addEdgeFromRoot(thisSystem, this.priority);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 63 */     return "SystemDependency{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\dependency\RootDependency.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */