/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.attribute.PosixFilePermission;
/*     */ import java.security.AccessController;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NativeLibraryLoader
/*     */ {
/*  52 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NativeLibraryLoader.class);
/*     */   
/*     */   private static final String NATIVE_RESOURCE_HOME = "META-INF/native/";
/*     */   
/*     */   private static final File WORKDIR;
/*     */   
/*     */   private static final boolean DELETE_NATIVE_LIB_AFTER_LOADING;
/*     */   private static final boolean TRY_TO_PATCH_SHADED_ID;
/*     */   private static final boolean DETECT_NATIVE_LIBRARY_DUPLICATES;
/*  61 */   private static final byte[] UNIQUE_ID_BYTES = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
/*  62 */     .getBytes(CharsetUtil.US_ASCII);
/*     */   
/*     */   static {
/*  65 */     String workdir = SystemPropertyUtil.get("io.netty.native.workdir");
/*  66 */     if (workdir != null) {
/*  67 */       File f = new File(workdir);
/*  68 */       if (!f.exists() && !f.mkdirs()) {
/*  69 */         throw new ExceptionInInitializerError(new IOException("Custom native workdir mkdirs failed: " + workdir));
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/*  74 */         f = f.getAbsoluteFile();
/*  75 */       } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */       
/*  79 */       WORKDIR = f;
/*  80 */       logger.debug("-Dio.netty.native.workdir: " + WORKDIR);
/*     */     } else {
/*  82 */       WORKDIR = PlatformDependent.tmpdir();
/*  83 */       logger.debug("-Dio.netty.native.workdir: " + WORKDIR + " (io.netty.tmpdir)");
/*     */     } 
/*     */     
/*  86 */     DELETE_NATIVE_LIB_AFTER_LOADING = SystemPropertyUtil.getBoolean("io.netty.native.deleteLibAfterLoading", true);
/*     */     
/*  88 */     logger.debug("-Dio.netty.native.deleteLibAfterLoading: {}", Boolean.valueOf(DELETE_NATIVE_LIB_AFTER_LOADING));
/*     */     
/*  90 */     TRY_TO_PATCH_SHADED_ID = SystemPropertyUtil.getBoolean("io.netty.native.tryPatchShadedId", true);
/*     */     
/*  92 */     logger.debug("-Dio.netty.native.tryPatchShadedId: {}", Boolean.valueOf(TRY_TO_PATCH_SHADED_ID));
/*     */     
/*  94 */     DETECT_NATIVE_LIBRARY_DUPLICATES = SystemPropertyUtil.getBoolean("io.netty.native.detectNativeLibraryDuplicates", true);
/*     */     
/*  96 */     logger.debug("-Dio.netty.native.detectNativeLibraryDuplicates: {}", Boolean.valueOf(DETECT_NATIVE_LIBRARY_DUPLICATES));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadFirstAvailable(ClassLoader loader, String... names) {
/* 107 */     List<Throwable> suppressed = new ArrayList<>();
/* 108 */     for (String name : names) {
/*     */       try {
/* 110 */         load(name, loader);
/* 111 */         logger.debug("Loaded library with name '{}'", name);
/*     */         return;
/* 113 */       } catch (Throwable t) {
/* 114 */         suppressed.add(t);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 119 */     IllegalArgumentException iae = new IllegalArgumentException("Failed to load any of the given libraries: " + Arrays.toString((Object[])names));
/* 120 */     ThrowableUtil.addSuppressedAndClear(iae, suppressed);
/* 121 */     throw iae;
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
/*     */   private static String calculateMangledPackagePrefix() {
/* 144 */     String maybeShaded = NativeLibraryLoader.class.getName();
/*     */     
/* 146 */     String expected = "io!netty!util!internal!NativeLibraryLoader".replace('!', '.');
/* 147 */     if (!maybeShaded.endsWith(expected)) {
/* 148 */       throw new UnsatisfiedLinkError(String.format("Could not find prefix added to %s to get %s. When shading, only adding a package prefix is supported", new Object[] { expected, maybeShaded }));
/*     */     }
/*     */ 
/*     */     
/* 152 */     return maybeShaded.substring(0, maybeShaded.length() - expected.length())
/* 153 */       .replace("_", "_1")
/* 154 */       .replace('.', '_');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void load(String originalName, ClassLoader loader) {
/* 161 */     String mangledPackagePrefix = calculateMangledPackagePrefix();
/* 162 */     String name = mangledPackagePrefix + originalName;
/* 163 */     List<Throwable> suppressed = new ArrayList<>();
/*     */     
/*     */     try {
/* 166 */       loadLibrary(loader, name, false);
/*     */       return;
/* 168 */     } catch (Throwable ex) {
/* 169 */       suppressed.add(ex);
/*     */ 
/*     */       
/* 172 */       String libname = System.mapLibraryName(name);
/* 173 */       String path = "META-INF/native/" + libname;
/*     */       
/* 175 */       File tmpFile = null;
/* 176 */       URL url = getResource(path, loader);
/*     */       try {
/* 178 */         if (url == null) {
/* 179 */           if (PlatformDependent.isOsx()) {
/*     */             
/* 181 */             String fileName = path.endsWith(".jnilib") ? ("META-INF/native/lib" + name + ".dynlib") : ("META-INF/native/lib" + name + ".jnilib");
/* 182 */             url = getResource(fileName, loader);
/* 183 */             if (url == null) {
/* 184 */               FileNotFoundException fnf = new FileNotFoundException(fileName);
/* 185 */               ThrowableUtil.addSuppressedAndClear(fnf, suppressed);
/* 186 */               throw fnf;
/*     */             } 
/*     */           } else {
/* 189 */             FileNotFoundException fnf = new FileNotFoundException(path);
/* 190 */             ThrowableUtil.addSuppressedAndClear(fnf, suppressed);
/* 191 */             throw fnf;
/*     */           } 
/*     */         }
/*     */         
/* 195 */         int index = libname.lastIndexOf('.');
/* 196 */         String prefix = libname.substring(0, index);
/* 197 */         String suffix = libname.substring(index);
/*     */         
/* 199 */         tmpFile = PlatformDependent.createTempFile(prefix, suffix, WORKDIR);
/* 200 */         InputStream in = url.openStream(); 
/* 201 */         try { OutputStream out = new FileOutputStream(tmpFile);
/*     */           
/* 203 */           try { byte[] buffer = new byte[8192];
/*     */             int length;
/* 205 */             while ((length = in.read(buffer)) > 0) {
/* 206 */               out.write(buffer, 0, length);
/*     */             }
/* 208 */             out.flush();
/*     */             
/* 210 */             if (shouldShadedLibraryIdBePatched(mangledPackagePrefix))
/*     */             {
/*     */               
/* 213 */               tryPatchShadedLibraryIdAndSign(tmpFile, originalName);
/*     */             }
/* 215 */             out.close(); } catch (Throwable throwable) { try { out.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  if (in != null) in.close();  } catch (Throwable throwable) { if (in != null)
/*     */             try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */               throw throwable; }
/* 218 */          loadLibrary(loader, tmpFile.getPath(), true);
/*     */       }
/* 220 */       catch (UnsatisfiedLinkError e) {
/*     */         try {
/* 222 */           if (tmpFile != null && tmpFile.isFile() && tmpFile.canRead() && 
/* 223 */             !NoexecVolumeDetector.canExecuteExecutable(tmpFile))
/*     */           {
/*     */ 
/*     */             
/* 227 */             String message = String.format("%s exists but cannot be executed even when execute permissions set; check volume for \"noexec\" flag; use -D%s=[path] to set native working directory separately.", new Object[] { tmpFile
/*     */ 
/*     */ 
/*     */                   
/* 231 */                   .getPath(), "io.netty.native.workdir" });
/* 232 */             logger.info(message);
/* 233 */             suppressed.add(ThrowableUtil.unknownStackTrace(new UnsatisfiedLinkError(message), NativeLibraryLoader.class, "load"));
/*     */           }
/*     */         
/* 236 */         } catch (Throwable t) {
/* 237 */           suppressed.add(t);
/* 238 */           logger.debug("Error checking if {} is on a file store mounted with noexec", tmpFile, t);
/*     */         } 
/*     */         
/* 241 */         ThrowableUtil.addSuppressedAndClear(e, suppressed);
/* 242 */         throw e;
/* 243 */       } catch (Exception e) {
/* 244 */         UnsatisfiedLinkError ule = new UnsatisfiedLinkError("could not load a native library: " + name);
/* 245 */         ule.initCause(e);
/* 246 */         ThrowableUtil.addSuppressedAndClear(ule, suppressed);
/* 247 */         throw ule;
/*     */       
/*     */       }
/*     */       finally {
/*     */         
/* 252 */         if (tmpFile != null && (!DELETE_NATIVE_LIB_AFTER_LOADING || !tmpFile.delete()))
/* 253 */           tmpFile.deleteOnExit(); 
/*     */       } 
/*     */       return;
/*     */     } 
/*     */   }
/*     */   private static URL getResource(String path, ClassLoader loader) {
/*     */     Enumeration<URL> urls;
/*     */     try {
/* 261 */       if (loader == null) {
/* 262 */         urls = ClassLoader.getSystemResources(path);
/*     */       } else {
/* 264 */         urls = loader.getResources(path);
/*     */       } 
/* 266 */     } catch (IOException iox) {
/* 267 */       throw new RuntimeException("An error occurred while getting the resources for " + path, iox);
/*     */     } 
/*     */     
/* 270 */     List<URL> urlsList = Collections.list(urls);
/* 271 */     int size = urlsList.size();
/* 272 */     switch (size) {
/*     */       case 0:
/* 274 */         return null;
/*     */       case 1:
/* 276 */         return urlsList.get(0);
/*     */     } 
/* 278 */     if (DETECT_NATIVE_LIBRARY_DUPLICATES) {
/*     */       try {
/* 280 */         MessageDigest md = MessageDigest.getInstance("SHA-256");
/*     */ 
/*     */         
/* 283 */         URL url = urlsList.get(0);
/* 284 */         byte[] digest = digest(md, url);
/* 285 */         boolean allSame = true;
/* 286 */         if (digest != null) {
/* 287 */           for (int i = 1; i < size; i++) {
/* 288 */             byte[] digest2 = digest(md, urlsList.get(i));
/* 289 */             if (digest2 == null || !Arrays.equals(digest, digest2)) {
/* 290 */               allSame = false;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } else {
/* 295 */           allSame = false;
/*     */         } 
/* 297 */         if (allSame) {
/* 298 */           return url;
/*     */         }
/* 300 */       } catch (NoSuchAlgorithmException e) {
/* 301 */         logger.debug("Don't support SHA-256, can't check if resources have same content.", e);
/*     */       } 
/*     */       
/* 304 */       throw new IllegalStateException("Multiple resources found for '" + path + "' with different content: " + urlsList);
/*     */     } 
/*     */     
/* 307 */     logger.warn("Multiple resources found for '" + path + "' with different content: " + urlsList + ". Please fix your dependency graph.");
/*     */     
/* 309 */     return urlsList.get(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] digest(MessageDigest digest, URL url) {
/*     */     
/* 315 */     try { InputStream in = url.openStream(); 
/* 316 */       try { byte[] bytes = new byte[8192];
/*     */         int i;
/* 318 */         while ((i = in.read(bytes)) != -1) {
/* 319 */           digest.update(bytes, 0, i);
/*     */         }
/* 321 */         byte[] arrayOfByte1 = digest.digest();
/* 322 */         if (in != null) in.close();  return arrayOfByte1; } catch (Throwable throwable) { if (in != null) try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 323 */     { logger.debug("Can't read resource.", e);
/* 324 */       return null; }
/*     */   
/*     */   }
/*     */   
/*     */   static void tryPatchShadedLibraryIdAndSign(File libraryFile, String originalName) {
/* 329 */     if (!(new File("/Library/Developer/CommandLineTools")).exists()) {
/* 330 */       logger.debug("Can't patch shaded library id as CommandLineTools are not installed. Consider installing CommandLineTools with 'xcode-select --install'");
/*     */       
/*     */       return;
/*     */     } 
/* 334 */     String newId = new String(generateUniqueId(originalName.length()), CharsetUtil.UTF_8);
/* 335 */     if (!tryExec("install_name_tool -id " + newId + " " + libraryFile.getAbsolutePath())) {
/*     */       return;
/*     */     }
/*     */     
/* 339 */     tryExec("codesign -s - " + libraryFile.getAbsolutePath());
/*     */   }
/*     */   
/*     */   private static boolean tryExec(String cmd) {
/*     */     try {
/* 344 */       int exitValue = Runtime.getRuntime().exec(cmd).waitFor();
/* 345 */       if (exitValue != 0) {
/* 346 */         logger.debug("Execution of '{}' failed: {}", cmd, Integer.valueOf(exitValue));
/* 347 */         return false;
/*     */       } 
/* 349 */       logger.debug("Execution of '{}' succeed: {}", cmd, Integer.valueOf(exitValue));
/* 350 */       return true;
/* 351 */     } catch (InterruptedException e) {
/* 352 */       Thread.currentThread().interrupt();
/* 353 */     } catch (IOException e) {
/* 354 */       logger.info("Execution of '{}' failed.", cmd, e);
/* 355 */     } catch (SecurityException e) {
/* 356 */       logger.error("Execution of '{}' failed.", cmd, e);
/*     */     } 
/* 358 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean shouldShadedLibraryIdBePatched(String packagePrefix) {
/* 362 */     return (TRY_TO_PATCH_SHADED_ID && PlatformDependent.isOsx() && !packagePrefix.isEmpty());
/*     */   }
/*     */   
/*     */   private static byte[] generateUniqueId(int length) {
/* 366 */     byte[] idBytes = new byte[length];
/* 367 */     for (int i = 0; i < idBytes.length; i++)
/*     */     {
/* 369 */       idBytes[i] = UNIQUE_ID_BYTES[ThreadLocalRandom.current()
/* 370 */           .nextInt(UNIQUE_ID_BYTES.length)];
/*     */     }
/* 372 */     return idBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadLibrary(ClassLoader loader, String name, boolean absolute) {
/* 382 */     Throwable suppressed = null;
/*     */ 
/*     */ 
/*     */     
/* 386 */     try { Class<?> newHelper = tryToLoadClass(loader, NativeLibraryUtil.class);
/* 387 */       loadLibraryByHelper(newHelper, name, absolute);
/* 388 */       logger.debug("Successfully loaded the library {}", name);
/*     */       return; }
/* 390 */     catch (UnsatisfiedLinkError e)
/* 391 */     { suppressed = e;
/*     */ 
/*     */ 
/*     */       
/* 395 */       NativeLibraryUtil.loadLibrary(name, absolute);
/* 396 */       logger.debug("Successfully loaded the library {}", name); } catch (Exception e) { suppressed = e; NativeLibraryUtil.loadLibrary(name, absolute); logger.debug("Successfully loaded the library {}", name); }
/* 397 */     catch (NoSuchMethodError nsme)
/* 398 */     { if (suppressed != null) {
/* 399 */         ThrowableUtil.addSuppressed(nsme, suppressed);
/*     */       }
/* 401 */       throw new LinkageError("Possible multiple incompatible native libraries on the classpath for '" + name + "'?", nsme); }
/*     */     
/* 403 */     catch (UnsatisfiedLinkError ule)
/* 404 */     { if (suppressed != null) {
/* 405 */         ThrowableUtil.addSuppressed(ule, suppressed);
/*     */       }
/* 407 */       throw ule; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadLibraryByHelper(final Class<?> helper, final String name, final boolean absolute) throws UnsatisfiedLinkError {
/* 413 */     Object ret = AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           
/*     */           public Object run()
/*     */           {
/*     */             try {
/* 419 */               Method method = helper.getMethod("loadLibrary", new Class[] { String.class, boolean.class });
/* 420 */               method.setAccessible(true);
/* 421 */               return method.invoke(null, new Object[] { this.val$name, Boolean.valueOf(this.val$absolute) });
/* 422 */             } catch (Exception e) {
/* 423 */               return e;
/*     */             } 
/*     */           }
/*     */         });
/* 427 */     if (ret instanceof Throwable) {
/* 428 */       Throwable t = (Throwable)ret;
/* 429 */       assert !(t instanceof UnsatisfiedLinkError) : t + " should be a wrapper throwable";
/* 430 */       Throwable cause = t.getCause();
/* 431 */       if (cause instanceof UnsatisfiedLinkError) {
/* 432 */         throw (UnsatisfiedLinkError)cause;
/*     */       }
/* 434 */       UnsatisfiedLinkError ule = new UnsatisfiedLinkError(t.getMessage());
/* 435 */       ule.initCause(t);
/* 436 */       throw ule;
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
/*     */   private static Class<?> tryToLoadClass(final ClassLoader loader, final Class<?> helper) throws ClassNotFoundException {
/*     */     try {
/* 450 */       return Class.forName(helper.getName(), false, loader);
/* 451 */     } catch (ClassNotFoundException e1) {
/* 452 */       if (loader == null)
/*     */       {
/* 454 */         throw e1;
/*     */       }
/*     */       
/*     */       try {
/* 458 */         final byte[] classBinary = classToByteArray(helper);
/* 459 */         return AccessController.<Class<?>>doPrivileged(new PrivilegedAction<Class<?>>()
/*     */             {
/*     */               
/*     */               public Class<?> run()
/*     */               {
/*     */                 try {
/* 465 */                   Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class });
/*     */                   
/* 467 */                   defineClass.setAccessible(true);
/* 468 */                   return (Class)defineClass.invoke(loader, new Object[] { this.val$helper.getName(), this.val$classBinary, Integer.valueOf(0), 
/* 469 */                         Integer.valueOf(this.val$classBinary.length) });
/* 470 */                 } catch (Exception e) {
/* 471 */                   throw new IllegalStateException("Define class failed!", e);
/*     */                 } 
/*     */               }
/*     */             });
/* 475 */       } catch (ClassNotFoundException|RuntimeException|Error e2) {
/* 476 */         ThrowableUtil.addSuppressed(e2, e1);
/* 477 */         throw e2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] classToByteArray(Class<?> clazz) throws ClassNotFoundException {
/* 489 */     String fileName = clazz.getName();
/* 490 */     int lastDot = fileName.lastIndexOf('.');
/* 491 */     if (lastDot > 0) {
/* 492 */       fileName = fileName.substring(lastDot + 1);
/*     */     }
/* 494 */     URL classUrl = clazz.getResource(fileName + ".class");
/* 495 */     if (classUrl == null) {
/* 496 */       throw new ClassNotFoundException(clazz.getName());
/*     */     }
/* 498 */     byte[] buf = new byte[1024];
/* 499 */     ByteArrayOutputStream out = new ByteArrayOutputStream(4096); 
/* 500 */     try { InputStream in = classUrl.openStream(); 
/* 501 */       try { int r; while ((r = in.read(buf)) != -1) {
/* 502 */           out.write(buf, 0, r);
/*     */         }
/* 504 */         byte[] arrayOfByte = out.toByteArray();
/* 505 */         if (in != null) in.close();  return arrayOfByte; } catch (Throwable throwable) { if (in != null) try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException ex)
/* 506 */     { throw new ClassNotFoundException(clazz.getName(), ex); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class NoexecVolumeDetector
/*     */   {
/*     */     private static boolean canExecuteExecutable(File file) throws IOException {
/* 518 */       if (file.canExecute()) {
/* 519 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 526 */       Set<PosixFilePermission> existingFilePermissions = Files.getPosixFilePermissions(file.toPath(), new java.nio.file.LinkOption[0]);
/*     */       
/* 528 */       Set<PosixFilePermission> executePermissions = EnumSet.of(PosixFilePermission.OWNER_EXECUTE, PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.OTHERS_EXECUTE);
/*     */ 
/*     */       
/* 531 */       if (existingFilePermissions.containsAll(executePermissions)) {
/* 532 */         return false;
/*     */       }
/*     */       
/* 535 */       Set<PosixFilePermission> newPermissions = EnumSet.copyOf(existingFilePermissions);
/* 536 */       newPermissions.addAll(executePermissions);
/* 537 */       Files.setPosixFilePermissions(file.toPath(), newPermissions);
/* 538 */       return file.canExecute();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\NativeLibraryLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */