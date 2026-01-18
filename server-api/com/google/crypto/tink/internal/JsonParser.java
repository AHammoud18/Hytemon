/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.NotSerializableException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.StringReader;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import javax.annotation.Nullable;
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
/*     */ public final class JsonParser
/*     */ {
/*     */   public static boolean isValidString(String s) {
/*  49 */     int length = s.length();
/*  50 */     int i = 0;
/*     */ 
/*     */     
/*     */     while (true) {
/*  54 */       if (i == length) {
/*  55 */         return true;
/*     */       }
/*  57 */       char ch = s.charAt(i);
/*  58 */       i++;
/*  59 */       if (Character.isSurrogate(ch)) {
/*  60 */         if (Character.isLowSurrogate(ch) || i == length || !Character.isLowSurrogate(s.charAt(i))) {
/*  61 */           return false;
/*     */         }
/*  63 */         i++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class LazilyParsedNumber
/*     */     extends Number {
/*     */     private final String value;
/*     */     
/*     */     public LazilyParsedNumber(String value) {
/*  73 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public int intValue() {
/*     */       try {
/*  79 */         return Integer.parseInt(this.value);
/*  80 */       } catch (NumberFormatException e) {
/*     */         try {
/*  82 */           return (int)Long.parseLong(this.value);
/*  83 */         } catch (NumberFormatException nfe) {
/*  84 */           return (new BigDecimal(this.value)).intValue();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long longValue() {
/*     */       try {
/*  92 */         return Long.parseLong(this.value);
/*  93 */       } catch (NumberFormatException e) {
/*  94 */         return (new BigDecimal(this.value)).longValue();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float floatValue() {
/* 100 */       return Float.parseFloat(this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public double doubleValue() {
/* 105 */       return Double.parseDouble(this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 110 */       return this.value;
/*     */     }
/*     */     
/*     */     private Object writeReplace() throws NotSerializableException {
/* 114 */       throw new NotSerializableException("serialization is not supported");
/*     */     }
/*     */     
/*     */     private void readObject(ObjectInputStream in) throws NotSerializableException {
/* 118 */       throw new NotSerializableException("serialization is not supported");
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 123 */       return this.value.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 128 */       if (this == obj) {
/* 129 */         return true;
/*     */       }
/* 131 */       if (obj instanceof LazilyParsedNumber) {
/* 132 */         LazilyParsedNumber other = (LazilyParsedNumber)obj;
/* 133 */         return this.value.equals(other.value);
/*     */       } 
/* 135 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class JsonElementTypeAdapter
/*     */     extends TypeAdapter<JsonElement>
/*     */   {
/*     */     private static final int RECURSION_LIMIT = 100;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private JsonElementTypeAdapter() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private JsonElement tryBeginNesting(JsonReader in, JsonToken peeked) throws IOException {
/*     */       // Byte code:
/*     */       //   0: getstatic com/google/crypto/tink/internal/JsonParser$1.$SwitchMap$com$google$gson$stream$JsonToken : [I
/*     */       //   3: aload_2
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: lookupswitch default -> 60, 1 -> 36, 2 -> 48
/*     */       //   36: aload_1
/*     */       //   37: invokevirtual beginArray : ()V
/*     */       //   40: new com/google/gson/JsonArray
/*     */       //   43: dup
/*     */       //   44: invokespecial <init> : ()V
/*     */       //   47: areturn
/*     */       //   48: aload_1
/*     */       //   49: invokevirtual beginObject : ()V
/*     */       //   52: new com/google/gson/JsonObject
/*     */       //   55: dup
/*     */       //   56: invokespecial <init> : ()V
/*     */       //   59: areturn
/*     */       //   60: aconst_null
/*     */       //   61: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #149	-> 0
/*     */       //   #151	-> 36
/*     */       //   #152	-> 40
/*     */       //   #154	-> 48
/*     */       //   #155	-> 52
/*     */       //   #157	-> 60
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	62	0	this	Lcom/google/crypto/tink/internal/JsonParser$JsonElementTypeAdapter;
/*     */       //   0	62	1	in	Lcom/google/gson/stream/JsonReader;
/*     */       //   0	62	2	peeked	Lcom/google/gson/stream/JsonToken;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private JsonElement readTerminal(JsonReader in, JsonToken peeked) throws IOException {
/*     */       // Byte code:
/*     */       //   0: getstatic com/google/crypto/tink/internal/JsonParser$1.$SwitchMap$com$google$gson$stream$JsonToken : [I
/*     */       //   3: aload_2
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: tableswitch default -> 117, 3 -> 40, 4 -> 71, 5 -> 94, 6 -> 109
/*     */       //   40: aload_1
/*     */       //   41: invokevirtual nextString : ()Ljava/lang/String;
/*     */       //   44: astore_3
/*     */       //   45: aload_3
/*     */       //   46: invokestatic isValidString : (Ljava/lang/String;)Z
/*     */       //   49: ifne -> 62
/*     */       //   52: new java/io/IOException
/*     */       //   55: dup
/*     */       //   56: ldc 'illegal characters in string'
/*     */       //   58: invokespecial <init> : (Ljava/lang/String;)V
/*     */       //   61: athrow
/*     */       //   62: new com/google/gson/JsonPrimitive
/*     */       //   65: dup
/*     */       //   66: aload_3
/*     */       //   67: invokespecial <init> : (Ljava/lang/String;)V
/*     */       //   70: areturn
/*     */       //   71: aload_1
/*     */       //   72: invokevirtual nextString : ()Ljava/lang/String;
/*     */       //   75: astore #4
/*     */       //   77: new com/google/gson/JsonPrimitive
/*     */       //   80: dup
/*     */       //   81: new com/google/crypto/tink/internal/JsonParser$LazilyParsedNumber
/*     */       //   84: dup
/*     */       //   85: aload #4
/*     */       //   87: invokespecial <init> : (Ljava/lang/String;)V
/*     */       //   90: invokespecial <init> : (Ljava/lang/Number;)V
/*     */       //   93: areturn
/*     */       //   94: new com/google/gson/JsonPrimitive
/*     */       //   97: dup
/*     */       //   98: aload_1
/*     */       //   99: invokevirtual nextBoolean : ()Z
/*     */       //   102: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*     */       //   105: invokespecial <init> : (Ljava/lang/Boolean;)V
/*     */       //   108: areturn
/*     */       //   109: aload_1
/*     */       //   110: invokevirtual nextNull : ()V
/*     */       //   113: getstatic com/google/gson/JsonNull.INSTANCE : Lcom/google/gson/JsonNull;
/*     */       //   116: areturn
/*     */       //   117: new java/lang/IllegalStateException
/*     */       //   120: dup
/*     */       //   121: new java/lang/StringBuilder
/*     */       //   124: dup
/*     */       //   125: invokespecial <init> : ()V
/*     */       //   128: ldc 'Unexpected token: '
/*     */       //   130: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */       //   133: aload_2
/*     */       //   134: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */       //   137: invokevirtual toString : ()Ljava/lang/String;
/*     */       //   140: invokespecial <init> : (Ljava/lang/String;)V
/*     */       //   143: athrow
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #163	-> 0
/*     */       //   #165	-> 40
/*     */       //   #166	-> 45
/*     */       //   #167	-> 52
/*     */       //   #169	-> 62
/*     */       //   #171	-> 71
/*     */       //   #172	-> 77
/*     */       //   #174	-> 94
/*     */       //   #176	-> 109
/*     */       //   #177	-> 113
/*     */       //   #180	-> 117
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   45	26	3	value	Ljava/lang/String;
/*     */       //   77	17	4	number	Ljava/lang/String;
/*     */       //   0	144	0	this	Lcom/google/crypto/tink/internal/JsonParser$JsonElementTypeAdapter;
/*     */       //   0	144	1	in	Lcom/google/gson/stream/JsonReader;
/*     */       //   0	144	2	peeked	Lcom/google/gson/stream/JsonToken;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonElement read(JsonReader in) throws IOException {
/* 188 */       JsonToken peeked = in.peek();
/*     */       
/* 190 */       JsonElement current = tryBeginNesting(in, peeked);
/* 191 */       if (current == null) {
/* 192 */         return readTerminal(in, peeked);
/*     */       }
/*     */       
/* 195 */       Deque<JsonElement> stack = new ArrayDeque<>();
/*     */       
/*     */       while (true) {
/* 198 */         while (in.hasNext()) {
/* 199 */           String name = null;
/*     */           
/* 201 */           if (current instanceof JsonObject) {
/* 202 */             name = in.nextName();
/* 203 */             if (!JsonParser.isValidString(name)) {
/* 204 */               throw new IOException("illegal characters in string");
/*     */             }
/*     */           } 
/*     */           
/* 208 */           peeked = in.peek();
/* 209 */           JsonElement value = tryBeginNesting(in, peeked);
/* 210 */           boolean isNesting = (value != null);
/*     */           
/* 212 */           if (value == null) {
/* 213 */             value = readTerminal(in, peeked);
/*     */           }
/*     */           
/* 216 */           if (current instanceof JsonArray) {
/* 217 */             ((JsonArray)current).add(value);
/*     */           } else {
/* 219 */             if (((JsonObject)current).has(name)) {
/* 220 */               throw new IOException("duplicate key: " + name);
/*     */             }
/* 222 */             ((JsonObject)current).add(name, value);
/*     */           } 
/*     */           
/* 225 */           if (isNesting) {
/* 226 */             stack.addLast(current);
/* 227 */             if (stack.size() > 100) {
/* 228 */               throw new IOException("too many recursions");
/*     */             }
/* 230 */             current = value;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 235 */         if (current instanceof JsonArray) {
/* 236 */           in.endArray();
/*     */         } else {
/* 238 */           in.endObject();
/*     */         } 
/*     */         
/* 241 */         if (stack.isEmpty()) {
/* 242 */           return current;
/*     */         }
/*     */         
/* 245 */         current = stack.removeLast();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(JsonWriter out, JsonElement value) {
/* 252 */       throw new UnsupportedOperationException("write is not supported");
/*     */     }
/*     */   }
/*     */   
/* 256 */   private static final JsonElementTypeAdapter JSON_ELEMENT = new JsonElementTypeAdapter();
/*     */   
/*     */   public static JsonElement parse(String json) throws IOException {
/*     */     try {
/* 260 */       JsonReader jsonReader = new JsonReader(new StringReader(json));
/* 261 */       jsonReader.setLenient(false);
/* 262 */       return JSON_ELEMENT.read(jsonReader);
/* 263 */     } catch (NumberFormatException e) {
/* 264 */       throw new IOException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getParsedNumberAsLongOrThrow(Number number) {
/* 277 */     if (!(number instanceof LazilyParsedNumber))
/*     */     {
/*     */ 
/*     */       
/* 281 */       throw new IllegalArgumentException("does not contain a parsed number.");
/*     */     }
/*     */ 
/*     */     
/* 285 */     return Long.parseLong(number.toString());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\JsonParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */