/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
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
/*     */ public class BuilderToolArgUpdate implements Packet {
/*     */   public static final int PACKET_ID = 400;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 14;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 22;
/*     */   public static final int MAX_SIZE = 32768032;
/*     */   public int token;
/*     */   public int section;
/*     */   public int slot;
/*     */   
/*     */   public int getId() {
/*  25 */     return 400;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  31 */   public BuilderToolArgGroup group = BuilderToolArgGroup.Tool;
/*     */   
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public String value;
/*     */   
/*     */   public BuilderToolArgUpdate(int token, int section, int slot, @Nonnull BuilderToolArgGroup group, @Nullable String id, @Nullable String value) {
/*  39 */     this.token = token;
/*  40 */     this.section = section;
/*  41 */     this.slot = slot;
/*  42 */     this.group = group;
/*  43 */     this.id = id;
/*  44 */     this.value = value;
/*     */   }
/*     */   
/*     */   public BuilderToolArgUpdate(@Nonnull BuilderToolArgUpdate other) {
/*  48 */     this.token = other.token;
/*  49 */     this.section = other.section;
/*  50 */     this.slot = other.slot;
/*  51 */     this.group = other.group;
/*  52 */     this.id = other.id;
/*  53 */     this.value = other.value;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolArgUpdate deserialize(@Nonnull ByteBuf buf, int offset) {
/*  58 */     BuilderToolArgUpdate obj = new BuilderToolArgUpdate();
/*  59 */     byte nullBits = buf.getByte(offset);
/*  60 */     obj.token = buf.getIntLE(offset + 1);
/*  61 */     obj.section = buf.getIntLE(offset + 5);
/*  62 */     obj.slot = buf.getIntLE(offset + 9);
/*  63 */     obj.group = BuilderToolArgGroup.fromValue(buf.getByte(offset + 13));
/*     */     
/*  65 */     if ((nullBits & 0x1) != 0) {
/*  66 */       int varPos0 = offset + 22 + buf.getIntLE(offset + 14);
/*  67 */       int idLen = VarInt.peek(buf, varPos0);
/*  68 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  69 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  70 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  72 */     if ((nullBits & 0x2) != 0) {
/*  73 */       int varPos1 = offset + 22 + buf.getIntLE(offset + 18);
/*  74 */       int valueLen = VarInt.peek(buf, varPos1);
/*  75 */       if (valueLen < 0) throw ProtocolException.negativeLength("Value", valueLen); 
/*  76 */       if (valueLen > 4096000) throw ProtocolException.stringTooLong("Value", valueLen, 4096000); 
/*  77 */       obj.value = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  80 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  84 */     byte nullBits = buf.getByte(offset);
/*  85 */     int maxEnd = 22;
/*  86 */     if ((nullBits & 0x1) != 0) {
/*  87 */       int fieldOffset0 = buf.getIntLE(offset + 14);
/*  88 */       int pos0 = offset + 22 + fieldOffset0;
/*  89 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  90 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  92 */     if ((nullBits & 0x2) != 0) {
/*  93 */       int fieldOffset1 = buf.getIntLE(offset + 18);
/*  94 */       int pos1 = offset + 22 + fieldOffset1;
/*  95 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  96 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  98 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 104 */     int startPos = buf.writerIndex();
/* 105 */     byte nullBits = 0;
/* 106 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 107 */     if (this.value != null) nullBits = (byte)(nullBits | 0x2); 
/* 108 */     buf.writeByte(nullBits);
/*     */     
/* 110 */     buf.writeIntLE(this.token);
/* 111 */     buf.writeIntLE(this.section);
/* 112 */     buf.writeIntLE(this.slot);
/* 113 */     buf.writeByte(this.group.getValue());
/*     */     
/* 115 */     int idOffsetSlot = buf.writerIndex();
/* 116 */     buf.writeIntLE(0);
/* 117 */     int valueOffsetSlot = buf.writerIndex();
/* 118 */     buf.writeIntLE(0);
/*     */     
/* 120 */     int varBlockStart = buf.writerIndex();
/* 121 */     if (this.id != null) {
/* 122 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 123 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 125 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 127 */     if (this.value != null) {
/* 128 */       buf.setIntLE(valueOffsetSlot, buf.writerIndex() - varBlockStart);
/* 129 */       PacketIO.writeVarString(buf, this.value, 4096000);
/*     */     } else {
/* 131 */       buf.setIntLE(valueOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 137 */     int size = 22;
/* 138 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 139 */     if (this.value != null) size += PacketIO.stringSize(this.value);
/*     */     
/* 141 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 145 */     if (buffer.readableBytes() - offset < 22) {
/* 146 */       return ValidationResult.error("Buffer too small: expected at least 22 bytes");
/*     */     }
/*     */     
/* 149 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 152 */     if ((nullBits & 0x1) != 0) {
/* 153 */       int idOffset = buffer.getIntLE(offset + 14);
/* 154 */       if (idOffset < 0) {
/* 155 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 157 */       int pos = offset + 22 + idOffset;
/* 158 */       if (pos >= buffer.writerIndex()) {
/* 159 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 161 */       int idLen = VarInt.peek(buffer, pos);
/* 162 */       if (idLen < 0) {
/* 163 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 165 */       if (idLen > 4096000) {
/* 166 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 168 */       pos += VarInt.length(buffer, pos);
/* 169 */       pos += idLen;
/* 170 */       if (pos > buffer.writerIndex()) {
/* 171 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 175 */     if ((nullBits & 0x2) != 0) {
/* 176 */       int valueOffset = buffer.getIntLE(offset + 18);
/* 177 */       if (valueOffset < 0) {
/* 178 */         return ValidationResult.error("Invalid offset for Value");
/*     */       }
/* 180 */       int pos = offset + 22 + valueOffset;
/* 181 */       if (pos >= buffer.writerIndex()) {
/* 182 */         return ValidationResult.error("Offset out of bounds for Value");
/*     */       }
/* 184 */       int valueLen = VarInt.peek(buffer, pos);
/* 185 */       if (valueLen < 0) {
/* 186 */         return ValidationResult.error("Invalid string length for Value");
/*     */       }
/* 188 */       if (valueLen > 4096000) {
/* 189 */         return ValidationResult.error("Value exceeds max length 4096000");
/*     */       }
/* 191 */       pos += VarInt.length(buffer, pos);
/* 192 */       pos += valueLen;
/* 193 */       if (pos > buffer.writerIndex()) {
/* 194 */         return ValidationResult.error("Buffer overflow reading Value");
/*     */       }
/*     */     } 
/* 197 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolArgUpdate clone() {
/* 201 */     BuilderToolArgUpdate copy = new BuilderToolArgUpdate();
/* 202 */     copy.token = this.token;
/* 203 */     copy.section = this.section;
/* 204 */     copy.slot = this.slot;
/* 205 */     copy.group = this.group;
/* 206 */     copy.id = this.id;
/* 207 */     copy.value = this.value;
/* 208 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolArgUpdate other;
/* 214 */     if (this == obj) return true; 
/* 215 */     if (obj instanceof BuilderToolArgUpdate) { other = (BuilderToolArgUpdate)obj; } else { return false; }
/* 216 */      return (this.token == other.token && this.section == other.section && this.slot == other.slot && Objects.equals(this.group, other.group) && Objects.equals(this.id, other.id) && Objects.equals(this.value, other.value));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 221 */     return Objects.hash(new Object[] { Integer.valueOf(this.token), Integer.valueOf(this.section), Integer.valueOf(this.slot), this.group, this.id, this.value });
/*     */   }
/*     */   
/*     */   public BuilderToolArgUpdate() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolArgUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */