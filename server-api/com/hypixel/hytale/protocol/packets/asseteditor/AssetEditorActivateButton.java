/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AssetEditorActivateButton implements Packet {
/*     */   public static final int PACKET_ID = 335;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 16384006;
/*     */   @Nullable
/*     */   public String buttonId;
/*     */   
/*     */   public int getId() {
/*  25 */     return 335;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorActivateButton() {}
/*     */ 
/*     */   
/*     */   public AssetEditorActivateButton(@Nullable String buttonId) {
/*  34 */     this.buttonId = buttonId;
/*     */   }
/*     */   
/*     */   public AssetEditorActivateButton(@Nonnull AssetEditorActivateButton other) {
/*  38 */     this.buttonId = other.buttonId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorActivateButton deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     AssetEditorActivateButton obj = new AssetEditorActivateButton();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int buttonIdLen = VarInt.peek(buf, pos);
/*  48 */       if (buttonIdLen < 0) throw ProtocolException.negativeLength("ButtonId", buttonIdLen); 
/*  49 */       if (buttonIdLen > 4096000) throw ProtocolException.stringTooLong("ButtonId", buttonIdLen, 4096000); 
/*  50 */       int buttonIdVarLen = VarInt.length(buf, pos);
/*  51 */       obj.buttonId = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  52 */       pos += buttonIdVarLen + buttonIdLen; }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 1;
/*  60 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  61 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  67 */     byte nullBits = 0;
/*  68 */     if (this.buttonId != null) nullBits = (byte)(nullBits | 0x1); 
/*  69 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  72 */     if (this.buttonId != null) PacketIO.writeVarString(buf, this.buttonId, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  77 */     int size = 1;
/*  78 */     if (this.buttonId != null) size += PacketIO.stringSize(this.buttonId);
/*     */     
/*  80 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  84 */     if (buffer.readableBytes() - offset < 1) {
/*  85 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  88 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  90 */     int pos = offset + 1;
/*     */     
/*  92 */     if ((nullBits & 0x1) != 0) {
/*  93 */       int buttonIdLen = VarInt.peek(buffer, pos);
/*  94 */       if (buttonIdLen < 0) {
/*  95 */         return ValidationResult.error("Invalid string length for ButtonId");
/*     */       }
/*  97 */       if (buttonIdLen > 4096000) {
/*  98 */         return ValidationResult.error("ButtonId exceeds max length 4096000");
/*     */       }
/* 100 */       pos += VarInt.length(buffer, pos);
/* 101 */       pos += buttonIdLen;
/* 102 */       if (pos > buffer.writerIndex()) {
/* 103 */         return ValidationResult.error("Buffer overflow reading ButtonId");
/*     */       }
/*     */     } 
/* 106 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorActivateButton clone() {
/* 110 */     AssetEditorActivateButton copy = new AssetEditorActivateButton();
/* 111 */     copy.buttonId = this.buttonId;
/* 112 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorActivateButton other;
/* 118 */     if (this == obj) return true; 
/* 119 */     if (obj instanceof AssetEditorActivateButton) { other = (AssetEditorActivateButton)obj; } else { return false; }
/* 120 */      return Objects.equals(this.buttonId, other.buttonId);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 125 */     return Objects.hash(new Object[] { this.buttonId });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorActivateButton.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */