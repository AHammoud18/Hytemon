package joptsimple;

import java.util.List;

public interface OptionDeclarer {
  OptionSpecBuilder accepts(String paramString);
  
  OptionSpecBuilder accepts(String paramString1, String paramString2);
  
  OptionSpecBuilder acceptsAll(List<String> paramList);
  
  OptionSpecBuilder acceptsAll(List<String> paramList, String paramString);
  
  NonOptionArgumentSpec<String> nonOptions();
  
  NonOptionArgumentSpec<String> nonOptions(String paramString);
  
  void posixlyCorrect(boolean paramBoolean);
  
  void allowsUnrecognizedOptions();
  
  void recognizeAlternativeLongOptions(boolean paramBoolean);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\OptionDeclarer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */