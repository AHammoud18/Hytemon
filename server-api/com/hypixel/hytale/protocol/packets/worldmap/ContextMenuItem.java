/*     */ package com.hypixel.hytale.protocol.packets.worldmap;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class ContextMenuItem
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 0;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 8;
/*     */   public static final int MAX_SIZE = 32768018;
/*     */   @Nonnull
/*  20 */   public String name = ""; @Nonnull
/*  21 */   public String command = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContextMenuItem(@Nonnull String name, @Nonnull String command) {
/*  27 */     this.name = name;
/*  28 */     this.command = command;
/*     */   }
/*     */   
/*     */   public ContextMenuItem(@Nonnull ContextMenuItem other) {
/*  32 */     this.name = other.name;
/*  33 */     this.command = other.command;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ContextMenuItem deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     ContextMenuItem obj = new ContextMenuItem();
/*     */ 
/*     */ 
/*     */     
/*  42 */     int varPos0 = offset + 8 + buf.getIntLE(offset + 0);
/*  43 */     int nameLen = VarInt.peek(buf, varPos0);
/*  44 */     if (nameLen < 0) throw ProtocolException.negativeLength("Name", nameLen); 
/*  45 */     if (nameLen > 4096000) throw ProtocolException.stringTooLong("Name", nameLen, 4096000); 
/*  46 */     obj.name = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */ 
/*     */     
/*  49 */     int varPos1 = offset + 8 + buf.getIntLE(offset + 4);
/*  50 */     int commandLen = VarInt.peek(buf, varPos1);
/*  51 */     if (commandLen < 0) throw ProtocolException.negativeLength("Command", commandLen); 
/*  52 */     if (commandLen > 4096000) throw ProtocolException.stringTooLong("Command", commandLen, 4096000); 
/*  53 */     obj.command = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     int maxEnd = 8;
/*     */     
/*  62 */     int fieldOffset0 = buf.getIntLE(offset + 0);
/*  63 */     int pos0 = offset + 8 + fieldOffset0;
/*  64 */     int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  65 */     if (pos0 - offset > maxEnd) maxEnd = pos0 - offset;
/*     */ 
/*     */     
/*  68 */     int fieldOffset1 = buf.getIntLE(offset + 4);
/*  69 */     int pos1 = offset + 8 + fieldOffset1;
/*  70 */     sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  71 */     if (pos1 - offset > maxEnd) maxEnd = pos1 - offset;
/*     */     
/*  73 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  78 */     int startPos = buf.writerIndex();
/*     */ 
/*     */     
/*  81 */     int nameOffsetSlot = buf.writerIndex();
/*  82 */     buf.writeIntLE(0);
/*  83 */     int commandOffsetSlot = buf.writerIndex();
/*  84 */     buf.writeIntLE(0);
/*     */     
/*  86 */     int varBlockStart = buf.writerIndex();
/*  87 */     buf.setIntLE(nameOffsetSlot, buf.writerIndex() - varBlockStart);
/*  88 */     PacketIO.writeVarString(buf, this.name, 4096000);
/*  89 */     buf.setIntLE(commandOffsetSlot, buf.writerIndex() - varBlockStart);
/*  90 */     PacketIO.writeVarString(buf, this.command, 4096000);
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  95 */     int size = 8;
/*  96 */     size += PacketIO.stringSize(this.name);
/*  97 */     size += PacketIO.stringSize(this.command);
/*     */     
/*  99 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 103 */     if (buffer.readableBytes() - offset < 8) {
/* 104 */       return ValidationResult.error("Buffer too small: expected at least 8 bytes");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     int nameOffset = buffer.getIntLE(offset + 0);
/* 111 */     if (nameOffset < 0) {
/* 112 */       return ValidationResult.error("Invalid offset for Name");
/*     */     }
/* 114 */     int pos = offset + 8 + nameOffset;
/* 115 */     if (pos >= buffer.writerIndex()) {
/* 116 */       return ValidationResult.error("Offset out of bounds for Name");
/*     */     }
/* 118 */     int nameLen = VarInt.peek(buffer, pos);
/* 119 */     if (nameLen < 0) {
/* 120 */       return ValidationResult.error("Invalid string length for Name");
/*     */     }
/* 122 */     if (nameLen > 4096000) {
/* 123 */       return ValidationResult.error("Name exceeds max length 4096000");
/*     */     }
/* 125 */     pos += VarInt.length(buffer, pos);
/* 126 */     pos += nameLen;
/* 127 */     if (pos > buffer.writerIndex()) {
/* 128 */       return ValidationResult.error("Buffer overflow reading Name");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 133 */     int commandOffset = buffer.getIntLE(offset + 4);
/* 134 */     if (commandOffset < 0) {
/* 135 */       return ValidationResult.error("Invalid offset for Command");
/*     */     }
/* 137 */     pos = offset + 8 + commandOffset;
/* 138 */     if (pos >= buffer.writerIndex()) {
/* 139 */       return ValidationResult.error("Offset out of bounds for Command");
/*     */     }
/* 141 */     int commandLen = VarInt.peek(buffer, pos);
/* 142 */     if (commandLen < 0) {
/* 143 */       return ValidationResult.error("Invalid string length for Command");
/*     */     }
/* 145 */     if (commandLen > 4096000) {
/* 146 */       return ValidationResult.error("Command exceeds max length 4096000");
/*     */     }
/* 148 */     pos += VarInt.length(buffer, pos);
/* 149 */     pos += commandLen;
/* 150 */     if (pos > buffer.writerIndex()) {
/* 151 */       return ValidationResult.error("Buffer overflow reading Command");
/*     */     }
/*     */     
/* 154 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ContextMenuItem clone() {
/* 158 */     ContextMenuItem copy = new ContextMenuItem();
/* 159 */     copy.name = this.name;
/* 160 */     copy.command = this.command;
/* 161 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ContextMenuItem other;
/* 167 */     if (this == obj) return true; 
/* 168 */     if (obj instanceof ContextMenuItem) { other = (ContextMenuItem)obj; } else { return false; }
/* 169 */      return (Objects.equals(this.name, other.name) && Objects.equals(this.command, other.command));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 174 */     return Objects.hash(new Object[] { this.name, this.command });
/*     */   }
/*     */   
/*     */   public ContextMenuItem() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\worldmap\ContextMenuItem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */