/*     */ package com.hypixel.hytale.protocol.packets.interface_;
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
/*     */ public class EditorBlocksChange
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 222;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 30;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 38;
/*     */   
/*     */   public int getId() {
/*  25 */     return 222;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int MAX_SIZE = 139264048;
/*     */   
/*     */   @Nullable
/*     */   public EditorSelection selection;
/*     */   
/*     */   @Nullable
/*     */   public BlockChange[] blocksChange;
/*     */   
/*     */   public EditorBlocksChange(@Nullable EditorSelection selection, @Nullable BlockChange[] blocksChange, @Nullable FluidChange[] fluidsChange, int blocksCount, boolean advancedPreview) {
/*  38 */     this.selection = selection;
/*  39 */     this.blocksChange = blocksChange;
/*  40 */     this.fluidsChange = fluidsChange;
/*  41 */     this.blocksCount = blocksCount;
/*  42 */     this.advancedPreview = advancedPreview;
/*     */   } @Nullable
/*     */   public FluidChange[] fluidsChange; public int blocksCount; public boolean advancedPreview; public EditorBlocksChange() {}
/*     */   public EditorBlocksChange(@Nonnull EditorBlocksChange other) {
/*  46 */     this.selection = other.selection;
/*  47 */     this.blocksChange = other.blocksChange;
/*  48 */     this.fluidsChange = other.fluidsChange;
/*  49 */     this.blocksCount = other.blocksCount;
/*  50 */     this.advancedPreview = other.advancedPreview;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EditorBlocksChange deserialize(@Nonnull ByteBuf buf, int offset) {
/*  55 */     EditorBlocksChange obj = new EditorBlocksChange();
/*  56 */     byte nullBits = buf.getByte(offset);
/*  57 */     if ((nullBits & 0x1) != 0) obj.selection = EditorSelection.deserialize(buf, offset + 1); 
/*  58 */     obj.blocksCount = buf.getIntLE(offset + 25);
/*  59 */     obj.advancedPreview = (buf.getByte(offset + 29) != 0);
/*     */     
/*  61 */     if ((nullBits & 0x2) != 0) {
/*  62 */       int varPos0 = offset + 38 + buf.getIntLE(offset + 30);
/*  63 */       int blocksChangeCount = VarInt.peek(buf, varPos0);
/*  64 */       if (blocksChangeCount < 0) throw ProtocolException.negativeLength("BlocksChange", blocksChangeCount); 
/*  65 */       if (blocksChangeCount > 4096000) throw ProtocolException.arrayTooLong("BlocksChange", blocksChangeCount, 4096000); 
/*  66 */       int varIntLen = VarInt.length(buf, varPos0);
/*  67 */       if ((varPos0 + varIntLen) + blocksChangeCount * 17L > buf.readableBytes())
/*  68 */         throw ProtocolException.bufferTooSmall("BlocksChange", varPos0 + varIntLen + blocksChangeCount * 17, buf.readableBytes()); 
/*  69 */       obj.blocksChange = new BlockChange[blocksChangeCount];
/*  70 */       int elemPos = varPos0 + varIntLen;
/*  71 */       for (int i = 0; i < blocksChangeCount; i++) {
/*  72 */         obj.blocksChange[i] = BlockChange.deserialize(buf, elemPos);
/*  73 */         elemPos += BlockChange.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  76 */     if ((nullBits & 0x4) != 0) {
/*  77 */       int varPos1 = offset + 38 + buf.getIntLE(offset + 34);
/*  78 */       int fluidsChangeCount = VarInt.peek(buf, varPos1);
/*  79 */       if (fluidsChangeCount < 0) throw ProtocolException.negativeLength("FluidsChange", fluidsChangeCount); 
/*  80 */       if (fluidsChangeCount > 4096000) throw ProtocolException.arrayTooLong("FluidsChange", fluidsChangeCount, 4096000); 
/*  81 */       int varIntLen = VarInt.length(buf, varPos1);
/*  82 */       if ((varPos1 + varIntLen) + fluidsChangeCount * 17L > buf.readableBytes())
/*  83 */         throw ProtocolException.bufferTooSmall("FluidsChange", varPos1 + varIntLen + fluidsChangeCount * 17, buf.readableBytes()); 
/*  84 */       obj.fluidsChange = new FluidChange[fluidsChangeCount];
/*  85 */       int elemPos = varPos1 + varIntLen;
/*  86 */       for (int i = 0; i < fluidsChangeCount; i++) {
/*  87 */         obj.fluidsChange[i] = FluidChange.deserialize(buf, elemPos);
/*  88 */         elemPos += FluidChange.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  96 */     byte nullBits = buf.getByte(offset);
/*  97 */     int maxEnd = 38;
/*  98 */     if ((nullBits & 0x2) != 0) {
/*  99 */       int fieldOffset0 = buf.getIntLE(offset + 30);
/* 100 */       int pos0 = offset + 38 + fieldOffset0;
/* 101 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/* 102 */       for (int i = 0; i < arrLen; ) { pos0 += BlockChange.computeBytesConsumed(buf, pos0); i++; }
/* 103 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 105 */     if ((nullBits & 0x4) != 0) {
/* 106 */       int fieldOffset1 = buf.getIntLE(offset + 34);
/* 107 */       int pos1 = offset + 38 + fieldOffset1;
/* 108 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 109 */       for (int i = 0; i < arrLen; ) { pos1 += FluidChange.computeBytesConsumed(buf, pos1); i++; }
/* 110 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 112 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 118 */     int startPos = buf.writerIndex();
/* 119 */     byte nullBits = 0;
/* 120 */     if (this.selection != null) nullBits = (byte)(nullBits | 0x1); 
/* 121 */     if (this.blocksChange != null) nullBits = (byte)(nullBits | 0x2); 
/* 122 */     if (this.fluidsChange != null) nullBits = (byte)(nullBits | 0x4); 
/* 123 */     buf.writeByte(nullBits);
/*     */     
/* 125 */     if (this.selection != null) { this.selection.serialize(buf); } else { buf.writeZero(24); }
/* 126 */      buf.writeIntLE(this.blocksCount);
/* 127 */     buf.writeByte(this.advancedPreview ? 1 : 0);
/*     */     
/* 129 */     int blocksChangeOffsetSlot = buf.writerIndex();
/* 130 */     buf.writeIntLE(0);
/* 131 */     int fluidsChangeOffsetSlot = buf.writerIndex();
/* 132 */     buf.writeIntLE(0);
/*     */     
/* 134 */     int varBlockStart = buf.writerIndex();
/* 135 */     if (this.blocksChange != null) {
/* 136 */       buf.setIntLE(blocksChangeOffsetSlot, buf.writerIndex() - varBlockStart);
/* 137 */       if (this.blocksChange.length > 4096000) throw ProtocolException.arrayTooLong("BlocksChange", this.blocksChange.length, 4096000);  VarInt.write(buf, this.blocksChange.length); for (BlockChange item : this.blocksChange) item.serialize(buf); 
/*     */     } else {
/* 139 */       buf.setIntLE(blocksChangeOffsetSlot, -1);
/*     */     } 
/* 141 */     if (this.fluidsChange != null) {
/* 142 */       buf.setIntLE(fluidsChangeOffsetSlot, buf.writerIndex() - varBlockStart);
/* 143 */       if (this.fluidsChange.length > 4096000) throw ProtocolException.arrayTooLong("FluidsChange", this.fluidsChange.length, 4096000);  VarInt.write(buf, this.fluidsChange.length); for (FluidChange item : this.fluidsChange) item.serialize(buf); 
/*     */     } else {
/* 145 */       buf.setIntLE(fluidsChangeOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 151 */     int size = 38;
/* 152 */     if (this.blocksChange != null) size += VarInt.size(this.blocksChange.length) + this.blocksChange.length * 17; 
/* 153 */     if (this.fluidsChange != null) size += VarInt.size(this.fluidsChange.length) + this.fluidsChange.length * 17;
/*     */     
/* 155 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 159 */     if (buffer.readableBytes() - offset < 38) {
/* 160 */       return ValidationResult.error("Buffer too small: expected at least 38 bytes");
/*     */     }
/*     */     
/* 163 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 166 */     if ((nullBits & 0x2) != 0) {
/* 167 */       int blocksChangeOffset = buffer.getIntLE(offset + 30);
/* 168 */       if (blocksChangeOffset < 0) {
/* 169 */         return ValidationResult.error("Invalid offset for BlocksChange");
/*     */       }
/* 171 */       int pos = offset + 38 + blocksChangeOffset;
/* 172 */       if (pos >= buffer.writerIndex()) {
/* 173 */         return ValidationResult.error("Offset out of bounds for BlocksChange");
/*     */       }
/* 175 */       int blocksChangeCount = VarInt.peek(buffer, pos);
/* 176 */       if (blocksChangeCount < 0) {
/* 177 */         return ValidationResult.error("Invalid array count for BlocksChange");
/*     */       }
/* 179 */       if (blocksChangeCount > 4096000) {
/* 180 */         return ValidationResult.error("BlocksChange exceeds max length 4096000");
/*     */       }
/* 182 */       pos += VarInt.length(buffer, pos);
/* 183 */       pos += blocksChangeCount * 17;
/* 184 */       if (pos > buffer.writerIndex()) {
/* 185 */         return ValidationResult.error("Buffer overflow reading BlocksChange");
/*     */       }
/*     */     } 
/*     */     
/* 189 */     if ((nullBits & 0x4) != 0) {
/* 190 */       int fluidsChangeOffset = buffer.getIntLE(offset + 34);
/* 191 */       if (fluidsChangeOffset < 0) {
/* 192 */         return ValidationResult.error("Invalid offset for FluidsChange");
/*     */       }
/* 194 */       int pos = offset + 38 + fluidsChangeOffset;
/* 195 */       if (pos >= buffer.writerIndex()) {
/* 196 */         return ValidationResult.error("Offset out of bounds for FluidsChange");
/*     */       }
/* 198 */       int fluidsChangeCount = VarInt.peek(buffer, pos);
/* 199 */       if (fluidsChangeCount < 0) {
/* 200 */         return ValidationResult.error("Invalid array count for FluidsChange");
/*     */       }
/* 202 */       if (fluidsChangeCount > 4096000) {
/* 203 */         return ValidationResult.error("FluidsChange exceeds max length 4096000");
/*     */       }
/* 205 */       pos += VarInt.length(buffer, pos);
/* 206 */       pos += fluidsChangeCount * 17;
/* 207 */       if (pos > buffer.writerIndex()) {
/* 208 */         return ValidationResult.error("Buffer overflow reading FluidsChange");
/*     */       }
/*     */     } 
/* 211 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public EditorBlocksChange clone() {
/* 215 */     EditorBlocksChange copy = new EditorBlocksChange();
/* 216 */     copy.selection = (this.selection != null) ? this.selection.clone() : null;
/* 217 */     copy.blocksChange = (this.blocksChange != null) ? (BlockChange[])Arrays.<BlockChange>stream(this.blocksChange).map(e -> e.clone()).toArray(x$0 -> new BlockChange[x$0]) : null;
/* 218 */     copy.fluidsChange = (this.fluidsChange != null) ? (FluidChange[])Arrays.<FluidChange>stream(this.fluidsChange).map(e -> e.clone()).toArray(x$0 -> new FluidChange[x$0]) : null;
/* 219 */     copy.blocksCount = this.blocksCount;
/* 220 */     copy.advancedPreview = this.advancedPreview;
/* 221 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     EditorBlocksChange other;
/* 227 */     if (this == obj) return true; 
/* 228 */     if (obj instanceof EditorBlocksChange) { other = (EditorBlocksChange)obj; } else { return false; }
/* 229 */      return (Objects.equals(this.selection, other.selection) && Arrays.equals((Object[])this.blocksChange, (Object[])other.blocksChange) && Arrays.equals((Object[])this.fluidsChange, (Object[])other.fluidsChange) && this.blocksCount == other.blocksCount && this.advancedPreview == other.advancedPreview);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 234 */     int result = 1;
/* 235 */     result = 31 * result + Objects.hashCode(this.selection);
/* 236 */     result = 31 * result + Arrays.hashCode((Object[])this.blocksChange);
/* 237 */     result = 31 * result + Arrays.hashCode((Object[])this.fluidsChange);
/* 238 */     result = 31 * result + Integer.hashCode(this.blocksCount);
/* 239 */     result = 31 * result + Boolean.hashCode(this.advancedPreview);
/* 240 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\EditorBlocksChange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */