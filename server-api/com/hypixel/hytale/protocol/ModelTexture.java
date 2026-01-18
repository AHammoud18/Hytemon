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
/*     */ 
/*     */ public class ModelTexture
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 16384010;
/*     */   @Nullable
/*     */   public String texture;
/*     */   public float weight;
/*     */   
/*     */   public ModelTexture() {}
/*     */   
/*     */   public ModelTexture(@Nullable String texture, float weight) {
/*  27 */     this.texture = texture;
/*  28 */     this.weight = weight;
/*     */   }
/*     */   
/*     */   public ModelTexture(@Nonnull ModelTexture other) {
/*  32 */     this.texture = other.texture;
/*  33 */     this.weight = other.weight;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ModelTexture deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     ModelTexture obj = new ModelTexture();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     obj.weight = buf.getFloatLE(offset + 1);
/*     */     
/*  42 */     int pos = offset + 5;
/*  43 */     if ((nullBits & 0x1) != 0) { int textureLen = VarInt.peek(buf, pos);
/*  44 */       if (textureLen < 0) throw ProtocolException.negativeLength("Texture", textureLen); 
/*  45 */       if (textureLen > 4096000) throw ProtocolException.stringTooLong("Texture", textureLen, 4096000); 
/*  46 */       int textureVarLen = VarInt.length(buf, pos);
/*  47 */       obj.texture = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  48 */       pos += textureVarLen + textureLen; }
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int pos = offset + 5;
/*  56 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  57 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  62 */     byte nullBits = 0;
/*  63 */     if (this.texture != null) nullBits = (byte)(nullBits | 0x1); 
/*  64 */     buf.writeByte(nullBits);
/*     */     
/*  66 */     buf.writeFloatLE(this.weight);
/*     */     
/*  68 */     if (this.texture != null) PacketIO.writeVarString(buf, this.texture, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  73 */     int size = 5;
/*  74 */     if (this.texture != null) size += PacketIO.stringSize(this.texture);
/*     */     
/*  76 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  80 */     if (buffer.readableBytes() - offset < 5) {
/*  81 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/*  84 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  86 */     int pos = offset + 5;
/*     */     
/*  88 */     if ((nullBits & 0x1) != 0) {
/*  89 */       int textureLen = VarInt.peek(buffer, pos);
/*  90 */       if (textureLen < 0) {
/*  91 */         return ValidationResult.error("Invalid string length for Texture");
/*     */       }
/*  93 */       if (textureLen > 4096000) {
/*  94 */         return ValidationResult.error("Texture exceeds max length 4096000");
/*     */       }
/*  96 */       pos += VarInt.length(buffer, pos);
/*  97 */       pos += textureLen;
/*  98 */       if (pos > buffer.writerIndex()) {
/*  99 */         return ValidationResult.error("Buffer overflow reading Texture");
/*     */       }
/*     */     } 
/* 102 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ModelTexture clone() {
/* 106 */     ModelTexture copy = new ModelTexture();
/* 107 */     copy.texture = this.texture;
/* 108 */     copy.weight = this.weight;
/* 109 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ModelTexture other;
/* 115 */     if (this == obj) return true; 
/* 116 */     if (obj instanceof ModelTexture) { other = (ModelTexture)obj; } else { return false; }
/* 117 */      return (Objects.equals(this.texture, other.texture) && this.weight == other.weight);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return Objects.hash(new Object[] { this.texture, Float.valueOf(this.weight) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ModelTexture.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */