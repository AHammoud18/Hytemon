/*     */ package com.google.gson;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Locale;
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
/*     */ public enum FieldNamingPolicy
/*     */   implements FieldNamingStrategy
/*     */ {
/*  34 */   IDENTITY
/*     */   {
/*     */     public String translateName(Field f) {
/*  37 */       return f.getName();
/*     */     }
/*     */   },
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
/*  52 */   UPPER_CAMEL_CASE
/*     */   {
/*     */     public String translateName(Field f) {
/*  55 */       return null.upperCaseFirstLetter(f.getName());
/*     */     }
/*     */   },
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
/*  72 */   UPPER_CAMEL_CASE_WITH_SPACES
/*     */   {
/*     */     public String translateName(Field f) {
/*  75 */       return null.upperCaseFirstLetter(null.separateCamelCase(f.getName(), ' '));
/*     */     }
/*     */   },
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
/*  94 */   UPPER_CASE_WITH_UNDERSCORES
/*     */   {
/*     */     public String translateName(Field f) {
/*  97 */       return null.separateCamelCase(f.getName(), '_').toUpperCase(Locale.ENGLISH);
/*     */     }
/*     */   },
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
/* 114 */   LOWER_CASE_WITH_UNDERSCORES
/*     */   {
/*     */     public String translateName(Field f) {
/* 117 */       return null.separateCamelCase(f.getName(), '_').toLowerCase(Locale.ENGLISH);
/*     */     }
/*     */   },
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
/* 141 */   LOWER_CASE_WITH_DASHES
/*     */   {
/*     */     public String translateName(Field f) {
/* 144 */       return null.separateCamelCase(f.getName(), '-').toLowerCase(Locale.ENGLISH);
/*     */     }
/*     */   },
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
/* 168 */   LOWER_CASE_WITH_DOTS
/*     */   {
/*     */     public String translateName(Field f) {
/* 171 */       return null.separateCamelCase(f.getName(), '.').toLowerCase(Locale.ENGLISH);
/*     */     }
/*     */   };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String separateCamelCase(String name, char separator) {
/* 180 */     StringBuilder translation = new StringBuilder();
/* 181 */     for (int i = 0, length = name.length(); i < length; i++) {
/* 182 */       char character = name.charAt(i);
/* 183 */       if (Character.isUpperCase(character) && translation.length() != 0) {
/* 184 */         translation.append(separator);
/*     */       }
/* 186 */       translation.append(character);
/*     */     } 
/* 188 */     return translation.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   static String upperCaseFirstLetter(String s) {
/* 193 */     int length = s.length();
/* 194 */     for (int i = 0; i < length; i++) {
/* 195 */       char c = s.charAt(i);
/* 196 */       if (Character.isLetter(c)) {
/* 197 */         if (Character.isUpperCase(c)) {
/* 198 */           return s;
/*     */         }
/*     */         
/* 201 */         char uppercased = Character.toUpperCase(c);
/*     */         
/* 203 */         if (i == 0) {
/* 204 */           return uppercased + s.substring(1);
/*     */         }
/* 206 */         return s.substring(0, i) + uppercased + s.substring(i + 1);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 211 */     return s;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\FieldNamingPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */