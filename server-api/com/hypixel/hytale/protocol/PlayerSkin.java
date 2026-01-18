/*      */ package com.hypixel.hytale.protocol;
/*      */ 
/*      */ import com.hypixel.hytale.protocol.io.PacketIO;
/*      */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*      */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*      */ import com.hypixel.hytale.protocol.io.VarInt;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import java.util.Objects;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PlayerSkin
/*      */ {
/*      */   public static final int NULLABLE_BIT_FIELD_SIZE = 3;
/*      */   public static final int FIXED_BLOCK_SIZE = 3;
/*      */   public static final int VARIABLE_FIELD_COUNT = 20;
/*      */   public static final int VARIABLE_BLOCK_START = 83;
/*      */   public static final int MAX_SIZE = 327680183;
/*      */   @Nullable
/*      */   public String bodyCharacteristic;
/*      */   @Nullable
/*      */   public String underwear;
/*      */   @Nullable
/*      */   public String face;
/*      */   @Nullable
/*      */   public String eyes;
/*      */   @Nullable
/*      */   public String ears;
/*      */   @Nullable
/*      */   public String mouth;
/*      */   @Nullable
/*      */   public String facialHair;
/*      */   @Nullable
/*      */   public String haircut;
/*      */   
/*      */   public PlayerSkin(@Nullable String bodyCharacteristic, @Nullable String underwear, @Nullable String face, @Nullable String eyes, @Nullable String ears, @Nullable String mouth, @Nullable String facialHair, @Nullable String haircut, @Nullable String eyebrows, @Nullable String pants, @Nullable String overpants, @Nullable String undertop, @Nullable String overtop, @Nullable String shoes, @Nullable String headAccessory, @Nullable String faceAccessory, @Nullable String earAccessory, @Nullable String skinFeature, @Nullable String gloves, @Nullable String cape) {
/*   45 */     this.bodyCharacteristic = bodyCharacteristic;
/*   46 */     this.underwear = underwear;
/*   47 */     this.face = face;
/*   48 */     this.eyes = eyes;
/*   49 */     this.ears = ears;
/*   50 */     this.mouth = mouth;
/*   51 */     this.facialHair = facialHair;
/*   52 */     this.haircut = haircut;
/*   53 */     this.eyebrows = eyebrows;
/*   54 */     this.pants = pants;
/*   55 */     this.overpants = overpants;
/*   56 */     this.undertop = undertop;
/*   57 */     this.overtop = overtop;
/*   58 */     this.shoes = shoes;
/*   59 */     this.headAccessory = headAccessory;
/*   60 */     this.faceAccessory = faceAccessory;
/*   61 */     this.earAccessory = earAccessory;
/*   62 */     this.skinFeature = skinFeature;
/*   63 */     this.gloves = gloves;
/*   64 */     this.cape = cape; } @Nullable public String eyebrows; @Nullable public String pants; @Nullable public String overpants; @Nullable public String undertop; @Nullable public String overtop; @Nullable public String shoes; @Nullable public String headAccessory; @Nullable public String faceAccessory; @Nullable
/*      */   public String earAccessory; @Nullable
/*      */   public String skinFeature; @Nullable
/*      */   public String gloves; @Nullable
/*   68 */   public String cape; public PlayerSkin() {} public PlayerSkin(@Nonnull PlayerSkin other) { this.bodyCharacteristic = other.bodyCharacteristic;
/*   69 */     this.underwear = other.underwear;
/*   70 */     this.face = other.face;
/*   71 */     this.eyes = other.eyes;
/*   72 */     this.ears = other.ears;
/*   73 */     this.mouth = other.mouth;
/*   74 */     this.facialHair = other.facialHair;
/*   75 */     this.haircut = other.haircut;
/*   76 */     this.eyebrows = other.eyebrows;
/*   77 */     this.pants = other.pants;
/*   78 */     this.overpants = other.overpants;
/*   79 */     this.undertop = other.undertop;
/*   80 */     this.overtop = other.overtop;
/*   81 */     this.shoes = other.shoes;
/*   82 */     this.headAccessory = other.headAccessory;
/*   83 */     this.faceAccessory = other.faceAccessory;
/*   84 */     this.earAccessory = other.earAccessory;
/*   85 */     this.skinFeature = other.skinFeature;
/*   86 */     this.gloves = other.gloves;
/*   87 */     this.cape = other.cape; }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static PlayerSkin deserialize(@Nonnull ByteBuf buf, int offset) {
/*   92 */     PlayerSkin obj = new PlayerSkin();
/*   93 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 3);
/*      */     
/*   95 */     if ((nullBits[0] & 0x1) != 0) {
/*   96 */       int varPos0 = offset + 83 + buf.getIntLE(offset + 3);
/*   97 */       int bodyCharacteristicLen = VarInt.peek(buf, varPos0);
/*   98 */       if (bodyCharacteristicLen < 0) throw ProtocolException.negativeLength("BodyCharacteristic", bodyCharacteristicLen); 
/*   99 */       if (bodyCharacteristicLen > 4096000) throw ProtocolException.stringTooLong("BodyCharacteristic", bodyCharacteristicLen, 4096000); 
/*  100 */       obj.bodyCharacteristic = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*      */     } 
/*  102 */     if ((nullBits[0] & 0x2) != 0) {
/*  103 */       int varPos1 = offset + 83 + buf.getIntLE(offset + 7);
/*  104 */       int underwearLen = VarInt.peek(buf, varPos1);
/*  105 */       if (underwearLen < 0) throw ProtocolException.negativeLength("Underwear", underwearLen); 
/*  106 */       if (underwearLen > 4096000) throw ProtocolException.stringTooLong("Underwear", underwearLen, 4096000); 
/*  107 */       obj.underwear = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*      */     } 
/*  109 */     if ((nullBits[0] & 0x4) != 0) {
/*  110 */       int varPos2 = offset + 83 + buf.getIntLE(offset + 11);
/*  111 */       int faceLen = VarInt.peek(buf, varPos2);
/*  112 */       if (faceLen < 0) throw ProtocolException.negativeLength("Face", faceLen); 
/*  113 */       if (faceLen > 4096000) throw ProtocolException.stringTooLong("Face", faceLen, 4096000); 
/*  114 */       obj.face = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*      */     } 
/*  116 */     if ((nullBits[0] & 0x8) != 0) {
/*  117 */       int varPos3 = offset + 83 + buf.getIntLE(offset + 15);
/*  118 */       int eyesLen = VarInt.peek(buf, varPos3);
/*  119 */       if (eyesLen < 0) throw ProtocolException.negativeLength("Eyes", eyesLen); 
/*  120 */       if (eyesLen > 4096000) throw ProtocolException.stringTooLong("Eyes", eyesLen, 4096000); 
/*  121 */       obj.eyes = PacketIO.readVarString(buf, varPos3, PacketIO.UTF8);
/*      */     } 
/*  123 */     if ((nullBits[0] & 0x10) != 0) {
/*  124 */       int varPos4 = offset + 83 + buf.getIntLE(offset + 19);
/*  125 */       int earsLen = VarInt.peek(buf, varPos4);
/*  126 */       if (earsLen < 0) throw ProtocolException.negativeLength("Ears", earsLen); 
/*  127 */       if (earsLen > 4096000) throw ProtocolException.stringTooLong("Ears", earsLen, 4096000); 
/*  128 */       obj.ears = PacketIO.readVarString(buf, varPos4, PacketIO.UTF8);
/*      */     } 
/*  130 */     if ((nullBits[0] & 0x20) != 0) {
/*  131 */       int varPos5 = offset + 83 + buf.getIntLE(offset + 23);
/*  132 */       int mouthLen = VarInt.peek(buf, varPos5);
/*  133 */       if (mouthLen < 0) throw ProtocolException.negativeLength("Mouth", mouthLen); 
/*  134 */       if (mouthLen > 4096000) throw ProtocolException.stringTooLong("Mouth", mouthLen, 4096000); 
/*  135 */       obj.mouth = PacketIO.readVarString(buf, varPos5, PacketIO.UTF8);
/*      */     } 
/*  137 */     if ((nullBits[0] & 0x40) != 0) {
/*  138 */       int varPos6 = offset + 83 + buf.getIntLE(offset + 27);
/*  139 */       int facialHairLen = VarInt.peek(buf, varPos6);
/*  140 */       if (facialHairLen < 0) throw ProtocolException.negativeLength("FacialHair", facialHairLen); 
/*  141 */       if (facialHairLen > 4096000) throw ProtocolException.stringTooLong("FacialHair", facialHairLen, 4096000); 
/*  142 */       obj.facialHair = PacketIO.readVarString(buf, varPos6, PacketIO.UTF8);
/*      */     } 
/*  144 */     if ((nullBits[0] & 0x80) != 0) {
/*  145 */       int varPos7 = offset + 83 + buf.getIntLE(offset + 31);
/*  146 */       int haircutLen = VarInt.peek(buf, varPos7);
/*  147 */       if (haircutLen < 0) throw ProtocolException.negativeLength("Haircut", haircutLen); 
/*  148 */       if (haircutLen > 4096000) throw ProtocolException.stringTooLong("Haircut", haircutLen, 4096000); 
/*  149 */       obj.haircut = PacketIO.readVarString(buf, varPos7, PacketIO.UTF8);
/*      */     } 
/*  151 */     if ((nullBits[1] & 0x1) != 0) {
/*  152 */       int varPos8 = offset + 83 + buf.getIntLE(offset + 35);
/*  153 */       int eyebrowsLen = VarInt.peek(buf, varPos8);
/*  154 */       if (eyebrowsLen < 0) throw ProtocolException.negativeLength("Eyebrows", eyebrowsLen); 
/*  155 */       if (eyebrowsLen > 4096000) throw ProtocolException.stringTooLong("Eyebrows", eyebrowsLen, 4096000); 
/*  156 */       obj.eyebrows = PacketIO.readVarString(buf, varPos8, PacketIO.UTF8);
/*      */     } 
/*  158 */     if ((nullBits[1] & 0x2) != 0) {
/*  159 */       int varPos9 = offset + 83 + buf.getIntLE(offset + 39);
/*  160 */       int pantsLen = VarInt.peek(buf, varPos9);
/*  161 */       if (pantsLen < 0) throw ProtocolException.negativeLength("Pants", pantsLen); 
/*  162 */       if (pantsLen > 4096000) throw ProtocolException.stringTooLong("Pants", pantsLen, 4096000); 
/*  163 */       obj.pants = PacketIO.readVarString(buf, varPos9, PacketIO.UTF8);
/*      */     } 
/*  165 */     if ((nullBits[1] & 0x4) != 0) {
/*  166 */       int varPos10 = offset + 83 + buf.getIntLE(offset + 43);
/*  167 */       int overpantsLen = VarInt.peek(buf, varPos10);
/*  168 */       if (overpantsLen < 0) throw ProtocolException.negativeLength("Overpants", overpantsLen); 
/*  169 */       if (overpantsLen > 4096000) throw ProtocolException.stringTooLong("Overpants", overpantsLen, 4096000); 
/*  170 */       obj.overpants = PacketIO.readVarString(buf, varPos10, PacketIO.UTF8);
/*      */     } 
/*  172 */     if ((nullBits[1] & 0x8) != 0) {
/*  173 */       int varPos11 = offset + 83 + buf.getIntLE(offset + 47);
/*  174 */       int undertopLen = VarInt.peek(buf, varPos11);
/*  175 */       if (undertopLen < 0) throw ProtocolException.negativeLength("Undertop", undertopLen); 
/*  176 */       if (undertopLen > 4096000) throw ProtocolException.stringTooLong("Undertop", undertopLen, 4096000); 
/*  177 */       obj.undertop = PacketIO.readVarString(buf, varPos11, PacketIO.UTF8);
/*      */     } 
/*  179 */     if ((nullBits[1] & 0x10) != 0) {
/*  180 */       int varPos12 = offset + 83 + buf.getIntLE(offset + 51);
/*  181 */       int overtopLen = VarInt.peek(buf, varPos12);
/*  182 */       if (overtopLen < 0) throw ProtocolException.negativeLength("Overtop", overtopLen); 
/*  183 */       if (overtopLen > 4096000) throw ProtocolException.stringTooLong("Overtop", overtopLen, 4096000); 
/*  184 */       obj.overtop = PacketIO.readVarString(buf, varPos12, PacketIO.UTF8);
/*      */     } 
/*  186 */     if ((nullBits[1] & 0x20) != 0) {
/*  187 */       int varPos13 = offset + 83 + buf.getIntLE(offset + 55);
/*  188 */       int shoesLen = VarInt.peek(buf, varPos13);
/*  189 */       if (shoesLen < 0) throw ProtocolException.negativeLength("Shoes", shoesLen); 
/*  190 */       if (shoesLen > 4096000) throw ProtocolException.stringTooLong("Shoes", shoesLen, 4096000); 
/*  191 */       obj.shoes = PacketIO.readVarString(buf, varPos13, PacketIO.UTF8);
/*      */     } 
/*  193 */     if ((nullBits[1] & 0x40) != 0) {
/*  194 */       int varPos14 = offset + 83 + buf.getIntLE(offset + 59);
/*  195 */       int headAccessoryLen = VarInt.peek(buf, varPos14);
/*  196 */       if (headAccessoryLen < 0) throw ProtocolException.negativeLength("HeadAccessory", headAccessoryLen); 
/*  197 */       if (headAccessoryLen > 4096000) throw ProtocolException.stringTooLong("HeadAccessory", headAccessoryLen, 4096000); 
/*  198 */       obj.headAccessory = PacketIO.readVarString(buf, varPos14, PacketIO.UTF8);
/*      */     } 
/*  200 */     if ((nullBits[1] & 0x80) != 0) {
/*  201 */       int varPos15 = offset + 83 + buf.getIntLE(offset + 63);
/*  202 */       int faceAccessoryLen = VarInt.peek(buf, varPos15);
/*  203 */       if (faceAccessoryLen < 0) throw ProtocolException.negativeLength("FaceAccessory", faceAccessoryLen); 
/*  204 */       if (faceAccessoryLen > 4096000) throw ProtocolException.stringTooLong("FaceAccessory", faceAccessoryLen, 4096000); 
/*  205 */       obj.faceAccessory = PacketIO.readVarString(buf, varPos15, PacketIO.UTF8);
/*      */     } 
/*  207 */     if ((nullBits[2] & 0x1) != 0) {
/*  208 */       int varPos16 = offset + 83 + buf.getIntLE(offset + 67);
/*  209 */       int earAccessoryLen = VarInt.peek(buf, varPos16);
/*  210 */       if (earAccessoryLen < 0) throw ProtocolException.negativeLength("EarAccessory", earAccessoryLen); 
/*  211 */       if (earAccessoryLen > 4096000) throw ProtocolException.stringTooLong("EarAccessory", earAccessoryLen, 4096000); 
/*  212 */       obj.earAccessory = PacketIO.readVarString(buf, varPos16, PacketIO.UTF8);
/*      */     } 
/*  214 */     if ((nullBits[2] & 0x2) != 0) {
/*  215 */       int varPos17 = offset + 83 + buf.getIntLE(offset + 71);
/*  216 */       int skinFeatureLen = VarInt.peek(buf, varPos17);
/*  217 */       if (skinFeatureLen < 0) throw ProtocolException.negativeLength("SkinFeature", skinFeatureLen); 
/*  218 */       if (skinFeatureLen > 4096000) throw ProtocolException.stringTooLong("SkinFeature", skinFeatureLen, 4096000); 
/*  219 */       obj.skinFeature = PacketIO.readVarString(buf, varPos17, PacketIO.UTF8);
/*      */     } 
/*  221 */     if ((nullBits[2] & 0x4) != 0) {
/*  222 */       int varPos18 = offset + 83 + buf.getIntLE(offset + 75);
/*  223 */       int glovesLen = VarInt.peek(buf, varPos18);
/*  224 */       if (glovesLen < 0) throw ProtocolException.negativeLength("Gloves", glovesLen); 
/*  225 */       if (glovesLen > 4096000) throw ProtocolException.stringTooLong("Gloves", glovesLen, 4096000); 
/*  226 */       obj.gloves = PacketIO.readVarString(buf, varPos18, PacketIO.UTF8);
/*      */     } 
/*  228 */     if ((nullBits[2] & 0x8) != 0) {
/*  229 */       int varPos19 = offset + 83 + buf.getIntLE(offset + 79);
/*  230 */       int capeLen = VarInt.peek(buf, varPos19);
/*  231 */       if (capeLen < 0) throw ProtocolException.negativeLength("Cape", capeLen); 
/*  232 */       if (capeLen > 4096000) throw ProtocolException.stringTooLong("Cape", capeLen, 4096000); 
/*  233 */       obj.cape = PacketIO.readVarString(buf, varPos19, PacketIO.UTF8);
/*      */     } 
/*      */     
/*  236 */     return obj;
/*      */   }
/*      */   
/*      */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  240 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 3);
/*  241 */     int maxEnd = 83;
/*  242 */     if ((nullBits[0] & 0x1) != 0) {
/*  243 */       int fieldOffset0 = buf.getIntLE(offset + 3);
/*  244 */       int pos0 = offset + 83 + fieldOffset0;
/*  245 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  246 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*      */     } 
/*  248 */     if ((nullBits[0] & 0x2) != 0) {
/*  249 */       int fieldOffset1 = buf.getIntLE(offset + 7);
/*  250 */       int pos1 = offset + 83 + fieldOffset1;
/*  251 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  252 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*      */     } 
/*  254 */     if ((nullBits[0] & 0x4) != 0) {
/*  255 */       int fieldOffset2 = buf.getIntLE(offset + 11);
/*  256 */       int pos2 = offset + 83 + fieldOffset2;
/*  257 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/*  258 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*      */     } 
/*  260 */     if ((nullBits[0] & 0x8) != 0) {
/*  261 */       int fieldOffset3 = buf.getIntLE(offset + 15);
/*  262 */       int pos3 = offset + 83 + fieldOffset3;
/*  263 */       int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl;
/*  264 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*      */     } 
/*  266 */     if ((nullBits[0] & 0x10) != 0) {
/*  267 */       int fieldOffset4 = buf.getIntLE(offset + 19);
/*  268 */       int pos4 = offset + 83 + fieldOffset4;
/*  269 */       int sl = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4) + sl;
/*  270 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*      */     } 
/*  272 */     if ((nullBits[0] & 0x20) != 0) {
/*  273 */       int fieldOffset5 = buf.getIntLE(offset + 23);
/*  274 */       int pos5 = offset + 83 + fieldOffset5;
/*  275 */       int sl = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + sl;
/*  276 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*      */     } 
/*  278 */     if ((nullBits[0] & 0x40) != 0) {
/*  279 */       int fieldOffset6 = buf.getIntLE(offset + 27);
/*  280 */       int pos6 = offset + 83 + fieldOffset6;
/*  281 */       int sl = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6) + sl;
/*  282 */       if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*      */     } 
/*  284 */     if ((nullBits[0] & 0x80) != 0) {
/*  285 */       int fieldOffset7 = buf.getIntLE(offset + 31);
/*  286 */       int pos7 = offset + 83 + fieldOffset7;
/*  287 */       int sl = VarInt.peek(buf, pos7); pos7 += VarInt.length(buf, pos7) + sl;
/*  288 */       if (pos7 - offset > maxEnd) maxEnd = pos7 - offset; 
/*      */     } 
/*  290 */     if ((nullBits[1] & 0x1) != 0) {
/*  291 */       int fieldOffset8 = buf.getIntLE(offset + 35);
/*  292 */       int pos8 = offset + 83 + fieldOffset8;
/*  293 */       int sl = VarInt.peek(buf, pos8); pos8 += VarInt.length(buf, pos8) + sl;
/*  294 */       if (pos8 - offset > maxEnd) maxEnd = pos8 - offset; 
/*      */     } 
/*  296 */     if ((nullBits[1] & 0x2) != 0) {
/*  297 */       int fieldOffset9 = buf.getIntLE(offset + 39);
/*  298 */       int pos9 = offset + 83 + fieldOffset9;
/*  299 */       int sl = VarInt.peek(buf, pos9); pos9 += VarInt.length(buf, pos9) + sl;
/*  300 */       if (pos9 - offset > maxEnd) maxEnd = pos9 - offset; 
/*      */     } 
/*  302 */     if ((nullBits[1] & 0x4) != 0) {
/*  303 */       int fieldOffset10 = buf.getIntLE(offset + 43);
/*  304 */       int pos10 = offset + 83 + fieldOffset10;
/*  305 */       int sl = VarInt.peek(buf, pos10); pos10 += VarInt.length(buf, pos10) + sl;
/*  306 */       if (pos10 - offset > maxEnd) maxEnd = pos10 - offset; 
/*      */     } 
/*  308 */     if ((nullBits[1] & 0x8) != 0) {
/*  309 */       int fieldOffset11 = buf.getIntLE(offset + 47);
/*  310 */       int pos11 = offset + 83 + fieldOffset11;
/*  311 */       int sl = VarInt.peek(buf, pos11); pos11 += VarInt.length(buf, pos11) + sl;
/*  312 */       if (pos11 - offset > maxEnd) maxEnd = pos11 - offset; 
/*      */     } 
/*  314 */     if ((nullBits[1] & 0x10) != 0) {
/*  315 */       int fieldOffset12 = buf.getIntLE(offset + 51);
/*  316 */       int pos12 = offset + 83 + fieldOffset12;
/*  317 */       int sl = VarInt.peek(buf, pos12); pos12 += VarInt.length(buf, pos12) + sl;
/*  318 */       if (pos12 - offset > maxEnd) maxEnd = pos12 - offset; 
/*      */     } 
/*  320 */     if ((nullBits[1] & 0x20) != 0) {
/*  321 */       int fieldOffset13 = buf.getIntLE(offset + 55);
/*  322 */       int pos13 = offset + 83 + fieldOffset13;
/*  323 */       int sl = VarInt.peek(buf, pos13); pos13 += VarInt.length(buf, pos13) + sl;
/*  324 */       if (pos13 - offset > maxEnd) maxEnd = pos13 - offset; 
/*      */     } 
/*  326 */     if ((nullBits[1] & 0x40) != 0) {
/*  327 */       int fieldOffset14 = buf.getIntLE(offset + 59);
/*  328 */       int pos14 = offset + 83 + fieldOffset14;
/*  329 */       int sl = VarInt.peek(buf, pos14); pos14 += VarInt.length(buf, pos14) + sl;
/*  330 */       if (pos14 - offset > maxEnd) maxEnd = pos14 - offset; 
/*      */     } 
/*  332 */     if ((nullBits[1] & 0x80) != 0) {
/*  333 */       int fieldOffset15 = buf.getIntLE(offset + 63);
/*  334 */       int pos15 = offset + 83 + fieldOffset15;
/*  335 */       int sl = VarInt.peek(buf, pos15); pos15 += VarInt.length(buf, pos15) + sl;
/*  336 */       if (pos15 - offset > maxEnd) maxEnd = pos15 - offset; 
/*      */     } 
/*  338 */     if ((nullBits[2] & 0x1) != 0) {
/*  339 */       int fieldOffset16 = buf.getIntLE(offset + 67);
/*  340 */       int pos16 = offset + 83 + fieldOffset16;
/*  341 */       int sl = VarInt.peek(buf, pos16); pos16 += VarInt.length(buf, pos16) + sl;
/*  342 */       if (pos16 - offset > maxEnd) maxEnd = pos16 - offset; 
/*      */     } 
/*  344 */     if ((nullBits[2] & 0x2) != 0) {
/*  345 */       int fieldOffset17 = buf.getIntLE(offset + 71);
/*  346 */       int pos17 = offset + 83 + fieldOffset17;
/*  347 */       int sl = VarInt.peek(buf, pos17); pos17 += VarInt.length(buf, pos17) + sl;
/*  348 */       if (pos17 - offset > maxEnd) maxEnd = pos17 - offset; 
/*      */     } 
/*  350 */     if ((nullBits[2] & 0x4) != 0) {
/*  351 */       int fieldOffset18 = buf.getIntLE(offset + 75);
/*  352 */       int pos18 = offset + 83 + fieldOffset18;
/*  353 */       int sl = VarInt.peek(buf, pos18); pos18 += VarInt.length(buf, pos18) + sl;
/*  354 */       if (pos18 - offset > maxEnd) maxEnd = pos18 - offset; 
/*      */     } 
/*  356 */     if ((nullBits[2] & 0x8) != 0) {
/*  357 */       int fieldOffset19 = buf.getIntLE(offset + 79);
/*  358 */       int pos19 = offset + 83 + fieldOffset19;
/*  359 */       int sl = VarInt.peek(buf, pos19); pos19 += VarInt.length(buf, pos19) + sl;
/*  360 */       if (pos19 - offset > maxEnd) maxEnd = pos19 - offset; 
/*      */     } 
/*  362 */     return maxEnd;
/*      */   }
/*      */ 
/*      */   
/*      */   public void serialize(@Nonnull ByteBuf buf) {
/*  367 */     int startPos = buf.writerIndex();
/*  368 */     byte[] nullBits = new byte[3];
/*  369 */     if (this.bodyCharacteristic != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/*  370 */     if (this.underwear != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/*  371 */     if (this.face != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/*  372 */     if (this.eyes != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/*  373 */     if (this.ears != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/*  374 */     if (this.mouth != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/*  375 */     if (this.facialHair != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/*  376 */     if (this.haircut != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/*  377 */     if (this.eyebrows != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/*  378 */     if (this.pants != null) nullBits[1] = (byte)(nullBits[1] | 0x2); 
/*  379 */     if (this.overpants != null) nullBits[1] = (byte)(nullBits[1] | 0x4); 
/*  380 */     if (this.undertop != null) nullBits[1] = (byte)(nullBits[1] | 0x8); 
/*  381 */     if (this.overtop != null) nullBits[1] = (byte)(nullBits[1] | 0x10); 
/*  382 */     if (this.shoes != null) nullBits[1] = (byte)(nullBits[1] | 0x20); 
/*  383 */     if (this.headAccessory != null) nullBits[1] = (byte)(nullBits[1] | 0x40); 
/*  384 */     if (this.faceAccessory != null) nullBits[1] = (byte)(nullBits[1] | 0x80); 
/*  385 */     if (this.earAccessory != null) nullBits[2] = (byte)(nullBits[2] | 0x1); 
/*  386 */     if (this.skinFeature != null) nullBits[2] = (byte)(nullBits[2] | 0x2); 
/*  387 */     if (this.gloves != null) nullBits[2] = (byte)(nullBits[2] | 0x4); 
/*  388 */     if (this.cape != null) nullBits[2] = (byte)(nullBits[2] | 0x8); 
/*  389 */     buf.writeBytes(nullBits);
/*      */ 
/*      */     
/*  392 */     int bodyCharacteristicOffsetSlot = buf.writerIndex();
/*  393 */     buf.writeIntLE(0);
/*  394 */     int underwearOffsetSlot = buf.writerIndex();
/*  395 */     buf.writeIntLE(0);
/*  396 */     int faceOffsetSlot = buf.writerIndex();
/*  397 */     buf.writeIntLE(0);
/*  398 */     int eyesOffsetSlot = buf.writerIndex();
/*  399 */     buf.writeIntLE(0);
/*  400 */     int earsOffsetSlot = buf.writerIndex();
/*  401 */     buf.writeIntLE(0);
/*  402 */     int mouthOffsetSlot = buf.writerIndex();
/*  403 */     buf.writeIntLE(0);
/*  404 */     int facialHairOffsetSlot = buf.writerIndex();
/*  405 */     buf.writeIntLE(0);
/*  406 */     int haircutOffsetSlot = buf.writerIndex();
/*  407 */     buf.writeIntLE(0);
/*  408 */     int eyebrowsOffsetSlot = buf.writerIndex();
/*  409 */     buf.writeIntLE(0);
/*  410 */     int pantsOffsetSlot = buf.writerIndex();
/*  411 */     buf.writeIntLE(0);
/*  412 */     int overpantsOffsetSlot = buf.writerIndex();
/*  413 */     buf.writeIntLE(0);
/*  414 */     int undertopOffsetSlot = buf.writerIndex();
/*  415 */     buf.writeIntLE(0);
/*  416 */     int overtopOffsetSlot = buf.writerIndex();
/*  417 */     buf.writeIntLE(0);
/*  418 */     int shoesOffsetSlot = buf.writerIndex();
/*  419 */     buf.writeIntLE(0);
/*  420 */     int headAccessoryOffsetSlot = buf.writerIndex();
/*  421 */     buf.writeIntLE(0);
/*  422 */     int faceAccessoryOffsetSlot = buf.writerIndex();
/*  423 */     buf.writeIntLE(0);
/*  424 */     int earAccessoryOffsetSlot = buf.writerIndex();
/*  425 */     buf.writeIntLE(0);
/*  426 */     int skinFeatureOffsetSlot = buf.writerIndex();
/*  427 */     buf.writeIntLE(0);
/*  428 */     int glovesOffsetSlot = buf.writerIndex();
/*  429 */     buf.writeIntLE(0);
/*  430 */     int capeOffsetSlot = buf.writerIndex();
/*  431 */     buf.writeIntLE(0);
/*      */     
/*  433 */     int varBlockStart = buf.writerIndex();
/*  434 */     if (this.bodyCharacteristic != null) {
/*  435 */       buf.setIntLE(bodyCharacteristicOffsetSlot, buf.writerIndex() - varBlockStart);
/*  436 */       PacketIO.writeVarString(buf, this.bodyCharacteristic, 4096000);
/*      */     } else {
/*  438 */       buf.setIntLE(bodyCharacteristicOffsetSlot, -1);
/*      */     } 
/*  440 */     if (this.underwear != null) {
/*  441 */       buf.setIntLE(underwearOffsetSlot, buf.writerIndex() - varBlockStart);
/*  442 */       PacketIO.writeVarString(buf, this.underwear, 4096000);
/*      */     } else {
/*  444 */       buf.setIntLE(underwearOffsetSlot, -1);
/*      */     } 
/*  446 */     if (this.face != null) {
/*  447 */       buf.setIntLE(faceOffsetSlot, buf.writerIndex() - varBlockStart);
/*  448 */       PacketIO.writeVarString(buf, this.face, 4096000);
/*      */     } else {
/*  450 */       buf.setIntLE(faceOffsetSlot, -1);
/*      */     } 
/*  452 */     if (this.eyes != null) {
/*  453 */       buf.setIntLE(eyesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  454 */       PacketIO.writeVarString(buf, this.eyes, 4096000);
/*      */     } else {
/*  456 */       buf.setIntLE(eyesOffsetSlot, -1);
/*      */     } 
/*  458 */     if (this.ears != null) {
/*  459 */       buf.setIntLE(earsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  460 */       PacketIO.writeVarString(buf, this.ears, 4096000);
/*      */     } else {
/*  462 */       buf.setIntLE(earsOffsetSlot, -1);
/*      */     } 
/*  464 */     if (this.mouth != null) {
/*  465 */       buf.setIntLE(mouthOffsetSlot, buf.writerIndex() - varBlockStart);
/*  466 */       PacketIO.writeVarString(buf, this.mouth, 4096000);
/*      */     } else {
/*  468 */       buf.setIntLE(mouthOffsetSlot, -1);
/*      */     } 
/*  470 */     if (this.facialHair != null) {
/*  471 */       buf.setIntLE(facialHairOffsetSlot, buf.writerIndex() - varBlockStart);
/*  472 */       PacketIO.writeVarString(buf, this.facialHair, 4096000);
/*      */     } else {
/*  474 */       buf.setIntLE(facialHairOffsetSlot, -1);
/*      */     } 
/*  476 */     if (this.haircut != null) {
/*  477 */       buf.setIntLE(haircutOffsetSlot, buf.writerIndex() - varBlockStart);
/*  478 */       PacketIO.writeVarString(buf, this.haircut, 4096000);
/*      */     } else {
/*  480 */       buf.setIntLE(haircutOffsetSlot, -1);
/*      */     } 
/*  482 */     if (this.eyebrows != null) {
/*  483 */       buf.setIntLE(eyebrowsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  484 */       PacketIO.writeVarString(buf, this.eyebrows, 4096000);
/*      */     } else {
/*  486 */       buf.setIntLE(eyebrowsOffsetSlot, -1);
/*      */     } 
/*  488 */     if (this.pants != null) {
/*  489 */       buf.setIntLE(pantsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  490 */       PacketIO.writeVarString(buf, this.pants, 4096000);
/*      */     } else {
/*  492 */       buf.setIntLE(pantsOffsetSlot, -1);
/*      */     } 
/*  494 */     if (this.overpants != null) {
/*  495 */       buf.setIntLE(overpantsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  496 */       PacketIO.writeVarString(buf, this.overpants, 4096000);
/*      */     } else {
/*  498 */       buf.setIntLE(overpantsOffsetSlot, -1);
/*      */     } 
/*  500 */     if (this.undertop != null) {
/*  501 */       buf.setIntLE(undertopOffsetSlot, buf.writerIndex() - varBlockStart);
/*  502 */       PacketIO.writeVarString(buf, this.undertop, 4096000);
/*      */     } else {
/*  504 */       buf.setIntLE(undertopOffsetSlot, -1);
/*      */     } 
/*  506 */     if (this.overtop != null) {
/*  507 */       buf.setIntLE(overtopOffsetSlot, buf.writerIndex() - varBlockStart);
/*  508 */       PacketIO.writeVarString(buf, this.overtop, 4096000);
/*      */     } else {
/*  510 */       buf.setIntLE(overtopOffsetSlot, -1);
/*      */     } 
/*  512 */     if (this.shoes != null) {
/*  513 */       buf.setIntLE(shoesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  514 */       PacketIO.writeVarString(buf, this.shoes, 4096000);
/*      */     } else {
/*  516 */       buf.setIntLE(shoesOffsetSlot, -1);
/*      */     } 
/*  518 */     if (this.headAccessory != null) {
/*  519 */       buf.setIntLE(headAccessoryOffsetSlot, buf.writerIndex() - varBlockStart);
/*  520 */       PacketIO.writeVarString(buf, this.headAccessory, 4096000);
/*      */     } else {
/*  522 */       buf.setIntLE(headAccessoryOffsetSlot, -1);
/*      */     } 
/*  524 */     if (this.faceAccessory != null) {
/*  525 */       buf.setIntLE(faceAccessoryOffsetSlot, buf.writerIndex() - varBlockStart);
/*  526 */       PacketIO.writeVarString(buf, this.faceAccessory, 4096000);
/*      */     } else {
/*  528 */       buf.setIntLE(faceAccessoryOffsetSlot, -1);
/*      */     } 
/*  530 */     if (this.earAccessory != null) {
/*  531 */       buf.setIntLE(earAccessoryOffsetSlot, buf.writerIndex() - varBlockStart);
/*  532 */       PacketIO.writeVarString(buf, this.earAccessory, 4096000);
/*      */     } else {
/*  534 */       buf.setIntLE(earAccessoryOffsetSlot, -1);
/*      */     } 
/*  536 */     if (this.skinFeature != null) {
/*  537 */       buf.setIntLE(skinFeatureOffsetSlot, buf.writerIndex() - varBlockStart);
/*  538 */       PacketIO.writeVarString(buf, this.skinFeature, 4096000);
/*      */     } else {
/*  540 */       buf.setIntLE(skinFeatureOffsetSlot, -1);
/*      */     } 
/*  542 */     if (this.gloves != null) {
/*  543 */       buf.setIntLE(glovesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  544 */       PacketIO.writeVarString(buf, this.gloves, 4096000);
/*      */     } else {
/*  546 */       buf.setIntLE(glovesOffsetSlot, -1);
/*      */     } 
/*  548 */     if (this.cape != null) {
/*  549 */       buf.setIntLE(capeOffsetSlot, buf.writerIndex() - varBlockStart);
/*  550 */       PacketIO.writeVarString(buf, this.cape, 4096000);
/*      */     } else {
/*  552 */       buf.setIntLE(capeOffsetSlot, -1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeSize() {
/*  558 */     int size = 83;
/*  559 */     if (this.bodyCharacteristic != null) size += PacketIO.stringSize(this.bodyCharacteristic); 
/*  560 */     if (this.underwear != null) size += PacketIO.stringSize(this.underwear); 
/*  561 */     if (this.face != null) size += PacketIO.stringSize(this.face); 
/*  562 */     if (this.eyes != null) size += PacketIO.stringSize(this.eyes); 
/*  563 */     if (this.ears != null) size += PacketIO.stringSize(this.ears); 
/*  564 */     if (this.mouth != null) size += PacketIO.stringSize(this.mouth); 
/*  565 */     if (this.facialHair != null) size += PacketIO.stringSize(this.facialHair); 
/*  566 */     if (this.haircut != null) size += PacketIO.stringSize(this.haircut); 
/*  567 */     if (this.eyebrows != null) size += PacketIO.stringSize(this.eyebrows); 
/*  568 */     if (this.pants != null) size += PacketIO.stringSize(this.pants); 
/*  569 */     if (this.overpants != null) size += PacketIO.stringSize(this.overpants); 
/*  570 */     if (this.undertop != null) size += PacketIO.stringSize(this.undertop); 
/*  571 */     if (this.overtop != null) size += PacketIO.stringSize(this.overtop); 
/*  572 */     if (this.shoes != null) size += PacketIO.stringSize(this.shoes); 
/*  573 */     if (this.headAccessory != null) size += PacketIO.stringSize(this.headAccessory); 
/*  574 */     if (this.faceAccessory != null) size += PacketIO.stringSize(this.faceAccessory); 
/*  575 */     if (this.earAccessory != null) size += PacketIO.stringSize(this.earAccessory); 
/*  576 */     if (this.skinFeature != null) size += PacketIO.stringSize(this.skinFeature); 
/*  577 */     if (this.gloves != null) size += PacketIO.stringSize(this.gloves); 
/*  578 */     if (this.cape != null) size += PacketIO.stringSize(this.cape);
/*      */     
/*  580 */     return size;
/*      */   }
/*      */   
/*      */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  584 */     if (buffer.readableBytes() - offset < 83) {
/*  585 */       return ValidationResult.error("Buffer too small: expected at least 83 bytes");
/*      */     }
/*      */     
/*  588 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 3);
/*      */     
/*  590 */     if ((nullBits[0] & 0x1) != 0) {
/*  591 */       int bodyCharacteristicOffset = buffer.getIntLE(offset + 3);
/*  592 */       if (bodyCharacteristicOffset < 0) {
/*  593 */         return ValidationResult.error("Invalid offset for BodyCharacteristic");
/*      */       }
/*  595 */       int pos = offset + 83 + bodyCharacteristicOffset;
/*  596 */       if (pos >= buffer.writerIndex()) {
/*  597 */         return ValidationResult.error("Offset out of bounds for BodyCharacteristic");
/*      */       }
/*  599 */       int bodyCharacteristicLen = VarInt.peek(buffer, pos);
/*  600 */       if (bodyCharacteristicLen < 0) {
/*  601 */         return ValidationResult.error("Invalid string length for BodyCharacteristic");
/*      */       }
/*  603 */       if (bodyCharacteristicLen > 4096000) {
/*  604 */         return ValidationResult.error("BodyCharacteristic exceeds max length 4096000");
/*      */       }
/*  606 */       pos += VarInt.length(buffer, pos);
/*  607 */       pos += bodyCharacteristicLen;
/*  608 */       if (pos > buffer.writerIndex()) {
/*  609 */         return ValidationResult.error("Buffer overflow reading BodyCharacteristic");
/*      */       }
/*      */     } 
/*      */     
/*  613 */     if ((nullBits[0] & 0x2) != 0) {
/*  614 */       int underwearOffset = buffer.getIntLE(offset + 7);
/*  615 */       if (underwearOffset < 0) {
/*  616 */         return ValidationResult.error("Invalid offset for Underwear");
/*      */       }
/*  618 */       int pos = offset + 83 + underwearOffset;
/*  619 */       if (pos >= buffer.writerIndex()) {
/*  620 */         return ValidationResult.error("Offset out of bounds for Underwear");
/*      */       }
/*  622 */       int underwearLen = VarInt.peek(buffer, pos);
/*  623 */       if (underwearLen < 0) {
/*  624 */         return ValidationResult.error("Invalid string length for Underwear");
/*      */       }
/*  626 */       if (underwearLen > 4096000) {
/*  627 */         return ValidationResult.error("Underwear exceeds max length 4096000");
/*      */       }
/*  629 */       pos += VarInt.length(buffer, pos);
/*  630 */       pos += underwearLen;
/*  631 */       if (pos > buffer.writerIndex()) {
/*  632 */         return ValidationResult.error("Buffer overflow reading Underwear");
/*      */       }
/*      */     } 
/*      */     
/*  636 */     if ((nullBits[0] & 0x4) != 0) {
/*  637 */       int faceOffset = buffer.getIntLE(offset + 11);
/*  638 */       if (faceOffset < 0) {
/*  639 */         return ValidationResult.error("Invalid offset for Face");
/*      */       }
/*  641 */       int pos = offset + 83 + faceOffset;
/*  642 */       if (pos >= buffer.writerIndex()) {
/*  643 */         return ValidationResult.error("Offset out of bounds for Face");
/*      */       }
/*  645 */       int faceLen = VarInt.peek(buffer, pos);
/*  646 */       if (faceLen < 0) {
/*  647 */         return ValidationResult.error("Invalid string length for Face");
/*      */       }
/*  649 */       if (faceLen > 4096000) {
/*  650 */         return ValidationResult.error("Face exceeds max length 4096000");
/*      */       }
/*  652 */       pos += VarInt.length(buffer, pos);
/*  653 */       pos += faceLen;
/*  654 */       if (pos > buffer.writerIndex()) {
/*  655 */         return ValidationResult.error("Buffer overflow reading Face");
/*      */       }
/*      */     } 
/*      */     
/*  659 */     if ((nullBits[0] & 0x8) != 0) {
/*  660 */       int eyesOffset = buffer.getIntLE(offset + 15);
/*  661 */       if (eyesOffset < 0) {
/*  662 */         return ValidationResult.error("Invalid offset for Eyes");
/*      */       }
/*  664 */       int pos = offset + 83 + eyesOffset;
/*  665 */       if (pos >= buffer.writerIndex()) {
/*  666 */         return ValidationResult.error("Offset out of bounds for Eyes");
/*      */       }
/*  668 */       int eyesLen = VarInt.peek(buffer, pos);
/*  669 */       if (eyesLen < 0) {
/*  670 */         return ValidationResult.error("Invalid string length for Eyes");
/*      */       }
/*  672 */       if (eyesLen > 4096000) {
/*  673 */         return ValidationResult.error("Eyes exceeds max length 4096000");
/*      */       }
/*  675 */       pos += VarInt.length(buffer, pos);
/*  676 */       pos += eyesLen;
/*  677 */       if (pos > buffer.writerIndex()) {
/*  678 */         return ValidationResult.error("Buffer overflow reading Eyes");
/*      */       }
/*      */     } 
/*      */     
/*  682 */     if ((nullBits[0] & 0x10) != 0) {
/*  683 */       int earsOffset = buffer.getIntLE(offset + 19);
/*  684 */       if (earsOffset < 0) {
/*  685 */         return ValidationResult.error("Invalid offset for Ears");
/*      */       }
/*  687 */       int pos = offset + 83 + earsOffset;
/*  688 */       if (pos >= buffer.writerIndex()) {
/*  689 */         return ValidationResult.error("Offset out of bounds for Ears");
/*      */       }
/*  691 */       int earsLen = VarInt.peek(buffer, pos);
/*  692 */       if (earsLen < 0) {
/*  693 */         return ValidationResult.error("Invalid string length for Ears");
/*      */       }
/*  695 */       if (earsLen > 4096000) {
/*  696 */         return ValidationResult.error("Ears exceeds max length 4096000");
/*      */       }
/*  698 */       pos += VarInt.length(buffer, pos);
/*  699 */       pos += earsLen;
/*  700 */       if (pos > buffer.writerIndex()) {
/*  701 */         return ValidationResult.error("Buffer overflow reading Ears");
/*      */       }
/*      */     } 
/*      */     
/*  705 */     if ((nullBits[0] & 0x20) != 0) {
/*  706 */       int mouthOffset = buffer.getIntLE(offset + 23);
/*  707 */       if (mouthOffset < 0) {
/*  708 */         return ValidationResult.error("Invalid offset for Mouth");
/*      */       }
/*  710 */       int pos = offset + 83 + mouthOffset;
/*  711 */       if (pos >= buffer.writerIndex()) {
/*  712 */         return ValidationResult.error("Offset out of bounds for Mouth");
/*      */       }
/*  714 */       int mouthLen = VarInt.peek(buffer, pos);
/*  715 */       if (mouthLen < 0) {
/*  716 */         return ValidationResult.error("Invalid string length for Mouth");
/*      */       }
/*  718 */       if (mouthLen > 4096000) {
/*  719 */         return ValidationResult.error("Mouth exceeds max length 4096000");
/*      */       }
/*  721 */       pos += VarInt.length(buffer, pos);
/*  722 */       pos += mouthLen;
/*  723 */       if (pos > buffer.writerIndex()) {
/*  724 */         return ValidationResult.error("Buffer overflow reading Mouth");
/*      */       }
/*      */     } 
/*      */     
/*  728 */     if ((nullBits[0] & 0x40) != 0) {
/*  729 */       int facialHairOffset = buffer.getIntLE(offset + 27);
/*  730 */       if (facialHairOffset < 0) {
/*  731 */         return ValidationResult.error("Invalid offset for FacialHair");
/*      */       }
/*  733 */       int pos = offset + 83 + facialHairOffset;
/*  734 */       if (pos >= buffer.writerIndex()) {
/*  735 */         return ValidationResult.error("Offset out of bounds for FacialHair");
/*      */       }
/*  737 */       int facialHairLen = VarInt.peek(buffer, pos);
/*  738 */       if (facialHairLen < 0) {
/*  739 */         return ValidationResult.error("Invalid string length for FacialHair");
/*      */       }
/*  741 */       if (facialHairLen > 4096000) {
/*  742 */         return ValidationResult.error("FacialHair exceeds max length 4096000");
/*      */       }
/*  744 */       pos += VarInt.length(buffer, pos);
/*  745 */       pos += facialHairLen;
/*  746 */       if (pos > buffer.writerIndex()) {
/*  747 */         return ValidationResult.error("Buffer overflow reading FacialHair");
/*      */       }
/*      */     } 
/*      */     
/*  751 */     if ((nullBits[0] & 0x80) != 0) {
/*  752 */       int haircutOffset = buffer.getIntLE(offset + 31);
/*  753 */       if (haircutOffset < 0) {
/*  754 */         return ValidationResult.error("Invalid offset for Haircut");
/*      */       }
/*  756 */       int pos = offset + 83 + haircutOffset;
/*  757 */       if (pos >= buffer.writerIndex()) {
/*  758 */         return ValidationResult.error("Offset out of bounds for Haircut");
/*      */       }
/*  760 */       int haircutLen = VarInt.peek(buffer, pos);
/*  761 */       if (haircutLen < 0) {
/*  762 */         return ValidationResult.error("Invalid string length for Haircut");
/*      */       }
/*  764 */       if (haircutLen > 4096000) {
/*  765 */         return ValidationResult.error("Haircut exceeds max length 4096000");
/*      */       }
/*  767 */       pos += VarInt.length(buffer, pos);
/*  768 */       pos += haircutLen;
/*  769 */       if (pos > buffer.writerIndex()) {
/*  770 */         return ValidationResult.error("Buffer overflow reading Haircut");
/*      */       }
/*      */     } 
/*      */     
/*  774 */     if ((nullBits[1] & 0x1) != 0) {
/*  775 */       int eyebrowsOffset = buffer.getIntLE(offset + 35);
/*  776 */       if (eyebrowsOffset < 0) {
/*  777 */         return ValidationResult.error("Invalid offset for Eyebrows");
/*      */       }
/*  779 */       int pos = offset + 83 + eyebrowsOffset;
/*  780 */       if (pos >= buffer.writerIndex()) {
/*  781 */         return ValidationResult.error("Offset out of bounds for Eyebrows");
/*      */       }
/*  783 */       int eyebrowsLen = VarInt.peek(buffer, pos);
/*  784 */       if (eyebrowsLen < 0) {
/*  785 */         return ValidationResult.error("Invalid string length for Eyebrows");
/*      */       }
/*  787 */       if (eyebrowsLen > 4096000) {
/*  788 */         return ValidationResult.error("Eyebrows exceeds max length 4096000");
/*      */       }
/*  790 */       pos += VarInt.length(buffer, pos);
/*  791 */       pos += eyebrowsLen;
/*  792 */       if (pos > buffer.writerIndex()) {
/*  793 */         return ValidationResult.error("Buffer overflow reading Eyebrows");
/*      */       }
/*      */     } 
/*      */     
/*  797 */     if ((nullBits[1] & 0x2) != 0) {
/*  798 */       int pantsOffset = buffer.getIntLE(offset + 39);
/*  799 */       if (pantsOffset < 0) {
/*  800 */         return ValidationResult.error("Invalid offset for Pants");
/*      */       }
/*  802 */       int pos = offset + 83 + pantsOffset;
/*  803 */       if (pos >= buffer.writerIndex()) {
/*  804 */         return ValidationResult.error("Offset out of bounds for Pants");
/*      */       }
/*  806 */       int pantsLen = VarInt.peek(buffer, pos);
/*  807 */       if (pantsLen < 0) {
/*  808 */         return ValidationResult.error("Invalid string length for Pants");
/*      */       }
/*  810 */       if (pantsLen > 4096000) {
/*  811 */         return ValidationResult.error("Pants exceeds max length 4096000");
/*      */       }
/*  813 */       pos += VarInt.length(buffer, pos);
/*  814 */       pos += pantsLen;
/*  815 */       if (pos > buffer.writerIndex()) {
/*  816 */         return ValidationResult.error("Buffer overflow reading Pants");
/*      */       }
/*      */     } 
/*      */     
/*  820 */     if ((nullBits[1] & 0x4) != 0) {
/*  821 */       int overpantsOffset = buffer.getIntLE(offset + 43);
/*  822 */       if (overpantsOffset < 0) {
/*  823 */         return ValidationResult.error("Invalid offset for Overpants");
/*      */       }
/*  825 */       int pos = offset + 83 + overpantsOffset;
/*  826 */       if (pos >= buffer.writerIndex()) {
/*  827 */         return ValidationResult.error("Offset out of bounds for Overpants");
/*      */       }
/*  829 */       int overpantsLen = VarInt.peek(buffer, pos);
/*  830 */       if (overpantsLen < 0) {
/*  831 */         return ValidationResult.error("Invalid string length for Overpants");
/*      */       }
/*  833 */       if (overpantsLen > 4096000) {
/*  834 */         return ValidationResult.error("Overpants exceeds max length 4096000");
/*      */       }
/*  836 */       pos += VarInt.length(buffer, pos);
/*  837 */       pos += overpantsLen;
/*  838 */       if (pos > buffer.writerIndex()) {
/*  839 */         return ValidationResult.error("Buffer overflow reading Overpants");
/*      */       }
/*      */     } 
/*      */     
/*  843 */     if ((nullBits[1] & 0x8) != 0) {
/*  844 */       int undertopOffset = buffer.getIntLE(offset + 47);
/*  845 */       if (undertopOffset < 0) {
/*  846 */         return ValidationResult.error("Invalid offset for Undertop");
/*      */       }
/*  848 */       int pos = offset + 83 + undertopOffset;
/*  849 */       if (pos >= buffer.writerIndex()) {
/*  850 */         return ValidationResult.error("Offset out of bounds for Undertop");
/*      */       }
/*  852 */       int undertopLen = VarInt.peek(buffer, pos);
/*  853 */       if (undertopLen < 0) {
/*  854 */         return ValidationResult.error("Invalid string length for Undertop");
/*      */       }
/*  856 */       if (undertopLen > 4096000) {
/*  857 */         return ValidationResult.error("Undertop exceeds max length 4096000");
/*      */       }
/*  859 */       pos += VarInt.length(buffer, pos);
/*  860 */       pos += undertopLen;
/*  861 */       if (pos > buffer.writerIndex()) {
/*  862 */         return ValidationResult.error("Buffer overflow reading Undertop");
/*      */       }
/*      */     } 
/*      */     
/*  866 */     if ((nullBits[1] & 0x10) != 0) {
/*  867 */       int overtopOffset = buffer.getIntLE(offset + 51);
/*  868 */       if (overtopOffset < 0) {
/*  869 */         return ValidationResult.error("Invalid offset for Overtop");
/*      */       }
/*  871 */       int pos = offset + 83 + overtopOffset;
/*  872 */       if (pos >= buffer.writerIndex()) {
/*  873 */         return ValidationResult.error("Offset out of bounds for Overtop");
/*      */       }
/*  875 */       int overtopLen = VarInt.peek(buffer, pos);
/*  876 */       if (overtopLen < 0) {
/*  877 */         return ValidationResult.error("Invalid string length for Overtop");
/*      */       }
/*  879 */       if (overtopLen > 4096000) {
/*  880 */         return ValidationResult.error("Overtop exceeds max length 4096000");
/*      */       }
/*  882 */       pos += VarInt.length(buffer, pos);
/*  883 */       pos += overtopLen;
/*  884 */       if (pos > buffer.writerIndex()) {
/*  885 */         return ValidationResult.error("Buffer overflow reading Overtop");
/*      */       }
/*      */     } 
/*      */     
/*  889 */     if ((nullBits[1] & 0x20) != 0) {
/*  890 */       int shoesOffset = buffer.getIntLE(offset + 55);
/*  891 */       if (shoesOffset < 0) {
/*  892 */         return ValidationResult.error("Invalid offset for Shoes");
/*      */       }
/*  894 */       int pos = offset + 83 + shoesOffset;
/*  895 */       if (pos >= buffer.writerIndex()) {
/*  896 */         return ValidationResult.error("Offset out of bounds for Shoes");
/*      */       }
/*  898 */       int shoesLen = VarInt.peek(buffer, pos);
/*  899 */       if (shoesLen < 0) {
/*  900 */         return ValidationResult.error("Invalid string length for Shoes");
/*      */       }
/*  902 */       if (shoesLen > 4096000) {
/*  903 */         return ValidationResult.error("Shoes exceeds max length 4096000");
/*      */       }
/*  905 */       pos += VarInt.length(buffer, pos);
/*  906 */       pos += shoesLen;
/*  907 */       if (pos > buffer.writerIndex()) {
/*  908 */         return ValidationResult.error("Buffer overflow reading Shoes");
/*      */       }
/*      */     } 
/*      */     
/*  912 */     if ((nullBits[1] & 0x40) != 0) {
/*  913 */       int headAccessoryOffset = buffer.getIntLE(offset + 59);
/*  914 */       if (headAccessoryOffset < 0) {
/*  915 */         return ValidationResult.error("Invalid offset for HeadAccessory");
/*      */       }
/*  917 */       int pos = offset + 83 + headAccessoryOffset;
/*  918 */       if (pos >= buffer.writerIndex()) {
/*  919 */         return ValidationResult.error("Offset out of bounds for HeadAccessory");
/*      */       }
/*  921 */       int headAccessoryLen = VarInt.peek(buffer, pos);
/*  922 */       if (headAccessoryLen < 0) {
/*  923 */         return ValidationResult.error("Invalid string length for HeadAccessory");
/*      */       }
/*  925 */       if (headAccessoryLen > 4096000) {
/*  926 */         return ValidationResult.error("HeadAccessory exceeds max length 4096000");
/*      */       }
/*  928 */       pos += VarInt.length(buffer, pos);
/*  929 */       pos += headAccessoryLen;
/*  930 */       if (pos > buffer.writerIndex()) {
/*  931 */         return ValidationResult.error("Buffer overflow reading HeadAccessory");
/*      */       }
/*      */     } 
/*      */     
/*  935 */     if ((nullBits[1] & 0x80) != 0) {
/*  936 */       int faceAccessoryOffset = buffer.getIntLE(offset + 63);
/*  937 */       if (faceAccessoryOffset < 0) {
/*  938 */         return ValidationResult.error("Invalid offset for FaceAccessory");
/*      */       }
/*  940 */       int pos = offset + 83 + faceAccessoryOffset;
/*  941 */       if (pos >= buffer.writerIndex()) {
/*  942 */         return ValidationResult.error("Offset out of bounds for FaceAccessory");
/*      */       }
/*  944 */       int faceAccessoryLen = VarInt.peek(buffer, pos);
/*  945 */       if (faceAccessoryLen < 0) {
/*  946 */         return ValidationResult.error("Invalid string length for FaceAccessory");
/*      */       }
/*  948 */       if (faceAccessoryLen > 4096000) {
/*  949 */         return ValidationResult.error("FaceAccessory exceeds max length 4096000");
/*      */       }
/*  951 */       pos += VarInt.length(buffer, pos);
/*  952 */       pos += faceAccessoryLen;
/*  953 */       if (pos > buffer.writerIndex()) {
/*  954 */         return ValidationResult.error("Buffer overflow reading FaceAccessory");
/*      */       }
/*      */     } 
/*      */     
/*  958 */     if ((nullBits[2] & 0x1) != 0) {
/*  959 */       int earAccessoryOffset = buffer.getIntLE(offset + 67);
/*  960 */       if (earAccessoryOffset < 0) {
/*  961 */         return ValidationResult.error("Invalid offset for EarAccessory");
/*      */       }
/*  963 */       int pos = offset + 83 + earAccessoryOffset;
/*  964 */       if (pos >= buffer.writerIndex()) {
/*  965 */         return ValidationResult.error("Offset out of bounds for EarAccessory");
/*      */       }
/*  967 */       int earAccessoryLen = VarInt.peek(buffer, pos);
/*  968 */       if (earAccessoryLen < 0) {
/*  969 */         return ValidationResult.error("Invalid string length for EarAccessory");
/*      */       }
/*  971 */       if (earAccessoryLen > 4096000) {
/*  972 */         return ValidationResult.error("EarAccessory exceeds max length 4096000");
/*      */       }
/*  974 */       pos += VarInt.length(buffer, pos);
/*  975 */       pos += earAccessoryLen;
/*  976 */       if (pos > buffer.writerIndex()) {
/*  977 */         return ValidationResult.error("Buffer overflow reading EarAccessory");
/*      */       }
/*      */     } 
/*      */     
/*  981 */     if ((nullBits[2] & 0x2) != 0) {
/*  982 */       int skinFeatureOffset = buffer.getIntLE(offset + 71);
/*  983 */       if (skinFeatureOffset < 0) {
/*  984 */         return ValidationResult.error("Invalid offset for SkinFeature");
/*      */       }
/*  986 */       int pos = offset + 83 + skinFeatureOffset;
/*  987 */       if (pos >= buffer.writerIndex()) {
/*  988 */         return ValidationResult.error("Offset out of bounds for SkinFeature");
/*      */       }
/*  990 */       int skinFeatureLen = VarInt.peek(buffer, pos);
/*  991 */       if (skinFeatureLen < 0) {
/*  992 */         return ValidationResult.error("Invalid string length for SkinFeature");
/*      */       }
/*  994 */       if (skinFeatureLen > 4096000) {
/*  995 */         return ValidationResult.error("SkinFeature exceeds max length 4096000");
/*      */       }
/*  997 */       pos += VarInt.length(buffer, pos);
/*  998 */       pos += skinFeatureLen;
/*  999 */       if (pos > buffer.writerIndex()) {
/* 1000 */         return ValidationResult.error("Buffer overflow reading SkinFeature");
/*      */       }
/*      */     } 
/*      */     
/* 1004 */     if ((nullBits[2] & 0x4) != 0) {
/* 1005 */       int glovesOffset = buffer.getIntLE(offset + 75);
/* 1006 */       if (glovesOffset < 0) {
/* 1007 */         return ValidationResult.error("Invalid offset for Gloves");
/*      */       }
/* 1009 */       int pos = offset + 83 + glovesOffset;
/* 1010 */       if (pos >= buffer.writerIndex()) {
/* 1011 */         return ValidationResult.error("Offset out of bounds for Gloves");
/*      */       }
/* 1013 */       int glovesLen = VarInt.peek(buffer, pos);
/* 1014 */       if (glovesLen < 0) {
/* 1015 */         return ValidationResult.error("Invalid string length for Gloves");
/*      */       }
/* 1017 */       if (glovesLen > 4096000) {
/* 1018 */         return ValidationResult.error("Gloves exceeds max length 4096000");
/*      */       }
/* 1020 */       pos += VarInt.length(buffer, pos);
/* 1021 */       pos += glovesLen;
/* 1022 */       if (pos > buffer.writerIndex()) {
/* 1023 */         return ValidationResult.error("Buffer overflow reading Gloves");
/*      */       }
/*      */     } 
/*      */     
/* 1027 */     if ((nullBits[2] & 0x8) != 0) {
/* 1028 */       int capeOffset = buffer.getIntLE(offset + 79);
/* 1029 */       if (capeOffset < 0) {
/* 1030 */         return ValidationResult.error("Invalid offset for Cape");
/*      */       }
/* 1032 */       int pos = offset + 83 + capeOffset;
/* 1033 */       if (pos >= buffer.writerIndex()) {
/* 1034 */         return ValidationResult.error("Offset out of bounds for Cape");
/*      */       }
/* 1036 */       int capeLen = VarInt.peek(buffer, pos);
/* 1037 */       if (capeLen < 0) {
/* 1038 */         return ValidationResult.error("Invalid string length for Cape");
/*      */       }
/* 1040 */       if (capeLen > 4096000) {
/* 1041 */         return ValidationResult.error("Cape exceeds max length 4096000");
/*      */       }
/* 1043 */       pos += VarInt.length(buffer, pos);
/* 1044 */       pos += capeLen;
/* 1045 */       if (pos > buffer.writerIndex()) {
/* 1046 */         return ValidationResult.error("Buffer overflow reading Cape");
/*      */       }
/*      */     } 
/* 1049 */     return ValidationResult.OK;
/*      */   }
/*      */   
/*      */   public PlayerSkin clone() {
/* 1053 */     PlayerSkin copy = new PlayerSkin();
/* 1054 */     copy.bodyCharacteristic = this.bodyCharacteristic;
/* 1055 */     copy.underwear = this.underwear;
/* 1056 */     copy.face = this.face;
/* 1057 */     copy.eyes = this.eyes;
/* 1058 */     copy.ears = this.ears;
/* 1059 */     copy.mouth = this.mouth;
/* 1060 */     copy.facialHair = this.facialHair;
/* 1061 */     copy.haircut = this.haircut;
/* 1062 */     copy.eyebrows = this.eyebrows;
/* 1063 */     copy.pants = this.pants;
/* 1064 */     copy.overpants = this.overpants;
/* 1065 */     copy.undertop = this.undertop;
/* 1066 */     copy.overtop = this.overtop;
/* 1067 */     copy.shoes = this.shoes;
/* 1068 */     copy.headAccessory = this.headAccessory;
/* 1069 */     copy.faceAccessory = this.faceAccessory;
/* 1070 */     copy.earAccessory = this.earAccessory;
/* 1071 */     copy.skinFeature = this.skinFeature;
/* 1072 */     copy.gloves = this.gloves;
/* 1073 */     copy.cape = this.cape;
/* 1074 */     return copy;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*      */     PlayerSkin other;
/* 1080 */     if (this == obj) return true; 
/* 1081 */     if (obj instanceof PlayerSkin) { other = (PlayerSkin)obj; } else { return false; }
/* 1082 */      return (Objects.equals(this.bodyCharacteristic, other.bodyCharacteristic) && Objects.equals(this.underwear, other.underwear) && Objects.equals(this.face, other.face) && Objects.equals(this.eyes, other.eyes) && Objects.equals(this.ears, other.ears) && Objects.equals(this.mouth, other.mouth) && Objects.equals(this.facialHair, other.facialHair) && Objects.equals(this.haircut, other.haircut) && Objects.equals(this.eyebrows, other.eyebrows) && Objects.equals(this.pants, other.pants) && Objects.equals(this.overpants, other.overpants) && Objects.equals(this.undertop, other.undertop) && Objects.equals(this.overtop, other.overtop) && Objects.equals(this.shoes, other.shoes) && Objects.equals(this.headAccessory, other.headAccessory) && Objects.equals(this.faceAccessory, other.faceAccessory) && Objects.equals(this.earAccessory, other.earAccessory) && Objects.equals(this.skinFeature, other.skinFeature) && Objects.equals(this.gloves, other.gloves) && Objects.equals(this.cape, other.cape));
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1087 */     return Objects.hash(new Object[] { this.bodyCharacteristic, this.underwear, this.face, this.eyes, this.ears, this.mouth, this.facialHair, this.haircut, this.eyebrows, this.pants, this.overpants, this.undertop, this.overtop, this.shoes, this.headAccessory, this.faceAccessory, this.earAccessory, this.skinFeature, this.gloves, this.cape });
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\PlayerSkin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */