package org.bouncycastle.pkix.util.filter;

public class HTMLFilter implements Filter {
  public String doFilter(String paramString) {
    StringBuilder stringBuilder = new StringBuilder(paramString);
    for (byte b = 0; b < stringBuilder.length(); b += 4) {
      char c = stringBuilder.charAt(b);
      switch (c) {
        case '<':
          stringBuilder.replace(b, b + 1, "&#60");
          break;
        case '>':
          stringBuilder.replace(b, b + 1, "&#62");
          break;
        case '(':
          stringBuilder.replace(b, b + 1, "&#40");
          break;
        case ')':
          stringBuilder.replace(b, b + 1, "&#41");
          break;
        case '#':
          stringBuilder.replace(b, b + 1, "&#35");
          break;
        case '&':
          stringBuilder.replace(b, b + 1, "&#38");
          break;
        case '"':
          stringBuilder.replace(b, b + 1, "&#34");
          break;
        case '\'':
          stringBuilder.replace(b, b + 1, "&#39");
          break;
        case '%':
          stringBuilder.replace(b, b + 1, "&#37");
          break;
        case ';':
          stringBuilder.replace(b, b + 1, "&#59");
          break;
        case '+':
          stringBuilder.replace(b, b + 1, "&#43");
          break;
        case '-':
          stringBuilder.replace(b, b + 1, "&#45");
          break;
        default:
          b -= 3;
          break;
      } 
    } 
    return stringBuilder.toString();
  }
  
  public String doFilterUrl(String paramString) {
    return doFilter(paramString);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pki\\util\filter\HTMLFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */