/*     */ package org.bson.codecs;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import org.bson.BsonReader;
/*     */ import org.bson.BsonRegularExpression;
/*     */ import org.bson.BsonWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PatternCodec
/*     */   implements Codec<Pattern>
/*     */ {
/*     */   private static final int GLOBAL_FLAG = 256;
/*     */   
/*     */   public void encode(BsonWriter writer, Pattern value, EncoderContext encoderContext) {
/*  35 */     writer.writeRegularExpression(new BsonRegularExpression(value.pattern(), getOptionsAsString(value)));
/*     */   }
/*     */ 
/*     */   
/*     */   public Pattern decode(BsonReader reader, DecoderContext decoderContext) {
/*  40 */     BsonRegularExpression regularExpression = reader.readRegularExpression();
/*  41 */     return Pattern.compile(regularExpression.getPattern(), getOptionsAsInt(regularExpression));
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<Pattern> getEncoderClass() {
/*  46 */     return Pattern.class;
/*     */   }
/*     */   
/*     */   private static String getOptionsAsString(Pattern pattern) {
/*  50 */     int flags = pattern.flags();
/*  51 */     StringBuilder buf = new StringBuilder();
/*     */     
/*  53 */     for (RegexFlag flag : RegexFlag.values()) {
/*  54 */       if ((pattern.flags() & flag.javaFlag) > 0) {
/*  55 */         buf.append(flag.flagChar);
/*  56 */         flags -= flag.javaFlag;
/*     */       } 
/*     */     } 
/*     */     
/*  60 */     if (flags > 0) {
/*  61 */       throw new IllegalArgumentException("some flags could not be recognized.");
/*     */     }
/*     */     
/*  64 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private static int getOptionsAsInt(BsonRegularExpression regularExpression) {
/*  68 */     int optionsInt = 0;
/*     */     
/*  70 */     String optionsString = regularExpression.getOptions();
/*     */     
/*  72 */     if (optionsString == null || optionsString.length() == 0) {
/*  73 */       return optionsInt;
/*     */     }
/*     */     
/*  76 */     optionsString = optionsString.toLowerCase();
/*     */     
/*  78 */     for (int i = 0; i < optionsString.length(); i++) {
/*  79 */       RegexFlag flag = RegexFlag.getByCharacter(optionsString.charAt(i));
/*  80 */       if (flag != null) {
/*  81 */         optionsInt |= flag.javaFlag;
/*  82 */         if (flag.unsupported != null);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/*  88 */         throw new IllegalArgumentException("unrecognized flag [" + optionsString.charAt(i) + "] " + optionsString.charAt(i));
/*     */       } 
/*     */     } 
/*  91 */     return optionsInt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private enum RegexFlag
/*     */   {
/*  98 */     CANON_EQ(128, 'c', "Pattern.CANON_EQ"),
/*  99 */     UNIX_LINES(1, 'd', "Pattern.UNIX_LINES"),
/* 100 */     GLOBAL(256, 'g', null),
/* 101 */     CASE_INSENSITIVE(2, 'i', null),
/* 102 */     MULTILINE(8, 'm', null),
/* 103 */     DOTALL(32, 's', "Pattern.DOTALL"),
/* 104 */     LITERAL(16, 't', "Pattern.LITERAL"),
/* 105 */     UNICODE_CASE(64, 'u', "Pattern.UNICODE_CASE"),
/* 106 */     COMMENTS(4, 'x', null);
/*     */     
/* 108 */     private static final Map<Character, RegexFlag> BY_CHARACTER = new HashMap<>();
/*     */     
/*     */     private final int javaFlag;
/*     */     private final char flagChar;
/*     */     private final String unsupported;
/*     */     
/*     */     static {
/* 115 */       for (RegexFlag flag : values()) {
/* 116 */         BY_CHARACTER.put(Character.valueOf(flag.flagChar), flag);
/*     */       }
/*     */     }
/*     */     
/*     */     public static RegexFlag getByCharacter(char ch) {
/* 121 */       return BY_CHARACTER.get(Character.valueOf(ch));
/*     */     }
/*     */     
/*     */     RegexFlag(int f, char ch, String u) {
/* 125 */       this.javaFlag = f;
/* 126 */       this.flagChar = ch;
/* 127 */       this.unsupported = u;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\PatternCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */