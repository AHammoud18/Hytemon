package it.unimi.dsi.fastutil.chars;

public interface CharHash {
  public static interface Strategy {
    int hashCode(char param1Char);
    
    boolean equals(char param1Char1, char param1Char2);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharHash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */