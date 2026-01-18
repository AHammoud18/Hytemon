/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.QueueMaterialProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import java.util.ArrayList;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class QueueMaterialProviderAsset
/*    */   extends MaterialProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<QueueMaterialProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 22 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(QueueMaterialProviderAsset.class, QueueMaterialProviderAsset::new, MaterialProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Queue", (Codec)new ArrayCodec((Codec)MaterialProviderAsset.CODEC, x$0 -> new MaterialProviderAsset[x$0]), true), (t, k) -> t.queue = k, k -> k.queue).add()).build();
/*    */   }
/* 24 */   private MaterialProviderAsset[] queue = new MaterialProviderAsset[0];
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MaterialProvider<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/* 29 */     if (skip()) return MaterialProvider.noMaterialProvider();
/*    */     
/* 31 */     ArrayList<MaterialProvider<Material>> queueList = new ArrayList<>(this.queue.length);
/* 32 */     for (MaterialProviderAsset m : this.queue) {
/* 33 */       if (m == null) {
/* 34 */         LoggerUtil.getLogger().warning("Null element in queue provided.");
/*    */       } else {
/*    */         
/* 37 */         queueList.add(m.build(argument));
/*    */       } 
/* 39 */     }  return (MaterialProvider<Material>)new QueueMaterialProvider(queueList);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 44 */     for (MaterialProviderAsset materialProviderAsset : this.queue)
/* 45 */       materialProviderAsset.cleanUp(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\QueueMaterialProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */