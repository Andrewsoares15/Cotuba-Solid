package cotuba.cli;

import cotuba.domain.Diretorios;
import cotuba.domain.FormatosEbook;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

class LeitorOpcoesCLI {

    private Diretorios diretorios = new Diretorios();
    private boolean modoVerboso = false;

    public LeitorOpcoesCLI(String[] args) {
        try {
            Options options = criaOptions();

            CommandLine cmd = parseArgumentos(args, options);

            trataDiretoriosDosMD(cmd);

            trataFormato(cmd);

            trataArquivosdeSaida(cmd);

            trataModoVerboso(cmd);

        } catch (Exception e) {
            throw new IllegalArgumentException("Argumentos inválidos!");
        }
    }

    private void trataModoVerboso(CommandLine cmd) {
        modoVerboso = cmd.hasOption("verbose");
    }

    private void trataArquivosdeSaida(CommandLine cmd) throws IOException {
        String nomeDoArquivoDeSaidaDoEbook = cmd.getOptionValue("output");

        if (nomeDoArquivoDeSaidaDoEbook != null) {
            diretorios.setArquivoDeSaida(Paths.get(nomeDoArquivoDeSaidaDoEbook));
        } else {
            diretorios.setArquivoDeSaida(Paths.get("book." + diretorios.getFormato().name().toLowerCase()));
        }
        if (Files.isDirectory(diretorios.getArquivoDeSaida())) {
            // deleta arquivos do diretório recursivamente
            Files.walk(diretorios.getArquivoDeSaida()).sorted(Comparator.reverseOrder())
                    .map(Path::toFile).forEach(File::delete);
        } else {
            Files.deleteIfExists(diretorios.getArquivoDeSaida());
        }
    }

    private void trataFormato(CommandLine cmd) {
        FormatosEbook nomeDoFormatoDoEbook = FormatosEbook.valueOf(cmd.getOptionValue("format"));

        if (nomeDoFormatoDoEbook != null) {
            diretorios.setFormato(nomeDoFormatoDoEbook);
        } else {
            diretorios.setFormato(nomeDoFormatoDoEbook);
        }
    }

    private void trataDiretoriosDosMD(CommandLine cmd) {
        String nomeDoDiretorioDosMD = cmd.getOptionValue("dir");

        if (nomeDoDiretorioDosMD != null) {
            diretorios.setDiretorioDosMD(Paths.get(nomeDoDiretorioDosMD));
            if (!Files.isDirectory(diretorios.getDiretorioDosMD())) {
                throw new IllegalArgumentException(nomeDoDiretorioDosMD + " não é um diretório.");
            }
        } else {
            Path diretorioAtual = Paths.get("");
            diretorios.setDiretorioDosMD(diretorioAtual);
        }
    }

    private CommandLine parseArgumentos(String[] args, Options options) {
        CommandLineParser cmdParser = new DefaultParser();
        var ajuda = new HelpFormatter();
        CommandLine cmd;
        try {
            cmd = cmdParser.parse(options, args);
        } catch (ParseException e) {
            ajuda.printHelp("cotuba", options);
            throw new IllegalArgumentException("Argumentos inválidos!", e);
        }
        return cmd;
    }

    private Options criaOptions() {
        var options = new Options();

        var opcaoDeDiretorioDosMD = new Option("d", "dir", true,
                "Diretório que contém os arquivos md. Default: diretório atual.");
        options.addOption(opcaoDeDiretorioDosMD);

        var opcaoDeFormatoDoEbook = new Option("f", "format", true,
                "Formato de saída do ebook. Pode ser: pdf ou epub. Default: pdf");
        options.addOption(opcaoDeFormatoDoEbook);

        var opcaoDeArquivoDeSaida = new Option("o", "output", true,
                "Arquivo de saída do ebook. Default: book.{formato}.");
        options.addOption(opcaoDeArquivoDeSaida);

        var opcaoModoVerboso = new Option("v", "verbose", false,
                "Habilita modo verboso.");
        options.addOption(opcaoModoVerboso);
        return options;
    }

    public boolean isModoVerboso() {
        return modoVerboso;
    }

    public Diretorios getDiretorios() {
        return diretorios;
    }

    public void setDiretorios(Diretorios diretorios) {
        this.diretorios = diretorios;
    }

}
