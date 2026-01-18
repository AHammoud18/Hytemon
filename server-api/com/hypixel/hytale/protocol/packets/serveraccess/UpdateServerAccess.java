/*     */ package com.hypixel.hytale.protocol.packets.serveraccess;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.HostAddress;
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
/*     */ public class UpdateServerAccess
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 251;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  25 */     return 251;
/*     */   }
/*     */   @Nonnull
/*  28 */   public Access access = Access.Private;
/*     */   
/*     */   @Nullable
/*     */   public HostAddress[] hosts;
/*     */ 
/*     */   
/*     */   public UpdateServerAccess(@Nonnull Access access, @Nullable HostAddress[] hosts) {
/*  35 */     this.access = access;
/*  36 */     this.hosts = hosts;
/*     */   }
/*     */   
/*     */   public UpdateServerAccess(@Nonnull UpdateServerAccess other) {
/*  40 */     this.access = other.access;
/*  41 */     this.hosts = other.hosts;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateServerAccess deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     UpdateServerAccess obj = new UpdateServerAccess();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.access = Access.fromValue(buf.getByte(offset + 1));
/*     */     
/*  50 */     int pos = offset + 2;
/*  51 */     if ((nullBits & 0x1) != 0) { int hostsCount = VarInt.peek(buf, pos);
/*  52 */       if (hostsCount < 0) throw ProtocolException.negativeLength("Hosts", hostsCount); 
/*  53 */       if (hostsCount > 4096000) throw ProtocolException.arrayTooLong("Hosts", hostsCount, 4096000); 
/*  54 */       int hostsVarLen = VarInt.size(hostsCount);
/*  55 */       if ((pos + hostsVarLen) + hostsCount * 2L > buf.readableBytes())
/*  56 */         throw ProtocolException.bufferTooSmall("Hosts", pos + hostsVarLen + hostsCount * 2, buf.readableBytes()); 
/*  57 */       pos += hostsVarLen;
/*  58 */       obj.hosts = new HostAddress[hostsCount];
/*  59 */       for (int i = 0; i < hostsCount; i++) {
/*  60 */         obj.hosts[i] = HostAddress.deserialize(buf, pos);
/*  61 */         pos += HostAddress.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     byte nullBits = buf.getByte(offset);
/*  69 */     int pos = offset + 2;
/*  70 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  71 */       for (int i = 0; i < arrLen; ) { pos += HostAddress.computeBytesConsumed(buf, pos); i++; }  }
/*  72 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  78 */     byte nullBits = 0;
/*  79 */     if (this.hosts != null) nullBits = (byte)(nullBits | 0x1); 
/*  80 */     buf.writeByte(nullBits);
/*     */     
/*  82 */     buf.writeByte(this.access.getValue());
/*     */     
/*  84 */     if (this.hosts != null) { if (this.hosts.length > 4096000) throw ProtocolException.arrayTooLong("Hosts", this.hosts.length, 4096000);  VarInt.write(buf, this.hosts.length); for (HostAddress item : this.hosts) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  89 */     int size = 2;
/*  90 */     if (this.hosts != null) {
/*  91 */       int hostsSize = 0;
/*  92 */       for (HostAddress elem : this.hosts) hostsSize += elem.computeSize(); 
/*  93 */       size += VarInt.size(this.hosts.length) + hostsSize;
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
/* 109 */       int hostsCount = VarInt.peek(buffer, pos);
/* 110 */       if (hostsCount < 0) {
/* 111 */         return ValidationResult.error("Invalid array count for Hosts");
/*     */       }
/* 113 */       if (hostsCount > 4096000) {
/* 114 */         return ValidationResult.error("Hosts exceeds max length 4096000");
/*     */       }
/* 116 */       pos += VarInt.length(buffer, pos);
/* 117 */       for (int i = 0; i < hostsCount; i++) {
/* 118 */         ValidationResult structResult = HostAddress.validateStructure(buffer, pos);
/* 119 */         if (!structResult.isValid()) {
/* 120 */           return ValidationResult.error("Invalid HostAddress in Hosts[" + i + "]: " + structResult.error());
/*     */         }
/* 122 */         pos += HostAddress.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 125 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateServerAccess clone() {
/* 129 */     UpdateServerAccess copy = new UpdateServerAccess();
/* 130 */     copy.access = this.access;
/* 131 */     copy.hosts = (this.hosts != null) ? (HostAddress[])Arrays.<HostAddress>stream(this.hosts).map(e -> e.clone()).toArray(x$0 -> new HostAddress[x$0]) : null;
/* 132 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateServerAccess other;
/* 138 */     if (this == obj) return true; 
/* 139 */     if (obj instanceof UpdateServerAccess) { other = (UpdateServerAccess)obj; } else { return false; }
/* 140 */      return (Objects.equals(this.access, other.access) && Arrays.equals((Object[])this.hosts, (Object[])other.hosts));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 145 */     int result = 1;
/* 146 */     result = 31 * result + Objects.hashCode(this.access);
/* 147 */     result = 31 * result + Arrays.hashCode((Object[])this.hosts);
/* 148 */     return result;
/*     */   }
/*     */   
/*     */   public UpdateServerAccess() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\serveraccess\UpdateServerAccess.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */