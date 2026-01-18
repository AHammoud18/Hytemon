/*     */ package io.netty.resolver;
/*     */ 
/*     */ import io.netty.util.NetUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.net.InetAddress;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HostsFileEntriesProvider
/*     */ {
/*     */   public static Parser parser() {
/* 123 */     return ParserImpl.INSTANCE;
/*     */   }
/*     */   
/* 126 */   static final HostsFileEntriesProvider EMPTY = new HostsFileEntriesProvider(
/*     */       
/* 128 */       Collections.emptyMap(), 
/* 129 */       Collections.emptyMap());
/*     */   
/*     */   private final Map<String, List<InetAddress>> ipv4Entries;
/*     */   private final Map<String, List<InetAddress>> ipv6Entries;
/*     */   
/*     */   HostsFileEntriesProvider(Map<String, List<InetAddress>> ipv4Entries, Map<String, List<InetAddress>> ipv6Entries) {
/* 135 */     this.ipv4Entries = Collections.unmodifiableMap(new HashMap<>(ipv4Entries));
/* 136 */     this.ipv6Entries = Collections.unmodifiableMap(new HashMap<>(ipv6Entries));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, List<InetAddress>> ipv4Entries() {
/* 145 */     return this.ipv4Entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, List<InetAddress>> ipv6Entries() {
/* 154 */     return this.ipv6Entries;
/*     */   }
/*     */   
/*     */   private static final class ParserImpl
/*     */     implements Parser
/*     */   {
/*     */     private static final String WINDOWS_DEFAULT_SYSTEM_ROOT = "C:\\Windows";
/*     */     private static final String WINDOWS_HOSTS_FILE_RELATIVE_PATH = "\\system32\\drivers\\etc\\hosts";
/*     */     private static final String X_PLATFORMS_HOSTS_FILE_PATH = "/etc/hosts";
/* 163 */     private static final Pattern WHITESPACES = Pattern.compile("[ \t]+");
/*     */     
/* 165 */     private static final InternalLogger logger = InternalLoggerFactory.getInstance(HostsFileEntriesProvider.Parser.class);
/*     */     
/* 167 */     static final ParserImpl INSTANCE = new ParserImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HostsFileEntriesProvider parse() throws IOException {
/* 175 */       return parse(locateHostsFile(), new Charset[] { Charset.defaultCharset() });
/*     */     }
/*     */ 
/*     */     
/*     */     public HostsFileEntriesProvider parse(Charset... charsets) throws IOException {
/* 180 */       return parse(locateHostsFile(), charsets);
/*     */     }
/*     */ 
/*     */     
/*     */     public HostsFileEntriesProvider parse(File file, Charset... charsets) throws IOException {
/* 185 */       ObjectUtil.checkNotNull(file, "file");
/* 186 */       ObjectUtil.checkNotNull(charsets, "charsets");
/* 187 */       if (charsets.length == 0) {
/* 188 */         charsets = new Charset[] { Charset.defaultCharset() };
/*     */       }
/* 190 */       if (file.exists() && file.isFile())
/* 191 */         for (Charset charset : charsets) {
/* 192 */           BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
/*     */           
/* 194 */           try { HostsFileEntriesProvider entries = parse(reader);
/* 195 */             if (entries != HostsFileEntriesProvider.EMPTY)
/* 196 */             { HostsFileEntriesProvider hostsFileEntriesProvider = entries;
/*     */               
/* 198 */               reader.close(); return hostsFileEntriesProvider; }  reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */              throw throwable; }
/*     */         
/* 201 */         }   return HostsFileEntriesProvider.EMPTY;
/*     */     }
/*     */ 
/*     */     
/*     */     public HostsFileEntriesProvider parse(Reader reader) throws IOException {
/* 206 */       ObjectUtil.checkNotNull(reader, "reader");
/* 207 */       BufferedReader buff = new BufferedReader(reader); 
/* 208 */       try { Map<String, List<InetAddress>> ipv4Entries = new HashMap<>();
/* 209 */         Map<String, List<InetAddress>> ipv6Entries = new HashMap<>();
/*     */         String line;
/* 211 */         while ((line = buff.readLine()) != null) {
/*     */           
/* 213 */           int commentPosition = line.indexOf('#');
/* 214 */           if (commentPosition != -1) {
/* 215 */             line = line.substring(0, commentPosition);
/*     */           }
/*     */           
/* 218 */           line = line.trim();
/* 219 */           if (line.isEmpty()) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 224 */           List<String> lineParts = new ArrayList<>();
/* 225 */           for (String s : WHITESPACES.split(line)) {
/* 226 */             if (!s.isEmpty()) {
/* 227 */               lineParts.add(s);
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 232 */           if (lineParts.size() < 2) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 237 */           byte[] ipBytes = NetUtil.createByteArrayFromIpAddressString(lineParts.get(0));
/*     */           
/* 239 */           if (ipBytes == null) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 245 */           for (int i = 1; i < lineParts.size(); i++) {
/* 246 */             List<InetAddress> addresses; String hostname = lineParts.get(i);
/* 247 */             String hostnameLower = hostname.toLowerCase(Locale.ENGLISH);
/* 248 */             InetAddress address = InetAddress.getByAddress(hostname, ipBytes);
/*     */             
/* 250 */             if (address instanceof java.net.Inet4Address) {
/* 251 */               addresses = ipv4Entries.get(hostnameLower);
/* 252 */               if (addresses == null) {
/* 253 */                 addresses = new ArrayList<>();
/* 254 */                 ipv4Entries.put(hostnameLower, addresses);
/*     */               } 
/*     */             } else {
/* 257 */               addresses = ipv6Entries.get(hostnameLower);
/* 258 */               if (addresses == null) {
/* 259 */                 addresses = new ArrayList<>();
/* 260 */                 ipv6Entries.put(hostnameLower, addresses);
/*     */               } 
/*     */             } 
/* 263 */             addresses.add(address);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 268 */         HostsFileEntriesProvider hostsFileEntriesProvider = (ipv4Entries.isEmpty() && ipv6Entries.isEmpty()) ? HostsFileEntriesProvider.EMPTY : new HostsFileEntriesProvider(ipv4Entries, ipv6Entries);
/* 269 */         buff.close(); return hostsFileEntriesProvider; }
/*     */       catch (Throwable throwable) { try { buff.close(); }
/*     */         catch (Throwable throwable1)
/*     */         { throwable.addSuppressed(throwable1); }
/*     */          throw throwable; }
/* 274 */        } public HostsFileEntriesProvider parseSilently() { return parseSilently(locateHostsFile(), new Charset[] { Charset.defaultCharset() }); }
/*     */ 
/*     */ 
/*     */     
/*     */     public HostsFileEntriesProvider parseSilently(Charset... charsets) {
/* 279 */       return parseSilently(locateHostsFile(), charsets);
/*     */     }
/*     */ 
/*     */     
/*     */     public HostsFileEntriesProvider parseSilently(File file, Charset... charsets) {
/*     */       try {
/* 285 */         return parse(file, charsets);
/* 286 */       } catch (IOException e) {
/* 287 */         if (logger.isWarnEnabled()) {
/* 288 */           logger.warn("Failed to load and parse hosts file at " + file.getPath(), e);
/*     */         }
/* 290 */         return HostsFileEntriesProvider.EMPTY;
/*     */       } 
/*     */     }
/*     */     
/*     */     private static File locateHostsFile() {
/*     */       File hostsFile;
/* 296 */       if (PlatformDependent.isWindows()) {
/* 297 */         hostsFile = new File(System.getenv("SystemRoot") + "\\system32\\drivers\\etc\\hosts");
/* 298 */         if (!hostsFile.exists()) {
/* 299 */           hostsFile = new File("C:\\Windows\\system32\\drivers\\etc\\hosts");
/*     */         }
/*     */       } else {
/* 302 */         hostsFile = new File("/etc/hosts");
/*     */       } 
/* 304 */       return hostsFile;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Parser {
/*     */     HostsFileEntriesProvider parse() throws IOException;
/*     */     
/*     */     HostsFileEntriesProvider parse(Charset... param1VarArgs) throws IOException;
/*     */     
/*     */     HostsFileEntriesProvider parse(File param1File, Charset... param1VarArgs) throws IOException;
/*     */     
/*     */     HostsFileEntriesProvider parse(Reader param1Reader) throws IOException;
/*     */     
/*     */     HostsFileEntriesProvider parseSilently();
/*     */     
/*     */     HostsFileEntriesProvider parseSilently(Charset... param1VarArgs);
/*     */     
/*     */     HostsFileEntriesProvider parseSilently(File param1File, Charset... param1VarArgs);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\HostsFileEntriesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */