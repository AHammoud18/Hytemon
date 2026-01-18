package joptsimple;

import java.util.List;

public interface OptionSpec<V> {
  List<V> values(OptionSet paramOptionSet);
  
  V value(OptionSet paramOptionSet);
  
  List<String> options();
  
  boolean isForHelp();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\OptionSpec.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */