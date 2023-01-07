package cotuba.domain;

import java.nio.file.Path;
import java.util.List;

public class Ebook {
    private String formato;
    private Path path;
    private List<Capitulo> capitulos;

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public List<Capitulo> getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(List<Capitulo> capitulos) {
        this.capitulos = capitulos;
    }
    public boolean isUltimoCapitulo(Capitulo capitulo) {
        if(capitulos.size() <= 0) return false;

        return this.capitulos.get(this.capitulos.size() - 1)
                .equals(capitulo);
    }

    public Ebook(String formato, Path path, List<Capitulo> capitulos){
        this.formato = formato;
        this.path = path;
        this.capitulos = capitulos;
    }

}
