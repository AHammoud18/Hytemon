/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public abstract class NPixelBuffer<T>
/*    */   extends NBuffer {
/*    */   public static final int BUFFER_SIZE_BITS = 3;
/* 10 */   public static final Vector3i SIZE = new Vector3i(8, 1, 8);
/*    */   
/*    */   @Nullable
/*    */   public abstract T getPixelContent(@Nonnull Vector3i paramVector3i);
/*    */   
/*    */   public abstract void setPixelContent(@Nonnull Vector3i paramVector3i, @Nullable T paramT);
/*    */   
/*    */   @Nonnull
/*    */   public abstract Class<T> getPixelType();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\bufferbundle\buffers\NPixelBuffer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */