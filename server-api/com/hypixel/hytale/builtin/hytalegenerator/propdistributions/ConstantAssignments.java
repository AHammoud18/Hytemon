/*    */ package com.hypixel.hytale.builtin.hytalegenerator.propdistributions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ConstantAssignments
/*    */   extends Assignments {
/*    */   @Nonnull
/*    */   private final Prop prop;
/*    */   private final int runtime;
/*    */   
/*    */   public ConstantAssignments(@Nonnull Prop prop, int runtime) {
/* 17 */     this.prop = prop;
/* 18 */     this.runtime = runtime;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Prop propAt(@Nonnull Vector3d position, @Nonnull WorkerIndexer.Id id, double distanceTOBiomeEdge) {
/* 24 */     return this.prop;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRuntime() {
/* 29 */     return this.runtime;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<Prop> getAllPossibleProps() {
/* 35 */     return Collections.singletonList(this.prop);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\propdistributions\ConstantAssignments.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */