import easyaccept.EasyAccept;
/**
 * Chama os arquivos de teste e usa as funções de Facade.
 */
public class Main {
    public static void main(String[] args) {
        String[] args2 = {"br.ufal.ic.p2.jackut.Facade",
                "tests/us1_1.txt", "tests/us1_2.txt",
                "tests/us2_1.txt", "tests/us2_2.txt",
                "tests/us3_1.txt", "tests/us3_2.txt",
                "tests/us4_1.txt", "tests/us4_2.txt",
        };
        EasyAccept.main(args2);
    }
}