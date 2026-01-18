/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.NetUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
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
/*     */ public final class MacAddressUtil
/*     */ {
/*  36 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(MacAddressUtil.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int EUI64_MAC_ADDRESS_LENGTH = 8;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int EUI48_MAC_ADDRESS_LENGTH = 6;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] bestAvailableMac() {
/*  50 */     byte[] bestMacAddr = EmptyArrays.EMPTY_BYTES;
/*  51 */     InetAddress bestInetAddr = NetUtil.LOCALHOST4;
/*     */ 
/*     */     
/*  54 */     Map<NetworkInterface, InetAddress> ifaces = new LinkedHashMap<>();
/*  55 */     for (NetworkInterface iface : NetUtil.NETWORK_INTERFACES) {
/*     */       
/*  57 */       Enumeration<InetAddress> addrs = SocketUtils.addressesFromNetworkInterface(iface);
/*  58 */       if (addrs.hasMoreElements()) {
/*  59 */         InetAddress a = addrs.nextElement();
/*  60 */         if (!a.isLoopbackAddress()) {
/*  61 */           ifaces.put(iface, a);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     for (Map.Entry<NetworkInterface, InetAddress> entry : ifaces.entrySet()) {
/*  67 */       byte[] macAddr; NetworkInterface iface = entry.getKey();
/*  68 */       InetAddress inetAddr = entry.getValue();
/*  69 */       if (iface.isVirtual()) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/*  75 */         macAddr = SocketUtils.hardwareAddressFromNetworkInterface(iface);
/*  76 */       } catch (SocketException e) {
/*  77 */         logger.debug("Failed to get the hardware address of a network interface: {}", iface, e);
/*     */         
/*     */         continue;
/*     */       } 
/*  81 */       boolean replace = false;
/*  82 */       int res = compareAddresses(bestMacAddr, macAddr);
/*  83 */       if (res < 0) {
/*     */         
/*  85 */         replace = true;
/*  86 */       } else if (res == 0) {
/*     */         
/*  88 */         res = compareAddresses(bestInetAddr, inetAddr);
/*  89 */         if (res < 0) {
/*     */           
/*  91 */           replace = true;
/*  92 */         } else if (res == 0) {
/*     */           
/*  94 */           if (bestMacAddr.length < macAddr.length) {
/*  95 */             replace = true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 100 */       if (replace) {
/* 101 */         bestMacAddr = macAddr;
/* 102 */         bestInetAddr = inetAddr;
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     if (bestMacAddr == EmptyArrays.EMPTY_BYTES) {
/* 107 */       return null;
/*     */     }
/*     */     
/* 110 */     if (bestMacAddr.length == 6) {
/* 111 */       byte[] newAddr = new byte[8];
/* 112 */       System.arraycopy(bestMacAddr, 0, newAddr, 0, 3);
/* 113 */       newAddr[3] = -1;
/* 114 */       newAddr[4] = -2;
/* 115 */       System.arraycopy(bestMacAddr, 3, newAddr, 5, 3);
/* 116 */       bestMacAddr = newAddr;
/*     */     } else {
/*     */       
/* 119 */       bestMacAddr = Arrays.copyOf(bestMacAddr, 8);
/*     */     } 
/*     */     
/* 122 */     return bestMacAddr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] defaultMachineId() {
/* 130 */     byte[] bestMacAddr = bestAvailableMac();
/* 131 */     if (bestMacAddr == null) {
/* 132 */       bestMacAddr = new byte[8];
/* 133 */       ThreadLocalRandom.current().nextBytes(bestMacAddr);
/* 134 */       logger.warn("Failed to find a usable hardware address from the network interfaces; using random bytes: {}", 
/*     */           
/* 136 */           formatAddress(bestMacAddr));
/*     */     } 
/* 138 */     return bestMacAddr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] parseMAC(String value) {
/*     */     byte[] machineId;
/*     */     char separator;
/* 149 */     switch (value.length()) {
/*     */       case 17:
/* 151 */         separator = value.charAt(2);
/* 152 */         validateMacSeparator(separator);
/* 153 */         machineId = new byte[6];
/*     */         break;
/*     */       case 23:
/* 156 */         separator = value.charAt(2);
/* 157 */         validateMacSeparator(separator);
/* 158 */         machineId = new byte[8];
/*     */         break;
/*     */       default:
/* 161 */         throw new IllegalArgumentException("value is not supported [MAC-48, EUI-48, EUI-64]");
/*     */     } 
/*     */     
/* 164 */     int end = machineId.length - 1;
/* 165 */     int j = 0;
/* 166 */     for (int i = 0; i < end; i++, j += 3) {
/* 167 */       int sIndex = j + 2;
/* 168 */       machineId[i] = StringUtil.decodeHexByte(value, j);
/* 169 */       if (value.charAt(sIndex) != separator) {
/* 170 */         throw new IllegalArgumentException("expected separator '" + separator + " but got '" + value
/* 171 */             .charAt(sIndex) + "' at index: " + sIndex);
/*     */       }
/*     */     } 
/*     */     
/* 175 */     machineId[end] = StringUtil.decodeHexByte(value, j);
/*     */     
/* 177 */     return machineId;
/*     */   }
/*     */   
/*     */   private static void validateMacSeparator(char separator) {
/* 181 */     if (separator != ':' && separator != '-') {
/* 182 */       throw new IllegalArgumentException("unsupported separator: " + separator + " (expected: [:-])");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatAddress(byte[] addr) {
/* 191 */     StringBuilder buf = new StringBuilder(24);
/* 192 */     for (byte b : addr) {
/* 193 */       buf.append(String.format("%02x:", new Object[] { Integer.valueOf(b & 0xFF) }));
/*     */     } 
/* 195 */     return buf.substring(0, buf.length() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int compareAddresses(byte[] current, byte[] candidate) {
/* 203 */     if (candidate == null || candidate.length < 6) {
/* 204 */       return 1;
/*     */     }
/*     */ 
/*     */     
/* 208 */     boolean onlyZeroAndOne = true;
/* 209 */     for (byte b : candidate) {
/* 210 */       if (b != 0 && b != 1) {
/* 211 */         onlyZeroAndOne = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 216 */     if (onlyZeroAndOne) {
/* 217 */       return 1;
/*     */     }
/*     */ 
/*     */     
/* 221 */     if ((candidate[0] & 0x1) != 0) {
/* 222 */       return 1;
/*     */     }
/*     */ 
/*     */     
/* 226 */     if ((candidate[0] & 0x2) == 0) {
/* 227 */       if (current.length != 0 && (current[0] & 0x2) == 0)
/*     */       {
/* 229 */         return 0;
/*     */       }
/*     */       
/* 232 */       return -1;
/*     */     } 
/*     */     
/* 235 */     if (current.length != 0 && (current[0] & 0x2) == 0)
/*     */     {
/* 237 */       return 1;
/*     */     }
/*     */     
/* 240 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int compareAddresses(InetAddress current, InetAddress candidate) {
/* 249 */     return scoreAddress(current) - scoreAddress(candidate);
/*     */   }
/*     */   
/*     */   private static int scoreAddress(InetAddress addr) {
/* 253 */     if (addr.isAnyLocalAddress() || addr.isLoopbackAddress()) {
/* 254 */       return 0;
/*     */     }
/* 256 */     if (addr.isMulticastAddress()) {
/* 257 */       return 1;
/*     */     }
/* 259 */     if (addr.isLinkLocalAddress()) {
/* 260 */       return 2;
/*     */     }
/* 262 */     if (addr.isSiteLocalAddress()) {
/* 263 */       return 3;
/*     */     }
/*     */     
/* 266 */     return 4;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\MacAddressUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */