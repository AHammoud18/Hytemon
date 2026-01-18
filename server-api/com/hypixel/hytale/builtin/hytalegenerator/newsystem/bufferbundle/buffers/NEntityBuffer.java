/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.performanceinstruments.MemInstrument;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.entity.EntityPlacementData;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NEntityBuffer
/*    */   extends NBuffer
/*    */ {
/* 16 */   private List<EntityPlacementData> entities = null;
/*    */   
/*    */   private boolean isReference = false;
/*    */   
/*    */   public void forEach(@Nonnull Consumer<EntityPlacementData> consumer) {
/* 21 */     if (this.entities == null) {
/*    */       return;
/*    */     }
/* 24 */     for (EntityPlacementData entity : this.entities) {
/* 25 */       consumer.accept(entity);
/*    */     }
/*    */   }
/*    */   
/*    */   public void addEntity(@Nonnull EntityPlacementData entityPlacementData) {
/* 30 */     if (this.entities == null) {
/* 31 */       this.entities = new ArrayList<>();
/*    */     }
/*    */     
/* 34 */     this.entities.add(entityPlacementData);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MemInstrument.Report getMemoryUsage() {
/* 40 */     long size_bytes = 1L;
/*    */     
/* 42 */     if (this.entities != null) {
/* 43 */       size_bytes += 24L + 8L * this.entities.size();
/* 44 */       for (EntityPlacementData entity : this.entities) {
/* 45 */         size_bytes += entity.getMemoryUsage().size_bytes();
/*    */       }
/*    */     } 
/*    */     
/* 49 */     return new MemInstrument.Report(size_bytes);
/*    */   }
/*    */   
/*    */   public void copyFrom(@Nonnull NEntityBuffer sourceBuffer) {
/* 53 */     this.entities = sourceBuffer.entities;
/* 54 */     this.isReference = true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\bufferbundle\buffers\NEntityBuffer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */