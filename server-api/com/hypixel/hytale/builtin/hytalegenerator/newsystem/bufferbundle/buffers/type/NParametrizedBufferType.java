/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NBuffer;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NParametrizedBufferType
/*    */   extends NBufferType
/*    */ {
/*    */   @Nonnull
/*    */   public final Class parameterClass;
/*    */   
/*    */   public NParametrizedBufferType(@Nonnull String name, int index, @Nonnull Class bufferClass, @Nonnull Class parameterClass, @Nonnull Supplier<NBuffer> bufferSupplier) {
/* 20 */     super(name, index, bufferClass, bufferSupplier);
/* 21 */     this.parameterClass = parameterClass;
/*    */   }
/*    */   
/*    */   public boolean isValidType(@Nonnull Class bufferClass, @Nonnull Class parameterClass) {
/* 25 */     return (this.bufferClass.equals(bufferClass) && this.parameterClass.equals(parameterClass));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValid(@Nonnull NBuffer buffer) {
/* 30 */     if (!this.bufferClass.isInstance(buffer)) {
/* 31 */       return false;
/*    */     }
/*    */     
/* 34 */     return true;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/*    */     NParametrizedBufferType that;
/* 39 */     if (o instanceof NParametrizedBufferType) { that = (NParametrizedBufferType)o; } else { return false; }
/* 40 */      if (!super.equals(o)) return false; 
/* 41 */     return Objects.equals(this.parameterClass, that.parameterClass);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 46 */     return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.parameterClass });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\bufferbundle\buffers\type\NParametrizedBufferType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */