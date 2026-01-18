/*     */ package org.fusesource.jansi.internal;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JansiLoader
/*     */ {
/*     */   private static boolean loaded = false;
/*     */   private static String nativeLibraryPath;
/*     */   private static String nativeLibrarySourceUrl;
/*     */   
/*     */   public static synchronized boolean initialize() {
/*  75 */     if (!loaded) {
/*  76 */       Thread cleanup = new Thread(JansiLoader::cleanup, "cleanup");
/*  77 */       cleanup.setPriority(1);
/*  78 */       cleanup.setDaemon(true);
/*  79 */       cleanup.start();
/*     */     } 
/*     */     try {
/*  82 */       loadJansiNativeLibrary();
/*  83 */     } catch (Exception e) {
/*  84 */       if (!Boolean.parseBoolean(System.getProperty("jansi.graceful", "true"))) {
/*  85 */         throw new RuntimeException("Unable to load jansi native library. You may want set the `jansi.graceful` system property to true to be able to use Jansi on your platform", e);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  90 */     return loaded;
/*     */   }
/*     */   
/*     */   public static String getNativeLibraryPath() {
/*  94 */     return nativeLibraryPath;
/*     */   }
/*     */   
/*     */   public static String getNativeLibrarySourceUrl() {
/*  98 */     return nativeLibrarySourceUrl;
/*     */   }
/*     */   
/*     */   private static File getTempDir() {
/* 102 */     return new File(System.getProperty("jansi.tmpdir", System.getProperty("java.io.tmpdir")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void cleanup() {
/* 110 */     String tempFolder = getTempDir().getAbsolutePath();
/* 111 */     File dir = new File(tempFolder);
/*     */     
/* 113 */     File[] nativeLibFiles = dir.listFiles(new FilenameFilter() {
/* 114 */           private final String searchPattern = "jansi-" + JansiLoader.getVersion();
/*     */           
/*     */           public boolean accept(File dir, String name) {
/* 117 */             return (name.startsWith(this.searchPattern) && !name.endsWith(".lck"));
/*     */           }
/*     */         });
/* 120 */     if (nativeLibFiles != null) {
/* 121 */       for (File nativeLibFile : nativeLibFiles) {
/* 122 */         File lckFile = new File(nativeLibFile.getAbsolutePath() + ".lck");
/* 123 */         if (!lckFile.exists()) {
/*     */           try {
/* 125 */             nativeLibFile.delete();
/* 126 */           } catch (SecurityException e) {
/* 127 */             System.err.println("Failed to delete old native lib" + e.getMessage());
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static int readNBytes(InputStream in, byte[] b) throws IOException {
/* 135 */     int n = 0;
/* 136 */     int len = b.length;
/* 137 */     while (n < len) {
/* 138 */       int count = in.read(b, n, len - n);
/* 139 */       if (count <= 0)
/* 140 */         break;  n += count;
/*     */     } 
/* 142 */     return n;
/*     */   }
/*     */   private static String contentsEquals(InputStream in1, InputStream in2) throws IOException {
/*     */     int numRead2;
/* 146 */     byte[] buffer1 = new byte[8192];
/* 147 */     byte[] buffer2 = new byte[8192];
/*     */ 
/*     */     
/*     */     while (true) {
/* 151 */       int numRead1 = readNBytes(in1, buffer1);
/* 152 */       numRead2 = readNBytes(in2, buffer2);
/* 153 */       if (numRead1 > 0) {
/* 154 */         if (numRead2 <= 0) {
/* 155 */           return "EOF on second stream but not first";
/*     */         }
/* 157 */         if (numRead2 != numRead1) {
/* 158 */           return "Read size different (" + numRead1 + " vs " + numRead2 + ")";
/*     */         }
/*     */         
/* 161 */         if (!Arrays.equals(buffer1, buffer2))
/* 162 */           return "Content differs"; 
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 167 */     if (numRead2 > 0) {
/* 168 */       return "EOF on first stream but not second";
/*     */     }
/* 170 */     return null;
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
/*     */   private static boolean extractAndLoadLibraryFile(String libFolderForCurrentOS, String libraryFileName, String targetFolder) {
/* 186 */     String nativeLibraryFilePath = libFolderForCurrentOS + "/" + libraryFileName;
/*     */ 
/*     */     
/* 189 */     String uuid = randomUUID();
/* 190 */     String extractedLibFileName = String.format("jansi-%s-%s-%s", new Object[] { getVersion(), uuid, libraryFileName });
/* 191 */     String extractedLckFileName = extractedLibFileName + ".lck";
/*     */     
/* 193 */     File extractedLibFile = new File(targetFolder, extractedLibFileName);
/* 194 */     File extractedLckFile = new File(targetFolder, extractedLckFileName);
/*     */     
/*     */     try {
/*     */       
/* 198 */       try { InputStream in = JansiLoader.class.getResourceAsStream(nativeLibraryFilePath); 
/* 199 */         try { if (!extractedLckFile.exists()) {
/* 200 */             (new FileOutputStream(extractedLckFile)).close();
/*     */           }
/* 202 */           Files.copy(in, extractedLibFile.toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/* 203 */           if (in != null) in.close();  } catch (Throwable throwable) { if (in != null)
/*     */             try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  }
/* 205 */       finally { extractedLibFile.deleteOnExit();
/* 206 */         extractedLckFile.deleteOnExit(); }
/*     */ 
/*     */ 
/*     */       
/* 210 */       extractedLibFile.setReadable(true);
/* 211 */       extractedLibFile.setWritable(true);
/* 212 */       extractedLibFile.setExecutable(true);
/*     */ 
/*     */       
/* 215 */       InputStream nativeIn = JansiLoader.class.getResourceAsStream(nativeLibraryFilePath); 
/* 216 */       try { InputStream extractedLibIn = new FileInputStream(extractedLibFile); 
/* 217 */         try { String eq = contentsEquals(nativeIn, extractedLibIn);
/* 218 */           if (eq != null) {
/* 219 */             throw new RuntimeException(String.format("Failed to write a native library file at %s because %s", new Object[] { extractedLibFile, eq }));
/*     */           }
/*     */           
/* 222 */           extractedLibIn.close(); } catch (Throwable throwable) { try { extractedLibIn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 223 */          if (nativeIn != null) nativeIn.close();  } catch (Throwable throwable) { if (nativeIn != null)
/*     */           try { nativeIn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */             throw throwable; }
/* 226 */        if (loadNativeLibrary(extractedLibFile)) {
/*     */         
/* 228 */         nativeLibrarySourceUrl = JansiLoader.class.getResource(nativeLibraryFilePath).toExternalForm();
/* 229 */         return true;
/*     */       } 
/* 231 */     } catch (IOException e) {
/* 232 */       System.err.println(e.getMessage());
/*     */     } 
/* 234 */     return false;
/*     */   }
/*     */   
/*     */   private static String randomUUID() {
/* 238 */     return Long.toHexString((new Random()).nextLong());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean loadNativeLibrary(File libPath) {
/* 248 */     if (libPath.exists()) {
/*     */       try {
/* 250 */         String path = libPath.getAbsolutePath();
/* 251 */         System.load(path);
/* 252 */         nativeLibraryPath = path;
/* 253 */         return true;
/* 254 */       } catch (UnsatisfiedLinkError e) {
/* 255 */         if (!libPath.canExecute()) {
/*     */ 
/*     */ 
/*     */           
/* 259 */           System.err.printf("Failed to load native library:%s. The native library file at %s is not executable, make sure that the directory is mounted on a partition without the noexec flag, or set the jansi.tmpdir system property to point to a proper location.  osinfo: %s%n", new Object[] { libPath
/*     */ 
/*     */ 
/*     */                 
/* 263 */                 .getName(), libPath, OSInfo.getNativeLibFolderPathForCurrentOS() });
/*     */         } else {
/* 265 */           System.err.printf("Failed to load native library:%s. osinfo: %s%n", new Object[] { libPath
/*     */                 
/* 267 */                 .getName(), OSInfo.getNativeLibFolderPathForCurrentOS() });
/*     */         } 
/* 269 */         System.err.println(e);
/* 270 */         return false;
/*     */       } 
/*     */     }
/*     */     
/* 274 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadJansiNativeLibrary() throws Exception {
/* 284 */     if (loaded) {
/*     */       return;
/*     */     }
/*     */     
/* 288 */     List<String> triedPaths = new LinkedList<>();
/*     */ 
/*     */     
/* 291 */     String jansiNativeLibraryPath = System.getProperty("library.jansi.path");
/* 292 */     String jansiNativeLibraryName = System.getProperty("library.jansi.name");
/* 293 */     if (jansiNativeLibraryName == null) {
/* 294 */       jansiNativeLibraryName = System.mapLibraryName("jansi");
/* 295 */       assert jansiNativeLibraryName != null;
/* 296 */       if (jansiNativeLibraryName.endsWith(".dylib")) {
/* 297 */         jansiNativeLibraryName = jansiNativeLibraryName.replace(".dylib", ".jnilib");
/*     */       }
/*     */     } 
/*     */     
/* 301 */     if (jansiNativeLibraryPath != null) {
/* 302 */       String withOs = jansiNativeLibraryPath + "/" + OSInfo.getNativeLibFolderPathForCurrentOS();
/* 303 */       if (loadNativeLibrary(new File(withOs, jansiNativeLibraryName))) {
/* 304 */         loaded = true;
/*     */         return;
/*     */       } 
/* 307 */       triedPaths.add(withOs);
/*     */ 
/*     */       
/* 310 */       if (loadNativeLibrary(new File(jansiNativeLibraryPath, jansiNativeLibraryName))) {
/* 311 */         loaded = true;
/*     */         return;
/*     */       } 
/* 314 */       triedPaths.add(jansiNativeLibraryPath);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 319 */     String packagePath = JansiLoader.class.getPackage().getName().replace('.', '/');
/*     */     
/* 321 */     jansiNativeLibraryPath = String.format("/%s/native/%s", new Object[] { packagePath, OSInfo.getNativeLibFolderPathForCurrentOS() });
/* 322 */     boolean hasNativeLib = hasResource(jansiNativeLibraryPath + "/" + jansiNativeLibraryName);
/*     */     
/* 324 */     if (hasNativeLib) {
/*     */       
/* 326 */       String tempFolder = getTempDir().getAbsolutePath();
/*     */       
/* 328 */       if (extractAndLoadLibraryFile(jansiNativeLibraryPath, jansiNativeLibraryName, tempFolder)) {
/* 329 */         loaded = true;
/*     */         return;
/*     */       } 
/* 332 */       triedPaths.add(jansiNativeLibraryPath);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 337 */     String javaLibraryPath = System.getProperty("java.library.path", "");
/* 338 */     for (String ldPath : javaLibraryPath.split(File.pathSeparator)) {
/* 339 */       if (!ldPath.isEmpty()) {
/*     */ 
/*     */         
/* 342 */         if (loadNativeLibrary(new File(ldPath, jansiNativeLibraryName))) {
/* 343 */           loaded = true;
/*     */           return;
/*     */         } 
/* 346 */         triedPaths.add(ldPath);
/*     */       } 
/*     */     } 
/*     */     
/* 350 */     throw new Exception(String.format("No native library found for os.name=%s, os.arch=%s, paths=[%s]", new Object[] {
/*     */             
/* 352 */             OSInfo.getOSName(), OSInfo.getArchName(), String.join(File.pathSeparator, triedPaths) }));
/*     */   }
/*     */   
/*     */   private static boolean hasResource(String path) {
/* 356 */     return (JansiLoader.class.getResource(path) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMajorVersion() {
/* 363 */     String[] c = getVersion().split("\\.");
/* 364 */     return (c.length > 0) ? Integer.parseInt(c[0]) : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMinorVersion() {
/* 371 */     String[] c = getVersion().split("\\.");
/* 372 */     return (c.length > 1) ? Integer.parseInt(c[1]) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 380 */     URL versionFile = JansiLoader.class.getResource("/org/fusesource/jansi/jansi.properties");
/*     */     
/* 382 */     String version = "unknown";
/*     */     try {
/* 384 */       if (versionFile != null) {
/* 385 */         Properties versionData = new Properties();
/* 386 */         versionData.load(versionFile.openStream());
/* 387 */         version = versionData.getProperty("version", version);
/* 388 */         version = version.trim().replaceAll("[^0-9.]", "");
/*     */       } 
/* 390 */     } catch (IOException e) {
/* 391 */       System.err.println(e);
/*     */     } 
/* 393 */     return version;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\internal\JansiLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */