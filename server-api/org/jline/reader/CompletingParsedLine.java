package org.jline.reader;

public interface CompletingParsedLine extends ParsedLine {
  CharSequence escape(CharSequence paramCharSequence, boolean paramBoolean);
  
  int rawWordCursor();
  
  int rawWordLength();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\CompletingParsedLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */