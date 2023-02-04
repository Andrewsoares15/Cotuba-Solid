package cotuba.application;

import cotuba.domain.Ebook;
import cotuba.domain.FormatosEbook;

public interface GeradorEbook {
    void gera(Ebook ebook);


    static GeradorEbook cria(FormatosEbook formato) {

    return formato.getGerador();

    }

}
