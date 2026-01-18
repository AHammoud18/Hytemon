/*    */ package com.hypixel.hytale.builtin.hytalegenerator.propdistributions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class FieldFunctionAssignments
/*    */   extends Assignments
/*    */ {
/*    */   @Nonnull
/*    */   private final Density density;
/*    */   @Nonnull
/*    */   private final List<FieldDelimiter> fieldDelimiters;
/*    */   private final int runtime;
/*    */   
/*    */   public FieldFunctionAssignments(@Nonnull Density functionTree, @Nonnull List<FieldDelimiter> fieldDelimiters, int runtime) {
/* 22 */     this.runtime = runtime;
/* 23 */     this.density = functionTree;
/* 24 */     this.fieldDelimiters = new ArrayList<>(fieldDelimiters);
/*    */   }
/*    */ 
/*    */   
/*    */   public Prop propAt(@Nonnull Vector3d position, @Nonnull WorkerIndexer.Id id, double distanceTOBiomeEdge) {
/* 29 */     if (this.fieldDelimiters.isEmpty()) {
/* 30 */       return Prop.noProp();
/*    */     }
/* 32 */     Density.Context context = new Density.Context();
/* 33 */     context.position = position;
/* 34 */     context.workerId = id;
/* 35 */     context.distanceToBiomeEdge = distanceTOBiomeEdge;
/*    */     
/* 37 */     double fieldValue = this.density.process(context);
/* 38 */     for (FieldDelimiter fd : this.fieldDelimiters) {
/* 39 */       if (fd.isInside(fieldValue)) {
/* 40 */         return fd.assignments.propAt(position, id, distanceTOBiomeEdge);
/*    */       }
/*    */     } 
/*    */     
/* 44 */     return Prop.noProp();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRuntime() {
/* 49 */     return this.runtime;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<Prop> getAllPossibleProps() {
/* 55 */     ArrayList<Prop> list = new ArrayList<>();
/* 56 */     for (FieldDelimiter f : this.fieldDelimiters) {
/* 57 */       list.addAll(f.assignments.getAllPossibleProps());
/*    */     }
/* 59 */     return list;
/*    */   }
/*    */   
/*    */   public static class FieldDelimiter {
/*    */     double top;
/*    */     double bottom;
/*    */     Assignments assignments;
/*    */     
/*    */     public FieldDelimiter(@Nonnull Assignments propDistributions, double bottom, double top) {
/* 68 */       this.bottom = bottom;
/* 69 */       this.top = top;
/* 70 */       this.assignments = propDistributions;
/*    */     }
/*    */     
/*    */     boolean isInside(double fieldValue) {
/* 74 */       return (fieldValue < this.top && fieldValue >= this.bottom);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\propdistributions\FieldFunctionAssignments.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */