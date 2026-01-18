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
/*     */ public class AssetEditorUpdateJsonAsset
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 323;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   
/*     */   public int getId() {
/*  25 */     return 323;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 21; public static final int MAX_SIZE = 1677721600; public int token; @Nullable
/*     */   public String assetType;
/*     */   @Nullable
/*     */   public AssetPath path;
/*  31 */   public int assetIndex = Integer.MIN_VALUE;
/*     */   
/*     */   @Nullable
/*     */   public JsonUpdateCommand[] commands;
/*     */ 
/*     */   
/*     */   public AssetEditorUpdateJsonAsset(int token, @Nullable String assetType, @Nullable AssetPath path, int assetIndex, @Nullable JsonUpdateCommand[] commands) {
/*  38 */     this.token = token;
/*  39 */     this.assetType = assetType;
/*  40 */     this.path = path;
/*  41 */     this.assetIndex = assetIndex;
/*  42 */     this.commands = commands;
/*     */   }
/*     */   
/*     */   public AssetEditorUpdateJsonAsset(@Nonnull AssetEditorUpdateJsonAsset other) {
/*  46 */     this.token = other.token;
/*  47 */     this.assetType = other.assetType;
/*  48 */     this.path = other.path;
/*  49 */     this.assetIndex = other.assetIndex;
/*  50 */     this.commands = other.commands;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorUpdateJsonAsset deserialize(@Nonnull ByteBuf buf, int offset) {
/*  55 */     AssetEditorUpdateJsonAsset obj = new AssetEditorUpdateJsonAsset();
/*  56 */     byte nullBits = buf.getByte(offset);
/*  57 */     obj.token = buf.getIntLE(offset + 1);
/*  58 */     obj.assetIndex = buf.getIntLE(offset + 5);
/*     */     
/*  60 */     if ((nullBits & 0x1) != 0) {
/*  61 */       int varPos0 = offset + 21 + buf.getIntLE(offset + 9);
/*  62 */       int assetTypeLen = VarInt.peek(buf, varPos0);
/*  63 */       if (assetTypeLen < 0) throw ProtocolException.negativeLength("AssetType", assetTypeLen); 
/*  64 */       if (assetTypeLen > 4096000) throw ProtocolException.stringTooLong("AssetType", assetTypeLen, 4096000); 
/*  65 */       obj.assetType = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  67 */     if ((nullBits & 0x2) != 0) {
/*  68 */       int varPos1 = offset + 21 + buf.getIntLE(offset + 13);
/*  69 */       obj.path = AssetPath.deserialize(buf, varPos1);
/*     */     } 
/*  71 */     if ((nullBits & 0x4) != 0) {
/*  72 */       int varPos2 = offset + 21 + buf.getIntLE(offset + 17);
/*  73 */       int commandsCount = VarInt.peek(buf, varPos2);
/*  74 */       if (commandsCount < 0) throw ProtocolException.negativeLength("Commands", commandsCount); 
/*  75 */       if (commandsCount > 4096000) throw ProtocolException.arrayTooLong("Commands", commandsCount, 4096000); 
/*  76 */       int varIntLen = VarInt.length(buf, varPos2);
/*  77 */       if ((varPos2 + varIntLen) + commandsCount * 7L > buf.readableBytes())
/*  78 */         throw ProtocolException.bufferTooSmall("Commands", varPos2 + varIntLen + commandsCount * 7, buf.readableBytes()); 
/*  79 */       obj.commands = new JsonUpdateCommand[commandsCount];
/*  80 */       int elemPos = varPos2 + varIntLen;
/*  81 */       for (int i = 0; i < commandsCount; i++) {
/*  82 */         obj.commands[i] = JsonUpdateCommand.deserialize(buf, elemPos);
/*  83 */         elemPos += JsonUpdateCommand.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  91 */     byte nullBits = buf.getByte(offset);
/*  92 */     int maxEnd = 21;
/*  93 */     if ((nullBits & 0x1) != 0) {
/*  94 */       int fieldOffset0 = buf.getIntLE(offset + 9);
/*  95 */       int pos0 = offset + 21 + fieldOffset0;
/*  96 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  97 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  99 */     if ((nullBits & 0x2) != 0) {
/* 100 */       int fieldOffset1 = buf.getIntLE(offset + 13);
/* 101 */       int pos1 = offset + 21 + fieldOffset1;
/* 102 */       pos1 += AssetPath.computeBytesConsumed(buf, pos1);
/* 103 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 105 */     if ((nullBits & 0x4) != 0) {
/* 106 */       int fieldOffset2 = buf.getIntLE(offset + 17);
/* 107 */       int pos2 = offset + 21 + fieldOffset2;
/* 108 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 109 */       for (int i = 0; i < arrLen; ) { pos2 += JsonUpdateCommand.computeBytesConsumed(buf, pos2); i++; }
/* 110 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 112 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 118 */     int startPos = buf.writerIndex();
/* 119 */     byte nullBits = 0;
/* 120 */     if (this.assetType != null) nullBits = (byte)(nullBits | 0x1); 
/* 121 */     if (this.path != null) nullBits = (byte)(nullBits | 0x2); 
/* 122 */     if (this.commands != null) nullBits = (byte)(nullBits | 0x4); 
/* 123 */     buf.writeByte(nullBits);
/*     */     
/* 125 */     buf.writeIntLE(this.token);
/* 126 */     buf.writeIntLE(this.assetIndex);
/*     */     
/* 128 */     int assetTypeOffsetSlot = buf.writerIndex();
/* 129 */     buf.writeIntLE(0);
/* 130 */     int pathOffsetSlot = buf.writerIndex();
/* 131 */     buf.writeIntLE(0);
/* 132 */     int commandsOffsetSlot = buf.writerIndex();
/* 133 */     buf.writeIntLE(0);
/*     */     
/* 135 */     int varBlockStart = buf.writerIndex();
/* 136 */     if (this.assetType != null) {
/* 137 */       buf.setIntLE(assetTypeOffsetSlot, buf.writerIndex() - varBlockStart);
/* 138 */       PacketIO.writeVarString(buf, this.assetType, 4096000);
/*     */     } else {
/* 140 */       buf.setIntLE(assetTypeOffsetSlot, -1);
/*     */     } 
/* 142 */     if (this.path != null) {
/* 143 */       buf.setIntLE(pathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 144 */       this.path.serialize(buf);
/*     */     } else {
/* 146 */       buf.setIntLE(pathOffsetSlot, -1);
/*     */     } 
/* 148 */     if (this.commands != null) {
/* 149 */       buf.setIntLE(commandsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 150 */       if (this.commands.length > 4096000) throw ProtocolException.arrayTooLong("Commands", this.commands.length, 4096000);  VarInt.write(buf, this.commands.length); for (JsonUpdateCommand item : this.commands) item.serialize(buf); 
/*     */     } else {
/* 152 */       buf.setIntLE(commandsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 158 */     int size = 21;
/* 159 */     if (this.assetType != null) size += PacketIO.stringSize(this.assetType); 
/* 160 */     if (this.path != null) size += this.path.computeSize(); 
/* 161 */     if (this.commands != null) {
/* 162 */       int commandsSize = 0;
/* 163 */       for (JsonUpdateCommand elem : this.commands) commandsSize += elem.computeSize(); 
/* 164 */       size += VarInt.size(this.commands.length) + commandsSize;
/*     */     } 
/*     */     
/* 167 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 171 */     if (buffer.readableBytes() - offset < 21) {
/* 172 */       return ValidationResult.error("Buffer too small: expected at least 21 bytes");
/*     */     }
/*     */     
/* 175 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 178 */     if ((nullBits & 0x1) != 0) {
/* 179 */       int assetTypeOffset = buffer.getIntLE(offset + 9);
/* 180 */       if (assetTypeOffset < 0) {
/* 181 */         return ValidationResult.error("Invalid offset for AssetType");
/*     */       }
/* 183 */       int pos = offset + 21 + assetTypeOffset;
/* 184 */       if (pos >= buffer.writerIndex()) {
/* 185 */         return ValidationResult.error("Offset out of bounds for AssetType");
/*     */       }
/* 187 */       int assetTypeLen = VarInt.peek(buffer, pos);
/* 188 */       if (assetTypeLen < 0) {
/* 189 */         return ValidationResult.error("Invalid string length for AssetType");
/*     */       }
/* 191 */       if (assetTypeLen > 4096000) {
/* 192 */         return ValidationResult.error("AssetType exceeds max length 4096000");
/*     */       }
/* 194 */       pos += VarInt.length(buffer, pos);
/* 195 */       pos += assetTypeLen;
/* 196 */       if (pos > buffer.writerIndex()) {
/* 197 */         return ValidationResult.error("Buffer overflow reading AssetType");
/*     */       }
/*     */     } 
/*     */     
/* 201 */     if ((nullBits & 0x2) != 0) {
/* 202 */       int pathOffset = buffer.getIntLE(offset + 13);
/* 203 */       if (pathOffset < 0) {
/* 204 */         return ValidationResult.error("Invalid offset for Path");
/*     */       }
/* 206 */       int pos = offset + 21 + pathOffset;
/* 207 */       if (pos >= buffer.writerIndex()) {
/* 208 */         return ValidationResult.error("Offset out of bounds for Path");
/*     */       }
/* 210 */       ValidationResult pathResult = AssetPath.validateStructure(buffer, pos);
/* 211 */       if (!pathResult.isValid()) {
/* 212 */         return ValidationResult.error("Invalid Path: " + pathResult.error());
/*     */       }
/* 214 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 217 */     if ((nullBits & 0x4) != 0) {
/* 218 */       int commandsOffset = buffer.getIntLE(offset + 17);
/* 219 */       if (commandsOffset < 0) {
/* 220 */         return ValidationResult.error("Invalid offset for Commands");
/*     */       }
/* 222 */       int pos = offset + 21 + commandsOffset;
/* 223 */       if (pos >= buffer.writerIndex()) {
/* 224 */         return ValidationResult.error("Offset out of bounds for Commands");
/*     */       }
/* 226 */       int commandsCount = VarInt.peek(buffer, pos);
/* 227 */       if (commandsCount < 0) {
/* 228 */         return ValidationResult.error("Invalid array count for Commands");
/*     */       }
/* 230 */       if (commandsCount > 4096000) {
/* 231 */         return ValidationResult.error("Commands exceeds max length 4096000");
/*     */       }
/* 233 */       pos += VarInt.length(buffer, pos);
/* 234 */       for (int i = 0; i < commandsCount; i++) {
/* 235 */         ValidationResult structResult = JsonUpdateCommand.validateStructure(buffer, pos);
/* 236 */         if (!structResult.isValid()) {
/* 237 */           return ValidationResult.error("Invalid JsonUpdateCommand in Commands[" + i + "]: " + structResult.error());
/*     */         }
/* 239 */         pos += JsonUpdateCommand.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 242 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorUpdateJsonAsset clone() {
/* 246 */     AssetEditorUpdateJsonAsset copy = new AssetEditorUpdateJsonAsset();
/* 247 */     copy.token = this.token;
/* 248 */     copy.assetType = this.assetType;
/* 249 */     copy.path = (this.path != null) ? this.path.clone() : null;
/* 250 */     copy.assetIndex = this.assetIndex;
/* 251 */     copy.commands = (this.commands != null) ? (JsonUpdateCommand[])Arrays.<JsonUpdateCommand>stream(this.commands).map(e -> e.clone()).toArray(x$0 -> new JsonUpdateCommand[x$0]) : null;
/* 252 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorUpdateJsonAsset other;
/* 258 */     if (this == obj) return true; 
/* 259 */     if (obj instanceof AssetEditorUpdateJsonAsset) { other = (AssetEditorUpdateJsonAsset)obj; } else { return false; }
/* 260 */      return (this.token == other.token && Objects.equals(this.assetType, other.assetType) && Objects.equals(this.path, other.path) && this.assetIndex == other.assetIndex && Arrays.equals((Object[])this.commands, (Object[])other.commands));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 265 */     int result = 1;
/* 266 */     result = 31 * result + Integer.hashCode(this.token);
/* 267 */     result = 31 * result + Objects.hashCode(this.assetType);
/* 268 */     result = 31 * result + Objects.hashCode(this.path);
/* 269 */     result = 31 * result + Integer.hashCode(this.assetIndex);
/* 270 */     result = 31 * result + Arrays.hashCode((Object[])this.commands);
/* 271 */     return result;
/*     */   }
/*     */   
/*     */   public AssetEditorUpdateJsonAsset() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorUpdateJsonAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */