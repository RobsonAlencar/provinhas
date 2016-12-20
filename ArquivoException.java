package br.gov.serpro.provinha;

/**
 * Created by 83509739353 on 09/09/16.
 */
public class ArquivoException extends Exception {
    public String msg;
    public ArquivoException(String msg) {
        this.msg = msg;
    }
}
