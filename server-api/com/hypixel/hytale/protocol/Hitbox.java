/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Hitbox
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 24;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 24;
/*     */   public static final int MAX_SIZE = 24;
/*     */   public float minX;
/*     */   public float minY;
/*     */   public float minZ;
/*     */   public float maxX;
/*     */   public float maxY;
/*     */   public float maxZ;
/*     */   
/*     */   public Hitbox() {}
/*     */   
/*     */   public Hitbox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
/*  31 */     this.minX = minX;
/*  32 */     this.minY = minY;
/*  33 */     this.minZ = minZ;
/*  34 */     this.maxX = maxX;
/*  35 */     this.maxY = maxY;
/*  36 */     this.maxZ = maxZ;
/*     */   }
/*     */   
/*     */   public Hitbox(@Nonnull Hitbox other) {
/*  40 */     this.minX = other.minX;
/*  41 */     this.minY = other.minY;
/*  42 */     this.minZ = other.minZ;
/*  43 */     this.maxX = other.maxX;
/*  44 */     this.maxY = other.maxY;
/*  45 */     this.maxZ = other.maxZ;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Hitbox deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     Hitbox obj = new Hitbox();
/*     */     
/*  52 */     obj.minX = buf.getFloatLE(offset + 0);
/*  53 */     obj.minY = buf.getFloatLE(offset + 4);
/*  54 */     obj.minZ = buf.getFloatLE(offset + 8);
/*  55 */     obj.maxX = buf.getFloatLE(offset + 12);
/*  56 */     obj.maxY = buf.getFloatLE(offset + 16);
/*  57 */     obj.maxZ = buf.getFloatLE(offset + 20);
/*     */ 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     return 24;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  69 */     buf.writeFloatLE(this.minX);
/*  70 */     buf.writeFloatLE(this.minY);
/*  71 */     buf.writeFloatLE(this.minZ);
/*  72 */     buf.writeFloatLE(this.maxX);
/*  73 */     buf.writeFloatLE(this.maxY);
/*  74 */     buf.writeFloatLE(this.maxZ);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  80 */     return 24;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  84 */     if (buffer.readableBytes() - offset < 24) {
/*  85 */       return ValidationResult.error("Buffer too small: expected at least 24 bytes");
/*     */     }
/*     */ 
/*     */     
/*  89 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Hitbox clone() {
/*  93 */     Hitbox copy = new Hitbox();
/*  94 */     copy.minX = this.minX;
/*  95 */     copy.minY = this.minY;
/*  96 */     copy.minZ = this.minZ;
/*  97 */     copy.maxX = this.maxX;
/*  98 */     copy.maxY = this.maxY;
/*  99 */     copy.maxZ = this.maxZ;
/* 100 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Hitbox other;
/* 106 */     if (this == obj) return true; 
/* 107 */     if (obj instanceof Hitbox) { other = (Hitbox)obj; } else { return false; }
/* 108 */      return (this.minX == other.minX && this.minY == other.minY && this.minZ == other.minZ && this.maxX == other.maxX && this.maxY == other.maxY && this.maxZ == other.maxZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return Objects.hash(new Object[] { Float.valueOf(this.minX), Float.valueOf(this.minY), Float.valueOf(this.minZ), Float.valueOf(this.maxX), Float.valueOf(this.maxY), Float.valueOf(this.maxZ) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Hitbox.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */