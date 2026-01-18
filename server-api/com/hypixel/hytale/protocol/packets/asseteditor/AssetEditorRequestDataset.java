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
/*     */ public class AssetEditorRequestDataset implements Packet {
/*     */   public static final int PACKET_ID = 333;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 16384006;
/*     */   @Nullable
/*     */   public String name;
/*     */   
/*     */   public int getId() {
/*  25 */     return 333;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorRequestDataset() {}
/*     */ 
/*     */   
/*     */   public AssetEditorRequestDataset(@Nullable String name) {
/*  34 */     this.name = name;
/*     */   }
/*     */   
/*     */   public AssetEditorRequestDataset(@Nonnull AssetEditorRequestDataset other) {
/*  38 */     this.name = other.name;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorRequestDataset deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     AssetEditorRequestDataset obj = new AssetEditorRequestDataset();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int nameLen = VarInt.peek(buf, pos);
/*  48 */       if (nameLen < 0) throw ProtocolException.negativeLength("Name", nameLen); 
/*  49 */       if (nameLen > 4096000) throw ProtocolException.stringTooLong("Name", nameLen, 4096000); 
/*  50 */       int nameVarLen = VarInt.length(buf, pos);
/*  51 */       obj.name = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  52 */       pos += nameVarLen + nameLen; }
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
/*  68 */     if (this.name != null) nullBits = (byte)(nullBits | 0x1); 
/*  69 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  72 */     if (this.name != null) PacketIO.writeVarString(buf, this.name, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  77 */     int size = 1;
/*  78 */     if (this.name != null) size += PacketIO.stringSize(this.name);
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
/*  93 */       int nameLen = VarInt.peek(buffer, pos);
/*  94 */       if (nameLen < 0) {
/*  95 */         return ValidationResult.error("Invalid string length for Name");
/*     */       }
/*  97 */       if (nameLen > 4096000) {
/*  98 */         return ValidationResult.error("Name exceeds max length 4096000");
/*     */       }
/* 100 */       pos += VarInt.length(buffer, pos);
/* 101 */       pos += nameLen;
/* 102 */       if (pos > buffer.writerIndex()) {
/* 103 */         return ValidationResult.error("Buffer overflow reading Name");
/*     */       }
/*     */     } 
/* 106 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorRequestDataset clone() {
/* 110 */     AssetEditorRequestDataset copy = new AssetEditorRequestDataset();
/* 111 */     copy.name = this.name;
/* 112 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorRequestDataset other;
/* 118 */     if (this == obj) return true; 
/* 119 */     if (obj instanceof AssetEditorRequestDataset) { other = (AssetEditorRequestDataset)obj; } else { return false; }
/* 120 */      return Objects.equals(this.name, other.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 125 */     return Objects.hash(new Object[] { this.name });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorRequestDataset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */