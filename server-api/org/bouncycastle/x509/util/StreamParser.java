package org.bouncycastle.x509.util;

import java.util.Collection;

public interface StreamParser {
  Object read() throws StreamParsingException;
  
  Collection readAll() throws StreamParsingException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\x50\\util\StreamParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */