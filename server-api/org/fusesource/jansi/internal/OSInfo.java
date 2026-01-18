/*     */ package org.fusesource.jansi.internal;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  56 */   private static final HashMap<String, String> archMapping = new HashMap<>();
/*     */ 
/*     */   
/*     */   static {
/*  60 */     archMapping.put("x86", "x86");
/*  61 */     archMapping.put("i386", "x86");
/*  62 */     archMapping.put("i486", "x86");
/*  63 */     archMapping.put("i586", "x86");
/*  64 */     archMapping.put("i686", "x86");
/*  65 */     archMapping.put("pentium", "x86");
/*     */ 
/*     */     
/*  68 */     archMapping.put("x86_64", "x86_64");
/*  69 */     archMapping.put("amd64", "x86_64");
/*  70 */     archMapping.put("em64t", "x86_64");
/*  71 */     archMapping.put("universal", "x86_64");
/*     */ 
/*     */     
/*  74 */     archMapping.put("ia64", "ia64");
/*  75 */     archMapping.put("ia64w", "ia64");
/*     */ 
/*     */     
/*  78 */     archMapping.put("ia64_32", "ia64_32");
/*  79 */     archMapping.put("ia64n", "ia64_32");
/*     */ 
/*     */     
/*  82 */     archMapping.put("ppc", "ppc");
/*  83 */     archMapping.put("power", "ppc");
/*  84 */     archMapping.put("powerpc", "ppc");
/*  85 */     archMapping.put("power_pc", "ppc");
/*  86 */     archMapping.put("power_rs", "ppc");
/*     */ 
/*     */     
/*  89 */     archMapping.put("ppc64", "ppc64");
/*  90 */     archMapping.put("power64", "ppc64");
/*  91 */     archMapping.put("powerpc64", "ppc64");
/*  92 */     archMapping.put("power_pc64", "ppc64");
/*  93 */     archMapping.put("power_rs64", "ppc64");
/*     */ 
/*     */     
/*  96 */     archMapping.put("aarch64", "arm64");
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 100 */     if (args.length >= 1) {
/* 101 */       if ("--os".equals(args[0])) {
/* 102 */         System.out.print(getOSName()); return;
/*     */       } 
/* 104 */       if ("--arch".equals(args[0])) {
/* 105 */         System.out.print(getArchName());
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 110 */     System.out.print(getNativeLibFolderPathForCurrentOS());
/*     */   }
/*     */   
/*     */   public static String getNativeLibFolderPathForCurrentOS() {
/* 114 */     return getOSName() + "/" + getArchName();
/*     */   }
/*     */   
/*     */   public static String getOSName() {
/* 118 */     return translateOSNameToFolderName(System.getProperty("os.name"));
/*     */   }
/*     */   
/*     */   public static boolean isAndroid() {
/* 122 */     return System.getProperty("java.runtime.name", "").toLowerCase().contains("android");
/*     */   }
/*     */   
/*     */   public static boolean isAlpine() {
/*     */     try {
/* 127 */       for (String line : Files.readAllLines(Paths.get("/etc/os-release", new String[0]))) {
/* 128 */         if (line.startsWith("ID") && line.toLowerCase(Locale.ROOT).contains("alpine")) {
/* 129 */           return true;
/*     */         }
/*     */       } 
/* 132 */     } catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 135 */     return false;
/*     */   }
/*     */   
/*     */   static String getHardwareName() {
/*     */     try {
/* 140 */       Process p = Runtime.getRuntime().exec("uname -m");
/* 141 */       p.waitFor();
/*     */       
/* 143 */       InputStream in = p.getInputStream();
/*     */       try {
/* 145 */         return readFully(in);
/*     */       } finally {
/* 147 */         in.close();
/*     */       } 
/* 149 */     } catch (Throwable e) {
/* 150 */       System.err.println("Error while running uname -m: " + e.getMessage());
/* 151 */       return "unknown";
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String readFully(InputStream in) throws IOException {
/* 156 */     int readLen = 0;
/* 157 */     ByteArrayOutputStream b = new ByteArrayOutputStream();
/* 158 */     byte[] buf = new byte[32];
/* 159 */     while ((readLen = in.read(buf, 0, buf.length)) >= 0) {
/* 160 */       b.write(buf, 0, readLen);
/*     */     }
/* 162 */     return b.toString();
/*     */   }
/*     */   
/*     */   static String resolveArmArchType() {
/* 166 */     if (System.getProperty("os.name").contains("Linux")) {
/* 167 */       String armType = getHardwareName();
/*     */       
/* 169 */       if (armType.startsWith("armv6"))
/*     */       {
/* 171 */         return "armv6"; } 
/* 172 */       if (armType.startsWith("armv7"))
/*     */       {
/* 174 */         return "armv7"; } 
/* 175 */       if (armType.startsWith("armv5"))
/*     */       {
/* 177 */         return "arm"; } 
/* 178 */       if (armType.equals("aarch64"))
/*     */       {
/* 180 */         return "arm64";
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 185 */       String abi = System.getProperty("sun.arch.abi");
/* 186 */       if (abi != null && abi.startsWith("gnueabihf")) {
/* 187 */         return "armv7";
/*     */       }
/*     */     } 
/*     */     
/* 191 */     return "arm";
/*     */   }
/*     */   
/*     */   public static String getArchName() {
/* 195 */     String osArch = System.getProperty("os.arch");
/*     */     
/* 197 */     if (isAndroid()) {
/* 198 */       return "android-arm";
/*     */     }
/*     */     
/* 201 */     if (osArch.startsWith("arm")) {
/* 202 */       osArch = resolveArmArchType();
/*     */     } else {
/* 204 */       String lc = osArch.toLowerCase(Locale.US);
/* 205 */       if (archMapping.containsKey(lc)) return archMapping.get(lc); 
/*     */     } 
/* 207 */     return translateArchNameToFolderName(osArch);
/*     */   }
/*     */   
/*     */   static String translateOSNameToFolderName(String osName) {
/* 211 */     if (osName.contains("Windows"))
/* 212 */       return "Windows"; 
/* 213 */     if (osName.contains("Mac") || osName.contains("Darwin")) {
/* 214 */       return "Mac";
/*     */     }
/*     */     
/* 217 */     if (osName.contains("Linux"))
/* 218 */       return "Linux"; 
/* 219 */     if (osName.contains("AIX")) {
/* 220 */       return "AIX";
/*     */     }
/* 222 */     return osName.replaceAll("\\W", "");
/*     */   }
/*     */ 
/*     */   
/*     */   static String translateArchNameToFolderName(String archName) {
/* 227 */     return archName.replaceAll("\\W", "");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\internal\OSInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */