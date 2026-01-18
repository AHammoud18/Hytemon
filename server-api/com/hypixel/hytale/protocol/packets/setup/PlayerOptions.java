/*     */ package com.hypixel.hytale.protocol.packets.setup;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.PlayerSkin;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class PlayerOptions
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 33;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 327680184;
/*     */   @Nullable
/*     */   public PlayerSkin skin;
/*     */   
/*     */   public int getId() {
/*  25 */     return 33;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerOptions() {}
/*     */ 
/*     */   
/*     */   public PlayerOptions(@Nullable PlayerSkin skin) {
/*  34 */     this.skin = skin;
/*     */   }
/*     */   
/*     */   public PlayerOptions(@Nonnull PlayerOptions other) {
/*  38 */     this.skin = other.skin;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PlayerOptions deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     PlayerOptions obj = new PlayerOptions();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { obj.skin = PlayerSkin.deserialize(buf, pos);
/*  48 */       pos += PlayerSkin.computeBytesConsumed(buf, pos); }
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int pos = offset + 1;
/*  56 */     if ((nullBits & 0x1) != 0) pos += PlayerSkin.computeBytesConsumed(buf, pos); 
/*  57 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  63 */     byte nullBits = 0;
/*  64 */     if (this.skin != null) nullBits = (byte)(nullBits | 0x1); 
/*  65 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  68 */     if (this.skin != null) this.skin.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  73 */     int size = 1;
/*  74 */     if (this.skin != null) size += this.skin.computeSize();
/*     */     
/*  76 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  80 */     if (buffer.readableBytes() - offset < 1) {
/*  81 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  84 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  86 */     int pos = offset + 1;
/*     */     
/*  88 */     if ((nullBits & 0x1) != 0) {
/*  89 */       ValidationResult skinResult = PlayerSkin.validateStructure(buffer, pos);
/*  90 */       if (!skinResult.isValid()) {
/*  91 */         return ValidationResult.error("Invalid Skin: " + skinResult.error());
/*     */       }
/*  93 */       pos += PlayerSkin.computeBytesConsumed(buffer, pos);
/*     */     } 
/*  95 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public PlayerOptions clone() {
/*  99 */     PlayerOptions copy = new PlayerOptions();
/* 100 */     copy.skin = (this.skin != null) ? this.skin.clone() : null;
/* 101 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     PlayerOptions other;
/* 107 */     if (this == obj) return true; 
/* 108 */     if (obj instanceof PlayerOptions) { other = (PlayerOptions)obj; } else { return false; }
/* 109 */      return Objects.equals(this.skin, other.skin);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 114 */     return Objects.hash(new Object[] { this.skin });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\setup\PlayerOptions.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */