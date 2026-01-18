/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class UVMotion {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 19;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 19;
/*     */   public static final int MAX_SIZE = 16384024;
/*     */   @Nullable
/*     */   public String texture;
/*     */   public boolean addRandomUVOffset;
/*     */   public float speedX;
/*     */   public float speedY;
/*     */   public float scale;
/*     */   public float strength;
/*     */   @Nonnull
/*  26 */   public UVMotionCurveType strengthCurveType = UVMotionCurveType.Constant;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UVMotion(@Nullable String texture, boolean addRandomUVOffset, float speedX, float speedY, float scale, float strength, @Nonnull UVMotionCurveType strengthCurveType) {
/*  32 */     this.texture = texture;
/*  33 */     this.addRandomUVOffset = addRandomUVOffset;
/*  34 */     this.speedX = speedX;
/*  35 */     this.speedY = speedY;
/*  36 */     this.scale = scale;
/*  37 */     this.strength = strength;
/*  38 */     this.strengthCurveType = strengthCurveType;
/*     */   }
/*     */   
/*     */   public UVMotion(@Nonnull UVMotion other) {
/*  42 */     this.texture = other.texture;
/*  43 */     this.addRandomUVOffset = other.addRandomUVOffset;
/*  44 */     this.speedX = other.speedX;
/*  45 */     this.speedY = other.speedY;
/*  46 */     this.scale = other.scale;
/*  47 */     this.strength = other.strength;
/*  48 */     this.strengthCurveType = other.strengthCurveType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UVMotion deserialize(@Nonnull ByteBuf buf, int offset) {
/*  53 */     UVMotion obj = new UVMotion();
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     obj.addRandomUVOffset = (buf.getByte(offset + 1) != 0);
/*  56 */     obj.speedX = buf.getFloatLE(offset + 2);
/*  57 */     obj.speedY = buf.getFloatLE(offset + 6);
/*  58 */     obj.scale = buf.getFloatLE(offset + 10);
/*  59 */     obj.strength = buf.getFloatLE(offset + 14);
/*  60 */     obj.strengthCurveType = UVMotionCurveType.fromValue(buf.getByte(offset + 18));
/*     */     
/*  62 */     int pos = offset + 19;
/*  63 */     if ((nullBits & 0x1) != 0) { int textureLen = VarInt.peek(buf, pos);
/*  64 */       if (textureLen < 0) throw ProtocolException.negativeLength("Texture", textureLen); 
/*  65 */       if (textureLen > 4096000) throw ProtocolException.stringTooLong("Texture", textureLen, 4096000); 
/*  66 */       int textureVarLen = VarInt.length(buf, pos);
/*  67 */       obj.texture = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  68 */       pos += textureVarLen + textureLen; }
/*     */     
/*  70 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  74 */     byte nullBits = buf.getByte(offset);
/*  75 */     int pos = offset + 19;
/*  76 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  77 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  82 */     byte nullBits = 0;
/*  83 */     if (this.texture != null) nullBits = (byte)(nullBits | 0x1); 
/*  84 */     buf.writeByte(nullBits);
/*     */     
/*  86 */     buf.writeByte(this.addRandomUVOffset ? 1 : 0);
/*  87 */     buf.writeFloatLE(this.speedX);
/*  88 */     buf.writeFloatLE(this.speedY);
/*  89 */     buf.writeFloatLE(this.scale);
/*  90 */     buf.writeFloatLE(this.strength);
/*  91 */     buf.writeByte(this.strengthCurveType.getValue());
/*     */     
/*  93 */     if (this.texture != null) PacketIO.writeVarString(buf, this.texture, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  98 */     int size = 19;
/*  99 */     if (this.texture != null) size += PacketIO.stringSize(this.texture);
/*     */     
/* 101 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 105 */     if (buffer.readableBytes() - offset < 19) {
/* 106 */       return ValidationResult.error("Buffer too small: expected at least 19 bytes");
/*     */     }
/*     */     
/* 109 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 111 */     int pos = offset + 19;
/*     */     
/* 113 */     if ((nullBits & 0x1) != 0) {
/* 114 */       int textureLen = VarInt.peek(buffer, pos);
/* 115 */       if (textureLen < 0) {
/* 116 */         return ValidationResult.error("Invalid string length for Texture");
/*     */       }
/* 118 */       if (textureLen > 4096000) {
/* 119 */         return ValidationResult.error("Texture exceeds max length 4096000");
/*     */       }
/* 121 */       pos += VarInt.length(buffer, pos);
/* 122 */       pos += textureLen;
/* 123 */       if (pos > buffer.writerIndex()) {
/* 124 */         return ValidationResult.error("Buffer overflow reading Texture");
/*     */       }
/*     */     } 
/* 127 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UVMotion clone() {
/* 131 */     UVMotion copy = new UVMotion();
/* 132 */     copy.texture = this.texture;
/* 133 */     copy.addRandomUVOffset = this.addRandomUVOffset;
/* 134 */     copy.speedX = this.speedX;
/* 135 */     copy.speedY = this.speedY;
/* 136 */     copy.scale = this.scale;
/* 137 */     copy.strength = this.strength;
/* 138 */     copy.strengthCurveType = this.strengthCurveType;
/* 139 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UVMotion other;
/* 145 */     if (this == obj) return true; 
/* 146 */     if (obj instanceof UVMotion) { other = (UVMotion)obj; } else { return false; }
/* 147 */      return (Objects.equals(this.texture, other.texture) && this.addRandomUVOffset == other.addRandomUVOffset && this.speedX == other.speedX && this.speedY == other.speedY && this.scale == other.scale && this.strength == other.strength && Objects.equals(this.strengthCurveType, other.strengthCurveType));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 152 */     return Objects.hash(new Object[] { this.texture, Boolean.valueOf(this.addRandomUVOffset), Float.valueOf(this.speedX), Float.valueOf(this.speedY), Float.valueOf(this.scale), Float.valueOf(this.strength), this.strengthCurveType });
/*     */   }
/*     */   
/*     */   public UVMotion() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\UVMotion.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */