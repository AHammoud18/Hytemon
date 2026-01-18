package org.bouncycastle.pkix.util.filter;

public class SQLFilter implements Filter {
  public String doFilter(String paramString) {
    StringBuilder stringBuilder = new StringBuilder(paramString);
    for (byte b = 0; b < stringBuilder.length(); b++) {
      char c = stringBuilder.charAt(b);
      switch (c) {
        case '\'':
          stringBuilder.replace(b, b + 1, "\\'");
          b++;
          break;
        case '"':
          stringBuilder.replace(b, b + 1, "\\\"");
          b++;
          break;
        case '=':
          stringBuilder.replace(b, b + 1, "\\=");
          b++;
          break;
        case '-':
          stringBuilder.replace(b, b + 1, "\\-");
          b++;
          break;
        case '/':
          stringBuilder.replace(b, b + 1, "\\/");
          b++;
          break;
        case '\\':
          stringBuilder.replace(b, b + 1, "\\\\");
          b++;
          break;
        case ';':
          stringBuilder.replace(b, b + 1, "\\;");
          b++;
          break;
        case '\r':
          stringBuilder.replace(b, b + 1, "\\r");
          b++;
          break;
        case '\n':
          stringBuilder.replace(b, b + 1, "\\n");
          b++;
          break;
      } 
    } 
    return stringBuilder.toString();
  }
  
  public String doFilterUrl(String paramString) {
    return doFilter(paramString);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pki\\util\filter\SQLFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */