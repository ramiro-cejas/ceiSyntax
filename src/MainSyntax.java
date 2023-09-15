import FileManipulator.FileManipulator;
import Lexical.LexicalAnalyzer;
import Lexical.LexicalException;
import Lexical.Token;
import Syntax.SyntaxAnalyzer;

import java.io.IOException;
import java.util.*;

class MainSyntax{
    public static void main(String[] args) {
        long actual = System.currentTimeMillis();
        if (args.length == 0){
            System.out.println("ERROR: Ningun archivo fuente pasado como parámetro. Por favor proporcione la ruta del archivo como parámetro.");
        }
        else{
            ArrayList<Exception> errorsCollection = new ArrayList<>();
            System.out.println("Se va a ejecutar el analizador léxico en el archivo: "+args[0]);
            try {
                FileManipulator fileManipulator = new FileManipulator(args[0]);
                LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManipulator);
                SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer);

                try {
                    syntaxAnalyzer.analyze();
                }catch (Exception e){
                    errorsCollection.add(e);
                }

            } catch (IOException e) {
                System.out.println("Error al abrir o leer el archivo.");
            }

            if (errorsCollection.isEmpty()){
                System.out.println("\n---------------------------------------------------------\n\n[SinErrores]");
            }else {
                for (Exception lexicalError : errorsCollection) {
                    System.out.println(lexicalError.toString());
                }
                System.out.println("\nCantidad de errores totales encontrados: "+errorsCollection.size());
            }
        }
        System.out.println("La ejecución a durado: "+(System.currentTimeMillis() - actual)+"ms");
    }
}