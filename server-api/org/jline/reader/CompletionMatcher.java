package org.jline.reader;

import java.util.List;
import java.util.Map;

public interface CompletionMatcher {
  void compile(Map<LineReader.Option, Boolean> paramMap, boolean paramBoolean1, CompletingParsedLine paramCompletingParsedLine, boolean paramBoolean2, int paramInt, String paramString);
  
  List<Candidate> matches(List<Candidate> paramList);
  
  Candidate exactMatch();
  
  String getCommonPrefix();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\CompletionMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */