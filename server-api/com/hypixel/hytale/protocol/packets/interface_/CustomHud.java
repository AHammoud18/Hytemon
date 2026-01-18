/*     */ package com.hypixel.hytale.protocol.packets.interface_;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CustomHud implements Packet {
/*     */   public static final int PACKET_ID = 217;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public boolean clear;
/*     */   @Nullable
/*     */   public CustomUICommand[] commands;
/*     */   
/*     */   public int getId() {
/*  25 */     return 217;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomHud() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomHud(boolean clear, @Nullable CustomUICommand[] commands) {
/*  35 */     this.clear = clear;
/*  36 */     this.commands = commands;
/*     */   }
/*     */   
/*     */   public CustomHud(@Nonnull CustomHud other) {
/*  40 */     this.clear = other.clear;
/*  41 */     this.commands = other.commands;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CustomHud deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     CustomHud obj = new CustomHud();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.clear = (buf.getByte(offset + 1) != 0);
/*     */     
/*  50 */     int pos = offset + 2;
/*  51 */     if ((nullBits & 0x1) != 0) { int commandsCount = VarInt.peek(buf, pos);
/*  52 */       if (commandsCount < 0) throw ProtocolException.negativeLength("Commands", commandsCount); 
/*  53 */       if (commandsCount > 4096000) throw ProtocolException.arrayTooLong("Commands", commandsCount, 4096000); 
/*  54 */       int commandsVarLen = VarInt.size(commandsCount);
/*  55 */       if ((pos + commandsVarLen) + commandsCount * 2L > buf.readableBytes())
/*  56 */         throw ProtocolException.bufferTooSmall("Commands", pos + commandsVarLen + commandsCount * 2, buf.readableBytes()); 
/*  57 */       pos += commandsVarLen;
/*  58 */       obj.commands = new CustomUICommand[commandsCount];
/*  59 */       for (int i = 0; i < commandsCount; i++) {
/*  60 */         obj.commands[i] = CustomUICommand.deserialize(buf, pos);
/*  61 */         pos += CustomUICommand.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     byte nullBits = buf.getByte(offset);
/*  69 */     int pos = offset + 2;
/*  70 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  71 */       for (int i = 0; i < arrLen; ) { pos += CustomUICommand.computeBytesConsumed(buf, pos); i++; }  }
/*  72 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  78 */     byte nullBits = 0;
/*  79 */     if (this.commands != null) nullBits = (byte)(nullBits | 0x1); 
/*  80 */     buf.writeByte(nullBits);
/*     */     
/*  82 */     buf.writeByte(this.clear ? 1 : 0);
/*     */     
/*  84 */     if (this.commands != null) { if (this.commands.length > 4096000) throw ProtocolException.arrayTooLong("Commands", this.commands.length, 4096000);  VarInt.write(buf, this.commands.length); for (CustomUICommand item : this.commands) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  89 */     int size = 2;
/*  90 */     if (this.commands != null) {
/*  91 */       int commandsSize = 0;
/*  92 */       for (CustomUICommand elem : this.commands) commandsSize += elem.computeSize(); 
/*  93 */       size += VarInt.size(this.commands.length) + commandsSize;
/*     */     } 
/*     */     
/*  96 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 100 */     if (buffer.readableBytes() - offset < 2) {
/* 101 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */     
/* 104 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 106 */     int pos = offset + 2;
/*     */     
/* 108 */     if ((nullBits & 0x1) != 0) {
/* 109 */       int commandsCount = VarInt.peek(buffer, pos);
/* 110 */       if (commandsCount < 0) {
/* 111 */         return ValidationResult.error("Invalid array count for Commands");
/*     */       }
/* 113 */       if (commandsCount > 4096000) {
/* 114 */         return ValidationResult.error("Commands exceeds max length 4096000");
/*     */       }
/* 116 */       pos += VarInt.length(buffer, pos);
/* 117 */       for (int i = 0; i < commandsCount; i++) {
/* 118 */         ValidationResult structResult = CustomUICommand.validateStructure(buffer, pos);
/* 119 */         if (!structResult.isValid()) {
/* 120 */           return ValidationResult.error("Invalid CustomUICommand in Commands[" + i + "]: " + structResult.error());
/*     */         }
/* 122 */         pos += CustomUICommand.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 125 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CustomHud clone() {
/* 129 */     CustomHud copy = new CustomHud();
/* 130 */     copy.clear = this.clear;
/* 131 */     copy.commands = (this.commands != null) ? (CustomUICommand[])Arrays.<CustomUICommand>stream(this.commands).map(e -> e.clone()).toArray(x$0 -> new CustomUICommand[x$0]) : null;
/* 132 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CustomHud other;
/* 138 */     if (this == obj) return true; 
/* 139 */     if (obj instanceof CustomHud) { other = (CustomHud)obj; } else { return false; }
/* 140 */      return (this.clear == other.clear && Arrays.equals((Object[])this.commands, (Object[])other.commands));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 145 */     int result = 1;
/* 146 */     result = 31 * result + Boolean.hashCode(this.clear);
/* 147 */     result = 31 * result + Arrays.hashCode((Object[])this.commands);
/* 148 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\CustomHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */