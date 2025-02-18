import java.util.ArrayList;

public class Clientes {

    private ArrayList<Pessoa> pessoa;

    public Clientes() {
        this.pessoa = new ArrayList<>();
    }

    public void addPessoa (Pessoa person) {

        pessoa.add(person);
    }

   public String get_all_taxes () {

        double client_taxes = 0;

        for (int i = 0; i < pessoa.size(); i++)
        {
            client_taxes += pessoa.get(i).tax();
        }

        return "TOTAL TAXES: $ " + String.format("%.2f",client_taxes);
    }

    public String get_tax_list() {
        String lista = "TAXES PAID:\n";
        for (int i = 0; i < pessoa.size(); i++)
        {
            lista = lista + pessoa.get(i).getName() + ": $ " + String.format("%.2f",pessoa.get(i).tax()) + "\n";
        }
        return lista;
    }
}
