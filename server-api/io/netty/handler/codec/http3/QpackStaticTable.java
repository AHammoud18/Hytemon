/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.handler.codec.UnsupportedValueConverter;
/*     */ import io.netty.handler.codec.ValueConverter;
/*     */ import io.netty.util.AsciiString;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ final class QpackStaticTable
/*     */ {
/*     */   static final int NOT_FOUND = -1;
/*     */   static final int MASK_NAME_REF = 1024;
/*  41 */   private static final List<QpackHeaderField> STATIC_TABLE = Arrays.asList(new QpackHeaderField[] { 
/*  42 */         newEmptyHeaderField(":authority"), 
/*  43 */         newHeaderField(":path", "/"), 
/*  44 */         newHeaderField("age", "0"), 
/*  45 */         newEmptyHeaderField("content-disposition"), 
/*  46 */         newHeaderField("content-length", "0"), 
/*  47 */         newEmptyHeaderField("cookie"), 
/*  48 */         newEmptyHeaderField("date"), 
/*  49 */         newEmptyHeaderField("etag"), 
/*  50 */         newEmptyHeaderField("if-modified-since"), 
/*  51 */         newEmptyHeaderField("if-none-match"), 
/*  52 */         newEmptyHeaderField("last-modified"), 
/*  53 */         newEmptyHeaderField("link"), 
/*  54 */         newEmptyHeaderField("location"), 
/*  55 */         newEmptyHeaderField("referer"), 
/*  56 */         newEmptyHeaderField("set-cookie"), 
/*  57 */         newHeaderField(":method", "CONNECT"), 
/*  58 */         newHeaderField(":method", "DELETE"), 
/*  59 */         newHeaderField(":method", "GET"), 
/*  60 */         newHeaderField(":method", "HEAD"), 
/*  61 */         newHeaderField(":method", "OPTIONS"), 
/*  62 */         newHeaderField(":method", "POST"), 
/*  63 */         newHeaderField(":method", "PUT"), 
/*  64 */         newHeaderField(":scheme", "http"), 
/*  65 */         newHeaderField(":scheme", "https"), 
/*  66 */         newHeaderField(":status", "103"), 
/*  67 */         newHeaderField(":status", "200"), 
/*  68 */         newHeaderField(":status", "304"), 
/*  69 */         newHeaderField(":status", "404"), 
/*  70 */         newHeaderField(":status", "503"), 
/*  71 */         newHeaderField("accept", "*/*"), 
/*  72 */         newHeaderField("accept", "application/dns-message"), 
/*  73 */         newHeaderField("accept-encoding", "gzip, deflate, br"), 
/*  74 */         newHeaderField("accept-ranges", "bytes"), 
/*  75 */         newHeaderField("access-control-allow-headers", "cache-control"), 
/*  76 */         newHeaderField("access-control-allow-headers", "content-type"), 
/*  77 */         newHeaderField("access-control-allow-origin", "*"), 
/*  78 */         newHeaderField("cache-control", "max-age=0"), 
/*  79 */         newHeaderField("cache-control", "max-age=2592000"), 
/*  80 */         newHeaderField("cache-control", "max-age=604800"), 
/*  81 */         newHeaderField("cache-control", "no-cache"), 
/*  82 */         newHeaderField("cache-control", "no-store"), 
/*  83 */         newHeaderField("cache-control", "public, max-age=31536000"), 
/*  84 */         newHeaderField("content-encoding", "br"), 
/*  85 */         newHeaderField("content-encoding", "gzip"), 
/*  86 */         newHeaderField("content-type", "application/dns-message"), 
/*  87 */         newHeaderField("content-type", "application/javascript"), 
/*  88 */         newHeaderField("content-type", "application/json"), 
/*  89 */         newHeaderField("content-type", "application/x-www-form-urlencoded"), 
/*  90 */         newHeaderField("content-type", "image/gif"), 
/*  91 */         newHeaderField("content-type", "image/jpeg"), 
/*  92 */         newHeaderField("content-type", "image/png"), 
/*  93 */         newHeaderField("content-type", "text/css"), 
/*  94 */         newHeaderField("content-type", "text/html;charset=utf-8"), 
/*  95 */         newHeaderField("content-type", "text/plain"), 
/*  96 */         newHeaderField("content-type", "text/plain;charset=utf-8"), 
/*  97 */         newHeaderField("range", "bytes=0-"), 
/*  98 */         newHeaderField("strict-transport-security", "max-age=31536000"), 
/*  99 */         newHeaderField("strict-transport-security", "max-age=31536000;includesubdomains"), 
/* 100 */         newHeaderField("strict-transport-security", "max-age=31536000;includesubdomains;preload"), 
/* 101 */         newHeaderField("vary", "accept-encoding"), 
/* 102 */         newHeaderField("vary", "origin"), 
/* 103 */         newHeaderField("x-content-type-options", "nosniff"), 
/* 104 */         newHeaderField("x-xss-protection", "1; mode=block"), 
/* 105 */         newHeaderField(":status", "100"), 
/* 106 */         newHeaderField(":status", "204"), 
/* 107 */         newHeaderField(":status", "206"), 
/* 108 */         newHeaderField(":status", "302"), 
/* 109 */         newHeaderField(":status", "400"), 
/* 110 */         newHeaderField(":status", "403"), 
/* 111 */         newHeaderField(":status", "421"), 
/* 112 */         newHeaderField(":status", "425"), 
/* 113 */         newHeaderField(":status", "500"), 
/* 114 */         newEmptyHeaderField("accept-language"), 
/* 115 */         newHeaderField("access-control-allow-credentials", "FALSE"), 
/* 116 */         newHeaderField("access-control-allow-credentials", "TRUE"), 
/* 117 */         newHeaderField("access-control-allow-headers", "*"), 
/* 118 */         newHeaderField("access-control-allow-methods", "get"), 
/* 119 */         newHeaderField("access-control-allow-methods", "get, post, options"), 
/* 120 */         newHeaderField("access-control-allow-methods", "options"), 
/* 121 */         newHeaderField("access-control-expose-headers", "content-length"), 
/* 122 */         newHeaderField("access-control-request-headers", "content-type"), 
/* 123 */         newHeaderField("access-control-request-method", "get"), 
/* 124 */         newHeaderField("access-control-request-method", "post"), 
/* 125 */         newHeaderField("alt-svc", "clear"), 
/* 126 */         newEmptyHeaderField("authorization"), 
/* 127 */         newHeaderField("content-security-policy", "script-src 'none';object-src 'none';base-uri 'none'"), 
/* 128 */         newHeaderField("early-data", "1"), 
/* 129 */         newEmptyHeaderField("expect-ct"), 
/* 130 */         newEmptyHeaderField("forwarded"), 
/* 131 */         newEmptyHeaderField("if-range"), 
/* 132 */         newEmptyHeaderField("origin"), 
/* 133 */         newHeaderField("purpose", "prefetch"), 
/* 134 */         newEmptyHeaderField("server"), 
/* 135 */         newHeaderField("timing-allow-origin", "*"), 
/* 136 */         newHeaderField("upgrade-insecure-requests", "1"), 
/* 137 */         newEmptyHeaderField("user-agent"), 
/* 138 */         newEmptyHeaderField("x-forwarded-for"), 
/* 139 */         newHeaderField("x-frame-options", "deny"), 
/* 140 */         newHeaderField("x-frame-options", "sameorigin") });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   static final int length = STATIC_TABLE.size();
/*     */   
/* 147 */   private static final CharSequenceMap<List<Integer>> STATIC_INDEX_BY_NAME = createMap(length);
/*     */   
/*     */   private static QpackHeaderField newEmptyHeaderField(String name) {
/* 150 */     return new QpackHeaderField((CharSequence)AsciiString.cached(name), (CharSequence)AsciiString.EMPTY_STRING);
/*     */   }
/*     */   
/*     */   private static QpackHeaderField newHeaderField(String name, String value) {
/* 154 */     return new QpackHeaderField((CharSequence)AsciiString.cached(name), (CharSequence)AsciiString.cached(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static QpackHeaderField getField(int index) {
/* 162 */     return STATIC_TABLE.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getIndex(CharSequence name) {
/* 170 */     List<Integer> index = (List<Integer>)STATIC_INDEX_BY_NAME.get(name);
/* 171 */     if (index == null) {
/* 172 */       return -1;
/*     */     }
/*     */     
/* 175 */     return ((Integer)index.get(0)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int findFieldIndex(CharSequence name, CharSequence value) {
/* 185 */     List<Integer> nameIndex = (List<Integer>)STATIC_INDEX_BY_NAME.get(name);
/*     */ 
/*     */     
/* 188 */     if (nameIndex == null) {
/* 189 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 193 */     for (Iterator<Integer> iterator = nameIndex.iterator(); iterator.hasNext(); ) { int index = ((Integer)iterator.next()).intValue();
/* 194 */       QpackHeaderField field = STATIC_TABLE.get(index);
/* 195 */       if (QpackUtil.equalsVariableTime(value, field.value)) {
/* 196 */         return index;
/*     */       } }
/*     */ 
/*     */ 
/*     */     
/* 201 */     return ((Integer)nameIndex.get(0)).intValue() | 0x400;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static CharSequenceMap<List<Integer>> createMap(int length) {
/* 210 */     CharSequenceMap<List<Integer>> mapping = new CharSequenceMap<>(true, (ValueConverter<List<Integer>>)UnsupportedValueConverter.instance(), length);
/* 211 */     for (int index = 0; index < length; index++) {
/* 212 */       QpackHeaderField field = getField(index);
/* 213 */       List<Integer> cursor = (List<Integer>)mapping.get(field.name);
/* 214 */       if (cursor == null) {
/* 215 */         List<Integer> holder = new ArrayList<>(16);
/* 216 */         holder.add(Integer.valueOf(index));
/* 217 */         mapping.set(field.name, holder);
/*     */       } else {
/* 219 */         cursor.add(Integer.valueOf(index));
/*     */       } 
/*     */     } 
/* 222 */     return mapping;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\QpackStaticTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */