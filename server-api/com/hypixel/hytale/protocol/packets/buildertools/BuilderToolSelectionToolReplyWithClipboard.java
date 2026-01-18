/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.BlockChange;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.FluidChange;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuilderToolSelectionToolReplyWithClipboard
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 411;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   
/*     */   public int getId() {
/*  26 */     return 411;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 2; public static final int VARIABLE_BLOCK_START = 9; public static final int MAX_SIZE = 139264019; @Nullable
/*     */   public BlockChange[] blocksChange;
/*     */   @Nullable
/*     */   public FluidChange[] fluidsChange;
/*     */   
/*     */   public BuilderToolSelectionToolReplyWithClipboard() {}
/*     */   
/*     */   public BuilderToolSelectionToolReplyWithClipboard(@Nullable BlockChange[] blocksChange, @Nullable FluidChange[] fluidsChange) {
/*  36 */     this.blocksChange = blocksChange;
/*  37 */     this.fluidsChange = fluidsChange;
/*     */   }
/*     */   
/*     */   public BuilderToolSelectionToolReplyWithClipboard(@Nonnull BuilderToolSelectionToolReplyWithClipboard other) {
/*  41 */     this.blocksChange = other.blocksChange;
/*  42 */     this.fluidsChange = other.fluidsChange;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolSelectionToolReplyWithClipboard deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     BuilderToolSelectionToolReplyWithClipboard obj = new BuilderToolSelectionToolReplyWithClipboard();
/*  48 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  50 */     if ((nullBits & 0x1) != 0) {
/*  51 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  52 */       int blocksChangeCount = VarInt.peek(buf, varPos0);
/*  53 */       if (blocksChangeCount < 0) throw ProtocolException.negativeLength("BlocksChange", blocksChangeCount); 
/*  54 */       if (blocksChangeCount > 4096000) throw ProtocolException.arrayTooLong("BlocksChange", blocksChangeCount, 4096000); 
/*  55 */       int varIntLen = VarInt.length(buf, varPos0);
/*  56 */       if ((varPos0 + varIntLen) + blocksChangeCount * 17L > buf.readableBytes())
/*  57 */         throw ProtocolException.bufferTooSmall("BlocksChange", varPos0 + varIntLen + blocksChangeCount * 17, buf.readableBytes()); 
/*  58 */       obj.blocksChange = new BlockChange[blocksChangeCount];
/*  59 */       int elemPos = varPos0 + varIntLen;
/*  60 */       for (int i = 0; i < blocksChangeCount; i++) {
/*  61 */         obj.blocksChange[i] = BlockChange.deserialize(buf, elemPos);
/*  62 */         elemPos += BlockChange.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  65 */     if ((nullBits & 0x2) != 0) {
/*  66 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  67 */       int fluidsChangeCount = VarInt.peek(buf, varPos1);
/*  68 */       if (fluidsChangeCount < 0) throw ProtocolException.negativeLength("FluidsChange", fluidsChangeCount); 
/*  69 */       if (fluidsChangeCount > 4096000) throw ProtocolException.arrayTooLong("FluidsChange", fluidsChangeCount, 4096000); 
/*  70 */       int varIntLen = VarInt.length(buf, varPos1);
/*  71 */       if ((varPos1 + varIntLen) + fluidsChangeCount * 17L > buf.readableBytes())
/*  72 */         throw ProtocolException.bufferTooSmall("FluidsChange", varPos1 + varIntLen + fluidsChangeCount * 17, buf.readableBytes()); 
/*  73 */       obj.fluidsChange = new FluidChange[fluidsChangeCount];
/*  74 */       int elemPos = varPos1 + varIntLen;
/*  75 */       for (int i = 0; i < fluidsChangeCount; i++) {
/*  76 */         obj.fluidsChange[i] = FluidChange.deserialize(buf, elemPos);
/*  77 */         elemPos += FluidChange.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  81 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  85 */     byte nullBits = buf.getByte(offset);
/*  86 */     int maxEnd = 9;
/*  87 */     if ((nullBits & 0x1) != 0) {
/*  88 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  89 */       int pos0 = offset + 9 + fieldOffset0;
/*  90 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/*  91 */       for (int i = 0; i < arrLen; ) { pos0 += BlockChange.computeBytesConsumed(buf, pos0); i++; }
/*  92 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  94 */     if ((nullBits & 0x2) != 0) {
/*  95 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  96 */       int pos1 = offset + 9 + fieldOffset1;
/*  97 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  98 */       for (int i = 0; i < arrLen; ) { pos1 += FluidChange.computeBytesConsumed(buf, pos1); i++; }
/*  99 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 101 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 107 */     int startPos = buf.writerIndex();
/* 108 */     byte nullBits = 0;
/* 109 */     if (this.blocksChange != null) nullBits = (byte)(nullBits | 0x1); 
/* 110 */     if (this.fluidsChange != null) nullBits = (byte)(nullBits | 0x2); 
/* 111 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 114 */     int blocksChangeOffsetSlot = buf.writerIndex();
/* 115 */     buf.writeIntLE(0);
/* 116 */     int fluidsChangeOffsetSlot = buf.writerIndex();
/* 117 */     buf.writeIntLE(0);
/*     */     
/* 119 */     int varBlockStart = buf.writerIndex();
/* 120 */     if (this.blocksChange != null) {
/* 121 */       buf.setIntLE(blocksChangeOffsetSlot, buf.writerIndex() - varBlockStart);
/* 122 */       if (this.blocksChange.length > 4096000) throw ProtocolException.arrayTooLong("BlocksChange", this.blocksChange.length, 4096000);  VarInt.write(buf, this.blocksChange.length); for (BlockChange item : this.blocksChange) item.serialize(buf); 
/*     */     } else {
/* 124 */       buf.setIntLE(blocksChangeOffsetSlot, -1);
/*     */     } 
/* 126 */     if (this.fluidsChange != null) {
/* 127 */       buf.setIntLE(fluidsChangeOffsetSlot, buf.writerIndex() - varBlockStart);
/* 128 */       if (this.fluidsChange.length > 4096000) throw ProtocolException.arrayTooLong("FluidsChange", this.fluidsChange.length, 4096000);  VarInt.write(buf, this.fluidsChange.length); for (FluidChange item : this.fluidsChange) item.serialize(buf); 
/*     */     } else {
/* 130 */       buf.setIntLE(fluidsChangeOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 136 */     int size = 9;
/* 137 */     if (this.blocksChange != null) size += VarInt.size(this.blocksChange.length) + this.blocksChange.length * 17; 
/* 138 */     if (this.fluidsChange != null) size += VarInt.size(this.fluidsChange.length) + this.fluidsChange.length * 17;
/*     */     
/* 140 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 144 */     if (buffer.readableBytes() - offset < 9) {
/* 145 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 148 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 151 */     if ((nullBits & 0x1) != 0) {
/* 152 */       int blocksChangeOffset = buffer.getIntLE(offset + 1);
/* 153 */       if (blocksChangeOffset < 0) {
/* 154 */         return ValidationResult.error("Invalid offset for BlocksChange");
/*     */       }
/* 156 */       int pos = offset + 9 + blocksChangeOffset;
/* 157 */       if (pos >= buffer.writerIndex()) {
/* 158 */         return ValidationResult.error("Offset out of bounds for BlocksChange");
/*     */       }
/* 160 */       int blocksChangeCount = VarInt.peek(buffer, pos);
/* 161 */       if (blocksChangeCount < 0) {
/* 162 */         return ValidationResult.error("Invalid array count for BlocksChange");
/*     */       }
/* 164 */       if (blocksChangeCount > 4096000) {
/* 165 */         return ValidationResult.error("BlocksChange exceeds max length 4096000");
/*     */       }
/* 167 */       pos += VarInt.length(buffer, pos);
/* 168 */       pos += blocksChangeCount * 17;
/* 169 */       if (pos > buffer.writerIndex()) {
/* 170 */         return ValidationResult.error("Buffer overflow reading BlocksChange");
/*     */       }
/*     */     } 
/*     */     
/* 174 */     if ((nullBits & 0x2) != 0) {
/* 175 */       int fluidsChangeOffset = buffer.getIntLE(offset + 5);
/* 176 */       if (fluidsChangeOffset < 0) {
/* 177 */         return ValidationResult.error("Invalid offset for FluidsChange");
/*     */       }
/* 179 */       int pos = offset + 9 + fluidsChangeOffset;
/* 180 */       if (pos >= buffer.writerIndex()) {
/* 181 */         return ValidationResult.error("Offset out of bounds for FluidsChange");
/*     */       }
/* 183 */       int fluidsChangeCount = VarInt.peek(buffer, pos);
/* 184 */       if (fluidsChangeCount < 0) {
/* 185 */         return ValidationResult.error("Invalid array count for FluidsChange");
/*     */       }
/* 187 */       if (fluidsChangeCount > 4096000) {
/* 188 */         return ValidationResult.error("FluidsChange exceeds max length 4096000");
/*     */       }
/* 190 */       pos += VarInt.length(buffer, pos);
/* 191 */       pos += fluidsChangeCount * 17;
/* 192 */       if (pos > buffer.writerIndex()) {
/* 193 */         return ValidationResult.error("Buffer overflow reading FluidsChange");
/*     */       }
/*     */     } 
/* 196 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolSelectionToolReplyWithClipboard clone() {
/* 200 */     BuilderToolSelectionToolReplyWithClipboard copy = new BuilderToolSelectionToolReplyWithClipboard();
/* 201 */     copy.blocksChange = (this.blocksChange != null) ? (BlockChange[])Arrays.<BlockChange>stream(this.blocksChange).map(e -> e.clone()).toArray(x$0 -> new BlockChange[x$0]) : null;
/* 202 */     copy.fluidsChange = (this.fluidsChange != null) ? (FluidChange[])Arrays.<FluidChange>stream(this.fluidsChange).map(e -> e.clone()).toArray(x$0 -> new FluidChange[x$0]) : null;
/* 203 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolSelectionToolReplyWithClipboard other;
/* 209 */     if (this == obj) return true; 
/* 210 */     if (obj instanceof BuilderToolSelectionToolReplyWithClipboard) { other = (BuilderToolSelectionToolReplyWithClipboard)obj; } else { return false; }
/* 211 */      return (Arrays.equals((Object[])this.blocksChange, (Object[])other.blocksChange) && Arrays.equals((Object[])this.fluidsChange, (Object[])other.fluidsChange));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 216 */     int result = 1;
/* 217 */     result = 31 * result + Arrays.hashCode((Object[])this.blocksChange);
/* 218 */     result = 31 * result + Arrays.hashCode((Object[])this.fluidsChange);
/* 219 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolSelectionToolReplyWithClipboard.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */