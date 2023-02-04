package cotuba.application;

import cotuba.domain.Diretorios;
import cotuba.domain.Ebook;
import cotuba.domain.FormatosEbook;
import cotuba.md.RenderizadorMDParaHTML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Cotuba {

    @Autowired
    private RenderizadorMDParaHTML renderizadorMDParaHTML;

    public void executa(Diretorios params) {

        FormatosEbook formato = params.getFormato();
        var arquivoDeSaida = params.getArquivoDeSaida();
        var capitulos = renderizadorMDParaHTML.renderiza(params.getDiretorioDosMD());

        var ebook = new Ebook(formato, arquivoDeSaida, capitulos);

        GeradorEbook gerador = GeradorEbook.cria(formato);
        gerador.gera(ebook);

        System.out.println("Arquivo gerado com sucesso: " + arquivoDeSaida);
    }

}
