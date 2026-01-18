/*    */ package com.hypixel.hytale.builtin.hytalegenerator.propdistributions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SandwichAssignments
/*    */   extends Assignments
/*    */ {
/*    */   @Nonnull
/*    */   private final List<VerticalDelimiter> verticalDelimiters;
/*    */   private final int runtime;
/*    */   
/*    */   public SandwichAssignments(@Nonnull List<VerticalDelimiter> verticalDelimiters, int runtime) {
/* 18 */     this.runtime = runtime;
/* 19 */     this.verticalDelimiters = new ArrayList<>(verticalDelimiters);
/*    */   }
/*    */ 
/*    */   
/*    */   public Prop propAt(@Nonnull Vector3d position, @Nonnull WorkerIndexer.Id id, double distanceTOBiomeEdge) {
/* 24 */     if (this.verticalDelimiters.isEmpty()) {
/* 25 */       return Prop.noProp();
/*    */     }
/* 27 */     for (VerticalDelimiter fd : this.verticalDelimiters) {
/* 28 */       if (fd.isInside(position.y)) {
/* 29 */         return fd.assignments.propAt(position, id, distanceTOBiomeEdge);
/*    */       }
/*    */     } 
/*    */     
/* 33 */     return Prop.noProp();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRuntime() {
/* 38 */     return this.runtime;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<Prop> getAllPossibleProps() {
/* 44 */     ArrayList<Prop> list = new ArrayList<>();
/* 45 */     for (VerticalDelimiter f : this.verticalDelimiters) {
/* 46 */       list.addAll(f.assignments.getAllPossibleProps());
/*    */     }
/* 48 */     return list;
/*    */   }
/*    */   
/*    */   public static class VerticalDelimiter {
/*    */     double maxY;
/*    */     double minY;
/*    */     Assignments assignments;
/*    */     
/*    */     public VerticalDelimiter(@Nonnull Assignments propDistributions, double minY, double maxY) {
/* 57 */       this.minY = minY;
/* 58 */       this.maxY = maxY;
/* 59 */       this.assignments = propDistributions;
/*    */     }
/*    */     
/*    */     boolean isInside(double fieldValue) {
/* 63 */       return (fieldValue < this.maxY && fieldValue >= this.minY);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\propdistributions\SandwichAssignments.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */