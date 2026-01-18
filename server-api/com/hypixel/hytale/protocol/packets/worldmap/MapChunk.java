/*     */ package com.hypixel.hytale.protocol.packets.worldmap;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapChunk
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 16384023;
/*     */   public int chunkX;
/*     */   public int chunkZ;
/*     */   @Nullable
/*     */   public MapImage image;
/*     */   
/*     */   public MapChunk() {}
/*     */   
/*     */   public MapChunk(int chunkX, int chunkZ, @Nullable MapImage image) {
/*  28 */     this.chunkX = chunkX;
/*  29 */     this.chunkZ = chunkZ;
/*  30 */     this.image = image;
/*     */   }
/*     */   
/*     */   public MapChunk(@Nonnull MapChunk other) {
/*  34 */     this.chunkX = other.chunkX;
/*  35 */     this.chunkZ = other.chunkZ;
/*  36 */     this.image = other.image;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static MapChunk deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     MapChunk obj = new MapChunk();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.chunkX = buf.getIntLE(offset + 1);
/*  44 */     obj.chunkZ = buf.getIntLE(offset + 5);
/*     */     
/*  46 */     int pos = offset + 9;
/*  47 */     if ((nullBits & 0x1) != 0) { obj.image = MapImage.deserialize(buf, pos);
/*  48 */       pos += MapImage.computeBytesConsumed(buf, pos); }
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int pos = offset + 9;
/*  56 */     if ((nullBits & 0x1) != 0) pos += MapImage.computeBytesConsumed(buf, pos); 
/*  57 */     return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  62 */     byte nullBits = 0;
/*  63 */     if (this.image != null) nullBits = (byte)(nullBits | 0x1); 
/*  64 */     buf.writeByte(nullBits);
/*     */     
/*  66 */     buf.writeIntLE(this.chunkX);
/*  67 */     buf.writeIntLE(this.chunkZ);
/*     */     
/*  69 */     if (this.image != null) this.image.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  74 */     int size = 9;
/*  75 */     if (this.image != null) size += this.image.computeSize();
/*     */     
/*  77 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  81 */     if (buffer.readableBytes() - offset < 9) {
/*  82 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/*  85 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  87 */     int pos = offset + 9;
/*     */     
/*  89 */     if ((nullBits & 0x1) != 0) {
/*  90 */       ValidationResult imageResult = MapImage.validateStructure(buffer, pos);
/*  91 */       if (!imageResult.isValid()) {
/*  92 */         return ValidationResult.error("Invalid Image: " + imageResult.error());
/*     */       }
/*  94 */       pos += MapImage.computeBytesConsumed(buffer, pos);
/*     */     } 
/*  96 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MapChunk clone() {
/* 100 */     MapChunk copy = new MapChunk();
/* 101 */     copy.chunkX = this.chunkX;
/* 102 */     copy.chunkZ = this.chunkZ;
/* 103 */     copy.image = (this.image != null) ? this.image.clone() : null;
/* 104 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MapChunk other;
/* 110 */     if (this == obj) return true; 
/* 111 */     if (obj instanceof MapChunk) { other = (MapChunk)obj; } else { return false; }
/* 112 */      return (this.chunkX == other.chunkX && this.chunkZ == other.chunkZ && Objects.equals(this.image, other.image));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 117 */     return Objects.hash(new Object[] { Integer.valueOf(this.chunkX), Integer.valueOf(this.chunkZ), this.image });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\worldmap\MapChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */