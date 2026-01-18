/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class BuilderToolSelectionUpdate implements Packet {
/*     */   public static final int PACKET_ID = 409;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 24;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 24;
/*     */   public static final int MAX_SIZE = 24;
/*     */   public int xMin;
/*     */   public int yMin;
/*     */   public int zMin;
/*     */   public int xMax;
/*     */   public int yMax;
/*     */   public int zMax;
/*     */   
/*     */   public int getId() {
/*  25 */     return 409;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderToolSelectionUpdate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderToolSelectionUpdate(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
/*  39 */     this.xMin = xMin;
/*  40 */     this.yMin = yMin;
/*  41 */     this.zMin = zMin;
/*  42 */     this.xMax = xMax;
/*  43 */     this.yMax = yMax;
/*  44 */     this.zMax = zMax;
/*     */   }
/*     */   
/*     */   public BuilderToolSelectionUpdate(@Nonnull BuilderToolSelectionUpdate other) {
/*  48 */     this.xMin = other.xMin;
/*  49 */     this.yMin = other.yMin;
/*  50 */     this.zMin = other.zMin;
/*  51 */     this.xMax = other.xMax;
/*  52 */     this.yMax = other.yMax;
/*  53 */     this.zMax = other.zMax;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolSelectionUpdate deserialize(@Nonnull ByteBuf buf, int offset) {
/*  58 */     BuilderToolSelectionUpdate obj = new BuilderToolSelectionUpdate();
/*     */     
/*  60 */     obj.xMin = buf.getIntLE(offset + 0);
/*  61 */     obj.yMin = buf.getIntLE(offset + 4);
/*  62 */     obj.zMin = buf.getIntLE(offset + 8);
/*  63 */     obj.xMax = buf.getIntLE(offset + 12);
/*  64 */     obj.yMax = buf.getIntLE(offset + 16);
/*  65 */     obj.zMax = buf.getIntLE(offset + 20);
/*     */ 
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     return 24;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  78 */     buf.writeIntLE(this.xMin);
/*  79 */     buf.writeIntLE(this.yMin);
/*  80 */     buf.writeIntLE(this.zMin);
/*  81 */     buf.writeIntLE(this.xMax);
/*  82 */     buf.writeIntLE(this.yMax);
/*  83 */     buf.writeIntLE(this.zMax);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  89 */     return 24;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  93 */     if (buffer.readableBytes() - offset < 24) {
/*  94 */       return ValidationResult.error("Buffer too small: expected at least 24 bytes");
/*     */     }
/*     */ 
/*     */     
/*  98 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolSelectionUpdate clone() {
/* 102 */     BuilderToolSelectionUpdate copy = new BuilderToolSelectionUpdate();
/* 103 */     copy.xMin = this.xMin;
/* 104 */     copy.yMin = this.yMin;
/* 105 */     copy.zMin = this.zMin;
/* 106 */     copy.xMax = this.xMax;
/* 107 */     copy.yMax = this.yMax;
/* 108 */     copy.zMax = this.zMax;
/* 109 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolSelectionUpdate other;
/* 115 */     if (this == obj) return true; 
/* 116 */     if (obj instanceof BuilderToolSelectionUpdate) { other = (BuilderToolSelectionUpdate)obj; } else { return false; }
/* 117 */      return (this.xMin == other.xMin && this.yMin == other.yMin && this.zMin == other.zMin && this.xMax == other.xMax && this.yMax == other.yMax && this.zMax == other.zMax);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return Objects.hash(new Object[] { Integer.valueOf(this.xMin), Integer.valueOf(this.yMin), Integer.valueOf(this.zMin), Integer.valueOf(this.xMax), Integer.valueOf(this.yMax), Integer.valueOf(this.zMax) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolSelectionUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */