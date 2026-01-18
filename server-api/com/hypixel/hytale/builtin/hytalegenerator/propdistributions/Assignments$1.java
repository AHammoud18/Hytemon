/*    */ package com.hypixel.hytale.builtin.hytalegenerator.propdistributions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends Assignments
/*    */ {
/*    */   @Nonnull
/*    */   public Prop propAt(@Nonnull Vector3d position, @Nonnull WorkerIndexer.Id id, double distanceTOBiomeEdge) {
/* 25 */     return Prop.noProp();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRuntime() {
/* 30 */     return runtime;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<Prop> getAllPossibleProps() {
/* 36 */     return Collections.singletonList(Prop.noProp());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\propdistributions\Assignments$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */