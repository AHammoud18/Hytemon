/*     */ package io.netty.handler.codec.http.websocketx.extensions;
/*     */ 
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaderValues;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public final class WebSocketExtensionUtil
/*     */ {
/*     */   private static final String EXTENSION_SEPARATOR = ",";
/*     */   private static final String PARAMETER_SEPARATOR = ";";
/*     */   private static final char PARAMETER_EQUAL = '=';
/*  37 */   private static final Pattern PARAMETER = Pattern.compile("^([^=]+)(=[\\\"]?([^\\\"]+)[\\\"]?)?$");
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isWebsocketUpgrade(HttpHeaders headers) {
/*  42 */     return (headers.contains((CharSequence)HttpHeaderNames.UPGRADE) && headers
/*  43 */       .containsValue((CharSequence)HttpHeaderNames.CONNECTION, (CharSequence)HttpHeaderValues.UPGRADE, true) && headers
/*  44 */       .contains((CharSequence)HttpHeaderNames.UPGRADE, (CharSequence)HttpHeaderValues.WEBSOCKET, true));
/*     */   }
/*     */   
/*     */   public static List<WebSocketExtensionData> extractExtensions(String extensionHeader) {
/*  48 */     String[] rawExtensions = extensionHeader.split(",");
/*  49 */     if (rawExtensions.length > 0) {
/*  50 */       List<WebSocketExtensionData> extensions = new ArrayList<>(rawExtensions.length);
/*  51 */       for (String rawExtension : rawExtensions) {
/*  52 */         Map<String, String> parameters; String[] extensionParameters = rawExtension.split(";");
/*  53 */         String name = extensionParameters[0].trim();
/*     */         
/*  55 */         if (extensionParameters.length > 1) {
/*  56 */           parameters = new LinkedHashMap<>(extensionParameters.length - 1);
/*  57 */           for (int i = 1; i < extensionParameters.length; i++) {
/*  58 */             String parameter = extensionParameters[i].trim();
/*  59 */             Matcher parameterMatcher = PARAMETER.matcher(parameter);
/*  60 */             if (parameterMatcher.matches() && parameterMatcher.group(1) != null) {
/*  61 */               parameters.put(parameterMatcher.group(1), parameterMatcher.group(3));
/*     */             }
/*     */           } 
/*     */         } else {
/*  65 */           parameters = Collections.emptyMap();
/*     */         } 
/*  67 */         extensions.add(new WebSocketExtensionData(name, parameters));
/*     */       } 
/*  69 */       return extensions;
/*     */     } 
/*  71 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String computeMergeExtensionsHeaderValue(String userDefinedHeaderValue, List<WebSocketExtensionData> extraExtensions) {
/*  80 */     List<WebSocketExtensionData> userDefinedExtensions = (userDefinedHeaderValue != null) ? extractExtensions(userDefinedHeaderValue) : Collections.<WebSocketExtensionData>emptyList();
/*     */     
/*  82 */     for (WebSocketExtensionData userDefined : userDefinedExtensions) {
/*  83 */       WebSocketExtensionData matchingExtra = null;
/*     */       int i;
/*  85 */       for (i = 0; i < extraExtensions.size(); i++) {
/*  86 */         WebSocketExtensionData extra = extraExtensions.get(i);
/*  87 */         if (extra.name().equals(userDefined.name())) {
/*  88 */           matchingExtra = extra;
/*     */           break;
/*     */         } 
/*     */       } 
/*  92 */       if (matchingExtra == null) {
/*  93 */         extraExtensions.add(userDefined);
/*     */         continue;
/*     */       } 
/*  96 */       Map<String, String> mergedParameters = new LinkedHashMap<>(matchingExtra.parameters());
/*  97 */       mergedParameters.putAll(userDefined.parameters());
/*  98 */       extraExtensions.set(i, new WebSocketExtensionData(matchingExtra.name(), mergedParameters));
/*     */     } 
/*     */ 
/*     */     
/* 102 */     StringBuilder sb = new StringBuilder(150);
/*     */     
/* 104 */     for (WebSocketExtensionData data : extraExtensions) {
/* 105 */       sb.append(data.name());
/* 106 */       for (Map.Entry<String, String> parameter : data.parameters().entrySet()) {
/* 107 */         sb.append(";");
/* 108 */         sb.append(parameter.getKey());
/* 109 */         if (parameter.getValue() != null) {
/* 110 */           sb.append('=');
/* 111 */           sb.append(parameter.getValue());
/*     */         } 
/*     */       } 
/* 114 */       sb.append(",");
/*     */     } 
/*     */     
/* 117 */     if (!extraExtensions.isEmpty()) {
/* 118 */       sb.setLength(sb.length() - ",".length());
/*     */     }
/*     */     
/* 121 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\WebSocketExtensionUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */