/*    */ package com.hypixel.hytale.builtin.hytalegenerator.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class OffsetPositionProvider
/*    */   extends PositionProvider {
/*    */   @Nonnull
/*    */   private final Vector3i offset3i;
/*    */   @Nonnull
/*    */   private final Vector3d offset3d;
/*    */   @Nonnull
/*    */   private final PositionProvider positionProvider;
/*    */   
/*    */   public OffsetPositionProvider(@Nonnull Vector3i offset, @Nonnull PositionProvider positionProvider) {
/* 17 */     this.offset3i = offset.clone();
/* 18 */     this.positionProvider = positionProvider;
/*    */     
/* 20 */     this.offset3d = this.offset3i.toVector3d();
/*    */   }
/*    */   
/*    */   public OffsetPositionProvider(@Nonnull Vector3d offset, @Nonnull PositionProvider positionProvider) {
/* 24 */     this.offset3d = offset.clone();
/* 25 */     this.positionProvider = positionProvider;
/*    */     
/* 27 */     this.offset3i = this.offset3d.toVector3i();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void positionsIn(@Nonnull PositionProvider.Context context) {
/* 33 */     Vector3d windowMin = context.minInclusive.clone();
/* 34 */     Vector3d windowMax = context.maxExclusive.clone();
/* 35 */     windowMin.subtract(this.offset3d);
/* 36 */     windowMax.subtract(this.offset3d);
/*    */     
/* 38 */     PositionProvider.Context childContext = new PositionProvider.Context();
/* 39 */     childContext.minInclusive = windowMin;
/* 40 */     childContext.maxExclusive = windowMax;
/* 41 */     childContext.consumer = (p -> {
/*    */         Vector3d offsetP = p.clone();
/*    */         offsetP.add(this.offset3d);
/*    */         context.consumer.accept(offsetP);
/*    */       });
/* 46 */     childContext.workerId = context.workerId;
/*    */ 
/*    */     
/* 49 */     this.positionProvider.positionsIn(childContext);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\positionproviders\OffsetPositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */