package cotuba.application;

import cotuba.domain.Diretorios;
import cotuba.domain.Ebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Cotuba {

    @Autowired
    private GeradorEpub geradorEpub;
    @Autowired
    private  GeradorPdf geradorPdf;
    @Autowired
    private  RenderizadorMDParaHTML renderizadorMDParaHTML;


    public void executa(Diretorios params) {

        String formato = params.getFormato();
        var arquivoDeSaida = params.getArquivoDeSaida();
        var capitulos = renderizadorMDParaHTML.renderiza(params.getDiretorioDosMD());

        var ebook = new Ebook(formato, arquivoDeSaida, capitulos);

        // TODO: POSSÍVEL MUDANÇA
        if ("pdf".equals(formato)) {
            geraPdf(ebook);
        } else if ("epub".equals(formato)) {
            geraEpub(ebook);
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
        }

        System.out.println("Arquivo gerado com sucesso: " + arquivoDeSaida);
    }

    private void geraEpub(Ebook ebook) {
        geradorEpub.gera(ebook);
    }

    private void geraPdf(Ebook ebook) {
        geradorPdf.gera(ebook);
    }

}
