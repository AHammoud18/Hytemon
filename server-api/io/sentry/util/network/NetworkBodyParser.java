/*     */ package io.sentry.util.network;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.vendor.gson.stream.JsonReader;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.StringReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
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
/*     */ @Internal
/*     */ public final class NetworkBodyParser
/*     */ {
/*     */   @Nullable
/*     */   public static NetworkBody fromBytes(@Nullable byte[] bytes, @Nullable String contentType, @Nullable String charset, int maxSizeBytes, @NotNull ILogger logger) {
/*  44 */     if (bytes == null || bytes.length == 0) {
/*  45 */       return null;
/*     */     }
/*     */     
/*  48 */     if (contentType != null && isBinaryContentType(contentType))
/*     */     {
/*  50 */       return new NetworkBody("[Binary data, " + bytes.length + " bytes, type: " + contentType + "]");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  56 */       String effectiveCharset = (charset != null) ? charset : "UTF-8";
/*  57 */       int size = Math.min(bytes.length, maxSizeBytes);
/*  58 */       boolean isPartial = (bytes.length > maxSizeBytes);
/*  59 */       String content = new String(bytes, 0, size, effectiveCharset);
/*  60 */       return parse(content, contentType, isPartial, logger);
/*  61 */     } catch (UnsupportedEncodingException e) {
/*  62 */       logger.log(SentryLevel.WARNING, "Failed to decode bytes: " + e.getMessage(), new Object[0]);
/*  63 */       return new NetworkBody("[Failed to decode bytes, " + bytes.length + " bytes]", 
/*     */           
/*  65 */           Collections.singletonList(NetworkBody.NetworkBodyWarning.BODY_PARSE_ERROR));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static NetworkBody parse(@Nullable String content, @Nullable String contentType, boolean isPartial, @Nullable ILogger logger) {
/*  75 */     if (content == null || content.isEmpty()) {
/*  76 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  80 */     if (contentType != null) {
/*  81 */       String lowerContentType = contentType.toLowerCase(Locale.ROOT);
/*  82 */       if (lowerContentType.contains("application/x-www-form-urlencoded"))
/*  83 */         return parseFormUrlEncoded(content, isPartial, logger); 
/*  84 */       if (lowerContentType.contains("application/json")) {
/*  85 */         return parseJson(content, isPartial, logger);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  91 */     List<NetworkBody.NetworkBodyWarning> warnings = isPartial ? Collections.<NetworkBody.NetworkBodyWarning>singletonList(NetworkBody.NetworkBodyWarning.TEXT_TRUNCATED) : null;
/*  92 */     return new NetworkBody(content, warnings);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private static NetworkBody parseJson(@NotNull String content, boolean isPartial, @Nullable ILogger logger) {
/*     */     
/*  98 */     try { JsonReader reader = new JsonReader(new StringReader(content)); 
/*  99 */       try { List<NetworkBody.NetworkBodyWarning> warnings; SaferJsonParser.Result result = SaferJsonParser.parse(reader);
/* 100 */         Object data = result.data;
/* 101 */         if (data == null && !isPartial && !result.errored && !result.hitMaxDepth)
/*     */         
/* 103 */         { NetworkBody networkBody1 = new NetworkBody(null);
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
/* 115 */           reader.close(); return networkBody1; }  if (isPartial || result.hitMaxDepth) { warnings = Collections.singletonList(NetworkBody.NetworkBodyWarning.JSON_TRUNCATED); } else if (result.errored) { warnings = Collections.singletonList(NetworkBody.NetworkBodyWarning.INVALID_JSON); } else { warnings = null; }  NetworkBody networkBody = new NetworkBody(data, warnings); reader.close(); return networkBody; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Exception e)
/* 116 */     { if (logger != null) {
/* 117 */         logger.log(SentryLevel.WARNING, "Failed to parse JSON: " + e.getMessage(), new Object[0]);
/*     */       }
/*     */       
/* 120 */       return new NetworkBody(null, 
/* 121 */           Collections.singletonList(NetworkBody.NetworkBodyWarning.INVALID_JSON)); }
/*     */   
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private static NetworkBody parseFormUrlEncoded(@NotNull String content, boolean isPartial, @Nullable ILogger logger) {
/*     */     try {
/*     */       List<NetworkBody.NetworkBodyWarning> warnings;
/* 129 */       Map<String, Object> params = new HashMap<>();
/* 130 */       String[] pairs = content.split("&", -1);
/*     */       
/* 132 */       for (String pair : pairs) {
/* 133 */         int idx = pair.indexOf("=");
/* 134 */         if (idx > 0) {
/* 135 */           String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
/*     */           
/* 137 */           String value = (idx < pair.length() - 1) ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : "";
/*     */ 
/*     */           
/* 140 */           if (params.containsKey(key)) {
/* 141 */             Object existing = params.get(key);
/* 142 */             if (existing instanceof List) {
/*     */               
/* 144 */               List<String> list = (List<String>)existing;
/* 145 */               list.add(value);
/*     */             } else {
/* 147 */               List<String> list = new ArrayList<>();
/* 148 */               list.add((String)existing);
/* 149 */               list.add(value);
/* 150 */               params.put(key, list);
/*     */             } 
/*     */           } else {
/* 153 */             params.put(key, value);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 158 */       if (isPartial) {
/* 159 */         warnings = Collections.singletonList(NetworkBody.NetworkBodyWarning.TEXT_TRUNCATED);
/*     */       } else {
/* 161 */         warnings = null;
/*     */       } 
/* 163 */       return new NetworkBody(params, warnings);
/* 164 */     } catch (UnsupportedEncodingException e) {
/* 165 */       if (logger != null) {
/* 166 */         logger.log(SentryLevel.WARNING, "Failed to parse form data: " + e.getMessage(), new Object[0]);
/*     */       }
/*     */       
/* 169 */       return new NetworkBody(null, 
/* 170 */           Collections.singletonList(NetworkBody.NetworkBodyWarning.BODY_PARSE_ERROR));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isBinaryContentType(@NotNull String contentType) {
/* 175 */     String lower = contentType.toLowerCase(Locale.ROOT);
/* 176 */     return (lower.contains("image/") || lower
/* 177 */       .contains("video/") || lower
/* 178 */       .contains("audio/") || lower
/* 179 */       .contains("application/octet-stream") || lower
/* 180 */       .contains("application/pdf") || lower
/* 181 */       .contains("application/zip") || lower
/* 182 */       .contains("application/gzip"));
/*     */   }
/*     */   
/*     */   private static class SaferJsonParser {
/*     */     private static final int MAX_DEPTH = 100;
/*     */     
/*     */     private static class Result {
/*     */       @Nullable
/*     */       private Object data;
/*     */       private boolean hitMaxDepth;
/*     */       private boolean errored;
/*     */       
/*     */       private Result() {} }
/* 195 */     final Result result = new Result();
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public static Result parse(@NotNull JsonReader reader) {
/* 201 */       SaferJsonParser parser = new SaferJsonParser();
/* 202 */       parser.result.data = parser.parse(reader, 0);
/* 203 */       return parser.result;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     private Object parse(@NotNull JsonReader reader, int currentDepth) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: getfield result : Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;
/*     */       //   4: invokestatic access$100 : (Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;)Z
/*     */       //   7: ifeq -> 12
/*     */       //   10: aconst_null
/*     */       //   11: areturn
/*     */       //   12: iload_2
/*     */       //   13: bipush #100
/*     */       //   15: if_icmplt -> 29
/*     */       //   18: aload_0
/*     */       //   19: getfield result : Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;
/*     */       //   22: iconst_1
/*     */       //   23: invokestatic access$202 : (Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;Z)Z
/*     */       //   26: pop
/*     */       //   27: aconst_null
/*     */       //   28: areturn
/*     */       //   29: getstatic io/sentry/util/network/NetworkBodyParser$1.$SwitchMap$io$sentry$vendor$gson$stream$JsonToken : [I
/*     */       //   32: aload_1
/*     */       //   33: invokevirtual peek : ()Lio/sentry/vendor/gson/stream/JsonToken;
/*     */       //   36: invokevirtual ordinal : ()I
/*     */       //   39: iaload
/*     */       //   40: tableswitch default -> 257, 1 -> 80, 2 -> 157, 3 -> 230, 4 -> 235, 5 -> 243, 6 -> 251
/*     */       //   80: new java/util/LinkedHashMap
/*     */       //   83: dup
/*     */       //   84: invokespecial <init> : ()V
/*     */       //   87: astore_3
/*     */       //   88: aload_1
/*     */       //   89: invokevirtual beginObject : ()V
/*     */       //   92: aload_1
/*     */       //   93: invokevirtual hasNext : ()Z
/*     */       //   96: ifeq -> 135
/*     */       //   99: aload_0
/*     */       //   100: getfield result : Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;
/*     */       //   103: invokestatic access$100 : (Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;)Z
/*     */       //   106: ifne -> 135
/*     */       //   109: aload_1
/*     */       //   110: invokevirtual nextName : ()Ljava/lang/String;
/*     */       //   113: astore #4
/*     */       //   115: aload_3
/*     */       //   116: aload #4
/*     */       //   118: aload_0
/*     */       //   119: aload_1
/*     */       //   120: iload_2
/*     */       //   121: iconst_1
/*     */       //   122: iadd
/*     */       //   123: invokespecial parse : (Lio/sentry/vendor/gson/stream/JsonReader;I)Ljava/lang/Object;
/*     */       //   126: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */       //   131: pop
/*     */       //   132: goto -> 92
/*     */       //   135: aload_1
/*     */       //   136: invokevirtual endObject : ()V
/*     */       //   139: goto -> 155
/*     */       //   142: astore #4
/*     */       //   144: aload_0
/*     */       //   145: getfield result : Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;
/*     */       //   148: iconst_1
/*     */       //   149: invokestatic access$102 : (Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;Z)Z
/*     */       //   152: pop
/*     */       //   153: aload_3
/*     */       //   154: areturn
/*     */       //   155: aload_3
/*     */       //   156: areturn
/*     */       //   157: new java/util/ArrayList
/*     */       //   160: dup
/*     */       //   161: invokespecial <init> : ()V
/*     */       //   164: astore #4
/*     */       //   166: aload_1
/*     */       //   167: invokevirtual beginArray : ()V
/*     */       //   170: aload_1
/*     */       //   171: invokevirtual hasNext : ()Z
/*     */       //   174: ifeq -> 206
/*     */       //   177: aload_0
/*     */       //   178: getfield result : Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;
/*     */       //   181: invokestatic access$100 : (Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;)Z
/*     */       //   184: ifne -> 206
/*     */       //   187: aload #4
/*     */       //   189: aload_0
/*     */       //   190: aload_1
/*     */       //   191: iload_2
/*     */       //   192: iconst_1
/*     */       //   193: iadd
/*     */       //   194: invokespecial parse : (Lio/sentry/vendor/gson/stream/JsonReader;I)Ljava/lang/Object;
/*     */       //   197: invokeinterface add : (Ljava/lang/Object;)Z
/*     */       //   202: pop
/*     */       //   203: goto -> 170
/*     */       //   206: aload_1
/*     */       //   207: invokevirtual endArray : ()V
/*     */       //   210: goto -> 227
/*     */       //   213: astore #5
/*     */       //   215: aload_0
/*     */       //   216: getfield result : Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;
/*     */       //   219: iconst_1
/*     */       //   220: invokestatic access$102 : (Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;Z)Z
/*     */       //   223: pop
/*     */       //   224: aload #4
/*     */       //   226: areturn
/*     */       //   227: aload #4
/*     */       //   229: areturn
/*     */       //   230: aload_1
/*     */       //   231: invokevirtual nextString : ()Ljava/lang/String;
/*     */       //   234: areturn
/*     */       //   235: aload_1
/*     */       //   236: invokevirtual nextDouble : ()D
/*     */       //   239: invokestatic valueOf : (D)Ljava/lang/Double;
/*     */       //   242: areturn
/*     */       //   243: aload_1
/*     */       //   244: invokevirtual nextBoolean : ()Z
/*     */       //   247: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*     */       //   250: areturn
/*     */       //   251: aload_1
/*     */       //   252: invokevirtual nextNull : ()V
/*     */       //   255: aconst_null
/*     */       //   256: areturn
/*     */       //   257: aload_0
/*     */       //   258: getfield result : Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;
/*     */       //   261: iconst_1
/*     */       //   262: invokestatic access$102 : (Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;Z)Z
/*     */       //   265: pop
/*     */       //   266: aconst_null
/*     */       //   267: areturn
/*     */       //   268: astore_3
/*     */       //   269: aload_0
/*     */       //   270: getfield result : Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;
/*     */       //   273: iconst_1
/*     */       //   274: invokestatic access$102 : (Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser$Result;Z)Z
/*     */       //   277: pop
/*     */       //   278: aconst_null
/*     */       //   279: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #208	-> 0
/*     */       //   #209	-> 10
/*     */       //   #211	-> 12
/*     */       //   #212	-> 18
/*     */       //   #213	-> 27
/*     */       //   #216	-> 29
/*     */       //   #218	-> 80
/*     */       //   #220	-> 88
/*     */       //   #221	-> 92
/*     */       //   #222	-> 109
/*     */       //   #223	-> 115
/*     */       //   #224	-> 132
/*     */       //   #225	-> 135
/*     */       //   #229	-> 139
/*     */       //   #226	-> 142
/*     */       //   #227	-> 144
/*     */       //   #228	-> 153
/*     */       //   #230	-> 155
/*     */       //   #233	-> 157
/*     */       //   #235	-> 166
/*     */       //   #236	-> 170
/*     */       //   #237	-> 187
/*     */       //   #239	-> 206
/*     */       //   #243	-> 210
/*     */       //   #240	-> 213
/*     */       //   #241	-> 215
/*     */       //   #242	-> 224
/*     */       //   #244	-> 227
/*     */       //   #247	-> 230
/*     */       //   #250	-> 235
/*     */       //   #253	-> 243
/*     */       //   #256	-> 251
/*     */       //   #257	-> 255
/*     */       //   #260	-> 257
/*     */       //   #261	-> 266
/*     */       //   #263	-> 268
/*     */       //   #264	-> 269
/*     */       //   #265	-> 278
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   115	17	4	name	Ljava/lang/String;
/*     */       //   144	11	4	e	Ljava/lang/Exception;
/*     */       //   88	69	3	map	Ljava/util/Map;
/*     */       //   215	12	5	e	Ljava/lang/Exception;
/*     */       //   166	64	4	list	Ljava/util/List;
/*     */       //   269	11	3	ignored	Ljava/lang/Exception;
/*     */       //   0	280	0	this	Lio/sentry/util/network/NetworkBodyParser$SaferJsonParser;
/*     */       //   0	280	1	reader	Lio/sentry/vendor/gson/stream/JsonReader;
/*     */       //   0	280	2	currentDepth	I
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   88	69	3	map	Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;
/*     */       //   166	64	4	list	Ljava/util/List<Ljava/lang/Object;>;
/*     */       // Exception table:
/*     */       //   from	to	target	type
/*     */       //   29	154	268	java/lang/Exception
/*     */       //   88	139	142	java/lang/Exception
/*     */       //   155	156	268	java/lang/Exception
/*     */       //   157	226	268	java/lang/Exception
/*     */       //   166	210	213	java/lang/Exception
/*     */       //   227	229	268	java/lang/Exception
/*     */       //   230	234	268	java/lang/Exception
/*     */       //   235	242	268	java/lang/Exception
/*     */       //   243	250	268	java/lang/Exception
/*     */       //   251	256	268	java/lang/Exception
/*     */       //   257	267	268	java/lang/Exception
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Result {
/*     */     @Nullable
/*     */     private Object data;
/*     */     private boolean hitMaxDepth;
/*     */     private boolean errored;
/*     */     
/*     */     private Result() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\network\NetworkBodyParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */