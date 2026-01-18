/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NBuffer;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBufferType
/*    */ {
/*    */   public final Class bufferClass;
/*    */   public final int index;
/*    */   public final Supplier<NBuffer> bufferSupplier;
/*    */   public final String name;
/*    */   
/*    */   public NBufferType(@Nonnull String name, int index, @Nonnull Class bufferClass, @Nonnull Supplier<NBuffer> bufferSupplier) {
/* 20 */     this.name = name;
/* 21 */     this.index = index;
/* 22 */     this.bufferClass = bufferClass;
/* 23 */     this.bufferSupplier = bufferSupplier;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/*    */     NBufferType that;
/* 28 */     if (o instanceof NBufferType) { that = (NBufferType)o; } else { return false; }
/*    */     
/* 30 */     return (this.index == that.index && this.bufferClass.equals(that.bufferClass) && this.bufferSupplier.equals(that.bufferSupplier));
/*    */   }
/*    */   
/*    */   public boolean isValidType(@Nonnull Class bufferClass) {
/* 34 */     return this.bufferClass.equals(bufferClass);
/*    */   }
/*    */   
/*    */   public boolean isValid(@Nonnull NBuffer buffer) {
/* 38 */     return this.bufferClass.isInstance(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 43 */     int result = this.bufferClass.hashCode();
/* 44 */     result = 31 * result + this.index;
/* 45 */     result = 31 * result + this.bufferSupplier.hashCode();
/* 46 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\bufferbundle\buffers\type\NBufferType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */