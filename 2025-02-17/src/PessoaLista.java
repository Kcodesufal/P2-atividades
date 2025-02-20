import java.util.ArrayList;

public class PessoaLista {

    private ArrayList<Pessoa> pessoa;

    public PessoaLista() {
        this.pessoa = new ArrayList<>();
    }

    public void addPessoa (Pessoa person) {

        pessoa.add(person);
    }

   public String GetAllTaxes () {

        double ClientTaxes = 0;

        for (int i = 0; i < pessoa.size(); i++)
        {
            ClientTaxes += pessoa.get(i).tax();
        }

        return "TOTAL TAXES: $ " + String.format("%.2f",ClientTaxes);
    }

    public String GetTaxList() {
        String lista = "TAXES PAID:\n";
        for (int i = 0; i < pessoa.size(); i++)
        {
            lista = lista + pessoa.get(i).getName() + ": $ " + String.format("%.2f",pessoa.get(i).tax()) + "\n";
        }
        return lista;
    }
}
