package ch.randelshofer.fastdoubleparser;

abstract class AbstractFloatValueParser extends AbstractNumberParser {
  public static final int MAX_INPUT_LENGTH = 2147483643;
  
  static final long MINIMAL_NINETEEN_DIGIT_INTEGER = 1000000000000000000L;
  
  static final int MAX_EXPONENT_NUMBER = 1024;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\AbstractFloatValueParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */