package cotuba.application;

import cotuba.domain.Diretorios;
import cotuba.domain.Ebook;
import cotuba.epub.GeradorEpubImplComEpubLib;
import cotuba.pdf.GeradorPdfImplComIText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Cotuba {

    private GeradorEbook geradorEbook;

    @Autowired
    private  RenderizadorMDParaHTML renderizadorMDParaHTML;


    public void executa(Diretorios params) {

        String formato = params.getFormato();
        var arquivoDeSaida = params.getArquivoDeSaida();
        var capitulos = renderizadorMDParaHTML.renderiza(params.getDiretorioDosMD());

        var ebook = new Ebook(formato, arquivoDeSaida, capitulos);

        // TODO: POSSÍVEL MUDANÇA
        if ("pdf".equals(formato)) {
            geradorEbook = new GeradorPdfImplComIText();
        } else if ("epub".equals(formato)) {
            geradorEbook = new GeradorEpubImplComEpubLib();
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
        }

        geradorEbook.gera(ebook);

        System.out.println("Arquivo gerado com sucesso: " + arquivoDeSaida);
    }

}
