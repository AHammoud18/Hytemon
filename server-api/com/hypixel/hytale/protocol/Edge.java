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
/*    */ public class Edge
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 9;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 9;
/*    */   public static final int MAX_SIZE = 9;
/*    */   @Nullable
/*    */   public ColorAlpha color;
/*    */   public float width;
/*    */   
/*    */   public Edge() {}
/*    */   
/*    */   public Edge(@Nullable ColorAlpha color, float width) {
/* 27 */     this.color = color;
/* 28 */     this.width = width;
/*    */   }
/*    */   
/*    */   public Edge(@Nonnull Edge other) {
/* 32 */     this.color = other.color;
/* 33 */     this.width = other.width;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Edge deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     Edge obj = new Edge();
/* 39 */     byte nullBits = buf.getByte(offset);
/* 40 */     if ((nullBits & 0x1) != 0) obj.color = ColorAlpha.deserialize(buf, offset + 1); 
/* 41 */     obj.width = buf.getFloatLE(offset + 5);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 9;
/*    */   }
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 52 */     byte nullBits = 0;
/* 53 */     if (this.color != null) nullBits = (byte)(nullBits | 0x1); 
/* 54 */     buf.writeByte(nullBits);
/*    */     
/* 56 */     if (this.color != null) { this.color.serialize(buf); } else { buf.writeZero(4); }
/* 57 */      buf.writeFloatLE(this.width);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 63 */     return 9;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 67 */     if (buffer.readableBytes() - offset < 9) {
/* 68 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*    */     }
/*    */ 
/*    */     
/* 72 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public Edge clone() {
/* 76 */     Edge copy = new Edge();
/* 77 */     copy.color = (this.color != null) ? this.color.clone() : null;
/* 78 */     copy.width = this.width;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     Edge other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof Edge) { other = (Edge)obj; } else { return false; }
/* 87 */      return (Objects.equals(this.color, other.color) && this.width == other.width);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { this.color, Float.valueOf(this.width) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Edge.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */