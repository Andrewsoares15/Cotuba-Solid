package cotuba.md;


import cotuba.application.RenderizadorMDParaHTML;
import cotuba.domain.Capitulo;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Component
public class RenderizadorMDParaHTMLImplComCommonMark implements RenderizadorMDParaHTML {

    @Override
    public List<Capitulo> renderiza(Path diretorioDosMD) {
        List<Path> paths = obtemArquivosMd(diretorioDosMD);

        return paths.stream().map(arquivo -> {
                    var capitulo = new Capitulo();
                    Node document = getNode(arquivo, capitulo);
                    renderizaParaHtml(arquivo, document, capitulo);
                    return capitulo;
                }).collect(Collectors.toList());
    }

    private void renderizaParaHtml(Path arquivoMD, Node document, Capitulo capitulo) {
        try {
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String html = renderer.render(document);
            capitulo.setConteudo(html);

        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao renderizar para HTML o arquivo " + arquivoMD, ex);
        }
    }

    private Node getNode(Path arquivoMD, Capitulo capitulo) {
        Parser parser = Parser.builder().build();
        Node document = null;

        try {
            document = parser.parseReader(Files.newBufferedReader(arquivoMD));
            document.accept(getVisitor(capitulo));
        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao fazer parse do arquivo " + arquivoMD, ex);
        }
        return document;
    }


    private List<Path> obtemArquivosMd(Path diretorioDosMD){
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");

        try (Stream<Path> arquivosMD = Files.list(diretorioDosMD)) {
            return arquivosMD
                    .filter(matcher::matches)
                    .sorted()
                    .collect(Collectors.toList());
        }catch (IOException e){
            throw new IllegalStateException("Erro ao obter os arquivosMd  " + diretorioDosMD, e);
        }
    }

    private AbstractVisitor getVisitor(Capitulo capitulo) {
        return new AbstractVisitor() {
            @Override
            public void visit(Heading heading) {
                if (heading.getLevel() == 1) {
                    // capítulo
                    String tituloDoCapitulo = ((Text) heading.getFirstChild()).getLiteral();
                    capitulo.setTitulo(tituloDoCapitulo);
                } else if (heading.getLevel() == 2) {
                    // seção
                } else if (heading.getLevel() == 3) {
                    // título
                }
            }

        };
    }
}
