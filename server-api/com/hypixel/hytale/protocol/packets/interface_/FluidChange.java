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
/*     */ public class FluidChange
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 17;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 17;
/*     */   public static final int MAX_SIZE = 17;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   public int fluidId;
/*     */   public byte fluidLevel;
/*     */   
/*     */   public FluidChange() {}
/*     */   
/*     */   public FluidChange(int x, int y, int z, int fluidId, byte fluidLevel) {
/*  30 */     this.x = x;
/*  31 */     this.y = y;
/*  32 */     this.z = z;
/*  33 */     this.fluidId = fluidId;
/*  34 */     this.fluidLevel = fluidLevel;
/*     */   }
/*     */   
/*     */   public FluidChange(@Nonnull FluidChange other) {
/*  38 */     this.x = other.x;
/*  39 */     this.y = other.y;
/*  40 */     this.z = other.z;
/*  41 */     this.fluidId = other.fluidId;
/*  42 */     this.fluidLevel = other.fluidLevel;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static FluidChange deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     FluidChange obj = new FluidChange();
/*     */     
/*  49 */     obj.x = buf.getIntLE(offset + 0);
/*  50 */     obj.y = buf.getIntLE(offset + 4);
/*  51 */     obj.z = buf.getIntLE(offset + 8);
/*  52 */     obj.fluidId = buf.getIntLE(offset + 12);
/*  53 */     obj.fluidLevel = buf.getByte(offset + 16);
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     return 17;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  65 */     buf.writeIntLE(this.x);
/*  66 */     buf.writeIntLE(this.y);
/*  67 */     buf.writeIntLE(this.z);
/*  68 */     buf.writeIntLE(this.fluidId);
/*  69 */     buf.writeByte(this.fluidLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  75 */     return 17;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  79 */     if (buffer.readableBytes() - offset < 17) {
/*  80 */       return ValidationResult.error("Buffer too small: expected at least 17 bytes");
/*     */     }
/*     */ 
/*     */     
/*  84 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public FluidChange clone() {
/*  88 */     FluidChange copy = new FluidChange();
/*  89 */     copy.x = this.x;
/*  90 */     copy.y = this.y;
/*  91 */     copy.z = this.z;
/*  92 */     copy.fluidId = this.fluidId;
/*  93 */     copy.fluidLevel = this.fluidLevel;
/*  94 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     FluidChange other;
/* 100 */     if (this == obj) return true; 
/* 101 */     if (obj instanceof FluidChange) { other = (FluidChange)obj; } else { return false; }
/* 102 */      return (this.x == other.x && this.y == other.y && this.z == other.z && this.fluidId == other.fluidId && this.fluidLevel == other.fluidLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     return Objects.hash(new Object[] { Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z), Integer.valueOf(this.fluidId), Byte.valueOf(this.fluidLevel) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\FluidChange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */