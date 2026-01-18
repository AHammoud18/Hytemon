/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.MovementType;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.UpdateType;
/*     */ import com.hypixel.hytale.protocol.ViewBobbing;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class UpdateViewBobbing
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 76;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  28 */     return 76;
/*     */   }
/*     */   @Nonnull
/*  31 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   @Nullable
/*     */   public Map<MovementType, ViewBobbing> profiles;
/*     */ 
/*     */   
/*     */   public UpdateViewBobbing(@Nonnull UpdateType type, @Nullable Map<MovementType, ViewBobbing> profiles) {
/*  38 */     this.type = type;
/*  39 */     this.profiles = profiles;
/*     */   }
/*     */   
/*     */   public UpdateViewBobbing(@Nonnull UpdateViewBobbing other) {
/*  43 */     this.type = other.type;
/*  44 */     this.profiles = other.profiles;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateViewBobbing deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     UpdateViewBobbing obj = new UpdateViewBobbing();
/*  50 */     byte nullBits = buf.getByte(offset);
/*  51 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  53 */     int pos = offset + 2;
/*  54 */     if ((nullBits & 0x1) != 0) { int profilesCount = VarInt.peek(buf, pos);
/*  55 */       if (profilesCount < 0) throw ProtocolException.negativeLength("Profiles", profilesCount); 
/*  56 */       if (profilesCount > 4096000) throw ProtocolException.dictionaryTooLarge("Profiles", profilesCount, 4096000); 
/*  57 */       pos += VarInt.size(profilesCount);
/*  58 */       obj.profiles = new HashMap<>(profilesCount);
/*  59 */       for (int i = 0; i < profilesCount; i++) {
/*  60 */         MovementType key = MovementType.fromValue(buf.getByte(pos)); pos++;
/*  61 */         ViewBobbing val = ViewBobbing.deserialize(buf, pos);
/*  62 */         pos += ViewBobbing.computeBytesConsumed(buf, pos);
/*  63 */         if (obj.profiles.put(key, val) != null)
/*  64 */           throw ProtocolException.duplicateKey("profiles", key); 
/*     */       }  }
/*     */     
/*  67 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  71 */     byte nullBits = buf.getByte(offset);
/*  72 */     int pos = offset + 2;
/*  73 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  74 */       for (int i = 0; i < dictLen; ) { pos = ++pos + ViewBobbing.computeBytesConsumed(buf, pos); i++; }  }
/*  75 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  81 */     byte nullBits = 0;
/*  82 */     if (this.profiles != null) nullBits = (byte)(nullBits | 0x1); 
/*  83 */     buf.writeByte(nullBits);
/*     */     
/*  85 */     buf.writeByte(this.type.getValue());
/*     */     
/*  87 */     if (this.profiles != null) { if (this.profiles.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Profiles", this.profiles.size(), 4096000);  VarInt.write(buf, this.profiles.size()); for (Map.Entry<MovementType, ViewBobbing> e : this.profiles.entrySet()) { buf.writeByte(((MovementType)e.getKey()).getValue()); ((ViewBobbing)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  92 */     int size = 2;
/*  93 */     if (this.profiles != null) {
/*  94 */       int profilesSize = 0;
/*  95 */       for (Map.Entry<MovementType, ViewBobbing> kvp : this.profiles.entrySet()) profilesSize += 1 + ((ViewBobbing)kvp.getValue()).computeSize(); 
/*  96 */       size += VarInt.size(this.profiles.size()) + profilesSize;
/*     */     } 
/*     */     
/*  99 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 103 */     if (buffer.readableBytes() - offset < 2) {
/* 104 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */     
/* 107 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 109 */     int pos = offset + 2;
/*     */     
/* 111 */     if ((nullBits & 0x1) != 0) {
/* 112 */       int profilesCount = VarInt.peek(buffer, pos);
/* 113 */       if (profilesCount < 0) {
/* 114 */         return ValidationResult.error("Invalid dictionary count for Profiles");
/*     */       }
/* 116 */       if (profilesCount > 4096000) {
/* 117 */         return ValidationResult.error("Profiles exceeds max length 4096000");
/*     */       }
/* 119 */       pos += VarInt.length(buffer, pos);
/* 120 */       for (int i = 0; i < profilesCount; i++) {
/* 121 */         pos++;
/*     */         
/* 123 */         pos += ViewBobbing.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 127 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateViewBobbing clone() {
/* 131 */     UpdateViewBobbing copy = new UpdateViewBobbing();
/* 132 */     copy.type = this.type;
/* 133 */     if (this.profiles != null) {
/* 134 */       Map<MovementType, ViewBobbing> m = new HashMap<>();
/* 135 */       for (Map.Entry<MovementType, ViewBobbing> e : this.profiles.entrySet()) m.put(e.getKey(), ((ViewBobbing)e.getValue()).clone()); 
/* 136 */       copy.profiles = m;
/*     */     } 
/* 138 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateViewBobbing other;
/* 144 */     if (this == obj) return true; 
/* 145 */     if (obj instanceof UpdateViewBobbing) { other = (UpdateViewBobbing)obj; } else { return false; }
/* 146 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.profiles, other.profiles));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 151 */     return Objects.hash(new Object[] { this.type, this.profiles });
/*     */   }
/*     */   
/*     */   public UpdateViewBobbing() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateViewBobbing.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */