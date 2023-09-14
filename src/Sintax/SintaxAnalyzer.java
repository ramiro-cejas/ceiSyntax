package Sintax;

import Lexical.LexicalAnalyzer;
import Lexical.LexicalException;
import Lexical.Token;

import java.io.IOException;

public class SintaxAnalyzer {
    private LexicalAnalyzer lexicalAnalyzer;
    private Token tokenActual;
    public SintaxAnalyzer(LexicalAnalyzer lexicalAnalyzer) throws LexicalException, SintaxException, IOException {
        this.lexicalAnalyzer = lexicalAnalyzer;
        inicial();
    }

    private void match(String tokenName) throws SintaxException, LexicalException, IOException {
        if (tokenName.equals(tokenActual.getName())){
            tokenActual = lexicalAnalyzer.getNextToken();
        }else {
            throw new SintaxException(lexicalAnalyzer.getLine(), tokenActual.getName(), tokenName);
        }
    }

    private void inicial() throws LexicalException, SintaxException, IOException {
        listaClases();
        match("EOF");
    }

    private void listaClases() throws SintaxException, LexicalException, IOException {
        if (tokenActual.getName().equals("keyword_class") || tokenActual.getName().equals("keyword_interface")){
            clase();
            listaClases();
        } else {
            //Epsilon
        }
    }

    private void clase() throws SintaxException, LexicalException, IOException {
        if (tokenActual.getName().equals("keyword_class")){
            claseConcreta();
        } else if (tokenActual.getName().equals("keyword_interface")){
            interfaceConcreta();
        } else {
            throw new SintaxException(lexicalAnalyzer.getLine(), tokenActual.getName(), "keyword_class o keyword_interface");
        }
    }

    private void interfaceConcreta() throws LexicalException, SintaxException, IOException {
        match("keyword_interface");
        match("idClass");
        extiendeOpcional();
        match("{");
        listaEncabezados();
        match("}");
    }

    private void listaEncabezados() throws LexicalException, SintaxException, IOException {
        if (tokenActual.getName().equals("keyword_static") || tokenActual.getName().equals("keyword_boolean") || tokenActual.getName().equals("keyword_char") || tokenActual.getName().equals("keyword_int") || tokenActual.getName().equals("keyword_float") || tokenActual.getName().equals("idClass") || tokenActual.getName().equals("keyword_void")){
            encabezadoMetodo();
            listaEncabezados();
        } else {
            //Epsilon
        }
    }
    
    private void encabezadoMetodo() throws LexicalException, SintaxException, IOException {
        parte1Miembro();
        argsFormales();
        match(";");
    }

    private void argsFormales() throws LexicalException, SintaxException, IOException {
        match("(");
        listaArgsFormalesOpcional();
        match(")");
    }

    private void listaArgsFormalesOpcional() {
        if (tokenActual.getName().equals("keyword_boolean") || tokenActual.getName().equals("keyword_char") || tokenActual.getName().equals("keyword_int") || tokenActual.getName().equals("keyword_float") || tokenActual.getName().equals("idClass")){
            argFormal();
            listaArgsFormales();
        } else {
            //Epsilon
        }
    }

    private void parte1Miembro() throws LexicalException, SintaxException, IOException {
        estaticoOpcional();
        tipoMiembro();
        match("idMetVar");
    }

    private void extiendeOpcional() throws LexicalException, SintaxException, IOException {
        if (tokenActual.getName().equals("keyword_extends")){
            match("keyword_extends");
            match("idClass");
        } else {
            //Epsilon
        }
    }

    private void herenciaOpcional() {
        if (tokenActual.getName().equals("keyword_extends")){
            heredaDe();
        } else if (tokenActual.getName().equals("keyword_implements")){
            implementaA();
        } else {
            //Epsilon
        }
    }

    private void claseConcreta() {
        match("keyword_class");
        match("idClass");
        herenciaOpcional();
        match("{");
        listaMiembros();
        match("}");
    }



}