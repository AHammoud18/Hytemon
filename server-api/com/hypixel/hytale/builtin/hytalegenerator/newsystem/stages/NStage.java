/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.NBufferBundle;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NBufferType;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public interface NStage {
/*    */   void run(@Nonnull Context paramContext);
/*    */   
/*    */   @Nonnull
/*    */   Map<NBufferType, Bounds3i> getInputTypesAndBounds_bufferGrid();
/*    */   
/*    */   @Nonnull
/*    */   List<NBufferType> getOutputTypes();
/*    */   
/*    */   @Nonnull
/*    */   String getName();
/*    */   
/*    */   public static final class Context {
/*    */     public Context(@Nonnull Map<NBufferType, NBufferBundle.Access.View> bufferAccess, @Nonnull WorkerIndexer.Id workerId) {
/* 21 */       this.bufferAccess = bufferAccess;
/* 22 */       this.workerId = workerId;
/*    */     }
/*    */     
/*    */     @Nonnull
/*    */     public Map<NBufferType, NBufferBundle.Access.View> bufferAccess;
/*    */     @Nonnull
/*    */     public WorkerIndexer.Id workerId;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\stages\NStage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */