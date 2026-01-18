/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class QueueMaterialProvider<V> extends MaterialProvider<V> {
/*    */   @Nonnull
/*    */   private final MaterialProvider<V>[] queue;
/*    */   
/*    */   public QueueMaterialProvider(@Nonnull List<MaterialProvider<V>> queue) {
/* 12 */     this.queue = (MaterialProvider<V>[])new MaterialProvider[queue.size()];
/*    */     
/* 14 */     for (int i = 0; i < queue.size(); i++) {
/* 15 */       MaterialProvider<V> l = queue.get(i);
/* 16 */       if (l == null) throw new IllegalArgumentException("null element in layers"); 
/* 17 */       this.queue[i] = l;
/*    */     } 
/*    */   } @Nullable
/*    */   public V getVoxelTypeAt(@Nonnull MaterialProvider.Context context) {
/*    */     MaterialProvider<V>[] arrayOfMaterialProvider;
/*    */     int i;
/*    */     byte b;
/* 24 */     for (arrayOfMaterialProvider = this.queue, i = arrayOfMaterialProvider.length, b = 0; b < i; ) { MaterialProvider<V> layer = arrayOfMaterialProvider[b];
/* 25 */       V material = layer.getVoxelTypeAt(context);
/* 26 */       if (material == null) { b++; continue; }
/* 27 */        return material; }
/*    */     
/* 29 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\QueueMaterialProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */