package org.jline.reader;

import java.util.regex.Pattern;
import org.jline.utils.AttributedString;

public interface Highlighter {
  AttributedString highlight(LineReader paramLineReader, String paramString);
  
  default void refresh(LineReader reader) {}
  
  default void setErrorPattern(Pattern errorPattern) {}
  
  default void setErrorIndex(int errorIndex) {}
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\Highlighter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */