import java.util.ArrayList;

public class RegistrodePessoas {

    private ArrayList<Pessoa> pessoa;

    public RegistrodePessoas() {
        this.pessoa = new ArrayList<>();
    }

    public void addPessoa(Pessoa person) {

        pessoa.add(person);
    }

    public double GetAllTaxes() {

        double TotalTax = 0;

        for (int i = 0; i < pessoa.size(); i++) {
            TotalTax += pessoa.get(i).tax();
        }

        return TotalTax;
    }

    public String PrintAllTaxes()
    {

        return "TOTAL TAXES: $ " + String.format("%.2f", GetAllTaxes());
    }

    public String GetTaxList() {
        String lista = "TAXES PAID:\n";
        for (int i = 0; i < pessoa.size(); i++) {
            lista = lista + pessoa.get(i).getName() + ": $ " + String.format("%.2f", pessoa.get(i).tax()) + "\n";
        }
        return lista;
    }

    @Override

    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("RegistrodePessoas:\n");
        for (Pessoa p : pessoa) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }
}
