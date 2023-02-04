package cotuba.cli;

import cotuba.CotubaConfig;
import cotuba.application.Cotuba;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {
    boolean modoVerboso = false;

    try {
      var leitorOpcoesCLI = new LeitorOpcoesCLI(args);


      modoVerboso = leitorOpcoesCLI.isModoVerboso();

      var annotationConfigApplicationContext = new AnnotationConfigApplicationContext(CotubaConfig.class);
      Cotuba cotuba = annotationConfigApplicationContext.getBean(Cotuba.class);
      cotuba.executa(leitorOpcoesCLI.getDiretorios());

    } catch (Exception ex) {
      System.err.println(ex.getMessage());
      if (modoVerboso) {
        ex.printStackTrace();
      }
      System.exit(1);
    }
  }

}
