/*    */ package com.hypixel.hytale.builtin.hytalegenerator.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.SeedGenerator;
/*    */ import com.hypixel.hytale.math.util.FastRandom;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class FieldFunctionOccurrencePositionProvider
/*    */   extends PositionProvider
/*    */ {
/*    */   public static final double FP_RESOLUTION = 100.0D;
/*    */   @Nonnull
/*    */   private final Density field;
/*    */   @Nonnull
/*    */   private final PositionProvider positionProvider;
/*    */   @Nonnull
/*    */   private final SeedGenerator seedGenerator;
/*    */   
/*    */   public FieldFunctionOccurrencePositionProvider(@Nonnull Density field, @Nonnull PositionProvider positionProvider, int seed) {
/* 22 */     this.field = field;
/* 23 */     this.positionProvider = positionProvider;
/* 24 */     this.seedGenerator = new SeedGenerator(seed);
/*    */   }
/*    */ 
/*    */   
/*    */   public void positionsIn(@Nonnull PositionProvider.Context context) {
/* 29 */     PositionProvider.Context childContext = new PositionProvider.Context(context);
/* 30 */     childContext.consumer = (position -> {
/*    */         Density.Context densityContext = new Density.Context();
/*    */         
/*    */         densityContext.position = position;
/*    */         densityContext.positionsAnchor = context.anchor;
/*    */         densityContext.workerId = context.workerId;
/*    */         double discardChance = 1.0D - this.field.process(densityContext);
/*    */         FastRandom random = new FastRandom(this.seedGenerator.seedAt(position.x, position.y, position.z, 100.0D));
/*    */         if (discardChance > random.nextDouble()) {
/*    */           return;
/*    */         }
/*    */         context.consumer.accept(position);
/*    */       });
/* 43 */     this.positionProvider.positionsIn(childContext);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\positionproviders\FieldFunctionOccurrencePositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */