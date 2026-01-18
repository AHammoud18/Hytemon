package META-INF.versions.9.org.bouncycastle.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Vector;
import org.bouncycastle.util.StringList;
import org.bouncycastle.util.encoders.UTF8;

public final class Strings {
  private static String LINE_SEPARATOR;
  
  public static String fromUTF8ByteArray(byte[] paramArrayOfbyte) {
    return fromUTF8ByteArray(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public static String fromUTF8ByteArray(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    char[] arrayOfChar = new char[paramInt2];
    int i = UTF8.transcodeToUTF16(paramArrayOfbyte, paramInt1, paramInt2, arrayOfChar);
    if (i < 0)
      throw new IllegalArgumentException("Invalid UTF-8 input"); 
    return new String(arrayOfChar, 0, i);
  }
  
  public static byte[] toUTF8ByteArray(String paramString) {
    return toUTF8ByteArray(paramString.toCharArray());
  }
  
  public static byte[] toUTF8ByteArray(char[] paramArrayOfchar) {
    return toUTF8ByteArray(paramArrayOfchar, 0, paramArrayOfchar.length);
  }
  
  public static byte[] toUTF8ByteArray(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      toUTF8ByteArray(paramArrayOfchar, paramInt1, paramInt2, byteArrayOutputStream);
    } catch (IOException iOException) {
      throw new IllegalStateException("cannot encode string to byte array!");
    } 
    return byteArrayOutputStream.toByteArray();
  }
  
  public static void toUTF8ByteArray(char[] paramArrayOfchar, OutputStream paramOutputStream) throws IOException {
    toUTF8ByteArray(paramArrayOfchar, 0, paramArrayOfchar.length, paramOutputStream);
  }
  
  public static void toUTF8ByteArray(char[] paramArrayOfchar, int paramInt1, int paramInt2, OutputStream paramOutputStream) throws IOException {
    if (paramInt2 < 1)
      return; 
    byte[] arrayOfByte = new byte[64];
    byte b1 = 0;
    byte b2 = 0;
    while (true) {
      char c = paramArrayOfchar[paramInt1 + b2++];
      if (c < '') {
        arrayOfByte[b1++] = (byte)c;
      } else if (c < 'ࠀ') {
        arrayOfByte[b1++] = (byte)(0xC0 | c >> 6);
        arrayOfByte[b1++] = (byte)(0x80 | c & 0x3F);
      } else if (c >= '?' && c <= '?') {
        char c1 = c;
        if (c1 > '?')
          throw new IllegalStateException("invalid UTF-16 high surrogate"); 
        if (b2 >= paramInt2)
          throw new IllegalStateException("invalid UTF-16 codepoint (truncated surrogate pair)"); 
        char c2 = paramArrayOfchar[paramInt1 + b2++];
        if (c2 < '?' || c2 > '?')
          throw new IllegalStateException("invalid UTF-16 low surrogate"); 
        int i = ((c1 & 0x3FF) << 10 | c2 & 0x3FF) + 65536;
        arrayOfByte[b1++] = (byte)(0xF0 | i >> 18);
        arrayOfByte[b1++] = (byte)(0x80 | i >> 12 & 0x3F);
        arrayOfByte[b1++] = (byte)(0x80 | i >> 6 & 0x3F);
        arrayOfByte[b1++] = (byte)(0x80 | i & 0x3F);
      } else {
        arrayOfByte[b1++] = (byte)(0xE0 | c >> 12);
        arrayOfByte[b1++] = (byte)(0x80 | c >> 6 & 0x3F);
        arrayOfByte[b1++] = (byte)(0x80 | c & 0x3F);
      } 
      if (b1 + 4 > arrayOfByte.length) {
        paramOutputStream.write(arrayOfByte, 0, b1);
        b1 = 0;
      } 
      if (b2 >= paramInt2) {
        if (b1 > 0)
          paramOutputStream.write(arrayOfByte, 0, b1); 
        return;
      } 
    } 
  }
  
  public static String toUpperCase(String paramString) {
    boolean bool = false;
    char[] arrayOfChar = paramString.toCharArray();
    for (byte b = 0; b != arrayOfChar.length; b++) {
      char c = arrayOfChar[b];
      if ('a' <= c && 'z' >= c) {
        bool = true;
        arrayOfChar[b] = (char)(c - 97 + 65);
      } 
    } 
    return bool ? new String(arrayOfChar) : paramString;
  }
  
  public static String toLowerCase(String paramString) {
    boolean bool = false;
    char[] arrayOfChar = paramString.toCharArray();
    for (byte b = 0; b != arrayOfChar.length; b++) {
      char c = arrayOfChar[b];
      if ('A' <= c && 'Z' >= c) {
        bool = true;
        arrayOfChar[b] = (char)(c - 65 + 97);
      } 
    } 
    return bool ? new String(arrayOfChar) : paramString;
  }
  
  public static byte[] toByteArray(char[] paramArrayOfchar) {
    byte[] arrayOfByte = new byte[paramArrayOfchar.length];
    for (byte b = 0; b != arrayOfByte.length; b++)
      arrayOfByte[b] = (byte)paramArrayOfchar[b]; 
    return arrayOfByte;
  }
  
  public static byte[] toByteArray(String paramString) {
    byte[] arrayOfByte = new byte[paramString.length()];
    for (byte b = 0; b != arrayOfByte.length; b++) {
      char c = paramString.charAt(b);
      arrayOfByte[b] = (byte)c;
    } 
    return arrayOfByte;
  }
  
  public static int toByteArray(String paramString, byte[] paramArrayOfbyte, int paramInt) {
    int i = paramString.length();
    for (byte b = 0; b < i; b++) {
      char c = paramString.charAt(b);
      paramArrayOfbyte[paramInt + b] = (byte)c;
    } 
    return i;
  }
  
  public static boolean constantTimeAreEqual(String paramString1, String paramString2) {
    int i = (paramString1.length() == paramString2.length()) ? 1 : 0;
    int j = paramString1.length();
    if (i) {
      for (int k = 0; k != j; k++)
        i &= (paramString1.charAt(k) == paramString2.charAt(k)) ? 1 : 0; 
    } else {
      for (int k = 0; k != j; k++)
        i &= (paramString1.charAt(k) == ' ') ? 1 : 0; 
    } 
    return i;
  }
  
  public static String fromByteArray(byte[] paramArrayOfbyte) {
    return new String(asCharArray(paramArrayOfbyte));
  }
  
  public static char[] asCharArray(byte[] paramArrayOfbyte) {
    char[] arrayOfChar = new char[paramArrayOfbyte.length];
    for (byte b = 0; b != arrayOfChar.length; b++)
      arrayOfChar[b] = (char)(paramArrayOfbyte[b] & 0xFF); 
    return arrayOfChar;
  }
  
  public static String[] split(String paramString, char paramChar) {
    Vector<String> vector = new Vector();
    boolean bool = true;
    while (bool) {
      int i = paramString.indexOf(paramChar);
      if (i > 0) {
        String str = paramString.substring(0, i);
        vector.addElement(str);
        paramString = paramString.substring(i + 1);
        continue;
      } 
      bool = false;
      vector.addElement(paramString);
    } 
    String[] arrayOfString = new String[vector.size()];
    for (byte b = 0; b != arrayOfString.length; b++)
      arrayOfString[b] = vector.elementAt(b); 
    return arrayOfString;
  }
  
  public static StringList newList() {
    return (StringList)new StringListImpl(null);
  }
  
  public static String lineSeparator() {
    return LINE_SEPARATOR;
  }
  
  static {
    try {
      LINE_SEPARATOR = AccessController.<String>doPrivileged((PrivilegedAction<String>)new Object());
    } catch (Exception exception) {
      try {
        LINE_SEPARATOR = String.format("%n", new Object[0]);
      } catch (Exception exception1) {
        LINE_SEPARATOR = "\n";
      } 
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastl\\util\Strings.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */