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
/*     */ public class UpdateVisibleHudComponents
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 230;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 4096006;
/*     */   @Nullable
/*     */   public HudComponent[] visibleComponents;
/*     */   
/*     */   public int getId() {
/*  25 */     return 230;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdateVisibleHudComponents() {}
/*     */ 
/*     */   
/*     */   public UpdateVisibleHudComponents(@Nullable HudComponent[] visibleComponents) {
/*  34 */     this.visibleComponents = visibleComponents;
/*     */   }
/*     */   
/*     */   public UpdateVisibleHudComponents(@Nonnull UpdateVisibleHudComponents other) {
/*  38 */     this.visibleComponents = other.visibleComponents;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateVisibleHudComponents deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     UpdateVisibleHudComponents obj = new UpdateVisibleHudComponents();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int visibleComponentsCount = VarInt.peek(buf, pos);
/*  48 */       if (visibleComponentsCount < 0) throw ProtocolException.negativeLength("VisibleComponents", visibleComponentsCount); 
/*  49 */       if (visibleComponentsCount > 4096000) throw ProtocolException.arrayTooLong("VisibleComponents", visibleComponentsCount, 4096000); 
/*  50 */       int visibleComponentsVarLen = VarInt.size(visibleComponentsCount);
/*  51 */       if ((pos + visibleComponentsVarLen) + visibleComponentsCount * 1L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("VisibleComponents", pos + visibleComponentsVarLen + visibleComponentsCount * 1, buf.readableBytes()); 
/*  53 */       pos += visibleComponentsVarLen;
/*  54 */       obj.visibleComponents = new HudComponent[visibleComponentsCount];
/*  55 */       for (int i = 0; i < visibleComponentsCount; i++) {
/*  56 */         obj.visibleComponents[i] = HudComponent.fromValue(buf.getByte(pos)); pos++;
/*     */       }  }
/*     */     
/*  59 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  63 */     byte nullBits = buf.getByte(offset);
/*  64 */     int pos = offset + 1;
/*  65 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 1; }
/*  66 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  72 */     byte nullBits = 0;
/*  73 */     if (this.visibleComponents != null) nullBits = (byte)(nullBits | 0x1); 
/*  74 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  77 */     if (this.visibleComponents != null) { if (this.visibleComponents.length > 4096000) throw ProtocolException.arrayTooLong("VisibleComponents", this.visibleComponents.length, 4096000);  VarInt.write(buf, this.visibleComponents.length); for (HudComponent item : this.visibleComponents) buf.writeByte(item.getValue());  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  82 */     int size = 1;
/*  83 */     if (this.visibleComponents != null) size += VarInt.size(this.visibleComponents.length) + this.visibleComponents.length * 1;
/*     */     
/*  85 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  89 */     if (buffer.readableBytes() - offset < 1) {
/*  90 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  93 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  95 */     int pos = offset + 1;
/*     */     
/*  97 */     if ((nullBits & 0x1) != 0) {
/*  98 */       int visibleComponentsCount = VarInt.peek(buffer, pos);
/*  99 */       if (visibleComponentsCount < 0) {
/* 100 */         return ValidationResult.error("Invalid array count for VisibleComponents");
/*     */       }
/* 102 */       if (visibleComponentsCount > 4096000) {
/* 103 */         return ValidationResult.error("VisibleComponents exceeds max length 4096000");
/*     */       }
/* 105 */       pos += VarInt.length(buffer, pos);
/* 106 */       pos += visibleComponentsCount * 1;
/* 107 */       if (pos > buffer.writerIndex()) {
/* 108 */         return ValidationResult.error("Buffer overflow reading VisibleComponents");
/*     */       }
/*     */     } 
/* 111 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateVisibleHudComponents clone() {
/* 115 */     UpdateVisibleHudComponents copy = new UpdateVisibleHudComponents();
/* 116 */     copy.visibleComponents = (this.visibleComponents != null) ? Arrays.<HudComponent>copyOf(this.visibleComponents, this.visibleComponents.length) : null;
/* 117 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateVisibleHudComponents other;
/* 123 */     if (this == obj) return true; 
/* 124 */     if (obj instanceof UpdateVisibleHudComponents) { other = (UpdateVisibleHudComponents)obj; } else { return false; }
/* 125 */      return Arrays.equals((Object[])this.visibleComponents, (Object[])other.visibleComponents);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 130 */     int result = 1;
/* 131 */     result = 31 * result + Arrays.hashCode((Object[])this.visibleComponents);
/* 132 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\UpdateVisibleHudComponents.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */