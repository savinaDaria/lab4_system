import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {
    private final StringBuilder input = new StringBuilder();
    private Token token;
    private String lexeme;
    private boolean end_of_file = false;
    private String errorMessage = "";
    private final Set<Character> blankChars = new HashSet<>();

    public LexicalAnalyzer(String filePath) throws Exception {
        input.append(get_file_strings(filePath).concat(" "));
        find_remove_comments();
        {
            blankChars.add('\r');
            blankChars.add('\n');
            blankChars.add((char) 8);
            blankChars.add((char) 9);
            blankChars.add((char) 11);
            blankChars.add((char) 12);
            blankChars.add((char) 32);
        }
        check_and_move();
    }
    public void find_remove_comments(){
        String comment_regex="(\\/\\*[^~]*?\\*\\/)|(\\/\\/[^\\n]*)";
        Pattern pattern_comment;
        int begin, end;
        pattern_comment=Pattern.compile(comment_regex);
        Matcher m = pattern_comment.matcher(input);
        while(m.find()){
            begin=m.start();
            end=m.end();
            input.replace(begin,end,"");
            m = pattern_comment.matcher(input);
        }
    }

    public  String get_file_strings(String pathname) throws Exception {
        File f = new File(pathname);
        final int length = (int) f.length();
        if (length != 0) {
            char[] words = new char[length];
            InputStreamReader stream = new InputStreamReader(new FileInputStream(f), "CP1251");
            final int read = stream.read(words);
            return new String(words, 0, read);
        }
        else{
            throw new Exception("Помилка у файлі");
        }
    }

    public void check_and_move() {
        if (end_of_file) {
            return;
        }
        delete_white_spaces();
        if (input.length() == 0) {
            end_of_file = true;
            return;

        }

        if (find_next_lexeme()) {
            return;
        }

        end_of_file = true;

        if (input.length() > 0) {

            errorMessage = " ------ Кінець файлу ------";
        }
    }

    private void delete_white_spaces() {
        int charsToDelete = 0;
        while (blankChars.contains(input.charAt(charsToDelete))&& input.length() > 0) {

            if(charsToDelete==input.length()-1){
                end_of_file=true;
                break;
            }
            charsToDelete++;
        }

        if (charsToDelete > 0) {
            input.delete(0, charsToDelete);
        }
    }

    private boolean find_next_lexeme() {
        for (Token t : Token.values()) {
            int end = t.lastCharPosition_of_match(input.toString());

            if (end != -1) {
                token = t;
                if(t.name().equals("UNKNOWN")){
                    lexeme = input.substring(0, end-1);
                }
                else{
                    lexeme = input.substring(0, end);
                }
                input.delete(0, end);
                return true;
            }
        }

        return false;
    }

    public Token currentClass() {
        return token;
    }

    public String currentLexeme() {
        return lexeme;
    }

    public boolean isSuccessful() {
        return errorMessage.isEmpty();
    }

    public String errorMessage() {
        return errorMessage;
    }

    public boolean isEnd_of_file() {
        return end_of_file;
    }
}