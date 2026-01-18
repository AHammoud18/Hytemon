/*     */ package org.jline.nativ;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
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
/*     */ public class OSInfo
/*     */ {
/*     */   public static final String X86 = "x86";
/*     */   public static final String X86_64 = "x86_64";
/*     */   public static final String IA64_32 = "ia64_32";
/*     */   public static final String IA64 = "ia64";
/*     */   public static final String PPC = "ppc";
/*     */   public static final String PPC64 = "ppc64";
/*     */   public static final String ARM64 = "arm64";
/*  53 */   private static final Logger logger = Logger.getLogger("org.jline");
/*  54 */   private static final HashMap<String, String> archMapping = new HashMap<>();
/*     */ 
/*     */   
/*     */   static {
/*  58 */     archMapping.put("x86", "x86");
/*  59 */     archMapping.put("i386", "x86");
/*  60 */     archMapping.put("i486", "x86");
/*  61 */     archMapping.put("i586", "x86");
/*  62 */     archMapping.put("i686", "x86");
/*  63 */     archMapping.put("pentium", "x86");
/*     */ 
/*     */     
/*  66 */     archMapping.put("x86_64", "x86_64");
/*  67 */     archMapping.put("amd64", "x86_64");
/*  68 */     archMapping.put("em64t", "x86_64");
/*  69 */     archMapping.put("universal", "x86_64");
/*     */ 
/*     */     
/*  72 */     archMapping.put("ia64", "ia64");
/*  73 */     archMapping.put("ia64w", "ia64");
/*     */ 
/*     */     
/*  76 */     archMapping.put("ia64_32", "ia64_32");
/*  77 */     archMapping.put("ia64n", "ia64_32");
/*     */ 
/*     */     
/*  80 */     archMapping.put("ppc", "ppc");
/*  81 */     archMapping.put("power", "ppc");
/*  82 */     archMapping.put("powerpc", "ppc");
/*  83 */     archMapping.put("power_pc", "ppc");
/*  84 */     archMapping.put("power_rs", "ppc");
/*     */ 
/*     */     
/*  87 */     archMapping.put("ppc64", "ppc64");
/*  88 */     archMapping.put("power64", "ppc64");
/*  89 */     archMapping.put("powerpc64", "ppc64");
/*  90 */     archMapping.put("power_pc64", "ppc64");
/*  91 */     archMapping.put("power_rs64", "ppc64");
/*     */     
/*  93 */     archMapping.put("aarch64", "arm64");
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/*  97 */     if (args.length >= 1) {
/*  98 */       if ("--os".equals(args[0])) {
/*  99 */         System.out.print(getOSName()); return;
/*     */       } 
/* 101 */       if ("--arch".equals(args[0])) {
/* 102 */         System.out.print(getArchName());
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 107 */     System.out.print(getNativeLibFolderPathForCurrentOS());
/*     */   }
/*     */   
/*     */   public static String getNativeLibFolderPathForCurrentOS() {
/* 111 */     return getOSName() + "/" + getArchName();
/*     */   }
/*     */   
/*     */   public static String getOSName() {
/* 115 */     return translateOSNameToFolderName(System.getProperty("os.name"));
/*     */   }
/*     */   
/*     */   public static boolean isAndroid() {
/* 119 */     return System.getProperty("java.runtime.name", "").toLowerCase().contains("android");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isAlpine() {
/*     */     try {
/* 125 */       Process p = Runtime.getRuntime().exec(new String[] { "cat", "/etc/os-release", "|", "grep", "^ID" });
/* 126 */       p.waitFor();
/*     */       
/* 128 */       InputStream in = p.getInputStream(); 
/* 129 */       try { boolean bool = readFully(in).toLowerCase().contains("alpine");
/* 130 */         if (in != null) in.close();  return bool; } catch (Throwable throwable) { if (in != null)
/*     */           try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/* 132 */     } catch (Throwable e) {
/* 133 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   static String getHardwareName() {
/*     */     
/* 139 */     try { Process p = Runtime.getRuntime().exec(new String[] { "uname", "-m" });
/* 140 */       p.waitFor();
/*     */       
/* 142 */       InputStream in = p.getInputStream(); 
/* 143 */       try { String str = readFully(in);
/* 144 */         if (in != null) in.close();  return str; } catch (Throwable throwable) { if (in != null)
/* 145 */           try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable e)
/* 146 */     { log(Level.WARNING, "Error while running uname -m", e);
/* 147 */       return "unknown"; }
/*     */   
/*     */   }
/*     */   
/*     */   private static String readFully(InputStream in) throws IOException {
/* 152 */     int readLen = 0;
/* 153 */     ByteArrayOutputStream b = new ByteArrayOutputStream();
/* 154 */     byte[] buf = new byte[32];
/* 155 */     while ((readLen = in.read(buf, 0, buf.length)) >= 0) {
/* 156 */       b.write(buf, 0, readLen);
/*     */     }
/* 158 */     return b.toString();
/*     */   }
/*     */   
/*     */   static String resolveArmArchType() {
/* 162 */     if (System.getProperty("os.name").contains("Linux")) {
/* 163 */       String armType = getHardwareName();
/*     */       
/* 165 */       if (armType.startsWith("armv6"))
/*     */       {
/* 167 */         return "armv6"; } 
/* 168 */       if (armType.startsWith("armv7"))
/*     */       {
/* 170 */         return "armv7"; } 
/* 171 */       if (armType.startsWith("armv5"))
/*     */       {
/* 173 */         return "arm"; } 
/* 174 */       if (armType.equals("aarch64"))
/*     */       {
/* 176 */         return "arm64";
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 181 */       String abi = System.getProperty("sun.arch.abi");
/* 182 */       if (abi != null && abi.startsWith("gnueabihf")) {
/* 183 */         return "armv7";
/*     */       }
/*     */     } 
/*     */     
/* 187 */     return "arm";
/*     */   }
/*     */   
/*     */   public static String getArchName() {
/* 191 */     String osArch = System.getProperty("os.arch");
/*     */     
/* 193 */     if (isAndroid()) {
/* 194 */       return "android-arm";
/*     */     }
/*     */     
/* 197 */     if (osArch.startsWith("arm")) {
/* 198 */       osArch = resolveArmArchType();
/*     */     } else {
/* 200 */       String lc = osArch.toLowerCase(Locale.US);
/* 201 */       if (archMapping.containsKey(lc)) return archMapping.get(lc); 
/*     */     } 
/* 203 */     return translateArchNameToFolderName(osArch);
/*     */   }
/*     */   
/*     */   static String translateOSNameToFolderName(String osName) {
/* 207 */     if (osName.contains("Windows"))
/* 208 */       return "Windows"; 
/* 209 */     if (osName.contains("Mac") || osName.contains("Darwin")) {
/* 210 */       return "Mac";
/*     */     }
/*     */     
/* 213 */     if (osName.contains("Linux"))
/* 214 */       return "Linux"; 
/* 215 */     if (osName.contains("AIX")) {
/* 216 */       return "AIX";
/*     */     }
/* 218 */     return osName.replaceAll("\\W", "");
/*     */   }
/*     */ 
/*     */   
/*     */   static String translateArchNameToFolderName(String archName) {
/* 223 */     return archName.replaceAll("\\W", "");
/*     */   }
/*     */   
/*     */   private static void log(Level level, String message, Throwable t) {
/* 227 */     if (logger.isLoggable(level))
/* 228 */       if (logger.isLoggable(Level.FINE)) {
/* 229 */         logger.log(level, message, t);
/*     */       } else {
/* 231 */         logger.log(level, message + " (caused by: " + t + ", enable debug logging for stacktrace)");
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\nativ\OSInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */