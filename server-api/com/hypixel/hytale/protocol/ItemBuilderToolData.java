/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolState;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ItemBuilderToolData {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String[] ui;
/*     */   @Nullable
/*     */   public BuilderToolState[] tools;
/*     */   
/*     */   public ItemBuilderToolData() {}
/*     */   
/*     */   public ItemBuilderToolData(@Nullable String[] ui, @Nullable BuilderToolState[] tools) {
/*  27 */     this.ui = ui;
/*  28 */     this.tools = tools;
/*     */   }
/*     */   
/*     */   public ItemBuilderToolData(@Nonnull ItemBuilderToolData other) {
/*  32 */     this.ui = other.ui;
/*  33 */     this.tools = other.tools;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemBuilderToolData deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     ItemBuilderToolData obj = new ItemBuilderToolData();
/*  39 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  41 */     if ((nullBits & 0x1) != 0) {
/*  42 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  43 */       int uiCount = VarInt.peek(buf, varPos0);
/*  44 */       if (uiCount < 0) throw ProtocolException.negativeLength("Ui", uiCount); 
/*  45 */       if (uiCount > 4096000) throw ProtocolException.arrayTooLong("Ui", uiCount, 4096000); 
/*  46 */       int varIntLen = VarInt.length(buf, varPos0);
/*  47 */       if ((varPos0 + varIntLen) + uiCount * 1L > buf.readableBytes())
/*  48 */         throw ProtocolException.bufferTooSmall("Ui", varPos0 + varIntLen + uiCount * 1, buf.readableBytes()); 
/*  49 */       obj.ui = new String[uiCount];
/*  50 */       int elemPos = varPos0 + varIntLen;
/*  51 */       for (int i = 0; i < uiCount; i++) {
/*  52 */         int strLen = VarInt.peek(buf, elemPos);
/*  53 */         if (strLen < 0) throw ProtocolException.negativeLength("ui[" + i + "]", strLen); 
/*  54 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("ui[" + i + "]", strLen, 4096000); 
/*  55 */         int strVarLen = VarInt.length(buf, elemPos);
/*  56 */         obj.ui[i] = PacketIO.readVarString(buf, elemPos);
/*  57 */         elemPos += strVarLen + strLen;
/*     */       } 
/*     */     } 
/*  60 */     if ((nullBits & 0x2) != 0) {
/*  61 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  62 */       int toolsCount = VarInt.peek(buf, varPos1);
/*  63 */       if (toolsCount < 0) throw ProtocolException.negativeLength("Tools", toolsCount); 
/*  64 */       if (toolsCount > 4096000) throw ProtocolException.arrayTooLong("Tools", toolsCount, 4096000); 
/*  65 */       int varIntLen = VarInt.length(buf, varPos1);
/*  66 */       if ((varPos1 + varIntLen) + toolsCount * 2L > buf.readableBytes())
/*  67 */         throw ProtocolException.bufferTooSmall("Tools", varPos1 + varIntLen + toolsCount * 2, buf.readableBytes()); 
/*  68 */       obj.tools = new BuilderToolState[toolsCount];
/*  69 */       int elemPos = varPos1 + varIntLen;
/*  70 */       for (int i = 0; i < toolsCount; i++) {
/*  71 */         obj.tools[i] = BuilderToolState.deserialize(buf, elemPos);
/*  72 */         elemPos += BuilderToolState.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  80 */     byte nullBits = buf.getByte(offset);
/*  81 */     int maxEnd = 9;
/*  82 */     if ((nullBits & 0x1) != 0) {
/*  83 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  84 */       int pos0 = offset + 9 + fieldOffset0;
/*  85 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/*  86 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl; i++; }
/*  87 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  89 */     if ((nullBits & 0x2) != 0) {
/*  90 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  91 */       int pos1 = offset + 9 + fieldOffset1;
/*  92 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  93 */       for (int i = 0; i < arrLen; ) { pos1 += BuilderToolState.computeBytesConsumed(buf, pos1); i++; }
/*  94 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  96 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 101 */     int startPos = buf.writerIndex();
/* 102 */     byte nullBits = 0;
/* 103 */     if (this.ui != null) nullBits = (byte)(nullBits | 0x1); 
/* 104 */     if (this.tools != null) nullBits = (byte)(nullBits | 0x2); 
/* 105 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 108 */     int uiOffsetSlot = buf.writerIndex();
/* 109 */     buf.writeIntLE(0);
/* 110 */     int toolsOffsetSlot = buf.writerIndex();
/* 111 */     buf.writeIntLE(0);
/*     */     
/* 113 */     int varBlockStart = buf.writerIndex();
/* 114 */     if (this.ui != null) {
/* 115 */       buf.setIntLE(uiOffsetSlot, buf.writerIndex() - varBlockStart);
/* 116 */       if (this.ui.length > 4096000) throw ProtocolException.arrayTooLong("Ui", this.ui.length, 4096000);  VarInt.write(buf, this.ui.length); for (String item : this.ui) PacketIO.writeVarString(buf, item, 4096000); 
/*     */     } else {
/* 118 */       buf.setIntLE(uiOffsetSlot, -1);
/*     */     } 
/* 120 */     if (this.tools != null) {
/* 121 */       buf.setIntLE(toolsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 122 */       if (this.tools.length > 4096000) throw ProtocolException.arrayTooLong("Tools", this.tools.length, 4096000);  VarInt.write(buf, this.tools.length); for (BuilderToolState item : this.tools) item.serialize(buf); 
/*     */     } else {
/* 124 */       buf.setIntLE(toolsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 130 */     int size = 9;
/* 131 */     if (this.ui != null) {
/* 132 */       int uiSize = 0;
/* 133 */       for (String elem : this.ui) uiSize += PacketIO.stringSize(elem); 
/* 134 */       size += VarInt.size(this.ui.length) + uiSize;
/*     */     } 
/* 136 */     if (this.tools != null) {
/* 137 */       int toolsSize = 0;
/* 138 */       for (BuilderToolState elem : this.tools) toolsSize += elem.computeSize(); 
/* 139 */       size += VarInt.size(this.tools.length) + toolsSize;
/*     */     } 
/*     */     
/* 142 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 146 */     if (buffer.readableBytes() - offset < 9) {
/* 147 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 150 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 153 */     if ((nullBits & 0x1) != 0) {
/* 154 */       int uiOffset = buffer.getIntLE(offset + 1);
/* 155 */       if (uiOffset < 0) {
/* 156 */         return ValidationResult.error("Invalid offset for Ui");
/*     */       }
/* 158 */       int pos = offset + 9 + uiOffset;
/* 159 */       if (pos >= buffer.writerIndex()) {
/* 160 */         return ValidationResult.error("Offset out of bounds for Ui");
/*     */       }
/* 162 */       int uiCount = VarInt.peek(buffer, pos);
/* 163 */       if (uiCount < 0) {
/* 164 */         return ValidationResult.error("Invalid array count for Ui");
/*     */       }
/* 166 */       if (uiCount > 4096000) {
/* 167 */         return ValidationResult.error("Ui exceeds max length 4096000");
/*     */       }
/* 169 */       pos += VarInt.length(buffer, pos);
/* 170 */       for (int i = 0; i < uiCount; i++) {
/* 171 */         int strLen = VarInt.peek(buffer, pos);
/* 172 */         if (strLen < 0) {
/* 173 */           return ValidationResult.error("Invalid string length in Ui");
/*     */         }
/* 175 */         pos += VarInt.length(buffer, pos);
/* 176 */         pos += strLen;
/* 177 */         if (pos > buffer.writerIndex()) {
/* 178 */           return ValidationResult.error("Buffer overflow reading string in Ui");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 183 */     if ((nullBits & 0x2) != 0) {
/* 184 */       int toolsOffset = buffer.getIntLE(offset + 5);
/* 185 */       if (toolsOffset < 0) {
/* 186 */         return ValidationResult.error("Invalid offset for Tools");
/*     */       }
/* 188 */       int pos = offset + 9 + toolsOffset;
/* 189 */       if (pos >= buffer.writerIndex()) {
/* 190 */         return ValidationResult.error("Offset out of bounds for Tools");
/*     */       }
/* 192 */       int toolsCount = VarInt.peek(buffer, pos);
/* 193 */       if (toolsCount < 0) {
/* 194 */         return ValidationResult.error("Invalid array count for Tools");
/*     */       }
/* 196 */       if (toolsCount > 4096000) {
/* 197 */         return ValidationResult.error("Tools exceeds max length 4096000");
/*     */       }
/* 199 */       pos += VarInt.length(buffer, pos);
/* 200 */       for (int i = 0; i < toolsCount; i++) {
/* 201 */         ValidationResult structResult = BuilderToolState.validateStructure(buffer, pos);
/* 202 */         if (!structResult.isValid()) {
/* 203 */           return ValidationResult.error("Invalid BuilderToolState in Tools[" + i + "]: " + structResult.error());
/*     */         }
/* 205 */         pos += BuilderToolState.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 208 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemBuilderToolData clone() {
/* 212 */     ItemBuilderToolData copy = new ItemBuilderToolData();
/* 213 */     copy.ui = (this.ui != null) ? Arrays.<String>copyOf(this.ui, this.ui.length) : null;
/* 214 */     copy.tools = (this.tools != null) ? (BuilderToolState[])Arrays.<BuilderToolState>stream(this.tools).map(e -> e.clone()).toArray(x$0 -> new BuilderToolState[x$0]) : null;
/* 215 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemBuilderToolData other;
/* 221 */     if (this == obj) return true; 
/* 222 */     if (obj instanceof ItemBuilderToolData) { other = (ItemBuilderToolData)obj; } else { return false; }
/* 223 */      return (Arrays.equals((Object[])this.ui, (Object[])other.ui) && Arrays.equals((Object[])this.tools, (Object[])other.tools));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 228 */     int result = 1;
/* 229 */     result = 31 * result + Arrays.hashCode((Object[])this.ui);
/* 230 */     result = 31 * result + Arrays.hashCode((Object[])this.tools);
/* 231 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemBuilderToolData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */