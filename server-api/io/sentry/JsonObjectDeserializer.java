/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class JsonObjectDeserializer
/*     */ {
/*     */   private static final class TokenName
/*     */     implements Token
/*     */   {
/*     */     final String value;
/*     */     
/*     */     TokenName(@NotNull String value) {
/*  27 */       this.value = value;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Object getValue() {
/*  32 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class TokenPrimitive implements Token {
/*     */     final Object value;
/*     */     
/*     */     TokenPrimitive(@NotNull Object value) {
/*  40 */       this.value = value;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Object getValue() {
/*  45 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class TokenArray implements Token {
/*  50 */     final ArrayList<Object> value = new ArrayList();
/*     */     private TokenArray() {}
/*     */     @NotNull
/*     */     public Object getValue() {
/*  54 */       return this.value;
/*     */     } }
/*     */   private static final class TokenMap implements Token { final HashMap<String, Object> value;
/*     */     
/*     */     private TokenMap() {
/*  59 */       this.value = new HashMap<>();
/*     */     }
/*     */     @NotNull
/*     */     public Object getValue() {
/*  63 */       return this.value;
/*     */     } }
/*     */ 
/*     */   
/*  67 */   private final ArrayList<Token> tokens = new ArrayList<>();
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Object deserialize(@NotNull JsonObjectReader reader) throws IOException {
/*  72 */     parse(reader);
/*  73 */     Token root = getCurrentToken();
/*  74 */     if (root != null) {
/*  75 */       return root.getValue();
/*     */     }
/*  77 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parse(@NotNull JsonObjectReader reader) throws IOException {
/*  84 */     boolean done = false;
/*  85 */     switch (reader.peek()) {
/*     */       case BEGIN_ARRAY:
/*  87 */         reader.beginArray();
/*  88 */         pushCurrentToken(new TokenArray());
/*     */         break;
/*     */       case END_ARRAY:
/*  91 */         reader.endArray();
/*  92 */         done = handleArrayOrMapEnd();
/*     */         break;
/*     */       case BEGIN_OBJECT:
/*  95 */         reader.beginObject();
/*  96 */         pushCurrentToken(new TokenMap());
/*     */         break;
/*     */       case END_OBJECT:
/*  99 */         reader.endObject();
/* 100 */         done = handleArrayOrMapEnd();
/*     */         break;
/*     */       case NAME:
/* 103 */         pushCurrentToken(new TokenName(reader.nextName()));
/*     */         break;
/*     */ 
/*     */       
/*     */       case STRING:
/* 108 */         done = handlePrimitive(() -> reader.nextString());
/*     */         break;
/*     */       case NUMBER:
/* 111 */         done = handlePrimitive(() -> nextNumber(reader));
/*     */         break;
/*     */ 
/*     */       
/*     */       case BOOLEAN:
/* 116 */         done = handlePrimitive(() -> Boolean.valueOf(reader.nextBoolean()));
/*     */         break;
/*     */       case NULL:
/* 119 */         reader.nextNull();
/* 120 */         done = handlePrimitive(() -> null);
/*     */         break;
/*     */       case END_DOCUMENT:
/* 123 */         done = true;
/*     */         break;
/*     */     } 
/* 126 */     if (!done) {
/* 127 */       parse(reader);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean handleArrayOrMapEnd() {
/* 132 */     if (hasOneToken()) {
/* 133 */       return true;
/*     */     }
/* 135 */     Token arrayOrMapToken = getCurrentToken();
/* 136 */     popCurrentToken();
/*     */     
/* 138 */     if (getCurrentToken() instanceof TokenName) {
/* 139 */       TokenName tokenName = (TokenName)getCurrentToken();
/* 140 */       popCurrentToken();
/*     */       
/* 142 */       TokenMap tokenMap = (TokenMap)getCurrentToken();
/* 143 */       if (tokenName != null && arrayOrMapToken != null && tokenMap != null) {
/* 144 */         tokenMap.value.put(tokenName.value, arrayOrMapToken.getValue());
/*     */       }
/* 146 */     } else if (getCurrentToken() instanceof TokenArray) {
/* 147 */       TokenArray tokenArray = (TokenArray)getCurrentToken();
/* 148 */       if (arrayOrMapToken != null && tokenArray != null) {
/* 149 */         tokenArray.value.add(arrayOrMapToken.getValue());
/*     */       }
/*     */     } 
/* 152 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean handlePrimitive(NextValue callback) throws IOException {
/* 157 */     Object primitive = callback.nextValue();
/* 158 */     if (getCurrentToken() == null && primitive != null) {
/* 159 */       pushCurrentToken(new TokenPrimitive(primitive));
/* 160 */       return true;
/* 161 */     }  if (getCurrentToken() instanceof TokenName) {
/* 162 */       TokenName tokenNameNumber = (TokenName)getCurrentToken();
/* 163 */       popCurrentToken();
/*     */       
/* 165 */       TokenMap tokenMapNumber = (TokenMap)getCurrentToken();
/* 166 */       tokenMapNumber.value.put(tokenNameNumber.value, primitive);
/*     */     }
/* 168 */     else if (getCurrentToken() instanceof TokenArray) {
/* 169 */       TokenArray tokenArrayNumber = (TokenArray)getCurrentToken();
/* 170 */       tokenArrayNumber.value.add(primitive);
/*     */     } 
/* 172 */     return false;
/*     */   }
/*     */   
/*     */   private Object nextNumber(JsonObjectReader reader) throws IOException {
/*     */     try {
/* 177 */       return Integer.valueOf(reader.nextInt());
/* 178 */     } catch (Exception exception) {
/*     */ 
/*     */       
/*     */       try {
/* 182 */         return Double.valueOf(reader.nextDouble());
/* 183 */       } catch (Exception exception1) {
/*     */ 
/*     */         
/* 186 */         return Long.valueOf(reader.nextLong());
/*     */       } 
/*     */     }  } @Nullable
/*     */   private Token getCurrentToken() {
/* 190 */     if (this.tokens.isEmpty()) {
/* 191 */       return null;
/*     */     }
/* 193 */     return this.tokens.get(this.tokens.size() - 1);
/*     */   }
/*     */   
/*     */   private void pushCurrentToken(Token token) {
/* 197 */     this.tokens.add(token);
/*     */   }
/*     */   
/*     */   private void popCurrentToken() {
/* 201 */     if (this.tokens.isEmpty()) {
/*     */       return;
/*     */     }
/* 204 */     this.tokens.remove(this.tokens.size() - 1);
/*     */   }
/*     */   
/*     */   private boolean hasOneToken() {
/* 208 */     return (this.tokens.size() == 1);
/*     */   }
/*     */   
/*     */   private static interface Token {
/*     */     @NotNull
/*     */     Object getValue();
/*     */   }
/*     */   
/*     */   private static interface NextValue {
/*     */     @Nullable
/*     */     Object nextValue() throws IOException;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\JsonObjectDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */