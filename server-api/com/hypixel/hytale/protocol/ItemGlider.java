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
/*     */ public class ItemGlider
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 16;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 16;
/*     */   public static final int MAX_SIZE = 16;
/*     */   public float terminalVelocity;
/*     */   public float fallSpeedMultiplier;
/*     */   public float horizontalSpeedMultiplier;
/*     */   public float speed;
/*     */   
/*     */   public ItemGlider() {}
/*     */   
/*     */   public ItemGlider(float terminalVelocity, float fallSpeedMultiplier, float horizontalSpeedMultiplier, float speed) {
/*  29 */     this.terminalVelocity = terminalVelocity;
/*  30 */     this.fallSpeedMultiplier = fallSpeedMultiplier;
/*  31 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  32 */     this.speed = speed;
/*     */   }
/*     */   
/*     */   public ItemGlider(@Nonnull ItemGlider other) {
/*  36 */     this.terminalVelocity = other.terminalVelocity;
/*  37 */     this.fallSpeedMultiplier = other.fallSpeedMultiplier;
/*  38 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  39 */     this.speed = other.speed;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemGlider deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     ItemGlider obj = new ItemGlider();
/*     */     
/*  46 */     obj.terminalVelocity = buf.getFloatLE(offset + 0);
/*  47 */     obj.fallSpeedMultiplier = buf.getFloatLE(offset + 4);
/*  48 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 8);
/*  49 */     obj.speed = buf.getFloatLE(offset + 12);
/*     */ 
/*     */     
/*  52 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  56 */     return 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  61 */     buf.writeFloatLE(this.terminalVelocity);
/*  62 */     buf.writeFloatLE(this.fallSpeedMultiplier);
/*  63 */     buf.writeFloatLE(this.horizontalSpeedMultiplier);
/*  64 */     buf.writeFloatLE(this.speed);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  70 */     return 16;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  74 */     if (buffer.readableBytes() - offset < 16) {
/*  75 */       return ValidationResult.error("Buffer too small: expected at least 16 bytes");
/*     */     }
/*     */ 
/*     */     
/*  79 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemGlider clone() {
/*  83 */     ItemGlider copy = new ItemGlider();
/*  84 */     copy.terminalVelocity = this.terminalVelocity;
/*  85 */     copy.fallSpeedMultiplier = this.fallSpeedMultiplier;
/*  86 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/*  87 */     copy.speed = this.speed;
/*  88 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemGlider other;
/*  94 */     if (this == obj) return true; 
/*  95 */     if (obj instanceof ItemGlider) { other = (ItemGlider)obj; } else { return false; }
/*  96 */      return (this.terminalVelocity == other.terminalVelocity && this.fallSpeedMultiplier == other.fallSpeedMultiplier && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.speed == other.speed);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     return Objects.hash(new Object[] { Float.valueOf(this.terminalVelocity), Float.valueOf(this.fallSpeedMultiplier), Float.valueOf(this.horizontalSpeedMultiplier), Float.valueOf(this.speed) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemGlider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */