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
/*    */ public class AppliedForce
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 18;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 18;
/*    */   public static final int MAX_SIZE = 18;
/*    */   @Nullable
/*    */   public Vector3f direction;
/*    */   public boolean adjustVertical;
/*    */   public float force;
/*    */   
/*    */   public AppliedForce() {}
/*    */   
/*    */   public AppliedForce(@Nullable Vector3f direction, boolean adjustVertical, float force) {
/* 28 */     this.direction = direction;
/* 29 */     this.adjustVertical = adjustVertical;
/* 30 */     this.force = force;
/*    */   }
/*    */   
/*    */   public AppliedForce(@Nonnull AppliedForce other) {
/* 34 */     this.direction = other.direction;
/* 35 */     this.adjustVertical = other.adjustVertical;
/* 36 */     this.force = other.force;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AppliedForce deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     AppliedForce obj = new AppliedForce();
/* 42 */     byte nullBits = buf.getByte(offset);
/* 43 */     if ((nullBits & 0x1) != 0) obj.direction = Vector3f.deserialize(buf, offset + 1); 
/* 44 */     obj.adjustVertical = (buf.getByte(offset + 13) != 0);
/* 45 */     obj.force = buf.getFloatLE(offset + 14);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 18;
/*    */   }
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 56 */     byte nullBits = 0;
/* 57 */     if (this.direction != null) nullBits = (byte)(nullBits | 0x1); 
/* 58 */     buf.writeByte(nullBits);
/*    */     
/* 60 */     if (this.direction != null) { this.direction.serialize(buf); } else { buf.writeZero(12); }
/* 61 */      buf.writeByte(this.adjustVertical ? 1 : 0);
/* 62 */     buf.writeFloatLE(this.force);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 68 */     return 18;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 72 */     if (buffer.readableBytes() - offset < 18) {
/* 73 */       return ValidationResult.error("Buffer too small: expected at least 18 bytes");
/*    */     }
/*    */ 
/*    */     
/* 77 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public AppliedForce clone() {
/* 81 */     AppliedForce copy = new AppliedForce();
/* 82 */     copy.direction = (this.direction != null) ? this.direction.clone() : null;
/* 83 */     copy.adjustVertical = this.adjustVertical;
/* 84 */     copy.force = this.force;
/* 85 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     AppliedForce other;
/* 91 */     if (this == obj) return true; 
/* 92 */     if (obj instanceof AppliedForce) { other = (AppliedForce)obj; } else { return false; }
/* 93 */      return (Objects.equals(this.direction, other.direction) && this.adjustVertical == other.adjustVertical && this.force == other.force);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return Objects.hash(new Object[] { this.direction, Boolean.valueOf(this.adjustVertical), Float.valueOf(this.force) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AppliedForce.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */