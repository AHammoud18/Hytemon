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
/*    */ public class RangeVector2f
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 17;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 17;
/*    */   public static final int MAX_SIZE = 17;
/*    */   @Nullable
/*    */   public Rangef x;
/*    */   @Nullable
/*    */   public Rangef y;
/*    */   
/*    */   public RangeVector2f() {}
/*    */   
/*    */   public RangeVector2f(@Nullable Rangef x, @Nullable Rangef y) {
/* 27 */     this.x = x;
/* 28 */     this.y = y;
/*    */   }
/*    */   
/*    */   public RangeVector2f(@Nonnull RangeVector2f other) {
/* 32 */     this.x = other.x;
/* 33 */     this.y = other.y;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static RangeVector2f deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     RangeVector2f obj = new RangeVector2f();
/* 39 */     byte nullBits = buf.getByte(offset);
/* 40 */     if ((nullBits & 0x1) != 0) obj.x = Rangef.deserialize(buf, offset + 1); 
/* 41 */     if ((nullBits & 0x2) != 0) obj.y = Rangef.deserialize(buf, offset + 9);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 17;
/*    */   }
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 52 */     byte nullBits = 0;
/* 53 */     if (this.x != null) nullBits = (byte)(nullBits | 0x1); 
/* 54 */     if (this.y != null) nullBits = (byte)(nullBits | 0x2); 
/* 55 */     buf.writeByte(nullBits);
/*    */     
/* 57 */     if (this.x != null) { this.x.serialize(buf); } else { buf.writeZero(8); }
/* 58 */      if (this.y != null) { this.y.serialize(buf); } else { buf.writeZero(8); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 64 */     return 17;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 68 */     if (buffer.readableBytes() - offset < 17) {
/* 69 */       return ValidationResult.error("Buffer too small: expected at least 17 bytes");
/*    */     }
/*    */ 
/*    */     
/* 73 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public RangeVector2f clone() {
/* 77 */     RangeVector2f copy = new RangeVector2f();
/* 78 */     copy.x = (this.x != null) ? this.x.clone() : null;
/* 79 */     copy.y = (this.y != null) ? this.y.clone() : null;
/* 80 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     RangeVector2f other;
/* 86 */     if (this == obj) return true; 
/* 87 */     if (obj instanceof RangeVector2f) { other = (RangeVector2f)obj; } else { return false; }
/* 88 */      return (Objects.equals(this.x, other.x) && Objects.equals(this.y, other.y));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 93 */     return Objects.hash(new Object[] { this.x, this.y });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\RangeVector2f.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */