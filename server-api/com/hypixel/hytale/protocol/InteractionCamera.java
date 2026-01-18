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
/*    */ public class InteractionCamera
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 29;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 29;
/*    */   public static final int MAX_SIZE = 29;
/*    */   public float time;
/*    */   @Nullable
/*    */   public Vector3f position;
/*    */   @Nullable
/*    */   public Direction rotation;
/*    */   
/*    */   public InteractionCamera() {}
/*    */   
/*    */   public InteractionCamera(float time, @Nullable Vector3f position, @Nullable Direction rotation) {
/* 28 */     this.time = time;
/* 29 */     this.position = position;
/* 30 */     this.rotation = rotation;
/*    */   }
/*    */   
/*    */   public InteractionCamera(@Nonnull InteractionCamera other) {
/* 34 */     this.time = other.time;
/* 35 */     this.position = other.position;
/* 36 */     this.rotation = other.rotation;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static InteractionCamera deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     InteractionCamera obj = new InteractionCamera();
/* 42 */     byte nullBits = buf.getByte(offset);
/* 43 */     obj.time = buf.getFloatLE(offset + 1);
/* 44 */     if ((nullBits & 0x1) != 0) obj.position = Vector3f.deserialize(buf, offset + 5); 
/* 45 */     if ((nullBits & 0x2) != 0) obj.rotation = Direction.deserialize(buf, offset + 17);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 29;
/*    */   }
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 56 */     byte nullBits = 0;
/* 57 */     if (this.position != null) nullBits = (byte)(nullBits | 0x1); 
/* 58 */     if (this.rotation != null) nullBits = (byte)(nullBits | 0x2); 
/* 59 */     buf.writeByte(nullBits);
/*    */     
/* 61 */     buf.writeFloatLE(this.time);
/* 62 */     if (this.position != null) { this.position.serialize(buf); } else { buf.writeZero(12); }
/* 63 */      if (this.rotation != null) { this.rotation.serialize(buf); } else { buf.writeZero(12); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 69 */     return 29;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 73 */     if (buffer.readableBytes() - offset < 29) {
/* 74 */       return ValidationResult.error("Buffer too small: expected at least 29 bytes");
/*    */     }
/*    */ 
/*    */     
/* 78 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public InteractionCamera clone() {
/* 82 */     InteractionCamera copy = new InteractionCamera();
/* 83 */     copy.time = this.time;
/* 84 */     copy.position = (this.position != null) ? this.position.clone() : null;
/* 85 */     copy.rotation = (this.rotation != null) ? this.rotation.clone() : null;
/* 86 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     InteractionCamera other;
/* 92 */     if (this == obj) return true; 
/* 93 */     if (obj instanceof InteractionCamera) { other = (InteractionCamera)obj; } else { return false; }
/* 94 */      return (this.time == other.time && Objects.equals(this.position, other.position) && Objects.equals(this.rotation, other.rotation));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 99 */     return Objects.hash(new Object[] { Float.valueOf(this.time), this.position, this.rotation });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\InteractionCamera.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */