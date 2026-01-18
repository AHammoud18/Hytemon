/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
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
/*     */ 
/*     */ public class AssetEditorJsonAssetUpdated
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 325;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   
/*     */   public int getId() {
/*  25 */     return 325;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 2; public static final int VARIABLE_BLOCK_START = 9; public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public AssetPath path;
/*     */   @Nullable
/*     */   public JsonUpdateCommand[] commands;
/*     */   
/*     */   public AssetEditorJsonAssetUpdated() {}
/*     */   
/*     */   public AssetEditorJsonAssetUpdated(@Nullable AssetPath path, @Nullable JsonUpdateCommand[] commands) {
/*  35 */     this.path = path;
/*  36 */     this.commands = commands;
/*     */   }
/*     */   
/*     */   public AssetEditorJsonAssetUpdated(@Nonnull AssetEditorJsonAssetUpdated other) {
/*  40 */     this.path = other.path;
/*  41 */     this.commands = other.commands;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorJsonAssetUpdated deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AssetEditorJsonAssetUpdated obj = new AssetEditorJsonAssetUpdated();
/*  47 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  51 */       obj.path = AssetPath.deserialize(buf, varPos0);
/*     */     } 
/*  53 */     if ((nullBits & 0x2) != 0) {
/*  54 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  55 */       int commandsCount = VarInt.peek(buf, varPos1);
/*  56 */       if (commandsCount < 0) throw ProtocolException.negativeLength("Commands", commandsCount); 
/*  57 */       if (commandsCount > 4096000) throw ProtocolException.arrayTooLong("Commands", commandsCount, 4096000); 
/*  58 */       int varIntLen = VarInt.length(buf, varPos1);
/*  59 */       if ((varPos1 + varIntLen) + commandsCount * 7L > buf.readableBytes())
/*  60 */         throw ProtocolException.bufferTooSmall("Commands", varPos1 + varIntLen + commandsCount * 7, buf.readableBytes()); 
/*  61 */       obj.commands = new JsonUpdateCommand[commandsCount];
/*  62 */       int elemPos = varPos1 + varIntLen;
/*  63 */       for (int i = 0; i < commandsCount; i++) {
/*  64 */         obj.commands[i] = JsonUpdateCommand.deserialize(buf, elemPos);
/*  65 */         elemPos += JsonUpdateCommand.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  73 */     byte nullBits = buf.getByte(offset);
/*  74 */     int maxEnd = 9;
/*  75 */     if ((nullBits & 0x1) != 0) {
/*  76 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  77 */       int pos0 = offset + 9 + fieldOffset0;
/*  78 */       pos0 += AssetPath.computeBytesConsumed(buf, pos0);
/*  79 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  81 */     if ((nullBits & 0x2) != 0) {
/*  82 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  83 */       int pos1 = offset + 9 + fieldOffset1;
/*  84 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  85 */       for (int i = 0; i < arrLen; ) { pos1 += JsonUpdateCommand.computeBytesConsumed(buf, pos1); i++; }
/*  86 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  88 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  94 */     int startPos = buf.writerIndex();
/*  95 */     byte nullBits = 0;
/*  96 */     if (this.path != null) nullBits = (byte)(nullBits | 0x1); 
/*  97 */     if (this.commands != null) nullBits = (byte)(nullBits | 0x2); 
/*  98 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 101 */     int pathOffsetSlot = buf.writerIndex();
/* 102 */     buf.writeIntLE(0);
/* 103 */     int commandsOffsetSlot = buf.writerIndex();
/* 104 */     buf.writeIntLE(0);
/*     */     
/* 106 */     int varBlockStart = buf.writerIndex();
/* 107 */     if (this.path != null) {
/* 108 */       buf.setIntLE(pathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 109 */       this.path.serialize(buf);
/*     */     } else {
/* 111 */       buf.setIntLE(pathOffsetSlot, -1);
/*     */     } 
/* 113 */     if (this.commands != null) {
/* 114 */       buf.setIntLE(commandsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 115 */       if (this.commands.length > 4096000) throw ProtocolException.arrayTooLong("Commands", this.commands.length, 4096000);  VarInt.write(buf, this.commands.length); for (JsonUpdateCommand item : this.commands) item.serialize(buf); 
/*     */     } else {
/* 117 */       buf.setIntLE(commandsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 123 */     int size = 9;
/* 124 */     if (this.path != null) size += this.path.computeSize(); 
/* 125 */     if (this.commands != null) {
/* 126 */       int commandsSize = 0;
/* 127 */       for (JsonUpdateCommand elem : this.commands) commandsSize += elem.computeSize(); 
/* 128 */       size += VarInt.size(this.commands.length) + commandsSize;
/*     */     } 
/*     */     
/* 131 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 135 */     if (buffer.readableBytes() - offset < 9) {
/* 136 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 139 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 142 */     if ((nullBits & 0x1) != 0) {
/* 143 */       int pathOffset = buffer.getIntLE(offset + 1);
/* 144 */       if (pathOffset < 0) {
/* 145 */         return ValidationResult.error("Invalid offset for Path");
/*     */       }
/* 147 */       int pos = offset + 9 + pathOffset;
/* 148 */       if (pos >= buffer.writerIndex()) {
/* 149 */         return ValidationResult.error("Offset out of bounds for Path");
/*     */       }
/* 151 */       ValidationResult pathResult = AssetPath.validateStructure(buffer, pos);
/* 152 */       if (!pathResult.isValid()) {
/* 153 */         return ValidationResult.error("Invalid Path: " + pathResult.error());
/*     */       }
/* 155 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 158 */     if ((nullBits & 0x2) != 0) {
/* 159 */       int commandsOffset = buffer.getIntLE(offset + 5);
/* 160 */       if (commandsOffset < 0) {
/* 161 */         return ValidationResult.error("Invalid offset for Commands");
/*     */       }
/* 163 */       int pos = offset + 9 + commandsOffset;
/* 164 */       if (pos >= buffer.writerIndex()) {
/* 165 */         return ValidationResult.error("Offset out of bounds for Commands");
/*     */       }
/* 167 */       int commandsCount = VarInt.peek(buffer, pos);
/* 168 */       if (commandsCount < 0) {
/* 169 */         return ValidationResult.error("Invalid array count for Commands");
/*     */       }
/* 171 */       if (commandsCount > 4096000) {
/* 172 */         return ValidationResult.error("Commands exceeds max length 4096000");
/*     */       }
/* 174 */       pos += VarInt.length(buffer, pos);
/* 175 */       for (int i = 0; i < commandsCount; i++) {
/* 176 */         ValidationResult structResult = JsonUpdateCommand.validateStructure(buffer, pos);
/* 177 */         if (!structResult.isValid()) {
/* 178 */           return ValidationResult.error("Invalid JsonUpdateCommand in Commands[" + i + "]: " + structResult.error());
/*     */         }
/* 180 */         pos += JsonUpdateCommand.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 183 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorJsonAssetUpdated clone() {
/* 187 */     AssetEditorJsonAssetUpdated copy = new AssetEditorJsonAssetUpdated();
/* 188 */     copy.path = (this.path != null) ? this.path.clone() : null;
/* 189 */     copy.commands = (this.commands != null) ? (JsonUpdateCommand[])Arrays.<JsonUpdateCommand>stream(this.commands).map(e -> e.clone()).toArray(x$0 -> new JsonUpdateCommand[x$0]) : null;
/* 190 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorJsonAssetUpdated other;
/* 196 */     if (this == obj) return true; 
/* 197 */     if (obj instanceof AssetEditorJsonAssetUpdated) { other = (AssetEditorJsonAssetUpdated)obj; } else { return false; }
/* 198 */      return (Objects.equals(this.path, other.path) && Arrays.equals((Object[])this.commands, (Object[])other.commands));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 203 */     int result = 1;
/* 204 */     result = 31 * result + Objects.hashCode(this.path);
/* 205 */     result = 31 * result + Arrays.hashCode((Object[])this.commands);
/* 206 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorJsonAssetUpdated.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */