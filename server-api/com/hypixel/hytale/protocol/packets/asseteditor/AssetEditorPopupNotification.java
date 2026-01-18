/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.FormattedMessage;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssetEditorPopupNotification
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 337;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  25 */     return 337;
/*     */   }
/*     */   @Nonnull
/*  28 */   public AssetEditorPopupNotificationType type = AssetEditorPopupNotificationType.Info;
/*     */   
/*     */   @Nullable
/*     */   public FormattedMessage message;
/*     */ 
/*     */   
/*     */   public AssetEditorPopupNotification(@Nonnull AssetEditorPopupNotificationType type, @Nullable FormattedMessage message) {
/*  35 */     this.type = type;
/*  36 */     this.message = message;
/*     */   }
/*     */   
/*     */   public AssetEditorPopupNotification(@Nonnull AssetEditorPopupNotification other) {
/*  40 */     this.type = other.type;
/*  41 */     this.message = other.message;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorPopupNotification deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AssetEditorPopupNotification obj = new AssetEditorPopupNotification();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.type = AssetEditorPopupNotificationType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  50 */     int pos = offset + 2;
/*  51 */     if ((nullBits & 0x1) != 0) { obj.message = FormattedMessage.deserialize(buf, pos);
/*  52 */       pos += FormattedMessage.computeBytesConsumed(buf, pos); }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 2;
/*  60 */     if ((nullBits & 0x1) != 0) pos += FormattedMessage.computeBytesConsumed(buf, pos); 
/*  61 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  67 */     byte nullBits = 0;
/*  68 */     if (this.message != null) nullBits = (byte)(nullBits | 0x1); 
/*  69 */     buf.writeByte(nullBits);
/*     */     
/*  71 */     buf.writeByte(this.type.getValue());
/*     */     
/*  73 */     if (this.message != null) this.message.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  78 */     int size = 2;
/*  79 */     if (this.message != null) size += this.message.computeSize();
/*     */     
/*  81 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  85 */     if (buffer.readableBytes() - offset < 2) {
/*  86 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */     
/*  89 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  91 */     int pos = offset + 2;
/*     */     
/*  93 */     if ((nullBits & 0x1) != 0) {
/*  94 */       ValidationResult messageResult = FormattedMessage.validateStructure(buffer, pos);
/*  95 */       if (!messageResult.isValid()) {
/*  96 */         return ValidationResult.error("Invalid Message: " + messageResult.error());
/*     */       }
/*  98 */       pos += FormattedMessage.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 100 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorPopupNotification clone() {
/* 104 */     AssetEditorPopupNotification copy = new AssetEditorPopupNotification();
/* 105 */     copy.type = this.type;
/* 106 */     copy.message = (this.message != null) ? this.message.clone() : null;
/* 107 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorPopupNotification other;
/* 113 */     if (this == obj) return true; 
/* 114 */     if (obj instanceof AssetEditorPopupNotification) { other = (AssetEditorPopupNotification)obj; } else { return false; }
/* 115 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.message, other.message));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     return Objects.hash(new Object[] { this.type, this.message });
/*     */   }
/*     */   
/*     */   public AssetEditorPopupNotification() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorPopupNotification.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */