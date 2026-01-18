/*     */ package io.netty.resolver;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.net.InetAddress;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public final class HostsFileParser
/*     */ {
/*     */   public static HostsFileEntries parseSilently() {
/*  42 */     return hostsFileEntries(HostsFileEntriesProvider.parser().parseSilently());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HostsFileEntries parseSilently(Charset... charsets) {
/*  53 */     return hostsFileEntries(HostsFileEntriesProvider.parser().parseSilently(charsets));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HostsFileEntries parse() throws IOException {
/*  63 */     return hostsFileEntries(HostsFileEntriesProvider.parser().parse());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HostsFileEntries parse(File file) throws IOException {
/*  74 */     return hostsFileEntries(HostsFileEntriesProvider.parser().parse(file, new Charset[0]));
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
/*     */   public static HostsFileEntries parse(File file, Charset... charsets) throws IOException {
/*  86 */     return hostsFileEntries(HostsFileEntriesProvider.parser().parse(file, charsets));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HostsFileEntries parse(Reader reader) throws IOException {
/*  97 */     return hostsFileEntries(HostsFileEntriesProvider.parser().parse(reader));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static HostsFileEntries hostsFileEntries(HostsFileEntriesProvider provider) {
/* 108 */     return (provider == HostsFileEntriesProvider.EMPTY) ? HostsFileEntries.EMPTY : 
/* 109 */       new HostsFileEntries((Map)toMapWithSingleValue(provider.ipv4Entries()), 
/* 110 */         (Map)toMapWithSingleValue(provider.ipv6Entries()));
/*     */   }
/*     */   
/*     */   private static Map<String, ?> toMapWithSingleValue(Map<String, List<InetAddress>> fromMapWithListValue) {
/* 114 */     Map<String, InetAddress> result = new HashMap<>(fromMapWithListValue.size());
/* 115 */     for (Map.Entry<String, List<InetAddress>> entry : fromMapWithListValue.entrySet()) {
/* 116 */       List<InetAddress> value = entry.getValue();
/* 117 */       if (!value.isEmpty()) {
/* 118 */         result.put(entry.getKey(), value.get(0));
/*     */       }
/*     */     } 
/* 121 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\HostsFileParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */