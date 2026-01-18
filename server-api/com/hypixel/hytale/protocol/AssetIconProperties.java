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
/*    */ public class AssetIconProperties
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 25;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 25;
/*    */   public static final int MAX_SIZE = 25;
/*    */   public float scale;
/*    */   @Nullable
/*    */   public Vector2f translation;
/*    */   @Nullable
/*    */   public Vector3f rotation;
/*    */   
/*    */   public AssetIconProperties() {}
/*    */   
/*    */   public AssetIconProperties(float scale, @Nullable Vector2f translation, @Nullable Vector3f rotation) {
/* 28 */     this.scale = scale;
/* 29 */     this.translation = translation;
/* 30 */     this.rotation = rotation;
/*    */   }
/*    */   
/*    */   public AssetIconProperties(@Nonnull AssetIconProperties other) {
/* 34 */     this.scale = other.scale;
/* 35 */     this.translation = other.translation;
/* 36 */     this.rotation = other.rotation;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AssetIconProperties deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     AssetIconProperties obj = new AssetIconProperties();
/* 42 */     byte nullBits = buf.getByte(offset);
/* 43 */     obj.scale = buf.getFloatLE(offset + 1);
/* 44 */     if ((nullBits & 0x1) != 0) obj.translation = Vector2f.deserialize(buf, offset + 5); 
/* 45 */     if ((nullBits & 0x2) != 0) obj.rotation = Vector3f.deserialize(buf, offset + 13);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 25;
/*    */   }
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 56 */     byte nullBits = 0;
/* 57 */     if (this.translation != null) nullBits = (byte)(nullBits | 0x1); 
/* 58 */     if (this.rotation != null) nullBits = (byte)(nullBits | 0x2); 
/* 59 */     buf.writeByte(nullBits);
/*    */     
/* 61 */     buf.writeFloatLE(this.scale);
/* 62 */     if (this.translation != null) { this.translation.serialize(buf); } else { buf.writeZero(8); }
/* 63 */      if (this.rotation != null) { this.rotation.serialize(buf); } else { buf.writeZero(12); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 69 */     return 25;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 73 */     if (buffer.readableBytes() - offset < 25) {
/* 74 */       return ValidationResult.error("Buffer too small: expected at least 25 bytes");
/*    */     }
/*    */ 
/*    */     
/* 78 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public AssetIconProperties clone() {
/* 82 */     AssetIconProperties copy = new AssetIconProperties();
/* 83 */     copy.scale = this.scale;
/* 84 */     copy.translation = (this.translation != null) ? this.translation.clone() : null;
/* 85 */     copy.rotation = (this.rotation != null) ? this.rotation.clone() : null;
/* 86 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     AssetIconProperties other;
/* 92 */     if (this == obj) return true; 
/* 93 */     if (obj instanceof AssetIconProperties) { other = (AssetIconProperties)obj; } else { return false; }
/* 94 */      return (this.scale == other.scale && Objects.equals(this.translation, other.translation) && Objects.equals(this.rotation, other.rotation));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 99 */     return Objects.hash(new Object[] { Float.valueOf(this.scale), this.translation, this.rotation });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AssetIconProperties.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */