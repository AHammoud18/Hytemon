/*     */ package com.hypixel.hytale.protocol;
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
/*     */ public class ViewBobbing
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 565248085;
/*     */   @Nullable
/*     */   public CameraShakeConfig firstPerson;
/*     */   
/*     */   public ViewBobbing() {}
/*     */   
/*     */   public ViewBobbing(@Nullable CameraShakeConfig firstPerson) {
/*  26 */     this.firstPerson = firstPerson;
/*     */   }
/*     */   
/*     */   public ViewBobbing(@Nonnull ViewBobbing other) {
/*  30 */     this.firstPerson = other.firstPerson;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ViewBobbing deserialize(@Nonnull ByteBuf buf, int offset) {
/*  35 */     ViewBobbing obj = new ViewBobbing();
/*  36 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  38 */     int pos = offset + 1;
/*  39 */     if ((nullBits & 0x1) != 0) { obj.firstPerson = CameraShakeConfig.deserialize(buf, pos);
/*  40 */       pos += CameraShakeConfig.computeBytesConsumed(buf, pos); }
/*     */     
/*  42 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  46 */     byte nullBits = buf.getByte(offset);
/*  47 */     int pos = offset + 1;
/*  48 */     if ((nullBits & 0x1) != 0) pos += CameraShakeConfig.computeBytesConsumed(buf, pos); 
/*  49 */     return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  54 */     byte nullBits = 0;
/*  55 */     if (this.firstPerson != null) nullBits = (byte)(nullBits | 0x1); 
/*  56 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  59 */     if (this.firstPerson != null) this.firstPerson.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  64 */     int size = 1;
/*  65 */     if (this.firstPerson != null) size += this.firstPerson.computeSize();
/*     */     
/*  67 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  71 */     if (buffer.readableBytes() - offset < 1) {
/*  72 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  75 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  77 */     int pos = offset + 1;
/*     */     
/*  79 */     if ((nullBits & 0x1) != 0) {
/*  80 */       ValidationResult firstPersonResult = CameraShakeConfig.validateStructure(buffer, pos);
/*  81 */       if (!firstPersonResult.isValid()) {
/*  82 */         return ValidationResult.error("Invalid FirstPerson: " + firstPersonResult.error());
/*     */       }
/*  84 */       pos += CameraShakeConfig.computeBytesConsumed(buffer, pos);
/*     */     } 
/*  86 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ViewBobbing clone() {
/*  90 */     ViewBobbing copy = new ViewBobbing();
/*  91 */     copy.firstPerson = (this.firstPerson != null) ? this.firstPerson.clone() : null;
/*  92 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ViewBobbing other;
/*  98 */     if (this == obj) return true; 
/*  99 */     if (obj instanceof ViewBobbing) { other = (ViewBobbing)obj; } else { return false; }
/* 100 */      return Objects.equals(this.firstPerson, other.firstPerson);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 105 */     return Objects.hash(new Object[] { this.firstPerson });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ViewBobbing.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */