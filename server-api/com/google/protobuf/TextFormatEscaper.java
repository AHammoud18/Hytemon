/*     */ package com.google.protobuf;
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
/*     */ final class TextFormatEscaper
/*     */ {
/*     */   static String escapeBytes(ByteSequence input) {
/*  41 */     StringBuilder builder = new StringBuilder(input.size());
/*  42 */     for (int i = 0; i < input.size(); i++) {
/*  43 */       byte b = input.byteAt(i);
/*  44 */       switch (b) {
/*     */         case 7:
/*  46 */           builder.append("\\a");
/*     */           break;
/*     */         case 8:
/*  49 */           builder.append("\\b");
/*     */           break;
/*     */         case 12:
/*  52 */           builder.append("\\f");
/*     */           break;
/*     */         case 10:
/*  55 */           builder.append("\\n");
/*     */           break;
/*     */         case 13:
/*  58 */           builder.append("\\r");
/*     */           break;
/*     */         case 9:
/*  61 */           builder.append("\\t");
/*     */           break;
/*     */         case 11:
/*  64 */           builder.append("\\v");
/*     */           break;
/*     */         case 92:
/*  67 */           builder.append("\\\\");
/*     */           break;
/*     */         case 39:
/*  70 */           builder.append("\\'");
/*     */           break;
/*     */         case 34:
/*  73 */           builder.append("\\\"");
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/*  78 */           if (b >= 32 && b <= 126) {
/*  79 */             builder.append((char)b); break;
/*     */           } 
/*  81 */           builder.append('\\');
/*  82 */           builder.append((char)(48 + (b >>> 6 & 0x3)));
/*  83 */           builder.append((char)(48 + (b >>> 3 & 0x7)));
/*  84 */           builder.append((char)(48 + (b & 0x7)));
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/*  89 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String escapeBytes(final ByteString input) {
/*  96 */     return escapeBytes(new ByteSequence()
/*     */         {
/*     */           public int size()
/*     */           {
/* 100 */             return input.size();
/*     */           }
/*     */ 
/*     */           
/*     */           public byte byteAt(int offset) {
/* 105 */             return input.byteAt(offset);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   static String escapeBytes(final byte[] input) {
/* 112 */     return escapeBytes(new ByteSequence()
/*     */         {
/*     */           public int size()
/*     */           {
/* 116 */             return input.length;
/*     */           }
/*     */ 
/*     */           
/*     */           public byte byteAt(int offset) {
/* 121 */             return input[offset];
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String escapeText(String input) {
/* 130 */     return escapeBytes(ByteString.copyFromUtf8(input));
/*     */   } private static interface ByteSequence {
/*     */     int size();
/*     */     byte byteAt(int param1Int); }
/*     */   static String escapeDoubleQuotesAndBackslashes(String input) {
/* 135 */     return input.replace("\\", "\\\\").replace("\"", "\\\"");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\TextFormatEscaper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */