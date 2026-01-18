/*     */ package com.hypixel.hytale.common.util;
/*     */ 
/*     */ import com.hypixel.hytale.function.supplier.SupplierUtil;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class HardwareUtil {
/*  17 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private static final int PROCESS_TIMEOUT_SECONDS = 2;
/*  20 */   private static final Pattern UUID_PATTERN = Pattern.compile("([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})"); private static final Supplier<UUID> WINDOWS; private static final Supplier<UUID> MAC;
/*     */   private static final Supplier<UUID> LINUX;
/*     */   
/*     */   static {
/*  24 */     WINDOWS = (Supplier<UUID>)SupplierUtil.cache(() -> {
/*     */           String output = runCommand(new String[] { "reg", "query", "HKLM\\SOFTWARE\\Microsoft\\Cryptography", "/v", "MachineGuid" });
/*     */           
/*     */           if (output != null) {
/*     */             for (String line : output.split("\r?\n")) {
/*     */               if (line.contains("MachineGuid")) {
/*     */                 Matcher matcher = UUID_PATTERN.matcher(line);
/*     */                 
/*     */                 if (matcher.find()) {
/*     */                   return UUID.fromString(matcher.group(1));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           }
/*     */           
/*     */           output = runCommand(new String[] { "powershell", "-NoProfile", "-Command", "(Get-CimInstance -Class Win32_ComputerSystemProduct).UUID" });
/*     */           if (output != null) {
/*     */             UUID uuid = parseUuidFromOutput(output);
/*     */             if (uuid != null) {
/*     */               return uuid;
/*     */             }
/*     */           } 
/*     */           output = runCommand(new String[] { "wmic", "csproduct", "get", "UUID" });
/*     */           if (output != null) {
/*     */             UUID uuid = parseUuidFromOutput(output);
/*     */             if (uuid != null) {
/*     */               return uuid;
/*     */             }
/*     */           } 
/*     */           throw new RuntimeException("Failed to get hardware UUID for Windows - registry, PowerShell, and wmic all failed");
/*     */         });
/*  55 */     MAC = (Supplier<UUID>)SupplierUtil.cache(() -> {
/*     */           String output = runCommand(new String[] { "/usr/sbin/ioreg", "-rd1", "-c", "IOPlatformExpertDevice" });
/*     */           
/*     */           if (output != null) {
/*     */             for (String line : output.split("\r?\n")) {
/*     */               if (line.contains("IOPlatformUUID")) {
/*     */                 Matcher matcher = UUID_PATTERN.matcher(line);
/*     */                 
/*     */                 if (matcher.find()) {
/*     */                   return UUID.fromString(matcher.group(1));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           }
/*     */           
/*     */           output = runCommand(new String[] { "/usr/sbin/system_profiler", "SPHardwareDataType" });
/*     */           
/*     */           if (output != null) {
/*     */             for (String line : output.split("\r?\n")) {
/*     */               if (line.contains("Hardware UUID")) {
/*     */                 Matcher matcher = UUID_PATTERN.matcher(line);
/*     */                 
/*     */                 if (matcher.find()) {
/*     */                   return UUID.fromString(matcher.group(1));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           }
/*     */           throw new RuntimeException("Failed to get hardware UUID for macOS - ioreg and system_profiler both failed");
/*     */         });
/*  85 */     LINUX = (Supplier<UUID>)SupplierUtil.cache(() -> {
/*     */           UUID machineId = readMachineIdFile(Path.of("/etc/machine-id", new String[0]));
/*     */           
/*     */           if (machineId != null) {
/*     */             return machineId;
/*     */           }
/*     */           machineId = readMachineIdFile(Path.of("/var/lib/dbus/machine-id", new String[0]));
/*     */           if (machineId != null) {
/*     */             return machineId;
/*     */           }
/*     */           try {
/*     */             Path path = Path.of("/sys/class/dmi/id/product_uuid", new String[0]);
/*     */             if (Files.isReadable(path)) {
/*     */               String content = Files.readString(path, StandardCharsets.UTF_8).trim();
/*     */               if (!content.isEmpty()) {
/*     */                 return UUID.fromString(content);
/*     */               }
/*     */             } 
/* 103 */           } catch (Exception exception) {}
/*     */           String output = runCommand(new String[] { "dmidecode", "-s", "system-uuid" });
/*     */           if (output != null) {
/*     */             UUID uuid = parseUuidFromOutput(output);
/*     */             if (uuid != null) {
/*     */               return uuid;
/*     */             }
/*     */           } 
/*     */           throw new RuntimeException("Failed to get hardware UUID for Linux - all methods failed");
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static String runCommand(String... command) {
/*     */     try {
/* 120 */       Process process = (new ProcessBuilder(command)).start();
/* 121 */       if (process.waitFor(2L, TimeUnit.SECONDS)) {
/* 122 */         return (new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8)).trim();
/*     */       }
/* 124 */       process.destroyForcibly();
/* 125 */     } catch (Exception exception) {}
/*     */     
/* 127 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static UUID parseUuidFromOutput(String output) {
/* 132 */     Matcher matcher = UUID_PATTERN.matcher(output);
/* 133 */     if (matcher.find()) {
/* 134 */       return UUID.fromString(matcher.group(1));
/*     */     }
/* 136 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static UUID readMachineIdFile(Path path) {
/*     */     try {
/* 142 */       if (!Files.isReadable(path)) return null; 
/* 143 */       String content = Files.readString(path, StandardCharsets.UTF_8).trim();
/* 144 */       if (content.isEmpty() || content.length() != 32) return null;
/*     */       
/* 146 */       return UUID.fromString(content
/* 147 */           .substring(0, 8) + "-" + content.substring(0, 8) + "-" + content
/* 148 */           .substring(8, 12) + "-" + content
/* 149 */           .substring(12, 16) + "-" + content
/* 150 */           .substring(16, 20));
/*     */     
/*     */     }
/* 153 */     catch (Exception e) {
/* 154 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static UUID getUUID() {
/*     */     try {
/* 161 */       switch (SystemUtil.TYPE) { default: throw new MatchException(null, null);
/*     */         case WINDOWS: 
/*     */         case LINUX: 
/*     */         case MACOS: 
/* 165 */         case OTHER: break; }  throw new RuntimeException("Unknown OS!");
/*     */     }
/* 167 */     catch (Exception e) {
/* 168 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to get Hardware UUID");
/* 169 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\HardwareUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */