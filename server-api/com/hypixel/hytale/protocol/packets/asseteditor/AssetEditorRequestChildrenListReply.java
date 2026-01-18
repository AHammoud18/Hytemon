/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssetEditorRequestChildrenListReply
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 322;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   
/*     */   public int getId() {
/*  25 */     return 322;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 2; public static final int VARIABLE_BLOCK_START = 9; public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public AssetPath path;
/*     */   @Nullable
/*     */   public String[] childrenIds;
/*     */   
/*     */   public AssetEditorRequestChildrenListReply() {}
/*     */   
/*     */   public AssetEditorRequestChildrenListReply(@Nullable AssetPath path, @Nullable String[] childrenIds) {
/*  35 */     this.path = path;
/*  36 */     this.childrenIds = childrenIds;
/*     */   }
/*     */   
/*     */   public AssetEditorRequestChildrenListReply(@Nonnull AssetEditorRequestChildrenListReply other) {
/*  40 */     this.path = other.path;
/*  41 */     this.childrenIds = other.childrenIds;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorRequestChildrenListReply deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AssetEditorRequestChildrenListReply obj = new AssetEditorRequestChildrenListReply();
/*  47 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  51 */       obj.path = AssetPath.deserialize(buf, varPos0);
/*     */     } 
/*  53 */     if ((nullBits & 0x2) != 0) {
/*  54 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  55 */       int childrenIdsCount = VarInt.peek(buf, varPos1);
/*  56 */       if (childrenIdsCount < 0) throw ProtocolException.negativeLength("ChildrenIds", childrenIdsCount); 
/*  57 */       if (childrenIdsCount > 4096000) throw ProtocolException.arrayTooLong("ChildrenIds", childrenIdsCount, 4096000); 
/*  58 */       int varIntLen = VarInt.length(buf, varPos1);
/*  59 */       if ((varPos1 + varIntLen) + childrenIdsCount * 1L > buf.readableBytes())
/*  60 */         throw ProtocolException.bufferTooSmall("ChildrenIds", varPos1 + varIntLen + childrenIdsCount * 1, buf.readableBytes()); 
/*  61 */       obj.childrenIds = new String[childrenIdsCount];
/*  62 */       int elemPos = varPos1 + varIntLen;
/*  63 */       for (int i = 0; i < childrenIdsCount; i++) {
/*  64 */         int strLen = VarInt.peek(buf, elemPos);
/*  65 */         if (strLen < 0) throw ProtocolException.negativeLength("childrenIds[" + i + "]", strLen); 
/*  66 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("childrenIds[" + i + "]", strLen, 4096000); 
/*  67 */         int strVarLen = VarInt.length(buf, elemPos);
/*  68 */         obj.childrenIds[i] = PacketIO.readVarString(buf, elemPos);
/*  69 */         elemPos += strVarLen + strLen;
/*     */       } 
/*     */     } 
/*     */     
/*  73 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  77 */     byte nullBits = buf.getByte(offset);
/*  78 */     int maxEnd = 9;
/*  79 */     if ((nullBits & 0x1) != 0) {
/*  80 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  81 */       int pos0 = offset + 9 + fieldOffset0;
/*  82 */       pos0 += AssetPath.computeBytesConsumed(buf, pos0);
/*  83 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  85 */     if ((nullBits & 0x2) != 0) {
/*  86 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  87 */       int pos1 = offset + 9 + fieldOffset1;
/*  88 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  89 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl; i++; }
/*  90 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  92 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  98 */     int startPos = buf.writerIndex();
/*  99 */     byte nullBits = 0;
/* 100 */     if (this.path != null) nullBits = (byte)(nullBits | 0x1); 
/* 101 */     if (this.childrenIds != null) nullBits = (byte)(nullBits | 0x2); 
/* 102 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 105 */     int pathOffsetSlot = buf.writerIndex();
/* 106 */     buf.writeIntLE(0);
/* 107 */     int childrenIdsOffsetSlot = buf.writerIndex();
/* 108 */     buf.writeIntLE(0);
/*     */     
/* 110 */     int varBlockStart = buf.writerIndex();
/* 111 */     if (this.path != null) {
/* 112 */       buf.setIntLE(pathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 113 */       this.path.serialize(buf);
/*     */     } else {
/* 115 */       buf.setIntLE(pathOffsetSlot, -1);
/*     */     } 
/* 117 */     if (this.childrenIds != null) {
/* 118 */       buf.setIntLE(childrenIdsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 119 */       if (this.childrenIds.length > 4096000) throw ProtocolException.arrayTooLong("ChildrenIds", this.childrenIds.length, 4096000);  VarInt.write(buf, this.childrenIds.length); for (String item : this.childrenIds) PacketIO.writeVarString(buf, item, 4096000); 
/*     */     } else {
/* 121 */       buf.setIntLE(childrenIdsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 127 */     int size = 9;
/* 128 */     if (this.path != null) size += this.path.computeSize(); 
/* 129 */     if (this.childrenIds != null) {
/* 130 */       int childrenIdsSize = 0;
/* 131 */       for (String elem : this.childrenIds) childrenIdsSize += PacketIO.stringSize(elem); 
/* 132 */       size += VarInt.size(this.childrenIds.length) + childrenIdsSize;
/*     */     } 
/*     */     
/* 135 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 139 */     if (buffer.readableBytes() - offset < 9) {
/* 140 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 143 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 146 */     if ((nullBits & 0x1) != 0) {
/* 147 */       int pathOffset = buffer.getIntLE(offset + 1);
/* 148 */       if (pathOffset < 0) {
/* 149 */         return ValidationResult.error("Invalid offset for Path");
/*     */       }
/* 151 */       int pos = offset + 9 + pathOffset;
/* 152 */       if (pos >= buffer.writerIndex()) {
/* 153 */         return ValidationResult.error("Offset out of bounds for Path");
/*     */       }
/* 155 */       ValidationResult pathResult = AssetPath.validateStructure(buffer, pos);
/* 156 */       if (!pathResult.isValid()) {
/* 157 */         return ValidationResult.error("Invalid Path: " + pathResult.error());
/*     */       }
/* 159 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 162 */     if ((nullBits & 0x2) != 0) {
/* 163 */       int childrenIdsOffset = buffer.getIntLE(offset + 5);
/* 164 */       if (childrenIdsOffset < 0) {
/* 165 */         return ValidationResult.error("Invalid offset for ChildrenIds");
/*     */       }
/* 167 */       int pos = offset + 9 + childrenIdsOffset;
/* 168 */       if (pos >= buffer.writerIndex()) {
/* 169 */         return ValidationResult.error("Offset out of bounds for ChildrenIds");
/*     */       }
/* 171 */       int childrenIdsCount = VarInt.peek(buffer, pos);
/* 172 */       if (childrenIdsCount < 0) {
/* 173 */         return ValidationResult.error("Invalid array count for ChildrenIds");
/*     */       }
/* 175 */       if (childrenIdsCount > 4096000) {
/* 176 */         return ValidationResult.error("ChildrenIds exceeds max length 4096000");
/*     */       }
/* 178 */       pos += VarInt.length(buffer, pos);
/* 179 */       for (int i = 0; i < childrenIdsCount; i++) {
/* 180 */         int strLen = VarInt.peek(buffer, pos);
/* 181 */         if (strLen < 0) {
/* 182 */           return ValidationResult.error("Invalid string length in ChildrenIds");
/*     */         }
/* 184 */         pos += VarInt.length(buffer, pos);
/* 185 */         pos += strLen;
/* 186 */         if (pos > buffer.writerIndex()) {
/* 187 */           return ValidationResult.error("Buffer overflow reading string in ChildrenIds");
/*     */         }
/*     */       } 
/*     */     } 
/* 191 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorRequestChildrenListReply clone() {
/* 195 */     AssetEditorRequestChildrenListReply copy = new AssetEditorRequestChildrenListReply();
/* 196 */     copy.path = (this.path != null) ? this.path.clone() : null;
/* 197 */     copy.childrenIds = (this.childrenIds != null) ? Arrays.<String>copyOf(this.childrenIds, this.childrenIds.length) : null;
/* 198 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorRequestChildrenListReply other;
/* 204 */     if (this == obj) return true; 
/* 205 */     if (obj instanceof AssetEditorRequestChildrenListReply) { other = (AssetEditorRequestChildrenListReply)obj; } else { return false; }
/* 206 */      return (Objects.equals(this.path, other.path) && Arrays.equals((Object[])this.childrenIds, (Object[])other.childrenIds));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 211 */     int result = 1;
/* 212 */     result = 31 * result + Objects.hashCode(this.path);
/* 213 */     result = 31 * result + Arrays.hashCode((Object[])this.childrenIds);
/* 214 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorRequestChildrenListReply.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */