package cotuba.domain;

import cotuba.application.GeradorEbook;
import cotuba.epub.GeradorEpub;
import cotuba.pdf.GeradorPdf;

public enum FormatosEbook {

    PDF(new GeradorPdf()),
    EPUB(new GeradorEpub());

    private GeradorEbook gerador;

    FormatosEbook(GeradorEbook geradorEbook){
        gerador = geradorEbook;
    }
    public GeradorEbook getGerador(){
        return gerador;
    }
}
