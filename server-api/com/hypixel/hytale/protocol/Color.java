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
/*    */ public class Color
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 3;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 3;
/*    */   public static final int MAX_SIZE = 3;
/*    */   public byte red;
/*    */   public byte green;
/*    */   public byte blue;
/*    */   
/*    */   public Color() {}
/*    */   
/*    */   public Color(byte red, byte green, byte blue) {
/* 28 */     this.red = red;
/* 29 */     this.green = green;
/* 30 */     this.blue = blue;
/*    */   }
/*    */   
/*    */   public Color(@Nonnull Color other) {
/* 34 */     this.red = other.red;
/* 35 */     this.green = other.green;
/* 36 */     this.blue = other.blue;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Color deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     Color obj = new Color();
/*    */     
/* 43 */     obj.red = buf.getByte(offset + 0);
/* 44 */     obj.green = buf.getByte(offset + 1);
/* 45 */     obj.blue = buf.getByte(offset + 2);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     buf.writeByte(this.red);
/* 58 */     buf.writeByte(this.green);
/* 59 */     buf.writeByte(this.blue);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 65 */     return 3;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 69 */     if (buffer.readableBytes() - offset < 3) {
/* 70 */       return ValidationResult.error("Buffer too small: expected at least 3 bytes");
/*    */     }
/*    */ 
/*    */     
/* 74 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public Color clone() {
/* 78 */     Color copy = new Color();
/* 79 */     copy.red = this.red;
/* 80 */     copy.green = this.green;
/* 81 */     copy.blue = this.blue;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     Color other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof Color) { other = (Color)obj; } else { return false; }
/* 90 */      return (this.red == other.red && this.green == other.green && this.blue == other.blue);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { Byte.valueOf(this.red), Byte.valueOf(this.green), Byte.valueOf(this.blue) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Color.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */