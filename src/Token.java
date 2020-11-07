import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Token {

    RESERVED("^(abstract|assert|boolean|Boolean|break|byte|case|catch|char|Character|class|const|continue|double|do|default|else|enum|extends|false|finally|final|float|for|goto|if|import|int|Integer|implement|instanceof|interface|long|native|new|null|package|private|public|protected|return|static|String|switch|short|super|synchronized|this|throws|throw|try|true|transient|void|while)"),
    IDENTIFIER("^([A-Za-z_](\\w|\\d)*)"),
    OPERATOR("^(([+]|[-]|[=]|[&]|[|]|[<]|[>]){2}|(([+]|[-]|[*]|[\\\\/]|[%]|[=]|[!]|[&]|[|]|[>]|[<])[=]?))"),
    UNKNOWN("^(([\\d]+[A-DF-WYZa-df-wyz_][A-Za-z_]*[0-9]*[\\n\\r ]?)|([Р-пр-џ]+))"),
    NUMBERS("^((([1-9]+)\\.([\\d]+))|(([0])\\.([\\d]+))|([1-9][\\d]*)|([0])([xX][0-9A-Fa-f]+)?)([eE][+-]?[\\d]+)?"),
    PUNCTUATION("^([\\.\\,\\;\\:\\(\\)\\[\\]\\{\\}])"),
    SYMBOL_CONSTANT("^(\\'.\\')"),
    STRING_CONSTANT("^(\\\".*\\\")"),
    COMMENTS("(\\/\\*[^~]*?\\*\\/)|(\\/\\/[^\\n]*)");

    private final Pattern pattern;

    Token(String regex) {
        pattern = Pattern.compile(regex);
    }

    int lastCharPosition_of_match(String s) {
        Matcher m = pattern.matcher(s);
        if (m.find()) {
            return m.end();
        }
        return -1;
    }
}