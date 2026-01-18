/*     */ package org.jline.nativ;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Random;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JLineNativeLoader
/*     */ {
/* 115 */   private static final Logger logger = Logger.getLogger("org.jline");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean loaded = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String nativeLibraryPath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String nativeLibrarySourceUrl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized boolean initialize() {
/* 150 */     if (!loaded) {
/* 151 */       Thread cleanup = new Thread(JLineNativeLoader::cleanup, "cleanup");
/* 152 */       cleanup.setPriority(1);
/* 153 */       cleanup.setDaemon(true);
/* 154 */       cleanup.start();
/*     */     } 
/*     */     try {
/* 157 */       loadJLineNativeLibrary();
/* 158 */     } catch (Exception e) {
/* 159 */       throw new RuntimeException("Unable to load jline native library: " + e.getMessage(), e);
/*     */     } 
/* 161 */     return loaded;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getNativeLibraryPath() {
/* 176 */     return nativeLibraryPath;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getNativeLibrarySourceUrl() {
/* 192 */     return nativeLibrarySourceUrl;
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static File getTempDir() {
/* 207 */     return new File(System.getProperty("jline.tmpdir", System.getProperty("java.io.tmpdir")));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void cleanup() {
/* 227 */     String tempFolder = getTempDir().getAbsolutePath();
/* 228 */     File dir = new File(tempFolder);
/*     */     
/* 230 */     File[] nativeLibFiles = dir.listFiles(new FilenameFilter() {
/* 231 */           private final String searchPattern = "jlinenative-" + JLineNativeLoader.getVersion();
/*     */           
/*     */           public boolean accept(File dir, String name) {
/* 234 */             return (name.startsWith(this.searchPattern) && !name.endsWith(".lck"));
/*     */           }
/*     */         });
/* 237 */     if (nativeLibFiles != null) {
/* 238 */       for (File nativeLibFile : nativeLibFiles) {
/* 239 */         File lckFile = new File(nativeLibFile.getAbsolutePath() + ".lck");
/* 240 */         if (!lckFile.exists()) {
/*     */           try {
/* 242 */             nativeLibFile.delete();
/* 243 */           } catch (SecurityException e) {
/* 244 */             logger.log(Level.INFO, "Failed to delete old native lib" + e.getMessage(), e);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static int readNBytes(InputStream in, byte[] b) throws IOException {
/* 252 */     int n = 0;
/* 253 */     int len = b.length;
/* 254 */     while (n < len) {
/* 255 */       int count = in.read(b, n, len - n);
/* 256 */       if (count <= 0)
/* 257 */         break;  n += count;
/*     */     } 
/* 259 */     return n;
/*     */   }
/*     */   private static String contentsEquals(InputStream in1, InputStream in2) throws IOException {
/*     */     int numRead2;
/* 263 */     byte[] buffer1 = new byte[8192];
/* 264 */     byte[] buffer2 = new byte[8192];
/*     */ 
/*     */     
/*     */     while (true) {
/* 268 */       int numRead1 = readNBytes(in1, buffer1);
/* 269 */       numRead2 = readNBytes(in2, buffer2);
/* 270 */       if (numRead1 > 0) {
/* 271 */         if (numRead2 <= 0) {
/* 272 */           return "EOF on second stream but not first";
/*     */         }
/* 274 */         if (numRead2 != numRead1) {
/* 275 */           return "Read size different (" + numRead1 + " vs " + numRead2 + ")";
/*     */         }
/*     */         
/* 278 */         if (!Arrays.equals(buffer1, buffer2))
/* 279 */           return "Content differs"; 
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 284 */     if (numRead2 > 0) {
/* 285 */       return "EOF on first stream but not second";
/*     */     }
/* 287 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean extractAndLoadLibraryFile(String libFolderForCurrentOS, String libraryFileName, String targetFolder) {
/* 319 */     String nativeLibraryFilePath = libFolderForCurrentOS + "/" + libraryFileName;
/*     */ 
/*     */     
/* 322 */     String uuid = randomUUID();
/* 323 */     String extractedLibFileName = String.format("jlinenative-%s-%s-%s", new Object[] { getVersion(), uuid, libraryFileName });
/* 324 */     String extractedLckFileName = extractedLibFileName + ".lck";
/*     */     
/* 326 */     File extractedLibFile = new File(targetFolder, extractedLibFileName);
/* 327 */     File extractedLckFile = new File(targetFolder, extractedLckFileName);
/*     */     
/*     */     try {
/*     */       
/* 331 */       try { InputStream in = JLineNativeLoader.class.getResourceAsStream(nativeLibraryFilePath); 
/* 332 */         try { if (!extractedLckFile.exists()) {
/* 333 */             (new FileOutputStream(extractedLckFile)).close();
/*     */           }
/* 335 */           OutputStream out = new FileOutputStream(extractedLibFile); 
/* 336 */           try { copy(in, out);
/* 337 */             out.close(); } catch (Throwable throwable) { try { out.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 338 */            if (in != null) in.close();  } catch (Throwable throwable) { if (in != null)
/*     */             try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  }
/* 340 */       finally { extractedLibFile.deleteOnExit();
/* 341 */         extractedLckFile.deleteOnExit(); }
/*     */ 
/*     */ 
/*     */       
/* 345 */       extractedLibFile.setReadable(true);
/* 346 */       extractedLibFile.setWritable(true);
/* 347 */       extractedLibFile.setExecutable(true);
/*     */ 
/*     */       
/* 350 */       InputStream nativeIn = JLineNativeLoader.class.getResourceAsStream(nativeLibraryFilePath); 
/* 351 */       try { InputStream extractedLibIn = new FileInputStream(extractedLibFile); 
/* 352 */         try { String eq = contentsEquals(nativeIn, extractedLibIn);
/* 353 */           if (eq != null) {
/* 354 */             throw new RuntimeException(String.format("Failed to write a native library file at %s because %s", new Object[] { extractedLibFile, eq }));
/*     */           }
/*     */           
/* 357 */           extractedLibIn.close(); } catch (Throwable throwable) { try { extractedLibIn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 358 */          if (nativeIn != null) nativeIn.close();  } catch (Throwable throwable) { if (nativeIn != null)
/*     */           try { nativeIn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */             throw throwable; }
/* 361 */        if (loadNativeLibrary(extractedLibFile)) {
/*     */ 
/*     */         
/* 364 */         nativeLibrarySourceUrl = JLineNativeLoader.class.getResource(nativeLibraryFilePath).toExternalForm();
/* 365 */         return true;
/*     */       } 
/* 367 */     } catch (IOException e) {
/* 368 */       log(Level.WARNING, "Unable to load JLine's native library", e);
/*     */     } 
/* 370 */     return false;
/*     */   }
/*     */   
/*     */   private static String randomUUID() {
/* 374 */     return Long.toHexString((new Random()).nextLong());
/*     */   }
/*     */   
/*     */   private static void copy(InputStream in, OutputStream out) throws IOException {
/* 378 */     byte[] buf = new byte[8192];
/*     */     int n;
/* 380 */     while ((n = in.read(buf)) > 0) {
/* 381 */       out.write(buf, 0, n);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean loadNativeLibrary(File libPath) {
/* 403 */     if (libPath.exists()) {
/*     */       
/*     */       try {
/* 406 */         String path = libPath.getAbsolutePath();
/* 407 */         System.load(path);
/* 408 */         nativeLibraryPath = path;
/* 409 */         return true;
/* 410 */       } catch (UnsatisfiedLinkError e) {
/* 411 */         log(Level.WARNING, "Failed to load native library:" + libPath
/*     */             
/* 413 */             .getName() + ". osinfo: " + 
/* 414 */             OSInfo.getNativeLibFolderPathForCurrentOS(), e);
/*     */         
/* 416 */         return false;
/*     */       } 
/*     */     }
/*     */     
/* 420 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadJLineNativeLibrary() throws Exception {
/* 449 */     if (loaded) {
/*     */       return;
/*     */     }
/*     */     
/* 453 */     List<String> triedPaths = new ArrayList<>();
/*     */ 
/*     */     
/* 456 */     String jlineNativeLibraryPath = System.getProperty("library.jline.path");
/* 457 */     String jlineNativeLibraryName = System.getProperty("library.jline.name");
/* 458 */     if (jlineNativeLibraryName == null) {
/* 459 */       jlineNativeLibraryName = System.mapLibraryName("jlinenative");
/* 460 */       assert jlineNativeLibraryName != null;
/* 461 */       if (jlineNativeLibraryName.endsWith(".dylib")) {
/* 462 */         jlineNativeLibraryName = jlineNativeLibraryName.replace(".dylib", ".jnilib");
/*     */       }
/*     */     } 
/*     */     
/* 466 */     if (jlineNativeLibraryPath != null) {
/* 467 */       String withOs = jlineNativeLibraryPath + "/" + OSInfo.getNativeLibFolderPathForCurrentOS();
/* 468 */       if (loadNativeLibrary(new File(withOs, jlineNativeLibraryName))) {
/* 469 */         loaded = true;
/*     */         return;
/*     */       } 
/* 472 */       triedPaths.add(withOs);
/*     */ 
/*     */       
/* 475 */       if (loadNativeLibrary(new File(jlineNativeLibraryPath, jlineNativeLibraryName))) {
/* 476 */         loaded = true;
/*     */         return;
/*     */       } 
/* 479 */       triedPaths.add(jlineNativeLibraryPath);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 484 */     String packagePath = JLineNativeLoader.class.getPackage().getName().replace('.', '/');
/* 485 */     jlineNativeLibraryPath = String.format("/%s/%s", new Object[] { packagePath, OSInfo.getNativeLibFolderPathForCurrentOS() });
/* 486 */     boolean hasNativeLib = hasResource(jlineNativeLibraryPath + "/" + jlineNativeLibraryName);
/*     */     
/* 488 */     if (hasNativeLib) {
/*     */       
/* 490 */       String tempFolder = getTempDir().getAbsolutePath();
/*     */       
/* 492 */       if (extractAndLoadLibraryFile(jlineNativeLibraryPath, jlineNativeLibraryName, tempFolder)) {
/* 493 */         loaded = true;
/*     */         return;
/*     */       } 
/* 496 */       triedPaths.add(jlineNativeLibraryPath);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 501 */     String javaLibraryPath = System.getProperty("java.library.path", "");
/* 502 */     for (String ldPath : javaLibraryPath.split(File.pathSeparator)) {
/* 503 */       if (!ldPath.isEmpty()) {
/*     */ 
/*     */         
/* 506 */         if (loadNativeLibrary(new File(ldPath, jlineNativeLibraryName))) {
/* 507 */           loaded = true;
/*     */           return;
/*     */         } 
/* 510 */         triedPaths.add(ldPath);
/*     */       } 
/*     */     } 
/*     */     
/* 514 */     throw new Exception(String.format("No native library found for os.name=%s, os.arch=%s, paths=[%s]", new Object[] {
/*     */             
/* 516 */             OSInfo.getOSName(), OSInfo.getArchName(), join(triedPaths, File.pathSeparator) }));
/*     */   }
/*     */   
/*     */   private static boolean hasResource(String path) {
/* 520 */     return (JLineNativeLoader.class.getResource(path) != null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMajorVersion() {
/* 536 */     String[] c = getVersion().split("\\.");
/* 537 */     return (c.length > 0) ? Integer.parseInt(c[0]) : 1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMinorVersion() {
/* 553 */     String[] c = getVersion().split("\\.");
/* 554 */     return (c.length > 1) ? Integer.parseInt(c[1]) : 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 573 */     URL versionFile = JLineNativeLoader.class.getResource("/META-INF/maven/org.jline/jline-native/pom.properties");
/*     */     
/* 575 */     String version = "unknown";
/*     */     try {
/* 577 */       if (versionFile != null) {
/* 578 */         Properties versionData = new Properties();
/* 579 */         versionData.load(versionFile.openStream());
/* 580 */         version = versionData.getProperty("version", version);
/* 581 */         version = version.trim().replaceAll("[^0-9.]", "");
/*     */       } 
/* 583 */     } catch (IOException e) {
/* 584 */       log(Level.WARNING, "Unable to load jline-native version", e);
/*     */     } 
/* 586 */     return version;
/*     */   }
/*     */   
/*     */   private static String join(List<String> list, String separator) {
/* 590 */     StringBuilder sb = new StringBuilder();
/* 591 */     boolean first = true;
/* 592 */     for (String item : list) {
/* 593 */       if (first) { first = false; }
/* 594 */       else { sb.append(separator); }
/*     */       
/* 596 */       sb.append(item);
/*     */     } 
/* 598 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private static void log(Level level, String message, Throwable t) {
/* 602 */     if (logger.isLoggable(level))
/* 603 */       if (logger.isLoggable(Level.FINE)) {
/* 604 */         logger.log(level, message, t);
/*     */       } else {
/* 606 */         logger.log(level, message + " (caused by: " + t + ", enable debug logging for stacktrace)");
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\nativ\JLineNativeLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */