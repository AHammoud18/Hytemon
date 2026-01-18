/*    */ package com.hypixel.hytale.builtin.hytalegenerator.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListPositionProvider
/*    */   extends PositionProvider
/*    */ {
/*    */   private List<Vector3i> positions3i;
/*    */   private List<Vector3d> positions3d;
/*    */   
/*    */   @Nonnull
/*    */   public static ListPositionProvider from3i(@Nonnull List<Vector3i> positions3i) {
/* 20 */     ListPositionProvider instance = new ListPositionProvider();
/* 21 */     instance.positions3i = new ArrayList<>();
/* 22 */     instance.positions3i.addAll(positions3i);
/*    */     
/* 24 */     instance.positions3d = new ArrayList<>(positions3i.size());
/* 25 */     instance.positions3i.forEach(p -> instance.positions3d.add(p.toVector3d()));
/* 26 */     return instance;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ListPositionProvider from3d(@Nonnull List<Vector3d> positions3d) {
/* 31 */     ListPositionProvider instance = new ListPositionProvider();
/* 32 */     instance.positions3d = new ArrayList<>();
/* 33 */     instance.positions3d.addAll(positions3d);
/*    */     
/* 35 */     instance.positions3i = new ArrayList<>(positions3d.size());
/* 36 */     instance.positions3d.forEach(p -> instance.positions3i.add(p.toVector3i()));
/* 37 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public void positionsIn(@Nonnull PositionProvider.Context context) {
/* 42 */     for (Vector3d p : this.positions3d) {
/* 43 */       if (VectorUtil.isInside(p, context.minInclusive, context.maxExclusive));
/* 44 */       context.consumer.accept(p);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\positionproviders\ListPositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */