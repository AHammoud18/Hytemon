/*     */ package com.hypixel.hytale.protocol.packets.interface_;
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
/*     */ public class EditorSelection
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 24;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 24;
/*     */   public static final int MAX_SIZE = 24;
/*     */   public int minX;
/*     */   public int minY;
/*     */   public int minZ;
/*     */   public int maxX;
/*     */   public int maxY;
/*     */   public int maxZ;
/*     */   
/*     */   public EditorSelection() {}
/*     */   
/*     */   public EditorSelection(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
/*  31 */     this.minX = minX;
/*  32 */     this.minY = minY;
/*  33 */     this.minZ = minZ;
/*  34 */     this.maxX = maxX;
/*  35 */     this.maxY = maxY;
/*  36 */     this.maxZ = maxZ;
/*     */   }
/*     */   
/*     */   public EditorSelection(@Nonnull EditorSelection other) {
/*  40 */     this.minX = other.minX;
/*  41 */     this.minY = other.minY;
/*  42 */     this.minZ = other.minZ;
/*  43 */     this.maxX = other.maxX;
/*  44 */     this.maxY = other.maxY;
/*  45 */     this.maxZ = other.maxZ;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EditorSelection deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     EditorSelection obj = new EditorSelection();
/*     */     
/*  52 */     obj.minX = buf.getIntLE(offset + 0);
/*  53 */     obj.minY = buf.getIntLE(offset + 4);
/*  54 */     obj.minZ = buf.getIntLE(offset + 8);
/*  55 */     obj.maxX = buf.getIntLE(offset + 12);
/*  56 */     obj.maxY = buf.getIntLE(offset + 16);
/*  57 */     obj.maxZ = buf.getIntLE(offset + 20);
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
/*  69 */     buf.writeIntLE(this.minX);
/*  70 */     buf.writeIntLE(this.minY);
/*  71 */     buf.writeIntLE(this.minZ);
/*  72 */     buf.writeIntLE(this.maxX);
/*  73 */     buf.writeIntLE(this.maxY);
/*  74 */     buf.writeIntLE(this.maxZ);
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
/*     */   public EditorSelection clone() {
/*  93 */     EditorSelection copy = new EditorSelection();
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
/*     */     EditorSelection other;
/* 106 */     if (this == obj) return true; 
/* 107 */     if (obj instanceof EditorSelection) { other = (EditorSelection)obj; } else { return false; }
/* 108 */      return (this.minX == other.minX && this.minY == other.minY && this.minZ == other.minZ && this.maxX == other.maxX && this.maxY == other.maxY && this.maxZ == other.maxZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return Objects.hash(new Object[] { Integer.valueOf(this.minX), Integer.valueOf(this.minY), Integer.valueOf(this.minZ), Integer.valueOf(this.maxX), Integer.valueOf(this.maxY), Integer.valueOf(this.maxZ) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\EditorSelection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */