/*     */ package com.hypixel.hytale.protocol.packets.interface_;
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
/*     */ public class CustomPage
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 218;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 4;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   
/*     */   public int getId() {
/*  25 */     return 218;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 16; public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public String key; public boolean isInitial;
/*     */   public boolean clear;
/*     */   @Nonnull
/*  31 */   public CustomPageLifetime lifetime = CustomPageLifetime.CantClose;
/*     */   
/*     */   @Nullable
/*     */   public CustomUICommand[] commands;
/*     */   @Nullable
/*     */   public CustomUIEventBinding[] eventBindings;
/*     */   
/*     */   public CustomPage(@Nullable String key, boolean isInitial, boolean clear, @Nonnull CustomPageLifetime lifetime, @Nullable CustomUICommand[] commands, @Nullable CustomUIEventBinding[] eventBindings) {
/*  39 */     this.key = key;
/*  40 */     this.isInitial = isInitial;
/*  41 */     this.clear = clear;
/*  42 */     this.lifetime = lifetime;
/*  43 */     this.commands = commands;
/*  44 */     this.eventBindings = eventBindings;
/*     */   }
/*     */   
/*     */   public CustomPage(@Nonnull CustomPage other) {
/*  48 */     this.key = other.key;
/*  49 */     this.isInitial = other.isInitial;
/*  50 */     this.clear = other.clear;
/*  51 */     this.lifetime = other.lifetime;
/*  52 */     this.commands = other.commands;
/*  53 */     this.eventBindings = other.eventBindings;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CustomPage deserialize(@Nonnull ByteBuf buf, int offset) {
/*  58 */     CustomPage obj = new CustomPage();
/*  59 */     byte nullBits = buf.getByte(offset);
/*  60 */     obj.isInitial = (buf.getByte(offset + 1) != 0);
/*  61 */     obj.clear = (buf.getByte(offset + 2) != 0);
/*  62 */     obj.lifetime = CustomPageLifetime.fromValue(buf.getByte(offset + 3));
/*     */     
/*  64 */     if ((nullBits & 0x1) != 0) {
/*  65 */       int varPos0 = offset + 16 + buf.getIntLE(offset + 4);
/*  66 */       int keyLen = VarInt.peek(buf, varPos0);
/*  67 */       if (keyLen < 0) throw ProtocolException.negativeLength("Key", keyLen); 
/*  68 */       if (keyLen > 4096000) throw ProtocolException.stringTooLong("Key", keyLen, 4096000); 
/*  69 */       obj.key = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  71 */     if ((nullBits & 0x2) != 0) {
/*  72 */       int varPos1 = offset + 16 + buf.getIntLE(offset + 8);
/*  73 */       int commandsCount = VarInt.peek(buf, varPos1);
/*  74 */       if (commandsCount < 0) throw ProtocolException.negativeLength("Commands", commandsCount); 
/*  75 */       if (commandsCount > 4096000) throw ProtocolException.arrayTooLong("Commands", commandsCount, 4096000); 
/*  76 */       int varIntLen = VarInt.length(buf, varPos1);
/*  77 */       if ((varPos1 + varIntLen) + commandsCount * 2L > buf.readableBytes())
/*  78 */         throw ProtocolException.bufferTooSmall("Commands", varPos1 + varIntLen + commandsCount * 2, buf.readableBytes()); 
/*  79 */       obj.commands = new CustomUICommand[commandsCount];
/*  80 */       int elemPos = varPos1 + varIntLen;
/*  81 */       for (int i = 0; i < commandsCount; i++) {
/*  82 */         obj.commands[i] = CustomUICommand.deserialize(buf, elemPos);
/*  83 */         elemPos += CustomUICommand.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  86 */     if ((nullBits & 0x4) != 0) {
/*  87 */       int varPos2 = offset + 16 + buf.getIntLE(offset + 12);
/*  88 */       int eventBindingsCount = VarInt.peek(buf, varPos2);
/*  89 */       if (eventBindingsCount < 0) throw ProtocolException.negativeLength("EventBindings", eventBindingsCount); 
/*  90 */       if (eventBindingsCount > 4096000) throw ProtocolException.arrayTooLong("EventBindings", eventBindingsCount, 4096000); 
/*  91 */       int varIntLen = VarInt.length(buf, varPos2);
/*  92 */       if ((varPos2 + varIntLen) + eventBindingsCount * 3L > buf.readableBytes())
/*  93 */         throw ProtocolException.bufferTooSmall("EventBindings", varPos2 + varIntLen + eventBindingsCount * 3, buf.readableBytes()); 
/*  94 */       obj.eventBindings = new CustomUIEventBinding[eventBindingsCount];
/*  95 */       int elemPos = varPos2 + varIntLen;
/*  96 */       for (int i = 0; i < eventBindingsCount; i++) {
/*  97 */         obj.eventBindings[i] = CustomUIEventBinding.deserialize(buf, elemPos);
/*  98 */         elemPos += CustomUIEventBinding.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 106 */     byte nullBits = buf.getByte(offset);
/* 107 */     int maxEnd = 16;
/* 108 */     if ((nullBits & 0x1) != 0) {
/* 109 */       int fieldOffset0 = buf.getIntLE(offset + 4);
/* 110 */       int pos0 = offset + 16 + fieldOffset0;
/* 111 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 112 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 114 */     if ((nullBits & 0x2) != 0) {
/* 115 */       int fieldOffset1 = buf.getIntLE(offset + 8);
/* 116 */       int pos1 = offset + 16 + fieldOffset1;
/* 117 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 118 */       for (int i = 0; i < arrLen; ) { pos1 += CustomUICommand.computeBytesConsumed(buf, pos1); i++; }
/* 119 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 121 */     if ((nullBits & 0x4) != 0) {
/* 122 */       int fieldOffset2 = buf.getIntLE(offset + 12);
/* 123 */       int pos2 = offset + 16 + fieldOffset2;
/* 124 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 125 */       for (int i = 0; i < arrLen; ) { pos2 += CustomUIEventBinding.computeBytesConsumed(buf, pos2); i++; }
/* 126 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 128 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 134 */     int startPos = buf.writerIndex();
/* 135 */     byte nullBits = 0;
/* 136 */     if (this.key != null) nullBits = (byte)(nullBits | 0x1); 
/* 137 */     if (this.commands != null) nullBits = (byte)(nullBits | 0x2); 
/* 138 */     if (this.eventBindings != null) nullBits = (byte)(nullBits | 0x4); 
/* 139 */     buf.writeByte(nullBits);
/*     */     
/* 141 */     buf.writeByte(this.isInitial ? 1 : 0);
/* 142 */     buf.writeByte(this.clear ? 1 : 0);
/* 143 */     buf.writeByte(this.lifetime.getValue());
/*     */     
/* 145 */     int keyOffsetSlot = buf.writerIndex();
/* 146 */     buf.writeIntLE(0);
/* 147 */     int commandsOffsetSlot = buf.writerIndex();
/* 148 */     buf.writeIntLE(0);
/* 149 */     int eventBindingsOffsetSlot = buf.writerIndex();
/* 150 */     buf.writeIntLE(0);
/*     */     
/* 152 */     int varBlockStart = buf.writerIndex();
/* 153 */     if (this.key != null) {
/* 154 */       buf.setIntLE(keyOffsetSlot, buf.writerIndex() - varBlockStart);
/* 155 */       PacketIO.writeVarString(buf, this.key, 4096000);
/*     */     } else {
/* 157 */       buf.setIntLE(keyOffsetSlot, -1);
/*     */     } 
/* 159 */     if (this.commands != null) {
/* 160 */       buf.setIntLE(commandsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 161 */       if (this.commands.length > 4096000) throw ProtocolException.arrayTooLong("Commands", this.commands.length, 4096000);  VarInt.write(buf, this.commands.length); for (CustomUICommand item : this.commands) item.serialize(buf); 
/*     */     } else {
/* 163 */       buf.setIntLE(commandsOffsetSlot, -1);
/*     */     } 
/* 165 */     if (this.eventBindings != null) {
/* 166 */       buf.setIntLE(eventBindingsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 167 */       if (this.eventBindings.length > 4096000) throw ProtocolException.arrayTooLong("EventBindings", this.eventBindings.length, 4096000);  VarInt.write(buf, this.eventBindings.length); for (CustomUIEventBinding item : this.eventBindings) item.serialize(buf); 
/*     */     } else {
/* 169 */       buf.setIntLE(eventBindingsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 175 */     int size = 16;
/* 176 */     if (this.key != null) size += PacketIO.stringSize(this.key); 
/* 177 */     if (this.commands != null) {
/* 178 */       int commandsSize = 0;
/* 179 */       for (CustomUICommand elem : this.commands) commandsSize += elem.computeSize(); 
/* 180 */       size += VarInt.size(this.commands.length) + commandsSize;
/*     */     } 
/* 182 */     if (this.eventBindings != null) {
/* 183 */       int eventBindingsSize = 0;
/* 184 */       for (CustomUIEventBinding elem : this.eventBindings) eventBindingsSize += elem.computeSize(); 
/* 185 */       size += VarInt.size(this.eventBindings.length) + eventBindingsSize;
/*     */     } 
/*     */     
/* 188 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 192 */     if (buffer.readableBytes() - offset < 16) {
/* 193 */       return ValidationResult.error("Buffer too small: expected at least 16 bytes");
/*     */     }
/*     */     
/* 196 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 199 */     if ((nullBits & 0x1) != 0) {
/* 200 */       int keyOffset = buffer.getIntLE(offset + 4);
/* 201 */       if (keyOffset < 0) {
/* 202 */         return ValidationResult.error("Invalid offset for Key");
/*     */       }
/* 204 */       int pos = offset + 16 + keyOffset;
/* 205 */       if (pos >= buffer.writerIndex()) {
/* 206 */         return ValidationResult.error("Offset out of bounds for Key");
/*     */       }
/* 208 */       int keyLen = VarInt.peek(buffer, pos);
/* 209 */       if (keyLen < 0) {
/* 210 */         return ValidationResult.error("Invalid string length for Key");
/*     */       }
/* 212 */       if (keyLen > 4096000) {
/* 213 */         return ValidationResult.error("Key exceeds max length 4096000");
/*     */       }
/* 215 */       pos += VarInt.length(buffer, pos);
/* 216 */       pos += keyLen;
/* 217 */       if (pos > buffer.writerIndex()) {
/* 218 */         return ValidationResult.error("Buffer overflow reading Key");
/*     */       }
/*     */     } 
/*     */     
/* 222 */     if ((nullBits & 0x2) != 0) {
/* 223 */       int commandsOffset = buffer.getIntLE(offset + 8);
/* 224 */       if (commandsOffset < 0) {
/* 225 */         return ValidationResult.error("Invalid offset for Commands");
/*     */       }
/* 227 */       int pos = offset + 16 + commandsOffset;
/* 228 */       if (pos >= buffer.writerIndex()) {
/* 229 */         return ValidationResult.error("Offset out of bounds for Commands");
/*     */       }
/* 231 */       int commandsCount = VarInt.peek(buffer, pos);
/* 232 */       if (commandsCount < 0) {
/* 233 */         return ValidationResult.error("Invalid array count for Commands");
/*     */       }
/* 235 */       if (commandsCount > 4096000) {
/* 236 */         return ValidationResult.error("Commands exceeds max length 4096000");
/*     */       }
/* 238 */       pos += VarInt.length(buffer, pos);
/* 239 */       for (int i = 0; i < commandsCount; i++) {
/* 240 */         ValidationResult structResult = CustomUICommand.validateStructure(buffer, pos);
/* 241 */         if (!structResult.isValid()) {
/* 242 */           return ValidationResult.error("Invalid CustomUICommand in Commands[" + i + "]: " + structResult.error());
/*     */         }
/* 244 */         pos += CustomUICommand.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 248 */     if ((nullBits & 0x4) != 0) {
/* 249 */       int eventBindingsOffset = buffer.getIntLE(offset + 12);
/* 250 */       if (eventBindingsOffset < 0) {
/* 251 */         return ValidationResult.error("Invalid offset for EventBindings");
/*     */       }
/* 253 */       int pos = offset + 16 + eventBindingsOffset;
/* 254 */       if (pos >= buffer.writerIndex()) {
/* 255 */         return ValidationResult.error("Offset out of bounds for EventBindings");
/*     */       }
/* 257 */       int eventBindingsCount = VarInt.peek(buffer, pos);
/* 258 */       if (eventBindingsCount < 0) {
/* 259 */         return ValidationResult.error("Invalid array count for EventBindings");
/*     */       }
/* 261 */       if (eventBindingsCount > 4096000) {
/* 262 */         return ValidationResult.error("EventBindings exceeds max length 4096000");
/*     */       }
/* 264 */       pos += VarInt.length(buffer, pos);
/* 265 */       for (int i = 0; i < eventBindingsCount; i++) {
/* 266 */         ValidationResult structResult = CustomUIEventBinding.validateStructure(buffer, pos);
/* 267 */         if (!structResult.isValid()) {
/* 268 */           return ValidationResult.error("Invalid CustomUIEventBinding in EventBindings[" + i + "]: " + structResult.error());
/*     */         }
/* 270 */         pos += CustomUIEventBinding.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 273 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CustomPage clone() {
/* 277 */     CustomPage copy = new CustomPage();
/* 278 */     copy.key = this.key;
/* 279 */     copy.isInitial = this.isInitial;
/* 280 */     copy.clear = this.clear;
/* 281 */     copy.lifetime = this.lifetime;
/* 282 */     copy.commands = (this.commands != null) ? (CustomUICommand[])Arrays.<CustomUICommand>stream(this.commands).map(e -> e.clone()).toArray(x$0 -> new CustomUICommand[x$0]) : null;
/* 283 */     copy.eventBindings = (this.eventBindings != null) ? (CustomUIEventBinding[])Arrays.<CustomUIEventBinding>stream(this.eventBindings).map(e -> e.clone()).toArray(x$0 -> new CustomUIEventBinding[x$0]) : null;
/* 284 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CustomPage other;
/* 290 */     if (this == obj) return true; 
/* 291 */     if (obj instanceof CustomPage) { other = (CustomPage)obj; } else { return false; }
/* 292 */      return (Objects.equals(this.key, other.key) && this.isInitial == other.isInitial && this.clear == other.clear && Objects.equals(this.lifetime, other.lifetime) && Arrays.equals((Object[])this.commands, (Object[])other.commands) && Arrays.equals((Object[])this.eventBindings, (Object[])other.eventBindings));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 297 */     int result = 1;
/* 298 */     result = 31 * result + Objects.hashCode(this.key);
/* 299 */     result = 31 * result + Boolean.hashCode(this.isInitial);
/* 300 */     result = 31 * result + Boolean.hashCode(this.clear);
/* 301 */     result = 31 * result + Objects.hashCode(this.lifetime);
/* 302 */     result = 31 * result + Arrays.hashCode((Object[])this.commands);
/* 303 */     result = 31 * result + Arrays.hashCode((Object[])this.eventBindings);
/* 304 */     return result;
/*     */   }
/*     */   
/*     */   public CustomPage() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\CustomPage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */