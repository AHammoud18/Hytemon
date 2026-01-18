/*     */ package io.netty.handler.codec.rtsp;
/*     */ 
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public final class RtspMethods
/*     */ {
/*  37 */   public static final HttpMethod OPTIONS = HttpMethod.OPTIONS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public static final HttpMethod DESCRIBE = HttpMethod.valueOf("DESCRIBE");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public static final HttpMethod ANNOUNCE = HttpMethod.valueOf("ANNOUNCE");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static final HttpMethod SETUP = HttpMethod.valueOf("SETUP");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public static final HttpMethod PLAY = HttpMethod.valueOf("PLAY");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final HttpMethod PAUSE = HttpMethod.valueOf("PAUSE");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public static final HttpMethod TEARDOWN = HttpMethod.valueOf("TEARDOWN");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final HttpMethod GET_PARAMETER = HttpMethod.valueOf("GET_PARAMETER");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public static final HttpMethod SET_PARAMETER = HttpMethod.valueOf("SET_PARAMETER");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static final HttpMethod REDIRECT = HttpMethod.valueOf("REDIRECT");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public static final HttpMethod RECORD = HttpMethod.valueOf("RECORD");
/*     */   
/* 100 */   private static final Map<String, HttpMethod> methodMap = new HashMap<>();
/*     */   
/*     */   static {
/* 103 */     methodMap.put(DESCRIBE.toString(), DESCRIBE);
/* 104 */     methodMap.put(ANNOUNCE.toString(), ANNOUNCE);
/* 105 */     methodMap.put(GET_PARAMETER.toString(), GET_PARAMETER);
/* 106 */     methodMap.put(OPTIONS.toString(), OPTIONS);
/* 107 */     methodMap.put(PAUSE.toString(), PAUSE);
/* 108 */     methodMap.put(PLAY.toString(), PLAY);
/* 109 */     methodMap.put(RECORD.toString(), RECORD);
/* 110 */     methodMap.put(REDIRECT.toString(), REDIRECT);
/* 111 */     methodMap.put(SETUP.toString(), SETUP);
/* 112 */     methodMap.put(SET_PARAMETER.toString(), SET_PARAMETER);
/* 113 */     methodMap.put(TEARDOWN.toString(), TEARDOWN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpMethod valueOf(String name) {
/* 122 */     name = ObjectUtil.checkNonEmptyAfterTrim(name, "name").toUpperCase();
/* 123 */     HttpMethod result = methodMap.get(name);
/* 124 */     if (result != null) {
/* 125 */       return result;
/*     */     }
/* 127 */     return HttpMethod.valueOf(name);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\rtsp\RtspMethods.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */