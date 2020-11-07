import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        ArrayList<Lexeme> lexemes_array = new ArrayList<>();

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer("input_text");

        while (!lexicalAnalyzer.isEnd_of_file()) {
            Lexeme finalLexeme_Class = new Lexeme();
            finalLexeme_Class.lexeme = "< ".concat(lexicalAnalyzer.currentLexeme()).concat(" >");
            finalLexeme_Class.classLexeme = get_ukr_ClassOfLexeme(lexicalAnalyzer.currentClass().toString());
            System.out.print(finalLexeme_Class.lexeme + " - " + finalLexeme_Class.classLexeme +"\n");
            lexemes_array.add(finalLexeme_Class);
            lexicalAnalyzer.check_and_move();
        }
        write_FileResult(lexemes_array);
        if (!lexicalAnalyzer.isSuccessful()) {
            System.out.println(lexicalAnalyzer.errorMessage());
        }
    }

    public static void write_FileResult(ArrayList<Lexeme> lexemes_array){
        try(FileWriter writer = new FileWriter("output", false))
        {
            for (Lexeme l:lexemes_array
            ) {
                writer.write(l.lexeme);
                writer.write(" - " + l.classLexeme);
                writer.append('\n');
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static String get_ukr_ClassOfLexeme(String currentLexeme){
        Map<String, String> classes;
        classes = new HashMap<>();
        classes.put("RESERVED", "<����: ������������ �����>");
        classes.put("IDENTIFIER", "<����: ��������������>");
        classes.put("OPERATOR", "<����: ���������>");
        classes.put("NUMBERS", "<����: �����>");
        classes.put("PUNCTUATION", "<����: ������� �����>");
        classes.put("UNKNOWN", "<������������ �������>");
        classes.put("SYMBOL_CONSTANT", "<����: �������� ���������>");
        classes.put("COMMENTS", "<����: ��������>");
        classes.put("STRING_CONSTANT", "<����: ������ ���������>");

        return classes.get(currentLexeme);
    }

}
