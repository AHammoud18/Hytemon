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
/*    */ public class AOECircleSelector
/*    */   extends Selector
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 17;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 17;
/*    */   public static final int MAX_SIZE = 17;
/*    */   public float range;
/*    */   @Nullable
/*    */   public Vector3f offset;
/*    */   
/*    */   public AOECircleSelector() {}
/*    */   
/*    */   public AOECircleSelector(float range, @Nullable Vector3f offset) {
/* 27 */     this.range = range;
/* 28 */     this.offset = offset;
/*    */   }
/*    */   
/*    */   public AOECircleSelector(@Nonnull AOECircleSelector other) {
/* 32 */     this.range = other.range;
/* 33 */     this.offset = other.offset;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AOECircleSelector deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     AOECircleSelector obj = new AOECircleSelector();
/* 39 */     byte nullBits = buf.getByte(offset);
/* 40 */     obj.range = buf.getFloatLE(offset + 1);
/* 41 */     if ((nullBits & 0x1) != 0) obj.offset = Vector3f.deserialize(buf, offset + 5);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 17;
/*    */   }
/*    */ 
/*    */   
/*    */   public int serialize(@Nonnull ByteBuf buf) {
/* 53 */     int startPos = buf.writerIndex();
/* 54 */     byte nullBits = 0;
/* 55 */     if (this.offset != null) nullBits = (byte)(nullBits | 0x1); 
/* 56 */     buf.writeByte(nullBits);
/*    */     
/* 58 */     buf.writeFloatLE(this.range);
/* 59 */     if (this.offset != null) { this.offset.serialize(buf); } else { buf.writeZero(12); }
/*    */     
/* 61 */     return buf.writerIndex() - startPos;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 67 */     return 17;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 71 */     if (buffer.readableBytes() - offset < 17) {
/* 72 */       return ValidationResult.error("Buffer too small: expected at least 17 bytes");
/*    */     }
/*    */ 
/*    */     
/* 76 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public AOECircleSelector clone() {
/* 80 */     AOECircleSelector copy = new AOECircleSelector();
/* 81 */     copy.range = this.range;
/* 82 */     copy.offset = (this.offset != null) ? this.offset.clone() : null;
/* 83 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     AOECircleSelector other;
/* 89 */     if (this == obj) return true; 
/* 90 */     if (obj instanceof AOECircleSelector) { other = (AOECircleSelector)obj; } else { return false; }
/* 91 */      return (this.range == other.range && Objects.equals(this.offset, other.offset));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 96 */     return Objects.hash(new Object[] { Float.valueOf(this.range), this.offset });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AOECircleSelector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */