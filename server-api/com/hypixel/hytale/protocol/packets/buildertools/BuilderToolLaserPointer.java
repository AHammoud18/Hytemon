/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuilderToolLaserPointer
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 419;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 36;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 36;
/*     */   public static final int MAX_SIZE = 36;
/*     */   public int playerNetworkId;
/*     */   
/*     */   public int getId() {
/*  25 */     return 419;
/*     */   }
/*     */ 
/*     */   
/*     */   public float startX;
/*     */   
/*     */   public float startY;
/*     */   public float startZ;
/*     */   public float endX;
/*     */   public float endY;
/*     */   public float endZ;
/*     */   public int color;
/*     */   public int durationMs;
/*     */   
/*     */   public BuilderToolLaserPointer() {}
/*     */   
/*     */   public BuilderToolLaserPointer(int playerNetworkId, float startX, float startY, float startZ, float endX, float endY, float endZ, int color, int durationMs) {
/*  42 */     this.playerNetworkId = playerNetworkId;
/*  43 */     this.startX = startX;
/*  44 */     this.startY = startY;
/*  45 */     this.startZ = startZ;
/*  46 */     this.endX = endX;
/*  47 */     this.endY = endY;
/*  48 */     this.endZ = endZ;
/*  49 */     this.color = color;
/*  50 */     this.durationMs = durationMs;
/*     */   }
/*     */   
/*     */   public BuilderToolLaserPointer(@Nonnull BuilderToolLaserPointer other) {
/*  54 */     this.playerNetworkId = other.playerNetworkId;
/*  55 */     this.startX = other.startX;
/*  56 */     this.startY = other.startY;
/*  57 */     this.startZ = other.startZ;
/*  58 */     this.endX = other.endX;
/*  59 */     this.endY = other.endY;
/*  60 */     this.endZ = other.endZ;
/*  61 */     this.color = other.color;
/*  62 */     this.durationMs = other.durationMs;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolLaserPointer deserialize(@Nonnull ByteBuf buf, int offset) {
/*  67 */     BuilderToolLaserPointer obj = new BuilderToolLaserPointer();
/*     */     
/*  69 */     obj.playerNetworkId = buf.getIntLE(offset + 0);
/*  70 */     obj.startX = buf.getFloatLE(offset + 4);
/*  71 */     obj.startY = buf.getFloatLE(offset + 8);
/*  72 */     obj.startZ = buf.getFloatLE(offset + 12);
/*  73 */     obj.endX = buf.getFloatLE(offset + 16);
/*  74 */     obj.endY = buf.getFloatLE(offset + 20);
/*  75 */     obj.endZ = buf.getFloatLE(offset + 24);
/*  76 */     obj.color = buf.getIntLE(offset + 28);
/*  77 */     obj.durationMs = buf.getIntLE(offset + 32);
/*     */ 
/*     */     
/*  80 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  84 */     return 36;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  90 */     buf.writeIntLE(this.playerNetworkId);
/*  91 */     buf.writeFloatLE(this.startX);
/*  92 */     buf.writeFloatLE(this.startY);
/*  93 */     buf.writeFloatLE(this.startZ);
/*  94 */     buf.writeFloatLE(this.endX);
/*  95 */     buf.writeFloatLE(this.endY);
/*  96 */     buf.writeFloatLE(this.endZ);
/*  97 */     buf.writeIntLE(this.color);
/*  98 */     buf.writeIntLE(this.durationMs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 104 */     return 36;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 108 */     if (buffer.readableBytes() - offset < 36) {
/* 109 */       return ValidationResult.error("Buffer too small: expected at least 36 bytes");
/*     */     }
/*     */ 
/*     */     
/* 113 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolLaserPointer clone() {
/* 117 */     BuilderToolLaserPointer copy = new BuilderToolLaserPointer();
/* 118 */     copy.playerNetworkId = this.playerNetworkId;
/* 119 */     copy.startX = this.startX;
/* 120 */     copy.startY = this.startY;
/* 121 */     copy.startZ = this.startZ;
/* 122 */     copy.endX = this.endX;
/* 123 */     copy.endY = this.endY;
/* 124 */     copy.endZ = this.endZ;
/* 125 */     copy.color = this.color;
/* 126 */     copy.durationMs = this.durationMs;
/* 127 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolLaserPointer other;
/* 133 */     if (this == obj) return true; 
/* 134 */     if (obj instanceof BuilderToolLaserPointer) { other = (BuilderToolLaserPointer)obj; } else { return false; }
/* 135 */      return (this.playerNetworkId == other.playerNetworkId && this.startX == other.startX && this.startY == other.startY && this.startZ == other.startZ && this.endX == other.endX && this.endY == other.endY && this.endZ == other.endZ && this.color == other.color && this.durationMs == other.durationMs);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 140 */     return Objects.hash(new Object[] { Integer.valueOf(this.playerNetworkId), Float.valueOf(this.startX), Float.valueOf(this.startY), Float.valueOf(this.startZ), Float.valueOf(this.endX), Float.valueOf(this.endY), Float.valueOf(this.endZ), Integer.valueOf(this.color), Integer.valueOf(this.durationMs) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolLaserPointer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */