package cotuba.epub;

import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;
import cotuba.application.GeradorEbook;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class GeradorEpub implements GeradorEbook {

    @Override
    public void gera(Ebook ebook) {
        Path arquivoDeSaida = ebook.getPath();
        var epub = preencheEpub(ebook);

        var epubWriter = new EpubWriter();
        try {
            epubWriter.write(epub, Files.newOutputStream(arquivoDeSaida));
        } catch (IOException ex) {
            throw new IllegalStateException("Erro ao criar arquivo EPUB: " + arquivoDeSaida.toAbsolutePath(), ex);
        }
    }
    private Book preencheEpub(Ebook ebook){
        var epub = new Book();
        for (Capitulo capitulo : ebook.getCapitulos()) {

            String conteudo = capitulo.getConteudo();
            String titulo = capitulo.getTitulo();

            epub.addSection(titulo, new Resource(conteudo.getBytes(), MediatypeService.XHTML));
        }
        return epub;
    }
}
