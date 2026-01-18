/*    */ package com.hypixel.hytale.protocol;
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
/*    */ 
/*    */ 
/*    */ public class Size
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public int width;
/*    */   public int height;
/*    */   
/*    */   public Size() {}
/*    */   
/*    */   public Size(int width, int height) {
/* 27 */     this.width = width;
/* 28 */     this.height = height;
/*    */   }
/*    */   
/*    */   public Size(@Nonnull Size other) {
/* 32 */     this.width = other.width;
/* 33 */     this.height = other.height;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Size deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     Size obj = new Size();
/*    */     
/* 40 */     obj.width = buf.getIntLE(offset + 0);
/* 41 */     obj.height = buf.getIntLE(offset + 4);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 53 */     buf.writeIntLE(this.width);
/* 54 */     buf.writeIntLE(this.height);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 60 */     return 8;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 64 */     if (buffer.readableBytes() - offset < 8) {
/* 65 */       return ValidationResult.error("Buffer too small: expected at least 8 bytes");
/*    */     }
/*    */ 
/*    */     
/* 69 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public Size clone() {
/* 73 */     Size copy = new Size();
/* 74 */     copy.width = this.width;
/* 75 */     copy.height = this.height;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     Size other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof Size) { other = (Size)obj; } else { return false; }
/* 84 */      return (this.width == other.width && this.height == other.height);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { Integer.valueOf(this.width), Integer.valueOf(this.height) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Size.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */