/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import com.hypixel.hytale.server.spawning.ISpawnableWithModel;
/*    */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SpawnableWithModelBuilder<T>
/*    */   extends BuilderBase<T>
/*    */   implements ISpawnableWithModel
/*    */ {
/*    */   private IntSet dynamicDependencies;
/*    */   
/*    */   public boolean hasDynamicDependencies() {
/* 16 */     return (this.dynamicDependencies != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addDynamicDependency(int builderIndex) {
/* 21 */     if (this.dynamicDependencies == null) this.dynamicDependencies = (IntSet)new IntOpenHashSet(); 
/* 22 */     this.dynamicDependencies.add(builderIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   public IntSet getDynamicDependencies() {
/* 27 */     return this.dynamicDependencies;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clearDynamicDependencies() {
/* 32 */     if (this.dynamicDependencies != null) this.dynamicDependencies.clear();
/*    */   
/*    */   }
/*    */   
/*    */   public boolean isSpawnable() {
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\SpawnableWithModelBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */