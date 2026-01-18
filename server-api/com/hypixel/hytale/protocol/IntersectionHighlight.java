/*    */ package com.hypixel.hytale.protocol;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntersectionHighlight
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public float highlightThreshold;
/*    */   @Nullable
/*    */   public Color highlightColor;
/*    */   
/*    */   public IntersectionHighlight() {}
/*    */   
/*    */   public IntersectionHighlight(float highlightThreshold, @Nullable Color highlightColor) {
/* 27 */     this.highlightThreshold = highlightThreshold;
/* 28 */     this.highlightColor = highlightColor;
/*    */   }
/*    */   
/*    */   public IntersectionHighlight(@Nonnull IntersectionHighlight other) {
/* 32 */     this.highlightThreshold = other.highlightThreshold;
/* 33 */     this.highlightColor = other.highlightColor;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static IntersectionHighlight deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     IntersectionHighlight obj = new IntersectionHighlight();
/* 39 */     byte nullBits = buf.getByte(offset);
/* 40 */     obj.highlightThreshold = buf.getFloatLE(offset + 1);
/* 41 */     if ((nullBits & 0x1) != 0) obj.highlightColor = Color.deserialize(buf, offset + 5);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 8;
/*    */   }
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 52 */     byte nullBits = 0;
/* 53 */     if (this.highlightColor != null) nullBits = (byte)(nullBits | 0x1); 
/* 54 */     buf.writeByte(nullBits);
/*    */     
/* 56 */     buf.writeFloatLE(this.highlightThreshold);
/* 57 */     if (this.highlightColor != null) { this.highlightColor.serialize(buf); } else { buf.writeZero(3); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 63 */     return 8;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 67 */     if (buffer.readableBytes() - offset < 8) {
/* 68 */       return ValidationResult.error("Buffer too small: expected at least 8 bytes");
/*    */     }
/*    */ 
/*    */     
/* 72 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public IntersectionHighlight clone() {
/* 76 */     IntersectionHighlight copy = new IntersectionHighlight();
/* 77 */     copy.highlightThreshold = this.highlightThreshold;
/* 78 */     copy.highlightColor = (this.highlightColor != null) ? this.highlightColor.clone() : null;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     IntersectionHighlight other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof IntersectionHighlight) { other = (IntersectionHighlight)obj; } else { return false; }
/* 87 */      return (this.highlightThreshold == other.highlightThreshold && Objects.equals(this.highlightColor, other.highlightColor));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Float.valueOf(this.highlightThreshold), this.highlightColor });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\IntersectionHighlight.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */