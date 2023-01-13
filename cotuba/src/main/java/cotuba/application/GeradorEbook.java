package cotuba.application;

import cotuba.domain.Ebook;
import cotuba.epub.GeradorEpub;
import cotuba.pdf.GeradorPdf;

public interface GeradorEbook {
    void gera(Ebook ebook);

    static GeradorEbook cria(String	formato) {

        GeradorEbook geradorEbook;
        // TODO: POSSÍVEL MUDANÇA
        if ("pdf".equals(formato)) {
            geradorEbook = new GeradorPdf();
        } else if ("epub".equals(formato)) {
            geradorEbook = new GeradorEpub();
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
        }

        return geradorEbook;
    }

}
