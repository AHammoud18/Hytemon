/*    */ package com.hypixel.hytale.protocol.packets.buildertools;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderToolBrushAxisArg
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   @Nonnull
/* 20 */   public BrushAxis defaultValue = BrushAxis.None;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BuilderToolBrushAxisArg(@Nonnull BrushAxis defaultValue) {
/* 26 */     this.defaultValue = defaultValue;
/*    */   }
/*    */   
/*    */   public BuilderToolBrushAxisArg(@Nonnull BuilderToolBrushAxisArg other) {
/* 30 */     this.defaultValue = other.defaultValue;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BuilderToolBrushAxisArg deserialize(@Nonnull ByteBuf buf, int offset) {
/* 35 */     BuilderToolBrushAxisArg obj = new BuilderToolBrushAxisArg();
/*    */     
/* 37 */     obj.defaultValue = BrushAxis.fromValue(buf.getByte(offset + 0));
/*    */ 
/*    */     
/* 40 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 44 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 49 */     buf.writeByte(this.defaultValue.getValue());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 55 */     return 1;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 59 */     if (buffer.readableBytes() - offset < 1) {
/* 60 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*    */     }
/*    */ 
/*    */     
/* 64 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public BuilderToolBrushAxisArg clone() {
/* 68 */     BuilderToolBrushAxisArg copy = new BuilderToolBrushAxisArg();
/* 69 */     copy.defaultValue = this.defaultValue;
/* 70 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     BuilderToolBrushAxisArg other;
/* 76 */     if (this == obj) return true; 
/* 77 */     if (obj instanceof BuilderToolBrushAxisArg) { other = (BuilderToolBrushAxisArg)obj; } else { return false; }
/* 78 */      return Objects.equals(this.defaultValue, other.defaultValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 83 */     return Objects.hash(new Object[] { this.defaultValue });
/*    */   }
/*    */   
/*    */   public BuilderToolBrushAxisArg() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolBrushAxisArg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */