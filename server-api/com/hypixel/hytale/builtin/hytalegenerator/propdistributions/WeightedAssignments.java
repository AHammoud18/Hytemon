/*    */ package com.hypixel.hytale.builtin.hytalegenerator.propdistributions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.WeightedMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.SeedGenerator;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.util.FastRandom;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class WeightedAssignments
/*    */   extends Assignments
/*    */ {
/*    */   @Nonnull
/*    */   private final WeightedMap<Assignments> weightedDistributions;
/*    */   @Nonnull
/*    */   private final SeedGenerator seedGenerator;
/*    */   private final int runtime;
/*    */   private final double noneProbability;
/*    */   
/*    */   public WeightedAssignments(@Nonnull WeightedMap<Assignments> props, int seed, double noneProbability, int runtime) {
/* 26 */     this.weightedDistributions = new WeightedMap(props);
/* 27 */     this.runtime = runtime;
/* 28 */     this.seedGenerator = new SeedGenerator(seed);
/* 29 */     this.noneProbability = noneProbability;
/*    */   }
/*    */ 
/*    */   
/*    */   public Prop propAt(@Nonnull Vector3d position, @Nonnull WorkerIndexer.Id id, double distanceTOBiomeEdge) {
/* 34 */     if (this.weightedDistributions.size() == 0) {
/* 35 */       return Prop.noProp();
/*    */     }
/*    */     
/* 38 */     long x = (long)(position.x * 10000.0D);
/* 39 */     long y = (long)(position.y * 10000.0D);
/* 40 */     long z = (long)(position.z * 10000.0D);
/*    */     
/* 42 */     FastRandom rand = new FastRandom(this.seedGenerator.seedAt(x, y, z));
/* 43 */     if (rand.nextDouble() < this.noneProbability) {
/* 44 */       return Prop.noProp();
/*    */     }
/*    */     
/* 47 */     return ((Assignments)this.weightedDistributions.pick((Random)rand)).propAt(position, id, distanceTOBiomeEdge);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRuntime() {
/* 52 */     return this.runtime;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<Prop> getAllPossibleProps() {
/* 58 */     ArrayList<Prop> list = new ArrayList<>();
/* 59 */     for (Assignments d : this.weightedDistributions.allElements()) {
/* 60 */       list.addAll(d.getAllPossibleProps());
/*    */     }
/* 62 */     return list;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\propdistributions\WeightedAssignments.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */