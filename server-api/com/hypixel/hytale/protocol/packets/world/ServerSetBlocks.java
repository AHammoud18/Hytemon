/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ServerSetBlocks
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 141;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 12;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 12;
/*     */   public static final int MAX_SIZE = 36864017;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   
/*     */   public int getId() {
/*  25 */     return 141;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  31 */   public SetBlockCmd[] cmds = new SetBlockCmd[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSetBlocks(int x, int y, int z, @Nonnull SetBlockCmd[] cmds) {
/*  37 */     this.x = x;
/*  38 */     this.y = y;
/*  39 */     this.z = z;
/*  40 */     this.cmds = cmds;
/*     */   }
/*     */   
/*     */   public ServerSetBlocks(@Nonnull ServerSetBlocks other) {
/*  44 */     this.x = other.x;
/*  45 */     this.y = other.y;
/*  46 */     this.z = other.z;
/*  47 */     this.cmds = other.cmds;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ServerSetBlocks deserialize(@Nonnull ByteBuf buf, int offset) {
/*  52 */     ServerSetBlocks obj = new ServerSetBlocks();
/*     */     
/*  54 */     obj.x = buf.getIntLE(offset + 0);
/*  55 */     obj.y = buf.getIntLE(offset + 4);
/*  56 */     obj.z = buf.getIntLE(offset + 8);
/*     */     
/*  58 */     int pos = offset + 12;
/*  59 */     int cmdsCount = VarInt.peek(buf, pos);
/*  60 */     if (cmdsCount < 0) throw ProtocolException.negativeLength("Cmds", cmdsCount); 
/*  61 */     if (cmdsCount > 4096000) throw ProtocolException.arrayTooLong("Cmds", cmdsCount, 4096000); 
/*  62 */     int cmdsVarLen = VarInt.size(cmdsCount);
/*  63 */     if ((pos + cmdsVarLen) + cmdsCount * 9L > buf.readableBytes())
/*  64 */       throw ProtocolException.bufferTooSmall("Cmds", pos + cmdsVarLen + cmdsCount * 9, buf.readableBytes()); 
/*  65 */     pos += cmdsVarLen;
/*  66 */     obj.cmds = new SetBlockCmd[cmdsCount];
/*  67 */     for (int i = 0; i < cmdsCount; i++) {
/*  68 */       obj.cmds[i] = SetBlockCmd.deserialize(buf, pos);
/*  69 */       pos += SetBlockCmd.computeBytesConsumed(buf, pos);
/*     */     } 
/*     */     
/*  72 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  76 */     int pos = offset + 12;
/*  77 */     int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  78 */     for (int i = 0; i < arrLen; ) { pos += SetBlockCmd.computeBytesConsumed(buf, pos); i++; }
/*  79 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  86 */     buf.writeIntLE(this.x);
/*  87 */     buf.writeIntLE(this.y);
/*  88 */     buf.writeIntLE(this.z);
/*     */     
/*  90 */     if (this.cmds.length > 4096000) throw ProtocolException.arrayTooLong("Cmds", this.cmds.length, 4096000);  VarInt.write(buf, this.cmds.length); for (SetBlockCmd item : this.cmds) item.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  95 */     int size = 12;
/*  96 */     size += VarInt.size(this.cmds.length) + this.cmds.length * 9;
/*     */     
/*  98 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 102 */     if (buffer.readableBytes() - offset < 12) {
/* 103 */       return ValidationResult.error("Buffer too small: expected at least 12 bytes");
/*     */     }
/*     */ 
/*     */     
/* 107 */     int pos = offset + 12;
/*     */     
/* 109 */     int cmdsCount = VarInt.peek(buffer, pos);
/* 110 */     if (cmdsCount < 0) {
/* 111 */       return ValidationResult.error("Invalid array count for Cmds");
/*     */     }
/* 113 */     if (cmdsCount > 4096000) {
/* 114 */       return ValidationResult.error("Cmds exceeds max length 4096000");
/*     */     }
/* 116 */     pos += VarInt.length(buffer, pos);
/* 117 */     pos += cmdsCount * 9;
/* 118 */     if (pos > buffer.writerIndex()) {
/* 119 */       return ValidationResult.error("Buffer overflow reading Cmds");
/*     */     }
/* 121 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ServerSetBlocks clone() {
/* 125 */     ServerSetBlocks copy = new ServerSetBlocks();
/* 126 */     copy.x = this.x;
/* 127 */     copy.y = this.y;
/* 128 */     copy.z = this.z;
/* 129 */     copy.cmds = (SetBlockCmd[])Arrays.<SetBlockCmd>stream(this.cmds).map(e -> e.clone()).toArray(x$0 -> new SetBlockCmd[x$0]);
/* 130 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ServerSetBlocks other;
/* 136 */     if (this == obj) return true; 
/* 137 */     if (obj instanceof ServerSetBlocks) { other = (ServerSetBlocks)obj; } else { return false; }
/* 138 */      return (this.x == other.x && this.y == other.y && this.z == other.z && Arrays.equals((Object[])this.cmds, (Object[])other.cmds));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 143 */     int result = 1;
/* 144 */     result = 31 * result + Integer.hashCode(this.x);
/* 145 */     result = 31 * result + Integer.hashCode(this.y);
/* 146 */     result = 31 * result + Integer.hashCode(this.z);
/* 147 */     result = 31 * result + Arrays.hashCode((Object[])this.cmds);
/* 148 */     return result;
/*     */   }
/*     */   
/*     */   public ServerSetBlocks() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\ServerSetBlocks.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */