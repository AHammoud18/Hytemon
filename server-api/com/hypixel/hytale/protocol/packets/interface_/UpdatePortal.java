/*     */ package com.hypixel.hytale.protocol.packets.interface_;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class UpdatePortal
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 229;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 6;
/*     */   public static final int MAX_SIZE = 16384020;
/*     */   @Nullable
/*     */   public PortalState state;
/*     */   @Nullable
/*     */   public PortalDef definition;
/*     */   
/*     */   public int getId() {
/*  25 */     return 229;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdatePortal() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdatePortal(@Nullable PortalState state, @Nullable PortalDef definition) {
/*  35 */     this.state = state;
/*  36 */     this.definition = definition;
/*     */   }
/*     */   
/*     */   public UpdatePortal(@Nonnull UpdatePortal other) {
/*  40 */     this.state = other.state;
/*  41 */     this.definition = other.definition;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdatePortal deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     UpdatePortal obj = new UpdatePortal();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     if ((nullBits & 0x1) != 0) obj.state = PortalState.deserialize(buf, offset + 1);
/*     */     
/*  50 */     int pos = offset + 6;
/*  51 */     if ((nullBits & 0x2) != 0) { obj.definition = PortalDef.deserialize(buf, pos);
/*  52 */       pos += PortalDef.computeBytesConsumed(buf, pos); }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 6;
/*  60 */     if ((nullBits & 0x2) != 0) pos += PortalDef.computeBytesConsumed(buf, pos); 
/*  61 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  67 */     byte nullBits = 0;
/*  68 */     if (this.state != null) nullBits = (byte)(nullBits | 0x1); 
/*  69 */     if (this.definition != null) nullBits = (byte)(nullBits | 0x2); 
/*  70 */     buf.writeByte(nullBits);
/*     */     
/*  72 */     if (this.state != null) { this.state.serialize(buf); } else { buf.writeZero(5); }
/*     */     
/*  74 */     if (this.definition != null) this.definition.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  79 */     int size = 6;
/*  80 */     if (this.definition != null) size += this.definition.computeSize();
/*     */     
/*  82 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  86 */     if (buffer.readableBytes() - offset < 6) {
/*  87 */       return ValidationResult.error("Buffer too small: expected at least 6 bytes");
/*     */     }
/*     */     
/*  90 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  92 */     int pos = offset + 6;
/*     */     
/*  94 */     if ((nullBits & 0x2) != 0) {
/*  95 */       ValidationResult definitionResult = PortalDef.validateStructure(buffer, pos);
/*  96 */       if (!definitionResult.isValid()) {
/*  97 */         return ValidationResult.error("Invalid Definition: " + definitionResult.error());
/*     */       }
/*  99 */       pos += PortalDef.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 101 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdatePortal clone() {
/* 105 */     UpdatePortal copy = new UpdatePortal();
/* 106 */     copy.state = (this.state != null) ? this.state.clone() : null;
/* 107 */     copy.definition = (this.definition != null) ? this.definition.clone() : null;
/* 108 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdatePortal other;
/* 114 */     if (this == obj) return true; 
/* 115 */     if (obj instanceof UpdatePortal) { other = (UpdatePortal)obj; } else { return false; }
/* 116 */      return (Objects.equals(this.state, other.state) && Objects.equals(this.definition, other.definition));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 121 */     return Objects.hash(new Object[] { this.state, this.definition });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\UpdatePortal.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */