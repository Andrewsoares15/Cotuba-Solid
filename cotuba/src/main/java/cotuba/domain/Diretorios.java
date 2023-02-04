package cotuba.domain;

import java.nio.file.Path;

public class Diretorios {
    private Path diretorioDosMD;
    private FormatosEbook formato;
    private Path arquivoDeSaida;

    public Path getDiretorioDosMD() {
        return diretorioDosMD;
    }

    public void setDiretorioDosMD(Path diretorioDosMD) {
        this.diretorioDosMD = diretorioDosMD;
    }

    public FormatosEbook getFormato() {
        return formato;
    }

    public void setFormato(FormatosEbook formato) {
        this.formato = formato;
    }

    public Path getArquivoDeSaida() {
        return arquivoDeSaida;
    }

    public void setArquivoDeSaida(Path arquivoDeSaida) {
        this.arquivoDeSaida = arquivoDeSaida;
    }
}
