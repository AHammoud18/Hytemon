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
/*     */ public class MovementEffects
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 7;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 7;
/*     */   public static final int MAX_SIZE = 7;
/*     */   public boolean disableForward;
/*     */   public boolean disableBackward;
/*     */   public boolean disableLeft;
/*     */   public boolean disableRight;
/*     */   public boolean disableSprint;
/*     */   public boolean disableJump;
/*     */   public boolean disableCrouch;
/*     */   
/*     */   public MovementEffects() {}
/*     */   
/*     */   public MovementEffects(boolean disableForward, boolean disableBackward, boolean disableLeft, boolean disableRight, boolean disableSprint, boolean disableJump, boolean disableCrouch) {
/*  32 */     this.disableForward = disableForward;
/*  33 */     this.disableBackward = disableBackward;
/*  34 */     this.disableLeft = disableLeft;
/*  35 */     this.disableRight = disableRight;
/*  36 */     this.disableSprint = disableSprint;
/*  37 */     this.disableJump = disableJump;
/*  38 */     this.disableCrouch = disableCrouch;
/*     */   }
/*     */   
/*     */   public MovementEffects(@Nonnull MovementEffects other) {
/*  42 */     this.disableForward = other.disableForward;
/*  43 */     this.disableBackward = other.disableBackward;
/*  44 */     this.disableLeft = other.disableLeft;
/*  45 */     this.disableRight = other.disableRight;
/*  46 */     this.disableSprint = other.disableSprint;
/*  47 */     this.disableJump = other.disableJump;
/*  48 */     this.disableCrouch = other.disableCrouch;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static MovementEffects deserialize(@Nonnull ByteBuf buf, int offset) {
/*  53 */     MovementEffects obj = new MovementEffects();
/*     */     
/*  55 */     obj.disableForward = (buf.getByte(offset + 0) != 0);
/*  56 */     obj.disableBackward = (buf.getByte(offset + 1) != 0);
/*  57 */     obj.disableLeft = (buf.getByte(offset + 2) != 0);
/*  58 */     obj.disableRight = (buf.getByte(offset + 3) != 0);
/*  59 */     obj.disableSprint = (buf.getByte(offset + 4) != 0);
/*  60 */     obj.disableJump = (buf.getByte(offset + 5) != 0);
/*  61 */     obj.disableCrouch = (buf.getByte(offset + 6) != 0);
/*     */ 
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     return 7;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  73 */     buf.writeByte(this.disableForward ? 1 : 0);
/*  74 */     buf.writeByte(this.disableBackward ? 1 : 0);
/*  75 */     buf.writeByte(this.disableLeft ? 1 : 0);
/*  76 */     buf.writeByte(this.disableRight ? 1 : 0);
/*  77 */     buf.writeByte(this.disableSprint ? 1 : 0);
/*  78 */     buf.writeByte(this.disableJump ? 1 : 0);
/*  79 */     buf.writeByte(this.disableCrouch ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  85 */     return 7;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  89 */     if (buffer.readableBytes() - offset < 7) {
/*  90 */       return ValidationResult.error("Buffer too small: expected at least 7 bytes");
/*     */     }
/*     */ 
/*     */     
/*  94 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MovementEffects clone() {
/*  98 */     MovementEffects copy = new MovementEffects();
/*  99 */     copy.disableForward = this.disableForward;
/* 100 */     copy.disableBackward = this.disableBackward;
/* 101 */     copy.disableLeft = this.disableLeft;
/* 102 */     copy.disableRight = this.disableRight;
/* 103 */     copy.disableSprint = this.disableSprint;
/* 104 */     copy.disableJump = this.disableJump;
/* 105 */     copy.disableCrouch = this.disableCrouch;
/* 106 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MovementEffects other;
/* 112 */     if (this == obj) return true; 
/* 113 */     if (obj instanceof MovementEffects) { other = (MovementEffects)obj; } else { return false; }
/* 114 */      return (this.disableForward == other.disableForward && this.disableBackward == other.disableBackward && this.disableLeft == other.disableLeft && this.disableRight == other.disableRight && this.disableSprint == other.disableSprint && this.disableJump == other.disableJump && this.disableCrouch == other.disableCrouch);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 119 */     return Objects.hash(new Object[] { Boolean.valueOf(this.disableForward), Boolean.valueOf(this.disableBackward), Boolean.valueOf(this.disableLeft), Boolean.valueOf(this.disableRight), Boolean.valueOf(this.disableSprint), Boolean.valueOf(this.disableJump), Boolean.valueOf(this.disableCrouch) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\MovementEffects.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */