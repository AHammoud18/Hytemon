/*     */ package com.hypixel.hytale.protocol.packets.player;
/*     */ import com.hypixel.hytale.protocol.DamageCause;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Vector3d;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class DamageInfo implements Packet {
/*     */   public static final int PACKET_ID = 112;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 29;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 29;
/*     */   public static final int MAX_SIZE = 32768048;
/*     */   @Nullable
/*     */   public Vector3d damageSourcePosition;
/*     */   public float damageAmount;
/*     */   @Nullable
/*     */   public DamageCause damageCause;
/*     */   
/*     */   public int getId() {
/*  26 */     return 112;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DamageInfo() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public DamageInfo(@Nullable Vector3d damageSourcePosition, float damageAmount, @Nullable DamageCause damageCause) {
/*  37 */     this.damageSourcePosition = damageSourcePosition;
/*  38 */     this.damageAmount = damageAmount;
/*  39 */     this.damageCause = damageCause;
/*     */   }
/*     */   
/*     */   public DamageInfo(@Nonnull DamageInfo other) {
/*  43 */     this.damageSourcePosition = other.damageSourcePosition;
/*  44 */     this.damageAmount = other.damageAmount;
/*  45 */     this.damageCause = other.damageCause;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DamageInfo deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     DamageInfo obj = new DamageInfo();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     if ((nullBits & 0x1) != 0) obj.damageSourcePosition = Vector3d.deserialize(buf, offset + 1); 
/*  53 */     obj.damageAmount = buf.getFloatLE(offset + 25);
/*     */     
/*  55 */     int pos = offset + 29;
/*  56 */     if ((nullBits & 0x2) != 0) { obj.damageCause = DamageCause.deserialize(buf, pos);
/*  57 */       pos += DamageCause.computeBytesConsumed(buf, pos); }
/*     */     
/*  59 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  63 */     byte nullBits = buf.getByte(offset);
/*  64 */     int pos = offset + 29;
/*  65 */     if ((nullBits & 0x2) != 0) pos += DamageCause.computeBytesConsumed(buf, pos); 
/*  66 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  72 */     byte nullBits = 0;
/*  73 */     if (this.damageSourcePosition != null) nullBits = (byte)(nullBits | 0x1); 
/*  74 */     if (this.damageCause != null) nullBits = (byte)(nullBits | 0x2); 
/*  75 */     buf.writeByte(nullBits);
/*     */     
/*  77 */     if (this.damageSourcePosition != null) { this.damageSourcePosition.serialize(buf); } else { buf.writeZero(24); }
/*  78 */      buf.writeFloatLE(this.damageAmount);
/*     */     
/*  80 */     if (this.damageCause != null) this.damageCause.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  85 */     int size = 29;
/*  86 */     if (this.damageCause != null) size += this.damageCause.computeSize();
/*     */     
/*  88 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  92 */     if (buffer.readableBytes() - offset < 29) {
/*  93 */       return ValidationResult.error("Buffer too small: expected at least 29 bytes");
/*     */     }
/*     */     
/*  96 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  98 */     int pos = offset + 29;
/*     */     
/* 100 */     if ((nullBits & 0x2) != 0) {
/* 101 */       ValidationResult damageCauseResult = DamageCause.validateStructure(buffer, pos);
/* 102 */       if (!damageCauseResult.isValid()) {
/* 103 */         return ValidationResult.error("Invalid DamageCause: " + damageCauseResult.error());
/*     */       }
/* 105 */       pos += DamageCause.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 107 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public DamageInfo clone() {
/* 111 */     DamageInfo copy = new DamageInfo();
/* 112 */     copy.damageSourcePosition = (this.damageSourcePosition != null) ? this.damageSourcePosition.clone() : null;
/* 113 */     copy.damageAmount = this.damageAmount;
/* 114 */     copy.damageCause = (this.damageCause != null) ? this.damageCause.clone() : null;
/* 115 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     DamageInfo other;
/* 121 */     if (this == obj) return true; 
/* 122 */     if (obj instanceof DamageInfo) { other = (DamageInfo)obj; } else { return false; }
/* 123 */      return (Objects.equals(this.damageSourcePosition, other.damageSourcePosition) && this.damageAmount == other.damageAmount && Objects.equals(this.damageCause, other.damageCause));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 128 */     return Objects.hash(new Object[] { this.damageSourcePosition, Float.valueOf(this.damageAmount), this.damageCause });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\player\DamageInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */