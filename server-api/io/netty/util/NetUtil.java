/*      */ package io.netty.util;
/*      */ 
/*      */ import io.netty.util.internal.BoundedInputStream;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import io.netty.util.internal.SystemPropertyUtil;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.net.Inet4Address;
/*      */ import java.net.Inet6Address;
/*      */ import java.net.InetAddress;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.NetworkInterface;
/*      */ import java.net.UnknownHostException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class NetUtil
/*      */ {
/*      */   public static final Inet4Address LOCALHOST4;
/*      */   public static final Inet6Address LOCALHOST6;
/*      */   public static final InetAddress LOCALHOST;
/*      */   public static final NetworkInterface LOOPBACK_IF;
/*      */   public static final Collection<NetworkInterface> NETWORK_INTERFACES;
/*      */   public static final int SOMAXCONN;
/*      */   private static final int IPV6_WORD_COUNT = 8;
/*      */   private static final int IPV6_MAX_CHAR_COUNT = 39;
/*      */   private static final int IPV6_BYTE_COUNT = 16;
/*      */   private static final int IPV6_MAX_CHAR_BETWEEN_SEPARATOR = 4;
/*      */   private static final int IPV6_MIN_SEPARATORS = 2;
/*      */   private static final int IPV6_MAX_SEPARATORS = 8;
/*      */   private static final int IPV4_MAX_CHAR_BETWEEN_SEPARATOR = 3;
/*      */   private static final int IPV4_SEPARATORS = 3;
/*  129 */   private static final boolean IPV4_PREFERRED = SystemPropertyUtil.getBoolean("java.net.preferIPv4Stack", false);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean IPV6_ADDRESSES_PREFERRED;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  139 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NetUtil.class);
/*      */   
/*      */   static {
/*  142 */     String prefer = SystemPropertyUtil.get("java.net.preferIPv6Addresses", "false");
/*  143 */     if ("true".equalsIgnoreCase(prefer.trim())) {
/*  144 */       IPV6_ADDRESSES_PREFERRED = true;
/*      */     } else {
/*      */       
/*  147 */       IPV6_ADDRESSES_PREFERRED = false;
/*      */     } 
/*  149 */     logger.debug("-Djava.net.preferIPv4Stack: {}", Boolean.valueOf(IPV4_PREFERRED));
/*  150 */     logger.debug("-Djava.net.preferIPv6Addresses: {}", prefer);
/*      */     
/*  152 */     NETWORK_INTERFACES = NetUtilInitializations.networkInterfaces();
/*      */ 
/*      */     
/*  155 */     LOCALHOST4 = NetUtilInitializations.createLocalhost4();
/*      */ 
/*      */     
/*  158 */     LOCALHOST6 = NetUtilInitializations.createLocalhost6();
/*      */ 
/*      */     
/*  161 */     NetUtilInitializations.NetworkIfaceAndInetAddress loopback = NetUtilInitializations.determineLoopback(NETWORK_INTERFACES, LOCALHOST4, LOCALHOST6);
/*  162 */     LOOPBACK_IF = loopback.iface();
/*  163 */     LOCALHOST = loopback.address();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  168 */     SOMAXCONN = ((Integer)AccessController.<Integer>doPrivileged(new SoMaxConnAction())).intValue();
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class SoMaxConnAction
/*      */     implements PrivilegedAction<Integer>
/*      */   {
/*      */     private SoMaxConnAction() {}
/*      */ 
/*      */     
/*      */     public Integer run() {
/*      */       int i;
/*  180 */       if (PlatformDependent.isWindows()) {
/*  181 */         i = 200;
/*  182 */       } else if (PlatformDependent.isOsx()) {
/*  183 */         i = 128;
/*      */       } else {
/*  185 */         i = 4096;
/*      */       } 
/*  187 */       File file = new File("/proc/sys/net/core/somaxconn");
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  192 */         if (file.exists())
/*  193 */         { BufferedReader in = new BufferedReader(new InputStreamReader((InputStream)new BoundedInputStream(new FileInputStream(file))));
/*      */           
/*  195 */           try { i = Integer.parseInt(in.readLine());
/*  196 */             if (NetUtil.logger.isDebugEnabled()) {
/*  197 */               NetUtil.logger.debug("{}: {}", file, Integer.valueOf(i));
/*      */             }
/*  199 */             in.close(); } catch (Throwable throwable) { try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */              throw throwable; }
/*      */            }
/*  202 */         else { Integer tmp = null;
/*  203 */           if (SystemPropertyUtil.getBoolean("io.netty.net.somaxconn.trySysctl", false)) {
/*  204 */             tmp = NetUtil.sysctlGetInt("kern.ipc.somaxconn");
/*  205 */             if (tmp == null) {
/*  206 */               tmp = NetUtil.sysctlGetInt("kern.ipc.soacceptqueue");
/*  207 */               if (tmp != null) {
/*  208 */                 i = tmp.intValue();
/*      */               }
/*      */             } else {
/*  211 */               i = tmp.intValue();
/*      */             } 
/*      */           } 
/*      */           
/*  215 */           if (tmp == null) {
/*  216 */             NetUtil.logger.debug("Failed to get SOMAXCONN from sysctl and file {}. Default: {}", file, 
/*  217 */                 Integer.valueOf(i));
/*      */           } }
/*      */       
/*  220 */       } catch (Exception e) {
/*  221 */         if (NetUtil.logger.isDebugEnabled()) {
/*  222 */           NetUtil.logger.debug("Failed to get SOMAXCONN from sysctl and file {}. Default: {}", new Object[] { file, 
/*  223 */                 Integer.valueOf(i), e });
/*      */         }
/*      */       } 
/*  226 */       return Integer.valueOf(i);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Integer sysctlGetInt(String sysctlKey) throws IOException {
/*  236 */     Process process = (new ProcessBuilder(new String[] { "sysctl", sysctlKey })).start();
/*      */     
/*      */     try {
/*  239 */       InputStream is = process.getInputStream();
/*  240 */       InputStreamReader isr = new InputStreamReader((InputStream)new BoundedInputStream(is));
/*  241 */       BufferedReader br = new BufferedReader(isr);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  256 */       process.destroy();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isIpV4StackPreferred() {
/*  268 */     return IPV4_PREFERRED;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isIpV6AddressesPreferred() {
/*  279 */     return IPV6_ADDRESSES_PREFERRED;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] createByteArrayFromIpAddressString(String ipAddressString) {
/*  287 */     if (isValidIpV4Address(ipAddressString)) {
/*  288 */       return validIpV4ToBytes(ipAddressString);
/*      */     }
/*      */     
/*  291 */     if (isValidIpV6Address(ipAddressString)) {
/*  292 */       if (ipAddressString.charAt(0) == '[') {
/*  293 */         ipAddressString = ipAddressString.substring(1, ipAddressString.length() - 1);
/*      */       }
/*      */       
/*  296 */       int percentPos = ipAddressString.indexOf('%');
/*  297 */       if (percentPos >= 0) {
/*  298 */         ipAddressString = ipAddressString.substring(0, percentPos);
/*      */       }
/*      */       
/*  301 */       return getIPv6ByName(ipAddressString, true);
/*      */     } 
/*  303 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static InetAddress createInetAddressFromIpAddressString(String ipAddressString) {
/*  311 */     if (isValidIpV4Address(ipAddressString)) {
/*  312 */       byte[] bytes = validIpV4ToBytes(ipAddressString);
/*      */       try {
/*  314 */         return InetAddress.getByAddress(bytes);
/*  315 */       } catch (UnknownHostException e) {
/*      */         
/*  317 */         throw new IllegalStateException(e);
/*      */       } 
/*      */     } 
/*      */     
/*  321 */     if (isValidIpV6Address(ipAddressString)) {
/*  322 */       if (ipAddressString.charAt(0) == '[') {
/*  323 */         ipAddressString = ipAddressString.substring(1, ipAddressString.length() - 1);
/*      */       }
/*      */       
/*  326 */       int percentPos = ipAddressString.indexOf('%');
/*  327 */       if (percentPos >= 0) {
/*      */         try {
/*  329 */           int scopeId = Integer.parseInt(ipAddressString.substring(percentPos + 1));
/*  330 */           ipAddressString = ipAddressString.substring(0, percentPos);
/*  331 */           byte[] arrayOfByte = getIPv6ByName(ipAddressString, true);
/*  332 */           if (arrayOfByte == null) {
/*  333 */             return null;
/*      */           }
/*      */           try {
/*  336 */             return Inet6Address.getByAddress((String)null, arrayOfByte, scopeId);
/*  337 */           } catch (UnknownHostException e) {
/*      */             
/*  339 */             throw new IllegalStateException(e);
/*      */           } 
/*  341 */         } catch (NumberFormatException e) {
/*  342 */           return null;
/*      */         } 
/*      */       }
/*  345 */       byte[] bytes = getIPv6ByName(ipAddressString, true);
/*  346 */       if (bytes == null) {
/*  347 */         return null;
/*      */       }
/*      */       try {
/*  350 */         return InetAddress.getByAddress(bytes);
/*  351 */       } catch (UnknownHostException e) {
/*      */         
/*  353 */         throw new IllegalStateException(e);
/*      */       } 
/*      */     } 
/*  356 */     return null;
/*      */   }
/*      */   
/*      */   private static int decimalDigit(String str, int pos) {
/*  360 */     return str.charAt(pos) - 48;
/*      */   }
/*      */   
/*      */   private static byte ipv4WordToByte(String ip, int from, int toExclusive) {
/*  364 */     int ret = decimalDigit(ip, from);
/*  365 */     from++;
/*  366 */     if (from == toExclusive) {
/*  367 */       return (byte)ret;
/*      */     }
/*  369 */     ret = ret * 10 + decimalDigit(ip, from);
/*  370 */     from++;
/*  371 */     if (from == toExclusive) {
/*  372 */       return (byte)ret;
/*      */     }
/*  374 */     return (byte)(ret * 10 + decimalDigit(ip, from));
/*      */   }
/*      */ 
/*      */   
/*      */   static byte[] validIpV4ToBytes(String ip) {
/*      */     int i;
/*  380 */     return new byte[] {
/*  381 */         ipv4WordToByte(ip, 0, i = ip.indexOf('.', 1)), 
/*  382 */         ipv4WordToByte(ip, i + 1, i = ip.indexOf('.', i + 2)), 
/*  383 */         ipv4WordToByte(ip, i + 1, i = ip.indexOf('.', i + 2)), 
/*  384 */         ipv4WordToByte(ip, i + 1, ip.length())
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int ipv4AddressToInt(Inet4Address ipAddress) {
/*  392 */     byte[] octets = ipAddress.getAddress();
/*      */     
/*  394 */     return (octets[0] & 0xFF) << 24 | (octets[1] & 0xFF) << 16 | (octets[2] & 0xFF) << 8 | octets[3] & 0xFF;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String intToIpAddress(int i) {
/*  404 */     StringBuilder buf = new StringBuilder(15);
/*  405 */     buf.append(i >> 24 & 0xFF);
/*  406 */     buf.append('.');
/*  407 */     buf.append(i >> 16 & 0xFF);
/*  408 */     buf.append('.');
/*  409 */     buf.append(i >> 8 & 0xFF);
/*  410 */     buf.append('.');
/*  411 */     buf.append(i & 0xFF);
/*  412 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String bytesToIpAddress(byte[] bytes) {
/*  422 */     return bytesToIpAddress(bytes, 0, bytes.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String bytesToIpAddress(byte[] bytes, int offset, int length) {
/*  432 */     switch (length) {
/*      */       case 4:
/*  434 */         return (new StringBuilder(15))
/*  435 */           .append(bytes[offset] & 0xFF)
/*  436 */           .append('.')
/*  437 */           .append(bytes[offset + 1] & 0xFF)
/*  438 */           .append('.')
/*  439 */           .append(bytes[offset + 2] & 0xFF)
/*  440 */           .append('.')
/*  441 */           .append(bytes[offset + 3] & 0xFF).toString();
/*      */       
/*      */       case 16:
/*  444 */         return toAddressString(bytes, offset, false);
/*      */     } 
/*  446 */     throw new IllegalArgumentException("length: " + length + " (expected: 4 or 16)");
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isValidIpV6Address(String ip) {
/*  451 */     return isValidIpV6Address(ip);
/*      */   }
/*      */   
/*      */   public static boolean isValidIpV6Address(CharSequence ip) {
/*  455 */     int start, colons, compressBegin, end = ip.length();
/*  456 */     if (end < 2) {
/*  457 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  462 */     char c = ip.charAt(0);
/*  463 */     if (c == '[') {
/*  464 */       end--;
/*  465 */       if (ip.charAt(end) != ']')
/*      */       {
/*  467 */         return false;
/*      */       }
/*  469 */       start = 1;
/*  470 */       c = ip.charAt(1);
/*      */     } else {
/*  472 */       start = 0;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  477 */     if (c == ':') {
/*      */       
/*  479 */       if (ip.charAt(start + 1) != ':') {
/*  480 */         return false;
/*      */       }
/*  482 */       colons = 2;
/*  483 */       compressBegin = start;
/*  484 */       start += 2;
/*      */     } else {
/*  486 */       colons = 0;
/*  487 */       compressBegin = -1;
/*      */     } 
/*      */     
/*  490 */     int wordLen = 0;
/*      */     
/*  492 */     for (int i = start; i < end; i++) {
/*  493 */       c = ip.charAt(i);
/*  494 */       if (isValidHexChar(c)) {
/*  495 */         if (wordLen < 4) {
/*  496 */           wordLen++;
/*      */         } else {
/*      */           
/*  499 */           return false;
/*      */         } 
/*      */       } else {
/*  502 */         int ipv4Start; int j; int ipv4End; switch (c) {
/*      */           case ':':
/*  504 */             if (colons > 7) {
/*  505 */               return false;
/*      */             }
/*  507 */             if (ip.charAt(i - 1) == ':') {
/*  508 */               if (compressBegin >= 0) {
/*  509 */                 return false;
/*      */               }
/*  511 */               compressBegin = i - 1;
/*      */             } else {
/*  513 */               wordLen = 0;
/*      */             } 
/*  515 */             colons++;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case '.':
/*  521 */             if ((compressBegin < 0 && colons != 6) || (colons == 7 && compressBegin >= start) || colons > 7)
/*      */             {
/*      */ 
/*      */               
/*  525 */               return false;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  531 */             ipv4Start = i - wordLen;
/*  532 */             j = ipv4Start - 2;
/*  533 */             if (isValidIPv4MappedChar(ip.charAt(j))) {
/*  534 */               if (!isValidIPv4MappedChar(ip.charAt(j - 1)) || 
/*  535 */                 !isValidIPv4MappedChar(ip.charAt(j - 2)) || 
/*  536 */                 !isValidIPv4MappedChar(ip.charAt(j - 3))) {
/*  537 */                 return false;
/*      */               }
/*  539 */               j -= 5;
/*      */             } 
/*      */             
/*  542 */             for (; j >= start; j--) {
/*  543 */               char tmpChar = ip.charAt(j);
/*  544 */               if (tmpChar != '0' && tmpChar != ':') {
/*  545 */                 return false;
/*      */               }
/*      */             } 
/*      */ 
/*      */             
/*  550 */             ipv4End = AsciiString.indexOf(ip, '%', ipv4Start + 7);
/*  551 */             if (ipv4End < 0) {
/*  552 */               ipv4End = end;
/*      */             }
/*  554 */             return isValidIpV4Address(ip, ipv4Start, ipv4End);
/*      */           
/*      */           case '%':
/*  557 */             end = i;
/*      */             break;
/*      */           default:
/*  560 */             return false;
/*      */         } 
/*      */       
/*      */       } 
/*      */     } 
/*  565 */     if (compressBegin < 0) {
/*  566 */       return (colons == 7 && wordLen > 0);
/*      */     }
/*      */     
/*  569 */     return (compressBegin + 2 == end || (wordLen > 0 && (colons < 8 || compressBegin <= start)));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isValidIpV4Word(CharSequence word, int from, int toExclusive) {
/*  575 */     int len = toExclusive - from;
/*      */     char c0;
/*  577 */     if (len < 1 || len > 3 || (c0 = word.charAt(from)) < '0') {
/*  578 */       return false;
/*      */     }
/*  580 */     if (len == 3) {
/*  581 */       char c1; char c2; return ((c1 = word.charAt(from + 1)) >= '0' && (
/*  582 */         c2 = word.charAt(from + 2)) >= '0' && ((c0 <= '1' && c1 <= '9' && c2 <= '9') || (c0 == '2' && c1 <= '5' && (c2 <= '5' || (c1 < '5' && c2 <= '9')))));
/*      */     } 
/*      */ 
/*      */     
/*  586 */     return (c0 <= '9' && (len == 1 || isValidNumericChar(word.charAt(from + 1))));
/*      */   }
/*      */   
/*      */   private static boolean isValidHexChar(char c) {
/*  590 */     return ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f'));
/*      */   }
/*      */   
/*      */   private static boolean isValidNumericChar(char c) {
/*  594 */     return (c >= '0' && c <= '9');
/*      */   }
/*      */   
/*      */   private static boolean isValidIPv4MappedChar(char c) {
/*  598 */     return (c == 'f' || c == 'F');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isValidIPv4MappedSeparators(byte b0, byte b1, boolean mustBeZero) {
/*  605 */     return (b0 == b1 && (b0 == 0 || (!mustBeZero && b1 == -1)));
/*      */   }
/*      */   
/*      */   private static boolean isValidIPv4Mapped(byte[] bytes, int currentIndex, int compressBegin, int compressLength) {
/*  609 */     boolean mustBeZero = (compressBegin + compressLength >= 14);
/*  610 */     return (currentIndex <= 12 && currentIndex >= 2 && (!mustBeZero || compressBegin < 12) && 
/*  611 */       isValidIPv4MappedSeparators(bytes[currentIndex - 1], bytes[currentIndex - 2], mustBeZero) && 
/*  612 */       PlatformDependent.isZero(bytes, 0, currentIndex - 3));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isValidIpV4Address(CharSequence ip) {
/*  622 */     return isValidIpV4Address(ip, 0, ip.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isValidIpV4Address(String ip) {
/*  632 */     return isValidIpV4Address(ip, 0, ip.length());
/*      */   }
/*      */   
/*      */   private static boolean isValidIpV4Address(CharSequence ip, int from, int toExcluded) {
/*  636 */     return (ip instanceof String) ? isValidIpV4Address((String)ip, from, toExcluded) : (
/*  637 */       (ip instanceof AsciiString) ? isValidIpV4Address((AsciiString)ip, from, toExcluded) : 
/*  638 */       isValidIpV4Address0(ip, from, toExcluded));
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isValidIpV4Address(String ip, int from, int toExcluded) {
/*  643 */     int len = toExcluded - from;
/*      */     int i;
/*  645 */     return (len <= 15 && len >= 7 && (
/*  646 */       i = ip.indexOf('.', from + 1)) > 0 && isValidIpV4Word(ip, from, i) && (
/*  647 */       i = ip.indexOf('.', from = i + 2)) > 0 && isValidIpV4Word(ip, from - 1, i) && (
/*  648 */       i = ip.indexOf('.', from = i + 2)) > 0 && isValidIpV4Word(ip, from - 1, i) && 
/*  649 */       isValidIpV4Word(ip, i + 1, toExcluded));
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isValidIpV4Address(AsciiString ip, int from, int toExcluded) {
/*  654 */     int len = toExcluded - from;
/*      */     int i;
/*  656 */     return (len <= 15 && len >= 7 && (
/*  657 */       i = ip.indexOf('.', from + 1)) > 0 && isValidIpV4Word(ip, from, i) && (
/*  658 */       i = ip.indexOf('.', from = i + 2)) > 0 && isValidIpV4Word(ip, from - 1, i) && (
/*  659 */       i = ip.indexOf('.', from = i + 2)) > 0 && isValidIpV4Word(ip, from - 1, i) && 
/*  660 */       isValidIpV4Word(ip, i + 1, toExcluded));
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isValidIpV4Address0(CharSequence ip, int from, int toExcluded) {
/*  665 */     int len = toExcluded - from;
/*      */     int i;
/*  667 */     return (len <= 15 && len >= 7 && (
/*  668 */       i = AsciiString.indexOf(ip, '.', from + 1)) > 0 && isValidIpV4Word(ip, from, i) && (
/*  669 */       i = AsciiString.indexOf(ip, '.', from = i + 2)) > 0 && isValidIpV4Word(ip, from - 1, i) && (
/*  670 */       i = AsciiString.indexOf(ip, '.', from = i + 2)) > 0 && isValidIpV4Word(ip, from - 1, i) && 
/*  671 */       isValidIpV4Word(ip, i + 1, toExcluded));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Inet6Address getByName(CharSequence ip) {
/*  682 */     return getByName(ip, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Inet6Address getByName(CharSequence ip, boolean ipv4Mapped) {
/*  700 */     byte[] bytes = getIPv6ByName(ip, ipv4Mapped);
/*  701 */     if (bytes == null) {
/*  702 */       return null;
/*      */     }
/*      */     try {
/*  705 */       return Inet6Address.getByAddress((String)null, bytes, -1);
/*  706 */     } catch (UnknownHostException e) {
/*  707 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static byte[] getIPv6ByName(CharSequence ip, boolean ipv4Mapped) {
/*  727 */     byte[] bytes = new byte[16];
/*  728 */     int ipLength = ip.length();
/*  729 */     int compressBegin = 0;
/*  730 */     int compressLength = 0;
/*  731 */     int currentIndex = 0;
/*  732 */     int value = 0;
/*  733 */     int begin = -1;
/*  734 */     int i = 0;
/*  735 */     int ipv6Separators = 0;
/*  736 */     int ipv4Separators = 0;
/*      */     
/*  738 */     for (; i < ipLength; i++) {
/*  739 */       int tmp; char c = ip.charAt(i);
/*  740 */       switch (c) {
/*      */         case ':':
/*  742 */           ipv6Separators++;
/*  743 */           if (i - begin > 4 || ipv4Separators > 0 || ipv6Separators > 8 || currentIndex + 1 >= bytes.length)
/*      */           {
/*      */             
/*  746 */             return null;
/*      */           }
/*  748 */           value <<= 4 - i - begin << 2;
/*      */           
/*  750 */           if (compressLength > 0) {
/*  751 */             compressLength -= 2;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  757 */           bytes[currentIndex++] = (byte)((value & 0xF) << 4 | value >> 4 & 0xF);
/*  758 */           bytes[currentIndex++] = (byte)((value >> 8 & 0xF) << 4 | value >> 12 & 0xF);
/*  759 */           tmp = i + 1;
/*  760 */           if (tmp < ipLength && ip.charAt(tmp) == ':') {
/*  761 */             tmp++;
/*  762 */             if (compressBegin != 0 || (tmp < ipLength && ip.charAt(tmp) == ':')) {
/*  763 */               return null;
/*      */             }
/*  765 */             ipv6Separators++;
/*  766 */             compressBegin = currentIndex;
/*  767 */             compressLength = bytes.length - compressBegin - 2;
/*  768 */             i++;
/*      */           } 
/*  770 */           value = 0;
/*  771 */           begin = -1;
/*      */           break;
/*      */         case '.':
/*  774 */           ipv4Separators++;
/*  775 */           tmp = i - begin;
/*  776 */           if (tmp > 3 || begin < 0 || ipv4Separators > 3 || (ipv6Separators > 0 && currentIndex + compressLength < 12) || i + 1 >= ipLength || currentIndex >= bytes.length || (ipv4Separators == 1 && (!ipv4Mapped || (currentIndex != 0 && 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  784 */             !isValidIPv4Mapped(bytes, currentIndex, compressBegin, compressLength)) || (tmp == 3 && (
/*      */             
/*  786 */             !isValidNumericChar(ip.charAt(i - 1)) || 
/*  787 */             !isValidNumericChar(ip.charAt(i - 2)) || 
/*  788 */             !isValidNumericChar(ip.charAt(i - 3)))) || (tmp == 2 && (
/*  789 */             !isValidNumericChar(ip.charAt(i - 1)) || 
/*  790 */             !isValidNumericChar(ip.charAt(i - 2)))) || (tmp == 1 && 
/*  791 */             !isValidNumericChar(ip.charAt(i - 1)))))) {
/*  792 */             return null;
/*      */           }
/*  794 */           value <<= 3 - tmp << 2;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  799 */           begin = (value & 0xF) * 100 + (value >> 4 & 0xF) * 10 + (value >> 8 & 0xF);
/*  800 */           if (begin > 255) {
/*  801 */             return null;
/*      */           }
/*  803 */           bytes[currentIndex++] = (byte)begin;
/*  804 */           value = 0;
/*  805 */           begin = -1;
/*      */           break;
/*      */         default:
/*  808 */           if (!isValidHexChar(c) || (ipv4Separators > 0 && !isValidNumericChar(c))) {
/*  809 */             return null;
/*      */           }
/*  811 */           if (begin < 0) {
/*  812 */             begin = i;
/*  813 */           } else if (i - begin > 4) {
/*  814 */             return null;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  820 */           value += StringUtil.decodeHexNibble(c) << i - begin << 2;
/*      */           break;
/*      */       } 
/*      */     
/*      */     } 
/*  825 */     boolean isCompressed = (compressBegin > 0);
/*      */     
/*  827 */     if (ipv4Separators > 0) {
/*  828 */       if ((begin > 0 && i - begin > 3) || ipv4Separators != 3 || currentIndex >= bytes.length)
/*      */       {
/*      */         
/*  831 */         return null;
/*      */       }
/*  833 */       if (ipv6Separators != 0 && (ipv6Separators < 2 || ((isCompressed || ipv6Separators != 6 || ip
/*  834 */         .charAt(0) == ':') && (!isCompressed || ipv6Separators >= 8 || (ip
/*      */         
/*  836 */         .charAt(0) == ':' && compressBegin > 2))))) {
/*  837 */         return null;
/*      */       }
/*  839 */       value <<= 3 - i - begin << 2;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  844 */       begin = (value & 0xF) * 100 + (value >> 4 & 0xF) * 10 + (value >> 8 & 0xF);
/*  845 */       if (begin > 255) {
/*  846 */         return null;
/*      */       }
/*  848 */       bytes[currentIndex++] = (byte)begin;
/*      */     } else {
/*  850 */       int tmp = ipLength - 1;
/*  851 */       if ((begin > 0 && i - begin > 4) || ipv6Separators < 2 || (!isCompressed && (ipv6Separators + 1 != 8 || ip
/*      */ 
/*      */         
/*  854 */         .charAt(0) == ':' || ip.charAt(tmp) == ':')) || (isCompressed && (ipv6Separators > 8 || (ipv6Separators == 8 && ((compressBegin <= 2 && ip
/*      */ 
/*      */         
/*  857 */         .charAt(0) != ':') || (compressBegin >= 14 && ip
/*  858 */         .charAt(tmp) != ':'))))) || currentIndex + 1 >= bytes.length || (begin < 0 && ip
/*      */         
/*  860 */         .charAt(tmp - 1) != ':') || (compressBegin > 2 && ip
/*  861 */         .charAt(0) == ':')) {
/*  862 */         return null;
/*      */       }
/*  864 */       if (begin >= 0 && i - begin <= 4) {
/*  865 */         value <<= 4 - i - begin << 2;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  870 */       bytes[currentIndex++] = (byte)((value & 0xF) << 4 | value >> 4 & 0xF);
/*  871 */       bytes[currentIndex++] = (byte)((value >> 8 & 0xF) << 4 | value >> 12 & 0xF);
/*      */     } 
/*      */     
/*  874 */     if (currentIndex < bytes.length) {
/*  875 */       int toBeCopiedLength = currentIndex - compressBegin;
/*  876 */       int targetIndex = bytes.length - toBeCopiedLength;
/*  877 */       System.arraycopy(bytes, compressBegin, bytes, targetIndex, toBeCopiedLength);
/*      */       
/*  879 */       Arrays.fill(bytes, compressBegin, targetIndex, (byte)0);
/*      */     } 
/*      */     
/*  882 */     if (ipv4Separators > 0) {
/*      */ 
/*      */ 
/*      */       
/*  886 */       bytes[11] = -1; bytes[10] = -1;
/*      */     } 
/*      */     
/*  889 */     return bytes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toSocketAddressString(InetSocketAddress addr) {
/*      */     StringBuilder sb;
/*  900 */     String port = String.valueOf(addr.getPort());
/*      */ 
/*      */     
/*  903 */     if (addr.isUnresolved()) {
/*  904 */       String hostname = getHostname(addr);
/*  905 */       sb = newSocketAddressStringBuilder(hostname, port, !isValidIpV6Address(hostname));
/*      */     } else {
/*  907 */       InetAddress address = addr.getAddress();
/*  908 */       String hostString = toAddressString(address);
/*  909 */       sb = newSocketAddressStringBuilder(hostString, port, address instanceof Inet4Address);
/*      */     } 
/*  911 */     return sb.append(':').append(port).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toSocketAddressString(String host, int port) {
/*  918 */     String portStr = String.valueOf(port);
/*  919 */     return newSocketAddressStringBuilder(host, portStr, 
/*  920 */         !isValidIpV6Address(host)).append(':').append(portStr).toString();
/*      */   }
/*      */   
/*      */   private static StringBuilder newSocketAddressStringBuilder(String host, String port, boolean ipv4) {
/*  924 */     int hostLen = host.length();
/*  925 */     if (ipv4)
/*      */     {
/*  927 */       return (new StringBuilder(hostLen + 1 + port.length())).append(host);
/*      */     }
/*      */     
/*  930 */     StringBuilder stringBuilder = new StringBuilder(hostLen + 3 + port.length());
/*  931 */     if (hostLen > 1 && host.charAt(0) == '[' && host.charAt(hostLen - 1) == ']') {
/*  932 */       return stringBuilder.append(host);
/*      */     }
/*  934 */     return stringBuilder.append('[').append(host).append(']');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toAddressString(InetAddress ip) {
/*  950 */     return toAddressString(ip, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toAddressString(InetAddress ip, boolean ipv4Mapped) {
/*  978 */     if (ip instanceof Inet4Address) {
/*  979 */       return ip.getHostAddress();
/*      */     }
/*  981 */     if (!(ip instanceof Inet6Address)) {
/*  982 */       throw new IllegalArgumentException("Unhandled type: " + ip);
/*      */     }
/*      */     
/*  985 */     return toAddressString(ip.getAddress(), 0, ipv4Mapped);
/*      */   }
/*      */   
/*      */   private static String toAddressString(byte[] bytes, int offset, boolean ipv4Mapped) {
/*  989 */     int[] words = new int[8];
/*  990 */     for (int i = 0; i < words.length; i++) {
/*  991 */       int idx = (i << 1) + offset;
/*  992 */       words[i] = (bytes[idx] & 0xFF) << 8 | bytes[idx + 1] & 0xFF;
/*      */     } 
/*      */ 
/*      */     
/*  996 */     int currentStart = -1;
/*      */     
/*  998 */     int shortestStart = -1;
/*  999 */     int shortestLength = 0;
/* 1000 */     for (int j = 0; j < words.length; j++) {
/* 1001 */       if (words[j] == 0) {
/* 1002 */         if (currentStart < 0) {
/* 1003 */           currentStart = j;
/*      */         }
/* 1005 */       } else if (currentStart >= 0) {
/* 1006 */         int currentLength = j - currentStart;
/* 1007 */         if (currentLength > shortestLength) {
/* 1008 */           shortestStart = currentStart;
/* 1009 */           shortestLength = currentLength;
/*      */         } 
/* 1011 */         currentStart = -1;
/*      */       } 
/*      */     } 
/*      */     
/* 1015 */     if (currentStart >= 0) {
/* 1016 */       int currentLength = words.length - currentStart;
/* 1017 */       if (currentLength > shortestLength) {
/* 1018 */         shortestStart = currentStart;
/* 1019 */         shortestLength = currentLength;
/*      */       } 
/*      */     } 
/*      */     
/* 1023 */     if (shortestLength == 1) {
/* 1024 */       shortestLength = 0;
/* 1025 */       shortestStart = -1;
/*      */     } 
/*      */ 
/*      */     
/* 1029 */     int shortestEnd = shortestStart + shortestLength;
/* 1030 */     StringBuilder b = new StringBuilder(39);
/* 1031 */     if (shortestEnd < 0) {
/* 1032 */       b.append(Integer.toHexString(words[0]));
/* 1033 */       for (int k = 1; k < words.length; k++) {
/* 1034 */         b.append(':');
/* 1035 */         b.append(Integer.toHexString(words[k]));
/*      */       } 
/*      */     } else {
/*      */       boolean isIpv4Mapped;
/*      */       
/* 1040 */       if (inRangeEndExclusive(0, shortestStart, shortestEnd)) {
/* 1041 */         b.append("::");
/* 1042 */         isIpv4Mapped = (ipv4Mapped && shortestEnd == 5 && words[5] == 65535);
/*      */       } else {
/* 1044 */         b.append(Integer.toHexString(words[0]));
/* 1045 */         isIpv4Mapped = false;
/*      */       } 
/* 1047 */       for (int k = 1; k < words.length; k++) {
/* 1048 */         if (!inRangeEndExclusive(k, shortestStart, shortestEnd)) {
/* 1049 */           if (!inRangeEndExclusive(k - 1, shortestStart, shortestEnd))
/*      */           {
/* 1051 */             if (!isIpv4Mapped || k == 6) {
/* 1052 */               b.append(':');
/*      */             } else {
/* 1054 */               b.append('.');
/*      */             } 
/*      */           }
/* 1057 */           if (isIpv4Mapped && k > 5) {
/* 1058 */             b.append(words[k] >> 8);
/* 1059 */             b.append('.');
/* 1060 */             b.append(words[k] & 0xFF);
/*      */           } else {
/* 1062 */             b.append(Integer.toHexString(words[k]));
/*      */           } 
/* 1064 */         } else if (!inRangeEndExclusive(k - 1, shortestStart, shortestEnd)) {
/*      */           
/* 1066 */           b.append("::");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1071 */     return b.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getHostname(InetSocketAddress addr) {
/* 1080 */     return addr.getHostString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean inRangeEndExclusive(int value, int start, int end) {
/* 1095 */     return (value >= start && value < end);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\NetUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */