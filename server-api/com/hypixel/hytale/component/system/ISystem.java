/*    */ package com.hypixel.hytale.component.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.SystemGroup;
/*    */ import com.hypixel.hytale.component.dependency.Dependency;
/*    */ import com.hypixel.hytale.component.dependency.DependencyGraph;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ISystem<ECS_TYPE>
/*    */ {
/* 22 */   public static final ISystem[] EMPTY_ARRAY = new ISystem[0];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default void onSystemRegistered() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default void onSystemUnregistered() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   default SystemGroup<ECS_TYPE> getGroup() {
/* 46 */     return null;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   default Set<Dependency<ECS_TYPE>> getDependencies() {
/* 51 */     return Collections.emptySet();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static <ECS_TYPE> void calculateOrder(@Nonnull ComponentRegistry<ECS_TYPE> registry, @Nonnull ISystem<ECS_TYPE>[] sortedSystems, int systemSize) {
/* 59 */     DependencyGraph<ECS_TYPE> graph = new DependencyGraph(Arrays.<ISystem>copyOf((ISystem[])sortedSystems, systemSize));
/*    */     
/* 61 */     graph.resolveEdges(registry);
/*    */     
/* 63 */     graph.sort((ISystem[])sortedSystems);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\ISystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */